package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.system.domain.dataHandler.SysExport;
import com.yiruantong.system.domain.dataHandler.SysExportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysExportBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportColumnVo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportVo;
import com.yiruantong.system.mapper.dataHandler.SysExportMapper;
import com.yiruantong.system.service.dataHandler.ISysExportColumnService;
import com.yiruantong.system.service.dataHandler.ISysExportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.generator.domain.GenTable;
import com.yiruantong.generator.domain.GenTableColumn;
import com.yiruantong.generator.service.IGenTableService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 导出设置Service业务层处理
 *
 * @author YRT
 * @date 2023-08-05
 */
@RequiredArgsConstructor
@Service
public class SysExportServiceImpl extends ServiceImplPlus<SysExportMapper, SysExport, SysExportVo, SysExportBo> implements ISysExportService, ITenantInitService {
  private final ISysExportColumnService sysExportColumnService;
  private final IGenTableService genTableService;

  @Override
  public void beforeSaveEditor(SaveEditorBo<SysExportBo> saveEditorBo) {
    Long customImportId = saveEditorBo.getData().getMaster().getCustomExportId();
    // 为母版账套时，自定义字段默认为主键ID
    if (ObjectUtil.isEmpty(customImportId) && StringUtils.equals(TenantHelper.getTenantId(), TenantConstants.DEFAULT_TENANT_ID)) {
      saveEditorBo.getData().getMaster().setCustomExportId(saveEditorBo.getData().getMaster().getExportId());
    }
  }

  //#region saveFieldLeft
  @Override
  public R<Void> saveFieldLeft(Map<String, Object> map) {
    var colInfo = new SysExportColumn();
    BeanUtil.copyProperties(map, colInfo);
    boolean save;
    if (colInfo.getColumnId() == null) {
      save = sysExportColumnService.save(colInfo);
    } else {
      save = sysExportColumnService.updateById(colInfo);
    }
    return save ? R.ok("" + colInfo.getColumnId()) : R.fail();
  }
  //#endregion

  //#region saveModule
  @Override
  public R<Void> saveModule(Map<String, Object> map) {
    Long exportId = Convert.toLong(map.get("exportId"));
    Assert.isFalse(!NumberUtils.isPositiveInteger(exportId), "更新ID不能为空");

    LambdaUpdateWrapper<SysExport> exportLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    exportLambdaUpdateWrapper
      .set(SysExport::getTableNameCn, map.get("tableNameCn"))
      .set(SysExport::getTableName, map.get("tableName"))
      .set(SysExport::getOrderNum, map.get("orderNum"))
      .set(SysExport::getParentId, map.get("parentId"))
      .set(SysExport::getCustomExportId, map.get("customExportId"))
      .eq(SysExport::getExportId, exportId);
    this.update(exportLambdaUpdateWrapper);

    return R.ok();
  }
  //#endregion

  //#region deleteField
  @Override
  public R<Void> deleteField(Map<String, Object> map) {
    List<Long> idList = Convert.toList(Long.class, map.get("idList"));
    Assert.isFalse(CollUtil.isEmpty(idList), "没有可删除的数据");

    this.sysExportColumnService.deleteByIds(idList.toArray(Long[]::new));
    return R.ok("删除完毕");
  }
  //#endregion

  //#region changeEnable
  @Override
  public R<Void> changeEnable(Map<String, Object> map) {
    List<Long> idList = Convert.toList(Long.class, map.get("idList"));
    int enable = Convert.toInt(map.get("enable"));
    Assert.isFalse(CollUtil.isEmpty(idList), "没有可修改的数据");

    LambdaUpdateWrapper<SysExportColumn> columnLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    columnLambdaUpdateWrapper.set(SysExportColumn::getEnable, enable)
      .in(SysExportColumn::getColumnId, idList);

    this.sysExportColumnService.update(columnLambdaUpdateWrapper);
    return R.ok();
  }
  //#endregion

