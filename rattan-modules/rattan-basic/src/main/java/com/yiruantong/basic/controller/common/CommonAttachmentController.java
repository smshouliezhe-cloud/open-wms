package com.yiruantong.basic.controller.common;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.common.CommonAttachment;
import com.yiruantong.basic.domain.common.bo.CommonAttachmentBo;
import com.yiruantong.basic.domain.common.vo.CommonAttachmentVo;
import com.yiruantong.basic.mapper.common.CommonAttachmentMapper;
import com.yiruantong.basic.service.common.ICommonAttachmentService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模块附件
 *
 * @author YRT
 * @date 2025-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/common/attachment")
public class CommonAttachmentController extends AbstractController<CommonAttachmentMapper, CommonAttachment, CommonAttachmentVo, CommonAttachmentBo> {
  private final ICommonAttachmentService commonAttachmentService;

  /**
   * 上传附件
   *
   * @param commonAttachmentBo 删除附件
   * @return 返回保存结果
   */
  @PostMapping("/uploadFile")
  public R<Void> uploadFile(@RequestBody CommonAttachmentBo commonAttachmentBo) {
    return commonAttachmentService.uploadFile(commonAttachmentBo);
  }
}
