package com.yiruantong.common.rabbitmq.service;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;

import java.util.List;
import java.util.Optional;

/**
 * @author xtb
 * @version 1.0.0
 * @date 2024-01-06
 * @description rabbitmq 接收器
 */
public interface IRabbitReceiver {
  /**
   * 获取接收类型
   *
   * @return 返回RabbitmqTypeEnum集合
   */
  List<RabbitmqTypeEnum> getType();

  /**
   * rabbitmq接收处理逻辑
   *
   * @param rabbitReceiverDto 传输数据字典
   * @return
   */
  R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto);

  /**
   * 初始化下一步数据
   *
   * @param rabbitReceiverDto 上下文
   * @param billId            单据ID
   * @param billCode          单据编号
   */
  default void initNextStep(RabbitReceiverDto rabbitReceiverDto, Long billId, String billCode) {
    // 下一步消息队列执行，记录当前业务单号
    Optional.ofNullable(rabbitReceiverDto.getDoStepList()) // 将可能为 null 的集合包装成 Optional
      .flatMap(list -> list.stream() // 如果集合非空，则创建流
        .filter(x -> !x.isFinished()) // 根据条件过滤
        .findFirst()) // 找到第一个满足条件的元素
      .ifPresent(doStepDto -> { // 如果找到了元素，则执行此 lambda 表达式
        // 这里放置你想要做的进一步处理
        doStepDto.setBillId(billId);
        doStepDto.setBillCode(billCode);
      });
  }
}
