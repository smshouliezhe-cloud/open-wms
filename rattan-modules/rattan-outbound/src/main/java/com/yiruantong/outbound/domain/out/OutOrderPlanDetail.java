package com.yiruantong.outbound.domain.out;

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
 * 出库计划单明细对象 out_order_plan_detail
 *
 * @author YiRuanTong
 * @date 2025-01-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_plan_detail", autoResultMap = true)
public class OutOrderPlanDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 出库计划单明细ID
   */
  @TableId(value = "order_plan_detail_id")
  private Long orderPlanDetailId;

  /**
   * 出库计划单ID
   */
  private Long orderPlanId;

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
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 数量
   */
  private BigDecimal quantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位换算
   */
  private String unitConvertText;

  /**
   * 成本单价
   */
  private BigDecimal purchasePrice;

  /**
   * 售价
   */
  private BigDecimal salePrice;

  /**
   * 金额
   */
  private BigDecimal saleAmount;

  /**
   * 已出库数量
   */
  private BigDecimal outQuantity;

  /**
   * 未出库数量
   */
  private BigDecimal surplusQuantity;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库编号
   */
  private String storageCode;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 有效库存
   */
  private Long validQuantity;

  /**
   * 发件人省
   */
  private String senderprov;

  /**
   * 发件人市
   */
  private String sendercity;

  /**
   * 发件人区
   */
  private String sendercounty;

  /**
   * 发件人地址
   */
  private String senderaddress;

  /**
   * 发件公司名
   */
  private String sendercompany;

  /**
   * 发件手机号
   */
  private String sendermobile;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 批次号
   */
  private String batchNumber;

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
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 货主Id
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


}
