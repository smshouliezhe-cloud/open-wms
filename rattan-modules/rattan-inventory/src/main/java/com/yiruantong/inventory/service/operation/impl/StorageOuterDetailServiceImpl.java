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
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageOuterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageOuterDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 盘点单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageOuterDetailServiceImpl extends ServiceImplPlus<StorageOuterDetailMapper, StorageOuterDetail, StorageOuterDetailVo, StorageOuterDetailBo> implements IStorageOuterDetailService {
  private final IDataAuthService dataAuthService;
  @Override
  public List<StorageOuterDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageOuterDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageOuterDetail::getOuterId, id);

    return this.list(detailLambdaQueryWrapper);
  }

  //#region 其他出库明细查询数据
  @Override
  public TableDataInfo<StorageOuterDetailComposeVo> selectStorageOuterDetailComposeList(PageQuery pageQuery) {
    IPage<StorageOuterDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageOuterDetail> wrapper = new MPJLambdaWrapper<StorageOuterDetail>()
      .select(StorageOuter::getOuterCode,StorageOuter::getOuterStatus,
        StorageOuter::getConsignorName, StorageOuter::getOrderType, StorageOuter::getStorageName, StorageOuter::getSourceCode,
        StorageOuter::getApplyDate, StorageOuter::getDeptName, StorageOuter::getNickName,StorageOuter::getClientShortName,
        StorageOuter::getContainerNo)
      .selectAll(StorageOuterDetail.class)
      .innerJoin(StorageOuter.class, StorageOuter::getOuterId, StorageOuterDetail::getOuterId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageOuterDetail.class, StorageOuter.class);

    IPage<StorageOuterDetailComposeVo> page = this.selectJoinListPage(ipage, StorageOuterDetailComposeVo.class, wrapper);
    TableDataInfo<StorageOuterDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
