package com.yiruantong.system.domain.task.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.task.TaskLog;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 任务日志视图对象 task_log
 *
 * @author YRT
 * @date 2024-12-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskLog.class)
public class TaskLogVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 日志ID
   */
  @ExcelProperty(value = "日志ID")
  private Long logId;

  /**
   * 配置ID
   */
  @ExcelProperty(value = "配置ID")
  private Long configId;

  /**
   * 平台名称
   */
  @ExcelProperty(value = "平台名称")
  private String platName;

  /**
   * 模块名称
   */
  @ExcelProperty(value = "模块名称")
  private String moduleName;

  /**
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 单号
   */
  @ExcelProperty(value = "单号")
  private String billCode;

  /**
   * 推送类型
   */
  @ExcelProperty(value = "推送类型")
  private String pushType;

  /**
   * 推送次数
   */
  @ExcelProperty(value = "推送次数")
  private Long pushCount;

  /**
   * 推送时间
   */
  @ExcelProperty(value = "推送时间")
  private Date pushDate;

  /**
   * 推送状态
   */
  @ExcelProperty(value = "推送状态")
  private String pushState;

  /**
   * 推送消息
   */
  @ExcelProperty(value = "推送消息")
  private String resultMsg;

  /**
   * 推送结果
   */
  @ExcelProperty(value = "推送结果")
  private String resultJson;

  /**
   * 异常消息
   */
  @ExcelProperty(value = "异常消息")
  private String exceptionMsg;

  /**
   * 推送数据
   */
  @ExcelProperty(value = "推送数据")
  private String pushData;

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


}
