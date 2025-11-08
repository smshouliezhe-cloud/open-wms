package com.yiruantong.basic.domain.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.product.BaseProductType;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 商品类目管理视图对象 base_product_type
 *
 * @author YiRuanTong
 * @date 2024-10-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseProductType.class)
public class BaseProductTypeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ERP类目ID
   */
  @ExcelProperty(value = "ERP类目ID")
  private Long typeId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 类目名称
   */
  @ExcelProperty(value = "类目名称")
  private String typeName;

  /**
   * WEB类目名称
   */
  @ExcelProperty(value = "WEB类目名称")
  private String webTypeName;

  /**
   * 英文申报名
   */
  @ExcelProperty(value = "英文申报名")
  private String ciqName;

  /**
   * 中文申报名
   */
  @ExcelProperty(value = "中文申报名")
  private String ciqNameCn;

  /**
   * 根目录ID
   */
  @ExcelProperty(value = "根目录ID")
  private Long rootId;

  /**
   * 根目录名称
   */
  @ExcelProperty(value = "根目录名称")
  private String rootName;

  /**
   * 类型ID
   */
  @ExcelProperty(value = "类型ID")
  private String fullTypeId;

  /**
   * 类型名称
   */
  @ExcelProperty(value = "类型名称")
  private String fullTypeName;

  /**
   * 节点级别
   */
  @ExcelProperty(value = "节点级别")
  private Long nodeLevel;

  /**
   * 预制类型
   */
  @ExcelProperty(value = "预制类型")
  private String precutPosition;

  /**
   * 店铺类目ID
   */
  @ExcelProperty(value = "店铺类目ID")
  private Long categoryId;

  /**
   * 是否有子集
   */
  @ExcelProperty(value = "是否有子集")
  private Long hasChild;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String state;

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
   * 质检方案
   */
  @ExcelProperty(value = "质检方案")
  private String qualityPlan;

  /**
   * 质检比例
   */
  @ExcelProperty(value = "质检比例")
  private BigDecimal qualityProportion;

  /**
   * 是否推荐
   */
  @ExcelProperty(value = "是否推荐")
  private Long isRecommend;

  /**
   * 类型URL
   */
  @ExcelProperty(value = "类型URL")
  private String typeUrl;

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
   * 类目编号
   */
  @ExcelProperty(value = "类目编号")
  private String typeCode;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 类别图片
   */
  @ExcelProperty(value = "类别图片")
  private String images;


}
