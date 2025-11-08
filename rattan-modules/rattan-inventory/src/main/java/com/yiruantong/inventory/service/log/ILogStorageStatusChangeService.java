package com.yiruantong.inventory.service.log;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.log.LogStorageStatusChange;
import com.yiruantong.inventory.domain.log.vo.LogStorageStatusChangeVo;
import com.yiruantong.inventory.domain.log.bo.LogStorageStatusChangeBo;

/**
 * 状态转变日志Service接口
 *
 * @author YRT
 * @date 2024-01-26
 */
public interface ILogStorageStatusChangeService extends IServicePlus<LogStorageStatusChange, LogStorageStatusChangeVo, LogStorageStatusChangeBo> {
}
