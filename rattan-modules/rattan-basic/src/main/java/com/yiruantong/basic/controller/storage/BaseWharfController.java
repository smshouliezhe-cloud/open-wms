package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.storage.BaseWharf;
import com.yiruantong.basic.domain.storage.vo.BaseWharfVo;
import com.yiruantong.basic.domain.storage.bo.BaseWharfBo;
import com.yiruantong.basic.mapper.storage.BaseWharfMapper;
import com.yiruantong.basic.service.storage.IBaseWharfService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 码头管理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/wharf")
public class BaseWharfController extends AbstractController<BaseWharfMapper, BaseWharf, BaseWharfVo, BaseWharfBo> {
}
