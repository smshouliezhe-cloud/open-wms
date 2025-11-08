package com.yiruantong.system.domain.core.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 上传对象信息
 *
 * @author YiRuanTong
 */
@Data
public class SysOssUploadVo {

  /**
   * URL地址
   */
  private String url;

  /**
   * 文件名
   */
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
   * md5值
   */
  @ExcelProperty(value = "md5值")
  private String md5key;

  /**
   * 文件大小
   */
  @ExcelProperty(value = "文件大小")
  private BigDecimal fileSize;

  /**
   * 对象存储主键
   */
  private String ossId;

}
