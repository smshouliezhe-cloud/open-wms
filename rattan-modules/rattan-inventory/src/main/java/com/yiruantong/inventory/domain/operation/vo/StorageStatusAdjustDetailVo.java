package com.yiruantong.inventory.domain.operation.vo;

  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjustDetail;
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
 * 状态属性调整明细视图对象 storage_status_adjust_detail
 *
 * @author YRT
 * @date 2025-02-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageStatusAdjustDetail.class)
public class StorageStatusAdjustDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 调整ID
       */
      @ExcelProperty(value = "调整ID")
    private Long statusAdjustDetailId;

      /**
       * 调整ID
       */
      @ExcelProperty(value = "调整ID")
    private Long statusAdjustId;

      /**
       * 库存ID
       */
      @ExcelProperty(value = "库存ID")
    private Long inventoryId;

      /**
       * 货位名称
       */
      @ExcelProperty(value = "货位名称")
    private String positionName;

      /**
       * 库存量
       */
      @ExcelProperty(value = "库存量")
    private BigDecimal productStorage;

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
       * 批次号
       */
      @ExcelProperty(value = "批次号")
    private String batchNumber;

      /**
       * 保质期天数
       */
      @ExcelProperty(value = "保质期天数")
    private Long shelfLifeDay;

      /**
       * 生产日期
       */
      @ExcelProperty(value = "生产日期")
    private Date produceDate;

      /**
       * 到期日期
       */
      @ExcelProperty(value = "到期日期")
    private Date limitDate;

      /**
       * 原属性
       */
      @ExcelProperty(value = "原属性")
    private String productAttribute;

      /**
       * 原状态
       */
      @ExcelProperty(value = "原状态")
    private String storageStatus;

      /**
       * 目标属性
       */
      @ExcelProperty(value = "目标属性")
    private String productAttributeTarget;

      /**
       * 目标状态
       */
      @ExcelProperty(value = "目标状态")
    private String storageStatusTarget;

      /**
       * 合计毛重
       */
      @ExcelProperty(value = "合计毛重")
    private BigDecimal rowWeight;

      /**
       * 单位毛重
       */
      @ExcelProperty(value = "单位毛重")
    private BigDecimal weight;

      /**
       * 排序号
       */
      @ExcelProperty(value = "排序号")
    private Long orderNum;

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

      /**
       * 单位净重
       */
      @ExcelProperty(value = "单位净重")
    private BigDecimal netWeight;

      /**
       * 小计净重
       */
      @ExcelProperty(value = "小计净重")
    private BigDecimal rowNetWeight;

      /**
       * 来源类别
       */
      @ExcelProperty(value = "来源类别")
    private String sourceType;

      /**
       * 来源主表ID
       */
      @ExcelProperty(value = "来源主表ID")
    private String sourceMainId;

      /**
       * 来源明细ID
       */
      @ExcelProperty(value = "来源明细ID")
    private String sourceDetailId;

      /**
       * 项目号
       */
      @ExcelProperty(value = "项目号")
    private String projectCode;

      /**
       * 箱号
       */
      @ExcelProperty(value = "箱号")
    private String caseNumber;

      /**
       * 分拣状态
       */
      @ExcelProperty(value = "分拣状态")
    private Long sortingStatus;

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
       * 供应商全称
       */
      @ExcelProperty(value = "供应商全称")
    private String providerName;

      /**
       * 供应商简称
       */
      @ExcelProperty(value = "供应商简称")
    private String providerShortName;

      /**
       * 订单行号
       */
      @ExcelProperty(value = "订单行号")
    private String orderLineCode;

      /**
       * 异议号
       */
      @ExcelProperty(value = "异议号")
    private String objectionCode;

      /**
       * 缺货数量
       */
      @ExcelProperty(value = "缺货数量")
    private BigDecimal lackStorage;


}
