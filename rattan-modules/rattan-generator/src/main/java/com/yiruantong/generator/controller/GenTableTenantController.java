package com.yiruantong.generator.controller;

import com.yiruantong.generator.domain.GenTableTenant;
import com.yiruantong.generator.domain.bo.GenTableTenantBo;
import com.yiruantong.generator.domain.vo.GenTableTenantVo;
import com.yiruantong.generator.mapper.GenTableTenantMapper;
import com.yiruantong.generator.service.IGenTableTenantService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 生成表租户数据
 *
 * @author 谢天保
 * @date 2023-06-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/generator/tableTenant")
public class GenTableTenantController extends AbstractController<GenTableTenantMapper, GenTableTenant, GenTableTenantVo, GenTableTenantBo> {
  private final IGenTableTenantService genTableTenantService;

  /**
   * 保存
   * @param genTableTenantBo 表ID
   */
  @PostMapping(value = "/saveui")
  public R<Map<String, Object>> saveui(@RequestBody GenTableTenantBo genTableTenantBo) {
	  return genTableTenantService.saveui(genTableTenantBo);
  }
}
