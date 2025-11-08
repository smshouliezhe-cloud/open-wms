package com.yiruantong.basic.service.storage.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlateHistory;
import com.yiruantong.basic.domain.storage.bo.BasePlateHistoryBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateHistoryVo;
import com.yiruantong.basic.mapper.storage.BasePlateHistoryMapper;
import com.yiruantong.basic.service.storage.IBasePlateHistoryService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 容器使用轨迹Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class BasePlateHistoryServiceImpl extends ServiceImplPlus<BasePlateHistoryMapper, BasePlateHistory, BasePlateHistoryVo, BasePlateHistoryBo> implements IBasePlateHistoryService {
}
