package com.yiruantong.inventory.domain.operation;

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
 * 盘点单对象 storage_check
 *
 * @author YRT
 * @date 2023-10-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_check", autoResultMap = true)
public class StorageCheck extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 盘点单ID
   */
  @TableId(value = "check_id")
  private Long checkId;

  /**
   * 盘点单编号
   */
  private String checkCode;

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
   * 盘点日期
   */
  private Date applyDate;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 合计账面库存量
   */
  private BigDecimal totalProductStorage;

  /**
   * 合计账面成本额
   */
  private BigDecimal totalPurchaseAmount;

  /**
   * 合计盘点数量
   */
  private BigDecimal totalCheckQuantity;

  /**
   * 合计盘盈数量
   */
  private BigDecimal totalProfitQuantity;

  /**
   * 合计盘盈金额
   */
  private BigDecimal totalProfitAmount;

  /**
   * 合计盘亏数量
   */
  private BigDecimal totalLossQuantity;

  /**
   * 合计盘亏金额
   */
  private BigDecimal totalLossAmount;

  /**
   * 盘点状态
   */
  private String checkStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

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
   * 盘点单类型
   */
  private String checkType;

  /**
   * 差异天数
   */
  private Long diffDate;

  /**
   * 是否盲盘
   */
  private Long isBlind;

  /**
   * 盈亏单ID
   */
  private Long profitLossId;

  /**
   * 原始盘点单ID
   */
  private Long fromCheckId;

  /**
   * 原始盘点单编号
   */
  private String fromCheckCode;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 账面库存毛重合计
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
