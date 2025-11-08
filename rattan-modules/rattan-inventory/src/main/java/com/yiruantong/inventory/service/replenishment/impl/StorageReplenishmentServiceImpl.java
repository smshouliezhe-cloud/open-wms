package com.yiruantong.inventory.service.replenishment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageReplenishmentActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageReplenishmentStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.base.scan.ScanReplenishmentBo;
import com.yiruantong.inventory.domain.base.scan.ScanReplenishmentDetailBo;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.domain.replenishment.bo.StorageReplenishmentBo;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentVo;
import com.yiruantong.inventory.mapper.replenishment.StorageReplenishmentMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentDetailService;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 补货单Service业务层处理
 *
 * @author YRT
 * @date 2024-08-23
 */
@RequiredArgsConstructor
@Service
public class StorageReplenishmentServiceImpl extends ServiceImplPlus<StorageReplenishmentMapper, StorageReplenishment, StorageReplenishmentVo, StorageReplenishmentBo> implements IStorageReplenishmentService, IInventoryBaseService {
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventorySnService coreInventorySnService;
  private final IStorageReplenishmentDetailService storageReplenishmentDetailService;

  private HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.PC_REPLENISHMENT;
  private InventorySourceTypeEnum sourceTypeEnum_Out = InventorySourceTypeEnum.PC_REPLENISHMENT_OUT;
  private InventorySourceTypeEnum sourceTypeEnum_In = InventorySourceTypeEnum.PC_REPLENISHMENT_IN;

  //#region 补货单根据单据编号获取

  /**
   * 补货单根据单据编号获取
   *
   * @param replenishmentCode
   * @return
   */
  @Override
  public StorageReplenishment getByCode(String replenishmentCode) {
    LambdaQueryWrapper<StorageReplenishment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(StorageReplenishment::getReplenishmentCode, replenishmentCode);

    return this.getOne(lambdaQueryWrapper);
  }
  //#endregion

  //#region 补货审核自定义实现业务逻辑

  /**
   * 补货审核自定义实现业务逻辑
   *
   * @param ids 前端参数
   * @return
   */
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    LambdaQueryWrapper<StorageReplenishment> lineLambdaQueryWrapper = new LambdaQueryWrapper<>();
    lineLambdaQueryWrapper.eq(StorageReplenishment::getReplenishmentId, ids);
    StorageReplenishment orderInfo = this.getOne(lineLambdaQueryWrapper);

    if (ObjectUtil.isEmpty(orderInfo)) {
      throw new ServiceException("未获取到单据");
    }

    //修改数据
    LambdaUpdateWrapper<StorageReplenishment> lambda = new UpdateWrapper<StorageReplenishment>().lambda();
    lambda.set(StorageReplenishment::getAuditDate, DateUtils.getNowDate())
      .set(StorageReplenishment::getAuditor, LoginHelper.getNickname())
      .set(StorageReplenishment::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .set(StorageReplenishment::getBillStatus, AuditEnum.AUDITED_SUCCESS.getName())
      .eq(StorageReplenishment::getReplenishmentId, orderInfo.getReplenishmentId());
    this.update(lambda);//提交
    return R.ok("审核成功");

  }
  //#endregion

  //#region 分拣

  /**
   * 分拣
   *
   * @param map 前端参数
   * @return
   */
  @Override
  public R<Void> sorting(Map<String, Object> map) {
    Long replenishmentId = Convert.toLong(map.get("id"));
    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.PC_REPLENISHMENT;
    InventorySourceTypeEnum sourceTypeEnum_Out = InventorySourceTypeEnum.PC_REPLENISHMENT_OUT;
    InventorySourceTypeEnum sourceTypeEnum_In = InventorySourceTypeEnum.PC_REPLENISHMENT_IN;


    final StorageReplenishment storageReplenishment = this.getById(replenishmentId);

    //#region 通用模块调用
    inventoryCommonService.setBizService(this);
    CommonMainDto commonMainDto = BeanUtil.copyProperties(storageReplenishment, CommonMainDto.class);
    commonMainDto.setMainId(storageReplenishment.getReplenishmentId());
    commonMainDto.setMainCode(storageReplenishment.getReplenishmentCode());

    // 明细表数据
    final List<StorageReplenishmentDetail> detailList = storageReplenishmentDetailService.selectListByMainId(replenishmentId);

    List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(detail -> {
      CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
      detailDto.setMainId(detail.getReplenishmentId()); // 主表ID
      detailDto.setDetailId(detail.getReplenishmentDetailId()); // 明细ID
      detailDto.setBillCode(storageReplenishment.getReplenishmentCode());
      detailDto.setInQuantity(detail.getTransferQuantity());  // 入库数量
      detailDto.setOutQuantity(detail.getTransferQuantity()); // 出库数量
      detailDto.setPositionNameIn(detail.getPositionName()); // 入库货位
//        detailDto.setPositionNameOut(detail.getPositionName());  // 出库货位
      detailDto.setInventoryId(Convert.toLong(detail.getSourceDetailId()));

      return detailDto;
    }).toList();

    // 分拣库存
    R<List<CommonDetailDto>> sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, holderSourceTypeEnum);
    Assert.isFalse(!sortingResult.isResult(), "分拣失败：" + sortingResult.getMsg());

