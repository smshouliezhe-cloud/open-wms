package com.yiruantong.inventory.service.plate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.plate.bo.BasePlateAdjustDetailBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateAdjustDetailVo;
import com.yiruantong.inventory.domain.plate.BasePlateAdjustDetail;
import com.yiruantong.inventory.mapper.plate.BasePlateAdjustDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateAdjustDetailService;

/**
 * 容器调整明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateAdjustDetailServiceImpl extends ServiceImplPlus<BasePlateAdjustDetailMapper, BasePlateAdjustDetail, BasePlateAdjustDetailVo, BasePlateAdjustDetailBo> implements IBasePlateAdjustDetailService {
}
