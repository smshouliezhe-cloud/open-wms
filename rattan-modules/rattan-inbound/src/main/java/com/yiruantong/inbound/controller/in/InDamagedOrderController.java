package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InDamagedOrder;
import com.yiruantong.inbound.domain.in.bo.InDamagedOrderBo;
import com.yiruantong.inbound.domain.in.vo.InDamagedOrderVo;
import com.yiruantong.inbound.mapper.in.InDamagedOrderMapper;
import com.yiruantong.inbound.service.in.IInDamagedOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 残品入库单
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/damagedOrder")
public class InDamagedOrderController extends AbstractController<InDamagedOrderMapper, InDamagedOrder, InDamagedOrderVo, InDamagedOrderBo> {
  private final IInDamagedOrderService inDamagedOrderService;

  /**
   * 残品入库单审核
   */
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return inDamagedOrderService.multiAuditing(ids);
  }

  /**
   * 转到预到货单
   */
  @PostMapping("/toInOrder")
  public R<Void> toInOrder(@RequestBody Map<String, Object> map) {
    return inDamagedOrderService.toInOrder(map);
  }
}
