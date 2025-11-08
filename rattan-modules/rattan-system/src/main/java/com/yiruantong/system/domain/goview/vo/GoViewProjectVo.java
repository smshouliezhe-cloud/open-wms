package com.yiruantong.system.domain.goview.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.goview.GoViewProject;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 大屏项目视图对象 go_view_project
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = GoViewProject.class)
public class GoViewProjectVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ExcelProperty(value = "编号")
  private Long id;

  /**
   * 项目名
   */
  @ExcelProperty(value = "项目名")
  private String projectName;

  /**
   * 封面
   */
  @ExcelProperty(value = "封面")
  private String indexImage;

  /**
   * 发布(1发布-1取消发布)
   */
  @ExcelProperty(value = "发布(1发布-1取消发布)")
  private Long state;

  /**
   * 内容
   */
  @ExcelProperty(value = "内容")
  private String content;

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
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

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


}
