package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysDeptPost;
import com.yiruantong.system.domain.permission.bo.SysDeptPostBo;
import com.yiruantong.system.domain.permission.vo.SysDeptPostVo;
import com.yiruantong.system.mapper.permission.SysDeptPostMapper;
import com.yiruantong.system.service.permission.ISysDeptPostService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门岗位设置
 *
 * @author YRT
 * @date 2024-07-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/deptPost")
public class SysDeptPostController extends AbstractController<SysDeptPostMapper, SysDeptPost, SysDeptPostVo, SysDeptPostBo> {
  private final ISysDeptPostService sysDeptPostService;

}
