package com.yiruantong.composite.service.in.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.composite.service.in.IInScanService;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInScanNoBillService;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferService;
import com.yiruantong.inventory.tool.MpjWrapperHelper;
import com.yiruantong.outbound.service.out.IOutScanNoBillService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InScanServiceImpl implements IInScanService {
  private final ISysConfigService sysConfigService;
  private final ICoreInventoryService coreInventoryService;
  private final IInScanNoBillService inScanNoBillService;
  private final IOutScanNoBillService outScanNoBillService;
  private final MpjWrapperHelper mpjWrapperHelper;
  private final IStoragePositionTransferService storagePositionTransferService;
  private final IInEnterDetailService enterDetailService;
  private final IInShelveDetailService shelveDetailService;

  /**
   * 码盘扫描入库 - 获取码盘数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @Override
  public R<List<Map<String, Object>>> getEnterStackingData(Map<String, Object> map) {
    String productModel = Convert.toStr(map.get("productModel"));
    String positionName = Convert.toStr(map.get("positionName"));
    Long storageId = Convert.toLong(map.get("storageId"));

    Assert.isTrue(productModel != null, "条码不能为空！");
    Assert.isTrue(positionName != null, "货位不能为空！");
    Assert.isTrue(storageId != null, "请正确选择仓库！");

    //#region 固定条件
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .selectAll(CoreInventory.class)
      .select(BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getMiddleBarcode, BaseProduct::getMiddleUnitConvert, BaseProduct::getBigBarcode)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .eq(CoreInventory::getProductModel, productModel)
      .eq(CoreInventory::getStorageId, storageId)
      .eq(CoreInventory::getPositionName, positionName) // 收货位
      .isNull(CoreInventory::getPlateCode) // 拍号为空
      .eq(CoreInventory::getStorageStatus, InventoryStatusEnum.TALLY_WAITING.getName())
      .eq(BasePosition::getIsLocked, EnableEnum.DISABLE.getId()) // 排除所得货位，1=锁定，0=未锁定
      .gt(CoreInventory::getValidStorage, 0L)
      .notExists("SELECT t.product_storage FROM core_inventory_holder h WHERE t.inventory_id=h.inventory_id HAVING t.product_storage <= SUM(h.holder_storage)")
      .last("limit 500");
    List<Map<String, Object>> orderList = coreInventoryService.selectJoinMaps(wrapper);

    return R.ok(orderList);
  }

  /**
   * 码盘扫描入库 - 保存
   *
   * @param storageScanPositionTransferBo@return 返回查询数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveEnterStacking(ScanPositionTransferBo storageScanPositionTransferBo) {
    storagePositionTransferService.savePositionTransfer(storageScanPositionTransferBo);
    // 将当前拍号更新到预到货明细里面
    for (var transferItem : storageScanPositionTransferBo.getDataList()) {

      Long enterDetailId = Convert.toLong(transferItem.getSourceDetailId()); // 这个来源明细ID
      InEnterDetail enterDetail = enterDetailService.getBaseMapper().selectById(enterDetailId); // 入库单明细
      if (ObjectUtil.isNull(enterDetail)) {
        continue;
      }
      //入库单明细处理
      if (B.isGreater(enterDetail.getEnterQuantity(), transferItem.getFinishedQuantity())) {
        //原数据 从新计算
        enterDetail.setEnterQuantity(B.sub(enterDetail.getEnterQuantity(), transferItem.getFinishedQuantity()));
        enterDetail.setRowWeight(B.mul(enterDetail.getEnterQuantity(), enterDetail.getWeight()));
        enterDetail.setRowWeight(B.mul(enterDetail.getEnterQuantity(), enterDetail.getWeight()));
        enterDetail.setRowWeightTon(B.div(enterDetail.getRowWeight(), new BigDecimal(1000)));
        enterDetail.setRowCube(B.mul(enterDetail.getEnterQuantity(), enterDetail.getUnitCube()));
        enterDetail.setPurchaseAmount(B.mul(enterDetail.getEnterQuantity(), enterDetail.getPurchasePrice()));
        enterDetail.setRateAmount(B.mul(enterDetail.getEnterQuantity(), enterDetail.getRatePrice()));
        enterDetail.setBigQty(B.div(enterDetail.getEnterQuantity(), enterDetail.getUnitConvert()));
        enterDetailService.updateById(enterDetail);

        //添加新生成数据
        InEnterDetail newEnterDetail = new InEnterDetail();
        BeanUtil.copyProperties(enterDetail, newEnterDetail); // 复制数据
        newEnterDetail.setEnterQuantity(transferItem.getFinishedQuantity());
        newEnterDetail.setRowWeight(B.mul(transferItem.getFinishedQuantity(), newEnterDetail.getWeight()));
        newEnterDetail.setRowWeight(B.mul(transferItem.getFinishedQuantity(), newEnterDetail.getWeight()));
        newEnterDetail.setRowWeightTon(B.div(newEnterDetail.getRowWeight(), new BigDecimal(1000)));
        newEnterDetail.setRowCube(B.mul(transferItem.getFinishedQuantity(), newEnterDetail.getUnitCube()));
        newEnterDetail.setPurchaseAmount(B.mul(transferItem.getFinishedQuantity(), newEnterDetail.getPurchasePrice()));
        newEnterDetail.setRateAmount(B.mul(transferItem.getFinishedQuantity(), newEnterDetail.getRatePrice()));
        newEnterDetail.setBigQty(B.div(transferItem.getFinishedQuantity(), newEnterDetail.getUnitConvert()));
        newEnterDetail.setPlateCode(transferItem.getPlateCode());
        newEnterDetail.setEnterDetailId(null);
        enterDetailService.save(newEnterDetail);
      } else {
        // 直接更新拍号
        enterDetail.setPlateCode(transferItem.getPlateCode());
        enterDetailService.updateById(enterDetail);
      }
      LambdaQueryWrapper<InShelveDetail> inShelveDetailLqw = new LambdaQueryWrapper<>();
      inShelveDetailLqw.eq(InShelveDetail::getEnterDetailId, enterDetailId)
        .eq(InShelveDetail::getEnterId, enterDetail.getEnterId())
        .isNull(InShelveDetail::getPlateCode);
      InShelveDetail shelveDetail = shelveDetailService.getOne(inShelveDetailLqw); // 上架单明细
      if (ObjectUtil.isNull(shelveDetail)) {
        continue;
      }

      //入库单明细处理
      if (B.isGreater(shelveDetail.getQuantity(), transferItem.getFinishedQuantity())) {
        //原数据 从新计算
        shelveDetail.setQuantity(B.sub(shelveDetail.getQuantity(), transferItem.getFinishedQuantity()));
        shelveDetail.setRowWeight(B.mul(shelveDetail.getQuantity(), shelveDetail.getWeight()));
        shelveDetail.setRowWeight(B.mul(shelveDetail.getQuantity(), shelveDetail.getWeight()));
        shelveDetail.setRowWeightTon(B.div(shelveDetail.getRowWeight(), new BigDecimal(1000)));
        shelveDetail.setRowCube(B.mul(shelveDetail.getQuantity(), shelveDetail.getUnitCube()));
        shelveDetail.setPurchaseAmount(B.mul(shelveDetail.getQuantity(), shelveDetail.getPurchasePrice()));
        shelveDetail.setRateAmount(B.mul(shelveDetail.getQuantity(), shelveDetail.getRatePrice()));
        shelveDetail.setBigQty(B.div(shelveDetail.getQuantity(), shelveDetail.getUnitConvert()));
        shelveDetailService.updateById(shelveDetail);

        //添加新生成数据
        InShelveDetail newShelveDetail = new InShelveDetail();
        BeanUtil.copyProperties(shelveDetail, newShelveDetail); // 复制数据
        newShelveDetail.setQuantity(transferItem.getFinishedQuantity());
        newShelveDetail.setRowWeight(B.mul(transferItem.getFinishedQuantity(), newShelveDetail.getWeight()));
        newShelveDetail.setRowWeight(B.mul(transferItem.getFinishedQuantity(), newShelveDetail.getWeight()));
        newShelveDetail.setRowWeightTon(B.div(newShelveDetail.getRowWeight(), new BigDecimal(1000)));
        newShelveDetail.setRowCube(B.mul(transferItem.getFinishedQuantity(), newShelveDetail.getUnitCube()));
        newShelveDetail.setPurchaseAmount(B.mul(transferItem.getFinishedQuantity(), newShelveDetail.getPurchasePrice()));
        newShelveDetail.setRateAmount(B.mul(transferItem.getFinishedQuantity(), newShelveDetail.getRatePrice()));
        newShelveDetail.setBigQty(B.div(transferItem.getFinishedQuantity(), newShelveDetail.getUnitConvert()));
        newShelveDetail.setPlateCode(transferItem.getPlateCode());
        newShelveDetail.setShelveDetailId(null);
        shelveDetailService.save(newShelveDetail);
      } else {
        // 直接更新拍号
        shelveDetail.setPlateCode(transferItem.getPlateCode());
        shelveDetailService.updateById(shelveDetail);
      }
    }

    return R.ok("码盘扫描成功");
  }
}
