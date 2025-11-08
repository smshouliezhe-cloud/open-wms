package com.yiruantong.inventory.service.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySortTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryHolderBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHolderComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHolderVo;
import com.yiruantong.inventory.mapper.core.CoreInventoryHolderMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 库存占位查询(异常)Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@RequiredArgsConstructor
@Service
public class CoreInventoryHolderServiceImpl extends ServiceImplPlus<CoreInventoryHolderMapper, CoreInventoryHolder, CoreInventoryHolderVo, CoreInventoryHolderBo> implements ICoreInventoryHolderService {
  private final IDataAuthService dataAuthService;

  //#region 清除占位
  @Override
  public void clearHolder(List<HolderSourceTypeEnum> sourceTypeEnumList, Long mainId) {
    LambdaUpdateWrapper<CoreInventoryHolder> holderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    holderLambdaUpdateWrapper.in(CoreInventoryHolder::getSourceType, sourceTypeEnumList.stream().map(HolderSourceTypeEnum::getName).toList())
      .eq(CoreInventoryHolder::getMainId, mainId);
    List<CoreInventoryHolder> holderList = this.getBaseMapper().selectList(holderLambdaUpdateWrapper);

    boolean result = this.remove(holderLambdaUpdateWrapper);
    // 更新库存占位数量
    ICoreInventoryService coreInventoryService = SpringUtils.getBean(ICoreInventoryService.class);
    for (var holder : holderList) {
      coreInventoryService.updateHolderStorage(holder.getInventoryId());
    }
  }
  //#endregion

  //#region 获取占位数据
  @Override
  public BigDecimal getPlaceholderStorage(Long mainId, Long deailId, HolderSourceTypeEnum... holderSourceTypeEnums) {
    MPJLambdaWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new MPJLambdaWrapper<>();
    inventoryMPJLambdaWrapper.selectSum(CoreInventoryHolder::getHolderStorage)
      .eq(CoreInventoryHolder::getMainId, mainId)
      .eq(CoreInventoryHolder::getDetailId, deailId)
      .in(CoreInventoryHolder::getSourceType, Arrays.stream(holderSourceTypeEnums).map(HolderSourceTypeEnum::getName).toArray());
    Map<String, Object> joinMap = this.selectJoinMap(inventoryMPJLambdaWrapper);
    return Optional.ofNullable(joinMap).map(m -> Convert.toBigDecimal(m.get("holderStorage"))).orElse(BigDecimal.ZERO);
  }


  //#region 获取占位数据
  @Override
  public BigDecimal getPlaceholderStorage(Long mainId, Long deailId, String batchNumber, HolderSourceTypeEnum... holderSourceTypeEnums) {
    MPJLambdaWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new MPJLambdaWrapper<>();
    inventoryMPJLambdaWrapper.selectSum(CoreInventoryHolder::getHolderStorage)
      .eq(CoreInventoryHolder::getMainId, mainId)
      .eq(CoreInventoryHolder::getDetailId, deailId)
      .eq(CoreInventoryHolder::getBatchNumber, batchNumber)
      .in(CoreInventoryHolder::getSourceType, Arrays.stream(holderSourceTypeEnums).map(HolderSourceTypeEnum::getName).toArray());
    Map<String, Object> joinMap = this.selectJoinMap(inventoryMPJLambdaWrapper);
    return Optional.ofNullable(joinMap).map(m -> Convert.toBigDecimal(m.get("holderStorage"))).orElse(BigDecimal.ZERO);
  }

  //#region 获取占位数据
  @Override
  public BigDecimal getPlaceholderStorage(Long inventoryId) {
    MPJLambdaWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new MPJLambdaWrapper<>();
    inventoryMPJLambdaWrapper.selectSum(CoreInventoryHolder::getHolderStorage)
      .eq(CoreInventoryHolder::getInventoryId, inventoryId);
    Map<String, Object> joinMap = this.selectJoinMap(inventoryMPJLambdaWrapper);
    return Optional.ofNullable(joinMap).map(m -> Convert.toBigDecimal(m.get("holderStorage"))).orElse(BigDecimal.ZERO);
  }
  //#endregion

