package com.yiruantong.inbound.domain.in.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 质检管理明细业务对象 in_quality_check_detail
 *
 * @author YiRuanTong
 * @date 2023-10-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InQualityCheckDetail.class, reverseConvertGenerate = false)
public class InQualityCheckDetailBo extends BaseEntity {

  /**
   * 质检明细ID
   */
  @NotNull(message = "质检明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long qualityCheckDetailId;

  /**
   * 质检ID
   */
  @NotNull(message = "质检ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long qualityCheckId;

  /**
   * 采购明细ID
   */
  @NotNull(message = "采购明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  @NotNull(message = "采购单ID不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 质检类型
   */
  @NotBlank(message = "质检类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String checkType;

  /**
   * 质检数量
   */
  @NotNull(message = "质检数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkQuantity;

  /**
   * 次品数量
   */
  @NotNull(message = "次品数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal defectiveQuantity;

  /**
   * 合格率
   */
  @NotNull(message = "合格率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal qualifiedRate;

  /**
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date productionDate;

  /**
   * 保质期天数
   */
  @NotNull(message = "保质期天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long shelfLifeDay;

  /**
   * 次品原因
   */
  @NotBlank(message = "次品原因不能为空", groups = {AddGroup.class, EditGroup.class})
  private String defectiveRemark;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
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
