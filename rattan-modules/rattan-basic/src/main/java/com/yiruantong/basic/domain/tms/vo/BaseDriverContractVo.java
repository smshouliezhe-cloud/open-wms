package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseDriverContract;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 司机合同管理视图对象 base_driver_contract
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseDriverContract.class)
public class BaseDriverContractVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 合同ID
   */
  @ExcelProperty(value = "合同ID")
  private Long contractId;

  /**
   * 合同编号
   */
  @ExcelProperty(value = "合同编号")
  private String contractCode;

  /**
   * 真实姓名
   */
  @ExcelProperty(value = "真实姓名")
  private String consignorName;

  /**
   * 身份证号
   */
  @ExcelProperty(value = "身份证号")
  private String idCardCode;

  /**
   * 手机号
   */
  @ExcelProperty(value = "手机号")
  private String mobile;

  /**
   * 现住地址
   */
  @ExcelProperty(value = "现住地址")
  private String homeAddDress;

  /**
   * 车型
   */
  @ExcelProperty(value = "车型")
  private String vehicleType;

  /**
   * 车辆品牌
   */
  @ExcelProperty(value = "车辆品牌")
  private String vehicleBrand;

  /**
   * 车辆识别号
   */
  @ExcelProperty(value = "车辆识别号")
  private String vehicleSignNo;

  /**
   * 银行
   */
  @ExcelProperty(value = "银行")
  private String bank;

  /**
   * 开户行
   */
  @ExcelProperty(value = "开户行")
  private String bankName;

  /**
   * 开户账号
   */
  @ExcelProperty(value = "开户账号")
  private String bankCode;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Long auditing;

  /**
   * 签署状态
   */
  @ExcelProperty(value = "签署状态")
  private String signingStatus;

  /**
   * 合同签署日期
   */
  @ExcelProperty(value = "合同签署日期")
  private Date signingDate;

  /**
   * 现住城市
   */
  @ExcelProperty(value = "现住城市")
  private String livingCity;

  /**
   * 开户支行
   */
  @ExcelProperty(value = "开户支行")
  private String accountBranch;

  /**
   * 自定义号
   */
  @ExcelProperty(value = "自定义号")
  private String customNo;

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


}
