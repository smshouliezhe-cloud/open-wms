package com.yiruantong.composite.service.inventory;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.composite.domain.inventory.Bo.StorageLowerReplenishmentBo;

import java.util.List;

/**
 * 库存下限转补货单
 */
public interface IStorageLowerReplenishmentService {
  void toReplenishment(LoginUser loginUser, List<StorageLowerReplenishmentBo> storageReplenishmentBoList);

}
