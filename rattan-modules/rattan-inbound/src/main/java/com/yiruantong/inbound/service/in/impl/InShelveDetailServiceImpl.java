package com.yiruantong.inbound.service.in.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.domain.in.bo.InShelveDetailBo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailVo;
import com.yiruantong.inbound.mapper.in.InShelveDetailMapper;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品上架明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class InShelveDetailServiceImpl extends ServiceImplPlus<InShelveDetailMapper, InShelveDetail, InShelveDetailVo, InShelveDetailBo> implements IInShelveDetailService {
  private final IDataAuthService dataAuthService;

  @Override
  public List<InShelveDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<InShelveDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InShelveDetail::getShelveId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }

  /**
   * 上架记录明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<InShelveDetailComposeVo> selectInShelveDetailComposeList(PageQuery pageQuery) {
    IPage<InShelveDetailComposeVo> ipage = pageQuery.build();

    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<InShelveDetail> wrapper = new MPJLambdaWrapper<InShelveDetail>()
      .select(InShelve::getEnterCode, InShelve::getOrderCode, InShelve::getShelveCode,
        InShelve::getConsignorName, InShelve::getProviderShortName, InShelve::getStorageName,
        InShelve::getSourceCode, InShelve::getTrackingNumber, InShelve::getShelveStatus,
        InShelve::getNickName, InShelve::getOrderType, InShelve::getStartDate,
        InShelve::getEndDate, InShelve::getSpanTime, InShelve::getShelveType, InShelve::getRemark)
      .selectAll(InShelveDetail.class)
      .innerJoin(InShelve.class, InShelve::getShelveId, InShelveDetail::getShelveId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, InShelveDetail.class, InShelve.class);

    IPage<InShelveDetailComposeVo> page = this.selectJoinListPage(ipage, InShelveDetailComposeVo.class, wrapper);
    TableDataInfo<InShelveDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  /**
   * 根据主键id和订单明细id查询
   *
   * @param orderId
   * @param orderDetailId
   * @return
   */
  @Override
  public InShelveDetail getById(Long orderId, Long orderDetailId) {
    LambdaQueryWrapper<InShelveDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(InShelveDetail::getShelveId, orderId)
      .eq(InShelveDetail::getOrderDetailId, orderDetailId);
    return this.getOnly(lambdaQueryWrapper);
  }
}
