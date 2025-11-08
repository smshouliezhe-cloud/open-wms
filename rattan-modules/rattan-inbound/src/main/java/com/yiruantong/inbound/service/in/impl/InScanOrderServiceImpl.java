package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.constant.HttpStatus;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.in.QualityCheckTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.helper.RecommendPositionDto;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import com.yiruantong.system.service.core.ISysConfigService;
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

@RequiredArgsConstructor
@Service
public class InScanOrderServiceImpl implements IInScanOrderService {
  //#region service实例化
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final ISysConfigService sysConfigService;
  private final IInArrivalProcessService inArrivalProcessService;
  private final IBaseStorageService baseStorageService;
  private final IInEnterDetailService inEnterDetailService;
  private final IBasePositionService basePositionService;
  private final IBaseProductService baseProductService;
  private final IInOrderPlanDetailService inOrderPlanDetailService;
  private final IInQualityCheckService inQualityCheckService;
  private final IInQualityCheckDetailService inQualityCheckDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IRecommendPositionService recommendPositionService;
  @Resource
  private final FlowExecutor flowExecutor;
  //#endregion

  //region 获取入库单扫描数据

  /**
   * 获取入库单扫描数据
   *
   * @param map 前端参数
   */
  @Override
  public R<Map<String, Object>> getInOrderData(Map<String, Object> map) {
    String orderCode = map.get("orderCode").toString();
    Boolean isOnShelve = Convert.toBool(map.get("isOnShelve")); // 是否直接上架

    // 系统参数设置中设置了收货后上架前进行质检时，操作上架的时候需要增加判断：当前上架单对应的入库单所对应的质检单是否质检完成
    String in_inboundQualityPosition = sysConfigService.selectConfigByKey("in_inboundQualityPosition");

    //#region 校验数据
    if (StringUtils.isEmpty(orderCode)) {
      throw new ServiceException("预到货单号都不能为空！");
    }
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

    var orderInfo = inOrderService.getOne(wrapper);
    if (ObjectUtil.isEmpty(orderInfo)) {
      throw new ServiceException("预到货单数据不存在！");
    }
//    Assert.isFalse(B.isEqual(orderInfo.getCrossDocking(), NumberUtils.ONE), "越库预到货单不允许操作！");

    String qualityCheckTypeEnum = String.valueOf(QualityCheckTypeEnum.INBOUND_BEFORE);
    //#region 收货后上架前质检验
    if (ObjectUtil.equals(orderInfo.getIsChecking(), EnableEnum.ENABLE.getId())) {
      if (in_inboundQualityPosition.equals(qualityCheckTypeEnum)) {
        LambdaQueryWrapper<InQualityCheck> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InQualityCheck::getOrderCode, orderCode);
        var inQualityCheck = inQualityCheckService.selectOne(queryWrapper);

        if (ObjectUtil.isEmpty(inQualityCheck) || !Objects.equals(inQualityCheck.getAuditing(), AuditEnum.AUDITED_SUCCESS.getId())) {
          throw new ServiceException("需要质检！");
        }
      }
    }

