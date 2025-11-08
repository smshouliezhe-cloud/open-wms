package com.yiruantong.common.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RootRabbitListener {
  /**
   * 监听备份队列消息
   *
   * @param message
   */
  @RabbitListener(queues = RabbitConstant.BACKUP_QUEUE)
  public void receiveMsg(Message message, Channel channel) throws IOException {
    var deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
      String msg = new String(message.getBody());
      log.info("接收到备份队列的消息：【{}】", msg);
      channel.basicAck(deliveryTag, true);
    } catch (Exception e) {
      /*
        true消息处理失败且将该消息重新放回队列
        false消息处理失败且将该消息直接丢弃
       */
      channel.basicReject(deliveryTag, false);
    }
  }
}
