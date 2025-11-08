package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderWaveSub;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveSubBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveSubVo;

/**
 * 子波次Service接口
 *
 * @author YRT
 * @date 2024-08-24
 */
public interface IOutOrderWaveSubService extends IServicePlus<OutOrderWaveSub, OutOrderWaveSubVo, OutOrderWaveSubBo> {
  /**
   * 子波次单编号
   *
   * @param subOrderWaveCode 子波次号
   * @return 返回子波次单信息
   */
  OutOrderWaveSub getByCode(String subOrderWaveCode);

  /**
   * 更新子波次所熟人
   *
   * @param subOrderWaveCode 子波次号
   * @param userId           所属人ID
   * @param nickName         所属人
   * @return 返回子波次单信息
   */
  boolean updatePickUserInfo(String subOrderWaveCode, Long userId, String nickName);
}
