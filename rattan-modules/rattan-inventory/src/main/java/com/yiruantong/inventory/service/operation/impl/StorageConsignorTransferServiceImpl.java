package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageConsignorStransferStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageEnterStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransferDetail;
import com.yiruantong.inventory.domain.operation.api.ApiStorageConsignorTransferBo;
import com.yiruantong.inventory.domain.operation.bo.StorageConsignorTransferBo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailVo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferVo;
import com.yiruantong.inventory.domain.operation.vo.TransferAppVo;
import com.yiruantong.inventory.mapper.operation.StorageConsignorTransferMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.operation.IStorageConsignorTransferDetailService;
import com.yiruantong.inventory.service.operation.IStorageConsignorTransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 货位转移Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageConsignorTransferServiceImpl extends ServiceImplPlus<StorageConsignorTransferMapper, StorageConsignorTransfer, StorageConsignorTransferVo, StorageConsignorTransferBo> implements IStorageConsignorTransferService, IInventoryBaseService {
  private final IStorageConsignorTransferDetailService storageConsignorTransferDetailService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseStorageService baseStorageService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StorageConsignorTransfer storageConsignorTransfer = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(storageConsignorTransfer.getTransferStatus(), StorageConsignorStransferStatusEnum.FINISHED.getName()),
        storageConsignorTransfer.getConsignorTransferCode() + "出库完成的，不允许重复操作");
      List<StorageConsignorTransferDetail> detailList = storageConsignorTransferDetailService.selectListByMainId(id);
      for (StorageConsignorTransferDetail detail : detailList) {
        Assert.isFalse(ObjectUtil.isEmpty(detail.getConsignorNameTarget()), "目标货主不能为空");
        Assert.isFalse(ObjectUtil.isEmpty(detail.getPositionNameIn()), "目标货位不能为空");
      }

      //#region 通用模块调用
      inventoryCommonService.setBizService(this);

      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(storageConsignorTransfer, CommonMainDto.class);
      commonMainDto.setMainId(storageConsignorTransfer.getConsignorTransferId());
      commonMainDto.setMainCode(storageConsignorTransfer.getConsignorTransferCode());

      // 构建DTO数据 - 明细集合
      List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(m -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(m, CommonDetailDto.class);
        detailDto.setMainId(m.getConsignorTransferId()); // 主表ID
        detailDto.setDetailId(m.getConsignorTransferDetailId()); // 明细ID
        detailDto.setInQuantity(m.getTransferQuantity());  // 入库数量
        detailDto.setOutQuantity(m.getTransferQuantity()); // 出库数量
        detailDto.setPositionNameIn(m.getPositionNameIn()); // 入库货位
        detailDto.setPositionNameOut(m.getPositionName());  // 出库货位
        detailDto.setInStorageDate(m.getInStorageDate());  // 入库时间
        detailDto.setInventoryId(m.getInventoryId());//库存ID
        detailDto.setBillCode(storageConsignorTransfer.getConsignorTransferCode());
        return detailDto;
      }).toList();

      // 分拣库存
      var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, HolderSourceTypeEnum.STORAGE_CONSIGNOR_TRANSFER);
      Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());

      // 出库
      R<List<CommonDetailDto>> outResult = inventoryCommonService.out(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_STORAGE_CONSIGNOR_TRANSFER_OUT);

      List<CommonDetailDto> inResult = outResult.getData().stream().map(m -> {
        m.setBillCode(storageConsignorTransfer.getConsignorTransferCode());
        return m;
      }).toList();

      // 入库
      inventoryCommonService.in(commonMainDto, inResult, InventorySourceTypeEnum.PC_STORAGE_CONSIGNOR_TRANSFER_IN);
      //#endregion

      //#region 更新状态
      StorageConsignorTransfer updateStorageOuter = new StorageConsignorTransfer();
      updateStorageOuter.setAuditor(LoginHelper.getNickname());
      updateStorageOuter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      updateStorageOuter.setAuditDate(DateUtil.date());
      updateStorageOuter.setTransferStatus(StorageConsignorStransferStatusEnum.FINISHED.getName());
      updateStorageOuter.setConsignorTransferId(storageConsignorTransfer.getConsignorTransferId()); // 设置主键

      this.updateById(updateStorageOuter);
      //#endregion
    }
    return R.ok("过户成功");
  }

  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {
    for (var detail : detailList) {
      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), HolderSourceTypeEnum.STORAGE_CONSIGNOR_TRANSFER);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StorageConsignorTransferDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StorageConsignorTransferDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StorageConsignorTransferDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StorageConsignorTransferDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StorageConsignorTransferDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StorageConsignorTransferDetail::getConsignorTransferDetailId, detail.getDetailId());
      storageConsignorTransferDetailService.update(detailLambdaUpdateWrapper);
    }
  }
  //#endregion

  //#region 更新分拣状态 updateSortingStatus

  /**
   * 更新明细分拣状态
   *
   * @param mainID            主表ID
   * @param sortingStatusEnum 分拣状态
   */
  @Override
  public void updateSortingStatus(Long mainID, SortingStatusEnum sortingStatusEnum) {
    LambdaUpdateWrapper<StorageConsignorTransfer> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StorageConsignorTransfer::getSortingStatus, sortingStatusEnum.getId())
      .set(StorageConsignorTransfer::getSortingDate, DateUtil.date())
      .eq(StorageConsignorTransfer::getConsignorTransferId, mainID);
    this.update(orderLambdaUpdateWrapper);
  }
  //#endregion


  @Override
  public R<Map<String, Object>> add(ApiStorageConsignorTransferBo bo) {
    try {
      // 验证单号是否已推送
      LambdaQueryWrapper<StorageConsignorTransfer> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(StorageConsignorTransfer::getSourceCode, bo.getSourceCode());
      StorageConsignorTransfer existDataInfo = this.getOnly(queryWrapper);

      if (ObjectUtil.isNotNull(existDataInfo)) {
        Map<String, Object> result = new HashMap<>();
        result.put("consignorTransferId", existDataInfo.getConsignorTransferId());
        result.put("consignorTransferCode", existDataInfo.getConsignorTransferCode());
        return R.ok(bo.getSourceCode() + "已存在，不允许重复推送", result);
      }

      // 验证仓库
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在！");

      bo.setStorageId(storageInfo.getStorageId());

      // 验证明细
      for (var detailInfo : bo.getDetailList()) {
        BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
        Assert.isFalse(ObjectUtil.isNull(prodInfo), detailInfo.getProductCode() + "商品编号不存在");

        detailInfo.setTransferQuantity(detailInfo.getTransferQuantity());
        detailInfo.setProductId(prodInfo.getProductId());
        detailInfo.setProductModel(prodInfo.getProductModel());
        detailInfo.setProductName(prodInfo.getProductName());
        detailInfo.setProductSpec(prodInfo.getProductSpec());

        if (ObjectUtil.isNull(detailInfo.getTransferQuantity()) || B.isLessOrEqual(detailInfo.getTransferQuantity(), BigDecimal.ZERO)) {
          return R.fail(detailInfo.getProductModel() + "数量必须大于0");
        }

        // 验证货主
        BaseConsignor consignorInfo = baseConsignorService.getByName(detailInfo.getConsignorName());
        Assert.isFalse(ObjectUtil.isNull(consignorInfo), "明细源货主" + detailInfo.getConsignorName() + "不存在！");
        detailInfo.setConsignorId(consignorInfo.getConsignorId());
        detailInfo.setConsignorCode(consignorInfo.getConsignorCode());

        // 验证目标货主
        LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        consignorLambdaQueryWrapper.eq(BaseConsignor::getConsignorName, detailInfo.getConsignorNameTarget());
        BaseConsignor targetConsignorInfo = baseConsignorService.getOnly(consignorLambdaQueryWrapper);

        Assert.isFalse(ObjectUtil.isNull(targetConsignorInfo), "明细目标货主" + detailInfo.getConsignorNameTarget() + "不存在！");
        detailInfo.setConsignorIdTarget(targetConsignorInfo.getConsignorId());
        detailInfo.setConsignorCodeTarget(targetConsignorInfo.getConsignorCode());

      }


      // 创建单据和明细
      StorageConsignorTransfer dataInfo = new StorageConsignorTransfer();
      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("consignorTransferId"));
      dataInfo.setConsignorTransferCode(DBUtils.getCodeRegular(MenuEnum.MENU_1787));
      dataInfo.setTransferStatus(StorageEnterStatusEnum.NEWED.getName());

      if (ObjectUtil.isNotNull(bo.getSourceType())) {
        dataInfo.setSourceType(bo.getSourceType());
      } else {
        dataInfo.setSourceType("接口推送");
      }

      this.save(dataInfo);

      for (var detailInfo : bo.getDetailList()) {
        StorageConsignorTransferDetail detail = new StorageConsignorTransferDetail();
        BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("consignorTransferDetailId"));
        detail.setConsignorTransferId(dataInfo.getConsignorTransferId());
        storageConsignorTransferDetailService.save(detail);
      }

      List<StorageConsignorTransferDetail> details = storageConsignorTransferDetailService.selectListByMainId(dataInfo.getConsignorTransferId());

      BigDecimal totalTransferQuantity = details.stream().map(StorageConsignorTransferDetail::getTransferQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalPurchaseAmount = details.stream().map(StorageConsignorTransferDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalWeight = details.stream().map(StorageConsignorTransferDetail::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StorageConsignorTransfer> lambda = new LambdaUpdateWrapper<>();
      lambda.set(StorageConsignorTransfer::getTotalTransferQuantity, totalTransferQuantity)
        .set(StorageConsignorTransfer::getTotalPurchaseAmount, totalPurchaseAmount)
        .set(StorageConsignorTransfer::getTotalWeight, totalWeight)
        .eq(StorageConsignorTransfer::getConsignorTransferId, dataInfo.getConsignorTransferId());


      Map<String, Object> result = new HashMap<>();
      result.put("consignorTransferId", dataInfo.getConsignorTransferId());
      result.put("consignorTransferCode", dataInfo.getConsignorTransferCode());
      return R.ok("过户单保存成功", result);
    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }

  @Override
  public TableDataInfo<TransferAppVo> pageDetailList(PageQuery pageQuery) {
    TableDataInfo<StorageConsignorTransferVo> tableDataInfo = this.pageList(pageQuery);
    List<Long> list = new ArrayList<>(tableDataInfo.getRows().stream().map(StorageConsignorTransferVo::getConsignorTransferId).toList());
    if (list.isEmpty()) {
      list.add(NumberUtils.ZERO);
    }
    LambdaQueryWrapper<StorageConsignorTransferDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper
      .in(StorageConsignorTransferDetail::getConsignorTransferId, list);
    List<StorageConsignorTransferDetailVo> erpSaleOrderDetailVos = storageConsignorTransferDetailService.selectList(detailLambdaQueryWrapper);
    List<Long> productIds = new ArrayList<>(erpSaleOrderDetailVos.stream().map(StorageConsignorTransferDetailVo::getProductId).distinct().toList());
    if (productIds.isEmpty()) {
      productIds.add(NumberUtils.ZERO);
    }
    List<BaseProductVo> baseProductVos = baseProductService.selectByIds(productIds);

    List<TransferAppVo> detailVoList = new ArrayList<>();
    for (var row : tableDataInfo.getRows()) {
      // 主表数据
      TransferAppVo vo = BeanUtil.copyProperties(row, TransferAppVo.class);

      // 明细处理
      List<StorageConsignorTransferDetailVo> orderDetailVos = erpSaleOrderDetailVos.stream().filter(x -> Objects.equals(x.getConsignorTransferId(), row.getConsignorTransferId())).toList();

      List<StorageConsignorTransferDetailVo> erpSaleOrderDetailAppVos = BeanUtil.copyToList(orderDetailVos, StorageConsignorTransferDetailVo.class);
      for (var detail : erpSaleOrderDetailAppVos) {
        var product = baseProductVos.stream().filter(x -> Objects.equals(x.getProductId(), detail.getProductId())).findAny().orElse(null);
        if (ObjectUtil.isNotNull(product)) {
          detail.setImages(product.getImages());
        }
      }
      vo.setDetailList(erpSaleOrderDetailAppVos);
      detailVoList.add(vo);
    }

    TableDataInfo<TransferAppVo> detailVoTableDataInfo = new TableDataInfo<>();
    detailVoTableDataInfo.setResult(true);
    detailVoTableDataInfo.setTableName("StorageConsignorTransferDetailVo");
    detailVoTableDataInfo.setTotal(tableDataInfo.getTotal());
    detailVoTableDataInfo.setRows(detailVoList);

    return detailVoTableDataInfo;
  }

  @Override
  public R<TransferAppVo> transferDetail(Long consignorTransferId) {
    StorageConsignorTransferVo orderVo = this.selectById(consignorTransferId);
    LambdaQueryWrapper<StorageConsignorTransferDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper
      .eq(StorageConsignorTransferDetail::getConsignorTransferId, consignorTransferId);
    var erpSaleOrderDetailVos = storageConsignorTransferDetailService.selectList(detailLambdaQueryWrapper);
    TransferAppVo orderAppVo = BeanUtil.copyProperties(orderVo, TransferAppVo.class);

    if(ObjectUtil.isNotNull(erpSaleOrderDetailVos) && B.isGreater(erpSaleOrderDetailVos.size())){
      List<BaseProductVo> baseProductVos = baseProductService.selectByIds(erpSaleOrderDetailVos.stream().map(StorageConsignorTransferDetailVo::getProductId).distinct().toList());

      // 明细处理
      for (var detail : erpSaleOrderDetailVos) {
        var product = baseProductVos.stream().filter(x -> Objects.equals(x.getProductId(), detail.getProductId())).findAny().orElse(null);
        if (ObjectUtil.isNotNull(product)) {
          detail.setImages(product.getImages());
        }
      }
      orderAppVo.setDetailList(erpSaleOrderDetailVos);
    }

    return R.ok(orderAppVo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<TransferAppVo> transferAdd(TransferAppVo transferAppVo) {
    StorageConsignorTransfer transfer = BeanUtil.copyProperties(transferAppVo, StorageConsignorTransfer.class);
    transfer.setConsignorTransferCode(DBUtils.getCodeRegular(MenuEnum.MENU_1787));
    this.save(transfer);
    for (var detail :transferAppVo.getDetailList()){

        StorageConsignorTransferDetail transferDetail = BeanUtil.copyProperties(detail, StorageConsignorTransferDetail.class);
      transferDetail.setConsignorTransferId(transfer.getConsignorTransferId());
      storageConsignorTransferDetailService.save(transferDetail);
    }
    return R.ok("添加成功");
  }
}
