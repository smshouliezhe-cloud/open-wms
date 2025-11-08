package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import com.yiruantong.inbound.service.in.IInShelveDetailStepService;
import com.yiruantong.inbound.service.in.IInShelveService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@LiteflowComponent(id = "inScanGenShelveCmp", name = "4.生成上架单")
@RequiredArgsConstructor
public class InScanGenShelveCmp extends NodeComponent {
  private final IInShelveService inShelveService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInShelveDetailService inShelveDetailService;
  private final IBasePositionService basePositionService;
  private final IInShelveDetailStepService inShelveDetailStepService;

  @Override
  public void process() {
    InScanContext ctx = this.getContextBean(InScanContext.class);
    InScanOrderBo inScanOrderBo = ctx.getInScanOrderBo();
    InOrder inOrder = ctx.getInOrder();
    InEnter inEnter = ctx.getInEnter();
    Assert.isFalse(ObjectUtil.isNull(inEnter), "入库单未生成，不允许生成上架单");

    //#region 生成上架单
    InShelve inShelve = null;
    if (ctx.isInGenerateShelve()) {
      // 扫描入库时合并生成上架单
      if (ctx.isInGenerateShelveOnly()) {
        inShelve = inShelveService.getByEnterId(inEnter.getEnterId()); // 根据入库单获取上架单
      }

      if (ObjectUtil.isNull(inShelve)) {
        // 生成上架单号
        String shelveCode = DBUtils.getCodeRegular(MenuEnum.MENU_1660, LoginHelper.getLoginUser().getTenantId()); // 上架单号
        inShelve = new InShelve();
        BeanUtil.copyProperties(inOrder, inShelve);
        inShelve.setShelveCode(shelveCode);
        inShelve.setScanInType(inScanOrderBo.getScanInType().getName());
        inShelve.setLpnCode(inScanOrderBo.getLpnCode());
        inShelve.setEnterId(inEnter.getEnterId());
        inShelve.setEnterCode(inEnter.getEnterCode());
        inShelve.setShelveStatus(InShelveStatusEnum.WAITING.getName());
        inShelve.setAuditor(LoginHelper.getNickname());
        inShelve.setAuditDate(DateUtil.date());
        inShelve.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
        inShelve.setUserId(null);
        inShelve.setNickName(null);

        inShelveService.save(inShelve);
      }
    }
    //#endregion

    ctx.setInShelve(inShelve);
    genDetail(ctx, inShelve);
  }

  private void genDetail(InScanContext cxt, InShelve inShelve) {
    InScanOrderBo inScanOrderBo = cxt.getInScanOrderBo();
    InEnter inEnter = cxt.getInEnter();
    InOrder inOrder = cxt.getInOrder();
    List<InScanOrderDetailBo> dataList = cxt.getInScanOrderBo().getDataList();
    List<PositionTypeEnum> positionTypeEnumList = cxt.getPositionTypeEnumList();

    //#region 明细处理
    BigDecimal totalQuantity = BigDecimal.ZERO; // 数量合计求和
    BigDecimal totalWeight = BigDecimal.ZERO; // 毛重合计求和
    BigDecimal totalNewWeight = BigDecimal.ZERO; // 净重合计求和
    BigDecimal totalAmount = BigDecimal.ZERO; // 金额合计求和

    //用于存放 报废出库 入库明细信息
    List<InEnterDetail> invalidateDetails = new ArrayList<>();
    // 明细处理
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      Long orderDetailId = inScanOrderDetailBo.getOrderDetailId();
      String positionName = inScanOrderDetailBo.getPositionName();
      String batchNumber = inScanOrderDetailBo.getBatchNumber();
      String productCode = inScanOrderDetailBo.getProductCode();

      // 获取入库单明细
      InEnterDetail inEnterDetail = cxt.getInEnterDetailList().stream().filter(x -> StringUtils.equals(x.getProductCode(), inScanOrderDetailBo.getProductCode())).findFirst().orElse(null);
      Assert.isFalse(ObjectUtil.isNull(inEnterDetail), "入库明细不存在，生成质检单明细失败");

      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量
      Assert.isFalse(B.isLessOrEqual(finishedQuantity), "扫描数量不能小于0");

      // 货位信息
      BasePosition basePosition = basePositionService.getByName(inOrder.getStorageId(), positionName);
      Assert.isFalse(ObjectUtil.isEmpty(basePosition), positionName + "货位不存在！");

      //#region 生成上架单明细
      if (cxt.isInGenerateShelve() && ObjectUtil.isNotNull(inShelve)) {
        // 预到货明细信息
        InOrderDetail inOrderDetail = inOrderDetailService.getById(orderDetailId);

        InShelveDetail inShelveDetail = new InShelveDetail();
        BeanUtil.copyProperties(inEnterDetail, inShelveDetail);
        inShelveDetail.setShelveId(inShelve.getShelveId());
        inShelveDetail.setOrderId(inShelve.getOrderId());
        inShelveDetail.setEnterId(inShelve.getEnterId());
        inShelveDetail.setEnterDetailId(inEnterDetail.getEnterDetailId());
        inShelveDetail.setOrderDetailId(inOrderDetail.getOrderDetailId());
        inShelveDetail.setQuantity(finishedQuantity);

        // 直接上架的
        if (positionTypeEnumList.stream().anyMatch(a -> ObjectUtil.equals(a.getId(), basePosition.getPositionType()))) {
          inShelveDetail.setOnShelveQuantity(BigDecimal.ZERO);
          inShelveDetail.setShelvedQuantity(finishedQuantity);
          inShelveDetail.setShelveStatus(InShelveStatusEnum.FINISHED.getName());
          inShelveDetail.setPositionNo(inScanOrderDetailBo.getPositionName());

        } else {
          inShelveDetail.setOnShelveQuantity(finishedQuantity);
          inShelveDetail.setShelvedQuantity(BigDecimal.ZERO);
          inShelveDetail.setShelveStatus(InShelveStatusEnum.WAITING.getName());
        }
        //如果是一键入库 有些值需要按照一键入库的值去填写
        if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PC_QUICK_ENTER) {
          if (inScanOrderDetailBo.getProduceDate() != null) {
            inShelveDetail.setProduceDate(inScanOrderDetailBo.getProduceDate());
          }
          if (inScanOrderDetailBo.getBatchNumber() != null) {
            inShelveDetail.setBatchNumber(inScanOrderDetailBo.getBatchNumber());
          }
        }

        inShelveDetailService.save(inShelveDetail); // 保存上架单明细
        if (positionTypeEnumList.stream().anyMatch(a -> ObjectUtil.equals(a.getId(), basePosition.getPositionType()))) {
          //#region 生成上架明细步骤
          InShelveDetailStep inShelveDetailStep = new InShelveDetailStep();
          BeanUtil.copyProperties(inShelveDetail, inShelveDetailStep);
          inShelveDetailStep.setShelveId(inShelve.getShelveId());
          inShelveDetailStep.setShelveDetailId(inShelveDetail.getShelveDetailId());
          inShelveDetailStep.setShelveStatus(InShelveStatusEnum.FINISHED.getName()); // 状态为上架中
          inShelveDetailStep.setPositionName(inScanOrderDetailBo.getPositionName()); // 上架货位
          inShelveDetailStep.setQuantity(finishedQuantity); // 完成数量
          inShelveDetailStep.setCreateTime(DateUtil.date());
          inShelveDetailStep.setUpdateTime(DateUtil.date());
          inShelveDetailStepService.save(inShelveDetailStep);
          //#endregion
        }
      }
      //#endregion
    }
    //#endregion
  }

}
