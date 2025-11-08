package com.yiruantong.system.domain.core.bo;

import com.yiruantong.system.domain.core.SysOssConfig;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 对象存储配置业务对象 sys_oss_config
 *
 * @author YiRuanTong
 * @date 2024-08-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysOssConfig.class, reverseConvertGenerate = false)
public class SysOssConfigBo extends BaseEntity {

  /**
   * 主建
   */
  @NotNull(message = "主建不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long ossConfigId;

  /**
   * 配置key
   */
  @NotBlank(message = "配置key不能为空", groups = {AddGroup.class, EditGroup.class})
  private String configKey;

  /**
   * accessKey
   */
  @NotBlank(message = "accessKey不能为空", groups = {AddGroup.class, EditGroup.class})
  private String accessKey;

  /**
   * 秘钥
   */
  @NotBlank(message = "秘钥不能为空", groups = {AddGroup.class, EditGroup.class})
  private String secretKey;

  /**
   * 桶名称
   */
  @NotBlank(message = "桶名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bucketName;

  /**
   * 前缀
   */
  @NotBlank(message = "前缀不能为空", groups = {AddGroup.class, EditGroup.class})
  private String prefix;

  /**
   * 访问站点
   */
  @NotBlank(message = "访问站点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String endpoint;

  /**
   * 自定义域名
   */
  @NotBlank(message = "自定义域名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String domain;

  /**
   * 是否https
   */
  @NotNull(message = "是否https不能为空", groups = {AddGroup.class, EditGroup.class})
  private Integer isHttps;

  /**
   * 域
   */
  @NotBlank(message = "域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String region;

  /**
   * 桶权限类型(0=private 1=public 2=custom)
   */
  @NotBlank(message = "桶权限类型(0=private 1=public 2=custom)不能为空", groups = {AddGroup.class, EditGroup.class})
  private String accessPolicy;

  /**
   * 是否默认
   */
  @NotNull(message = "是否默认不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte status;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ext1;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

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
