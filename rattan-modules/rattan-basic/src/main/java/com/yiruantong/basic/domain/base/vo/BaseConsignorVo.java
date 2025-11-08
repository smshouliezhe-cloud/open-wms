package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 货主信息视图对象 base_consignor
 *
 * @author YRT
 * @date 2025-01-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseConsignor.class)
public class BaseConsignorVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 货主英文名称
   */
  @ExcelProperty(value = "货主英文名称")
  private String consignorEnName;

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
   * 别名
   */
  @ExcelProperty(value = "别名")
  private String aliasName;

  /**
   * 最后登录IP
   */
  @ExcelProperty(value = "最后登录IP")
  private String loginIp;

  /**
   * 最后登录时间
   */
  @ExcelProperty(value = "最后登录时间")
  private Date loginDate;

  /**
   * 本位币
   */
  @ExcelProperty(value = "本位币")
  private String baseCurrency;

  /**
   * 一级客户
   */
  @ExcelProperty(value = "一级客户")
  private String firstCustomer;

  /**
   * 二级客户
   */
  @ExcelProperty(value = "二级客户")
  private String secondaryCustomer;

  /**
   * 一级客户ID
   */
  @ExcelProperty(value = "一级客户ID")
  private Long firstCustomerId;

  /**
   * 二级客户ID
   */
  @ExcelProperty(value = "二级客户ID")
  private Long secondaryCustomerId;

  /**
   * 门店等级
   */
  @ExcelProperty(value = "门店等级")
  private String consignorGrade;

  /**
   * 门店名称
   */
  @ExcelProperty(value = "门店名称")
  private String shopName;

  /**
   * 门店地址
   */
  @ExcelProperty(value = "门店地址")
  private String shopAddress;

  /**
   * 门店面积
   */
  @ExcelProperty(value = "门店面积")
  private String shopArea;

  /**
   * 是否有空地搞促销
   */
  @ExcelProperty(value = "是否有空地搞促销")
  private Byte isPromotion;

  /**
   * 门店流量
   */
  @ExcelProperty(value = "门店流量")
  private String shopTraffic;

  /**
   * 总库存
   */
  @ExcelProperty(value = "总库存")
  private BigDecimal shopTotalInventory;

  /**
   * 总销量
   */
  @ExcelProperty(value = "总销量")
  private BigDecimal shopTatalSales;

  /**
   * 是否重复拜访
   */
  @ExcelProperty(value = "是否重复拜访")
  private Byte isRepeatVisit;

  /**
   * 拜访时间
   */
  @ExcelProperty(value = "拜访时间")
  private Date visitDate;

  /**
   * 责任业务员
   */
  @ExcelProperty(value = "责任业务员")
  private String salesName;

  /**
   * 销售等级
   */
  @ExcelProperty(value = "销售等级")
  private String salesGrade;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

  /**
   * 联系人
   */
  @ExcelProperty(value = "联系人")
  private String linker;

  /**
   * 联系地址
   */
  @ExcelProperty(value = "联系地址")
  private String address;

  /**
   * 邮政编码
   */
  @ExcelProperty(value = "邮政编码")
  private String zip;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

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
   * QQ
   */
  @ExcelProperty(value = "QQ")
  private String qq;

  /**
   * 开户行
   */
  @ExcelProperty(value = "开户行")
  private String bank;

  /**
   * 银行行号
   */
  @ExcelProperty(value = "银行行号")
  private String bankCode;

  /**
   * 纳税号
   */
  @ExcelProperty(value = "纳税号")
  private String raxCode;

  /**
   * 公司主页
   */
  @ExcelProperty(value = "公司主页")
  private String corpUrl;

  /**
   * 公司游戏那个
   */
  @ExcelProperty(value = "公司游戏那个")
  private String email;

  /**
   * 公司法人
   */
  @ExcelProperty(value = "公司法人")
  private String artificiaPerson;

  /**
   * Token
   */
  @ExcelProperty(value = "Token")
  private String token;

  /**
   * 超收百分比
   */
  @ExcelProperty(value = "超收百分比")
  private BigDecimal overcharges;

  /**
   * 质检方案
   */
  @ExcelProperty(value = "质检方案")
  private String qualityPlan;

  /**
   * 质检比例
   */
  @ExcelProperty(value = "质检比例")
  private BigDecimal qualityProportion;

  /**
   * 需效期管理
   */
  @ExcelProperty(value = "需效期管理")
  private Long isNeedPeriod;

  /**
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private BigDecimal shelfLifeDay;

  /**
   * 禁收效期系数%
   */
  @ExcelProperty(value = "禁收效期系数%")
  private BigDecimal noReceivingRate;

  /**
   * 停售提前时长（天）
   */
  @ExcelProperty(value = "停售提前时长", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "天=")
  private Long stopSaleday;

  /**
   * 公司所在区域
   */
  @ExcelProperty(value = "公司所在区域")
  private String areaName;

  /**
   * 商家类型
   */
  @ExcelProperty(value = "商家类型")
  private String consignorType;

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
   * 信用额度
   */
  @ExcelProperty(value = "信用额度")
  private String creditLine;

  /**
   * 保险费率
   */
  @ExcelProperty(value = "保险费率")
  private Long premiumRate;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private String lng;

  /**
   * 纬度
   */
  @ExcelProperty(value = "纬度")
  private String lat;

  /**
   * 运单规则
   */
  @ExcelProperty(value = "运单规则")
  private String wayBillRule;


}
