package com.yiruantong.basic.domain.storage.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 容器管理业务对象 base_plate
 *
 * @author YiRuanTong
 * @date 2024-03-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlate.class, reverseConvertGenerate = false)
public class BasePlateBo extends BaseEntity {

  /**
   * 容器ID
   */
  @NotNull(message = "容器ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateId;

  /**
   * 容器编号
   */
  @NotBlank(message = "容器编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 容器类别
   */
  @NotBlank(message = "容器类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;

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
   * 已打印
   */
  @NotNull(message = "已打印不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isPrinted;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 是否使用
   */
  @NotNull(message = "是否使用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isUse;

  /**
   * 容器状态
   */
  @NotBlank(message = "容器状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateState;

  /**
   * 打印状态
   */
  @NotBlank(message = "打印状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String printState;

  /**
   * 打印次数
   */
  @NotNull(message = "打印次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long pringQuantity;

  /**
   * 已使用
   */
  @NotNull(message = "已使用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isUsing;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
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

  /**
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;


}
