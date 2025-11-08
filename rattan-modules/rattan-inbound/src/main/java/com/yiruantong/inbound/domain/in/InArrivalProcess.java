package com.yiruantong.inbound.domain.in;

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
 * 到货加工对象 in_arrival_process
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_arrival_process", autoResultMap = true)
public class InArrivalProcess extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 加工ID
   */
  @TableId(value = "process_id")
  private Long processId;

  /**
   * 加工编号
   */
  private String processCode;

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
   * 加工人ID
   */
  private Long userId;

  /**
   * 加工人
   */
  private String nickName;

  /**
   * 完成时间
   */
  private Date endDate;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Long auditing;

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


}
