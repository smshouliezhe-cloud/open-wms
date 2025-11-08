package com.yiruantong.inventory.service.operation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.operation.StorageProfitLoss;
import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageProfitLossDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageProfitLossDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageProfitLossDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 盈亏单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageProfitLossDetailServiceImpl extends ServiceImplPlus<StorageProfitLossDetailMapper, StorageProfitLossDetail, StorageProfitLossDetailVo, StorageProfitLossDetailBo> implements IStorageProfitLossDetailService {
  private final IDataAuthService dataAuthService;

  //#region 盈亏单明细查询数据
  @Override
  public TableDataInfo<StorageProfitLossDetailComposeVo> selectProfitLossDetailComposeList(PageQuery pageQuery) {
    IPage<StorageProfitLossDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageProfitLossDetail> wrapper = new MPJLambdaWrapper<StorageProfitLossDetail>()
      .selectAll(StorageProfitLossDetail.class)
      .select(StorageProfitLoss::getProfitLossCode,StorageProfitLoss::getCheckCode,
        StorageProfitLoss::getConsignorName, StorageProfitLoss::getApplyDate, StorageProfitLoss::getStorageName, StorageProfitLoss::getSourceCode,
        StorageProfitLoss::getDeptName, StorageProfitLoss::getLossStatus, StorageProfitLoss::getNickName,StorageProfitLoss::getSortingStatus,
        StorageProfitLoss::getSortingDate)
      .innerJoin(StorageProfitLoss.class, StorageProfitLoss::getProfitLossId, StorageProfitLossDetail::getProfitLossId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageProfitLossDetail.class, StorageProfitLoss.class);

    IPage<StorageProfitLossDetailComposeVo> page = this.selectJoinListPage(ipage, StorageProfitLossDetailComposeVo.class, wrapper);
    TableDataInfo<StorageProfitLossDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion

  @Override
  public List<StorageProfitLossDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageProfitLossDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageProfitLossDetail::getProfitLossId, id);

    return this.list(detailLambdaQueryWrapper);
  }
}
