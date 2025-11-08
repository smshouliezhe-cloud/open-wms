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
 * 容器使用轨迹对象 base_plateHistory
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_history", autoResultMap = true)
public class BasePlateHistory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器历史ID
   */
  @TableId(value = "plate_history_id")
  private Long plateHistoryId;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 单据编号
   */
  private String billCode;

  /**
   * 容器Id
   */
  private Long plateId;

  /**
   * 容易编号
   */
  private String plateCode;

  /**
   * 容器类型
   */
  private String plateType;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 操作类型
   */
  private String actionType;

  /**
   * 货位
   */
  private String positionName;

  /**
   * 使用前状态
   */
  private String beforeStatus;

  /**
   * 使用后状态
   */
  private String afterStatus;

  /**
   * 操作时间
   */
  private Date operateDate;

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


}
