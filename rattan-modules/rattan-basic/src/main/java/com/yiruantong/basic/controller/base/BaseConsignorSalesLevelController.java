package com.yiruantong.basic.controller.base;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.base.BaseConsignorSalesLevel;
import com.yiruantong.basic.domain.base.vo.BaseConsignorSalesLevelVo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorSalesLevelBo;
import com.yiruantong.basic.mapper.base.BaseConsignorSalesLevelMapper;
import com.yiruantong.basic.service.base.IBaseConsignorSalesLevelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门店销售等级设置
 *
 * @author YRT
 * @date 2025-01-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/consignorSalesLevel")
public class BaseConsignorSalesLevelController extends AbstractController<BaseConsignorSalesLevelMapper, BaseConsignorSalesLevel, BaseConsignorSalesLevelVo, BaseConsignorSalesLevelBo> {
}
