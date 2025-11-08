package com.yiruantong.inventory.service.replenishment;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.base.scan.ScanReplenishmentBo;
import com.yiruantong.inventory.domain.base.scan.ScanReplenishmentDetailBo;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.bo.StorageReplenishmentBo;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentVo;

import java.util.List;

/**
 * 补货单Service接口
 *
 * @author YRT
 * @date 2024-08-23
 */
public interface IStorageReplenishmentService extends IServicePlus<StorageReplenishment, StorageReplenishmentVo, StorageReplenishmentBo> {

  /**
   * 根据单号查询补货单
   *
   * @param replenishmentCode
   * @return 返回波次单信息
   */
  StorageReplenishment getByCode(String replenishmentCode);


  /**
   * 保存补货扫描
   *
   * @param storageScanReplenishmentBo 保存数据
   * @return R 返回保存结果
   */
  R<Void> saveReplenishment(ScanReplenishmentBo storageScanReplenishmentBo);

  /**
   * @param storageReplenishment       补货单主表
   * @param detailList                 补货单明细
   * @param storageScanReplenishmentBo 前端传递Bo
   * @return
   */
  R<Void> replenishment(StorageReplenishment storageReplenishment, List<ScanReplenishmentDetailBo> detailList, ScanReplenishmentBo storageScanReplenishmentBo);

}
