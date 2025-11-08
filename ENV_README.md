# 环境变量配置说明

本项目已将数据库连接等敏感配置改为环境变量方式，以提高安全性和灵活性。

## 📁 文件说明

| 文件名 | 说明 | 是否提交到Git |
|--------|------|--------------|
| `env.example` | 环境变量模板文件，包含所有变量说明 | ✅ 是 |
| `env-windows.bat` | Windows 系统的环境变量设置脚本 | ❌ 否 |
| `env-linux.sh` | Linux/Mac 系统的环境变量设置脚本 | ❌ 否 |
| `docker-compose.env` | Docker Compose 使用的环境变量文件 | ❌ 否 |

## 🔧 配置的环境变量

### MySQL 主库
- `MYSQL_HOST` - MySQL 主库地址 (默认: 127.0.0.1)
- `MYSQL_PORT` - MySQL 主库端口 (默认: 3306)
- `MYSQL_DATABASE` - MySQL 数据库名 (默认: yrt-open-wms)
- `MYSQL_USERNAME` - MySQL 用户名 (默认: root)
- `MYSQL_PASSWORD` - MySQL 密码 (默认: 123456)

### MySQL 从库
- `MYSQL_SLAVE_HOST` - MySQL 从库地址 (默认: 127.0.0.1)
- `MYSQL_SLAVE_PORT` - MySQL 从库端口 (默认: 3306)
- `MYSQL_SLAVE_DATABASE` - MySQL 从库数据库名 (默认: yrt-open-wms)
- `MYSQL_SLAVE_USERNAME` - MySQL 从库用户名 (默认: root)
- `MYSQL_SLAVE_PASSWORD` - MySQL 从库密码 (默认: 123456)

### Redis
- `REDIS_HOST` - Redis 地址 (默认: 127.0.0.1)
- `REDIS_PORT` - Redis 端口 (默认: 6379)
- `REDIS_DATABASE` - Redis 数据库索引 (默认: 0)
- `REDIS_PASSWORD` - Redis 密码 (默认: 123456)

### MongoDB
- `MONGO_HOST` - MongoDB 地址 (默认: 127.0.0.1)
- `MONGO_PORT` - MongoDB 端口 (默认: 27017)
- `MONGO_DATABASE` - MongoDB 数据库名 (默认: yrt-open-wms)
- `MONGO_USERNAME` - MongoDB 用户名 (默认: wms)
- `MONGO_PASSWORD` - MongoDB 密码 (默认: 123456)
- `MONGO_AUTH_DATABASE` - MongoDB 认证数据库 (默认: yrt-open-wms)

### RabbitMQ
- `RABBITMQ_HOST` - RabbitMQ 地址 (默认: 127.0.0.1)
- `RABBITMQ_PORT` - RabbitMQ 端口 (默认: 5672)
- `RABBITMQ_VIRTUAL_HOST` - RabbitMQ 虚拟主机 (默认: /)
- `RABBITMQ_USERNAME` - RabbitMQ 用户名 (默认: rabbit)
- `RABBITMQ_PASSWORD` - RabbitMQ 密码 (默认: 123456)

## 🚀 使用方法

### 方法一：使用脚本文件（推荐）

#### Windows 系统

1. 复制 `env.example` 为 `env-windows.bat`：
   ```cmd
   copy env.example env-windows.bat
   ```

2. 编辑 `env-windows.bat`，修改为实际配置值

3. 在启动应用前运行脚本：
   ```cmd
   env-windows.bat
   ```

4. 在同一命令行窗口中启动应用：
   ```cmd
   mvn spring-boot:run
   ```

#### Linux/Mac 系统

1. 复制 `env.example` 为 `env-linux.sh`：
   ```bash
   cp env.example env-linux.sh
   ```

2. 编辑 `env-linux.sh`，修改为实际配置值

3. 加载环境变量：
   ```bash
   source env-linux.sh
   ```

4. 启动应用：
   ```bash
   mvn spring-boot:run
   ```

### 方法二：IDEA 配置

1. 打开 Run/Debug Configurations
2. 选择你的启动配置
3. 在 Environment variables 中添加环境变量
4. 格式：`MYSQL_HOST=127.0.0.1;MYSQL_PORT=3306;...`

### 方法三：Docker Compose

1. 复制 `env.example` 为 `docker-compose.env`：
   ```bash
   cp env.example docker-compose.env
   ```

2. 编辑 `docker-compose.env`，修改配置（注意 host 要使用服务名）

3. 在 `docker-compose.yml` 中引用：
   ```yaml
   services:
     app:
       env_file:
         - docker-compose.env
   ```

### 方法四：系统环境变量

#### Windows 系统
1. 右键 "此电脑" → "属性" → "高级系统设置" → "环境变量"
2. 在 "用户变量" 或 "系统变量" 中添加上述环境变量

#### Linux/Mac 系统
编辑 `~/.bashrc` 或 `~/.zshrc`：
```bash
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
# ... 其他变量
```

然后执行：
```bash
source ~/.bashrc
```

## ⚠️ 注意事项

1. **安全性**：环境变量文件包含敏感信息（如密码），已添加到 `.gitignore`，请勿提交到版本控制系统
2. **默认值**：即使不设置环境变量，应用也会使用配置文件中的默认值
3. **优先级**：环境变量的优先级高于配置文件中的默认值
4. **生产环境**：生产环境建议使用容器编排工具（如 Kubernetes）的 ConfigMap 和 Secret 管理配置

## 🔍 验证配置

启动应用后，可以通过日志查看实际使用的配置：

```
启动日志中会显示数据库连接地址等信息
```

## 📝 团队协作

1. 新成员加入时，提供 `env.example` 文件
2. 让其复制为对应的脚本文件并修改配置
3. 各环境（开发、测试、生产）使用不同的配置值
4. 定期更新 `env.example` 模板文件

## 🐛 常见问题

**Q: 为什么应用还是使用默认配置？**  
A: 请确保环境变量已正确设置，并在设置环境变量的同一终端/命令行窗口中启动应用。

**Q: 如何在 Docker 中使用？**  
A: 使用 `docker run -e MYSQL_HOST=xxx -e MYSQL_PORT=xxx ...` 或使用 `--env-file` 参数。

**Q: 可以部分使用环境变量吗？**  
A: 可以，未设置的环境变量会使用配置文件中的默认值。

