package com.yiruantong.wechat.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: lst
 * @Date 2020-08-19
 * see <a href="https://developer.work.weixin.qq.com/document/path/96458#%E6%96%87%E6%9C%AC%E6%B6%88%E6%81%AF">帮助文档</a>
 */
@Setter
@Getter
public class TextMessage {
  private String touser;
  private String msgtype;
  private Long agentid;
  private TextMessageContent text;
  /**
   * 表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
   */
  int safe;
  /**
   * 表示是否开启id转译，0表示否，1表示是，默认0。
   */
  int enable_id_trans;
  /**
   * 支持重复消息检查，当指定 "enable_duplicate_check": 1开启: 表示在一定时间间隔内，同样内容（请求json）的消息，不会重复收到；
   */
  int enable_duplicate_check;
  /**
   * 时间间隔可通过duplicate_check_interval指定，默认1800秒
   */
  int duplicate_check_interval;

  private String Content;

}
