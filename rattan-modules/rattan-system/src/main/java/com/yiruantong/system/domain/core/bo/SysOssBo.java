package com.yiruantong.system.domain.core.bo;

import com.yiruantong.system.domain.core.SysOss;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


/**
 * OSS对象存储业务对象 sys_oss
 *
 * @author YiRuanTong
 * @date 2025-01-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysOss.class, reverseConvertGenerate = false)
public class SysOssBo extends BaseEntity {

  /**
   * 对象存储主键
   */
  @NotNull(message = "对象存储主键不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long ossId;

  /**
   * 文件名
   */
  @NotBlank(message = "文件名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fileName;

  /**
   * 原名
   */
  @NotBlank(message = "原名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String originalName;

  /**
   * 文件后缀名
   */
  @NotBlank(message = "文件后缀名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fileSuffix;

  /**
   * URL地址
   */
  @NotBlank(message = "URL地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String url;

  /**
   * 服务商
   */
  @NotBlank(message = "服务商不能为空", groups = {AddGroup.class, EditGroup.class})
  private String service;

  /**
   * md5值
   */
  @NotBlank(message = "md5值不能为空", groups = {AddGroup.class, EditGroup.class})
  private String md5key;

  /**
   * 文件大小
   */
  @NotNull(message = "文件大小不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal fileSize;


}
