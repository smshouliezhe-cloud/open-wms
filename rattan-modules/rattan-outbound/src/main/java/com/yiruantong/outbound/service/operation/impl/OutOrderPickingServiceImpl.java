package com.yiruantong.outbound.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.out.*;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.bo.OutOrderPickingBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.mapper.operation.OutOrderPickingMapper;
import com.yiruantong.outbound.service.operation.IOutOrderPickingService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.outbound.service.out.IOutOrderWaveStatusHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单拣货查询Service业务层处理
 *
 * @author YRT
 * @date 2023-12-01
 */
@RequiredArgsConstructor
@Service
public class OutOrderPickingServiceImpl extends ServiceImplPlus<OutOrderPickingMapper, OutOrderPicking, OutOrderPickingVo, OutOrderPickingBo> implements IOutOrderPickingService {
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderService outOrderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService;

  //#region selectListByOrderWaveCode

  /**
   * 根据拣货单编号获取拣货单信息
   *
   * @param orderWaveCode 波次单号编号
   * @return 返回拣货单信息
   */
  @Override
  public List<OutOrderPicking> selectListByOrderWaveCode(String orderWaveCode) {
    LambdaQueryWrapper<OutOrderPicking> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(OutOrderPicking::getOrderWaveCode, orderWaveCode);

    return this.list(shelveLambdaQueryWrapper);
  }

  @Override
  public OutOrderPicking getByOrderWaveCode(String orderWaveCode) {
    LambdaQueryWrapper<OutOrderPicking> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper
      .eq(OutOrderPicking::getOrderWaveCode, orderWaveCode)
      .last("limit 1");

    return this.getOne(shelveLambdaQueryWrapper);
  }
  //#endregion

  //#region createPicking

  /**
   * 创建拣货单
   *
   * @param orderWaveCode 波次单号
   * @param outOrderWave  波次单信息
   * @param scanInType    出库单拣货类型枚举
   */
  @Override
  public void createPicking(String orderWaveCode, OutOrderWave outOrderWave, InventorySourceTypeEnum scanInType) {
    boolean isSub = false; // 是否子波次
    String mainWaveCode = orderWaveCode; // 主波次号
    //区分主波次 子波次
    if (orderWaveCode.contains(StringUtils.SEPARATOR_MINUS)) {
      isSub = true;
      var arr = orderWaveCode.split(StringUtils.SEPARATOR_MINUS);
      mainWaveCode = arr[0];
    }

    LambdaQueryWrapper<OutOrderPicking> pickingLambdaQueryWrapper = new LambdaQueryWrapper<>();
    pickingLambdaQueryWrapper.eq(OutOrderPicking::getOrderWaveCode, outOrderWave.getOrderWaveCode())
      .eq(OutOrderPicking::getUserId, LoginHelper.getUserId());

    // 子波次条件
    if (isSub) {
      pickingLambdaQueryWrapper.eq(OutOrderPicking::getSubOrderWaveCode, orderWaveCode);
    }

    OutOperationTypeEnum outOperationTypeEnum = OutOperationTypeEnum.PC_ORDER_PICKING;
    OutWaveOperationTypeEnum waveOperationTypeEnum = OutWaveOperationTypeEnum.PC_PICKING;
    OutPickingTypeEnum outPickingTypeEnum = OutPickingTypeEnum.PC_ORDER_PICKING;
    switch (scanInType) {
      case PDA_ORDER_PICKING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_PICKING;
        waveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_PICKING;
        outPickingTypeEnum = OutPickingTypeEnum.PDA_ORDER_PICKING;
      }
      case PDA_PLATE_PICKING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_PLATE_PICKING;
        waveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_PLATE_PICKING;
        outPickingTypeEnum = OutPickingTypeEnum.PDA_PLATE_PICKING;
      }
      case PDA_ORDER_PICKING_ZG -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_PICKING_ZG;
        waveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_PICKING_ZG;
        outPickingTypeEnum = OutPickingTypeEnum.PDA_ORDER_PICKING_ZG;
      }
    }

    OutOrderPicking outOrderPicking = this.getOne(pickingLambdaQueryWrapper);

    // 波次单不存在，重新创建波次单
    if (ObjectUtil.isNull(outOrderPicking)) {
      outOrderPicking = BeanUtil.copyProperties(outOrderWave, OutOrderPicking.class);
      outOrderPicking.setStartDate(DateUtil.date());
      outOrderPicking.setUserId(LoginHelper.getUserId());
      outOrderPicking.setNickName(LoginHelper.getNickname());
      outOrderPicking.setPickingType(outPickingTypeEnum.getName());
      outOrderPicking.setOrderPickingCode(DBUtils.getCodeRegular(MenuEnum.MENU_1744));
      outOrderPicking.setPickingStatus(OutPickingStatusEnum.PICKING.getName());
      outOrderPicking.setOrderWaveCode(mainWaveCode); // 主波次单号
      if (isSub) {
        outOrderPicking.setSubOrderWaveCode(orderWaveCode);
      }
      this.save(outOrderPicking);

      // 更新波次状态
      LambdaUpdateWrapper<OutOrderWave> waveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      waveLambdaUpdateWrapper
        .set(OutOrderWave::getWaveStatus, OutOrderStatusEnum.PICKING.getName())
        .set(OutOrderWave::getPickingStatus, OutPickingStatusEnum.PICKING.getName())
        .eq(OutOrderWave::getOrderWaveId, outOrderWave.getOrderWaveId());
      outOrderWaveService.update(waveLambdaUpdateWrapper);

      // 生成波次的轨迹
      outOrderWaveStatusHistoryService.AddHistory(outOrderWave, waveOperationTypeEnum, OutOrderStatusEnum.PICKING, "生成拣货单" + outOrderPicking.getOrderPickingCode());

      List<OutOrder> outOrderList = outOrderService.selectByOrderWaveId(outOrderWave.getOrderWaveId());
      for (OutOrder outOrder : outOrderList) {
        // 增加出库单轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.PICKING, "生成拣货单" + outOrderPicking.getOrderPickingCode());
      }

      // 更新出库单拣货状态
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper.set(OutOrder::getPickingStatus, OutOrderStatusEnum.PICKING.getName())
        .set(OutOrder::getOrderStatus, OutOrderStatusEnum.PICKING.getName())
        .eq(OutOrder::getOrderWaveId, outOrderWave.getOrderWaveId());
      outOrderService.update(orderLambdaUpdateWrapper);
    }
  }
  //#endregion
}
