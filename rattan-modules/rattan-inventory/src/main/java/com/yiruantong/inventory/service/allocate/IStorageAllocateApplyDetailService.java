package com.yiruantong.inventory.service.allocate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyDetailVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyDetailBo;

import java.util.List;

/**
 * 调拨申请单明细Service接口
 *
 * @author YRT
 * @date 2023-12-19
 */
public interface IStorageAllocateApplyDetailService extends IServicePlus<StorageAllocateApplyDetail, StorageAllocateApplyDetailVo, StorageAllocateApplyDetailBo> {

  /**
   * 根据主表ID获取明细集合
   * @param mainId
   * @return 返回明细集合
   */
  List<StorageAllocateApplyDetail> selectListByMainId(Long mainId);
}