    return R.ok("分拣成功");
  }
  //#endregion

  //#region 开启
  @Override
  public R<Void> open(Map<String, Object> map) {
    try {
      StorageReplenishment orderInfo = this.getById(Convert.toLong(map.get("ids")));

      Assert.isTrue(orderInfo.getBillStatus().equals(StorageReplenishmentActionEnum.STOP.getName()), "只有终止的单据才允许开启");

      //修改数据
      LambdaUpdateWrapper<StorageReplenishment> lambda = new LambdaUpdateWrapper<>();
      lambda.set(StorageReplenishment::getAuditing, 0L)
        .set(StorageReplenishment::getAuditDate, null)
        .set(StorageReplenishment::getAuditor, null)
        .set(StorageReplenishment::getBillStatus, StorageReplenishmentStatusEnum.NEWED.getName())
        .eq(StorageReplenishment::getReplenishmentId, orderInfo.getReplenishmentId());
      this.update(lambda);//提交

      return R.ok("终止成功！");
    } catch (Exception exception) {

      return R.fail(exception.getMessage());
    }
  }
  //#endregion

  //#region 终止
  @Override
  public R<Void> stop(Map<String, Object> map) {
    try {
      StorageReplenishment orderInfo = this.getById(Convert.toLong(map.get("ids")));
      //修改数据
      LambdaUpdateWrapper<StorageReplenishment> lambda = new LambdaUpdateWrapper<>();
      lambda
        .set(StorageReplenishment::getBillStatus, StorageReplenishmentActionEnum.STOP.getName())
        .eq(StorageReplenishment::getReplenishmentId, orderInfo.getReplenishmentId());
      this.update(lambda);//提交

      return R.ok("终止成功！");
    } catch (Exception exception) {

      return R.fail(exception.getMessage());
    }
  }

