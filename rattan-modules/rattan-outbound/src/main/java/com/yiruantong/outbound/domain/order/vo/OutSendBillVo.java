package com.yiruantong.outbound.domain.order.vo;

  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.outbound.domain.order.OutSendBill;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 发货单明细视图对象 out_send_bill
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutSendBill.class)
public class OutSendBillVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 配送ID
       */
      @ExcelProperty(value = "配送ID")
    private Long sendBillId;

      /**
       * 店铺ID
       */
      @ExcelProperty(value = "店铺ID")
    private Long storeId;

      /**
       * 店铺名称
       */
      @ExcelProperty(value = "店铺名称")
    private String storeName;

      /**
       * 快递ID
       */
      @ExcelProperty(value = "快递ID")
    private Long expressCorpId;

      /**
       * 快递名称
       */
      @ExcelProperty(value = "快递名称")
    private String expressCorpName;

      /**
       * 订单渠道
       */
      @ExcelProperty(value = "订单渠道")
    private Byte orderChannel;

      /**
       * 销售订单ID
       */
      @ExcelProperty(value = "销售订单ID")
    private Long orderId;

      /**
       * 销售订单编号
       */
      @ExcelProperty(value = "销售订单编号")
    private String orderCode;

      /**
       * 快递单号
       */
      @ExcelProperty(value = "快递单号")
    private String expressCode;

      /**
       * 店铺订单编号
       */
      @ExcelProperty(value = "店铺订单编号")
    private String storeOrderCode;

      /**
       * 打印状态
       */
      @ExcelProperty(value = "打印状态")
    private String printStatus;

      /**
       * 配送同步状态
       */
      @ExcelProperty(value = "配送同步状态")
    private String shipStatus;

      /**
       * 经手人ID
       */
      @ExcelProperty(value = "经手人ID")
    private Long userId;

      /**
       * 经手人
       */
      @ExcelProperty(value = "经手人")
    private String nickName;

      /**
       * 下单日期
       */
      @ExcelProperty(value = "下单日期")
    private Date applyDate;

      /**
       * 收款期限
       */
      @ExcelProperty(value = "收款期限")
    private Date payLimitDate;

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
       * 客户姓名
       */
      @ExcelProperty(value = "客户姓名")
    private String clientShortName;

      /**
       * 订单数量
       */
      @ExcelProperty(value = "订单数量")
    private BigDecimal totalQuantityOrder;

      /**
       * 订单金额
       */
      @ExcelProperty(value = "订单金额")
    private BigDecimal totalAmount;

      /**
       * 应收金额
       */
      @ExcelProperty(value = "应收金额")
    private BigDecimal grandTotal;

      /**
       * 快递费
       */
      @ExcelProperty(value = "快递费")
    private BigDecimal shippingAmount;

      /**
       * 合计体积
       */
      @ExcelProperty(value = "合计体积")
    private BigDecimal totalCube;

      /**
       * 重量
       */
      @ExcelProperty(value = "重量")
    private BigDecimal totalWeight;

      /**
       * 包裹
       */
      @ExcelProperty(value = "包裹")
    private String packageName;

      /**
       * 发货人
       */
      @ExcelProperty(value = "发货人")
    private String shippingName;

      /**
       * 收货人
       */
      @ExcelProperty(value = "收货人")
    private String billingName;

      /**
       * 发货人地址
       */
      @ExcelProperty(value = "发货人地址")
    private String shippingAddress;

      /**
       * 收货人地址
       */
      @ExcelProperty(value = "收货人地址")
    private String billingAddress;

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
       * 国家中文名
       */
      @ExcelProperty(value = "国家中文名")
    private String countryCnName;

      /**
       * 客户邮件
       */
      @ExcelProperty(value = "客户邮件")
    private String email;

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
       * 打包单ID
       */
      @ExcelProperty(value = "打包单ID")
    private Long packageId;

      /**
       * 打包单编号
       */
      @ExcelProperty(value = "打包单编号")
    private String packageCode;

      /**
       * 打印批次ID
       */
      @ExcelProperty(value = "打印批次ID")
    private Long orderWaveId;

      /**
       * 打印批次编号
       */
      @ExcelProperty(value = "打印批次编号")
    private String orderWaveCode;

      /**
       * 仓库ID
       */
      @ExcelProperty(value = "仓库ID")
    private Long storageId;

      /**
       * 仓库名称
       */
      @ExcelProperty(value = "仓库名称")
    private String storageName;

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
       * 仓库编号
       */
      @ExcelProperty(value = "仓库编号")
    private String storageCode;


}
