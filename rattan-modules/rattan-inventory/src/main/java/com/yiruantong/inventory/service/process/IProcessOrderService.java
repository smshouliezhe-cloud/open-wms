package com.yiruantong.inventory.service.process;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.process.ProcessOrder;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderVo;
import com.yiruantong.inventory.domain.process.bo.ProcessOrderBo;

/**
 * 加工列Service接口
 *
 * @author YRT
 * @date 2025-01-17
 */
public interface IProcessOrderService extends IServicePlus<ProcessOrder, ProcessOrderVo, ProcessOrderBo> {
}
