package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.TmsClientLine;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 客户线路规则视图对象 tms_client_line
 *
 * @author YRT
 * @date 2024-03-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsClientLine.class)
public class TmsClientLineVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户线路关系ID
   */
  @ExcelProperty(value = "客户线路关系ID")
  private Long clientLineId;

  /**
   * 客户线路关系名称
   */
  @ExcelProperty(value = "客户线路关系名称")
  private String clientLineName;

  /**
   * 线路Id
   */
  @ExcelProperty(value = "线路Id")
  private Long lineId;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 线路名称
   */
  @ExcelProperty(value = "线路名称")
  private String lineName;

  /**
   * 线路类型
   */
  @ExcelProperty(value = "线路类型")
  private String lineType;

  /**
   * 车辆ID
   */
  @ExcelProperty(value = "车辆ID")
  private Long vehicleId;

  /**
   * 车辆编号
   */
  @ExcelProperty(value = "车辆编号")
  private String vehicleCode;

  /**
   * 司机ID
   */
  @ExcelProperty(value = "司机ID")
  private Long driverId;

  /**
   * 司机编号
   */
  @ExcelProperty(value = "司机编号")
  private String driverCode;

  /**
   * 司机名称
   */
  @ExcelProperty(value = "司机名称")
  private String driverName;

  /**
   * 线路规则
   */
  @ExcelProperty(value = "线路规则")
  private String lineRule;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 订单详情模板ID
   */
  @ExcelProperty(value = "订单详情模板ID")
  private Long orderDetailTemplateId;

  /**
   * 订单详情模板
   */
  @ExcelProperty(value = "订单详情模板")
  private String orderDetailTemplate;

  /**
   * 订单部分详情模板ID
   */
  @ExcelProperty(value = "订单部分详情模板ID")
  private Long orderPartialDetailTemplateId;

  /**
   * 订单部分详情模板
   */
  @ExcelProperty(value = "订单部分详情模板")
  private String orderPartialDetailTemplate;

  /**
   * 装箱清单模板ID
   */
  @ExcelProperty(value = "装箱清单模板ID")
  private Long packingDetailTemplateId;

  /**
   * 装箱清单模板
   */
  @ExcelProperty(value = "装箱清单模板")
  private String packingDetailTemplate;

  /**
   * 面单模板ID
   */
  @ExcelProperty(value = "面单模板ID")
  private Long faceBillTemplateId;

  /**
   * 面单模板
   */
  @ExcelProperty(value = "面单模板")
  private String faceBillTemplate;

  /**
   * 附属面单模板ID
   */
  @ExcelProperty(value = "附属面单模板ID")
  private Long subFaceBillTemplateId;

  /**
   * 附属面单模板
   */
  @ExcelProperty(value = "附属面单模板")
  private String subFaceBillTemplate;

  /**
   * 快递单模板ID
   */
  @ExcelProperty(value = "快递单模板ID")
  private Long expressBillTemplateId;

  /**
   * 快递单模板
   */
  @ExcelProperty(value = "快递单模板")
  private String expressBillTemplate;

  /**
   * 线路假期
   */
  @ExcelProperty(value = "线路假期")
  private String lineVacation;

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
