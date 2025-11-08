package com.yiruantong.outbound.liteflow.OutScanCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutPackageStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.out.*;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.*;

import java.math.BigDecimal;
import java.util.List;

@LiteflowComponent(id = "outScanCalcStatusCmp", name = "4.计算状态")
@RequiredArgsConstructor
public class OutScanCalcStatusCmp extends NodeComponent {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutPackageService outPackageService;
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService;

  @Override
  public void process() {
    OutScanMainBo outScanMainBo = this.getRequestData();
    OutScanContext ctx = this.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();
    Long orderId = outOrder.getOrderId();
    OutPackage outPackage = ctx.getOutPackage();
    List<OutOrderDetail> outOrderDetailList = ctx.getOutOrderDetailList(); // 出库单明细集合
    List<OutPackageDetail> outPackageDetailList = ctx.getOutPackageDetailList(); // 打包单明细集合
    OutWaveOperationTypeEnum outWaveOperationTypeEnum = ctx.getOutWaveOperationTypeEnum();
    OutOperationTypeEnum outOperationTypeEnum = ctx.getOutOperationTypeEnum();
    OutOrderWave oldOutOrderWave = outOrderWaveService.getById(outOrder.getOrderWaveId());


    //#region 计算单据状态
    // 添加出库单操作轨迹
    // outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_OUT_SCAN, OutOrderStatusEnum.PACKAGE_FINISHED);

    // 更新出库单合计出库数量
    BigDecimal outedQty = outOrderDetailList.stream().map(OutOrderDetail::getQuantityOuted).reduce(BigDecimal.ZERO, BigDecimal::add);
    outOrder.setTotalQuantityOuted(outedQty);
    outOrder.setUserId(LoginHelper.getUserId());
    outOrder.setNickName(LoginHelper.getNickname());
    outOrderService.getBaseMapper().updateById(outOrder);

    List<OutOrderDetail> outOrderDetails = outOrderDetailService.selectListByMainId(orderId);
    outOrderDetails = outOrderDetails.stream().map(detail -> {
      OutOrderDetail detailDto = BeanUtil.copyProperties(detail, OutOrderDetail.class);

      if (ObjectUtil.isNull(detailDto.getQuantityOuted())) {
        detailDto.setQuantityOuted(BigDecimal.ZERO);
      }
      if (ObjectUtil.isNull(detailDto.getQuantityOrder())) {
        detailDto.setQuantityOrder(BigDecimal.ZERO);
      }
      return detailDto;
    }).toList();
    // 已出货数量
    BigDecimal quantityOuted = outOrderDetails.stream().map(OutOrderDetail::getQuantityOuted).reduce(BigDecimal.ZERO, BigDecimal::add);
    // 已出货数量
    BigDecimal quantityOrder = outOrderDetails.stream().map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);

    // 找到最新一条的状态轨迹
    LambdaQueryWrapper<OutOrderStatusHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(OutOrderStatusHistory::getBillId, outOrder.getOrderId())
      .orderByDesc(OutOrderStatusHistory::getHistoryId)
      .last("limit 1");
    var orderStatusHistory = outOrderStatusHistoryService.getOne(lambdaQueryWrapper);

