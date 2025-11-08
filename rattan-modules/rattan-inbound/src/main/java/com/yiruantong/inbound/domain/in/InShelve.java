package com.yiruantong.inbound.domain.in;

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
 * 商品上架对象 in_shelve
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_shelve", autoResultMap = true)
public class InShelve extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 上架ID
   */
  @TableId(value = "shelve_id")
  private Long shelveId;

  /**
   * 上架编号
   */
  private String shelveCode;

  /**
   * 上架类型
   */
  private String shelveType;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 入库单ID
   */
  private Long enterId;

  /**
   * 入库单编号
   */
  private String enterCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 上架人ID
   */
  private Long userId;

  /**
   * 上架人
   */
  private String nickName;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  /**
   * 持续时间
   */
  private String spanTime;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 审核状态
   */
  private Byte auditing;

  /**
   * 审核人
   */
  private String auditor;

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
   * 上架状态
   */
  private String shelveStatus;

  /**
   * 待商家数量
   */
  private BigDecimal totalOnshelveQuantity;

  /**
   * 已上架数量
   */
  private BigDecimal totalShelvedQuantity;

  /**
   * 单据类型
   */
  private String orderType;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商编号
   */
  private String providerCode;

  /**
   * 采购商名称
   */
  private String providerShortName;

  /**
   * 集装箱号
   */
  private String containerNos;

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
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 预到货单ID
   */
  private Long orderId;

  /**
   * 采购单编号
   */
  private String orderCode;

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
   * 接收任务人员
   */
  private String receiveTaskPeople;

  /**
   * 入库类型
   */
  private String scanInType;

  /**
   * 跟踪单号
   */
  private String trackingNumber;

  /**
   * LPN编号
   */
  private String lpnCode;

  /**
   * 铅封号
   */
  private String sealNos;

  /**
   * 仓库编号
   */
  private String storageCode;


}
