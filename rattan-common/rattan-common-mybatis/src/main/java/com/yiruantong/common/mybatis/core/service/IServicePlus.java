package com.yiruantong.common.mybatis.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.mybatis.core.domain.bo.EditorBo;
import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IServicePlus<T, V, B> extends MPJBaseService<T> {
  /**
   * 查询前处理钩子函数
   */
  void beforePageQuery(PageQuery pageQuery);

  /**
   * 查询后处理钩子函数
   */
  void afterPageQuery(PageQuery pageQuery, IPage<V> iPage);

  /**
   * 编辑页面加载前钩子函数
   */
  void beforeEditor(EditorBo editorBo);

  /**
   * 编辑页面后处理后钩子函数
   */
  void afterEditor(EditorVo<V> editor);

  /**
   * 编辑页面保存前钩子函数
   *
   * @param saveEditorBo 保存提交bo数据
   */
  void beforeSaveEditor(SaveEditorBo<B> saveEditorBo);

  /**
   * 编辑页面保存后钩子函数
   *
   * @param saveEditorBo 保存提交bo数据
   * @param editor
   */
  void afterSaveEditor(SaveEditorBo<B> saveEditorBo, EditorVo<V> editor);

  /**
   * 分页查询
   *
   * @param pageQuery 分页条件
   * @return
   */
  TableDataInfo<V> pageList(PageQuery pageQuery);

  /**
   * 分页查询 - 返回List<Map<String, Object>>
   *
   * @param pageQuery 分页条件
   * @return
   */
  TableDataInfo<Map<String, Object>> pageMapList(PageQuery pageQuery);

  /**
   * 查询集合
   *
   * @param queryBoList 查询条件
   * @return 操作日志集合
   */
  List<V> selectList(List<QueryBo> queryBoList);

  /**
   * 查询集合
   *
   * @param queryWrapper 查询条件
   * @return 操作日志集合
   */
  List<V> selectList(Wrapper<T> queryWrapper);

  /**
   * 查询单条
   *
   * @queryBoList 查询条件
   */
  V selectOne(List<QueryBo> queryBoList);

  /**
   * 查询单条
   *
   * @queryWrapper 查询条件
   */
  V selectOne(Wrapper<T> queryWrapper);

  /**
   * 获取编辑页面数据
   *
   * @param editorBo 编辑数据bo
   * @return 返回单条数据
   */
  EditorVo<V> editor(EditorBo editorBo);

  /**
   * 保存编辑页面数据
   *
   * @param saveEditorBo 编辑数据bo
   * @return 返回单条数据
   */
  EditorVo<V> saveEditor(SaveEditorBo<B> saveEditorBo);

  /**
   * 根据ID
   *
   * @param id 主键ID
   * @return 返回单条数据
   */
  V selectById(Long id);

  /**
   * 根据IDs
   *
   * @param ids 主键IDs
   * @return 返回List数据
   */
  List<V> selectByIds(List<Long> ids);

  /**
   * 插入更新操作
   *
   * @param row 实体对象
   * @return 返回保存是否成功
   */
  Boolean insertOrUpdate(Map<String, Object> row);

  /**
   * 批量新增
   *
   * @param boList 新增信息BO
   * @return 结果
   */
  int batchSave(List<B> boList);

  /**
   * 新增
   *
   * @param bo 新增信息BO
   * @return 结果
   */
  int insert(B bo);

  /**
   * 修改
   *
   * @param bo 公告信息
   * @return 结果
   */
  V update(B bo);

  /**
   * 修改
   *
   * @param bo 修改信息
   * @return 结果
   */
  boolean updateExpandField(B bo);

  /**
   * 可以根据实体更新空值
   *
   * @param entity 实体
   * @return 结果
   */
  boolean updateWidthNull(T entity);

  /**
   * 可以根据map更新为空值
   *
   * @param row map对象
   * @return 结果
   */
  boolean updateWidthNull(Map<String, Object> row);

  /**
   * 根据ID删除信息，单条删除
   *
   * @param id 删除ID
   * @return 结果
   */
  int deleteById(Long id);

  /**
   * 批量删除系统操作日志
   *
   * @param Ids 需要删除的ID集合
   * @return 结果
   */
  int deleteByIds(Long[] Ids);

  /**
   * 清空操作
   */
  void clean();

  /**
   * 新增数据
   */
  Boolean insertByBo(B bo);

  /**
   * 修改数据
   */
  Boolean updateByBo(B bo);

  /**
   * 删除tree数据
   */
  Boolean deleteTreeNode(LoadTreeBo loadTreeBo);

  /**
   * 批量审核
   *
   * @param ids 前端参数
   */
  R<Void> multiAuditing(List<Long> ids);

  /**
   * 复制编辑页面数据
   *
   * @param saveEditorBo 复制信息bo
   */
  public R<Map<String, Object>> copyEditor(SaveEditorBo<B> saveEditorBo);

  /**
   * 分拣
   *
   * @param map 前端参数
   */
  R<Void> sorting(Map<String, Object> map);

  /**
   * 终止
   *
   * @param map 前端参数
   */
  R<Void> stop(Map<String, Object> map);

  /**
   * 开启
   *
   * @param map 前端参数
   */
  R<Void> open(Map<String, Object> map);

  /**
   * 导入数据
   *
   * @param inputStream 导入文件
   * @param importId    导入ID
   * @param request     请求参数
   * @param loginUser   当前用户信息
   */
  void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser);

  /**
   * 导出数据
   *
   * @param request   请求参数
   * @param pageQuery 导出参数
   */
  void exportData(HttpServletRequest request, HttpServletResponse response, String pageQuery) throws IOException;

  /**
   * 获取单条数据，不报错
   *
   * @param wrapper 查询条件
   * @return T
   */
  default T getOnly(LambdaQueryWrapper<T> wrapper) {
    wrapper.last("limit 1");

    return this.getOne(wrapper);
  }

  /**
   * 获取单条数据，不报错
   *
   * @param wrapper 查询条件
   * @return T
   */
  default T getOnly(MPJLambdaWrapper<T> wrapper) {
    wrapper.last("limit 1");

    return this.getOne(wrapper);
  }
}
