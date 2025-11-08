package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderMatching;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.bo.OutOrderMatchingBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingVo;

/**
 * 订单配货Service接口
 *
 * @author YRT
 * @date 2023-12-09
 */
public interface IOutOrderMatchingService extends IServicePlus<OutOrderMatching, OutOrderMatchingVo, OutOrderMatchingBo> {
  /**
   * 子波次单编号
   *
   * @param orderWaveCode 波次单号
   * @return 返回子波次单信息
   */
  OutOrderMatching getbyOrderWaveCode(String orderWaveCode);

  /**
   * 生成拣货单
   * @param orderWaveCode 波次单号
   * @param outOrderWave 波次单信息
   */
    void createMatch(String orderWaveCode, OutOrderWave outOrderWave);
}
