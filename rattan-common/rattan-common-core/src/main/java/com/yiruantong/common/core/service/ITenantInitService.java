package com.yiruantong.common.core.service;

/**
 * 多租户账套初始化
 */
public interface ITenantInitService {
  /**
   * 租户数据拷贝
   *
   * @param fromPackageId  来源套餐ID
   * @param targetTenantId 目标租户ID
   * @param targetUserId   目标用户ID
   */
  default void tenantCopy(Long fromPackageId, String targetTenantId, Long targetUserId) {
  }

  /**
   * 清理数据
   */
  default void tenantClean(String tenantId, Long userId) {
  }
}
