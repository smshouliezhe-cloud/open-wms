package com.yiruantong.outbound.domain.order;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 发货单明细对象 out_send_bill
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_send_bill", autoResultMap = true)
public class OutSendBill extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 配送ID
   */
    @TableId(value = "send_bill_id")
  private Long sendBillId;

  /**
   * 店铺ID
   */
  private Long storeId;

  /**
   * 店铺名称
   */
  private String storeName;

  /**
   * 快递ID
   */
  private Long expressCorpId;

  /**
   * 快递名称
   */
  private String expressCorpName;

  /**
   * 订单渠道
   */
  private Byte orderChannel;

  /**
   * 销售订单ID
   */
  private Long orderId;

  /**
   * 销售订单编号
   */
  private String orderCode;

  /**
   * 快递单号
   */
  private String expressCode;

  /**
   * 店铺订单编号
   */
  private String storeOrderCode;

  /**
   * 打印状态
   */
  private String printStatus;

  /**
   * 配送同步状态
   */
  private String shipStatus;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 下单日期
   */
  private Date applyDate;

  /**
   * 收款期限
   */
  private Date payLimitDate;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户姓名
   */
  private String clientShortName;

  /**
   * 订单数量
   */
  private BigDecimal totalQuantityOrder;

  /**
   * 订单金额
   */
  private BigDecimal totalAmount;

  /**
   * 应收金额
   */
  private BigDecimal grandTotal;

  /**
   * 快递费
   */
  private BigDecimal shippingAmount;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 重量
   */
  private BigDecimal totalWeight;

  /**
   * 包裹
   */
  private String packageName;

  /**
   * 发货人
   */
  private String shippingName;

  /**
   * 收货人
   */
  private String billingName;

  /**
   * 发货人地址
   */
  private String shippingAddress;

  /**
   * 收货人地址
   */
  private String billingAddress;

  /**
   * 电话
   */
  private String telephone;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 国家ID
   */
  private Long countryId;

  /**
   * 国家
   */
  private String countryName;

  /**
   * 国家全称
   */
  private String countryFullName;

  /**
   * 国家中文名
   */
  private String countryCnName;

  /**
   * 客户邮件
   */
  private String email;

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
   * 打包单ID
   */
  private Long packageId;

  /**
   * 打包单编号
   */
  private String packageCode;

  /**
   * 打印批次ID
   */
  private Long orderWaveId;

  /**
   * 打印批次编号
   */
  private String orderWaveCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 仓库编号
   */
  private String storageCode;


}
