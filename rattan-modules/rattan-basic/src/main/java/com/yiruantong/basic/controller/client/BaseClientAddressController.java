package com.yiruantong.basic.controller.client;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.domain.client.vo.BaseClientAddressVo;
import com.yiruantong.basic.domain.client.bo.BaseClientAddressBo;
import com.yiruantong.basic.mapper.client.BaseClientAddressMapper;
import com.yiruantong.basic.service.client.IBaseClientAddressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户地址管理
 *
 * @author YRT
 * @date 2023-10-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/client/clientAddress")
public class BaseClientAddressController extends AbstractController<BaseClientAddressMapper, BaseClientAddress, BaseClientAddressVo, BaseClientAddressBo> {
}
