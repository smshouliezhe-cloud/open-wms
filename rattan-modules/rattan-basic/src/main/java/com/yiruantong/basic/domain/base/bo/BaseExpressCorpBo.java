package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseExpressCorp;
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
 * 快递管理业务对象 base_express_corp
 *
 * @author YRT
 * @date 2023-11-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseExpressCorp.class, reverseConvertGenerate = false)
public class BaseExpressCorpBo extends BaseEntity {

  /**
   * 快递ID
   */
  @NotNull(message = "快递ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressCorpId;

  /**
   * 快递编号
   */
  @NotBlank(message = "快递编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpCode;

  /**
   * 快递名称
   */
  @NotBlank(message = "快递名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpName;

  /**
   * 快递英文名
   */
  @NotBlank(message = "快递英文名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpEnName;

  /**
   * 快递类型
   */
  @NotNull(message = "快递类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressCorpType;

  /**
   * 拼音码
   */
  @NotBlank(message = "拼音码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pinYinCode;

  /**
   * 服务电话
   */
  @NotBlank(message = "服务电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String servicePhone;

  /**
   * 取货站点
   */
  @NotBlank(message = "取货站点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pickSite;

  /**
   * 联系人
   */
  @NotBlank(message = "联系人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String linker;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 电话
   */
  @NotBlank(message = "电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

  /**
   * email
   */
  @NotBlank(message = "email不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * QQ
   */
  @NotBlank(message = "QQ不能为空", groups = {AddGroup.class, EditGroup.class})
  private String qq;

  /**
   * 订单详情模板ID
   */
  @NotNull(message = "订单详情模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailTemplateId;

  /**
   * 订单部分详情模板ID
   */
  @NotNull(message = "订单部分详情模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPartialDetailTemplateId;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 发货后导入快递单
   */
  @NotNull(message = "发货后导入快递单不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isAfterImportExpress;

  /**
   * 按件数打印
   */
  @NotNull(message = "按件数打印不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isExpressCorp;

  /**
   * 显示提交按钮
   */
  @NotNull(message = "显示提交按钮不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isShowSubmit;

  /**
   * 按件数打印并提交
   */
  @NotNull(message = "按件数打印并提交不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isExpressCorpSubmit;

  /**
   * 自动生成装箱单
   */
  @NotNull(message = "自动生成装箱单不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isCreatePackage;

  /**
   * 运费计算模板ID
   */
  @NotNull(message = "运费计算模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long freightTemplateId;

  /**
   * 运费计算模板
   */
  @NotBlank(message = "运费计算模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String freightTemplateName;

  /**
   * 返回标志
   */
  @NotBlank(message = "返回标志不能为空", groups = {AddGroup.class, EditGroup.class})
  private String returnSign;

  /**
   * 面单模板ID
   */
  @NotNull(message = "面单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long faceBillTemplateId;

  /**
   * 面单模板
   */
  @NotBlank(message = "面单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String faceBillTemplate;

  /**
   * 附属面单模板ID
   */
  @NotNull(message = "附属面单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long subFaceBillTemplateId;

  /**
   * 附属面单模板
   */
  @NotBlank(message = "附属面单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subFaceBillTemplate;

  /**
   * 快递单模板ID
   */
  @NotNull(message = "快递单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressBillTemplateId;

  /**
   * 快递单模板
   */
  @NotBlank(message = "快递单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressBillTemplate;

  /**
   * 日最小送货量
   */
  @NotNull(message = "日最小送货量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long minQuantity;

  /**
   * 日最大送货量
   */
  @NotNull(message = "日最大送货量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long maxQuantity;

  /**
   * 从接口获得跟踪单号
   */
  @NotNull(message = "从接口获得跟踪单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long interfaceCode;

  /**
   * 站点网址
   */
  @NotBlank(message = "站点网址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String siteUrl;

  /**
   * 声音地址
   */
  @NotBlank(message = "声音地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String soundUrl;

  /**
   * 最小申报价值
   */
  @NotNull(message = "最小申报价值不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal minDeclareValue;

  /**
   * 最大申报价值
   */
  @NotNull(message = "最大申报价值不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal maxDeclareValue;

  /**
   * 保险类型
   */
  @NotBlank(message = "保险类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String insuranceType;

  /**
   * 订单详情模板
   */
  @NotBlank(message = "订单详情模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderDetailTemplate;

  /**
   * 关联接口账号ID
   */
  @NotBlank(message = "关联接口账号ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String interfaceAccountId;

  /**
   * 关联接口账号
   */
  @NotBlank(message = "关联接口账号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String interfaceAccountName;

  /**
   * 订单部分详情模板
   */
  @NotBlank(message = "订单部分详情模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderPartialDetailTemplate;

  /**
   * 销售订单模板详情ID
   */
  @NotNull(message = "销售订单模板详情ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long saleOrderPrintTemplateId;

  /**
   * 销售订单模板详情
   */
  @NotBlank(message = "销售订单模板详情不能为空", groups = {AddGroup.class, EditGroup.class})
  private String saleOrderPrintTemplate;

  /**
   * 服务类型
   */
  @NotBlank(message = "服务类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String serviceType;

  /**
   * 装箱清单模板ID
   */
  @NotNull(message = "装箱清单模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long packingDetailTemplateId;

  /**
   * 装箱清单模板
   */
  @NotBlank(message = "装箱清单模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packingDetailTemplate;

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


}
