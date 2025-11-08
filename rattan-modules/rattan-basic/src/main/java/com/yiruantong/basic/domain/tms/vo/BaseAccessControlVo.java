package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseAccessControl;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 门禁信息视图对象 base_access_control
 *
 * @author YRT
 * @date 2024-12-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseAccessControl.class)
public class BaseAccessControlVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 门禁信息id
   */
  @ExcelProperty(value = "门禁信息id")
  private Long accessControlId;

  /**
   * 记录单号
   */
  @ExcelProperty(value = "记录单号")
  private String recordNo;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String carPlateNo;

  /**
   * 物料类型
   */
  @ExcelProperty(value = "物料类型")
  private String materialType;

  /**
   * 入场时间
   */
  @ExcelProperty(value = "入场时间")
  private Date enterTime;

  /**
   * 进场通道名称
   */
  @ExcelProperty(value = "进场通道名称")
  private String channelName;

  /**
   * 岗亭名称
   */
  @ExcelProperty(value = "岗亭名称")
  private String clientComputerName;

  /**
   * 通行证类
   */
  @ExcelProperty(value = "通行证类")
  private String passportTypeName;

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
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 最后登录IP
   */
  @ExcelProperty(value = "最后登录IP")
  private String loginIp;

  /**
   * 最后登录时间
   */
  @ExcelProperty(value = "最后登录时间")
  private Date loginDate;


}
