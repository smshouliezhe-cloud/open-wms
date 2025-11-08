package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysParamValue;
import com.yiruantong.system.domain.dataHandler.bo.SysParamValueBo;
import com.yiruantong.system.domain.dataHandler.vo.SysParamValueVo;
import com.yiruantong.system.mapper.dataHandler.SysParamValueMapper;
import com.yiruantong.system.service.dataHandler.ISysParamValueService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下拉框值设置
 *
 * @author YRT
 * @date 2023-07-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/paramValue")
public class SysParamValueController extends AbstractController<SysParamValueMapper, SysParamValue, SysParamValueVo, SysParamValueBo> {
  private final ISysParamValueService sysParamValueService;
}
