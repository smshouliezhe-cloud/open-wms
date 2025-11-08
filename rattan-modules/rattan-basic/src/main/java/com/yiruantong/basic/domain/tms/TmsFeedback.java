package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 司机反馈对象 tms_feedback
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_feedback", autoResultMap = true)
public class TmsFeedback extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 反馈ID
   */
  @TableId(value = "feedback_id")
  private Long feedbackId;

  /**
   * 司机姓名
   */
  private String driverName;

  /**
   * 手机号码
   */
  private String mobile;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 问题类型
   */
  private String questionType;

  /**
   * 处理类型
   */
  private String resolutionType;

  /**
   * 反馈内容
   */
  private String feedbackContent;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
