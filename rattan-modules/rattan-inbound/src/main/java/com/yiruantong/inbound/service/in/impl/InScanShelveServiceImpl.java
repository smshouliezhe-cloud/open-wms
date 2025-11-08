package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.github.yulichang.wrapper.UpdateJoinWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.bo.BasePositionSearchBo;
import com.yiruantong.basic.domain.storage.vo.BasePositionVo;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePlateService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.in.*;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.helper.RecommendPositionDto;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.enums.HistoryTypeEnum;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyService;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.inventory.service.operation.IStorageOuterService;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.task.ITaskQueueService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
@Service
public class InScanShelveServiceImpl implements IInScanShelveService {
  //#region service定义
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IInEnterService inEnterService;
  private final IInEnterDetailService inEnterDetailService;
  private final IBasePositionService basePositionService;
  private final IBasePlateService basePlateService;
  private final IBaseProductService baseProductService;
  private final IInQualityCheckService inQualityCheckService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventorySnService coreInventorySnService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;
  private final IInShelveDetailStepService inShelveDetailStepService;
  private final IInScanOrderService inScanService;
  private final IInEnterStatusHistoryService inEnterStatusHistoryService;
  private final IOutOrderService outOrderService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IStorageOuterService storageOuterService;
  private final IStorageAllocateApplyService storageAllocateApplyService;
  private final IRecommendPositionService recommendPositionService;
  private final ITaskQueueService taskQueueService;
  //#endregion

  //region 获取上架单扫描数据

  /**
   * 获取入库单扫描数据
   *
   * @param map 前端参数
   */
  @Override
  public R<List<Map<String, Object>>> getShelveData(Map<String, Object> map) {
    Assert.notEmpty(map, "参数不能为空！");
    String shelveCode = map.get("shelveCode").toString();
    Assert.isFalse(StringUtils.isEmpty(shelveCode), "参数不能为空！");

    // 系统参数设置中设置了收货后上架前进行质检时，操作上架的时候需要增加判断：当前上架单对应的入库单所对应的质检单是否质检完成
    String in_inboundQualityPosition = sysConfigService.selectConfigByKey("in_inboundQualityPosition");

    InShelve inShelve = inShelveService.getByCode(shelveCode);
    Assert.isFalse(ObjectUtil.isEmpty(inShelve), shelveCode + "待上架单不存在！");

    String[] statusList = {InShelveStatusEnum.WAITING.getName(), InShelveStatusEnum.PARTIAL_FINISHED.getName(), InShelveStatusEnum.SHELVING.getName()};
    Assert.isFalse(Arrays.stream(statusList).noneMatch(m -> B.isEqual(m, inShelve.getShelveStatus())), "只有待上架、部分上架或上架中的才允许操作！");

    // 收货后，上架前质检验证
    var enterInfo = inEnterService.getById(inShelve.getEnterId());
    if (ObjectUtil.isNotEmpty(enterInfo)) {
      var orderInfo = inOrderService.getById(enterInfo.getOrderId());

      Assert.isFalse(ObjectUtil.isEmpty(orderInfo), enterInfo.getOrderCode() + "预到货单不存在!");
      if (ObjectUtil.isNotEmpty(orderInfo)) {
        inScanService.qualityCheck(orderInfo, QualityCheckTypeEnum.INBOUND_AFTER);
      }

      String qualityCheckTypeEnum = String.valueOf(QualityCheckTypeEnum.INBOUND_AFTER);
      //#region 收货后上架前质检验
      if (NumberUtil.equals(orderInfo.getIsChecking(), EnableEnum.ENABLE.getId())) {
        if (in_inboundQualityPosition.equals(qualityCheckTypeEnum)) {
          LambdaQueryWrapper<InQualityCheck> queryWrapper = new LambdaQueryWrapper<>();
          queryWrapper.eq(InQualityCheck::getOrderCode, orderInfo.getOrderCode());
          var inQualityCheck = inQualityCheckService.getOnly(queryWrapper);

          if (ObjectUtil.isEmpty(inQualityCheck) || !Objects.equals(inQualityCheck.getAuditing(), AuditEnum.AUDITED_SUCCESS.getId())) {
            throw new ServiceException("需要质检！");
          }
        }
      }
    }


    try {
      // 获取仓库信息
      var storageInfo = baseStorageService.getById(inShelve.getStorageId());

      MPJLambdaWrapper<InShelveDetail> detailWrapper = new MPJLambdaWrapper<>();
      detailWrapper
        .select(InShelve::getConsignorId, InShelve::getStorageId, InShelve::getShelveCode)
        .select(InShelveDetail::getEnterDetailId, InShelveDetail::getShelveDetailId,
          InShelveDetail::getQuantity, InShelveDetail::getOnShelveQuantity, InShelveDetail::getShelvedQuantity,
          InShelveDetail::getPositionName, InShelveDetail::getPlateCode, InShelveDetail::getProduceDate, InShelveDetail::getPurchasePrice, InShelveDetail::getWeight, InShelveDetail::getRowWeight, InShelveDetail::getSingleSignCode, InShelveDetail::getBigQty, InShelveDetail::getLimitDate, InShelveDetail::getShelfLifeDay, InShelveDetail::getBatchNumber, InShelveDetail::getCaseNumber)
        .select(BaseProduct::getProviderId, BaseProduct::getProductId, BaseProduct::getProductName, BaseProduct::getProductCode,
          BaseProduct::getProductModel, BaseProduct::getProductSpec, BaseProduct::getRelationCode, BaseProduct::getRelationCode2,
          BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getMiddleUnit,
          BaseProduct::getMiddleUnitConvert, BaseProduct::getBigBarcode, BaseProduct::getUnitConvert, BaseProduct::getSmallUnit,
          BaseProduct::getIsManageSn, BaseProduct::getBrandName, BaseProduct::getBigUnit)
        //.select("ISNULL(quantity,0) - ISNULL(L.shelvedQuantity,0) AS quantity")
        .innerJoin(InShelve.class, InShelve::getShelveId, InShelveDetail::getShelveId)
        .innerJoin(BaseProduct.class, BaseProduct::getProductId, InShelveDetail::getProductId)
        .eq(InShelve::getShelveId, inShelve.getShelveId())
        .apply("t.quantity>t.shelved_quantity")
        .orderByAsc(InShelveDetail::getProductModel);

      var dataList = inShelveDetailService.selectJoinMaps(detailWrapper);
      if (dataList.isEmpty()) {
        throw new ServiceException("上架单号没有可上架的数量！");
      }

      //更新这个上架单为当前用户所有
      LambdaUpdateWrapper<InShelve> shelveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      shelveLambdaUpdateWrapper.set(InShelve::getUserId, LoginHelper.getUserId())
        .set(InShelve::getNickName, LoginHelper.getNickname())

        .eq(InShelve::getShelveId, inShelve.getShelveId());
      if (ObjectUtil.isEmpty(inShelve.getStartDate())) {
        shelveLambdaUpdateWrapper.set(InShelve::getStartDate, DateUtils.getNowDate());
      }
      if (StrUtil.equals(inShelve.getShelveStatus(), InShelveStatusEnum.WAITING.getName())) {
        shelveLambdaUpdateWrapper.set(InShelve::getShelveStatus, InShelveStatusEnum.SHELVING.getName());
      }
      inShelveService.update(shelveLambdaUpdateWrapper);

      for (var item : dataList) {
        // 上架推荐货位
        RecommendPositionDto recommendPositionDto = new RecommendPositionDto();// 复制数据
        recommendPositionDto.setInQty(Convert.toBigDecimal(item.get("quantity")));
        recommendPositionDto.setPlateCode(Convert.toStr(item.get("plateCode")));
        recommendPositionDto.setBatchNumber(Convert.toStr(item.get("batchNumber")));
        recommendPositionDto.setProductId(Convert.toLong(item.get("productId")));
        recommendPositionDto.setProductSpec(Convert.toStr(item.get("productSpec")));
        recommendPositionDto.setConsignorId(Convert.toLong(item.get("consignorId")));
        recommendPositionDto.setStorageId(Convert.toLong(item.get("storageId")));
        recommendPositionDto.setProviderId(Convert.toLong(item.get("providerId")));
        recommendPositionDto.setWeight(Convert.toBigDecimal(item.get("weight")));
        recommendPositionDto.setUnitCube(Convert.toBigDecimal(item.get("unitCube")));
        String positionName = recommendPositionService.getRecommendPosition(recommendPositionDto);

        // 开启SN管理状态处理
        item.put("positionName", positionName);
        Byte isManageSn = Convert.toByte(item.get("isManageSn"));
        boolean snDisabled = NumberUtil.equals(storageInfo.getSnDisabled(), EnableEnum.ENABLE.getId());
        // 仓库设置了不需SN管理
        if (snDisabled) {
          isManageSn = 0; // 不需要SN
        }
        item.put("isManageSn", isManageSn);
      }

      return R.ok(dataList);
    } catch (Exception ex) {
      return R.fail("获取数据失败：" + ex.getMessage());
    }
  }
  //endregion

