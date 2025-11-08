package com.yiruantong.system.domain.magic.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.magic.MagicApiBackup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * magic api 备份视图对象 magic_api_backup
 *
 * @author YRT
 * @date 2024-11-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MagicApiBackup.class)
public class MagicApiBackupVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 原对象ID
   */
  @ExcelProperty(value = "原对象ID")
  private String id;

  /**
   * 原名称
   */
  @ExcelProperty(value = "原名称")
  private String name;

  /**
   * 备份内容
   */
  @ExcelProperty(value = "备份内容")
  private String content;

  /**
   * 标签
   */
  @ExcelProperty(value = "标签")
  private String tag;

  /**
   * 类型
   */
  @ExcelProperty(value = "类型")
  private String type;

  /**
   * 备份时间
   */
  @ExcelProperty(value = "备份时间")
  private Date createDate;


}
