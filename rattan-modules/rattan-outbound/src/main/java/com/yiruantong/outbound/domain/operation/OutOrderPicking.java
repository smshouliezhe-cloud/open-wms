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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单拣货查询对象 out_order_picking
 *
 * @author YRT
 * @date 2023-12-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_picking", autoResultMap = true)
public class OutOrderPicking extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 拣货单ID
   */
  @TableId(value = "order_picking_id")
  private Long orderPickingId;

  /**
   * 拣货单编号
   */
  private String orderPickingCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 订单类型
   */
  private String orderType;

  /**
   * 拣货人ID
   */
  private Long userId;

  /**
   * 拣货人
   */
  private String nickName;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  /**
   * 持续时间
   */
  private String spanTime;

  /**
   * 订单数
   */
  private Long orderCount;

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
   * 打印批次ID
   */
  private Long orderWaveId;

  /**
   * 打印批次编号
   */
  private String orderWaveCode;

  /**
   * 拣货数量
   */
  private BigDecimal totalQuanityOrder;

  /**
   * 状态
   */
  private String pickingStatus;

  /**
   * 子波次号
   */
  private String subOrderWaveCode;

  /**
   * 成本金额
   */
  private BigDecimal totalPurchaseAmount;

  /**
   * 销售总额
   */
  private BigDecimal totalsaleAmount;

  /**
   * 小计毛重
   */
  private BigDecimal totalWeight;

  /**
   * 拣货类型
   */
  private String pickingType;


}
