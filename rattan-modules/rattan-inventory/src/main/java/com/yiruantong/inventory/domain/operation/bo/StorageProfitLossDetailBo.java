package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 盈亏单明细业务对象 storage_profit_loss_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageProfitLossDetail.class, reverseConvertGenerate = false)
public class StorageProfitLossDetailBo extends BaseEntity {

  /**
   * 报损明细ID
   */
  @NotNull(message = "报损明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long profitLossDetailId;

  /**
   * 报损单ID
   */
  @NotNull(message = "报损单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long profitLossId;

  /**
   * 盘点单ID
   */
  @NotNull(message = "盘点单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long checkId;

  /**
   * 盘点明细ID
   */
  @NotNull(message = "盘点明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long checkDetailId;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 关联码
   */
  @NotBlank(message = "关联码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String relationCode;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 小单位
   */
  @NotBlank(message = "小单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String smallUnit;

  /**
   * 大单位
   */
  @NotBlank(message = "大单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigUnit;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  @NotBlank(message = "单位关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unitConvertText;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 账面库存量
   */
  @NotNull(message = "账面库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal productStorage;

  /**
   * 平均成本价
   */
  @NotNull(message = "平均成本价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 账面成本额
   */
  @NotNull(message = "账面成本额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

  /**
   * 盘点数量
   */
  @NotNull(message = "盘点数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkQuantity;

  /**
   * 盘盈数量
   */
  @NotNull(message = "盘盈数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal profitQuantity;

  /**
   * 盘盈金额
   */
  @NotNull(message = "盘盈金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal profitAmount;

  /**
   * 盘亏数量
   */
  @NotNull(message = "盘亏数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal lossQuantity;

  /**
   * 盘亏金额
   */
  @NotNull(message = "盘亏金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal lossAmount;

  /**
   * 缺货数量
   */
  @NotNull(message = "缺货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal lackStorage;

  /**
   * 初盘差异(生成盈亏单的时候计算出来的？)
   */
  @NotNull(message = "初盘差异(生成盈亏单的时候计算出来的？)不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkDiff;

  /**
   * 复盘数量
   */
  @NotNull(message = "复盘数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal reCheckQuantity;

  /**
   * 复盘差异
   */
  @NotNull(message = "复盘差异不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal reCheckDiff;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 生产时间
   */
  @NotNull(message = "生产时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 限制日期
   */
  @NotNull(message = "限制日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date limitDate;

  /**
   * 单位毛重
   */
  @NotNull(message = "单位毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 毛重小计
   */
  @NotNull(message = "毛重小计不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 盘盈重量
   */
  @NotNull(message = "盘盈重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal profitWeight;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * SN唯一码
   */
  @NotBlank(message = "SN唯一码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 单位净重
   */
  @NotNull(message = "单位净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @NotNull(message = "小计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotBlank(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @NotBlank(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailId;

  /**
   * 库存Id
   */
  @NotNull(message = "库存Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

  /**
   * 项目号
   */
  @NotBlank(message = "项目号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String projectCode;

  /**
   * 箱号
   */
  @NotBlank(message = "箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String caseNumber;


}
