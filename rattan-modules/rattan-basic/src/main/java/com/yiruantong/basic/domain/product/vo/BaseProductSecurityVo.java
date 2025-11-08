package com.yiruantong.basic.domain.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.product.BaseProductSecurity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 防伪标签视图对象 base_product_security
 *
 * @author YRT
 * @date 2024-04-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseProductSecurity.class)
public class BaseProductSecurityVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 防伪标签id
   */
  @ExcelProperty(value = "防伪标签id")
  private Long securityId;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
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
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 生产企业
   */
  @ExcelProperty(value = "生产企业")
  private String produceEnterPrise;

  /**
   * 批号名称
   */
  @ExcelProperty(value = "批号名称")
  private String batchNumber;

  /**
   * 批号编号
   */
  @ExcelProperty(value = "批号编号")
  private String batchCode;

  /**
   * 防伪码数量
   */
  @ExcelProperty(value = "防伪码数量")
  private Long quantity;

  /**
   * 单位
   */
  @ExcelProperty(value = "单位")
  private String unit;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private String unitConvert;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Long auditing;

  /**
   * 审核时间
   */
  @ExcelProperty(value = "审核时间")
  private Date auditDate;

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
   * 上传图片
   */
  @ExcelProperty(value = "上传图片")
  private String productImageUrl;


}
