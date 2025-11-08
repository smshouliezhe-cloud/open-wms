package com.yiruantong.basic.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 快递管理对象 base_express_corp
 *
 * @author YRT
 * @date 2023-11-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_express_corp", autoResultMap = true)
public class BaseExpressCorp extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 快递ID
   */
  @TableId(value = "express_corp_id")
  private Long expressCorpId;

  /**
   * 快递编号
   */
  private String expressCorpCode;

  /**
   * 快递名称
   */
  private String expressCorpName;

  /**
   * 快递英文名
   */
  private String expressCorpEnName;

  /**
   * 快递类型
   */
  private Long expressCorpType;

  /**
   * 拼音码
   */
  private String pinYinCode;

  /**
   * 服务电话
   */
  private String servicePhone;

  /**
   * 取货站点
   */
  private String pickSite;

  /**
   * 联系人
   */
  private String linker;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 电话
   */
  private String tel;

  /**
   * email
   */
  private String email;

  /**
   * QQ
   */
  private String qq;

  /**
   * 订单详情模板ID
   */
  private Long orderDetailTemplateId;

  /**
   * 订单部分详情模板ID
   */
  private Long orderPartialDetailTemplateId;

  /**
   * 是否可用
   */
  private Long enable;

  /**
   * 发货后导入快递单
   */
  private Byte isAfterImportExpress;

  /**
   * 按件数打印
   */
  private Byte isExpressCorp;

  /**
   * 显示提交按钮
   */
  private Byte isShowSubmit;

  /**
   * 按件数打印并提交
   */
  private Byte isExpressCorpSubmit;

  /**
   * 自动生成装箱单
   */
  private Byte isCreatePackage;

  /**
   * 运费计算模板ID
   */
  private Long freightTemplateId;

  /**
   * 运费计算模板
   */
  private String freightTemplateName;

  /**
   * 返回标志
   */
  private String returnSign;

  /**
   * 面单模板ID
   */
  private Long faceBillTemplateId;

  /**
   * 面单模板
   */
  private String faceBillTemplate;

  /**
   * 附属面单模板ID
   */
  private Long subFaceBillTemplateId;

  /**
   * 附属面单模板
   */
  private String subFaceBillTemplate;

  /**
   * 快递单模板ID
   */
  private Long expressBillTemplateId;

  /**
   * 快递单模板
   */
  private String expressBillTemplate;

  /**
   * 日最小送货量
   */
  private Long minQuantity;

  /**
   * 日最大送货量
   */
  private Long maxQuantity;

  /**
   * 从接口获得跟踪单号
   */
  private Long interfaceCode;

  /**
   * 站点网址
   */
  private String siteUrl;

  /**
   * 声音地址
   */
  private String soundUrl;

  /**
   * 最小申报价值
   */
  private BigDecimal minDeclareValue;

  /**
   * 最大申报价值
   */
  private BigDecimal maxDeclareValue;

  /**
   * 保险类型
   */
  private String insuranceType;

  /**
   * 订单详情模板
   */
  private String orderDetailTemplate;

  /**
   * 关联接口账号ID
   */
  private String interfaceAccountId;

  /**
   * 关联接口账号
   */
  private String interfaceAccountName;

  /**
   * 订单部分详情模板
   */
  private String orderPartialDetailTemplate;

  /**
   * 销售订单模板详情ID
   */
  private Long saleOrderPrintTemplateId;

  /**
   * 销售订单模板详情
   */
  private String saleOrderPrintTemplate;

  /**
   * 服务类型
   */
  private String serviceType;

  /**
   * 装箱清单模板ID
   */
  private Long packingDetailTemplateId;

  /**
   * 装箱清单模板
   */
  private String packingDetailTemplate;

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


}
