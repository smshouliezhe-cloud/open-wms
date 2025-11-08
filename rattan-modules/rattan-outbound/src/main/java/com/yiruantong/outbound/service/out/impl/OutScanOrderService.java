package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.base.BaseExpressCorp;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.base.IBaseExpressCorpService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePlateProductService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderWaveStatusEnum;
import com.yiruantong.common.core.enums.out.OutPackageStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPickingVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.bo.OutOrderScanBo;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.operation.IOutOrderPickingDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.task.ITaskQueueService;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.yiruantong.common.satoken.utils.LoginHelper.getLoginUser;

/**
 * 出库单扫描service
 */
@RequiredArgsConstructor
@Service
public class OutScanOrderService implements IOutScanOrderService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IBaseExpressCorpService baseExpressCorpService;
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final IOutPackageService outPackageService;
  private final IOutPackageDetailService outPackageDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IBaseProductService baseProductService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService;
  private final ITaskQueueService taskQueueService;
  private final ISysConfigService sysConfigService;
  private final IBasePlateProductService basePlateProductService;
  private final IBaseStorageService baseStorageService;
  private final ICoreInventorySnService coreInventorySnService;
  private final IOutOrderPickingDetailService outOrderPickingDetailService;

  @Resource
  private final FlowExecutor flowExecutor;

  //#region getOutOrderData 获取出库单扫描数据
  @Override
  public R<Map<String, Object>> getOutOrderData(OutOrderScanBo outOrderScanBo) {

    String orderCode = outOrderScanBo.getOrderCode();
    Assert.isFalse(ObjectUtil.isEmpty(orderCode), orderCode + "扫描单号不能为空！");

    // 出库单信息
    OutOrder outOrder = outOrderService.getByCode(orderCode);
    Assert.isFalse(ObjectUtil.isNull(outOrder), orderCode + "不存在！");

    // 判断是否生成多个波次
    QueryWrapper<OutOrderWaveDetail> waveDetailQueryWrapper = new QueryWrapper<>();
    waveDetailQueryWrapper
      .select("COUNT(DISTINCT order_wave_id) AS cnt")
      .lambda()
      .eq(OutOrderWaveDetail::getOrderId, outOrder.getOrderId());
    Map<String, Object> longMap = outOrderWaveDetailService.getMap(waveDetailQueryWrapper);
    Assert.isFalse(ObjectUtil.isNotEmpty(longMap) && Convert.toLong(longMap.get("cnt")) >= 2,
      "该订单已经生成多个波次单，不允许按单出库，请使用波次打包出库！");

    // 按拣货数量打包开启
    var outer_pickQuantity = sysConfigService.getConfigBool("outer_pickQuantity");

    // 获取扫描明细信息
    MPJLambdaWrapper<OutOrderDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    detailMPJLambdaWrapper
      .select(OutOrder::getOrderCode, OutOrder::getStoreOrderCode)
      .select(BaseProduct::getBigBarcode, BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getIsManageSn, BaseProduct::getUnitConvert)
      .selectAll(OutOrderDetail.class)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderDetail::getProductId)
      .in(OutOrder::getSortingStatus, Arrays.asList(SortingStatusEnum.ASSIGNED.getId(), SortingStatusEnum.PARTIAL_ASSIGNED.getId()))
      .eq(OutOrder::getOrderId, outOrder.getOrderId())
      .in(OutOrder::getOrderStatus, List.of(OutOrderStatusEnum.AUDIT_SUCCESS.getName(), OutOrderStatusEnum.PICKED.getName(), OutOrderStatusEnum.MATCHED.getName()))
      .eq(OutOrder::getSortingStatus, SortingStatusEnum.ASSIGNED.getId());

    // 开启只出库下架理货位的数据
    if (outer_pickQuantity) {
      detailMPJLambdaWrapper.select(OutOrderWaveDetail::getPickQuantity)
        .select(OutOrderWaveDetail::getQuantityOuted, OutOrderWaveDetail::getPickQuantity)
        .gt(OutOrderWaveDetail::getPickQuantity, BigDecimal.ZERO)
        .innerJoin(OutOrderWaveDetail.class, on -> {
          on.eq(OutOrderWaveDetail::getOrderDetailId, OutOrderDetail::getOrderDetailId)
            .eq(OutOrderWaveDetail::getPositionType, PositionTypeEnum.UNLOADING.getId());
          return on;
        });

      detailMPJLambdaWrapper.apply("t3.pick_quantity - IFNULL(t3.quantity_outed, 0)>0");
    } else {
      detailMPJLambdaWrapper.apply("t.quantity_order - IFNULL(t.quantity_outed, 0)>0");
    }

    List<Map<String, Object>> detailList = outOrderDetailService.selectJoinMaps(detailMPJLambdaWrapper);
    Assert.isFalse(detailList.isEmpty(), "没有可用的商品明细！");

    // 仓库信息
    var storageInfo = baseStorageService.selectById(outOrder.getStorageId());
    // 获取波次单明细中的SN
    for (var detail : detailList) {
      // SN处理
      var isManageSn = Convert.toInt(detail.get("isManageSn"));
      var snDisabled = storageInfo.getSnDisabled(); // 仓库设置了不需SN管理
      if (snDisabled == 1) {
        isManageSn = 0; // 不需要SN
      }
      detail.put("isManageSn", isManageSn);

      // 获取下架SN
      if (B.isEqual(isManageSn, EnableEnum.ENABLE.getId())) {
        LambdaQueryWrapper<OutOrderPickingDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper
          .eq(OutOrderPickingDetail::getOrderId, outOrder.getOrderId())
          .eq(OutOrderPickingDetail::getOrderDetailId, detail.get("orderDetailId"))
          .eq(OutOrderPickingDetail::getProductId, detail.get("productId"))
          .isNotNull(OutOrderPickingDetail::getSingleSignCode);
        List<OutOrderPickingDetail> waveDetailList = outOrderPickingDetailService.list(detailLambdaQueryWrapper);
        String snList = waveDetailList.stream().map(OutOrderPickingDetail::getSingleSignCode).collect(Collectors.joining(","));
        detail.put("singleSignCode", snList);
      }
    }

    // 快递公司信息
    BaseExpressCorp corpInfo = null;
    if (ObjectUtil.isNotEmpty(outOrder.getExpressCorpId())) {
      corpInfo = baseExpressCorpService.getById(outOrder.getExpressCorpId());
    }

    //#region 获得最大箱号值
    LambdaQueryWrapper<OutPackageDetail> packageDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    packageDetailLambdaQueryWrapper
      .eq(OutPackageDetail::getOrderId, outOrder.getOrderId())
      .likeRight(OutPackageDetail::getCaseNumber, orderCode)
      .orderByDesc(OutPackageDetail::getCaseNumber);
    OutPackageDetail outPackageDetail = outPackageDetailService.getOne(packageDetailLambdaQueryWrapper);

    // 获取装箱号
    String caseNumber;

    if (Optional.ofNullable(outPackageDetail).map(OutPackageDetail::getCaseNumber).isEmpty()) {
      caseNumber = orderCode + "-01";
    } else {
      var caseNumbers = StringUtils.split(outPackageDetail.getCaseNumber(), "-");
      int num = Convert.toInt(caseNumbers[caseNumbers.length - 1]);
      String num1 = "0000" + ++num;
      caseNumber = orderCode + "-" + num1.substring(num1.length() - 2);
    }
    //#endregion

    // 返回数据
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("detailList", detailList);
    resultMap.put("orderInfo", outOrder);
    resultMap.put("caseNumber", caseNumber);
    if (ObjectUtil.isNotNull(corpInfo)) {
      resultMap.put("isExpressCorp", corpInfo.getIsExpressCorp());
      resultMap.put("isShowSubmit", corpInfo.getIsShowSubmit());
      resultMap.put("isExpressCorpSubmit", corpInfo.getIsExpressCorpSubmit());
    }

    // 部分打包的话  不进入
    if (!B.isEqual(outOrder.getPackageStatus(), OutPackageStatusEnum.PACKAGING_PARTIAL.getName())) {
      // 更新打包状态
      outOrderService.updatePackageStatus(outOrder.getOrderId(), OutPackageStatusEnum.PACKAGING);
    }

    return R.ok(resultMap);
  }
  //#endregion

  //#region getOutOrderDataFlashIn 一键闪出获取出库单扫描数据
  @Override
  public R<Map<String, Object>> getOutOrderDataFlashIn(OutOrderScanBo outOrderScanBo) {
    String orderCode = outOrderScanBo.getOrderCode();
    Assert.isFalse(ObjectUtil.isEmpty(orderCode), orderCode + "扫描单号不能为空！");

    // 出库单信息
    OutOrder outOrder = outOrderService.getByCode(orderCode);
    Assert.isFalse(ObjectUtil.isNull(outOrder), orderCode + "不存在！");
    Assert.isFalse(!StringUtils.equals(outOrder.getOrderStatus(), OutOrderStatusEnum.AUDIT_SUCCESS.getName()), "只有状态为审核成功的出库单才允许一键闪入");

    // 获取扫描明细信息
    MPJLambdaWrapper<OutOrderDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    detailMPJLambdaWrapper
      .select(OutOrder::getOrderCode, OutOrder::getStorageId, OutOrder::getStorageName, OutOrder::getConsignorId, OutOrder::getConsignorCode, OutOrder::getConsignorName)
      .select(BaseProduct::getBigBarcode, BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getIsManageSn, BaseProduct::getUnitConvert, BaseProduct::getProviderId, BaseProduct::getProviderCode, BaseProduct::getProviderShortName)
      .selectAll(OutOrderDetail.class)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderDetail::getProductId)
      .eq(OutOrder::getOrderCode, orderCode);

    List<Map<String, Object>> detailList = outOrderDetailService.selectJoinMaps(detailMPJLambdaWrapper);
    Assert.isFalse(detailList.isEmpty(), "没有可用的商品明细！");
    detailList.forEach(item -> item.put("orderDetailId", null)); // 清空明细主键ID

    // 仓库信息
    var storageInfo = baseStorageService.selectById(outOrder.getStorageId());

    // 返回数据
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("detailList", detailList);
    resultMap.put("orderInfo", outOrder);

    return R.ok(resultMap);
  }
  //#endregion

  //#region 保存出库单扫描数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<OutOrder> normalOutSave(@NotNull OutScanMainBo outScanMainBo) {
    LiteflowResponse response = flowExecutor.execute2Resp("OutScanChain", outScanMainBo, OutScanContext.class);
    Assert.isFalse(!response.isSuccess(), response.getMessage());

    return R.ok();
  }
  //#endregion

  //#region 获取波次打包扫描数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> getBatchPackageData(Map<String, Object> map) {
    String orderWaveCode = Convert.toStr(map.get("orderWaveCode"));
    cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isEmpty(orderWaveCode), "波次单号不能为空！");

    LoginUser loginUser = getLoginUser();
    cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isNull(loginUser), "当前用户不存在！");

    String mainWaveCode = orderWaveCode; // 主波次号
    //区分主波次 子波次
    if (orderWaveCode.contains("-")) {
      var arr = orderWaveCode.split("-");
      mainWaveCode = arr[0];
    }
    var orderWave = outOrderWaveService.getByCode(mainWaveCode);
    if (ObjectUtil.isEmpty(orderWave)) {
      throw new ServiceException("波次不存在！");
    }

    //    String[] statusList = new String[]{OutOrderWaveStatusEnum.WAIT_PICKING.getName(), OutOrderWaveStatusEnum.PC_ORDER_PICKING.getName(), OutOrderWaveStatusEnum.PART_PICKING.getName(), OutOrderWaveStatusEnum.WAVE_FINISHED.getName(), OutOrderWaveStatusEnum.PACKAGE_PARTIAL.getName(), OutOrderWaveStatusEnum.MATCHED.getName()};
    String[] statusList = new String[]{OutOrderWaveStatusEnum.PICKING.getName(), OutOrderWaveStatusEnum.WAVE_FINISHED.getName(), OutOrderWaveStatusEnum.PICKED.getName()};

    if (!Arrays.asList(statusList).contains(orderWave.getWaveStatus())) {
      throw new ServiceException("波次单必须是" + String.join(",", statusList) + "才允许下架操作，当前状态是[" + orderWave.getWaveStatus() + "]！");
    }

    // 查询字段
    String selectFields = "orderId,orderCode,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,bigUnit,smallUnit," +
      "pickQuantity,holderStorage,salePrice,remark,orderId,orderDetailId,orderWaveDetailId,plateCode,positionName,weight,rowWeight," +
      "consignorId,consignorCode,consignorName,storageId,storageName,middleBarcode,middleUnitConvert,bigBarcode,unitConvert," +
      "singleSignCode,areaCode,channelCode,brandName," +
      "relationCode,relationCode2,relationCode3,relationCode4,relationCode5,middleUnitConvert,bigBarcode,unitConvert,isManageSn";

    // 求和字段
    String sumFields = "quantityOrder,quantityOrderOrigin,quantityOuted,holderStorage,rowWeight";

    // 分组字段
    String groupFields = "storageId,storageName,consignorId,consignorCode,consignorName,positionName,orderId,orderCode,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,plateCode,areaCode,channelCode,columnCode,rowCode,middleBarcode,middleUnitConvert,bigBarcode,unitConvert";

    // 构建联表查询
    MPJLambdaWrapper<OutOrderWaveDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderWaveDetail::getProductId)
      .innerJoin(CoreInventoryHolder.class, CoreInventoryHolder::getHolderId, OutOrderWaveDetail::getHolderId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventoryHolder::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventoryHolder::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .eq(OutOrderWave::getOrderWaveId, orderWave.getOrderWaveId())
      .in(OutOrderWaveDetail::getPositionType, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.STORAGE.getId(), PositionTypeEnum.ELEVATED.getId()));

    // 货位优先
    wrapper.orderByAsc(OutOrderWaveDetail::getPositionName)
      .orderByAsc(BasePosition::getRowCode)
      .orderByAsc(BasePosition::getColumnCode);

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, OutOrderWaveDetail.class, OutOrderWave.class, BaseProduct.class, CoreInventoryHolder.class, BasePosition.class);
    List<OutOrderWaveDetailPickingVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailPickingVo.class, wrapper);
    Map<String, Object> result = new HashMap<>();

    // 获取下架理货位
    result.put("dataList", inventoryComposeVoList);

    //获得最大箱号值
    LambdaQueryWrapper<OutPackageDetail> packageDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    packageDetailLambdaQueryWrapper
      .apply("order_id IN(SELECT order_id FROM out_order p WHERE p.order_wave_code={0})", orderWaveCode)
      .orderByDesc(OutPackageDetail::getCaseNumber);
    OutPackageDetail outPackageDetail = outPackageDetailService.getOne(packageDetailLambdaQueryWrapper);

    // 获取装箱号
    String caseNumber;
    if (ObjectUtil.isEmpty(outPackageDetail) || ObjectUtil.isEmpty(outPackageDetail.getCaseNumber())) {
      caseNumber = orderWaveCode + "-01";
    } else {
      var caseNumbers = StringUtils.split(outPackageDetail.getCaseNumber(), "-");
      int num = Convert.toInt(caseNumbers[caseNumbers.length - 1]);
      String num1 = "0000" + ++num;
      caseNumber = orderWaveCode + "-" + num1.substring(num1.length() - 2);
    }
    result.put("caseNumber", caseNumber);

    // 更新波次单名下的所有订单打包状态
    List<Long> orderIdlist = inventoryComposeVoList.stream().map(OutOrderWaveDetail::getOrderId).distinct().toList();
    for (var orderId : orderIdlist) {
      // 更新打包状态
      outOrderService.updatePackageStatus(orderId, OutPackageStatusEnum.PACKAGING);
    }

    return R.ok(result);
  }
  //#endregion

  //#region 保存出库单扫描数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveBatchPackageData(OutScanMainBo outScanMainBo) {
    List<OutScanDetailBo> dataList = outScanMainBo.getDataList();
    for (OutScanDetailBo outDetail : dataList) {
      Assert.isFalse(ObjectUtil.isEmpty(outDetail.getOrderId()), "出库单ID不能为空！");
    }

    Map<Long, List<OutScanDetailBo>> listMap = dataList.stream().collect(Collectors.groupingBy(OutScanDetailBo::getOrderId)); // 分组，核心方法Collectors.groupingBy
    for (var groupItem : listMap.entrySet()) {
      Long orderId = groupItem.getKey();
      OutScanMainBo newOutScanMainBo = BeanUtil.copyProperties(outScanMainBo, OutScanMainBo.class);
      newOutScanMainBo.setDataList(groupItem.getValue());
      newOutScanMainBo.setOrderId(orderId);
      this.normalOutSave(newOutScanMainBo);
    }

    return R.ok("出库完成");
  }
  //#endregion

  //#region 获取拍号
  @Override
  public R<Map<String, Object>> getOutOrderPlate(OutOrderScanBo outOrderScanBo) {

    String orderCode = outOrderScanBo.getOrderCode();
    Assert.isFalse(ObjectUtil.isEmpty(orderCode), orderCode + "扫描单号不能为空！");

    // 出库单信息
    OutOrder outOrder = outOrderService.getByCode(orderCode);
    Assert.isFalse(ObjectUtil.isNull(outOrder), orderCode + "不存在！");

    // 判断是否生成多个波次
    QueryWrapper<OutOrderWaveDetail> waveDetailQueryWrapper = new QueryWrapper<>();
    waveDetailQueryWrapper
      .select("COUNT(DISTINCT order_wave_id) AS cnt")
      .lambda()
      .eq(OutOrderWaveDetail::getOrderId, outOrder.getOrderId());
    Map<String, Object> longMap = outOrderWaveDetailService.getMap(waveDetailQueryWrapper);
    Assert.isFalse(ObjectUtil.isNotEmpty(longMap) && Convert.toLong(longMap.get("cnt")) >= 2,
      "该订单已经生成多个波次单，不允许按单出库，请使用波次打包出库！");

    // 按拣货数量打包开启
    var outer_pickQuantity = sysConfigService.getConfigBool("outer_pickQuantity");

    // 获取扫描明细信息
    MPJLambdaWrapper<OutOrderDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    detailMPJLambdaWrapper
      .select(CoreInventory::getPlateCode)
      .select(OutOrder::getOrderCode)
      .select(BaseProduct::getBigBarcode, BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getIsManageSn, BaseProduct::getUnitConvert)
      .selectAll(OutOrderDetail.class)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderDetail::getProductId)
      .innerJoin(CoreInventoryHolder.class, on -> on.eq(CoreInventoryHolder::getMainId, OutOrderDetail::getOrderId).eq(CoreInventoryHolder::getDetailId, OutOrderDetail::getOrderDetailId))
      .in(OutOrder::getSortingStatus, Arrays.asList(SortingStatusEnum.ASSIGNED.getId(), SortingStatusEnum.PARTIAL_ASSIGNED.getId()))
      .eq(OutOrder::getOrderCode, orderCode)
      .gt(CoreInventoryHolder::getHolderStorage, 0);

    // 开启只出库下架理货位的数据
    if (outer_pickQuantity) {
      detailMPJLambdaWrapper.select(OutOrderWaveDetail::getPickQuantity)
        .select(OutOrderWaveDetail::getQuantityOuted)
        .gt(OutOrderWaveDetail::getPickQuantity, BigDecimal.ZERO)
        .innerJoin(OutOrderWaveDetail.class, on -> {
          on.eq(OutOrderWaveDetail::getOrderDetailId, OutOrderDetail::getOrderDetailId)
            .eq(OutOrderWaveDetail::getPositionType, PositionTypeEnum.UNLOADING.getId());
          return on;
        });

      detailMPJLambdaWrapper.apply("t3.pick_quantity - IFNULL(t3.quantity_outed, 0)>0");
    } else {
      detailMPJLambdaWrapper.apply("t.quantity_order - IFNULL(t.quantity_outed, 0)>0");
    }

    List<Map<String, Object>> detailList = outOrderDetailService.selectJoinMaps(detailMPJLambdaWrapper);
    Assert.isFalse(detailList.isEmpty(), "没有可用的商品明细！");
    Assert.isFalse(detailList.stream().filter(f -> ObjectUtil.isNotEmpty(f.get("plateCode"))).toList().isEmpty(), "当前出库单没有可用的托盘号！");

    // 仓库信息
    var storageInfo = baseStorageService.selectById(outOrder.getStorageId());
    // 获取波次单明细中的SN
    for (var detail : detailList) {
      // 开启按拣货数量按钮后，重新计算扫描数量
      if (outer_pickQuantity) {
        detail.put("quantityOuted", detail.get("quantityOuted(1)"));
      }

      // SN处理
      var isManageSn = Convert.toInt(detail.get("isManageSn"));
      var snDisabled = storageInfo.getSnDisabled(); // 仓库设置了不需SN管理
      if (snDisabled == 1) {
        isManageSn = 0; // 不需要SN
      }
      detail.put("isManageSn", isManageSn);

      // 获取下架SN
      if (B.isEqual(isManageSn, EnableEnum.ENABLE.getId())) {
        LambdaQueryWrapper<OutOrderPickingDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper
          .eq(OutOrderPickingDetail::getOrderId, outOrder.getOrderId())
          .eq(OutOrderPickingDetail::getOrderDetailId, detail.get("orderDetailId"))
          .eq(OutOrderPickingDetail::getProductId, detail.get("productId"))
          .isNotNull(OutOrderPickingDetail::getSingleSignCode);
        List<OutOrderPickingDetail> waveDetailList = outOrderPickingDetailService.list(detailLambdaQueryWrapper);
        String snList = waveDetailList.stream().map(OutOrderPickingDetail::getSingleSignCode).collect(Collectors.joining(","));
        detail.put("singleSignCode", snList);
      }
    }

    // 返回数据
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("detailList", detailList);
    resultMap.put("orderInfo", outOrder);

    // 部分打包的话  不进入
    if (!B.isEqual(outOrder.getPackageStatus(), OutPackageStatusEnum.PACKAGING_PARTIAL.getName())) {
      // 更新打包状态
      outOrderService.updatePackageStatus(outOrder.getOrderId(), OutPackageStatusEnum.PACKAGING);
    }

    return R.ok(resultMap);
  }
  //endregion
}
