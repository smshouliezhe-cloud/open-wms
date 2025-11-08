@echo off
REM ==========================================
REM Open-WMS Windows环境变量设置脚本
REM ==========================================
REM 使用说明：
REM 1. 修改下面的配置值
REM 2. 在启动应用前运行此脚本
REM ==========================================

REM MySQL 主库配置
set MYSQL_HOST=127.0.0.1
set MYSQL_PORT=3306
set MYSQL_DATABASE=yrt-open-wms
set MYSQL_USERNAME=root
set MYSQL_PASSWORD=123456

REM MySQL 从库配置
set MYSQL_SLAVE_HOST=127.0.0.1
set MYSQL_SLAVE_PORT=3306
set MYSQL_SLAVE_DATABASE=yrt-open-wms
set MYSQL_SLAVE_USERNAME=root
set MYSQL_SLAVE_PASSWORD=123456

REM Redis 配置
set REDIS_HOST=127.0.0.1
set REDIS_PORT=6379
set REDIS_DATABASE=0
set REDIS_PASSWORD=123456

REM MongoDB 配置
set MONGO_HOST=127.0.0.1
set MONGO_PORT=27017
set MONGO_DATABASE=yrt-open-wms
set MONGO_USERNAME=wms
set MONGO_PASSWORD=123456
set MONGO_AUTH_DATABASE=yrt-open-wms

REM RabbitMQ 配置
set RABBITMQ_HOST=127.0.0.1
set RABBITMQ_PORT=5672
set RABBITMQ_VIRTUAL_HOST=/
set RABBITMQ_USERNAME=rabbit
set RABBITMQ_PASSWORD=123456

echo 环境变量设置完成！
echo 请在当前命令行窗口中启动应用。

