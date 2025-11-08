package com.yiruantong.inventory.service.operation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageStatusAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageStatusAdjustDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageStatusAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageStatusAdjustDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 状态属性调整明细Service业务层处理
 *
 * @author YRT
 * @date 2025-02-14
 */
@RequiredArgsConstructor
@Service
public class StorageStatusAdjustDetailServiceImpl extends ServiceImplPlus<StorageStatusAdjustDetailMapper, StorageStatusAdjustDetail, StorageStatusAdjustDetailVo, StorageStatusAdjustDetailBo> implements IStorageStatusAdjustDetailService {
  @Override
  public List<StorageStatusAdjustDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageStatusAdjustDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageStatusAdjustDetail::getStatusAdjustId, id);

    return this.list(detailLambdaQueryWrapper);
  }
}
