package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.other.XgOrderTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;

import java.math.BigDecimal;
import java.util.*;

@LiteflowComponent(id = "inScanValidationCmp", name = "2.入库扫描校验组件")
@RequiredArgsConstructor
public class InScanValidationCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IBaseProductService baseProductService;
  private final IBasePositionService basePositionService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventorySnService coreInventorySnService;

  @Override
  public void process() throws Exception {
    InScanContext ctx = this.getContextBean(InScanContext.class);
    InScanOrderBo inScanOrderBo = ctx.getInScanOrderBo();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据
    InOrder inOrder = ctx.getInOrder();

    //#region 对扫描明细循环验证
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      // 合金辅料一键入库时把预到货数量更新成入库数量
      if (StringUtils.isNotEmpty(inOrder.getOrderType()) && inOrder.getOrderType().equals(XgOrderTypeEnum.ALLOY_AUXILIARY_MATERIAL.getName())) {
        LambdaUpdateWrapper<InOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(InOrderDetail::getQuantity, inScanOrderDetailBo.getQuantity())
          .eq(InOrderDetail::getOrderDetailId, inScanOrderDetailBo.getOrderDetailId());
        inOrderDetailService.update(updateWrapper);
      }
      Long orderDetailId = inScanOrderDetailBo.getOrderDetailId();
      String positionName = inScanOrderDetailBo.getPositionName();
      String productCode = inScanOrderDetailBo.getProductCode();
      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量
      Date produceDate = inScanOrderDetailBo.getProduceDate(); // 生产日期

      // 预到货明细信息
      var inOrderDetail = inOrderDetailService.getById(orderDetailId);
      Assert.isFalse(ObjectUtil.isEmpty(inOrderDetail.getProviderId()), "明细供应商不能为空！");

      // 商品信息
      var productInfo = baseProductService.getByCode(productCode);
      Assert.isFalse(ObjectUtil.isEmpty(productInfo), productCode + "商品编号不存在！");

      // 有期效管理的 商品生产日期不能为空
      if (Objects.equals(productInfo.getIsNeedPeriod(), EnableEnum.ENABLE.getId()) && ObjectUtil.isEmpty(produceDate)) {
        throw new ServiceException("开启期效管理的商品，生产日期不能为空！");
      }

      // 验证货位不可用
      if (!basePositionService.existEnableByName(inOrder.getStorageId(), positionName)) {
        throw new ServiceException("货位：" + positionName + "不可用！");
      }

      // 获取当前货位内已有托盘数
      Long plateCount = coreInventoryService.getPlateCount(inOrder.getStorageId(), positionName);
      // 获取货位信息
      BasePosition basePosition = basePositionService.getByName(inOrder.getStorageId(), positionName);

      // 开启了按拍上架
      if (ctx.isInShelvePaiCount()) {
        // 如果当前库存内的实物拍数大于货位最大拍数时
        if (plateCount > basePosition.getMaxPaiQty()) {
          throw new ServiceException("货位：" + positionName + "入库数量已经超过货位最大托盘数，禁止入库！");
        }
      } else {
        // 获取当前货位库存数量
        BigDecimal productStorage = coreInventoryService.getPositionStorage(inOrder.getStorageId(), positionName);
        BigDecimal maxCapacity = Optional.ofNullable(basePosition.getMaxCapacity()).orElse(BigDecimal.ZERO);

        // 只有大于0才判断  验证入库数量是否大于货位最大容量
        if (B.isGreater(Convert.toBigDecimal(maxCapacity)) && productStorage.compareTo(maxCapacity) > 0) {
          throw new ServiceException("货位：" + positionName + "入库数量已经超过货位最大数量，禁止入库！");
        }
      }

      // 验证禁售日期
      if (ctx.isInNoReceivingDate()) {
        // 预到货明细中的禁收日期小于当前时间时，且不是2=残品货位, 8=次品货位， 14=临期货位的库存
        if (ObjectUtil.isNotNull(inOrderDetail.getNoReceivingDate()) &&
          DateUtil.compare(inOrderDetail.getNoReceivingDate(), DateUtil.date()) < 0
          && Arrays.asList(PositionTypeEnum.DEFECTIVE.getId(), PositionTypeEnum.SCRAP.getId(), PositionTypeEnum.EXPIRE.getId()).contains(basePosition.getPositionType())
        ) {
          throw new ServiceException("商品编号为【" + productCode + "】超出禁售日期不允许收货！");
        }
      }

      var quantity = inOrderDetail.getQuantity();
      // 预到货常规扫描入库时允许超收
      if (ctx.isInOvercharges()) {
        var overcharges = inOrderDetail.getOvercharges(); // 超收比例
        quantity = B.add(quantity, B.mul(quantity, overcharges)); // 加上超收数量
      }
      BigDecimal enterQuantity = Optional.ofNullable(inOrderDetail.getEnterQuantity()).orElse(BigDecimal.ZERO);
      // 验证入库数量是否大于预到货数量
      if (enterQuantity.add(finishedQuantity).compareTo(quantity) > 0) {
        throw new ServiceException("产品编号：" + productCode + "已扫描数量超过可以入库数量！");
      }

      // 校验SN数量和扫描数量是否相等
      String singleSignCode = inScanOrderDetailBo.getSingleSignCode();
      List<String> snList = StringUtils.splitList(singleSignCode);

      // 商品开启SN管理 且 仓库未关闭SN管理
      if (Objects.equals(productInfo.getIsManageSn(), EnableEnum.ENABLE.getId()) && !Objects.equals(ctx.getStorageInfo().getSnDisabled(), EnableEnum.ENABLE.getId())) {
        Assert.isFalse(!B.isEqual(snList.size(), inScanOrderDetailBo.getFinishedQuantity()), inScanOrderDetailBo.getProviderCode() + "扫描的SN数不等于已扫描数量！");

        // 校验SN是否存在
        List<CoreInventorySn> validSnList = coreInventorySnService.getValidSnList(inOrder.getStorageId(), singleSignCode);
        Assert.isFalse(!validSnList.isEmpty(), "SN【" + StringUtils.join(validSnList.stream().map(CoreInventorySn::getSnNo).toList(), ",") + "】已存在，不允许重复入库！");
      }
    }
    //#endregion

  }
}
