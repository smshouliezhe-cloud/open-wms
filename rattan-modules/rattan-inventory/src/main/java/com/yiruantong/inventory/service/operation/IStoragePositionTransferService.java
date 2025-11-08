package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferDetailBo;
import com.yiruantong.inventory.domain.operation.StoragePositionTransfer;
import com.yiruantong.inventory.domain.operation.bo.StoragePositionTransferBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferVo;

import java.util.List;

/**
 * 货位转移Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStoragePositionTransferService extends IServicePlus<StoragePositionTransfer, StoragePositionTransferVo, StoragePositionTransferBo> {
  /**
   * 保存货位转移
   *
   * @param storageScanPositionTransferBo 保存数据
   * @return R 返回保存结果
   */
  R<Void> savePositionTransfer(ScanPositionTransferBo storageScanPositionTransferBo);

  /**
   * 货位转移
   *
   * @param storagePositionTransfer       货位转移主标
   * @param detailList                    货位转移明细
   * @param storageScanPositionTransferBo 前端传递Bo
   * @return R
   */
  R<Void> transfter(StoragePositionTransfer storagePositionTransfer, List<ScanPositionTransferDetailBo> detailList, ScanPositionTransferBo storageScanPositionTransferBo);
}
