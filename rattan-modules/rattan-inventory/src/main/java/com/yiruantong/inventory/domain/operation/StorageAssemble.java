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
 * 商品拆装单对象 storage_assemble
 *
 * @author YRT
 * @date 2024-01-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_assemble", autoResultMap = true)
public class StorageAssemble extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 组装单ID
   */
  @TableId(value = "assemble_id")
  private Long assembleId;

  /**
   * 组装单编号
   */
  private String assembleCode;

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
   * 组装日期
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
   * 合计出库数量
   */
  private BigDecimal totalOuterQuantity;

  /**
   * 合计出库金额
   */
  private BigDecimal totalOuterAmount;

  /**
   * 合计入库数量
   */
  private BigDecimal totalEnterQuantity;

  /**
   * 合计入库金额
   */
  private BigDecimal totalEnterAmount;

  /**
   * 组装状态
   */
  private String assembleStatus;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

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

  /**
   * 主商品数量
   */
  private BigDecimal mainQuantity;

  /**
   * 主商品编号
   */
  private String mainProductCode;

  /**
   * 主商品名称
   */
  private String mainProductName;


}
