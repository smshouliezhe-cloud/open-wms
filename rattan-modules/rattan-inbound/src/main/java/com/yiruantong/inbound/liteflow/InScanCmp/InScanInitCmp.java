package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InEnterActionEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.system.service.core.ISysConfigService;

import java.util.Arrays;
import java.util.List;

@LiteflowComponent(id = "inScanInitCmp", name = "1.入库扫描初始化组件")
@RequiredArgsConstructor
public class InScanInitCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IInEnterService inEnterService;

  @Override
  public void process() {
    InScanOrderBo inScanOrderBo = this.getRequestData();
    InScanContext ctx = this.getContextBean(InScanContext.class);

    String orderCode = inScanOrderBo.getOrderCode(); // 预到货单号
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据

    LambdaQueryWrapper<InOrder> wrapper = new LambdaQueryWrapper<>();
    if (StringUtils.startsWith(orderCode, "PO")) {
      wrapper.eq(InOrder::getOrderCode, orderCode);
    } else {
      wrapper.eq(InOrder::getOrderCode, orderCode)
        .or()
        .eq(InOrder::getSourceCode, orderCode)
        .or()
        .eq(InOrder::getTrackingNumber, orderCode);
    }
    InOrder inOrder = inOrderService.getOne(wrapper);
    if (ObjectUtil.isEmpty(inOrder)) {
      throw new ServiceException("采购单号【" + orderCode + "】不存在！");
    }
    if (ObjectUtil.isEmpty(inOrder.getProviderId())) {
      throw new ServiceException("采购单号【" + orderCode + "】供应商不能为空！");
    }
    if (ObjectUtil.equals(InOrderStatusEnum.FINISHED.getName(), inOrder.getOrderStatus())) {
      throw new ServiceException("采购单号【" + orderCode + "】已完全交货！");
    }
    if (dataList.isEmpty()) {
      throw new ServiceException("扫描明细不能为空！");
    }

    // 校验LPN号不能重复使用
    if (List.of(InventorySourceTypeEnum.PC_LPN_SCAN_IN, InventorySourceTypeEnum.PDA_LPN_SCAN_IN).contains(inScanOrderBo.getScanInType())) {
      Assert.isFalse(StringUtils.isEmpty(inScanOrderBo.getLpnCode()), "LPN号不能为空！");
      inEnterService.checkLpnCodeValid(inScanOrderBo.getLpnCode());
    }

    // 获取全局参数
    boolean in_generateShelve = sysConfigService.getConfigBool("in_generateShelve"); // 是否生成上架单
    boolean in_generateShelveOnly = sysConfigService.getConfigBool("in_generateShelveOnly"); // 扫描入库时合并生成上架单
    boolean in_overcharges = sysConfigService.getConfigBool("in_overcharges"); // 预到货常规扫描入库时允许超收
    boolean in_shelvePaiCount = sysConfigService.getConfigBool("in_shelvePaiCount"); // 按拍上架推荐货位根据拍数推荐
    boolean in_noReceivingDate = sysConfigService.getConfigBool("in_noReceivingDate");  // 入库时启用禁收日期
    boolean in_generateQualityCheck = sysConfigService.getConfigBool("in_generateQualityCheck");  // 是否生成质检单
    boolean in_waitShelveBillScan = sysConfigService.getConfigBool("in_waitShelveBillScan");  // 托盘号默认为货位号 - 待上架单扫描
    // boolean in_plateToRelationCode = sysConfigService.getConfigBool("in_plateToRelationCode");  // 如果关联码为空，将采用托盘号作为关联码，用于扫描商品使用
    // 上架货位类型
    List<PositionTypeEnum> positionTypeEnumList = Arrays.asList(PositionTypeEnum.NORMAL, PositionTypeEnum.ELEVATED, PositionTypeEnum.STORAGE,
      PositionTypeEnum.VIRTUAL, PositionTypeEnum.DEFECTIVE, PositionTypeEnum.SCRAP, PositionTypeEnum.EXPIRE);

    // 仓库信息
    var storageInfo = baseStorageService.getById(inOrder.getStorageId());

    ctx.setInScanOrderBo(inScanOrderBo);
    ctx.setInOrder(inOrder);
    ctx.setStorageInfo(storageInfo);
    ctx.setPositionTypeEnumList(positionTypeEnumList);

    ctx.setInNoReceivingDate(in_noReceivingDate);
    ctx.setInOvercharges(in_overcharges);
    ctx.setInShelvePaiCount(in_shelvePaiCount);
    ctx.setInGenerateShelve(in_generateShelve);
    ctx.setInGenerateShelveOnly(in_generateShelveOnly);
    ctx.setInGenerateQualityCheck(in_generateQualityCheck);
    ctx.setInWaitShelveBillScan(in_waitShelveBillScan);

    // 获取入库、上架枚举动作
    getActionEnum(ctx);
  }

  //#region 获取入库、上架枚举动作
  private void getActionEnum(InScanContext cxt) {
    InScanOrderBo inScanOrderBo = cxt.getInScanOrderBo();
    // 预到货单单状态处理
    InOrderActionEnum orderActionEnumHistory = InOrderActionEnum.PC_RECEIVE_IN;
    InOrderActionEnum orderShelveActionEnum = InOrderActionEnum.PC_RECEIVE_SHELVE_DIRECT;

    // 入库单单状态处理
    InEnterActionEnum inEnterActionEnumEntered = InEnterActionEnum.PC_RECEIVE_IN; // 确认入库动作
    InEnterActionEnum inEnterActionEnumShelve = InEnterActionEnum.PC_RECEIVE_SHELVE_DIRECT; // 上架动作

    switch (inScanOrderBo.getScanInType()) {
      case PC_STACKING_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_STACKING_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_STACKING_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_STACKING_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_STACKING_SHELVE_DIRECT;
      }
      case PC_QUICK_ENTER -> {
        orderActionEnumHistory = InOrderActionEnum.PC_QUICK_ENTER;
        orderShelveActionEnum = InOrderActionEnum.PC_QUICK_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_QUICK_ENTER;
        inEnterActionEnumShelve = InEnterActionEnum.PC_QUICK_SHELVE_DIRECT;
      }
      case PC_ORDER_STACKING_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_ORDER_STACKING_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_ORDER_STACKING_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_ORDER_STACKING_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_ORDER_STACKING_SHELVE_DIRECT;
      }
      case PC_LPN_SCAN_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_LPN_SCAN_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_LPN_SCAN_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_LPN_SCAN_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_LPN_SCAN_SHELVE_DIRECT;
      }
      case PC_PAI_SCAN_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_PAI_SCAN_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_PAI_SCAN_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_PAI_SCAN_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_PAI_SCAN_SHELVE_DIRECT;
      }
      case PC_CASING_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_CASING_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_CASING_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_CASING_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_CASING_SHELVE_DIRECT;
      }
      case PC_NO_BILL_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_NO_BILL_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_NO_BILL_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_NO_BILL_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_NO_BILL_SHELVE_DIRECT;
      }
      case PDA_ORDER_STACKING_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_ORDER_STACKING_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_ORDER_STACKING_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_ORDER_STACKING_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_ORDER_STACKING_SHELVE_DIRECT;
      }
      case PDA_RECEIVE_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_RECEIVE_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_RECEIVE_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_RECEIVE_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_RECEIVE_SHELVE_DIRECT;
      }
      case PDA_PAI_SCAN_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_PAI_SCAN_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_PAI_SCAN_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_PAI_SCAN_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_PAI_SCAN_SHELVE_DIRECT;
      }
      case PDA_LPN_SCAN_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_LPN_SCAN_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_LPN_SCAN_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_LPN_SCAN_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_LPN_SCAN_SHELVE_DIRECT;
      }
      case PDA_NO_BILL_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_NO_BILL_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_NO_BILL_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_NO_BILL_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_NO_BILL_SHELVE_DIRECT;
      }
      case PDA_NO_BILL_SHELVE -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_NO_BILL_SHELVE;
        orderShelveActionEnum = InOrderActionEnum.PDA_NO_BILL_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_NO_BILL_SHELVE;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_NO_BILL_SHELVE_DIRECT;
      }
      case PDA_QUICK_ENTER -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_QUICK_ENTER;
        orderShelveActionEnum = InOrderActionEnum.PDA_QUICK_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_QUICK_ENTER;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_QUICK_SHELVE_DIRECT;
      }
      case PDA_CASING_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_CASING_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_CASING_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_CASING_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_CASING_SHELVE_DIRECT;
      }
      case PDA_NO_BILL_FLASH_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PDA_NO_BILL_FLASH_IN;
        orderShelveActionEnum = InOrderActionEnum.PDA_NO_BILL_FLASH_SHELVE_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PDA_NO_BILL_FLASH_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PDA_NO_BILL_FLASH_SHELVE_DIRECT;
      }
      case COMPLETION_ORDER -> {
        orderActionEnumHistory = InOrderActionEnum.PC_COMPLETION_ORDER;
        orderShelveActionEnum = InOrderActionEnum.PC_COMPLETION_ORDER_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_COMPLETION_ORDER;
        inEnterActionEnumShelve = InEnterActionEnum.PC_COMPLETION_ORDER_DIRECT;
      }
      case PC_CROSS_DOCKING_IN -> {
        orderActionEnumHistory = InOrderActionEnum.PC_CROSS_DOCKING_IN;
        orderShelveActionEnum = InOrderActionEnum.PC_CROSS_DOCKING_DIRECT;

        inEnterActionEnumEntered = InEnterActionEnum.PC_CROSS_DOCKING_IN;
        inEnterActionEnumShelve = InEnterActionEnum.PC_CROSS_DOCKING_DIRECT;
      }
    }

    cxt.setOrderActionEnumHistory(orderActionEnumHistory);
    cxt.setOrderShelveActionEnum(orderShelveActionEnum);
    cxt.setInEnterActionEnumEntered(inEnterActionEnumEntered);
    cxt.setInEnterActionEnumShelve(inEnterActionEnumShelve);
  }
  //#endregion


}
