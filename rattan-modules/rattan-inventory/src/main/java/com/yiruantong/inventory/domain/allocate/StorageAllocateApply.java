package com.yiruantong.inventory.domain.allocate;

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
 * 调拨申请单对象 storage_allocate_apply
 *
 * @author YRT
 * @date 2024-01-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_allocate_apply", autoResultMap = true)
public class StorageAllocateApply extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调拨申请单ID
   */
  @TableId(value = "allocate_apply_id")
  private Long allocateApplyId;

  /**
   * 调拨申请单编号
   */
  private String allocateApplyCode;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 申请日期
   */
  private Date applyDate;

  /**
   * 调出仓库ID
   */
  private Long storageId;

  /**
   * 调出仓库名称
   */
  private String storageName;

  /**
   * 调入仓库ID
   */
  private Long storageIdIn;

  /**
   * 调入仓库名称
   */
  private String storageNameIn;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 申请状态
   */
  private String applyStatus;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

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
   * 审核备注
   */
  private String auditRemark;

  /**
   * 订单类型
   */
  private String orderType;

  /**
   * 总重量
   */
  private BigDecimal totalWeight;

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
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;

  /**
   * 在途虚拟仓id
   */
  private Long virtualStorageId;

  /**
   * 在途虚拟仓名称
   */
  private String virtualStorageName;

  /**
   * 收货人
   */
  private String shippingName;

  /**
   * 邮政编码
   */
  private String postCode;

  /**
   * 国家ID
   */
  private Long countryId;

  /**
   * 国家
   */
  private String countryName;

  /**
   * 国家全称
   */
  private String countryFullName;

  /**
   * 省ID
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市ID
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区ID
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

  /**
   * 发件人地址
   */
  private String shippingAddress;

  /**
   * 快递类别
   */
  private Byte expressCorpType;

  /**
   * 快递ID
   */
  private Long expressCorpId;

  /**
   * 快递名称
   */
  private String expressCorpName;

  /**
   * 快递编号
   */
  private String expressCode;

  /**
   * 线路Id
   */
  private Long lineId;

  /**
   * 线路编号
   */
  private String lineCode;

  /**
   * 线路名称
   */
  private String lineName;

  /**
   * 配送类型
   */
  private String distributionType;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;

  /**
   * 应付快递费
   */
  private BigDecimal shippingAmount;

  /**
   * 本单应收
   */
  private BigDecimal totalUnpaid;

  /**
   * 目标货主名称
   */
  private String consignorNameTarget;

  /**
   * 目标货主编号
   */
  private String consignorCodeTarget;

  /**
   * 目标货主ID
   */
  private Long consignorIdTarget;


}
