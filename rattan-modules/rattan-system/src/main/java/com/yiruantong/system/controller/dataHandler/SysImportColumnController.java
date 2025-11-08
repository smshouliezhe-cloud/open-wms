package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysImportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysImportColumnBo;
import com.yiruantong.system.domain.dataHandler.vo.SysImportColumnVo;
import com.yiruantong.system.mapper.dataHandler.SysImportColumnMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导入字段
 *
 * @author YRT
 * @date 2023-08-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/importColumn")
public class SysImportColumnController extends AbstractController<SysImportColumnMapper, SysImportColumn, SysImportColumnVo, SysImportColumnBo> {
}
