package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysExportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysExportColumnBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportColumnVo;
import com.yiruantong.system.mapper.dataHandler.SysExportColumnMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出字段
 *
 * @author YRT
 * @date 2023-08-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/exportColumn")
public class SysExportColumnController extends AbstractController<SysExportColumnMapper, SysExportColumn, SysExportColumnVo, SysExportColumnBo> {
}
