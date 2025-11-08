package com.yiruantong.system.domain.core.vo;

import java.math.BigDecimal;

import com.yiruantong.system.domain.core.SysOss;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * OSS对象存储视图对象 sys_oss
 *
 * @author YiRuanTong
 * @date 2025-01-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysOss.class)
public class SysOssVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 对象存储主键
   */
  @ExcelProperty(value = "对象存储主键")
  private Long ossId;

  /**
   * 文件名
   */
  @ExcelProperty(value = "文件名")
  private String fileName;

  /**
   * 原名
   */
  @ExcelProperty(value = "原名")
  private String originalName;

  /**
   * 文件后缀名
   */
  @ExcelProperty(value = "文件后缀名")
  private String fileSuffix;

  /**
   * URL地址
   */
  @ExcelProperty(value = "URL地址")
  private String url;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 服务商
   */
  @ExcelProperty(value = "服务商")
  private String service;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * md5值
   */
  @ExcelProperty(value = "md5值")
  private String md5key;

  /**
   * 文件大小
   */
  @ExcelProperty(value = "文件大小")
  private BigDecimal fileSize;


}
