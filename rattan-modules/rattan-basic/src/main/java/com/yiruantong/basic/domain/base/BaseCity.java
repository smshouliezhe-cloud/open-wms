package com.yiruantong.basic.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 省市区管理对象 base_city
 *
 * @author YRT
 * @date 2024-11-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_city", autoResultMap = true)
public class BaseCity extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 地区ID
   */
  @TableId(value = "city_id")
  private Long cityId;

  /**
   * 地区名称
   */
  private String cityName;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 所属仓库ID
   */
  private Long storageId;

  /**
   * 所属仓库
   */
  private String storageName;

  /**
   * 货到付款
   */
  private Byte isPayAfter;

  /**
   * 发货时效
   */
  private Long sendDay;

  /**
   * 层级ID
   */
  private Long stepId;

  /**
   * 根ID
   */
  private Long rootId;

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
