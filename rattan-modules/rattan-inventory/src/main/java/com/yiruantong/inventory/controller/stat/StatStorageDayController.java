package com.yiruantong.inventory.controller.stat;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.stat.StatStorageDay;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayVo;
import com.yiruantong.inventory.domain.stat.bo.StatStorageDayBo;
import com.yiruantong.inventory.mapper.stat.StatStorageDayMapper;
import com.yiruantong.inventory.service.stat.IStatStorageDayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 每日库存快照
 *
 * @author YRT
 * @date 2024-03-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/stat/storageDay")
public class StatStorageDayController extends AbstractController<StatStorageDayMapper, StatStorageDay, StatStorageDayVo, StatStorageDayBo> {
}
