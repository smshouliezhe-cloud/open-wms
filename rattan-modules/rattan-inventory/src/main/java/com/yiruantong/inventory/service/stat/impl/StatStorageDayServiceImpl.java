package com.yiruantong.inventory.service.stat.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.stat.bo.StatStorageDayBo;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayVo;
import com.yiruantong.inventory.domain.stat.StatStorageDay;
import com.yiruantong.inventory.mapper.stat.StatStorageDayMapper;
import com.yiruantong.inventory.service.stat.IStatStorageDayService;

/**
 * 每日库存快照Service业务层处理
 *
 * @author YRT
 * @date 2024-03-18
 */
@RequiredArgsConstructor
@Service
public class StatStorageDayServiceImpl extends ServiceImplPlus<StatStorageDayMapper, StatStorageDay, StatStorageDayVo, StatStorageDayBo> implements IStatStorageDayService {
}
