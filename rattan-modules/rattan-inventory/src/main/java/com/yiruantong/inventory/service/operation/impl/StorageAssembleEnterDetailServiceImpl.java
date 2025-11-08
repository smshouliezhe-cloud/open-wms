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
import com.yiruantong.inventory.domain.operation.StorageAssembleEnterDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleEnterDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageAssembleEnterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageAssembleEnterDetailService;
import org.springframework.stereotype.Service;

/**
 * 商品拆装单入库明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageAssembleEnterDetailServiceImpl extends ServiceImplPlus<StorageAssembleEnterDetailMapper, StorageAssembleEnterDetail, StorageAssembleEnterDetailVo, StorageAssembleEnterDetailBo> implements IStorageAssembleEnterDetailService {
  private final IDataAuthService dataAuthService;

  //#region 商品拆装单入库明细查询数据
  @Override
  public TableDataInfo<StorageAssembleEnterDetailComposeVo> selectAssembleEnterDetailComposeList(PageQuery pageQuery) {
    IPage<StorageAssembleEnterDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    // 查询入库数据
    MPJLambdaWrapper<StorageAssembleEnterDetail> wrapper = new MPJLambdaWrapper<StorageAssembleEnterDetail>()
      .selectAll(StorageAssembleEnterDetail.class)
      .select(StorageAssemble::getAssembleCode,StorageAssemble::getAssembleStatus,
        StorageAssemble::getConsignorName, StorageAssemble::getDeptName, StorageAssemble::getStorageName, StorageAssemble::getSourceCode,
        StorageAssemble::getApplyDate, StorageAssemble::getSortingDate, StorageAssemble::getNickName,StorageAssemble::getSortingStatus,
        StorageAssemble::getConsignorCode)
      .innerJoin(StorageAssemble.class, StorageAssemble::getAssembleId, StorageAssembleEnterDetail::getAssembleId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageAssembleEnterDetail.class, StorageAssemble.class);

    IPage<StorageAssembleEnterDetailComposeVo> page = this.selectJoinListPage(ipage, StorageAssembleEnterDetailComposeVo.class, wrapper);
    TableDataInfo<StorageAssembleEnterDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
//    final List<StorageAssembleEnterDetailComposeVo> rows = tableDataInfoV.getRows();
    return tableDataInfoV;
  }
  //#endregion
}
