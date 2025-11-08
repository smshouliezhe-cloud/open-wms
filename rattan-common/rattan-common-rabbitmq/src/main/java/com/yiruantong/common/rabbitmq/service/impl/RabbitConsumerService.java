package com.yiruantong.common.rabbitmq.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.rabbitmq.constant.RabbitConstant;
import com.yiruantong.common.rabbitmq.service.IRabbitConsumerService;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * <p>普通消费者</p>
 *
 * @author xtb
 * Date 2024-1-5
 * @version 1.0
 */
@Slf4j
@Component
public class RabbitConsumerService implements IRabbitConsumerService {
  @Resource
  private SimpleMessageListenerContainer listenerContainer;
  @Resource
  private RabbitAckReceiver rabbitAckReceiver;
  @Resource
  private CreateNormalQueueAndBind createNormalQueueAndBind;

  /**
   * 监听队列消息
   * 动态切换要监控的队列
   */
  @Override
  public void init() {
    // 创建队列
    createNormalQueueAndBind.create(RabbitConstant.EXCHANGE_WMS_NAME, RabbitConstant.QUEUE_WMS_NAME, RabbitConstant.ROUTING_KEY);
    // 设置要监听的队列（用set，不是add）
    listenerContainer.setQueueNames(RabbitConstant.QUEUE_WMS_NAME);
    // 设置消息接收器
    listenerContainer.setMessageListener(rabbitAckReceiver);

    // 当前监听的队列列表
    String[] queueNames = listenerContainer.getQueueNames();
    for (String queueName : queueNames) {
      System.out.println("当前监听的队列：" + queueName);
    }
  }
}
