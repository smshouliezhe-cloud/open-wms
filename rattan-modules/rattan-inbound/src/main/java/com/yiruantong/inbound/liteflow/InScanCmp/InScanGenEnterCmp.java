package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InEnterStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@LiteflowComponent(id = "inScanGenEnterCmp", name = "3.生成入库单组件")
@RequiredArgsConstructor
public class InScanGenEnterCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final IInEnterService inEnterService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInEnterDetailService inEnterDetailService;
  private final IBaseProductService baseProductService;
  private final IBasePositionService basePositionService;

  @Override
  public void process() {
    InScanContext cxt = this.getContextBean(InScanContext.class);
    InScanOrderBo inScanOrderBo = cxt.getInScanOrderBo();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据
    InOrder inOrder = cxt.getInOrder();

    //#region 生成入库单主表
    LambdaQueryWrapper<InEnter> enterLambdaQueryWrapper = new LambdaQueryWrapper<>();
    enterLambdaQueryWrapper.eq(InEnter::getOrderId, inOrder.getOrderId())
      .eq(InEnter::getOrderCode, inOrder.getOrderCode())
      .last("limit 1");
    var inEnter = inEnterService.getOne(enterLambdaQueryWrapper);
    if ((ObjectUtil.isEmpty(inEnter) || !cxt.isInGenerateShelveOnly()) || inScanOrderBo.getScanInType().equals(InventorySourceTypeEnum.PC_LPN_SCAN_IN)) {
      String enterCode = DBUtils.getCodeRegular(MenuEnum.MENU_1650, LoginHelper.getTenantId());  // 入库单号
      inEnter = new InEnter();
      BeanUtil.copyProperties(inOrder, inEnter);
      inEnter.setEnterCode(enterCode);

//      inEnter.setApplyDate(DateUtil.date());
      inEnter.setScanInType(inScanOrderBo.getScanInType().getName());
      inEnter.setEnterStatus(InEnterStatusEnum.NEWED.getName());

      inEnter.setUserId(LoginHelper.getUserId());
      inEnter.setNickName(LoginHelper.getNickname());
      inEnter.setDeptId(LoginHelper.getDeptId());
      inEnter.setDeptName(LoginHelper.getDeptName());
      inEnter.setAuditor(null);
      inEnter.setAuditDate(null);
      inEnter.setAuditing(null);
      inEnter.setLpnCode(inScanOrderBo.getLpnCode());
      inEnter.setRemark(inScanOrderBo.getRemark());
      //如果是一键入库 有些值需要按照一键入库的值去填写
      if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PC_QUICK_ENTER) {
        if (dataList.get(0).getInStorageDate() != null) {
          inEnter.setApplyDate(dataList.get(0).getInStorageDate());
        }
      }

      inEnterService.save(inEnter); // 保存入库单
    }
    //#endregion

    cxt.setInEnter(inEnter);
    genDetail(cxt);
  }

  /**
   * 生成入库单明细
   *
   * @param ctx 上下文
   */
  private void genDetail(InScanContext ctx) {
    InScanOrderBo inScanOrderBo = ctx.getInScanOrderBo();
    InEnter inEnter = ctx.getInEnter();
    InOrder inOrder = ctx.getInOrder();
    List<InScanOrderDetailBo> dataList = ctx.getInScanOrderBo().getDataList();

    //#region 明细处理
    BigDecimal totalQuantity = BigDecimal.ZERO; // 数量合计求和
    BigDecimal totalWeight = BigDecimal.ZERO; // 毛重合计求和
    BigDecimal totalNewWeight = BigDecimal.ZERO; // 净重合计求和
    BigDecimal totalAmount = BigDecimal.ZERO; // 金额合计求和

    // 用于存放 报废出库 入库明细信息
    List<InEnterDetail> invalidateDetails = new ArrayList<>();
    // 入库明细
    List<InEnterDetail> inEnterDetailList = new ArrayList<>();
    // 明细处理
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      Long orderDetailId = inScanOrderDetailBo.getOrderDetailId();
      String positionName = inScanOrderDetailBo.getPositionName();
      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量
      Assert.isFalse(B.isLessOrEqual(finishedQuantity), "扫描数量不能小于0");

      // 预到货明细信息
      InOrderDetail inOrderDetail = inOrderDetailService.getById(orderDetailId);
      // 货位信息
      BasePosition basePosition = basePositionService.getByName(inOrder.getStorageId(), positionName);
      if (ObjectUtil.isNull(basePosition)) {
        // 新增一条货位信息
        BasePosition newPosition = new BasePosition();
        newPosition.setStorageId(inOrder.getStorageId());
        newPosition.setStorageName(inOrder.getStorageName());
        newPosition.setPositionName(positionName);
        newPosition.setPositionType(PositionTypeEnum.NORMAL.getId());
        newPosition.setIsLocked(EnableEnum.DISABLE.getId());
        newPosition.setEnable(EnableEnum.ENABLE.getId());
        newPosition.setIsMixProduct(EnableEnum.ENABLE.getId());
        basePositionService.save(newPosition);
      }
      basePosition = basePositionService.getByName(inOrder.getStorageId(), positionName);
      Assert.isFalse(ObjectUtil.isEmpty(basePosition), positionName + "货位不存在！");

      // 明细求和
      totalQuantity = totalQuantity.add(finishedQuantity);
      BigDecimal weight = Optional.ofNullable(inOrderDetail.getWeight()).orElse(BigDecimal.ZERO);
      BigDecimal newWeight = Optional.ofNullable(inOrderDetail.getNetWeight()).orElse(BigDecimal.ZERO);
      BigDecimal purchasePrice = Optional.ofNullable(inOrderDetail.getPurchasePrice()).orElse(BigDecimal.ZERO);
      totalWeight = totalWeight.add(weight.multiply(finishedQuantity));
      totalNewWeight = totalNewWeight.add(newWeight.multiply(finishedQuantity));
      totalAmount = totalAmount.add(purchasePrice.multiply(finishedQuantity));

      //#region 生成入库单明细
      InEnterDetail inEnterDetail = new InEnterDetail();
      final Map<String, Object> expandFields = inOrderDetail.getExpandFields();
      inOrderDetail.setExpandFields(null);
      BeanUtil.copyProperties(inOrderDetail, inEnterDetail);
      inEnterDetail.setExpandFields(expandFields);

      final Map<String, Object> boExpandFields = inScanOrderDetailBo.getExpandFields();
//      inScanOrderDetailBo.setExpandFields(null);
      BeanUtil.copyProperties(inScanOrderDetailBo, inEnterDetail, CopyOptions.create().setIgnoreNullValue(true));

      if (ObjectUtil.isNotNull(boExpandFields) && ObjectUtil.isNull(inEnterDetail.getExpandFields())) {
        inEnterDetail.setExpandFields(boExpandFields);
      }

      inEnterDetail.setEnterId(inEnter.getEnterId());
      inEnterDetail.setEnterQuantity(finishedQuantity);
      inEnterDetail.setRemark(inOrderDetail.getRemark());
      inEnterDetail.setPositionName(positionName); // 入库货位
      inEnterDetail.setPurchaseAmount(B.mul(finishedQuantity, inOrderDetail.getPurchasePrice()));
      inEnterDetail.setDetailStatus(InEnterStatusEnum.ENTERED.getName());

      //#region  需要重新从inEnterDetail 赋值
      //从新计算税价 合计税价
      inEnterDetail.setRate(inOrderDetail.getRate());
      BigDecimal ratePrice = B.add(BigDecimal.ONE, inEnterDetail.getRate());
      inEnterDetail.setRatePrice(B.mul(inOrderDetail.getPurchasePrice(), ratePrice));
      inEnterDetail.setRateAmount(B.mul(finishedQuantity, inEnterDetail.getRatePrice()));
      inEnterDetail.setRowWeight(B.mul(finishedQuantity, inEnterDetail.getWeight()));  //合计重量
      inEnterDetail.setRowWeightTon(B.div(inEnterDetail.getRowWeight(), new BigDecimal(1000))); // 合计重量吨
      inEnterDetail.setShelvedQuantity(BigDecimal.ZERO);
      inEnterDetail.setRowNetWeight(B.mul(finishedQuantity, inEnterDetail.getNetWeight())); // 合计净重
      inEnterDetail.setRowNetWeightTon(B.div(inEnterDetail.getRowNetWeight(), new BigDecimal(1000)));//  合计净重吨
      //如果是一键入库 有些值需要按照一键入库的值去填写
      if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PC_QUICK_ENTER) {
        if (inScanOrderDetailBo.getProduceDate() != null) {
          inEnterDetail.setProduceDate(inScanOrderDetailBo.getProduceDate());
        }
        if (inScanOrderDetailBo.getBatchNumber() != null) {
          inEnterDetail.setBatchNumber(inScanOrderDetailBo.getBatchNumber());
        }
      }
      //#endregion

      // 托盘号默认为货位号 - 待上架单扫描
      if (ctx.isInWaitShelveBillScan() && StringUtils.isEmpty(inOrderDetail.getPlateCode())) {
        inEnterDetail.setPlateCode(positionName);
      }

      inEnterDetail.setSortingCount(1L);
      inEnter.setRemark(inScanOrderDetailBo.getRemark());
      inEnterService.saveOrUpdate(inEnter);
      inEnterDetailService.save(inEnterDetail); // 保存入库单明细
      // 记录入库明细
      inEnterDetailList.add(inEnterDetail);

      if (StrUtil.equals(inScanOrderDetailBo.getProductAttribute(), "报废出库")) {
        invalidateDetails.add(inEnterDetail);
      }
      //#endregion
    }

    // 记录入库单明细
    ctx.setInEnterDetailList(inEnterDetailList);
    // 记录报废明细
    ctx.setInvalidateDetails(invalidateDetails);
    //#endregion
  }
}
