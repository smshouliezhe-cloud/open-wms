package com.yiruantong.web.domain.vo;

import com.yiruantong.system.domain.tenant.vo.SysTenantVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 租户列表
 *
 * @author YiRuanTong
 */
@Data
@AutoMapper(target = SysTenantVo.class)
public class TenantListVo {

  private String tenantId;

  private String companyName;

  private String domain;

}
