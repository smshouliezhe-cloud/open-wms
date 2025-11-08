package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 预到货单明细业务对象 in_order_detail
 *
 * @author YRT
 * @date 2024-12-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InOrderDetail.class, reverseConvertGenerate = false)
public class InOrderDetailBo extends BaseEntity {

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
   * 参考URL
   */
  private String url;

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
   * 小计金额
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
   * 已收货数量
   */
  private BigDecimal enterQuantity;

  /**
   * 退货数量
   */
  private BigDecimal returnQuantity;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 预计到货日期
   */
  private Date deliveryDate;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 厂家拍号
   */
  private String plateCode;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * 保质期
   */
  private BigDecimal shelfLifeDay;

  /**
   * 超收百分比
   */
  private BigDecimal overcharges;

  /**
   * 禁收日期
   */
  private Date noReceivingDate;

  /**
   * 到期日期
   */
  private Date limitDate;

  /**
   * 市场采购价
   */
  private BigDecimal marketPrice;

  /**
   * 折扣率
   */
  private BigDecimal discountRate;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 总件数
   */
  private BigDecimal rowPackage;

  /**
   * 建议拍数
   */
  private BigDecimal paiQty;

  /**
   * 打包配置
   */
  private BigDecimal unitPackage;

  /**
   * 图片
   */
  private String productImage;

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
   * 已上架数量
   */
  private BigDecimal shelvedQuantity;

  /**
   * 收货位
   */
  private String positionName;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商名称
   */
  private String providerShortName;

  /**
   * 次品数量
   */
  private BigDecimal defectiveQty;

  /**
   * 计划数量
   */
  private BigDecimal planQty;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 类别编号
   */
  private Long typeId;

  /**
   * 类别名称
   */
  private String typeName;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 品牌ID
   */
  private Long brandId;

  /**
   * 品牌名
   */
  private String brandName;

  /**
   * 合计重量（吨）
   */
  private BigDecimal rowWeightTon;

  /**
   * 图片
   */
  private String images;

  /**
   * 温层
   */
  private String thermocLine;

  /**
   * 商品属性
   */
  private String productAttribute;

  /**
   * 入库日期
   */
  private Date inStorageDate;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 税额
   */
  private BigDecimal taxAmount;

  /**
   * 铅封号
   */
  private String sealNo;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 皮重
   */
  @NotNull(message = "皮重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowTareWeight;

  /**
   * 扣重
   */
  @NotNull(message = "扣重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowDeductWeight;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailCode;

  /**
   * 来源单号2
   */
  @NotBlank(message = "来源单号2不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailCode2;

  /**
   * 是否质检
   */
  @NotNull(message = "是否质检不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isChecking;

  /**
   * 包数
   */
  @NotNull(message = "包数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parcelQuantity;

  /**
   * 均重
   */
  @NotNull(message = "均重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal parcelAverageWeight;


}
