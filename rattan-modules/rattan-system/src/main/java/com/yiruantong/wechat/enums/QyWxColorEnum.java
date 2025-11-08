package com.yiruantong.wechat.enums;

import lombok.Getter;

/**
 * 文字的颜色，目前支持：0(默认) 灰色，1 黑色，2 红色，3 绿色
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2024/11/15
 */
@Getter
public enum QyWxColorEnum {

  DEFAULT(0),

  BLACK(1),

  RED(2),

  GREEN(3);

  QyWxColorEnum(int color) {
    this.color = color;
  }

  private final int color;
}