  //region 获取上架单货位

  /**
   * 获取上架单货位
   *
   * @param map 前端参数
   */
  @Override
  public R<List<Map<String, Object>>> getShelvePositionList(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String name = Convert.toStr(map.get("name"));

    BasePositionSearchBo positionSearchBo = new BasePositionSearchBo();
    positionSearchBo.setStorageId(storageId);
    positionSearchBo.setName(name);
    positionSearchBo.setOnShelve(true); // 获取上架货位的数据
    positionSearchBo.setPositionTypes("1,2,12,13"); // 获取上架货位的数据
    List<Map<String, Object>> positionList = basePositionService.getPositionList(positionSearchBo);

    return R.ok(positionList);
  }
  //endregion

  //region 上架扫描保存
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> shelveSave(@NotNull InScanOrderBo inScanOrderBo) {
    String shelveCode = inScanOrderBo.getShelveCode();
    List<InScanOrderDetailBo> details = inScanOrderBo.getDataList();
    Assert.isFalse(ObjectUtil.isEmpty(details), shelveCode + "扫描明细不能为空！");
    // 根据上架单号查询入库单号
    var shelveInfo = inShelveService.getByCode(shelveCode);
    Assert.isFalse(ObjectUtil.isEmpty(shelveInfo), shelveCode + "上架单不存在！");
    // 仓库信息
    BaseStorage baseStorage = baseStorageService.getById(shelveInfo.getStorageId());
    InOrder orderInfo = inOrderService.getByCode(shelveInfo.getOrderCode());

    // 系统参数设置中设置了收货后上架前进行质检时，操作上架的时候需要增加判断：当前上架单对应的入库单所对应的质检单是否质检完成
    String in_inboundQualityPosition = sysConfigService.selectConfigByKey("in_inboundQualityPosition");
    // 按拍上架推荐货位根据拍数推荐
    boolean in_shelvePaiCount = sysConfigService.getConfigBool("in_shelvePaiCount");

    //#region 收货后上架前质检验
    if (StringUtils.equals(in_inboundQualityPosition, QualityCheckTypeEnum.INBOUND_AFTER.getName())) {
      // 根据入库单号查询预到货单号
      var enterInfo = inEnterService.getById(shelveInfo.getEnterId());
      if (!StringUtils.equals(enterInfo.getOrderType(), InOrderTypeEnum.NO_BILL.getName())) {
        LambdaQueryWrapper<InQualityCheck> checkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        checkLambdaQueryWrapper
          .eq(InQualityCheck::getAuditing, AuditEnum.AUDIT.getId())
          .eq(InQualityCheck::getOrderCode, enterInfo.getOrderCode());
        // 同时根据入库单号与预到货单号查询质检单，是否存在质检单，存在是否质检完成
        var qualityCheck = inQualityCheckService.getOne(checkLambdaQueryWrapper);
        // 存在且未质检
        Assert.isFalse(ObjectUtil.isNotEmpty(qualityCheck), "请先进行质检处理！");
      }
    }
    //#endregion

    //#region 上架校验
    for (InScanOrderDetailBo scanDetail : details) {
      String shelvePositionName = scanDetail.getShelvePositionName();  // 上架货位
      Long productId = scanDetail.getProductId();
      Long shelveDetailId = scanDetail.getShelveDetailId(); // 上架单明细

      // 上架单明细
      InShelveDetail inShelveDetail = inShelveDetailService.getById(shelveDetailId);
      Assert.isFalse(ObjectUtil.isEmpty(inShelveDetail), "上架单明细不存在！");

      // 验证货位是否存在，必须是收货位和下架理货位
      List<PositionTypeEnum> positionTypeEnumList = Arrays.asList(PositionTypeEnum.NORMAL, PositionTypeEnum.ELEVATED, PositionTypeEnum.STORAGE, PositionTypeEnum.SCRAP);
      var positionExist = basePositionService.existEnableByName(shelveInfo.getStorageId(), shelvePositionName, positionTypeEnumList);
      Assert.isFalse(!positionExist, "货位：" + shelvePositionName + "不可用！");

      // 获取当前货位内已有托盘数
      Long plateCount = coreInventoryService.getPlateCount(shelveInfo.getStorageId(), shelvePositionName);
      // 货位信息
      BasePosition basePosition = basePositionService.getByName(shelveInfo.getStorageId(), shelvePositionName);
      // 按拍上架推荐货位根据拍数推荐 且 如果当前库存内的实物拍数大于货位最大拍数时
      Assert.isFalse(in_shelvePaiCount && plateCount > basePosition.getMaxPaiQty(), "货位：" + shelvePositionName + "入库数量已经超过货位最大托盘数，禁止入库！");

      // 获取当前货位库存数量
      BigDecimal productStorage = coreInventoryService.getPositionStorage(shelveInfo.getStorageId(), shelvePositionName);
      BigDecimal maxCapacity = Optional.ofNullable(basePosition.getMaxCapacity()).orElse(BigDecimal.ZERO);
      // 验证入库数量是否大于货位最大容量
      if (B.isGreater(productStorage, maxCapacity) && B.isGreater(maxCapacity)) {
        throw new ServiceException("货位：" + shelvePositionName + "入库数量已经超过货位最大数量，禁止入库！");
      }
      // 验证是否允许混物料
      Assert.isFalse(!coreInventoryService.isMixProduct(shelveInfo.getStorageId(), productId, shelvePositionName), shelvePositionName + "不允许混物料存放！");

      // 验证SN是否存在
    }
    //#endregion

    //#region 上架入库、出库状态类型
    InventorySourceTypeEnum inventorySourceTypeEnum = inScanOrderBo.getScanInType();
    InventorySourceTypeEnum inventorySourceTypeEnum_out = InventorySourceTypeEnum.PC_SCAN_SHELVE_OUT;
    InOrderActionEnum inOrderActionEnum = InOrderActionEnum.PC_SCAN_SHELVE_IN;
    InEnterActionEnum inEnterActionEnum = InEnterActionEnum.PC_SCAN_SHELVE_IN;
    switch (inScanOrderBo.getScanInType()) {
      case PC_SCAN_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PC_SCAN_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PC_SCAN_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PC_SCAN_SHELVE_OUT;
      }
      case PC_NO_BILL_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PC_NO_BILL_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PC_NO_BILL_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PC_NO_BILL_SHELVE_OUT;
      }
      case PC_LPN_SCAN_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PC_LPN_SCAN_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PC_LPN_SCAN_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PC_LPN_SCAN_SHELVE_OUT;
      }
      case PC_PLATE_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PC_PLATE_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PC_PLATE_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PC_PLATE_SHELVE_OUT;
      }
      case PDA_SCAN_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PDA_SCAN_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PDA_SCAN_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PDA_SCAN_SHELVE_OUT;
      }
      case PDA_LPN_SCAN_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PDA_LPN_SCAN_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PDA_LPN_SCAN_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PDA_LPN_SCAN_SHELVE_OUT;
      }
      case PDA_PLATE_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PDA_PLATE_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PDA_PLATE_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PDA_PLATE_SHELVE_OUT;
      }
      case PDA_NO_BILL_SHELVE_IN -> {
        inOrderActionEnum = InOrderActionEnum.PDA_NO_BILL_SHELVE_IN;
        inEnterActionEnum = InEnterActionEnum.PDA_NO_BILL_SHELVE_IN;
        inventorySourceTypeEnum_out = InventorySourceTypeEnum.PDA_NO_BILL_SHELVE_OUT;
      }
    }
    //#endregion

    //#region 上架处理
    for (InScanOrderDetailBo scanDetail : details) {
      String shelvePositionName = scanDetail.getShelvePositionName();  // 上架货位
      String singleSignCode = scanDetail.getSingleSignCode(); // SN号
      Long productId = scanDetail.getProductId();
      Long shelveDetailId = scanDetail.getShelveDetailId(); // 上架单明细
      BigDecimal finishedQuantity = scanDetail.getFinishedQuantity(); // 扫描完成的数量

      // 上架单明细
      InShelveDetail inShelveDetail = inShelveDetailService.getById(shelveDetailId);
      // 商品信息
      var productInfo = baseProductService.getById(productId);
      // 货位信息
      BasePosition basePosition = basePositionService.getByName(shelveInfo.getStorageId(), shelvePositionName);

      // 更新上架单明细实际上架货位
      LambdaUpdateWrapper<InShelveDetail> shelveDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      shelveDetailLambdaUpdateWrapper.set(InShelveDetail::getPositionNo, shelvePositionName)
        .eq(InShelveDetail::getShelveDetailId, inShelveDetail.getShelveDetailId());
      inShelveDetailService.update(shelveDetailLambdaUpdateWrapper);

      //#region 生成上架明细步骤
      InShelveDetailStep inShelveDetailStep = new InShelveDetailStep();
      BeanUtil.copyProperties(inShelveDetail, inShelveDetailStep);
      inShelveDetailStep.setShelveId(shelveInfo.getShelveId());
      inShelveDetailStep.setShelveDetailId(inShelveDetail.getShelveDetailId());
      inShelveDetailStep.setShelveStatus(InShelveStatusEnum.SHELVING.getName()); // 状态为上架中
      inShelveDetailStep.setPositionName(shelvePositionName); // 上架货位
      inShelveDetailStep.setQuantity(finishedQuantity); // 完成数量
      inShelveDetailStep.setProduceDate(scanDetail.getProduceDate());
      inShelveDetailStep.setCreateTime(DateUtil.date());
      inShelveDetailStep.setUpdateTime(DateUtil.date());
      inShelveDetailStepService.save(inShelveDetailStep);
      //#endregion

      //#region 获取入库前：库存数量和重量
      BigDecimal beforeQuantity = BigDecimal.ZERO; // 保存前数量
      BigDecimal beforeWeight = BigDecimal.ZERO;   // 保存前重量
      //#endregion

      //#region 增加库存
      CommonDetailDto commonDetailDto = BeanUtil.copyProperties(shelveInfo, CommonDetailDto.class);
      BeanUtil.copyProperties(inShelveDetail, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));
      commonDetailDto.setMainId(shelveInfo.getOrderId());
      commonDetailDto.setDetailId(inShelveDetail.getOrderDetailId());
      commonDetailDto.setBillCode(shelveInfo.getShelveCode());
      commonDetailDto.setIsManageSn(scanDetail.getIsManageSn());
      commonDetailDto.setBatchNumber(scanDetail.getBatchNumber());
      commonDetailDto.setProduceDate(scanDetail.getProduceDate());
      commonDetailDto.setLimitDate(scanDetail.getLimitDate());
      commonDetailDto.setShelfLifeDay(scanDetail.getShelfLifeDay());

      if (StrUtil.isNotEmpty(orderInfo.getSourceCode())) {
        commonDetailDto.setSourceCode(orderInfo.getSourceCode());
      } else {
        commonDetailDto.setSourceCode(orderInfo.getOrderCode());
      }

      commonDetailDto.setInQuantity(finishedQuantity);
      // 质检状态
      if (StringUtils.equals(orderInfo.getCheckingStatus(), InCheckingStatusEnum.UNCHECKED.getName())) {
        commonDetailDto.setCheckingStatusEnum(InCheckingStatusEnum.UNCHECKED);
      }

      var coreInventory = coreInventoryService.addInventory(productInfo, basePosition, commonDetailDto, inventorySourceTypeEnum);
      //#endregion

      //#region 获取入库后：库存数量和重量 生成入库轨迹数据
      BigDecimal afterQuantity = coreInventory.getProductStorage();
      BigDecimal afterWeight = coreInventory.getRowWeight();

      // 生成入库轨迹数据
      commonDetailDto.setInventoryId(coreInventory.getInventoryId());
      commonDetailDto.setPositionNameIn(shelvePositionName);
      commonDetailDto.setMainId(inShelveDetail.getShelveId());
      commonDetailDto.setDetailId(inShelveDetail.getShelveDetailId());
      commonDetailDto.setBillCode(shelveInfo.getShelveCode());
      commonDetailDto.setSourceCode(shelveInfo.getOrderCode());
      commonDetailDto.setSourceCode2(shelveInfo.getTrackingNumber());
      commonDetailDto.setInQuantity(finishedQuantity);
      commonDetailDto.setOutQuantity(finishedQuantity);

      coreInventoryHistoryService.addHistory(HistoryTypeEnum.IN, productInfo, commonDetailDto, inventorySourceTypeEnum, beforeQuantity, beforeWeight, afterQuantity, afterWeight);
      //#endregion

      //#region 入库收货库存出库消减，snDisabled=0(开启SN管理)，snDisabled=1(关闭SN管理)
      if (B.isEqual(commonDetailDto.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        List<String> snList = StringUtils.splitList(singleSignCode);
        List<PositionTypeEnum> positionTypeEnumList = List.of(PositionTypeEnum.RECEIVING);
        List<String> invalidSnList = coreInventorySnService.getInvalidSnList(shelveInfo.getStorageId(), snList, positionTypeEnumList);
        Assert.isFalse(!invalidSnList.isEmpty(), "SN[" + StringUtils.join(invalidSnList, ",") + "]SN必须有效且在收货位中！");

        List<CoreInventorySn> validSnList = coreInventorySnService.getValidSnList(shelveInfo.getStorageId(), snList);

        //#region 暂存操作前的库存数量和重量
        HashMap<Long, Map<String, BigDecimal>> inventoryIdList = new HashMap<>(); // 暂存操作前的库存数量和重量
        HashSet<Long> inventoryIdSet = new HashSet<>(validSnList.stream().map(CoreInventorySn::getInventoryId).toList());
        for (Long inventoryId : inventoryIdSet) {
          // 获取入库前：库存数量和重量
          var inventoryInfo = coreInventoryService.getById(inventoryId);
          beforeQuantity = Optional.ofNullable(inventoryInfo).map(CoreInventory::getProductStorage).orElse(BigDecimal.ZERO); // 保存前数量
          beforeWeight = Optional.ofNullable(inventoryInfo).map(CoreInventory::getRowWeight).orElse(BigDecimal.ZERO);   // 保存前重量
          Map<String, BigDecimal> mapVal = new HashMap<>();
          mapVal.put("beforeQuantity", beforeQuantity);
          mapVal.put("beforeWeight", beforeWeight);
          inventoryIdList.put(inventoryId, mapVal);
        }
        //#endregion

        //#region sn出库消减
        for (String sn : snList) {
          var snInfo = validSnList.stream().filter(f -> Objects.equals(f.getSnNo(), sn)).findFirst().orElse(null);
          Assert.isFalse(ObjectUtil.isNull(snInfo), sn + "不存在！");

          // 将SN改为不可用
          UpdateJoinWrapper<CoreInventorySn> snLambdaUpdateWrapper = JoinWrappers.update(CoreInventorySn.class)
            .set(CoreInventorySn::getEnable, EnableEnum.DISABLE.getId())
            .innerJoin(CoreInventory.class, CoreInventory::getInventoryId, CoreInventorySn::getInventoryId)
            .eq(CoreInventorySn::getSnId, snInfo.getSnId());
          coreInventorySnService.updateJoin(null, snLambdaUpdateWrapper);

          // 重新计算库存数量和SN集合
          UpdateJoinWrapper<CoreInventory> inventoryLambdaUpdateWrapper = JoinWrappers.update(CoreInventory.class)
            .setSql("product_storage=(SELECT COUNT(1) FROM core_inventory_sn sn WHERE sn.inventory_id=t.inventory_id AND sn.enable=1)")
            .setSql("valid_storage=(SELECT COUNT(1) FROM core_inventory_sn sn WHERE sn.inventory_id=t.inventory_id AND sn.enable=1)")
            /*.setSql("single_sign_code=(SELECT GROUP_CONCAT(sn_no ORDER BY sn_id SEPARATOR_COMMA ',') FROM core_inventory_sn sn WHERE sn.inventory_id=t.inventory_id AND sn.enable=0)")*/
            .eq(CoreInventory::getInventoryId, snInfo.getInventoryId());
          coreInventoryService.updateJoin(null, inventoryLambdaUpdateWrapper);
        }
        //#endregion

        //#region sn入库处理
        List<CoreInventorySn> coreInventorySnList = snList.stream().map(sn -> {
          var snInfo = BeanUtil.copyProperties(coreInventory, CoreInventorySn.class);
          snInfo.setSnNo(sn);
          snInfo.setEnable(EnableEnum.ENABLE.getId());
          return snInfo;
        }).toList();
        coreInventorySnService.saveBatch(coreInventorySnList);
        //#endregion

        //#region 记录出库轨迹
        for (Long inventoryId : inventoryIdList.keySet()) {
          // 获取入库后：库存数量和重量
          var inventoryInfo = coreInventoryService.getById(inventoryId);
          if (ObjectUtil.isNull(inventoryInfo)) continue;

          afterQuantity = Optional.of(inventoryInfo).map(CoreInventory::getProductStorage).orElse(BigDecimal.ZERO); // 保存前数量
          afterWeight = Optional.of(inventoryInfo).map(CoreInventory::getRowWeight).orElse(BigDecimal.ZERO);   // 保存前重量

          beforeQuantity = inventoryIdList.get(inventoryId).get("beforeQuantity"); // 操作前库存数量
          beforeWeight = inventoryIdList.get(inventoryId).get("beforeWeight"); // 操作前库存重量

          // 生成出库轨迹数据
          BeanUtil.copyProperties(shelveInfo, commonDetailDto);
          BeanUtil.copyProperties(inShelveDetail, commonDetailDto);
          commonDetailDto.setPositionNameOut(inventoryInfo.getPositionName());
          commonDetailDto.setInventoryId(inventoryId);
          commonDetailDto.setMainId(inShelveDetail.getShelveId());
          commonDetailDto.setDetailId(inShelveDetail.getShelveDetailId());
          commonDetailDto.setBillCode(shelveInfo.getShelveCode());
          commonDetailDto.setSourceCode(shelveInfo.getOrderCode());
          commonDetailDto.setSourceCode2(shelveInfo.getTrackingNumber());
          coreInventoryHistoryService.addHistory(HistoryTypeEnum.OUT, productInfo, commonDetailDto, inventorySourceTypeEnum_out, beforeQuantity, beforeWeight, afterQuantity, afterWeight);
        }
        //#endregion
        //#endregion
      } else {
        //#region 常规出库
        LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        inventoryLambdaQueryWrapper
          .eq(CoreInventory::getMainId, inShelveDetail.getEnterId())
          .eq(CoreInventory::getDetailId, inShelveDetail.getEnterDetailId())
          .eq(CoreInventory::getProductId, inShelveDetail.getProductId())
          .gt(CoreInventory::getProductStorage, 0);
        List<CoreInventory> inventoryList = coreInventoryService.list(inventoryLambdaQueryWrapper);
        for (var inverntoryItem : inventoryList) {
          if (finishedQuantity.compareTo(BigDecimal.ZERO) <= 0) break;

          // 获取入库前：库存数量和重量
          var inventoryInfo = coreInventoryService.getById(inverntoryItem.getInventoryId());
          beforeQuantity = Optional.ofNullable(inventoryInfo).map(CoreInventory::getProductStorage).orElse(BigDecimal.ZERO); // 保存前数量
          beforeWeight = Optional.ofNullable(inventoryInfo).map(CoreInventory::getRowWeight).orElse(BigDecimal.ZERO);   // 保存前重量

          // 减少库存
          LambdaUpdateWrapper<CoreInventory> inventoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
          if (finishedQuantity.compareTo(inverntoryItem.getProductStorage()) >= 0) {
            inventoryLambdaUpdateWrapper.set(CoreInventory::getProductStorage, 0)
              .set(CoreInventory::getValidStorage, 0)
              .set(CoreInventory::getRowWeight, BigDecimal.ZERO)
              .set(CoreInventory::getRowCube, BigDecimal.ZERO)
              .set(CoreInventory::getPurchaseAmount, BigDecimal.ZERO)
              .set(CoreInventory::getRateAmount, BigDecimal.ZERO);
            finishedQuantity = finishedQuantity.subtract(inverntoryItem.getProductStorage());
          } else {
            inventoryLambdaUpdateWrapper.set(CoreInventory::getProductStorage, finishedQuantity)
              .set(CoreInventory::getValidStorage, finishedQuantity)
              .set(CoreInventory::getRowWeight, B.mul(finishedQuantity, inventoryInfo.getWeight()))
              .set(CoreInventory::getRowCube, B.mul(finishedQuantity, inventoryInfo.getUnitCube()))
              .set(CoreInventory::getPurchaseAmount, B.mul(finishedQuantity, inventoryInfo.getPurchasePrice()))
              .set(CoreInventory::getRateAmount, B.mul(finishedQuantity, inventoryInfo.getRatePrice()));
          }

          inventoryLambdaUpdateWrapper.eq(CoreInventory::getInventoryId, inverntoryItem.getInventoryId());
          coreInventoryService.update(inventoryLambdaUpdateWrapper);

          //#region 记录出库轨迹
          // 获取入库后：库存数量和重量
          inventoryInfo = coreInventoryService.getById(inverntoryItem.getInventoryId());
          afterQuantity = Optional.ofNullable(inventoryInfo).map(CoreInventory::getProductStorage).orElse(BigDecimal.ZERO); // 保存前数量
          afterWeight = Optional.ofNullable(inventoryInfo).map(CoreInventory::getRowWeight).orElse(BigDecimal.ZERO);   // 保存前重量

          // 生成出库轨迹数据
          BeanUtil.copyProperties(shelveInfo, commonDetailDto);
          BeanUtil.copyProperties(inShelveDetail, commonDetailDto);
          commonDetailDto.setPositionNameOut(inverntoryItem.getPositionName());
          commonDetailDto.setInventoryId(inverntoryItem.getInventoryId());
          commonDetailDto.setMainId(inShelveDetail.getShelveId());
          commonDetailDto.setDetailId(inShelveDetail.getShelveDetailId());
          commonDetailDto.setBillCode(shelveInfo.getShelveCode());
          commonDetailDto.setSourceCode(shelveInfo.getOrderCode());
          commonDetailDto.setSourceCode2(shelveInfo.getTrackingNumber());
          coreInventoryHistoryService.addHistory(HistoryTypeEnum.OUT, productInfo, commonDetailDto, inventorySourceTypeEnum_out, beforeQuantity, beforeWeight, afterQuantity, afterWeight);
          //#endregion
        }
        //#endregion
      }
      //#endregion
    }
    //#endregion

    /* *******************************************
     * 单据状态处理
     * *******************************************/
    //#region 上架单状态处理
    // 更新明细上架步骤中的状态
    LambdaUpdateWrapper<InShelveDetailStep> stepLambdaQueryWrapper = new LambdaUpdateWrapper<>();
    stepLambdaQueryWrapper
      .set(InShelveDetailStep::getShelveStatus, InShelveStatusEnum.FINISHED.getName())
      .eq(InShelveDetailStep::getShelveId, shelveInfo.getShelveId());
    inShelveDetailStepService.update(stepLambdaQueryWrapper);

    // 更新上架明细中的上架数量
    List<InShelveDetailStep> steplList = inShelveDetailStepService.selectListByMainId(shelveInfo.getShelveId());
    List<InShelveDetail> shelveDetailList = inShelveDetailService.selectListByMainId(shelveInfo.getShelveId());
    for (var detail : shelveDetailList) {
      BigDecimal totalShelvedQuantity = steplList.stream()
        .filter(f -> Objects.equals(f.getShelveDetailId(), detail.getShelveDetailId()))
        .map(InShelveDetailStep::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      detail.setShelvedQuantity(totalShelvedQuantity);
      detail.setOnShelveQuantity(detail.getQuantity().subtract(totalShelvedQuantity));
      if (B.isGreaterOrEqual(detail.getShelvedQuantity(), detail.getQuantity())) {
        detail.setShelveStatus(InShelveStatusEnum.FINISHED.getName());
      } else if (B.isGreater(detail.getShelvedQuantity()) && B.isGreater(detail.getOnShelveQuantity())) {
        detail.setShelveStatus(InShelveStatusEnum.PARTIAL_FINISHED.getName());
      } else {
        detail.setShelveStatus(InShelveStatusEnum.SHELVING.getName());
      }
      inShelveDetailService.getBaseMapper().updateById(detail);
    }

    /* -- 上架单状态计算 -- */
    BigDecimal totalShelvedQuantity = shelveDetailList.stream().map(InShelveDetail::getShelvedQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalQuantity = shelveDetailList.stream().map(InShelveDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalOnshelveQuantity = shelveDetailList.stream().map(InShelveDetail::getOnShelveQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    // 更新上架单主表合计
    shelveInfo.setTotalShelvedQuantity(totalShelvedQuantity);
    shelveInfo.setTotalQuantity(totalQuantity);
    shelveInfo.setTotalOnshelveQuantity(totalOnshelveQuantity);
    shelveInfo.setShelveType(inventorySourceTypeEnum.getName());
    if (B.isEqual(totalQuantity, totalShelvedQuantity)) {
      shelveInfo.setEndDate(DateUtils.getNowDate());
      long millis = DateUtil.between(shelveInfo.getStartDate(), shelveInfo.getEndDate(), DateUnit.MS); // 计算时间差
      shelveInfo.setSpanTime(DateUtil.formatBetween(millis, BetweenFormatter.Level.SECOND)); // 计算时间差格式化

    }
    inShelveService.getBaseMapper().updateById(shelveInfo);

    // 计算上架单的状态
    if (shelveDetailList.stream().allMatch(f -> B.isEqual(f.getOnShelveQuantity()))) {
      // 上架完成
      inShelveService.updateShelveStatus(shelveInfo.getShelveId(), InShelveStatusEnum.FINISHED);
    } else if (shelveDetailList.stream().anyMatch(f -> B.isGreater(f.getShelvedQuantity()))
      && shelveDetailList.stream().anyMatch(f -> B.isGreater(f.getOnShelveQuantity()))) {
      // 部分上架
      inShelveService.updateShelveStatus(shelveInfo.getShelveId(), InShelveStatusEnum.PARTIAL_FINISHED);
    } else {
      // 上架中
      inShelveService.updateShelveStatus(shelveInfo.getShelveId(), InShelveStatusEnum.SHELVING);
    }
    //#endregion

    //#region 入库单状态计算
    List<InEnterDetail> enterDetailList = inEnterDetailService.selectListByMainId(shelveInfo.getEnterId());
    for (var enterDetail : enterDetailList) {
      MPJLambdaWrapper<InShelveDetail> shelveDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
      shelveDetailMPJLambdaWrapper
        .selectSum(InShelveDetail::getShelvedQuantity)
        .eq(InShelveDetail::getEnterDetailId, enterDetail.getEnterDetailId())
        .eq(InShelveDetail::getEnterId, enterDetail.getEnterId());
      Map<String, Object> objectMap = inShelveDetailService.selectJoinMap(shelveDetailMPJLambdaWrapper);
      BigDecimal totalQty = Optional.ofNullable(objectMap).map(m -> Convert.toBigDecimal(m.get("shelvedQuantity"))).orElse(BigDecimal.ZERO);
      // 更新入库单明细上架数量
      enterDetail.setShelvedQuantity(totalQty);
      //入库数量跟上架数量相等 更新状态为 完全上架
      if (B.isEqual(enterDetail.getEnterQuantity(), enterDetail.getShelvedQuantity())) {
        enterDetail.setDetailStatus(InEnterStatusEnum.FINISHED.getName());

      } else if (B.isGreater(enterDetail.getEnterQuantity(), enterDetail.getShelvedQuantity()) && !B.isEqual(enterDetail.getShelvedQuantity())) {
        enterDetail.setDetailStatus(InEnterStatusEnum.PARTIAL_FINISHED.getName());

      }
      inEnterDetailService.getBaseMapper().updateById(enterDetail);
    }

    // 更新入库单主表上架数量
    totalShelvedQuantity = enterDetailList.stream().map(InEnterDetail::getShelvedQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    LambdaUpdateWrapper<InEnter> enterLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    enterLambdaUpdateWrapper.set(InEnter::getTotalShelvedQuantity, totalShelvedQuantity)
      .eq(InEnter::getEnterId, shelveInfo.getEnterId());
    inEnterService.update(enterLambdaUpdateWrapper);

    //查询入库信息
    InEnter enterInfo = inEnterService.getById(shelveInfo.getEnterId());
    //匹配枚举字段
    InEnterStatusEnum StatusEnumInfo = InEnterStatusEnum.matchingEnum(enterInfo.getEnterStatus());
    Assert.isFalse(ObjectUtil.isNull(StatusEnumInfo), "入库单状态不存在！");

    // 计算入库单的状态
    if (enterDetailList.stream().allMatch(f -> f.getShelvedQuantity().compareTo(f.getEnterQuantity()) >= 0)) {
      // 上架完成
      inEnterService.updateEnterStatus(enterInfo.getEnterId(), InEnterStatusEnum.FINISHED);
      //生成入库记录轨迹
      inEnterStatusHistoryService.addHistoryInfo(enterInfo, inEnterActionEnum, StatusEnumInfo, InEnterStatusEnum.FINISHED);

    } else if (B.isGreater(totalShelvedQuantity) && enterDetailList.stream().anyMatch(f -> f.getShelvedQuantity().compareTo(f.getEnterQuantity()) < 0)) {
      // 部分上架
      inEnterService.updateEnterStatus(enterInfo.getEnterId(), InEnterStatusEnum.PARTIAL_FINISHED);
      //生成入库记录轨迹
      inEnterStatusHistoryService.addHistoryInfo(enterInfo, inEnterActionEnum, StatusEnumInfo, InEnterStatusEnum.PARTIAL_FINISHED);
    }
    //#endregion

    //#region 预到货单状态计算
    List<InOrderDetail> orderDetailList = inOrderDetailService.selectListByMainId(shelveInfo.getOrderId());
    for (var orderDetail : orderDetailList) {
      MPJLambdaWrapper<InShelveDetail> shelveDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
      shelveDetailMPJLambdaWrapper
        .selectSum(InShelveDetail::getShelvedQuantity)
        .eq(InShelveDetail::getOrderDetailId, orderDetail.getOrderDetailId())
        .eq(InShelveDetail::getOrderId, orderDetail.getOrderId());
      Map<String, Object> objectMap = inShelveDetailService.selectJoinMap(shelveDetailMPJLambdaWrapper);
      BigDecimal totalQty = Optional.ofNullable(objectMap).map(m -> Convert.toBigDecimal(m.get("shelvedQuantity"))).orElse(BigDecimal.ZERO);


      // 更新入库单明细上架数量
      orderDetail.setShelvedQuantity(totalQty);
      inOrderDetailService.getBaseMapper().updateById(orderDetail);
    }
    // 预到货主表上架数量合计
    totalShelvedQuantity = orderDetailList.stream().map(InOrderDetail::getShelvedQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);

    InOrder inOrder = inOrderService.getById(shelveInfo.getOrderId());

    // 更新预到货单中的上架合计
    inOrder.setTotalShelvedQuantity(totalShelvedQuantity);

    inOrderService.updateById(inOrder);

    // 计算预到货单上架状态
    if (orderDetailList.stream().allMatch(f -> f.getShelvedQuantity().compareTo(f.getQuantity()) >= 0)) {
      // 上架完成
      inOrderService.updateShelverStatus(shelveInfo.getOrderId(), InShelveStatusEnum.FINISHED);

      InOrderStatusEnum fromShelveStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getShelveStatus());
      //添加轨迹
      inOrderStatusHistoryService.addHistoryInfo(inOrder, inOrderActionEnum, fromShelveStatusEnum, InOrderStatusEnum.HISTORY_FINISHED);

    } else if (B.isGreater(totalShelvedQuantity) && orderDetailList.stream().anyMatch(f -> f.getShelvedQuantity().compareTo(f.getQuantity()) < 0)) {
      // 部分上架
      inOrderService.updateShelverStatus(shelveInfo.getOrderId(), InShelveStatusEnum.PARTIAL_FINISHED);

      InOrderStatusEnum fromShelveStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getShelveStatus());
      //添加轨迹
      inOrderStatusHistoryService.addHistoryInfo(inOrder, inOrderActionEnum, fromShelveStatusEnum, InOrderStatusEnum.HISTORY_PARTIAL_FINISHED);
    }
    //#endregion

    //#region 将容器类型为LPN设置未使用
    for (var shelveDetail : shelveDetailList) {
      LambdaUpdateWrapper<BasePlate> plateLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      plateLambdaUpdateWrapper
        .set(BasePlate::getPlateState, PlateStatusEnum.NOT_USED.getName())
        .set(BasePlate::getIsUsing, PlateStatusEnum.NOT_USED.getId())
        .eq(BasePlate::getPlateCode, shelveDetail.getPlateCode());
      basePlateService.update(plateLambdaUpdateWrapper);
    }

    LambdaUpdateWrapper<BasePlate> plateLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    plateLambdaUpdateWrapper
      .set(BasePlate::getPlateState, PlateStatusEnum.NOT_USED.getName())
      .set(BasePlate::getIsUsing, PlateStatusEnum.NOT_USED.getId())
      .eq(BasePlate::getPlateCode, shelveInfo.getLpnCode());
    basePlateService.update(plateLambdaUpdateWrapper);
    //#endregion

    //调用RabbitMQ，上架成功后，自动分拣出库单
    RabbitReceiverDto receiverDto = new RabbitReceiverDto();
    receiverDto.setRabbitmqType(RabbitmqTypeEnum.SHELVE_FINISHED_TO_AUTO_SORTING); // 自动分拣出库单
    receiverDto.setBillId(shelveInfo.getShelveId());
    receiverDto.setBillCode(shelveInfo.getShelveCode());
    taskQueueService.createTask(receiverDto);

    boolean in_shelved_to_wcs = sysConfigService.getConfigBool("in_shelved_to_wcs"); // 入库直接上架后自动生成WCS任务
    if (in_shelved_to_wcs) {
      //调用RabbitMQ，生成WCS接口数据
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_WCS); // 入库上架后生成WCS接口数据
      rabbitReceiverDto.setBillId(shelveInfo.getEnterId());
      rabbitReceiverDto.setBillCode(shelveInfo.getEnterCode());
      rabbitReceiverDto.setSourceId(inOrder.getOrderId().toString());
      rabbitReceiverDto.setSourceCode(inOrder.getOrderCode());
      taskQueueService.createTask(rabbitReceiverDto);
    }
    //#endregion

    return R.ok();
  }
  //endregion

  //#region searchShelvePositionList

  /**
   * 获取单号筛选上架货位
   *
   * @param map 单号
   * @return BasePosition 对象结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<BasePositionVo> searchShelvePositionList(Map<String, Object> map) {
    if (Objects.equals(map.get("shelveCode"), "") || ObjectUtil.isNull(map.get("shelveCode"))) {
      throw new ServiceException("单号为空，请先保存单据后操作!");
    }
    // 截取单号的前两位
    String orderCode = Convert.toStr(map.get("shelveCode")).substring(0, 2);
    Long StorageId = null;

    // 判断是否是出库单
    if (orderCode.equals("SO")) {
      LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(OutOrder::getOrderCode, Convert.toStr(map.get("shelveCode")));
      outOrderService.getOne(orderLambdaQueryWrapper);

      OutOrder outOrder = outOrderService.getOne(orderLambdaQueryWrapper);
      StorageId = outOrder.getStorageId();
    } else if (orderCode.equals("OE")) {
      LambdaQueryWrapper<StorageOuter> outerLambdaQueryWrapper = new LambdaQueryWrapper<>();
      outerLambdaQueryWrapper.eq(StorageOuter::getOuterCode, Convert.toStr(map.get("shelveCode")));
      storageOuterService.getOne(outerLambdaQueryWrapper);

      StorageOuter storageOuter = storageOuterService.getOne(outerLambdaQueryWrapper);
      StorageId = storageOuter.getStorageId();
    } else if (orderCode.equals("AA")) {
      LambdaQueryWrapper<StorageAllocateApply> storageAllocateApplyLambdaQueryWrapper = new LambdaQueryWrapper<>();
      storageAllocateApplyLambdaQueryWrapper.eq(StorageAllocateApply::getAllocateApplyCode, Convert.toStr(map.get("shelveCode")));
      storageAllocateApplyService.getOne(storageAllocateApplyLambdaQueryWrapper);

      StorageAllocateApply storageAllocateApply = storageAllocateApplyService.getOne(storageAllocateApplyLambdaQueryWrapper);
      StorageId = storageAllocateApply.getStorageId();
    }
    //    if(orderCode.equals("PS") || orderCode.equals("SJ")){
    //      LambdaQueryWrapper<InShelve> inShelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    //      inShelveLambdaQueryWrapper.eq(InShelve::getOrderCode, Convert.toStr(map.get("shelveCode")));
    //      inShelveService.getOne(inShelveLambdaQueryWrapper);
    //
    //      InShelve outOrder = inShelveService.getOne(inShelveLambdaQueryWrapper);
    //      StorageId = outOrder.getStorageId();
    //    }
    // 查询仓库
    LambdaQueryWrapper<BaseStorage> baseStorageLambdaQueryWrapper = new LambdaQueryWrapper<>();
    baseStorageLambdaQueryWrapper.eq(BaseStorage::getStorageId, StorageId);
    BaseStorage storageInfo = baseStorageService.getOne(baseStorageLambdaQueryWrapper);

    // 查询货位
    LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    positionLambdaQueryWrapper.eq(BasePosition::getStorageId, StorageId)
      .eq(BasePosition::getPositionType, storageInfo.getPositionType())
      .like(BasePosition::getPositionName, Convert.toStr(map.get("key")));
    List<BasePositionVo> position = basePositionService.selectList(positionLambdaQueryWrapper);

    return position;
  }
  //#endregion

  //#region 无单扫描入库 - 获取数据

  /**
   * 无单扫描入库 - 获取数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @Override
  public R<List<Map<String, Object>>> getShelveNoBillData(Map<String, Object> map) {
    String storageId = Convert.toStr(map.get("storageId"));
    String positionName = Convert.toStr(map.get("positionName"));
    String productModel = Convert.toStr(map.get("productModel"));
    List<String> status = new ArrayList<>();
    status.add(InShelveStatusEnum.SHELVING.getName());
    status.add(InShelveStatusEnum.PARTIAL_FINISHED.getName());
    status.add(InShelveStatusEnum.WAITING.getName());

    MPJLambdaWrapper<InShelveDetail> shelveDetailMPJ = new MPJLambdaWrapper<InShelveDetail>()
      .select(InShelve::getShelveId, InShelve::getShelveCode, InShelve::getStartDate)
      .selectAll(InShelveDetail.class)
      .eq(InShelve::getStorageId, storageId)
      .eq(InShelveDetail::getPositionName, positionName)
      .eq(InShelveDetail::getProductModel, productModel)
      .in(InShelveDetail::getShelveStatus, status)
      .apply("t.on_shelve_quantity>t.shelved_quantity")
      .innerJoin(InShelve.class, InShelve::getShelveId, InShelveDetail::getShelveId)
      .orderByDesc(InShelve::getCreateTime)
      .last("limit 1");

    List<Map<String, Object>> mapList = inShelveDetailService.selectJoinMaps(shelveDetailMPJ);

    Assert.isTrue(mapList.size() > 0, "未找到可上架明细数据！");

    Long shelveId = Convert.toLong(mapList.get(0).get("shelveId"));
    Date startDate = Convert.toDate(mapList.get(0).get("startDate"));


    //更新这个上架单为当前用户所有
    LambdaUpdateWrapper<InShelve> shelveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    shelveLambdaUpdateWrapper.set(InShelve::getUserId, LoginHelper.getUserId())
      .set(InShelve::getNickName, LoginHelper.getNickname())
      .set(InShelve::getShelveStatus, InShelveStatusEnum.SHELVING.getName())
      .eq(InShelve::getShelveId, shelveId);
    if (ObjectUtil.isEmpty(startDate)) {
      shelveLambdaUpdateWrapper.set(InShelve::getStartDate, DateUtils.getNowDate());
    }
    inShelveService.update(shelveLambdaUpdateWrapper);
    return R.ok(mapList);
  }
  //#endregion
}
