package com.yiruantong.inventory.service.plate.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateInCost;
import com.yiruantong.inventory.domain.plate.BasePlateInDetail;
import com.yiruantong.inventory.domain.plate.BasePlateIn;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInDetailBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInDetailVo;
import com.yiruantong.inventory.mapper.plate.BasePlateInDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateInCostService;
import com.yiruantong.inventory.service.plate.IBasePlateInDetailService;
import org.springframework.stereotype.Service;

/**
 * 容器归还明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateInDetailServiceImpl extends ServiceImplPlus<BasePlateInDetailMapper, BasePlateInDetail, BasePlateInDetailVo, BasePlateInDetailBo> implements IBasePlateInDetailService {
}
