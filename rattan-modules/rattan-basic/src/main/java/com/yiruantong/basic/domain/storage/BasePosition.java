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
 * 货位管理对象 base_position
 *
 * @author YiRuanTong
 * @date 2024-12-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_position", autoResultMap = true)
public class BasePosition extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "position_id")
  private Long positionId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 货位类型
   */
  private Byte positionType;

  /**
   * 是否锁定
   */
  private Byte isLocked;

  /**
   * 是否冻结
   */
  private Byte isFreeze;

  /**
   * 最大容量
   */
  private BigDecimal maxCapacity;

  /**
   * 通道代码
   */
  private String channelCode;

  /**
   * 面
   */
  private String lineCode;

  /**
   * 货架号
   */
  private String shelveCode;

  /**
   * 列
   */
  private String columnCode;

  /**
   * 层
   */
  private String rowCode;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 摆放模式
   */
  private String shelveMode;

  /**
   * 是否支持混物料编号
   */
  private Byte isMixProduct;

  /**
   * 货位长度
   */
  private BigDecimal positionLength;

  /**
   * 货位宽度
   */
  private BigDecimal positionWidth;

  /**
   * 最低库存
   */
  private Long minCapacity;

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
   * 是否可用
   */
  private Byte enable;

  /**
   * 最大拍数
   */
  private Long maxPaiQty;

  /**
   * 温层
   */
  private String thermocLine;


}
