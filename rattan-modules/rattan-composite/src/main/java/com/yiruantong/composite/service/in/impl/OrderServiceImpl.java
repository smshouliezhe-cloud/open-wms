package com.yiruantong.composite.service.in.impl;


import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.composite.liteflow.Context.CheckingContext;
import com.yiruantong.composite.service.in.IOrderService;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {

  @Resource
  private final FlowExecutor flowExecutor;

  @Override
  public R<Map<String, Object>> inOrderChecking(ApiInOrderBo bo) {
    LiteflowResponse response = flowExecutor.execute2Resp("InOrderChecking", bo, CheckingContext.class);
    cn.hutool.core.lang.Assert.isFalse(!response.isSuccess(), response.getMessage());
    return R.ok("更新质检状态成功");
  }
}
