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
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjust;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StoragePurchasePriceAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailVo;
import com.yiruantong.inventory.mapper.operation.StoragePurchasePriceAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStoragePurchasePriceAdjustDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存成本价调整明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StoragePurchasePriceAdjustDetailServiceImpl extends ServiceImplPlus<StoragePurchasePriceAdjustDetailMapper, StoragePurchasePriceAdjustDetail, StoragePurchasePriceAdjustDetailVo, StoragePurchasePriceAdjustDetailBo> implements IStoragePurchasePriceAdjustDetailService {
  private final IDataAuthService dataAuthService;
  @Override
  public List<StoragePurchasePriceAdjustDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StoragePurchasePriceAdjustDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StoragePurchasePriceAdjustDetail::getPurchasePriceAdjustId, id);

    return this.list(detailLambdaQueryWrapper);
  }

  //#region 库存成本价调整明细查询数据
  @Override
  public TableDataInfo<StoragePurchasePriceAdjustDetailComposeVo> selectPurchasePriceAdjustDetailComposeList(PageQuery pageQuery) {
    IPage<StoragePurchasePriceAdjustDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StoragePurchasePriceAdjustDetail> wrapper = new MPJLambdaWrapper<StoragePurchasePriceAdjustDetail>()
      .selectAll(StoragePurchasePriceAdjustDetail.class)
      .select(StoragePurchasePriceAdjust::getPurchasePriceAdjustCode,StoragePurchasePriceAdjust::getAdjustStatus,
        StoragePurchasePriceAdjust::getConsignorName, StoragePurchasePriceAdjust::getDeptName, StoragePurchasePriceAdjust::getStorageName, StoragePurchasePriceAdjust::getSourceCode,
        StoragePurchasePriceAdjust::getNickName, StoragePurchasePriceAdjust::getApplyDate, StoragePurchasePriceAdjust::getNickName)
      .innerJoin(StoragePurchasePriceAdjust.class, StoragePurchasePriceAdjust::getPurchasePriceAdjustId, StoragePurchasePriceAdjustDetail::getPurchasePriceAdjustId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StoragePurchasePriceAdjustDetail.class, StoragePurchasePriceAdjust.class);

    IPage<StoragePurchasePriceAdjustDetailComposeVo> page = this.selectJoinListPage(ipage, StoragePurchasePriceAdjustDetailComposeVo.class, wrapper);
    TableDataInfo<StoragePurchasePriceAdjustDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
