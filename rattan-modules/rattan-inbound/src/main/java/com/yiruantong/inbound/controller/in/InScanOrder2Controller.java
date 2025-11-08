package com.yiruantong.inbound.controller.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.IInScanNoBillService;
import com.yiruantong.inbound.service.in.IInScanOrder2Service;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 入库扫描
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/inScanOrder2")
class InScanOrder2Controller extends BaseController {
  private final IInScanOrder2Service inScanOrder2Service;

  /**
   * 入库前校验：是否存在符合禁收日期
   *
   * @param inScanOrderBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/saveOrderFreight")
  public R<Void> saveOrderFreight(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanOrder2Service.saveOrderFreight(inScanOrderBo);
  }


}
