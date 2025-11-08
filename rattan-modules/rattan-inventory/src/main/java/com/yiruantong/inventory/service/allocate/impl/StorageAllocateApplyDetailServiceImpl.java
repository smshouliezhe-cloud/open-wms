package com.yiruantong.inventory.service.allocate.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyDetailBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyDetailVo;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateApplyDetailMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyDetailService;

import java.util.List;

/**
 * 调拨申请单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-19
 */
@RequiredArgsConstructor
@Service
public class StorageAllocateApplyDetailServiceImpl extends ServiceImplPlus<StorageAllocateApplyDetailMapper, StorageAllocateApplyDetail, StorageAllocateApplyDetailVo, StorageAllocateApplyDetailBo> implements IStorageAllocateApplyDetailService {

  @Override
  public List<StorageAllocateApplyDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<StorageAllocateApplyDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageAllocateApplyDetail::getAllocateApplyId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }
}
