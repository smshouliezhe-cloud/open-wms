package com.yiruantong.common.web.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.domain.bo.EditorBo;
import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.dto.UpdateBo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.redis.utils.QueueUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 *
 * @author YiRuanTong
 */
public abstract class AbstractController<M extends BaseMapperPlus<T, V>, T, V, B> extends BaseController {

  private final Class<T> boClass;
  private final Class<V> voClass;
  @Autowired
  protected ServiceImplPlus<M, T, V, B> pageService;

  public AbstractController() {
    ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
    Type[] actualTypeArguments = type.getActualTypeArguments();
    boClass = (Class<T>) actualTypeArguments[1];
    voClass = (Class<V>) actualTypeArguments[2];
  }

  /**
   * 查询全部
   *
   * @param queryBoList 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectList")
  public R<List<V>> selectList(@RequestBody List<QueryBo> queryBoList) {
    List<V> list = pageService.selectList(queryBoList);
    return R.ok(list);
  }

  /**
   * 查询全部
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/queryList")
  public R<List<V>> queryList(@RequestBody PageQuery pageQuery) {
    List<V> list = pageService.selectList(pageQuery.getQueryBoList());
    return R.ok(list);
  }

  /**
   * 查询全部
   *
   * @param queryBoList 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getMapList")
  public R<List<Map<String, Object>>> getMapList(@RequestBody List<QueryBo> queryBoList) {
    List<Map<String, Object>> list = pageService.getMapList(queryBoList);
    return R.ok(list);
  }

  /**
   * 查询单条
   *
   * @param queryBoList 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectOne")
  public R<V> selectOne(@Validated
                        @NotNull(message = "查询条件不能为空")
                        @Size(min = 1, message = "查询条件大小不可空")
                        @RequestBody List<QueryBo> queryBoList) {
    V one = pageService.selectOne(queryBoList);
    return R.ok(one);
  }

  /**
   * 查询单条
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getOne")
  public R<V> getOne(@Validated
                     @NotNull(message = "查询条件不能为空")
                     @RequestBody PageQuery pageQuery) {
    V one = pageService.getOne(pageQuery);
    return R.ok(one);
  }

  /**
   * 查询单条
   *
   * @param queryBoList 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getMap")
  public R<Map<String, Object>> getMap(@RequestBody List<QueryBo> queryBoList) {
    Map<String, Object> one = pageService.getMap(queryBoList);
    return R.ok(one);
  }

  /**
   * 分页查询
   *
   * @param pageQuery 分页参数
   * @return 返回查询列表数据
   */
  @PostMapping("/pageList")
  public TableDataInfo<V> pageList(@RequestBody PageQuery pageQuery) {
    return pageService.pageList(pageQuery);
  }

  /**
   * 分页查询
   *
   * @param pageQuery 分页参数
   * @return 返回查询列表数据
   */
  @PostMapping("/pageMapList")
  public TableDataInfo<Map<String, Object>> pageMapList(@RequestBody PageQuery pageQuery) {
    return pageService.pageMapList(pageQuery);
  }

