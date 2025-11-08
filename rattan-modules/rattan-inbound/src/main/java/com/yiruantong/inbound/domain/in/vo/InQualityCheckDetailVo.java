package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 质检管理明细视图对象 in_quality_check_detail
 *
 * @author YiRuanTong
 * @date 2023-10-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InQualityCheckDetail.class)
public class InQualityCheckDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 质检明细ID
   */
  @ExcelProperty(value = "质检明细ID")
  private Long qualityCheckDetailId;

  /**
   * 质检ID
   */
  @ExcelProperty(value = "质检ID")
  private Long qualityCheckId;

  /**
   * 采购明细ID
   */
  @ExcelProperty(value = "采购明细ID")
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  @ExcelProperty(value = "采购单ID")
  private Long orderId;

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
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

  /**
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal quantity;

  /**
   * 质检类型
   */
  @ExcelProperty(value = "质检类型")
  private String checkType;

  /**
   * 质检数量
   */
  @ExcelProperty(value = "质检数量")
  private BigDecimal checkQuantity;

  /**
   * 次品数量
   */
  @ExcelProperty(value = "次品数量")
  private BigDecimal defectiveQuantity;

  /**
   * 合格率
   */
  @ExcelProperty(value = "合格率")
  private BigDecimal qualifiedRate;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date productionDate;

  /**
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private Long shelfLifeDay;

  /**
   * 次品原因
   */
  @ExcelProperty(value = "次品原因")
  private String defectiveRemark;

  /**
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
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
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

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


}
