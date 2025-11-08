package com.yiruantong.basic.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.basic.domain.common.CommonAttachment;
import com.yiruantong.basic.domain.common.bo.CommonAttachmentBo;
import com.yiruantong.basic.domain.common.vo.CommonAttachmentVo;
import com.yiruantong.basic.mapper.common.CommonAttachmentMapper;
import com.yiruantong.basic.service.common.ICommonAttachmentService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模块附件Service业务层处理
 *
 * @author YRT
 * @date 2025-03-08
 */
@RequiredArgsConstructor
@Service
public class CommonAttachmentServiceImpl extends ServiceImplPlus<CommonAttachmentMapper, CommonAttachment, CommonAttachmentVo, CommonAttachmentBo> implements ICommonAttachmentService {
  @Override
  public R<Void> uploadFile(CommonAttachmentBo commonAttachmentBo) {
    return null;
  }

  @Override
  public List<CommonAttachment> getByBillIds(List<Long> ids) {
    LambdaQueryWrapper<CommonAttachment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(CommonAttachment::getBillId, ids);
    return this.list(queryWrapper);
  }
}
