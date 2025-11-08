package com.yiruantong.system.domain.core;

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
 * OSS对象存储对象 sys_oss
 *
 * @author YiRuanTong
 * @date 2025-01-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oss", autoResultMap = true)
public class SysOss extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 对象存储主键
   */
  @TableId(value = "oss_id")
  private Long ossId;

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
   * 服务商
   */
  private String service;

  /**
   * md5值
   */
  private String md5key;

  /**
   * 文件大小
   */
  private BigDecimal fileSize;


}
