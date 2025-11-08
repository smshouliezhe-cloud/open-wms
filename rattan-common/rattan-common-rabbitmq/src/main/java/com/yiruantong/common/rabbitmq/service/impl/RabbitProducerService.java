package com.yiruantong.common.rabbitmq.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.rabbitmq.constant.RabbitConstant;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitProducerService;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>消息生产者</p>
 *
 * @author xtb
 * Date 2023/1/4
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class RabbitProducerService implements IRabbitProducerService {
  @Resource
  private RabbitTemplate rabbitTemplate;
  @Resource
  private CreateNormalQueueAndBind createNormalQueueAndBind;

  /**
   * 异步发送单条消息
   *
   * @param rabbitReceiverDto 消息实体的json字符串
   * @param routingKey        路由键值
   */
  @Override
  public void sendMsg(RabbitReceiverDto rabbitReceiverDto, String routingKey) {
    if (ObjectUtil.isEmpty(rabbitReceiverDto)) {
      return;
    }
    LoginUser loginUser = new LoginUser();
    loginUser.setNickname(StringUtils.defaultIfBlank(LoginHelper.getNickname(), "系统自动"));
    loginUser.setUserId(ObjectUtil.defaultIfNull(LoginHelper.getUserId(), 1L));
    loginUser.setDeptId(0L);
    loginUser.setDeptName(StringUtils.defaultIfBlank(LoginHelper.getNickname(), "默认部门"));
    loginUser.setUserType("mq");
    if (StrUtil.isEmpty(LoginHelper.getTenantId())) {
      loginUser.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
    } else {
      loginUser.setTenantId(LoginHelper.getTenantId());
    }

    rabbitReceiverDto.setLoginUser(loginUser);
    // 创建队列，并绑定至交换机
    createNormalQueueAndBind.create(RabbitConstant.EXCHANGE_WMS_NAME, RabbitConstant.QUEUE_WMS_NAME, RabbitConstant.ROUTING_KEY);
    String msgJson = JsonUtils.toJsonString(rabbitReceiverDto);
    if (msgJson != null) {
      rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_WMS_NAME, routingKey, msgJson);
    }
  }

  /**
   * 异步发送多条消息
   *
   * @param rabbitReceiverDto list集合；元素为：MqMsg
   */
  @Override
  public void sendMsg(RabbitReceiverDto rabbitReceiverDto) {
    this.sendMsg(rabbitReceiverDto, RabbitConstant.ROUTING_KEY);
  }
}
