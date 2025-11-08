package com.yiruantong.outbound.liteflow.OutOrderSorting;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.SortingRuleBillTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreSortingRuleService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.vo.OutSortingRuleVo;
import com.yiruantong.outbound.liteflow.Context.OrderSortingContext;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@LiteflowComponent(id = "orderSortingFilterCmp", name = "3.出库单查询库存，并过滤")
@RequiredArgsConstructor
public class OrderSortingFilterCmp extends NodeComponent {

  private final IBaseProductService baseProductService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutSortingRuleService outSortingRuleService;
  private final IBaseStorageService baseStorageService;
  private final IBasePositionService basePositionService;
  private final ISysConfigService sysConfigService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreSortingRuleService coreSortingRuleService;

  @Override
  public void process() throws Exception {
    OrderSortingContext ctx = this.getContextBean(OrderSortingContext.class);
    OutOrder outOrder = ctx.getOrderInfo();

    for (var detail : ctx.getOutOrderDetails()) {
      // 商品信息
      BaseProduct baseProduct = baseProductService.getById(detail.getProductId());
      Assert.isFalse(ObjectUtil.isNull(baseProduct), "商品ID不存在");

      //#region  获取有效库存，并过滤
      List<CoreInventoryComposeVo> inventoryList; // 有效库存数据

      // 设置库存可分拣状态
      coreInventoryService.setInventoryStatusEnumList(ctx.getInventoryStatusEnumList()); // 设置库存可分拣的状态
      // 设置库存可分拣货位类型
      coreInventoryService.setPositionTypeEnumList(ctx.getPositionTypeEnumList()); // 设置库存可分拣的货位类型

      // 查到当前明细数据
      LambdaQueryWrapper<OutSortingRule> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(OutSortingRule::getOrderDetailId, detail.getOrderDetailId())
        .and(a ->
          a.isNotNull(OutSortingRule::getPositionName)
            .or().isNotNull(OutSortingRule::getPlateCode)
            .or().isNotNull(OutSortingRule::getBatchNumber)
            .or().isNotNull(OutSortingRule::getInventoryId)
            .or().isNotNull(OutSortingRule::getSingleSignCode)
            .or().isNotNull(OutSortingRule::getProduceDate)
            .or().isNotNull(OutSortingRule::getProduceDateGt)
            .or().isNotNull(OutSortingRule::getProjectCode)
        )
      ;

      // 明细中设置的分拣规则
      List<OutSortingRuleVo> sortingRuleList = outSortingRuleService.selectList(queryWrapper);
      // 在出库明细中指定货位对应的货位类型
      List<Byte> rulePositionTypeList = new ArrayList<>();
      List<Byte> positionTypeList;
      positionTypeList = this.getPositionTypes(outOrder.getStorageId(), null);

      for (var sortingRule : sortingRuleList) {
        if (ObjectUtil.isNotEmpty(sortingRule)) {
          // 指定货位
          if (StringUtils.isNotEmpty(sortingRule.getPositionName())) {
            BasePosition position = basePositionService.getByName(outOrder.getStorageId(), sortingRule.getPositionName());
            Assert.isFalse(ObjectUtil.isEmpty(position), "商品编号：" + sortingRule.getProductCode() + "，指定货位未找到");
            positionTypeList = this.getPositionTypes(outOrder.getStorageId(), String.valueOf(position.getPositionType()));
          }
        }
      }


      OutOrderTypeEnum outOrderTypeEnum = OutOrderTypeEnum.matchingEnum(outOrder.getOrderType());
      if (ObjectUtil.isNotNull(outOrderTypeEnum)) {
        if (outOrderTypeEnum == OutOrderTypeEnum.CROSS_ORDER) {
          coreInventoryService.setProductAttributeEnumList(CollUtil.newArrayList(InventoryStatusEnum.ZERO_INVENTORY));
        }
      }

      Map<String, Object> expandFields = outOrder.getExpandFields();
      // 是否需要过滤 商品属性
      Boolean filterProductAttribute = true;

      // 如果出库单为 紧急放行库存状态默认正常和未质检
      if (ObjectUtil.isNotNull(expandFields) && ObjectUtil.isNotNull(expandFields.get("release")) && Convert.toBool(expandFields.get("release"))) {
        coreInventoryService.setProductAttributeEnumList(CollUtil.newArrayList(InventoryStatusEnum.UNCHECKED));
        filterProductAttribute = false;
      }

      inventoryList = coreInventoryService.selectValidInventoryList(outOrder.getStorageId(), outOrder.getConsignorId(), baseProduct.getProductId(), null, positionTypeList);

      // 允许跨货主分拣
      if (ctx.getSorting_crossConsignor()) {
        //如果跨货主， 则查询出来其他货主的信息，并根据 天数 进行筛选 ，然后把数据追加到 正常分拣的list集合中
        outOrderTypeEnum = OutOrderTypeEnum.matchingEnum(outOrder.getOrderType());
        var kuahuozhu = coreInventoryService.selectValidInventoryList(outOrder.getStorageId(), null, baseProduct.getProductId(), null, null);
        if (B.isGreater(ctx.getSorting_crossConsignorDays())) {
          Long days = ctx.getSorting_crossConsignorDays();
          //查看日期间隔多少 会少1  则过滤的时候需要将计算后的日期间隔 +1
          kuahuozhu = kuahuozhu.stream().filter(f -> B.isGreaterOrEqual(B.add(DateUtil.between(f.getInStorageDate(), new Date(), DateUnit.DAY), 1), new BigDecimal(days))).toList();
        }
        List<Long> ids = inventoryList.stream().map(CoreInventory::getInventoryId).toList();
        //把当前已经存在的库存排除掉 在合并到 inventoryList 中
        inventoryList.addAll(kuahuozhu.stream().filter(item -> !ids.contains(item.getInventoryId())).toList());
      }
      // 清空可分拣库存状态和库存属性
      coreInventoryService.setInventoryStatusEnumList(null);
      coreInventoryService.setProductAttributeEnumList(null);

      if (inventoryList.isEmpty()) continue;

      // 按规格分拣
      if (Objects.equals(baseProduct.getIsSpecSorting(), EnableEnum.ENABLE.getId())) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProductSpec(), baseProduct.getProductSpec())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 开启库区分拣策略
      if (ctx.getBatch_openStorageArea_regular()) {

      }

      // 开启集装箱分拣
      if (ctx.getSorting_isContainerNo() && StringUtils.isNotEmpty(outOrder.getContainerNo())) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getContainerNo(), outOrder.getContainerNo())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 如果商品设置的是按批次分拣并且订单明细的批次号不为空，则按批次占位
      if (ObjectUtil.equals(baseProduct.getIsBatchNumberSorting(), EnableEnum.ENABLE.getId())) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getBatchNumber(), detail.getBatchNumber())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      if (!sortingRuleList.isEmpty()) {
        for (var sortingRule : sortingRuleList) {
          if (ObjectUtil.isNotEmpty(sortingRule)) {
            // 指定货位
            if (StringUtils.isNotEmpty(sortingRule.getPositionName())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPositionName(), sortingRule.getPositionName())).toList();
              if (inventoryList.isEmpty()) continue;
              else {
                BasePosition position = basePositionService.getByName(outOrder.getStorageId(), sortingRule.getPositionName());

                Assert.isFalse(ObjectUtil.isEmpty(position), "商品编号：" + sortingRule.getProductCode() + "指定货位未找到");
                rulePositionTypeList.add(position.getPositionType());
              }
            }

            // 指定拍号
            if (StringUtils.isNotEmpty(sortingRule.getPlateCode())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPlateCode(), sortingRule.getPlateCode())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 指定批次号
            if (StringUtils.isNotEmpty(sortingRule.getBatchNumber())) {
              List<String> ruleList = StringUtils.splitList(Convert.toStr(sortingRule.getBatchNumber()));
              inventoryList = inventoryList.stream().filter(x -> ruleList.contains(x.getBatchNumber())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 指定商品规格
            if (StringUtils.isNotEmpty(sortingRule.getProductSpec())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProductSpec(), sortingRule.getProductSpec())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 指定唯一码
            if (StringUtils.isNotEmpty(sortingRule.getSingleSignCode())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getSingleSignCode(), sortingRule.getSingleSignCode())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 指定项目号
            if (StringUtils.isNotEmpty(sortingRule.getProjectCode())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProjectCode(), sortingRule.getProjectCode())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 指定箱号
            if (StringUtils.isNotEmpty(sortingRule.getCaseNumber())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getCaseNumber(), sortingRule.getCaseNumber())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 指定库存ID
            if (ObjectUtil.isNotEmpty(sortingRule.getInventoryId())) {
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getInventoryId(), sortingRule.getInventoryId())).toList();
              if (inventoryList.isEmpty()) continue;
            }

            // 生产日期
            if (ObjectUtil.isNotEmpty(sortingRule.getProduceDate()) && ObjectUtil.isEmpty(sortingRule.getProduceDateGt())) {
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
              inventoryList = inventoryList.stream().filter(f -> ObjectUtil.isNotEmpty(f.getProduceDate())).filter(f -> ObjectUtil.equals(formatter.format(f.getProduceDate()), formatter.format(sortingRule.getProduceDate()))).toList();
              if (inventoryList.isEmpty()) continue;
            }

            List<OutSortingRuleVo> nonEmptyProduceDateGt = sortingRuleList.stream()
              .filter(k -> StringUtils.isNotEmpty(Convert.toStr(k.getProduceDateGt())))
              .toList();
            if (ObjectUtil.isNotEmpty(sortingRule.getProduceDateGt())) {

              inventoryList = inventoryList.stream()
                .filter(f -> nonEmptyProduceDateGt.stream()
                  .anyMatch(k -> {
                    LocalDate date1 = f.getProduceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate date2 = k.getProduceDateGt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return date1.equals(date2) || date1.isAfter(date2);
                  }))
                .collect(Collectors.toList());
            }


          }

        }
      } else {

        // 没有规则就指定属性为正常的商品
        if (filterProductAttribute) {
          inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getProductAttribute(), InventoryStatusEnum.NORMAL.getName())).toList();

        }
      }


      // 定制唯一码分拣规则
      if (ctx.getSorting_singleSignCode() && StringUtils.isNotEmpty(detail.getSingleSignCode())) {
        inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getContainerNo(), detail.getContainerNo())).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 仓库指定货位类型分拣
      BaseStorage baseStorage = baseStorageService.getById(outOrder.getStorageId());
      String positionType = baseStorage.getPositionType();
      if (ctx.getIsOnlyStoragePosition()) {
        positionType = "12,13"; // 12=高架货位, 13=存储货位
      }
      // 拼接上出库单明细指定货位的货位类型
      if (!rulePositionTypeList.isEmpty()) {
        String rulePositionTypes = StringUtils.join(rulePositionTypeList, ",");
        positionType = positionType + "," + rulePositionTypes;
      }
      List<String> positionTypes = StringUtils.splitList(positionType, ",");
      if (!positionTypes.isEmpty()) {
        inventoryList = inventoryList.stream()
          .filter(f -> positionTypes.stream().anyMatch(a -> Objects.equals(Convert.toByte(a), f.getPositionType()))).toList();
        if (inventoryList.isEmpty()) continue;
      }

      // 先按库区权重分拣
      if (ctx.getSorting_positionPriorIndate()) {
        // 商品开启效期排序，生产日期优先有入库日期
        if (Objects.equals(baseProduct.getIsNeedPeriod(), EnableEnum.ENABLE.getId())) {
          // 排序顺序：库区权重、生产日期、入库日期
          inventoryList = inventoryList.stream().sorted(Comparator
            .comparing(CoreInventoryComposeVo::getAreaOrderNum)
            .thenComparing(CoreInventoryComposeVo::getProduceDate)
            .thenComparing(CoreInventoryComposeVo::getInStorageDate)).toList();
        } else {
          // 排序顺序：库区权重、入库日期
          inventoryList = inventoryList.stream().sorted(Comparator
            .comparing(CoreInventoryComposeVo::getAreaOrderNum)
            .thenComparing(CoreInventoryComposeVo::getInStorageDate)).toList();
        }
      } else {
        // 商品开启效期排序，生产日期优先有入库日期
        if (Objects.equals(baseProduct.getIsNeedPeriod(), EnableEnum.ENABLE.getId())) {
          // 排序顺序：生产日期、入库日期、生产日期为空排到后面
          inventoryList = inventoryList.stream().sorted(Comparator.comparing(CoreInventoryComposeVo::getProduceDate, Comparator.nullsLast(Date::compareTo)).thenComparing(CoreInventoryComposeVo::getInStorageDate)).toList();
        } else {
          // 排序顺序：生产日期为空、生产日期、入库日期
          inventoryList = inventoryList.stream().sorted(Comparator.comparing(CoreInventoryComposeVo::getProduceDate, Comparator.nullsFirst(Date::compareTo)).thenComparing(CoreInventoryComposeVo::getInStorageDate)).toList();
        }
      }

      // 货位数量更多优先分拣
      if (ctx.getSorting_positionGreaterQty()) {
        inventoryList = inventoryList.stream().sorted(Comparator
          .comparing(CoreInventoryComposeVo::getPositionType)
          .thenComparing(CoreInventoryComposeVo::getValidStorage).reversed()).toList(); // 有效库存倒序
      }
      //#endregion

      /*===================================================--
      -- 出库订单商品如果够一整箱的，优先分拣出库整货区商品
      --===================================================--*/
      List<PositionTypeEnum> wholePositionTypeEnumList = Arrays.asList(PositionTypeEnum.ELEVATED, PositionTypeEnum.STORAGE); // 高架货位、存储货位
      if (!ctx.getIsOnlyStoragePosition()) {
        // 指定存储货位分拣时不走整拣单，常规货位整拣单分拣
        // 默认整箱分拣
        BigDecimal bagUnitQty = baseProduct.getUnitConvert(); // 大单位换算关系
        // 整托分拣
        if (B.isEqual(baseProduct.getSortingType(), InventorySortTypeEnum.FULL_PLATE_SORTING) && B.isGreater(baseProduct.getPlateQty())) {
          bagUnitQty = baseProduct.getPlateQty();
        }

        // 2=整箱， 3=整托
        List<InventorySortTypeEnum> sortingTypeEnumList = CollUtil.newArrayList(InventorySortTypeEnum.FULL_CONTAINER_SORTING, InventorySortTypeEnum.FULL_PLATE_SORTING);
        if (sortingTypeEnumList.stream().anyMatch(a -> B.isEqual(baseProduct.getSortingType(), a.getId())) && B.isGreater(bagUnitQty)) {
          // 整拣数量
          BigDecimal bagCount = NumberUtil.round(B.div(detail.getLackStorage(), bagUnitQty), 0, RoundingMode.CEILING); // 取整
          // 剩余零散数量=缺货数量 - 整托数 * 商品信息中的每托数量
          AtomicReference<BigDecimal> smallQty = new AtomicReference<>(B.sub(detail.getLackStorage(), B.mul(bagCount, bagUnitQty)));


          if (B.isLess(smallQty.get())) smallQty.set(B.sub(B.mul(bagCount, bagUnitQty), detail.getLackStorage()));

          if (B.isEqual(smallQty.get())) smallQty.set(BigDecimal.ZERO);

          // 整拣分拣
          List<CoreInventoryComposeVo> inventoryBagList = new ArrayList<>();
          if (B.isGreater(bagCount)) {
            List<CoreInventoryComposeVo> finalInventoryList = inventoryList;
            BigDecimal finalBagUnitQty = bagUnitQty;
            inventoryBagList = inventoryList.stream().filter(f -> {
                BigDecimal sumValidStorage = finalInventoryList.stream()
                  .filter(t -> Objects.equals(t.getPositionName(), f.getPositionName()))
                  .map(CoreInventoryComposeVo::getValidStorage)
                  .reduce(BigDecimal.ZERO, BigDecimal::add); // 同货位求和
                return B.isGreaterOrEqual(sumValidStorage, finalBagUnitQty); // 有效库>=大单位换算关系
              })
              // 优先分拣存储货位，然后常规货位进行分拣整箱
              .sorted(Comparator
                .comparing(CoreInventoryComposeVo::getPositionType).reversed()
                .thenComparing(CoreInventoryComposeVo::getPositionName))
              .toList();

            // 查存储货位
            List<CoreInventoryComposeVo> inventoryStorageList = inventoryBagList.stream().filter(f -> {
                // 查询 13=存储货位
                boolean isNotPositionType = wholePositionTypeEnumList.stream().noneMatch(n -> Objects.equals(n.getId(), f.getPositionType()));
                return !isNotPositionType;
              })
              .toList();

            // 如果没有存储货位的库存，就直接不进行分箱，直接走常规货位
            if (B.isLessOrEqual(BigDecimal.valueOf(inventoryStorageList.size()))) {

              AtomicReference<BigDecimal> bagLackStorage = new AtomicReference<>(BigDecimal.ZERO);

              bagLackStorage.set(B.floor(detail.getLackStorage())); // 取整抹去小数, 获得最大可以分拣的整箱数量
              // 开始整拣分拣-从高架货位拣货
              this.sortingInventory(outOrder, detail, inventoryBagList, bagLackStorage, InventorySortTypeEnum.FULL_CONTAINER_SORTING);
            } else {
              BigDecimal bagValidStorage = inventoryBagList.stream().map(CoreInventoryComposeVo::getValidStorage).reduce(BigDecimal.ZERO, BigDecimal::add); // 可用库存量
              BigDecimal bagIndex = new BigDecimal(1);
              AtomicReference<BigDecimal> bagLackStorage = new AtomicReference<>(BigDecimal.ZERO); // 需要分拣的数量，整托数量倍数
              while (B.isLessOrEqual(bagIndex, bagCount)) {
                BigDecimal bagUnitQtys = B.mul(bagIndex, bagUnitQty);
                if (B.isGreaterOrEqual(bagValidStorage, bagUnitQtys)) {
                  bagLackStorage.set(B.floor(bagUnitQtys)); // 取整抹去小数, 获得最大可以分拣的整箱数量
                }
                bagIndex = B.adds(bagIndex, 1);
              }
              if (B.isGreater(smallQty.get())) {
                bagLackStorage.set(B.floor(B.sub(bagLackStorage, bagUnitQty)));
              }
              // 开始整拣分拣-从高架货位拣货
              this.sortingInventory(outOrder, detail, inventoryBagList, bagLackStorage, InventorySortTypeEnum.FULL_CONTAINER_SORTING);
            }
          }

          /*--===================================================--
          -- 零散分拣
          --===================================================--*/
          /* 不满整箱是否需要整箱下架，整箱下架后取出所需要的库存，剩余返回货架 */
          List<CoreInventoryComposeVo> finalInventoryBagList = inventoryBagList.stream().filter(f -> B.isGreater(f.getValidStorage())).toList();
          // 剩余零散数量>=商品信息中的满足数量为整托分拣 且 满足数量为整托分拣>0
          if (!finalInventoryBagList.isEmpty() && B.isGreaterOrEqual(smallQty.get(), baseProduct.getCeilPlateQty()) && B.isGreater(baseProduct.getCeilPlateQty())) {
            // 开始分拣
            this.sortingInventory(outOrder, detail, finalInventoryBagList, smallQty, InventorySortTypeEnum.SCATTERED_SORTING);
          } else {
            List<CoreInventoryComposeVo> finalInventoryList = inventoryList;
            // 先分拣零散货位是否满足整箱分拣
            List<CoreInventoryComposeVo> inventoryScatteredList = inventoryList.stream().filter(f -> {
                BigDecimal sumValidStorage = finalInventoryList.stream().filter(t -> Objects.equals(t.getPositionName(), f.getPositionName()))
                  .map(CoreInventoryComposeVo::getValidStorage).reduce(BigDecimal.ZERO, BigDecimal::add); // 同货位求和

                // 排除掉,12=高架货位,13=存储货位
                boolean isNotPositionType = wholePositionTypeEnumList.stream().noneMatch(n -> Objects.equals(n.getId(), f.getPositionType()));
                return B.isGreater(f.getValidStorage(), sumValidStorage) && isNotPositionType && B.isGreater(f.getValidStorage()); // 整货位数量满足才分拣，且库存>0
              })
              .toList();

            // 开始零散分拣
            this.sortingInventory(outOrder, detail, inventoryScatteredList, smallQty, InventorySortTypeEnum.SCATTERED_SORTING);
          }
        }
      }

      /*--===================================================--
        -- 零拣分拣
        --===================================================--*/
      if (inventoryList.isEmpty()) continue;

      // 相同日期不跨货位分拣，取整
      if (ctx.getSorting_isFullPositionLoad()) {
        inventoryList = inventoryList.stream()
          .sorted(Comparator
            .comparing(CoreInventoryComposeVo::getPositionName)
            .thenComparing(CoreInventoryComposeVo::getProduceDate)
            .thenComparing(CoreInventoryComposeVo::getInStorageDate))
          .toList();
      }
      // 开始零拣分拣
      this.sortingInventory(outOrder, detail, inventoryList, new AtomicReference<>(detail.getLackStorage()), InventorySortTypeEnum.SCATTERED_SORTING);
    } // 出库单明细循环完毕

  }

  //#region getPositionTypes

  /**
   * 获取可分拣货位类型
   *
   * @param storageId 仓库ID
   * @return 可分拣货位类型
   */
  private List<Byte> getPositionTypes(Long storageId, String positionTypes) {
    var storageInfo = baseStorageService.selectById(storageId);
    com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(ObjectUtil.isNull(storageInfo), "获取有效库仓库信息不存在！");
    String positionType = storageInfo.getPositionType();
    if (ObjectUtil.isNotEmpty(positionTypes)) {
      positionType = positionType + "," + positionTypes;
    }
    if (ObjectUtil.isEmpty(positionType)) {
      // 全局设定的：允许参与分拣货位类型
      positionType = sysConfigService.selectConfigByKey("sorting_positionType");
    }
    if (positionType.contains("/")) {
      positionType = positionType.replace("/", ",");
    }
    // 分割字符串
    String[] stringNumbers = positionType.split(",");
    List<Byte> byteList = new ArrayList<>();
    for (String number : stringNumbers) {
      byteList.add(Byte.parseByte(number.trim()));
    }
    return byteList;
  }
  //endregion

  //#region sortingInventory

  /**
   * 库存分拣
   *
   * @param outOrder      出库单主表信息
   * @param detail        出库单明细信息
   * @param inventoryList 库存数据集合
   * @param lackStorage   缺货数量
   * @param sortType      分拣类型
   */
  private void sortingInventory(OutOrder outOrder, OutOrderDetail detail, List<CoreInventoryComposeVo> inventoryList, AtomicReference<BigDecimal> lackStorage, InventorySortTypeEnum sortType) {
    // 缺货数量<=0的不参与分拣
    if (B.isLessOrEqual(lackStorage.get())) return;

    BigDecimal validStorage;
    int isFinished = 0;   // 当前订单明细是否分拣完成1=完成，0=未完成
    for (var item : inventoryList.stream().filter(item -> B.isGreater(item.getValidStorage())).toList()) {
      validStorage = item.getValidStorage();
      BigDecimal holderQty;
      if (B.isGreater(validStorage, lackStorage.get())) {
        holderQty = lackStorage.get();
        lackStorage.set(BigDecimal.ZERO);
        isFinished = 1;
      } else {
        holderQty = validStorage;
        lackStorage.set(B.sub(lackStorage.get(), validStorage));
        if (B.isLessOrEqual(lackStorage.get())) {
          isFinished = 1;
        }
      }


      HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());
      Assert.isFalse(holderSourceTypeEnum == null, "未找到出库单的枚举");
      // 构建DTO数据
      CommonMainDto mainInfo = BeanUtil.copyProperties(outOrder, CommonMainDto.class);
      mainInfo.setMainId(outOrder.getOrderId());
      mainInfo.setMainCode(outOrder.getOrderCode());
      mainInfo.setHolderSourceType(holderSourceTypeEnum); // 占位类型
      mainInfo.setStoreOrderCode(outOrder.getStoreOrderCode());
      CommonDetailDto detailInfo = BeanUtil.copyProperties(detail, CommonDetailDto.class);
      detailInfo.setMainId(outOrder.getOrderId());
      detailInfo.setDetailId(detail.getOrderDetailId());
      detailInfo.setBillCode(outOrder.getOrderCode());
      detailInfo.setSourceType(holderSourceTypeEnum.getName());
      detailInfo.setPositionNameOut(item.getPositionName());
      // 生成占位
      coreInventoryHolderService.createHolder(mainInfo, detailInfo, item, holderQty, sortType);

      // 更新缓存中的有效库存
      item.setValidStorage(B.sub(item.getValidStorage(), holderQty));  // 消减有效库存
//      var first = inventoryList.stream().filter(f -> Objects.equals(f.getInventoryId(), item.getInventoryId()))
//        .findFirst().orElse(null);
//      if (ObjectUtil.isNotNull(first)) {
//        first.setValidStorage(B.sub(first.getValidStorage(), holderQty)); // 消减有效库存
//      }
      detail.setLackStorage(B.sub(detail.getLackStorage(), holderQty)); // 缺货数量计算

      if (isFinished == 1) {
        break;
      }
    }
  }
  //endregion
}
