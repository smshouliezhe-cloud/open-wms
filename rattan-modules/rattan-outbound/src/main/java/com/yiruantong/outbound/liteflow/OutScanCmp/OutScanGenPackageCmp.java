package com.yiruantong.outbound.liteflow.OutScanCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.enums.HistoryTypeEnum;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutPackageDetailService;
import com.yiruantong.outbound.service.out.IOutPackageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LiteflowComponent(id = "outScanGenPackageCmp", name = "3.生成打包单组件")
@RequiredArgsConstructor
public class OutScanGenPackageCmp extends NodeComponent {
  private final IOutPackageService outPackageService;
  private final IOutPackageDetailService outPackageDetailService;
  private final IBaseProductService baseProductService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final ICoreInventorySnService coreInventorySnService;

  @Override
  public void process() {
    OutScanContext ctx = this.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder(); // 出库单主表信息

    // 生成打保单
    String packageCode = DBUtils.getCodeRegular(MenuEnum.MENU_1696, LoginHelper.getLoginUser().getTenantId());  // 生成打包单号
    OutPackage outPackage = new OutPackage();

    BeanUtil.copyProperties(outOrder, outPackage);

    outPackage.setConsignorNameSale(outOrder.getConsignorNameSale());
    outPackage.setConsignorIdSale(outOrder.getConsignorIdSale());
    outPackage.setPackageCode(packageCode);
    outPackage.setPackageStatus(OutOrderStatusEnum.PACKAGING.getName());
    outPackage.setAuditor(null);
    outPackage.setAuditDate(null);
    outPackage.setAuditing(null);
    if (ObjectUtil.isNotNull(outOrder.getExpandFields())) {
      outPackage.setExpandFields(outOrder.getExpandFields());

    }
    outPackageService.save(outPackage);
    //#endregion

    ctx.setOutPackage(outPackage);
    genDetail(ctx);
  }

  /**
   * 生成打包单明细
   *
   * @param ctx 上下文
   */
  private void genDetail(OutScanContext ctx) {
    OutScanMainBo outScanMainBo = ctx.getOutScanMainBo();
    List<OutScanDetailBo> dataList = outScanMainBo.getDataList(); // 明细JSON集合数据
    OutOrder outOrder = ctx.getOutOrder(); // 出库单信息
    OutPackage outPackage = ctx.getOutPackage(); // 打包单信息
    Long orderId = outOrder.getOrderId(); // 出库单id
    List<OutOrderDetail> outOrderDetailList = new ArrayList<>(); // 出库单明细集合
    List<OutPackageDetail> outPackageDetailList = new ArrayList<>(); // 打包单明细集合

    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());
    Assert.isFalse(holderSourceTypeEnum == null, "未找到出库单的枚举");

