package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.out.*;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreSnComposeVo;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferService;
import com.yiruantong.outbound.constant.OutboundConstants;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPickingVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.operation.*;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.domain.core.SysConfig;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.yiruantong.common.satoken.utils.LoginHelper.getLoginUser;

/**
 * 拣货下架
 */
@RequiredArgsConstructor
@Service
public class OutScanPickingService implements IOutScanPickingService, IInventoryBaseService {
  private final IOutOrderService outOrderService;
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final IOutOrderWaveSubService outOrderWaveSubService;
  private final IOutOrderPickingService outOrderPickingService;
  private final IOutOrderPickingDetailService outOrderPickingDetailService;
  private final ISysConfigService sysConfigService;
  private final IBasePositionService basePositionService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IStoragePositionTransferService storagePositionTransferService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService;
  private final IBaseProductService baseProductService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IBaseStorageService baseStorageService;
  private final ICoreInventorySnService coreInventorySnService;

  //#region 获取波次扫描数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> getPickingData(Map<String, Object> map) {
    String orderWaveCode = Convert.toStr(map.get("orderWaveCode"));
    Assert.isFalse(ObjectUtil.isEmpty(orderWaveCode), "波次单号不能为空！");
    String scanInTypeStr = Convert.toStr(map.get("scanInType"));
    Assert.isFalse(StringUtils.isEmpty(scanInTypeStr), "扫描类型不能为空！");
    InventorySourceTypeEnum scanInType = InventorySourceTypeEnum.valueOf(scanInTypeStr);
    LoginUser loginUser = getLoginUser();
    Assert.isFalse(ObjectUtil.isNull(loginUser), "当前用户不存在！");

    String mainWaveCode = orderWaveCode; // 主波次号
    //区分主波次 子波次
    if (orderWaveCode.contains(StringUtils.SEPARATOR_MINUS)) {
      var arr = orderWaveCode.split(StringUtils.SEPARATOR_MINUS);
      mainWaveCode = arr[0];
    }
    var orderWave = outOrderWaveService.getByCode(mainWaveCode);
    if (ObjectUtil.isEmpty(orderWave)) {
      throw new ServiceException("波次不存在！");
    }

    if (B.isEqual(orderWave.getSubBatch(), EnableEnum.ENABLE.getId())) {
      //子波次场景
      var orderWaveSub = outOrderWaveSubService.getByCode(orderWaveCode);
      if (ObjectUtil.isEmpty(orderWaveSub)) {
        throw new ServiceException("请扫描子波次单号！");
      }
      if (B.isGreater(orderWaveSub.getPickUserId()) && !ObjectUtil.equal(loginUser.getUserId(), orderWaveSub.getPickUserId())) {
        return R.warn("当前子波次拣货单已经被抢走，请选择其他拣货单！");
      }
    } else {
      //主波次场景
      if (ObjectUtil.isNotEmpty(orderWave.getPickNickName())) {
        if (ObjectUtil.isNotEmpty(orderWave.getPickUserId()) && !ObjectUtil.equal(orderWave.getPickUserId(), loginUser.getUserId())) {
          return R.warn("当前拣货单已经被抢走，请选择其他拣货单！");
        }
      }

      orderWave.setUserId(loginUser.getUserId());
      orderWave.setNickName(loginUser.getNickname());
      outOrderWaveService.saveOrUpdate(orderWave);
    }

    String[] statusList = new String[]{OutOrderWaveStatusEnum.WAIT_PICKING.getName(), OutOrderWaveStatusEnum.PICKING.getName(), OutOrderWaveStatusEnum.PART_PICKING.getName(), OutOrderWaveStatusEnum.WAVE_FINISHED.getName(), OutOrderWaveStatusEnum.PACKAGE_PARTIAL.getName(), OutOrderWaveStatusEnum.MATCHED.getName()};

    if (!Arrays.asList(statusList).contains(orderWave.getWaveStatus())) {
      throw new ServiceException("波次单必须是" + String.join(",", statusList) + "才允许下架操作，当前状态是[" + orderWave.getWaveStatus() + "]！");
    }
    // 拣货路径优先方式
    String pickOrder_firstMode = sysConfigService.selectConfigByKey("pickOrder_firstMode");

    // 查询字段
    String selectFields = "productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,bigUnit,smallUnit," +
      "pickQuantity,holderStorage,salePrice,remark,orderId,orderDetailId,orderWaveDetailId,plateCode,positionName,weight,rowWeight," +
      "consignorId,consignorCode,consignorName,storageId,storageName,middleBarcode,middleUnitConvert,bigBarcode,unitConvert," +
      "singleSignCode,areaCode,channelCode,brandName," +
      "t2.relationCode,t2.relationCode2,t2.relationCode3,t2.relationCode4,t2.relationCode5,middleUnitConvert,bigBarcode,unitConvert,isManageSn";

