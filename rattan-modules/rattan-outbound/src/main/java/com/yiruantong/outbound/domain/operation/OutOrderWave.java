package com.yiruantong.outbound.domain.operation;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 出库单波次对象 out_order_wave
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_wave", autoResultMap = true)
public class OutOrderWave extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 波次单ID
   */
    @TableId(value = "order_wave_id")
  private Long orderWaveId;

  /**
   * 波次单号
   */
  private String orderWaveCode;

  /**
   * 单据类型
   */
  private String orderType;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 订单数
   */
  private Long orderCount;

  /**
   * 冻结数量
   */
  private BigDecimal freezeQuantity;

  /**
   * 完成数量
   */
  private Long finishedCount;

  /**
   * 未完成数量
   */
  private Long unFinishedCount;

  /**
   * 订单数量
   */
  private BigDecimal totalQuanityOrder;

  /**
   * 波次状态
   */
  private String waveStatus;

  /**
   * 打印状态
   */
  private Long printStatus;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主编号
   */
  private String consignorName;

  /**
   * 快递公司ID
   */
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  private String expressCorpName;

  /**
   * 拣货状态
   */
  private String pickingStatus;

  /**
   * 拣货人ID
   */
  private Long pickUserId;

  /**
   * 拣货人
   */
  private String pickNickName;

  /**
   * 拣货数量
   */
  private BigDecimal pickQuantity;

  /**
   * 拣配单打印次数
   */
  private Long pickingPrint;

  /**
   * 出库单打印次数
   */
  private Long outerPrint;

  /**
   * 物流单打印次数
   */
  private Long logisticsPrint;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 生成子波次
   */
  private Byte subBatch;

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
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 仓库编号
   */
  private String storageCode;


}
