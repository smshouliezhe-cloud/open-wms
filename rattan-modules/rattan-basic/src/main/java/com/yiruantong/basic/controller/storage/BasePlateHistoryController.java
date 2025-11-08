package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlateHistory;
import com.yiruantong.basic.domain.storage.bo.BasePlateHistoryBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateHistoryVo;
import com.yiruantong.basic.mapper.storage.BasePlateHistoryMapper;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器使用轨迹
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/plateHistory")
public class BasePlateHistoryController extends AbstractController<BasePlateHistoryMapper, BasePlateHistory, BasePlateHistoryVo, BasePlateHistoryBo> {
}
