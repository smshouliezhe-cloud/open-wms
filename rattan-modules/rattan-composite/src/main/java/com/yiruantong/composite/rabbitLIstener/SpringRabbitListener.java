package com.yiruantong.composite.rabbitLIstener;

import lombok.RequiredArgsConstructor;
import com.yiruantong.composite.service.inventory.IAllocateApplyService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringRabbitListener {
  private final IAllocateApplyService allocateApplyService;
  private final ITaskQueueService taskQueueService;

  // 监听死信队列
//  @RabbitListener(queues = RabbitRoutingKey.QUEUE_WMS_NAME)
//  public void process(String msg, Channel channel, @Headers Map<String, Object> headers, Message message) throws IOException {
//    long deliveryTag = message.getMessageProperties().getDeliveryTag();
//    try {
//      System.out.println("处理业务：" + msg);
//      Dict dict = JsonUtils.parseMap(msg);
//      String type = Convert.toStr(dict.get("type"));
//      RabbitmqTypeEnum rabbitmqTypeEnum = RabbitmqTypeEnum.matchingEnum(type);
//      if (ObjectUtil.isNull(rabbitmqTypeEnum)) {
//        // 更新队列状态-错误
//        channel.basicNack(deliveryTag, true, false); // 标识消息队列挂起
//        return;
//      }
//
//      switch (rabbitmqTypeEnum) {
//        case OUT_FINISHED_TO_ALLOCATE:
//          allocateApplyService.outOrderSubsequent(msg);
//          break;
//        default:
//          break;
//      }
//      // 更新队列状态
//
//      channel.basicAck(deliveryTag, true); // 标识消息队列执行完成
//    } catch (Exception e) {
//      /*
//      第三个参数：requeue：重回队列。如果设置为true，则消息重新回到queue，broker会重新发送该消息给消费端
//      这里要演示发到死信队列，就设置为false
//       */
//      channel.basicNack(deliveryTag, true, false);
//    }
//  }
}
