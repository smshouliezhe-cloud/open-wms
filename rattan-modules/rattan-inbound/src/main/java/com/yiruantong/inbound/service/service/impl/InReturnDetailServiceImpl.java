package com.yiruantong.inbound.service.service.impl;

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
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.domain.service.bo.InReturnDetailBo;
import com.yiruantong.inbound.domain.service.vo.InReturnDetailComposeVo;
import com.yiruantong.inbound.domain.service.vo.InReturnDetailVo;
import com.yiruantong.inbound.mapper.service.InReturnDetailMapper;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退货管理明细单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InReturnDetailServiceImpl extends ServiceImplPlus<InReturnDetailMapper, InReturnDetail, InReturnDetailVo, InReturnDetailBo> implements IInReturnDetailService {
  private final IDataAuthService dataAuthService;

  /**
   * 到货退货明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<InReturnDetailComposeVo> selectInReturnDetailComposeList(PageQuery pageQuery) {
    IPage<InReturnDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<InReturnDetail> wrapper = new MPJLambdaWrapper<InReturnDetail>()
      .select(InReturn::getReturnCode, InReturn::getOrderCode, InReturn::getReturnStatus,
        InReturn::getConsignorName, InReturn::getProviderShortName, InReturn::getStorageName,
        InReturn::getDeptName, InReturn::getNickName, InReturn::getShippingName, InReturn::getSourceCode,
        InReturn::getShippingAddress, InReturn::getMobile, InReturn::getRemark)
      .selectAll(InReturnDetail.class)
      .innerJoin(InReturn.class, InReturn::getReturnId, InReturnDetail::getReturnId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, InReturnDetail.class, InShelve.class);

    IPage<InReturnDetailComposeVo> page = this.selectJoinListPage(ipage, InReturnDetailComposeVo.class, wrapper);
    TableDataInfo<InReturnDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  @Override
  public List<InReturnDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<InReturnDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InReturnDetail::getReturnId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }
}
