package com.yiruantong.outbound.domain.out;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 订单特殊分拣规则对象 out_sorting_rule
 *
 * @author YRT
 * @date 2025-02-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_sorting_rule", autoResultMap = true)
public class OutSortingRule extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "rule_id")
  private Long ruleId;

  /**
   * 订单编号
   */
  private String orderCode;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 订单ID
   */
  private Long orderId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 销售明细ID
   */
  private Long orderDetailId;

  /**
   * 库存ID
   */
  private Long inventoryId;

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
   * 大于生产日期
   */
  private Date produceDateGt;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 规则类别
   */
  private String ruleType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

}
