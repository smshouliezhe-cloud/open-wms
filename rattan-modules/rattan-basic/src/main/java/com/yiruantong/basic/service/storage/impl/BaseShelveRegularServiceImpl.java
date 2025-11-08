package com.yiruantong.basic.service.storage.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.storage.bo.BaseShelveRegularBo;
import com.yiruantong.basic.domain.storage.vo.BaseShelveRegularVo;
import com.yiruantong.basic.domain.storage.BaseShelveRegular;
import com.yiruantong.basic.mapper.storage.BaseShelveRegularMapper;
import com.yiruantong.basic.service.storage.IBaseShelveRegularService;

/**
 * 商品上架策略Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class BaseShelveRegularServiceImpl extends ServiceImplPlus<BaseShelveRegularMapper, BaseShelveRegular, BaseShelveRegularVo, BaseShelveRegularBo> implements IBaseShelveRegularService {
}
