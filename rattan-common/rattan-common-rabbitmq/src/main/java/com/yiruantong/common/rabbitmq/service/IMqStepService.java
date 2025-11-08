package com.yiruantong.common.rabbitmq.service;

import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;

/**
 * 消息队列步骤执行service
 */
public interface IMqStepService {
  /**
   * 执行下一步消息队列
   *
   * @param rabbitReceiverDto 上下文
   */
  void doNextStepMQ(RabbitReceiverDto rabbitReceiverDto);
}
