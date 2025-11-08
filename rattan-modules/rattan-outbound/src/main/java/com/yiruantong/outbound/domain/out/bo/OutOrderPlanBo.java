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
import com.yiruantong.outbound.domain.out.OutOrderPlan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库计划单业务对象 out_order_plan
 *
 * @author YiRuanTong
 * @date 2025-01-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderPlan.class, reverseConvertGenerate = false)
public class OutOrderPlanBo extends BaseEntity {

  /**
   * 出库计划id
   */
  @NotNull(message = "出库计划id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPlanId;

  /**
   * 出库计划编号
   */
  @NotBlank(message = "出库计划编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderPlanCode;

  /**
   * 客户ID
   */
  @NotNull(message = "客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户简称
   */
  @NotBlank(message = "客户简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 联系人ID
   */
  @NotNull(message = "联系人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long linkerId;

  /**
   * 联系人
   */
  @NotBlank(message = "联系人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String linkerName;

  /**
   * 国家ID
   */
  @NotNull(message = "国家ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long countryId;

  /**
   * 国家
   */
  @NotBlank(message = "国家不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryName;

  /**
   * 国家全称
   */
  @NotBlank(message = "国家全称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryFullName;

  /**
   * 部门ID
   */
  @NotNull(message = "部门ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门
   */
  @NotBlank(message = "部门不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 计划类型
   */
  @NotBlank(message = "计划类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String planType;

  /**
   * 计划出库时间
   */
  @NotNull(message = "计划出库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date planDate;

  /**
   * 经手人ID
   */
  @NotNull(message = "经手人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 经手人
   */
  @NotBlank(message = "经手人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 计划要求
   */
  @NotBlank(message = "计划要求不能为空", groups = {AddGroup.class, EditGroup.class})
  private String request;

  /**
   * 预计签单日期
   */
  @NotNull(message = "预计签单日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date predictDate;

  /**
   * 预期金额
   */
  @NotNull(message = "预期金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal predictAmount;

  /**
   * 可能性
   */
  @NotNull(message = "可能性不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal possibility;

  /**
   * 计划状态
   */
  @NotBlank(message = "计划状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String planStatus;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

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
   * 省ID
   */
  @NotNull(message = "省ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long provinceId;

  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;

  /**
   * 市ID
   */
  @NotNull(message = "市ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;

  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 区ID
   */
  @NotNull(message = "区ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long regionId;

  /**
   * 区
   */
  @NotBlank(message = "区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;

  /**
   * 邮编
   */
  @NotBlank(message = "邮编不能为空", groups = {AddGroup.class, EditGroup.class})
  private String zip;

  /**
   * 电话
   */
  @NotBlank(message = "电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 发货人地址
   */
  @NotBlank(message = "发货人地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingAddress;

  /**
   * 发货人
   */
  @NotBlank(message = "发货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingName;

  /**
   * 地址
   */
  @NotBlank(message = "地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

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
   * 上传文件
   */
  @NotBlank(message = "上传文件不能为空", groups = {AddGroup.class, EditGroup.class})
  private String uploadFile;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQtyTotal;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;

}
