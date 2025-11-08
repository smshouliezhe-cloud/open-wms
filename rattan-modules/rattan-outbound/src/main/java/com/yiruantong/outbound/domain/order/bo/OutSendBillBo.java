package com.yiruantong.outbound.domain.order.bo;

import com.yiruantong.outbound.domain.order.OutSendBill;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 发货单明细业务对象 out_send_bill
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutSendBill.class, reverseConvertGenerate = false)
public class OutSendBillBo extends BaseEntity {

      /**
       * 配送ID
       */
        @NotNull(message = "配送ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sendBillId;

      /**
       * 店铺ID
       */
        @NotNull(message = "店铺ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long storeId;

      /**
       * 店铺名称
       */
        @NotBlank(message = "店铺名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storeName;

      /**
       * 快递ID
       */
        @NotNull(message = "快递ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long expressCorpId;

      /**
       * 快递名称
       */
        @NotBlank(message = "快递名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expressCorpName;

      /**
       * 订单渠道
       */
        @NotNull(message = "订单渠道不能为空", groups = { AddGroup.class, EditGroup.class })
    private Byte orderChannel;

      /**
       * 销售订单ID
       */
        @NotNull(message = "销售订单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderId;

      /**
       * 销售订单编号
       */
        @NotBlank(message = "销售订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderCode;

      /**
       * 快递单号
       */
        @NotBlank(message = "快递单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expressCode;

      /**
       * 店铺订单编号
       */
        @NotBlank(message = "店铺订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storeOrderCode;

      /**
       * 打印状态
       */
        @NotBlank(message = "打印状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String printStatus;

      /**
       * 配送同步状态
       */
        @NotBlank(message = "配送同步状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shipStatus;

      /**
       * 经手人ID
       */
        @NotNull(message = "经手人ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

      /**
       * 经手人
       */
        @NotBlank(message = "经手人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

      /**
       * 下单日期
       */
        @NotNull(message = "下单日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date applyDate;

      /**
       * 收款期限
       */
        @NotNull(message = "收款期限不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date payLimitDate;

      /**
       * 客户ID
       */
        @NotNull(message = "客户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long clientId;

      /**
       * 客户编号
       */
        @NotBlank(message = "客户编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientCode;

      /**
       * 客户姓名
       */
        @NotBlank(message = "客户姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clientShortName;

      /**
       * 订单数量
       */
        @NotNull(message = "订单数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalQuantityOrder;

      /**
       * 订单金额
       */
        @NotNull(message = "订单金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalAmount;

      /**
       * 应收金额
       */
        @NotNull(message = "应收金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal grandTotal;

      /**
       * 快递费
       */
        @NotNull(message = "快递费不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal shippingAmount;

      /**
       * 合计体积
       */
        @NotNull(message = "合计体积不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalCube;

      /**
       * 重量
       */
        @NotNull(message = "重量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalWeight;

      /**
       * 包裹
       */
        @NotBlank(message = "包裹不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageName;

      /**
       * 发货人
       */
        @NotBlank(message = "发货人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shippingName;

      /**
       * 收货人
       */
        @NotBlank(message = "收货人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billingName;

      /**
       * 发货人地址
       */
        @NotBlank(message = "发货人地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shippingAddress;

      /**
       * 收货人地址
       */
        @NotBlank(message = "收货人地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String billingAddress;

      /**
       * 电话
       */
        @NotBlank(message = "电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String telephone;

      /**
       * 手机
       */
        @NotBlank(message = "手机不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mobile;

      /**
       * 国家ID
       */
        @NotNull(message = "国家ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long countryId;

      /**
       * 国家
       */
        @NotBlank(message = "国家不能为空", groups = { AddGroup.class, EditGroup.class })
    private String countryName;

      /**
       * 国家全称
       */
        @NotBlank(message = "国家全称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String countryFullName;

      /**
       * 国家中文名
       */
        @NotBlank(message = "国家中文名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String countryCnName;

      /**
       * 客户邮件
       */
        @NotBlank(message = "客户邮件不能为空", groups = { AddGroup.class, EditGroup.class })
    private String email;

      /**
       * 货主ID
       */
        @NotNull(message = "货主ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long consignorId;

      /**
       * 货主编号
       */
        @NotBlank(message = "货主编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consignorCode;

      /**
       * 货主名称
       */
        @NotBlank(message = "货主名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String consignorName;

      /**
       * 打包单ID
       */
        @NotNull(message = "打包单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long packageId;

      /**
       * 打包单编号
       */
        @NotBlank(message = "打包单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String packageCode;

      /**
       * 打印批次ID
       */
        @NotNull(message = "打印批次ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderWaveId;

      /**
       * 打印批次编号
       */
        @NotBlank(message = "打印批次编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderWaveCode;

      /**
       * 仓库ID
       */
        @NotNull(message = "仓库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long storageId;

      /**
       * 仓库名称
       */
        @NotBlank(message = "仓库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageName;

      /**
       * 扩展字段
       */
        @NotNull(message = "扩展字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Map<String, Object> expandFields;

      /**
       * 备注
       */
        @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

      /**
       * 删除时间
       */
        @NotNull(message = "删除时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date deleteTime;

      /**
       * 删除人id
       */
        @NotNull(message = "删除人id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deleteBy;

      /**
       * 删除人
       */
        @NotBlank(message = "删除人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deleteByName;

      /**
       * 仓库编号
       */
        @NotBlank(message = "仓库编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storageCode;


}
