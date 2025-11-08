package com.yiruantong.system.domain.tenant;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 系统授权对象 sys_client
 *
 * @author YiRuanTong
 * @date 2024-06-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_client", autoResultMap = true)
public class SysClient extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(value = "id")
  private Long id;

  /**
   * 客户端id
   */
  private String clientId;

  /**
   * 客户端key
   */
  private String clientKey;

  /**
   * 客户端秘钥
   */
  private String clientSecret;

  /**
   * 授权类型
   */
  private String grantType;

  /**
   * 设备类型
   */
  private String deviceType;

  /**
   * token活跃超时时间
   */
  private Long activeTimeout;

  /**
   * token固定超时
   */
  private Long timeout;

  /**
   * 状态
   */
  private Byte status;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
