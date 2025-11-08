package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseContainer;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 集装箱信息业务对象 base_container
 *
 * @author YRT
 * @date 2025-01-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseContainer.class, reverseConvertGenerate = false)
public class BaseContainerBo extends BaseEntity {

  /**
   * 集装箱ID
   */
  @NotNull(message = "集装箱ID不能为空", groups = { AddGroup.class, EditGroup.class })
  private Long containerId;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = { AddGroup.class, EditGroup.class })
  private String containerNo;

  /**
   * 集装箱类型
   */
  @NotBlank(message = "集装箱类型不能为空", groups = { AddGroup.class, EditGroup.class })
  private String containerType;

  /**
   * 尺寸
   */
  @NotBlank(message = "尺寸不能为空", groups = { AddGroup.class, EditGroup.class })
  private String size;

  /**
   * 交易编号
   */
  @NotBlank(message = "交易编号不能为空", groups = { AddGroup.class, EditGroup.class })
  private String containerSpellMode;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = { AddGroup.class, EditGroup.class })
  private String sealNo;

  /**
   * 预订舱长宽高(mm)
   */
  @NotBlank(message = "预订舱长宽高(mm)不能为空", groups = { AddGroup.class, EditGroup.class })
  private String cabinLwhBooking;

  /**
   * 实际订舱长宽高(mm)
   */
  @NotBlank(message = "实际订舱长宽高(mm)不能为空", groups = { AddGroup.class, EditGroup.class })
  private String cabinLwhActual;

  /**
   * 装箱后实际长宽高(mm)
   */
  @NotBlank(message = "装箱后实际长宽高(mm)不能为空", groups = { AddGroup.class, EditGroup.class })
  private String cabinLwhAfter;

  /**
   * 体积
   */
  @NotNull(message = "体积不能为空", groups = { AddGroup.class, EditGroup.class })
  private BigDecimal volume;

  /**
   * 自重(KGS)
   */
  @NotNull(message = "自重(KGS)不能为空", groups = { AddGroup.class, EditGroup.class })
  private BigDecimal weight;

  /**
   * 运输ID
   */
  @NotNull(message = "运输ID不能为空", groups = { AddGroup.class, EditGroup.class })
  private Long transportId;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = { AddGroup.class, EditGroup.class })
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = { AddGroup.class, EditGroup.class })
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = { AddGroup.class, EditGroup.class })
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = { AddGroup.class, EditGroup.class })
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = { AddGroup.class, EditGroup.class })
  private String deleteByName;

  /**
   * 运输类型
   */
  @NotBlank(message = "运输类型不能为空", groups = { AddGroup.class, EditGroup.class })
  private String transportationType;


}