  /**
   * 获取详情页面查询
   *
   * @param editorBo 加载编辑页面参数
   * @return 返回编辑页面数据
   */
  @PostMapping("/editor")
  public R<EditorVo<V>> editor(@RequestBody EditorBo editorBo)
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    final EditorVo<V> editor = pageService.editor(editorBo);
    return R.ok(editor);
  }

  /**
   * 保存详情页面查询
   *
   * @param saveEditorBo 保存编辑页面参数
   * @return 返回编辑页面数据
   */
  @Log(title = "保存数据", businessType = BusinessType.SAVE)
  @PostMapping("/saveEditor")
  public R<EditorVo<V>> saveEditor(@RequestBody SaveEditorBo<B> saveEditorBo) {
    final EditorVo<V> editor = pageService.saveEditor(saveEditorBo);

    return R.ok(editor);
  }

  /**
   * 根据通知公告编号获取详细信息
   *
   * @param id ID
   */
  @PostMapping(value = "selectById/{id}")
  public R<V> selectById(@PathVariable Long id) {
    return R.ok(pageService.selectById(id));
  }

  /**
   * 批量插入数据
   */
  @Log(title = "批量插入数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/batchSave")
  public R<Map<String, Object>> batchSave(@Validated @RequestBody List<B> boList) {
    var result = pageService.batchSave(boList);
    return result >= NumberUtils.ONE ? R.ok(new HashMap<>()) : R.fail();
  }

  /**
   * 新增数据
   */
  @Log(title = "插入数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/insert")
  public R<Void> insert(@Validated @RequestBody B bo) {
    return toAjax(pageService.insert(bo));
  }

  /**
   * 修改数据
   */
  @Log(title = "修改数据", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PutMapping("/put")
  public R<V> edit(@Validated @RequestBody B bo) {
    return R.ok(pageService.update(bo));
  }

  /**
   * 修改数据
   */
  @Log(title = "修改数据", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping("/update")
  public R<V> update(@Validated @RequestBody B bo) {
    return R.ok(pageService.update(bo));
  }

  /**
   * 修改扩展字段
   */
  @Log(title = "修改扩展字段", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping("/updateExpandField")
  public R<Boolean> updateExpandField(@Validated @RequestBody B bo) {
    return R.ok(pageService.updateExpandField(bo));
  }

  /**
   * 修改数据
   */
  @Log(title = "修改数据", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping("/update-multi-condition")
  public R<Void> update(@Validated @RequestBody UpdateBo updateBo) {
    return toAjax(pageService.update(updateBo));
  }

  /**
   * 批量删除操作日志记录
   *
   * @param ids 日志ids
   */
  @Log(title = "删除数据", businessType = BusinessType.DELETE)
  @DeleteMapping("remove/{ids}")
  public R<Void> remove(@PathVariable Long[] ids) {
    return toAjax(pageService.deleteByIds(ids));
  }

  /**
   * 清理操作日志记录
   */
  @Log(title = "清空数据", businessType = BusinessType.CLEAN)
  @DeleteMapping("/clean")
  public R<Void> clean() {
    pageService.clean();
    return R.ok();
  }

  /**
   * 删除tree结构数据
   */
  @Log(title = "删除数据", businessType = BusinessType.DELETE)
  @PostMapping("/deleteTreeNode")
  public R<Void> deleteTreeNode(@RequestBody LoadTreeBo loadTreeBo) {
    boolean isDel = pageService.deleteTreeNode(loadTreeBo);

    return isDel ? R.ok() : R.fail();
  }

  /**
   * 保存详情页面查询
   *
   * @param saveEditorBo 复制编辑页面参数
   * @return 返回复制影响行数
   */
  @Log(title = "复制数据", businessType = BusinessType.SAVE)
  @PostMapping("/copyEditor")
  public R<Map<String, Object>> copyEditor(@RequestBody SaveEditorBo<B> saveEditorBo) {
    return pageService.copyEditor(saveEditorBo);
  }

  /**
   * 批量审核
   *
   * @param ids 审核参数
   * @return 返回审核结果
   */
  @Log(title = "审核数据", businessType = BusinessType.AUDIT)
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return pageService.multiAuditing(ids);
  }

  /**
   * 批量反审核
   *
   * @param map 反审核参数
   * @return 返回审核结果
   */
  @Log(title = "反审核数据", businessType = BusinessType.AUDIT)
  @PostMapping("/reMultiAuditing")
  public R<Void> reMultiAuditing(@RequestBody Map<String, Object> map) {
    return pageService.reMultiAuditing(map);
  }

  /**
   * 分拣
   *
   * @param map 分拣参数
   * @return 返回分拣结果
   */
  @PostMapping("/sorting")
  @Log(title = "分拣数据", businessType = BusinessType.SORTING)
  public R<Void> sorting(@RequestBody Map<String, Object> map) {
    return pageService.sorting(map);
  }

  /**
   * 终止
   *
   * @param map 终止参数
   * @return 返回终止结果
   */
  @Log(title = "终止数据", businessType = BusinessType.STOP)
  @PostMapping("/stop")
  public R<Void> stop(@RequestBody Map<String, Object> map) {
    return pageService.stop(map);
  }

  /**
   * 开启
   *
   * @param map 开启参数
   * @return 返回开启结果
   */
  @Log(title = "开启数据", businessType = BusinessType.OPEN)
  @PostMapping("/open")
  public R<Void> open(@RequestBody Map<String, Object> map) {
    return pageService.open(map);
  }

  /**
   * 批量导入
   *
   * @param file 审核参数
   */
  @Log(title = "导入数据", businessType = BusinessType.IMPORT)
  @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public R<Void> importData(@RequestPart("file") MultipartFile file, Long importId, HttpServletRequest request) {
    String key = request.getParameter("key");
    try {
      pageService.importData(file.getInputStream(), importId, request, LoginHelper.getLoginUser());
    } catch (Exception e) {
      QueueUtils.addQueueObject(key, "错误：" + e);
      QueueUtils.getClient().getQueue(key).expire(Instant.now().plus(10, ChronoUnit.MINUTES)); // 设置过期时间
      QueueUtils.addQueueObject(key, "-1");
    }
    return R.ok("开始执行...");
  }

  /**
   * 导出生成表租户数据列表
   */
//  @SaCheckPermission("generator:tableTenant:export")
//  @Log(title = "导出数据", businessType = BusinessType.EXPORT)
//  @PostMapping("/exportData")
//  public void exportData(List<QueryBo> queryBoList, HttpServletResponse response) {
//    List<V> list = pageService.selectList(queryBoList);
//    ExcelUtil.exportExcel(list, "数据", voClass, response);
//  }
}
