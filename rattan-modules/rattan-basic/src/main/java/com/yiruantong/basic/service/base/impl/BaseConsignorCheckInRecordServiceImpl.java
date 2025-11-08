package com.yiruantong.basic.service.base.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.base.bo.BaseConsignorCheckInRecordBo;
import com.yiruantong.basic.domain.base.vo.BaseConsignorCheckInRecordVo;
import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;
import com.yiruantong.basic.mapper.base.BaseConsignorCheckInRecordMapper;
import com.yiruantong.basic.service.base.IBaseConsignorCheckInRecordService;

/**
 * 门店打卡记录Service业务层处理
 *
 * @author YRT
 * @date 2025-01-08
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorCheckInRecordServiceImpl extends ServiceImplPlus<BaseConsignorCheckInRecordMapper, BaseConsignorCheckInRecord, BaseConsignorCheckInRecordVo, BaseConsignorCheckInRecordBo> implements IBaseConsignorCheckInRecordService {
}
