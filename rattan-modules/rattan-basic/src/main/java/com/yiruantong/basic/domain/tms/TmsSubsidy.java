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
 * 挂车管理对象 tms_subsidy
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_subsidy", autoResultMap = true)
public class TmsSubsidy extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车贴ID
   */
  @TableId(value = "subsidy_Id")
  private Long subsidyId;

  /**
   * 车贴单号
   */
  private String subsidyCode;

  /**
   * 司机姓名
   */
  private String driverName;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 生成状态
   */
  private String buildStatus;

  /**
   * 车贴奖励
   */
  private Long subsidyReward;

  /**
   * 结算对象
   */
  private String settlementName;

  /**
   * 审核状态
   */
  private Long auditing;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核时间
   */
  private Date auditDate;

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
