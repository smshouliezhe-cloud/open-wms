package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.TmsSubsidy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 挂车管理视图对象 tms_subsidy
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsSubsidy.class)
public class TmsSubsidyVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车贴ID
   */
  @ExcelProperty(value = "车贴ID")
  private Long subsidyId;

  /**
   * 车贴单号
   */
  @ExcelProperty(value = "车贴单号")
  private String subsidyCode;

  /**
   * 司机姓名
   */
  @ExcelProperty(value = "司机姓名")
  private String driverName;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 手机号
   */
  @ExcelProperty(value = "手机号")
  private String mobile;

  /**
   * 生成状态
   */
  @ExcelProperty(value = "生成状态")
  private String buildStatus;

  /**
   * 车贴奖励
   */
  @ExcelProperty(value = "车贴奖励")
  private Long subsidyReward;

  /**
   * 结算对象
   */
  @ExcelProperty(value = "结算对象")
  private String settlementName;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Long auditing;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核时间
   */
  @ExcelProperty(value = "审核时间")
  private Date auditDate;

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
