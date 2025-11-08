package com.yiruantong.outbound.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.domain.service.bo.OutReturnDetailBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnDetailVo;
import com.yiruantong.outbound.mapper.service.OutReturnDetailMapper;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出库退货单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class OutReturnDetailServiceImpl extends ServiceImplPlus<OutReturnDetailMapper, OutReturnDetail, OutReturnDetailVo, OutReturnDetailBo> implements IOutReturnDetailService {

  @Override
  public List<OutReturnDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<OutReturnDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutReturnDetail::getReturnId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }
}
