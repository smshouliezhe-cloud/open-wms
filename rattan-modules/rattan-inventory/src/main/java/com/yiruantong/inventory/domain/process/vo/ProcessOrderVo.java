package com.yiruantong.inventory.domain.process.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.process.ProcessOrder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 加工列视图对象 process_order
 *
 * @author YRT
 * @date 2025-01-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ProcessOrder.class)
public class ProcessOrderVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 内部单据id
   */
  @ExcelProperty(value = "内部单据id")
  private Long processId;

  /**
   * 内部订单号
   */
  @ExcelProperty(value = "内部订单号")
  private String processCode;

  /**
   * 加工项目
   */
  @ExcelProperty(value = "加工项目")
  private String processProject;

  /**
   * 来源id
   */
  @ExcelProperty(value = "来源id")
  private Long billId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String billCode;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
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
   * 报关单号
   */
  @ExcelProperty(value = "报关单号")
  private String customsNumber;

  /**
   * 投料重量
   */
  @ExcelProperty(value = "投料重量")
  private BigDecimal feedingWeight;

  /**
   * 投料重量单位
   */
  @ExcelProperty(value = "投料重量单位")
  private String feedingWeightUnit;

  /**
   * 投料数量
   */
  @ExcelProperty(value = "投料数量")
  private BigDecimal feedingNumber;

  /**
   * 投料数量单位
   */
  @ExcelProperty(value = "投料数量单位")
  private String feedingNumberUnit;

  /**
   * 产出重量
   */
  @ExcelProperty(value = "产出重量")
  private BigDecimal outputWeight;

  /**
   * 产出重量单位
   */
  @ExcelProperty(value = "产出重量单位")
  private String outputWeightUnit;

  /**
   * 产出件数
   */
  @ExcelProperty(value = "产出件数")
  private Long outputNumber;

  /**
   * 产出件数单位
   */
  @ExcelProperty(value = "产出件数单位")
  private String outputNumberUnit;

  /**
   * 出成率
   */
  @ExcelProperty(value = "出成率")
  private String yieId;

  /**
   * 所属仓位
   */
  @ExcelProperty(value = "所属仓位")
  private String literaId;

  /**
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long storageId;

  /**
   * 出货仓库
   */
  @ExcelProperty(value = "出货仓库")
  private String storageName;

  /**
   * 装卸队
   */
  @ExcelProperty(value = "装卸队")
  private String carrierName;

  /**
   * 加工日期
   */
  @ExcelProperty(value = "加工日期")
  private Date processDate;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String orderStatus;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 平台Id
   */
  @ExcelProperty(value = "平台Id")
  private Long platUserId;

  /**
   * 平台编号
   */
  @ExcelProperty(value = "平台编号")
  private String platUserCode;

  /**
   * 平台名称
   */
  @ExcelProperty(value = "平台名称")
  private String platUserName;

  /**
   * 公司名称
   */
  @ExcelProperty(value = "公司名称")
  private String platCorpName;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

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
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNumber;


}
