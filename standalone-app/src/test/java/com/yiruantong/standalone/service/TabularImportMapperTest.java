package com.yiruantong.standalone.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TabularImportMapperTest {

    @Test
    void mapsCommonChineseWpsHeaders() {
        TabularImportMapper mapper = new TabularImportMapper();
        List<List<String>> rows = List.of(
            List.of("出库日期", "材料名称", "规格型号", "数量", "单位", "单价", "领用人", "备注"),
            List.of("2026-09-10", "PE管", "De20", "12.5", "米", "2.80", "张三", "维修领用")
        );

        List<TabularImportMapper.PreviewRow> result = mapper.map(rows, "test", 1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).valid()).isTrue();
        InventoryService.StockRow row = result.get(0).row();
        assertThat(row.date()).isEqualTo(LocalDate.of(2026, 9, 10));
        assertThat(row.name()).isEqualTo("PE管");
        assertThat(row.spec()).isEqualTo("De20");
        assertThat(row.quantity()).isEqualByComparingTo(new BigDecimal("12.5"));
        assertThat(row.unit()).isEqualTo("米");
        assertThat(row.unitPrice()).isEqualByComparingTo(new BigDecimal("2.80"));
        assertThat(row.receiver()).isEqualTo("张三");
        assertThat(row.remark()).isEqualTo("维修领用");
        assertThat(row.sourceKey()).hasSize(64);
    }

    @Test
    void rejectsRowsWithoutPositiveQuantity() {
        TabularImportMapper mapper = new TabularImportMapper();
        List<List<String>> rows = List.of(
            List.of("物资名称", "数量"),
            List.of("阀门", "0")
        );

        List<TabularImportMapper.PreviewRow> result = mapper.map(rows, "test", 1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).valid()).isFalse();
        assertThat(result.get(0).error()).contains("数量无效");
    }
}
