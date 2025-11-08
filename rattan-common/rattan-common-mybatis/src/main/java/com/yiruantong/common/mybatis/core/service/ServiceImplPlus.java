package com.yiruantong.common.mybatis.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.constant.CommonFields;
import com.yiruantong.common.mybatis.core.domain.bo.*;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.dto.UpdateBo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.redis.utils.QueueUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceImplPlus<M extends BaseMapperPlus<T, V>, T, V, BO> extends MPJBaseServiceImpl<M, T> {
  //#region 常量
  private final static List<String> ignoreList = new ArrayList<>();

  static {
    ignoreList.add(CommonFields.CREATE_TIME);
    ignoreList.add(CommonFields.CREATE_BY_NAME);
    ignoreList.add(CommonFields.CREATE_BY);
    ignoreList.add(CommonFields.UPDATE_TIME);
    ignoreList.add(CommonFields.UPDATE_BY_NAME);
    ignoreList.add(CommonFields.UPDATE_BY);
    ignoreList.add(CommonFields.TENANT_ID);
    ignoreList.add(CommonFields.DEL_FLAG);
  }

  @Resource
  private IDataAuthService dataAuthService;
  //#endregion

  //#region 事件方法

  /**
   * 列表加载前钩子函数
   *
   * @param pageQuery 加载参数
   */
  public void beforePageQuery(PageQuery pageQuery) {
  }

  /**
   * 查询后处理钩子函数
   *
   * @param iPage 分页参数
   */
  public void afterPageQuery(PageQuery pageQuery, IPage<V> iPage) {
  }

  /**
   * 编辑页面加载前钩子函数
   *
   * @param editorBo 加载参数
   */
  public void beforeEditor(EditorBo editorBo) {
  }

  /**
   * 编辑页面后处理后钩子函数
   *
   * @param editor 编辑内容
   */
  public void afterEditor(EditorVo<V> editor) {

  }

  /**
   * 列表加载前钩子函数
   *
   * @param pageQuery 加载数据
   */
  public void beforePageList(PageQuery pageQuery) {
  }

  /**
   * 列表加载后钩子函数
   *
   * @param pageQuery      查询条件
   * @param tableDataInfoV 返回数据
   */
  public void afterPageList(PageQuery pageQuery, TableDataInfo<V> tableDataInfoV) {
  }

  /**
   * 编辑页面保存前钩子函数
   *
   * @param saveEditorBo 保存提交bo数据
   */
  public void beforeSaveEditor(SaveEditorBo<BO> saveEditorBo) {
  }

  /**
   * 编辑页面保存前钩子函数2
   *
   * @param saveEditorBo 保存提交bo数据
   * @param entity       实体
   */
  public void beforeSaveEditor2(SaveEditorBo<BO> saveEditorBo, T entity) {
  }

  /**
   * 编辑页面保存后钩子函数
   *
   * @param saveEditorBo 保存提交bo数据
   * @param editor       编辑内容
   */
  public void afterSaveEditor(SaveEditorBo<BO> saveEditorBo, EditorVo<V> editor) {
  }

  /**
   * 删除前事件
   *
   * @param ids 删除明细ID
   */
  public boolean beforeDelete(Long[] ids) {
    return true;
  }


  /**
   * 删除后事件
   *
   * @param ids 删除明细ID
   */
  public boolean afterDelete(Long[] ids) {
    return true;
  }

  /**
   * 自定义编码规则钩子函数
   *
   * @param saveEditorBo 保存提交bo数据
   */
  public boolean customCodeRegular(SaveEditorBo<BO> saveEditorBo, MetaObject metaObject) {
    return false;
  }

  //#endregion

  //#region pageList 分页查询

  /**
   * 分页查询
   */
  public TableDataInfo<V> pageList(PageQuery pageQuery) {
    beforePageQuery(pageQuery);
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    this.beforePageList(pageQuery);
    String tableName = TableInfoHelper.getTableInfo(super.getEntityClass()).getTableName();
    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    // 默认按主键进行排序
    if (ObjectUtil.isNull(pageQuery.getOrderByColumn())) {
      pageQuery.setOrderByColumn(keyProperty);
      pageQuery.setIsAsc("desc");
    }

    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    IPage<T> ipage = pageQuery.build();
    // 构建查询条件
    QueryWrapper<T> wrapper = BuildWrapperHelper.createWrapper(pageQuery.getQueryBoList(), modelClass);
    // 查询字段
    if (StringUtils.isNotEmpty(pageQuery.getSelectColumns())) {
      var columns = StringUtils.splitList(pageQuery.getSelectColumns()).stream().map(StringUtils::toUnderScoreCase).toList();
      wrapper.select(columns);
    }
    IPage<V> page = this.baseMapper.selectVoPage(ipage, wrapper);
    try {
      afterPageQuery(pageQuery, page);
    } catch (Exception e) {
      e.printStackTrace();
    }
    TableDataInfo<V> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(tableName);

    //#region 列表合计求和
    if (ObjectUtil.isNotNull(pageQuery.getSumColumnNames())) {
      // 拼接查询条件
      QueryWrapper<T> sumWrapper = BuildWrapperHelper.createWrapper(pageQuery.getQueryBoList(), modelClass);
      if (!pageQuery.getSumColumnNames().isEmpty()) {
        List<String> sums = new ArrayList<>();
        for (var sumItem : pageQuery.getSumColumnNames()) {
          sums.add("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
        }
        sumWrapper.select(CollUtil.join(sums, ","));
        List<Map<String, Object>> maps = this.listMaps(sumWrapper);
        tableDataInfoV.setFooter(maps);
      }
    }
    this.afterPageList(pageQuery, tableDataInfoV);

    return tableDataInfoV;
  }

  /**
   * 主从表关联分页查询
   */
  public TableDataInfo<Map<String, Object>> pageMapList(PageQuery pageQuery) {
    String idField = pageQuery.getIdField();
    if (StringUtils.isEmpty(idField)) {
      idField = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    }
    TableDataInfo<Map<String, Object>> dataInfo = new TableDataInfo<>();
    TableDataInfo<V> tableDataInfo = this.pageList(pageQuery);
    if (ObjectUtil.isNotNull(tableDataInfo.getRows())) {
      List<Map<String, Object>> mapList = tableDataInfo.getRows().stream()
        .map(BeanUtil::beanToMap)
        .collect(Collectors.toList());

      // 加载明细
      String finalIdField = idField;
      String idList = mapList.stream().map(m -> Convert.toStr(m.get(finalIdField))).collect(Collectors.joining(","));

      int detailIndex = 0;
      for (EditorDetailBo detail : pageQuery.getDetailParams()) {
        String subTableName = detail.getSubTableName();
        subTableName = StringUtils.toCamelCase(subTableName);
        String serviceName = subTableName + "ServiceImpl";
        if (!SpringUtils.containsBean(serviceName)) {
          continue;
        }
        var serviceImplPlus = SpringUtils.getBean(serviceName, ServiceImplPlus.class);
        List<QueryBo> queryBoList = detail.getQueryBoList();
        String detailIdField = idField; // 明细表外键关联字段，默认和主表相同
        if (CollUtil.isEmpty(queryBoList)) {
          queryBoList = new ArrayList<>();
          QueryBo queryBo = new QueryBo();
          queryBo.setColumn(idField);
          queryBo.setQueryType(QueryTypeEnum.IN);
          queryBo.setDataType(DataTypeEnum.LONG);
          queryBo.setValues(idList);
          queryBoList.add(queryBo);
        } else {
          // 设置多查询条件中的主键
          for (QueryBo queryBo : queryBoList) {
            String columnName = queryBo.getValues();
            if (StringUtils.equals(columnName, "{{" + idField + "}}")) {
              detailIdField = queryBo.getColumn(); // 明细表外键
              queryBo.setValues(idList);
            }
          }
        }
        List<Map<String, Object>> detailMapList = serviceImplPlus.getMapList(queryBoList);
        for (var map : detailMapList) {
          var expandFields = JSONUtil.parse(map.get("expandFields"));
          map.put("expandFields", expandFields);
        }

        for (var map : mapList) {
          String finalIdField1 = idField;
          String finalDetailIdField = detailIdField;
          var tabeDataList = detailMapList.stream().filter(x -> B.isEqual(map.get(finalIdField1), x.get(finalDetailIdField))).toList();
          if (detailIndex == 0) {
            map.put("detailList", tabeDataList);
          } else {
            map.put("detailList" + detailIndex, tabeDataList);
          }
        }
        detailIndex++;
      }

      dataInfo.setRows(mapList);
    }

    dataInfo.setResult(tableDataInfo.isResult());
    dataInfo.setCode(tableDataInfo.getCode());
    dataInfo.setTotal(tableDataInfo.getTotal());
    dataInfo.setFooter(tableDataInfo.getFooter());
    dataInfo.setMsg(tableDataInfo.getMsg());

    return dataInfo;
  }
  //#endregion

  //#region selectList 查询集合

  /**
   * 查询集合
   */
  public List<V> selectList(List<QueryBo> queryBoList) {
    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    QueryWrapper<T> wrapper = BuildWrapperHelper.createWrapper(queryBoList, modelClass); // 构建查询条件
    wrapper.last("limit 5000");

    return baseMapper.selectVoList(wrapper);
  }
  //#endregion

  //#region selectList 查询集合

  /**
   * 查询Map集合
   */
  public List<Map<String, Object>> getMapList(List<QueryBo> queryBoList) {
    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    // 拼接查询条件
    QueryWrapper<T> wrapper = BuildWrapperHelper.createWrapper(queryBoList, modelClass); // 构建查询条件
    return this.listMaps(wrapper);
  }

  /**
   * 查询集合
   */
  public List<V> selectList(Wrapper<T> queryWrapper) {
    return baseMapper.selectVoList(queryWrapper);
  }
  //#endregion

  //#region selectOne 查询单条

  /**
   * 查询单条
   * {@code @queryBoList} 查询条件
   */
  public V selectOne(List<QueryBo> queryBoList) {
    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    QueryWrapper<T> wrapper = BuildWrapperHelper.createWrapper(queryBoList, modelClass); // 构建查询条件
    wrapper.last("limit 1");

    return baseMapper.selectVoOne(wrapper);
  }

  /**
   * 查询单条
   * {@code @queryWrapper} 查询条件
   */
  public V selectOne(Wrapper<T> queryWrapper) {
    return baseMapper.selectVoOne(queryWrapper);
  }

  /**
   * 查询单条
   * {@code @queryWrapper} 查询条件
   */
  public V getOne(PageQuery pageQuery) {
    return this.selectOne(pageQuery.getQueryBoList());
  }
  //#endregion

  /**
   * 查询集合
   */

  //#region getMap
  public Map<String, Object> getMap(List<QueryBo> queryBoList) {
    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    // 拼接查询条件
    QueryWrapper<T> wrapper = BuildWrapperHelper.createWrapper(queryBoList, modelClass); // 构建查询条件
    return this.getMap(wrapper);
  }
  //#endregion

  //#region selectById

  /**
   * 查询公告信息
   *
   * @param id 主键ID
   * @return 返回单条信息
   */
  public V selectById(Long id) {
    return baseMapper.selectVoById(id);
  }
  //#endregion

  //#region selectById

  /**
   * 根据IDs
   *
   * @param ids 主键IDs
   * @return 返回List数据
   */
  public List<V> selectByIds(List<Long> ids) {
    return baseMapper.selectVoBatchIds(ids);
  }
  //#endregion

  //#region editor

  /**
   * 获取编辑页面数据
   *
   * @param editorBo 编辑页面参数
   * @return 返回单条信息
   */
  public EditorVo<V> editor(EditorBo editorBo) {
    // 数据加载前处理函数
    beforeEditor(editorBo);

    V master = baseMapper.selectVoById(editorBo.getIdValue());
    EditorVo<V> editor = new EditorVo<>();
    List<TableDataInfo> detailList = new ArrayList<>();
    this.getDetails(editorBo, detailList); // 获取明细
    editor.setMaster(master); // 主表数据
    editor.setDetailList(detailList); // 明细集合数据

    // 数据加载后处理函数
    afterEditor(editor);

    return editor;
  }
  //#endregion

  //#region getDetails

  /**
   * 获取明细数据
   *
   * @param editorBo   bo参数
   * @param detailList 明细集合
   */
  protected void getDetails(EditorBo editorBo, List<TableDataInfo> detailList) {
    if (ObjectUtil.isNull(editorBo.getDetailParams())) {
      return;
    }

    for (EditorDetailBo detail : editorBo.getDetailParams()) {
      String subTableName = detail.getSubTableName();
      subTableName = StringUtils.toCamelCase(subTableName);
      String serviceName = subTableName + "ServiceImpl";
      if (!SpringUtils.containsBean(serviceName)) {
        continue;
      }
      var serviceImplPlus = SpringUtils.getBean(serviceName, ServiceImplPlus.class);
      // 明细查询条件
      PageQuery pageQuery = new PageQuery();
      pageQuery.setPageIndex(detail.getPageIndex());
      int pageSize = detail.getPageSize();
      if (B.isLessOrEqual(pageSize)) {
        pageSize = 500;
      }
      pageQuery.setPageSize(pageSize);
      pageQuery.setTableName(subTableName);

      //添加主键Id查询
      List<QueryBo> queryBoList = new ArrayList<>();
      QueryBo queryBo = new QueryBo();
      queryBo.setColumn(editorBo.getIdField());
      queryBo.setDataType(DataTypeEnum.LONG);
      if (ObjectUtil.isNotNull(editorBo.getIdValues())) {
        queryBo.setQueryType(QueryTypeEnum.IN);
        queryBo.setValues(StrUtil.join(StringUtils.SEPARATOR_COMMA, editorBo.getIdValues()));
      } else {
        queryBo.setQueryType(QueryTypeEnum.EQ);
        queryBo.setValues(editorBo.getIdValue().toString());
      }
      queryBoList.add(queryBo);

      //添加明细其他字段查询
      if (ObjectUtil.isNotNull(detail.getQueryBoList()) && B.isGreater(detail.getQueryBoList().size())) {
        queryBoList.addAll(detail.getQueryBoList());// 复制数据
      }

      pageQuery.setQueryBoList(queryBoList);
      pageQuery.setOrderByColumn(detail.getOrderByColumn());
      pageQuery.setIsAsc(detail.getIsAsc());

      var detailInfo = serviceImplPlus.pageList(pageQuery);
      detailList.add(detailInfo);
    }
  }
  //#endregion

  //#region saveEditor

  /**
   * 保存编辑页面数据
   *
   * @param saveEditorBo 保存页面参数
   * @return 返回单条信息
   */
  @Transactional(rollbackFor = Exception.class)
  public EditorVo<V> saveEditor(SaveEditorBo<BO> saveEditorBo) {
    BO bMaster = saveEditorBo.getData().getMaster();
    T entity = MapstructUtils.convert(bMaster, this.baseMapper.getCurrentModelClass());
    MetaObject metaObject = SystemMetaObject.forObject(entity);
    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    beforeSaveEditor(saveEditorBo);
    Assert.isFalse(ObjectUtil.isNull(entity), "数据不存在！");

    Long idValue = (Long) metaObject.getValue(keyProperty);
    // 是否新建
    saveEditorBo.setAdd(ObjectUtil.isNull(idValue) || B.isEqual(idValue));

    List<Field> allFields = TableInfoHelper.getAllFields(super.getEntityClass());
    // 设置自动编码
    if (StringUtils.isNotEmpty(saveEditorBo.getCodeRegular()) && !customCodeRegular(saveEditorBo, metaObject)) {
      // 没有自定义编码规则是走默认规则
      Field boField = allFields.stream().filter(f -> f.getName().equals(saveEditorBo.getCodeRegular())).findFirst().orElse(null);
      if (ObjectUtil.isNotNull(boField)) {
        Object val = metaObject.getValue(boField.getName());
        MenuEnum menuEnum = MenuEnum.getEnumById(saveEditorBo.getMenuId());
        if (ObjectUtil.isEmpty(val) && (ObjectUtil.isNotNull(menuEnum))) {
          String regularValue = DBUtils.getCodeRegular(menuEnum);
          metaObject.setValue(saveEditorBo.getCodeRegular(), regularValue);
        }
      }
    }
    beforeSaveEditor2(saveEditorBo, entity);
    if (ObjectUtil.isNotNull(idValue) && idValue > 0) {
      if (saveEditorBo.isFieldStrategy()) {
        this.updateById(entity);
      } else {
        this.updateWidthNull(entity);
      }
    } else {
      baseMapper.insert(entity);
      idValue = (Long) metaObject.getValue(keyProperty);
    }
    saveEditorBo.setIdValue(idValue);

    V master = baseMapper.selectVoById(idValue);
    EditorVo<V> editor = new EditorVo<>();

    var detailList = Optional.ofNullable(saveEditorBo.getData().getDetailList()).orElse(new ArrayList<>());
    for (SaveDataDetailBo detail : detailList) {
      String subTableName = detail.getSubTableName();
      final String finalSubTableName = StringUtils.toCamelCase(subTableName);
      String serviceName = finalSubTableName + "ServiceImpl";
      if (!SpringUtils.containsBean(serviceName)) {
        continue;
      }

      // 明细数据结构
      var serviceImplPlus = SpringUtils.getBean(serviceName, ServiceImplPlus.class);
      // 遍历明细行数据
      for (Map<String, Object> row : detail.getRows()) {
        // 明细设置外键ID
        Long foreignKeyValue = Convert.toLong(row.get(saveEditorBo.getIdField()));
        if (ObjectUtil.isEmpty(foreignKeyValue) || foreignKeyValue <= 0) {
          row.put(saveEditorBo.getIdField(), saveEditorBo.getIdValue());
        }
        serviceImplPlus.insertOrUpdate(row);
      }
    }

    editor.setMaster(master); // 主表数据

    // 获取明细数据
    List<TableDataInfo> detailDataList = new ArrayList<>();
    EditorBo editorBo = new EditorBo();
    BeanUtil.copyProperties(saveEditorBo, editorBo);
    this.getDetails(editorBo, detailDataList); // 获取明细
    editor.setDetailList(detailDataList); // 明细集合数据

    afterSaveEditor(saveEditorBo, editor);

    return editor;
  }
  //#endregion

  //#region insertOrUpdate

  /**
   * 插入更新操作，更新操作支持更新null值
   *
   * @param row map对象
   * @return 返回保存是否成功
   */
  public Boolean insertOrUpdate(Map<String, Object> row) {
    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    Long subIdValue = Convert.toLong(row.get(keyProperty));
    if (ObjectUtil.isNotNull(subIdValue) && subIdValue > 0) {
      return this.updateWidthNull(row);
    } else {
      T newEntity = BeanUtil.mapToBean(row, super.getEntityClass(), true, CopyOptions.create());
      return this.getBaseMapper().insert(newEntity) > 0;
    }
  }
  //#endregion

  //#region batchSave

  /**
   * 批量新增
   *
   * @param boList 新增信息bo
   * @return 结果
   */
  @Transactional(rollbackFor = Exception.class)
  public int batchSave(List<BO> boList) {
    int result = 0;
    for (var bo : boList) {
      result = this.insert(bo);
    }

    return result;
  }
  //#endregion

  //#region insert

  /**
   * 新增
   *
   * @param bo 新增信息bo
   * @return 结果
   */
  public int insert(BO bo) {
    T domain = MapstructUtils.convert(bo, this.baseMapper.getCurrentModelClass());
    int result = baseMapper.insert(domain);
    if (result > 0) {
      var fields = ReflectUtil.getFields(domain.getClass());
      for (Field field : fields) {
        var ann = field.getAnnotation(TableId.class); // 找到domain的主键
        if (ObjectUtil.isNotNull(ann)) {
          String fieldName = field.getName();
          Object val = ReflectUtil.getFieldValue(domain, field);
          Field boField = ReflectUtil.getField(bo.getClass(), fieldName);
          if (ObjectUtil.isNotNull(boField)) {
            ReflectUtil.setFieldValue(bo, boField, val); // 给bo主键赋值
          }
          break;
        }
      }
    }
    return result;
  }
  //#endregion

  //#region insertByBo

  /**
   * 新增数据
   */
  public Boolean insertByBo(BO bo) {
    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    T add = MapstructUtils.convert(bo, modelClass);
    boolean isValid = validEntityBeforeSave(add);
    if (!isValid) {
      return false;
    }
    return baseMapper.insert(add) > 0;
  }
  //#endregion

  //#region update by bo

  /**
   * 修改
   *
   * @param bo 修改信息
   * @return 结果
   */
  public V update(BO bo) {
    T domain = MapstructUtils.convert(bo, this.baseMapper.getCurrentModelClass());
    baseMapper.updateById(domain);
    MetaObject metaObject = SystemMetaObject.forObject(domain);
    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    Long idValue = (Long) metaObject.getValue(keyProperty);

    return this.selectById(idValue);
  }
  //#endregion

  //#region update by bo

  /**
   * 修改
   *
   * @param bo 修改信息
   * @return 结果
   */
  public boolean updateExpandField(BO bo) {
    String expandFieldName = "expand_fields";
    String expandFieldCol = StringUtils.toCamelCase(expandFieldName);
    T domain = MapstructUtils.convert(bo, this.baseMapper.getCurrentModelClass());
    MetaObject metaObject = SystemMetaObject.forObject(domain);
    Map<String, Object> expandFields = Convert.toMap(String.class, Object.class, metaObject.getValue(expandFieldCol));

    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    Long idValue = (Long) metaObject.getValue(keyProperty);
    QueryWrapper<T> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(keyProperty, idValue);

    T t = this.getOne(queryWrapper);
    Map<String, Object> originExpandFields = null;
    if (!ObjectUtil.isNull(t)) {
      MetaObject metaObject2 = SystemMetaObject.forObject(t);
      originExpandFields = Convert.toMap(String.class, Object.class, metaObject2.getValue(expandFieldCol));
    }
    if (ObjectUtil.isNull(originExpandFields)) {
      originExpandFields = new HashMap<>();
    }
    originExpandFields.putAll(expandFields);
    UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
    // 更新扩展字段
    updateWrapper.set(expandFieldName, originExpandFields, "typeHandler=com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler")
      .eq(keyProperty, idValue);

    return this.update(updateWrapper);
  }
  //#endregion

  //#region updateByBo

  /**
   * 修改数据
   */
  public Boolean updateByBo(BO bo) {
    Class<T> modelClass = this.baseMapper.getCurrentModelClass();
    T update = MapstructUtils.convert(bo, modelClass);
    validEntityBeforeSave(update);
    return baseMapper.updateById(update) > 0;
  }
  //#endregion

  //#region updateWidthNull 实体可以更新为空值

  /**
   * 可以更新为空值
   *
   * @param entity 实体
   * @return 结果
   */
  public boolean updateWidthNull(T entity) {
    UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
    List<Field> allFields = TableInfoHelper.getAllFields(entity.getClass());
    MetaObject metaObject = SystemMetaObject.forObject(entity);
    for (Field field : allFields) {
      String columnName = StringUtils.toUnderScoreCase(field.getName());
      if (StringUtils.equals(columnName, CommonFields.UPDATE_TIME)) {
        updateWrapper.set(CommonFields.UPDATE_TIME, DateUtil.date());
      } else if (StringUtils.equals(columnName, CommonFields.UPDATE_BY)) {
        updateWrapper.set(CommonFields.UPDATE_BY, LoginHelper.getUserId());
      } else if (StringUtils.equals(columnName, CommonFields.UPDATE_BY_NAME)) {
        updateWrapper.set(CommonFields.UPDATE_BY_NAME, LoginHelper.getNickname());
      } else if (StringUtils.equals(columnName, CommonFields.EXPAND_FIELDS)) {
        String value = JSONUtil.toJsonStr(metaObject.getValue(field.getName()));
        updateWrapper.set(CommonFields.EXPAND_FIELDS, value);
      } else if (!ignoreList.contains(columnName)) {
        Object value = metaObject.getValue(field.getName());
        updateWrapper.set(columnName, value);
      }
    }
    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    updateWrapper.eq(StringUtils.toUnderScoreCase(keyProperty), metaObject.getValue(keyProperty));

    return this.update(updateWrapper);
  }
  //#endregion

  //#region updateWidthNull 实体可以更新为空值

  /**
   * 可以更新为空值
   *
   * @param row map对象
   * @return 结果
   */
  public boolean updateWidthNull(Map<String, Object> row) {
    UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
    List<Field> allFields = TableInfoHelper.getAllFields(super.getEntityClass());
    for (var field : row.entrySet()) {
      String fieldName = field.getKey();
      String columnName = StrUtil.toUnderlineCase(fieldName);
      if (!ignoreList.contains(columnName) && allFields.stream().anyMatch(a -> StringUtils.equals(a.getName(), fieldName))) {
        Object value = row.get(fieldName);
        if (value instanceof ArrayList<?> || value instanceof HashMap<?, ?>) {
          value = JSONUtil.toJsonStr(value);
        }
        updateWrapper.set(columnName, value);
      }
    }
    String keyProperty = TableInfoHelper.getTableInfo(super.getEntityClass()).getKeyProperty();
    updateWrapper.eq(StrUtil.toUnderlineCase(keyProperty), row.get(keyProperty));

    return this.update(updateWrapper);
  }
  //#endregion

  //#region update by UpdateBo

  /**
   * 修改
   *
   * @param updateBo 修改信息
   * @return 结果
   */
  public boolean update(UpdateBo updateBo) {
    UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
    var columns = updateBo.getColumns();
    // 更新字段
    for (var entry : columns.entrySet()) {
      String columnName = StringUtils.toUnderScoreCase(entry.getKey());
      updateWrapper.set(columnName, columns.get(entry.getKey()));
    }
    for (QueryBo queryBo : updateBo.getQueryBoList()) {
      String column = queryBo.getColumn();
      String columnName = StrUtil.toUnderlineCase(column);
      String values = queryBo.getValues();
      Object _values = BuildWrapperHelper.convertData(queryBo);

      switch (queryBo.getQueryType()) {
        case EQ -> updateWrapper.eq(StrUtil.isNotBlank(queryBo.getValues()), columnName, _values);
        case NE -> updateWrapper.ne(StrUtil.isNotBlank(values), columnName, _values);
        case GT -> updateWrapper.gt(StrUtil.isNotBlank(values), columnName, _values);
        case GE -> updateWrapper.ge(StrUtil.isNotBlank(values), columnName, _values);
        case LT -> updateWrapper.lt(StrUtil.isNotBlank(values), columnName, _values);
        case LE -> updateWrapper.le(StrUtil.isNotBlank(values), columnName, _values);
        case LIKE -> updateWrapper.like(StrUtil.isNotBlank(values), columnName, _values);
        case NOTLIKE -> updateWrapper.notLike(StrUtil.isNotBlank(values), columnName, _values);
        case LIKELEFT -> updateWrapper.likeLeft(StrUtil.isNotBlank(values), columnName, _values);
        case LIKERIGHT -> updateWrapper.likeRight(StrUtil.isNotBlank(values), columnName, _values);
        default -> {
        }
      }
    }
    return this.update(updateWrapper);
  }
  //#endregion

  //#region deleteById

  /**
   * 根据ID删除信息，单条删除
   *
   * @param id 删除ID
   * @return 结果
   */
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(Long id) {
    Long[] longIds = new Long[1];
    longIds[0] = id;
    if (!this.beforeDelete(longIds)) return -1;
    int delResult = baseMapper.deleteById(id);

    if (!this.afterDelete(longIds)) {
      throw new ServiceException("删除失败");
    }

    return delResult;
  }
  //#endregion

  //#region deleteByIds

  /**
   * 批量删除操作
   *
   * @param Ids 需要删除的ID集合
   * @return 结果
   */
  @Transactional(rollbackFor = Exception.class)
  public int deleteByIds(Long[] Ids) {
    if (!this.beforeDelete(Ids)) return -1;

    int delResult = this.baseMapper.deleteBatchIds(Arrays.asList(Ids));
    if (!this.afterDelete(Ids)) {
      throw new ServiceException("删除失败");
    }
    return delResult;
  }
  //#endregion

  //#region clean

  /**
   * 清空操作
   */
  public void clean() {
    baseMapper.delete(new LambdaQueryWrapper<>());
  }
  //#endregion

  //#region validEntityBeforeSave

  /**
   * 保存前的数据校验
   */
  public boolean validEntityBeforeSave(T entity) {
    // TODO 做一些数据校验,如唯一约束
    return true;
  }
  //#endregion

  //#region deleteTreeNode

  /**
   * 删除tree结构数据
   */
  public Boolean deleteTreeNode(LoadTreeBo loadTreeBo) {
    var keyName = StringUtils.toUnderScoreCase(loadTreeBo.getKeyName());
    var tableName = StringUtils.toUnderScoreCase(loadTreeBo.getTableName());
    var nodeName = StringUtils.toUnderScoreCase(loadTreeBo.getNodeName());
    var parentName = StringUtils.toUnderScoreCase(loadTreeBo.getParentName());
    var keyValue = loadTreeBo.getKeyValue();

    String where = StrUtil.EMPTY;
    if (ObjectUtil.isNotNull(loadTreeBo.getWhereList())) {
      for (QueryBo queryBo : loadTreeBo.getWhereList()) {
        switch (queryBo.getQueryType()) {
          case EQ -> {
            where = StringUtils.toUnderScoreCase(queryBo.getColumn()) + "='" + queryBo.getValues() + "'";
          }
          case NE -> {
            where = StringUtils.toUnderScoreCase(queryBo.getColumn()) + "<>'" + queryBo.getValues() + "'";
          }
          default -> {
          }
        }
      }
    }
    if (StringUtils.isNotEmpty(where)) where = " AND " + where;

    var sql = """
      WITH RECURSIVE recursiveInfo AS
      (
        SELECT {keyName} FROM {tableName} where {keyName}={keyValue} {where}
        UNION ALL
        SELECT a.{keyName} FROM {tableName} AS a INNER JOIN recursiveInfo AS b ON a.{parentName}=b.{keyName} {where}
      )
      DELETE FROM {tableName} WHERE {keyName} in(SELECT {keyName} FROM recursiveInfo) {where}
      """;
    Map<String, String> map = new HashMap<>();
    map.put("tableName", tableName);
    map.put("keyName", keyName);
    map.put("keyValue", keyValue);
    map.put("where", where);
    map.put("parentName", parentName);
    sql = StrUtil.format(sql, map);

    return SqlRunner.db().delete(sql);
  }
  //#endregion

  //#region multiAuditing

  /**
   * 批量审核
   */
  public R<Void> multiAuditing(List<Long> ids) {
    return R.fail("审核未实现");
  }

  /**
   * 批量反审核
   */
  public R<Void> reMultiAuditing(Map<String, Object> map) {
    return R.fail("反审核未实现");
  }
//#endregion

  //#region sorting 分拣

  /**
   * 分拣
   */
  public R<Void> sorting(Map<String, Object> map) {
    return R.fail("分拣未实现");
  }
  //#endregion

  //#region copyEditor 复制

  /**
   * 复制
   *
   * @param saveEditorBo 复制信息
   */
  public R<Map<String, Object>> copyEditor(SaveEditorBo<BO> saveEditorBo) {

    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    return R.fail("复制未实现");
  }
  //#endregion

  //#region stop 终止

  /**
   * 终止
   */
  public R<Void> stop(Map<String, Object> map) {
    return R.fail("终止未实现");
  }
  //#endregion

  //#region start 开启

  /**
   * 开启
   */
  public R<Void> open(Map<String, Object> map) {
    return R.fail("开启未实现");
  }
  //#endregion

  //#region importData 批量导入

  /**
   * 批量导入
   */
  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {
    String key = request.getParameter("key");
    QueueUtils.addQueueObject(key, "导入未实现");
    QueueUtils.getClient().getQueue(key).expire(Instant.now().plus(10, ChronoUnit.MINUTES)); // 设置过期时间
    QueueUtils.addQueueObject(key, "-1");
  }
  //#endregion

  //#region exportData 批量导出

  /**
   * 批量导出
   */
  public void exportData(HttpServletRequest request, HttpServletResponse response, String pageQuery) throws IOException {
  }
  //#endregion
}
