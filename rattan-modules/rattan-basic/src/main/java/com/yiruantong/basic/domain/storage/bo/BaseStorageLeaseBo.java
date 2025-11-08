package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BaseStorageLease;
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
 * 租赁管理业务对象 base_storage_lease
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseStorageLease.class, reverseConvertGenerate = false)
public class BaseStorageLeaseBo extends BaseEntity {

  /**
   * 租赁ID
   */
  @NotNull(message = "租赁ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageLeaseId;

  /**
   * 租赁编号
   */
  @NotBlank(message = "租赁编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String leaseCode;

  /**
   * 租赁名称
   */
  @NotBlank(message = "租赁名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String leaseName;

  /**
   * 租赁类别
   */
  @NotBlank(message = "租赁类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String leaseType;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 所属仓库
   */
  @NotBlank(message = "所属仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 所属货架
   */
  @NotBlank(message = "所属货架不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 所属货位
   */
  @NotBlank(message = "所属货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelves;

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
   * 所属客户
   */
  @NotBlank(message = "所属客户不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 结算状态
   */
  @NotBlank(message = "结算状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String payState;

  /**
   * 是否作废
   */
  @NotNull(message = "是否作废不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isInvalid;

  /**
   * 租赁开始日期
   */
  @NotNull(message = "租赁开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date startDate;

  /**
   * 租赁截止日期
   */
  @NotNull(message = "租赁截止日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endDate;

  /**
   * 租金单价
   */
  @NotNull(message = "租金单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitPrice;

  /**
   * 租金总计(元)
   */
  @NotNull(message = "租金总计(元)不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPrice;

  /**
   * 已付租金
   */
  @NotNull(message = "已付租金不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal paidPrice;

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


}
