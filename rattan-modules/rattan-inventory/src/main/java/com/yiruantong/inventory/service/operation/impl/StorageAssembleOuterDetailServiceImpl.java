package com.yiruantong.inventory.service.operation.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.operation.StorageAssemble;
import com.yiruantong.inventory.domain.operation.StorageAssembleOuterDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleOuterDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageAssembleOuterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageAssembleOuterDetailService;
import org.springframework.stereotype.Service;

/**
 * 商品拆装单出库明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageAssembleOuterDetailServiceImpl extends ServiceImplPlus<StorageAssembleOuterDetailMapper, StorageAssembleOuterDetail, StorageAssembleOuterDetailVo, StorageAssembleOuterDetailBo> implements IStorageAssembleOuterDetailService {
  private final IDataAuthService dataAuthService;

  //#region 商品拆装单出库明细查询数据
  @Override
  public TableDataInfo<StorageAssembleOuterDetailComposeVo> selectAssembleOuterDetailComposeList(PageQuery pageQuery) {
    IPage<StorageAssembleOuterDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    // 查询出库数据
    MPJLambdaWrapper<StorageAssembleOuterDetail> wrapper = new MPJLambdaWrapper<StorageAssembleOuterDetail>()
      .selectAll(StorageAssembleOuterDetail.class)
      .select(StorageAssemble::getAssembleCode,StorageAssemble::getAssembleStatus,
        StorageAssemble::getConsignorName, StorageAssemble::getDeptName, StorageAssemble::getStorageName, StorageAssemble::getSourceCode,
        StorageAssemble::getApplyDate, StorageAssemble::getSortingDate, StorageAssemble::getNickName,StorageAssemble::getSortingStatus,
        StorageAssemble::getConsignorCode)
      .innerJoin(StorageAssemble.class, StorageAssemble::getAssembleId, StorageAssembleOuterDetail::getAssembleId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageAssembleOuterDetail.class, StorageAssemble.class);

    IPage<StorageAssembleOuterDetailComposeVo> page = this.selectJoinListPage(ipage, StorageAssembleOuterDetailComposeVo.class, wrapper);
    TableDataInfo<StorageAssembleOuterDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
