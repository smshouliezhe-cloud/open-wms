package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.TaskLog;
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


/**
 * 任务日志业务对象 task_log
 *
 * @author YRT
 * @date 2024-12-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskLog.class, reverseConvertGenerate = false)
public class TaskLogBo extends BaseEntity {

  /**
   * 日志ID
   */
  @NotNull(message = "日志ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long logId;

  /**
   * 配置ID
   */
  @NotNull(message = "配置ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long configId;

  /**
   * 平台名称
   */
  @NotBlank(message = "平台名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String platName;

  /**
   * 模块名称
   */
  @NotBlank(message = "模块名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String moduleName;

  /**
   * 单据ID
   */
  @NotNull(message = "单据ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 单号
   */
  @NotBlank(message = "单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 推送类型
   */
  @NotBlank(message = "推送类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushType;

  /**
   * 推送次数
   */
  @NotNull(message = "推送次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long pushCount;

  /**
   * 推送时间
   */
  @NotNull(message = "推送时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date pushDate;

  /**
   * 推送状态
   */
  @NotBlank(message = "推送状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushState;

  /**
   * 推送消息
   */
  @NotBlank(message = "推送消息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String resultMsg;

  /**
   * 推送结果
   */
  @NotBlank(message = "推送结果不能为空", groups = {AddGroup.class, EditGroup.class})
  private String resultJson;

  /**
   * 异常消息
   */
  @NotBlank(message = "异常消息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String exceptionMsg;

  /**
   * 推送数据
   */
  @NotBlank(message = "推送数据不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushData;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;


}
