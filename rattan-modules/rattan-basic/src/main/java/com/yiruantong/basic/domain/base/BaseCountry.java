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
import java.util.Date;
import java.util.Map;

/**
 * 国家信息对象 base_country
 *
 * @author YRT
 * @date 2024-06-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_country", autoResultMap = true)
public class BaseCountry extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 国家ID
   */
  @TableId(value = "country_id")
  private Long countryId;

  /**
   * ISO二字代码
   */
  private String iso2Code;

  /**
   * ISO三字代码
   */
  private String iso3Code;

  /**
   * 数字代码
   */
  private String digitalCode;

  /**
   * 国家英文名
   */
  private String countryName;

  /**
   * 国家中文名
   */
  private String countryNameCn;

  /**
   * 区域代码
   */
  private String countryRegionCode;

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


}
