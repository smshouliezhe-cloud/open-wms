package com.yiruantong.inventory.mapper.log;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.log.LogStorageStatusChange;
import com.yiruantong.inventory.domain.log.vo.LogStorageStatusChangeVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 状态转变日志Mapper接口
 *
 * @author YRT
 * @date 2024-01-26
 */
public interface LogStorageStatusChangeMapper extends BaseMapperPlus<LogStorageStatusChange, LogStorageStatusChangeVo> {

}
