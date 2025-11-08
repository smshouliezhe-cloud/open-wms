package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseCity;
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
 * 省市区管理业务对象 base_city
 *
 * @author YRT
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseCity.class, reverseConvertGenerate = false)
public class BaseCityBo extends BaseEntity {

  /**
   * 地区ID
   */
  @NotNull(message = "地区ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;

  /**
   * 地区名称
   */
  @NotBlank(message = "地区名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 所属仓库ID
   */
  @NotNull(message = "所属仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 所属仓库
   */
  @NotBlank(message = "所属仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 货到付款
   */
  @NotNull(message = "货到付款不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isPayAfter;

  /**
   * 发货时效
   */
  @NotNull(message = "发货时效不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sendDay;

  /**
   * 层级ID
   */
  @NotNull(message = "层级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long stepId;

  /**
   * 根ID
   */
  @NotNull(message = "根ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long rootId;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

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
