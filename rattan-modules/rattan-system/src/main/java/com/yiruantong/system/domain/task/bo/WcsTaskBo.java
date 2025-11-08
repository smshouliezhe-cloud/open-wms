package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.WcsTask;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * WCS接口业务对象 wcs_task
 *
 * @author YRT
 * @date 2025-01-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = WcsTask.class, reverseConvertGenerate = false)
public class WcsTaskBo extends BaseEntity {

  /**
   * 任务ID
   */
  @NotNull(message = "任务ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long wcsTaskId;

  /**
   * 任务类型
   */
  @NotBlank(message = "任务类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String wcsTaskType;

  /**
   * 来源主表ID
   */
  @NotNull(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long mainId;

  /**
   * 来源明细ID
   */
  @NotNull(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long detailId;

  /**
   * 执行单号
   */
  @NotBlank(message = "执行单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 货位
   */
  @NotBlank(message = "货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 生成日期
   */
  @NotNull(message = "生成日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 关联号
   */
  @NotBlank(message = "关联号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String relationCode;

  /**
   * 数量
   */
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal qty;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

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
   * 推送状态
   */
  @NotBlank(message = "推送状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushStatus;

  /**
   * 推送时间
   */
  @NotNull(message = "推送时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date pushDate;

  /**
   * 推送消息
   */
  @NotBlank(message = "推送消息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pushMsg;

  /**
   * 推送次数
   */
  @NotNull(message = "推送次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long pushCount;

  /**
   * 来源ID
   */
  @NotNull(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceId;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;

  /**
   * sn号
   */
  @NotBlank(message = "sn号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * wcs优先级
   */
  @NotBlank(message = "wcs优先级不能为空", groups = {AddGroup.class, EditGroup.class})
  private String itemGroup;


}
