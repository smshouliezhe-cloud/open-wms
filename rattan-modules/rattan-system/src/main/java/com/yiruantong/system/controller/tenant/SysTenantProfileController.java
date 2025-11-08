package com.yiruantong.system.controller.tenant;

import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.bo.SysTenantProfileBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantProfileVo;
import com.yiruantong.system.mapper.tenant.SysTenantProfileMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户档案
 *
 * @author YRT
 * @date 2024-05-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant/tenantProfile")
public class SysTenantProfileController extends AbstractController<SysTenantProfileMapper, SysTenantProfile, SysTenantProfileVo, SysTenantProfileBo> {
}
