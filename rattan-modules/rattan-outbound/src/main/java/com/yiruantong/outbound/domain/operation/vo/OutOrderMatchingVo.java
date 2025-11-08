package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderMatching;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 订单配货视图对象 out_order_matching
 *
 * @author YRT
 * @date 2023-12-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderMatching.class)
public class OutOrderMatchingVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 配货单ID
   */
  @ExcelProperty(value = "配货单ID")
  private Long matchingId;

  /**
   * 配货单编号
   */
  @ExcelProperty(value = "配货单编号")
  private String matchingCode;

  /**
   * 波次单ID
   */
  @ExcelProperty(value = "波次单ID")
  private Long orderWaveId;

  /**
   * 波次单号
   */
  @ExcelProperty(value = "波次单号")
  private String orderWaveCode;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 配货人ID
   */
  @ExcelProperty(value = "配货人ID")
  private Long userId;

  /**
   * 配货人
   */
  @ExcelProperty(value = "配货人")
  private String nickName;

  /**
   * 开始时间
   */
  @ExcelProperty(value = "开始时间")
  private Date startDate;

  /**
   * 结束时间
   */
  @ExcelProperty(value = "结束时间")
  private Date endDate;

  /**
   * 持续时间
   */
  @ExcelProperty(value = "持续时间")
  private String spanTime;

  /**
   * 拣货数量
   */
  @ExcelProperty(value = "拣货数量")
  private BigDecimal totalQuantityOrder;

  /**
   * 配货数量
   */
  @ExcelProperty(value = "配货数量")
  private BigDecimal totalMatchQuantity;

  /**
   * 订单数
   */
  @ExcelProperty(value = "订单数")
  private Long orderCount;

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
   * 配货状态
   */
  @ExcelProperty(value = "配货状态")
  private String matchStatus;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

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
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQtyTotal;


}
