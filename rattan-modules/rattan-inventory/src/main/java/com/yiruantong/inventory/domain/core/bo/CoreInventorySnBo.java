package com.yiruantong.inventory.domain.core.bo;

import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 库存明细SN业务对象 core_inventory_sn
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoreInventorySn.class, reverseConvertGenerate = false)
public class CoreInventorySnBo extends BaseEntity {

  /**
   * SN Id
   */
  @NotNull(message = "SN Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long snId;

  /**
   * 库存ID
   */
  @NotNull(message = "库存ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

  /**
   * SN号
   */
  @NotBlank(message = "SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String snNo;

  /**
   * 出库类型
   */
  @NotBlank(message = "出库类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outType;

  /**
   * 出库单ID
   */
  @NotNull(message = "出库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outBillId;

  /**
   * 出库编码
   */
  @NotBlank(message = "出库编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outBillCode;

  /**
   * 出库时间
   */
  @NotNull(message = "出库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date outDate;

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
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotNull(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long mainId;

  /**
   * 来源明细ID
   */
  @NotNull(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long detailId;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 默认货位
   */
  @NotBlank(message = "默认货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

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
   * 入库时间
   */
  @NotNull(message = "入库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date inStorageDate;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 供应商ID
   */
  @NotNull(message = "供应商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 供应商编号
   */
  @NotBlank(message = "供应商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 供应商简称
   */
  @NotBlank(message = "供应商简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;


}
