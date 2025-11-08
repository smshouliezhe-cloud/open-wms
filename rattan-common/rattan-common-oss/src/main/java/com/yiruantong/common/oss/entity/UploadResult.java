package com.yiruantong.common.oss.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 上传返回体
 *
 * @author YiRuanTong
 */
@Data
@Builder
public class UploadResult {

  /**
   * 文件路径
   */
  private String url;

  /**
   * 文件名
   */
  private String filename;

  /**
   * md5值
   */
  private String md5Key;

  /**
   * 文件大小
   */
  private BigDecimal fileSize;
}
