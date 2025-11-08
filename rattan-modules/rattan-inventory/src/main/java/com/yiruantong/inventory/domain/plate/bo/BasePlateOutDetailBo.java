package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
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
 * 容器借出明细业务对象 base_plate_out_detail
 *
 * @author YRT
 * @date 2024-03-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateOutDetail.class, reverseConvertGenerate = false)
public class BasePlateOutDetailBo extends BaseEntity {

  /**
   * 容器借出明细Id
   */
  @NotNull(message = "容器借出明细Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outDetailId;

  /**
   * 容器借出Id
   */
  @NotNull(message = "容器借出Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outId;

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
   * 当前借出数量
   */
  @NotNull(message = "当前借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal currentOutQty;

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
   * 已归还数量
   */
  @NotNull(message = "已归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnedQty;

  /**
   * 未归还数量
   */
  @NotNull(message = "未归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unreturnedQty;

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


}
