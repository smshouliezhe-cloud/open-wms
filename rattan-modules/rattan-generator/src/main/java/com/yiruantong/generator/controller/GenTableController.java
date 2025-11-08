package com.yiruantong.generator.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.yiruantong.generator.domain.vo.GenTableVo;
import com.yiruantong.generator.mapper.GenTableMapper;
import com.yiruantong.generator.service.IGenTableService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.helper.DataBaseHelper;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableColumn;
import com.yiruantong.generator.domain.bo.GenTableBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tool/gen")
public class GenTableController extends AbstractController<GenTableMapper, GenTable, GenTableVo, GenTableBo> {

  private final IGenTableService genTableService;

  /**
   * 查询代码生成列表
   */
//  @SaCheckPermission("tool:gen:list")
  @GetMapping("/list")
  public TableDataInfo<GenTable> genList(GenTable genTable, PageQuery pageQuery) {
    return genTableService.selectPageGenTableList(genTable, pageQuery);
  }

  /**
   * 修改代码生成业务
   *
   * @param tableId 表ID
   */
//  @SaCheckPermission("tool:gen:query")
  @GetMapping(value = "/{tableId}")
  public R<Map<String, Object>> getInfo(@PathVariable Long tableId) {
    GenTable table = genTableService.selectGenTableById(tableId);
    List<GenTable> tables = genTableService.selectGenTableAll();
    List<GenTableColumn> list = genTableService.selectGenTableColumnListByTableId(tableId);
    Map<String, Object> map = new HashMap<>(3);
    map.put("info", table);
    map.put("rows", list);
    map.put("tables", tables);
    return R.ok(map);
  }

  /**
   * 查询数据库列表
   */
//  @SaCheckPermission("tool:gen:list")
  @GetMapping("/db/list")
  public TableDataInfo<GenTable> dataList(GenTable genTable, PageQuery pageQuery) {
    return genTableService.selectPageDbTableList(genTable, pageQuery);
  }

  /**
   * 查询数据表字段列表
   *
   * @param tableId 表ID
   */
//  @SaCheckPermission("tool:gen:list")
  @GetMapping(value = "/column/{tableId}")
  public R<TableDataInfo<GenTableColumn>> columnList(@PathVariable Long tableId) {
    TableDataInfo<GenTableColumn> dataInfo = new TableDataInfo<>();
    List<GenTableColumn> list = genTableService.selectGenTableColumnListByTableId(tableId);
    dataInfo.setRows(list);
    dataInfo.setTotal(list.size());
    return R.ok(dataInfo);
  }

  /**
   * 查询数据表字段列表
   *
   * @param tableName 表名
   */
//  @SaCheckPermission("tool:gen:list")
  @GetMapping(value = "/columnListByTableName/{tableName}")
  public R<TableDataInfo<GenTableColumn>> columnListByTableName(@PathVariable String tableName) {
    var genTableInfo = genTableService.selectGenTableByName(tableName);
    TableDataInfo<GenTableColumn> dataInfo = new TableDataInfo<>();
    List<GenTableColumn> list = genTableService.selectGenTableColumnListByTableId(genTableInfo.getTableId());
    dataInfo.setRows(list);
    dataInfo.setTotal(list.size());
    return R.ok(dataInfo);
  }

  /**
   * 导入表结构（保存）
   *
   * @param requestMap 前端传递参数
   */
//  @SaCheckPermission("tool:gen:import")
  @Log(title = "代码生成", businessType = BusinessType.IMPORT)
  @PostMapping("/importTable")
  public R<List<Map<String, Object>>> importTableSave(@RequestBody Map<String, String> requestMap) {
    String tables = requestMap.get("tables");
    String dataName = requestMap.get("dataName");
    String[] tableNames = Convert.toStrArray(tables);
    // 查询表信息
    List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames, dataName);
    genTableService.importGenTable(tableList, dataName, requestMap);

