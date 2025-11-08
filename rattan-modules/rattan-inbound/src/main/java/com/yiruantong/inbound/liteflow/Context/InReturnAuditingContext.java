package com.yiruantong.inbound.liteflow.Context;

import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import lombok.Data;

import java.util.List;

/**
 * 扫描入库上下文
 */
@Data
public class InReturnAuditingContext {
  InReturn inReturn;
  List<InReturnDetail> detailList;
}
