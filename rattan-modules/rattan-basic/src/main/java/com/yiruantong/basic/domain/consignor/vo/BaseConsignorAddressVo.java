package com.yiruantong.basic.domain.consignor.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.basic.domain.consignor.BaseConsignorAddress;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 货主地址管理视图对象 base_consignor_address
 *
 * @author YiRuanTong
 * @date 2025-02-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseConsignorAddress.class)
public class BaseConsignorAddressVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 发货人id
   */
  @ExcelProperty(value = "发货人id")
  private Long addressId;

  /**
   * 地址类型
   */
  @ExcelProperty(value = "地址类型")
  private String addressType;

  /**
   * 省ID
   */
  @ExcelProperty(value = "省ID")
  private Long provinceId;

  /**
   * 省
   */
  @ExcelProperty(value = "省")
  private String provinceName;

  /**
   * 市ID
   */
  @ExcelProperty(value = "市ID")
  private Long cityId;

  /**
   * 市
   */
  @ExcelProperty(value = "市")
  private String cityName;

  /**
   * 区ID
   */
  @ExcelProperty(value = "区ID")
  private Long regionId;

  /**
   * 区
   */
  @ExcelProperty(value = "区")
  private String regionName;

  /**
   * 详细地址
   */
  @ExcelProperty(value = "详细地址")
  private String detailAddress;

  /**
   * 收货人
   */
  @ExcelProperty(value = "收货人")
  private String consignee;

  /**
   * 邮箱
   */
  @ExcelProperty(value = "邮箱")
  private String email;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 邮编
   */
  @ExcelProperty(value = "邮编")
  private String zip;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String tel;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private BigDecimal longitude;

  /**
   * 纬度
   */
  @ExcelProperty(value = "纬度")
  private BigDecimal latitude;

  /**
   * 默认地址
   */
  @ExcelProperty(value = "默认地址")
  private Long isDefaultAddress;

  /**
   * 支付方式
   */
  @ExcelProperty(value = "支付方式")
  private Long payType;

  /**
   * 配送方式
   */
  @ExcelProperty(value = "配送方式")
  private String deliveryType;

  /**
   * 收件人街道
   */
  @ExcelProperty(value = "收件人街道")
  private String street;

  /**
   * 收件人身份证
   */
  @ExcelProperty(value = "收件人身份证")
  private String consigneeIdcard;

  /**
   * 联系人属性
   */
  @ExcelProperty(value = "联系人属性")
  private String linkmanAttr;

  /**
   * 所属客户ID
   */
  @ExcelProperty(value = "所属客户ID")
  private Long consignorId;

  /**
   * 所属客户编号
   */
  @ExcelProperty(value = "所属客户编号")
  private String consignorCode;

  /**
   * 所属客户
   */
  @ExcelProperty(value = "所属客户")
  private String consignorName;

  /**
   * 提货仓库名称
   */
  @ExcelProperty(value = "提货仓库名称")
  private String pickuper;

  /**
   * 发货人电话
   */
  @ExcelProperty(value = "发货人电话")
  private String billingMobile;

  /**
   * 发货人名称
   */
  @ExcelProperty(value = "发货人名称")
  private String billingName;

  /**
   * 发货人编码
   */
  @ExcelProperty(value = "发货人编码")
  private String billingCode;

  /**
   * 发货区域
   */
  @ExcelProperty(value = "发货区域")
  private String billingArea;

  /**
   * 发货地图地址
   */
  @ExcelProperty(value = "发货地图地址")
  private String billingMapaddress;

  /**
   * 发货详细地址
   */
  @ExcelProperty(value = "发货详细地址")
  private String deliveryAddress;

  /**
   * 是否司机装车
   */
  @ExcelProperty(value = "是否司机装车")
  private Long isdriverLoading;

  /**
   * 是否有装卸平台
   */
  @ExcelProperty(value = "是否有装卸平台")
  private Long isLoadingPlatform;

  /**
   * 提货是否测温
   */
  @ExcelProperty(value = "提货是否测温")
  private Long isThermometry;

  /**
   * 账套ID
   */
  @ExcelProperty(value = "账套ID")
  private Long userProductId;

  /**
   * 地址id
   */
  @ExcelProperty(value = "地址id")
  private Long consignorAddressId;

  /**
   * 发件地址
   */
  @ExcelProperty(value = "发件地址")
  private String billingAddress;

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
  private Long enable;


}
