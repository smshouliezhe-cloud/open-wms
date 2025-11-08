package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageProfitLoss;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossVo;
import com.yiruantong.inventory.domain.operation.bo.StorageProfitLossBo;

import java.util.Map;

/**
 * 盈亏单Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageProfitLossService extends IServicePlus<StorageProfitLoss, StorageProfitLossVo, StorageProfitLossBo> {

  R<Void> createCheck(Map<String, Object> map);

  R<Void> adjustInventory(Map<String, Object> map);
}
