package com.yiruantong.inventory.service.operation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.operation.StorageSnAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageSnAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageSnAdjustDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageSnAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageSnAdjustDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SN调整明细Service业务层处理
 *
 * @author YRT
 * @date 2024-09-05
 */
@RequiredArgsConstructor
@Service
public class StorageSnAdjustDetailServiceImpl extends ServiceImplPlus<StorageSnAdjustDetailMapper, StorageSnAdjustDetail, StorageSnAdjustDetailVo, StorageSnAdjustDetailBo> implements IStorageSnAdjustDetailService {
  @Override
  public List<StorageSnAdjustDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<StorageSnAdjustDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageSnAdjustDetail::getSnAdjustId, mainId);

    return this.list(detailLambdaQueryWrapper);

  }
}
