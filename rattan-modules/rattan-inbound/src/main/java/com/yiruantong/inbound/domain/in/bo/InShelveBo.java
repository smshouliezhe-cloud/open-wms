package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 商品上架业务对象 in_shelve
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InShelve.class, reverseConvertGenerate = false)
public class InShelveBo extends BaseEntity {

  /**
   * 上架ID
   */
  @NotNull(message = "上架ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long shelveId;

  /**
   * 上架编号
   */
  @NotBlank(message = "上架编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveCode;

  /**
   * 上架类型
   */
  @NotBlank(message = "上架类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveType;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 入库单ID
   */
  @NotNull(message = "入库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enterId;

  /**
   * 入库单编号
   */
  @NotBlank(message = "入库单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String enterCode;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 上架人ID
   */
  @NotNull(message = "上架人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 上架人
   */
  @NotBlank(message = "上架人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 开始时间
   */
  @NotNull(message = "开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date startDate;

  /**
   * 结束时间
   */
  @NotNull(message = "结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endDate;

  /**
   * 持续时间
   */
  @NotBlank(message = "持续时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private String spanTime;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantity;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  @NotNull(message = "分拣日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date sortingDate;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 上架状态
   */
  @NotBlank(message = "上架状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveStatus;

  /**
   * 待商家数量
   */
  @NotNull(message = "待商家数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOnshelveQuantity;

  /**
   * 已上架数量
   */
  @NotNull(message = "已上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalShelvedQuantity;

  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 采购商ID
   */
  @NotNull(message = "采购商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 采购商编号
   */
  @NotBlank(message = "采购商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 采购商名称
   */
  @NotBlank(message = "采购商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNos;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

  /**
   * 预到货单ID
   */
  @NotNull(message = "预到货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 采购单编号
   */
  @NotBlank(message = "采购单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源ID
   */
  @NotBlank(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 接收任务人员
   */
  private String receiveTaskPeople;

  /**
   * 入库类型
   */
  @NotBlank(message = "入库类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String scanInType;

  /**
   * 跟踪单号
   */
  @NotBlank(message = "跟踪单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trackingNumber;

  /**
   * LPN编号
   */
  @NotBlank(message = "LPN编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lpnCode;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sealNos;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
