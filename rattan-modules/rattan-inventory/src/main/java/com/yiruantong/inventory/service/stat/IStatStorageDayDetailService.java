package com.yiruantong.inventory.service.stat;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayDetailVo;
import com.yiruantong.inventory.domain.stat.bo.StatStorageDayDetailBo;

/**
 * 每日库存快照明细Service接口
 *
 * @author YRT
 * @date 2024-03-18
 */
public interface IStatStorageDayDetailService extends IServicePlus<StatStorageDayDetail, StatStorageDayDetailVo, StatStorageDayDetailBo> {
}
