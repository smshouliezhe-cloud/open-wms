package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InArrivalProcess;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 到货加工视图对象 in_arrival_process
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InArrivalProcess.class)
public class InArrivalProcessVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 加工ID
   */
  @ExcelProperty(value = "加工ID")
  private Long processId;

  /**
   * 加工编号
   */
  @ExcelProperty(value = "加工编号")
  private String processCode;

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
   * 采购单ID
   */
  @ExcelProperty(value = "采购单ID")
  private Long orderId;

  /**
   * 采购单编号
   */
  @ExcelProperty(value = "采购单编号")
  private String orderCode;

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
   * 加工人ID
   */
  @ExcelProperty(value = "加工人ID")
  private Long userId;

  /**
   * 加工人
   */
  @ExcelProperty(value = "加工人")
  private String nickName;

  /**
   * 完成时间
   */
  @ExcelProperty(value = "完成时间")
  private Date endDate;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Long auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 审核备注
   */
  @ExcelProperty(value = "审核备注")
  private String auditRemark;

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
