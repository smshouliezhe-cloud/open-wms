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
 * 订单配货对象 out_order_matching
 *
 * @author YRT
 * @date 2023-12-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_matching", autoResultMap = true)
public class OutOrderMatching extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 配货单ID
   */
  @TableId(value = "matching_id")
  private Long matchingId;

  /**
   * 配货单编号
   */
  private String matchingCode;

  /**
   * 波次单ID
   */
  private Long orderWaveId;

  /**
   * 波次单号
   */
  private String orderWaveCode;

  /**
   * 单据类型
   */
  private String orderType;

  /**
   * 配货人ID
   */
  private Long userId;

  /**
   * 配货人
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
   * 拣货数量
   */
  private BigDecimal totalQuantityOrder;

  /**
   * 配货数量
   */
  private BigDecimal totalMatchQuantity;

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
   * 配货状态
   */
  private String matchStatus;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;


}
