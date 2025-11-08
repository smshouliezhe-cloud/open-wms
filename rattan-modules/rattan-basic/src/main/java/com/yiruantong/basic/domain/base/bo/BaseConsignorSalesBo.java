package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseConsignorSales;
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
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 门店明细业务对象 base_consignor_sales
 *
 * @author YRT
 * @date 2024-12-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignorSales.class, reverseConvertGenerate = false)
public class BaseConsignorSalesBo extends BaseEntity {

  /**
   * 门店明细ID
   */
  @NotNull(message = "门店明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorDetailId;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 店主姓名
   */
  @NotBlank(message = "店主姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ownerName;

  /**
   * 店主电话
   */
  @NotBlank(message = "店主电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ownerContact;

  /**
   * 采购员姓名
   */
  @NotBlank(message = "采购员姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String purchasementName;

  /**
   * 采购员电话
   */
  @NotBlank(message = "采购员电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String purchasementContact;

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
