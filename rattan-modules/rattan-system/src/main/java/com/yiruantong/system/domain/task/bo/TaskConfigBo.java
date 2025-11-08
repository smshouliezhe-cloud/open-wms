package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.TaskConfig;
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
 * 任务配置业务对象 task_config
 *
 * @author YRT
 * @date 2024-12-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskConfig.class, reverseConvertGenerate = false)
public class TaskConfigBo extends BaseEntity {

  /**
   * 任务配置ID
   */
  @NotNull(message = "任务配置ID不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 接口名称
   */
  @NotBlank(message = "接口名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String apiName;

  /**
   * 任务类型
   */
  @NotBlank(message = "任务类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String taskType;

  /**
   * 任务开始日期
   */
  @NotNull(message = "任务开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date startDate;

  /**
   * 任务结束日期
   */
  @NotNull(message = "任务结束日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endDate;

  /**
   * 任务开始时间
   */
  @NotBlank(message = "任务开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private String startTime;

  /**
   * 任务结束时间
   */
  @NotBlank(message = "任务结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private String endTime;

  /**
   * 是否启用
   */
  @NotNull(message = "是否启用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 推送条件
   */
  @NotNull(message = "推送条件不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> pushWhere;

  /**
   * 查询接口
   */
  @NotBlank(message = "查询接口不能为空", groups = {AddGroup.class, EditGroup.class})
  private String prefixRouter;

  /**
   * 菜单ID
   */
  @NotNull(message = "菜单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 最大推送次数
   */
  @NotNull(message = "最大推送次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long maxPushCount;

  /**
   * 推送接口地址
   */
  @NotBlank(message = "推送接口地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushUrl;

  /**
   * 推送接口方法
   */
  @NotBlank(message = "推送接口方法不能为空", groups = {AddGroup.class, EditGroup.class})
  private String method;

  /**
   * appKey
   */
  @NotBlank(message = "appKey不能为空", groups = {AddGroup.class, EditGroup.class})
  private String appKey;

  /**
   * appSecrect
   */
  @NotBlank(message = "appSecrect不能为空", groups = {AddGroup.class, EditGroup.class})
  private String appSecrect;

  /**
   * grantType
   */
  @NotBlank(message = "grantType不能为空", groups = {AddGroup.class, EditGroup.class})
  private String grantType;

  /**
   * 状态值
   */
  @NotBlank(message = "状态值不能为空", groups = {AddGroup.class, EditGroup.class})
  private String code;

  /**
   * 最后执行结果
   */
  @NotBlank(message = "最后执行结果不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lastRunMsg;

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

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源ID
   */
  @NotBlank(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * ID字段名
   */
  @NotBlank(message = "ID字段名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String idField;

  /**
   * Code字段名
   */
  @NotBlank(message = "Code字段名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String codeField;

  /**
   * 排序字段
   */
  @NotBlank(message = "排序字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderbyField;

  /**
   * 排序方式
   */
  @NotBlank(message = "排序方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderbyType;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 状态字段
   */
  @NotBlank(message = "状态字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusField;

  /**
   * 手动推送地址
   */
  @NotBlank(message = "手动推送地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushApi;


}
