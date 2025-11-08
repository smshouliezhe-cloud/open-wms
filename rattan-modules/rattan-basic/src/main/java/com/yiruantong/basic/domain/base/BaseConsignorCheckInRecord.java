package com.yiruantong.basic.domain.base;

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
 * 门店打卡记录对象 base_consignor_check_in_record
 *
 * @author YRT
 * @date 2025-01-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor_check_in_record", autoResultMap = true)
public class BaseConsignorCheckInRecord extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 打卡记录id
   */
  @TableId(value = "check_in_record_id")
  private Long checkInRecordId;

  /**
   * 责任业务员
   */
  private String salesName;

  /**
   * 打卡地址
   */
  private String punchInAddress;

  /**
   * 经度
   */
  private String lng;

  /**
   * 纬度
   */
  private String lat;

  /**
   * 图片
   */
  private String images;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 拜访时间
   */
  private Date visitDate;

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
