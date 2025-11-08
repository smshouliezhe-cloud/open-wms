package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseAccessControl;
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
 * 门禁信息业务对象 base_access_control
 *
 * @author YRT
 * @date 2024-12-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseAccessControl.class, reverseConvertGenerate = false)
public class BaseAccessControlBo extends BaseEntity {

  /**
   * 门禁信息id
   */
  @NotNull(message = "门禁信息id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long accessControlId;

  /**
   * 记录单号
   */
  @NotBlank(message = "记录单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String recordNo;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carPlateNo;

  /**
   * 物料类型
   */
  @NotBlank(message = "物料类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String materialType;

  /**
   * 入场时间
   */
  @NotNull(message = "入场时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date enterTime;

  /**
   * 进场通道名称
   */
  @NotBlank(message = "进场通道名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String channelName;

  /**
   * 岗亭名称
   */
  @NotBlank(message = "岗亭名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientComputerName;

  /**
   * 通行证类
   */
  @NotBlank(message = "通行证类不能为空", groups = {AddGroup.class, EditGroup.class})
  private String passportTypeName;

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
   * 最后登录IP
   */
  @NotBlank(message = "最后登录IP不能为空", groups = {AddGroup.class, EditGroup.class})
  private String loginIp;

  /**
   * 最后登录时间
   */
  @NotNull(message = "最后登录时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date loginDate;


}
