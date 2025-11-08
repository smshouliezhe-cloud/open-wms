package com.yiruantong.outbound.service.operation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveDetailBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailVo;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.mapper.operation.OutOrderWaveDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;

import java.util.List;

/**
 * 出库单波次明细Service业务层处理
 *
 * @author YRT
 * @date 2023-11-01
 */
@RequiredArgsConstructor
@Service
public class OutOrderWaveDetailServiceImpl extends ServiceImplPlus<OutOrderWaveDetailMapper, OutOrderWaveDetail, OutOrderWaveDetailVo, OutOrderWaveDetailBo> implements IOutOrderWaveDetailService {
  /**
   * 根据主表ID获取明细集合
   *
   * @param orderWaveId
   * @return 返回明细集合
   */
  public List<OutOrderWaveDetail> selectListById(Long orderWaveId) {
    LambdaQueryWrapper<OutOrderWaveDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutOrderWaveDetail::getOrderWaveId, orderWaveId);

    return this.list(detailLambdaQueryWrapper);
  }

  @Override
  public List<OutOrderWaveDetail> selectListByMainId(Long orderWaveId) {
    LambdaQueryWrapper<OutOrderWaveDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutOrderWaveDetail::getOrderWaveId, orderWaveId);

    return this.list(detailLambdaQueryWrapper);
  }

}
