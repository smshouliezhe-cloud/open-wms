package com.yiruantong.system.mapper.task;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.system.domain.task.TaskMessageConfig;
import com.yiruantong.system.domain.task.vo.TaskMessageConfigVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 消息推送配置Mapper接口
 *
 * @author YRT
 * @date 2025-03-23
 */
public interface TaskMessageConfigMapper extends BaseMapperPlus<TaskMessageConfig, TaskMessageConfigVo> {

}
