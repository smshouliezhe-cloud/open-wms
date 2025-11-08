package com.yiruantong.inventory.controller.stat;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayDetailVo;
import com.yiruantong.inventory.domain.stat.bo.StatStorageDayDetailBo;
import com.yiruantong.inventory.mapper.stat.StatStorageDayDetailMapper;
import com.yiruantong.inventory.service.stat.IStatStorageDayDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 每日库存快照明细
 *
 * @author YRT
 * @date 2024-03-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/stat/storageDayDetail")
public class StatStorageDayDetailController extends AbstractController<StatStorageDayDetailMapper, StatStorageDayDetail, StatStorageDayDetailVo, StatStorageDayDetailBo> {
}
