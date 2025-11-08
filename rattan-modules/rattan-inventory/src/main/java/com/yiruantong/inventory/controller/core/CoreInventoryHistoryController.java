package com.yiruantong.inventory.controller.core;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHistoryVo;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryHistoryBo;
import com.yiruantong.inventory.mapper.core.CoreInventoryHistoryMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WMS库存变化推送
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/core/inventoryHistory")
public class CoreInventoryHistoryController extends AbstractController<CoreInventoryHistoryMapper, CoreInventoryHistory, CoreInventoryHistoryVo, CoreInventoryHistoryBo> {
}