    //#region 生成出库单明细
    for (OutScanDetailBo outScanDetailBo : dataList) {
      Long orderDetailId = outScanDetailBo.getOrderDetailId();
      Long productId = outScanDetailBo.getProductId();
      String productCode = outScanDetailBo.getProductCode();
      BigDecimal finishedQuantity = outScanDetailBo.getFinishedQuantity();

      BaseProduct productInfo = baseProductService.getById(productId);
      Assert.isFalse(ObjectUtil.isEmpty(productInfo), productCode + "商品信息不存在");

      OutOrderDetail outOrderDetail = outOrderDetailService.getById(orderDetailId);
      outOrderDetailList.add(outOrderDetail);

      // 生成打包单明细
      OutPackageDetail outPackageDetail = new OutPackageDetail();
      BeanUtil.copyProperties(productInfo, outPackageDetail);
      BeanUtil.copyProperties(outOrderDetail, outPackageDetail);
      BeanUtil.copyProperties(outScanDetailBo, outPackageDetail);
      outPackageDetail.setPackageId(outPackage.getPackageId());
      outPackageDetail.setPackageQuantity(finishedQuantity);
      outPackageDetail.setRowPackage(outScanDetailBo.getRowPackage());
      outPackageDetail.setPlateCode(outOrderDetail.getPlateCode());
      outPackageDetail.setRowWeightTon(B.div(outPackageDetail.getRowWeight(), new BigDecimal(1000)));
      outPackageDetailService.save(outPackageDetail);
      outPackageDetailList.add(outPackageDetail);

      // 更新出库单明细出库数量
      outOrderDetail.setQuantityOuted(B.add(outOrderDetail.getQuantityOuted(), finishedQuantity));
      outOrderDetailService.getBaseMapper().updateById(outOrderDetail);

      //#region 开始出库单，扣减占位和库存
      List<CoreInventoryHolder> inventoryHolderList = coreInventoryHolderService.selectHolderList(orderId, orderDetailId, HolderSourceTypeEnum.OUT_ORDER_NORMAL, HolderSourceTypeEnum.OUT_PICKING, holderSourceTypeEnum);
      BigDecimal leaveQuantity;
      // 开启只出库下架理货位的数据
      if (ctx.isOuterPickQuantity()) {
        inventoryHolderList = coreInventoryHolderService.selectHolderList(orderId, orderDetailId, HolderSourceTypeEnum.OUT_PICKING);
      }

      BigDecimal finishQuantity = outScanDetailBo.getFinishedQuantity();
      for (var holderInfo : inventoryHolderList) {
        if (B.isLessOrEqual(holderInfo.getHolderStorage())) {
          continue;
        }
        if (B.isEqual(finishQuantity)) {
          continue;
        }
        String positionName = holderInfo.getPositionName();

        //#region 获取出库前：库存数量和重量
        CoreInventory coreInventory = coreInventoryService.getById(holderInfo.getInventoryId());
        BigDecimal beforeQuantity = coreInventory.getProductStorage(); // 保存前数量
        BigDecimal beforeWeight = coreInventory.getRowWeight();   // 保存前重量
        //#endregion

        if (B.isGreaterOrEqual(holderInfo.getHolderStorage(), finishQuantity)) {
          // 扣减占位
          holderInfo.setHolderStorage(B.sub(holderInfo.getHolderStorage(), finishQuantity));
          coreInventoryHolderService.getBaseMapper().updateById(holderInfo);
          // 扣减库存
          coreInventoryService.subInventory(holderInfo.getInventoryId(), finishQuantity);
          leaveQuantity = finishQuantity;
          finishQuantity = BigDecimal.ZERO;
        } else {
          finishQuantity = B.sub(finishQuantity, holderInfo.getHolderStorage());
          leaveQuantity = holderInfo.getHolderStorage();
          // 扣减库存
          coreInventoryService.subInventory(holderInfo.getInventoryId(), holderInfo.getHolderStorage());
          // 扣减占位
          holderInfo.setHolderStorage(BigDecimal.ZERO);
          coreInventoryHolderService.getBaseMapper().updateById(holderInfo);
        }
        // 更新库存占位数据
        coreInventoryService.updateHolderStorage(holderInfo.getInventoryId());

        // 修改波次单中的出库数量
        LambdaQueryWrapper<OutOrderWaveDetail> waveDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        waveDetailLambdaQueryWrapper
          .eq(OutOrderWaveDetail::getOrderId, outPackageDetail.getOrderId())
          .eq(OutOrderWaveDetail::getHolderId, holderInfo.getHolderId());
        OutOrderWaveDetail outOrderWaveDetail = outOrderWaveDetailService.getOne(waveDetailLambdaQueryWrapper);
        if (ObjectUtil.isNotNull(outOrderWaveDetail)) {
          outOrderWaveDetail.setQuantityOuted(B.sub(outOrderWaveDetail.getQuantityOrder(), holderInfo.getHolderStorage()));
          outOrderWaveDetailService.updateById(outOrderWaveDetail);
        }

        // 修改打包单中的生产日期
        outPackageDetail.setProduceDate(holderInfo.getProduceDate());
        outPackageDetail.setBatchNumber(holderInfo.getBatchNumber());

        //#region 新增库存轨迹
        // 获取出库后：库存数量和重量
        coreInventory = coreInventoryService.getById(holderInfo.getInventoryId());
        BigDecimal afterQuantity = coreInventory.getProductStorage();
        BigDecimal afterWeight = coreInventory.getRowWeight();
        Map<String, Object> maps = new HashMap<>();
        if (ObjectUtil.isNotEmpty(outScanDetailBo.getExpandFields())) {
          maps = outScanDetailBo.getExpandFields();
        }
        // 新增库存轨迹
        CommonDetailDto commonDetailDto = BeanUtil.copyProperties(outOrderDetail, CommonDetailDto.class);
        BeanUtil.copyProperties(outOrder, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
        BeanUtil.copyProperties(holderInfo, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
        BeanUtil.copyProperties(outScanDetailBo, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
        commonDetailDto.setPositionNameOut(positionName);
        commonDetailDto.setOutQuantity(leaveQuantity);
        commonDetailDto.setMainId(outOrderDetail.getOrderId());
        commonDetailDto.setDetailId(outOrderDetail.getOrderDetailId());
        commonDetailDto.setBillCode(outOrder.getOrderCode());
        commonDetailDto.setSourceCode(outOrder.getStoreOrderCode());
        commonDetailDto.setContainerNo(holderInfo.getContainerNo());
        commonDetailDto.setProductSpec(holderInfo.getProductSpec());
        if (ObjectUtil.isNotNull(commonDetailDto.getExpandFields())) {
          commonDetailDto.getExpandFields().putAll(maps);
        } else {
          commonDetailDto.setExpandFields(maps);
        }

        coreInventoryHistoryService.addHistory(HistoryTypeEnum.OUT, productInfo, commonDetailDto, outScanMainBo.getScanInType(), beforeQuantity, beforeWeight, afterQuantity, afterWeight);
        //#endregion
      }
      //#endregion

      outPackageDetailService.getBaseMapper().updateById(outPackageDetail); // 更新打包明细

      // 消减SN处理
      if (B.isEqual(productInfo.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        Assert.isFalse(outScanMainBo.getScanInType() == InventorySourceTypeEnum.PC_QUICK_OUT, "一键出库存在SN管理，不允许操作！");
        Assert.isFalse(outScanMainBo.getScanInType() == InventorySourceTypeEnum.PC_BATCH_OUT, "批量出库存在SN管理，不允许操作！");

        List<String> snList = StringUtils.splitList(outScanDetailBo.getSingleSignCode());
        coreInventorySnService.updateInvalid(outOrder.getStorageId(), snList);
      }
    }
    //#endregion

    ctx.setOutOrderDetailList(outOrderDetailList);
    ctx.setOutPackageDetailList(outPackageDetailList);
  }
}
