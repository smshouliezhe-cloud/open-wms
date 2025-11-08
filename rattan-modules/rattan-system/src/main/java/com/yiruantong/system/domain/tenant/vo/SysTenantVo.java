package com.yiruantong.system.domain.tenant.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.tenant.SysTenant;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 租户管理视图对象 sys_tenant
 *
 * @author YiRuanTong
 * @date 2024-12-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenant.class)
public class SysTenantVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @ExcelProperty(value = "id")
  private Long id;

  /**
   * 租户编号
   */
  @ExcelProperty(value = "租户编号")
  private String tenantId;

  /**
   * 联系人
   */
  @ExcelProperty(value = "联系人")
  private String contactUserName;

  /**
   * 联系电话
   */
  @ExcelProperty(value = "联系电话")
  private String contactPhone;

  /**
   * 企业名称
   */
  @ExcelProperty(value = "企业名称")
  private String companyName;

  /**
   * 统一社会信用代码
   */
  @ExcelProperty(value = "统一社会信用代码")
  private String licenseNumber;

  /**
   * 地址
   */
  @ExcelProperty(value = "地址")
  private String address;

  /**
   * 企业简介
   */
  @ExcelProperty(value = "企业简介")
  private String intro;

  /**
   * 域名
   */
  @ExcelProperty(value = "域名")
  private String domain;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 租户套餐编号
   */
  @ExcelProperty(value = "租户套餐编号")
  private Long packageId;

  /**
   * 过期时间
   */
  @ExcelProperty(value = "过期时间")
  private Date expireTime;

  /**
   * 用户数量（-1不限制）
   */
  @ExcelProperty(value = "用户数量", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "-=1不限制")
  private Long accountCount;

  /**
   * 租户状态
   */
  @ExcelProperty(value = "租户状态")
  private Byte status;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

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
   * 套餐名称
   */
  @ExcelProperty(value = "套餐名称")
  private String packageName;

  /**
   * 登录模板
   */
  @ExcelProperty(value = "登录模板")
  private String loginTemplate;

  /**
   * 主界面模板
   */
  @ExcelProperty(value = "主界面模板")
  private String mainTemplate;

  /**
   * 系统全称
   */
  @ExcelProperty(value = "系统全称")
  private String sysFullName;

  /**
   * 系统简称
   */
  @ExcelProperty(value = "系统简称")
  private String sysShortName;

  /**
   * 短logo
   */
  @ExcelProperty(value = "短logo")
  private String logoShort;

  /**
   * 长logo
   */
  @ExcelProperty(value = "长logo")
  private String logoLong;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;


}
