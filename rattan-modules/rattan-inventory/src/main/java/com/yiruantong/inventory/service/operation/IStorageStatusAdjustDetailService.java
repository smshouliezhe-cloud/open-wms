package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageStatusAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageStatusAdjustDetailVo;

import java.util.List;

/**
 * 状态属性调整明细Service接口
 *
 * @author YRT
 * @date 2025-02-14
 */
public interface IStorageStatusAdjustDetailService extends IServicePlus<StorageStatusAdjustDetail, StorageStatusAdjustDetailVo, StorageStatusAdjustDetailBo> {
  List<StorageStatusAdjustDetail> selectListByMainId(Long id);
}
