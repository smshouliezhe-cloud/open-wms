package com.yiruantong.inventory.domain.plate;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 容器流水记录对象 base_plate_flow
 *
 * @author YRT
 * @date 2024-03-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_flow", autoResultMap = true)
public class BasePlateFlow extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 流水ID
   */
  @TableId(value = "flow_id")
  private Long flowId;

  /**
   * 流水单号
   */
  private String flowCode;

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 客户Id
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
   * 容器类别
   */
  private String plateType;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 归还数量
   */
  private BigDecimal returnQty;

  /**
   * 借出数量
   */
  private BigDecimal outerQty;

  /**
   * 总借出数量
   */
  private BigDecimal totalouterQty;

  /**
   * 操作前数量
   */
  private BigDecimal beforeBalanceQty;

  /**
   * 操作后数量
   */
  private BigDecimal balanceQty;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 部门Id
   */
  private Long deptId;

  /**
   * 部门
   */
  private String deptName;

  /**
   * 来源类型
   */
  private String sourceType;

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
   * 容器名称
   */
  private String plateName;

  /**
   * 容器编号
   */
  private String plateCode;

  /**
   * 容器id
   */
  private Long plateId;

  /**
   * 销售组织
   */
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  private Long consignorIdSale;

  /**
   * 容器规格
   */
  private String plateSpec;


}
