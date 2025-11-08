package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ColorUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.ImportValidateTypeEnum;
import com.yiruantong.common.core.enums.system.ImportExpandFieldTypeEnum;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.redis.utils.QueueUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.dataHandler.SysDropdown;
import com.yiruantong.system.domain.dataHandler.SysImport;
import com.yiruantong.system.domain.dataHandler.SysImportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysImportBo;
import com.yiruantong.system.domain.dataHandler.vo.SysImportVo;
import com.yiruantong.system.mapper.dataHandler.SysImportMapper;
import com.yiruantong.system.service.dataHandler.ISysDropdownService;
import com.yiruantong.system.service.dataHandler.ISysImportColumnService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 导入设置Service业务层处理
 *
 * @author YRT
 * @date 2023-08-05
 */
@RequiredArgsConstructor
@Service
public class SysImportServiceImpl extends ServiceImplPlus<SysImportMapper, SysImport, SysImportVo, SysImportBo> implements ISysImportService, ITenantInitService {
  //#region 变量
  private final SqlSessionTemplate sqlSessionTemplate;
  private final ISysImportColumnService sysImportColumnService;
  private final ISysDropdownService sysDropdownService;
  private String KEY = "importKey";
  private boolean isExistError = false;
  private List<SysImportColumn> importColumnList;

  @Override
  public void beforeSaveEditor(SaveEditorBo<SysImportBo> saveEditorBo) {
    Long customImportId = saveEditorBo.getData().getMaster().getCustomImportId();
    // 为母版账套时，自定义字段默认为主键ID
    if (ObjectUtil.isEmpty(customImportId) && StringUtils.equals(TenantHelper.getTenantId(), TenantConstants.DEFAULT_TENANT_ID)) {
      saveEditorBo.getData().getMaster().setCustomImportId(saveEditorBo.getData().getMaster().getImportId());
    }
  }

  @Override
  public void setKey(String key) {
    if (StringUtils.isNotEmpty(key)) {
      this.KEY = key;
    }
  }

  @Override
  public boolean isError() {
    return this.isExistError;
  }

  @Override
  public void setError(boolean isExistError) {
    this.isExistError = isExistError;
  }
  //#endregion

  //#region commonCheck
  @Override
  public R<Void> commonCheck(List<Map<String, Object>> excelDataList, Long importId, HttpServletRequest request, LoginUser loginUser) {
    this.importColumnList = new ArrayList<>();
    this.writeMsg("开始校验数据...");
    SysImport sysImport = this.getInfoByCustomImportId(importId);
    if (ObjectUtil.isEmpty(sysImport)) {
      this.writeMsgRed("导入模板ID【" + importId + "】不存在！");
      return R.fail();
    }

    if (CollUtil.isEmpty(this.importColumnList)) {
      this.importColumnList = sysImportColumnService.selectListByMainId(sysImport.getImportId());
    }

    if (ObjectUtil.isEmpty(this.importColumnList)) {
      this.writeMsgRed("导入字段不能为空");
      return R.fail();
    }

    // 字段中文转英文名
    this.changeColumName(excelDataList, request);
    // 规则验证
    this.validate(excelDataList, loginUser);

    return this.isExistError ? R.fail() : R.ok();
  }
  //#endregion

  //#region writeMsg
  public void writeMsg(String msg, Color color, Object... params) {
    if (ObjectUtil.isNotEmpty(color) && !StringUtils.equals(msg, "-1")) {
      msg = "<span style='color:" + ColorUtil.toHex(color) + ";font-weight:bold;'>" + msg + "</span>";
    }
    if (!StringUtils.equals(msg, "-1")) {
      msg += "<span style='color:#888;margin-left:15px;'>" + DateUtil.now() + "</span>";
    }
    msg = StringUtils.format(msg, params);
    QueueUtils.addQueueObject(KEY, msg);
    QueueUtils.getClient().getQueue(KEY).expire(Instant.now().plus(10, ChronoUnit.MINUTES)); // 设置过期时间
  }

  public void writeMsg(String msg, Object... params) {
    this.writeMsg(msg, null, params);
  }

  public void writeMsgRed(String msg, Object... params) {
    this.isExistError = true; // 红色标示存在错误
    this.writeMsg(msg, Color.red, params);
  }

  public void writeMsgBlue(String msg, Object... params) {
    this.writeMsg(msg, Color.blue, params);
  }

