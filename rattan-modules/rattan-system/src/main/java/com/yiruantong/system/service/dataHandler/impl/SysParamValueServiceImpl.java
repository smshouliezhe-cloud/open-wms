package com.yiruantong.system.service.dataHandler.impl;

import com.yiruantong.system.domain.dataHandler.SysParamValue;
import com.yiruantong.system.domain.dataHandler.bo.SysParamValueBo;
import com.yiruantong.system.domain.dataHandler.vo.SysParamValueVo;
import com.yiruantong.system.mapper.dataHandler.SysParamValueMapper;
import com.yiruantong.system.service.dataHandler.ISysParamValueService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 下拉框值设置Service业务层处理
 *
 * @author YRT
 * @date 2023-07-29
 */
@RequiredArgsConstructor
@Service
public class SysParamValueServiceImpl extends ServiceImplPlus<SysParamValueMapper, SysParamValue, SysParamValueVo, SysParamValueBo> implements ISysParamValueService {
}
