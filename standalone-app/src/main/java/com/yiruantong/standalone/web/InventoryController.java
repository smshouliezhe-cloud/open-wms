package com.yiruantong.standalone.web;

import com.yiruantong.standalone.service.ExcelImportService;
import com.yiruantong.standalone.service.InventoryService;
import com.yiruantong.standalone.service.TabularImportMapper;
import com.yiruantong.standalone.service.WpsComService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ExcelImportService excelImportService;
    private final WpsComService wpsComService;

    public InventoryController(
        InventoryService inventoryService,
        ExcelImportService excelImportService,
        WpsComService wpsComService
    ) {
        this.inventoryService = inventoryService;
        this.excelImportService = excelImportService;
        this.wpsComService = wpsComService;
    }

    public record CommitRequest(String type, List<InventoryService.StockRow> rows) {}

    @GetMapping("/inventory")
    public List<Map<String, Object>> inventory() {
        return inventoryService.inventory();
    }

    @GetMapping("/movements")
    public List<Map<String, Object>> movements(@RequestParam(defaultValue = "200") int limit) {
        return inventoryService.movements(limit);
    }

    @PostMapping("/stock/{type}")
    public InventoryService.MovementResult stock(
        @PathVariable String type,
        @RequestBody InventoryService.StockRow row
    ) {
        return inventoryService.apply(type, row);
    }

    @PostMapping(value = "/import/preview", consumes = "multipart/form-data")
    public List<TabularImportMapper.PreviewRow> preview(@RequestPart("file") MultipartFile file) throws Exception {
        return excelImportService.preview(file);
    }

    @PostMapping("/wps/preview")
    public List<TabularImportMapper.PreviewRow> previewWps() throws Exception {
        return wpsComService.previewActiveSheet();
    }

    @PostMapping("/import/commit")
    public Map<String, Integer> commit(@RequestBody CommitRequest request) {
        if (request == null || request.rows() == null || request.rows().isEmpty()) {
            throw new IllegalArgumentException("没有可提交的数据");
        }
        return inventoryService.applyBatch(request.type(), request.rows());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
