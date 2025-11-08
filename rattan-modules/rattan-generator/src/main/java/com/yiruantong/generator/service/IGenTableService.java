package com.yiruantong.generator.service;

import com.yiruantong.generator.domain.vo.GenTableVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableColumn;
import com.yiruantong.generator.domain.bo.GenTableBo;

import java.util.List;
import java.util.Map;

/**
 * 业务 服务层
 *
 * @author YiRuanTong
 */
public interface IGenTableService extends IServicePlus<GenTable, GenTableVo, GenTableBo> {

  /**
   * 查询业务字段列表
   *
   * @param tableId 业务字段编号
   * @return 业务字段集合
   */
  List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

  /**
   * 查询业务列表
   *
   * @param genTable 业务信息
   * @return 业务集合
   */
  TableDataInfo<GenTable> selectPageGenTableList(GenTable genTable, PageQuery pageQuery);

  /**
   * 查询据库列表
   *
   * @param genTable 业务信息
   * @return 数据库表集合
   */
  TableDataInfo<GenTable> selectPageDbTableList(GenTable genTable, PageQuery pageQuery);

  /**
   * 查询据库列表
   *
   * @param tableNames 表名称组
   * @param dataName   数据源名称
   * @return 数据库表集合
   */
  List<GenTable> selectDbTableListByNames(String[] tableNames, String dataName);

  /**
   * 查询所有表信息
   *
   * @return 表信息集合
   */
  List<GenTable> selectGenTableAll();

  /**
   * 查询业务信息
   *
   * @param id 业务ID
   * @return 业务信息
   */
  GenTable selectGenTableById(Long id);

  /**
   * 查询业务信息
   *
   * @param tableName 表名称
   * @return 业务信息
   */
  GenTable selectGenTableByName(String tableName);

  /**
   * 修改业务
   *
   * @param genTable 业务信息
   * @return 结果
   */
  void updateGenTable(GenTable genTable);

  /**
   * 删除业务信息
   *
   * @param tableIds 需要删除的表数据ID
   * @return 结果
   */
  void deleteGenTableByIds(Long[] tableIds);

  /**
   * 导入表结构
   *
   * @param tableList 导入表列表
   * @param dataName  数据源名称
   */
  void importGenTable(List<GenTable> tableList, String dataName, Map<String, String> requestMap);

  /**
   * 预览代码
   *
   * @param tableId 表编号
   * @return 预览数据列表
   */
  Map<String, String> previewCode(Long tableId);

  /**
   * 预览代码
   *
   * @param tableId 表编号
   * @return 生成实体文件
   */
  Boolean createFile(Long tableId);

  /**
   * 生成代码（下载方式）
   *
   * @param tableId 表名称
   * @return 数据
   */
  byte[] downloadCode(Long tableId);

  /**
   * 生成代码（自定义路径）
   *
   * @param tableId 表名称
   */
  void generatorCode(Long tableId);

  /**
   * 同步数据库
   *
   * @param tableId 表名称
   */
  void synchDb(Long tableId);

  /**
   * 批量生成代码（下载方式）
   *
   * @param tableIds 表ID数组
   * @return 数据
   */
  byte[] downloadCode(String[] tableIds);

  /**
   * 修改保存参数校验
   *
   * @param genTable 业务信息
   */
  void validateEdit(GenTable genTable);
}
