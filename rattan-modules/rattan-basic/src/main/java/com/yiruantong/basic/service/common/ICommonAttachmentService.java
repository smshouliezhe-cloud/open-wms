package com.yiruantong.basic.service.common;

import com.yiruantong.basic.domain.common.CommonAttachment;
import com.yiruantong.basic.domain.common.bo.CommonAttachmentBo;
import com.yiruantong.basic.domain.common.vo.CommonAttachmentVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 模块附件Service接口
 *
 * @author YRT
 * @date 2025-03-08
 */
public interface ICommonAttachmentService extends IServicePlus<CommonAttachment, CommonAttachmentVo, CommonAttachmentBo> {
  /**
   * 上传附件
   *
   * @param commonAttachmentBo 参数
   * @return
   */
  R<Void> uploadFile(CommonAttachmentBo commonAttachmentBo);

  List<CommonAttachment> getByBillIds(List<Long> ids);
}
