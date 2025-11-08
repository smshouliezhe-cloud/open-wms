package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.ErpOutOrder;
import com.yiruantong.inventory.domain.operation.vo.ErpOutOrderVo;
import com.yiruantong.inventory.domain.operation.bo.ErpOutOrderBo;

/**
 * 其他出库单Service接口
 *
 * @author YRT
 * @date 2024-06-26
 */
public interface IErpOutOrderService extends IServicePlus<ErpOutOrder, ErpOutOrderVo, ErpOutOrderBo> {
}
