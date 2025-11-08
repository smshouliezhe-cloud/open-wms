package com.yiruantong.inventory.domain.operation;

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
 * 其他入库单明细对象 storage_enter_detail
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_enter_detail", autoResultMap = true)
public class StorageEnterDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 其他入库明细ID
   */
  @TableId(value = "enter_detail_id")
  private Long enterDetailId;

  /**
   * 其他入库单ID
   */
  private Long enterId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 商品规格
   */
  private String productModel;

  /**
   * 条形码
   */
  private String productSpec;

  /**
   * 库存单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 入库数量
   */
  private BigDecimal enterQuantity;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 单价
   */
  private BigDecimal purchasePrice;

  /**
   * 金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产时间
   */
  private Date produceDate;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 定制唯一码
   */
  private String singleSignCode;

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
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 商品品牌
   */
  private String brandName;

  /**
   * 商品类别
   */
  private String typeName;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 温层
   */
  private String thermocLine;

  /**
   * 图片
   */
  private String images;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
