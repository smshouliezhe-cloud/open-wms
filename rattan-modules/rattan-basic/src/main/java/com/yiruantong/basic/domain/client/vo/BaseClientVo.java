package com.yiruantong.basic.domain.client.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.client.BaseClient;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 客户信息视图对象 base_client
 *
 * @author YRT
 * @date 2024-10-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseClient.class)
public class BaseClientVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 拼音码
   */
  @ExcelProperty(value = "拼音码")
  private String pinYinCode;

  /**
   * 客户简称
   */
  @ExcelProperty(value = "客户简称")
  private String clientShortName;

  /**
   * 客户全称
   */
  @ExcelProperty(value = "客户全称")
  private String clientFullName;

  /**
   * 国家ID
   */
  @ExcelProperty(value = "国家ID")
  private Long countryId;

  /**
   * 国家
   */
  @ExcelProperty(value = "国家")
  private String countryName;

  /**
   * 国家全称
   */
  @ExcelProperty(value = "国家全称")
  private String countryFullName;

  /**
   * 是否热点
   */
  @ExcelProperty(value = "是否热点")
  private Long isHot;

  /**
   * 热点类别
   */
  @ExcelProperty(value = "热点类别")
  private String hotType;

  /**
   * 热度
   */
  @ExcelProperty(value = "热度")
  private String hotLevel;

  /**
   * 热点说明
   */
  @ExcelProperty(value = "热点说明")
  private String hotRemark;

  /**
   * 价值评估
   */
  @ExcelProperty(value = "价值评估")
  private String valuation;

  /**
   * 信用等级
   */
  @ExcelProperty(value = "信用等级")
  private String creditLevel;

  /**
   * 信用额度
   */
  @ExcelProperty(value = "信用额度")
  private BigDecimal creditLine;

  /**
   * 种类
   */
  @ExcelProperty(value = "种类")
  private String clientType;

  /**
   * 行业
   */
  @ExcelProperty(value = "行业")
  private String profession;

  /**
   * 关系等级
   */
  @ExcelProperty(value = "关系等级")
  private String relationLevel;

  /**
   * 付款类别
   */
  @ExcelProperty(value = "付款类别")
  private String payType;

  /**
   * 人员规模
   */
  @ExcelProperty(value = "人员规模")
  private String staffSize;

  /**
   * 客户来源
   */
  @ExcelProperty(value = "客户来源")
  private String source;

  /**
   * 阶段
   */
  @ExcelProperty(value = "阶段")
  private String phase;

  /**
   * 标签
   */
  @ExcelProperty(value = "标签")
  private String label;

  /**
   * 公司简介
   */
  @ExcelProperty(value = "公司简介")
  private String corpDesc;

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
   * 邮箱
   */
  @ExcelProperty(value = "邮箱")
  private String email;

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
   * 传真
   */
  @ExcelProperty(value = "传真")
  private String fax;

  /**
   * 地址
   */
  @ExcelProperty(value = "地址")
  private String address;

  /**
   * 网址
   */
  @ExcelProperty(value = "网址")
  private String corpUrl;

  /**
   * 开户行
   */
  @ExcelProperty(value = "开户行")
  private String bank;

  /**
   * 银行账号
   */
  @ExcelProperty(value = "银行账号")
  private String bankCode;

  /**
   * 纳税号
   */
  @ExcelProperty(value = "纳税号")
  private String raxCode;

  /**
   * 公司法人
   */
  @ExcelProperty(value = "公司法人")
  private String artificiaPerson;

  /**
   * 估计月用量
   */
  @ExcelProperty(value = "估计月用量")
  private String monthSales;

  /**
   * 第一次联系时间
   */
  @ExcelProperty(value = "第一次联系时间")
  private Date submiDate;

  /**
   * 下一次回访时间
   */
  @ExcelProperty(value = "下一次回访时间")
  private Date nextDate;

  /**
   * 是否公海
   */
  @ExcelProperty(value = "是否公海")
  private Long isOpen;

  /**
   * 第一笔成交日期
   */
  @ExcelProperty(value = "第一笔成交日期")
  private Date bargainDate;

  /**
   * 成交周期
   */
  @ExcelProperty(value = "成交周期")
  private Long bargainSeasonal;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;

  /**
   * 所有者ID
   */
  @ExcelProperty(value = "所有者ID")
  private Long userId;

  /**
   * 所有者
   */
  @ExcelProperty(value = "所有者")
  private String nickName;

  /**
   * 领取时间
   */
  @ExcelProperty(value = "领取时间")
  private Date drawDate;

  /**
   * 到期时间
   */
  @ExcelProperty(value = "到期时间")
  private Date expireDate;

  /**
   * 客户性质
   */
  @ExcelProperty(value = "客户性质")
  private String customerNature;

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
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String telephone;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 收件地址
   */
  @ExcelProperty(value = "收件地址")
  private String shippingAddress;

  /**
   * 收件人
   */
  @ExcelProperty(value = "收件人")
  private String shippingName;

  /**
   * 密码
   */
  @ExcelProperty(value = "密码")
  private String password;

  /**
   * 登录名
   */
  @ExcelProperty(value = "登录名")
  private String loginName;

  /**
   * 头像
   */
  @ExcelProperty(value = "头像")
  private String avatarUrl;

  /**
   * sessionKey
   */
  @ExcelProperty(value = "sessionKey")
  private String sessionKey;

  /**
   * openID
   */
  @ExcelProperty(value = "openID")
  private String openid;

  /**
   * 收藏
   */
  @ExcelProperty(value = "收藏")
  private String favorite;

  /**
   * 公司ID
   */
  @ExcelProperty(value = "公司ID")
  private Long corperationId;

  /**
   * 公司编号
   */
  @ExcelProperty(value = "公司编号")
  private String corperationCode;

  /**
   * 公司名称
   */
  @ExcelProperty(value = "公司名称")
  private String corperationName;

  /**
   * 关联码
   */
  @ExcelProperty(value = "关联码")
  private String relationCode;

  /**
   * 所属仓库ID
   */
  @ExcelProperty(value = "所属仓库ID")
  private Long storageId;

  /**
   * 所属仓库
   */
  @ExcelProperty(value = "所属仓库")
  private String storageName;

  /**
   * 客户类型
   */
  @ExcelProperty(value = "客户类型")
  private String clientMode;

  /**
   * 货主等级
   */
  @ExcelProperty(value = "货主等级")
  private String consignorRank;

  /**
   * jsonData
   */
  @ExcelProperty(value = "jsonData")
  private String jsonData;

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
   * 配送线路
   */
  @ExcelProperty(value = "配送线路")
  private String lineName;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 配送顺序
   */
  @ExcelProperty(value = "配送顺序")
  private Long lineOrderNo;

  /**
   * 线路Id
   */
  @ExcelProperty(value = "线路Id")
  private Long lineId;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 运费计价方式
   */
  @ExcelProperty(value = "运费计价方式")
  private String pricingManner;

  /**
   * 所属网点
   */
  @ExcelProperty(value = "所属网点")
  private String siteName;

  /**
   * 自动生成运费
   */
  @ExcelProperty(value = "自动生成运费")
  private Byte autoBuildFreight;

  /**
   * 网点Id
   */
  @ExcelProperty(value = "网点Id")
  private Long siteId;

  /**
   * 费用科目id
   */
  @ExcelProperty(value = "费用科目id")
  private Long feeItemId;

  /**
   * 费用科目
   */
  @ExcelProperty(value = "费用科目")
  private String feeItemName;

  /**
   * 公司注册地址
   */
  @ExcelProperty(value = "公司注册地址")
  private String registeredAddress;

  /**
   * 公司注册电话
   */
  @ExcelProperty(value = "公司注册电话")
  private String registrationPhone;

  /**
   * 公司营业地址
   */
  @ExcelProperty(value = "公司营业地址")
  private String businessAddress;

  /**
   * 法人邮箱
   */
  @ExcelProperty(value = "法人邮箱")
  private String corporateEmail;

  /**
   * 默认联系人
   */
  @ExcelProperty(value = "默认联系人")
  private String defaultContact;

  /**
   * 默认联系电话
   */
  @ExcelProperty(value = "默认联系电话")
  private String defaultPhone;

  /**
   * 联系人邮箱
   */
  @ExcelProperty(value = "联系人邮箱")
  private String contactEmail;

  /**
   * 开始成交日期
   */
  @ExcelProperty(value = "开始成交日期")
  private Date startTransactionDate;

  /**
   * 开始放账日期
   */
  @ExcelProperty(value = "开始放账日期")
  private Date startCreditedDate;

  /**
   * 总信保额度(USD)
   */
  @ExcelProperty(value = "总信保额度(USD)")
  private BigDecimal totalLineCredit;

  /**
   * 已使用信保额度(USD)
   */
  @ExcelProperty(value = "已使用信保额度(USD)")
  private BigDecimal usedCreditAmount;

  /**
   * 剩余信保额度(USD)
   */
  @ExcelProperty(value = "剩余信保额度(USD)")
  private BigDecimal remainingLineCredit;

  /**
   * 最长账期天数
   */
  @ExcelProperty(value = "最长账期天数")
  private BigDecimal accountPeriodDay;

  /**
   * 最高赔付比例
   */
  @ExcelProperty(value = "最高赔付比例")
  private BigDecimal maxCompensationRatio;

  /**
   * 计息利率%
   */
  @ExcelProperty(value = "计息利率%")
  private BigDecimal interestBearingTaxRate;

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


}
