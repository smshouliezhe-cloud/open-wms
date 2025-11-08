package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageCheck;
import com.yiruantong.inventory.domain.operation.bo.StorageCheckBo;
import com.yiruantong.inventory.domain.operation.vo.CreateStorageCheckVo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckVo;

import java.util.Map;

/**
 * 盘点单Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageCheckService extends IServicePlus<StorageCheck, StorageCheckVo, StorageCheckBo> {
  //#region 创建盘点单
  R<Void> createOrderCheck(CreateStorageCheckVo createStorageCheckVo);

  R<Void> subimtCheckBill(Map<String, Object> map);

  R<Void> createCheck(Map<String, Object> map);

  R<Void> adjustInventory(Map<String, Object> map);
}
