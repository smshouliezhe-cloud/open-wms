package com.yiruantong.inventory.service.operation.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.operation.bo.ErpOutOrderBo;
import com.yiruantong.inventory.domain.operation.vo.ErpOutOrderVo;
import com.yiruantong.inventory.domain.operation.ErpOutOrder;
import com.yiruantong.inventory.mapper.operation.ErpOutOrderMapper;
import com.yiruantong.inventory.service.operation.IErpOutOrderService;

/**
 * 其他出库单Service业务层处理
 *
 * @author YRT
 * @date 2024-06-26
 */
@RequiredArgsConstructor
@Service
public class ErpOutOrderServiceImpl extends ServiceImplPlus<ErpOutOrderMapper, ErpOutOrder, ErpOutOrderVo, ErpOutOrderBo> implements IErpOutOrderService {
}
