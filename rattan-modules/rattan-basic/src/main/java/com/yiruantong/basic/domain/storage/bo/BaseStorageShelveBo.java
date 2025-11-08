package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BaseStorageShelve;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 仓库货架业务对象 base_storage_shelve
 *
 * @author YRT
 * @date 2024-02-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseStorageShelve.class, reverseConvertGenerate = false)
public class BaseStorageShelveBo extends BaseEntity {

  /**
   * 货架信息ID
   */
  @NotNull(message = "货架信息ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageShelveId;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String areaCode;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String channelCode;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveCode;

  /**
   * 列数
   */
  @NotNull(message = "列数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long columnNum;

  /**
   * 层数
   */
  @NotNull(message = "层数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long rowNum;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionType;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String columnRegular;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionRegular;

  /**
   * 最大容量
   */
  @NotNull(message = "最大容量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long maxCapacity;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelvesRegular;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String channelRegular;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String rowRegular;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNo;

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
