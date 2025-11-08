package com.yiruantong.inventory.controller.log;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.log.LogStorageStatusChange;
import com.yiruantong.inventory.domain.log.vo.LogStorageStatusChangeVo;
import com.yiruantong.inventory.domain.log.bo.LogStorageStatusChangeBo;
import com.yiruantong.inventory.mapper.log.LogStorageStatusChangeMapper;
import com.yiruantong.inventory.service.log.ILogStorageStatusChangeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 状态转变日志
 *
 * @author YRT
 * @date 2024-01-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/log/storageStatusChange")
public class LogStorageStatusChangeController extends AbstractController<LogStorageStatusChangeMapper, LogStorageStatusChange, LogStorageStatusChangeVo, LogStorageStatusChangeBo> {
}
