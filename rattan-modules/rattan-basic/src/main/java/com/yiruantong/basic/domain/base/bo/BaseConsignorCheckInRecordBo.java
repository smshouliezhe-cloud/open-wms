package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;
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
 * 门店打卡记录业务对象 base_consignor_check_in_record
 *
 * @author YRT
 * @date 2025-01-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignorCheckInRecord.class, reverseConvertGenerate = false)
public class BaseConsignorCheckInRecordBo extends BaseEntity {

  /**
   * 打卡记录id
   */
  @NotNull(message = "打卡记录id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long checkInRecordId;

  /**
   * 责任业务员
   */
  @NotBlank(message = "责任业务员不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesName;

  /**
   * 打卡地址
   */
  @NotBlank(message = "打卡地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String punchInAddress;

  /**
   * 经度
   */
  @NotBlank(message = "经度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lng;

  /**
   * 纬度
   */
  @NotBlank(message = "纬度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lat;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;

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
   * 拜访时间
   */
  @NotNull(message = "拜访时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date visitDate;

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
