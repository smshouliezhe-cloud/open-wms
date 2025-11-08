package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.storage.BaseShelveRegular;
import com.yiruantong.basic.domain.storage.vo.BaseShelveRegularVo;
import com.yiruantong.basic.domain.storage.bo.BaseShelveRegularBo;
import com.yiruantong.basic.mapper.storage.BaseShelveRegularMapper;
import com.yiruantong.basic.service.storage.IBaseShelveRegularService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品上架策略
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/shelveRegular")
public class BaseShelveRegularController extends AbstractController<BaseShelveRegularMapper, BaseShelveRegular, BaseShelveRegularVo, BaseShelveRegularBo> {
}
