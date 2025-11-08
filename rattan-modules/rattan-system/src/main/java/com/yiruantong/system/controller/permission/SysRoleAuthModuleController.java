package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysRoleAuthModule;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthModuleBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthModuleVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthModuleMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限模块
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/roleAuthModule")
public class SysRoleAuthModuleController extends AbstractController<SysRoleAuthModuleMapper, SysRoleAuthModule, SysRoleAuthModuleVo, SysRoleAuthModuleBo> {
}
