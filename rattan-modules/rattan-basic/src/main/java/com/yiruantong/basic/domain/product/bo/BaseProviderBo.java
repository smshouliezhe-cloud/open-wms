package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseProvider;
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
 * 供应商管理业务对象 base_provider
 *
 * @author YiRuanTong
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseProvider.class, reverseConvertGenerate = false)
public class BaseProviderBo extends BaseEntity {

  /**
   * 行ID
   */
  private Long providerId;

  /**
   * 拼音码
   */
  private String pinYinCode;

  /**
   * 供应商编号
   */
  @NotBlank(message = "供应商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 供应商简称
   */
  @NotBlank(message = "供应商简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 供应商全称
   */
  @NotBlank(message = "供应商全称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerName;

  /**
   * 所属地区ID
   */
  private Long cityId;

  /**
   * 所属地区
   */
  private String cityName;

  /**
   * 单位类别ID
   */
  private Long typeId;

  /**
   * 类别名称
   */
  private String typeName;

  /**
   * 联系人
   */
  private String linker;

  /**
   * 地址
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
   * MSN
   */
  private String msn;

  /**
   * 开户行
   */
  private String bank;

  /**
   * 银行账号
   */
  private String bankCode;

  /**
   * 公司法人
   */
  private String raxCode;

  /**
   * 供应商URL
   */
  private String corpUrl;

  /**
   * Email
   */
  private String email;

  /**
   * 公司法人
   */
  private String artificiaPerson;

  /**
   * 在途天数
   */
  private Long transitDays;

  /**
   * 应付款上限
   */
  private Long moneyUpper;

  /**
   * 结款方式
   */
  private String accountsMode;

  /**
   * 结款周期(天)
   */
  private String accountsPeriod;

  /**
   * 信用额度
   */
  private BigDecimal creditLine;

  /**
   * 企业性质
   */
  private String enpterType;

  /**
   * 企业类型
   */
  private String enpterMode;

  /**
   * 合同开始日期
   */
  private Date contractStart;

  /**
   * 合同开始日期
   */
  private Date contractEnd;

  /**
   * 发票类型
   */
  private String invoiceType;

  /**
   * 合作模式
   */
  private String cooperationMode;

  /**
   * 是否返点
   */
  private Long isFanDianFee;

  /**
   * 返点额
   */
  private String fanDianFee;

  /**
   * 商品折扣
   */
  private String productDiscount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 密码
   */
  private String password;

  /**
   * 货主ID
   */
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
  private Long stopSaleDay;

  /**
   * 是否带货安装
   */
  private Long isProductinstallation;

  /**
   * 公众号OpenId
   */
  private String wechatOpenid;

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


}
