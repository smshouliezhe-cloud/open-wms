package com.yiruantong.system.domain.magic.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.magic.MagicPageBackup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 设计器备份视图对象 magic_page_backup
 *
 * @author YRT
 * @date 2024-11-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MagicPageBackup.class)
public class MagicPageBackupVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 备份ID
   */
  @ExcelProperty(value = "备份ID")
  private Long backupId;

  /**
   * 主键
   */
  @ExcelProperty(value = "主键")
  private Long pageId;

  /**
   * 页面标题
   */
  @ExcelProperty(value = "页面标题")
  private String title;

  /**
   * 副标题
   */
  @ExcelProperty(value = "副标题")
  private String subtitle;

  /**
   * 父页面
   */
  @ExcelProperty(value = "父页面")
  private Long parentId;

  /**
   * 页面json
   */
  @ExcelProperty(value = "页面json")
  private String pageschema;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private Long status;

  /**
   * 分类编码
   */
  @ExcelProperty(value = "分类编码")
  private String classify;

  /**
   * 文件描述
   */
  @ExcelProperty(value = "文件描述")
  private String remarks;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 页面脚本
   */
  @ExcelProperty(value = "页面脚本")
  private String pageScript;

  /**
   * 版本号
   */
  @ExcelProperty(value = "版本号")
  private Long version;


}
