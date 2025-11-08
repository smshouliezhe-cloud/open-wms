package com.yiruantong.basic.controller.client;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.client.BaseClientTemplate;
import com.yiruantong.basic.domain.client.vo.BaseClientTemplateVo;
import com.yiruantong.basic.domain.client.bo.BaseClientTemplateBo;
import com.yiruantong.basic.mapper.client.BaseClientTemplateMapper;
import com.yiruantong.basic.service.client.IBaseClientTemplateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户运价模板
 *
 * @author YRT
 * @date 2024-04-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/client/clientTemplate")
public class BaseClientTemplateController extends AbstractController<BaseClientTemplateMapper, BaseClientTemplate, BaseClientTemplateVo, BaseClientTemplateBo> {
}
