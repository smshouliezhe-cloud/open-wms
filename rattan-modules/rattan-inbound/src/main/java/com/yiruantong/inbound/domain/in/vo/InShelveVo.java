package com.yiruantong.inbound.domain.in.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inbound.domain.in.InShelve;
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
 * 商品上架视图对象 in_shelve
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InShelve.class)
public class InShelveVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 上架ID
   */
  @ExcelProperty(value = "上架ID")
  private Long shelveId;

  /**
   * 上架编号
   */
  @ExcelProperty(value = "上架编号")
  private String shelveCode;

  /**
   * 上架类型
   */
  @ExcelProperty(value = "上架类型")
  private String shelveType;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 入库单ID
   */
  @ExcelProperty(value = "入库单ID")
  private Long enterId;

  /**
   * 入库单编号
   */
  @ExcelProperty(value = "入库单编号")
  private String enterCode;

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
   * 上架人ID
   */
  @ExcelProperty(value = "上架人ID")
  private Long userId;

  /**
   * 上架人
   */
  @ExcelProperty(value = "上架人")
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
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalQuantity;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Byte auditing;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

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
   * 上架状态
   */
  @ExcelProperty(value = "上架状态")
  private String shelveStatus;

  /**
   * 待商家数量
   */
  @ExcelProperty(value = "待商家数量")
  private BigDecimal totalOnshelveQuantity;

  /**
   * 已上架数量
   */
  @ExcelProperty(value = "已上架数量")
  private BigDecimal totalShelvedQuantity;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 采购商ID
   */
  @ExcelProperty(value = "采购商ID")
  private Long providerId;

  /**
   * 采购商编号
   */
  @ExcelProperty(value = "采购商编号")
  private String providerCode;

  /**
   * 采购商名称
   */
  @ExcelProperty(value = "采购商名称")
  private String providerShortName;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNos;

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
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;

  /**
   * 预到货单ID
   */
  @ExcelProperty(value = "预到货单ID")
  private Long orderId;

  /**
   * 采购单编号
   */
  @ExcelProperty(value = "采购单编号")
  private String orderCode;

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
   * 接收任务人员
   */
  @ExcelProperty(value = "接收任务人员")
  private String receiveTaskPeople;

  /**
   * 入库类型
   */
  @ExcelProperty(value = "入库类型")
  private String scanInType;

  /**
   * 跟踪单号
   */
  @ExcelProperty(value = "跟踪单号")
  private String trackingNumber;

  /**
   * LPN编号
   */
  @ExcelProperty(value = "LPN编号")
  private String lpnCode;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNos;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
