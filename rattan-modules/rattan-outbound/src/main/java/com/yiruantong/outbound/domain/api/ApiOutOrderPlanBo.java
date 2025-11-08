package com.yiruantong.outbound.domain.api;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 出库计划业务对象 out_order
 *
 * @author YiRuanTong
 * @date 2024-06-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiOutOrderPlanBo {

  /**
   * 出库单明细
   */
  List<ApiOutOrderPlanDetailBo> detailList;

  /**
   * 出库计划id
   */
  private Long orderPlanId;

  /**
   * 出库计划编号
   */
  private String orderPlanCode;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户简称
   */
  private String clientShortName;

  /**
   * 联系人ID
   */
  private Long linkerId;

  /**
   * 联系人
   */
  private String linkerName;

  /**
   * 国家ID
   */
  private Long countryId;

  /**
   * 国家
   */
  private String countryName;

  /**
   * 国家全称
   */
  private String countryFullName;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 部门
   */
  private String deptName;

  /**
   * 计划类型
   */
  private String planType;

  /**
   * 计划出库时间
   */
  private Date planDate;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 计划要求
   */
  private String request;

  /**
   * 预计签单日期
   */
  private Date predictDate;

  /**
   * 预期金额
   */
  private BigDecimal predictAmount;

  /**
   * 可能性
   */
  private BigDecimal possibility;

  /**
   * 计划状态
   */
  private String planStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 省ID
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市ID
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区ID
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

  /**
   * 邮编
   */
  private String zip;

  /**
   * 电话
   */
  private String tel;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 发货人地址
   */
  private String shippingAddress;

  /**
   * 发货人
   */
  private String shippingName;

  /**
   * 地址
   */
  private String address;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;

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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 上传文件
   */
  private String uploadFile;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;

  /**
   * 仓库编号
   */
  private String storageCode;

}