  @Override
  public void writeEnd(String msg, Object... params) {
    this.writeMsgRed(msg, params);
    this.writeMsg("-1");
  }

  @Override
  public void writeEnd() {
    this.writeMsg("-1");
  }
  //#endregion

  //#region 断言校验
  public <X extends IllegalArgumentException> void isFalse(boolean expression, Supplier<X> errorSupplier) throws IllegalArgumentException {
    if (expression) {
      throw errorSupplier.get();
    }
  }

  @Override
  public void isAssert(boolean expression, String errorMsgTemplate, Object... params) {
    isFalse(expression, () -> {
      this.writeEnd(errorMsgTemplate, params);
      return new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params));
    });
  }
  //#endregion

  //#region 将Excel中的中文字段转换为英文字段名

  /**
   * 将Excel中的中文字段转换为英文字段名
   */
  private void changeColumName(List<Map<String, Object>> excelDataList, HttpServletRequest request) {
    String storageName = request.getParameter("storageName");
    String consignorName = request.getParameter("consignorName");

    List<Map<String, Object>> excelDataListTemp = new ArrayList<>();
    for (var item : excelDataList) {
      // 特殊处理仓库和货主默认值
      if (ObjectUtil.isEmpty(item.get("仓库名称"))) {
        item.put("仓库名称", storageName);
      }
      if (ObjectUtil.isEmpty(item.get("货主名称"))) {
        item.put("货主名称", consignorName);
      }

      Map<String, Object> tempMap = new HashMap<>();
      for (var entry : item.entrySet()) {
        String key = entry.getKey();
        SysImportColumn colInfo = this.importColumnList.stream().filter(f -> Objects.equals(Convert.toStr(f.getCnName()), key)).findFirst().orElse(null);
        String val = Convert.toStr(item.get(key));
        if (StringUtils.isEmpty(val)) {
          val = null;
        }

        if (ObjectUtil.isNotNull(colInfo)) {
          tempMap.put(colInfo.getColumnName(), val);
        } else {
          tempMap.put(key, val);
        }
      }
      excelDataListTemp.add(tempMap);
    }
    excelDataList.clear();
    excelDataList.addAll(excelDataListTemp);
  }
  //#endregion

  //#region validate
  private void validate(List<Map<String, Object>> excelDataList, LoginUser loginUser) {
    Map<String, List<LinkedHashMap<String, Object>>> dict = new HashMap<>(); // 字典更新集合数据

    int i = 0;
    for (var row : excelDataList) {
      i++;
      for (var colInfo : this.importColumnList) {
        String columnName = colInfo.getColumnName(); // 字段名称
        var val = row.get(columnName); // 字段值
        // 必填验证
        if (NumberUtil.equals(colInfo.getIsMust(), EnableEnum.ENABLE.getId())) {
          // 校验货主名称
          if (ObjectUtil.isEmpty(val)) {
            this.writeMsgRed("第{}行：{}不能为空", i, colInfo.getCnName());
            continue;
          }
        }

        String msg = colInfo.getValDescription();
        List<String> dictList = List.of(ImportValidateTypeEnum.DICTIONARY_VALIDATION.getName(), ImportValidateTypeEnum.DICTIONARY_TRANSLATE.getName(), ImportValidateTypeEnum.DICTIONARY_BRING_IN.getName());
        if (Objects.equals(colInfo.getValidate(), ImportValidateTypeEnum.REGULAR_VALIDATION.getName())) {
          boolean result = this.regularValidate(colInfo.getValExpression(), Convert.toStr(val));
          if (!result) {
            if (StringUtils.isEmpty(msg)) msg = "[" + val + "]正则验证不通过";
            this.writeMsgRed("第{}行：{}", i, msg);
          }
        } else if (StringUtils.isNotEmpty(colInfo.getValidate()) && CollUtil.contains(dictList, colInfo.getValidate())) {
          this.dicValidate(dict, colInfo, row, excelDataList, i, loginUser);
        } else if (Objects.equals(colInfo.getValidate(), ImportValidateTypeEnum.FORMULA_OPERATION.getName())) {
          String express = colInfo.getValExpression();
          if (ObjectUtil.isNotEmpty(express)) {
            try {
              row.put(columnName, express); // 动态计算
            } catch (Exception exception) {
              this.writeMsgRed("第{}行：{}公式运算错误", i, colInfo.getCnName());
            }
          }
        } else if (Objects.equals(colInfo.getValidate(), ImportValidateTypeEnum.SQL_UPDATE.getName())) {
          var dataList = dict.get(colInfo.getColumnName());
          if (dataList.isEmpty()) {
            try {
              // 缓存字典数据
              dataList = this.getDicList(colInfo, i, excelDataList, loginUser);
              dict.put(colInfo.getColumnName(), dataList);
            } catch (Exception exception) {
              this.writeMsgRed("第{}行：{}列SQL设置不正确", i, colInfo.getCnName());
            }
          }

          var dictItem = dataList.stream().filter(item -> Objects.equals(Convert.toStr(item.get("name")), Convert.toStr(val))).findFirst().orElse(null);
          if (Objects.isNull(dictItem)) {
            this.writeMsgRed("第{}行：{}列的值{}在下拉框设置中不存在", i, colInfo.getCnName(), val);
          } else {
            // 中文名转换为ID值
            row.put(colInfo.getColumnName(), dictItem.get("id"));
          }
        } else if (Objects.equals(colInfo.getValidate(), ImportValidateTypeEnum.JSON_UPDATE.getName())) {
          var dataList = dict.get(colInfo.getColumnName());
          if (dataList.isEmpty()) {
            try {
              // 缓存字典数据
              var _dataList = StreamUtils.toList(JsonUtils.parseArray(colInfo.getValExpression(), Map.class));
              var dictItem = _dataList.stream().filter(item -> Objects.equals(Convert.toStr(item.get("name")), Convert.toStr(val))).findFirst().orElse(null);
              if (Objects.isNull(dictItem)) {
                this.writeMsgRed("第{}行：{}列的值{}在下拉框设置中不存在", i, colInfo.getCnName(), val);
              } else {
                // 中文名转换为ID值
                row.put(colInfo.getColumnName(), dictItem.get("id"));
              }
            } catch (Exception exception) {
              this.writeMsgRed("第{}行：{}列JSON设置不正确", i, colInfo.getCnName());
            }
          }

          // 自定义excel单元格数据处理
//          if (typeof cellCallBack == = "function"){
//            await cellCallBack (row, colInfo, val);
//          }
        } else if (Objects.equals(colInfo.getValidate(), ImportValidateTypeEnum.DEFAULT_VALUE.getName())) {
          if (ObjectUtil.isNotNull(colInfo.getValExpression())) {
            // 默认值
            row.put(colInfo.getColumnName(), colInfo.getValExpression());
          }
        }
      }
    }
  }
  //#endregion

  //#region 正则验证, return true 表示验证未通过
  private boolean regularValidate(String regexp, String inputText) {
    if (StringUtils.isEmpty(regexp)) return false;
    return ReUtil.isMatch(regexp, inputText);
  }
  //#endregion

  //#region 字典验证, 字典带入，字典翻译
  private void dicValidate(Map<String, List<LinkedHashMap<String, Object>>> dict, SysImportColumn colInfo, Map<String, Object> row, List<Map<String, Object>> excelDataList, int i, LoginUser loginUser) {
    String columnName = colInfo.getColumnName();
    String colVal = Convert.toStr(row.get(columnName)); // 字段值
    var vals = StringUtils.split(colVal, ",");
    if (ObjectUtil.isNull(vals)) {
      if (NumberUtil.equals(colInfo.getIsMust(), EnableEnum.ENABLE.getId())) {
        this.writeMsgRed("第{}行、{}列的值为空，不允许为空值！", i, colInfo.getCnName());
      }
      return;
    }

    // 从缓存中获取字典数据，如果不存在重新获取字典数据
    List<LinkedHashMap<String, Object>> dataList = dict.get(columnName);
    if (CollUtil.isEmpty(dataList)) {
      dataList = this.getDicList(colInfo, i, excelDataList, loginUser);
      dict.put(columnName, dataList);
    }

    // 在字典数据集合中查找是否存在
    for (var val : vals) {
      var dictItem = dataList.stream().filter(item -> StringUtils.equals(Convert.toStr(item.get("name")), val) || (ObjectUtil.equals(Convert.toStr(item.get(columnName)), val))).findFirst().orElse(null);
      // 必填时验证字段值必须在字典中存在
      if (NumberUtil.equals(colInfo.getIsMust(), EnableEnum.ENABLE.getId()) && ObjectUtil.isEmpty(dictItem)) {
        this.writeMsgRed("第{}行、{}列的值[{}]验证不存在", i, colInfo.getCnName(), val);
      }
      // 翻译数据
      if (StringUtils.equals(colInfo.getValidate(), ImportValidateTypeEnum.DICTIONARY_TRANSLATE.getName()) && ObjectUtil.isNotNull(dictItem)) {
        String value = Convert.toStr(dictItem.get("id"));
        if (StringUtils.isEmpty(value)) {
          value = Convert.toStr(dictItem.get("name"));
        }
        row.put(columnName, value);
      }
      if (ObjectUtil.isNotNull(dictItem)) {
        // 带入关联字段值
        var bringField = colInfo.getBringField();
        if (StringUtils.isNotEmpty(bringField)) {
          // 带入字段
          var bringFields = StringUtils.splitList(bringField).stream().map(String::trim).toList();
          for (String field : bringFields) {
            if (ObjectUtil.isNotEmpty(dictItem.get(field))) {
              // 扩展字段没有值时，从标准里面带入
              row.put(field, dictItem.get(field));
            }
          }
        }
      }
    }
  }
  //#endregion

  //#region 获取字典数据
  private List<LinkedHashMap<String, Object>> getDicList(SysImportColumn colInfo, int rowIndex, List<Map<String, Object>> excelDataList, LoginUser loginUser) {
    List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
    try {
      // 缓存字典数据
      if (StringUtils.isEmpty(colInfo.getValExpression())) {
        this.writeMsgRed("第{}行：{}列验证表达式不能为空，需要设置下拉框ID", rowIndex, colInfo.getCnName());
        return dataList;
      }

      var dropdownId = Convert.toLong(colInfo.getValExpression());
      SysDropdown dropInfo = sysDropdownService.getById(dropdownId);
      if (ObjectUtil.isEmpty(dropInfo)) {
        this.writeMsgRed(StringUtils.format("第{}行：{}列验证表达式下拉框ID不存在", rowIndex, colInfo.getCnName()));
        return dataList;
      }
      // 替换账套ID
      String sql = ReUtil.replaceAll(dropInfo.getSqlScript(), "\\{tenant_id\\}", loginUser.getTenantId());
      sql = ReUtil.replaceAll(sql, "LIMIT [0-9]+", "LIMIT 15000");
      // 34=商品编号验证
      if (dropdownId == 34) {
        // 商品列表缓存，需要当前excel中存在的编号进行加载，需要特殊处理防止字典数据过大
        List<String> productCodes = excelDataList.stream().filter(m -> ObjectUtil.isNotEmpty(m.get("productCode"))).
          map(m -> ReUtil.replaceAll(Convert.toStr(m.get("productCode")), "/ '/gi", "' '")).distinct().toList();
        if (!productCodes.isEmpty()) {
          // 采用编号
          sql = ReUtil.replaceAll(sql, "\\{AUTHWHERE\\}", "product_code IN ('" + StringUtils.join(productCodes, "', '") + "')");
        } else {
          // 果编号不存在条码
          List<String> productModels = excelDataList.stream().filter(m -> ObjectUtil.isNotEmpty(m.get("productModel"))).
            map(m -> ReUtil.replaceAll(Convert.toStr(m.get("productModel")), " '", "' '")).toList();
          sql = ReUtil.replaceAll(sql, "\\{AUTHWHERE\\}", "product_model IN ('" + StringUtils.join(productModels, "', '") + "')");
        }
      } else {
        sql = ReUtil.replaceAll(sql, "\\{AUTHWHERE\\}", "1=1");
      }
      var dataList1 = new DBUtils(sqlSessionTemplate).selectList(sql);
      for (var item : dataList1) {
        LinkedHashMap<String, Object> newMap = new LinkedHashMap<>(item);
        int index = 0;
        for (var key : item.keySet()) {
          if (item.keySet().size() == 2) {
            if (index == 0) {
              newMap.put("id", item.get(key));
            } else if (index == 1) {
              newMap.put("name", item.get(key));
            }
          } else if (item.keySet().size() >= 3) {
            if (index == 0) {
              newMap.put("id", item.get(key));
            } else if (index == 1) {
              newMap.put("code", newMap.get(key));
            } else if (index == 2) {
              newMap.put("name", newMap.get(key));
            }
            if (index >= 2) break;
          }
          index++;
        }
        dataList.add(newMap);
      }
    } catch (Exception exception) {
      this.writeMsgRed("第{}行：{}规则需要正确设置验证表达式的下拉框ID", rowIndex, colInfo.getCnName());
    }

    return dataList;
  }
  //#endregion

  //#region 获取分组列表
  public List<Map<String, Object>> getGroupList(List<Map<String, Object>> excelDataList, Long importId) {
    List<SysImportColumn> groupFields = sysImportColumnService.selectGroupColumns(importId);

    Map<Object, Boolean> map = new HashMap<>();
    Stream<Map<String, Object>> mapStream = excelDataList.stream()
      .filter(f -> {
        List<String> keys = new ArrayList<>();
        for (var item : groupFields) {
          var value = Convert.toStr(f.get(item.getColumnName()));
          if (value == null) value = "null";
          keys.add(value);
        }
        return map.putIfAbsent(StringUtils.join(keys, ","), Boolean.TRUE) == null;
      });

    return mapStream.collect(Collectors.toList());
  }
  //#endregion

  //#region 获取分组明细
  public List<Map<String, Object>> getGroupDetails(List<Map<String, Object>> excelDataList, Map<String, Object> groupInfo, Long importId) {
    List<SysImportColumn> groupFields = sysImportColumnService.selectGroupColumns(importId);

    return excelDataList.stream().filter(item -> {
      var isEqual = true;
      for (var g : groupFields) {
        if (!Objects.equals(item.get(g.getColumnName()), groupInfo.get(g.getColumnName()))) {
          isEqual = false;
        }
      }
      return isEqual;
    }).collect(Collectors.toList());
  }
