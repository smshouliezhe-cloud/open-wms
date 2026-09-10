package com.yiruantong.standalone.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

@Service
public class ExcelImportService {

    private final TabularImportMapper mapper;

    public ExcelImportService(TabularImportMapper mapper) {
        this.mapper = mapper;
    }

    public List<TabularImportMapper.PreviewRow> preview(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("请选择 WPS/Excel 文件");
        }

        byte[] bytes = file.getBytes();
        String fileHash = sha256(bytes);
        List<TabularImportMapper.PreviewRow> result = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(bytes))) {
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                List<List<String>> grid = new ArrayList<>();
                int lastRow = Math.min(sheet.getLastRowNum(), 5000);

                for (int r = 0; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    List<String> values = new ArrayList<>();
                    if (row != null) {
                        int cells = Math.min(Math.max(row.getLastCellNum(), 0), 80);
                        for (int c = 0; c < cells; c++) {
                            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                            values.add(cell == null ? "" : formatter.formatCellValue(cell).trim());
                        }
                    }
                    grid.add(values);
                }

                result.addAll(mapper.map(grid, "xlsx:" + fileHash + ":" + sheetIndex, 1));
            }
        }

        if (result.isEmpty()) {
            throw new IllegalArgumentException("没有识别到可导入数据。至少需要表头：物资名称、数量");
        }
        return result;
    }

    private static String sha256(byte[] bytes) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    }
}