    if (ObjectUtil.equals(orderInfo.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
      throw new ServiceException("该订单已经完成交货！");
    }
    // 审核成功允许扫描入库
    var in_auditEnableScan = sysConfigService.getConfigBool("in_auditEnableScan");
    List<InOrderStatusEnum> statusList = new ArrayList<>(Arrays.asList(InOrderStatusEnum.IN_TRANSIT, InOrderStatusEnum.PARTIAL_FINISHED));
    if (in_auditEnableScan) {
      statusList.add(InOrderStatusEnum.SUCCESS);
    }
    if (statusList.stream().noneMatch(m -> m.getName().equals(orderInfo.getOrderStatus()))) {
      String statusStr = statusList.stream().map(InOrderStatusEnum::getName).collect(Collectors.joining(","));
      throw new ServiceException("采购订单的订单状态不是" + statusStr + "，不能进行入库！");
    }
    // 验证到货加工
    if (Objects.equals(Optional.of(orderInfo).map(InOrder::getIsArrivalProcess).orElse(EnableEnum.DISABLE.getId()), EnableEnum.ENABLE.getId())) {
      LambdaQueryWrapper<InArrivalProcess> arrivalWrapper = new LambdaQueryWrapper<>();
      arrivalWrapper.eq(InArrivalProcess::getOrderCode, orderCode);
      InArrivalProcess inArrivalProcess = inArrivalProcessService.getOne(arrivalWrapper);
      if (ObjectUtil.isEmpty(inArrivalProcess)) {
        throw new ServiceException("预到货单【" + orderCode + "】未进行到货加工，不能入库！");
      }
    }
    // 收货前质检验证
    var checkResult = this.qualityCheck(orderInfo, QualityCheckTypeEnum.INBOUND_BEFORE);
    if (!checkResult.isResult()) {
      throw new ServiceException("采购订单【" + orderCode + "】未进行质检操作，不能入库！");
    }
    //#endregion

    //#region 获取需要扫描的数据
    MPJLambdaWrapper<InOrderDetail> detailWrapper = new MPJLambdaWrapper<>();
    detailWrapper
      .select(InOrderDetail::getOrderId, InOrderDetail::getBigUnit, InOrderDetail::getBigQty, InOrderDetail::getOrderDetailId,
        InOrderDetail::getProductId, InOrderDetail::getProductCode, InOrderDetail::getProductName, InOrderDetail::getProductModel,
        InOrderDetail::getProductSpec, InOrderDetail::getPurchasePrice, InOrderDetail::getSingleSignCode,
        InOrderDetail::getQuantity, InOrderDetail::getEnterQuantity, InOrderDetail::getPlateCode, InOrderDetail::getRemark,
        InOrderDetail::getProduceDate, InOrderDetail::getLimitDate, InOrderDetail::getBatchNumber, InOrderDetail::getRowWeight,
        InOrderDetail::getRowNetWeight, InOrderDetail::getOvercharges, InOrderDetail::getProviderId, InOrderDetail::getCaseNumber, InOrderDetail::getTypeName, InOrderDetail::getRowCube, InOrderDetail::getExpandFields)
      .select(InOrder::getStorageId, InOrder::getConsignorId, InOrder::getTrackingNumber, InOrder::getOrderCode, InOrder::getContainerNos)
      .select(BaseProduct::getImages, BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3,
        BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getMiddleBarcode, BaseProduct::getMiddleUnitConvert,
        BaseProduct::getBigBarcode, BaseProduct::getUnitConvert, BaseProduct::getSmallUnit, BaseProduct::getWeight, BaseProduct::getWidth,
        BaseProduct::getLength, BaseProduct::getHeight, BaseProduct::getUnitCube, BaseProduct::getShelfLifeDay, BaseProduct::getBrandName,
        BaseProduct::getIsManageSn, BaseProduct::getExpandFields)
      .innerJoin(InOrder.class, InOrder::getOrderId, InOrderDetail::getOrderId)
      .leftJoin(BaseProduct.class, BaseProduct::getProductId, InOrderDetail::getProductId)
      .eq(InOrder::getOrderId, orderInfo.getOrderId())
      .ge(InOrderDetail::getQuantity, 0)
      .apply("t.quantity > IFNULL(t.enter_quantity, 0)")
      .orderByAsc(InOrderDetail::getOrderDetailId);
    List<Map<String, Object>> orderList = inOrderDetailService.selectJoinMaps(detailWrapper);

    if (ArrayUtil.isEmpty(orderList)) {
      throw new ServiceException("未找到对应的订单明细数据");
    }
    // 仓库信息
    var storageInfo = baseStorageService.selectById(orderInfo.getStorageId());
    Assert.isFalse(ObjectUtil.isNull(storageInfo), "预到货单仓库不存在！");
    // 推荐货位规则
    for (var item : orderList) {
      //直接上架时匹配货位推荐规则
      if (isOnShelve) {
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
        item.put("positionName", positionName);
      }
      // 开启SN管理状态处理
      var isManageSn = Convert.toInt(item.get("isManageSn"));
      var snDisabled = storageInfo.getSnDisabled(); // 仓库设置了不需SN管理
      if (snDisabled == 1) {
        isManageSn = 0; // 不需要SN
      }
      item.put("isManageSn", isManageSn);

      // 如果类型是合金/辅料 需要吧 生产日期 默认为当前时间
      if (B.isEqual(orderInfo.getOrderType(), InOrderTypeEnum.AUXILIARY_MATERIAL.getName())) {
        item.put("produceDate", new Date());
      }
      //取出来 商品的扩展字段值
      if (ObjectUtil.isNotEmpty(item.get("expandFields"))) {
        Map<String, Object> jsonData = JsonUtils.toMap(Convert.toStr(item.get("expandFields")));

        item.put("bigTareWeight", jsonData.get("bigTareWeight"));
        item.put("smallTareWeight", jsonData.get("smallTareWeight"));
        item.put("expandFields", jsonData);
      }
    }
    //#endregion

    //#region 获得上架货位推荐

//    if (isOnShelve) {
//      for (let item of orderList){
//        const connection:
//        any = await this.dbWrite.connection;
//        let request = new mssql.Request(connection.driver.master);
//        request.input("storage_Id", item.storage_Id);
//        request.input("consignor_Id", item.consignor_Id);
//        request.input("product_Id", item.product_Id);
//        request.input("inQty", item.quantity);
//        request.input("productSpec", item.productSpec);
//        request.input("provider_Id", item.provider_Id);
//        request.input("batchNumber", item.batchNumber);
//        request.output("positionName", mssql.NVarChar(2000));
//        let result = await request.execute("sp_Purchase_ShelveRecommendPosition_Get");
//        let positionName = result.output.positionName;
//        item.positionName = positionName;
//      }
//    }
    //#endregion
    //保存作业开始时间
//      this.ctx.helper.setExpandFields(orderInfo, "startHours", moment(new Date()).format("YYYY-MM-DD HH:mm:ss"));
//      await this.dbWrite.save(orderInfo);

    // 返回数据
    Map<String, Object> resultData = new HashMap<>();
    resultData.put("orderList", orderList);
    resultData.put("orderId", orderInfo.getOrderId());
    resultData.put("storageId", orderInfo.getStorageId());
    resultData.put("storageName", orderInfo.getStorageName());
    resultData.put("sourceCode", orderInfo.getSourceCode());
    resultData.put("consignorId", orderInfo.getConsignorId());
    resultData.put("consignorName", orderInfo.getConsignorName());

    //#region 获取新箱号
    var In_CaseNumber = sysConfigService.getConfigBool("In_CaseNumber");
    if (In_CaseNumber) {
      //获得最大箱号值
      LambdaQueryWrapper<InEnterDetail> enterLambdaQueryWrapper = new LambdaQueryWrapper<>();
      enterLambdaQueryWrapper.select(InEnterDetail::getCaseNumber)
        .eq(InEnterDetail::getOrderId, orderInfo.getOrderId())
        .orderByDesc(InEnterDetail::getOrderDetailId)
        .last("limit 1");
      InEnterDetail enterDetail = inEnterDetailService.getOne(enterLambdaQueryWrapper);

      String newCaseNumber;
      if (ObjectUtil.isEmpty(enterDetail)) {
        newCaseNumber = orderInfo.getOrderCode() + "-01";
      } else {
        int maxNum = 0;
        String caseNumber = enterDetail.getCaseNumber();
        String[] caseNumbers = StringUtils.split(caseNumber, "-");
        if (Objects.equals(caseNumbers[0], orderInfo.getOrderCode())) {
          int num = caseNumbers.length > 1 ? Convert.toInt(caseNumbers[caseNumbers.length - 1]) : 1;
          if (maxNum < num) {
            maxNum = num;
          }
        }

        if (maxNum < 100) {
          String num1 = "0000" + ++maxNum;
          newCaseNumber = orderInfo.getOrderCode() + "-" + num1.substring(num1.length() - 2);
        } else {
          newCaseNumber = orderInfo.getOrderCode() + "-" + ++maxNum;
        }
      }
      resultData.put("newCaseNumber", newCaseNumber);
    }
    //#endregion

    return R.ok(resultData);

  }
  //endregion

