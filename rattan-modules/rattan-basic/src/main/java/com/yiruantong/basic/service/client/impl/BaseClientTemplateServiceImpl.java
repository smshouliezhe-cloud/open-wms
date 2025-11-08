package com.yiruantong.basic.service.client.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.client.bo.BaseClientTemplateBo;
import com.yiruantong.basic.domain.client.vo.BaseClientTemplateVo;
import com.yiruantong.basic.domain.client.BaseClientTemplate;
import com.yiruantong.basic.mapper.client.BaseClientTemplateMapper;
import com.yiruantong.basic.service.client.IBaseClientTemplateService;

/**
 * 客户运价模板Service业务层处理
 *
 * @author YRT
 * @date 2024-04-12
 */
@RequiredArgsConstructor
@Service
public class BaseClientTemplateServiceImpl extends ServiceImplPlus<BaseClientTemplateMapper, BaseClientTemplate, BaseClientTemplateVo, BaseClientTemplateBo> implements IBaseClientTemplateService {
}
