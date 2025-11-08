package com.yiruantong.system.domain.dataHandler;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 【请填写功能名称】对象 sys_layout
 *
 * @author ${author}
 * @date 2024-01-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_layout", autoResultMap = true)
public class SysLayout extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 首页Id
   */
  @TableId(value = "layout_id")
  private Long layoutId;

  /**
   * jsonData
   */
  private String jsonData;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除人ID
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
