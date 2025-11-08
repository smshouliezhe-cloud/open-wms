package com.yiruantong.basic.domain.common;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.math.BigDecimal;


import java.io.Serial;

/**
 * 模块附件对象 common_attachment
 *
 * @author YRT
 * @date 2025-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "common_attachment", autoResultMap = true)
public class CommonAttachment extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 附件ID
   */
    @TableId(value = "attachment_id")
  private Long attachmentId;

  /**
   * 文件名
   */
  private String fileName;

  /**
   * 原名
   */
  private String originalName;

  /**
   * 文件后缀名
   */
  private String fileSuffix;

  /**
   * URL地址
   */
  private String url;

  /**
   * md5值
   */
  private String md5key;

  /**
   * 文件大小
   */
  private BigDecimal fileSize;

  /**
   * 备注
   */
  private String remark;

  /**
   * 服务商
   */
  private String service;

  /**
   * 单据类型
   */
  private String billType;

  /**
   * 单据id
   */
  private Long billId;

  /**
   * 单号
   */
  private String billCode;


}
