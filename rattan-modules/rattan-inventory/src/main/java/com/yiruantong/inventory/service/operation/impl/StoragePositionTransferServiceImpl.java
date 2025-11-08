package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StoragePositionStransferStatusEnum;
import com.yiruantong.common.core.enums.inventory.TransferTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferDetailBo;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.operation.StoragePositionTransfer;
import com.yiruantong.inventory.domain.operation.StoragePositionTransferDetail;
import com.yiruantong.inventory.domain.operation.bo.StoragePositionTransferBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferVo;
import com.yiruantong.inventory.mapper.operation.StoragePositionTransferMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferDetailService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 货位转移Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StoragePositionTransferServiceImpl extends ServiceImplPlus<StoragePositionTransferMapper, StoragePositionTransfer, StoragePositionTransferVo, StoragePositionTransferBo> implements IStoragePositionTransferService, IInventoryBaseService {
  private final IStoragePositionTransferDetailService storagePositionTransferDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventorySnService coreInventorySnService;

  private HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.STORAGE_POSITION_TRANSFTER;
  private InventorySourceTypeEnum sourceTypeEnum_Out = InventorySourceTypeEnum.PC_POSITION_TRANSFER_OUT;
  private InventorySourceTypeEnum sourceTypeEnum_In = InventorySourceTypeEnum.PC_POSITION_TRANSFER_IN;


  //#region savePositionTransfer

  /**
   * 保存货位转移
   *
   * @param storageScanPositionTransferBo@return R 返回保存结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> savePositionTransfer(ScanPositionTransferBo storageScanPositionTransferBo) {
    Long storageId = storageScanPositionTransferBo.getStorageId();
    List<ScanPositionTransferDetailBo> transferDetailBoList = storageScanPositionTransferBo.getDataList();
    Assert.isFalse(ObjectUtil.isEmpty(storageId), "仓库不能为空！");
    Assert.isFalse(transferDetailBoList.isEmpty(), "扫描明细不能为空！");

    //#region 验证数据
    for (ScanPositionTransferDetailBo mapItem : transferDetailBoList) {
      String targetPositionName = mapItem.getTargetPositionName();
      String productCode = mapItem.getProductCode();
      Assert.isFalse(ObjectUtil.isEmpty(targetPositionName), productCode + "目标货位不能为空！");
    }
    //#endregion

    //region 生成货位转移主表
    StoragePositionTransfer storagePositionTransfer = new StoragePositionTransfer();
    storagePositionTransfer.setTransferCode(DBUtils.getCodeRegular(MenuEnum.MENU_1042)); // 生成单号
    BeanUtil.copyProperties(transferDetailBoList.get(0), storagePositionTransfer); // 复制数据
    storagePositionTransfer.setUserId(LoginHelper.getUserId());
    storagePositionTransfer.setNickName(LoginHelper.getNickname());
    storagePositionTransfer.setSortingStatus(SortingStatusEnum.NONE.getId());
    storagePositionTransfer.setTransferType(storageScanPositionTransferBo.getTransferType().getName());
    this.save(storagePositionTransfer);
    //#endregion

    //region 生成货位转移明细
    for (ScanPositionTransferDetailBo mapItem : transferDetailBoList) {
      String targetPositionName = mapItem.getTargetPositionName();
      BigDecimal finishedQuantity = mapItem.getFinishedQuantity();
      if (B.isEqual(finishedQuantity)) {
        continue;
      }

      StoragePositionTransferDetail detail = new StoragePositionTransferDetail();
      BeanUtil.copyProperties(mapItem, detail);
      detail.setTransferId(storagePositionTransfer.getTransferId()); // 设置主键
      detail.setPositionNameIn(targetPositionName); // 目标货位，也就是入库货位
      detail.setTransferQuantity(finishedQuantity); // 扫描数量
      detail.setRowWeight(B.mul(detail.getWeight(), finishedQuantity));
      detail.setRowNetWeight(B.mul(detail.getNetWeight(), finishedQuantity));
      detail.setPurchaseAmount(B.mul(detail.getPurchasePrice(), finishedQuantity));
      if (StrUtil.equals(storagePositionTransfer.getTransferType(), TransferTypeEnum.PC_STACKING_IN.getName())) {
        detail.setSourceDetailId("" + mapItem.getInventoryId());
      }
      storagePositionTransferDetailService.save(detail);

      mapItem.setTransferId(detail.getTransferId());
      mapItem.setTransferDetailId(detail.getTransferDetailId());
    }
    //#endregion

    // 货位转移
    transfter(storagePositionTransfer, transferDetailBoList, storageScanPositionTransferBo);

    return R.ok("货位转移成功");
  }

  @Override
  public R<Void> transfter(StoragePositionTransfer storagePositionTransfer, List<ScanPositionTransferDetailBo> detailList, ScanPositionTransferBo storageScanPositionTransferBo) {

    //#region 通用模块调用
    inventoryCommonService.setBizService(this);
    CommonMainDto commonMainDto = BeanUtil.copyProperties(storagePositionTransfer, CommonMainDto.class);
    commonMainDto.setMainId(storagePositionTransfer.getTransferId());
    commonMainDto.setMainCode(storagePositionTransfer.getTransferCode());
    commonMainDto.setProductAttributeEnumList(List.of(InventoryStatusEnum.NORMAL, InventoryStatusEnum.UNCHECKED, InventoryStatusEnum.UNQUALIFIED, InventoryStatusEnum.PENDING_PROCESSING));
    List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(detail -> {
      CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
      detailDto.setMainId(detail.getTransferId()); // 主表ID
      detailDto.setDetailId(detail.getTransferDetailId()); // 明细ID
      detailDto.setBillCode(storagePositionTransfer.getTransferCode());
      detailDto.setInQuantity(detail.getFinishedQuantity());  // 入库数量
      detailDto.setOutQuantity(detail.getFinishedQuantity()); // 出库数量
      detailDto.setPositionNameIn(detail.getTargetPositionName()); // 入库货位
      detailDto.setPositionNameOut(detail.getPositionName());  // 出库货位
      detailDto.setInventoryId(Convert.toLong(detail.getSourceDetailId()));

      return detailDto;
    }).toList();

    if (storageScanPositionTransferBo.getTransferType() == TransferTypeEnum.PC_STACKING_IN) {
      // PC码盘入库
      holderSourceTypeEnum = HolderSourceTypeEnum.PC_STACKING_IN;
      sourceTypeEnum_Out = InventorySourceTypeEnum.PC_STACKING_OUT;
      sourceTypeEnum_In = InventorySourceTypeEnum.PC_STACKING_IN;
    } else if (storageScanPositionTransferBo.getTransferType() == TransferTypeEnum.PC_PICKING_SHELVE) {
      // PC下架回拣
      holderSourceTypeEnum = HolderSourceTypeEnum.PC_PICKING_SHELVE;
      sourceTypeEnum_Out = InventorySourceTypeEnum.PC_ORDER_PICKING_OUT;
      sourceTypeEnum_In = InventorySourceTypeEnum.PC_ORDER_PICKING_IN;
    } else if (storageScanPositionTransferBo.getTransferType() == TransferTypeEnum.ATTRIBUTE_CHANGE) {
      // 属性转换
      holderSourceTypeEnum = HolderSourceTypeEnum.ATTRIBUTE_CHANGE;
      sourceTypeEnum_Out = InventorySourceTypeEnum.ATTRIBUTE_CHANGE_OUT;
      sourceTypeEnum_In = InventorySourceTypeEnum.ATTRIBUTE_CHANGE_IN;
    } else if (storageScanPositionTransferBo.getTransferType() == TransferTypeEnum.PDA_POSITION_TRANSFTER) {
      // PDA货位转移
      holderSourceTypeEnum = HolderSourceTypeEnum.PDA_STORAGE_POSITION_TRANSFTER;
      sourceTypeEnum_Out = InventorySourceTypeEnum.PDA_STORAGE_POSITION_TRANSFTER_OUT;
      sourceTypeEnum_In = InventorySourceTypeEnum.PDA_STORAGE_POSITION_TRANSFTER_IN;
    } else if (storageScanPositionTransferBo.getTransferType() == TransferTypeEnum.PDA_ORDER_PICKING_RETURN) {
      // PDA下架回拣
      holderSourceTypeEnum = HolderSourceTypeEnum.PDA_ORDER_PICKING_RETURN;
      sourceTypeEnum_Out = InventorySourceTypeEnum.PDA_ORDER_PICKING_OUT;
      sourceTypeEnum_In = InventorySourceTypeEnum.PDA_ORDER_PICKING_IN;
    }

    // 分拣库存
    R<List<CommonDetailDto>> sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, holderSourceTypeEnum);
    Assert.isFalse(!sortingResult.isResult(), "分拣失败：" + sortingResult.getMsg());

    // 进行货位转移 - 出库
    R<List<CommonDetailDto>> outResult = inventoryCommonService.out(commonMainDto, commonDetailDtoList, sourceTypeEnum_Out);
    // 出库 - 消减SN处理
    for (var item : commonDetailDtoList) {

      if (B.isEqual(item.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        List<String> snList = StringUtils.splitList(item.getSingleSignCode());

        // 校验SN数量
        if (!B.isEqual(snList.size(), item.getOutQuantity())) {
          throw new ServiceException(item.getProductModel() + "扫描的SN数量不正确");
        }

        // 下架回拣时，必须是下架理货位的库存
        List<PositionTypeEnum> positionTypeEnumList = null;
        if (holderSourceTypeEnum == HolderSourceTypeEnum.PC_PICKING_SHELVE) {
          positionTypeEnumList = List.of(PositionTypeEnum.UNLOADING);
        }
        // 校验SN是否有效，
        List<String> invalidSnList = coreInventorySnService.getInvalidSnList(storageScanPositionTransferBo.getStorageId(), snList, positionTypeEnumList);
        Assert.isFalse(!invalidSnList.isEmpty(), "SN【" + StringUtils.join(invalidSnList, ",") + "】无效！");
        coreInventorySnService.updateInvalid(commonMainDto.getStorageId(), snList);
      }
    }

    List<CommonDetailDto> detailDtoList = outResult.getData();
    // 进行货位转移 - 入库
    inventoryCommonService.in(commonMainDto, detailDtoList, sourceTypeEnum_In);
    // 入库 - 增加SN处理
    for (CommonDetailDto item : commonDetailDtoList) {
      if (B.isEqual(item.getIsManageSn(), EnableEnum.ENABLE.getId())) {
        List<String> snList = StringUtils.splitList(item.getSingleSignCode());

        List<CoreInventorySn> coreInventorySnList = snList.stream().map(sn -> {
          CoreInventorySn coreInventorySn = BeanUtil.copyProperties(item, CoreInventorySn.class);
          coreInventorySn.setEnable(EnableEnum.ENABLE.getId());
          coreInventorySn.setPositionName(item.getPositionNameIn());
          coreInventorySn.setSnNo(sn);
          // 绑定当前库存ID
          CommonDetailDto commonDetailDto = detailDtoList.stream().filter(f -> StringUtils.equals(f.getProductCode(), item.getProductCode())).findFirst().orElse(null);
          if (ObjectUtil.isNotNull(commonDetailDto)) {
            coreInventorySn.setInventoryId(commonDetailDto.getInventoryId());
          }

          Assert.isFalse(ObjectUtil.isNull(coreInventorySn.getStorageId()), "SN仓库不能为空！");
          Assert.isFalse(ObjectUtil.isNull(coreInventorySn.getConsignorId()), "SN货主不能为空！");

          return coreInventorySn;
        }).toList();
        coreInventorySnService.saveBatch(coreInventorySnList);
      }
    }
    //#endregion

    //#region 更新转移到状态和求和
    var updateEntity = new StoragePositionTransfer();
    updateEntity.setTransferId(storagePositionTransfer.getTransferId());
    updateEntity.setAuditor(LoginHelper.getNickname());
    updateEntity.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
    updateEntity.setAuditDate(DateUtil.date());
    updateEntity.setTansfterStatus(StoragePositionStransferStatusEnum.FINISHED.getName());

    // 求和
    updateEntity.setTotalWeight(StreamUtils.sum(detailList, ScanPositionTransferDetailBo::getRowWeight));
    updateEntity.setTotalNetWeight(StreamUtils.sum(detailList, ScanPositionTransferDetailBo::getNetWeight));
    updateEntity.setTotalPurchaseAmount(StreamUtils.sum(detailList, ScanPositionTransferDetailBo::getPurchaseAmount));
    updateEntity.setTotalQuantity(StreamUtils.sum(detailList, ScanPositionTransferDetailBo::getFinishedQuantity));
    this.updateById(updateEntity);
    //#endregion

    return R.ok("分拣成功");
  }
  //#endregion

  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {

    for (var detail : detailList) {
      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), holderSourceTypeEnum);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StoragePositionTransferDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StoragePositionTransferDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StoragePositionTransferDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StoragePositionTransferDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StoragePositionTransferDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StoragePositionTransferDetail::getTransferDetailId, detail.getDetailId());
      storagePositionTransferDetailService.update(detailLambdaUpdateWrapper);
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
    LambdaUpdateWrapper<StoragePositionTransfer> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StoragePositionTransfer::getSortingStatus, sortingStatusEnum.getId())
      .set(StoragePositionTransfer::getSortingDate, DateUtil.date())
      .eq(StoragePositionTransfer::getTransferId, mainID);
    this.update(orderLambdaUpdateWrapper);
  }
  //#endregion

  //#region 自定义分拣过滤器
  @Override
  public List<CoreInventoryComposeVo> customSortingFilter(CommonMainDto mainInfo, CommonDetailDto detailInfo, List<CoreInventoryComposeVo> inventoryList) {
    // 在这里写
    // 出库单明细集合
    List<StoragePositionTransferDetail> outOrderDetailList = storagePositionTransferDetailService.selectListByMainId(mainInfo.getMainId());


    // 指定拍号
    if (StringUtils.isNotEmpty(detailInfo.getPlateCode())) {
      inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPlateCode(), detailInfo.getPlateCode())).toList();
    }

    // 指定批次号
    if (StringUtils.isNotEmpty(detailInfo.getBatchNumber())) {
      inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getBatchNumber(), detailInfo.getBatchNumber())).toList();
    }

    // 指定唯一码
    if (StringUtils.isNotEmpty(detailInfo.getSingleSignCode())) {
      inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getSingleSignCode(), detailInfo.getSingleSignCode())).toList();
    }

    // 指定库存ID
    if (ObjectUtil.isNotEmpty(detailInfo.getInventoryId())) {
      inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getInventoryId(), detailInfo.getInventoryId())).toList();
    }

    // 生产日期
    if (ObjectUtil.isNotEmpty(detailInfo.getProduceDate())) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      inventoryList = inventoryList.stream()
        .filter(f -> ObjectUtil.isNotNull(f.getProduceDate())
          && ObjectUtil.equals(formatter.format(f.getProduceDate()), formatter.format(detailInfo.getProduceDate()))
        )
        .toList();
    }
    return inventoryList;
  }
  //#endregion
}
