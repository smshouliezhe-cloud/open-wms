package com.yiruantong.composite.service.inventory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageReplenishmentActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageReplenishmentStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.domain.inventory.Bo.StorageLowerReplenishmentBo;
import com.yiruantong.composite.service.inventory.IStorageLowerReplenishmentService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentDetailService;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 库存下限转补货单
 */
@RequiredArgsConstructor
@Service
public class StorageLowerReplenishmentServiceImpl implements IStorageLowerReplenishmentService {
  private final ICoreInventoryService coreInventoryService;
  private final IStorageReplenishmentService storageReplenishmentService;
  private final IStorageReplenishmentDetailService storageReplenishmentDetailService;

  //#region 生成补货单

  /**
   * 生成补货单
   *
   * @param loginUser
   * @param storageReplenishmentBoList
   */
  @Async
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void toReplenishment(LoginUser loginUser, List<StorageLowerReplenishmentBo> storageReplenishmentBoList) {

    // 根据仓库货主分组生成
    // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<StorageLowerReplenishmentBo, List<Object>> compositeKey = person ->
      Arrays.asList(person.getStorageId(), person.getConsignorId());
    // 分组
    Map<List<Object>, List<StorageLowerReplenishmentBo>> groupingMap =
      storageReplenishmentBoList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    for (var mapItem : groupingMap.keySet()) {
      Long storageId = Convert.toLong(mapItem.get(0));
      Long consignorId = Convert.toLong(mapItem.get(1));

      //#region 生成补货单
      StorageLowerReplenishmentBo replenishmentBo = storageReplenishmentBoList.stream()
        .filter(x -> Objects.equals(x.getStorageId(), storageId) && Objects.equals(x.getConsignorId(), consignorId))
        .findFirst().orElse(null); // 获取第一条库存

      StorageReplenishment replenishment = new StorageReplenishment();
      BeanUtil.copyProperties(replenishmentBo, replenishment);
      replenishment.setReplenishmentCode(DBUtils.getCodeRegular(MenuEnum.MENU_2235, loginUser.getTenantId()));
      replenishment.setBillType(StorageReplenishmentActionEnum.STORAGELOWER.getName());
      replenishment.setBillStatus(StorageReplenishmentStatusEnum.NEWED.getName());
      replenishment.setSortingStatus(SortingStatusEnum.NONE.getId());
      replenishment.setSourceType(StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_OUT.getName());
      replenishment.setAuditing(AuditEnum.AUDIT.getId());

      storageReplenishmentService.save(replenishment);

      //#endregion

      // 明细数据
      List<StorageLowerReplenishmentBo> replenishmentBoList = groupingMap.get(mapItem);
      for (var item : replenishmentBoList) {
        // 查询库存
        LambdaQueryWrapper<CoreInventory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoreInventory::getInventoryId, item.getInventoryId());
        CoreInventory coreInventory = coreInventoryService.getOne(lambdaQueryWrapper);

        Assert.isTrue(ObjectUtil.isNotEmpty(coreInventory), "未找到单据");

        //#region 生成补货单明细
        //生成补货单明细
        StorageReplenishmentDetail detail = new StorageReplenishmentDetail();
        BeanUtil.copyProperties(coreInventory, detail);
        detail.setReplenishmentId(replenishment.getReplenishmentId());
        detail.setTransferQuantity(item.getDiffStorage()); // 补货数量 = 缺货数量
        detail.setSourceMainId(Convert.toStr(item.getInventoryId())); // 来源id
        detail.setSubTotalAmount(B.mul(detail.getTransferQuantity(), detail.getPurchasePrice()));
        storageReplenishmentDetailService.save(detail);

        //#endregion

      }

      LambdaQueryWrapper<StorageReplenishmentDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper.eq(StorageReplenishmentDetail::getReplenishmentId, replenishment.getReplenishmentId());
      var replenishmentDetails = storageReplenishmentDetailService.list(detailLambdaQueryWrapper);
      if (!replenishmentDetails.isEmpty()) {
        BigDecimal totalAmount = replenishmentDetails.stream().map(StorageReplenishmentDetail::getSubTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalQuantity = replenishmentDetails.stream().map(StorageReplenishmentDetail::getTransferQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        replenishment.setTotalAmount(totalAmount);
        replenishment.setTotalQuantity(totalQuantity);
        storageReplenishmentService.saveOrUpdate(replenishment);

      }
    }
  }
  //endregion

}
