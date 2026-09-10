# Waterworks Standalone

`standalone-app` 是 OpenWMS 分支中的独立单机实现，目标是让一台 Windows 电脑在断网状态下完成物资入库、出库、库存查询和 WPS/Excel 导入。

## 当前阶段

- 单独 Spring Boot 可执行 Jar，不依赖原 OpenWMS 的 Redis、MongoDB、RabbitMQ、MinIO、SSO、任务中心。
- H2 文件数据库，默认数据目录 `./data/`，不需要安装 MySQL。
- 浏览器界面只绑定 `127.0.0.1:7861`，默认不对局域网开放。
- 支持 WPS 保存的 `.xlsx` / `.xls` 文件。
- 自动识别常见中文表头：日期、物资名称/材料名称、规格型号、数量、单位、单价、领用人/经办人、备注。
- 导入先预览，只有点击“确认入账”才修改库存。
- 每个 Excel 文件按文件 SHA-256 + Sheet + 行号生成来源键，同一文件重复导入会自动跳过。
- 出库前检查库存，不允许库存变为负数。

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

启动后访问：`http://127.0.0.1:7861/`

## 数据文件

程序数据库位于运行目录下：

```text
data/waterworks.mv.db
```

备份时关闭程序并复制整个 `data` 目录即可。

## 下一步

1. 根据实际 WPS 出入库表继续补充表头映射和特殊版式识别。
2. 增加手工入库/出库界面、流水查询、盘点、打印/导出。
3. 增加 Windows WPS COM Bridge，直接读取当前打开的 WPS 工作簿，而不仅是上传 `.xlsx`。
4. 增加一键打包，最终做到目标电脑不安装 Maven，仅安装/内置 Java 运行环境即可使用。
