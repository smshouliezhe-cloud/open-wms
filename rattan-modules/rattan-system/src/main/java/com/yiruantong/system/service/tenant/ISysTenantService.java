package com.yiruantong.system.service.tenant;

import com.yiruantong.system.domain.permission.dto.SaveTenantResultDto;
import com.yiruantong.system.domain.tenant.SysTenant;
import com.yiruantong.system.domain.tenant.bo.SysTenantBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantVo;
import jakarta.servlet.http.HttpServletRequest;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 租户Service接口
 *
 * @author YiRuanTong
 */
public interface ISysTenantService extends IServicePlus<SysTenant, SysTenantVo, SysTenantBo> {

  /**
   * 查询租户
   */
  SysTenantVo queryById(Long id);

  /**
   * 基于租户ID查询租户
   */
  SysTenantVo queryByTenantId(String tenantId);

  /**
   * 新增租户
   */
  Boolean insertByBo(SysTenantBo bo, SaveTenantResultDto saveTenantResultDto);

  /**
   * 修改租户状态
   */
  int updateTenantStatus(SysTenantBo bo);

  /**
   * 校验租户是否允许操作
   */
  void checkTenantAllowed(String tenantId);

  /**
   * 校验账号余额
   */
  boolean checkAccountBalance(String tenantId);

  /**
   * 校验有效期
   */
  boolean checkExpireTime(String tenantId);

  /**
   * 同步租户套餐
   */
  Boolean syncTenantPackage(String tenantId, Long packageId);

  /**
   * 根据手机号获取所有账套列表
   */
  List<SysTenantVo> getMyTenantList(String mobile, String password);

  /**
   * 判断是否存多个账套
   *
   * @param mobile      手机号
   * @param refTenantId
   * @param password
   * @return boolean
   */
  boolean isExistMultiTenant(String mobile, AtomicReference<String> refTenantId, String password);

  /**
   * 根据域名获取系统信息
   *
   * @return boolean
   */
  Map<String, Object> getTenantInfo(HttpServletRequest request) throws URISyntaxException;

  /**
   * 取消账套
   *
   * @param tenantId 账套ID
   * @return R
   */
  R<Void> cancelApp(String tenantId);

  /**
   * 同步基础数据
   *
   * @param targetTenantId
   * @return
   */
  R<Void> syncBasicData(String targetTenantId, List<Long> menuIdList);
}
