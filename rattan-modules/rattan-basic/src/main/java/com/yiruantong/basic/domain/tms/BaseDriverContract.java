package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 司机合同管理对象 base_driver_contract
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_driver_contract", autoResultMap = true)
public class BaseDriverContract extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 合同ID
   */
  @TableId(value = "contract_id")
  private Long contractId;

  /**
   * 合同编号
   */
  private String contractCode;

  /**
   * 真实姓名
   */
  private String consignorName;

  /**
   * 身份证号
   */
  private String idCardCode;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 现住地址
   */
  private String homeAddDress;

  /**
   * 车型
   */
  private String vehicleType;

  /**
   * 车辆品牌
   */
  private String vehicleBrand;

  /**
   * 车辆识别号
   */
  private String vehicleSignNo;

  /**
   * 银行
   */
  private String bank;

  /**
   * 开户行
   */
  private String bankName;

  /**
   * 开户账号
   */
  private String bankCode;

  /**
   * 审核状态
   */
  private Long auditing;

  /**
   * 签署状态
   */
  private String signingStatus;

  /**
   * 合同签署日期
   */
  private Date signingDate;

  /**
   * 现住城市
   */
  private String livingCity;

  /**
   * 开户支行
   */
  private String accountBranch;

  /**
   * 自定义号
   */
  private String customNo;

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
