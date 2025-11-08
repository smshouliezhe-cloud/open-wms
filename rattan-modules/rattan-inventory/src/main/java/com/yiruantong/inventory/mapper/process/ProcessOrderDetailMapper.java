package com.yiruantong.inventory.mapper.process;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.process.ProcessOrderDetail;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 加工列明细Mapper接口
 *
 * @author YRT
 * @date 2025-01-17
 */
public interface ProcessOrderDetailMapper extends BaseMapperPlus<ProcessOrderDetail, ProcessOrderDetailVo> {

}
