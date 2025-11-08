package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PositionConfigSaveBo {
  /**
   * 库区信息
   */
  private AreaDataBo areaData;
  /**
   * 库区信息
   */
  private ArrayList<ShelveDataBo> shelveDataList;
  /**
   * 库区信息
   */
  private ArrayList<ChannelDataBo> channelDataList;
}
