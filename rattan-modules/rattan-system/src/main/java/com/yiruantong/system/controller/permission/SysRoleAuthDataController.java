package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthDataBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthDataVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthDataMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据权限
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/roleAuthData")
public class SysRoleAuthDataController extends AbstractController<SysRoleAuthDataMapper, SysRoleAuthData, SysRoleAuthDataVo, SysRoleAuthDataBo> {
}
