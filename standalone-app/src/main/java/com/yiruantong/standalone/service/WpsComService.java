package com.yiruantong.standalone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WpsComService {

    private final ObjectMapper objectMapper;
    private final TabularImportMapper mapper;

    public WpsComService(ObjectMapper objectMapper, TabularImportMapper mapper) {
        this.objectMapper = objectMapper;
        this.mapper = mapper;
    }

    public List<TabularImportMapper.PreviewRow> previewActiveSheet() throws Exception {
        if (!System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win")) {
            throw new IllegalArgumentException("直接读取当前 WPS 仅支持 Windows");
        }

        Path script = extractScript();
        try {
            Process process = new ProcessBuilder(
                "powershell.exe",
                "-NoProfile",
                "-NonInteractive",
                "-ExecutionPolicy", "Bypass",
                "-File", script.toString()
            ).redirectErrorStream(true).start();

            boolean finished = process.waitFor(Duration.ofSeconds(20).toMillis(), TimeUnit.MILLISECONDS);
            if (!finished) {
                process.destroyForcibly();
                throw new IllegalArgumentException("读取 WPS 超时，请确认 WPS 表格处于打开状态");
            }

            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            if (process.exitValue() != 0) {
                throw new IllegalArgumentException("读取 WPS 失败：" + compact(output));
            }

            String json = extractJson(output);
            Map<String, Object> payload;
            try {
                payload = objectMapper.readValue(json, new TypeReference<>() {});
            } catch (IOException ex) {
                throw new IllegalArgumentException("WPS 返回数据无法解析：" + compact(output));
            }

            String workbook = String.valueOf(payload.getOrDefault("workbook", "当前工作簿"));
            String sheet = String.valueOf(payload.getOrDefault("sheet", "当前Sheet"));
            int firstRow = toInt(payload.get("firstRow"), 1);
            List<List<String>> rows = toRows(payload.get("rows"));

            List<TabularImportMapper.PreviewRow> result = mapper.map(
                rows,
                "wps:" + workbook + ":" + sheet,
                firstRow
            );
            if (result.isEmpty()) {
                throw new IllegalArgumentException("当前 WPS 工作表没有识别到“物资名称 + 数量”表头");
            }
            return result;
        } finally {
            try { Files.deleteIfExists(script); } catch (IOException ignored) { }
        }
    }

    private static Path extractScript() throws IOException {
        ClassPathResource resource = new ClassPathResource("wps-reader.ps1");
        Path target = Files.createTempFile("waterworks-wps-reader-", ".ps1");
        try (InputStream input = resource.getInputStream()) {
            Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return target;
    }

    private static List<List<String>> toRows(Object value) {
        List<List<String>> result = new ArrayList<>();
        if (!(value instanceof List<?> outer)) return result;
        for (Object row : outer) {
            List<String> line = new ArrayList<>();
            if (row instanceof List<?> cells) {
                for (Object cell : cells) line.add(cell == null ? "" : String.valueOf(cell));
            }
            result.add(line);
        }
        return result;
    }

    private static int toInt(Object value, int fallback) {
        if (value instanceof Number n) return n.intValue();
        try { return Integer.parseInt(String.valueOf(value)); }
        catch (Exception ignored) { return fallback; }
    }

    private static String extractJson(String output) {
        int start = output.indexOf('{');
        int end = output.lastIndexOf('}');
        if (start < 0 || end < start) {
            throw new IllegalArgumentException("WPS 脚本没有返回 JSON：" + compact(output));
        }
        return output.substring(start, end + 1);
    }

    private static String compact(String value) {
        String s = value == null ? "" : value.replaceAll("\\s+", " ").trim();
        return s.length() <= 300 ? s : s.substring(0, 300) + "…";
    }
}
