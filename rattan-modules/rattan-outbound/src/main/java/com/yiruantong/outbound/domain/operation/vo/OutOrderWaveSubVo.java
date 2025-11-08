package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderWaveSub;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 子波次视图对象 out_order_wave_sub
 *
 * @author YRT
 * @date 2024-09-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderWaveSub.class)
public class OutOrderWaveSubVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long subId;

  /**
   * 子波次单号
   */
  @ExcelProperty(value = "子波次单号")
  private String subOrderWaveCode;

  /**
   * 波次ID
   */
  @ExcelProperty(value = "波次ID")
  private Long orderWaveId;

  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;

  /**
   * 子波次状态
   */
  @ExcelProperty(value = "子波次状态")
  private String subWaveStatus;

  /**
   * 拣货人ID
   */
  @ExcelProperty(value = "拣货人ID")
  private Long pickUserId;

  /**
   * 拣货人
   */
  @ExcelProperty(value = "拣货人")
  private String pickNickName;

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
