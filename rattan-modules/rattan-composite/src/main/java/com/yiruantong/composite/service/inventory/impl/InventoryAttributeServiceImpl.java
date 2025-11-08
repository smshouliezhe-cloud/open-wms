package com.yiruantong.composite.service.inventory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.TransferTypeEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.composite.service.inventory.IInventoryAttributeService;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferDetailBo;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.operation.StoragePositionTransfer;
import com.yiruantong.inventory.domain.operation.StoragePositionTransferDetail;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferDetailService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InventoryAttributeServiceImpl implements IInventoryAttributeService {
  private final ICoreInventoryService coreInventoryService;
  private final IStoragePositionTransferService storagePositionTransferService;
  private final IStoragePositionTransferDetailService storagePositionTransferDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutSortingRuleService outSortingRuleService;
  private final IOutOrderSortingService outOrderSortingService;
  private final IOutScanOrderService outScanOrderService;


  @Override
  @Transactional(rollbackFor = Exception.class)

  public R<Void> attributeConvert(Map<String, Object> map) {
    Long inventoryId = Convert.toLong(map.get("inventoryId"));
    String productAttribute = Convert.toStr(map.get("productAttribute")); // 目标属性
    BigDecimal validStorage = Convert.toBigDecimal(map.get("validStorage")); // 库存量

    CoreInventory coreInventory = coreInventoryService.getBaseMapper().selectById(inventoryId);
    if (ObjectUtil.isEmpty(coreInventory)) {
      throw new ServiceException("未获取到单据");
    }
    StoragePositionTransfer storagePositionTransfer = new StoragePositionTransfer();
    storagePositionTransfer.setTransferCode(DBUtils.getCodeRegular(MenuEnum.MENU_1042)); // 生成单号
    BeanUtil.copyProperties(coreInventory, storagePositionTransfer); // 复制数据

    storagePositionTransfer.setSortingStatus(SortingStatusEnum.NONE.getId());
    storagePositionTransfer.setTransferType(TransferTypeEnum.ATTRIBUTE_CHANGE.getName());
    storagePositionTransfer.setTansfterStatus("新建");
    storagePositionTransfer.setSortingDate(null);
    storagePositionTransfer.setTotalQuantity(validStorage);
    storagePositionTransfer.setTotalWeight(B.mul(storagePositionTransfer.getTotalQuantity(), coreInventory.getWeight()));
    storagePositionTransfer.setTotalNetWeight(B.mul(storagePositionTransfer.getTotalQuantity(), coreInventory.getNetWeight()));
    storagePositionTransfer.setTotalPurchaseAmount(B.mul(storagePositionTransfer.getTotalQuantity(), coreInventory.getRatePrice()));
    storagePositionTransfer.setTransferId(null);

    storagePositionTransferService.save(storagePositionTransfer);

    StoragePositionTransferDetail storagePositionTransferDetail = new StoragePositionTransferDetail();
    BeanUtil.copyProperties(coreInventory, storagePositionTransferDetail); // 复制数据
    storagePositionTransferDetail.setTransferId(storagePositionTransfer.getTransferId());
    storagePositionTransferDetail.setTransferDetailId(null);
    storagePositionTransferDetail.setPositionNameIn(storagePositionTransferDetail.getPositionName());
    storagePositionTransferDetail.setTransferQuantity(validStorage);
    storagePositionTransferDetail.setProductAttribute(productAttribute);
    storagePositionTransferDetail.setSourceDetailId("" + inventoryId);
    storagePositionTransferDetailService.save(storagePositionTransferDetail);

    List<ScanPositionTransferDetailBo> detailList = new ArrayList<>();
    ScanPositionTransferDetailBo detailBo = BeanUtil.copyProperties(storagePositionTransferDetail, ScanPositionTransferDetailBo.class);
    detailBo.setTransferId(storagePositionTransferDetail.getTransferId());
    detailBo.setTransferDetailId(storagePositionTransferDetail.getTransferDetailId());
    detailBo.setFinishedQuantity(storagePositionTransferDetail.getTransferQuantity());
    detailBo.setTargetPositionName(storagePositionTransferDetail.getPositionNameIn());
    detailList.add(detailBo);

    storagePositionTransferService.transfter(storagePositionTransfer, detailList, new ScanPositionTransferBo());

    if (!StrUtil.equals(productAttribute, "报废出库")) {
      return R.ok("成功");
    }

    //#region 创建出库单
    OutOrder outOrderInfo = new OutOrder();
    BeanUtil.copyProperties(storagePositionTransfer, outOrderInfo);
    outOrderInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671, LoginHelper.getLoginUser().getTenantId()));
    outOrderInfo.setTotalQuantityOrder(storagePositionTransfer.getTotalQuantity());
    outOrderInfo.setOrderType(OutOrderTypeEnum.INVALIDATE_OUT.getName());
    outOrderInfo.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
    outOrderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
    outOrderInfo.setSourceType("入库单");
    outOrderInfo.setSourceId("" + storagePositionTransfer.getTransferId());
    outOrderInfo.setSourceCode(storagePositionTransfer.getTransferCode());
    outOrderInfo.setOrderId(null);
    outOrderInfo.setAuditor(LoginHelper.getLoginUser().getNickname());
    outOrderInfo.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
    outOrderInfo.setAuditDate(new Date());
    outOrderInfo.setTotalQuantityOrder(storagePositionTransfer.getTotalQuantity());

    outOrderInfo.setConsignorNameSale("上海");
    outOrderInfo.setConsignorIdSale(483L);
    outOrderInfo.setRemark("报废出库");
    outOrderService.save(outOrderInfo);
    outOrderStatusHistoryService.AddHistory(outOrderInfo, OutOperationTypeEnum.PC_INVALIDATE_OUT, OutOrderStatusEnum.AUDIT_SUCCESS);

    //#endregion

    //#region 创建出库单 明细
    OutOrderDetail outOrderDetail = new OutOrderDetail();
    BeanUtil.copyProperties(storagePositionTransferDetail, outOrderDetail);
    outOrderDetail.setOrderId(outOrderInfo.getOrderId());
    outOrderDetail.setSourceDetailId("" + storagePositionTransferDetail.getTransferDetailId());
    outOrderDetail.setSourceMainId("" + storagePositionTransferDetail.getTransferId());
    outOrderDetail.setQuantityOrder(storagePositionTransferDetail.getTransferQuantity());
    outOrderDetail.setSourceType("货位转移");
    outOrderDetail.setRemark("报废出库");
    outOrderDetail.setConsignorNameSale("上海");
    outOrderDetail.setConsignorIdSale(483L);
    outOrderDetail.setOrderDetailId(null);
    outOrderDetailService.save(outOrderDetail);
    //查询库存Id
    LambdaQueryWrapper<CoreInventory> coreInventoryLqw = new LambdaQueryWrapper<>();
    coreInventoryLqw.select(CoreInventory::getInventoryId)
      .eq(CoreInventory::getBillCode, storagePositionTransfer.getTransferCode())
      .eq(CoreInventory::getProductAttribute, "报废出库")
      .eq(CoreInventory::getProductId, outOrderDetail.getProductId())
      .eq(CoreInventory::getStorageId, storagePositionTransfer.getStorageId())
      .eq(CoreInventory::getValidStorage, outOrderDetail.getQuantityOrder())
      .last("limit 1");

    CoreInventory info = coreInventoryService.getOne(coreInventoryLqw);
    if (ObjectUtil.isNotEmpty(info)) {
      OutSortingRule outSortingRule = new OutSortingRule();
      outSortingRule.setInventoryId(info.getInventoryId());
      outSortingRule.setOrderId(outOrderInfo.getOrderId());
      outSortingRule.setOrderDetailId(outOrderDetail.getOrderDetailId());
      outSortingRule.setProductId(outOrderDetail.getProductId());
      outSortingRule.setProductCode(outOrderDetail.getProductCode());
      outSortingRuleService.save(outSortingRule);
    }
    //#endregion

    outOrderSortingService.sorting(outOrderInfo.getOrderId(), false);// 更新任务状态为完成

    //#region 出库单自动出库
    OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrderInfo, OutScanMainBo.class);
    newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.INVALIDATE_OUT);
    newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.INVALIDATE_OUT);
    newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细

    OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
    outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
    newOutScanOrderBo.getDataList().add(outScanDetailBo);

    outScanOrderService.normalOutSave(newOutScanOrderBo);
    //#endregion

    return R.ok("属性转换成功");
  }
}
