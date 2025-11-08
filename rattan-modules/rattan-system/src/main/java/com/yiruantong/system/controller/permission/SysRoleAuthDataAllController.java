package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysRoleAuthDataAll;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthDataAllBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthDataAllVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthDataAllMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全部数据权限
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/roleAuthDataAll")
public class SysRoleAuthDataAllController extends AbstractController<SysRoleAuthDataAllMapper, SysRoleAuthDataAll, SysRoleAuthDataAllVo, SysRoleAuthDataAllBo> {
}
