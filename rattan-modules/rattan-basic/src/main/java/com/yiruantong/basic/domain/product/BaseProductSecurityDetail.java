package com.yiruantong.basic.domain.product;

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
 * 防伪标签明细对象 base_product_security_detail
 *
 * @author YRT
 * @date 2024-04-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_product_security_detail", autoResultMap = true)
public class BaseProductSecurityDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 防伪码明细ID
   */
  @TableId(value = "security_detail_id")
  private Long securityDetailId;

  /**
   * 防伪标签id
   */
  private Long securityId;

  /**
   * 防伪码
   */
  private String securityCode;

  /**
   * 状态
   */
  private String securityStatus;

  /**
   * 打印次数
   */
  private Long printCount;

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
