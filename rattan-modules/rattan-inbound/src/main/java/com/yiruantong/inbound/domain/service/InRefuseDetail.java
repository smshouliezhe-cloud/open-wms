package com.yiruantong.inbound.domain.service;

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
 * 拒收单明细对象 in_refuse_detail
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_refuse_detail", autoResultMap = true)
public class InRefuseDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 拒收单明细ID
   */
  @TableId(value = "refuse_detail_id")
  private Long refuseDetailId;

  /**
   * 拒收单ID
   */
  private Long refuseId;

  /**
   * 采购单明细ID
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
   * 商品规格
   */
  private String productSpec;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 明细状态
   */
  private String refuseStatus;

  /**
   * 拒收数量
   */
  private BigDecimal refuseQuantity;

  /**
   * 上架数量
   */
  private BigDecimal shelveQuantity;

  /**
   * 单价
   */
  private BigDecimal purchasePrice;

  /**
   * 货款金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 到期日期
   */
  private Date limitDate;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * SN码
   */
  private String singleSignCode;

  /**
   * 总件数
   */
  private BigDecimal packageQty;

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


}
