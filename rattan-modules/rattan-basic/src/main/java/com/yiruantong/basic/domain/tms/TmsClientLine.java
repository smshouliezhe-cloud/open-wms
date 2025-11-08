package com.yiruantong.basic.domain.tms;

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
 * 客户线路规则对象 tms_client_line
 *
 * @author YRT
 * @date 2024-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_client_line", autoResultMap = true)
public class TmsClientLine extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户线路关系ID
   */
  @TableId(value = "client_line_id")
  private Long clientLineId;

  /**
   * 客户线路关系名称
   */
  private String clientLineName;

  /**
   * 线路Id
   */
  private Long lineId;

  /**
   * 线路编号
   */
  private String lineCode;

  /**
   * 线路名称
   */
  private String lineName;

  /**
   * 线路类型
   */
  private String lineType;

  /**
   * 车辆ID
   */
  private Long vehicleId;

  /**
   * 车辆编号
   */
  private String vehicleCode;

  /**
   * 司机ID
   */
  private Long driverId;

  /**
   * 司机编号
   */
  private String driverCode;

  /**
   * 司机名称
   */
  private String driverName;

  /**
   * 线路规则
   */
  private String lineRule;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 订单详情模板ID
   */
  private Long orderDetailTemplateId;

  /**
   * 订单详情模板
   */
  private String orderDetailTemplate;

  /**
   * 订单部分详情模板ID
   */
  private Long orderPartialDetailTemplateId;

  /**
   * 订单部分详情模板
   */
  private String orderPartialDetailTemplate;

  /**
   * 装箱清单模板ID
   */
  private Long packingDetailTemplateId;

  /**
   * 装箱清单模板
   */
  private String packingDetailTemplate;

  /**
   * 面单模板ID
   */
  private Long faceBillTemplateId;

  /**
   * 面单模板
   */
  private String faceBillTemplate;

  /**
   * 附属面单模板ID
   */
  private Long subFaceBillTemplateId;

  /**
   * 附属面单模板
   */
  private String subFaceBillTemplate;

  /**
   * 快递单模板ID
   */
  private Long expressBillTemplateId;

  /**
   * 快递单模板
   */
  private String expressBillTemplate;

  /**
   * 线路假期
   */
  private String lineVacation;

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


}
