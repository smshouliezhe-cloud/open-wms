package com.yiruantong.system.domain.decorate;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 页面装修对象 sys_page
 *
 * @author YRT
 * @date 2024-09-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_page", autoResultMap = true)
public class SysPage extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 页面装修ID
   */
  @TableId(value = "page_id")
  private Long pageId;

  /**
   * 页面名称
   */
  private String pageName;

  /**
   * 页面类型
   */
  private String pageType;

  /**
   * 页面编码
   */
  private String pageCode;

  /**
   * jsonData
   */
  private String jsonData;

  /**
   * 页面状态
   */
  private Integer enable;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 父级id
   */
  private Long parentId;

  /**
   * 完整父级ID
   */
  private String fullId;

  /**
   * 父级名称
   */
  private String fullName;

  /**
   * 排序号
   */
  private Long orderNum;


}
