package com.yiruantong.outbound.domain.operation;

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
 * 子波次对象 out_order_wave_sub
 *
 * @author YRT
 * @date 2024-09-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_wave_sub", autoResultMap = true)
public class OutOrderWaveSub extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "sub_id")
  private Long subId;

  /**
   * 子波次单号
   */
  private String subOrderWaveCode;

  /**
   * 波次ID
   */
  private Long orderWaveId;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 子波次状态
   */
  private String subWaveStatus;

  /**
   * 拣货人ID
   */
  private Long pickUserId;

  /**
   * 拣货人
   */
  private String pickNickName;

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
