package com.yiruantong.composite.service.inventory.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.inventory.IAllocateApplyService;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyDetailService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyStatusHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.outbound.service.out.IOutScanOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AllocateApplyServiceImpl implements IAllocateApplyService {
  private final IStorageAllocateApplyStatusHistoryService storageAllocateApplyStatusHistoryService;
  private final IStorageAllocateApplyDetailService storageAllocateApplyDetailService;
  private final IStorageAllocateApplyService storageAllocateApplyService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final ICoreInventoryHolderService coreInventoryHolderService;


  //#region toOutOrder
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> toOutOrder(Map<String, Object> map) {
    String[] ids = Convert.toStr(map.get("ids")).split(",");
    String allocateType = Convert.toStr(map.get("allocateType"));

    for (String id : ids) {
      StorageAllocateApply applyInfo = storageAllocateApplyService.getById(id);

      Assert.isTrue(ObjectUtil.isNotEmpty(applyInfo), "未找到对应的调拨申请单");
      Assert.isTrue(B.isEqual(applyInfo.getApplyStatus(), StorageAllocateApplyStatusEnum.SUCCESS.getName()), applyInfo.getAllocateApplyCode() + "未审核的单据不允许操作");
      Assert.isTrue(B.isEqual(applyInfo.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId()), applyInfo.getAllocateApplyCode() + "未分配的单据不允许操作");

      //查询调拨明细
      List<StorageAllocateApplyDetail> details = storageAllocateApplyDetailService.selectListByMainId(applyInfo.getAllocateApplyId());

      //#region 生成出库单
      //生成出库单
      OutOrder outOrder = BeanUtil.copyProperties(applyInfo, OutOrder.class);
      outOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));
      outOrder.setSourceCode(applyInfo.getAllocateApplyCode());
      outOrder.setSourceId("" + applyInfo.getAllocateApplyId());
      outOrder.setTotalQuantityOrder(applyInfo.getTotalQuantity());
      BigDecimal totalCube = details.stream().map(StorageAllocateApplyDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
      outOrder.setTotalCube(totalCube);
      outOrder.setSourceType(StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_OUT.getName());
      outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
      outOrder.setClientCode(applyInfo.getConsignorCodeTarget());
      outOrder.setClientShortName(applyInfo.getConsignorNameTarget());
      outOrder.setOrderId(null);
      outOrderService.save(outOrder);

      //#endregion

      //#region 生成出库单明细
      //生成出库明细
      for (StorageAllocateApplyDetail detail : details) {
        OutOrderDetail outOrderDetail = BeanUtil.copyProperties(detail, OutOrderDetail.class);
        outOrderDetail.setQuantityOrder(detail.getApplyQuantity());
        outOrderDetail.setSalePrice(detail.getPurchasePrice());
        outOrderDetail.setSaleAmount(detail.getPurchaseAmount());
        outOrderDetail.setOrderId(outOrder.getOrderId());
        outOrderDetail.setSourceDetailId("" + detail.getAllocateApplyDetailId());
        outOrderDetail.setSourceMainId("" + detail.getAllocateApplyId());
        outOrderDetail.setSourceType(StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_OUT.getName());
        outOrderDetailService.save(outOrderDetail);
        //查询枚举
        HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(applyInfo.getOrderType());
        Assert.isFalse(holderSourceTypeEnum == null, "未找到调拨申请单的枚举");
        // 修改占位信息
        LambdaUpdateWrapper<CoreInventoryHolder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CoreInventoryHolder::getBillCode, outOrder.getOrderCode())
          .set(CoreInventoryHolder::getMainId, outOrder.getOrderId())
          .set(CoreInventoryHolder::getDetailId, outOrderDetail.getOrderDetailId())
          .eq(CoreInventoryHolder::getSourceType, holderSourceTypeEnum.getName())
          .eq(CoreInventoryHolder::getMainId, applyInfo.getAllocateApplyId())
          .eq(CoreInventoryHolder::getDetailId, detail.getAllocateApplyDetailId());
        coreInventoryHolderService.update(updateWrapper);
      }
      //#endregion

      //出库单添加轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_ALLOCATE_APPLY_PLAN, OutOrderStatusEnum.AUDIT_SUCCESS, applyInfo.getAllocateApplyCode());

      //#region 更新调拨单状态已经添加轨迹
      //调拨单添加轨迹
      storageAllocateApplyStatusHistoryService.addHistoryInfo(applyInfo, StorageAllocateApplyActionEnum.TO_OUT_ORDER, StorageAllocateApplyStatusEnum.SUCCESS, StorageAllocateApplyStatusEnum.TO_OUT_ORDER, outOrder.getOrderCode());
      //修改调拨单状态
      applyInfo.setApplyStatus(StorageAllocateApplyStatusEnum.TO_OUT_ORDER.getName());
      storageAllocateApplyService.updateById(applyInfo);
      //#endregion

      //如果是确认出库按钮点击则需要调用出库单的一键出库功能
      if (StrUtil.equals(allocateType, "validateOut")) {
        //分拣成功 自动出库
        OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrder, OutScanMainBo.class);
        newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_ALLOCATE_ROUTE_OUT);
        HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());

        Assert.isFalse(holderSourceTypeEnum == null, "未找到出库单的枚举");
        newOutScanOrderBo.setHolderSourceTypeEnum(holderSourceTypeEnum);

        List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId()); // 订单明细集合
        newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
        for (var outOrderDetail : outOrderDetailList) {
          OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
          outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
          newOutScanOrderBo.getDataList().add(outScanDetailBo);
        }
        IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);
        outScanOrderService.normalOutSave(newOutScanOrderBo);
      }
    }
    return R.ok("转出成功");
  }
  //endregion


}
