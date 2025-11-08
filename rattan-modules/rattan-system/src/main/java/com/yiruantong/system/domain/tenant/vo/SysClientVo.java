package com.yiruantong.system.domain.tenant.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.tenant.SysClient;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统授权视图对象 sys_client
 *
 * @author YiRuanTong
 * @date 2024-06-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysClient.class)
public class SysClientVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @ExcelProperty(value = "id")
  private Long id;

  /**
   * 客户端id
   */
  @ExcelProperty(value = "客户端id")
  private String clientId;

  /**
   * 客户端key
   */
  @ExcelProperty(value = "客户端key")
  private String clientKey;

  /**
   * 客户端秘钥
   */
  @ExcelProperty(value = "客户端秘钥")
  private String clientSecret;

  /**
   * 授权类型
   */
  @ExcelProperty(value = "授权类型")
  private String grantType;

  /**
   * 设备类型
   */
  @ExcelProperty(value = "设备类型")
  private String deviceType;

  /**
   * token活跃超时时间
   */
  @ExcelProperty(value = "token活跃超时时间")
  private Long activeTimeout;

  /**
   * token固定超时
   */
  @ExcelProperty(value = "token固定超时")
  private Long timeout;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private Byte status;

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
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

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
