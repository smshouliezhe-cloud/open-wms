package com.yiruantong.common.rabbitmq.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.rabbitmq.service.IRabbitConsumerService;
import com.yiruantong.common.rabbitmq.service.impl.RabbitConsumerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化 监听 模块对应业务数据
 *
 * @author YiRuanTong
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitApplicationRunner implements ApplicationRunner {
  private final IRabbitConsumerService rabbitConsumerService;

  @Override
  public void run(ApplicationArguments args) {
    rabbitConsumerService.init();
    log.info("初始化Rabbit监听完成");
  }
}
