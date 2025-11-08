package com.yiruantong.system.domain.decorate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.decorate.SysPage;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 页面装修视图对象 sys_page
 *
 * @author YRT
 * @date 2024-09-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysPage.class)
public class SysPageVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 页面装修ID
   */
  @ExcelProperty(value = "页面装修ID")
  private Long pageId;

  /**
   * 页面名称
   */
  @ExcelProperty(value = "页面名称")
  private String pageName;

  /**
   * 页面类型
   */
  @ExcelProperty(value = "页面类型")
  private String pageType;

  /**
   * 页面编码
   */
  @ExcelProperty(value = "页面编码")
  private String pageCode;

  /**
   * jsonData
   */
  @ExcelProperty(value = "jsonData")
  private String jsonData;

  /**
   * 页面状态
   */
  @ExcelProperty(value = "页面状态")
  private Integer enable;

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
   * 租户ID
   */
  @ExcelProperty(value = "租户ID")
  private String tenantId;

  /**
   * 父级id
   */
  @ExcelProperty(value = "父级id")
  private Long parentId;

  /**
   * 完整父级ID
   */
  @ExcelProperty(value = "完整父级ID")
  private String fullId;

  /**
   * 父级名称
   */
  @ExcelProperty(value = "父级名称")
  private String fullName;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;


}
