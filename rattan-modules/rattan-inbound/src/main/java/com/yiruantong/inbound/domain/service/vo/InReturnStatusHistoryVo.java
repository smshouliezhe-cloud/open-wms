package com.yiruantong.inbound.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.service.InReturnStatusHistory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 退货单流水视图对象 in_return_status_history
 *
 * @author YRT
 * @date 2024-01-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InReturnStatusHistory.class)
public class InReturnStatusHistoryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 历史ID
   */
  @ExcelProperty(value = "历史ID")
  private Long historyId;

  /**
   * 退货Id
   */
  @ExcelProperty(value = "退货Id")
  private Long returnId;

  /**
   * 状态类型
   */
  @ExcelProperty(value = "状态类型")
  private String statusType;

  /**
   * 操作类型
   */
  @ExcelProperty(value = "操作类型")
  private String operationType;

  /**
   * 变更前状态
   */
  @ExcelProperty(value = "变更前状态")
  private String fromStatus;

  /**
   * 变更后状态
   */
  @ExcelProperty(value = "变更后状态")
  private String toStatus;

  /**
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 单据编号
   */
  @ExcelProperty(value = "单据编号")
  private String billCode;

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
