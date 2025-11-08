package com.yiruantong.system.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.yiruantong.system.domain.dataHandler.SysImportColumn;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import com.yiruantong.common.core.enums.base.EnableEnum;

import java.util.List;
import java.util.Objects;


public class HeadStyleWriteHandler extends AbstractCellStyleStrategy implements CellWriteHandler {
  private List<SysImportColumn> importColumnList;
  public HeadStyleWriteHandler(List<SysImportColumn> importColumnList) {
    this.importColumnList = importColumnList;
  }

  @Override
  public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {
  }

  @Override
  public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
  }

  @Override
  protected void setHeadCellStyle(Cell cell, Head head, Integer integer) {
    SysImportColumn sysImportColumn = importColumnList.stream().filter(f -> Objects.equals(f.getCnName(), head.getHeadNameList().get(0))).findFirst().orElse(null);
    if(ObjectUtil.isNotNull(sysImportColumn) && ObjectUtil.equals(sysImportColumn.getIsMust(), EnableEnum.ENABLE.getId())) {
      XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
      cellStyle.setFillForegroundColor(new XSSFColor(IndexedColors.YELLOW, new DefaultIndexedColorMap()));
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    } else {
      XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
      cellStyle.setFillForegroundColor(new XSSFColor(IndexedColors.GREY_25_PERCENT, new DefaultIndexedColorMap()));
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
  }

  @Override
  protected void setContentCellStyle(Cell cell, Head head, Integer integer) {
  }
}
