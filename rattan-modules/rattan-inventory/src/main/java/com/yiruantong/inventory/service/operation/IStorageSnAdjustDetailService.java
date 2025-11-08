package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageSnAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageSnAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageSnAdjustDetailVo;

import java.util.List;

/**
 * SN调整明细Service接口
 *
 * @author YRT
 * @date 2024-09-05
 */
public interface IStorageSnAdjustDetailService extends IServicePlus<StorageSnAdjustDetail, StorageSnAdjustDetailVo, StorageSnAdjustDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<StorageSnAdjustDetail> selectListByMainId(Long mainId);
}
