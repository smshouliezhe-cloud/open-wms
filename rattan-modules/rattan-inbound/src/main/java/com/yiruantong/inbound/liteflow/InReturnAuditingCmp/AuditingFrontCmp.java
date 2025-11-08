package com.yiruantong.inbound.liteflow.InReturnAuditingCmp;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.in.InEnterActionEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.other.XgOrderTypeEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.liteflow.Context.InReturnAuditingContext;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@LiteflowComponent(id = "auditingFrontCmp", name = "1.入库退后单审核前 （湘钢特殊处理）")
@RequiredArgsConstructor
public class AuditingFrontCmp extends NodeComponent {
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderService outOrderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  @Override
  public void process() {
    InReturnAuditingContext inScanOrderBo = this.getRequestData();

    // 判断当前要消减的库存是否有占位信息
    List<InReturnDetail> detailList = inScanOrderBo.getDetailList();
    InReturn inReturn = inScanOrderBo.getInReturn();
    List<Long> ids = detailList.stream().map(InReturnDetail::getProductId).toList();
    LambdaQueryWrapper<CoreInventory> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(CoreInventory::getSourceCode, inReturn.getSourceCode())
      .eq(CoreInventory::getStorageId, inReturn.getStorageId())
      .eq(CoreInventory::getConsignorId, inReturn.getConsignorId())
      .in(CoreInventory::getProductId, ids);
    List<CoreInventory> list = coreInventoryService.list(queryWrapper);
    if (ObjectUtil.isNotNull(list) && B.isGreater(list.size())) {
      // 根据库存ID查询占位信息
      LambdaQueryWrapper<CoreInventoryHolder> historyLambdaQueryWrapper = new LambdaQueryWrapper<>();
      historyLambdaQueryWrapper.in(CoreInventoryHolder::getInventoryId, list.stream().map(CoreInventory::getInventoryId).toList());
      List<CoreInventoryHolder> holderList = coreInventoryHolderService.list(historyLambdaQueryWrapper);
      //查询出库单 让已经占位的出库单释放占位
      if (ObjectUtil.isNotNull(holderList) && B.isGreater(holderList.size())) {
        //获取到出库单号
        List<String> orderCodes = holderList.stream().map(CoreInventoryHolder::getBillCode).toList();
        LambdaQueryWrapper<OutOrder> outOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        outOrderLambdaQueryWrapper.in(OutOrder::getOrderCode, orderCodes)
          .eq(OutOrder::getOrderType, XgOrderTypeEnum.ALLOY_AUXILIARY_MATERIAL.getName());
        List<OutOrder> outOrders = outOrderService.list(outOrderLambdaQueryWrapper);
        for (OutOrder orderInfo : outOrders) {
          // 释放占位
          HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(orderInfo.getOrderType());
          Assert.isFalse(holderSourceTypeEnum == null, "未找到当前要释放占位的枚举");
          coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), orderInfo.getOrderId());

          // 回写出库单信息
          LambdaUpdateWrapper<OutOrder> outOrderLambdaUpdateWrapper =new LambdaUpdateWrapper<>();
          outOrderLambdaUpdateWrapper.set(OutOrder::getSortingStatus,SortingStatusEnum.NONE.getId())
            .setSql("expand_fields = json_set(expand_fields,'$.release', null)") // 把紧急放行单标记删除掉
            .setSql("expand_fields = json_set(expand_fields,'$.isQuickOut', true)") // 标记可以一键出库
            .eq(OutOrder::getOrderId, orderInfo.getOrderId());
          outOrderService.update(outOrderLambdaUpdateWrapper);

          //添加轨迹
          outOrderStatusHistoryService.AddHistory(orderInfo, OutOperationTypeEnum.QUALITY_TESTING_RESULT, OutOrderStatusEnum.matchingEnum(orderInfo.getOrderStatus()),"质检结果回推后释放当前占位信息");
        }
      }


    }


  }


}
