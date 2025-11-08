package com.yiruantong.basic.domain.consignor.bo;

import com.yiruantong.basic.domain.consignor.BaseConsignorStoreAddress;
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
 * 收货人管理业务对象 base_consignor_store_address
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignorStoreAddress.class, reverseConvertGenerate = false)
public class BaseConsignorStoreAddressBo extends BaseEntity {

  /**
   * 地址ID
   */
  @NotNull(message = "地址ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long addressId;

  /**
   * 所属客户Id
   */
  @NotNull(message = "所属客户Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 所属客户编号
   */
  @NotBlank(message = "所属客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 所属客户
   */
  @NotBlank(message = "所属客户不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 收货人编码
   */
  @NotBlank(message = "收货人编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeCode;

  /**
   * 收货门店
   */
  @NotBlank(message = "收货门店不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeStore;

  /**
   * 收货人姓名
   */
  @NotBlank(message = "收货人姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeName;

  /**
   * 收货人电话
   */
  @NotBlank(message = "收货人电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeMobile;

  /**
   * 收货区域
   */
  @NotBlank(message = "收货区域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeArea;

  /**
   * 承运商网点
   */
  @NotBlank(message = "承运商网点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierNetWork;

  /**
   * 线路方案
   */
  @NotBlank(message = "线路方案不能为空", groups = {AddGroup.class, EditGroup.class})
  private String linePlan;

  /**
   * 收货地图地址
   */
  @NotBlank(message = "收货地图地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeMapAddr;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 出库规则
   */
  @NotBlank(message = "出库规则不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outboundRules;

  /**
   * 收货详细地址
   */
  @NotBlank(message = "收货详细地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeAddress;

  /**
   * 经度
   */
  @NotNull(message = "经度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal longitude;

  /**
   * 纬度
   */
  @NotNull(message = "纬度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal latitude;

  /**
   * 门店图片
   */
  @NotBlank(message = "门店图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storePictures;

  /**
   * 是否限高
   */
  @NotNull(message = "是否限高不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isLimitHeight;

  /**
   * 是否限宽
   */
  @NotNull(message = "是否限宽不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isLimitingWidth;

  /**
   * 指定车型
   */
  @NotBlank(message = "指定车型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String designatedModels;

  /**
   * 是否需要通行证
   */
  @NotNull(message = "是否需要通行证不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isPass;

  /**
   * 是否需要小工
   */
  @NotNull(message = "是否需要小工不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isSmallWorker;

  /**
   * 是否需要测温
   */
  @NotNull(message = "是否需要测温不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isThermometry;

  /**
   * 配送停车是否收费
   */
  @NotNull(message = "配送停车是否收费不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isParkingCharge;

  /**
   * 是否需要小推车
   */
  @NotNull(message = "是否需要小推车不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isNeedTrolley;

  /**
   * 停车情况
   */
  @NotNull(message = "停车情况不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parkingSituation;

  /**
   * 要求到仓时间
   */
  @NotNull(message = "要求到仓时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date getStoragetime;

  /**
   * 仓库提货时间
   */
  @NotNull(message = "仓库提货时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date storagpickupTime;

  /**
   * 默认发货人
   */
  @NotBlank(message = "默认发货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingName;

  /**
   * 配送开始时间
   */
  @NotNull(message = "配送开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deliveryStartTime;

  /**
   * 配送结束时间
   */
  @NotNull(message = "配送结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deliveryEndTime;

  /**
   * 提货开始时间
   */
  @NotNull(message = "提货开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date pickupStartTime;

  /**
   * 提货结束时间
   */
  @NotNull(message = "提货结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date pickupEndTime;

  /**
   * 收货要求
   */
  @NotBlank(message = "收货要求不能为空", groups = {AddGroup.class, EditGroup.class})
  private String receivingAsk;

  /**
   * 是否为特殊门店
   */
  @NotNull(message = "是否为特殊门店不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isSpecialStores;

  /**
   * 紧急程度
   */
  @NotBlank(message = "紧急程度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String urgency;

  /**
   * 运输类型
   */
  @NotBlank(message = "运输类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String transportationType;

  /**
   * 是否代收款
   */
  @NotNull(message = "是否代收款不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isCollection;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 省Id
   */
  @NotNull(message = "省Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long provinceId;

  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;

  /**
   * 市Id
   */
  @NotNull(message = "市Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;

  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 区Id
   */
  @NotNull(message = "区Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long regionId;

  /**
   * 区
   */
  @NotBlank(message = "区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;

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
   * 收货人ID
   */
  @NotNull(message = "收货人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consigneeId;


}
