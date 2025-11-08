package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePlateService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.base.UsingEnum;
import com.yiruantong.common.core.enums.in.*;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.enums.HistoryTypeEnum;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@LiteflowComponent(id = "inScanInventoryCmp", name = "5.库存增加")
@RequiredArgsConstructor
public class InScanInventoryCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final IInEnterService inEnterService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInEnterDetailService inEnterDetailService;
  private final IBasePositionService basePositionService;
  private final IBaseProductService baseProductService;
  private final IInQualityCheckService inQualityCheckService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventorySnService coreInventorySnService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;
  private final IInEnterStatusHistoryService inEnterStatusHistoryService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IBasePlateService basePlateService;

  @Override
  public void process() {
    InScanOrderBo inScanOrderBo = this.getRequestData();
    InScanContext ctx = this.getContextBean(InScanContext.class);

    InOrder inOrder = ctx.getInOrder();
    InEnter inEnter = ctx.getInEnter();
    BaseStorage storageInfo = ctx.getStorageInfo();
    List<PositionTypeEnum> positionTypeEnumList = ctx.getPositionTypeEnumList();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据

    //#region 明细处理
    BigDecimal totalQuantity = BigDecimal.ZERO; // 数量合计求和
    BigDecimal totalWeight = BigDecimal.ZERO; // 毛重合计求和
    BigDecimal totalNewWeight = BigDecimal.ZERO; // 净重合计求和
    BigDecimal totalAmount = BigDecimal.ZERO; // 金额合计求和

    // 明细处理
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      Long orderDetailId = inScanOrderDetailBo.getOrderDetailId();
      String positionName = inScanOrderDetailBo.getPositionName();
      String batchNumber = inScanOrderDetailBo.getBatchNumber();
      String productCode = inScanOrderDetailBo.getProductCode();

      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量
      Assert.isFalse(B.isLessOrEqual(finishedQuantity), "扫描数量不能小于0");

      // 获取入库单明细
      InEnterDetail inEnterDetail = ctx.getInEnterDetailList().stream().filter(x -> StringUtils.equals(x.getProductCode(), inScanOrderDetailBo.getProductCode())).findFirst().orElse(null);
      Assert.isFalse(ObjectUtil.isNull(inEnterDetail), "入库明细不存在，生成质检单明细失败");

      // 预到货明细信息
      InOrderDetail inOrderDetail = inOrderDetailService.getById(orderDetailId);
      // 商品信息
      BaseProduct productInfo = baseProductService.getByCode(productCode);
      // 货位信息
      BasePosition basePosition = basePositionService.getByName(inOrder.getStorageId(), positionName);
      Assert.isFalse(ObjectUtil.isEmpty(basePosition), positionName + "货位不存在！");

      // 明细求和
      totalQuantity = totalQuantity.add(finishedQuantity);
      BigDecimal weight = Optional.ofNullable(inOrderDetail.getWeight()).orElse(BigDecimal.ZERO);
      BigDecimal newWeight = Optional.ofNullable(inOrderDetail.getNetWeight()).orElse(BigDecimal.ZERO);
      BigDecimal purchasePrice = Optional.ofNullable(inOrderDetail.getPurchasePrice()).orElse(BigDecimal.ZERO);
      totalWeight = totalWeight.add(weight.multiply(finishedQuantity));
      totalNewWeight = totalNewWeight.add(newWeight.multiply(finishedQuantity));
      totalAmount = totalAmount.add(purchasePrice.multiply(finishedQuantity));

      //#region 获取入库前：库存数量和重量
      BigDecimal beforeQuantity = BigDecimal.ZERO; // 保存前数量
      BigDecimal beforeWeight = BigDecimal.ZERO;   // 保存前重量
      //#endregion

      //#region 增加库存
      CommonDetailDto commonDetailDto = BeanUtil.copyProperties(inOrderDetail, CommonDetailDto.class);
      BeanUtil.copyProperties(inOrder, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("expandFields"));
      BeanUtil.copyProperties(inEnterDetail, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true)); // 复制入库单明细信息
      BeanUtil.copyProperties(inScanOrderDetailBo, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
      commonDetailDto.setMainId(inEnterDetail.getEnterId());
      commonDetailDto.setDetailId(inEnterDetail.getEnterDetailId());
      commonDetailDto.setBillCode(inEnter.getEnterCode());
      //来源单号
      if (StrUtil.isNotEmpty(inOrder.getSourceCode())) {
        commonDetailDto.setSourceCode(inOrder.getSourceCode());
      } else {
        commonDetailDto.setSourceCode(inEnter.getOrderCode());
      }
      //如果是一键入库 有些值需要按照一键入库的值去填写
      if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PC_QUICK_ENTER) {
        if (inScanOrderDetailBo.getProduceDate() != null) {
          commonDetailDto.setProduceDate(inScanOrderDetailBo.getProduceDate());
        }
        if (inScanOrderDetailBo.getBatchNumber() != null) {
          commonDetailDto.setBatchNumber(inScanOrderDetailBo.getBatchNumber());
        }
        if (inScanOrderDetailBo.getInStorageDate() != null) {
          commonDetailDto.setInStorageDate(inScanOrderDetailBo.getInStorageDate());
        }
        if (inScanOrderDetailBo.getProductAttribute() != null) {
          commonDetailDto.setProductAttribute(inScanOrderDetailBo.getProductAttribute());
        }
      }
      // 开启需要质检且质检状态为未质检
      if (StringUtils.equals(inOrder.getCheckingStatus(), InCheckingStatusEnum.UNCHECKED.getName()) && B.isOk(inOrder.getIsChecking())) {
        commonDetailDto.setCheckingStatusEnum(InCheckingStatusEnum.UNCHECKED);
      }

      Date produceDate = commonDetailDto.getProduceDate();
      commonDetailDto.setInQuantity(inScanOrderDetailBo.getFinishedQuantity()); // 入库数量
      commonDetailDto.setPositionNameIn(inScanOrderDetailBo.getPositionName()); // 入库货位
      commonDetailDto.setExpandFields(inOrder.getExpandFields()); // 主表扩展字段
      commonDetailDto.setDetailExpandFields(inOrderDetail.getExpandFields()); // 明细扩展字段

      // 单位重量为0时，重新计算
      if(B.isEqual(productInfo.getWeight())) {
        productInfo.setWeight(B.div(inOrderDetail.getRowWeight(),inOrderDetail.getQuantity()));
      }
      // 单位体积为0时，重新计算
      if(B.isEqual(productInfo.getUnitCube())) {
        productInfo.setUnitCube(B.div(inOrderDetail.getRowCube(),inOrderDetail.getQuantity()));
      }
      var coreInventory = coreInventoryService.addInventory(productInfo, basePosition, commonDetailDto, inScanOrderBo.getScanInType());
      //#endregion

      //#region SN增加
      String singleSignCode = inEnterDetail.getSingleSignCode();
      // 商品开启SN管理 且 仓库未关闭SN管理
      if (Objects.equals(productInfo.getIsManageSn(), EnableEnum.ENABLE.getId()) && !Objects.equals(storageInfo.getSnDisabled(), EnableEnum.ENABLE.getId())) {
        List<String> snList = StringUtils.splitList(singleSignCode);

        List<CoreInventorySn> coreInventorySnList = snList.stream().map(sn -> {
          CoreInventorySn coreInventorySn = BeanUtil.copyProperties(coreInventory, CoreInventorySn.class);
          coreInventorySn.setEnable(EnableEnum.ENABLE.getId());
          coreInventorySn.setSnNo(sn);

          return coreInventorySn;
        }).toList();
        coreInventorySnService.saveBatch(coreInventorySnList);
      }
      //#endregion

      //#region 生成入库轨迹数据
      // 获取入库后：库存数量和重量
      BigDecimal afterQuantity = coreInventory.getProductStorage();
      BigDecimal afterWeight = coreInventory.getRowWeight();

      // 新增库存轨迹
      BeanUtil.copyProperties(inOrderDetail, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
      BeanUtil.copyProperties(inOrder, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
      commonDetailDto.setPositionNameIn(positionName);
      commonDetailDto.setMainId(inOrderDetail.getOrderId());
      commonDetailDto.setDetailId(inOrderDetail.getOrderDetailId());
      commonDetailDto.setBillCode(inOrder.getOrderCode());
      commonDetailDto.setSourceCode(inOrder.getTrackingNumber());
      commonDetailDto.setSourceCode2(inOrder.getSourceCode());
      commonDetailDto.setInventoryId(coreInventory.getInventoryId());
      commonDetailDto.setBigQty(coreInventory.getBigQty());
      commonDetailDto.setProduceDate(produceDate);
      commonDetailDto.setExpandFields(coreInventory.getExpandFields());
      if (ObjectUtil.isNull(commonDetailDto.getBatchNumber())) {
        commonDetailDto.setBatchNumber(batchNumber); // 批次号
      }


      coreInventoryHistoryService.addHistory(HistoryTypeEnum.IN, productInfo, commonDetailDto, inScanOrderBo.getScanInType(), beforeQuantity, beforeWeight, afterQuantity, afterWeight);
      //#endregion

      //#region 如果直接上架 更新预到货明细和入库单明细上架数量
      if (positionTypeEnumList.stream().anyMatch(a -> ObjectUtil.equals(a.getId(), basePosition.getPositionType()))) {
        // 更新预到货上架数量
        BigDecimal enterQuantity = B.nullToZero(inOrderDetail.getEnterQuantity()); // 当前已经入库数量
        BigDecimal shelvedQuantity = B.nullToZero(inOrderDetail.getShelvedQuantity()); // 当前已经上架数量
        inOrderDetail.setEnterQuantity(enterQuantity.add(finishedQuantity)); // 当前入库数量加上当前扫描数量
        inOrderDetail.setShelvedQuantity(shelvedQuantity.add(finishedQuantity)); // 当前入库数量加上当前扫描数量
        // 入库单明细上架数量更新
        inEnterDetail.setShelvedQuantity(inEnterDetail.getEnterQuantity());
        inEnterDetail.setDetailStatus(InEnterStatusEnum.FINISHED.getName());
        inEnterDetailService.updateById(inEnterDetail);
      }
      //#endregion

      // 如果是自产材入库，把货位更新到明细中
      if (StringUtils.isNotEmpty(inOrder.getOrderType()) && inOrder.getOrderType().equals(InSourceTypeEnum.IN_ZCC.getName())) {
        inOrderDetail.setPositionName(inScanOrderDetailBo.getPositionName());
      }
      inOrderDetail.setDefectiveQty(inScanOrderDetailBo.getDefectiveQty());
      inOrderDetailService.getBaseMapper().updateById(inOrderDetail); // 更新预到货明细上架数量
    }
    //#endregion

    statusCompute(ctx);
  }

  /**
   * 单据状态处理
   *
   * @param cxt 上下文
   */
  private void statusCompute(InScanContext cxt) {
    InScanOrderBo inScanOrderBo = cxt.getInScanOrderBo();
    InShelve inShelve = cxt.getInShelve();
    InEnter inEnter = cxt.getInEnter();
    InOrder inOrder = cxt.getInOrder();
    InQualityCheck inQualityCheck = cxt.getInQualityCheck();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据

    //#region 上架单合计数量
    if (cxt.isInGenerateShelve() && ObjectUtil.isNotNull(inShelve)) {
      List<InShelveDetail> shelveDetailList = inShelveDetailService.selectListByMainId(inShelve.getShelveId());
      BigDecimal totalQty = StreamUtils.sum(shelveDetailList, InShelveDetail::getQuantity);
      BigDecimal totalOnQty = StreamUtils.sum(shelveDetailList, InShelveDetail::getOnShelveQuantity);
      BigDecimal totalShelveQty = StreamUtils.sum(shelveDetailList, InShelveDetail::getShelvedQuantity);
      BigDecimal totalWeightShelve = shelveDetailList.stream().map(m -> B.mul(m.getQuantity(), m.getRowWeight())).reduce(BigDecimal.ZERO, BigDecimal::add);
      LambdaUpdateWrapper<InShelve> shelveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      shelveLambdaUpdateWrapper
        .set(InShelve::getTotalQuantity, totalQty)
        .set(InShelve::getTotalOnshelveQuantity, totalOnQty)
        .set(InShelve::getTotalShelvedQuantity, totalShelveQty)
        .set(InShelve::getTotalWeight, totalWeightShelve)
        // 如果数量上架完成直接修改状态为上架完成
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getShelveStatus, InShelveStatusEnum.FINISHED.getName()) // 完全上架
        .set(B.isGreater(totalShelveQty) && B.isGreater(totalQty, totalShelveQty), InShelve::getShelveStatus, InShelveStatusEnum.PARTIAL_FINISHED.getName()) // 部分上架
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getStartDate, DateUtil.date())
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getEndDate, DateUtil.date())
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getSpanTime, "0秒")
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getShelveType, InventorySourceTypeEnum.PC_RECEIVE_SHELVE_IN.getName())
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getNickName, LoginHelper.getNickname())
        .set(B.isGreaterOrEqual(totalShelveQty, totalQty), InShelve::getUserId, LoginHelper.getUserId())
        .eq(InShelve::getShelveId, inShelve.getShelveId());
      inShelveService.update(shelveLambdaUpdateWrapper);
    }
    //#endregion

    BigDecimal totalQuantity = StreamUtils.sum(dataList, InScanOrderDetailBo::getFinishedQuantity);
    BigDecimal totalWeight = StreamUtils.sum(dataList, (x) -> B.mul(x.getWeight(), x.getFinishedQuantity()));
    BigDecimal totalNewWeight = StreamUtils.sum(dataList, (x) -> B.mul(x.getNetWeight(), x.getFinishedQuantity()));
    BigDecimal totalAmount = StreamUtils.sum(dataList, (x) -> B.mul(x.getPurchasePrice(), x.getFinishedQuantity()));

    //#region 更新入库单合计
    List<InEnterDetail> enterDetailList = inEnterDetailService.selectListByMainId(inEnter.getEnterId());
    List<InOrderDetail> orderDetailList = inOrderDetailService.selectListByMainId(inEnter.getOrderId());
    BigDecimal totalEnterQuantity = enterDetailList.stream().map(InEnterDetail::getEnterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalShelveQty = enterDetailList.stream()
      .map(m -> B.nullToZero(m.getShelvedQuantity()))
      .reduce(BigDecimal.ZERO, BigDecimal::add);
    LambdaUpdateWrapper<InEnter> inEnterLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    inEnterLambdaUpdateWrapper
      .set(InEnter::getTotalEnterQuantity, totalEnterQuantity)
      .set(InEnter::getTotalWeight, totalWeight)
      .set(InEnter::getTotalNetWeight, totalNewWeight)
      .set(InEnter::getTotalAmount, totalAmount)
      .set(InEnter::getTotalShelvedQuantity, totalShelveQty)
      .set(totalShelveQty.compareTo(totalEnterQuantity) >= 0, InEnter::getEnterStatus, InEnterStatusEnum.FINISHED.getName())
      .eq(InEnter::getEnterId, inEnter.getEnterId());
    inEnterService.update(inEnterLambdaUpdateWrapper);


    if (enterDetailList.stream().allMatch(f -> B.isGreaterOrEqual(f.getShelvedQuantity(), f.getEnterQuantity()))) {
      // 完全上架
      inEnterService.updateEnterStatus(inEnter.getEnterId(), InEnterStatusEnum.FINISHED);
      //生成入库记录轨迹
      inEnterStatusHistoryService.addHistoryInfo(inEnter, cxt.getInEnterActionEnumShelve(), InEnterStatusEnum.FINISHED);
    } else if (enterDetailList.stream().anyMatch(f -> B.isGreater(f.getShelvedQuantity()) && B.isLess(f.getShelvedQuantity(), f.getEnterQuantity()))) {
      // 部分上架
      inEnterService.updateEnterStatus(inEnter.getEnterId(), InEnterStatusEnum.PARTIAL_FINISHED);
      //生成入库记录轨迹
      inEnterStatusHistoryService.addHistoryInfo(inEnter, cxt.getInEnterActionEnumShelve(), InEnterStatusEnum.PARTIAL_FINISHED);
    } else {
      // 确认入库
      inEnterService.updateEnterStatus(inEnter.getEnterId(), InEnterStatusEnum.ENTERED);
      //生成入库记录轨迹
      inEnterStatusHistoryService.addHistoryInfo(inEnter, cxt.getInEnterActionEnumEntered(), InEnterStatusEnum.ENTERED);
    }
    //#endregion

    //#region 更新更新质检单合计
    if (cxt.isInGenerateQualityCheck() && Objects.equals(inOrder.getIsChecking(), EnableEnum.ENABLE.getId())) {
      LambdaUpdateWrapper<InQualityCheck> inQualityCheckLambdaUpdateWrapper = new LambdaUpdateWrapper<>();

      inQualityCheckLambdaUpdateWrapper.set(InQualityCheck::getTotalQuantity, totalQuantity)
        .set(InQualityCheck::getTotalWeight, totalWeight)
        .set(InQualityCheck::getTotalNetWeight, totalNewWeight)
        .eq(InQualityCheck::getQualityCheckId, ObjectUtil.isNotNull(inQualityCheck) ? inQualityCheck.getQualityCheckId() : BigDecimal.ZERO);
      inQualityCheckService.update(inQualityCheckLambdaUpdateWrapper);
    }
    //#endregion

    //#region 获取到所有预到货的入库数量
    for (var orderDetail : orderDetailList) {
      // 明细入库数量，跨多个入库单求和
      totalEnterQuantity = inEnterDetailService.getEnterQuantity(orderDetail.getOrderId(), orderDetail.getOrderDetailId());

      orderDetail.setEnterQuantity(totalEnterQuantity);
      inOrderDetailService.getBaseMapper().updateById(orderDetail);
    }

    // 更新预到货主表合计入库数量和上架数量
    BigDecimal enterQuantity = orderDetailList.stream().map(InOrderDetail::getEnterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal shelveQuantity = orderDetailList.stream()
      .map(m -> Optional.ofNullable(m.getShelvedQuantity()).orElse(BigDecimal.ZERO))
      .reduce(BigDecimal.ZERO, BigDecimal::add);
    inOrder.setTotalShelvedQuantity(shelveQuantity);
    inOrder.setTotalEnterQuantity(enterQuantity);
    inOrder.setUserId(LoginHelper.getUserId());
    inOrder.setNickName(LoginHelper.getNickname());
    inOrderService.getBaseMapper().updateById(inOrder);

    // 预到货单状态处理
    BigDecimal totalShelvedQuantity = Optional.ofNullable(inOrder.getTotalShelvedQuantity()).orElse(BigDecimal.ZERO);
    totalQuantity = Optional.ofNullable(inOrder.getTotalQuantity()).orElse(BigDecimal.ZERO);

    //是否可以直接状态为收货完成
    boolean isAccomplish = dataList.stream().allMatch(item -> ObjectUtil.isNotNull(item.getIsAccomplish()) && item.getIsAccomplish());
    int size = orderDetailList.stream().filter(item -> B.isEqual(item.getEnterQuantity())).toList().size();

    if ((orderDetailList.stream().allMatch(f -> B.isGreaterOrEqual(f.getEnterQuantity(), f.getQuantity())) || isAccomplish) && B.isEqual(size)) {
      // 所有明细入库数量>=0明细预到货数量时，预到货单状态更新为：入库完成
      inOrderService.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.FINISHED);
      cxt.getInOrder().setOrderStatus(InOrderStatusEnum.FINISHED.getName());
      //根据预到货单的当前状态获取对应的枚举
      InOrderStatusEnum fromOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus());
      Assert.isFalse(ObjectUtil.isNull(inOrder.getOrderStatus()), "预到货单状态不存在！");
      //添加轨迹
      inOrderStatusHistoryService.addHistoryInfo(inOrder, cxt.getOrderActionEnumHistory(), fromOrderStatusEnum, InOrderStatusEnum.FINISHED);

      // 必须直接上架的才记录
      if (inScanOrderBo.isOnShelve() && totalShelvedQuantity.compareTo(totalQuantity) >= 0) {
        // 上架合计数量大于等于预到货合计数量，需要更新上架状态为已上架
        inOrderService.updateShelverStatus(inOrder.getOrderId(), InShelveStatusEnum.FINISHED);

        //根据预到货单的当前状态获取对应的枚举
        InOrderStatusEnum fromShelveStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getShelveStatus());
        Assert.isFalse(ObjectUtil.isNull(inOrder.getShelveStatus()), "预到货单上架状态不存在！");
        //添加轨迹
        inOrderStatusHistoryService.addHistoryInfo(inOrder, cxt.getOrderShelveActionEnum(), fromShelveStatusEnum, InOrderStatusEnum.HISTORY_FINISHED);
      }
    } else if (orderDetailList.stream().anyMatch(f -> B.isGreater(f.getEnterQuantity()))
      && orderDetailList.stream().anyMatch(f -> B.isLess(f.getEnterQuantity(), f.getQuantity()))) {
      // 预到货单状态更新为：部分入库
      inOrderService.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.PARTIAL_FINISHED);
      cxt.getInOrder().setOrderStatus(InOrderStatusEnum.PARTIAL_FINISHED.getName());

      //添加轨迹
      InOrderStatusEnum fromInOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus()); // 开始状态
      inOrderStatusHistoryService.addHistoryInfo(inOrder, cxt.getOrderActionEnumHistory(), fromInOrderStatusEnum, InOrderStatusEnum.PARTIAL_FINISHED);

      // 必须直接上架的才记录
      if (inScanOrderBo.isOnShelve() && totalShelvedQuantity.compareTo(totalQuantity) < 0
        && totalShelvedQuantity.compareTo(BigDecimal.ZERO) > 0) {
        // 上架状态更新为部分上架
        inOrderService.updateShelverStatus(inOrder.getOrderId(), InShelveStatusEnum.PARTIAL_FINISHED);
        InOrderStatusEnum fromShelveStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getShelveStatus());
        //添加轨迹
        inOrderStatusHistoryService.addHistoryInfo(inOrder, cxt.getOrderShelveActionEnum(), fromShelveStatusEnum, InOrderStatusEnum.HISTORY_PARTIAL_FINISHED);
      }
    }
    //#endregion

    //#region 如果有Lpn号 更新Lpn状态为已使用
    if (!ObjectUtil.isEmpty(inScanOrderBo.getLpnCode())) {
      LambdaUpdateWrapper<BasePlate> plateLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      plateLambdaUpdateWrapper.set(BasePlate::getIsUsing, UsingEnum.USING.getId())
        .eq(BasePlate::getPlateCode, inScanOrderBo.getLpnCode());
      basePlateService.update(plateLambdaUpdateWrapper);
    }
    //#endregion
  }
}
