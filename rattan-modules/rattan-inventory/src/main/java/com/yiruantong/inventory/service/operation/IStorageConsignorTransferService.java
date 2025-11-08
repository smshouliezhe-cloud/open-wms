package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;
import com.yiruantong.inventory.domain.operation.api.ApiStorageConsignorTransferBo;
import com.yiruantong.inventory.domain.operation.bo.StorageConsignorTransferBo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferVo;
import com.yiruantong.inventory.domain.operation.vo.TransferAppVo;

import java.util.Map;

/**
 * 货主过户Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageConsignorTransferService extends IServicePlus<StorageConsignorTransfer, StorageConsignorTransferVo, StorageConsignorTransferBo> {
  R<Map<String, Object>> add(ApiStorageConsignorTransferBo bo);

    TableDataInfo<TransferAppVo> pageDetailList(PageQuery pageQuery);

  R<TransferAppVo> transferDetail(Long consignorTransferId);

  R<TransferAppVo> transferAdd(TransferAppVo transferAppVo);
}
