package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.api.ApiInReturnBo;
import com.yiruantong.inbound.domain.service.bo.InReturnBo;
import com.yiruantong.inbound.domain.service.vo.InReturnVo;
import com.yiruantong.inbound.mapper.service.InReturnMapper;
import com.yiruantong.inbound.service.service.IInReturnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 退货单
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/return")
public class InReturnController extends AbstractController<InReturnMapper, InReturn, InReturnVo, InReturnBo> {
  private final IInReturnService inReturnService;


  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiInReturnBo bo) {
    return inReturnService.add(bo);
  }
}
