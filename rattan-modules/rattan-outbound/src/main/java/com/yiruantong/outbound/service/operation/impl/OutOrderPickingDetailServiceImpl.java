package com.yiruantong.outbound.service.operation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
import com.yiruantong.outbound.domain.operation.bo.OutOrderPickingDetailBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailComposeVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailVo;
import com.yiruantong.outbound.mapper.operation.OutOrderPickingDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderPickingDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单拣货查询明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-01
 */
@RequiredArgsConstructor
@Service
public class OutOrderPickingDetailServiceImpl extends ServiceImplPlus<OutOrderPickingDetailMapper, OutOrderPickingDetail, OutOrderPickingDetailVo, OutOrderPickingDetailBo> implements IOutOrderPickingDetailService {
  private final IDataAuthService dataAuthService;

  @Override
  public TableDataInfo<OutOrderPickingDetailComposeVo> selectPickingDetailComposeList(PageQuery pageQuery) {
    IPage<OutOrderPickingDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<OutOrderPickingDetail> wrapper = new MPJLambdaWrapper<OutOrderPickingDetail>()
      .select(OutOrderPicking::getOrderPickingCode, OutOrderPicking::getConsignorName, OutOrderPicking::getStorageName,
        OutOrderPicking::getStartDate, OutOrderPicking::getEndDate, OutOrderPicking::getSpanTime, OutOrderPicking::getOrderType,
        OutOrderPicking::getNickName, OutOrderPicking::getPickingStatus, OutOrderPicking::getOrderWaveCode)
      .selectAll(OutOrderPickingDetail.class)
      .innerJoin(OutOrderPicking.class, OutOrderPicking::getOrderPickingId, OutOrderPickingDetail::getOrderPickingId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, OutOrderPickingDetail.class, OutOrderPicking.class);

    IPage<OutOrderPickingDetailComposeVo> page = this.selectJoinListPage(ipage, OutOrderPickingDetailComposeVo.class, wrapper);
    TableDataInfo<OutOrderPickingDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  @Override
  public List<OutOrderPickingDetail> selectListByMainId(Long orderPickingId) {
    LambdaQueryWrapper<OutOrderPickingDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutOrderPickingDetail::getOrderPickingId, orderPickingId);

    return this.list(detailLambdaQueryWrapper);
  }
}
