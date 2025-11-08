package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BasePlatform;
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
 * 月台管理业务对象 base_platform
 *
 * @author YRT
 * @date 2024-05-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlatform.class, reverseConvertGenerate = false)
public class BasePlatformBo extends BaseEntity {

  /**
   * 月台ID
   */
  @NotNull(message = "月台ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long platformId;

  /**
   * 月台编号
   */
  @NotBlank(message = "月台编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String platformCode;

  /**
   * 月台名称
   */
  @NotBlank(message = "月台名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String platformName;

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
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 网点名称
   */
  @NotBlank(message = "网点名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String siteName;

  /**
   * 月台状态
   */
  @NotBlank(message = "月台状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String platStatus;

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
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 开始时间
   */
  @NotNull(message = "开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date startTime;

  /**
   * 结束时间
   */
  @NotNull(message = "结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endTime;

  /**
   * 月台使用类型
   */
  @NotBlank(message = "月台使用类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String platformType;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;


}
