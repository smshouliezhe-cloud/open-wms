package com.yiruantong.common.mybatis.core.domain;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户基类
 *
 * @author YiRuanTong
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

  /**
   * 租户编号
   */
  private String tenantId;

}
