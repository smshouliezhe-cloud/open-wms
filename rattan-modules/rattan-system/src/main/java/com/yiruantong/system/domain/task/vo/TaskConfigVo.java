package com.yiruantong.system.domain.task.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.task.TaskConfig;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 任务配置视图对象 task_config
 *
 * @author YRT
 * @date 2024-12-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskConfig.class)
public class TaskConfigVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 任务配置ID
   */
  @ExcelProperty(value = "任务配置ID")
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
   * 接口名称
   */
  @ExcelProperty(value = "接口名称")
  private String apiName;

  /**
   * 任务类型
   */
  @ExcelProperty(value = "任务类型")
  private String taskType;

  /**
   * 任务开始日期
   */
  @ExcelProperty(value = "任务开始日期")
  private Date startDate;

  /**
   * 任务结束日期
   */
  @ExcelProperty(value = "任务结束日期")
  private Date endDate;

  /**
   * 任务开始时间
   */
  @ExcelProperty(value = "任务开始时间")
  private String startTime;

  /**
   * 任务结束时间
   */
  @ExcelProperty(value = "任务结束时间")
  private String endTime;

  /**
   * 是否启用
   */
  @ExcelProperty(value = "是否启用")
  private Byte enable;

  /**
   * 推送条件
   */
  @ExcelProperty(value = "推送条件")
  private Map<String, Object> pushWhere;

  /**
   * 查询接口
   */
  @ExcelProperty(value = "查询接口")
  private String prefixRouter;

  /**
   * 菜单ID
   */
  @ExcelProperty(value = "菜单ID")
  private Long menuId;

  /**
   * 最大推送次数
   */
  @ExcelProperty(value = "最大推送次数")
  private Long maxPushCount;

  /**
   * 推送接口地址
   */
  @ExcelProperty(value = "推送接口地址")
  private String pushUrl;

  /**
   * 推送接口方法
   */
  @ExcelProperty(value = "推送接口方法")
  private String method;

  /**
   * appKey
   */
  @ExcelProperty(value = "appKey")
  private String appKey;

  /**
   * appSecrect
   */
  @ExcelProperty(value = "appSecrect")
  private String appSecrect;

  /**
   * grantType
   */
  @ExcelProperty(value = "grantType")
  private String grantType;

  /**
   * 状态值
   */
  @ExcelProperty(value = "状态值")
  private String code;

  /**
   * 最后执行结果
   */
  @ExcelProperty(value = "最后执行结果")
  private String lastRunMsg;

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
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private String sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * ID字段名
   */
  @ExcelProperty(value = "ID字段名")
  private String idField;

  /**
   * Code字段名
   */
  @ExcelProperty(value = "Code字段名")
  private String codeField;

  /**
   * 排序字段
   */
  @ExcelProperty(value = "排序字段")
  private String orderbyField;

  /**
   * 排序方式
   */
  @ExcelProperty(value = "排序方式")
  private String orderbyType;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 状态字段
   */
  @ExcelProperty(value = "状态字段")
  private String statusField;

  /**
   * 手动推送地址
   */
  @ExcelProperty(value = "手动推送地址")
  private String pushApi;


}
