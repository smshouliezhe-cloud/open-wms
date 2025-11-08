package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.service.OutExchange;
import com.yiruantong.outbound.domain.service.api.ApiOutReturnBo;
import com.yiruantong.outbound.domain.service.vo.OutExchangeComposeVo;
import com.yiruantong.outbound.domain.service.vo.OutExchangeVo;
import com.yiruantong.outbound.domain.service.bo.OutExchangeBo;
import com.yiruantong.outbound.mapper.service.OutExchangeMapper;
import com.yiruantong.outbound.service.service.IOutExchangeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 换货管理
 *
 * @author YiRuanTong
 * @date 2023-10-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/exchange")
public class OutExchangeController extends AbstractController<OutExchangeMapper, OutExchange, OutExchangeVo, OutExchangeBo> {
  private final IOutExchangeService outExchangeService;

  /**
   * PDA 新增数据
   */
  @PostMapping("/PdaAdd")
  public R<Void> PdaAdd(@RequestBody OutExchangeComposeVo compose) {
    return outExchangeService.PdaAdd(compose);
  }
}
