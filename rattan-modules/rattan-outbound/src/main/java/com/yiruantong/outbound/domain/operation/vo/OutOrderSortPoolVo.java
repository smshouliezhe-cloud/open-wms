package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderSortPool;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 分拣池视图对象 out_order_sort_pool
 *
 * @author YRT
 * @date 2024-05-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderSortPool.class)
public class OutOrderSortPoolVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 分拣池ID
   */
  @ExcelProperty(value = "分拣池ID")
  private Long sortPoolId;

  /**
   * 订单ID
   */
  @ExcelProperty(value = "订单ID")
  private Long orderId;

  /**
   * 池状态
   */
  @ExcelProperty(value = "池状态")
  private Byte poolState;

  /**
   * 权重
   */
  @ExcelProperty(value = "权重")
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
   * 出库单号
   */
  @ExcelProperty(value = "出库单号")
  private String orderCode;

  /**
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String storeOrderCode;


}
