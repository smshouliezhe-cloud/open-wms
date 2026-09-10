package com.yiruantong.standalone.service;

import com.yiruantong.standalone.WaterworksStandaloneApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(
    classes = WaterworksStandaloneApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "spring.datasource.url=jdbc:h2:mem:inventory-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.sql.init.mode=always",
        "logging.file.name="
    }
)
class InventoryServiceTest {

    @Autowired
    private InventoryService service;

    @Test
    void inboundOutboundAndDuplicateProtectionWorkTogether() {
        InventoryService.StockRow inbound = new InventoryService.StockRow(
            LocalDate.of(2026, 9, 10), "测试PE管", "De20", new BigDecimal("100"), "米",
            new BigDecimal("2.8"), "仓库", "初始入库", "test-in-001"
        );
        InventoryService.StockRow outbound = new InventoryService.StockRow(
            LocalDate.of(2026, 9, 10), "测试PE管", "De20", new BigDecimal("30"), "米",
            null, "张三", "维修", "test-out-001"
        );

        assertThat(service.apply("IN", inbound).status()).isEqualTo("OK");
        assertThat(service.apply("IN", inbound).status()).isEqualTo("SKIPPED_DUPLICATE");
        assertThat(service.apply("OUT", outbound).status()).isEqualTo("OK");

        Object qty = service.inventory().stream()
            .filter(row -> "测试PE管".equals(row.get("name")))
            .findFirst()
            .orElseThrow()
            .get("current_qty");
        assertThat(new BigDecimal(String.valueOf(qty))).isEqualByComparingTo("70");

        InventoryService.StockRow tooMuch = new InventoryService.StockRow(
            LocalDate.of(2026, 9, 10), "测试PE管", "De20", new BigDecimal("1000"), "米",
            null, "张三", "超量出库", "test-out-002"
        );
        assertThatThrownBy(() -> service.apply("OUT", tooMuch))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("库存不足");
    }
}
