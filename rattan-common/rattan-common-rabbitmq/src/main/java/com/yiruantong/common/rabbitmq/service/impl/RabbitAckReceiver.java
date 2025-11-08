package com.yiruantong.common.rabbitmq.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IMqStepService;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xtb
 * @version 1.0.0
 * @date 2024-01-06
 * @description 消息手动确认
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitAckReceiver implements ChannelAwareMessageListener {
  @Autowired
  private List<IRabbitReceiver> rabbitReceiverList;
  private final IMqStepService mqStepService;

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
      String consumerQueue = message.getMessageProperties().getConsumerQueue();
      String msg = new String(message.getBody());
      log.info("MyAckReceiver的【{}】队列，收到的消息：【{}】", consumerQueue, msg);
      RabbitReceiverDto rabbitReceiverDto = JsonUtils.parseObject(msg, RabbitReceiverDto.class);

      if (ObjectUtil.isNull(rabbitReceiverDto.getRabbitmqType())) {
        /*
        如果channel.basicNack(8, true, true);表示deliveryTag=8之前未确认的消息都处理失败且将这些消息重新放回队列中。
        如果channel.basicNack(8, true, false);表示deliveryTag=8之前未确认的消息都处理失败且将这些消息直接丢弃。
        如果channel.basicNack(8, false, true);表示deliveryTag=8的消息处理失败且将该消息重新放回队列。
        如果channel.basicNack(8, false, false);表示deliveryTag=8的消息处理失败且将该消息直接丢弃
         */
        channel.basicNack(deliveryTag, true, false); // 标识消息队列挂起
        return;
      }
      LoginHelper.setLoginUser(rabbitReceiverDto.getLoginUser()); // 非web设置当前登录用户信息

      // 执行对应接口方法
      RabbitReceiverDto receiverDto = JsonUtils.parseObject(msg, RabbitReceiverDto.class);
      for (var rabbitReceiver : rabbitReceiverList) {
        if (rabbitReceiver.getType().stream().anyMatch(a -> a == rabbitReceiverDto.getRabbitmqType())) {
          R<RabbitReceiverDto> recevier = rabbitReceiver.rabbitReceiver(receiverDto);
          if (recevier.isResult()) {
            // 在TaskQueueServiceImpl接收执行下一步消息队列
            mqStepService.doNextStepMQ(receiverDto);
          }
        }
      }

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
