package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.TmsFeedback;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 司机反馈视图对象 tms_feedback
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsFeedback.class)
public class TmsFeedbackVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 反馈ID
   */
  @ExcelProperty(value = "反馈ID")
  private Long feedbackId;

  /**
   * 司机姓名
   */
  @ExcelProperty(value = "司机姓名")
  private String driverName;

  /**
   * 手机号码
   */
  @ExcelProperty(value = "手机号码")
  private String mobile;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 问题类型
   */
  @ExcelProperty(value = "问题类型")
  private String questionType;

  /**
   * 处理类型
   */
  @ExcelProperty(value = "处理类型")
  private String resolutionType;

  /**
   * 反馈内容
   */
  @ExcelProperty(value = "反馈内容")
  private String feedbackContent;

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


}
