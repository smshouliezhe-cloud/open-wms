package com.yiruantong.outbound.domain.service;

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
 * 出库退货申请单对象 out_return_apply
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_return_apply", autoResultMap = true)
public class OutReturnApply extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户售后ID
   */
  @TableId(value = "return_apply_id")
  private Long returnApplyId;

  /**
   * 客户售后编号
   */
  private String returnApplyCode;

  /**
   * 出库iD
   */
  private Long orderId;

  /**
   * 出库编号
   */
  private String orderCode;

  /**
   * 入库仓库ID
   */
  private Long storageId;

  /**
   * 入库仓库名称
   */
  private String storageName;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 入库时间
   */
  private Date applyDate;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 退款数量
   */
  private Long totalReturnQuantity;

  /**
   * 退款金额
   */
  private BigDecimal totalSaleAmount;

  /**
   * 退货金额
   */
  private BigDecimal totalReturnAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;

  /**
   * 申请状态
   */
  private String applyStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

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
   * 司机姓名
   */
  private String driveName;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 发货人
   */
  private String shippingName;

  /**
   * 发货人地址
   */
  private String shippingAddress;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;


}
