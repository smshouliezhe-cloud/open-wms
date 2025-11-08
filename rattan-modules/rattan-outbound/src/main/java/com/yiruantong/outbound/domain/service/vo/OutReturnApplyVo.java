package com.yiruantong.outbound.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.service.OutReturnApply;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库退货申请单视图对象 out_return_apply
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutReturnApply.class)
public class OutReturnApplyVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户售后ID
   */
  @ExcelProperty(value = "客户售后ID")
  private Long returnApplyId;

  /**
   * 客户售后编号
   */
  @ExcelProperty(value = "客户售后编号")
  private String returnApplyCode;

  /**
   * 出库iD
   */
  @ExcelProperty(value = "出库iD")
  private Long orderId;

  /**
   * 出库编号
   */
  @ExcelProperty(value = "出库编号")
  private String orderCode;

  /**
   * 入库仓库ID
   */
  @ExcelProperty(value = "入库仓库ID")
  private Long storageId;

  /**
   * 入库仓库名称
   */
  @ExcelProperty(value = "入库仓库名称")
  private String storageName;

  /**
   * 经手人ID
   */
  @ExcelProperty(value = "经手人ID")
  private Long userId;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 部门ID
   */
  @ExcelProperty(value = "部门ID")
  private Long deptId;

  /**
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 入库时间
   */
  @ExcelProperty(value = "入库时间")
  private Date applyDate;

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
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

  /**
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalQuantity;

  /**
   * 退款数量
   */
  @ExcelProperty(value = "退款数量")
  private Long totalReturnQuantity;

  /**
   * 退款金额
   */
  @ExcelProperty(value = "退款金额")
  private BigDecimal totalSaleAmount;

  /**
   * 退货金额
   */
  @ExcelProperty(value = "退货金额")
  private BigDecimal totalReturnAmount;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 合计价税
   */
  @ExcelProperty(value = "合计价税")
  private BigDecimal totalRateAmount;

  /**
   * 申请状态
   */
  @ExcelProperty(value = "申请状态")
  private String applyStatus;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Long auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

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
   * 司机姓名
   */
  @ExcelProperty(value = "司机姓名")
  private String driveName;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 发货人
   */
  @ExcelProperty(value = "发货人")
  private String shippingName;

  /**
   * 发货人地址
   */
  @ExcelProperty(value = "发货人地址")
  private String shippingAddress;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

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
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;


}