  //#region 入库前校验：是否存在符合禁收日期

  /**
   * 入库前校验：是否存在符合禁收日期
   *
   * @param inScanOrderBo 前端参数
   */
  @Override
  public R<Void> saveCheck(@NotNull InScanOrderBo inScanOrderBo) {

    Long orderId = inScanOrderBo.getOrderId();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList();

    // 预到货单信息
    var orderInfo = inOrderService.getById(orderId);
    if (ObjectUtil.isEmpty(orderInfo)) {
      throw new ServiceException("采购单号不存在！");
    }
    List<String> statusList = Arrays.asList("新建", "审核成功", "在途中", "部分收货", "部分交货");
    if (!statusList.contains(orderInfo.getOrderStatus())) {
      throw new ServiceException("采购单号【" + orderInfo.getOrderCode() + "】只有状态为：" + StringUtils.join(statusList, ",") + "，才允许上架操作！");
    }

    boolean in_noReceivingDate = sysConfigService.getConfigBool("in_noReceivingDate");  // 入库时启用禁收日期
    boolean in_noEarlyWarning = sysConfigService.getConfigBool("in_noEarlyWarning");  // 入库是启用生产日期预警
    boolean in_WeightVerification = sysConfigService.getConfigBool("in_WeightVerification");  // 如果预到货单内的重量比入库计划单高10%需要预警
    // PDA收货任务商品信息校验   校验保质期与大单位换算率
    boolean receiving_task_verification = sysConfigService.getConfigBool("Receiving_task_verification");

    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      String positionName = inScanOrderDetailBo.getPositionName();
      Long productId = inScanOrderDetailBo.getProductId();
      String productCode = inScanOrderDetailBo.getProductCode();
      BigDecimal shelfLifeDay = inScanOrderDetailBo.getShelfLifeDay(); // 保质期天数
      BigDecimal unitConvert = inScanOrderDetailBo.getUnitConvert(); // unitConvert
      Date produceDate = inScanOrderDetailBo.getProduceDate(); // 生产日期
      BigDecimal enterQuantity = inScanOrderDetailBo.getEnterQuantity();

      LambdaQueryWrapper<BasePosition> positionQueryWrapper = new LambdaQueryWrapper<>();
      positionQueryWrapper.eq(BasePosition::getStorageId, orderInfo.getStorageId()) // 查询仓库
        .in(BasePosition::getEnable, EnableEnum.ENABLE.getId()) // 查询查询货位
        .eq(BasePosition::getPositionName, positionName);
      // 验证货位是否存在
      if (!basePositionService.getBaseMapper().exists(positionQueryWrapper)) {
        throw new ServiceException("上架货位：" + positionName + "不存在！");
      }
      if (enterQuantity.compareTo(BigDecimal.ZERO) <= 0) {
        throw new ServiceException(productCode + "没有可入库的数量");
      }
      // 按单码盘时校验托盘不能为空
      if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PC_ORDER_STACKING_IN && ObjectUtil.isEmpty(inScanOrderDetailBo.getPlateCode())) {
        throw new ServiceException(productCode + "托盘号不能为空");
      }

      LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
      productLambdaQueryWrapper.eq(BaseProduct::getProductId, productId);
      var productInfo = baseProductService.getById(productId);

      // PDA收货任务商品信息校验   校验保质期与大单位换算率
      if (receiving_task_verification) {
        // 保质期或者大单位转换率在基础信息不匹配
        if (productInfo.getShelfLifeDay().compareTo(shelfLifeDay) != 0) {
          throw new ServiceException("商品编号【" + productCode + "】保质期在基础信息不匹配");
        }
        if (productInfo.getUnitConvert().compareTo(unitConvert) != 0) {
          throw new ServiceException("商品编号【" + productCode + "】大单位转换率在基础信息不匹配");
        }
      }

      var noReceivingRate = productInfo.getNoReceivingRate(); // 禁售天数
      if (ObjectUtil.isNotEmpty(noReceivingRate) && in_noReceivingDate && ObjectUtil.isNotEmpty(produceDate)) {
        BigDecimal spanDays = Convert.toBigDecimal(DateUtil.between(DateUtil.date(), produceDate, DateUnit.DAY, false));
        BigDecimal validDays = B.sub(shelfLifeDay, spanDays); // 计算有效天数
        if (B.isLess(validDays, noReceivingRate)) {
          String outMsg = "商品编号为【" + productCode + "】保质期天数为" + shelfLifeDay + "，有效天数" + validDays
            + "，禁收天数" + noReceivingRate + "，有效天数已低于禁收天数";
          return R.fail(HttpStatus.WARN, outMsg);
        }
      }

      // 只有设置了日期预警才做校验
      if (in_noEarlyWarning && ObjectUtil.isNotEmpty(produceDate)) {
        LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        inventoryLambdaQueryWrapper.eq(CoreInventory::getProductId, productInfo.getProductId())
          .gt(CoreInventory::getProductStorage, 0)
          .orderByDesc(CoreInventory::getProduceDate)
          .last("limit 1");
        CoreInventory inventoryServiceOne = coreInventoryService.getOne(inventoryLambdaQueryWrapper);

        // 对象判空同时判断属性是否存在值
        if (Optional.ofNullable(inventoryServiceOne).map(CoreInventory::getProduceDate).isPresent()) {
          Date storageProduceDate = inventoryServiceOne.getProduceDate();
          if (storageProduceDate.compareTo(produceDate) > 0) {
            String outMsg = "当前商品【" + productCode + "】生产日期为" + DateUtil.formatDate(produceDate) + "小于库存内生产日期为"
              + DateUtil.formatDate(storageProduceDate) + "，是否强制入库";
            return R.fail(HttpStatus.WARN, outMsg);
          }

        }
      }

