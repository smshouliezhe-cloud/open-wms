package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseDriver;
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
 * 司机管理业务对象 base_driver
 *
 * @author YRT
 * @date 2024-06-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseDriver.class, reverseConvertGenerate = false)
public class BaseDriverBo extends BaseEntity {

  /**
   * 司机ID
   */
  @NotNull(message = "司机ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long driverId;

  /**
   * 司机编号
   */
  @NotBlank(message = "司机编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverCode;

  /**
   * 司机名称
   */
  @NotBlank(message = "司机名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 登录名
   */
  @NotBlank(message = "登录名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String userName;

  /**
   * 登陆密码
   */
  @NotBlank(message = "登陆密码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String userPwd;

  /**
   * 性别
   */
  @NotBlank(message = "性别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sex;

  /**
   * 部门
   */
  @NotBlank(message = "部门不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 年龄
   */
  @NotNull(message = "年龄不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long age;

  /**
   * Email
   */
  @NotBlank(message = "Email不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * QQ
   */
  @NotBlank(message = "QQ不能为空", groups = {AddGroup.class, EditGroup.class})
  private String qq;

  /**
   * 微信
   */
  @NotBlank(message = "微信不能为空", groups = {AddGroup.class, EditGroup.class})
  private String weChat;

  /**
   * 生日
   */
  @NotBlank(message = "生日不能为空", groups = {AddGroup.class, EditGroup.class})
  private String birthday;

  /**
   * 电话
   */
  @NotBlank(message = "电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

  /**
   * 地址
   */
  @NotBlank(message = "地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;

  /**
   * 身份证
   */
  @NotBlank(message = "身份证不能为空", groups = {AddGroup.class, EditGroup.class})
  private String idCardCode;

  /**
   * 开户银行
   */
  @NotBlank(message = "开户银行不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bank;

  /**
   * 账户名
   */
  @NotBlank(message = "账户名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bankName;

  /**
   * 银行卡号
   */
  @NotBlank(message = "银行卡号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bankCode;

  /**
   * 所属网点区域
   */
  @NotBlank(message = "所属网点区域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ownedOutletArea;

  /**
   * 司机类型
   */
  @NotBlank(message = "司机类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverType;

  /**
   * 司机等级
   */
  @NotBlank(message = "司机等级不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverLevel;

  /**
   * 合作关系
   */
  @NotBlank(message = "合作关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private String partnership;

  /**
   * 运营区域
   */
  @NotBlank(message = "运营区域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String operatingArea;

  /**
   * 驾龄
   */
  @NotBlank(message = "驾龄不能为空", groups = {AddGroup.class, EditGroup.class})
  private String drivingAge;

  /**
   * 运费结算周期
   */
  @NotBlank(message = "运费结算周期不能为空", groups = {AddGroup.class, EditGroup.class})
  private String freightCycle;

  /**
   * 结算冻结
   */
  @NotBlank(message = "结算冻结不能为空", groups = {AddGroup.class, EditGroup.class})
  private String settlementFreeze;

  /**
   * 项目类型偏好
   */
  @NotBlank(message = "项目类型偏好不能为空", groups = {AddGroup.class, EditGroup.class})
  private String projectPeference;

  /**
   * 时间点偏好
   */
  @NotBlank(message = "时间点偏好不能为空", groups = {AddGroup.class, EditGroup.class})
  private String timePreference;

  /**
   * 区域偏好
   */
  @NotBlank(message = "区域偏好不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionPreference;

  /**
   * 驾驶证号
   */
  @NotBlank(message = "驾驶证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverNo;

  /**
   * 驾驶证号
   */
  @NotBlank(message = "驾驶证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverLicenseNo;

  /**
   * 准假车型
   */
  @NotBlank(message = "准假车型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String quasifakemodel;

  /**
   * 初次领证日期
   */
  @NotNull(message = "初次领证日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date issueDate;

  /**
   * 有效期
   */
  @NotNull(message = "有效期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date termofvalidity;

  /**
   * 发证机关
   */
  @NotBlank(message = "发证机关不能为空", groups = {AddGroup.class, EditGroup.class})
  private String issuingauthority;

  /**
   * 驾驶证照片
   */
  @NotBlank(message = "驾驶证照片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverImage;

  /**
   * 准驾车型
   */
  @NotBlank(message = "准驾车型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String drivingModel;

  /**
   * openid
   */
  @NotBlank(message = "openid不能为空", groups = {AddGroup.class, EditGroup.class})
  private String openid;

  /**
   * 用户头像
   */
  @NotBlank(message = "用户头像不能为空", groups = {AddGroup.class, EditGroup.class})
  private String avatarUrl;

  /**
   * 承运商ID
   */
  @NotNull(message = "承运商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long carrierId;

  /**
   * 承运商编号
   */
  @NotBlank(message = "承运商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierCode;

  /**
   * 承运商名称
   */
  @NotBlank(message = "承运商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierName;

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

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

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
   * 最后登录IP
   */
  @NotBlank(message = "最后登录IP不能为空", groups = {AddGroup.class, EditGroup.class})
  private String loginIp;

  /**
   * 最后登录时间
   */
  @NotBlank(message = "最后登录时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private String loginDate;


}
