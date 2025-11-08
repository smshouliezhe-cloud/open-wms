package com.yiruantong.inbound.service.in.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanDetailBo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanDetailVo;
import com.yiruantong.inbound.mapper.in.InOrderPlanDetailMapper;
import com.yiruantong.inbound.service.in.IInOrderPlanDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 收货计划单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
@RequiredArgsConstructor
@Service
public class InOrderPlanDetailServiceImpl extends ServiceImplPlus<InOrderPlanDetailMapper, InOrderPlanDetail, InOrderPlanDetailVo, InOrderPlanDetailBo> implements IInOrderPlanDetailService {
  private final IDataAuthService dataAuthService;

  //#region 入库计划明细查询
  @Override
  public TableDataInfo<InOrderPlanDetailComposeVo> selectPlanDetailComposeList(PageQuery pageQuery) {
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    IPage<InOrderPlanDetailComposeVo> ipage = pageQuery.build();

    MPJLambdaWrapper<InOrderPlanDetail> wrapper = new MPJLambdaWrapper<InOrderPlanDetail>()
      .selectAll(InOrderPlanDetail.class)
      .select(InOrderPlan::getPlanCode, InOrderPlan::getPlanStatus, InOrderPlan::getPlanType,
        InOrderPlan::getConsignorId, InOrderPlan::getConsignorCode, InOrderPlan::getConsignorName)
      .innerJoin(InOrderPlan.class, InOrderPlan::getPlanId, InOrderPlanDetail::getPlanId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, InOrderPlanDetail.class, InOrderPlan.class);

    IPage<InOrderPlanDetailComposeVo> page = this.selectJoinListPage(ipage, InOrderPlanDetailComposeVo.class, wrapper);
    TableDataInfo<InOrderPlanDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    //#region 计算合计数量
    if (!pageQuery.getSumColumnNames().isEmpty()) {
      MPJLambdaWrapper<InOrderPlanDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        detailMPJLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      detailMPJLambdaWrapper
        .innerJoin(InOrderPlan.class, on -> {
          on.eq(InOrderPlanDetail::getPlanId, InOrderPlan::getPlanId);
          return on;
        });

      // 拼接查询条件
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), detailMPJLambdaWrapper, InOrderPlanDetail.class, InOrderPlan.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(detailMPJLambdaWrapper);
      tableDataInfoV.setFooter(maps);
    }
    return tableDataInfoV;
  }

  /**
   * 根据主表ID获取明细集合
   *
   * @param planId
   * @return 返回明细集合
   */
  @Override
  public List<InOrderPlanDetail> selectListByMainId(Long planId) {
    LambdaQueryWrapper<InOrderPlanDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InOrderPlanDetail::getPlanId, planId);

    return this.list(detailLambdaQueryWrapper);
  }
  //#endregion
}
