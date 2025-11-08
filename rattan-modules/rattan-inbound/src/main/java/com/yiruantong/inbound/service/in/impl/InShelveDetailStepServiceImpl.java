package com.yiruantong.inbound.service.in.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.in.bo.InShelveDetailStepBo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailStepVo;
import com.yiruantong.inbound.domain.in.InShelveDetailStep;
import com.yiruantong.inbound.mapper.in.InShelveDetailStepMapper;
import com.yiruantong.inbound.service.in.IInShelveDetailStepService;

import java.util.List;

/**
 * 商品上架明细的明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class InShelveDetailStepServiceImpl extends ServiceImplPlus<InShelveDetailStepMapper, InShelveDetailStep, InShelveDetailStepVo, InShelveDetailStepBo> implements IInShelveDetailStepService {
  @Override
  public List<InShelveDetailStep> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<InShelveDetailStep> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InShelveDetailStep::getShelveId, mainId);

    return this.list(detailLambdaQueryWrapper);

  }
}
