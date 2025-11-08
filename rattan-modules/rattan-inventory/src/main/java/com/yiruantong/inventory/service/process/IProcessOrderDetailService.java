package com.yiruantong.inventory.service.process;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.process.ProcessOrderDetail;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderDetailVo;
import com.yiruantong.inventory.domain.process.bo.ProcessOrderDetailBo;

/**
 * 加工列明细Service接口
 *
 * @author YRT
 * @date 2025-01-17
 */
public interface IProcessOrderDetailService extends IServicePlus<ProcessOrderDetail, ProcessOrderDetailVo, ProcessOrderDetailBo> {
}
