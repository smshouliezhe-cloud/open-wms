package com.yiruantong.inventory.service.stat.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.stat.bo.StatStorageDayDetailBo;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayDetailVo;
import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;
import com.yiruantong.inventory.mapper.stat.StatStorageDayDetailMapper;
import com.yiruantong.inventory.service.stat.IStatStorageDayDetailService;

/**
 * 每日库存快照明细Service业务层处理
 *
 * @author YRT
 * @date 2024-03-18
 */
@RequiredArgsConstructor
@Service
public class StatStorageDayDetailServiceImpl extends ServiceImplPlus<StatStorageDayDetailMapper, StatStorageDayDetail, StatStorageDayDetailVo, StatStorageDayDetailBo> implements IStatStorageDayDetailService {
}
