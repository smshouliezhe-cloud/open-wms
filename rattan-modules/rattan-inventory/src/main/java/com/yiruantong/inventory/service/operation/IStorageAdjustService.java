package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageAdjust;
import com.yiruantong.inventory.domain.operation.api.ApiStorageAdjustBo;
import com.yiruantong.inventory.domain.operation.bo.StorageAdjustBo;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustVo;

import java.util.Map;

/**
 * 库存调整单Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageAdjustService extends IServicePlus<StorageAdjust, StorageAdjustVo, StorageAdjustBo> {
  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiStorageAdjustBo bo);
}
