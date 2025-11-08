package com.yiruantong.outbound.service.operation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.operation.OutOrderWaveSub;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveSubBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveSubVo;
import com.yiruantong.outbound.mapper.operation.OutOrderWaveSubMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveSubService;
import org.springframework.stereotype.Service;

/**
 * 子波次Service业务层处理
 *
 * @author YRT
 * @date 2024-08-24
 */
@RequiredArgsConstructor
@Service
public class OutOrderWaveSubServiceImpl extends ServiceImplPlus<OutOrderWaveSubMapper, OutOrderWaveSub, OutOrderWaveSubVo, OutOrderWaveSubBo> implements IOutOrderWaveSubService {
  /**
   * 根据波次单编号获取波次单信息
   *
   * @param subOrderWaveCode 子波次单号
   * @return 返回波次单信息
   */
  @Override
  public OutOrderWaveSub getByCode(String subOrderWaveCode) {
    LambdaQueryWrapper<OutOrderWaveSub> subLambdaQueryWrapper = new LambdaQueryWrapper<>();
    subLambdaQueryWrapper
      .eq(OutOrderWaveSub::getSubOrderWaveCode, subOrderWaveCode)
      .last("limit 1");

    return this.getOne(subLambdaQueryWrapper);
  }

  @Override
  public boolean updatePickUserInfo(String subOrderWaveCode, Long userId, String nickName) {
    LambdaUpdateWrapper<OutOrderWaveSub> subLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    subLambdaUpdateWrapper
      .set(OutOrderWaveSub::getPickUserId, userId)
      .set(OutOrderWaveSub::getPickNickName, nickName)
      .eq(OutOrderWaveSub::getSubOrderWaveCode, subOrderWaveCode);

    return this.update(subLambdaUpdateWrapper);
  }
  //#endregion
}
