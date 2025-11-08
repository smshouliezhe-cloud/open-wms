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
import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransferDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageConsignorTransferDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageConsignorTransferDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageConsignorTransferDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 货位转移明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageConsignorTransferDetailServiceImpl extends ServiceImplPlus<StorageConsignorTransferDetailMapper, StorageConsignorTransferDetail, StorageConsignorTransferDetailVo, StorageConsignorTransferDetailBo> implements IStorageConsignorTransferDetailService {
  private final IDataAuthService dataAuthService;

  @Override
  public List<StorageConsignorTransferDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageConsignorTransferDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageConsignorTransferDetail::getConsignorTransferId, id);

    return this.list(detailLambdaQueryWrapper);
  }

  //#region 货主过户明细查询数据
  @Override
  public TableDataInfo<StorageConsignorTransferDetailComposeVo> selectConsignorTransferDetailComposeList(PageQuery pageQuery) {
    IPage<StorageConsignorTransferDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageConsignorTransferDetail> wrapper = new MPJLambdaWrapper<StorageConsignorTransferDetail>()
      .selectAll(StorageConsignorTransferDetail.class)
      .select(StorageConsignorTransfer::getConsignorTransferCode,StorageConsignorTransfer::getTransferStatus,
        StorageConsignorTransfer::getSortingStatus, StorageConsignorTransfer::getSortingDate, StorageConsignorTransfer::getStorageName, StorageConsignorTransfer::getSourceCode, StorageConsignorTransfer::getNickName)
      .innerJoin(StorageConsignorTransfer.class, StorageConsignorTransfer::getConsignorTransferId, StorageConsignorTransferDetail::getConsignorTransferId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageConsignorTransferDetail.class, StorageConsignorTransfer.class);

    IPage<StorageConsignorTransferDetailComposeVo> page = this.selectJoinListPage(ipage, StorageConsignorTransferDetailComposeVo.class, wrapper);
    TableDataInfo<StorageConsignorTransferDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
