package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.api.ApiStorageEnterBo;
import com.yiruantong.inventory.domain.operation.bo.StorageEnterBo;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterVo;

import java.util.Map;

/**
 * 其他入库单Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageEnterService extends IServicePlus<StorageEnter, StorageEnterVo, StorageEnterBo> {
  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiStorageEnterBo bo);
}
