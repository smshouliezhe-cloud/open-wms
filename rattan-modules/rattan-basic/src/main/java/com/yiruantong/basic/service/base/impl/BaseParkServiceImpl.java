package com.yiruantong.basic.service.base.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.base.bo.BaseParkBo;
import com.yiruantong.basic.domain.base.vo.BaseParkVo;
import com.yiruantong.basic.domain.base.BasePark;
import com.yiruantong.basic.mapper.base.BaseParkMapper;
import com.yiruantong.basic.service.base.IBaseParkService;

/**
 * 物流园区Service业务层处理
 *
 * @author YRT
 * @date 2024-03-09
 */
@RequiredArgsConstructor
@Service
public class BaseParkServiceImpl extends ServiceImplPlus<BaseParkMapper, BasePark, BaseParkVo, BaseParkBo> implements IBaseParkService {
}
