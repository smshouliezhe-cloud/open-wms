package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InShelveDetailStep;
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
 * 商品上架明细的明细业务对象 in_shelve_detail_step
 *
 * @author YiRuanTong
 * @date 2025-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InShelveDetailStep.class, reverseConvertGenerate = false)
public class InShelveDetailStepBo extends BaseEntity {

  /**
   * 上架明细ID
   */
  @NotNull(message = "上架明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long shelveDetailStepId;

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
   * 扫描数量
   */
  @NotNull(message = "扫描数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantity;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 上架状态
   */
  @NotBlank(message = "上架状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveStatus;

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
   * SN码
   */
  @NotBlank(message = "SN码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

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


}
