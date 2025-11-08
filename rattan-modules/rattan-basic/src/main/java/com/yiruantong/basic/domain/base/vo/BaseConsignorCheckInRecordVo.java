package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 门店打卡记录视图对象 base_consignor_check_in_record
 *
 * @author YRT
 * @date 2025-01-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseConsignorCheckInRecord.class)
public class BaseConsignorCheckInRecordVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 打卡记录id
   */
  @ExcelProperty(value = "打卡记录id")
  private Long checkInRecordId;

  /**
   * 责任业务员
   */
  @ExcelProperty(value = "责任业务员")
  private String salesName;

  /**
   * 打卡地址
   */
  @ExcelProperty(value = "打卡地址")
  private String punchInAddress;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private String lng;

  /**
   * 纬度
   */
  @ExcelProperty(value = "纬度")
  private String lat;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

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
   * 拜访时间
   */
  @ExcelProperty(value = "拜访时间")
  private Date visitDate;

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
