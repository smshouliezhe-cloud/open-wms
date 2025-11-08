package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveDetailBo;

import java.util.List;

/**
 * 出库单波次明细Service接口
 *
 * @author YRT
 * @date 2023-11-01
 */
public interface IOutOrderWaveDetailService extends IServicePlus<OutOrderWaveDetail, OutOrderWaveDetailVo, OutOrderWaveDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param orderWaveId
   * @return 返回明细集合
   */
  List<OutOrderWaveDetail> selectListById(Long orderWaveId);

  /**
   * 根据主表ID获取明细集合
   *
   * @param orderWaveId 波次单ID
   * @return 返回明细集合
   */
  List<OutOrderWaveDetail> selectListByMainId(Long orderWaveId);
}
