package com.yiruantong.basic.domain.consignor;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 货主地址管理对象 base_consignor_address
 *
 * @author YiRuanTong
 * @date 2025-02-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor_address", autoResultMap = true)
public class BaseConsignorAddress extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 发货人id
   */
  @TableId(value = "address_id")
  private Long addressId;

  /**
   * 地址类型
   */
  private String addressType;

  /**
   * 省ID
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市ID
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区ID
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

  /**
   * 详细地址
   */
  private String detailAddress;

  /**
   * 收货人
   */
  private String consignee;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 邮编
   */
  private String zip;

  /**
   * 电话
   */
  private String tel;

  /**
   * 经度
   */
  private BigDecimal longitude;

  /**
   * 纬度
   */
  private BigDecimal latitude;

  /**
   * 默认地址
   */
  private Long isDefaultAddress;

  /**
   * 支付方式
   */
  private Long payType;

  /**
   * 配送方式
   */
  private String deliveryType;

  /**
   * 收件人街道
   */
  private String street;

  /**
   * 收件人身份证
   */
  private String consigneeIdcard;

  /**
   * 联系人属性
   */
  private String linkmanAttr;

  /**
   * 所属客户ID
   */
  private Long consignorId;

  /**
   * 所属客户编号
   */
  private String consignorCode;

  /**
   * 所属客户
   */
  private String consignorName;

  /**
   * 提货仓库名称
   */
  private String pickuper;

  /**
   * 发货人电话
   */
  private String billingMobile;

  /**
   * 发货人名称
   */
  private String billingName;

  /**
   * 发货人编码
   */
  private String billingCode;

  /**
   * 发货区域
   */
  private String billingArea;

  /**
   * 发货地图地址
   */
  private String billingMapaddress;

  /**
   * 发货详细地址
   */
  private String deliveryAddress;

  /**
   * 是否司机装车
   */
  private Long isdriverLoading;

  /**
   * 是否有装卸平台
   */
  private Long isLoadingPlatform;

  /**
   * 提货是否测温
   */
  private Long isThermometry;

  /**
   * 账套ID
   */
  private Long userProductId;

  /**
   * 地址id
   */
  private Long consignorAddressId;

  /**
   * 发件地址
   */
  private String billingAddress;

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
  private Long enable;


}
