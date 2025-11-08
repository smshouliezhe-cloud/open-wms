package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysUserPost;
import com.yiruantong.system.domain.permission.bo.SysUserPostBo;
import com.yiruantong.system.domain.permission.vo.SysUserPostVo;
import com.yiruantong.system.mapper.permission.SysUserPostMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户与岗位关联
 *
 * @author YRT
 * @date 2024-07-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/userPost")
public class SysUserPostController extends AbstractController<SysUserPostMapper, SysUserPost, SysUserPostVo, SysUserPostBo> {
}
