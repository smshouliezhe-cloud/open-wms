package com.yiruantong.composite.service.in.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.in.IReturnService;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import com.yiruantong.inbound.service.service.IInReturnService;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReturnServiceImpl implements IReturnService {
  private final IInReturnService inReturnService;
  private final IInReturnDetailService inReturnDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IInOrderDetailService inOrderDetailService;

  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IInReturnStatusHistoryService inReturnStatusHistoryService;
  private final IOutSortingRuleService outSortingRuleService;

  private final ITaskQueueService taskQueueService;

  @Override
  public R<Void> toOutOrder(Map<String, Object> map) {
    String[] ids = Convert.toStr(map.get("ids")).split(",");
    String returnType = Convert.toStr(map.get("returnType"));

    for (String id : ids) {
      InReturn returnInfo = inReturnService.getById(id);

      Assert.isTrue(ObjectUtil.isNotEmpty(returnInfo), "未找到对应的退货单");

      Assert.isTrue(B.isEqual(returnInfo.getReturnStatus(), AuditEnum.AUDITED_SUCCESS.getName()), returnInfo.getReturnCode() + "未审核的单据不允许操作");
      Assert.isTrue(B.isEqual(returnInfo.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId()), returnInfo.getReturnCode() + "未分配的单据不允许操作");

      //查询退货单明细
      List<InReturnDetail> details = inReturnDetailService.selectListByMainId(returnInfo.getReturnId());

      //#region 生成出库单
      //生成出库单
      OutOrder outOrder = BeanUtil.copyProperties(returnInfo, OutOrder.class);
      outOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));
      outOrder.setSourceCode(returnInfo.getReturnCode());
      outOrder.setSourceId("" + returnInfo.getReturnId());
      outOrder.setTotalQuantityOrder(returnInfo.getTotalReturnQuantity());
      if (ObjectUtil.isNotNull(returnInfo.getOrderType())) {
        outOrder.setSourceType(returnInfo.getOrderType());
      } else {
        outOrder.setSourceType(InReturnEnum.RETURN_ORDER.getName());
      }
      outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
      outOrder.setTotalAmount(returnInfo.getReturnAmount());
      outOrder.setTaxAmount(returnInfo.getTotalRateAmount());
      outOrder.setTotalUnpaid(returnInfo.getTotalRateAmount());
      if (returnInfo.getOrderType().equals(InReturnEnum.QUALITY_INSPECTION_RETURN.getName())) {
        outOrder.setOrderType(InReturnEnum.QUALITY_INSPECTION_RETURN.getName());
      } else {
        outOrder.setOrderType(InReturnEnum.RETURN_ORDER.getName());
      }
      outOrder.setOrderId(null);
      outOrderService.save(outOrder);

      //#endregion

      //#region 生成出库单明细
      //生成出库明细
      for (InReturnDetail detail : details) {
        OutOrderDetail outOrderDetail = BeanUtil.copyProperties(detail, OutOrderDetail.class);
        outOrderDetail.setQuantityOrder(detail.getReturnQuantity());
        outOrderDetail.setSalePrice(detail.getPurchasePrice());
        outOrderDetail.setSaleAmount(detail.getPurchaseAmount());
        outOrderDetail.setOrderId(outOrder.getOrderId());
        outOrderDetail.setSourceDetailId("" + detail.getReturnDetailId());
        outOrderDetail.setSourceMainId("" + detail.getReturnId());
        outOrderDetail.setSourceType(InReturnEnum.RETURN_ORDER.getName());
        outOrderDetail.setOrderDetailId(null);
        outOrderDetailService.save(outOrderDetail);

        //查询枚举
        HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(returnInfo.getOrderType());
        // 修改占位信息
        LambdaUpdateWrapper<CoreInventoryHolder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CoreInventoryHolder::getBillCode, outOrder.getOrderCode())
          .set(CoreInventoryHolder::getMainId, outOrder.getOrderId())
          .set(CoreInventoryHolder::getDetailId, outOrderDetail.getOrderDetailId())
          .eq(CoreInventoryHolder::getSourceType, holderSourceTypeEnum.getName())
          .eq(CoreInventoryHolder::getMainId, returnInfo.getReturnId())
          .eq(CoreInventoryHolder::getDetailId, detail.getReturnDetailId());
        coreInventoryHolderService.update(updateWrapper);
      }
      //#endregion

      //出库单添加轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.RETURN_IN, OutOrderStatusEnum.AUDIT_SUCCESS, returnInfo.getReturnCode());

      //#region 更新退货单状态已经添加轨迹
      //退货单添加轨迹
      inReturnStatusHistoryService.addHistoryInfo(returnInfo, InReturnActionEnum.TO_OUT_ORDER, InReturnEnum.SUCCESS, InReturnEnum.TO_OUT_ORDER, outOrder.getOrderCode());
      //修改退货单状态
      returnInfo.setReturnStatus(InReturnEnum.TO_OUT_ORDER.getName());
      inReturnService.updateById(returnInfo);
      //#endregion

      //如果是确认出库按钮点击则需要调用出库单的一键出库功能
      if (StrUtil.equals(returnType, "validateOut")) {
        //分拣成功 自动出库
        OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrder, OutScanMainBo.class);
        newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.RETURN_ORDER);
        newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.RETURN_ORDER);
        List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId()); // 订单明细集合
        newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
        for (var outOrderDetail : outOrderDetailList) {
          OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
          outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
          newOutScanOrderBo.getDataList().add(outScanDetailBo);
        }
        IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);
        outScanOrderService.normalOutSave(newOutScanOrderBo);

        // 质检结果返回后  出库单也出完库之后需要将之前占位质检结果的那些单据进行出库
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.QUALITY_TESTING_RESULT_OUT_ORDER_QUICK_OUT); //类别
        rabbitReceiverDto.setBillId(111L);
        rabbitReceiverDto.setBillCode("111");
        taskQueueService.createTask(rabbitReceiverDto);
      }

    }
    return R.ok("转出成功");
  }
}
