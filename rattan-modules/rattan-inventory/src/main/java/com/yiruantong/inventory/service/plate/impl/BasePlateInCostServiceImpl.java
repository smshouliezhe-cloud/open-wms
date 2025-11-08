package com.yiruantong.inventory.service.plate.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateIn;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostComposeVo;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInCostBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostVo;
import com.yiruantong.inventory.domain.plate.BasePlateInCost;
import com.yiruantong.inventory.mapper.plate.BasePlateInCostMapper;
import com.yiruantong.inventory.service.plate.IBasePlateInCostService;

/**
 * 容器归还费用明细Service业务层处理
 *
 * @author YRT
 * @date 2024-03-14
 */
@RequiredArgsConstructor
@Service
public class BasePlateInCostServiceImpl extends ServiceImplPlus<BasePlateInCostMapper, BasePlateInCost, BasePlateInCostVo, BasePlateInCostBo> implements IBasePlateInCostService {

  private final IDataAuthService dataAuthService;
  /**
   * 容易归还明细查询
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<BasePlateInCostComposeVo> getBasePlateInDetailCompose(PageQuery pageQuery) {
    IPage<BasePlateInCostComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<BasePlateInCost> wrapper = new MPJLambdaWrapper<BasePlateInCost>()
      .select(BasePlateInCost::getPlateType,BasePlateInCost::getPricingManner)
      .selectAll(BasePlateIn.class)
      .selectAll(BasePlateInCost.class)
      .innerJoin(BasePlateIn.class, BasePlateIn::getInId, BasePlateInCost::getInId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, BasePlateInCost.class, BasePlateIn.class);

    IPage<BasePlateInCostComposeVo> page = this.selectJoinListPage(ipage, BasePlateInCostComposeVo.class, wrapper);
    TableDataInfo<BasePlateInCostComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
}
