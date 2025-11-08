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
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageEnterDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageEnterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageEnterDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 其他入库单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageEnterDetailServiceImpl extends ServiceImplPlus<StorageEnterDetailMapper, StorageEnterDetail, StorageEnterDetailVo, StorageEnterDetailBo> implements IStorageEnterDetailService {
  private final IDataAuthService dataAuthService;
  @Override
  public List<StorageEnterDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageEnterDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageEnterDetail::getEnterId, id);

    return this.list(detailLambdaQueryWrapper);
  }

  //#region 其他入库单明细查询数据
  @Override
  public TableDataInfo<StorageEnterDetailComposeVo> selectStorageEnterDetailComposeList(PageQuery pageQuery) {
    IPage<StorageEnterDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageEnterDetail> wrapper = new MPJLambdaWrapper<StorageEnterDetail>()
      .selectAll(StorageEnterDetail.class)
      .select(StorageEnter::getEnterCode,StorageEnter::getEnterStatus,
        StorageEnter::getConsignorName, StorageEnter::getProviderShortName, StorageEnter::getStorageName, StorageEnter::getSourceCode,
        StorageEnter::getApplyDate, StorageEnter::getNickName, StorageEnter::getDeptName,StorageEnter::getOrderType,
        StorageEnter::getPayLimitDate)
      .innerJoin(StorageEnter.class, StorageEnter::getEnterId, StorageEnterDetail::getEnterId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageEnterDetail.class, StorageEnter.class);

    IPage<StorageEnterDetailComposeVo> page = this.selectJoinListPage(ipage, StorageEnterDetailComposeVo.class, wrapper);
    TableDataInfo<StorageEnterDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
