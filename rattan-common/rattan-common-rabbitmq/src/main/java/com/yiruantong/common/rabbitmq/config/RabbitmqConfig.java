package com.yiruantong.common.rabbitmq.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置
 *
 * @author xtb
 */
@Slf4j
@Configuration
public class RabbitmqConfig {
  @Resource
  private CachingConnectionFactory connectionFactory;
  @Resource
  private RabbitTemplate rabbitTemplate;

  /**
   * 用于动态创建队列、交换机，并绑定
   * @return RabbitAdmin
   */
  @Bean
  public RabbitAdmin rabbitAdmin(){
    return new RabbitAdmin(rabbitTemplate);
  }

  /**
   * 用于设置动态监听
   * @return 返回SimpleMessageListenerContainer
   */
  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer(){
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);

    container.setConcurrentConsumers(10);
    container.setMaxConcurrentConsumers(50);
    // 手动确认
    container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    // 预取值
    container.setPrefetchCount(RabbitConstant.PRE_FETCH_SIZE);
    return container;
  }

  /**
   * 备份交换机
   * 类型：fanout
   *
   * @return 返回交换机FanoutExchange
   */
  @Bean(RabbitConstant.BEAN_BACKUP_EXCHANGE)
  public FanoutExchange getBackupExchange() {
    return new FanoutExchange(RabbitConstant.BACKUP_EXCHANGE);
  }

  /**
   * 获取备份队列
   *
   * @return 返回Queue
   */
  @Bean(RabbitConstant.BEAN_BACKUP_QUEUE)
  public Queue getBackupQueue() {
    return QueueBuilder.durable(RabbitConstant.BACKUP_QUEUE).build();
  }

  /**
   * 备份队列 绑定 备份交换机
   *
   * @param queue 队列
   * @param exchange 交换机
   * @return 返回Binding
   */
  @Bean
  public Binding backupQueueBindBackupExchange(
    @Qualifier(RabbitConstant.BEAN_BACKUP_QUEUE) Queue queue,
    @Qualifier(RabbitConstant.BEAN_BACKUP_EXCHANGE) FanoutExchange exchange
  ) {
    return BindingBuilder.bind(queue).to(exchange);
  }
}
