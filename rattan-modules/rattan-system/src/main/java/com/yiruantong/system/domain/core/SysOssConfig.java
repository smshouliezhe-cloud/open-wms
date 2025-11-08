package com.yiruantong.system.domain.core;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 对象存储配置对象 sys_oss_config
 *
 * @author YiRuanTong
 * @date 2024-08-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oss_config", autoResultMap = true)
public class SysOssConfig extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 主建
   */
  @TableId(value = "oss_config_id")
  private Long ossConfigId;

  /**
   * 配置key
   */
  private String configKey;

  /**
   * accessKey
   */
  private String accessKey;

  /**
   * 秘钥
   */
  private String secretKey;

  /**
   * 桶名称
   */
  private String bucketName;

  /**
   * 前缀
   */
  private String prefix;

  /**
   * 访问站点
   */
  private String endpoint;

  /**
   * 自定义域名
   */
  private String domain;

  /**
   * 是否https
   */
  private Integer isHttps;

  /**
   * 域
   */
  private String region;

  /**
   * 桶权限类型(0=private 1=public 2=custom)
   */
  private String accessPolicy;

  /**
   * 是否默认
   */
  private Byte status;

  /**
   * 扩展字段
   */
  private String ext1;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
