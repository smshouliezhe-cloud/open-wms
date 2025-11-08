package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysPost;
import com.yiruantong.system.domain.permission.bo.SysPostBo;
import com.yiruantong.system.domain.permission.vo.SysPostVo;
import com.yiruantong.system.mapper.permission.SysPostMapper;
import com.yiruantong.system.service.permission.ISysPostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/post")
public class SysPostController extends AbstractController<SysPostMapper, SysPost, SysPostVo, SysPostBo> {
  private final ISysPostService postService;

  /**
   * 获取岗位列表
   */
//  @SaCheckPermission("system:post:list")
  @GetMapping("/list")
  public TableDataInfo<SysPostVo> list(SysPostBo post, PageQuery pageQuery) {
    return postService.selectPagePostList(post, pageQuery);
  }

  /**
   * 导出岗位列表
   */
  @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
//  @SaCheckPermission("system:post:export")
  @PostMapping("/export")
  public void export(SysPostBo post, HttpServletResponse response) {
    List<SysPostVo> list = postService.selectPostList(post);
    ExcelUtil.exportExcel(list, "岗位数据", SysPostVo.class, response);
  }

  /**
   * 根据岗位编号获取详细信息
   *
   * @param postId 岗位ID
   */
//  @SaCheckPermission("system:post:query")
  @GetMapping(value = "/{postId}")
  public R<SysPostVo> getInfo(@PathVariable Long postId) {
    return R.ok(postService.selectPostById(postId));
  }

  /**
   * 新增岗位
   */
//  @SaCheckPermission("system:post:add")
  @Log(title = "岗位管理", businessType = BusinessType.INSERT)
  @PostMapping
  public R<Void> add(@Validated @RequestBody SysPostBo post) {
    if (!postService.checkPostNameUnique(post)) {
      return R.fail("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (!postService.checkPostCodeUnique(post)) {
      return R.fail("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    return toAjax(postService.insertPost(post));
  }

  /**
   * 修改岗位
   */
//  @SaCheckPermission("system:post:edit")
  @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editData(@Validated @RequestBody SysPostBo post) {
    if (!postService.checkPostNameUnique(post)) {
      return R.fail("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (!postService.checkPostCodeUnique(post)) {
      return R.fail("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    } else if (B.isEqual(UserConstants.POST_DISABLE, post.getStatus())
      && postService.countUserPostById(post.getPostId()) > 0) {
      return R.fail("该岗位下存在已分配用户，不能禁用!");
    }
    return toAjax(postService.updatePost(post));
  }

  /**
   * 获取岗位选择框列表
   */
  @GetMapping("/optionSelect")
  public R<List<SysPostVo>> optionSelect() {
    SysPostBo postBo = new SysPostBo();
    postBo.setStatus(UserConstants.POST_NORMAL);
    List<SysPostVo> posts = postService.selectPostList(postBo);
    return R.ok(posts);
  }
}