  //#region 获取占位集合信息
  @Override
  public List<CoreInventoryHolder> selectHolderList(Long mainId, Long deailId, HolderSourceTypeEnum... holderSourceTypeEnums) {
    LambdaUpdateWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new LambdaUpdateWrapper<>();
    inventoryMPJLambdaWrapper
      .eq(CoreInventoryHolder::getMainId, mainId)
      .eq(ObjectUtil.isNotNull(deailId), CoreInventoryHolder::getDetailId, deailId)
      .in(CoreInventoryHolder::getSourceType, Arrays.stream(holderSourceTypeEnums).map(HolderSourceTypeEnum::getName).toArray());
    return this.baseMapper.selectList(inventoryMPJLambdaWrapper);
  }

  @Override
  public List<CoreInventoryHolder> selectHolderList(Long mainId, HolderSourceTypeEnum... holderSourceTypeEnums) {
    return selectHolderList(mainId, null, holderSourceTypeEnums);
  }
  //#endregion


  //#region 获取占位集合信息
  @Override
  public List<CoreInventoryHolder> selectHolderList(Long mainId, Long deailId, String billCode) {
    LambdaUpdateWrapper<CoreInventoryHolder> inventoryMPJLambdaWrapper = new LambdaUpdateWrapper<>();
    inventoryMPJLambdaWrapper
      .eq(CoreInventoryHolder::getMainId, mainId)
      .eq(CoreInventoryHolder::getDetailId, deailId)
      .in(CoreInventoryHolder::getBillCode, billCode);
    return this.baseMapper.selectList(inventoryMPJLambdaWrapper);
  }
  //#endregion

