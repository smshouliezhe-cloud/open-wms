package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库计划单明细视图对象 out_order_plan_detail
 *
 * @author YiRuanTong
 * @date 2025-01-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderPlanDetail.class)
public class OutOrderPlanDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 出库计划单明细ID
   */
  @ExcelProperty(value = "出库计划单明细ID")
  private Long orderPlanDetailId;

  /**
   * 出库计划单ID
   */
  @ExcelProperty(value = "出库计划单ID")
  private Long orderPlanId;

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
   * 小单位
   */
  @ExcelProperty(value = "小单位")
  private String smallUnit;

  /**
   * 大单位
   */
  @ExcelProperty(value = "大单位")
  private String bigUnit;

  /**
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal quantity;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 单位换算
   */
  @ExcelProperty(value = "单位换算")
  private String unitConvertText;

  /**
   * 成本单价
   */
  @ExcelProperty(value = "成本单价")
  private BigDecimal purchasePrice;

  /**
   * 售价
   */
  @ExcelProperty(value = "售价")
  private BigDecimal salePrice;

  /**
   * 金额
   */
  @ExcelProperty(value = "金额")
  private BigDecimal saleAmount;

  /**
   * 已出库数量
   */
  @ExcelProperty(value = "已出库数量")
  private BigDecimal outQuantity;

  /**
   * 未出库数量
   */
  @ExcelProperty(value = "未出库数量")
  private BigDecimal surplusQuantity;

  /**
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

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
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 有效库存
   */
  @ExcelProperty(value = "有效库存")
  private Long validQuantity;

  /**
   * 发件人省
   */
  @ExcelProperty(value = "发件人省")
  private String senderprov;

  /**
   * 发件人市
   */
  @ExcelProperty(value = "发件人市")
  private String sendercity;

  /**
   * 发件人区
   */
  @ExcelProperty(value = "发件人区")
  private String sendercounty;

  /**
   * 发件人地址
   */
  @ExcelProperty(value = "发件人地址")
  private String senderaddress;

  /**
   * 发件公司名
   */
  @ExcelProperty(value = "发件公司名")
  private String sendercompany;

  /**
   * 发件手机号
   */
  @ExcelProperty(value = "发件手机号")
  private String sendermobile;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String singleSignCode;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

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
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 含税单价
   */
  @ExcelProperty(value = "含税单价")
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @ExcelProperty(value = "价税合计")
  private BigDecimal rateAmount;

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
   * 货主Id
   */
  @ExcelProperty(value = "货主Id")
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


}
