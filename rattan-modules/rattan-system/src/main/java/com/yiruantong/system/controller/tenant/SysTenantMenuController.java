package com.yiruantong.system.controller.tenant;

import cn.hutool.core.convert.Convert;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.yiruantong.system.domain.tenant.bo.SysTenantMenuBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantMenuVo;
import com.yiruantong.system.mapper.tenant.SysTenantMenuMapper;
import com.yiruantong.system.service.tenant.ISysTenantMenuService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 租户套餐菜单
 *
 * @author YRT
 * @date 2024-05-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant/tenantMenu")
public class SysTenantMenuController extends AbstractController<SysTenantMenuMapper, SysTenantMenu, SysTenantMenuVo, SysTenantMenuBo> {
  private final ISysTenantMenuService sysTenantMenuService;

  /**
   * 重置为标准菜单
   *
   * @param map 查询条件
   */
  @PostMapping("/resetDefault")
  public R<Void> resetDefault(@RequestBody Map<String, Object> map) {
    sysTenantMenuService.resetDefault(map);
    return R.ok();
  }

  /**
   * 重置为标准菜单
   *
   * @param map 查询条件
   */
  @PostMapping("/addMenu")
  public R<Void> addMenu(@RequestBody Map<String, Object> map) {
    Long packageId = Convert.toLong(map.get("packageId"));
    List<Long> menuIdList = Convert.toList(Long.class, map.get("menuIdList"));

    return sysTenantMenuService.addMenu(packageId, menuIdList);
  }
}
