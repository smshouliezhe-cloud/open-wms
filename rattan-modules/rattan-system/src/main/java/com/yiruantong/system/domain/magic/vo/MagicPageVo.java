package com.yiruantong.system.domain.magic.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.magic.MagicPage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 页面开发视图对象 magic_page
 *
 * @author YRT
 * @date 2024-12-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MagicPage.class)
public class MagicPageVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

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
   * 状态：0、正常，1、禁用
   */
  @ExcelProperty(value = "状态：0、正常，1、禁用")
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

  /**
   * 菜单ID
   */
  @ExcelProperty(value = "菜单ID")
  private Long menuId;

  /**
   * 表ID
   */
  @ExcelProperty(value = "表ID")
  private Long tableId;

  /**
   * 页面编号
   */
  @ExcelProperty(value = "页面编号")
  private String pageCode;


}