    // 求和字段
    String sumFields = "quantityOrder,quantityOrderOrigin,pickQuantity,holderStorage,rowWeight";

    // 分组字段
    String groupFields = "storageId,storageName,consignorId,consignorCode,consignorName,positionName,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,plateCode,areaCode,channelCode,columnCode,rowCode,middleBarcode,middleUnitConvert,bigBarcode,unitConvert";

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
      .gt(OutOrderWaveDetail::getQuantityOrder, BigDecimal.ZERO)
      .in(OutOrderWaveDetail::getPositionType, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.STORAGE.getId(), PositionTypeEnum.ELEVATED.getId()));

    // 子波次扫描
    if (B.isEqual(orderWave.getSubBatch(), EnableEnum.ENABLE.getId())) {
      wrapper.eq(OutOrderWaveDetail::getSubOrderWaveCode, orderWaveCode);
    }

    if (ObjectUtil.equals(pickOrder_firstMode, OutboundConstants.PICK_ORDER_FIRST_MODE_COL)) {
      // 库区优先
      wrapper.orderByAsc(BasePosition::getAreaCode)
        .orderByAsc(BasePosition::getChannelCode)
        .orderByAsc(BasePosition::getRowCode);
    } else {
      // 货位优先
      wrapper.orderByAsc(OutOrderWaveDetail::getPositionName)
        .orderByAsc(BasePosition::getRowCode)
        .orderByAsc(BasePosition::getColumnCode);
    }

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, OutOrderWaveDetail.class, OutOrderWave.class, BaseProduct.class, CoreInventoryHolder.class, BasePosition.class);
    List<OutOrderWaveDetailPickingVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailPickingVo.class, wrapper);
    Assert.isFalse(inventoryComposeVoList.isEmpty(), "没有可下架的波次明细！");

    Map<String, Object> result = new HashMap<>();
    // 仓库信息
    var storageInfo = baseStorageService.selectById(orderWave.getStorageId());
    // 是否管理SN
    for (var item : inventoryComposeVoList) {
      var isManageSn = item.getIsManageSn();
      var snDisabled = storageInfo.getSnDisabled(); // 仓库设置了不需SN管理
      if (snDisabled == 1) {
        isManageSn = 0; // 不需要SN
      }
      item.setIsManageSn(isManageSn);
    }

    // 获取下架理货位
    List<BasePosition> offPositionList = basePositionService.getOffPositionList(orderWave.getStorageId());
    result.put("dataList", inventoryComposeVoList);
    result.put("offPositionList", offPositionList);
    result.put("storageId", orderWave.getStorageId());
    result.put("consignorId", orderWave.getConsignorId());

    // 创建拣货单
    outOrderPickingService.createPicking(orderWaveCode, orderWave, scanInType);

    // 更新波次单主表
    LambdaUpdateWrapper<OutOrderWave> outOrderWaveWrapper = new LambdaUpdateWrapper<>();
    outOrderWaveWrapper
      .set(OutOrderWave::getPickNickName, loginUser.getNickname())
      .eq(OutOrderWave::getOrderWaveId, orderWave.getOrderWaveId());

    outOrderWaveService.update(outOrderWaveWrapper);

    return R.ok(result);
  }

  //#region getZgPickingData
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Map<String, Object>> getZgPickingData(Map<String, Object> map) {
    String orderWaveCode = Convert.toStr(map.get("orderWaveCode"));
    var pickingData = this.getPickingData(map).getData();

    OutOrderWave outOrderWave = outOrderWaveService.getByCode(orderWaveCode);
    List<OutOrderWaveDetail> outOrderWaveDetailLis = outOrderWaveDetailService.selectListByMainId(outOrderWave.getOrderWaveId());

    Map<String, Object> result = new HashMap<>();
    result.put("dataList", pickingData.get("dataList"));
    result.put("orderDetailList", outOrderWaveDetailLis);
    result.put("offPositionList", pickingData.get("offPositionList"));

    return R.ok(result);
  }
  //#endregion

  //#region 拣货人员领取任务
  @Override
  public R<Void> receiveTask(Map<String, Object> map) {
    boolean isMain = Convert.toBool(map.get("isMain"));
    String orderWaveCode = Convert.toStr(map.get("orderWaveCode"));
    String subOrderWaveCode = Convert.toStr(map.get("subOrderWaveCode"));

    // 拣货领取任务上限
    LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysConfig::getConfigKey, "pick_receiveTaskLimitationNumber");
    SysConfig sysConfig = sysConfigService.getOne(queryWrapper);
    if (ObjectUtil.isNotEmpty(sysConfig)) {
      LambdaQueryWrapper<OutOrderWave> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.in(OutOrderWave::getWaveStatus, OutOrderStatusEnum.WAVE_FINISHED.getName(), OutOrderStatusEnum.PICKING.getName());
      lambdaQueryWrapper.in(OutOrderWave::getNickName, LoginHelper.getNickname());
      List<OutOrderWave> outOrderWave = outOrderWaveService.list(lambdaQueryWrapper);

      if (outOrderWave.size() > Convert.toLong(sysConfig.getConfigValue())) {
        throw new ServiceException("拣货任务领取已达上限");
      }
    }

    //主波次 isMain
    if (isMain) {
      outOrderWaveService.updatePickUserInfo(orderWaveCode, LoginHelper.getUserId(), LoginHelper.getNickname());
    } else {
      outOrderWaveSubService.updatePickUserInfo(subOrderWaveCode, LoginHelper.getUserId(), LoginHelper.getNickname());
    }

    return R.ok();
  }
  //#endregion

  //#region 保存扫描拣货下架
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> savePickingScan(OutScanMainBo outScanMainBo) {
    boolean isExistSub = false; // 是否子波次
    String orderWaveCode = outScanMainBo.getOrderWaveCode();
    String mainWaveCode = orderWaveCode; // 主波次号
    //区分主波次 子波次
    if (orderWaveCode.contains(StringUtils.SEPARATOR_MINUS)) {
      isExistSub = true;
      var arr = orderWaveCode.split(StringUtils.SEPARATOR_MINUS);
      mainWaveCode = arr[0];
    }

    OutOrderWave outOrderWave = outOrderWaveService.getByCode(mainWaveCode);
    Assert.isFalse(ObjectUtil.isNull(outOrderWave), outOrderWave.getOrderWaveCode() + "波次单不存在！");
    List<OutOrderStatusEnum> orderStatusEnums = CollUtil.newArrayList(OutOrderStatusEnum.WAVE_FINISHED, OutOrderStatusEnum.PICKING);
    Assert.isFalse(orderStatusEnums.stream().noneMatch(a -> Objects.equals(a.getName(), outOrderWave.getWaveStatus())),
      "{}当前状态{}不允许操作下架！", outOrderWave.getOrderWaveCode(), outOrderWave.getWaveStatus());
    // 波次明细
    List<OutOrderWaveDetail> waveDetailList = outOrderWaveDetailService.selectListById(outOrderWave.getOrderWaveId());
    // 当前扫描的波次明细
    var detailList = outScanMainBo.getDataList();

    // 设置仓库、货主
    outScanMainBo.setStorageId(outOrderWave.getStorageId());
    outScanMainBo.setStorageName(outOrderWave.getStorageName());
    outScanMainBo.setConsignorId(outOrderWave.getConsignorId());
    outScanMainBo.setConsignorCode(outOrderWave.getConsignorCode());
    outScanMainBo.setConsignorName(outOrderWave.getConsignorName());

    // 验证SN
    for (var detail : detailList) {
      if (B.isEqual(detail.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        if (StringUtils.isEmpty(detail.getSingleSignCode())) {
          throw new ServiceException(detail.getProductModel() + "SN不能为空！");
        }
        List<String> snList = StringUtils.splitList(detail.getSingleSignCode());
        if (!B.isEqual(snList.size(), detail.getFinishedQuantity())) {
          throw new ServiceException(detail.getProductModel() + "扫描的SN数量不正确");
        }
        List<CoreSnComposeVo> validSnList = coreInventorySnService.selectSnComposeList(outScanMainBo.getStorageId(), outScanMainBo.getConsignorId(), detail.getProductId(), snList, List.of(PositionTypeEnum.NORMAL, PositionTypeEnum.STORAGE, PositionTypeEnum.ELEVATED));
        if (!B.isEqual(validSnList.size(), snList.size())) {
          throw new ServiceException("商品编号【" + detail.getProductModel() + "】SN无效：" +
            snList.stream().filter(e -> !validSnList.stream().map(CoreSnComposeVo::getSnNo).toList().contains(e)).collect(Collectors.joining(",")));
        }
      }
    }

    //#region 通用模块调用
    inventoryCommonService.setBizService(this);

    // 构建DTO数据 - 主表
    CommonMainDto commonMainDto = BeanUtil.copyProperties(outScanMainBo, CommonMainDto.class);
    commonMainDto.setSortingStatus(SortingStatusEnum.ASSIGNED.getId());

    // 拣货单主表
    OutOrderPicking outOrderPicking = outOrderPickingService.getByOrderWaveCode(outOrderWave.getOrderWaveCode());
    Assert.isFalse(ObjectUtil.isEmpty(outOrderPicking), "当前波次拣货单不存在！");

    // 构建DTO数据 - 明细集合
    List<CommonDetailDto> commonDetailDtoList = new ArrayList<>();
    for (var detail : detailList) {
      List<OutOrderWaveDetail> waveDetailFilterList = waveDetailList.stream()
        .filter(f ->
          ObjectUtil.equal(f.getProductId(), detail.getProductId())
            && B.isGreater(f.getQuantityOrder())
            && !ObjectUtil.equals(f.getPositionType(), PositionTypeEnum.UNLOADING.getId())
            && ObjectUtil.equal(f.getBatchNumber(), detail.getBatchNumber())
            && ObjectUtil.equal(f.getPositionName(), detail.getPositionName())
            && ObjectUtil.equal(f.getProduceDate(), detail.getProduceDate())).toList();
      BigDecimal finishedQuantity = detail.getFinishedQuantity();
      Assert.isFalse(B.isLessOrEqual(finishedQuantity), detail.getProductCode() + "当前扫描数量不能<=0！");

      for (OutOrderWaveDetail waveDetail : waveDetailFilterList) {
        BigDecimal outQuantity;
        if (B.isGreater(finishedQuantity, waveDetail.getQuantityOrder())) {
          outQuantity = waveDetail.getQuantityOrder();
          finishedQuantity = B.sub(finishedQuantity, waveDetail.getQuantityOrder());
        } else {
          outQuantity = finishedQuantity;
          finishedQuantity = BigDecimal.ZERO;
        }
        if (B.isLessOrEqual(outQuantity)) break;

        CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
        detailDto.setMainId(waveDetail.getOrderId()); // 主表ID
        detailDto.setDetailId(waveDetail.getOrderDetailId()); // 明细ID
        detailDto.setInQuantity(BigDecimal.ZERO);  // 入库数量
        detailDto.setOutQuantity(outQuantity); // 出库数量
        detailDto.setPositionNameIn(null); // 入库货位
        detailDto.setPositionNameOut(waveDetail.getPositionName());  // 出库货位
        detailDto.setAllotPositionName(waveDetail.getAllotPositionName()); // 配货位
        detailDto.setBillCode(waveDetail.getOrderCode());
        detailDto.setSourceCode2(waveDetail.getSubOrderWaveCode());  // 子波次单号
        commonDetailDtoList.add(detailDto);

        // 将原波次明细数量扣减
        waveDetail.setQuantityOrder(B.sub(waveDetail.getQuantityOrder(), outQuantity));
        outOrderWaveDetailService.updateById(waveDetail);
      }

      //region 生成拣货单明细
      OutOrderPickingDetail outOrderPickingDetail = BeanUtil.copyProperties(detail, OutOrderPickingDetail.class);
      outOrderPickingDetail.setOrderPickingId(outOrderPicking.getOrderPickingId());
      outOrderPickingDetail.setQuantityOrder(detail.getFinishedQuantity());
      outOrderPickingDetail.setPlateCode(detail.getPlateCode());
      outOrderPickingDetail.setPurchaseAmount(B.mul(outOrderPickingDetail.getQuantityOrder(), detail.getPurchasePrice()));
      outOrderPickingDetail.setRowWeight(B.mul(outOrderPickingDetail.getQuantityOrder(), detail.getWeight()));
      outOrderPickingDetail.setOrderWaveId(outOrderPicking.getOrderWaveId());

      // 商品信息
      BaseProduct baseProduct = baseProductService.getById(detail.getProductId());
      // 大单位数量 = 数量 / 大单位换算
      outOrderPickingDetail.setBigQty(B.div(detail.getFinishedQuantity(), baseProduct.getUnitConvert()));
      Assert.isFalse(ObjectUtil.isEmpty(outOrderPickingDetail.getQuantityOrder()), detail.getProductCode() + "当前拣货数量不能<=0！");
      Assert.isFalse(ObjectUtil.isEmpty(outOrderPickingDetail.getOrderId()), detail.getProductCode() + "当前拣货明细出库单ID不能为空！");
      Assert.isFalse(ObjectUtil.isEmpty(outOrderPickingDetail.getOrderDetailId()), detail.getProductCode() + "当前拣货明细出库单明细ID不能为空！");

      // 查到当前明细数据
      LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
      OutOrderDetail outOrderDetail = outOrderDetailService.getOne(detailLambdaQueryWrapper);

      if (ObjectUtil.isNotNull(outOrderDetail)) {

        outOrderPickingDetail.setUnitCube(outOrderDetail.getUnitCube());
        outOrderPickingDetail.setRowCube(B.mul(detail.getFinishedQuantity(), outOrderDetail.getUnitCube()));
        outOrderPickingDetail.setRate(outOrderDetail.getRate());
        outOrderPickingDetail.setSalePrice(outOrderDetail.getSalePrice());
        outOrderPickingDetail.setRatePrice(outOrderDetail.getRatePrice());
        outOrderPickingDetail.setSaleAmount(B.mul(detail.getFinishedQuantity(), outOrderDetail.getSalePrice()));
        outOrderPickingDetail.setRateAmount(B.mul(detail.getFinishedQuantity(), outOrderDetail.getRatePrice()));

        // 获取占位
        LambdaQueryWrapper<CoreInventoryHolder> holderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        holderLambdaQueryWrapper.eq(CoreInventoryHolder::getDetailId, detail.getOrderDetailId());
        if (ObjectUtil.isNotEmpty(detail.getPositionName())) {
          holderLambdaQueryWrapper.eq(CoreInventoryHolder::getPositionName, detail.getPositionName());
        }
        // 生产日期
        if (ObjectUtil.isNotEmpty(detail.getProduceDate())) {
          holderLambdaQueryWrapper.eq(CoreInventoryHolder::getProduceDate, detail.getProduceDate());
        }
        // 批次号
        if (ObjectUtil.isNotEmpty(detail.getBatchNumber())) {
          holderLambdaQueryWrapper.eq(CoreInventoryHolder::getBatchNumber, detail.getBatchNumber());
        }
        // 商品规格
        if (ObjectUtil.isNotEmpty(detail.getProductSpec())) {
          holderLambdaQueryWrapper.eq(CoreInventoryHolder::getProductSpec, detail.getProductSpec());
        }
        // 集装箱号
        if (ObjectUtil.isNotEmpty(detail.getContainerNo())) {
          holderLambdaQueryWrapper.eq(CoreInventoryHolder::getContainerNo, detail.getContainerNo());
        }


        holderLambdaQueryWrapper.last("limit 1");
        CoreInventoryHolder coreInventoryHolder = coreInventoryHolderService.getOne(holderLambdaQueryWrapper);
        if (ObjectUtil.isNotNull(coreInventoryHolder)) {

          // 库存数据
          CoreInventory coreInventory = coreInventoryService.getById(coreInventoryHolder.getInventoryId());

          if (ObjectUtil.isNotNull(coreInventory)) {
            // 获取商品
            BaseProduct productInfo = baseProductService.getById(detail.getProductId());

            if (ObjectUtil.isNotEmpty(productInfo)) {
              outOrderPickingDetail.setBrandName(productInfo.getBrandName());
              outOrderPickingDetail.setTypeName(productInfo.getTypeName());
              outOrderPickingDetail.setProductBarCode(productInfo.getProductBarCode());
              outOrderPickingDetail.setThermocline(productInfo.getThermocLine());
              outOrderPickingDetail.setImages(productInfo.getImages());
            }
          }
        }
      }

      outOrderPickingDetail.setPlateCode(detail.getPlateCode());

      outOrderPickingDetailService.save(outOrderPickingDetail);
      //endregion
    }

    // 原货位出库，返回占位数据集合
    R<List<CommonDetailDto>> outResult = inventoryCommonService.out(commonMainDto, commonDetailDtoList, outScanMainBo.getScanInType());

    // SN出库消减
    for (var detail : detailList) {
      if (B.isEqual(detail.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        List<String> snList = StringUtils.splitList(detail.getSingleSignCode());
        coreInventorySnService.updateInvalid(outScanMainBo.getStorageId(), snList);
      }
    }

    List<CommonDetailDto> detailDtoList = outResult.getData();
    detailDtoList.forEach(detailDto -> {
      detailDto.setBillCode(outOrderWave.getOrderWaveCode());
      detailDto.setPositionNameIn(outScanMainBo.getPositionName()); // 入库货位
      // 将出库返回的占位数据作为下架入库数据
    });
    R<List<CommonDetailDto>> inResult = inventoryCommonService.in(commonMainDto, detailDtoList, outScanMainBo.getScanInType());
    // SN入库到下架理货位
    for (var detail : detailList) {
      if (B.isEqual(detail.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        List<String> snList = StringUtils.splitList(detail.getSingleSignCode());

        CommonDetailDto commonDetailDto = inResult.getData().stream().filter(f -> StringUtils.equals(f.getProductCode(), detail.getProductCode())
            && StringUtils.equals(f.getStorageName(), outScanMainBo.getStorageName())
            && StringUtils.equals(f.getConsignorName(), detail.getConsignorName()))
          .findFirst().orElse(null);
        if (ObjectUtil.isNull(commonDetailDto)) {
          throw new ServiceException(detail.getProductCode() + "不存在库存，无法挂载SN！");
        }

        List<CoreInventorySn> coreInventorySnList = snList.stream().map(sn -> {
          CoreInventorySn coreInventorySn = BeanUtil.copyProperties(detail, CoreInventorySn.class);
          coreInventorySn.setEnable(EnableEnum.ENABLE.getId());
          coreInventorySn.setSnNo(sn);
          coreInventorySn.setPositionName(outScanMainBo.getPositionName()); // 设置下架理货位
          coreInventorySn.setStorageId(outScanMainBo.getStorageId());
          coreInventorySn.setStorageName(outScanMainBo.getStorageName());
          coreInventorySn.setConsignorId(outScanMainBo.getConsignorId());
          coreInventorySn.setConsignorCode(outScanMainBo.getConsignorCode());
          coreInventorySn.setConsignorName(outScanMainBo.getConsignorName());
          coreInventorySn.setMainId(commonDetailDto.getMainId());
          coreInventorySn.setDetailId(commonDetailDto.getDetailId());
          coreInventorySn.setInventoryId(commonDetailDto.getInventoryId());

          return coreInventorySn;
        }).toList();
        coreInventorySnService.saveBatch(coreInventorySnList);
      }
    }

    commonMainDto.setHolderSourceType(HolderSourceTypeEnum.OUT_PICKING); // 占位类型：拣货下架
    // 根据入库数据，重新生成占位
    for (var commonDetailDto : inResult.getData()) {
      OutOrder outOrder = outOrderService.getById(commonDetailDto.getMainId());
      commonDetailDto.setBillCode(outOrder.getOrderCode());
      commonDetailDto.setSourceType(HolderSourceTypeEnum.OUT_PICKING.getName());
      commonDetailDto.setPositionNameOut(null); // 清空出库货位
      // 生成新占位
      CoreInventoryHolder inventoryHolder = coreInventoryHolderService.createHolder(commonMainDto, commonDetailDto);

      // 生成新波次明细
      OutOrderWaveDetail outOrderWaveDetail = BeanUtil.copyProperties(outOrder, OutOrderWaveDetail.class);
      BeanUtil.copyProperties(inventoryHolder, outOrderWaveDetail, CopyOptions.create().setIgnoreNullValue(true));

      outOrderWaveDetail.setOrderWaveId(outOrderWave.getOrderWaveId());
      outOrderWaveDetail.setOrderId(commonDetailDto.getMainId());
      outOrderWaveDetail.setOrderDetailId(commonDetailDto.getDetailId());
      outOrderWaveDetail.setPositionType(PositionTypeEnum.UNLOADING.getId()); // 类型为：下架理货位
      outOrderWaveDetail.setQuantityOrder(inventoryHolder.getHolderStorage());
      outOrderWaveDetail.setPickQuantity(inventoryHolder.getHolderStorage()); // 拣货下架数量
      outOrderWaveDetail.setAllotPositionName(commonDetailDto.getAllotPositionName());
      outOrderWaveDetail.setMatchedQuantity(BigDecimal.ZERO);
      outOrderWaveDetail.setSubOrderWaveCode(commonDetailDto.getSourceCode2()); // 子波次单号
      outOrderWaveDetailService.save(outOrderWaveDetail);
    }
    //#endregion

    //#region 更新状态
    waveDetailList = outOrderWaveDetailService.selectListById(outOrderWave.getOrderWaveId());
    // 订单分组
    Map<Long, List<OutOrderWaveDetail>> listMap = waveDetailList.stream().collect(Collectors.groupingBy(OutOrderWaveDetail::getOrderId));

    OutOperationTypeEnum outOperationTypeEnum = OutOperationTypeEnum.PC_ORDER_PICKING;
    OutWaveOperationTypeEnum waveOperationTypeEnum = OutWaveOperationTypeEnum.PC_PICKING;
    switch (outScanMainBo.getScanInType()) {
      case PDA_ORDER_PICKING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_PICKING;
        waveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_PICKING;
      }
      case PDA_PLATE_PICKING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_PLATE_PICKING;
        waveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_PLATE_PICKING;
      }
      case PDA_ORDER_PICKING_ZG -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_ORDER_PICKING_ZG;
        waveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_ORDER_PICKING_ZG;
      }
    }

    // 更新订单中的状态
    for (var groupItem : listMap.entrySet()) {
      Long orderId = groupItem.getKey();
      List<OutOrderWaveDetail> waveDetails = groupItem.getValue();
      BigDecimal pickQuantity = StreamUtils.sum(waveDetails, OutOrderWaveDetail::getPickQuantity); // 获取下架数量
      BigDecimal quantityOrder = StreamUtils.sum(waveDetails, OutOrderWaveDetail::getQuantityOrder); // 获取出库单数量
      // 查询出库单
      LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(OutOrder::getOrderId, orderId);
      OutOrder outOrder = outOrderService.getOne(orderLambdaQueryWrapper);

      if (B.isGreaterOrEqual(pickQuantity, quantityOrder)) {
        // 下架完成
        outOrderService.updatePickingStatus(orderId, OutPickingStatusEnum.PICKED);
        // 更新订单状态
        outOrderService.updateOrderStatus(orderId, OutOrderStatusEnum.PICKED);

        // 生成新建出库单的轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.PICKED);
      } else if (B.isLess(pickQuantity, quantityOrder) && B.isGreater(pickQuantity)) {
        // 部分完成
        outOrderService.updatePickingStatus(orderId, OutPickingStatusEnum.PICKING_PARTIAL);
        // 更新订单状态
        outOrderService.updateOrderStatus(orderId, OutOrderStatusEnum.PICKING);

        // 生成新建出库单的轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.PICKING);
      } else {
        // 拣货中
        outOrderService.updatePickingStatus(orderId, OutPickingStatusEnum.PICKING);
        // 生成新建出库单的轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.PICKING);
      }
    }

    // 更新波次单状态，获取波次数量
    BigDecimal pickQuantity = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getPickQuantity); // 获取下架数量
    BigDecimal quantityOrder = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getQuantityOrder); // 获取出库单数量

    // 获取新单的明细数据
    List<OutOrderPickingDetail> outOrderPickingDetails = outOrderPickingDetailService.selectListByMainId(outOrderPicking.getOrderPickingId());

    // 拣货数量
    BigDecimal totalQuanityOrder = BigDecimal.ZERO;
    BigDecimal totalWeight = BigDecimal.ZERO;
    // 更新新单数据
    if (!outOrderPickingDetails.isEmpty()) {
      // 合计销售金额
      totalQuanityOrder = outOrderPickingDetails.stream().map(OutOrderPickingDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
      totalWeight = outOrderPickingDetails.stream().map(OutOrderPickingDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    outOrderPicking.setTotalQuanityOrder(totalQuanityOrder);
    outOrderPicking.setTotalWeight(totalWeight);
    if (B.isGreaterOrEqual(pickQuantity, quantityOrder)) {
      // 下架完成
      outOrderWaveService.updateWaveStatus(outOrderWave.getOrderWaveId(), OutOrderWaveStatusEnum.PICKED, orderWaveCode);
      // 生成波次的轨迹
      outOrderWaveStatusHistoryService.AddHistory(outOrderWave, waveOperationTypeEnum, OutOrderStatusEnum.PICKED);
      // 计算拣货单时间
      outOrderPicking.setEndDate(DateUtil.date());
      outOrderPicking.setPickingStatus(OutOrderStatusEnum.PICKED.getName());
      String spanTime = DateUtil.formatBetween(outOrderPicking.getStartDate(), outOrderPicking.getEndDate(), BetweenFormatter.Level.SECOND);
      outOrderPicking.setSpanTime(spanTime); // 中文描述，例如：5分钟25秒
    } else {
      // 拣货中
      outOrderWaveService.updateWaveStatus(outOrderWave.getOrderWaveId(), OutOrderWaveStatusEnum.PICKING, orderWaveCode);
      // 生成波次的轨迹
      outOrderWaveStatusHistoryService.AddHistory(outOrderWave, waveOperationTypeEnum, OutOrderStatusEnum.PICKING);
    }

    // 如果存在子波次时，拣货单的状态需要单独处理，因为每个子波次对应一个拣货单
    if (isExistSub) {
      // 筛选出子波次明细数据
      waveDetailList = waveDetailList.stream().filter(x -> StringUtils.equals(x.getSubOrderWaveCode(), orderWaveCode)).toList();
      // 更新波次单状态
      pickQuantity = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getPickQuantity); // 获取下架数量
      quantityOrder = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getQuantityOrder); // 获取出库单数量
      if (B.isGreaterOrEqual(pickQuantity, quantityOrder)) {
        // 计算拣货单时间
        outOrderPicking.setEndDate(DateUtil.date());
        outOrderPicking.setPickingStatus(OutOrderStatusEnum.PICKED.getName());
        String spanTime = DateUtil.formatBetween(outOrderPicking.getStartDate(), outOrderPicking.getEndDate(), BetweenFormatter.Level.SECOND);
        outOrderPicking.setSpanTime(spanTime); // 中文描述，例如：5分钟25秒
      }
    }
    outOrderPickingService.updateById(outOrderPicking);
    //#endregion

    LoginUser loginUser = getLoginUser();
    Assert.isFalse(ObjectUtil.isNull(loginUser), "当前用户不存在！");

    // 更新波次单主表拣货数量
    LambdaUpdateWrapper<OutOrderWave> outOrderWaveWrapper = new LambdaUpdateWrapper<>();
    outOrderWaveWrapper
      .set(OutOrderWave::getPickQuantity, pickQuantity)
      .eq(OutOrderWave::getOrderWaveId, outOrderWave.getOrderWaveId());
    outOrderWaveService.update(outOrderWaveWrapper);

    OutOrderWave outOrderWave1 = outOrderWaveService.getById(outOrderWave.getOrderWaveId());
    Map<String, Object> result = new HashMap<>();
    result.put("waveStatus", outOrderWave1.getWaveStatus());
    return R.ok(result);
  }
  //endregion

  //#region 获取拣货下架回拣数据
  @Override
  public R<List<CoreInventoryComposeVo>> getOffPositionShelveData(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String positionName = Convert.toStr(map.get("positionName"));
    String productModel = Convert.toStr(map.get("productModel"));
    Assert.isFalse(ObjectUtil.isEmpty(positionName), "下架理货位不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(productModel), "商品条码不能为空！");

    LoginUser loginUser = getLoginUser();
    Assert.isFalse(ObjectUtil.isNull(loginUser), "当前用户不存在！");

    // 查询字段
    String selectFields = "inventoryId,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,purchasePrice,weight,netWeight,productAttribute,storageStatus,plateCode,positionName,consignorId,consignorCode,consignorName,storageId,storageName,relationCode,relationCode2,relationCode3,relationCode4,relationCode5,middleUnitConvert,bigBarcode,unitConvert,bigUnit,smallUnit,isManageSn";

    // 求和字段
    String sumFields = "productStorage,rowWeight,holderStorage,validStorage";

    // 分组字段
    String groupFields = "storageId,storageName,consignorId,consignorCode,consignorName,positionName,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,plateCode,areaCode,channelCode,columnCode,rowCode,middleBarcode,middleUnitConvert,bigBarcode,unitConvert";

    // 构建联表查询
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .eq(CoreInventory::getStorageId, storageId)
      .eq(CoreInventory::getPositionName, positionName)
      .eq(CoreInventory::getStorageStatus, InventoryStatusEnum.NORMAL.getName())
      .eq(BasePosition::getIsLocked, EnableEnum.DISABLE.getId()) // 排除所得货位，1=锁定，0=未锁定
      .gt(CoreInventory::getProductStorage, BigDecimal.ZERO)
      .eq(BasePosition::getPositionType, PositionTypeEnum.UNLOADING.getId()) // 下架理货位
      .eq(CoreInventory::getProductModel, productModel)
      .gt(CoreInventory::getValidStorage, BigDecimal.ZERO)
      .last("limit 50");

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, CoreInventory.class, BaseProduct.class, BasePosition.class);
    List<CoreInventoryComposeVo> inventoryComposeVoList = coreInventoryService.selectJoinList(CoreInventoryComposeVo.class, wrapper);

    return R.ok(inventoryComposeVoList);
  }
  //endregion

  //#region 保存拣货下架回拣数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveOffPositionShelveData(ScanPositionTransferBo storageScanPositionTransferBo) {
    storagePositionTransferService.savePositionTransfer(storageScanPositionTransferBo);

    return R.ok();
  }

  @Override
  public R<Void> checkSn(Map<String, Object> map) {
    List<String> snList = StreamUtils.toList(Convert.toList(map.get("snList")), Convert::toStr);

    if (snList.isEmpty()) {
      return R.ok();
    }

    Long productId = Convert.toLong(map.get("productId"));
    String productCode = Convert.toStr(map.get("productCode"));
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));
    List<CoreSnComposeVo> validSnList = coreInventorySnService.selectSnComposeList(storageId, consignorId, productId, snList, List.of(PositionTypeEnum.NORMAL, PositionTypeEnum.STORAGE, PositionTypeEnum.ELEVATED));
    if (!B.isEqual(validSnList.size(), snList.size())) {
      throw new ServiceException("商品编号【" + productCode + "】SN无效：" +
        snList.stream().filter(e -> !validSnList.stream().map(CoreSnComposeVo::getSnNo).toList().contains(e)).collect(Collectors.joining(",")));
    }

    return R.ok();
  }
  //endregion
}
