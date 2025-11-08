package com.yiruantong.inbound.domain.service.bo;

import com.yiruantong.inbound.domain.service.InExchangeEnterDetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 换货单入库明细业务对象 in_exchange_enter_detail
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InExchangeEnterDetail.class, reverseConvertGenerate = false)
public class InExchangeEnterDetailBo extends BaseEntity {

  /**
   * 入库明细ID
   */
  @NotNull(message = "入库明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long exchangeEnterDetailId;

  /**
   * 换货单ID
   */
  @NotNull(message = "换货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long exchangeId;

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
   * 数量
   */
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal enterQuantity;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  @NotBlank(message = "单位关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unitConvertText;

  /**
   * 单价
   */
  @NotNull(message = "单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 金额
   */
  @NotNull(message = "金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

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
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

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
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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


}
