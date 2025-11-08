package com.yiruantong.system.service.permission.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.permission.SysPost;
import com.yiruantong.system.domain.permission.SysUserPost;
import com.yiruantong.system.domain.permission.bo.SysPostBo;
import com.yiruantong.system.domain.permission.vo.SysPostVo;
import com.yiruantong.system.mapper.permission.SysPostMapper;
import com.yiruantong.system.mapper.permission.SysUserPostMapper;
import com.yiruantong.system.service.permission.ISysPostService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 岗位信息 服务层处理
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysPostServiceImpl extends ServiceImplPlus<SysPostMapper, SysPost, SysPostVo, SysPostBo> implements ISysPostService {

  private final SysPostMapper baseMapper;
  private final SysUserPostMapper userPostMapper;

  @Override
  public boolean afterDelete(Long[] ids) {
    // 删除用户岗位关系
    LambdaQueryWrapper<SysUserPost> userPostLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userPostLambdaQueryWrapper.in(SysUserPost::getPostId, ids);
    userPostMapper.delete(userPostLambdaQueryWrapper);

    return super.afterDelete(ids);
  }

  @Override
  public TableDataInfo<SysPostVo> selectPagePostList(SysPostBo post, PageQuery pageQuery) {
    LambdaQueryWrapper<SysPost> lqw = buildQueryWrapper(post);
    Page<SysPostVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
    return TableDataInfo.build(page);
  }

  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @return 岗位信息集合
   */
  @Override
  public List<SysPostVo> selectPostList(SysPostBo post) {
    LambdaQueryWrapper<SysPost> lqw = buildQueryWrapper(post);
    return baseMapper.selectVoList(lqw);
  }

  private LambdaQueryWrapper<SysPost> buildQueryWrapper(SysPostBo bo) {
    LambdaQueryWrapper<SysPost> lqw = Wrappers.lambdaQuery();
    lqw.like(StringUtils.isNotBlank(bo.getPostCode()), SysPost::getPostCode, bo.getPostCode());
    lqw.like(StringUtils.isNotBlank(bo.getPostName()), SysPost::getPostName, bo.getPostName());
    lqw.eq(NumberUtils.isValidNumber(bo.getStatus()), SysPost::getStatus, bo.getStatus());
    lqw.orderByAsc(SysPost::getPostSort);
    return lqw;
  }

  /**
   * 查询所有岗位
   *
   * @return 岗位列表
   */
  @Override
  public List<SysPostVo> selectPostAll() {
    return baseMapper.selectVoList(new QueryWrapper<>());
  }

  /**
   * 通过岗位ID查询岗位信息
   *
   * @param postId 岗位ID
   * @return 角色对象信息
   */
  @Override
  public SysPostVo selectPostById(Long postId) {
    return baseMapper.selectVoById(postId);
  }

  /**
   * 根据用户ID获取岗位选择框列表
   *
   * @param userId 用户ID
   * @return 选中岗位ID列表
   */
  @Override
  public List<Long> selectPostListByUserId(Long userId) {
    return baseMapper.selectPostListByUserId(userId);
  }

  /**
   * 校验岗位名称是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  @Override
  public boolean checkPostNameUnique(SysPostBo post) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysPost>()
      .eq(SysPost::getPostName, post.getPostName())
      .ne(ObjectUtil.isNotNull(post.getPostId()), SysPost::getPostId, post.getPostId()));
    return !exist;
  }

  /**
   * 校验岗位编码是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  @Override
  public boolean checkPostCodeUnique(SysPostBo post) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysPost>()
      .eq(SysPost::getPostCode, post.getPostCode())
      .ne(ObjectUtil.isNotNull(post.getPostId()), SysPost::getPostId, post.getPostId()));
    return !exist;
  }

  /**
   * 通过岗位ID查询岗位使用数量
   *
   * @param postId 岗位ID
   * @return 结果
   */
  @Override
  public long countUserPostById(Long postId) {
    return userPostMapper.selectCount(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getPostId, postId));
  }

  /**
   * 删除岗位信息
   *
   * @param postId 岗位ID
   * @return 结果
   */
  @Override
  public int deletePostById(Long postId) {
    return baseMapper.deleteById(postId);
  }

  /**
   * 批量删除岗位信息
   *
   * @param postIds 需要删除的岗位ID
   * @return 结果
   */
  @Override
  public int deletePostByIds(Long[] postIds) {
    for (Long postId : postIds) {
      SysPost post = baseMapper.selectById(postId);
      if (countUserPostById(postId) > 0) {
        throw new ServiceException(String.format("%1$s已分配，不能删除!", post.getPostName()));
      }
    }
    return baseMapper.deleteBatchIds(Arrays.asList(postIds));
  }

  /**
   * 新增保存岗位信息
   *
   * @param bo 岗位信息
   * @return 结果
   */
  @Override
  public int insertPost(SysPostBo bo) {
    SysPost post = MapstructUtils.convert(bo, SysPost.class);
    return baseMapper.insert(post);
  }

  /**
   * 修改保存岗位信息
   *
   * @param bo 岗位信息
   * @return 结果
   */
  @Override
  public int updatePost(SysPostBo bo) {
    SysPost post = MapstructUtils.convert(bo, SysPost.class);
    return baseMapper.updateById(post);
  }

  @Override
  public SysPost getByName(String postName) {
    LambdaQueryWrapper<SysPost> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysPost::getPostName, postName);
    return this.getOnly(lambdaQueryWrapper);
  }

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysPostBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysPost sysPost = this.getById(saveEditorBo.getIdValue());

    // 保存主表
    sysPost.setPostId(null);
    this.save(sysPost);

    return R.ok("复制成功");
  }
}
