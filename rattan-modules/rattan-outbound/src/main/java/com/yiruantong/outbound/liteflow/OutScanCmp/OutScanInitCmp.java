package com.yiruantong.outbound.liteflow.OutScanCmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.system.service.core.ISysConfigService;

import java.util.Arrays;
import java.util.List;

@LiteflowComponent(id = "outScanInitCmp", name = "1.出库扫描初始化组件")
@RequiredArgsConstructor
public class OutScanInitCmp extends NodeComponent {
  private final IOutOrderService outOrderService;
  private final ISysConfigService sysConfigService;

  @Override
  public void process() {
    OutScanMainBo outScanMainBo = this.getRequestData();
    OutScanContext ctx = this.getContextBean(OutScanContext.class);
    ctx.setLoginUser(LoginHelper.getLoginUser());
    Long orderId = outScanMainBo.getOrderId(); // 出库单ID
    OutOrder outOrder = outOrderService.getById(orderId); // 出库单主表信息

    // 设置仓库、货主
    outScanMainBo.setStorageId(outOrder.getStorageId());
    outScanMainBo.setStorageName(outOrder.getStorageName());
    outScanMainBo.setConsignorId(outOrder.getConsignorId());
    outScanMainBo.setConsignorCode(outOrder.getConsignorCode());
    outScanMainBo.setConsignorName(outOrder.getConsignorName());

    List<OutOrderStatusEnum> outOrderStatusEnumList = Arrays.asList(OutOrderStatusEnum.AUDIT_SUCCESS, OutOrderStatusEnum.MATCHED,
      OutOrderStatusEnum.WAVE_FINISHED, OutOrderStatusEnum.WAITING_MATCH, OutOrderStatusEnum.MATCHING, OutOrderStatusEnum.PACKAGE_PARTIAL,
      OutOrderStatusEnum.SHIPMENT_PARTIAL, OutOrderStatusEnum.PICKING, OutOrderStatusEnum.PICKED);
    if (outOrderStatusEnumList.stream().noneMatch(a -> B.isEqual(a.getName(), outOrder.getOrderStatus()))) {
      throw new ServiceException("[" + outOrder.getOrderStatus() + "]当前状态不允许出库");
    }

    // 按拣货数量打包开启
    boolean outer_pickQuantity = sysConfigService.getConfigBool("outer_pickQuantity");
    ctx.setOuterPickQuantity(outer_pickQuantity);
    ctx.setOutOrder(outOrder);
    ctx.setOutScanMainBo(outScanMainBo);

    // 获取入库、上架枚举动作
    getActionEnum(ctx);
  }

  //#region 获取出库轨迹、波次轨迹枚举动作
  private void getActionEnum(OutScanContext cxt) {
    OutScanMainBo outScanMainBo = cxt.getOutScanMainBo();

    //出库轨迹动作
    OutOperationTypeEnum outOperationTypeEnum = OutOperationTypeEnum.PC_PACKAGE;
    //波次轨迹动作
    OutWaveOperationTypeEnum outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PC_PACKAGE;

    // 根据前端传入的动作类型（库存来源类型），转换出库轨迹类型
    switch (outScanMainBo.getScanInType()) {
      case PC_BATCH_OUT -> outOperationTypeEnum = OutOperationTypeEnum.PC_BATCH_OUT;
      case PC_QUICK_OUT -> {
        outOperationTypeEnum = OutOperationTypeEnum.PC_QUICK_OUT;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PC_ORDER_OUT;
      }
      case PC_NO_BILL_OUT -> outOperationTypeEnum = OutOperationTypeEnum.PC_NO_BILL_OUT;
      case PC_SEND_BATCH -> outOperationTypeEnum = OutOperationTypeEnum.PC_SEND_BATCH;
      case PC_ORDER_OUT -> {
        outOperationTypeEnum = OutOperationTypeEnum.PC_ORDER_OUT;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PC_ORDER_OUT;
      }
      case PDA_ORDER_OUT -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_OUT;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_SCAN_OUT;
      }
      case PDA_NO_BILL_OUT -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_NO_BILL_OUT;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_NO_BILL_OUT;
      }
      case PDA_ORDER_PICKING_ZG -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_PICKING_ZG;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_PICKING_ZG;
      }
      case PDA_ORDER_PICKING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_PICKING;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_PICKING;
      }
      case PDA_QUICK_OUT -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_QUICK_OUT;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_QUICK_OUT;
      }
      case PDA_PLATE_PICKING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_PLATE_PICKING;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_PLATE_PICKING;
      }
      case PC_ORDER_OUT_PLATE -> {
        outOperationTypeEnum = OutOperationTypeEnum.PC_ORDER_OUT_PLATE;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PC_ORDER_OUT_PLATE;
      }
      case PDA_ORDER_OUT_PLATE -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_OUT_PLATE;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_OUT_PLATE;
      }
      case PDA_ORDER_FLASH_OUT -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_FLASH_OUT;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_FLASH_OUT;
      }
    }

    cxt.setOutOperationTypeEnum(outOperationTypeEnum);
    cxt.setOutWaveOperationTypeEnum(outWaveOperationTypeEnum);
  }
  //#endregion
}
