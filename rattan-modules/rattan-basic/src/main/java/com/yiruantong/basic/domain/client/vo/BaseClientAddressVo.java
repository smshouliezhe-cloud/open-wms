package com.yiruantong.basic.domain.client.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.client.BaseClientAddress;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 客户地址管理视图对象 base_client_address
 *
 * @author YRT
 * @date 2024-04-09
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseClientAddress.class)
public class BaseClientAddressVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户地址ID
   */
  @ExcelProperty(value = "客户地址ID")
  private Long addressId;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
  private Long clientId;

  /**
   * 名字
   */
  @ExcelProperty(value = "名字")
  private String name;

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
   * 街道
   */
  @ExcelProperty(value = "街道")
  private String street;

  /**
   * 地址
   */
  @ExcelProperty(value = "地址")
  private String address;

  /**
   * Email
   */
  @ExcelProperty(value = "Email")
  private String email;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 邮政编码
   */
  @ExcelProperty(value = "邮政编码")
  private String zip;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String tel;

  /**
   * 传真
   */
  @ExcelProperty(value = "传真")
  private String fax;

  /**
   * 是否默认地址
   */
  @ExcelProperty(value = "是否默认地址")
  private Long isDefaultAddress;

  /**
   * 付款类别
   */
  @ExcelProperty(value = "付款类别")
  private String payType;

  /**
   * 交付类型
   */
  @ExcelProperty(value = "交付类型")
  private String deliveryType;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private BigDecimal longitude;

  /**
   * 维度
   */
  @ExcelProperty(value = "维度")
  private BigDecimal latitude;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户简称
   */
  @ExcelProperty(value = "客户简称")
  private String clientShortName;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

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
   * 所属网点
   */
  @ExcelProperty(value = "所属网点")
  private String siteName;

  /**
   * 网点ID
   */
  @ExcelProperty(value = "网点ID")
  private Long siteId;

  /**
   * 线路网点
   */
  @ExcelProperty(value = "线路网点")
  private String lineName;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 线路Id
   */
  @ExcelProperty(value = "线路Id")
  private Long lineId;


}