//#endregion

  @Override
  public void importTemplate(HttpServletResponse response, Long importId) throws IOException {
    SysImport sysImport = this.getInfoByCustomImportId(importId);
    this.importColumnList = sysImportColumnService.selectListByMainId(sysImport.getImportId());

    List<List<String>> headList = this.importColumnList.stream().map(m -> {
      List<String> head = ListUtils.newArrayList();
      head.add(m.getCnName());
      return head;
    }).toList();

    // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setCharacterEncoding("utf-8");
    // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    String fileName = URLEncoder.encode(sysImport.getImportName() + "模板", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    try {
      // 头的策略
      HorizontalCellStyleStrategy horizontalCellStyleStrategy = getHorizontalCellStyleStrategy();
      EasyExcel
        .write(response.getOutputStream())
        .head(headList)
        .registerWriteHandler(horizontalCellStyleStrategy) // 字体背景色策略
        .registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 20, (short) 20)) // 简单的行高策略
        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
        .registerWriteHandler(new CellWriteHandler() {
          @Override
          public void afterCellDispose(CellWriteHandlerContext context) {
            // 当前事件会在 数据设置到poi的cell里面才会回调
            // 判断不是头的情况 如果是fill 的情况 这里会==null 所以用not true
            if (context.getHead()) {
              // 第一个单元格
              // 只要不是头 一定会有数据 当然fill的情况 可能要context.getCellDataList() ,这个需要看模板，因为一个单元格会有多个 WriteCellData
              WriteCellData<?> cellData = context.getFirstCellData();
              // 这里需要去cellData 获取样式
              // 很重要的一个原因是 WriteCellStyle 和 dataFormatData绑定的 简单的说 比如你加了 DateTimeFormat
              // ，已经将writeCellStyle里面的dataFormatData 改了 如果你自己new了一个WriteCellStyle，可能注解的样式就失效了
              // 然后 getOrCreateStyle 用于返回一个样式，如果为空，则创建一个后返回
              WriteCellStyle writeCellStyle = cellData.getOrCreateStyle();
              var cellValue = context.getCell().getStringCellValue();
              SysImportColumn sysImportColumn = importColumnList.stream().filter(f -> Objects.equals(f.getCnName(), cellValue)).findFirst().orElse(null);
              if (ObjectUtil.isNotNull(sysImportColumn) && ObjectUtil.equals(sysImportColumn.getIsMust(), EnableEnum.ENABLE.getId())) {
                writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
              } else {
                writeCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
              }
              // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
              writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            }
          }
        })
        .sheet(sysImport.getImportName() + "模板")
        .doWrite(CollUtil.newArrayList());
    } catch (IOException e) {
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
    // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
    contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
    // 背景绿色
    contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
    WriteFont contentWriteFont = new WriteFont();
    // 字体大小
    contentWriteFont.setFontHeightInPoints((short) 10);
    contentWriteCellStyle.setWriteFont(contentWriteFont);

    // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
    return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
  }

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
    LambdaQueryWrapper<SysImport> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysImport::getTenantId, targetTenantId);
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
    lambdaQueryWrapper.eq(SysImport::getTenantId, fromTenantId);
    List<SysImport> regularList = this.list(lambdaQueryWrapper);

    // 目标数据
    List<SysImport> newList = regularList.stream().peek(m -> {
      m.setTenantId(targetTenantId);
    }).toList();

    BiConsumer<Number, Number> consumer = getNumberBiConsumer(fromTenantId, newList);
    consumer.accept(BigDecimal.ZERO, BigDecimal.ZERO);

    TenantHelper.disableIgnore();
  }

  @NotNull
  private BiConsumer<Number, Number> getNumberBiConsumer(String fromTenantId, List<SysImport> newList) {
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
          Long id = item.getImportId();
          item.setParentId(Convert.toLong(newParentId));
          item.setImportId(null);
          // 从母版中复制时，需要设置自定义ID值
          if (StringUtils.equals(fromTenantId, TenantConstants.DEFAULT_TENANT_ID)) {
            item.setCustomImportId(id);
          }
          the.save(item);

          // 处理明细数据
          List<SysImportColumn> columnList = sysImportColumnService.selectListByMainId(id);
          List<SysImportColumn> list = columnList.stream().peek(p -> {
            p.setImportId(item.getImportId());
            p.setTenantId(item.getTenantId());
            p.setColumnId(null);
          }).toList();
          sysImportColumnService.saveBatch(list);

          // 使用局部变量self而不是this，递归执行
          self.accept(id, item.getImportId());
        }
      }
    };
  }

  @Override
  public SysImport getInfoByCustomImportId(Long importId) {
    LambdaQueryWrapper<SysImport> importLambdaQueryWrapper = new LambdaQueryWrapper<>();
    importLambdaQueryWrapper
      .eq(SysImport::getCustomImportId, importId)
      .eq(SysImport::getTenantId, LoginHelper.getTenantId());
    SysImport sysImport = this.getOne(importLambdaQueryWrapper);
    if (ObjectUtil.isNull(sysImport)) {
      importLambdaQueryWrapper = new LambdaQueryWrapper<>();
      importLambdaQueryWrapper.eq(SysImport::getImportId, importId);
      sysImport = this.getOne(importLambdaQueryWrapper);
    }
    return sysImport;
  }

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysImport> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysImport::getImportName, filterText).last("limit 10");
    List<SysImport> importList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var importItem : importList) {
      String importName = importItem.getImportName();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(importItem.getParentId());
      parentNameAll = parentNameAll + "&gt;" + importName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("importId", importItem.getImportId());
      resultMap.put("importName", importItem.getImportName());
      resultMap.put("parentNameAll", parentNameAll);
      mapList.add(resultMap);
    }


    return R.ok(mapList);
  }

  public Map<String, Object> getExpandFields(Map<String, Object> row) {
    Map<String, Object> expandFields = new HashMap<>();
    final List<SysImportColumn> list = this.importColumnList.stream().filter(x -> B.isEqual(x.getIsExpandField(), ImportExpandFieldTypeEnum.MAIN_EXPEND_FIELD.getId()) || B.isEqual(x.getIsExpandField(), ImportExpandFieldTypeEnum.DETAIL_EXPEND_FIELD.getId())).toList();
    for (SysImportColumn item : list) {
      String columnName = item.getColumnName();
      // 取到相同Key的字段
      if (row.containsKey(columnName)) {
        Object value = row.get(columnName);
        expandFields.put(columnName, value);
      }
    }
    return expandFields;
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
        LambdaQueryWrapper<SysImport> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysImport::getImportId, parentId);
        SysImport sysMenu = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysMenu)) {
          String _parentName = self.apply(sysMenu.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysMenu.getImportName();
        }
        return parentName;
      }
    };
  }
}
