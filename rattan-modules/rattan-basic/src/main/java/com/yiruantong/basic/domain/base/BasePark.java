package com.yiruantong.basic.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 物流园区对象 base_park
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_park", autoResultMap = true)
public class BasePark extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 园区ID
   */
  @TableId(value = "park_Id")
  private Long parkId;

  /**
   * 园区名称
   */
  private String parkName;

  /**
   * 地图地址
   */
  private String mapAddress;

  /**
   * 详细地址
   */
  private String detailAddress;

  /**
   * 经度
   */
  private BigDecimal longitude;

  /**
   * 纬度
   */
  private BigDecimal latitude;

  /**
   * 备注
   */
  private String remark;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 百度地图
   */
  private String baiduMap;

  /**
   * 经度
   */
  private String lng;

  /**
   * 维度
   */
  private String lat;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

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
   * 排序号
   */
  private Long orderNum;


}
