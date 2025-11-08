package com.yiruantong.basic.domain.storage;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 仓库货架对象 base_storage_shelve
 *
 * @author YRT
 * @date 2024-02-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_storage_shelve", autoResultMap = true)
public class BaseStorageShelve extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 货架信息ID
   */
  @TableId(value = "storage_shelve_id")
  private Long storageShelveId;

  /**
   *
   */
  private String areaCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   *
   */
  private String storageName;

  /**
   *
   */
  private String channelCode;

  /**
   *
   */
  private String shelveCode;

  /**
   * 列数
   */
  private Long columnNum;

  /**
   * 层数
   */
  private Long rowNum;

  /**
   *
   */
  private String positionType;

  /**
   *
   */
  private String columnRegular;

  /**
   *
   */
  private String positionRegular;

  /**
   * 最大容量
   */
  private Long maxCapacity;

  /**
   *
   */
  private String shelvesRegular;

  /**
   *
   */
  private String channelRegular;

  /**
   *
   */
  private String rowRegular;

  /**
   * 排序号
   */
  private Long orderNo;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
