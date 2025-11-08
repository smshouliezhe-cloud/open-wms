package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BaseStoragePlate;
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
 * 仓库容器查询业务对象 base_storage_plate
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseStoragePlate.class, reverseConvertGenerate = false)
public class BaseStoragePlateBo extends BaseEntity {

  /**
   * 主键字段id
   */
  @NotNull(message = "主键字段id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storagePlateId;

  /**
   * 执行单id
   */
  @NotNull(message = "执行单id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 执行单号
   */
  @NotBlank(message = "执行单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 仓库id
   */
  @NotNull(message = "仓库id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 客户id
   */
  @NotNull(message = "客户id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户简称
   */
  @NotBlank(message = "客户简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 销售区域id
   */
  @NotNull(message = "销售区域id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorIdSale;

  /**
   * 销售区域编号
   */
  @NotBlank(message = "销售区域编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCodeSale;

  /**
   * 销售区域名称
   */
  @NotBlank(message = "销售区域名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorNameSale;

  /**
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

  /**
   * 容器类别
   */
  @NotBlank(message = "容器类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 来源类型
   */
  @NotBlank(message = "来源类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 入库数量
   */
  @NotNull(message = "入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal enterQuantity;

  /**
   * 返厂数量
   */
  @NotNull(message = "返厂数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnFactoryQty;

  /**
   * 未返厂数量
   */
  @NotNull(message = "未返厂数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unReturnFactoryQty;

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
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;

  /**
   * 容器属性
   */
  @NotBlank(message = "容器属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateAttribute;


}
