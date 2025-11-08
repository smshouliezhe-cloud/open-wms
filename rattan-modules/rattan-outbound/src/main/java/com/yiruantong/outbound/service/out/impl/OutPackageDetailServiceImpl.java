package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.bo.OutPackageDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailVo;
import com.yiruantong.outbound.mapper.out.OutPackageDetailMapper;
import com.yiruantong.outbound.service.out.IOutPackageDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打包单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-11-07
 */
@RequiredArgsConstructor
@Service
public class OutPackageDetailServiceImpl extends ServiceImplPlus<OutPackageDetailMapper, OutPackageDetail, OutPackageDetailVo, OutPackageDetailBo> implements IOutPackageDetailService {
  private final IDataAuthService dataAuthService;

  //#region 根据主表ID获取明细集合
  @Override
  public List<OutPackageDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<OutPackageDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutPackageDetail::getPackageId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }
  //#endregion

  /**
   * 打包出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<OutPackageDetailComposeVo> selectPackageDetailComposeList(PageQuery pageQuery) {
    IPage<OutPackageDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<OutPackageDetail> wrapper = new MPJLambdaWrapper<OutPackageDetail>()

      .select(OutPackage::getPackageCode, OutPackage::getOrderType,
        OutPackage::getConsignorName, OutPackage::getClientShortName, OutPackage::getStorageName, OutPackage::getSourceCode,
        OutPackage::getExpressCorpName, OutPackage::getExpressCode, OutPackage::getOrderWaveCode, OutPackage::getPackageStatus,
        OutPackage::getStoreOrderCode, OutPackage::getOrderCode, OutPackage::getOrderChannel, OutPackage::getNickName, OutPackage::getShippingName,
        OutPackage::getDeptName, OutPackage::getShippingAddress, OutPackage::getMobile)
      .selectAll(OutPackageDetail.class)
      .innerJoin(OutPackage.class, OutPackage::getOrderId, OutPackageDetail::getOrderId);
    if (pageQuery.getQueryBoList().stream().anyMatch(i -> StrUtil.equals(i.getColumn(), "isShow"))) {
      wrapper.notExists("select 1 from fee_payable_bill_detail d where find_in_set(t.package_detail_id,d.source_detail_id)");
    }
    //取消掉  显示全部  的筛选条件
    pageQuery.setQueryBoList(pageQuery.getQueryBoList().stream().filter(i -> !StrUtil.equals(i.getColumn(), "isShow")).toList());
    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, OutPackageDetail.class, OutPackage.class);

    IPage<OutPackageDetailComposeVo> page = this.selectJoinListPage(ipage, OutPackageDetailComposeVo.class, wrapper);
    TableDataInfo<OutPackageDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
}
