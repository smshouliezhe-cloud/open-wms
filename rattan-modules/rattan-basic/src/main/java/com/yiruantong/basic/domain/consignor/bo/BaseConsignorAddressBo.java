package com.yiruantong.basic.domain.consignor.bo;

import com.yiruantong.basic.domain.consignor.BaseConsignorAddress;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 货主地址管理业务对象 base_consignor_address
 *
 * @author YiRuanTong
 * @date 2025-02-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignorAddress.class, reverseConvertGenerate = false)
public class BaseConsignorAddressBo extends BaseEntity {

  /**
   * 发货人id
   */
  @NotNull(message = "发货人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long addressId;

  /**
   * 地址类型
   */
  @NotBlank(message = "地址类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String addressType;

  /**
   * 省ID
   */
  @NotNull(message = "省ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long provinceId;

  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;

  /**
   * 市ID
   */
  @NotNull(message = "市ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;

  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 区ID
   */
  @NotNull(message = "区ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long regionId;

  /**
   * 区
   */
  @NotBlank(message = "区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;

  /**
   * 详细地址
   */
  @NotBlank(message = "详细地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String detailAddress;

  /**
   * 收货人
   */
  @NotBlank(message = "收货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignee;

  /**
   * 邮箱
   */
  @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 邮编
   */
  @NotBlank(message = "邮编不能为空", groups = {AddGroup.class, EditGroup.class})
  private String zip;

  /**
   * 电话
   */
  @NotBlank(message = "电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

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
   * 默认地址
   */
  @NotNull(message = "默认地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isDefaultAddress;

  /**
   * 支付方式
   */
  @NotNull(message = "支付方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long payType;

  /**
   * 配送方式
   */
  @NotBlank(message = "配送方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deliveryType;

  /**
   * 收件人街道
   */
  @NotBlank(message = "收件人街道不能为空", groups = {AddGroup.class, EditGroup.class})
  private String street;

  /**
   * 收件人身份证
   */
  @NotBlank(message = "收件人身份证不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeIdcard;

  /**
   * 联系人属性
   */
  @NotBlank(message = "联系人属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String linkmanAttr;

  /**
   * 所属客户ID
   */
  @NotNull(message = "所属客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 所属客户编号
   */
  @NotBlank(message = "所属客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 所属客户
   */
  @NotBlank(message = "所属客户不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 提货仓库名称
   */
  @NotBlank(message = "提货仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pickuper;

  /**
   * 发货人电话
   */
  @NotBlank(message = "发货人电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingMobile;

  /**
   * 发货人名称
   */
  @NotBlank(message = "发货人名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingName;

  /**
   * 发货人编码
   */
  @NotBlank(message = "发货人编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingCode;

  /**
   * 发货区域
   */
  @NotBlank(message = "发货区域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingArea;

  /**
   * 发货地图地址
   */
  @NotBlank(message = "发货地图地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingMapaddress;

  /**
   * 发货详细地址
   */
  @NotBlank(message = "发货详细地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deliveryAddress;

  /**
   * 是否司机装车
   */
  @NotNull(message = "是否司机装车不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isdriverLoading;

  /**
   * 是否有装卸平台
   */
  @NotNull(message = "是否有装卸平台不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isLoadingPlatform;

  /**
   * 提货是否测温
   */
  @NotNull(message = "提货是否测温不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isThermometry;

  /**
   * 账套ID
   */
  @NotNull(message = "账套ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userProductId;

  /**
   * 地址id
   */
  @NotNull(message = "地址id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorAddressId;

  /**
   * 发件地址
   */
  @NotBlank(message = "发件地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingAddress;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

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
  private Long enable;


}
