package com.yiruantong.composite.liteflow.checkingCmp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.composite.liteflow.Context.CheckingContext;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@LiteflowComponent(id = "checkingStatusCmp", name = "1.更新质检状态")
@RequiredArgsConstructor
public class CheckingStatusCmp extends NodeComponent {

  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInEnterService inEnterService;
  private final ICoreInventoryService coreInventoryService;

  @Override
  public void process() {
    ApiInOrderBo apiInOrderBo = this.getRequestData();
    CheckingContext ctx = this.getContextBean(CheckingContext.class);
    InOrder orderInfo = inOrderService.getById(apiInOrderBo.getOrderId());
    if (ObjectUtil.isEmpty(orderInfo)) {
      throw new ServiceException("未找到预到货单，请重新核实数据");
    }
    if (B.isEqual(orderInfo.getCheckingStatus(), InOrderStatusEnum.ALREADY_CHECKING.getName())) {
      throw new ServiceException("已经质检，不能再次质检");
    }
    //给上下文添加数据
    ctx.setApiInOrderBo(apiInOrderBo);
    ctx.setInOrder(orderInfo);

    //需要将已入库的对应的库存状态更新为“正常”
    LambdaQueryWrapper<InEnter> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InEnter::getOrderId, orderInfo.getOrderId())
      .eq(InEnter::getOrderCode, orderInfo.getOrderCode());
    InEnter enterInfo = inEnterService.getOne(queryWrapper);
    if (ObjectUtil.isNotNull(enterInfo)) {
      LambdaUpdateWrapper<CoreInventory> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(CoreInventory::getStorageStatus, InventoryStatusEnum.NORMAL.getName())
        .eq(CoreInventory::getBillCode, enterInfo.getEnterCode());
      coreInventoryService.update(updateWrapper);
    }
    // 修改质检状态
    LambdaUpdateWrapper<InOrder> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(InOrder::getCheckingStatus, apiInOrderBo.getCheckingStatus()) //  InOrderStatusEnum.ALREADY_CHECKING.getName()
      .eq(InOrder::getOrderId, orderInfo.getOrderId());
    inOrderService.update(updateWrapper);


    //查询明细信息
    List<InOrderDetail> inOrderDetails = inOrderDetailService.selectListByMainId(orderInfo.getOrderId());
    ctx.setInOrderDetails(inOrderDetails);
  }

}
