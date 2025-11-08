package com.yiruantong.generator.service.impl;

import com.yiruantong.generator.domain.bo.GenTableColumnBo;
import com.yiruantong.generator.domain.vo.GenTableColumnVo;
import com.yiruantong.generator.mapper.GenTableColumnMapper;
import com.yiruantong.generator.service.IGenTableColumnService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.generator.domain.GenTableColumn;

/**
 * 代码生成业务字段Service业务层处理
 *
 * @author YRT
 * @date 2023-08-12
 */
@RequiredArgsConstructor
@Service
public class GenTableColumnServiceImpl extends ServiceImplPlus<GenTableColumnMapper, GenTableColumn, GenTableColumnVo, GenTableColumnBo> implements IGenTableColumnService {
}
