package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseDriver;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 司机管理视图对象 base_driver
 *
 * @author YRT
 * @date 2024-06-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseDriver.class)
public class BaseDriverVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 司机ID
   */
  @ExcelProperty(value = "司机ID")
  private Long driverId;

  /**
   * 司机编号
   */
  @ExcelProperty(value = "司机编号")
  private String driverCode;

  /**
   * 司机名称
   */
  @ExcelProperty(value = "司机名称")
  private String driverName;

  /**
   * 登录名
   */
  @ExcelProperty(value = "登录名")
  private String userName;

  /**
   * 登陆密码
   */
  @ExcelProperty(value = "登陆密码")
  @JsonIgnore
  @JsonProperty
  private String userPwd;

  /**
   * 性别
   */
  @ExcelProperty(value = "性别")
  private String sex;

  /**
   * 部门
   */
  @ExcelProperty(value = "部门")
  private String deptName;

  /**
   * 年龄
   */
  @ExcelProperty(value = "年龄")
  private Long age;

  /**
   * Email
   */
  @ExcelProperty(value = "Email")
  private String email;

  /**
   * QQ
   */
  @ExcelProperty(value = "QQ")
  private String qq;

  /**
   * 微信
   */
  @ExcelProperty(value = "微信")
  private String weChat;

  /**
   * 生日
   */
  @ExcelProperty(value = "生日")
  private String birthday;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String tel;

  /**
   * 地址
   */
  @ExcelProperty(value = "地址")
  private String address;

  /**
   * 身份证
   */
  @ExcelProperty(value = "身份证")
  private String idCardCode;

  /**
   * 开户银行
   */
  @ExcelProperty(value = "开户银行")
  private String bank;

  /**
   * 账户名
   */
  @ExcelProperty(value = "账户名")
  private String bankName;

  /**
   * 银行卡号
   */
  @ExcelProperty(value = "银行卡号")
  private String bankCode;

  /**
   * 所属网点区域
   */
  @ExcelProperty(value = "所属网点区域")
  private String ownedOutletArea;

  /**
   * 司机类型
   */
  @ExcelProperty(value = "司机类型")
  private String driverType;

  /**
   * 司机等级
   */
  @ExcelProperty(value = "司机等级")
  private String driverLevel;

  /**
   * 合作关系
   */
  @ExcelProperty(value = "合作关系")
  private String partnership;

  /**
   * 运营区域
   */
  @ExcelProperty(value = "运营区域")
  private String operatingArea;

  /**
   * 驾龄
   */
  @ExcelProperty(value = "驾龄")
  private String drivingAge;

  /**
   * 运费结算周期
   */
  @ExcelProperty(value = "运费结算周期")
  private String freightCycle;

  /**
   * 结算冻结
   */
  @ExcelProperty(value = "结算冻结")
  private String settlementFreeze;

  /**
   * 项目类型偏好
   */
  @ExcelProperty(value = "项目类型偏好")
  private String projectPeference;

  /**
   * 时间点偏好
   */
  @ExcelProperty(value = "时间点偏好")
  private String timePreference;

  /**
   * 区域偏好
   */
  @ExcelProperty(value = "区域偏好")
  private String regionPreference;

  /**
   * 驾驶证号
   */
  @ExcelProperty(value = "驾驶证号")
  private String driverNo;

  /**
   * 驾驶证号
   */
  @ExcelProperty(value = "驾驶证号")
  private String driverLicenseNo;

  /**
   * 准假车型
   */
  @ExcelProperty(value = "准假车型")
  private String quasifakemodel;

  /**
   * 初次领证日期
   */
  @ExcelProperty(value = "初次领证日期")
  private Date issueDate;

  /**
   * 有效期
   */
  @ExcelProperty(value = "有效期")
  private Date termofvalidity;

  /**
   * 发证机关
   */
  @ExcelProperty(value = "发证机关")
  private String issuingauthority;

  /**
   * 驾驶证照片
   */
  @ExcelProperty(value = "驾驶证照片")
  private String driverImage;

  /**
   * 准驾车型
   */
  @ExcelProperty(value = "准驾车型")
  private String drivingModel;

  /**
   * openid
   */
  @ExcelProperty(value = "openid")
  private String openid;

  /**
   * 用户头像
   */
  @ExcelProperty(value = "用户头像")
  private String avatarUrl;

  /**
   * 承运商ID
   */
  @ExcelProperty(value = "承运商ID")
  private Long carrierId;

  /**
   * 承运商编号
   */
  @ExcelProperty(value = "承运商编号")
  private String carrierCode;

  /**
   * 承运商名称
   */
  @ExcelProperty(value = "承运商名称")
  private String carrierName;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 最后登录IP
   */
  @ExcelProperty(value = "最后登录IP")
  private String loginIp;

  /**
   * 最后登录时间
   */
  @ExcelProperty(value = "最后登录时间")
  private String loginDate;


}
