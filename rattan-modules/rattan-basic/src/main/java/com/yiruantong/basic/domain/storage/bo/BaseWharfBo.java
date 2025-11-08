package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BaseWharf;
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
 * 码头管理业务对象 base_wharf
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseWharf.class, reverseConvertGenerate = false)
public class BaseWharfBo extends BaseEntity {

  /**
   * 码头ID
   */
  @NotNull(message = "码头ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long wharfId;

  /**
   * 码头编号
   */
  @NotBlank(message = "码头编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String wharfCode;

  /**
   * 码头名称
   */
  @NotBlank(message = "码头名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String wharfName;

  /**
   * 码头地址
   */
  @NotBlank(message = "码头地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String wharfAddress;

  /**
   * 公司名称
   */
  @NotBlank(message = "公司名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String corpName;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

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


}
