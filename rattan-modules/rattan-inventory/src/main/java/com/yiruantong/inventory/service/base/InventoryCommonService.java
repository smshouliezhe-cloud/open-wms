package com.yiruantong.inventory.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.enums.HistoryTypeEnum;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xieti
 */
@RequiredArgsConstructor
@Service
public class InventoryCommonService implements IInventoryCommonService {
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IBaseProductService baseProductService;
  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IBasePositionService basePositionService;
  private final ThreadLocal<List<CommonDetailDto>> commonDetailDtoList = new TransmittableThreadLocal<>();
  private IInventoryBaseService inventoryBaseService;

  @Override
  public void setBizService(IInventoryBaseService baseService) {
    this.inventoryBaseService = baseService;
  }

  //#region 分拣库存
  @Override
  public R<List<CommonDetailDto>> sorting(CommonMainDto mainInfo, List<CommonDetailDto> detailList, HolderSourceTypeEnum holderSourceTypeEnum) {
    commonDetailDtoList.set(new ArrayList<>()); // 初始化
    mainInfo.setHolderSourceType(holderSourceTypeEnum); // 占位类型

    // 出库单信息
    if (ObjectUtil.isEmpty(mainInfo)) {
      throw new ServiceException("单据不存在");
    }
    Assert.isFalse(B.isEqual(mainInfo.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId()), "已经分拣的单据不允许在分拣");

    // 清除占位
    coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), mainInfo.getMainId());
    // 计算出库单明细缺货数量
    inventoryBaseService.updateLackStorage(detailList);
    for (var detail : detailList) {
      if (B.isLessOrEqual(detail.getOutQuantity())) continue; // 明细出库数量小于0忽略

      // 商品信息
      BaseProduct baseProduct = baseProductService.getById(detail.getProductId());
      Long consignorId = detail.getConsignorId();
      if (ObjectUtil.isEmpty(consignorId)) consignorId = mainInfo.getConsignorId();

      //#region  获取有效库存，并过滤
      MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
      wrapper.eq(CoreInventory::getStorageId, mainInfo.getStorageId())
        .eq(CoreInventory::getConsignorId, consignorId)
        .eq(CoreInventory::getProductId, baseProduct.getProductId())
        .eq(ObjectUtil.isNotEmpty(detail.getInventoryId()), CoreInventory::getInventoryId, detail.getInventoryId()) // 根据ID精确分配库存
        .eq(ObjectUtil.isNotEmpty(detail.getPositionNameOut()), CoreInventory::getPositionName, detail.getPositionNameOut()); // 出库货位条件
      List<CoreInventoryComposeVo> inventoryList; // 有效库存数据
      if (ObjectUtil.equals(HolderSourceTypeEnum.STORAGE_ADJUST, holderSourceTypeEnum)
        || ObjectUtil.equals(HolderSourceTypeEnum.PC_STACKING_IN, holderSourceTypeEnum)
      ) {
        List<InventoryStatusEnum> inventoryStatusEnumList = CollUtil.newArrayList(InventoryStatusEnum.NORMAL, InventoryStatusEnum.TALLY_WAITING);
        coreInventoryService.setInventoryStatusEnumList(inventoryStatusEnumList); // 设置库存可分拣的状态
      }
      if (ObjectUtil.equals(HolderSourceTypeEnum.QUALITY_INSPECTION_RETURN, holderSourceTypeEnum)) {
        List<InventoryStatusEnum> inventoryStatusEnumList = CollUtil.newArrayList(InventoryStatusEnum.ZERO_INVENTORY, InventoryStatusEnum.NORMAL);
        coreInventoryService.setInventoryStatusEnumList(inventoryStatusEnumList); // 设置库存可分拣的状态
      }

      if (ObjectUtil.equals(HolderSourceTypeEnum.SPILLOVER, holderSourceTypeEnum)) {
        // 如果是溢出 则只需要看商品 跟库存类型 其他的都不需要看
        wrapper = new MPJLambdaWrapper<>();
        wrapper.eq(CoreInventory::getProductId, baseProduct.getProductId());
        List<InventoryStatusEnum> inventoryStatusEnumList = CollUtil.newArrayList(InventoryStatusEnum.SPILLOVER);
        coreInventoryService.setInventoryStatusEnumList(inventoryStatusEnumList); // 设置库存可分拣的状态
      }
      // 设置可分拣的库存属性
      if (CollUtil.isNotEmpty(mainInfo.getProductAttributeEnumList())) {
        coreInventoryService.setProductAttributeEnumList(mainInfo.getProductAttributeEnumList());
      }
      inventoryList = coreInventoryService.selectValidInventoryList(wrapper);
      // 必须清空
      coreInventoryService.setProductAttributeEnumList(null);
      coreInventoryService.setInventoryStatusEnumList(null);
      if (inventoryList.isEmpty()) continue;

      // 按规格分拣
      if (Objects.equals(baseProduct.getIsSpecSorting(), EnableEnum.ENABLE.getId())) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProductSpec(), baseProduct.getProductSpec())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 如果商品设置的是按批次分拣并且订单明细的批次号不为空，则按批次占位
      if (ObjectUtil.equals(baseProduct.getIsBatchNumberSorting(), EnableEnum.ENABLE.getId())) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getBatchNumber(), detail.getBatchNumber())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 商品开启效期排序，生产日期优先有入库日期
      if (Objects.equals(baseProduct.getIsNeedPeriod(), EnableEnum.ENABLE.getId())) {
        // 排序顺序：生产日期、入库日期、生产日期为空排到后面
        inventoryList = inventoryList.stream().sorted(Comparator.comparing(CoreInventoryComposeVo::getProduceDate, Comparator.nullsLast(Date::compareTo)).thenComparing(CoreInventoryComposeVo::getInStorageDate)).toList();
      } else {
        // 排序顺序：生产日期为空、生产日期、入库日期
        inventoryList = inventoryList.stream().sorted(Comparator.comparing(CoreInventoryComposeVo::getProduceDate, Comparator.nullsFirst(Date::compareTo)).thenComparing(CoreInventoryComposeVo::getInStorageDate)).toList();
      }
      //#endregion

      //如果是到货退货单分配库存则不占位 下架理货位
      if (ObjectUtil.equals(holderSourceTypeEnum, HolderSourceTypeEnum.RETURN_ORDER)) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.notEqual(f.getPositionName(), PositionTypeEnum.UNLOADING.getName())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 自定义分拣过滤器
      inventoryList = inventoryBaseService.customSortingFilter(mainInfo, detail, inventoryList);

      // 开始分拣
      this.sortingInventory(mainInfo, detail, inventoryList, InventorySortTypeEnum.SCATTERED_SORTING);
    } // 出库单明细循环完毕
    //#endregion

    /*-- **********************************************
      -- 更新库存明细占位数量及分拣状态
      -- **********************************************/
    // 计算出库单明细缺货数量
    inventoryBaseService.updateLackStorage(detailList);
    boolean isSortingSuccess = false; // 是否分拣成功
    Byte orderStatus = null;
    if (detailList.stream().allMatch(f -> B.isLessOrEqual(f.getLackStorage()))) {
      mainInfo.setSortingStatus(SortingStatusEnum.ASSIGNED.getId());
      // 分拣成功
      inventoryBaseService.updateSortingStatus(mainInfo.getMainId(), SortingStatusEnum.ASSIGNED);
      isSortingSuccess = true;
    } else if (detailList.stream().anyMatch(f -> B.isGreater(f.getLackStorage()))
      && detailList.stream().anyMatch(f -> B.isGreater(f.getOutQuantity(), f.getLackStorage()))) {
      mainInfo.setSortingStatus(SortingStatusEnum.PARTIAL_ASSIGNED.getId());
      // 部分分配
      inventoryBaseService.updateSortingStatus(mainInfo.getMainId(), SortingStatusEnum.PARTIAL_ASSIGNED);
      orderStatus = SortingStatusEnum.PARTIAL_ASSIGNED.getId();
    } else {
      mainInfo.setSortingStatus(SortingStatusEnum.LACK.getId());
      // 缺货
      inventoryBaseService.updateSortingStatus(mainInfo.getMainId(), SortingStatusEnum.LACK);
      orderStatus = SortingStatusEnum.LACK.getId();
    }
    CommonDetailDto outOrderDetail = detailList.stream().filter(f -> B.isGreater(f.getLackStorage())).findFirst().orElse(null);
    String msg = "分拣成功";
    if (ObjectUtil.isNotNull(outOrderDetail)) {
      msg = "分拣失败：" + outOrderDetail.getProductCode() + "缺货！";

      if (ObjectUtil.equals(HolderSourceTypeEnum.WORK_ORDER_PICKING, holderSourceTypeEnum)
        || ObjectUtil.equals(HolderSourceTypeEnum.PRODUCTION_REPLENISHMENT, holderSourceTypeEnum)
      ) {
        CommonDetailDto commonDetailDto = new CommonDetailDto();
        commonDetailDto.setSortingStatus(orderStatus);
        commonDetailDtoList.get().add(commonDetailDto);
        return R.fail(msg, commonDetailDtoList.get());
      }
    }

    return isSortingSuccess ? R.ok(msg, commonDetailDtoList.get()) : R.fail(msg);
  }
  //#endregion

  //#region sortingInventory

  /**
   * 库存分拣
   *
   * @param mainInfo      出库单主表信息
   * @param detail        出库单明细信息
   * @param inventoryList 库存数据集合
   * @param sortType      分拣类型
   */
  private void sortingInventory(CommonMainDto mainInfo, CommonDetailDto detail, List<CoreInventoryComposeVo> inventoryList, InventorySortTypeEnum sortType) {
    BigDecimal lackStorage = detail.getLackStorage();
    BigDecimal validStorage;
    int isFinished = 0;   // 当前订单明细是否分拣完成1=完成，0=未完成
    for (var item : inventoryList) {
      validStorage = item.getValidStorage();
      BigDecimal holderQty;
      if (B.isGreater(validStorage, lackStorage)) {
        holderQty = lackStorage;
        lackStorage = BigDecimal.ZERO;
        isFinished = 1;
      } else {
        holderQty = validStorage;
        lackStorage = B.sub(lackStorage, validStorage);
        if (B.isLessOrEqual(lackStorage)) {
          isFinished = 1;
        }
      }

      // 生成占位
      CoreInventoryHolder holder = coreInventoryHolderService.createHolder(mainInfo, detail, item, holderQty, sortType);

      // 更新缓存中的有效库存
      item.setValidStorage(B.sub(item.getValidStorage(), holderQty));  // 消减有效库存
      var first = inventoryList.stream().filter(f -> Objects.equals(f.getInventoryId(), item.getInventoryId()))
        .findFirst().orElse(null);
      if (ObjectUtil.isNotNull(first)) {
        first.setValidStorage(B.sub(first.getValidStorage(), holderQty)); // 消减有效库存
      }
      detail.setLackStorage(B.sub(detail.getLackStorage(), holderQty)); // 缺货数量计算

      if (isFinished == 1) {
        break;
      }
    }
  }
  //#endregion

  //#region out 扣减库存

  /**
   * 出库 - 扣减库存
   *
   * @param mainInfo   主表明细
   * @param detailList 明细表信息
   */
  @Override
  public R<List<CommonDetailDto>> out(CommonMainDto mainInfo, List<CommonDetailDto> detailList, InventorySourceTypeEnum inventorySourceTypeEnum) {
    commonDetailDtoList.set(new ArrayList<>()); // 初始化
    if (!ObjectUtil.equals(mainInfo.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId())) {
      throw new ServiceException("未分配成功，不允许出库！");
    }
    // 数据验证
    for (var detailInfo : detailList) {
      if (B.isLessOrEqual(detailInfo.getOutQuantity())) continue; // 明细出库数量小于0忽略

      Assert.isFalse(ObjectUtil.isEmpty(detailInfo.getMainId()), "主表ID不能为空！");
      Assert.isFalse(ObjectUtil.isEmpty(detailInfo.getDetailId()), "明细ID不能为空！");
    }

    // 出库操作
    for (CommonDetailDto detailInfo : detailList) {
      BigDecimal outQuantity = detailInfo.getOutQuantity(); // 出库数量
      if (B.isLessOrEqual(outQuantity)) continue; // 明细出库数量小于0忽略

      if (ObjectUtil.isEmpty(detailInfo.getBillCode())) {
        detailInfo.setBillCode(mainInfo.getConsignorCode());
      }
      if (ObjectUtil.isEmpty(detailInfo.getSourceCode())) {
        detailInfo.setSourceCode(mainInfo.getSourceCode());
      }

      Long mainId = detailInfo.getMainId();
      Long detailId = detailInfo.getDetailId();
      Long productId = detailInfo.getProductId();
      BaseProduct productInfo = baseProductService.getById(productId);

      //#region 开始出库单，扣减占位和库存
      LambdaUpdateWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new LambdaUpdateWrapper<>();
      inventoryMPJLambdaWrapper
        .eq(CoreInventoryHolder::getMainId, mainId)
        .eq(CoreInventoryHolder::getDetailId, detailId)
        .in(CoreInventoryHolder::getBillCode, detailInfo.getBillCode());

      if (ObjectUtil.isNotEmpty(detailInfo.getPositionNameOut())) {
        inventoryMPJLambdaWrapper.eq(CoreInventoryHolder::getPositionName, detailInfo.getPositionNameOut());
      }
      if (ObjectUtil.isNotEmpty(detailInfo.getBatchNumber())) {
        inventoryMPJLambdaWrapper.eq(CoreInventoryHolder::getBatchNumber, detailInfo.getBatchNumber());
      }
      if (ObjectUtil.isNotEmpty(detailInfo.getProduceDate())) {
        inventoryMPJLambdaWrapper.eq(CoreInventoryHolder::getProduceDate, detailInfo.getProduceDate());
      }
      if (ObjectUtil.isNotEmpty(detailInfo.getProductSpec())) {
        inventoryMPJLambdaWrapper.eq(CoreInventoryHolder::getProductSpec, detailInfo.getProductSpec());
      }
      if (ObjectUtil.isNotEmpty(detailInfo.getContainerNo())) {
        inventoryMPJLambdaWrapper.eq(CoreInventoryHolder::getContainerNo, detailInfo.getContainerNo());
      }

      List<CoreInventoryHolder> inventoryHolderList = this.coreInventoryHolderService.list(inventoryMPJLambdaWrapper);

      Assert.isFalse(inventoryHolderList.isEmpty(), "出库未获取占位数据");
      for (CoreInventoryHolder holderInfo : inventoryHolderList) {
        if (B.isLessOrEqual(holderInfo.getHolderStorage())) {
          continue;
        }
        if (B.isLessOrEqual(outQuantity)) break;

        //#region 获取出库前：库存数量和重量
        CoreInventory coreInventory = coreInventoryService.getById(holderInfo.getInventoryId());
        BigDecimal beforeQuantity = coreInventory.getProductStorage(); // 保存前数量
        BigDecimal beforeWeight = coreInventory.getRowWeight();   // 保存前重量
        //#endregion

        BigDecimal inQuantity; // 扣减的占位数量，也就是给下一步入库的数量
        //#region 扣减占位、扣减库存
        if (B.isGreaterOrEqual(holderInfo.getHolderStorage(), outQuantity)) {
          inQuantity = outQuantity;
          // 扣减占位
          holderInfo.setHolderStorage(B.sub(holderInfo.getHolderStorage(), outQuantity));
          coreInventoryHolderService.getBaseMapper().updateById(holderInfo);
          // 扣减库存
          coreInventoryService.subInventory(holderInfo.getInventoryId(), outQuantity);
          // 扣减出库数量
          outQuantity = BigDecimal.ZERO;
        } else {
          inQuantity = holderInfo.getHolderStorage();
          // 扣减库存
          coreInventoryService.subInventory(holderInfo.getInventoryId(), holderInfo.getHolderStorage());
          // 扣减占位
          holderInfo.setHolderStorage(BigDecimal.ZERO);
          coreInventoryHolderService.getBaseMapper().updateById(holderInfo);
          // 扣减出库数量
          outQuantity = B.sub(outQuantity, inQuantity);
        }
        // 更新库存占位数据
        coreInventoryService.updateHolderStorage(holderInfo.getInventoryId());
        //endregion

        var inventoryItem = coreInventoryService.getInventoryComposeVo(holderInfo.getInventoryId());
        // 构建占位明细作为入库明细使用，返回到下一步入库
        this.addCommonDetailDto(inventoryItem, detailInfo, inQuantity);

        //#region 新增库存轨迹
        // 获取出库后：库存数量和重量
        coreInventory = coreInventoryService.getById(holderInfo.getInventoryId());
        BigDecimal afterQuantity = coreInventory.getProductStorage();
        BigDecimal afterWeight = coreInventory.getRowWeight();

        // 实例化轨迹
        BeanUtil.copyProperties(mainInfo, detailInfo, CopyOptions.create().setIgnoreNullValue(true));
        BeanUtil.copyProperties(holderInfo, detailInfo, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("singleSignCode"));
        detailInfo.setOutQuantity(inQuantity);
        detailInfo.setPositionNameOut(holderInfo.getPositionName());
        if (ObjectUtil.isEmpty(detailInfo.getSourceCode())) {
          detailInfo.setSourceCode(detailInfo.getBillCode()); // 如果来源单号为空，默认为执行单号
        }
        detailInfo.setSourceCode2(mainInfo.getSourceCode());
        detailInfo.setBigQty(B.div(detailInfo.getOutQuantity(), productInfo.getUnitConvert()));

        // 新增库存轨迹
        coreInventoryHistoryService.addHistory(HistoryTypeEnum.OUT, productInfo, detailInfo, inventorySourceTypeEnum, beforeQuantity, beforeWeight, afterQuantity, afterWeight);
        //#endregion
      }
      //#endregion
    }

    return R.ok(commonDetailDtoList.get());
  }
  //#endregion

  //#region 入库 - 增加库存

  /**
   * 入库 - 增加库存
   *
   * @param mainInfo   主表明细
   * @param detailList 明细表信息
   * @return
   */
  @Override
  public R<List<CommonDetailDto>> in(CommonMainDto mainInfo, List<CommonDetailDto> detailList, InventorySourceTypeEnum inventorySourceTypeEnum) {
    commonDetailDtoList.set(new ArrayList<>()); // 初始化

    // 数据验证
    for (var detailInfo : detailList) {
      if (B.isLessOrEqual(detailInfo.getInQuantity())) continue; // 明细入库数量小于0忽略

      Assert.isFalse(ObjectUtil.isEmpty(detailInfo.getMainId()), "主表ID不能为空！");
      Assert.isFalse(ObjectUtil.isEmpty(detailInfo.getDetailId()), "明细ID不能为空！");
      Assert.isFalse(ObjectUtil.isEmpty(detailInfo.getPositionNameIn()), "入库货位不能为空！");
    }

    // 入库操作
    for (var detailInfo : detailList) {
      if (B.isLessOrEqual(detailInfo.getInQuantity())) continue; // 明细入库数量小于0忽略
      // 如果明细仓库为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getStorageId())) {
        detailInfo.setStorageId(mainInfo.getStorageId());
        detailInfo.setStorageName(mainInfo.getStorageName());
      }
      // 如果明细中的目标货主有值有限使用目标货主
      if (ObjectUtil.isNotEmpty(detailInfo.getConsignorIdTarget())) {
        detailInfo.setConsignorId(detailInfo.getConsignorIdTarget());
        detailInfo.setConsignorCode(detailInfo.getConsignorCodeTarget());
        detailInfo.setConsignorName(detailInfo.getConsignorNameTarget());
      }
      // 如果明细货主为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getConsignorId())) {
        detailInfo.setConsignorId(mainInfo.getConsignorId());
        detailInfo.setConsignorCode(mainInfo.getConsignorCode());
        detailInfo.setConsignorName(mainInfo.getConsignorName());
      }
      // 如果明细供应商为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getProviderId())) {
        detailInfo.setProviderId(mainInfo.getProviderId());
        detailInfo.setProviderCode(mainInfo.getProviderCode());
        detailInfo.setProviderShortName(mainInfo.getProviderShortName());
      }
      // 如果明细集装箱号为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getContainerNo())) {
        detailInfo.setContainerNo(mainInfo.getContainerNo()); // 集装箱号
      }
      // 如果明细执行单号为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getBillCode())) {
        detailInfo.setBillCode(mainInfo.getMainCode()); // 执行单号
      }
      // 如果明细来源单号为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getSourceCode())) {
        detailInfo.setSourceCode(mainInfo.getMainCode()); // 来源单号
      }
      // 如果明细来源单号2为空，默认为主表的
      if (ObjectUtil.isEmpty(detailInfo.getSourceCode2())) {
        detailInfo.setSourceCode2(mainInfo.getSourceCode()); // 集装箱号
      }

      BaseProduct productInfo = baseProductService.getById(detailInfo.getProductId());
      // 货位信息
      BasePosition basePosition = basePositionService.getByName(detailInfo.getStorageId(), detailInfo.getPositionNameIn());
      Assert.isFalse(ObjectUtil.isEmpty(basePosition), detailInfo.getPositionNameIn() + "货位不存在！");

      //#region 获取入库前：库存数量和重量
      BigDecimal beforeQuantity = BigDecimal.ZERO; // 保存前数量
      BigDecimal beforeWeight = BigDecimal.ZERO;   // 保存前重量
      //#endregion

      //#region 增加库存
      var coreInventory = coreInventoryService.addInventory(productInfo, basePosition, detailInfo, inventorySourceTypeEnum);
      var inventoryItem = coreInventoryService.getInventoryComposeVo(coreInventory.getInventoryId());
      // 返回入库数据
      detailInfo.setInventoryId(coreInventory.getInventoryId()); // 更新为新库存ID
      this.addCommonDetailDto(inventoryItem, detailInfo, coreInventory.getProductStorage());
      //#endregion

      //#region 生成入库轨迹数据
      // 获取入库后：库存数量和重量
      BigDecimal afterQuantity = coreInventory.getProductStorage();
      BigDecimal afterWeight = coreInventory.getRowWeight();

      // 新增库存轨迹
      detailInfo.setInventoryId(coreInventory.getInventoryId());
      coreInventoryHistoryService.addHistory(HistoryTypeEnum.IN, productInfo, detailInfo, inventorySourceTypeEnum, beforeQuantity, beforeWeight, afterQuantity, afterWeight);
      //#endregion

    }

    return R.ok(commonDetailDtoList.get());
  }
  //#endregion

  //#region 构建占位明细作为入库明细使用

  /**
   * 构建占位明细作为入库明细使用，返回到下一步入库
   *
   * @param inventoryItem 库存明细
   * @param detailInfo    单据明细
   * @param inQuantity    给到入库时的数量
   */
  private void addCommonDetailDto(CoreInventoryComposeVo inventoryItem, CommonDetailDto detailInfo, BigDecimal inQuantity) {
    CommonDetailDto commonDetailDto = BeanUtil.copyProperties(inventoryItem, CommonDetailDto.class);
    BeanUtil.copyProperties(detailInfo, commonDetailDto, CopyOptions.create().setIgnoreNullValue(true));

    commonDetailDto.setInQuantity(inQuantity);
    commonDetailDto.setPurchaseAmount(B.mul(commonDetailDto.getPurchasePrice(), commonDetailDto.getInQuantity()));
    commonDetailDto.setRateAmount(B.mul(commonDetailDto.getRatePrice(), commonDetailDto.getInQuantity()));
    commonDetailDto.setRowWeight(B.mul(commonDetailDto.getWeight(), commonDetailDto.getInQuantity()));
    commonDetailDto.setRowNetWeight(B.mul(commonDetailDto.getNetWeight(), commonDetailDto.getInQuantity()));
    commonDetailDto.setRowCube(B.mul(commonDetailDto.getUnitCube(), commonDetailDto.getInQuantity()));
    commonDetailDtoList.get().add(commonDetailDto);
  }
  //endregion
}
