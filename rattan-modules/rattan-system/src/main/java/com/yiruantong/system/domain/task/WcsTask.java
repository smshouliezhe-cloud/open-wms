package com.yiruantong.system.domain.task;

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
 * WCS接口对象 wcs_task
 *
 * @author YRT
 * @date 2025-01-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "wcs_task", autoResultMap = true)
public class WcsTask extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 任务ID
   */
    @TableId(value = "wcs_task_id")
  private Long wcsTaskId;

  /**
   * 任务类型
   */
  private String wcsTaskType;

  /**
   * 来源主表ID
   */
  private Long mainId;

  /**
   * 来源明细ID
   */
  private Long detailId;

  /**
   * 执行单号
   */
  private String billCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 货位
   */
  private String positionName;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 关联号
   */
  private String relationCode;

  /**
   * 数量
   */
  private BigDecimal qty;

  /**
   * 排序号
   */
  private Long orderNum;

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
   * 推送状态
   */
  private String pushStatus;

  /**
   * 推送时间
   */
  private Date pushDate;

  /**
   * 推送消息
   */
  private String pushMsg;

  /**
   * 推送次数
   */
  private Long pushCount;

  /**
   * 来源ID
   */
  private Long sourceId;

  /**
   * 仓库编号
   */
  private String storageCode;

  /**
   * sn号
   */
  private String singleSignCode;

  /**
   * wcs优先级
   */
  private String itemGroup;


}
