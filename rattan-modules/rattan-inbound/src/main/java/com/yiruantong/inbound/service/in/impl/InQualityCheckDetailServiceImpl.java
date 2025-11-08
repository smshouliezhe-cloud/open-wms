package com.yiruantong.inbound.service.in.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;
import com.yiruantong.inbound.domain.in.bo.InQualityCheckDetailBo;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckDetailVo;
import com.yiruantong.inbound.mapper.in.InQualityCheckDetailMapper;
import com.yiruantong.inbound.service.in.IInQualityCheckDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 质检管理明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-25
 */
@RequiredArgsConstructor
@Service
public class InQualityCheckDetailServiceImpl extends ServiceImplPlus<InQualityCheckDetailMapper, InQualityCheckDetail, InQualityCheckDetailVo, InQualityCheckDetailBo> implements IInQualityCheckDetailService {
  @Override
  public List<InQualityCheckDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<InQualityCheckDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InQualityCheckDetail::getQualityCheckId, mainId);
    return this.list(detailLambdaQueryWrapper);
  }
}
