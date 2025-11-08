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


import java.io.Serial;

/**
 * 页面单据评论对象 common_comment
 *
 * @author YRT
 * @date 2025-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "common_comment", autoResultMap = true)
public class CommonComment extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 评论ID
   */
    @TableId(value = "comment_id")
  private Long commentId;

  /**
   * 评论内容
   */
  private String content;

  /**
   * 回复
   */
    @TableField(value = "reply", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> reply;

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
