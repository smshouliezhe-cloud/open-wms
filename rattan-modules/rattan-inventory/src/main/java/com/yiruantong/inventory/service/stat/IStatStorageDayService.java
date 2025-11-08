package com.yiruantong.inventory.service.stat;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.stat.StatStorageDay;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayVo;
import com.yiruantong.inventory.domain.stat.bo.StatStorageDayBo;

/**
 * 每日库存快照Service接口
 *
 * @author YRT
 * @date 2024-03-18
 */
public interface IStatStorageDayService extends IServicePlus<StatStorageDay, StatStorageDayVo, StatStorageDayBo> {
}
