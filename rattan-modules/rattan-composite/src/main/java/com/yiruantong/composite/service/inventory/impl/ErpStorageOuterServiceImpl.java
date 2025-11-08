package com.yiruantong.composite.service.inventory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageOuterStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.inventory.IErpStorageOuterService;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.operation.IStorageOuterDetailService;
import com.yiruantong.inventory.service.operation.IStorageOuterService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.outbound.service.out.IOutScanOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * erp其他出库
 */
@RequiredArgsConstructor
@Service
public class ErpStorageOuterServiceImpl implements IErpStorageOuterService {
  private final IStorageOuterService storageOuterService;
  private final IStorageOuterDetailService storageOuterDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  @Override
  public R<Void> toOutOrder(Map<String, Object> map) {
    String[] ids = Convert.toStr(map.get("ids")).split(",");
    String outType = Convert.toStr(map.get("outType"));

    for (String id : ids) {
      StorageOuter storageOuter = storageOuterService.getById(id);

      Assert.isTrue(ObjectUtil.isNotEmpty(storageOuter), "未找到对应的出库单");

      Assert.isTrue(B.isEqual(storageOuter.getOuterStatus(), AuditEnum.AUDITED_SUCCESS.getName()), storageOuter.getOuterCode() + "未审核的单据不允许操作");
      Assert.isTrue(B.isEqual(storageOuter.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId()), storageOuter.getOuterCode() + "未分配的单据不允许操作");

      //查询其他出库单明细
      List<StorageOuterDetail> details = storageOuterDetailService.selectListByMainId(storageOuter.getOuterId());

      //#region 生成出库单
      //生成出库单
      OutOrder outOrder = BeanUtil.copyProperties(storageOuter, OutOrder.class);
      outOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));
      outOrder.setSourceCode(storageOuter.getOuterCode());
      outOrder.setSourceId("" + storageOuter.getOuterId());
      outOrder.setTotalQuantityOrder(storageOuter.getTotalOuterQuantity());
      outOrder.setSourceType(StorageOuterStatusEnum.STORAGE_OUTER.getName());
      outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
      outOrder.setTotalAmount(storageOuter.getTotalAmount());
      outOrder.setTaxAmount(storageOuter.getTotalRateAmount());
      outOrder.setTotalUnpaid(storageOuter.getTotalRateAmount());
      outOrder.setOrderType(StorageOuterStatusEnum.STORAGE_OUTER.getName());
      outOrder.setOrderId(null);
      outOrderService.save(outOrder);

      //#endregion

      //#region 生成出库单明细
      //生成出库明细
      for (StorageOuterDetail detail : details) {
        OutOrderDetail outOrderDetail = BeanUtil.copyProperties(detail, OutOrderDetail.class);
        outOrderDetail.setQuantityOrder(detail.getReturnQuantity());
        outOrderDetail.setSalePrice(detail.getPurchasePrice());
        outOrderDetail.setSaleAmount(detail.getSaleAmount());
        outOrderDetail.setOrderId(outOrder.getOrderId());
        outOrderDetail.setSourceDetailId("" + detail.getOuterDetailId());
        outOrderDetail.setSourceMainId("" + detail.getOuterId());
        outOrderDetail.setSourceType(StorageOuterStatusEnum.STORAGE_OUTER.getName());
        outOrderDetail.setOrderDetailId(null);
        outOrderDetailService.save(outOrderDetail);
        //查询枚举
        // 修改占位信息
        LambdaUpdateWrapper<CoreInventoryHolder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CoreInventoryHolder::getBillCode, outOrder.getOrderCode())
          .set(CoreInventoryHolder::getMainId, outOrder.getOrderId())
          .set(CoreInventoryHolder::getDetailId, outOrderDetail.getOrderDetailId())
          .eq(CoreInventoryHolder::getSourceType, HolderSourceTypeEnum.STORAGE_OUTER.getName())
          .eq(CoreInventoryHolder::getMainId, storageOuter.getOuterId())
          .eq(CoreInventoryHolder::getDetailId, detail.getOuterDetailId());
        coreInventoryHolderService.update(updateWrapper);
      }
      //#endregion

      //出库单添加轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.RETURN_IN, OutOrderStatusEnum.AUDIT_SUCCESS, storageOuter.getOuterCode());

      //#region 更新退货单状态已经添加轨迹

      //修改退货单状态
      storageOuter.setReturnStatus(StorageOuterStatusEnum.TO_OUT_ORDER.getName());
      storageOuterService.updateById(storageOuter);
      //#endregion

      //如果是确认出库按钮点击则需要调用出库单的一键出库功能
      if (StrUtil.equals(outType, "validateOut")) {
        //分拣成功 自动出库
        OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrder, OutScanMainBo.class);
        newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.STORAGE_OUTER);
        newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.STORAGE_OUTER);
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
}
