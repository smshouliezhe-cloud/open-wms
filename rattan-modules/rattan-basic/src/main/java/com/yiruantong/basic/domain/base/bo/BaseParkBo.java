package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BasePark;
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
 * 物流园区业务对象 base_park
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePark.class, reverseConvertGenerate = false)
public class BaseParkBo extends BaseEntity {

  /**
   * 园区ID
   */
  @NotNull(message = "园区ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parkId;

  /**
   * 园区名称
   */
  @NotBlank(message = "园区名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String parkName;

  /**
   * 地图地址
   */
  @NotBlank(message = "地图地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mapAddress;

  /**
   * 详细地址
   */
  @NotBlank(message = "详细地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String detailAddress;

  /**
   * 经度
   */
  @NotNull(message = "经度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal longitude;

  /**
   * 纬度
   */
  @NotNull(message = "纬度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal latitude;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 百度地图
   */
  @NotBlank(message = "百度地图不能为空", groups = {AddGroup.class, EditGroup.class})
  private String baiduMap;

  /**
   * 经度
   */
  @NotBlank(message = "经度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lng;

  /**
   * 维度
   */
  @NotBlank(message = "维度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lat;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

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


}
