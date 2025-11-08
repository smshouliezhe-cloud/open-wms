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
import com.yiruantong.inventory.domain.operation.StorageValidAdjust;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageValidAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageValidAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageValidAdjustDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 效期信息调整明细Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageValidAdjustDetailServiceImpl extends ServiceImplPlus<StorageValidAdjustDetailMapper, StorageValidAdjustDetail, StorageValidAdjustDetailVo, StorageValidAdjustDetailBo> implements IStorageValidAdjustDetailService {
  private final IDataAuthService dataAuthService;

  @Override
  public List<StorageValidAdjustDetail> selectListByMainId(Long id) {
    LambdaQueryWrapper<StorageValidAdjustDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageValidAdjustDetail::getValidAdjustId, id);

    return this.list(detailLambdaQueryWrapper);
  }

  //#region 效期信息调整明细查询数据
  @Override
  public TableDataInfo<StorageValidAdjustDetailComposeVo> selectValidAdjustDetailComposeList(PageQuery pageQuery) {
    IPage<StorageValidAdjustDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<StorageValidAdjustDetail> wrapper = new MPJLambdaWrapper<StorageValidAdjustDetail>()
      .selectAll(StorageValidAdjustDetail.class)
      .select(StorageValidAdjust::getValidAdjustCode,StorageValidAdjust::getAdjustStatus,
        StorageValidAdjust::getConsignorName, StorageValidAdjust::getStorageName, StorageValidAdjust::getAdjustDate,
        StorageValidAdjust::getRemark,StorageValidAdjust::getNickName)
      .innerJoin(StorageValidAdjust.class, StorageValidAdjust::getValidAdjustId, StorageValidAdjustDetail::getValidAdjustId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, StorageValidAdjustDetail.class, StorageValidAdjust.class);

    IPage<StorageValidAdjustDetailComposeVo> page = this.selectJoinListPage(ipage, StorageValidAdjustDetailComposeVo.class, wrapper);
    TableDataInfo<StorageValidAdjustDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
