package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BaseShelveRegular;
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

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 商品上架策略业务对象 base_shelve_regular
 *
 * @author YiRuanTong
 * @date 2024-01-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseShelveRegular.class, reverseConvertGenerate = false)
public class BaseShelveRegularBo extends BaseEntity {

  /**
   * 规则ID
   */
  @NotNull(message = "规则ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long shelveRegularId;

  /**
   * 规则名称
   */
  @NotBlank(message = "规则名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveRegularName;

  /**
   * 货位类型
   */
  @NotNull(message = "货位类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long positionType;

  /**
   * jsondata
   */
  @NotBlank(message = "jsondata不能为空", groups = {AddGroup.class, EditGroup.class})
  private String jsonData;

  /**
   * 适用仓库ID
   */
  @NotNull(message = "适用仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;


}