    if (ObjectUtil.isNotEmpty(orderStatusHistory.getToStatus())) {
      outOrder.setOrderStatus(orderStatusHistory.getToStatus());
    }
    // 更新出库单状态
    if (B.isGreaterOrEqual(quantityOuted, quantityOrder)) {
      // 完全打包
      outOrderService.updateOrderStatus(outOrder.getOrderId(), OutOrderStatusEnum.PACKAGE_FINISHED);
      outOrderService.updatePackageStatus(outOrder.getOrderId(), OutPackageStatusEnum.FINISHED);
      outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.PACKAGE_FINISHED);
      outOrder.setOrderStatus(OutOrderStatusEnum.PACKAGE_FINISHED.getName());
      outOrder.setPackageStatus(OutPackageStatusEnum.FINISHED.getName());
      // 出库单的轨迹

    } else if (B.isGreater(quantityOuted) && B.isLess(quantityOuted, quantityOrder)) {
      // 部分打包
      // outOrderService.updateOrderStatus(outOrder.getOrderId(), OutOrderStatusEnum.PACKAGE_PARTIAL);
      outOrderService.updatePackageStatus(outOrder.getOrderId(), OutPackageStatusEnum.PACKAGING_PARTIAL);
      // outOrder.setOrderStatus(OutOrderStatusEnum.PACKAGE_PARTIAL.getName());
      outOrder.setPackageStatus(OutPackageStatusEnum.PACKAGING_PARTIAL.getName());
      // 出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.PACKAGE_PARTIAL);
    }

    // 修改实际称重
    LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(OutOrder::getFactWeight, outScanMainBo.getFactWeight())
      .set(OutOrder::getOutDate, outPackage.getCreateTime())
      .eq(OutOrder::getOrderId, outOrder.getOrderId());
    outOrderService.update(orderLambdaUpdateWrapper);

    // 更新打包单主表合计及状态
    BigDecimal totalPackageQty = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getPackageQuantity);
    BigDecimal totalRowWeight = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getRowWeight);
    BigDecimal totalPackage = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getRowPackage);
    BigDecimal totalAmount = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getSaleAmount);
    BigDecimal totalRateAmount = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getRateAmount);
    BigDecimal totalBigQty = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getBigQty);
    BigDecimal totalCube = StreamUtils.sum(outPackageDetailList, OutPackageDetail::getRowCube);

    outPackage.setTotalPackageQuantity(totalPackageQty);
    outPackage.setTotalWeight(totalRowWeight);
    outPackage.setTotalPackage(totalPackage);
    outPackage.setTotalBigQty(totalBigQty);
    outPackage.setTotalProductAmount(totalAmount);
    outPackage.setTotalCube(totalCube);

    outPackage.setTotalAmount(B.add(outOrder.getShippingAmount(), totalAmount));
    outPackage.setTotalRateAmount(totalRateAmount);
    outPackage.setPackageStatus(OutOrderStatusEnum.PACKAGE_FINISHED.getName());
    outPackage.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
    outPackage.setAuditor(LoginHelper.getNickname());
    outPackage.setAuditDate(DateUtil.date());
    outPackage.setTaxAmount(B.sub(totalRateAmount, totalAmount));
    outPackageService.getBaseMapper().updateById(outPackage);

    OutOrderWave outOrderWave = outOrderWaveService.getById(outOrder.getOrderWaveId());
    if (ObjectUtil.isNotEmpty(outOrderWave)) {
      // 更新波次单状态
      List<OutOrderWaveDetail> outOrderWaveDetails = outOrderWaveDetailService.selectListByMainId(outOrder.getOrderWaveId());
      String orderCode = outOrder.getOrderCode();
      // 已出货数量
      BigDecimal outQuantityOuted = outOrderWaveDetails.stream().filter(item -> B.isGreater(item.getQuantityOuted())).map(OutOrderWaveDetail::getQuantityOuted).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 已出货数量
      BigDecimal outQuantityOrder = outOrderWaveDetails.stream().filter(item -> B.isGreater(item.getQuantityOrder())).map(OutOrderWaveDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);

      if (outOrderWaveDetails.stream().allMatch(f -> B.isGreaterOrEqual(f.getQuantityOuted(), f.getQuantityOrder()))) {
        // 完全打包
        outOrderWave.setWaveStatus(OutOrderStatusEnum.PACKAGE_FINISHED.getName());
        // 生成波次的轨迹
        outOrderWaveStatusHistoryService.AddHistory(oldOutOrderWave, outWaveOperationTypeEnum, OutOrderStatusEnum.PACKAGE_FINISHED, orderCode);
      } else if (B.isGreater(outQuantityOuted) && B.isLess(outQuantityOuted, outQuantityOrder)) {
        // 部分打包
        // outOrderWave.setWaveStatus(OutOrderStatusEnum.PACKAGE_PARTIAL.getName());
        // 生成波次的轨迹
        outOrderWaveStatusHistoryService.AddHistory(oldOutOrderWave, outWaveOperationTypeEnum, OutOrderStatusEnum.PACKAGE_PARTIAL, orderCode);
      }

      // 更新波次未完成订单数
      List<Long> list = outOrderWaveDetails.stream().filter(f -> B.isGreater(f.getQuantityOrder(), f.getQuantityOuted())).map(OutOrderWaveDetail::getOrderId).distinct().toList();
      outOrderWave.setFinishedCount(outOrderWave.getOrderCount() - list.size());
      outOrderWave.setUnFinishedCount(Convert.toLong(list.size()));
      outOrderWaveService.getBaseMapper().updateById(outOrderWave);
    }
    //#endregion

  }
}
