package com.yiruantong.inbound.service.in.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InDamagedOrder;
import com.yiruantong.inbound.domain.in.InDamagedOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InDamagedOrderDetailBo;
import com.yiruantong.inbound.domain.in.vo.InDamagedDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InDamagedOrderDetailVo;
import com.yiruantong.inbound.mapper.in.InDamagedOrderDetailMapper;
import com.yiruantong.inbound.service.in.IInDamagedOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 残品入库单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
@RequiredArgsConstructor
@Service
public class InDamagedOrderDetailServiceImpl extends ServiceImplPlus<InDamagedOrderDetailMapper, InDamagedOrderDetail, InDamagedOrderDetailVo, InDamagedOrderDetailBo> implements IInDamagedOrderDetailService {
  private final IDataAuthService dataAuthService;

  //#region 残品入库明细查询
  @Override
  public TableDataInfo<InDamagedDetailComposeVo> selectDamagedDetailComposeList(PageQuery pageQuery) {
    dataAuthService.getDataAuth(pageQuery); // 数据权限

    IPage<InDamagedDetailComposeVo> ipage = pageQuery.build();

    MPJLambdaWrapper<InDamagedOrderDetail> wrapper = new MPJLambdaWrapper<InDamagedOrderDetail>()
      .selectAll(InDamagedOrderDetail.class)
      .select(InDamagedOrder::getDamagedOrderCode,
        InDamagedOrder::getConsignorName, InDamagedOrder::getProviderShortName, InDamagedOrder::getStorageName, InDamagedOrder::getDamagedStatus)
//        InDamagedOrder::getDamagedData, InDamagedOrder::getDeptName, InDamagedOrder::getNickName)
      .innerJoin(InDamagedOrder.class, InDamagedOrder::getDamagedOrderId, InDamagedOrderDetail::getDamagedOrderId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, InDamagedOrderDetail.class, InDamagedOrder.class);

    IPage<InDamagedDetailComposeVo> page = this.selectJoinListPage(ipage, InDamagedDetailComposeVo.class, wrapper);
    TableDataInfo<InDamagedDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
  //#endregion
}
