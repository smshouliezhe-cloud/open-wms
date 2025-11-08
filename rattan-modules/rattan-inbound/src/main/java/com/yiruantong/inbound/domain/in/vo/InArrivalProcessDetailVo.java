package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InArrivalProcessDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 到货加工明细视图对象 in_arrival_process_detail
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InArrivalProcessDetail.class)
public class InArrivalProcessDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 加工明细ID
   */
  @ExcelProperty(value = "加工明细ID")
  private Long processDetailId;

  /**
   * 加工ID
   */
  @ExcelProperty(value = "加工ID")
  private Long processId;

  /**
   * 采购明细ID
   */
  @ExcelProperty(value = "采购明细ID")
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  @ExcelProperty(value = "采购单ID")
  private Long orderId;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
  private Long productId;

  /**
   * 产品编号
   */
  @ExcelProperty(value = "产品编号")
  private String productCode;

  /**
   * 产品名称
   */
  @ExcelProperty(value = "产品名称")
  private String productName;

  /**
   * 条形码
   */
  @ExcelProperty(value = "条形码")
  private String productModel;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal quantity;

  /**
   * 过程类型
   */
  @ExcelProperty(value = "过程类型")
  private String processType;

  /**
   * 清关数量
   */
  @ExcelProperty(value = "清关数量")
  private BigDecimal clearanceQauntity;

  /**
   * 车牌编号
   */
  @ExcelProperty(value = "车牌编号")
  private String plateCode;

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


}
