package com.yiruantong.system.domain.permission.dto;

import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.tenant.SysTenant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 保存账套后返回数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode()
public class SaveTenantResultDto {
  private SysUser sysUser;
  private SysDept sysDept;
  private SysTenant sysTenant;
}
