package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysUserRole;
import com.yiruantong.system.domain.permission.bo.SysUserRoleBo;
import com.yiruantong.system.domain.permission.vo.SysUserRoleVo;
import com.yiruantong.system.mapper.permission.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户和角色关联
 *
 * @author YRT
 * @date 2024-05-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/userRole")
public class SysUserRoleController extends AbstractController<SysUserRoleMapper, SysUserRole, SysUserRoleVo, SysUserRoleBo> {
}
