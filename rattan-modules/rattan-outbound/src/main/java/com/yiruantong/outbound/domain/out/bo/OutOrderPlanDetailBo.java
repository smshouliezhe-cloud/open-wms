package com.yiruantong.outbound.domain.out.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库计划单明细业务对象 out_order_plan_detail
 *
 * @author YiRuanTong
 * @date 2025-01-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderPlanDetail.class, reverseConvertGenerate = false)
public class OutOrderPlanDetailBo extends BaseEntity {

  /**
   * 出库计划单明细ID
   */
  @NotNull(message = "出库计划单明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPlanDetailId;

  /**
   * 出库计划单ID
   */
  @NotNull(message = "出库计划单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPlanId;

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
   * 数量
   */
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantity;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

  /**
   * 单位换算
   */
  @NotBlank(message = "单位换算不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unitConvertText;

  /**
   * 成本单价
   */
  @NotNull(message = "成本单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 售价
   */
  @NotNull(message = "售价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal salePrice;

  /**
   * 金额
   */
  @NotNull(message = "金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal saleAmount;

  /**
   * 已出库数量
   */
  @NotNull(message = "已出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outQuantity;

  /**
   * 未出库数量
   */
  @NotNull(message = "未出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal surplusQuantity;

  /**
   * 商品规格
   */
  @NotBlank(message = "商品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 单位毛重
   */
  @NotNull(message = "单位毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @NotNull(message = "小计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库编号
   */
  @NotNull(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 有效库存
   */
  @NotNull(message = "有效库存不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long validQuantity;

  /**
   * 发件人省
   */
  @NotBlank(message = "发件人省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String senderprov;

  /**
   * 发件人市
   */
  @NotBlank(message = "发件人市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sendercity;

  /**
   * 发件人区
   */
  @NotBlank(message = "发件人区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sendercounty;

  /**
   * 发件人地址
   */
  @NotBlank(message = "发件人地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String senderaddress;

  /**
   * 发件公司名
   */
  @NotBlank(message = "发件公司名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sendercompany;

  /**
   * 发件手机号
   */
  @NotBlank(message = "发件手机号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sendermobile;

  /**
   * 原产地
   */
  @NotBlank(message = "原产地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String originPlace;

  /**
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * SN号
   */
  @NotBlank(message = "SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

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
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQty;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 含税单价
   */
  @NotNull(message = "含税单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @NotNull(message = "价税合计不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rateAmount;

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

  /**
   * 货主Id
   */
  @NotNull(message = "货主Id不能为空", groups = {AddGroup.class, EditGroup.class})
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


}
