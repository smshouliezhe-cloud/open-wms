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
 * magic api 接口对象 magic_api_file
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "magic_api_file", autoResultMap = true)
public class MagicApiFile extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 路径
   */
  @TableId(value = "file_path")
  private String filePath;

  /**
   * 内容
   */
  private String fileContent;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
