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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 到货加工明细对象 in_arrival_process_detail
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_arrival_process_detail", autoResultMap = true)
public class InArrivalProcessDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 加工明细ID
   */
  @TableId(value = "process_detail_id")
  private Long processDetailId;

  /**
   * 加工ID
   */
  private Long processId;

  /**
   * 采购明细ID
   */
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  private Long orderId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 数量
   */
  private BigDecimal quantity;

  /**
   * 过程类型
   */
  private String processType;

  /**
   * 清关数量
   */
  private BigDecimal clearanceQauntity;

  /**
   * 车牌编号
   */
  private String plateCode;

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
