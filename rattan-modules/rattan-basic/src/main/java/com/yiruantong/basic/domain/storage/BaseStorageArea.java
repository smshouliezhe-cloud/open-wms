package com.yiruantong.basic.domain.storage;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 库区管理对象 base_storage_area
 *
 * @author YiRuanTong
 * @date 2024-12-31
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_storage_area", autoResultMap = true)
public class BaseStorageArea extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 库区ID
   */
  @TableId(value = "storage_area_id")
  private Long storageAreaId;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 摆放模式
   */
  private String shelveMode;

  /**
   * 通道数
   */
  private Long channelNum;

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

  /**
   * 列数
   */
  private Long columnNum;

  /**
   * 层数
   */
  private Long rowNum;

  /**
   * A面
   */
  private Long isALine;

  /**
   * B面
   */
  private Long isBLine;

  /**
   * 货位类型
   */
  private Long positionType;

  /**
   * 货位编码规则
   */
  private String positionRegular;

  /**
   * 拣货模式
   */
  private String pickMode;

  /**
   * 最大容量
   */
  private BigDecimal maxCapacity;

  /**
   * 货架编码
   */
  private String shelvesRegular;

  /**
   * 通道编码规则
   */
  private String channelRegular;

  /**
   * 行编码规则
   */
  private String rowRegular;

  /**
   * 列编码规则
   */
  private String columnRegular;

  /**
   * 货架JSON
   */
  private String jsonData;

  /**
   * 温层类型
   */
  private String thermocLine;

  /**
   * 货位最大重量
   */
  private BigDecimal maxWeight;

  /**
   * 货位最大拍数
   */
  private Long maxBeatNumber;

  /**
   * 存货率计算
   */
  private String inventoryRate;

  /**
   * 上架操作员ID
   */
  private String userIds;

  /**
   * 上架操作员
   */
  private String userTrueNames;

  /**
   * 拣货操作员ID
   */
  private String packUserIds;

  /**
   * 拣货操作员
   */
  private String packUserTrueNames;

  /**
   * x6Data
   */
  private String x6Data;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

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

  /**
   * 自定义货架通道数据
   */
  private String channelDataList;

  /**
   * svg地址
   */
  private String svgUrl;


}
