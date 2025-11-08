package com.yiruantong.system.domain.tenant.bo;

import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 系统授权业务对象 sys_client
 *
 * @author YiRuanTong
 * @date 2024-06-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysClient.class, reverseConvertGenerate = false)
public class SysClientBo extends BaseEntity {

  /**
   * id
   */
  @NotNull(message = "id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long id;

  /**
   * 客户端id
   */
  @NotBlank(message = "客户端id不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientId;

  /**
   * 客户端key
   */
  @NotBlank(message = "客户端key不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientKey;

  /**
   * 客户端秘钥
   */
  @NotBlank(message = "客户端秘钥不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientSecret;

  /**
   * 授权类型
   */
  @NotBlank(message = "授权类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String grantType;

  /**
   * 设备类型
   */
  @NotBlank(message = "设备类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deviceType;

  /**
   * token活跃超时时间
   */
  @NotNull(message = "token活跃超时时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long activeTimeout;

  /**
   * token固定超时
   */
  @NotNull(message = "token固定超时不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long timeout;

  /**
   * 状态
   */
  @NotNull(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte status;

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
