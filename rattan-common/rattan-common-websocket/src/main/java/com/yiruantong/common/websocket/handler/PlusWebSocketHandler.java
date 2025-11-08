package com.yiruantong.common.websocket.handler;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.websocket.constant.WebSocketConstants;
import com.yiruantong.common.websocket.dto.WebSocketMessageDto;
import com.yiruantong.common.websocket.holder.WebSocketSessionHolder;
import com.yiruantong.common.websocket.utils.WebSocketUtils;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.List;

import static com.yiruantong.common.websocket.constant.WebSocketConstants.LOGIN_USER_KEY;

/**
 * WebSocketHandler 实现类
 *
 * @author zendwang
 */
@Slf4j
public class PlusWebSocketHandler extends AbstractWebSocketHandler {

  /**
   * 连接成功后
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);
    WebSocketSessionHolder.addSession(session.getId(), session);
    if (ObjectUtil.isNull(loginUser)) {
      log.info("[connect] sessionId: {}", session.getId());
      return;
    }
    log.info("[connect] sessionId: {},userId:{},userType:{}", session.getId(), loginUser.getUserId(), loginUser.getUserType());
  }

  /**
   * 处理发送来的文本消息
   *
   * @param session
   * @param message
   * @throws Exception
   */
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);
    if (!StringUtils.contains(message.getPayload(), WebSocketConstants.PING)) {
      log.info("PlusWebSocketHandler, 连接：" + session.getId() + "，已收到消息:" + message.getPayload());
    }
    List<String> userIds = List.of(session.getId());
    WebSocketMessageDto webSocketMessageDto = new WebSocketMessageDto();
    webSocketMessageDto.setSessionKeys(userIds);
    webSocketMessageDto.setMessage(message.getPayload());
    WebSocketUtils.publishMessage(webSocketMessageDto);
  }

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
    super.handleBinaryMessage(session, message);
  }

  /**
   * 心跳监测的回复
   *
   * @param session
   * @param message
   * @throws Exception
   */
  @Override
  protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
    WebSocketUtils.sendPongMessage(session);
  }

  /**
   * 连接出错时
   *
   * @param session
   * @param exception
   * @throws Exception
   */
  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error("[transport error] sessionId: {} , exception:{}", session.getId(), exception.getMessage());
  }

  /**
   * 连接关闭后
   *
   * @param session
   * @param status
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);
    WebSocketSessionHolder.removeSession(session.getId());
    if (ObjectUtil.isNull(loginUser)) {
      log.info("[disconnect] sessionId: {},userId:{},userType:{}", session.getId());
      return;
    }
    log.info("[disconnect] sessionId: {},userId:{},userType:{}", session.getId(), loginUser.getUserId(), loginUser.getUserType());
  }

  /**
   * 是否支持分片消息
   *
   * @return
   */
  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

}
