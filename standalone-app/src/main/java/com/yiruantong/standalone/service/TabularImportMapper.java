package com.yiruantong.standalone.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class TabularImportMapper {

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

    public List<PreviewRow> map(List<List<String>> rows, String sourcePrefix, int firstRowNumber) {
        if (rows == null || rows.isEmpty()) return List.of();

        HeaderInfo header = locateHeader(rows);
        if (header == null) return List.of();

        List<PreviewRow> result = new ArrayList<>();
        for (int i = header.rowIndex() + 1; i < rows.size(); i++) {
            List<String> source = rows.get(i);
            if (source == null || source.stream().allMatch(v -> clean(v).isEmpty())) {
                continue;
            }
            result.add(parseRow(source, header.columns(), sourcePrefix, firstRowNumber + i));
        }
        return result;
    }

    private PreviewRow parseRow(
        List<String> source,
        Map<String, Integer> columns,
        String sourcePrefix,
        int sourceRow
    ) {
        List<String> errors = new ArrayList<>();

        String name = value(source, columns.get("name"));
        String spec = value(source, columns.get("spec"));
        String unit = value(source, columns.get("unit"));
        String receiver = value(source, columns.get("receiver"));
        String remark = value(source, columns.get("remark"));
        LocalDate date = parseDate(value(source, columns.get("date")));
        BigDecimal quantity = parseDecimal(value(source, columns.get("quantity")));
        BigDecimal price = parseDecimal(value(source, columns.get("price")));

        if (name.isBlank()) errors.add("物资名称为空");
        if (quantity == null || quantity.signum() <= 0) errors.add("数量无效");

        String sourceKey = sha256(sourcePrefix + ":" + sourceRow + ":" + String.join("\u001f", source));
        InventoryService.StockRow row = new InventoryService.StockRow(
            date, name, spec, quantity, unit, price, receiver, remark, sourceKey
        );

        return new PreviewRow(sourceRow, errors.isEmpty(), String.join("；", errors), row);
    }

    private HeaderInfo locateHeader(List<List<String>> rows) {
        int scanTo = Math.min(rows.size(), 13);
        for (int r = 0; r < scanTo; r++) {
            List<String> row = rows.get(r);
            if (row == null) continue;

            Map<String, Integer> columns = new HashMap<>();
            for (int c = 0; c < row.size(); c++) {
                String canonical = HEADER_ALIASES.get(normalizeHeader(row.get(c)));
                if (canonical != null) columns.putIfAbsent(canonical, c);
            }
            if (columns.containsKey("name") && columns.containsKey("quantity")) {
                return new HeaderInfo(r, columns);
            }
        }
        return null;
    }

    private static String value(List<String> row, Integer column) {
        if (column == null || column < 0 || column >= row.size()) return "";
        return clean(row.get(column));
    }

    private static BigDecimal parseDecimal(String raw) {
        String value = clean(raw)
            .replace(",", "")
            .replace("￥", "")
            .replace("元", "");
        if (value.isEmpty()) return null;
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static LocalDate parseDate(String value) {
        value = clean(value);
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
        return clean(value).toLowerCase(Locale.ROOT)
            .replaceAll("[\\s_（）()：:－—-]", "");
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
        for (String alias : aliases) map.put(normalizeHeader(alias), canonical);
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception ex) {
            throw new IllegalStateException("无法生成导入来源标识", ex);
        }
    }

    private record HeaderInfo(int rowIndex, Map<String, Integer> columns) {}
}
