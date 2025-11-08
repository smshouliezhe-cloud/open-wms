package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.in.InEnterStatusEnum;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.bo.InEnterDetailBo;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailVo;
import com.yiruantong.inbound.mapper.in.InEnterDetailMapper;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 入库管理明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@RequiredArgsConstructor
@Service
public class InEnterDetailServiceImpl extends ServiceImplPlus<InEnterDetailMapper, InEnterDetail, InEnterDetailVo, InEnterDetailBo> implements IInEnterDetailService {
  private final IDataAuthService dataAuthService;

  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  public List<InEnterDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<InEnterDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InEnterDetail::getEnterId, mainId);

    return this.list(detailLambdaQueryWrapper);
  }

  /**
   * 获取入库单明细合计数量，跨过个入库单
   *
   * @param orderId       预到货单ID
   * @param orderDetailId 预到货单明细ID
   * @return 入库单明细合计数量
   */
  @Override
  public BigDecimal getEnterQuantity(Long orderId, Long orderDetailId) {
    MPJLambdaWrapper<InEnterDetail> detailLambdaQueryWrapper = new MPJLambdaWrapper<>();
    detailLambdaQueryWrapper.selectSum(InEnterDetail::getEnterQuantity)
      .eq(InEnterDetail::getOrderId, orderId)
      .eq(InEnterDetail::getOrderDetailId, orderDetailId)
      .innerJoin(InEnter.class, InEnter::getEnterId, InEnterDetail::getEnterId)
      .ne(InEnter::getEnterStatus, InEnterStatusEnum.CANCEL.getName());
    Map<String, Object> map = this.getMap(detailLambdaQueryWrapper);

    return ObjectUtil.isNull(map) ? BigDecimal.ZERO : Convert.toBigDecimal(map.get("enterQuantity"));
  }

  /**
   * 入库记录明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<InEnterDetailComposeVo> selectInEnterDetailComposeList(PageQuery pageQuery) {
    IPage<InEnterDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<InEnterDetail> wrapper = new MPJLambdaWrapper<InEnterDetail>()
      .select(InEnter::getEnterCode, InEnter::getOrderCode,
        InEnter::getConsignorName, InEnter::getProviderShortName, InEnter::getStorageName, InEnter::getSourceCode,
        InEnter::getTrackingNumber, InEnter::getShelveStatus, InEnter::getNickName, InEnter::getEnterStatus,
        InEnter::getOrderType, InEnter::getLpnCode, InEnter::getDeptName, InEnter::getScanInType, InEnter::getRemark,
        InEnter::getApplyDate)
      .selectAll(InEnterDetail.class)
      .select("t.remark as detailRemark")
      .innerJoin(InEnter.class, InEnter::getOrderId, InEnterDetail::getOrderId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, InEnterDetail.class, InEnter.class);

    IPage<InEnterDetailComposeVo> page = this.selectJoinListPage(ipage, InEnterDetailComposeVo.class, wrapper);
    TableDataInfo<InEnterDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  @Override
  public InEnterDetail getBySourceId(Long orderId, Long orderDetailId) {
    LambdaQueryWrapper<InEnterDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(InEnterDetail::getOrderId, orderId)
      .eq(InEnterDetail::getOrderDetailId, orderDetailId);
    return this.getOnly(lambdaQueryWrapper);
  }

  @Override
  public List<InEnterDetail> getListBySourceId(Long orderId, Long orderDetailId) {
    LambdaQueryWrapper<InEnterDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(InEnterDetail::getOrderId, orderId)
      .eq(InEnterDetail::getOrderDetailId, orderDetailId);
    return this.list(lambdaQueryWrapper);
  }
}
