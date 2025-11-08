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
 * 出库单波次明细对象 out_order_wave_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_wave_detail", autoResultMap = true)
public class OutOrderWaveDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 波次明细ID
   */
  @TableId(value = "order_wave_detail_id")
  private Long orderWaveDetailId;

  /**
   * 波次单ID
   */
  private Long orderWaveId;

  /**
   * 分拣占位ID
   */
  private Long holderId;

  /**
   * 配货位
   */
  private String allotPositionName;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 订单ID
   */
  private Long orderId;

  /**
   * 订单明细编号
   */
  private Long orderDetailId;

  /**
   * 订单编号
   */
  private String orderCode;

  /**
   * 店铺订单编号
   */
  private String storeOrderCode;

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
   * 商品规格
   */
  private String productSpec;

  /**
   * 订单数量
   */
  private BigDecimal quantityOrder;

  /**
   * 打包出库数量
   */
  private BigDecimal quantityOuted;

  /**
   * 配货数量
   */
  private BigDecimal matchedQuantity;

  /**
   * 冻结数量
   */
  private BigDecimal freezeQuantity;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 快递ID
   */
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  private String expressCorpName;

  /**
   * 快递公司编号
   */
  private String expressCode;

  /**
   * 拣货数量
   */
  private BigDecimal pickQuantity;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * 整拣单
   */
  private Byte isFullContainerLoad;

  /**
   * 原始订单数量
   */
  private BigDecimal quantityOrderOrigin;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 货位类型
   */
  private Byte positionType;

  /**
   * 子波次单号
   */
  private String subOrderWaveCode;

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
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 线路Id
   */
  private Long lineId;

  /**
   * 线路编号
   */
  private String lineCode;

  /**
   * 线路名称
   */
  private String lineName;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 已发货数量
   */
  private BigDecimal quantityShipped;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
