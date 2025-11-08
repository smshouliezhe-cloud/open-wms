package com.yiruantong.inventory.service.log.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.log.bo.LogStorageStatusChangeBo;
import com.yiruantong.inventory.domain.log.vo.LogStorageStatusChangeVo;
import com.yiruantong.inventory.domain.log.LogStorageStatusChange;
import com.yiruantong.inventory.mapper.log.LogStorageStatusChangeMapper;
import com.yiruantong.inventory.service.log.ILogStorageStatusChangeService;

/**
 * 状态转变日志Service业务层处理
 *
 * @author YRT
 * @date 2024-01-26
 */
@RequiredArgsConstructor
@Service
public class LogStorageStatusChangeServiceImpl extends ServiceImplPlus<LogStorageStatusChangeMapper, LogStorageStatusChange, LogStorageStatusChangeVo, LogStorageStatusChangeBo> implements ILogStorageStatusChangeService {
}
