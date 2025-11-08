package com.yiruantong.inventory.mapper.process;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.process.ProcessOrder;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 加工列Mapper接口
 *
 * @author YRT
 * @date 2025-01-17
 */
public interface ProcessOrderMapper extends BaseMapperPlus<ProcessOrder, ProcessOrderVo> {

}
