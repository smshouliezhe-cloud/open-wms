package com.yiruantong.system.domain.core;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数配置表 sys_config
 *
 * @author YiRuanTong
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_config", autoResultMap = true)
public class SysConfig extends TenantEntity {

  /**
   * 参数主键
   */
  @TableId(value = "config_id")
  private Long configId;

  /**
   * 参数名称
   */
  private String configName;

  /**
   * 参数键名
   */
  private String configKey;

  /**
   * 参数键值
   */
  private String configValue;

  /**
   * 系统内置（Y是 N否）
   */
  private String configType;

  /**
   * 备注
   */
  private String remark;

}
