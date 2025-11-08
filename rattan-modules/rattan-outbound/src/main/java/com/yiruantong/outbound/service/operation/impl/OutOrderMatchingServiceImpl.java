package com.yiruantong.outbound.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.out.OutMatchStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.outbound.domain.operation.OutOrderMatching;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.bo.OutOrderMatchingBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.mapper.operation.OutOrderMatchingMapper;
import com.yiruantong.outbound.service.operation.IOutOrderMatchingService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单配货Service业务层处理
 *
 * @author YRT
 * @date 2023-12-09
 */
@RequiredArgsConstructor
@Service
public class OutOrderMatchingServiceImpl extends ServiceImplPlus<OutOrderMatchingMapper, OutOrderMatching, OutOrderMatchingVo, OutOrderMatchingBo> implements IOutOrderMatchingService {
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderService outOrderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  @Override
  public OutOrderMatching getbyOrderWaveCode(String orderWaveCode) {
    LambdaQueryWrapper<OutOrderMatching> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(OutOrderMatching::getOrderWaveCode, orderWaveCode)
      .last("limit 1");

    return this.getOne(shelveLambdaQueryWrapper);
  }

  @Override
  public void createMatch(String orderWaveCode, OutOrderWave outOrderWave) {
    LambdaQueryWrapper<OutOrderMatching> pickingLambdaQueryWrapper = new LambdaQueryWrapper<>();
    pickingLambdaQueryWrapper.eq(OutOrderMatching::getOrderWaveCode, outOrderWave.getOrderWaveCode())
      .eq(OutOrderMatching::getUserId, LoginHelper.getUserId());

    OutOrderMatching outOrderMatching = this.getOne(pickingLambdaQueryWrapper);

    if (ObjectUtil.isNull(outOrderMatching)) {
      outOrderMatching = new OutOrderMatching();
      BeanUtil.copyProperties(outOrderWave, outOrderMatching);
      outOrderMatching.setStartDate(DateUtil.date());
      outOrderMatching.setUserId(LoginHelper.getUserId());
      outOrderMatching.setNickName(LoginHelper.getNickname());
      outOrderMatching.setMatchingCode(DBUtils.getCodeRegular(MenuEnum.MENU_1745));
      outOrderMatching.setMatchStatus(OutMatchStatusEnum.MATCHING.getName());
      this.save(outOrderMatching);

      List<OutOrder> outOrderList = outOrderService.selectByOrderWaveId(outOrderWave.getOrderWaveId());
      for (OutOrder outOrder : outOrderList) {
        // 增加出库单轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_MATCHING, OutOrderStatusEnum.MATCHING);
      }

      // 更新出库单配货状态
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper
        .set(OutOrder::getMatchStatus, OutMatchStatusEnum.MATCHING.getName())
        .eq(OutOrder::getOrderWaveId, outOrderWave.getOrderWaveId());
      outOrderService.update(orderLambdaUpdateWrapper);
    }
  }
}
