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
 * 订单拣货查询明细对象 out_order_picking_detail
 *
 * @author YRT
 * @date 2023-12-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_picking_detail", autoResultMap = true)
public class OutOrderPickingDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 出库明细ID
   */
  @TableId(value = "order_picking_detail_id")
  private Long orderPickingDetailId;

  /**
   * 出库单ID
   */
  private Long orderPickingId;

  /**
   * 货车编号
   */
  private String cartCode;

  /**
   * 销售单ID
   */
  private Long orderId;

  /**
   * 销售单编号
   */
  private String orderCode;

  /**
   * 销售明细ID
   */
  private Long orderDetailId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * SN码
   */
  private String singleSignCode;

  /**
   * 批次号
   */
  private String batchNumber;

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
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  private BigDecimal salePrice;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 下架货位
   */
  private String offPosition;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

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
   * 波次单ID
   */
  private Long orderWaveId;

  /**
   * 波次明细ID
   */
  private Long orderWaveDetailId;

  /**
   * 数量
   */
  private BigDecimal quantityOrder;

  /**
   * 成本金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 销售总额
   */
  private BigDecimal saleAmount;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 打印批次编号
   */
  private String subOrderWaveCode;

  /**
   * 温层
   */
  private String thermocline;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 商品品牌
   */
  private String brandName;

  /**
   * 商品类别
   */
  private String typeName;

  /**
   * 商品型号
   */
  private String productBarCode;

  /**
   * 图片
   */
  private String images;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 含税单价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 集装箱号
   */
  private String containerNo;


}
