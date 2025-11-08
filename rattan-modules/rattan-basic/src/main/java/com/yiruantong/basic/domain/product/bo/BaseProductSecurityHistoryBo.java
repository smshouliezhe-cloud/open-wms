package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseProductSecurityHistory;
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
 * 防伪码轨迹业务对象 base_product_security_history
 *
 * @author YRT
 * @date 2024-04-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseProductSecurityHistory.class, reverseConvertGenerate = false)
public class BaseProductSecurityHistoryBo extends BaseEntity {

  /**
   * 历史ID
   */
  @NotNull(message = "历史ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long securityHistoryId;

  /**
   * 操作类型
   */
  @NotBlank(message = "操作类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String operationType;

  /**
   * 变更前状态
   */
  @NotBlank(message = "变更前状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fromStatus;

  /**
   * 变更后状态
   */
  @NotBlank(message = "变更后状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String toStatus;

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
   * 防伪标签id
   */
  @NotNull(message = "防伪标签id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long securityId;

  /**
   * 防伪码明细ID
   */
  @NotNull(message = "防伪码明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long securityDetailId;

  /**
   * 防伪码
   */
  @NotBlank(message = "防伪码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String securityCode;

  /**
   * 操作数量
   */
  @NotNull(message = "操作数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long quantity;


}
