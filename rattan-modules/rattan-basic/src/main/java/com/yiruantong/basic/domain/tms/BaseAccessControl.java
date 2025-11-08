package com.yiruantong.basic.domain.tms;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 门禁信息对象 base_access_control
 *
 * @author YRT
 * @date 2024-12-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_access_control", autoResultMap = true)
public class BaseAccessControl extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 门禁信息id
   */
    @TableId(value = "access_control_id")
  private Long accessControlId;

  /**
   * 记录单号
   */
  private String recordNo;

  /**
   * 车牌号
   */
  private String carPlateNo;

  /**
   * 物料类型
   */
  private String materialType;

  /**
   * 入场时间
   */
  private Date enterTime;

  /**
   * 进场通道名称
   */
  private String channelName;

  /**
   * 岗亭名称
   */
  private String clientComputerName;

  /**
   * 通行证类
   */
  private String passportTypeName;

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

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 最后登录IP
   */
  private String loginIp;

  /**
   * 最后登录时间
   */
  private Date loginDate;


}
