package com.yiruantong.generator.controller;

import com.yiruantong.generator.domain.GenTableColumn;
import com.yiruantong.generator.domain.bo.GenTableColumnBo;
import com.yiruantong.generator.domain.vo.GenTableColumnVo;
import com.yiruantong.generator.mapper.GenTableColumnMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码生成业务字段
 *
 * @author YRT
 * @date 2023-08-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/generator/tableColumn")
public class GenTableColumnController
  extends AbstractController<
  GenTableColumnMapper, GenTableColumn, GenTableColumnVo, GenTableColumnBo> {
}