//#endregion

  //#region 保存补货扫描

  /**
   * 保存补货扫描
   *
   * @param storageScanReplenishmentBo 保存数据
   * @return R 返回保存结果
   */
  //#region 保存补货扫描
  @Override
  public R<Void> saveReplenishment(ScanReplenishmentBo storageScanReplenishmentBo) {
    String replenishmentCode = storageScanReplenishmentBo.getReplenishmentCode();
    List<ScanReplenishmentDetailBo> replenishmentDetailBoList = storageScanReplenishmentBo.getDataList();
    cn.hutool.core.lang.Assert.isFalse(replenishmentDetailBoList.isEmpty(), "扫描明细不能为空！");

    //#region 验证数据
    for (ScanReplenishmentDetailBo mapItem : replenishmentDetailBoList) {
      String targetPositionName = mapItem.getTargetPositionName();
      String productCode = mapItem.getProductCode();
      cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isEmpty(targetPositionName), productCode + "目标货位不能为空！");
    }
    //#endregion

    // 查询补货单
    StorageReplenishment storageReplenishment = this.getByCode(replenishmentCode);
    cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isEmpty(storageReplenishment), replenishmentCode + "补货单号不存在！");


    // 补货单扫描
    replenishment(storageReplenishment, replenishmentDetailBoList, storageScanReplenishmentBo);

    return R.ok("货位转移成功");
  }
  //#endregion

  /**
   * @param storageReplenishment
   * @param detailList
   * @param storageScanReplenishmentBo
   * @return
   */
  @Override
  public R<Void> replenishment(StorageReplenishment storageReplenishment, List<ScanReplenishmentDetailBo> detailList, ScanReplenishmentBo storageScanReplenishmentBo) {

    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.PC_REPLENISHMENT;
    InventorySourceTypeEnum sourceTypeEnum_Out = InventorySourceTypeEnum.PC_REPLENISHMENT_OUT;
    InventorySourceTypeEnum sourceTypeEnum_In = InventorySourceTypeEnum.PC_REPLENISHMENT_IN;


    //#region 通用模块调用
    inventoryCommonService.setBizService(this);
    CommonMainDto commonMainDto = BeanUtil.copyProperties(storageReplenishment, CommonMainDto.class);
    commonMainDto.setMainId(storageReplenishment.getReplenishmentId());
    commonMainDto.setMainCode(storageReplenishment.getReplenishmentCode());

    // 明细表数据

    List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(detail -> {
      CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
      detailDto.setMainId(detail.getReplenishmentId()); // 主表ID
      detailDto.setDetailId(detail.getReplenishmentDetailId()); // 明细ID
      detailDto.setBillCode(storageReplenishment.getReplenishmentCode());
      detailDto.setInQuantity(detail.getFinishedQuantity());  // 入库数量
      detailDto.setOutQuantity(detail.getFinishedQuantity()); // 出库数量
      detailDto.setPositionNameIn(detail.getPositionName()); // 入库货位
//        detailDto.setPositionNameOut(detail.getPositionName());  // 出库货位
      detailDto.setInventoryId(Convert.toLong(detail.getSourceDetailId()));

      return detailDto;
    }).toList();

//    // 分拣库存
//    R<List<CommonDetailDto>> sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, holderSourceTypeEnum);
//    Assert.isFalse(!sortingResult.isResult(), "分拣失败：" + sortingResult.getMsg());

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
        // 校验SN是否有效，
        List<String> invalidSnList = coreInventorySnService.getInvalidSnList(storageReplenishment.getStorageId(), snList);
        cn.hutool.core.lang.Assert.isFalse(!invalidSnList.isEmpty(), "SN【" + StringUtils.join(invalidSnList, ",") + "】无效！");
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

          cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isNull(coreInventorySn.getStorageId()), "SN仓库不能为空！");
          cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isNull(coreInventorySn.getConsignorId()), "SN货主不能为空！");

          return coreInventorySn;
        }).toList();
        coreInventorySnService.saveBatch(coreInventorySnList);
      }
    }
    //#endregion

    //#region 更新补货单状态，数量
    // 查询补货单明细
    for (CommonDetailDto item : commonDetailDtoList) {
      LambdaUpdateWrapper<StorageReplenishmentDetail> detailLambdaQueryWrapper = new LambdaUpdateWrapper<>();
      detailLambdaQueryWrapper.set(StorageReplenishmentDetail::getOutQuantity, item.getOutQuantity())
        .eq(StorageReplenishmentDetail::getReplenishmentId, item.getMainId())
        .eq(StorageReplenishmentDetail::getReplenishmentDetailId, item.getDetailId());
      storageReplenishmentDetailService.update(detailLambdaQueryWrapper);
    }

    List<StorageReplenishmentDetail> replenishmentDetails = storageReplenishmentDetailService.selectListByMainId(storageReplenishment.getReplenishmentId());

    // 预补货数量总和
    var totalQuantity = replenishmentDetails.stream().map(StorageReplenishmentDetail::getTransferQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    // 已补货数量总和
    var outQuantity = replenishmentDetails.stream().map(StorageReplenishmentDetail::getOutQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);


    LambdaUpdateWrapper<StorageReplenishment> lambda1 = new UpdateWrapper<StorageReplenishment>().lambda();
    if (B.isGreaterOrEqual(outQuantity, totalQuantity)) {
      //已入库
      lambda1.set(StorageReplenishment::getBillStatus, StorageReplenishmentStatusEnum.COMPLETE_REPLENISHMENT.getName()); // 完全补货
    } else if (B.isGreater(outQuantity) && B.isLess(outQuantity, totalQuantity)) {
      // 部分入库
      lambda1.set(StorageReplenishment::getBillStatus, StorageReplenishmentStatusEnum.PARTIAL_REPLENISHMENT.getName()); // 部分补货
    }
    lambda1.eq(StorageReplenishment::getReplenishmentCode, storageReplenishment.getReplenishmentCode());
    this.update(lambda1);

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
      LambdaUpdateWrapper<StorageReplenishmentDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StorageReplenishmentDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StorageReplenishmentDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StorageReplenishmentDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StorageReplenishmentDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StorageReplenishmentDetail::getReplenishmentDetailId, detail.getDetailId());
      storageReplenishmentDetailService.update(detailLambdaUpdateWrapper);
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
    LambdaUpdateWrapper<StorageReplenishment> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StorageReplenishment::getSortingStatus, sortingStatusEnum.getId())
      .set(StorageReplenishment::getSortingDate, DateUtil.date())
      .eq(StorageReplenishment::getReplenishmentId, mainID);
    this.update(orderLambdaUpdateWrapper);

    // 获取占位数据
    final List<CoreInventoryHolder> coreInventoryHolders = coreInventoryHolderService.selectHolderList(mainID, HolderSourceTypeEnum.PC_REPLENISHMENT);

    // 更新补货明细中的原货位
    final List<StorageReplenishmentDetail> replenishmentDetailList = storageReplenishmentDetailService.selectListByMainId(mainID);
    for (StorageReplenishmentDetail detail : replenishmentDetailList) {
      // 获取货位
      final String positionNames = coreInventoryHolders.stream().filter(holder -> ObjectUtil.equals(holder.getDetailId(), detail.getReplenishmentDetailId())).map(CoreInventoryHolder::getPositionName).collect(Collectors.joining(","));

      // 更新补货单明细中的原货位
      detail.setSortPositionName(positionNames);
      storageReplenishmentDetailService.updateById(detail);
    }
  }
  //#endregion

  @Override
  public List<CoreInventoryComposeVo> customSortingFilter(CommonMainDto mainInfo, CommonDetailDto detailInfo, List<CoreInventoryComposeVo> inventoryList) {
    // 只允许分拣存储货位库存
    inventoryList = inventoryList.stream().filter(f -> ObjectUtil.equals(f.getPositionType(), PositionTypeEnum.STORAGE.getId())).toList();

    return IInventoryBaseService.super.customSortingFilter(mainInfo, detailInfo, inventoryList);
  }
}
