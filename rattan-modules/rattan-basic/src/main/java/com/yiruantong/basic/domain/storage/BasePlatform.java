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
 * 月台管理对象 base_platform
 *
 * @author YRT
 * @date 2024-05-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_platform", autoResultMap = true)
public class BasePlatform extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 月台ID
   */
  @TableId(value = "platform_id")
  private Long platformId;

  /**
   * 月台编号
   */
  private String platformCode;

  /**
   * 月台名称
   */
  private String platformName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 所属仓库
   */
  private String storageName;

  /**
   * 是否可用
   */
  private Long enable;

  /**
   * 备注
   */
  private String remark;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 网点名称
   */
  private String siteName;

  /**
   * 月台状态
   */
  private String platStatus;

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

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 结束时间
   */
  private Date endTime;

  /**
   * 月台使用类型
   */
  private String platformType;

  /**
   * 车牌号
   */
  private String truckNo;


}
