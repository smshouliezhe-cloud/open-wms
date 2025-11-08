package com.yiruantong.basic.controller.base;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;
import com.yiruantong.basic.domain.base.vo.BaseConsignorCheckInRecordVo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorCheckInRecordBo;
import com.yiruantong.basic.mapper.base.BaseConsignorCheckInRecordMapper;
import com.yiruantong.basic.service.base.IBaseConsignorCheckInRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门店打卡记录
 *
 * @author YRT
 * @date 2025-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/consignorCheckInRecord")
public class BaseConsignorCheckInRecordController extends AbstractController<BaseConsignorCheckInRecordMapper, BaseConsignorCheckInRecord, BaseConsignorCheckInRecordVo, BaseConsignorCheckInRecordBo> {
}