  //#region importModule
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> importModule(Map<String, Object> map) {
    Long exportId = Convert.toLong(map.get("exportId"));
    String tableName = Convert.toStr(map.get("tableName"));

    SysExport sysExport = this.getById(exportId);
    Assert.isFalse(ObjectUtil.isEmpty(sysExport), "原模块不存在！");

    GenTable genTable = genTableService.selectGenTableByName(tableName);
    Assert.isFalse(ObjectUtil.isEmpty(genTable), "原模块不存在！");

    // 主表数据
    SysExport newSysExport = new SysExport();
    newSysExport.setParentId(sysExport.getParentId());
    newSysExport.setTableName(genTable.getTableName());
    newSysExport.setTableNameCn(genTable.getTableComment());
    newSysExport.setOrderNum(0L);
    newSysExport.setEnable(EnableEnum.ENABLE.getId());
    this.save(newSysExport);

    // 明细表数据
    List<GenTableColumn> genTableColumns = genTableService.selectGenTableColumnListByTableId(genTable.getTableId());
    List<SysExportColumn> sysExportColumnList = genTableColumns.stream().map(col -> {
      SysExportColumn sysExportColumn = new SysExportColumn();
      sysExportColumn.setExportId(newSysExport.getExportId());
      sysExportColumn.setColumnName(col.getColumnName());
      sysExportColumn.setCnName(col.getColumnComment());
      sysExportColumn.setOrderNum(0L);
      sysExportColumn.setEnable(EnableEnum.ENABLE.getId());

      return sysExportColumn;
    }).toList();
    sysExportColumnService.saveBatch(sysExportColumnList);

    return R.ok("导入成功");
  }
  //#endregion

  //#region deleteModule
  @Override
  public R<Void> deleteModule(Map<String, Object> map) {
    Long exportId = Convert.toLong(map.get("exportId"));
    SysExport sysExport = this.getInfoByCustomExportId(exportId);
    Assert.isFalse(ObjectUtil.isEmpty(sysExport), "原模块不存在！");

    return this.removeById(sysExport) ? R.ok("删除成功") : R.fail();
  }
  //#endregion

