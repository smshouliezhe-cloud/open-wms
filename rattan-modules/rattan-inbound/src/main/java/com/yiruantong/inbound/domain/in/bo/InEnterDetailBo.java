package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InEnterDetail;
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
 * 入库管理明细业务对象 in_enter_detail
 *
 * @author YiRuanTong
 * @date 2024-12-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InEnterDetail.class, reverseConvertGenerate = false)
public class InEnterDetailBo extends BaseEntity {

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
   * 预到货明细ID
   */
  @NotNull(message = "预到货明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

  /**
   * 预到货单ID
   */
  @NotNull(message = "预到货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

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
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

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
   * 入库数量
   */
  @NotNull(message = "入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal enterQuantity;

  /**
   * 上架数量
   */
  @NotNull(message = "上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shelvedQuantity;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

  /**
   * 换算关系文本
   */
  @NotBlank(message = "换算关系文本不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unitConvertText;

  /**
   * 单价
   */
  @NotNull(message = "单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 货款金额
   */
  @NotNull(message = "货款金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

  /**
   * 优惠金额
   */
  @NotNull(message = "优惠金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal favourAmount;

  /**
   * 实付金额
   */
  @NotNull(message = "实付金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal factAmount;

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
   * 未完成订货数量
   */
  @NotNull(message = "未完成订货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unfinishedQuantity;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * SN码
   */
  @NotBlank(message = "SN码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

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
   * 生成编号
   */
  @NotBlank(message = "生成编号不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 明细状态
   */
  @NotBlank(message = "明细状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String detailStatus;

  /**
   * 到期日期
   */
  @NotNull(message = "到期日期不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 小计件数
   */
  @NotNull(message = "小计件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowPackage;

  /**
   * 单位件数
   */
  @NotNull(message = "单位件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitPackage;

  /**
   * 建议拍数
   */
  @NotNull(message = "建议拍数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal advicePaiQty;

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
   * 净重重量(吨)
   */
  @NotNull(message = "净重重量(吨)不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeightTon;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sealNo;

  /**
   * 次品数量
   */
  @NotNull(message = "次品数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long defectiveQty;

  /**
   * 分拣次数
   */
  @NotNull(message = "分拣次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingCount;

  /**
   * 保质期天数
   */
  @NotNull(message = "保质期天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shelfLifeDay;

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
