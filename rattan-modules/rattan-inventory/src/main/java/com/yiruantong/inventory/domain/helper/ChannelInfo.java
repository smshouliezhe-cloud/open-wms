package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按通道推荐 ChannelInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class ChannelInfo {

  /**
   * 通道
   */
  private List<String> channelCodes;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}
