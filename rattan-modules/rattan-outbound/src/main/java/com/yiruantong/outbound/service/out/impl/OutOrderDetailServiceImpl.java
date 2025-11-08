package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.enums.out.OutSourceTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutOrderDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailVo;
import com.yiruantong.outbound.mapper.out.OutOrderDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 销售订单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class OutOrderDetailServiceImpl extends ServiceImplPlus<OutOrderDetailMapper, OutOrderDetail, OutOrderDetailVo, OutOrderDetailBo> implements IOutOrderDetailService {
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IDataAuthService dataAuthService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  //#region 根据主表ID获取明细集合

  /**
   * 根据主表ID获取明细集合
   *
   * @param orderId 出库单ID
   * @return 返回明细集合
   */
  public List<OutOrderDetail> selectListByMainId(Long orderId) {
    LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderId, orderId);

    return this.list(detailLambdaQueryWrapper);
  }
  //#endregion

  //#region 更新缺货数量
  @Override
  public boolean updateLackStorage(Long detailId, BigDecimal placeholderStorage) {
    var detailInfo = this.getById(detailId);
    LambdaUpdateWrapper<OutOrderDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    placeholderStorage = Optional.ofNullable(placeholderStorage).orElse(BigDecimal.ZERO);
    detailLambdaUpdateWrapper.set(OutOrderDetail::getLackStorage, placeholderStorage)
      .eq(OutOrderDetail::getOrderDetailId, detailId);
    return this.update(detailLambdaUpdateWrapper);
  }
  //#endregion

  //#region 获取明细拆分数据 selectDetailSplitList
  @Override
  public List<OutOrderDetailComposeVo> selectDetailSplitList(Map<String, Object> map) {
    Long orderId = Convert.toLong(map.get("orderId"));

    MPJLambdaWrapper<OutOrderDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper.selectAll(OutOrderDetail.class)
      .select(OutOrder::getOrderCode, OutOrder::getNickName, OutOrder::getDeptId, OutOrder::getDeptName, OutOrder::getCreateTime, OutOrder::getClientCode, OutOrder::getClientShortName, OutOrder::getClientId, OutOrder::getStorageId, OutOrder::getStorageName, OutOrder::getRate, OutOrder::getOrderPackage, OutOrder::getOrderStatus, OutOrder::getSortingStatus, OutOrder::getAuditing, OutOrder::getAuditDate, OutOrder::getAuditing, OutOrder::getAuditor, OutOrder::getStoreOrderCode, OutOrder::getOrderChannel, OutOrder::getStoreName, OutOrder::getShippingAddress, OutOrder::getCityName, OutOrder::getRegionName, OutOrder::getProvinceName, OutOrder::getCountryName, OutOrder::getCountryFullName, OutOrder::getPostCode, OutOrder::getExpressCode, OutOrder::getExpressCorpName, OutOrder::getTelephone, OutOrder::getBillingName, OutOrder::getOrderType, OutOrder::getShippingName, OutOrder::getConsignorName, OutOrder::getMobile, OutOrder::getUserId, OutOrder::getExternalNo, OutOrder::getExpressCorpType, OutOrder::getStreet, OutOrder::getEmail, OutOrder::getFax, OutOrder::getShippingAmount)
      .select(BaseProduct::getSalePrice, BaseProduct::getNetWeight, BaseProduct::getProviderShortName, BaseProduct::getProviderId, BaseProduct::getProviderCode, BaseProduct::getTypeName)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId)
      .leftJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderDetail::getProductId)
      .eq(OutOrder::getOrderId, orderId);

    return this.selectJoinList(OutOrderDetailComposeVo.class, wrapper);
  }

  //#endregion
  //#region 拆分订单(确认)
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Void splitOrder(Map<String, Object> map, LoginUser loginUser) {
    Long orderId = Convert.toLong(map.get("orderId"));
    // 查询出库单
    LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(OutOrder::getOrderId, orderId);

    IOutOrderService bean = SpringUtils.getBean(IOutOrderService.class);


    IOutOrderSortingService outOrderSortingService = SpringUtils.getBean(IOutOrderSortingService.class);
    OutOrder outOrder = bean.getOne(orderLambdaQueryWrapper);

    // 待审核、审核成功、拣货中
    String[] statusList = {OutOrderStatusEnum.AUDIT_WAITING.getName(), OutOrderStatusEnum.AUDIT_SUCCESS.getName(), OutOrderStatusEnum.PICKING.getName()};
    Assert.isFalse(Arrays.stream(statusList).noneMatch(m -> B.isEqual(m, outOrder.getOrderStatus())), "只有待审核、审核成功、拣货中的才允许操作！");

    // 拆分后新的出库单
    var newOrderInfo = new OutOrder();
    BeanUtil.copyProperties(outOrder, newOrderInfo);
    String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1671, loginUser.getTenantId());
    newOrderInfo.setOrderCode(orderCode);
    newOrderInfo.setOrderId(null);
    newOrderInfo.setOrderStatus(outOrder.getOrderStatus());
    newOrderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
    if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_SUCCESS.getName())) {
      // newOrderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
      newOrderInfo.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
    }
    newOrderInfo.setCreateTime(new Date());
    newOrderInfo.setSourceCode(outOrder.getOrderCode());
    newOrderInfo.setOrderType(OutOrderTypeEnum.OUT_SPLIT.getName());
    newOrderInfo.setSourceType(OutSourceTypeEnum.SPLIT_CREATE.getName());
    bean.save(newOrderInfo);


    List<OutOrderDetail> valueList = Convert.toList(OutOrderDetail.class, map.get("productList"));
    for (var detail : valueList) {
      // 查到当前明细数据
      LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
      OutOrderDetail outOrderDetail = this.baseMapper.selectOne(detailLambdaQueryWrapper);

      BigDecimal surplusQty = BigDecimal.ZERO; // 需要消减的占位数量（需要转到新订单明细下的占位数量）

      BigDecimal lackStorage = outOrderDetail.getLackStorage();
      if (B.isEqual(lackStorage)) {
        lackStorage = BigDecimal.valueOf(0);
      }
      // 如果单据数量>缺货数量
      if (outOrderDetail.getQuantityOrder().compareTo(lackStorage) > 0) {
        surplusQty = B.sub(detail.getQuantityOrder(), outOrderDetail.getLackStorage());
      }

      // 如果选中的拆分数量大于等于实际数量
      if (B.isGreaterOrEqual(detail.getQuantityOrder(), outOrderDetail.getQuantityOrder())) {
        // 如果数量大于源单数量，就直接把当条明细数据转到新单下
        this.baseMapper.update(null,
          new LambdaUpdateWrapper<OutOrderDetail>()
            .set(OutOrderDetail::getOrderId, newOrderInfo.getOrderId())
            .eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId()));

        // 如果单据状态为待审核就不走里面
        if (!StringUtils.equals(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_WAITING.getName())) {
          // 修改占位 (源单占位改成新单占位)
          LambdaUpdateWrapper<CoreInventoryHolder> updateWrapper = new UpdateWrapper<CoreInventoryHolder>().lambda();
          updateWrapper
            .set(CoreInventoryHolder::getMainId, newOrderInfo.getOrderId())
            .set(CoreInventoryHolder::getBillCode, newOrderInfo.getOrderCode())
            .eq(CoreInventoryHolder::getDetailId, detail.getOrderDetailId());
          coreInventoryHolderService.update(updateWrapper);
        }

        continue;
      } else {
        // 新增一条明细
        var newDetailInfo = new OutOrderDetail();
        BeanUtil.copyProperties(outOrderDetail, newDetailInfo);
        newDetailInfo.setOrderDetailId(null);
        //建立关系，设置主表ID
        newDetailInfo.setOrderId(newOrderInfo.getOrderId());
        newDetailInfo.setQuantityOrder(detail.getQuantityOrder());
        // 销售金额 = 数量*单价
        newDetailInfo.setSaleAmount(B.mul(detail.getQuantityOrder(), outOrderDetail.getSalePrice()));
        // 价税合计 = 数量*单价*税率
        newDetailInfo.setRateAmount(B.mul(detail.getQuantityOrder(), outOrderDetail.getRatePrice()));
        // 小计毛重 = 数量*重量
        newDetailInfo.setRowWeight(B.mul(detail.getQuantityOrder(), outOrderDetail.getWeight()));
        // 合计重量(吨) = 数量*重量
        newDetailInfo.setRowWeightTon(B.div(B.mul(detail.getQuantityOrder(), outOrderDetail.getWeight()), new BigDecimal(1000)));
        // 小计体积 = 数量*单位体积
        newDetailInfo.setRowCube(B.mul(detail.getQuantityOrder(), outOrderDetail.getUnitCube()));
        // 大单位数量 = 数量/换算关系
        newDetailInfo.setBigQty(B.div(detail.getQuantityOrder(), outOrderDetail.getUnitConvert()));

        // 小计净重 = 数量*单位净重
        newDetailInfo.setRowNetWeight(B.mul(detail.getQuantityOrder(), outOrderDetail.getNetWeight()));

        newDetailInfo.setRowLogisticsWeight(B.mul(detail.getQuantityOrder(), outOrderDetail.getLogisticsWeightTon()));
        this.save(newDetailInfo);


        if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_WAITING.getName())) {
          // 修改源单明细数据
          LambdaUpdateWrapper<OutOrderDetail> updateWrapper = new UpdateWrapper<OutOrderDetail>().lambda();
          updateWrapper.set(OutOrderDetail::getQuantityOrder, B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()))
            .set(OutOrderDetail::getSaleAmount, B.mul(outOrderDetail.getSalePrice(), B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder())))
            .set(OutOrderDetail::getRateAmount, B.mul(outOrderDetail.getRatePrice(), B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder())))
            .set(OutOrderDetail::getRowWeight, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getWeight()))
            .set(OutOrderDetail::getRowWeightTon, B.div(B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getWeight()), new BigDecimal(1000)))
            .set(OutOrderDetail::getRowCube, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getUnitCube()))
            .set(OutOrderDetail::getBigQty, B.div(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getUnitConvert()))
            .set(OutOrderDetail::getRowNetWeight, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getNetWeight()))
            .set(OutOrderDetail::getRowLogisticsWeight, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getLogisticsWeightTon()))
            .eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
          this.update(updateWrapper);
          continue;
        }
        // 获取占位
        List<CoreInventoryHolder> holderList = coreInventoryHolderService.selectHolderList(orderId, detail.getOrderDetailId(), HolderSourceTypeEnum.OUT_ORDER_NORMAL);

        for (var item : holderList) {
          // 如果surplusQty小于等于0
          if (B.isLessOrEqual(surplusQty)) {
            break;
          }

          // 需要转移的占位数量>当前循环占位数量
          if (B.isGreater(surplusQty, item.getHolderStorage())) {
            surplusQty = B.sub(surplusQty, item.getHolderStorage());

            // 当前占位行占位数量<=SurplusQty(需要转到新订单明细下的占位数量)时，直接转移占位到新订单明细下
            LambdaUpdateWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new LambdaUpdateWrapper<>();
            inventoryMPJLambdaWrapper
              .set(CoreInventoryHolder::getMainId, newOrderInfo.getOrderId())
              .set(CoreInventoryHolder::getBillCode, newOrderInfo.getOrderCode())
              .set(CoreInventoryHolder::getDetailId, newDetailInfo.getOrderDetailId())
              .eq(CoreInventoryHolder::getHolderId, item.getHolderId());
            coreInventoryHolderService.update(inventoryMPJLambdaWrapper);

            // 拣货中的出库单，要重新计算波次单 订单数量和状态
            LambdaUpdateWrapper<OutOrderWaveDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            detailLambdaUpdateWrapper
              .set(OutOrderWaveDetail::getQuantityOrder, BigDecimal.ZERO)
              .eq(OutOrderWaveDetail::getOrderId, orderId)
              .eq(OutOrderWaveDetail::getHolderId, item.getHolderId());
            outOrderWaveDetailService.update(detailLambdaUpdateWrapper);
          } else {
            // 构建DTO数据
            CommonMainDto mainInfo = BeanUtil.copyProperties(outOrder, CommonMainDto.class);
            mainInfo.setMainId(newOrderInfo.getOrderId());
            mainInfo.setMainCode(newOrderInfo.getOrderCode());
            CommonDetailDto detailInfo = BeanUtil.copyProperties(item, CommonDetailDto.class);
            detailInfo.setMainId(newOrderInfo.getOrderId());
            detailInfo.setDetailId(newDetailInfo.getOrderDetailId());
            detailInfo.setBillCode(newOrderInfo.getOrderCode());
            detailInfo.setSourceType(HolderSourceTypeEnum.OUT_ORDER_NORMAL.getName());
            detailInfo.setPositionNameOut(item.getPositionName());
            mainInfo.setHolderSourceType(HolderSourceTypeEnum.OUT_ORDER_NORMAL);
            // 生成占位
            InventorySortTypeEnum inventorySortTypeEnum = InventorySortTypeEnum.matchingEnum(item.getSortType());
            coreInventoryHolderService.createHolder(mainInfo, detailInfo, surplusQty, inventorySortTypeEnum);

            // 更新原来明细剩余的数量
            LambdaUpdateWrapper<CoreInventoryHolder> holderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            holderLambdaUpdateWrapper
              .set(CoreInventoryHolder::getHolderStorage, B.sub(item.getHolderStorage(), surplusQty))
              .eq(CoreInventoryHolder::getHolderId, item.getHolderId());
            coreInventoryHolderService.update(holderLambdaUpdateWrapper);

            // 查到当前明细数据
            LambdaQueryWrapper<OutOrderWaveDetail> outOrderWaveDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            outOrderWaveDetailLambdaQueryWrapper.eq(OutOrderWaveDetail::getOrderId, orderId)
              .eq(OutOrderWaveDetail::getHolderId, item.getHolderId());
            OutOrderWaveDetail waveDetail = outOrderWaveDetailService.getOne(outOrderWaveDetailLambdaQueryWrapper);

            if (ObjectUtil.isNotNull(waveDetail)) {
              // 拣货中的出库单，要重新计算波次单 订单数量和状态
              LambdaUpdateWrapper<OutOrderWaveDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
              detailLambdaUpdateWrapper
                .set(OutOrderWaveDetail::getQuantityOrder, B.sub(waveDetail.getQuantityOrder(), surplusQty))
                .eq(OutOrderWaveDetail::getOrderId, orderId)
                .eq(OutOrderWaveDetail::getHolderId, item.getHolderId());
            }

            surplusQty = BigDecimal.ZERO;

          }
        }
      }
      // 修改源单明细数据
      LambdaUpdateWrapper<OutOrderDetail> updateWrapper = new UpdateWrapper<OutOrderDetail>().lambda();
      updateWrapper.set(OutOrderDetail::getQuantityOrder, B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()))
        .set(OutOrderDetail::getSaleAmount, B.mul(outOrderDetail.getSalePrice(), B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder())))
        .set(OutOrderDetail::getRateAmount, B.mul(outOrderDetail.getRatePrice(), B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder())))
        .set(OutOrderDetail::getRowWeight, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getWeight()))
        .set(OutOrderDetail::getRowWeightTon, B.div(B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getWeight()), new BigDecimal(1000)))
        .set(OutOrderDetail::getRowCube, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getUnitCube()))
        .set(OutOrderDetail::getBigQty, B.div(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getUnitConvert()))
        .set(OutOrderDetail::getRowNetWeight, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getNetWeight()))
        .set(OutOrderDetail::getRowLogisticsWeight, B.mul(B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder()), outOrderDetail.getLogisticsWeightTon()))
        .eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
      this.update(updateWrapper);

      //      if (StringUtils.equals(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_WAITING.getName())) {
      //        // 分拣出库单
      //        outOrderSortingService.transfter(orderId, false);
      //      }
    }

    // 获取新单的明细数据
    LambdaQueryWrapper<OutOrderDetail> queryWrapperDetail = new LambdaQueryWrapper<>();
    queryWrapperDetail.eq(OutOrderDetail::getOrderId, newOrderInfo.getOrderId());
    List<OutOrderDetail> outOrderDetails = this.list(queryWrapperDetail);
    // 更新新单数据
    if (!outOrderDetails.isEmpty()) {
      // 合计销售金额
      BigDecimal totalAmount = outOrderDetails.stream().map(OutOrderDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计数量
      BigDecimal quantityOrder = outOrderDetails.stream().map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计重量
      BigDecimal rowWeight = outOrderDetails.stream().map(OutOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 含税金额
      BigDecimal taxAmount = outOrderDetails.stream().map(OutOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计体积
      BigDecimal totalCube = outOrderDetails.stream().map(OutOrderDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 大单位数量
      BigDecimal bigQtyTotal = outOrderDetails.stream().map(OutOrderDetail::getBigQty).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计净重
      BigDecimal totalNetWeight = outOrderDetails.stream().filter(f -> B.isGreater(f.getRowNetWeight())).map(OutOrderDetail::getRowNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalLogisticsWeight = outOrderDetails.stream().filter(f -> B.isGreater(f.getRowLogisticsWeight())).map(OutOrderDetail::getRowLogisticsWeight).reduce(BigDecimal.ZERO, BigDecimal::add);


      // 更新新单主表数据
      LambdaUpdateWrapper<OutOrder> lambda = new UpdateWrapper<OutOrder>().lambda();
      lambda.set(OutOrder::getTotalAmount, totalAmount)
        .set(OutOrder::getTotalWeight, rowWeight)
        .set(OutOrder::getTotalQuantityOrder, quantityOrder)
        .set(OutOrder::getTaxAmount, taxAmount)
        .set(OutOrder::getTotalCube, totalCube)
        .set(OutOrder::getTotalUnpaid, B.sub(taxAmount, outOrder.getDiscountAmount())) // 本单应收
        .set(OutOrder::getBigQtyTotal, bigQtyTotal)
        .set(OutOrder::getTotalNetWeight, totalNetWeight)
        .set(OutOrder::getTotalLogisticsWeight, totalLogisticsWeight)
        .eq(OutOrder::getOrderId, newOrderInfo.getOrderId());
      bean.update(lambda);//修改
    }

    // 获取旧单的明细数据
    LambdaQueryWrapper<OutOrderDetail> queryWrapperOldDetail = new LambdaQueryWrapper<>();
    queryWrapperOldDetail.eq(OutOrderDetail::getOrderId, orderId);
    List<OutOrderDetail> outOrderOldDetail = this.list(queryWrapperOldDetail);
    // 更新旧单数据
    if (!outOrderOldDetail.isEmpty()) {
      // 合计销售金额
      BigDecimal totalAmount = outOrderOldDetail.stream().map(OutOrderDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计数量
      BigDecimal quantityOrder = outOrderOldDetail.stream().map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计重量
      BigDecimal rowWeight = outOrderOldDetail.stream().map(OutOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 含税金额
      BigDecimal taxAmount = outOrderOldDetail.stream().map(OutOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计体积
      BigDecimal totalCube = outOrderOldDetail.stream().map(OutOrderDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 大单位数量
      BigDecimal bigQtyTotal = outOrderOldDetail.stream().map(OutOrderDetail::getBigQty).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计净重
      BigDecimal totalNetWeight = outOrderOldDetail.stream().filter(f -> B.isGreater(f.getRowNetWeight())).map(OutOrderDetail::getRowNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalLogisticsWeight = outOrderOldDetail.stream().filter(f -> B.isGreater(f.getRowLogisticsWeight())).map(OutOrderDetail::getRowLogisticsWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

      // 更新旧单主表数据
      LambdaUpdateWrapper<OutOrder> lambda = new UpdateWrapper<OutOrder>().lambda();
      lambda.set(OutOrder::getTotalAmount, totalAmount)
        .set(OutOrder::getTotalWeight, rowWeight)
        .set(OutOrder::getTotalQuantityOrder, quantityOrder)
        .set(OutOrder::getTaxAmount, taxAmount)
        .set(OutOrder::getTotalCube, totalCube)
        .set(OutOrder::getTotalUnpaid, B.sub(taxAmount, outOrder.getDiscountAmount())) // 本单应收
        .set(OutOrder::getBigQtyTotal, bigQtyTotal)
        .set(OutOrder::getTotalNetWeight, totalNetWeight)
        .set(OutOrder::getTotalLogisticsWeight, totalLogisticsWeight)
        .set(OutOrder::getSortingStatus, SortingStatusEnum.NONE.getId())//改为未分配 下面重新走分拣
        .eq(OutOrder::getOrderId, orderId);
      bean.update(lambda);//修改
    }


    //    if(B.isEqual(OutOrderStatusEnum.AUDIT_WAITING.getName(), outOrder.getOrderStatus())){
    //      return null;
    //
    //    }
    // 新单状态的处理
    if (B.isGreater(Convert.toBigDecimal(newOrderInfo.getOrderId()))) {

      if (!StringUtils.equals(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_WAITING.getName())) {
        for (var detail : valueList) {
          // 查到当前明细数据
          LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
          detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
          OutOrderDetail outOrderDetail = this.baseMapper.selectOne(detailLambdaQueryWrapper);

          // 获取源表占位数据
          List<CoreInventoryHolder> coreInventoryHolders = coreInventoryHolderService.selectHolderList(orderId, detail.getOrderDetailId(), HolderSourceTypeEnum.OUT_ORDER_NORMAL);
          if (coreInventoryHolders.stream().count() >= 1) {
            // 初始占位数据合
            BigDecimal orignHolderStorage = coreInventoryHolders.stream().map(CoreInventoryHolder::getOrignHolderStorage).reduce(BigDecimal.ZERO, BigDecimal::add);

            // 源单明细数量
            BigDecimal QuantityOldOrder = B.sub(outOrderDetail.getQuantityOrder(), detail.getQuantityOrder());

            // 更新新单明细数据 （缺货数量= 数量-占位量）
            LambdaUpdateWrapper<OutOrderDetail> lambda = new UpdateWrapper<OutOrderDetail>().lambda();
            lambda.set(OutOrderDetail::getLackStorage, B.sub(QuantityOldOrder, orignHolderStorage))
              .eq(OutOrderDetail::getOrderId, outOrder.getOrderId());
            this.update(lambda);//修改

            // 更新新单明细数据 (缺货数量<0  更新缺货数量0)
            LambdaUpdateWrapper<OutOrderDetail> lambda1 = new UpdateWrapper<OutOrderDetail>().lambda();
            lambda1.set(OutOrderDetail::getLackStorage, BigDecimal.ZERO)
              .eq(OutOrderDetail::getOrderId, outOrder.getOrderId())
              .le(OutOrderDetail::getLackStorage, BigDecimal.ZERO);
            this.update(lambda1);//修改
          }
        }

      }
      // 获取新单的明细数据
      //      LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      //      detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderId, newOrderInfo.getOrderId());
      //      List<OutOrderDetail> outOrderDetails1 = this.list(detailLambdaQueryWrapper);
      //      if (outOrderDetails1.stream().count() >= 1) {
      //        // 缺货数量
      //        BigDecimal lackStorage = outOrderDetails1.stream().map(OutOrderDetail::getLackStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
      //
      //        if(B.isGreater(lackStorage)){
      //          // 更新新单主表数据（3 缺货中）
      //          LambdaUpdateWrapper<OutOrder> lambda = new UpdateWrapper<OutOrder>().lambda();
      //          lambda.set(OutOrder::getSortingStatus, 3)
      //            .eq(OutOrder::getOrderId, newOrderInfo.getOrderId());
      //          bean.update(lambda);//修改
      //        }
      //        else{
      //          // 更新新单主表数据（2 已分配）
      //          LambdaUpdateWrapper<OutOrder> lambda = new UpdateWrapper<OutOrder>().lambda();
      //          lambda.set(OutOrder::getSortingStatus, 2)
      //            .eq(OutOrder::getOrderId, newOrderInfo.getOrderId());
      //          bean.update(lambda);//修改
      //        }
      //
      //      }


      // 分拣出库单
      //      outOrderSortingService.transfter(orderId, false);
    }


    // 如果状态等于拣货中
    if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.PICKING.getName())) {
      // 更新新单主表数据
      LambdaUpdateWrapper<OutOrder> lambda = new UpdateWrapper<OutOrder>().lambda();
      lambda.set(OutOrder::getOrderStatus, OutOrderStatusEnum.AUDIT_SUCCESS.getName())
        .eq(OutOrder::getOrderId, newOrderInfo.getOrderId());
      bean.update(lambda);//修改


      for (var detail : valueList) {
        // 获取新单的明细数据
        LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderId, newOrderInfo.getOrderId());
        OutOrderDetail outOrderDetail = this.getOne(detailLambdaQueryWrapper);
        // 更新波次明细的数量
        LambdaUpdateWrapper<OutOrderWaveDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
          .set(OutOrderWaveDetail::getQuantityOrderOrigin, outOrderDetail.getQuantityOrder())
          .eq(OutOrderWaveDetail::getOrderId, Convert.toLong(map.get("orderId")))
          .eq(OutOrderWaveDetail::getOrderDetailId, detail.getOrderDetailId());
        outOrderWaveDetailService.update(updateWrapper);
      }
    }
    // 只有源单是审核成功才会走分拣
    if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_SUCCESS.getName())) {
      // 分拣出库单
      outOrderSortingService.sorting(orderId, false);

      // 分拣新出库单
      outOrderSortingService.sorting(newOrderInfo.getOrderId(), false);
    }


    newOrderInfo.setOrderStatus(null);
    // 生成新建出库单的轨迹
    if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_SUCCESS.getName())) {
      outOrderStatusHistoryService.AddHistory(newOrderInfo, OutOperationTypeEnum.SPLIT, OutOrderStatusEnum.AUDIT_SUCCESS, outOrder.getOrderCode());
    } else if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_WAITING.getName())) {
      outOrderStatusHistoryService.AddHistory(newOrderInfo, OutOperationTypeEnum.SPLIT, OutOrderStatusEnum.AUDIT_WAITING, outOrder.getOrderCode());
    }


    return null;
  }

  /**
   * 出库订单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<OutOrderDetailComposeVo> selectOutOrderDetailComposeList(PageQuery pageQuery) {
    IPage<OutOrderDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<OutOrderDetail> wrapper = new MPJLambdaWrapper<OutOrderDetail>()
//      .selectAll(OutOrder.class)
      .select(OutOrder::getOrderCode, OutOrder::getOrderType, OutOrder::getOrderWaveCode, OutOrder::getNickName,
        OutOrder::getDeptName, OutOrder::getRemark, OutOrder::getShippingName, OutOrder::getConsignorCode,
        OutOrder::getConsignorName, OutOrder::getTelephone, OutOrder::getShippingAddress, OutOrder::getPostCode,
        OutOrder::getCountryName, OutOrder::getProvinceName, OutOrder::getCityName, OutOrder::getRegionName,
        OutOrder::getFax, OutOrder::getStreet, OutOrder::getEmail, OutOrder::getExpressCode, OutOrder::getExpressCorpType,
        OutOrder::getExpressCorpName, OutOrder::getLineName, OutOrder::getDistributionType, OutOrder::getShippingMethod,
        OutOrder::getStoreOrderCode, OutOrder::getClientShortName, OutOrder::getSortingStatus, OutOrder::getOrderStatus,
        OutOrder::getSourceCode, OutOrder::getStorageName, OutOrder::getConsignorCodeSale, OutOrder::getConsignorNameSale, OutOrder::getConsignorIdSale
        , OutOrder::getDeliveryDate, OutOrder::getArriveDate)
      .selectAll(OutOrderDetail.class)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, OutOrderDetail.class, OutOrder.class);

    IPage<OutOrderDetailComposeVo> page = this.selectJoinListPage(ipage, OutOrderDetailComposeVo.class, wrapper);
    TableDataInfo<OutOrderDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    //#region 计算合计数量
    if (!pageQuery.getSumColumnNames().isEmpty()) {
      MPJLambdaWrapper<OutOrderDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        detailMPJLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      detailMPJLambdaWrapper
        .innerJoin(OutOrder.class, on -> {
          on.eq(OutOrderDetail::getOrderId, OutOrder::getOrderId);
          return on;
        });

      // 拼接查询条件
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), detailMPJLambdaWrapper, OutOrderDetail.class, OutOrder.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(detailMPJLambdaWrapper);
      tableDataInfoV.setFooter(maps);
    }
    return tableDataInfoV;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveDetailTask(List<OutScanDetailBo> dataList) {

    for (OutScanDetailBo scanDetail : dataList) {
      String unitSpec = Convert.toStr(scanDetail.getExpandFields().get("unitSpec"));
      String specConvert = Convert.toStr(scanDetail.getExpandFields().get("specConvert"));
      LambdaUpdateWrapper<OutOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrderDetail::getQuantityOrder, scanDetail.getQuantityOrder())
        .setSql("expand_fields = json_set(expand_fields,'$.unitSpec', '" + unitSpec + "')")
        .setSql("expand_fields = json_set(expand_fields,'$.specConvert', '" + specConvert + "')")
        .set(OutOrderDetail::getBigQty, scanDetail.getBigQty())
        .eq(OutOrderDetail::getOrderDetailId, scanDetail.getOrderDetailId())
        .eq(OutOrderDetail::getOrderId, scanDetail.getOrderId());
      this.update(updateWrapper);
    }
    return R.ok("修改成功");
  }

}
