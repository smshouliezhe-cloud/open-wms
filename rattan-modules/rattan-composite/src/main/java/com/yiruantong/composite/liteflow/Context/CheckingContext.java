package com.yiruantong.composite.liteflow.Context;

import lombok.Data;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;

import java.util.List;

/**
 * 修改质检状态上下文
 */
@Data
public class CheckingContext {
  /**
   * 预到货单号
   */
  String orderCode;
  /**
   * 预到货单信息
   */
  InOrder inOrder;

  /**
   * 预到货明细
   */
  List<InOrderDetail> inOrderDetails;

  /**
   * 接口参数
   */
  ApiInOrderBo apiInOrderBo;


}
