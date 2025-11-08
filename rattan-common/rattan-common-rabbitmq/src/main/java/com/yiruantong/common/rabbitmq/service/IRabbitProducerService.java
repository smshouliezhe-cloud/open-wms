package com.yiruantong.common.rabbitmq.service;

import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;

/**
 * @author xtb
 * @version 1.0.0
 * @date 2024-01-06
 */
public interface IRabbitProducerService {
  void sendMsg(RabbitReceiverDto rabbitReceiverDto);
  void sendMsg(RabbitReceiverDto rabbitReceiverDto, String routingKey);
}
