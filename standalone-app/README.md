# Waterworks Standalone

`standalone-app` 是 OpenWMS 分支中的独立单机实现，目标是让一台 Windows 电脑在断网状态下完成物资入库、出库、库存查询和 WPS/Excel 导入。

## 当前已实现

- 单独 Spring Boot 可执行 Jar，不依赖原 OpenWMS 的 Redis、MongoDB、RabbitMQ、MinIO、SSO、任务中心。
- H2 文件数据库，默认数据目录 `./data/`，不需要安装 MySQL。
- 浏览器界面只绑定 `127.0.0.1:7861`，默认不对局域网开放。
- 支持 WPS 保存的 `.xlsx` / `.xls` 文件导入。
- Windows 下支持通过 WPS COM 直接读取“当前打开的 WPS 工作表”，无需先另存文件。
- 自动识别常见中文表头：日期、物资名称/材料名称、规格型号、数量、单位、单价、领用人/经办人、备注。
- 导入先预览，只有点击“确认入账”才修改库存。
- 导入来源按工作簿/文件、Sheet、行号和行数据生成 SHA-256 来源键，重复导入同一行会自动跳过。
- 出库前检查库存，不允许库存变为负数。

## WPS 直接读取

1. 在 Windows 上打开 WPS 表格。
2. 选中需要导入的工作表。
3. 启动本程序。
4. 点击“读取当前打开的 WPS”。
5. 系统只读取 WPS 当前工作表的 UsedRange，不会保存、关闭或修改 WPS 文件。
6. 预览无误后点击“确认入账”。

程序会尝试连接 `KET.Application` / `Ket.Application` / `ET.Application` / `Et.Application` COM 对象。`wps-reader.ps1` 必须和启动目录保持在一起。

## 构建与启动

需要 Java 17 和 Maven。

Windows：

```bat
package-windows.bat
run-windows.bat
```

或：

```bash
mvn -DskipTests clean package
java -jar target/waterworks-standalone.jar
```

启动后访问 `http://127.0.0.1:7861/`。

## 数据文件

数据库位于运行目录：

```text
data/waterworks.mv.db
```

备份时关闭程序并复制整个 `data` 目录即可。

## 下一步

- 用实际 WPS 出入库表校准字段映射和合并单元格/多行表头规则。
- 增加手工入库/出库、流水查询、盘点、打印和导出。
- 增加自动备份与恢复。
- 做 Windows 一键发行包，最终目标电脑无需 Maven，并可内置 Java Runtime。
