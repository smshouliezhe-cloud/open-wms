package com.yiruantong.system.domain.core.vo;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.tenant.SysTenantMenu;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础菜单权限视图对象 sys_menu
 *
 * @author YiRuanTong
 */
@Data
@AutoMappers({
  @AutoMapper(target = SysMenu.class),
  @AutoMapper(target = SysTenantMenu.class)
})
public class SysMenuBaseVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 菜单ID
   */
  private Long menuId;

  /**
   * 菜单名称
   */
  private String menuName;

  /**
   * 父菜单ID
   */
  private Long parentId;

  /**
   * 显示顺序
   */
  private Integer orderNum;
}
