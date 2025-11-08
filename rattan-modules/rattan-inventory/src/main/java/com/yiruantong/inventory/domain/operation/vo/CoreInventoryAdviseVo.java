package com.yiruantong.inventory.domain.operation.vo;

  import java.math.BigDecimal;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 建议采购转遇到货视图对象 core_inventory_advise
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoreInventoryAdvise.class)
public class CoreInventoryAdviseVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 行ID
       */
      @ExcelProperty(value = "行ID")
    private Long adviseId;

      /**
       * 仓库ID
       */
      @ExcelProperty(value = "仓库ID")
    private Long storageId;

      /**
       * 仓库名称
       */
      @ExcelProperty(value = "仓库名称")
    private String storageName;

      /**
       * 产品ID
       */
      @ExcelProperty(value = "产品ID")
    private Long productId;

      /**
       * 产品编号
       */
      @ExcelProperty(value = "产品编号")
    private String productCode;

      /**
       * 产品名称
       */
      @ExcelProperty(value = "产品名称")
    private String productName;

      /**
       * 条形码
       */
      @ExcelProperty(value = "条形码")
    private String productModel;

      /**
       * 产品规格
       */
      @ExcelProperty(value = "产品规格")
    private String productSpec;

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
       * 货主名称
       */
      @ExcelProperty(value = "货主名称")
    private String consignorName;

      /**
       * 供应商ID
       */
      @ExcelProperty(value = "供应商ID")
    private Long providerId;

      /**
       * 供应商编号
       */
      @ExcelProperty(value = "供应商编号")
    private String providerCode;

      /**
       * 供应商简称
       */
      @ExcelProperty(value = "供应商简称")
    private String providerShortName;

      /**
       * 库存量
       */
      @ExcelProperty(value = "库存量")
    private BigDecimal productStorage;

      /**
       * 最低库存
       */
      @ExcelProperty(value = "最低库存")
    private Long storageLower;

      /**
       * 30天销量
       */
      @ExcelProperty(value = "30天销量")
    private Long thirtyDaySale;

      /**
       *  周期
       */
      @ExcelProperty(value = " 周期")
    private Long cycle;

      /**
       * 最近30天销量均值
       */
      @ExcelProperty(value = "最近30天销量均值")
    private BigDecimal thirtyDayAverageSale;

      /**
       *  建议采购量
       */
      @ExcelProperty(value = " 建议采购量")
    private BigDecimal adviseQty;

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
       * 预定数量
       */
      @ExcelProperty(value = "预定数量")
    private Long reserveQty;

      /**
       * 成本价
       */
      @ExcelProperty(value = "成本价")
    private BigDecimal purchasePrice;

      /**
       * 仓库编号
       */
      @ExcelProperty(value = "仓库编号")
    private String storageCode;


}
