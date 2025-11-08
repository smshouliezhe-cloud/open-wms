package com.yiruantong.inventory.domain.core.vo;

import com.yiruantong.inventory.domain.core.CoreSortingRule;
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
 * 分拣规则视图对象 core_sorting_rule
 *
 * @author YRT
 * @date 2025-02-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoreSortingRule.class)
public class CoreSortingRuleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 分拣规则ID
       */
      @ExcelProperty(value = "分拣规则ID")
    private Long sortingRuleId;

      /**
       * 单价类型
       */
      @ExcelProperty(value = "单价类型")
    private String billType;

      /**
       * 单据ID
       */
      @ExcelProperty(value = "单据ID")
    private Long billId;

      /**
       * 单据号
       */
      @ExcelProperty(value = "单据号")
    private String billCode;

      /**
       * 单据明细ID
       */
      @ExcelProperty(value = "单据明细ID")
    private Long billDetailId;

      /**
       * 商品ID
       */
      @ExcelProperty(value = "商品ID")
    private Long productId;

      /**
       * 商品编号
       */
      @ExcelProperty(value = "商品编号")
    private String productCode;

      /**
       * 商品名称
       */
      @ExcelProperty(value = "商品名称")
    private String productName;

      /**
       * 规则类型
       */
      @ExcelProperty(value = "规则类型")
    private String sortingType;

      /**
       * 分拣规则
       */
      @ExcelProperty(value = "分拣规则")
    private Map<String, Object> sortingRule;

      /**
       * 来源类别
       */
      @ExcelProperty(value = "来源类别")
    private String sourceType;

      /**
       * 来源ID
       */
      @ExcelProperty(value = "来源ID")
    private String sourceId;

      /**
       * 来源单号
       */
      @ExcelProperty(value = "来源单号")
    private String sourceCode;

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


}
