package com.yiruantong.inbound.service.in.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.in.*;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InShelveCancelServiceImpl implements IInShelveCancelService {
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IInEnterDetailService inEnterDetailService;
  private final IInEnterService inEnterService;
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IInEnterStatusHistoryService inEnterStatusHistoryService;
  private final IInShelveDetailStepService inShelveDetailStepService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> cancelShelve(Map<String, Object> map) {
    try {
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);
      //查询生成上架单数据
      LambdaQueryWrapper<InShelve> inShelveLwq = new LambdaQueryWrapper<>();
      inShelveLwq.in(InShelve::getShelveId, _ids);
      List<InShelve> inShelves = inShelveService.list(inShelveLwq);

      // 根据上架单数据 查询是否已经被占位
      for (InShelve inShelveInfo : inShelves) {
        LambdaQueryWrapper<CoreInventoryHolder> inventoryHistoryLwq = new LambdaQueryWrapper<>();
        inventoryHistoryLwq.eq(CoreInventoryHolder::getBillCode, inShelveInfo.getOrderCode());
        CoreInventoryHolder coreInventoryHistoryInfo = coreInventoryHolderService.getOne(inventoryHistoryLwq);
        if (ObjectUtil.isNotEmpty(coreInventoryHistoryInfo)) {
          throw new ServiceException("商品编号为" + coreInventoryHistoryInfo.getProductCode() + "的已经被订单占位，不允许执行取消操作！");
        }
      }

      //处理上架单
      for (InShelve inShelveInfo : inShelves) {
        //#region 删除上架单库存数据
        LambdaQueryWrapper<CoreInventory> coreInventoryLwq = new LambdaQueryWrapper<>();
        coreInventoryLwq.eq(CoreInventory::getBillCode, inShelveInfo.getShelveCode());
        coreInventoryService.remove(coreInventoryLwq);


        //还原入库单有效库存
        LambdaUpdateWrapper<CoreInventory> coreInventoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        coreInventoryLambdaUpdateWrapper
          .setSql("product_storage=origin_storage,valid_storage=origin_storage")
          .set(CoreInventory::getHolderStorage, 0L)
          .eq(CoreInventory::getBillCode, inShelveInfo.getEnterCode());
        coreInventoryService.update(coreInventoryLambdaUpdateWrapper);
        //#endregion

        //#region 删除上架单库存监测记录
        LambdaQueryWrapper<CoreInventoryHistory> coreInventoryHistoryLwq = new LambdaQueryWrapper<>();
        coreInventoryHistoryLwq.eq(CoreInventoryHistory::getBillCode, inShelveInfo.getShelveCode());
        coreInventoryHistoryService.remove(coreInventoryHistoryLwq);
        //#endregion


        //#region 修改上架单明细
        LambdaUpdateWrapper<InShelveDetail> inShelveDetailLuq = new LambdaUpdateWrapper<>();
        inShelveDetailLuq.set(InShelveDetail::getShelveStatus, InShelveStatusEnum.WAITING.getName())
          .set(InShelveDetail::getShelvedQuantity, 0)
          .set(InShelveDetail::getSortingStatus, 0)
          .setSql("on_shelve_quantity = quantity")
          .set(InShelveDetail::getLackStorage, 0)
          .eq(InShelveDetail::getShelveId, inShelveInfo.getShelveId());
        inShelveDetailService.update(inShelveDetailLuq);//提交
        //#endregion

        //#region 删除上架单明细的明细
        LambdaQueryWrapper<InShelveDetailStep> inShelveDetailStepLqw = new LambdaQueryWrapper<>();
        inShelveDetailStepLqw.eq(InShelveDetailStep::getShelveId, inShelveInfo.getShelveId());
        inShelveDetailStepService.remove(inShelveDetailStepLqw);
        //#endregion

        //#region 更新入库单明细信息  跟预到货单明细信息
        //从新查询上架单明细
        LambdaQueryWrapper<InShelveDetail> inShelveDetailLwq = new LambdaQueryWrapper<>();
        inShelveDetailLwq.eq(InShelveDetail::getShelveId, inShelveInfo.getShelveId());
        List<InShelveDetail> inShelveDetails = inShelveDetailService.list(inShelveDetailLwq);

        for (InShelveDetail inShelveDetailInfo : inShelveDetails) {
          // 入库明细查询
          LambdaUpdateWrapper<InEnterDetail> inEnterDetailLuq = new LambdaUpdateWrapper<>();
          inEnterDetailLuq.set(InEnterDetail::getShelvedQuantity, 0)
            .eq(InEnterDetail::getEnterId, inShelveInfo.getEnterId())
            .eq(InEnterDetail::getEnterDetailId, inShelveDetailInfo.getEnterDetailId());
          inEnterDetailService.update(inEnterDetailLuq);//提交

          // 入库明细查询
          LambdaUpdateWrapper<InOrderDetail> inOrderDetailLuq = new LambdaUpdateWrapper<>();
          inOrderDetailLuq.set(InOrderDetail::getShelvedQuantity, 0)
            .eq(InOrderDetail::getOrderId, inShelveInfo.getOrderId())
            .eq(InOrderDetail::getOrderDetailId, inShelveDetailInfo.getOrderDetailId());
          inOrderDetailService.update(inOrderDetailLuq);//提交
        }
        //#endregion

        //#region 修改上架单主表
        BigDecimal totalOnshelveQuantity = inShelveDetails.stream().map(InShelveDetail::getOnShelveQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        LambdaUpdateWrapper<InShelve> inShelveLuq = new LambdaUpdateWrapper<>();
        inShelveLuq.set(InShelve::getShelveStatus, InShelveStatusEnum.WAITING.getName())
          .set(InShelve::getAuditing, AuditEnum.AUDIT.getId())
          .set(InShelve::getAuditor, null)
          .set(InShelve::getAuditDate, null)
          .set(InShelve::getTotalShelvedQuantity, 0)
          .set(InShelve::getTotalOnshelveQuantity, totalOnshelveQuantity)
          .eq(InShelve::getShelveId, inShelveInfo.getShelveId());
        inShelveService.update(inShelveLuq);//提交
        //#endregion

        //#region 更新入库单状态
        // 查询入库单明细
        InEnter inEnterInfo = inEnterService.getById(inShelveInfo.getEnterId());
        InEnterStatusEnum inEnterStatusEnum = InEnterStatusEnum.matchingEnum(inEnterInfo.getEnterStatus());

        inEnterStatusHistoryService.addHistoryInfo(inEnterInfo, InEnterActionEnum.PC_CANCEL_SHELVE, inEnterStatusEnum, InEnterStatusEnum.ENTERED);
        inEnterService.updateEnterStatus(inShelveInfo.getEnterId(), InEnterStatusEnum.ENTERED);

        //#endregion

        InOrder inOrderInfo = inOrderService.getById(inShelveInfo.getOrderId());

        // 更新预到货单 上架状态为未上架
        LambdaUpdateWrapper<InOrder> inOrderLuq = new LambdaUpdateWrapper<>();
        inOrderLuq.set(InOrder::getShelveStatus, InShelveStatusEnum.WAITING.getName())
          .set(InOrder::getTotalShelvedQuantity, 0L)
          .eq(InOrder::getOrderId, inShelveInfo.getOrderId());
        inOrderService.update(inOrderLuq);

        InOrderStatusEnum inOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrderInfo.getShelveStatus());
        //预到货单添加轨迹
        inOrderStatusHistoryService.addHistoryInfo(inOrderInfo, InOrderActionEnum.CANCEL_SHELVE, inOrderStatusEnum, InOrderStatusEnum.HISTORY_WAITING);


      }

      return R.ok("上架撤销成功！");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
}
