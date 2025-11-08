package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChannelDataBo {
  /**
   * 仓库ID
   */
  private Long storageId;
  /**
   * 仓库
   */
  private String storageName;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 通道编号
   */
  private String channelCode;

  /**
   * A面架数开始数
   */
  private Long shelveNumA1;
  /**
   * A面架数结束数
   */
  private Long shelveNumA2;
  /**
   * B面架数开始数
   */
  private Long shelveNumB1;
  /**
   * B面架数结束数
   */
  private Long shelveNumB2;
}
