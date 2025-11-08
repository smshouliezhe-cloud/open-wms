package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BaseStorageLease;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 租赁管理视图对象 base_storage_lease
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseStorageLease.class)
public class BaseStorageLeaseVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 租赁ID
   */
  @ExcelProperty(value = "租赁ID")
  private Long storageLeaseId;

  /**
   * 租赁编号
   */
  @ExcelProperty(value = "租赁编号")
  private String leaseCode;

  /**
   * 租赁名称
   */
  @ExcelProperty(value = "租赁名称")
  private String leaseName;

  /**
   * 租赁类别
   */
  @ExcelProperty(value = "租赁类别")
  private String leaseType;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 所属仓库
   */
  @ExcelProperty(value = "所属仓库")
  private String storageName;

  /**
   * 所属货架
   */
  @ExcelProperty(value = "所属货架")
  private String positionName;

  /**
   * 所属货位
   */
  @ExcelProperty(value = "所属货位")
  private String shelves;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorCode;

  /**
   * 所属客户
   */
  @ExcelProperty(value = "所属客户")
  private String consignorName;

  /**
   * 结算状态
   */
  @ExcelProperty(value = "结算状态")
  private String payState;

  /**
   * 是否作废
   */
  @ExcelProperty(value = "是否作废")
  private Long isInvalid;

  /**
   * 租赁开始日期
   */
  @ExcelProperty(value = "租赁开始日期")
  private Date startDate;

  /**
   * 租赁截止日期
   */
  @ExcelProperty(value = "租赁截止日期")
  private Date endDate;

  /**
   * 租金单价
   */
  @ExcelProperty(value = "租金单价")
  private BigDecimal unitPrice;

  /**
   * 租金总计(元)
   */
  @ExcelProperty(value = "租金总计(元)")
  private BigDecimal totalPrice;

  /**
   * 已付租金
   */
  @ExcelProperty(value = "已付租金")
  private BigDecimal paidPrice;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;


}
