package com.yiruantong.outbound.service.out.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanDetailVo;
import com.yiruantong.outbound.domain.out.vo.OutPlanDetailComposeVo;
import com.yiruantong.outbound.mapper.out.OutOrderPlanDetailMapper;
import com.yiruantong.outbound.service.out.IOutOrderPlanDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出库计划单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class OutOrderPlanDetailServiceImpl extends ServiceImplPlus<OutOrderPlanDetailMapper, OutOrderPlanDetail, OutOrderPlanDetailVo, OutOrderPlanDetailBo> implements IOutOrderPlanDetailService {
  private final IDataAuthService dataAuthService;

  /**
   * 出库计划明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<OutPlanDetailComposeVo> selectOutPlanDetailComposeList(PageQuery pageQuery) {
    IPage<OutPlanDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<OutOrderPlanDetail> wrapper = new MPJLambdaWrapper<OutOrderPlanDetail>()
      .select(OutOrderPlan::getOrderPlanCode,
        OutOrderPlan::getConsignorName, OutOrderPlan::getStorageName, OutOrderPlan::getSourceCode,
        OutOrderPlan::getNickName, OutOrderPlan::getPlanStatus, OutOrderPlan::getPlanDate,
        OutOrderPlan::getLinkerName, OutOrderPlan::getZip, OutOrderPlan::getCountryName,
        OutOrderPlan::getShippingName, OutOrderPlan::getMobile, OutOrderPlan::getTel,
        OutOrderPlan::getShippingAddress, OutOrderPlan::getProvinceName, OutOrderPlan::getCityName,
        OutOrderPlan::getRegionName, OutOrderPlan::getClientId, OutOrderPlan::getClientCode, OutOrderPlan::getClientShortName)
      .selectAll(OutOrderPlanDetail.class)
      .innerJoin(OutOrderPlan.class, OutOrderPlan::getOrderPlanId, OutOrderPlanDetail::getOrderPlanId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, OutOrderPlanDetail.class, OutOrderPlan.class);

    IPage<OutPlanDetailComposeVo> page = this.selectJoinListPage(ipage, OutPlanDetailComposeVo.class, wrapper);
    TableDataInfo<OutPlanDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  /**
   * 根据主表ID获取明细集合
   *
   * @param planId
   * @return 返回明细集合
   */
  @Override
  public List<OutOrderPlanDetail> selectListByMainId(Long planId) {
    LambdaQueryWrapper<OutOrderPlanDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutOrderPlanDetail::getOrderPlanId, planId);

    return this.list(detailLambdaQueryWrapper);
  }
}
