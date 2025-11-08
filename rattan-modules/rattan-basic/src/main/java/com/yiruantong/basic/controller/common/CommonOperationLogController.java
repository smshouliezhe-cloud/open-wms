package com.yiruantong.basic.controller.common;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.common.CommonOperationLog;
import com.yiruantong.basic.domain.common.vo.CommonOperationLogVo;
import com.yiruantong.basic.domain.common.bo.CommonOperationLogBo;
import com.yiruantong.basic.mapper.common.CommonOperationLogMapper;
import com.yiruantong.basic.service.common.ICommonOperationLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务操作日志
 *
 * @author YRT
 * @date 2025-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/common/operationLog")
public class CommonOperationLogController extends AbstractController<CommonOperationLogMapper, CommonOperationLog, CommonOperationLogVo, CommonOperationLogBo> {
}
