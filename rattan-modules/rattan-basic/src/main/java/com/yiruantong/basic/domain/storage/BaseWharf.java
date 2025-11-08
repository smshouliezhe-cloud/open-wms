package com.yiruantong.basic.domain.storage;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 码头管理对象 base_wharf
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_wharf", autoResultMap = true)
public class BaseWharf extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 码头ID
   */
  private Long wharfId;

  /**
   * 码头编号
   */
  private String wharfCode;

  /**
   * 码头名称
   */
  private String wharfName;

  /**
   * 码头地址
   */
  private String wharfAddress;

  /**
   * 公司名称
   */
  private String corpName;

  /**
   * 手机
   */
  private String mobile;

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
