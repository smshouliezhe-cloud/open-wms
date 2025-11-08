package com.yiruantong.outbound.liteflow.QuickOut;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.constant.ProductBarCodeConstants;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageEnterStatusEnum;
import com.yiruantong.common.core.enums.other.XgOrderTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageEnterDetailService;
import com.yiruantong.inventory.service.operation.IStorageEnterService;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.QuickOutContext;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.outbound.service.out.IOutScanOrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@LiteflowComponent(id = "quickOutCmp", name = "4.出库单一键出库")
@RequiredArgsConstructor
public class QuickOutCmp extends NodeComponent {
  private final IOutOrderService outOrderService;
  private final IOutScanOrderService outScanOrderService;
  private final IOutOrderSortingService outOrderSortingService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IStorageEnterService storageEnterService;
  private final IStorageEnterDetailService storageEnterDetailService;
  private final IBaseProviderService baseProviderService;
  private final IBasePositionService basePositionService;
  private final IBaseProductService baseProductService;


  @Override
  public void process() {
    QuickOutContext ctx = this.getContextBean(QuickOutContext.class);

    OutScanMainBo outScanMainBo = ctx.getOutScanMainBo();
    OutOrder outOrder = ctx.getOutOrder();
    if (B.isEqual(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_WAITING.getName())) {
      outOrderService.multiAuditing(List.of(outScanMainBo.getOrderId()));
      // 分拣出库单
      outOrderSortingService.sorting(outScanMainBo.getOrderId(), false);

      // 湘钢特殊处理
      if (B.isGreater(outScanMainBo.getDataList().stream().filter(item -> B.isGreater(item.getParcelQuantity())).toList().size())) {
        this.outCreateHolder(outScanMainBo);
      }
      //开启了溢出处理 湘钢
      if (B.isEqual(outScanMainBo.getSpillover(),EnableEnum.ENABLE.getId())) {
        this.isSpillover(ctx);

      }
    }
    //一键出库处理
    outScanOrderService.normalOutSave(outScanMainBo);
  }

  /**
   * 湘钢
   * 出库单出库类型为 合金/辅料 并且已分配的单据，查询占位的包数是否为空 ，如果为空需要把当前占位的库存里面所有的可用库存 分配到当前出库单中
   *
   * @param outScanMainBo
   */
  private void outCreateHolder(OutScanMainBo outScanMainBo) {
    //查询出库单 看是否分拣成功
    OutOrder outOrder = outOrderService.getById(outScanMainBo.getOrderId());
    if (!B.isEqual(outOrder.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId())) {
      return;
    }
    //只有订单类别为 合金/辅料 才处理
    if (!B.isEqual(outOrder.getOrderType(), XgOrderTypeEnum.ALLOY_AUXILIARY_MATERIAL.getName())) {
      return;
    }
    for (var scanInfo : outScanMainBo.getDataList()) {
      //查询占位信息
      List<CoreInventoryHolder> coreInventoryHolders = coreInventoryHolderService.selectHolderList(scanInfo.getOrderId(), scanInfo.getOrderDetailId(), HolderSourceTypeEnum.ALLOY_AUXILIARY_MATERIALS);
      //查询占位对应的库存 是否包数为0  如果为0 则需要把剩余的库存 占位到当前出库单中
      for (var holder : coreInventoryHolders) {
        CoreInventory coreInventory = coreInventoryService.getById(holder.getInventoryId());
        //如果有效库存为0 则进入下一层循环
        if (B.isEqual(coreInventory.getValidStorage())) {
          continue;
        }
        // 如果库存不为空，并且 包数等于0 则需要把所有的库存让当前占位
        if (ObjectUtil.isNotNull(coreInventory) && B.isEqual(coreInventory.getParcelQuantity())) {
          // 把剩余的有效库存放入到 出库单中

          CommonMainDto mainInfo = BeanUtil.copyProperties(outOrder, CommonMainDto.class);
          mainInfo.setMainId(outOrder.getOrderId());
          mainInfo.setMainCode(outOrder.getOrderCode());
          mainInfo.setHolderSourceType(HolderSourceTypeEnum.ALLOY_AUXILIARY_MATERIALS); // 占位类型
          mainInfo.setStoreOrderCode(outOrder.getStoreOrderCode());
          CommonDetailDto detailInfo = BeanUtil.copyProperties(scanInfo, CommonDetailDto.class);
          detailInfo.setMainId(outOrder.getOrderId());
          detailInfo.setDetailId(scanInfo.getOrderDetailId());
          detailInfo.setBillCode(outOrder.getOrderCode());
          detailInfo.setSourceType(HolderSourceTypeEnum.ALLOY_AUXILIARY_MATERIALS.getName());
          detailInfo.setPositionNameOut(coreInventory.getPositionName());
          // 生成占位
          coreInventoryHolderService.createHolder(mainInfo, detailInfo, coreInventory.getValidStorage(), InventorySortTypeEnum.SCATTERED_SORTING);

          //修改 出库数量
          scanInfo.setFinishedQuantity(B.add(scanInfo.getFinishedQuantity(), coreInventory.getValidStorage()));
          LambdaUpdateWrapper<OutOrderDetail> outOrderDetailLambdaQueryWrapper = new LambdaUpdateWrapper<>();
          outOrderDetailLambdaQueryWrapper.set(OutOrderDetail::getQuantityOrder, scanInfo.getFinishedQuantity())
            .setSql("expand_fields = json_set(expand_fields,'$.inventoryQuantity', " + coreInventory.getValidStorage() + ")")//记录多给出库数量的值
            .eq(OutOrderDetail::getOrderDetailId, scanInfo.getOrderDetailId());
          outOrderDetailService.update(outOrderDetailLambdaQueryWrapper);

        }
      }

    }


  }