      // 只有设置了重量核验才比较
      if (in_WeightVerification) {
        LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.eq(InOrderDetail::getOrderId, orderInfo.getOrderId())
          .eq(InOrderDetail::getProductCode, productCode);
        InOrderDetail inOrderDetail = inOrderDetailService.getOne(detailLambdaQueryWrapper);

        BigDecimal detailRowWeight = inOrderDetail.getRowWeight(); // 预到货明细重量

        // 获取计划单中的明细重量
        LambdaQueryWrapper<InOrderPlanDetail> planDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        planDetailLambdaQueryWrapper.eq(InOrderPlanDetail::getPlanDetailId, inOrderDetail.getSourceDetailId())
          .eq(InOrderPlanDetail::getProductCode, productCode);
        InOrderPlanDetail inOrderPlanDetail = inOrderPlanDetailService.getOne(planDetailLambdaQueryWrapper);
        // 判空给默认值，Optional使用很方便，判断预到货单明细重量大于计划单中的明细重量1.1倍时提示错误
        if (Optional.ofNullable(inOrderPlanDetail)
          .map(m -> detailRowWeight.compareTo(B.mul(m.getRowWeight(), BigDecimal.valueOf(1.1))) > 0)
          .orElse(false)) {
          throw new ServiceException("商品编号为【" + productCode + "】已超收10%");
        }
      }
    }

    return R.ok();

  }
  //#endregion

  //#region 收货前 ,收货后上架前 质检

  /**
   * 收货质检
   *
   * @param qualityCheckType 质检位置
   */
  @Override
  public R<Void> qualityCheck(InOrder orderInfo, QualityCheckTypeEnum qualityCheckType) {

    // 收货质检位置
    String in_inboundQualityPositionStr = sysConfigService.selectConfigByKey("in_inboundQualityPosition");
    if (StringUtils.isEmpty(in_inboundQualityPositionStr)) return R.ok();

    QualityCheckTypeEnum in_inboundQualityPosition = QualityCheckTypeEnum.valueOf(in_inboundQualityPositionStr);
    String productCodes = "";
    if (B.isEqual(orderInfo.getIsChecking(), EnableEnum.ENABLE.getId()) && in_inboundQualityPosition == qualityCheckType) {
      LambdaQueryWrapper<InQualityCheck> qualityCheckLambdaQueryWrapper = new LambdaQueryWrapper<>();
      qualityCheckLambdaQueryWrapper.eq(InQualityCheck::getOrderCode, orderInfo.getOrderCode());
      var processList = inQualityCheckService.list(qualityCheckLambdaQueryWrapper);
      Assert.isTrue(B.isGreater(BigDecimal.valueOf(processList.size())), "采购订单【" + orderInfo.getOrderCode() + "】未进行质检操作，不能入库！");
      for (var process : processList) {
        if (NumberUtil.equals(process.getAuditing(), AuditEnum.AUDIT.getId())) {
          throw new ServiceException(process.getQualityCheckCode() + "质检单未进行审核操作！");
        } else if (NumberUtil.equals(process.getAuditing(), AuditEnum.AUDIT_FAILED.getId())) {
          throw new ServiceException(process.getQualityCheckCode() + "质检单审核失败，无法操作！");
        } else if (!NumberUtil.equals(process.getAuditing(), AuditEnum.AUDITED_SUCCESS.getId())) {
          throw new ServiceException(process.getQualityCheckCode() + "质检单需要进行审核成功才能扫描入库");
        }
      }

      var orderDetails = inOrderDetailService.selectListByMainId(orderInfo.getOrderId());

      for (var item : orderDetails) {
        var skuInfo = baseProductService.getById(item.getProductId());
        this.getQualityPlan(skuInfo); //获取质检方案

        if ("抽检".equals(skuInfo.getQualityPlan()) || "全部质检".equals(skuInfo.getQualityPlan())) {
          LambdaQueryWrapper<InQualityCheckDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
          detailLambdaQueryWrapper.eq(InQualityCheckDetail::getOrderDetailId, item.getOrderDetailId());
          var checkDetailInfo = inQualityCheckDetailService.getOne(detailLambdaQueryWrapper);

          BigDecimal qualityProportion = Optional.of(skuInfo).map(BaseProduct::getQualityProportion).orElse(BigDecimal.ZERO);
          if ("全部质检".equals(skuInfo.getQualityPlan())) {
            qualityProportion = new BigDecimal(100);
          }

          // 没有做质检，质检标示为质检不合格商品
          if (ObjectUtil.isEmpty(checkDetailInfo)) {
            if (StringUtils.isNotEmpty(productCodes)) productCodes += ", ";
            productCodes += skuInfo.getProductCode() + "未做质检验收";
          } else if (qualityProportion.compareTo(checkDetailInfo.getQualifiedRate()) > 0) {
            // 质检不达标
            if (StringUtils.isNotEmpty(productCodes)) productCodes += ", ";
            productCodes += "商品编号" + skuInfo.getProductCode() + "的合格率为" + qualityProportion + "小于商品信息中设置的合格率" + checkDetailInfo.getQualifiedRate();
          }
        }
      }
      //收货前
      if (StringUtils.isNotEmpty(productCodes) && in_inboundQualityPosition == QualityCheckTypeEnum.INBOUND_BEFORE) {
        throw new ServiceException(productCodes + "，不能入库");
      }
      //收货后上架前
      if (StringUtils.isNotEmpty(productCodes) && in_inboundQualityPosition == QualityCheckTypeEnum.INBOUND_AFTER) {
        throw new ServiceException(productCodes + "，不能上架");
      }
    }

    return R.ok();

  }
  //#endregion

  //#region 装箱收货入库 获取数据

  /**
   * 常规扫描入库 - 装箱收货入库
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @Override
  public R<List<Map<String, Object>>> getOrderByProductModel(Map<String, Object> map) {

    String productModel = Convert.toStr(map.get("productModel"));
    String orderCode = Convert.toStr(map.get("orderCode"));

    // 审核成功允许扫描入库
    var in_auditEnableScan = sysConfigService.getConfigBool("in_auditEnableScan");
    List<String> statusList = new ArrayList<>(Arrays.asList(InOrderStatusEnum.IN_TRANSIT.getName(), InOrderStatusEnum.PARTIAL_FINISHED.getName()));
    if (in_auditEnableScan) {
      statusList.add(InOrderStatusEnum.SUCCESS.getName());
    }

    MPJLambdaWrapper<InOrderDetail> wrapper = new MPJLambdaWrapper<InOrderDetail>()
      .select(InOrder::getOrderCode, InOrder::getStorageId, InOrder::getStorageName, InOrder::getConsignorName, InOrder::getConsignorName,
        InOrder::getConsignorCode, InOrder::getConsignorId, InOrder::getProviderShortName, InOrder::getProviderId, InOrder::getProviderCode, InOrder::getOrderStatus, InOrder::getSourceCode)
      .select(BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getUnitConvert)
      .selectAll(InOrderDetail.class)
      .innerJoin(InOrder.class, InOrder::getOrderId, InOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, InOrderDetail::getProductId)
      .eq(StringUtils.isNotEmpty(orderCode), InOrder::getOrderCode, orderCode)
      .like(InOrderDetail::getProductModel, productModel)
      .in(InOrder::getOrderStatus, statusList);

    List<Map<String, Object>> orderList = inOrderDetailService.selectJoinMaps(wrapper);
    Assert.isFalse(B.isEqual(orderList.size()), "未找到该条码的单据");

    return R.ok(orderList);

  }

  /**
   * oneKeyIn
   *
   * @param orderId                 预到货单ID
   * @param inventorySourceTypeEnum 库存来源类型枚举
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> oneKeyIn(Long orderId, InventorySourceTypeEnum inventorySourceTypeEnum) {
    InOrder inOrder = inOrderService.getById(orderId);
    List<InOrderDetail> orderDetailList = inOrderDetailService.selectListByMainId(inOrder.getOrderId());
    final List<InScanOrderDetailBo> inScanOrderDetailBos = new ArrayList<>();

    // 获取默认货位--常规货位
    LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    positionLambdaQueryWrapper.eq(BasePosition::getStorageId, inOrder.getStorageId())
      .eq(BasePosition::getPositionType, PositionTypeEnum.NORMAL.getId())
      .last("limit 1");
    var positionInfo = basePositionService.getOne(positionLambdaQueryWrapper);

    for (var items : orderDetailList) {
      InScanOrderDetailBo orderDetail1 = new InScanOrderDetailBo();
      BeanUtil.copyProperties(items, orderDetail1);
      orderDetail1.setFinishedQuantity(items.getQuantity());
      // 如果没有货位，则使用默认货位
      if (ObjectUtil.isNull(items.getPositionName())) {
        orderDetail1.setPositionName(positionInfo.getPositionName());
      } else {
        orderDetail1.setPositionName(items.getPositionName());
      }
      inScanOrderDetailBos.add(orderDetail1);
    }

    InScanOrderBo inScanOrderBo = new InScanOrderBo();
    inScanOrderBo.setOrderCode(inOrder.getOrderCode());
    inScanOrderBo.setOnShelve(true);
    inScanOrderBo.setOrderId(inOrder.getOrderId());
    inScanOrderBo.setOrderCode(inOrder.getOrderCode());
    inScanOrderBo.setDataList(inScanOrderDetailBos);
    inScanOrderBo.setScanInType(inventorySourceTypeEnum);
    return this.normalScanSave(inScanOrderBo);
  }
  //#endregion

  //#region getQualityPlan 获取质检方案

  /**
   * 获取质检方案
   */
  private Map<String, Object> getQualityPlan(BaseProduct skuInfo) {

    String sql = """
      SELECT
        ( CASE WHEN P.quality_Plan IS NOT NULL	-- SKU自身设置
        THEN P.quality_Plan
        WHEN EXISTS(SELECT quality_Plan FROM base_brand WHERE Brand_Id=P.Brand_Id AND quality_Plan IS NOT NULL) -- 品牌
       THEN (SELECT quality_Plan FROM base_brand WHERE Brand_Id=P.Brand_Id AND quality_Plan IS NOT NULL)
       WHEN EXISTS(SELECT quality_Plan FROM base_product_type WHERE Type_Id=P.Type_Id AND quality_Plan IS NOT NULL) -- 类别
       THEN (SELECT quality_Plan FROM base_product_type WHERE Type_Id=P.Type_Id AND quality_Plan IS NOT NULL)
       WHEN EXISTS(SELECT quality_Plan FROM base_provider WHERE provider_Id={1} AND quality_Plan IS NOT NULL) -- 货主
       THEN (SELECT quality_Plan FROM base_provider WHERE provider_Id={1} AND quality_Plan IS NOT NULL)
       WHEN EXISTS(SELECT quality_Plan FROM base_consignor WHERE consignor_Id=P.consignor_Id AND quality_Plan IS NOT NULL) -- 货主
       THEN (SELECT quality_Plan FROM base_consignor WHERE consignor_Id=P.consignor_Id AND quality_Plan IS NOT NULL)
       ELSE NULL
       END) AS quality_Plan,
        (CASE WHEN P.quality_Plan IS NOT NULL
       THEN P.quality_Proportion
       WHEN EXISTS(SELECT quality_Plan FROM base_brand WHERE Brand_Id=P.Brand_Id AND quality_Plan IS NOT NULL)
       THEN (SELECT quality_Proportion FROM base_brand WHERE Brand_Id=P.Brand_Id AND quality_Plan IS NOT NULL)
       WHEN EXISTS(SELECT quality_Plan FROM base_product_type WHERE Type_Id=P.Type_Id AND quality_Plan IS NOT NULL)
       THEN (SELECT quality_Proportion FROM base_product_type WHERE Type_Id=P.Type_Id AND quality_Plan IS NOT NULL)
       WHEN EXISTS(SELECT quality_Plan FROM base_provider WHERE provider_Id={1} AND quality_Plan IS NOT NULL) -- 货主
       THEN (SELECT quality_Proportion FROM base_provider WHERE provider_Id={1} AND quality_Plan IS NOT NULL)
       WHEN EXISTS(SELECT quality_Plan FROM base_consignor WHERE consignor_Id=P.consignor_Id AND quality_Plan IS NOT NULL)
       THEN (SELECT quality_Proportion FROM base_consignor WHERE consignor_Id=P.consignor_Id AND quality_Plan IS NOT NULL)
       ELSE NULL  END) AS quality_Proportion
       FROM base_product as P  WHERE P.product_code={0}
      """;

    // 返回qualityPlan质检方案，qualityProportion=质检比例
    Map<String, Object> objectMap = SqlRunner.db().selectOne(sql, skuInfo.getProductCode(), skuInfo.getProviderId());
    Optional.ofNullable(objectMap).ifPresent(p -> {
      skuInfo.setQualityPlan(Convert.toStr(p.get("qualityPlan")));
      skuInfo.setQualityProportion(Convert.toBigDecimal(p.get("qualityProportion")));
    });
    return objectMap;

  }
  //#endregion

  //#region 预到货收货入库

  /**
   * 预到货收货入库
   *
   * @param inScanOrderBo 扫描入库单号
   * @return 返回R
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> normalScanSave(@NotNull InScanOrderBo inScanOrderBo) {

    return normalScanSave(inScanOrderBo, null);
  }

  /**
   * 预到货收货入库
   *
   * @param inScanOrderBo 扫描入库单号
   * @return 返回R
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> normalScanSaveXG(@NotNull InScanOrderBo inScanOrderBo) {
    // 根据 预到货单分组 因为 可能勾选多个预到货单

    Map<Long, List<InScanOrderDetailBo>> groupList = inScanOrderBo.getDataList().stream().collect(Collectors.groupingBy(InScanOrderDetailBo::getOrderId));
    for (var group : groupList.entrySet()) {
      InOrder orderInfo = inOrderService.getById(group.getKey());

      InScanOrderBo scanOrder = new InScanOrderBo();
      scanOrder.setOrderId(group.getKey());
      scanOrder.setOrderCode(orderInfo.getOrderCode());
      scanOrder.setOnShelve(inScanOrderBo.isOnShelve());
      scanOrder.setDataList(group.getValue());
      scanOrder.setScanInType(inScanOrderBo.getScanInType());
      normalScanSave(scanOrder, null);

    }
    return R.ok("入库成功");

  }

  /**
   * 预到货收货入库
   *
   * @param inScanOrderBo 扫描入库单号
   * @return 返回R
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> normalScanSave(@NotNull InScanOrderBo inScanOrderBo, Runnable callback) {
    // 过滤掉 扫描数量为0的

    var list = inScanOrderBo.getDataList().stream().filter(item -> B.isGreater(item.getFinishedQuantity())).toList();
    if (ObjectUtil.isNull(list) || B.isEqual(list.size())) {
      throw new ServiceException("必须扫描一条明细！");
    }
    inScanOrderBo.setDataList(list);
    LiteflowResponse response = flowExecutor.execute2Resp("inScanChain", inScanOrderBo, InScanContext.class);
    Assert.isFalse(!response.isSuccess(), response.getMessage());

    // 执行回调函数
    if (ObjectUtil.isNotNull(callback)) {
      callback.run();
    }

    return R.ok();
  }
  //#endregion







  /**
   * 根据商品条码获取预到货单数据
   *
   * @param map 前端参数
   */
  @Override
  public R<Map<String, Object>> getPurchaseOrderData(Map<String, Object> map) {
    String productModel = map.get("productModel").toString();

    // 系统参数设置中设置了  审核成功允许扫描入库
    var in_auditEnableScan = sysConfigService.getConfigBool("in_auditEnableScan");
    List<String> statusList = new ArrayList<>(Arrays.asList(InOrderStatusEnum.IN_TRANSIT.getName(), InOrderStatusEnum.PARTIAL_FINISHED.getName()));
    if (in_auditEnableScan) {
      statusList.add(InOrderStatusEnum.SUCCESS.getName());
    }
    //#region 获取需要扫描的数据
    MPJLambdaWrapper<InOrderDetail> detailWrapper = new MPJLambdaWrapper<>();
    detailWrapper
      .select(InOrderDetail::getOrderId, InOrderDetail::getBigUnit, InOrderDetail::getBigQty, InOrderDetail::getOrderDetailId,
        InOrderDetail::getProductId, InOrderDetail::getProductCode, InOrderDetail::getProductName, InOrderDetail::getProductModel,
        InOrderDetail::getProductSpec, InOrderDetail::getPurchasePrice, InOrderDetail::getSingleSignCode,
        InOrderDetail::getQuantity, InOrderDetail::getEnterQuantity, InOrderDetail::getPlateCode, InOrderDetail::getRemark,
        InOrderDetail::getProduceDate, InOrderDetail::getLimitDate, InOrderDetail::getBatchNumber, InOrderDetail::getRowWeight,
        InOrderDetail::getRowNetWeight, InOrderDetail::getOvercharges, InOrderDetail::getProviderId, InOrderDetail::getCaseNumber, InOrderDetail::getTypeName, InOrderDetail::getRowCube, InOrderDetail::getExpandFields)
      .select(InOrder::getProviderName,InOrder::getConsignorName,InOrder::getStorageName,InOrder::getStorageId, InOrder::getConsignorId, InOrder::getTrackingNumber, InOrder::getOrderCode, InOrder::getContainerNos)
      .innerJoin(InOrder.class, InOrder::getOrderId, InOrderDetail::getOrderId)
      .like(InOrderDetail::getProductModel, productModel)
      .in(InOrder::getOrderStatus, statusList)
      .orderByAsc(InOrderDetail::getOrderDetailId);
    List<Map<String, Object>> orderList = inOrderDetailService.selectJoinMaps(detailWrapper);

    Assert.isFalse(B.isEqual(orderList.size()), "该条码在" + statusList + "的预到货数据不存在！");
    // 返回数据
    Map<String, Object> resultData = new HashMap<>();
    resultData.put("orderList", orderList);

    return R.ok(resultData);
  }
  //endregion
}
