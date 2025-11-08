package com.yiruantong.system.service.core.impl;

import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.sensitive.core.SensitiveService;
import com.yiruantong.common.tenant.helper.TenantHelper;
import org.springframework.stereotype.Service;

/**
 * 脱敏服务
 * 默认管理员不过滤
 * 需自行根据业务重写实现
 *
 * @author YiRuanTong
 * @version 3.6.0
 */
@Service
public class SysSensitiveServiceImpl implements SensitiveService {

  /**
   * 是否脱敏
   */
  @Override
  public boolean isSensitive() {
    if (TenantHelper.isEnable()) {
      return !LoginHelper.isSuperAdmin() && !LoginHelper.isTenantAdmin();
    }
    return !LoginHelper.isSuperAdmin();
  }

}
