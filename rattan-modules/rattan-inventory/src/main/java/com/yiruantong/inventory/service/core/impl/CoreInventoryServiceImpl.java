package com.yiruantong.inventory.service.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.service.client.IBaseClientAddressService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.constant.InConstants;
import com.yiruantong.common.core.constant.InventoryConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InCheckingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.dto.SumColumnNameBo;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryBo;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySearchBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryCreateCheckVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryReplenishmentVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryVo;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.mapper.core.CoreInventoryMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentDetailService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 库存调整选择器Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@RequiredArgsConstructor
@Service
public class CoreInventoryServiceImpl extends ServiceImplPlus<CoreInventoryMapper, CoreInventory, CoreInventoryVo, CoreInventoryBo> implements ICoreInventoryService {
  private final IBasePositionService basePositionService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IBaseStorageService baseStorageService;
  private final ISysConfigService sysConfigService;
  private final IBaseProductService baseProductService;
  private final IDataAuthService dataAuthService;
  private final IStorageReplenishmentDetailService storageReplenishmentDetailService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseClientAddressService baseClientAddressService;

  /**
   * 库存状态分拣类型
   */
  private final ThreadLocal<List<InventoryStatusEnum>> inventoryStatusEnumList = new TransmittableThreadLocal<>();
  /**
   * 商品属性
   */
  private final ThreadLocal<List<InventoryStatusEnum>> productAttributeEnumList = new TransmittableThreadLocal<>();

  /**
   * 库存状态货位类型
   */
  private final ThreadLocal<List<PositionTypeEnum>> positionTypeEnumList = new TransmittableThreadLocal<>();

  /**
   * 设置可以分拣的库存状态
   *
   * @param inventoryStatusEnumList 可分拣的库存状态
   */
  @Override
  public void setInventoryStatusEnumList(List<InventoryStatusEnum> inventoryStatusEnumList) {
    this.inventoryStatusEnumList.set(inventoryStatusEnumList);
  }

  /**
   * 设置可分拣的库存属性
   *
   * @param productAttributeEnumList 库存属性
   * @return 返回查询列表数据
   */
  @Override
  public void setProductAttributeEnumList(List<InventoryStatusEnum> productAttributeEnumList) {
    this.productAttributeEnumList.set(productAttributeEnumList);
  }

  /**
   * 设置可以分拣的货位类型
   *
   * @param positionTypeEnumList 可分拣的货位类型
   */
  @Override
  public void setPositionTypeEnumList(List<PositionTypeEnum> positionTypeEnumList) {
    this.positionTypeEnumList.set(positionTypeEnumList);
  }

  //#region 获取仓库货位库存量
  @Override
  public BigDecimal getPositionStorage(Long storageId, String positionName) {
    // 获取当前货位库存数量
    QueryWrapper<CoreInventory> inventoryWrapper = new QueryWrapper<>();
    inventoryWrapper.select("SUM(product_storage) as productStorage")
      .lambda().eq(CoreInventory::getStorageId, storageId)
      .eq(CoreInventory::getPositionName, positionName);
    Map<String, Object> productStorageMap = this.getMap(inventoryWrapper);

    return Optional.ofNullable(productStorageMap).map(m -> Convert.toBigDecimal(productStorageMap.get("productStorage"))).orElse(BigDecimal.ZERO);
  }
  //#endregion

  //#region 获取货位拍数
  @Override
  public Long getPlateCount(Long storageId, String positionName) {
    // 获取当前货位内已有托盘数
    QueryWrapper<CoreInventory> inventoryWrapper = new QueryWrapper<>();
    inventoryWrapper.select("COUNT(DISTINCT plate_code) as plateCount").lambda()
      .eq(CoreInventory::getStorageId, storageId)
      .eq(CoreInventory::getPositionName, positionName);
    Map<String, Object> paiCountMap = this.getMap(inventoryWrapper);

    return Convert.toLong(paiCountMap.get("plateCount"));
  }
  //#endregion

  //#region 判断库存存在混物料，同货位只能存放同一种商品
  @Override
  public boolean isMixProduct(Long storageId, Long productId, String positionName) {
    BasePosition basePosition = basePositionService.getByName(storageId, positionName);
    if (NumberUtil.equals(basePosition.getIsMixProduct(), EnableEnum.DISABLE.getId())) {
      // 货位不允许混物料
      QueryWrapper<CoreInventory> inventoryQueryWrapper = new QueryWrapper<>();
      inventoryQueryWrapper
        .select("COUNT(DISTINCT product_id) AS productCount")
        .lambda().eq(CoreInventory::getStorageId, storageId)
        .eq(CoreInventory::getPositionName, positionName)
        .gt(CoreInventory::getProductStorage, 0);
      var inventoryMap = this.getMap(inventoryQueryWrapper);
      Long productCount = Convert.toLong(inventoryMap.get("productCount"));
      if (NumberUtil.compare(productCount, 1) > 0) {
        return false; // 同货位存在两个以上的商品，不允许混物料
      }

      // 判断货位存在的商品ID和当前需要入库的商品ID是否相同
      inventoryQueryWrapper = new QueryWrapper<>();
      inventoryQueryWrapper
        .select("DISTINCT product_id AS productId")
        .lambda().eq(CoreInventory::getStorageId, storageId)
        .eq(CoreInventory::getPositionName, positionName)
        .gt(CoreInventory::getProductStorage, 0);
      var productIdMap = this.getMap(inventoryQueryWrapper);
      Long _productId = Convert.toLong(productIdMap.get("productId"));
      return NumberUtil.equals(productId, _productId); // 判断货位存在的商品ID和当前需要入库的商品ID不相同，不允许混物料
    }
    return true;
  }
  //#endregion

