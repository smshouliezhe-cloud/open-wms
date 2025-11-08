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
 * 防伪码轨迹对象 base_product_security_history
 *
 * @author YRT
 * @date 2024-04-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_product_security_history", autoResultMap = true)
public class BaseProductSecurityHistory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 历史ID
   */
  @TableId(value = "security_history_id")
  private Long securityHistoryId;

  /**
   * 操作类型
   */
  private String operationType;

  /**
   * 变更前状态
   */
  private String fromStatus;

  /**
   * 变更后状态
   */
  private String toStatus;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 单据编号
   */
  private String billCode;

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

  /**
   * 防伪标签id
   */
  private Long securityId;

  /**
   * 防伪码明细ID
   */
  private Long securityDetailId;

  /**
   * 防伪码
   */
  private String securityCode;

  /**
   * 操作数量
   */
  private Long quantity;


}
