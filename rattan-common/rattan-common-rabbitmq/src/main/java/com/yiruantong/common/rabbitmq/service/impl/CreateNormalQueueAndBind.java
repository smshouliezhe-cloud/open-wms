package com.yiruantong.common.rabbitmq.service.impl;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * <p>动态创建队列</p>
 *
 * @author 土味儿
 * Date 2023/1/7
 * @version 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class CreateNormalQueueAndBind {
  @Resource
  private RabbitAdmin rabbitAdmin;

  /**
   * 动态创建普通队列，并绑定至普通交换机
   *
   * @param exchangeName 交换机
   * @param queueName 队列名
   * @param routingKey 路由键值
   */
  public void create(String exchangeName, String queueName, String routingKey) {
    String key = routingKey.toLowerCase(Locale.ROOT);

    // 创建队列
    Queue queue = null;
    // 查询队列属性，为null时，表示队列不存在
    Properties queueProperties = rabbitAdmin.getQueueProperties(queueName);
    // 队列不存在时，创建
    if (Objects.isNull(queueProperties)) {
      log.error("消息队列名进入{}", queueName);
      queue = QueueBuilder
        .durable(queueName)
        .maxLength(RabbitConstant.QUEUE_CAPACITY)
        // 设置死信交换机 如果队列满了的时候，再向队列发送消息时，最老的消息被丢弃，且不会启用备份交换机；
        // 为了防止信息丢失，加入死信交换机和死信队列，当前队列满了的时候，最老的信息进入死信交换机，再转至死信队列
        .deadLetterExchange(RabbitConstant.BACKUP_EXCHANGE)
        // 设置死信RoutingKey（死信队列）
        .deadLetterRoutingKey(RabbitConstant.BACKUP_QUEUE)
        // 改变溢出规则：当队列溢出时，拒绝接收新消息
        //.overflow(QueueBuilder.Overflow.rejectPublish)
        .build();
      rabbitAdmin.declareQueue(queue);

      // 创建交换机
      Exchange exchange = ExchangeBuilder
        .topicExchange(exchangeName) //交换机类型 ;参数为名字
        .durable(true)  //是否持久化，true即存到磁盘,false只在内存上
        .build();

      // 绑定至交换机
      Binding binding = BindingBuilder.bind(queue).to(exchange).with(key).noargs();
      rabbitAdmin.declareBinding(binding);
      log.error("消息队列名{}，已发送消息完成", queueName);
    } else {
      log.error("消息队列名{}不存在！", queueName);
    }
  }
}
