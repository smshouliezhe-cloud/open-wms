package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseDriverContract;
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
 * 司机合同管理业务对象 base_driver_contract
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseDriverContract.class, reverseConvertGenerate = false)
public class BaseDriverContractBo extends BaseEntity {

  /**
   * 合同ID
   */
  @NotNull(message = "合同ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long contractId;

  /**
   * 合同编号
   */
  @NotBlank(message = "合同编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractCode;

  /**
   * 真实姓名
   */
  @NotBlank(message = "真实姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 身份证号
   */
  @NotBlank(message = "身份证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String idCardCode;

  /**
   * 手机号
   */
  @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 现住地址
   */
  @NotBlank(message = "现住地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String homeAddDress;

  /**
   * 车型
   */
  @NotBlank(message = "车型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleType;

  /**
   * 车辆品牌
   */
  @NotBlank(message = "车辆品牌不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleBrand;

  /**
   * 车辆识别号
   */
  @NotBlank(message = "车辆识别号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleSignNo;

  /**
   * 银行
   */
  @NotBlank(message = "银行不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bank;

  /**
   * 开户行
   */
  @NotBlank(message = "开户行不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bankName;

  /**
   * 开户账号
   */
  @NotBlank(message = "开户账号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bankCode;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 签署状态
   */
  @NotBlank(message = "签署状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String signingStatus;

  /**
   * 合同签署日期
   */
  @NotNull(message = "合同签署日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date signingDate;

  /**
   * 现住城市
   */
  @NotBlank(message = "现住城市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String livingCity;

  /**
   * 开户支行
   */
  @NotBlank(message = "开户支行不能为空", groups = {AddGroup.class, EditGroup.class})
  private String accountBranch;

  /**
   * 自定义号
   */
  @NotBlank(message = "自定义号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String customNo;

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
