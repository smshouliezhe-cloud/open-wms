package com.yiruantong.basic.domain.client;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 客户地址管理对象 base_client_address
 *
 * @author YRT
 * @date 2024-04-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_client_address", autoResultMap = true)
public class BaseClientAddress extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户地址ID
   */
  @TableId(value = "address_id")
  private Long addressId;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 名字
   */
  private String name;

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
   * 街道
   */
  private String street;

  /**
   * 地址
   */
  private String address;

  /**
   * Email
   */
  private String email;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 邮政编码
   */
  private String zip;

  /**
   * 电话
   */
  private String tel;

  /**
   * 传真
   */
  private String fax;

  /**
   * 是否默认地址
   */
  private Long isDefaultAddress;

  /**
   * 付款类别
   */
  private String payType;

  /**
   * 交付类型
   */
  private String deliveryType;

  /**
   * 经度
   */
  private BigDecimal longitude;

  /**
   * 维度
   */
  private BigDecimal latitude;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户简称
   */
  private String clientShortName;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主ID
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

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
   * 所属网点
   */
  private String siteName;

  /**
   * 网点ID
   */
  private Long siteId;

  /**
   * 线路网点
   */
  private String lineName;

  /**
   * 线路编号
   */
  private String lineCode;

  /**
   * 线路Id
   */
  private Long lineId;


}
