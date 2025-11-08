package com.yiruantong.basic.domain.common.bo;

import com.yiruantong.basic.domain.common.CommonAttachment;
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
import java.math.BigDecimal;


/**
 * 模块附件业务对象 common_attachment
 *
 * @author YRT
 * @date 2025-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CommonAttachment.class, reverseConvertGenerate = false)
public class CommonAttachmentBo extends BaseEntity {

      /**
       * 附件ID
       */
        @NotNull(message = "附件ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long attachmentId;

      /**
       * 文件名
       */
        @NotBlank(message = "文件名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileName;

      /**
       * 原名
       */
        @NotBlank(message = "原名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String originalName;

      /**
       * 文件后缀名
       */
        @NotBlank(message = "文件后缀名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileSuffix;

      /**
       * URL地址
       */
        @NotBlank(message = "URL地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String url;

      /**
       * md5值
       */
        @NotBlank(message = "md5值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String md5key;

      /**
       * 文件大小
       */
        @NotNull(message = "文件大小不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal fileSize;

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