    return R.ok(tableList.stream().map(m -> {
      Map<String, Object> map = new HashMap<>();
      map.put("tableId", m.getTableId());
      map.put("parentId", m.getParentId());
      map.put("tableName", m.getTableName());
      map.put("tableComment", m.getTableComment());
      return map;
    }).toList());
  }

  /**
   * 修改保存代码生成业务
   */
//  @SaCheckPermission("tool:gen:edit")
  @Log(title = "代码生成", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editSave(@Validated @RequestBody GenTable genTable) {
    genTableService.validateEdit(genTable);
    genTableService.updateGenTable(genTable);
    return R.ok();
  }

  /**
   * 删除代码生成
   *
   * @param tableIds 表ID串
   */
//  @SaCheckPermission("tool:gen:remove")
  @Log(title = "代码生成", businessType = BusinessType.DELETE)
  @DeleteMapping("/{tableIds}")
  public R<Void> remove(@PathVariable Long[] tableIds) {
    genTableService.deleteGenTableByIds(tableIds);
    return R.ok();
  }

  /**
   * 预览代码
   *
   * @param tableId 表ID
   */
//  @SaCheckPermission("tool:gen:preview")
  @GetMapping("/preview/{tableId}")
  public R<Map<String, String>> preview(@PathVariable("tableId") Long tableId) throws IOException {
    Map<String, String> dataMap = genTableService.previewCode(tableId);
    return R.ok(dataMap);
  }

  /**
   * 生成实体文件
   *
   * @param tableId 表ID
   */
  @GetMapping("/createFile/{tableId}")
  public R<Boolean> createFile(@PathVariable("tableId") Long tableId) throws IOException {
    boolean result = genTableService.createFile(tableId);
    return R.ok(result);
  }

  /**
   * 生成代码（下载方式）
   *
   * @param tableId 表ID
   */
//  @SaCheckPermission("tool:gen:code")
  @Log(title = "代码生成", businessType = BusinessType.GENCODE)
  @GetMapping("/download/{tableId}")
  public void download(HttpServletResponse response, @PathVariable("tableId") Long tableId) throws IOException {
    byte[] data = genTableService.downloadCode(tableId);
    genCode(response, data);
  }

  /**
   * 生成代码（自定义路径）
   *
   * @param tableId 表ID
   */
//  @SaCheckPermission("tool:gen:code")
  @Log(title = "代码生成", businessType = BusinessType.GENCODE)
  @GetMapping("/genCode/{tableId}")
  public R<Void> genCode(@PathVariable("tableId") Long tableId) {
    genTableService.generatorCode(tableId);
    return R.ok();
  }

  /**
   * 同步数据库
   *
   * @param tableId 表ID
   */
//  @SaCheckPermission("tool:gen:edit")
  @Log(title = "代码生成", businessType = BusinessType.UPDATE)
  @GetMapping("/synchDb/{tableId}")
  public R<Void> synchDb(@PathVariable("tableId") Long tableId) {
    genTableService.synchDb(tableId);
    return R.ok();
  }

  /**
   * 批量生成代码
   *
   * @param tableIdStr 表ID串
   */
//  @SaCheckPermission("tool:gen:code")
  @Log(title = "代码生成", businessType = BusinessType.GENCODE)
  @GetMapping("/batchGenCode")
  public void batchGenCode(HttpServletResponse response, String tableIdStr) throws IOException {
    String[] tableIds = Convert.toStrArray(tableIdStr);
    byte[] data = genTableService.downloadCode(tableIds);
    genCode(response, data);
  }

  /**
   * 生成zip文件
   */
  private void genCode(HttpServletResponse response, byte[] data) throws IOException {
    response.reset();
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    response.setHeader("Content-Disposition", "attachment; filename=\"rattan.zip\"");
    response.addHeader("Content-Length", "" + data.length);
    response.setContentType("application/octet-stream; charset=UTF-8");
    IoUtil.write(response.getOutputStream(), false, data);
  }

  /**
   * 查询数据源名称列表
   */
//  @SaCheckPermission("tool:gen:list")
  @GetMapping(value = "/getDataNames")
  public R<Object> getCurrentDataSourceNameList() {
    return R.ok(DataBaseHelper.getDataSourceNameList());
  }
}