  /**
   * 如果开启了溢出 并且单据的实出数量大于库存占位数量则需要进行溢出处理 ( 湘钢 )
   */
  private void isSpillover(QuickOutContext ctx) {

    OutScanMainBo outScanMainBo = ctx.getOutScanMainBo();
    OutOrder outOrder = ctx.getOutOrder();
    List<StorageEnterDetail> details = new ArrayList<>();

    for (var scanInfo : outScanMainBo.getDataList()) {
      BaseProduct baseProduct = baseProductService.getById(scanInfo.getProductId());
      // 只有贵重合金生成 溢出单
      if(!B.isEqual(baseProduct.getProductBarCode(), ProductBarCodeConstants.PRECIOUS_ALLOY)){
        continue;
      }
      // 查询当前扫描明细占位信息
      List<CoreInventoryHolder> coreInventoryHolders = coreInventoryHolderService.selectHolderList(scanInfo.getOrderId(), scanInfo.getOrderDetailId(), HolderSourceTypeEnum.ALLOY_AUXILIARY_MATERIALS);
      //如果未找到占位信息则进入到下一层循环
      if (ObjectUtil.isNull(coreInventoryHolders) || B.isEqual(coreInventoryHolders.size())) {
        continue;
      }
      // 合计当前占位数
      BigDecimal totalQuantity = coreInventoryHolders.stream().map(CoreInventoryHolder::getOrignHolderStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
      //如果当前扫描明细不大于 合计占位明细 则跳过进入下一层循环
      if (!B.isGreater(scanInfo.getFinishedQuantity(), totalQuantity)) {
        continue;
      }
      StorageEnterDetail detail = new StorageEnterDetail();
      BeanUtil.copyProperties(scanInfo, detail);
      detail.setEnterQuantity(B.sub(scanInfo.getFinishedQuantity(), totalQuantity));

      detail.setSourceDetailId("" + scanInfo.getOrderDetailId()); //保存一下当前明细ID
      // 已抵扣数量 默认为0
      Map<String, Object> expandFields = detail.getExpandFields();
      expandFields.put("deductedQuantity", BigDecimal.ZERO);
      detail.setExpandFields(expandFields);
      if (StrUtil.isEmpty(detail.getPositionName())) {
        LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        positionLambdaQueryWrapper.eq(BasePosition::getStorageId, outOrder.getStorageId())
          .eq(BasePosition::getIsLocked, EnableEnum.DISABLE.getId())
          .eq(BasePosition::getPositionType, PositionTypeEnum.NORMAL.getId())
          .last("limit 1");
        BasePosition positionInfo = basePositionService.getOne(positionLambdaQueryWrapper);
        if (ObjectUtil.isNull(positionInfo)) {
          throw new ServiceException(outOrder.getStorageName() + "没有找到可用的货位");
        }
        detail.setPositionName(positionInfo.getPositionName());
      }
      details.add(detail);
      //  修改出库单明细的数量
      LambdaUpdateWrapper<OutOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper
        .set(OutOrderDetail::getQuantityOrder, totalQuantity)
        .setSql("expand_fields = json_set(expand_fields,'$.spilloverQuantity', " +detail.getEnterQuantity() + ")") //溢出数量
        .eq(OutOrderDetail::getOrderDetailId, scanInfo.getOrderDetailId());
      outOrderDetailService.update(updateWrapper);
      //当前扫描出库数量 = 合计 库存数量
      scanInfo.setFinishedQuantity(totalQuantity);
    }

    // 如果开启了溢出， 并且明细 实出数量 >  预计出库数量 ， 则生成一个其他入库单
    StorageEnter dataInfo = new StorageEnter();
    BeanUtil.copyProperties(outOrder, dataInfo);
    dataInfo.setEnterCode(DBUtils.getCodeRegular(MenuEnum.MENU_1043));
    dataInfo.setEnterId(null);
    dataInfo.setSourceCode(outOrder.getOrderCode());
    dataInfo.setSourceId("" + outOrder.getOrderId());
    dataInfo.setSourceType("出库单溢出");
    dataInfo.setTotalEnterQuantity(BigDecimal.ZERO);
    dataInfo.setTotalWeight(BigDecimal.ZERO);
    dataInfo.setTotalAmount(BigDecimal.ZERO);
    dataInfo.setCreateBy(null);
    dataInfo.setCreateByName(null);
    dataInfo.setCreateTime(null);
    dataInfo.setUpdateBy(null);
    dataInfo.setUpdateByName(null);
    dataInfo.setUpdateTime(null);
    dataInfo.setEnterStatus(StorageEnterStatusEnum.NEWED.getName());
    dataInfo.setOrderType("出库单溢出入库");
    LambdaQueryWrapper<BaseProvider> baseProviderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    baseProviderLambdaQueryWrapper.isNotNull(BaseProvider::getProviderShortName)
      .last("limit 1");
    // 随便查询一个供应商
    BaseProvider one = baseProviderService.getOne(baseProviderLambdaQueryWrapper);
    if (ObjectUtil.isNotNull(one)) {
      dataInfo.setProviderId(one.getProviderId());
      dataInfo.setProviderCode(one.getProviderCode());
      dataInfo.setProviderShortName(one.getProviderShortName());
    }
    storageEnterService.save(dataInfo);
    details.forEach(item -> {

      item.setEnterId(dataInfo.getEnterId());
    });
    storageEnterDetailService.saveBatch(details);
    // 调用其他入库单审核功能
    storageEnterService.multiAuditing(new ArrayList<>(Arrays.asList(dataInfo.getEnterId())));

    //清空占位
    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.ALLOY_AUXILIARY_MATERIALS;
    coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), outOrder.getOrderId());
    //修改出库单的分拣状态
    outOrderService.updateSortingStatus(outOrder.getOrderId(), SortingStatusEnum.NONE);
    // 分拣出库单
    outOrderSortingService.sorting(outScanMainBo.getOrderId(), false);

  }
}
