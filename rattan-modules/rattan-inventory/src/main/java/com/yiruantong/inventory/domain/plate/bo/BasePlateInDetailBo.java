package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateInDetail;
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
 * 容器归还明细业务对象 base_plate_in_detail
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateInDetail.class, reverseConvertGenerate = false)
public class BasePlateInDetailBo extends BaseEntity {

  /**
   * 容器归还明细Id
   */
  @NotNull(message = "容器归还明细Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inDetailId;

  /**
   * 容器归还Id
   */
  @NotNull(message = "容器归还Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inId;

  /**
   * 类型
   */
  @NotBlank(message = "类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 现借出数量
   */
  @NotNull(message = "现借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal nowOutQty;

  /**
   * 归还数量
   */
  @NotNull(message = "归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnQty;

  /**
   * 剩余借出数量
   */
  @NotNull(message = "剩余借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal surplusOutQty;

  /**
   * SN
   */
  @NotBlank(message = "SN不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

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
   * 容器id
   */
  @NotNull(message = "容器id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateId;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;

  /**
   * 容器编号
   */
  @NotBlank(message = "容器编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

  /**
   * 小计运费
   */
  @NotNull(message = "小计运费不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal subFreight;

  /**
   * 单位重量
   */
  @NotNull(message = "单位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 小计重量
   */
  @NotNull(message = "小计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 运费单价
   */
  @NotNull(message = "运费单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal price;

  /**
   * 来源主表id
   */
  @NotNull(message = "来源主表id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceMainId;

  /**
   * 来源明细id
   */
  @NotNull(message = "来源明细id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceDetailId;

  /**
   * 容器属性
   */
  @NotBlank(message = "容器属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateAttribute;


}
