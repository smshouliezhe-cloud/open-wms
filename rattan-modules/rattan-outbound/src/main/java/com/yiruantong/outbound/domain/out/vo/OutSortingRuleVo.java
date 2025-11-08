package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.out.OutSortingRule;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 订单特殊分拣规则视图对象 out_sorting_rule
 *
 * @author YRT
 * @date 2025-02-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutSortingRule.class)
public class OutSortingRuleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long ruleId;

  /**
   * 订单编号
   */
  @ExcelProperty(value = "订单编号")
  private String orderCode;

  /**
   * 产品编号
   */
  @ExcelProperty(value = "产品编号")
  private String productCode;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * 托盘号
   */
  @ExcelProperty(value = "托盘号")
  private String plateCode;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String singleSignCode;

  /**
   * 订单ID
   */
  @ExcelProperty(value = "订单ID")
  private Long orderId;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
  private Long productId;

  /**
   * 销售明细ID
   */
  @ExcelProperty(value = "销售明细ID")
  private Long orderDetailId;

  /**
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

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
   * 大于生产日期
   */
  @ExcelProperty(value = "大于生产日期")
  private Date produceDateGt;

  /**
   * 项目号
   */
  @ExcelProperty(value = "项目号")
  private String projectCode;

  /**
   * 箱号
   */
  @ExcelProperty(value = "箱号")
  private String caseNumber;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 规则类别
   */
  @ExcelProperty(value = "规则类别")
  private String ruleType;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private String sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;
}