  //#region exportData
  @Override
  public void exportData(HttpServletRequest request, HttpServletResponse response, String pageQuery) throws IOException {
    PageQuery _pageQuery = JsonUtils.parseObject(pageQuery, PageQuery.class);
    Long exportId = Convert.toLong(_pageQuery.getOtherParams().get("exportId"));
    SysExport sysExport = this.getInfoByCustomExportId(exportId);
    List<SysExportColumn> sysExportColumnList = this.sysExportColumnService.selectListByMainId(sysExport.getExportId());

    List<List<String>> headList = sysExportColumnList.stream().map(m -> {
      List<String> head = ListUtils.newArrayList();
      head.add(m.getCnName());
      return head;
    }).toList();

    try {
      String listMethod = _pageQuery.getListMethod();
      if (StringUtils.isEmpty(listMethod)) {
        listMethod = "pageList";
      }
      // 发送POST请求获取导出数据
      String url = "http://127.0.0.1:7852" + _pageQuery.getPrefixRouter() + "/" + listMethod;
      Map<String, List<String>> headerMap = new HashMap<>();
      Enumeration<String> headerNames = request.getHeaderNames();
      while (headerNames.hasMoreElements()) //读取请求消息头
      {
        String name = headerNames.nextElement();
        headerMap.put(name, List.of(request.getHeader(name)));
      }
      String postResult = HttpUtil.createPost(url).header(headerMap).body(pageQuery).execute().body();
      TableDataInfo<Map<String, Object>> tableDataInfo = new TableDataInfo<>();
      tableDataInfo = JsonUtils.parseObject(postResult, tableDataInfo.getClass());
      Assert.isFalse(ObjectUtil.isNull(tableDataInfo), "数据不存在！");
      Assert.isFalse(!tableDataInfo.isResult(), tableDataInfo.getMsg());

      List<List<Object>> dataList = new ArrayList<>();
      for (var item : tableDataInfo.getRows()) {
        List<Object> row = new ArrayList<>();
        for (var colInfo : sysExportColumnList) {
          String columnName = StringUtils.toCamelCase(colInfo.getColumnName());
          Object rowValue = item.get(columnName);
          if (StringUtils.equals(colInfo.getDataType(), DataTypeEnum.STRING.getCode())) {
            rowValue = Convert.toStr(rowValue);
          } else if (NumberUtils.isNumber(Convert.toStr(rowValue))) {
            rowValue = Convert.toNumber(rowValue);
          }

          // 扩展字段处理
          if (StringUtils.isNotEmpty(Convert.toStr(item.get("expandFields"))) && ObjectUtil.isNotNull(colInfo.getIsExpandField()) && colInfo.getIsExpandField() == 1) {
            HashMap jsonData = JsonUtils.parseObject(Convert.toStr(item.get("expandFields")), HashMap.class);
            rowValue = Convert.toStr(jsonData.get(columnName));
          }

          // 日期格式化
          if (StringUtils.equals(colInfo.getDataType(), DataTypeEnum.DATE.getCode()) || StringUtils.equals(colInfo.getDataType(), DataTypeEnum.DATETIME.getCode())) {
            if (!StringUtils.isEmpty(colInfo.getDataFormatter())) {
              rowValue = DateUtil.format(Convert.toDate(rowValue), colInfo.getDataFormatter());
            }
          }
          row.add(rowValue);


        }
        dataList.add(row);
      }

      String fileName = URLEncoder.encode(sysExport.getTableNameCn() + "数据导出", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
      response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
      // 头的策略
      HorizontalCellStyleStrategy horizontalCellStyleStrategy = getHorizontalCellStyleStrategy();
      EasyExcel
        .write(response.getOutputStream())
        .head(headList)
        .registerWriteHandler(horizontalCellStyleStrategy) // 字体背景色策略
        .registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 20, (short) 20)) // 简单的行高策略
        .registerWriteHandler(new AbstractColumnWidthStyleStrategy() {
          @Override
          protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> list, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            if (isHead) {
              Sheet sheet = writeSheetHolder.getSheet();
              var value = cell.getStringCellValue();
              int width = 3000;
              if (StringUtils.contains(value, "商品名")) {
                width = 6000;
              }
              int columnIndex = cell.getColumnIndex();
              // 列宽40
              sheet.setColumnWidth(columnIndex, width);
              // 行高7
              sheet.setDefaultRowHeight((short) 700);

              // 设置格式
//              head.getFieldName();
//              short format = sheet.getWorkbook().createDataFormat().getFormat("yyyy-MM-dd");
//              CellStyle cellStyle = cell.getCellStyle();
//              cellStyle.setDataFormat(format);
//              cell.setCellStyle(cellStyle);
            }
          }
        })
//        .registerWriteHandler(new SimpleColumnWidthStyleStrategy(15)) // 简单的列宽策略
//        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
        .sheet(sysExport.getTableNameCn())
        .doWrite(dataList);
    } catch (Exception e) {
      // 重置response
      response.reset();
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      Map<String, String> map = MapUtils.newHashMap();
      map.put("status", "failure");
      map.put("message", "下载文件失败" + e.getMessage());
      response.getWriter().println(JsonUtils.toJsonString(map));
    }
  }

  @NotNull
  private HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
    // 标题策略
    WriteCellStyle headWriteCellStyle = new WriteCellStyle();
    // 背景设置为灰色
    headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    WriteFont headWriteFont = new WriteFont();
    headWriteFont.setFontHeightInPoints((short) 10);
    headWriteCellStyle.setWriteFont(headWriteFont);

    // 内容策略
    WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
    //边框
    contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
    contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
    contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
    contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
    // 水平居中
    contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
    // 垂直居中
    contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    // 设置自动换行，前提内容中需要加「\n」才有效
    contentWriteCellStyle.setWrapped(true);

    WriteFont contentWriteFont = new WriteFont();
    // 字体大小
    contentWriteFont.setFontHeightInPoints((short) 10);
    contentWriteCellStyle.setWriteFont(contentWriteFont);

    // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
    return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
  }
  //#endregion

  //#region tenantCopy

  /**
   * 租户数据拷贝
   *
   * @param fromPackageId  来源套餐ID
   * @param targetTenantId 目标租户ID
   * @param targetUserId   目标用户ID
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void tenantCopy(Long fromPackageId, String targetTenantId, Long targetUserId) {
    TenantHelper.enableIgnore();
    LambdaQueryWrapper<SysExport> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysExport::getTenantId, targetTenantId);
    // 判断已经存在值，不允许重复复制
    if (this.exists(lambdaQueryWrapper)) {
      return;
    }

    // 获取拷贝数据源
    String fromTenantId;
    lambdaQueryWrapper = new LambdaQueryWrapper<>();
    if (NumberUtils.equals(fromPackageId, TenantConstants.PACKAGE_ID_1)) {
      fromTenantId = TenantConstants.DEFAULT_TENANT_ID;
    } else {
      fromTenantId = fromPackageId + '-' + TenantConstants.DEFAULT_TENANT_ID;
    }
    lambdaQueryWrapper.eq(SysExport::getTenantId, fromTenantId);
    List<SysExport> regularList = this.list(lambdaQueryWrapper);

    // 目标数据
    List<SysExport> newList = regularList.stream().peek(m -> {
      m.setTenantId(targetTenantId);
    }).toList();

    BiConsumer<Number, Number> consumer = getNumberBiConsumer(fromTenantId, newList);
    consumer.accept(BigDecimal.ZERO, BigDecimal.ZERO);

    TenantHelper.disableIgnore();
  }

  @NotNull
  private BiConsumer<Number, Number> getNumberBiConsumer(String fromTenantId, List<SysExport> newList) {
    var the = this;
    return new BiConsumer<>() {
      final BiConsumer<Number, Number> self = this; // 使用局部变量引用

      /**
       * BiConsumer构造函数
       *
       * @param parentId    原父级ID
       * @param newParentId 新父级ID
       */
      @Transactional(rollbackFor = Exception.class)
      @Override
      public void accept(Number parentId, Number newParentId) {
        var dataList = newList.stream().filter(f -> B.isEqual(f.getParentId(), parentId)).toList();
        for (var item : dataList) {
          Long id = item.getExportId();
          item.setParentId(Convert.toLong(newParentId));
          item.setExportId(null);
          // 从母版中复制时，需要设置自定义ID值
          if (StringUtils.equals(fromTenantId, TenantConstants.DEFAULT_TENANT_ID)) {
            item.setCustomExportId(id);
          }
          the.save(item);

          // 处理明细数据
          List<SysExportColumn> importColumnList = sysExportColumnService.selectListByMainId(id);
          List<SysExportColumn> list = importColumnList.stream().peek(p -> {
            p.setExportId(item.getExportId());
            p.setTenantId(item.getTenantId());
            p.setColumnId(null);
          }).toList();
          sysExportColumnService.saveBatch(list);

          // 使用局部变量self而不是this，递归执行
          self.accept(id, item.getExportId());
        }
      }
    };
  }
  //#endregion

  //#region getInfoByCustomExportId
  @Override
  public SysExport getInfoByCustomExportId(Long exportId) {
    LambdaQueryWrapper<SysExport> exportLambdaQueryWrapper = new LambdaQueryWrapper<>();
    exportLambdaQueryWrapper.eq(SysExport::getCustomExportId, exportId);
    ISysExportService sysImportService = SpringUtils.getBean(ISysExportService.class);
    SysExport sysExport = sysImportService.getOne(exportLambdaQueryWrapper);
    if (ObjectUtil.isNull(sysExport)) {
      exportLambdaQueryWrapper = new LambdaQueryWrapper<>();
      exportLambdaQueryWrapper.eq(SysExport::getExportId, exportId);
      sysExport = sysImportService.getOne(exportLambdaQueryWrapper);
    }
    return sysExport;
  }
  //#endregion

  //#region selectExportColumnList

  /**
   * 获取导出字段
   *
   * @param map 参数
   * @return List<SysExportColumnVo>
   */
  @Override
  public R<List<SysExportColumnVo>> selectExportColumnList(Map<String, Object> map) {
    Long exportId = Convert.toLong(map.get("exportId"));
    SysExport sysExport = this.getInfoByCustomExportId(exportId);
    Assert.isFalse(ObjectUtil.isEmpty(sysExport), "模块不存在！");

    LambdaQueryWrapper<SysExportColumn> exportColumnLambdaQueryWrapper = new LambdaQueryWrapper<>();
    exportColumnLambdaQueryWrapper
      .eq(SysExportColumn::getExportId, sysExport.getExportId())
      .eq(SysExportColumn::getEnable, EnableEnum.ENABLE.getId())
      .orderByDesc(SysExportColumn::getOrderNum);
    List<SysExportColumnVo> exportColumnList = sysExportColumnService.selectList(exportColumnLambdaQueryWrapper);

    return R.ok(exportColumnList);
  }
  //#endregion

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysExport> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysExport::getTableNameCn, filterText).last("limit 10");
    List<SysExport> exportList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var export : exportList) {
      String menuName = export.getTableNameCn();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(export.getParentId());
      parentNameAll = parentNameAll + "&gt;" + menuName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("exportId", export.getExportId());
      resultMap.put("tableNameCn", export.getTableNameCn());
      resultMap.put("parentNameAll", parentNameAll);
      mapList.add(resultMap);
    }


    return R.ok(mapList);
  }

  @NotNull
  private Function<Long, String> getParentNode() {
    var the = this;
    return new Function<>() {
      final Function<Long, String> self = this; // 使用局部变量引用

      /**
       * Function构造函数
       *
       * @param parentId    父级ID
       */
      @Override
      public String apply(Long parentId) {
        LambdaQueryWrapper<SysExport> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysExport::getExportId, parentId);
        SysExport sysMenu = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysMenu)) {
          String _parentName = self.apply(sysMenu.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysMenu.getTableNameCn();
        }
        return parentName;
      }
    };
  }
}
