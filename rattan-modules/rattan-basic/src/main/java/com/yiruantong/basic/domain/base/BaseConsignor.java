package com.yiruantong.basic.domain.base;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 货主信息对象 base_consignor
 *
 * @author YRT
 * @date 2025-01-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor", autoResultMap = true)
public class BaseConsignor extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 货主ID
   */
    @TableId(value = "consignor_id")
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 货主英文名称
   */
  private String consignorEnName;

  /**
   * 登陆密码
   */
        @TableField(
    insertStrategy = FieldStrategy.NOT_EMPTY,
    updateStrategy = FieldStrategy.NOT_EMPTY,
    whereStrategy = FieldStrategy.NOT_EMPTY
  )
  private String password;

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
  private String firstCustomer;

  /**
   * 二级客户
   */
  private String secondaryCustomer;

  /**
   * 一级客户ID
   */
  private Long firstCustomerId;

  /**
   * 二级客户ID
   */
  private Long secondaryCustomerId;

  /**
   * 门店等级
   */
  private String consignorGrade;

  /**
   * 门店名称
   */
  private String shopName;

  /**
   * 门店地址
   */
  private String shopAddress;

  /**
   * 门店面积
   */
  private String shopArea;

  /**
   * 是否有空地搞促销
   */
  private Byte isPromotion;

  /**
   * 门店流量
   */
  private String shopTraffic;

  /**
   * 总库存
   */
  private BigDecimal shopTotalInventory;

  /**
   * 总销量
   */
  private BigDecimal shopTatalSales;

  /**
   * 是否重复拜访
   */
  private Byte isRepeatVisit;

  /**
   * 拜访时间
   */
  private Date visitDate;

  /**
   * 责任业务员
   */
  private String salesName;

  /**
   * 销售等级
   */
  private String salesGrade;

  /**
   * 图片
   */
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
