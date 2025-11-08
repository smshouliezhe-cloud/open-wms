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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 租赁管理对象 base_storage_lease
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_storage_lease", autoResultMap = true)
public class BaseStorageLease extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 租赁ID
   */
  @TableId(value = "storage_lease_id")
  private Long storageLeaseId;

  /**
   * 租赁编号
   */
  private String leaseCode;

  /**
   * 租赁名称
   */
  private String leaseName;

  /**
   * 租赁类别
   */
  private String leaseType;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 所属仓库
   */
  private String storageName;

  /**
   * 所属货架
   */
  private String positionName;

  /**
   * 所属货位
   */
  private String shelves;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 所属客户
   */
  private String consignorName;

  /**
   * 结算状态
   */
  private String payState;

  /**
   * 是否作废
   */
  private Long isInvalid;

  /**
   * 租赁开始日期
   */
  private Date startDate;

  /**
   * 租赁截止日期
   */
  private Date endDate;

  /**
   * 租金单价
   */
  private BigDecimal unitPrice;

  /**
   * 租金总计(元)
   */
  private BigDecimal totalPrice;

  /**
   * 已付租金
   */
  private BigDecimal paidPrice;

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