  /**
   * 生成占位
   *
   * @param mainInfo 出库单主表信息
   * @param detail   出库单明细信息
   */
  public CoreInventoryHolder createHolder(CommonMainDto mainInfo, CommonDetailDto detail, CoreInventoryComposeVo inventoryItem, BigDecimal holderQty, InventorySortTypeEnum inventorySortTypeEnum) {
    Assert.isFalse(B.isLessOrEqual(holderQty), "占位数量必须大于0！");

    CoreInventoryHolder inventoryHolder = new CoreInventoryHolder();
    BeanUtil.copyProperties(mainInfo, inventoryHolder, CopyOptions.create().setIgnoreNullValue(true));
    BeanUtil.copyProperties(detail, inventoryHolder, CopyOptions.create().setIgnoreNullValue(true));
    if (ObjectUtil.isNotEmpty(inventoryItem)) {
      BeanUtil.copyProperties(inventoryItem, inventoryHolder, CopyOptions.create().setIgnoreNullValue(true));
    }
    inventoryHolder.setMainId(detail.getMainId());
    inventoryHolder.setDetailId(detail.getDetailId());
    inventoryHolder.setBillCode(detail.getBillCode());
    if (ObjectUtil.isEmpty(inventoryHolder.getBillCode())) {
      inventoryHolder.setBillCode(mainInfo.getMainCode());
    }
    if (ObjectUtil.isNotEmpty(detail.getPositionNameOut())) {
      inventoryHolder.setPositionName(detail.getPositionNameOut());
    } else if (ObjectUtil.isNotEmpty(detail.getPositionNameIn())) {
      inventoryHolder.setPositionName(detail.getPositionNameIn());
    }
    inventoryHolder.setHolderStorage(holderQty);
    inventoryHolder.setOrignHolderStorage(holderQty);
    if (inventorySortTypeEnum == InventorySortTypeEnum.FULL_CONTAINER_SORTING) {
      inventoryHolder.setIsFullContainerLoad(EnableEnum.ENABLE.getId());
    } else {
      inventoryHolder.setIsFullContainerLoad(EnableEnum.DISABLE.getId());
    }
    inventoryHolder.setRowWeight(B.mul(detail.getWeight(), holderQty));
    inventoryHolder.setNetWeight(B.mul(detail.getNetWeight(), holderQty));
    Assert.isFalse(ObjectUtil.isEmpty(mainInfo.getHolderSourceType()), detail.getProductCode() + "主表占位来源类型不能为空！");
    inventoryHolder.setSourceType(mainInfo.getHolderSourceType().getName()); // 占位类型

    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getInventoryId()), detail.getProductCode() + "占位库存ID不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getMainId()), detail.getProductCode() + "占位主表ID不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getDetailId()), detail.getProductCode() + "占位明细ID不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getConsignorId()), detail.getProductCode() + "占位货主不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getStorageId()), detail.getProductCode() + "占位仓库不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getProductId()), detail.getProductCode() + "占位商品不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getPositionName()), detail.getProductCode() + "占位货位不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getProviderId()), detail.getProductCode() + "占位供应商不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getSourceType()), detail.getProductCode() + "占位来源类型不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(inventoryHolder.getBillCode()), detail.getProductCode() + "占位来源单号不能为空！");
    inventoryHolder.setHolderId(null);

    // 保存占位
    this.save(inventoryHolder);
    // 更新库存占位数据
    ICoreInventoryService coreInventoryService = SpringUtils.getBean(ICoreInventoryService.class);
    coreInventoryService.updateHolderStorage(inventoryHolder.getInventoryId());

    return inventoryHolder;
  }

  @Override
  public void createHolder(CommonMainDto mainInfo, CommonDetailDto detail, BigDecimal holderQty, InventorySortTypeEnum inventorySortTypeEnum) {
    this.createHolder(mainInfo, detail, null, holderQty, inventorySortTypeEnum);
  }

  public CoreInventoryHolder createHolder(CommonMainDto mainInfo, CommonDetailDto detail) {
    BigDecimal holderQty = detail.getInQuantity();
    return this.createHolder(mainInfo, detail, null, holderQty, InventorySortTypeEnum.SCATTERED_SORTING);
  }

  ;

  // 删除前事件
  @Override
  public int deleteByIds(Long[] ids) {
    LambdaUpdateWrapper<CoreInventoryHolder> holderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    holderLambdaUpdateWrapper.in(CoreInventoryHolder::getHolderId, Arrays.asList(ids));
    List<CoreInventoryHolder> holderList = this.getBaseMapper().selectList(holderLambdaUpdateWrapper);

    this.remove(holderLambdaUpdateWrapper);
    // 更新库存占位数量
    ICoreInventoryService coreInventoryService = SpringUtils.getBean(ICoreInventoryService.class);
    for (var holder : holderList) {
      coreInventoryService.updateHolderStorage(holder.getInventoryId());
    }


    return 1;
  }

  //#region 获取库存占位信息，查询列表页面
  @Override
  public TableDataInfo<CoreInventoryHolderComposeVo> selectInventoryComposeHolderList(PageQuery pageQuery) {
    IPage<CoreInventoryHolderComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<CoreInventoryHolder> wrapper = new MPJLambdaWrapper<CoreInventoryHolder>()
      .selectAll(CoreInventoryHolder.class);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventoryHolder.class, BasePosition.class, BaseProduct.class);

    IPage<CoreInventoryHolderComposeVo> page = this.selectJoinListPage(ipage, CoreInventoryHolderComposeVo.class, wrapper);
    TableDataInfo<CoreInventoryHolderComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    //#region 计算合计数量
    if (!pageQuery.getSumColumnNames().isEmpty()) {
      MPJLambdaWrapper<CoreInventoryHolder> inventoryMpjLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        inventoryMpjLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      // 拼接查询条件
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), inventoryMpjLambdaWrapper, CoreInventoryHolder.class, BasePosition.class, BaseProduct.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(inventoryMpjLambdaWrapper);
      tableDataInfoV.setFooter(maps);
    }
    //#endregion
    return tableDataInfoV;
  }
  //#endregion
}
