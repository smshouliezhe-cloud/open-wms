package com.yiruantong.inventory.domain.process;

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
 * 加工列对象 process_order
 *
 * @author YRT
 * @date 2025-01-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "process_order", autoResultMap = true)
public class ProcessOrder extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 内部单据id
   */
    @TableId(value = "process_id")
  private Long processId;

  /**
   * 内部订单号
   */
  private String processCode;

  /**
   * 加工项目
   */
  private String processProject;

  /**
   * 来源id
   */
  private Long billId;

  /**
   * 来源单号
   */
  private String billCode;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户简称
   */
  private String clientShortName;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 报关单号
   */
  private String customsNumber;

  /**
   * 投料重量
   */
  private BigDecimal feedingWeight;

  /**
   * 投料重量单位
   */
  private String feedingWeightUnit;

  /**
   * 投料数量
   */
  private BigDecimal feedingNumber;

  /**
   * 投料数量单位
   */
  private String feedingNumberUnit;

  /**
   * 产出重量
   */
  private BigDecimal outputWeight;

  /**
   * 产出重量单位
   */
  private String outputWeightUnit;

  /**
   * 产出件数
   */
  private Long outputNumber;

  /**
   * 产出件数单位
   */
  private String outputNumberUnit;

  /**
   * 出成率
   */
  private String yieId;

  /**
   * 所属仓位
   */
  private String literaId;

  /**
   * 出货仓库ID
   */
  private Long storageId;

  /**
   * 出货仓库
   */
  private String storageName;

  /**
   * 装卸队
   */
  private String carrierName;

  /**
   * 加工日期
   */
  private Date processDate;

  /**
   * 状态
   */
  private String orderStatus;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

  /**
   * 平台Id
   */
  private Long platUserId;

  /**
   * 平台编号
   */
  private String platUserCode;

  /**
   * 平台名称
   */
  private String platUserName;

  /**
   * 公司名称
   */
  private String platCorpName;

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
   * 集装箱号
   */
  private String containerNumber;


}
