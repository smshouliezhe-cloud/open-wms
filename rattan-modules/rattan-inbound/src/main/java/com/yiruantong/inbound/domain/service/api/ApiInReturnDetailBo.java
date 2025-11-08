package com.yiruantong.inbound.domain.service.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 退货管理明细单业务对象 in_return_detail
 *
 * @author YiRuanTong
 * @date 2024-11-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiInReturnDetailBo {

  /**
   * 退货明细ID
   */
  private Long returnDetailId;

  /**
   * 退货ID
   */
  private Long returnId;

  /**
   * 预到货明细ID
   */
  private Long orderDetailId;

  /**
   * 预到货单ID
   */
  private Long orderId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
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
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 单价
   */
  private BigDecimal purchasePrice;

  /**
   * 金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 缺货数量
   */
  private Long lackStorage;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 退货数量
   */
  private BigDecimal returnQuantity;

  /**
   * 已出库数量
   */
  private BigDecimal outQuantity;

  /**
   * 退货额
   */
  private BigDecimal amountRefunded;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
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
   * 温层
   */
  private String thermocline;

  /**
   * 单位体积
   */
  private String unitCube;

  /**
   * 小计体积
   */
  private String rowCube;

  /**
   * 大单位数量
   */
  private String bigQty;

  /**
   * 品牌
   */
  private String brandName;

  /**
   * 类别
   */
  private String typeName;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 图片
   */
  private String images;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;


}
