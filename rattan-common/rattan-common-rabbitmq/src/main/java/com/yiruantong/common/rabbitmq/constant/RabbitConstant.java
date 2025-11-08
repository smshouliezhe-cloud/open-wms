package com.yiruantong.common.rabbitmq.constant;

import cn.hutool.core.net.NetUtil;

/**
 * 路由key常量
 *
 * @author xtb
 */
public interface RabbitConstant {
  /**
   * 队列容量、通道预取值
   * 队列容量应根据项目需要，设置合适的值；
   */
  int QUEUE_CAPACITY = 50;
  int PRE_FETCH_SIZE = 10;

  /**
   * 交换机名称WMS
   */
  String EXCHANGE_WMS_NAME = "yrt.topic.wms";

  /**
   * 队列名称WMS
   */
  String QUEUE_WMS_NAME = "boot_queue_wms" + NetUtil.getLocalMacAddress();
//  String QUEUE_WMS_NAME = "boot_queue_wms" + StringUtils.defaultIfEmpty(NetUtil.getLocalMacAddress(), NetUtil.localIps().stream().filter(x -> x.length() >= 20).toList().get(0)); // 本地开vpn 使用
  /**
   * WMS路由key
   */
  String ROUTING_KEY = "wms.message" + NetUtil.getLocalMacAddress();

//  String ROUTING_KEY = "wms.message" + StringUtils.defaultIfEmpty(NetUtil.getLocalMacAddress(), NetUtil.localIps().stream().filter(x -> x.length() >= 20).toList().get(0)); // 本地开vpn 使用

  /**
   * 备份交换机bean名称
   */
  String BEAN_BACKUP_EXCHANGE = "bean_backup_exchange";

  /**
   * 备份队列bean名称
   */
  String BEAN_BACKUP_QUEUE = "beanBackupExchange";

  /**
   * 备份交换机名称
   */
  String BACKUP_EXCHANGE = "backup_exchange";

  /**
   * 备份队列名称
   */
  String BACKUP_QUEUE = "backup_queue";
}
