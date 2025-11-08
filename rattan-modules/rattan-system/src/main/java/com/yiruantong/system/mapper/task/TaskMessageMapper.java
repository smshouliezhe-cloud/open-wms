package com.yiruantong.system.mapper.task;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.system.domain.task.TaskMessage;
import com.yiruantong.system.domain.task.vo.TaskMessageVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 消息推送Mapper接口
 *
 * @author YRT
 * @date 2025-03-23
 */
public interface TaskMessageMapper extends BaseMapperPlus<TaskMessage, TaskMessageVo> {

}
