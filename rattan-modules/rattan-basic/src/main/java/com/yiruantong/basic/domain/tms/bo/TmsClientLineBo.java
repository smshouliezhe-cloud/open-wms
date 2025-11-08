package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsClientLine;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 客户线路规则业务对象 tms_client_line
 *
 * @author YRT
 * @date 2024-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsClientLine.class, reverseConvertGenerate = false)
public class TmsClientLineBo extends BaseEntity {

  /**
   * 客户线路关系ID
   */
  @NotNull(message = "客户线路关系ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientLineId;

  /**
   * 客户线路关系名称
   */
  @NotBlank(message = "客户线路关系名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientLineName;

  /**
   * 线路Id
   */
  @NotNull(message = "线路Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long lineId;

  /**
   * 线路编号
   */
  @NotBlank(message = "线路编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineCode;

  /**
   * 线路名称
   */
  @NotBlank(message = "线路名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineName;

  /**
   * 线路类型
   */
  @NotBlank(message = "线路类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineType;

  /**
   * 车辆ID
   */
  @NotNull(message = "车辆ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleId;

  /**
   * 车辆编号
   */
  @NotBlank(message = "车辆编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleCode;

  /**
   * 司机ID
   */
  @NotNull(message = "司机ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long driverId;

  /**
   * 司机编号
   */
  @NotBlank(message = "司机编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverCode;

  /**
   * 司机名称
   */
  @NotBlank(message = "司机名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 线路规则
   */
  @NotBlank(message = "线路规则不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineRule;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 订单详情模板ID
   */
  @NotNull(message = "订单详情模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailTemplateId;

  /**
   * 订单详情模板
   */
  @NotBlank(message = "订单详情模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderDetailTemplate;

  /**
   * 订单部分详情模板ID
   */
  @NotNull(message = "订单部分详情模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPartialDetailTemplateId;

  /**
   * 订单部分详情模板
   */
  @NotBlank(message = "订单部分详情模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderPartialDetailTemplate;

  /**
   * 装箱清单模板ID
   */
  @NotNull(message = "装箱清单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long packingDetailTemplateId;

  /**
   * 装箱清单模板
   */
  @NotBlank(message = "装箱清单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packingDetailTemplate;

  /**
   * 面单模板ID
   */
  @NotNull(message = "面单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long faceBillTemplateId;

  /**
   * 面单模板
   */
  @NotBlank(message = "面单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String faceBillTemplate;

  /**
   * 附属面单模板ID
   */
  @NotNull(message = "附属面单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long subFaceBillTemplateId;

  /**
   * 附属面单模板
   */
  @NotBlank(message = "附属面单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subFaceBillTemplate;

  /**
   * 快递单模板ID
   */
  @NotNull(message = "快递单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressBillTemplateId;

  /**
   * 快递单模板
   */
  @NotBlank(message = "快递单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressBillTemplate;

  /**
   * 线路假期
   */
  @NotBlank(message = "线路假期不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineVacation;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;


}
