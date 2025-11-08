package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BaseStoragePlate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 仓库容器查询视图对象 base_storage_plate
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseStoragePlate.class)
public class BaseStoragePlateVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 主键字段id
   */
  @ExcelProperty(value = "主键字段id")
  private Long storagePlateId;

  /**
   * 执行单id
   */
  @ExcelProperty(value = "执行单id")
  private Long billId;

  /**
   * 执行单号
   */
  @ExcelProperty(value = "执行单号")
  private String billCode;

  /**
   * 仓库id
   */
  @ExcelProperty(value = "仓库id")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 客户id
   */
  @ExcelProperty(value = "客户id")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户简称
   */
  @ExcelProperty(value = "客户简称")
  private String clientShortName;

  /**
   * 销售区域id
   */
  @ExcelProperty(value = "销售区域id")
  private Long consignorIdSale;

  /**
   * 销售区域编号
   */
  @ExcelProperty(value = "销售区域编号")
  private String consignorCodeSale;

  /**
   * 销售区域名称
   */
  @ExcelProperty(value = "销售区域名称")
  private String consignorNameSale;

  /**
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;

  /**
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 来源类型
   */
  @ExcelProperty(value = "来源类型")
  private String sourceType;

  /**
   * 入库数量
   */
  @ExcelProperty(value = "入库数量")
  private BigDecimal enterQuantity;

  /**
   * 返厂数量
   */
  @ExcelProperty(value = "返厂数量")
  private BigDecimal returnFactoryQty;

  /**
   * 未返厂数量
   */
  @ExcelProperty(value = "未返厂数量")
  private BigDecimal unReturnFactoryQty;

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

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;

  /**
   * 容器属性
   */
  @ExcelProperty(value = "容器属性")
  private String plateAttribute;


}
