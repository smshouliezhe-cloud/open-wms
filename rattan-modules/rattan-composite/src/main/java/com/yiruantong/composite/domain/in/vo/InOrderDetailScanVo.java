package com.yiruantong.composite.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InOrderDetail;

import java.io.Serializable;


/**
 * 库存调整选择器视图对象 core_inventory
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data
@ExcelIgnoreUnannotated

@AutoMapper(target = InOrderDetail.class)
public class InOrderDetailScanVo implements Serializable {

}
