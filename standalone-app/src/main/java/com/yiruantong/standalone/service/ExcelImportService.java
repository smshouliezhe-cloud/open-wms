package com.yiruantong.standalone.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ExcelImportService {

    private static final Map<String, String> HEADER_ALIASES = buildAliases();
    private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[] {
        DateTimeFormatter.ofPattern("yyyy-M-d"),
        DateTimeFormatter.ofPattern("yyyy/M/d"),
        DateTimeFormatter.ofPattern("yyyy.M.d"),
        DateTimeFormatter.ofPattern("yyyy年M月d日")
    };

    public record PreviewRow(
        int sourceRow,
        boolean valid,
        String error,
        InventoryService.StockRow row
    ) {}

    public List<PreviewRow> preview(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("请选择 WPS/Excel 文件");
        }

        byte[] bytes = file.getBytes();
        String fileHash = sha256(bytes);
        List<PreviewRow> result = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(bytes))) {
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                HeaderInfo header = locateHeader(sheet);
                if (header == null) {
                    continue;
                }

                for (int r = header.rowIndex() + 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null || isEmpty(row)) {
                        continue;
                    }
                    result.add(parseRow(row, header.columns(), fileHash, sheetIndex));
                }
            }
        }

        if (result.isEmpty()) {
            throw new IllegalArgumentException("没有识别到可导入数据。至少需要表头：物资名称、数量");
        }
        return result;
    }

    private PreviewRow parseRow(Row excelRow, Map<String, Integer> columns, String hash, int sheetIndex) {
        List<String> errors = new ArrayList<>();

        String name = text(excelRow, columns.get("name"));
        String spec = text(excelRow, columns.get("spec"));
        String unit = text(excelRow, columns.get("unit"));
        String receiver = text(excelRow, columns.get("receiver"));
        String remark = text(excelRow, columns.get("remark"));
        LocalDate date = date(excelRow, columns.get("date"));
        BigDecimal quantity = decimal(excelRow, columns.get("quantity"));
        BigDecimal price = decimal(excelRow, columns.get("price"));

        if (name.isBlank()) {
            errors.add("物资名称为空");
        }
        if (quantity == null || quantity.signum() <= 0) {
            errors.add("数量无效");
        }

        String sourceKey = hash + ":" + sheetIndex + ":" + excelRow.getRowNum();
        InventoryService.StockRow stockRow = new InventoryService.StockRow(
            date, name, spec, quantity, unit, price, receiver, remark, sourceKey
        );

        return new PreviewRow(
            excelRow.getRowNum() + 1,
            errors.isEmpty(),
            String.join("；", errors),
            stockRow
        );
    }

    private HeaderInfo locateHeader(Sheet sheet) {
        int scanTo = Math.min(sheet.getLastRowNum(), 12);
        for (int r = 0; r <= scanTo; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;

            Map<String, Integer> columns = new HashMap<>();
            for (Cell cell : row) {
                String canonical = HEADER_ALIASES.get(normalizeHeader(cell.toString()));
                if (canonical != null && !columns.containsKey(canonical)) {
                    columns.put(canonical, cell.getColumnIndex());
                }
            }
            if (columns.containsKey("name") && columns.containsKey("quantity")) {
                return new HeaderInfo(r, columns);
            }
        }
        return null;
    }

    private static boolean isEmpty(Row row) {
        DataFormatter formatter = new DataFormatter();
        for (Cell cell : row) {
            if (!formatter.formatCellValue(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static String text(Row row, Integer column) {
        if (column == null) return "";
        Cell cell = row.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return "";
        return new DataFormatter().formatCellValue(cell).trim();
    }

    private static BigDecimal decimal(Row row, Integer column) {
        if (column == null) return null;
        Cell cell = row.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        }
        String value = new DataFormatter().formatCellValue(cell)
            .replace(",", "")
            .replace("￥", "")
            .trim();
        if (value.isEmpty()) return null;
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static LocalDate date(Row row, Integer column) {
        if (column == null) return null;
        Cell cell = row.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }

        String value = new DataFormatter().formatCellValue(cell).trim();
        if (value.isEmpty()) return null;
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    private static String normalizeHeader(String value) {
        if (value == null) return "";
        return value.toLowerCase(Locale.ROOT)
            .replaceAll("[\\s_（）()：:－—-]", "")
            .trim();
    }

    private static Map<String, String> buildAliases() {
        Map<String, String> map = new HashMap<>();
        alias(map, "date", "日期", "入库日期", "出库日期", "领用日期", "业务日期");
        alias(map, "name", "物资名称", "材料名称", "名称", "品名", "商品名称", "物料名称");
        alias(map, "spec", "规格", "规格型号", "型号", "物资规格", "材料规格");
        alias(map, "quantity", "数量", "入库数量", "出库数量", "领用数量", "实发数量");
        alias(map, "unit", "单位", "计量单位");
        alias(map, "price", "单价", "价格", "含税单价", "采购单价");
        alias(map, "receiver", "领用人", "领取人", "经办人", "使用人", "入库人", "出库人");
        alias(map, "remark", "备注", "说明", "用途");
        return map;
    }

    private static void alias(Map<String, String> map, String canonical, String... aliases) {
        for (String alias : aliases) {
            map.put(normalizeHeader(alias), canonical);
        }
    }

    private static String sha256(byte[] bytes) throws Exception {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(bytes);
        return HexFormat.of().formatHex(digest);
    }

    private record HeaderInfo(int rowIndex, Map<String, Integer> columns) {}
}
