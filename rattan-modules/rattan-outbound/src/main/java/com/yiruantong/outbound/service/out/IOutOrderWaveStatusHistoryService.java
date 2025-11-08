package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.out.OutOrderWaveStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderWaveStatusHistoryVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderWaveStatusHistoryBo;

/**
 * 订单状态历史记录Service接口
 *
 * @author YRT
 * @date 2023-11-24
 */
public interface IOutOrderWaveStatusHistoryService extends IServicePlus<OutOrderWaveStatusHistory, OutOrderWaveStatusHistoryVo, OutOrderWaveStatusHistoryBo> {

  /**
   * 添加波次查询操作轨迹
   * @param outOrderWave 波次查询信息
   * @param outWaveOperationTypeEnum
   * @param toStatus
   * @param remark
   */
  void AddHistory(OutOrderWave outOrderWave, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum toStatus, String remark);
  /**
   * 添加波次查询操作轨迹
   * @param outOrderWave 波次查询信息
   * @param outWaveOperationTypeEnum
   * @param toStatus
   */
  void AddHistory(OutOrderWave outOrderWave, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum toStatus);
}
