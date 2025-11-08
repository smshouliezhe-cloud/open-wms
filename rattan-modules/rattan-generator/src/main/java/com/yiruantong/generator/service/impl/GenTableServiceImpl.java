package com.yiruantong.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.generator.domain.vo.GenTableColumnVo;
import com.yiruantong.generator.domain.vo.GenTableVo;
import com.yiruantong.generator.mapper.GenTableColumnMapper;
import com.yiruantong.generator.mapper.GenTableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.file.FileUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.generator.constant.GenConstants;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableColumn;
import com.yiruantong.generator.domain.bo.GenTableBo;
import com.yiruantong.generator.service.IGenTableService;
import com.yiruantong.generator.util.GenUtils;
import com.yiruantong.generator.util.VelocityInitializer;
import com.yiruantong.generator.util.VelocityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 *
 * @author YiRuanTong
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GenTableServiceImpl extends ServiceImplPlus<GenTableMapper, GenTable, GenTableVo, GenTableBo> implements IGenTableService {

  private final GenTableMapper baseMapper;
  private final GenTableColumnMapper genTableColumnMapper;
  private final IdentifierGenerator identifierGenerator;

  /**
   * 获取代码生成地址
   *
   * @param table    业务表信息
   * @param template 模板文件路径
   * @return 生成地址
   */
  public static String getGenPath(GenTable table, String template) {
    String genPath = table.getGenPath();
    if (StringUtils.equals(genPath, "/")) {
      return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
    }
    return genPath + File.separator + VelocityUtils.getFileName(template, table);
  }

  /**
   * 查询业务字段列表
   *
   * @param tableId 业务字段编号
   * @return 业务字段集合
   */
  @Override
  public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
    LambdaQueryWrapper<GenTableColumn> wrapper = new LambdaQueryWrapper<>();
    wrapper.orderByDesc(GenTableColumn::getOrderNum).orderByAsc(GenTableColumn::getColumnId)
      .eq(GenTableColumn::getTableId, tableId);
    return genTableColumnMapper.selectList(wrapper);
  }

  /**
   * 查询业务信息
   *
   * @param id 业务ID
   * @return 业务信息
   */
  @Override
  public GenTable selectGenTableById(Long id) {
    GenTable genTable = baseMapper.selectGenTableById(id);
    setTableFromOptions(genTable);
    return genTable;
  }

  /**
   * 查询业务信息
   *
   * @param tableName 表名称
   * @return 业务信息
   */
  @Override
  public GenTable selectGenTableByName(String tableName) {
    GenTable genTable = baseMapper.selectGenTableByName(tableName);
    if (ObjectUtil.isNotEmpty(genTable)) {
      setTableFromOptions(genTable);
    }
    return genTable;
  }

  @Override
  public TableDataInfo<GenTable> selectPageGenTableList(GenTable genTable, PageQuery pageQuery) {
    Page<GenTable> page = baseMapper.selectPage(pageQuery.build(), this.buildGenTableQueryWrapper(genTable));
    return TableDataInfo.build(page);
  }

  private QueryWrapper<GenTable> buildGenTableQueryWrapper(GenTable genTable) {
    Map<String, Object> params = genTable.getParams();
    QueryWrapper<GenTable> wrapper = Wrappers.query();
    wrapper
      .eq(StringUtils.isNotEmpty(genTable.getDataName()), "data_name", genTable.getDataName())
      .like(StringUtils.isNotBlank(genTable.getTableName()), "lower(table_name)", StringUtils.lowerCase(genTable.getTableName()))
      .like(StringUtils.isNotBlank(genTable.getTableComment()), "lower(table_comment)", StringUtils.lowerCase(genTable.getTableComment()))
      .between(params.get("beginTime") != null && params.get("endTime") != null,
        "create_time", params.get("beginTime"), params.get("endTime"));
    return wrapper;
  }

  /**
   * #genTable.dataName #参数名称可以动态的使用我们的参数变量
   *
   * @param genTable  业务信息
   * @param pageQuery 查询条件
   * @return GenTable信息
   */
  @DS("#genTable.dataName")
  @Override
  public TableDataInfo<GenTable> selectPageDbTableList(GenTable genTable, PageQuery pageQuery) {
    genTable.getParams().put("genTableNames", baseMapper.selectTableNameList(genTable.getDataName()));
    Page<GenTable> page = baseMapper.selectPageDbTableList(pageQuery.build(), genTable);
    return TableDataInfo.build(page);
  }

  /**
   * 查询据库列表
   *
   * @param tableNames 表名称组
   * @param dataName   数据源名称
   * @return 数据库表集合
   */
  @DS("#dataName")
  @Override
  public List<GenTable> selectDbTableListByNames(String[] tableNames, String dataName) {
    return baseMapper.selectDbTableListByNames(tableNames);
  }

  /**
   * 查询所有表信息
   *
   * @return 表信息集合
   */
  @Override
  public List<GenTable> selectGenTableAll() {
    return baseMapper.selectGenTableAll();
  }

  /**
   * 修改业务
   *
   * @param genTable 业务信息
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateGenTable(GenTable genTable) {
    String options = JsonUtils.toJsonString(genTable.getParams());
    genTable.setOptions(options);
    int row = baseMapper.updateById(genTable);
    if (row > 0) {
      for (GenTableColumn cenTableColumn : genTable.getColumns()) {
        genTableColumnMapper.updateById(cenTableColumn);
      }
    }
  }

  /**
   * 删除业务对象
   *
   * @param tableIds 需要删除的数据ID
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteGenTableByIds(Long[] tableIds) {
    List<Long> ids = Arrays.asList(tableIds);
    baseMapper.deleteBatchIds(ids);
    genTableColumnMapper.delete(new LambdaQueryWrapper<GenTableColumn>().in(GenTableColumn::getTableId, ids));
  }

  /**
   * 导入表结构
   *
   * @param tableList 导入表列表
   * @param dataName  数据源名称
   */
  @DSTransactional
  @Override
  public void importGenTable(List<GenTable> tableList, String dataName, Map<String, String> requestMap) {
    try {
      for (GenTable table : tableList) {
        String tableName = table.getTableName();
        int row = 0;
        var tableInfo = this.selectGenTableByName(table.getTableName());
        if (ObjectUtil.isEmpty(tableInfo)) {
          GenUtils.initTable(table);
          table.setDataName(dataName);
          table.setParentId(Convert.toLong(requestMap.get("parentId"))); // 父级ID
          table.setModuleName(requestMap.get("folderName")); // 文件夹名称
          table.setPackageName(requestMap.get("packageName"));
          // 如果主表不存在，插入新增
          row = baseMapper.insert(table);
        } else {
          row = 1;
          table = tableInfo;
          table.setTableId(tableInfo.getTableId());
          table.setParentId(tableInfo.getParentId());
          table.setOrderNum(tableInfo.getOrderNum());
        }

        if (row > 0) {
          // 保存列信息
          List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName, dataName);

          // 获得主键、排序字段
          var colInfo = genTableColumns.stream().filter(GenTableColumn::isPk).findFirst().orElse(null);
          if (colInfo != null) {
            String javaField = StringUtils.toCamelCase(colInfo.getColumnName());
            table.setKeyName(javaField);
            String orderBy = "{\"" + javaField + "\":\"DESC\"}";
            table.setOrderBy(orderBy);
          }

          // 获得编码字段、连接字段
          colInfo = genTableColumns.stream().filter(item -> StrUtil.subSufByLength(item.getColumnName(), 4).equals("code")).findFirst().orElse(null);
          if (colInfo != null) {
            String javaField = StringUtils.toCamelCase(colInfo.getColumnName());
            table.setCodeRegular(javaField);
            table.setLinkColumn(javaField);
          }

          // 路由前缀
          String[] strList = StringUtils.split(table.getPackageName(), '.');
          String projectName = strList[strList.length - 1]; // 项目名称
          String prefixRouter = "/" + projectName + "/" + table.getModuleName() + "/" + table.getBusinessName();
          table.setPrefixRouter(prefixRouter);
          table.setWebRouter(prefixRouter);
          baseMapper.updateById(table); // 更新表信息

          // 保存字段
          List<GenTableColumn> saveColumns = new ArrayList<>();
          for (GenTableColumn column : genTableColumns) {
            LambdaQueryWrapper<GenTableColumn> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(GenTableColumn::getTableId, table.getTableId())
              .eq(GenTableColumn::getColumnName, column.getColumnName());
            GenTableColumnVo genTableColumnVo = genTableColumnMapper.selectVoOne(wrapper);
            // 如果字段不存在新增，存在不错任何操作
            if (ObjectUtil.isEmpty(genTableColumnVo)) {
              GenUtils.initColumnField(column, table);
              column.setOrderNum(0L); // 将排序号默认为0
              saveColumns.add(column);
            }
          }
          if (CollUtil.isNotEmpty(saveColumns)) {
            genTableColumnMapper.insertBatch(saveColumns);
          }
        }
      }
    } catch (Exception e) {
      throw new ServiceException("导入失败：" + e.getMessage());
    }
  }

  /**
   * 预览代码
   *
   * @param tableId 表编号
   * @return 预览数据列表
   */
  @Override
  public Map<String, String> previewCode(Long tableId) {
    Map<String, String> dataMap = new LinkedHashMap<>();
    // 查询表信息
    GenTable table = baseMapper.selectGenTableById(tableId);
    List<Long> menuIds = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      menuIds.add(identifierGenerator.nextId(null).longValue());
    }
    table.setMenuIds(menuIds);
    // 设置主键列信息
    setPkColumn(table);
    VelocityInitializer.initVelocity();

    VelocityContext context = VelocityUtils.prepareContext(table);

    // 获取模板列表
    List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
    for (String template : templates) {
      // 渲染模板
      StringWriter sw = new StringWriter();
      Template tpl = Velocity.getTemplate(template, Constants.UTF8);
      tpl.merge(context, sw);
      dataMap.put(template, sw.toString());
    }
    return dataMap;
  }

  /**
   * 生成代码（下载方式）
   *
   * @param tableId 表名称
   * @return 数据
   */
  @Override
  public byte[] downloadCode(Long tableId) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ZipOutputStream zip = new ZipOutputStream(outputStream);
    generatorCode(tableId, zip);
    IoUtil.close(zip);
    return outputStream.toByteArray();
  }

  /**
   * 生成代码（自定义路径）
   *
   * @param tableId 表名称
   */
  @Override
  public void generatorCode(Long tableId) {
    // 查询表信息
    GenTable table = baseMapper.selectGenTableById(tableId);
    // 设置主键列信息
    setPkColumn(table);

    VelocityInitializer.initVelocity();

    VelocityContext context = VelocityUtils.prepareContext(table);

    // 获取模板列表
    List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
    for (String template : templates) {
      if (!StringUtils.containsAny(template, "sql.vm", "api.ts.vm", "types.ts.vm", "index.vue.vm", "index-tree.vue.vm")) {
        // 渲染模板
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, Constants.UTF8);
        tpl.merge(context, sw);
        try {
          String path = getGenPath(table, template);
          FileUtils.writeUtf8String(sw.toString(), path);
        } catch (Exception e) {
          throw new ServiceException("渲染模板失败，表名：" + table.getTableName());
        }
      }
    }
  }

  /**
   * 同步数据库
   *
   * @param tableId 表名称
   */
  @DSTransactional
  @Override
  public void synchDb(Long tableId) {
    GenTable table = baseMapper.selectGenTableById(tableId);
    List<GenTableColumn> tableColumns = table.getColumns();
    Map<String, GenTableColumn> tableColumnMap = StreamUtils.toIdentityMap(tableColumns, GenTableColumn::getColumnName);

    List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(table.getTableName(), table.getDataName());
    if (CollUtil.isEmpty(dbTableColumns)) {
      throw new ServiceException("同步数据失败，原表结构不存在");
    }
    List<String> dbTableColumnNames = StreamUtils.toList(dbTableColumns, GenTableColumn::getColumnName);

    List<GenTableColumn> saveColumns = new ArrayList<>();
    dbTableColumns.forEach(column -> {
      GenUtils.initColumnField(column, table);
      if (tableColumnMap.containsKey(column.getColumnName())) {
        GenTableColumn prevColumn = tableColumnMap.get(column.getColumnName());
        column.setColumnId(prevColumn.getColumnId());
        if (column.isList()) {
          // 如果是列表，继续保留查询方式/字典类型选项
          column.setDictType(prevColumn.getDictType());
          column.setQueryType(prevColumn.getQueryType());
        }
        if (StringUtils.isNotEmpty(prevColumn.getIsRequired()) && !column.isPk()
          && (column.isInsert() || column.isEdit())
          && ((column.isUsableColumn()) || (!column.isSuperColumn()))) {
          // 如果是(新增/修改&非主键/非忽略及父属性)，继续保留必填/显示类型选项
          column.setIsRequired(prevColumn.getIsRequired());
          column.setHtmlType(prevColumn.getHtmlType());
        }
      }
      saveColumns.add(column);
    });
    if (CollUtil.isNotEmpty(saveColumns)) {
      genTableColumnMapper.insertOrUpdateBatch(saveColumns);
    }
    List<GenTableColumn> delColumns = StreamUtils.filter(tableColumns, column -> !dbTableColumnNames.contains(column.getColumnName()));
    if (CollUtil.isNotEmpty(delColumns)) {
      List<Long> ids = StreamUtils.toList(delColumns, GenTableColumn::getColumnId);
      if (CollUtil.isNotEmpty(ids)) {
        genTableColumnMapper.deleteBatchIds(ids);
      }
    }
  }

  /**
   * 批量生成代码（下载方式）
   *
   * @param tableIds 表ID数组
   * @return 数据
   */
  @Override
  public byte[] downloadCode(String[] tableIds) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ZipOutputStream zip = new ZipOutputStream(outputStream);
    for (String tableId : tableIds) {
      generatorCode(Long.parseLong(tableId), zip);
    }
    IoUtil.close(zip);
    return outputStream.toByteArray();
  }

  /**
   * 查询表信息并生成代码
   */
  private void generatorCode(Long tableId, ZipOutputStream zip) {
    // 查询表信息
    GenTable table = baseMapper.selectGenTableById(tableId);
    List<Long> menuIds = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      menuIds.add(identifierGenerator.nextId(null).longValue());
    }
    table.setMenuIds(menuIds);
    // 设置主键列信息
    setPkColumn(table);

    VelocityInitializer.initVelocity();

    VelocityContext context = VelocityUtils.prepareContext(table);

    // 获取模板列表
    List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
    for (String template : templates) {
      // 渲染模板
      StringWriter sw = new StringWriter();
      Template tpl = Velocity.getTemplate(template, Constants.UTF8);
      tpl.merge(context, sw);
      try {
        // 添加到zip
        zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
        IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
        IoUtil.close(sw);
        zip.flush();
        zip.closeEntry();
      } catch (IOException e) {
        log.error("渲染模板失败，表名：" + table.getTableName(), e);
      }
    }
  }

  /**
   * 修改保存参数校验
   *
   * @param genTable 业务信息
   */
  @Override
  public void validateEdit(GenTable genTable) {
    if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
      String options = JsonUtils.toJsonString(genTable.getParams());
      Dict paramsObj = JsonUtils.parseMap(options);
      if (StringUtils.isEmpty(paramsObj.getStr(GenConstants.TREE_CODE))) {
        throw new ServiceException("树编码字段不能为空");
      } else if (StringUtils.isEmpty(paramsObj.getStr(GenConstants.TREE_PARENT_CODE))) {
        throw new ServiceException("树父编码字段不能为空");
      } else if (StringUtils.isEmpty(paramsObj.getStr(GenConstants.TREE_NAME))) {
        throw new ServiceException("树名称字段不能为空");
      }
    }
  }

  /**
   * 设置主键列信息
   *
   * @param table 业务表信息
   */
  public void setPkColumn(GenTable table) {
    for (GenTableColumn column : table.getColumns()) {
      if (column.isPk()) {
        table.setPkColumn(column);
        break;
      }
    }
    if (ObjectUtil.isNull(table.getPkColumn())) {
      table.setPkColumn(table.getColumns().get(0));
    }

  }

  /**
   * 设置代码生成其他选项值
   *
   * @param genTable 设置后的生成对象
   */
  public void setTableFromOptions(GenTable genTable) {
    Dict paramsObj = JsonUtils.parseMap(genTable.getOptions());
    if (ObjectUtil.isNotNull(paramsObj)) {
      String treeCode = paramsObj.getStr(GenConstants.TREE_CODE);
      String treeParentCode = paramsObj.getStr(GenConstants.TREE_PARENT_CODE);
      String treeName = paramsObj.getStr(GenConstants.TREE_NAME);
      String parentMenuId = paramsObj.getStr(GenConstants.PARENT_MENU_ID);
      String parentMenuName = paramsObj.getStr(GenConstants.PARENT_MENU_NAME);

      genTable.setTreeCode(treeCode);
      genTable.setTreeParentCode(treeParentCode);
      genTable.setTreeName(treeName);
      genTable.setParentMenuId(parentMenuId);
      genTable.setParentMenuName(parentMenuName);
    }
  }

  /**
   * 生成实体文件
   *
   * @param tableId 表编号
   * @return 预览数据列表
   */
  @Override
  public Boolean createFile(Long tableId) {
    Map<String, String> dataMap = this.previewCode(tableId);
    String root = System.getProperty("user.dir");
    GenTable genTable = this.getById(tableId);
    String packagePath = genTable.getPackageName();
    packagePath = packagePath.replace(".", File.separator); // 包路径

    for (var entry : dataMap.entrySet()) {
      // region basePath
      String[] strList = StringUtils.split(genTable.getPackageName(), '.');
      String projectName = strList[strList.length - 1]; // 项目名称
      String rootPath = root + File.separator + "rattan-modules" + File.separator + "rattan-" + projectName + File.separator + "src" + File.separator + "main";
      String basePath = rootPath + File.separator + "java" + File.separator + packagePath;
      // endregion
      String folderName = Optional.ofNullable(genTable.getModuleName()).filter(f -> !f.isEmpty()).map(m -> File.separator + m).orElse("");

      if ("vm/java/domain.java.vm".equals(entry.getKey())) {
        // region domain
        String path = basePath + File.separator + "domain" + folderName
          + File.separator + genTable.getClassName() + ".java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        try {
          PrintStream stream = new PrintStream(path); // 写入的文件path
          stream.print(entry.getValue()); // 写入的字符串
          stream.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        // endregion
      } else if ("vm/java/vo.java.vm".equals(entry.getKey())) {
        // region vo
        String path = basePath + File.separator + "domain" + folderName
          + File.separator + "vo" + File.separator + genTable.getClassName() + "Vo.java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        try {
          PrintStream stream = new PrintStream(path); // 写入的文件path
          stream.print(entry.getValue()); // 写入的字符串
          stream.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        // endregion
      } else if ("vm/java/bo.java.vm".equals(entry.getKey())) {
        // region bo
        String path = basePath + File.separator + "domain" + folderName
          + File.separator + "bo" + File.separator + genTable.getClassName() + "Bo.java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        try {
          PrintStream stream = new PrintStream(path); // 写入的文件path
          stream.print(entry.getValue()); // 写入的字符串
          stream.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        // endregion
      } else if ("vm/java/service.java.vm".equals(entry.getKey())) {
        // region service
        String path = basePath + File.separator + "service" + folderName +
          File.separator + "I" + genTable.getClassName() + "Service.java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        file = new File(path); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          try {
            PrintStream stream = new PrintStream(path); // 写入的文件path
            stream.print(entry.getValue()); // 写入的字符串
            stream.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        // endregion
      } else if ("vm/java/serviceImpl.java.vm".equals(entry.getKey())) {
        // region serviceImpl
        String path = basePath + File.separator + "service" + folderName + File.separator + "impl"
          + File.separator + genTable.getClassName() + "ServiceImpl.java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        file = new File(path); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          try {
            PrintStream stream = new PrintStream(path); // 写入的文件path
            stream.print(entry.getValue()); // 写入的字符串
            stream.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        // endregion
      } else if ("vm/java/mapper.java.vm".equals(entry.getKey())) {
        // region mapper
        String path = basePath + File.separator + "mapper" + folderName + File.separator + genTable.getClassName() + "Mapper.java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        file = new File(path); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          try {
            PrintStream stream = new PrintStream(path); // 写入的文件path
            stream.print(entry.getValue()); // 写入的字符串
            stream.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        // endregion
      } else if ("vm/xml/mapper.xml.vm".equals(entry.getKey())) {
        // region mapper.xml
        String path = rootPath + File.separator + "resources" + File.separator + "mapper" + File.separator
          + genTable.getModuleName() + File.separator + genTable.getClassName() + "Mapper.xml";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        var file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        file = new File(path); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          try {
            PrintStream stream = new PrintStream(path); // 写入的文件path
            stream.print(entry.getValue()); // 写入的字符串
            stream.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        // endregion
      } else if ("vm/java/controller.java.vm".equals(entry.getKey())) {
        // region controller
        String path = basePath + File.separator + "controller" + folderName + File.separator + genTable.getClassName() + "Controller.java";

        String pathToCreate = path.substring(0, path.lastIndexOf(File.separator));
        File file = new File(pathToCreate); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          file.mkdirs(); // 创建目录
        }

        file = new File(path); // 以某路径实例化一个File对象
        if (!file.exists()) { // 如果不存在
          try {
            PrintStream stream = new PrintStream(path); // 写入的文件path
            stream.print(entry.getValue()); // 写入的字符串
            stream.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        // endregion
      }
    }

    return true;
  }
}

