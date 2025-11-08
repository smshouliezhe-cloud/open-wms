package com.yiruantong.inbound.domain.api;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 质检单业务对象 InQualityCheck
 *
 * @author YiRuanTong
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiInQualityCheckBo {


  /**
   * 质检单明细
   */
  List<ApiInQualityCheckDetailBo> detailList;
  /**
   * 质检ID
   */
  private Long qualityCheckId;

  /**
   * 质检编号
   */
  private String qualityCheckCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 采购单ID
   */
  private Long orderId;

  /**
   * 采购单编号
   */
  private String orderCode;

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
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计毛重
   */
  private BigDecimal totalWeight;

  /**
   * 合计质检数量
   */
  private BigDecimal totalCheckQuantity;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 质检人ID
   */
  private Long userId;

  /**
   * 质检人
   */
  private String nickName;

  /**
   * 质检时间
   */
  private Date checkDate;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

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
   * 入库单编号
   */
  private String enterCode;

  /**
   * 入库单ID
   */
  private Long enterId;


}
