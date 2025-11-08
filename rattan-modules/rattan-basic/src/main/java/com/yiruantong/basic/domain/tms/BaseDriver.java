package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 司机管理对象 base_driver
 *
 * @author YRT
 * @date 2024-06-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_driver", autoResultMap = true)
public class BaseDriver extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 司机ID
   */
  @TableId(value = "driver_id")
  private Long driverId;

  /**
   * 司机编号
   */
  private String driverCode;

  /**
   * 司机名称
   */
  private String driverName;

  /**
   * 登录名
   */
  private String userName;

  /**
   * 登陆密码
   */
  @TableField(
    insertStrategy = FieldStrategy.NOT_EMPTY,
    updateStrategy = FieldStrategy.NOT_EMPTY,
    whereStrategy = FieldStrategy.NOT_EMPTY
  )


  private String userPwd;

  /**
   * 性别
   */
  private String sex;

  /**
   * 部门
   */
  private String deptName;

  /**
   * 年龄
   */
  private Long age;

  /**
   * Email
   */
  private String email;

  /**
   * QQ
   */
  private String qq;

  /**
   * 微信
   */
  private String weChat;

  /**
   * 生日
   */
  private String birthday;

  /**
   * 电话
   */
  private String tel;

  /**
   * 地址
   */
  private String address;

  /**
   * 身份证
   */
  private String idCardCode;

  /**
   * 开户银行
   */
  private String bank;

  /**
   * 账户名
   */
  private String bankName;

  /**
   * 银行卡号
   */
  private String bankCode;

  /**
   * 所属网点区域
   */
  private String ownedOutletArea;

  /**
   * 司机类型
   */
  private String driverType;

  /**
   * 司机等级
   */
  private String driverLevel;

  /**
   * 合作关系
   */
  private String partnership;

  /**
   * 运营区域
   */
  private String operatingArea;

  /**
   * 驾龄
   */
  private String drivingAge;

  /**
   * 运费结算周期
   */
  private String freightCycle;

  /**
   * 结算冻结
   */
  private String settlementFreeze;

  /**
   * 项目类型偏好
   */
  private String projectPeference;

  /**
   * 时间点偏好
   */
  private String timePreference;

  /**
   * 区域偏好
   */
  private String regionPreference;

  /**
   * 驾驶证号
   */
  private String driverNo;

  /**
   * 驾驶证号
   */
  private String driverLicenseNo;

  /**
   * 准假车型
   */
  private String quasifakemodel;

  /**
   * 初次领证日期
   */
  private Date issueDate;

  /**
   * 有效期
   */
  private Date termofvalidity;

  /**
   * 发证机关
   */
  private String issuingauthority;

  /**
   * 驾驶证照片
   */
  private String driverImage;

  /**
   * 准驾车型
   */
  private String drivingModel;

  /**
   * openid
   */
  private String openid;

  /**
   * 用户头像
   */
  private String avatarUrl;

  /**
   * 承运商ID
   */
  private Long carrierId;

  /**
   * 承运商编号
   */
  private String carrierCode;

  /**
   * 承运商名称
   */
  private String carrierName;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 最后登录IP
   */
  private String loginIp;

  /**
   * 最后登录时间
   */
  private String loginDate;


}
