package com.yiruantong.system.domain.core.vo;

import lombok.Data;
import com.yiruantong.system.domain.permission.vo.SysUserVo;

/**
 * 用户个人信息
 *
 * @author YiRuanTong
 */
@Data
public class ProfileVo {

  /**
   * 用户信息
   */
  private SysUserVo user;

  /**
   * 用户所属角色组
   */
  private String roleGroup;

  /**
   * 用户所属岗位组
   */
  private String postGroup;


}
