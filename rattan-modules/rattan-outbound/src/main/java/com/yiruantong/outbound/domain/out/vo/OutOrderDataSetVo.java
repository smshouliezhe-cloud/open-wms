package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.io.Serializable;
import java.util.List;


/**
 * 出库订单数据集合
 *
 * @author YiRuanTong
 * @date 2023-11-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrder.class)
public class OutOrderDataSetVo implements Serializable {
  private OutOrder order;

  private List<OutOrderDetail> details;

  private List<OutOrderDetailHolderComposeVO> detailsVo;
}
