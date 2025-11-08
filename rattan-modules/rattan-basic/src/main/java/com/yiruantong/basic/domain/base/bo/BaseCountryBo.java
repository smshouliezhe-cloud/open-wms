package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseCountry;
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
 * 国家信息业务对象 base_country
 *
 * @author YRT
 * @date 2024-06-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseCountry.class, reverseConvertGenerate = false)
public class BaseCountryBo extends BaseEntity {

  /**
   * 国家ID
   */
  @NotNull(message = "国家ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long countryId;

  /**
   * ISO二字代码
   */
  @NotBlank(message = "ISO二字代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String iso2Code;

  /**
   * ISO三字代码
   */
  @NotBlank(message = "ISO三字代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String iso3Code;

  /**
   * 数字代码
   */
  @NotBlank(message = "数字代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String digitalCode;

  /**
   * 国家英文名
   */
  @NotBlank(message = "国家英文名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryName;

  /**
   * 国家中文名
   */
  @NotBlank(message = "国家中文名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryNameCn;

  /**
   * 区域代码
   */
  @NotBlank(message = "区域代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryRegionCode;

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
