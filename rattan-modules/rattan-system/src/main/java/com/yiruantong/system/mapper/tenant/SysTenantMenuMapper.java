package com.yiruantong.system.mapper.tenant;

import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.yiruantong.system.domain.tenant.vo.SysTenantMenuVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 租户套餐菜单Mapper接口
 *
 * @author YRT
 * @date 2024-05-02
 */
public interface SysTenantMenuMapper extends BaseMapperPlus<SysTenantMenu, SysTenantMenuVo> {

  /**
   * 根据用户ID查询菜单
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenu> selectMenuByUserId(Long packageId, Long userId);
}