  //#region 获取库存求和
  @Override
  public Map<String, Object> getSumData(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));
    Long productId = Convert.toLong(map.get("productId"));
    String positionName = Convert.toStr(map.get("positionName"));
    String productSpec = Convert.toStr(map.get("productSpec"));
    String batchNumber = Convert.toStr(map.get("batchNumber"));
    Date produceDate = Convert.toDate(map.get("produceDate"));
    String plateCode = Convert.toStr(map.get("plateCode"));

    QueryWrapper<CoreInventory> inventoryQueryWrapper = new QueryWrapper<>();
    inventoryQueryWrapper.select("SUM(product_storage) as productStorage", "SUM(row_weight) AS rowWeight").lambda()
      .eq(CoreInventory::getStorageId, storageId)
      .eq(CoreInventory::getConsignorId, consignorId)
      .eq(CoreInventory::getProductId, productId)
      .eq(CoreInventory::getPositionName, positionName)
      .eq(StringUtils.isNotEmpty(productSpec), CoreInventory::getProductSpec, productSpec)
      .eq(StringUtils.isNotEmpty(batchNumber), CoreInventory::getBatchNumber, batchNumber)
      .eq(ObjectUtil.isNotEmpty(produceDate), CoreInventory::getProduceDate, produceDate)
      .eq(StringUtils.isNotEmpty(plateCode), CoreInventory::getPlateCode, plateCode);
    return this.getMap(inventoryQueryWrapper);
  }
  //#endregion

  //#region 获取库存组合信息，查询列表页面
  @Override
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryComposeList(PageQuery pageQuery) {
    IPage<CoreInventoryComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限

    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .selectAll(CoreInventory.class)
      .select(BasePosition::getAreaCode, BasePosition::getPositionType, BasePosition::getChannelCode)
      // 商品信息字段
      .select(BaseProduct::getBigUnit, BaseProduct::getTypeName, BaseProduct::getBrandName, BaseProduct::getProductBarCode, BaseProduct::getSmallUnit, BaseProduct::getUnitConvert)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .innerJoin(BaseProduct.class, on -> {
        on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
        return on;
      });

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);

    IPage<CoreInventoryComposeVo> page = this.selectJoinListPage(ipage, CoreInventoryComposeVo.class, wrapper);
    TableDataInfo<CoreInventoryComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    //#region 计算合计数量
    if (pageQuery.getSumColumnNames() != null && !pageQuery.getSumColumnNames().isEmpty()) {
      MPJLambdaWrapper<CoreInventory> inventoryMpjLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        inventoryMpjLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      inventoryMpjLambdaWrapper
        .innerJoin(BasePosition.class, on -> {
          on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
            .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
          return on;
        })
        .innerJoin(BaseProduct.class, on -> {
          on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
          return on;
        });
      // 拼接查询条件
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), inventoryMpjLambdaWrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(inventoryMpjLambdaWrapper);
      tableDataInfoV.setFooter(maps);
    }
    //#endregion

    return tableDataInfoV;
  }
  //#endregion

  //#region 获取单条库存组合信息
  @Override
  public CoreInventoryComposeVo getInventoryComposeVo(Long inventoryId) {
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
    wrapper.selectAll(CoreInventory.class)
      .select(BaseProduct::getSalePrice)
      .select(BasePosition::getAreaCode, BasePosition::getPositionType, BasePosition::getChannelCode, BasePosition::getChannelCode,
        BasePosition::getColumnCode, BasePosition::getRowCode, BasePosition::getIsFreeze, BasePosition::getIsMixProduct, BasePosition::getIsLocked,
        BasePosition::getLineCode)
      .selectAs(BaseStorageArea::getOrderNum, CoreInventoryComposeVo::getAreaOrderNum)  // 库存排序号
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .leftJoin(BaseStorageArea.class, on -> {
        on.eq(BaseStorageArea::getStorageId, BasePosition::getStorageId)
          .eq(BaseStorageArea::getAreaCode, BasePosition::getAreaCode);
        return on;
      })
      .eq(CoreInventory::getInventoryId, inventoryId);

    return this.selectJoinOne(CoreInventoryComposeVo.class, wrapper);
  }
  //#endregion

  //#region 获取有效库存列表 selectValidInventoryList
  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(MPJLambdaWrapper<CoreInventory> wrapper) {
    if (CollUtil.isEmpty(inventoryStatusEnumList.get())) {
      inventoryStatusEnumList.set(CollUtil.newArrayList(InventoryStatusEnum.NORMAL)); // 库存状态默认正常
    }
    if (CollUtil.isEmpty(productAttributeEnumList.get())) {
      productAttributeEnumList.set(CollUtil.newArrayList(InventoryStatusEnum.NORMAL)); // 商品属性默认正常
    }

    wrapper.selectAll(CoreInventory.class)
      .select(BaseProduct::getSalePrice)
      .select(BasePosition::getAreaCode, BasePosition::getPositionType, BasePosition::getChannelCode, BasePosition::getChannelCode,
        BasePosition::getColumnCode, BasePosition::getRowCode, BasePosition::getIsFreeze, BasePosition::getIsMixProduct, BasePosition::getIsLocked,
        BasePosition::getLineCode)
      .selectAs(BaseStorageArea::getOrderNum, CoreInventoryComposeVo::getAreaOrderNum)  // 库存排序号
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .leftJoin(BaseStorageArea.class, on -> {
        on.eq(BaseStorageArea::getStorageId, BasePosition::getStorageId)
          .eq(BaseStorageArea::getAreaCode, BasePosition::getAreaCode);
        return on;
      })
      .in(CoreInventory::getStorageStatus, inventoryStatusEnumList.get().stream().map(InventoryStatusEnum::getName).toArray())
      .in(CoreInventory::getProductAttribute, productAttributeEnumList.get().stream().map(InventoryStatusEnum::getName).toArray())
      .eq(BasePosition::getEnable, EnableEnum.ENABLE.getId()) // 货位必须可用
      .eq(BasePosition::getIsLocked, EnableEnum.DISABLE.getId()) // 排除所得货位，1=锁定，0=未锁定
      .gt(CoreInventory::getValidStorage, BigDecimal.ZERO)
      .orderByAsc(CoreInventory::getProduceDate)
      .orderByAsc(CoreInventory::getInStorageDate)
      .orderByAsc(CoreInventory::getInventoryId)
      .last("limit 500");

    return this.selectJoinList(CoreInventoryComposeVo.class, wrapper);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long consignorId, Long productId, String positionName, List<Byte> positionTypes) {
    // 如果货位类型为空的时候，默认走仓库设置的货位类型
    if (Optional.ofNullable(positionTypes).isEmpty()) {
      if (CollUtil.isEmpty(this.positionTypeEnumList.get())) {
        // 获取可分拣货位类型
        positionTypes = this.getPositionTypes(storageId);
      } else {
        positionTypes = this.positionTypeEnumList.get().stream().map(PositionTypeEnum::getId).toList();
      }
    }

    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
    wrapper.eq(CoreInventory::getStorageId, storageId)
      .eq(ObjectUtil.isNotEmpty(consignorId), CoreInventory::getConsignorId, consignorId)
      .eq(ObjectUtil.isNotEmpty(positionName), CoreInventory::getPositionName, positionName)
      .eq(ObjectUtil.isNotEmpty(productId), CoreInventory::getProductId, productId)
      .in(BasePosition::getPositionType, positionTypes); // 货位类型
    return this.selectValidInventoryList(wrapper);
  }


  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long consignorId, Long productId, String positionName) {
    return this.selectValidInventoryList(storageId, consignorId, productId, positionName, null);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long productId, String positionName) {
    return this.selectValidInventoryList(storageId, null, productId, positionName, null);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, String productModel, String positionName) {
    BaseProduct byModel = baseProductService.getByModel(productModel);
    Assert.isFalse(ObjectUtil.isNull(byModel), "商品条码不存在！");
    return this.selectValidInventoryList(storageId, null, byModel.getProductId(), positionName, null);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, String positionName) {
    return this.selectValidInventoryList(storageId, null, null, positionName, null);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long consignorId, Long productId) {
    return this.selectValidInventoryList(storageId, null, productId, null);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long productId) {
    return this.selectValidInventoryList(storageId, null, productId);
  }

  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId) {
    return this.selectValidInventoryList(storageId, null, null, null);
  }
  //#endregion

  //#region 增加库存
  @Override
  public CoreInventory addInventory(BaseProduct productInfo, BasePosition basePosition, CommonDetailDto commonDetailDto, InventorySourceTypeEnum inventorySourceTypeEnum) {
    CoreInventory coreInventory = BeanUtil.copyProperties(commonDetailDto, CoreInventory.class);

    String batchNumber = commonDetailDto.getBatchNumber();
    String plateCode = commonDetailDto.getPlateCode();
    BigDecimal shelfLifeDay = commonDetailDto.getShelfLifeDay(); // 保质期天数
    BigDecimal inQuantity = commonDetailDto.getInQuantity(); // 扫描完
    Date produceDate = commonDetailDto.getProduceDate(); // 生产日期
    Assert.isFalse(B.isLessOrEqual(inQuantity), "入库数量必须大于0");

    if (inventorySourceTypeEnum == InventorySourceTypeEnum.PC_ALLOCATE_ROUTE_VIRTUAL_IN) {
      coreInventory.setStorageStatus(InventoryStatusEnum.ALLOCATE_ROUTE.getName());
    } else if (Objects.equals(basePosition.getPositionType(), PositionTypeEnum.RECEIVING.getId())) {
      coreInventory.setStorageStatus(InventoryStatusEnum.TALLY_WAITING.getName());
    } else if (Objects.equals(basePosition.getPositionName(), InventoryConstants.NO_STOCK)) {
      coreInventory.setStorageStatus(InventoryStatusEnum.ZERO_INVENTORY.getName());
    } else if (ObjectUtil.isNotEmpty(coreInventory.getStorageStatus())) {
      coreInventory.setStorageStatus(coreInventory.getStorageStatus());
    } else if (inventorySourceTypeEnum == InventorySourceTypeEnum.OUT_SPILLOVER_IN) { // 溢出
      coreInventory.setStorageStatus(InventoryStatusEnum.SPILLOVER.getName());
    } else {
      coreInventory.setStorageStatus(InventoryStatusEnum.NORMAL.getName());
    }

    // 质量异议，库存状态优先级最高
    int dissent = Optional.ofNullable(commonDetailDto.getExpandFields()).map(m -> Convert.toInt(m.get(InConstants.DISSENT))).orElse(0);
    if (B.isEqual(dissent, NumberUtils.ONE)) {
      coreInventory.setStorageStatus(InventoryStatusEnum.LOCKED.getName());
    }

    // 当来源单号为空时，默认为执行单号
    if (ObjectUtil.isEmpty(coreInventory.getSourceCode())) {
      coreInventory.setSourceCode(coreInventory.getBillCode()); // 来源单号
    }
    if (ObjectUtil.isEmpty(coreInventory.getImages())) {
      coreInventory.setImages(productInfo.getImages()); // 图片
    }
    if (ObjectUtil.isEmpty(coreInventory.getOriginPlace())) {
      coreInventory.setOriginPlace(productInfo.getOriginPlace()); // 原产地
    }
    coreInventory.setBigQty(B.div(inQuantity, productInfo.getUnitConvert())); // 大单位数量

    if (commonDetailDto.getCheckingStatusEnum() == InCheckingStatusEnum.UNCHECKED) {
      // 上架时如果质检为未质检，库存状态标识为未质检
      coreInventory.setProductAttribute(InventoryStatusEnum.UNCHECKED.getName());
    } else if (ObjectUtil.isEmpty(coreInventory.getProductAttribute())) {
      coreInventory.setProductAttribute(InventoryStatusEnum.NORMAL.getName());
    }

    coreInventory.setSourceType(inventorySourceTypeEnum.getName()); // 来源类型
    if (ObjectUtil.isEmpty(coreInventory.getInStorageDate())) {
      coreInventory.setInStorageDate(DateUtil.date()); // 入库日期
    }
    coreInventory.setPositionName(basePosition.getPositionName());
    coreInventory.setProductStorage(inQuantity);
    coreInventory.setOriginStorage(inQuantity);
    coreInventory.setWeight(productInfo.getWeight());
    coreInventory.setValidStorage(coreInventory.getProductStorage()); // 有效库存
    coreInventory.setHolderStorage(BigDecimal.ZERO); // 占位库存

    // 行数据重新计算
    coreInventory.setRowWeight(B.mul(productInfo.getWeight(), inQuantity));
    coreInventory.setRowNetWeight(B.mul(productInfo.getNetWeight(), inQuantity));
    coreInventory.setRowWeightOrigin(B.mul(productInfo.getWeight(), inQuantity));

    coreInventory.setUnitCube(productInfo.getUnitCube());
    coreInventory.setRowCube(B.mul(productInfo.getUnitCube(), inQuantity));

    if (ObjectUtil.isEmpty(coreInventory.getRate())) {
      coreInventory.setRate(productInfo.getRate()); // 税率
    }
    //税价  = 采购价 * ( 1+税率)
    coreInventory.setPurchaseAmount(B.mul(coreInventory.getPurchasePrice(), coreInventory.getProductStorage())); // 成本额
    coreInventory.setRatePrice(B.mul(coreInventory.getPurchasePrice(), B.add(BigDecimal.ONE, coreInventory.getRate()))); // 税价

    coreInventory.setRateAmount(B.mul(coreInventory.getRatePrice(), inQuantity)); // 税额
    coreInventory.setShelfLifeDay(shelfLifeDay); // 保质期天数
    if (ObjectUtil.isNotEmpty(productInfo.getShelfLifeDay())) {
      coreInventory.setShelfLifeDay(productInfo.getShelfLifeDay());
    }

    coreInventory.setProduceDate(produceDate); // 生产日期
    if (ObjectUtil.isNotEmpty(productInfo.getShelfLifeDay()) && ObjectUtil.isNotEmpty(produceDate)) {
      coreInventory.setLimitDate(DateUtil.offsetDay(produceDate, Convert.toInt(productInfo.getShelfLifeDay()))); // 到期日期
    }
    if (ObjectUtil.isNotEmpty(plateCode)) {
      coreInventory.setPlateCode(plateCode); // 拍号
    }
    if (ObjectUtil.isNotEmpty(batchNumber)) {
      coreInventory.setBatchNumber(batchNumber); // 批次号
    }

    // 默认商品信息的供应商
    if (ObjectUtil.isEmpty(coreInventory.getProviderId())) {
      coreInventory.setProviderId(productInfo.getProviderId());
      coreInventory.setProviderCode(productInfo.getProviderCode());
      coreInventory.setProviderShortName(productInfo.getProviderShortName());
    }
    coreInventory.setInventoryId(null);

    Assert.isFalse(ObjectUtil.isEmpty(coreInventory.getConsignorId()), "入库货主不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventory.getStorageId()), "入库仓库不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventory.getProductId()), "入库商品不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventory.getPositionName()), "入库货位不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventory.getProviderId()), "供应商不能为空！");
    this.save(coreInventory);

    return coreInventory;
  }
  //#endregion

  //#region 扣减库存和占位
  @Override
  public void subInventory(Long inventoryId, BigDecimal quantity) {
    CoreInventory coreInventory = this.getById(inventoryId);
    if (ObjectUtil.isNotEmpty(coreInventory)) {
      BigDecimal productStorage = B.sub(coreInventory.getProductStorage(), quantity);
      BigDecimal validStorage = B.sub(coreInventory.getValidStorage(), quantity);
      Assert.isFalse(B.isLess(productStorage), "库存ID=" + coreInventory.getInventoryId() + "，商品编号=" + coreInventory.getProductCode() + "库存扣为负数了！");

      //获取商品信息， 用于计算大单位数量
      BaseProduct baseProductInfo = baseProductService.getById(coreInventory.getProductId());

      LambdaUpdateWrapper<CoreInventory> inventoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      inventoryLambdaUpdateWrapper
        .set(CoreInventory::getProductStorage, productStorage)
        .set(CoreInventory::getValidStorage, validStorage)
        .set(CoreInventory::getRowWeight, B.mul(coreInventory.getWeight(), productStorage))
        .set(CoreInventory::getRateAmount, B.mul(coreInventory.getRatePrice(), productStorage))
        .set(CoreInventory::getRowCube, B.mul(coreInventory.getUnitCube(), productStorage))
        .set(CoreInventory::getPurchaseAmount, B.mul(coreInventory.getPurchasePrice(), productStorage))
        .set(CoreInventory::getBigQty, B.div(productStorage, baseProductInfo.getUnitConvert()))
        .eq(CoreInventory::getInventoryId, inventoryId);
      if (B.isGreater(coreInventory.getParcelAverageWeight()) && B.isGreater(validStorage)) {
        //当前包数 = 当前剩余有效库存  /  每包均重
        BigDecimal parcelQuantity = B.div(validStorage, coreInventory.getParcelAverageWeight());
        //如果包数为负数 则 直接更新0
        parcelQuantity = B.isGreater(parcelQuantity, 0) ? parcelQuantity : BigDecimal.ZERO;
        inventoryLambdaUpdateWrapper.set(CoreInventory::getParcelQuantity, parcelQuantity);
      } else if (B.isEqual(validStorage)) {
        //如果有效库存为0  则包数= 0
        inventoryLambdaUpdateWrapper.set(CoreInventory::getParcelQuantity, BigDecimal.ZERO);
      }

      this.update(inventoryLambdaUpdateWrapper);
    }
  }
  //#endregion

  //#region 商品库存查询 selectInventoryComposeGroupList
  @Override
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryComposeGroupList(PageQuery pageQuery) {
    IPage<CoreInventoryComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限

    //#region 构建查询字段、求和字段、分组字段
    // 查询字段
    String selectFields = "inventoryId,consignorName,storageName,productCode,productName,productStorage,holderStorage,validStorage,productModel,productSpec,batchNumber,produceDate,containerNo,originPlace,plateCode,bigUnit,providerShortName,typeName,brandName,consignorCode,relationCode,purchasePrice,smallUnit,shelfLifeDay,limitDate,areaCode,storageStatus";
    // 求和字段
    String sumFields = pageQuery.getSumColumnNames().stream().map(SumColumnNameBo::getProp).collect(Collectors.joining(","));
    // 分组字段
    String groupFields = pageQuery.getQueryBoList().stream().filter(item -> StrUtil.equals(item.getQueryType().getExplain(), QueryTypeEnum.GROUPBY.getExplain())).map(QueryBo::getColumn).collect(Collectors.joining(","));
    if (StrUtil.isNotEmpty(groupFields)) {
      groupFields = groupFields + ",productName";
    } else {
      groupFields = "productName";
    }

    //#endregion
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .innerJoin(BaseProduct.class, on -> {
        on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
        return on;
      });

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);

    //# 构建查询条件
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);

    IPage<CoreInventoryComposeVo> page = this.selectJoinListPage(ipage, CoreInventoryComposeVo.class, wrapper);
    TableDataInfo<CoreInventoryComposeVo> tableDataInfoVo = TableDataInfo.build(page);
    tableDataInfoVo.setTableName(pageQuery.getTableName());

    //#region 计算合计数量
    if (!pageQuery.getSumColumnNames().isEmpty()) {
      MPJLambdaWrapper<CoreInventory> inventoryMpjLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        inventoryMpjLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      inventoryMpjLambdaWrapper
        .innerJoin(BasePosition.class, on -> {
          on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
            .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
          return on;
        })
        .innerJoin(BaseProduct.class, on -> {
          on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
          return on;
        });
      // 拼接查询条件
      List<QueryBo> list = pageQuery.getQueryBoList().stream().filter(item -> !StrUtil.equals(item.getQueryType().getExplain(), QueryTypeEnum.GROUPBY.getExplain())).toList();
      pageQuery.setQueryBoList(list);
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), inventoryMpjLambdaWrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(inventoryMpjLambdaWrapper);
      tableDataInfoVo.setFooter(maps);
    }
    //#endregion

    return tableDataInfoVo;
  }
  //#endregion

  //#region 根据货位获取获取有效库存列表
  @Override
  public List<CoreInventoryComposeVo> selectValidInventoryListByPosition(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String positionName = Convert.toStr(map.get("positionName"));
    Assert.isFalse(ObjectUtil.isEmpty(positionName), "货位不能为空");

    // 获取可分拣货位
    List<Byte> positionTypes = this.getPositionTypes(storageId);

    // 查询字段
    String selectFields = "inventoryId,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,purchasePrice,weight,netWeight,productAttribute,storageStatus,plateCode,positionName,consignorId,consignorCode,consignorName,storageId,storageName,relationCode,relationCode2,relationCode3,relationCode4,relationCode5,middleUnitConvert,bigBarcode,unitConvert,bigUnit,smallUnit,weight,isManageSn";

    // 求和字段
    String sumFields = "productStorage,validStorage,holderStorage,rowWeight";

    // 分组字段
    String groupFields = "storageId,storageName,consignorId,consignorCode,consignorName,positionName,productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,productAttribute,storageStatus,plateCode";

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
      .in(BasePosition::getPositionType, positionTypes) // 货位类型
      .notExists("SELECT t.product_storage FROM core_inventory_holder h WHERE t.inventory_id=h.inventory_id HAVING t.product_storage <= SUM(h.holder_storage)")
      .last("limit 50");

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, CoreInventory.class, BaseProduct.class, BasePosition.class);

    return this.selectJoinList(CoreInventoryComposeVo.class, wrapper);
  }
  //#endregion

  //#region 生成盘点单查询列表
  @Override
  public TableDataInfo<CoreInventoryCreateCheckVo> selectCoreInventoryCreateCheckList(PageQuery pageQuery) {
    IPage<CoreInventoryCreateCheckVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    //#region 构建查询字段、求和字段、分组字段
    // 查询字段
    String selectFields = "inventoryId,storageId,storageName,positionName,productId,productCode,productName,productModel,productSpec,smallUnit,middleUnit,bigUnit,consignorId,consignorCode,consignorName,batchNumber,produceDate,shelfLifeDay,rate,ratePrice,providerId,providerCode,providerShortName,positionType,plateCode,storageStatus,productAttribute,brandName,typeName,middleBarcode,bigBarcode,areaCode,unitConvert,unitConvertText,originPlace,containerNo,inStorageDate,limitDate,channelCode,remark";

    // 求和字段
    String sumFields = "rowWeight,weight,netWeight,productStorage,bigQty,purchaseAmount,holderStorage,validStorage,originStorage";

    // 分组字段
    String groupFields = "storageId,storageName,positionName,productId,productCode,smallUnit,middleUnit,bigUnit,consignorId,consignorCode,consignorName,batchNumber,produceDate,shelfLifeDay,plateCode,storageStatus,productAttribute,productModel,productSpec,middleBarcode,bigBarcode,areaCode,providerId,providerCode,providerShortName,originPlace,containerNo,channelCode,limitDate";
    //#endregion

    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      });

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, CoreInventory.class, BaseProduct.class, BasePosition.class);

    //# 构建查询条件
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BaseProduct.class, BasePosition.class);

    IPage<CoreInventoryCreateCheckVo> page = this.selectJoinListPage(ipage, CoreInventoryCreateCheckVo.class, wrapper);
    TableDataInfo<CoreInventoryCreateCheckVo> tableDataInfoVo = TableDataInfo.build(page);
    tableDataInfoVo.setTableName(pageQuery.getTableName());

    var sumColumnNames = pageQuery.getSumColumnNames();
    //#region 计算合计数量
    if (!ObjectUtil.isEmpty(sumColumnNames)) {
      MPJLambdaWrapper<CoreInventory> inventoryMpjLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        inventoryMpjLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      inventoryMpjLambdaWrapper
        .innerJoin(BasePosition.class, on -> {
          on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
            .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
          return on;
        })
        .innerJoin(BaseProduct.class, on -> {
          on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
          return on;
        });
      // 拼接查询条件
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), inventoryMpjLambdaWrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(inventoryMpjLambdaWrapper);
      tableDataInfoVo.setFooter(maps);
    }
    //#endregion

    return tableDataInfoVo;
  }
  //#endregion

  //#region 获取可分拣货位类型

  /**
   * 获取可分拣货位类型
   *
   * @param storageId 仓库ID
   * @return 可分拣货位类型
   */
  private List<Byte> getPositionTypes(Long storageId) {
    var storageInfo = baseStorageService.selectById(storageId);
    Assert.isFalse(ObjectUtil.isNull(storageInfo), "获取有效库仓库信息不存在！");
    String positionType = storageInfo.getPositionType();
    if (ObjectUtil.isEmpty(positionType)) {
      // 全局设定的：允许参与分拣货位类型
      positionType = sysConfigService.selectConfigByKey("sorting_positionType");
    }
    return Arrays.stream(Optional.ofNullable(positionType).orElse("0").split(",")).map(Convert::toByte).toList();
  }
  //endregion

  //#region 更新占位数据 updateHolderStorage

  /**
   * 更新占位数据
   *
   * @param inventoryId 库存ID
   */
  @Override
  public void updateHolderStorage(Long inventoryId) {
    MPJLambdaWrapper<CoreInventoryHolder> holderLambdaQueryWrapper = new MPJLambdaWrapper<>();
    holderLambdaQueryWrapper.selectSum(CoreInventoryHolder::getHolderStorage, "holderStorage")
      .eq(CoreInventoryHolder::getInventoryId, inventoryId);
    Map<String, Object> stringObjectMap = coreInventoryHolderService.selectJoinMap(holderLambdaQueryWrapper);
    BigDecimal holderStorage = Optional.ofNullable(stringObjectMap).map(m -> Convert.toBigDecimal(stringObjectMap.get("holderStorage"))).orElse(BigDecimal.ZERO);

    UpdateWrapper<CoreInventory> inventoryUpdateWrapper = new UpdateWrapper<>();
    inventoryUpdateWrapper
      .setSql("holder_storage={0}", holderStorage)
      .setSql("valid_storage=product_storage - {0}", holderStorage)
      .lambda()
      .eq(CoreInventory::getInventoryId, inventoryId);
    this.update(inventoryUpdateWrapper);
  }
  //#endregion

  @Override
  public boolean isExistPlate(Long storageId, String plateCode) {
    return false;
  }

  //#region 查询保质期预警数据
  @Override
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryEarlyComposeList(PageQuery pageQuery) {
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .selectAll(CoreInventory.class)
      // 商品信息字段
      .select(BaseProduct::getStopSaleDay, BaseProduct::getTypeName, BaseProduct::getTypeId, BaseProduct::getShelfLifeDay)
      // 货位信息
      .select(BasePosition::getAreaCode)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .innerJoin(BaseProduct.class, on -> {
        on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
        return on;
      });

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);
    // 自定义查询条件
    for (var queryBo : pageQuery.getQueryBoList()) {
      if (queryBo.getQueryType() == QueryTypeEnum.CUSTOM) {
        // 库存有效期天数大于1/3且小于2/3
        if (ObjectUtil.equal(queryBo.getColumn(), "1")) {
          // 库存有效期天数=保质期天数-生产日期
          // 计算库存有效天数 validityDay =(IFNULL(shelf_life_day, 0) - datediff(NOW(),produce_date))
          wrapper.apply("(IFNULL(t.shelf_life_day, 0) - DATEDIFF(NOW(),produce_date)) > t.shelf_life_day / 3 and (IFNULL(t.shelf_life_day, 0) - DATEDIFF(NOW(),produce_date)) < t.shelf_life_day * 2 / 3");
        } else if (ObjectUtil.equal(queryBo.getColumn(), "2")) {
          // 库存有效期天数大于0且小于1/3
          wrapper.apply("(IFNULL(t.shelf_life_day, 0) - DATEDIFF(NOW(),produce_date)) > 0 and (IFNULL(t.shelf_life_day, 0) - DATEDIFF(NOW(),produce_date)) < t.shelf_life_day / 3");
        }
      }
      if (queryBo.getQueryType() == QueryTypeEnum.LT) {
        if (ObjectUtil.equal(queryBo.getColumn(), "3")) {
          // 已过期
          wrapper.apply("(IFNULL(t.shelf_life_day, 0) - datediff(NOW(),produce_date)) < 0");
        }
      }
    }

    pageQuery.setFieldClassList(List.of(CoreInventory.class, BasePosition.class, BaseProduct.class)); // 设置别名
    // 自定义排序
    Function<String, String> customOrderBy = (String orderByStr) -> {
      if (orderByStr.contains("validity_day")) {
        return "(IFNULL(t.shelf_life_day, 0) - datediff(NOW(),produce_date))";
      } else if (orderByStr.contains("storage_advance_day")) {
        return "(IFNULL(t.shelf_life_day, 0) - IFNULL(stop_sale_day,0))";
      } else {
        return orderByStr;
      }
    };
    pageQuery.setCustomOrderBy(customOrderBy);
    IPage<CoreInventoryComposeVo> ipage = pageQuery.build();

    IPage<CoreInventoryComposeVo> page = this.selectJoinListPage(ipage, CoreInventoryComposeVo.class, wrapper);
    TableDataInfo<CoreInventoryComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    // 循环计算 库存有效期天数：validityDay；提前预警剩余天数：storageAdvanceDay
    for (var row : tableDataInfoV.getRows()) {
      // 库存有效期天数：validityDay；
      if (ObjectUtil.isEmpty(row.getProduceDate()) || ObjectUtil.isEmpty(row.getShelfLifeDay())) {
        row.setValidityDay(BigDecimal.ZERO);
      } else {
        long daySpan = DateUtil.between(row.getProduceDate(), DateUtil.date(), DateUnit.DAY);
        row.setValidityDay(B.sub(row.getShelfLifeDay(), daySpan));
      }
      // 提前预警剩余天数：storageAdvanceDay
      if (ObjectUtil.isEmpty(row.getProduceDate()) || ObjectUtil.isEmpty(row.getShelfLifeDay())) {
        row.setStorageAdvanceDay(BigDecimal.ZERO);
      } else {
        long daySpan = DateUtil.between(row.getProduceDate(), DateUtil.date(), DateUnit.DAY);
        row.setStorageAdvanceDay(B.subs(row.getShelfLifeDay(), daySpan, row.getStopSaleDay()));
      }
    }
    return tableDataInfoV;
  }
  //#endregion

  //#region 查询库龄预警数据
  @Override
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryWarningComposeList(PageQuery pageQuery) {
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .selectAll(CoreInventory.class)
      // 商品信息字段
      .select(BaseProduct::getStopSaleDay, BaseProduct::getTypeName, BaseProduct::getTypeId)
      // 货位信息
      .select(BasePosition::getAreaCode)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .innerJoin(BaseProduct.class, on -> {
        on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
        return on;
      });

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);

    pageQuery.setFieldClassList(List.of(CoreInventory.class, BasePosition.class, BaseProduct.class)); // 设置别名
    // 自定义排序
    Function<String, String> customOrderBy = (String orderByStr) -> {
      if (orderByStr.contains("in_day")) {
        return "(datediff(now(),t.produce_date))";
      } else if (orderByStr.contains("produce_day")) {
        return "(datediff(now(),t.produce_date))";
      } else {
        return orderByStr;
      }
    };
    pageQuery.setCustomOrderBy(customOrderBy);
    IPage<CoreInventoryComposeVo> ipage = pageQuery.build();
    IPage<CoreInventoryComposeVo> page = this.selectJoinListPage(ipage, CoreInventoryComposeVo.class, wrapper);
    TableDataInfo<CoreInventoryComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    // 循环计算 入库库龄(天)：inDay；生产库龄：produceDay
    for (var row : tableDataInfoV.getRows()) {
      // 生产库龄：produceDay
      if (ObjectUtil.isEmpty(row.getProduceDate())) {
        row.setProduceDay(BigDecimal.ZERO);
      } else {
        long daySpan = DateUtil.between(row.getProduceDate(), DateUtil.date(), DateUnit.DAY);
        row.setProduceDay(Convert.toBigDecimal(daySpan));
      }
      // 入库库龄(天)：inDay；
      if (ObjectUtil.isEmpty(row.getInStorageDate())) {
        row.setInDay(BigDecimal.ZERO);
      } else {
        long daySpan = DateUtil.between(row.getInStorageDate(), DateUtil.date(), DateUnit.DAY);
        row.setInDay(B.subs(daySpan));
//        row.setInDay(B.adds(daySpan,1));
      }
    }
    return tableDataInfoV;
  }
  //#endregion

  //#region 货位最低库存预警
  @Override
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryLowerComposeList(PageQuery pageQuery) {
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .selectAll(CoreInventory.class)
      // 商品信息字段
      .select(BaseProduct::getStopSaleDay, BaseProduct::getTypeName, BaseProduct::getTypeId)
      // 货位信息
      .select(BasePosition::getAreaCode, BasePosition::getMinCapacity, BasePosition::getMaxCapacity)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .innerJoin(BaseProduct.class, on -> {
        on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
        return on;
      }).gt(BasePosition::getMinCapacity, BigDecimal.ZERO)
      .gt(BasePosition::getMinCapacity, CoreInventory::getProductStorage);// 货位最低库存量 > 库存量，目的为了算出来低于量出来不为负数

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);

    // 自定义排序
    Function<String, String> customOrderBy = (String orderByStr) -> {
      // 低于量
      if (orderByStr.contains("diff_storage")) {
        return "(min_capacity-t.product_storage)";
      }
      // 高于量
      if (orderByStr.contains("above_quantity")) {
        return "(max_capacity-t.product_storage)";
      } else {
        return orderByStr;
      }

    };
    pageQuery.setCustomOrderBy(customOrderBy);
    IPage<CoreInventoryComposeVo> ipage = pageQuery.build();
    IPage<CoreInventoryComposeVo> page = this.selectJoinListPage(ipage, CoreInventoryComposeVo.class, wrapper);
    TableDataInfo<CoreInventoryComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    // 循环计算 低于量：diffStorage
    for (var row : tableDataInfoV.getRows()) {
      // 低于量：diffStorage
      if (ObjectUtil.isEmpty(row.getMinCapacity())) {
        row.setDiffStorage(BigDecimal.ZERO);
      } else {
        BigDecimal diffStorage = B.sub(row.getMinCapacity(), row.getProductStorage());
        row.setDiffStorage(diffStorage);
      }
      // 高于量：aboveQuantity
      if (ObjectUtil.isEmpty(row.getMaxCapacity())) {
        row.setAboveQuantity(BigDecimal.ZERO);
      } else {
        BigDecimal aboveQuantity = B.sub(row.getMaxCapacity(), row.getProductStorage());
        row.setAboveQuantity(aboveQuantity);
      }
    }
    return tableDataInfoV;
  }
  //endregion


  //#region 锁定
  @Override
  public R<Void> lock(Map<String, Object> map) {
    try {
      List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);

      for (long inventoryId : ids) {
        CoreInventory coreInventory = this.baseMapper.selectById(inventoryId);
        if (ObjectUtil.isEmpty(coreInventory)) {
          throw new ServiceException("未获取到单据");
        }
        // 修改数据
        LambdaUpdateWrapper<CoreInventory> lambda = new UpdateWrapper<CoreInventory>().lambda();
        lambda.set(CoreInventory::getStorageStatus, InventoryStatusEnum.LOCKED.getName())
          .eq(CoreInventory::getInventoryId, inventoryId);
        this.update(lambda);//提交
      }


      return R.ok("锁定成功");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  //#endregion

  //#region 解锁
  @Override
  public R<Void> unlock(Map<String, Object> map) {
    try {
      List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);

      for (long inventoryId : ids) {
        CoreInventory coreInventory = this.baseMapper.selectById(inventoryId);
        if (ObjectUtil.isEmpty(coreInventory)) {
          throw new ServiceException("未获取到单据");
        }
        // 修改数据
        LambdaUpdateWrapper<CoreInventory> lambda = new UpdateWrapper<CoreInventory>().lambda();
        lambda.set(CoreInventory::getStorageStatus, InventoryStatusEnum.NORMAL.getName())
          .eq(CoreInventory::getInventoryId, inventoryId);
        this.update(lambda);//提交
      }


      return R.ok("解锁成功");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //endregion


  //#region 获取库存组合信息，查询列表页面
  @Override
  public TableDataInfo<CoreInventoryReplenishmentVo> selectInventoryReplenishmentList(PageQuery pageQuery) {
    IPage<CoreInventoryReplenishmentVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限

    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .select(CoreInventory::getStorageId, CoreInventory::getStorageName, CoreInventory::getConsignorId, CoreInventory::getConsignorCode, CoreInventory::getConsignorName, CoreInventory::getInventoryId, CoreInventory::getProductId, CoreInventory::getProductCode, CoreInventory::getProductName, CoreInventory::getProductModel, CoreInventory::getCreateByName, CoreInventory::getCreateTime)
      .select(BasePosition::getAreaCode, BasePosition::getPositionType, BasePosition::getPositionName, BasePosition::getMinCapacity)
      // 商品信息字段
      .select(BaseProduct::getBigUnit, BaseProduct::getTypeName, BaseProduct::getBrandName, BaseProduct::getProductBarCode, BaseProduct::getSmallUnit, BaseProduct::getUnitConvert)
      // 求和字段
      .selectSum(CoreInventory::getProductStorage)
      .selectSum(CoreInventory::getValidStorage)
      .selectSum(CoreInventory::getHolderStorage)
      .selectSum(CoreInventory::getOriginStorage)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)

      .gt(BasePosition::getMinCapacity, 0) // 货位最低库存>0
      .apply("t.valid_storage < t1.min_capacity") // 有效库存<货位最低库存
      .groupBy(CoreInventory::getStorageId, CoreInventory::getStorageName, CoreInventory::getConsignorId, CoreInventory::getConsignorCode, CoreInventory::getConsignorName, CoreInventory::getInventoryId)
      .groupBy(BasePosition::getAreaCode, BasePosition::getPositionType, BasePosition::getPositionName, BasePosition::getMinCapacity)
      .groupBy(BaseProduct::getBigUnit, BaseProduct::getTypeName, BaseProduct::getBrandName, BaseProduct::getProductBarCode, BaseProduct::getSmallUnit, BaseProduct::getUnitConvert);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);

    IPage<CoreInventoryReplenishmentVo> page = this.selectJoinListPage(ipage, CoreInventoryReplenishmentVo.class, wrapper);
    TableDataInfo<CoreInventoryReplenishmentVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    // 行数据处理
    for (var row : tableDataInfoV.getRows()) {
      row.setDiffStorage(B.sub(row.getMinCapacity(), row.getValidStorage())); // 计算缺货数量 = 最低库存 - 有效库存
    }

    //#region 计算合计列
    if (!pageQuery.getSumColumnNames().isEmpty()) {
      MPJLambdaWrapper<CoreInventory> inventoryMpjLambdaWrapper = new MPJLambdaWrapper<>();
      for (var sumItem : pageQuery.getSumColumnNames()) {
        inventoryMpjLambdaWrapper.select("SUM(" + StringUtils.toUnderScoreCase(sumItem.getProp()) + ") AS " + sumItem.getProp());
      }
      inventoryMpjLambdaWrapper
        .innerJoin(BasePosition.class, on -> {
          on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
            .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
          return on;
        })
        .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId);

      // 拼接查询条件
      BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), inventoryMpjLambdaWrapper, CoreInventory.class, BasePosition.class, BaseProduct.class);
      List<Map<String, Object>> maps = this.selectJoinMaps(inventoryMpjLambdaWrapper);
      tableDataInfoV.setFooter(maps);
    }
    //#endregion

    //#region 已生成未生成计算
    var rows = tableDataInfoV.getRows();
    if (!rows.isEmpty()) {
      // 查询已生成金额
      LambdaQueryWrapper<StorageReplenishmentDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper
        .in(StorageReplenishmentDetail::getTransferQuantity, rows.stream().map(CoreInventoryReplenishmentVo::getDiffStorage).distinct().toList())
        .in(StorageReplenishmentDetail::getProductId, rows.stream().map(CoreInventoryReplenishmentVo::getProductId).distinct().toList())
        .in(StorageReplenishmentDetail::getSourceMainId, rows.stream().map(CoreInventoryReplenishmentVo::getInventoryId).distinct().toList());
      List<StorageReplenishmentDetail> replenishmentDetails = storageReplenishmentDetailService.list(detailLambdaQueryWrapper);
      // 循环计算 计算已生成，未生成
      for (var row : rows) {

        List<StorageReplenishmentDetail> detailList = replenishmentDetails.stream()
          .filter(f -> Objects.equals(Convert.toLong(f.getSourceMainId()), row.getInventoryId())
            && Objects.equals(f.getTransferQuantity(), row.getDiffStorage())
            && Objects.equals(f.getProductId(), row.getProductId())).toList();

        row.setIsCreateReplenishment((byte) (detailList.isEmpty() ? 0 : 1));
      }
    }
    //#endregion

    return tableDataInfoV;
  }

  @Override
  public R<List<CoreInventory>> getList(CoreInventorySearchBo coreInventorySearchBo) {
    if (ObjectUtil.isNull(coreInventorySearchBo)) {
      return R.fail("没有任何查询条件 ，请重新审核");
    }
    Integer take = Optional.of(coreInventorySearchBo).map(CoreInventorySearchBo::getTake).orElse(50); // 查询top N，如果为空，默认50
    Assert.isFalse(B.isLessOrEqual(coreInventorySearchBo.getStorageId()), "仓库ID不能为空");


    String name = coreInventorySearchBo.getName();
    if (StringUtils.isNotEmpty(coreInventorySearchBo.getTerm())) {
      name = coreInventorySearchBo.getTerm();
    }
    String searchFields = coreInventorySearchBo.getSearchFields();

    MPJLambdaWrapper<CoreInventory> positionQueryWrapper = new MPJLambdaWrapper<>();
    positionQueryWrapper.eq(CoreInventory::getStorageId, coreInventorySearchBo.getStorageId()) // 查询仓库
      .eq(CoreInventory::getProductId, coreInventorySearchBo.getProductId()) // 查询商品
      .like(StringUtils.isNotEmpty(name), CoreInventory::getPositionName, name) // 关键词模糊查询
      .gt(CoreInventory::getProductStorage, BigDecimal.ZERO)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      });
    if (StringUtils.isNotEmpty(coreInventorySearchBo.getBatchNumber())) {
      positionQueryWrapper.eq(CoreInventory::getBatchNumber, coreInventorySearchBo.getBatchNumber());
    }
    if (StringUtils.isNotEmpty(coreInventorySearchBo.getPositionName())) {
      positionQueryWrapper.eq(CoreInventory::getPositionName, coreInventorySearchBo.getPositionName());
    }

    try {
      List<String> fields = Arrays.asList("positionName", "storageName", "productStorage", "originStorage", "validStorage"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields = new ArrayList<>(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (!fields.isEmpty()) {
        List<String> finalFields = fields;
        positionQueryWrapper.select(CoreInventory.class, s -> finalFields.contains(s.getProperty()));
      }
      positionQueryWrapper.orderByAsc(CoreInventory::getInventoryId); // 排序
      positionQueryWrapper.last("limit " + take); // top N
      List<CoreInventory> coreInventories = this.baseMapper.selectList(positionQueryWrapper);

      if (coreInventorySearchBo.getIsGroup()) {
        var groupList = coreInventories.stream().collect(Collectors.groupingBy(CoreInventory::getPositionName));
        List<CoreInventory> list = new ArrayList<>();
        for (var groupItem : groupList.entrySet()) {  // 货位循环

          BigDecimal productStorage = groupItem.getValue().stream().map(CoreInventory::getProductStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
          BigDecimal originStorage = groupItem.getValue().stream().map(CoreInventory::getOriginStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
          BigDecimal validStorage = groupItem.getValue().stream().map(CoreInventory::getValidStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
          CoreInventory coreInventory = groupItem.getValue().get(0);
          coreInventory.setProductStorage(NumberUtil.round(productStorage, 4));
          coreInventory.setOriginStorage(NumberUtil.round(originStorage, 4));
          coreInventory.setValidStorage(NumberUtil.round(validStorage, 4));
          list.add(coreInventory);
        }
        return R.ok(list);
      } else {
        return R.ok(coreInventories);
      }
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getMessage();
      throw new ServiceException(msg);
    }

  }
  //#endregion


  //#region importData
  @Override
  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {
    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);

    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到
    LoginHelper.setLoginUser(loginUser);
    String key = request.getParameter("key");
    sysImportService.setKey(key);
    DateTime startDate = DateUtil.date(); // 导入开始时间

    try {
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");

      // 处理解析结果
      ExcelResult<Map<String, Object>> excelResult = null;
      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
      var dataList = excelResult.getList();

      // 通用验证
      sysImportService.setError(false);
      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");
      sysImportService.writeMsg("开始导入...");
      //#endregion

      //#region 验证数据正确性
      int i = 0;
      int successCount = 0;
      int updateCount = 0;

      //查询所有货位
      LambdaQueryWrapper<BasePosition> positions = new LambdaQueryWrapper<>();
      positions.eq(BasePosition::getEnable, EnableEnum.ENABLE.getId())
        .orderByAsc(BasePosition::getPositionName);
      List<BasePosition> list = basePositionService.list(positions);
      int positionLength = 0;
      Long positionCount = 0L;
      for (var row : dataList) {

        Long productStorage = Convert.toLong(row.get("productStorage"));
        Long total = Convert.toLong(row.get("productStorage"));
        Long value = 40L;

        while (productStorage > 0L) {
          i++;
          String formattedNumericPart = String.format("%05d", i); // 确保是位数
          String plateCode = "PH2025" + formattedNumericPart;
          if (productStorage >= 40) {
            // 减少指定的数值
            productStorage -= value;
            row.put("productStorage", 40);
          } else {
            row.put("productStorage", productStorage);
            productStorage = 0L;
          }
          if (B.isEqual(positionCount, 80)) {
            positionCount = 40L;
            positionLength += 1;
          } else {
            positionCount += 40L;
          }

          CoreInventory inventoryInfo = new CoreInventory();
          // 数据拷贝
          BeanUtil.copyProperties(row, inventoryInfo);
          inventoryInfo.setStorageId(233L);
          inventoryInfo.setStorageName("铁峰四号库");

          inventoryInfo.setOriginStorage(inventoryInfo.getProductStorage());
          inventoryInfo.setHolderStorage(BigDecimal.ZERO);
          inventoryInfo.setValidStorage(inventoryInfo.getProductStorage());
          inventoryInfo.setPositionName(list.get(positionLength).getPositionName());
          inventoryInfo.setStorageStatus("正常");
          inventoryInfo.setProductAttribute("正常");
          inventoryInfo.setPlateCode(plateCode);
          inventoryInfo.setWeight(B.div(inventoryInfo.getRowWeight(), total));
          inventoryInfo.setRowWeight(B.mul(inventoryInfo.getWeight(), inventoryInfo.getProductStorage()));
          inventoryInfo.setRowWeightOrigin(inventoryInfo.getRowWeight());
          inventoryInfo.setRemark("20250115导入生成");
          inventoryInfo.setProviderId(1111L);
          inventoryInfo.setProviderCode("FH");
          inventoryInfo.setProviderShortName("飞鹤");
          this.save(inventoryInfo);

        }
      }
      //#endregion

      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);
      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
      sysImportService.writeMsgBlue("导入完成，更新{}条", updateCount);
      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception exception) {
      sysImportService.writeMsgRed("导入错误：{}", exception);
      transactionManager.rollback(transaction); // 手动回滚事务
    }
    sysImportService.writeEnd(); // 标记结算
  }
  //#endregion

}
