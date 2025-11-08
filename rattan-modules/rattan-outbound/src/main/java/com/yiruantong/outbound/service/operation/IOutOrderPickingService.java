package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.bo.OutOrderPickingBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingVo;

import java.util.List;

/**
 * 订单拣货查询Service接口
 *
 * @author YRT
 * @date 2023-12-01
 */
public interface IOutOrderPickingService extends IServicePlus<OutOrderPicking, OutOrderPickingVo, OutOrderPickingBo> {
  /**
   * 根据波次单编号获取拣货单号集合
   *
   * @param orderWaveCode 波次单号
   * @return 返回拣货单集合
   */
  List<OutOrderPicking> selectListByOrderWaveCode(String orderWaveCode);

  /**
   * 根据波次单编号获取拣货单号
   *
   * @param orderWaveCode 波次单号
   * @return 返回拣货单信息
   */
  OutOrderPicking getByOrderWaveCode(String orderWaveCode);

  /**
   * 生成拣货单
   *
   * @param orderWaveCode 波次单号
   * @param orderWave     波次单信息
   */
  void createPicking(String orderWaveCode, OutOrderWave orderWave, InventorySourceTypeEnum scanInType);
}
