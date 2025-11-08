package com.yiruantong.inventory.service.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryHistoryBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHistoryVo;
import com.yiruantong.inventory.enums.HistoryTypeEnum;
import com.yiruantong.inventory.mapper.core.CoreInventoryHistoryMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * WMS库存变化推送Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@RequiredArgsConstructor
@Service
public class CoreInventoryHistoryServiceImpl extends ServiceImplPlus<CoreInventoryHistoryMapper, CoreInventoryHistory, CoreInventoryHistoryVo, CoreInventoryHistoryBo> implements ICoreInventoryHistoryService {
  private final ICoreInventoryService coreInventoryService;

  @Override
  public List<CoreInventoryHistory> selectListByMainId(Long mainId) {
    List<String> statusList = Arrays.asList("销售订单", "一键出库", "SN拣货下架", "常规拣货下架");
    LambdaQueryWrapper<CoreInventoryHistory> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(CoreInventoryHistory::getMainId, mainId)
      .in(CoreInventoryHistory::getSourceType, statusList);

    return this.list(detailLambdaQueryWrapper);
  }

  @Override
  public void addHistory(HistoryTypeEnum historyTypeEnum, BaseProduct productInfo, CommonDetailDto commonDetailDto, InventorySourceTypeEnum inventorySourceTypeEnum, BigDecimal beforeQuantity, BigDecimal beforeWeight, BigDecimal afterQuantity, BigDecimal afterWeight) {
    CoreInventoryHistory coreInventoryHistory = BeanUtil.copyProperties(commonDetailDto, CoreInventoryHistory.class);

    String batchNumber = commonDetailDto.getBatchNumber();
    String plateCode = commonDetailDto.getPlateCode();
    Date produceDate = commonDetailDto.getProduceDate(); // 生产日期
    BigDecimal purchasePrice = commonDetailDto.getPurchasePrice();

    if (ObjectUtil.isEmpty(purchasePrice)) {
      purchasePrice = productInfo.getPurchasePrice();
    }

    if (Objects.equals(historyTypeEnum, HistoryTypeEnum.IN)) {
      Assert.isFalse(B.isLessOrEqual(commonDetailDto.getInQuantity()), "库存轨迹入库数量不能小于0");
      coreInventoryHistory.setInQuantity(commonDetailDto.getInQuantity());
      coreInventoryHistory.setInAmount(B.mul(commonDetailDto.getInQuantity(), purchasePrice));
//      coreInventoryHistory.setInWeight(B.mul(commonDetailDto.getInQuantity(),  productInfo.getWeight()));
      coreInventoryHistory.setInWeight(afterWeight);
      coreInventoryHistory.setOutQuantity(BigDecimal.ZERO);
      coreInventoryHistory.setOutAmount(BigDecimal.ZERO);
      coreInventoryHistory.setOutWeight(BigDecimal.ZERO);
      coreInventoryHistory.setPositionName(commonDetailDto.getPositionNameIn());
      coreInventoryHistory.setBigQty(B.div(commonDetailDto.getInQuantity(), productInfo.getUnitConvert()));
    } else {
      Assert.isFalse(B.isLessOrEqual(commonDetailDto.getOutQuantity()), "库存轨迹出库数量不能小于0");
      coreInventoryHistory.setInQuantity(BigDecimal.ZERO);
      coreInventoryHistory.setInAmount(BigDecimal.ZERO);
      coreInventoryHistory.setInWeight(BigDecimal.ZERO);
      coreInventoryHistory.setOutQuantity(commonDetailDto.getOutQuantity());
      coreInventoryHistory.setOutAmount(B.mul(commonDetailDto.getOutQuantity(), purchasePrice));
      coreInventoryHistory.setOutWeight(B.mul(commonDetailDto.getOutQuantity(), productInfo.getWeight()));
      coreInventoryHistory.setPositionName(commonDetailDto.getPositionNameOut());

      coreInventoryHistory.setBigQty(B.div(commonDetailDto.getOutQuantity(), productInfo.getUnitConvert()));
    }
    coreInventoryHistory.setBeforeQuantity(beforeQuantity);
    coreInventoryHistory.setBeforeWeight(beforeWeight);
    coreInventoryHistory.setAfterQuantity(afterQuantity);
    coreInventoryHistory.setAfterWeight(afterWeight);

    coreInventoryHistory.setBigUnit(productInfo.getBigUnit());
    coreInventoryHistory.setBrandName(productInfo.getBrandName());
    coreInventoryHistory.setBrandId(productInfo.getBrandId());
    coreInventoryHistory.setTypeName(productInfo.getTypeName());
    coreInventoryHistory.setTypeId(productInfo.getTypeId());
    coreInventoryHistory.setProductBarCode(productInfo.getProductBarCode());
    coreInventoryHistory.setCaseNumber(commonDetailDto.getCaseNumber()); // 箱号

    // 重新计算数据
    coreInventoryHistory.setAfterAmount(B.mul(afterQuantity, purchasePrice)); // 操作后库存金额
    coreInventoryHistory.setSourceType(inventorySourceTypeEnum.getName());
    if (ObjectUtil.isNull(coreInventoryHistory.getProduceDate())) {
      coreInventoryHistory.setProduceDate(produceDate); // 生产日期
    }
    if (ObjectUtil.isNull(coreInventoryHistory.getPlateCode())) {
      coreInventoryHistory.setPlateCode(plateCode); // 拍号
    }
    if (ObjectUtil.isNull(coreInventoryHistory.getBatchNumber())) {
      coreInventoryHistory.setBatchNumber(batchNumber); // 批次号
    }
    if (ObjectUtil.isNull(coreInventoryHistory.getSourceCode())) {
      coreInventoryHistory.setSourceCode(commonDetailDto.getBillCode()); // 批次号
    }

    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getMainId()), "库存轨迹中主表ID不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getDetailId()), "库存轨迹中明细ID不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getInventoryId()), "库存轨迹中库存ID不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getBillCode()), "库存轨迹中执行单号不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getSourceCode()), "库存轨迹中来源单号不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getStorageId()), "库存轨迹中仓库不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getConsignorId()), "库存轨迹中货主不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getPositionName()), "库存轨迹中货位不能为空！");
    Assert.isFalse(ObjectUtil.isEmpty(coreInventoryHistory.getProductId()), "库存轨迹中商品信息不能为空！");

    this.save(coreInventoryHistory);
  }
}
