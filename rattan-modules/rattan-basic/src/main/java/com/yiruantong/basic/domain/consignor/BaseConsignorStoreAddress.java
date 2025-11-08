package com.yiruantong.basic.domain.consignor;

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
 * 收货人管理对象 base_consignor_store_address
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor_store_address", autoResultMap = true)
public class BaseConsignorStoreAddress extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 地址ID
   */
  @TableId(value = "address_id")
  private Long addressId;

  /**
   * 所属客户Id
   */
  private Long consignorId;

  /**
   * 所属客户编号
   */
  private String consignorCode;

  /**
   * 所属客户
   */
  private String consignorName;

  /**
   * 收货人编码
   */
  private String consigneeCode;

  /**
   * 收货门店
   */
  private String consigneeStore;

  /**
   * 收货人姓名
   */
  private String consigneeName;

  /**
   * 收货人电话
   */
  private String consigneeMobile;

  /**
   * 收货区域
   */
  private String consigneeArea;

  /**
   * 承运商网点
   */
  private String carrierNetWork;

  /**
   * 线路方案
   */
  private String linePlan;

  /**
   * 收货地图地址
   */
  private String consigneeMapAddr;

  /**
   * 是否可用
   */
  private Long enable;

  /**
   * 出库规则
   */
  private String outboundRules;

  /**
   * 收货详细地址
   */
  private String consigneeAddress;

  /**
   * 经度
   */
  private BigDecimal longitude;

  /**
   * 纬度
   */
  private BigDecimal latitude;

  /**
   * 门店图片
   */
  private String storePictures;

  /**
   * 是否限高
   */
  private Long isLimitHeight;

  /**
   * 是否限宽
   */
  private Long isLimitingWidth;

  /**
   * 指定车型
   */
  private String designatedModels;

  /**
   * 是否需要通行证
   */
  private Long isPass;

  /**
   * 是否需要小工
   */
  private Long isSmallWorker;

  /**
   * 是否需要测温
   */
  private Long isThermometry;

  /**
   * 配送停车是否收费
   */
  private Long isParkingCharge;

  /**
   * 是否需要小推车
   */
  private Long isNeedTrolley;

  /**
   * 停车情况
   */
  private Long parkingSituation;

  /**
   * 要求到仓时间
   */
  private Date getStoragetime;

  /**
   * 仓库提货时间
   */
  private Date storagpickupTime;

  /**
   * 默认发货人
   */
  private String billingName;

  /**
   * 配送开始时间
   */
  private Date deliveryStartTime;

  /**
   * 配送结束时间
   */
  private Date deliveryEndTime;

  /**
   * 提货开始时间
   */
  private Date pickupStartTime;

  /**
   * 提货结束时间
   */
  private Date pickupEndTime;

  /**
   * 收货要求
   */
  private String receivingAsk;

  /**
   * 是否为特殊门店
   */
  private Long isSpecialStores;

  /**
   * 紧急程度
   */
  private String urgency;

  /**
   * 运输类型
   */
  private String transportationType;

  /**
   * 是否代收款
   */
  private Long isCollection;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 省Id
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市Id
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区Id
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

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
   * 收货人ID
   */
  private Long consigneeId;


}
