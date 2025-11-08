package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
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
 * 防伪标签明细业务对象 base_product_security_detail
 *
 * @author YRT
 * @date 2024-04-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseProductSecurityDetail.class, reverseConvertGenerate = false)
public class BaseProductSecurityDetailBo extends BaseEntity {

  /**
   * 防伪码明细ID
   */
  @NotNull(message = "防伪码明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long securityDetailId;

  /**
   * 防伪标签id
   */
  @NotNull(message = "防伪标签id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long securityId;

  /**
   * 防伪码
   */
  @NotBlank(message = "防伪码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String securityCode;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String securityStatus;

  /**
   * 打印次数
   */
  @NotNull(message = "打印次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long printCount;

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
