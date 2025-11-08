package com.yiruantong.basic.domain.common.bo;

import com.yiruantong.basic.domain.common.CommonComment;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;


/**
 * 页面单据评论业务对象 common_comment
 *
 * @author YRT
 * @date 2025-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CommonComment.class, reverseConvertGenerate = false)
public class CommonCommentBo extends BaseEntity {

      /**
       * 评论ID
       */
        @NotNull(message = "评论ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long commentId;

      /**
       * 评论内容
       */
        @NotBlank(message = "评论内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

      /**
       * 回复
       */
        @NotNull(message = "回复不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> reply;

      /**
       * 备注
       */
        @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

      /**
       * 服务商
       */
        @NotBlank(message = "服务商不能为空", groups = { AddGroup.class, EditGroup.class })
    private String service;

      /**
       * 单据类型
       */
        @NotBlank(message = "单据类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billType;

      /**
       * 单据id
       */
        @NotNull(message = "单据id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long billId;

      /**
       * 单号
       */
        @NotBlank(message = "单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billCode;


}
