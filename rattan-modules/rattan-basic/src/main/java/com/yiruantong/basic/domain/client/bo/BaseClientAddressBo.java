package com.yiruantong.basic.domain.client.bo;

import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 客户地址管理业务对象 base_client_address
 *
 * @author YRT
 * @date 2024-04-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseClientAddress.class, reverseConvertGenerate = false)
public class BaseClientAddressBo extends BaseEntity {

  /**
   * 客户地址ID
   */
  @NotNull(message = "客户地址ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long addressId;

  /**
   * 客户ID
   */
  @NotNull(message = "客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 名字
   */
  @NotBlank(message = "名字不能为空", groups = {AddGroup.class, EditGroup.class})
  private String name;

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
   * 街道
   */
  @NotBlank(message = "街道不能为空", groups = {AddGroup.class, EditGroup.class})
  private String street;

  /**
   * 地址
   */
  @NotBlank(message = "地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;

  /**
   * Email
   */
  @NotBlank(message = "Email不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 邮政编码
   */
  @NotBlank(message = "邮政编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String zip;

  /**
   * 电话
   */
  @NotBlank(message = "电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

  /**
   * 传真
   */
  @NotBlank(message = "传真不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fax;

  /**
   * 是否默认地址
   */
  @NotNull(message = "是否默认地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isDefaultAddress;

  /**
   * 付款类别
   */
  @NotBlank(message = "付款类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String payType;

  /**
   * 交付类型
   */
  @NotBlank(message = "交付类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deliveryType;

  /**
   * 经度
   */
  @NotNull(message = "经度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal longitude;

  /**
   * 维度
   */
  @NotNull(message = "维度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal latitude;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户简称
   */
  @NotBlank(message = "客户简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主ID
   */
  @NotBlank(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

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
   * 所属网点
   */
  @NotBlank(message = "所属网点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String siteName;

  /**
   * 网点ID
   */
  @NotNull(message = "网点ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long siteId;

  /**
   * 线路网点
   */
  @NotBlank(message = "线路网点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineName;

  /**
   * 线路编号
   */
  @NotBlank(message = "线路编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineCode;

  /**
   * 线路Id
   */
  @NotNull(message = "线路Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long lineId;


}
