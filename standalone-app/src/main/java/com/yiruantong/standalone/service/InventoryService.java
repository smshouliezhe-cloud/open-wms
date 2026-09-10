package com.yiruantong.standalone.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class InventoryService {

    private final JdbcTemplate jdbc;

    public InventoryService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public record StockRow(
        LocalDate date,
        String name,
        String spec,
        BigDecimal quantity,
        String unit,
        BigDecimal unitPrice,
        String receiver,
        String remark,
        String sourceKey
    ) {}

    public record MovementResult(String status, String message, Long movementId) {}

    public List<Map<String, Object>> inventory() {
        return jdbc.queryForList("""
            SELECT id, name, spec, unit, current_qty, last_price, updated_at
            FROM material
            ORDER BY name, spec, unit
            """);
    }

    public List<Map<String, Object>> movements(int limit) {
        int safeLimit = Math.max(1, Math.min(limit, 1000));
        return jdbc.queryForList("""
            SELECT id, movement_type, biz_date, material_name, spec, unit,
                   quantity, unit_price, receiver, remark, source_key, created_at
            FROM stock_movement
            ORDER BY id DESC
            LIMIT ?
            """, safeLimit);
    }

    @Transactional
    public MovementResult apply(String rawType, StockRow row) {
        String type = normalizeType(rawType);
        validate(row);

        if (row.sourceKey() != null && !row.sourceKey().isBlank()) {
            Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM stock_movement WHERE source_key = ?",
                Integer.class,
                row.sourceKey().trim()
            );
            if (count != null && count > 0) {
                return new MovementResult("SKIPPED_DUPLICATE", "该表格行已经入账，已跳过", null);
            }
        }

        String key = materialKey(row.name(), row.spec(), row.unit());
        Material material = findMaterial(key);
        if (material == null) {
            jdbc.update("""
                INSERT INTO material(material_key, name, spec, unit, current_qty, last_price)
                VALUES (?, ?, ?, ?, 0, ?)
                """,
                key, clean(row.name()), clean(row.spec()), clean(row.unit()), row.unitPrice()
            );
            material = findMaterial(key);
        }

        BigDecimal current = material.currentQty();
        BigDecimal next = "IN".equals(type)
            ? current.add(row.quantity())
            : current.subtract(row.quantity());

        if (next.signum() < 0) {
            throw new IllegalArgumentException(
                "库存不足：" + clean(row.name()) + " 当前库存 " + current + "，本次出库 " + row.quantity()
            );
        }

        jdbc.update("""
            UPDATE material
            SET current_qty = ?,
                last_price = COALESCE(?, last_price),
                updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """, next, row.unitPrice(), material.id());

        LocalDate bizDate = row.date() == null ? LocalDate.now() : row.date();
        jdbc.update("""
            INSERT INTO stock_movement(
                movement_type, biz_date, material_id, material_name, spec, unit,
                quantity, unit_price, receiver, remark, source_key
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """,
            type,
            bizDate,
            material.id(),
            clean(row.name()),
            clean(row.spec()),
            clean(row.unit()),
            row.quantity(),
            row.unitPrice(),
            clean(row.receiver()),
            clean(row.remark()),
            blankToNull(row.sourceKey())
        );

        Long movementId = jdbc.queryForObject("SELECT MAX(id) FROM stock_movement", Long.class);
        return new MovementResult("OK", "已入账", movementId);
    }

    @Transactional
    public Map<String, Integer> applyBatch(String type, List<StockRow> rows) {
        int ok = 0;
        int skipped = 0;
        for (StockRow row : rows) {
            MovementResult result = apply(type, row);
            if ("SKIPPED_DUPLICATE".equals(result.status())) {
                skipped++;
            } else {
                ok++;
            }
        }
        return Map.of("committed", ok, "skippedDuplicate", skipped);
    }

    private Material findMaterial(String key) {
        try {
            return jdbc.queryForObject(
                "SELECT id, current_qty FROM material WHERE material_key = ?",
                (rs, rowNum) -> new Material(rs.getLong("id"), rs.getBigDecimal("current_qty")),
                key
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static void validate(StockRow row) {
        if (row == null) {
            throw new IllegalArgumentException("数据不能为空");
        }
        if (row.name() == null || row.name().isBlank()) {
            throw new IllegalArgumentException("物资名称不能为空");
        }
        if (row.quantity() == null || row.quantity().signum() <= 0) {
            throw new IllegalArgumentException("数量必须大于0");
        }
    }

    private static String normalizeType(String rawType) {
        String type = rawType == null ? "" : rawType.trim().toUpperCase(Locale.ROOT);
        if (!"IN".equals(type) && !"OUT".equals(type)) {
            throw new IllegalArgumentException("出入库类型只能是 IN 或 OUT");
        }
        return type;
    }

    private static String materialKey(String name, String spec, String unit) {
        return clean(name).toLowerCase(Locale.ROOT) + "|"
            + clean(spec).toLowerCase(Locale.ROOT) + "|"
            + clean(unit).toLowerCase(Locale.ROOT);
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private static String blankToNull(String value) {
        String cleaned = clean(value);
        return cleaned.isEmpty() ? null : cleaned;
    }

    private record Material(Long id, BigDecimal currentQty) {}
}
