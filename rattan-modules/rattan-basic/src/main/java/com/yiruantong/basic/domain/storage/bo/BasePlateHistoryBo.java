package com.yiruantong.basic.domain.storage.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlateHistory;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.util.Date;
import java.util.Map;


/**
 * 容器使用轨迹业务对象 base_plateHistory
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateHistory.class, reverseConvertGenerate = false)
public class BasePlateHistoryBo extends BaseEntity {

  /**
   * 容器历史ID
   */
  @NotNull(message = "容器历史ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateHistoryId;

  /**
   * 单据ID
   */
  @NotNull(message = "单据ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 单据编号
   */
  @NotBlank(message = "单据编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 容器Id
   */
  @NotNull(message = "容器Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateId;

  /**
   * 容易编号
   */
  @NotBlank(message = "容易编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 容器类型
   */
  @NotBlank(message = "容器类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

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
   * 操作类型
   */
  @NotBlank(message = "操作类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String actionType;

  /**
   * 货位
   */
  @NotBlank(message = "货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 使用前状态
   */
  @NotBlank(message = "使用前状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String beforeStatus;

  /**
   * 使用后状态
   */
  @NotBlank(message = "使用后状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String afterStatus;

  /**
   * 操作时间
   */
  @NotNull(message = "操作时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date operateDate;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

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


}
