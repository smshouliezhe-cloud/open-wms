package com.yiruantong.basic.domain.storage;

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
 * 商品上架策略对象 base_shelve_regular
 *
 * @author YiRuanTong
 * @date 2024-01-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_shelve_regular", autoResultMap = true)
public class BaseShelveRegular extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 规则ID
   */
  @TableId(value = "shelve_regular_id")
  private Long shelveRegularId;

  /**
   * 规则名称
   */
  private String shelveRegularName;

  /**
   * 货位类型
   */
  private Long positionType;

  /**
   * jsondata
   */
  private String jsonData;

  /**
   * 适用仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
  private Long enable;


}
