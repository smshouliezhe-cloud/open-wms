package com.yiruantong.basic.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.service.bo.TmsServicePartBo;
import com.yiruantong.basic.domain.service.vo.TmsServicePartVo;
import com.yiruantong.basic.domain.service.TmsServicePart;
import com.yiruantong.basic.mapper.service.TmsServicePartMapper;
import com.yiruantong.basic.service.service.ITmsServicePartService;

/**
 * 维修配件管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-09
 */
@RequiredArgsConstructor
@Service
public class TmsServicePartServiceImpl extends ServiceImplPlus<TmsServicePartMapper, TmsServicePart, TmsServicePartVo, TmsServicePartBo> implements ITmsServicePartService {
}
