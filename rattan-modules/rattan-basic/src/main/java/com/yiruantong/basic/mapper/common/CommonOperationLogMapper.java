package com.yiruantong.basic.mapper.common;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.common.CommonOperationLog;
import com.yiruantong.basic.domain.common.vo.CommonOperationLogVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 业务操作日志Mapper接口
 *
 * @author YRT
 * @date 2025-03-08
 */
public interface CommonOperationLogMapper extends BaseMapperPlus<CommonOperationLog, CommonOperationLogVo> {

}
