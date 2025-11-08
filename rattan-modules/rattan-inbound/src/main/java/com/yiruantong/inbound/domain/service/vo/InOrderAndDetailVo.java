package com.yiruantong.inbound.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;

import java.io.Serializable;
import java.util.List;

/*
 返回的结果 存放主表跟明细信息
 */
@Data
@ExcelIgnoreUnannotated
public class InOrderAndDetailVo implements Serializable {
  private InOrder order;

  private List<InOrderDetail> details;
}
