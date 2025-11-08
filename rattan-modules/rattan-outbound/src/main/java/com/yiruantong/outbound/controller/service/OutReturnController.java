package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.vo.OutOrderDataSetVo;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.api.ApiOutReturnBo;
import com.yiruantong.outbound.domain.service.bo.OutReturnBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnVo;
import com.yiruantong.outbound.mapper.service.OutReturnMapper;
import com.yiruantong.outbound.service.service.IOutReturnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 出库退货单
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/return")
public class OutReturnController extends AbstractController<OutReturnMapper, OutReturn, OutReturnVo, OutReturnBo> {
  private final IOutReturnService iOutReturnService;

  /**
   * 输入遇到货单号失去焦点加载主表信息
   *
   * @param map
   * @return
   */
  @RequestMapping("/onBlurGetByCode")
  public R<OutOrderDataSetVo> onBlurGetByCode(@RequestBody Map<String, Object> map) {
    return iOutReturnService.onBlurGetByCode(map);
  }

  /*
   * 转到预到货单
   * */
  @RequestMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return iOutReturnService.multiAuditing(ids);
  }


  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiOutReturnBo bo) {
    return iOutReturnService.add(bo);
  }

  /**
   * PDA 新增数据
   */
  @PostMapping("/PdaAdd")
  public R<Void> PdaAdd(@RequestBody ApiOutReturnBo bo) {
    return iOutReturnService.PdaAdd(bo);
  }

  /*
   * 退货确认
   * */
  @RequestMapping("/returnConfirm")
  public R<Void> returnConfirm(@RequestBody List<Long> ids) {
    return iOutReturnService.returnConfirm(ids);
  }

  /*
   * 退货驳回
   * */
  @RequestMapping("/returnReject")
  public R<Void> returnReject(@RequestBody List<Long> ids) {
    return iOutReturnService.returnReject(ids);
  }
}
