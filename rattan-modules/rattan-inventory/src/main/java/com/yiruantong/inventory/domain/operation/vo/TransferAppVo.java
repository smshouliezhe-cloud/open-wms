package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;

import java.util.List;


/**
 * 销售单视图对象 erp_sale_order
 *
 * @author YRT
 * @date 2024-11-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageConsignorTransfer.class)
public class TransferAppVo extends StorageConsignorTransferVo {
  private List<StorageConsignorTransferDetailVo> detailList;

}
