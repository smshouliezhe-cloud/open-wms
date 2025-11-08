#!/bin/bash
# ==========================================
# Open-WMS Linux/Mac环境变量设置脚本
# ==========================================
# 使用说明：
# 1. 修改下面的配置值
# 2. 运行: source env-linux.sh
# 或者在启动脚本中导入: source /path/to/env-linux.sh
# ==========================================

# MySQL 主库配置
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
export MYSQL_DATABASE=yrt-open-wms
export MYSQL_USERNAME=root
export MYSQL_PASSWORD=123456

# MySQL 从库配置
export MYSQL_SLAVE_HOST=127.0.0.1
export MYSQL_SLAVE_PORT=3306
export MYSQL_SLAVE_DATABASE=yrt-open-wms
export MYSQL_SLAVE_USERNAME=root
export MYSQL_SLAVE_PASSWORD=123456

# Redis 配置
export REDIS_HOST=127.0.0.1
export REDIS_PORT=6379
export REDIS_DATABASE=0
export REDIS_PASSWORD=123456

# MongoDB 配置
export MONGO_HOST=127.0.0.1
export MONGO_PORT=27017
export MONGO_DATABASE=yrt-open-wms
export MONGO_USERNAME=wms
export MONGO_PASSWORD=123456
export MONGO_AUTH_DATABASE=yrt-open-wms

# RabbitMQ 配置
export RABBITMQ_HOST=127.0.0.1
export RABBITMQ_PORT=5672
export RABBITMQ_VIRTUAL_HOST=/
export RABBITMQ_USERNAME=rabbit
export RABBITMQ_PASSWORD=123456

echo "环境变量设置完成！"

