package com.yiruantong.inbound.domain.in;

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
 * 采购订单流水记录对象 in_order_status_history
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_order_status_history", autoResultMap = true)
public class InOrderStatusHistory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 历史ID
   */
  @TableId(value = "history_id")
  private Long historyId;

  /**
   * 产品Id
   */
  private Long orderId;

  /**
   * 状态类型
   */
  private String statusType;

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
   * 单据号
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


}
