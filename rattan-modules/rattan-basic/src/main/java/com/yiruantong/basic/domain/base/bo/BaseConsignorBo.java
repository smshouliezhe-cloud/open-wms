package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 货主信息业务对象 base_consignor
 *
 * @author YRT
 * @date 2025-01-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignor.class, reverseConvertGenerate = false)
public class BaseConsignorBo extends BaseEntity {

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 货主英文名称
   */
  private String consignorEnName;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
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
   * 别名
   */
  private String aliasName;

  /**
   * 最后登录IP
   */
  private String loginIp;

  /**
   * 最后登录时间
   */
  private Date loginDate;

  /**
   * 本位币
   */
  private String baseCurrency;

  /**
   * 一级客户
   */
  @NotBlank(message = "一级客户不能为空", groups = {AddGroup.class, EditGroup.class})
  private String firstCustomer;

  /**
   * 二级客户
   */
  @NotBlank(message = "二级客户不能为空", groups = {AddGroup.class, EditGroup.class})
  private String secondaryCustomer;

  /**
   * 一级客户ID
   */
  @NotNull(message = "一级客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long firstCustomerId;

  /**
   * 二级客户ID
   */
  @NotNull(message = "二级客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long secondaryCustomerId;

  /**
   * 门店等级
   */
  @NotBlank(message = "门店等级不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorGrade;

  /**
   * 门店名称
   */
  @NotBlank(message = "门店名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shopName;

  /**
   * 门店地址
   */
  @NotBlank(message = "门店地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shopAddress;

  /**
   * 门店面积
   */
  @NotBlank(message = "门店面积不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shopArea;

  /**
   * 是否有空地搞促销
   */
  @NotNull(message = "是否有空地搞促销不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isPromotion;

  /**
   * 门店流量
   */
  @NotBlank(message = "门店流量不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shopTraffic;

  /**
   * 总库存
   */
  @NotNull(message = "总库存不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shopTotalInventory;

  /**
   * 总销量
   */
  @NotNull(message = "总销量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shopTatalSales;

  /**
   * 是否重复拜访
   */
  @NotNull(message = "是否重复拜访不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isRepeatVisit;

  /**
   * 拜访时间
   */
  @NotNull(message = "拜访时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date visitDate;

  /**
   * 责任业务员
   */
  @NotBlank(message = "责任业务员不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesName;

  /**
   * 销售等级
   */
  @NotBlank(message = "销售等级不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesGrade;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;

  /**
   * 联系人
   */
  private String linker;

  /**
   * 联系地址
   */
  private String address;

  /**
   * 邮政编码
   */
  private String zip;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 电话
   */
  private String tel;

  /**
   * 传真
   */
  private String fax;

  /**
   * QQ
   */
  private String qq;

  /**
   * 开户行
   */
  private String bank;

  /**
   * 银行行号
   */
  private String bankCode;

  /**
   * 纳税号
   */
  private String raxCode;

  /**
   * 公司主页
   */
  private String corpUrl;

  /**
   * 公司游戏那个
   */
  private String email;

  /**
   * 公司法人
   */
  private String artificiaPerson;

  /**
   * Token
   */
  private String token;

  /**
   * 超收百分比
   */
  private BigDecimal overcharges;

  /**
   * 质检方案
   */
  private String qualityPlan;

  /**
   * 质检比例
   */
  private BigDecimal qualityProportion;

  /**
   * 需效期管理
   */
  private Long isNeedPeriod;

  /**
   * 保质期天数
   */
  private BigDecimal shelfLifeDay;

  /**
   * 禁收效期系数%
   */
  private BigDecimal noReceivingRate;

  /**
   * 停售提前时长（天）
   */
  private Long stopSaleday;

  /**
   * 公司所在区域
   */
  private String areaName;

  /**
   * 商家类型
   */
  private String consignorType;

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
   * 信用额度
   */
  private String creditLine;

  /**
   * 保险费率
   */
  private Long premiumRate;

  /**
   * 经度
   */
  private String lng;

  /**
   * 纬度
   */
  private String lat;

  /**
   * 运单规则
   */
  private String wayBillRule;


}
