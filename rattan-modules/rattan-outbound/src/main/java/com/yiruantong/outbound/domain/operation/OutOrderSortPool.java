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
 * 分拣池对象 out_order_sort_pool
 *
 * @author YRT
 * @date 2024-05-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_sort_pool", autoResultMap = true)
public class OutOrderSortPool extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 分拣池ID
   */
  @TableId(value = "sort_pool_id")
  private Long sortPoolId;

  /**
   * 订单ID
   */
  private Long orderId;

  /**
   * 池状态
   */
  private Byte poolState;

  /**
   * 权重
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
   * 出库单号
   */
  private String orderCode;

  /**
   * ERP单号
   */
  private String storeOrderCode;


}
