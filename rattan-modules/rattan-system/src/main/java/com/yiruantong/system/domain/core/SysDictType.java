package com.yiruantong.system.domain.core;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表 sys_dict_type
 *
 * @author YiRuanTong
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dict_type", autoResultMap = true)
public class SysDictType extends TenantEntity {

  /**
   * 字典主键
   */
  @TableId(value = "dict_id")
  private Long dictId;

  /**
   * 字典名称
   */
  private String dictName;

  /**
   * 字典类型
   */
  private String dictType;

  /**
   * 备注
   */
  private String remark;

}
