package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.product.BaseProviderContract;
import com.yiruantong.basic.domain.product.vo.BaseProviderContractVo;
import com.yiruantong.basic.domain.product.bo.BaseProviderContractBo;
import com.yiruantong.basic.mapper.product.BaseProviderContractMapper;
import com.yiruantong.basic.service.product.IBaseProviderContractService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商合同管理
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/providerContract")
public class BaseProviderContractController extends AbstractController<BaseProviderContractMapper, BaseProviderContract, BaseProviderContractVo, BaseProviderContractBo> {
}
