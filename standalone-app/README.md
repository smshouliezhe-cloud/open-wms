# Waterworks Standalone

`standalone-app` 是 OpenWMS 分支中的独立单机实现，目标是让一台 Windows 电脑在断网状态下完成物资入库、出库、库存查询和 WPS/Excel 导入。

## 当前已实现

- 单独 Spring Boot 可执行 Jar，不依赖原 OpenWMS 的 Redis、MongoDB、RabbitMQ、MinIO、SSO、任务中心。
- H2 文件数据库，默认数据目录 `./data/`，不需要安装 MySQL。
- 浏览器界面只绑定 `127.0.0.1:7861`，默认不对局域网开放。
- 手工入库、手工出库、当前库存、最近出入库流水。
- 支持 WPS 保存的 `.xlsx` / `.xls` 文件导入。
- Windows 下通过 WPS COM 直接读取“当前打开的 WPS 工作表”，无需先另存文件。
- WPS PowerShell Bridge 已打包进 Jar，运行时临时释放，不要求用户单独维护脚本文件。
- 自动识别常见中文表头：日期、物资名称/材料名称、规格型号、数量、单位、单价、领用人/经办人、备注。
- 导入先预览，只有点击“确认入账”才修改库存。
- 导入来源按工作簿/文件、Sheet、行号和行数据生成 SHA-256 来源键，重复导入同一行会自动跳过。
- 出库前检查库存，不允许库存变为负数。
- GitHub Actions 自动执行单元/集成测试并生成可执行 Jar artifact。

## WPS 直接读取

1. 在 Windows 上打开 WPS 表格并选中需要导入的工作表。
2. 启动本程序。
3. 点击“读取当前打开的 WPS”。
4. 程序通过 WPS COM 读取当前工作表的 UsedRange，不保存、不关闭、不修改原 WPS 文件。
5. 预览无误后点击“确认批量入账”。

程序会依次尝试 `KET.Application`、`Ket.Application`、`ET.Application`、`Et.Application` COM ProgID。

## 构建与启动

需要 Java 17。源码本地构建另需 Maven；GitHub Actions 也会自动生成 Jar。

```bat
package-windows.bat
run-windows.bat
```

启动后访问 `http://127.0.0.1:7861/`。

## 数据文件

数据库位于运行目录：

```text
data/waterworks.mv.db
```

备份时关闭程序并复制整个 `data` 目录即可。

## 下一步

- 用实际 WPS 出入库表校准字段映射、合并单元格和多行表头规则。
- 增加盘点、Excel 导出、自动备份与恢复。
- 制作 Windows 免 Java/免 Maven 的便携发行包。
