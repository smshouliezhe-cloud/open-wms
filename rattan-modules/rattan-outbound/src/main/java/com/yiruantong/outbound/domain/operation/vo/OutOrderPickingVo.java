package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 订单拣货查询视图对象 out_order_picking
 *
 * @author YRT
 * @date 2023-12-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderPicking.class)
public class OutOrderPickingVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 拣货单ID
   */
  @ExcelProperty(value = "拣货单ID")
  private Long orderPickingId;

  /**
   * 拣货单编号
   */
  @ExcelProperty(value = "拣货单编号")
  private String orderPickingCode;

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
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 拣货人ID
   */
  @ExcelProperty(value = "拣货人ID")
  private Long userId;

  /**
   * 拣货人
   */
  @ExcelProperty(value = "拣货人")
  private String nickName;

  /**
   * 开始时间
   */
  @ExcelProperty(value = "开始时间")
  private Date startDate;

  /**
   * 结束时间
   */
  @ExcelProperty(value = "结束时间")
  private Date endDate;

  /**
   * 持续时间
   */
  @ExcelProperty(value = "持续时间")
  private String spanTime;

  /**
   * 订单数
   */
  @ExcelProperty(value = "订单数")
  private Long orderCount;

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
   * 打印批次ID
   */
  @ExcelProperty(value = "打印批次ID")
  private Long orderWaveId;

  /**
   * 打印批次编号
   */
  @ExcelProperty(value = "打印批次编号")
  private String orderWaveCode;

  /**
   * 拣货数量
   */
  @ExcelProperty(value = "拣货数量")
  private BigDecimal totalQuanityOrder;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String pickingStatus;

  /**
   * 子波次号
   */
  @ExcelProperty(value = "子波次号")
  private String subOrderWaveCode;

  /**
   * 成本金额
   */
  @ExcelProperty(value = "成本金额")
  private BigDecimal totalPurchaseAmount;

  /**
   * 销售总额
   */
  @ExcelProperty(value = "销售总额")
  private BigDecimal totalsaleAmount;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal totalWeight;

  /**
   * 拣货类型
   */
  @ExcelProperty(value = "拣货类型")
  private String pickingType;


}
