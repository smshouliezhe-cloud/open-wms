package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InShelveDetail;
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
 * 商品上架明细业务对象 in_shelve_detail
 *
 * @author YiRuanTong
 * @date 2024-12-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InShelveDetail.class, reverseConvertGenerate = false)
public class InShelveDetailBo extends BaseEntity {

  /**
   * 明细ID
   */
  @NotNull(message = "明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long shelveDetailId;

  /**
   * 上架ID
   */
  @NotNull(message = "上架ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long shelveId;

  /**
   * 入库单明细ID
   */
  @NotNull(message = "入库单明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enterDetailId;

  /**
   * 入库单ID
   */
  @NotNull(message = "入库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enterId;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 商品规格
   */
  @NotBlank(message = "商品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 数量
   */
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantity;

  /**
   * 待上架数量
   */
  @NotNull(message = "待上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal onShelveQuantity;

  /**
   * 已上架数量
   */
  @NotNull(message = "已上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shelvedQuantity;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 是否高价
   */
  @NotNull(message = "是否高价不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isHighValue;

  /**
   * 缺货数量
   */
  @NotNull(message = "缺货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long lackStorage;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingStatus;

  /**
   * SN码
   */
  @NotBlank(message = "SN码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 上架号
   */
  @NotBlank(message = "上架号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionNo;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 生成日期
   */
  @NotNull(message = "生成日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号 不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 关联码
   */
  @NotBlank(message = "关联码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String relationCode;

  /**
   * 申报单号
   */
  @NotBlank(message = "申报单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String declareNo;

  /**
   * 上架状态
   */
  @NotBlank(message = "上架状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveStatus;

  /**
   * 日期期限
   */
  @NotNull(message = "日期期限不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date limitDate;

  /**
   * 单位毛重
   */
  @NotNull(message = "单位毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @NotNull(message = "小计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 税价
   */
  @NotNull(message = "税价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @NotNull(message = "价税合计不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rateAmount;

  /**
   * 成本单价
   */
  @NotNull(message = "成本单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 成本金额
   */
  @NotNull(message = "成本金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

  /**
   * 小单位
   */
  @NotBlank(message = "小单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String smallUnit;

  /**
   * 大单位
   */
  @NotBlank(message = "大单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigUnit;

  /**
   * 保质期
   */
  @NotNull(message = "保质期不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shelfLifeDay;

  /**
   * 原产地
   */
  @NotBlank(message = "原产地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String originPlace;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 单位净重
   */
  @NotNull(message = "单位净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @NotNull(message = "小计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotBlank(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @NotBlank(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailId;

  /**
   * 预到货单ID
   */
  @NotNull(message = "预到货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 预到货明细ID
   */
  @NotNull(message = "预到货明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQty;

  /**
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 类别编号
   */
  @NotNull(message = "类别编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long typeId;

  /**
   * 类别名称
   */
  @NotBlank(message = "类别名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String typeName;

  /**
   * 产品型号
   */
  @NotBlank(message = "产品型号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productBarCode;

  /**
   * 品牌ID
   */
  @NotNull(message = "品牌ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long brandId;

  /**
   * 品牌名
   */
  @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;

  /**
   * 温层
   */
  @NotBlank(message = "温层不能为空", groups = {AddGroup.class, EditGroup.class})
  private String thermocLine;

  /**
   * 合计重量(吨)
   */
  private BigDecimal rowWeightTon;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sealNo;

  /**
   * 项目号
   */
  @NotBlank(message = "项目号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String projectCode;

  /**
   * 箱号
   */
  @NotBlank(message = "箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String caseNumber;

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
