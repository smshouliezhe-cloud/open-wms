package com.yiruantong.system.domain.magic;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * magic api 备份对象 magic_api_backup
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "magic_api_backup", autoResultMap = true)
public class MagicApiBackup extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 原对象ID
   */
  @TableId(value = "id")
  private String id;

  /**
   * 原名称
   */
  private String name;

  /**
   * 备份内容
   */
  private String content;

  /**
   * 标签
   */
  private String tag;

  /**
   * 类型
   */
  private String type;

  /**
   * 备份时间
   */
  private Date createDate;


}
