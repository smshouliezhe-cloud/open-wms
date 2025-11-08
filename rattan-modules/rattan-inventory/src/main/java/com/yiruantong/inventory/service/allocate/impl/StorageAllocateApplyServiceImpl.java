package com.yiruantong.inventory.service.allocate.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyBo;
import com.yiruantong.inventory.domain.allocate.vo.ApplySortingRuleVo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyVo;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateApplyMapper;
import com.yiruantong.inventory.service.allocate.IApplySortingRuleService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyDetailService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyStatusHistoryService;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 调拨申请单Service业务层处理
 *
 * @author YRT
 * @date 2023-12-19
 */
@RequiredArgsConstructor
@Service
public class StorageAllocateApplyServiceImpl extends ServiceImplPlus<StorageAllocateApplyMapper, StorageAllocateApply, StorageAllocateApplyVo, StorageAllocateApplyBo> implements IStorageAllocateApplyService, IInventoryBaseService {
  private final IStorageAllocateApplyStatusHistoryService storageAllocateApplyStatusHistoryService;
  private final IStorageAllocateApplyDetailService storageAllocateApplyDetailService;
  private final IInventoryCommonService inventoryCommonService;

  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IApplySortingRuleService applySortingRuleService;


  //#region  批量审核
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StorageAllocateApply storageAllocateApplyInfo = this.getById(id);
      Assert.isFalse(StrUtil.equals(StorageAllocateApplyStatusEnum.SUCCESS.getName(), storageAllocateApplyInfo.getApplyStatus()), "单据已审核不允许在审核");

      Assert.isTrue(StrUtil.equals(StorageAllocateApplyStatusEnum.NEWED.getName(), storageAllocateApplyInfo.getApplyStatus()), "只有新建的单据才允许审核");

      //查询明细信息
      List<StorageAllocateApplyDetail> detailList = storageAllocateApplyDetailService.selectListByMainId(id);
      inventoryCommonService.setBizService(this);

      //#region     分配库存
      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(storageAllocateApplyInfo, CommonMainDto.class);
      commonMainDto.setMainId(storageAllocateApplyInfo.getAllocateApplyId());
      commonMainDto.setMainCode(storageAllocateApplyInfo.getAllocateApplyCode());

      // 构建DTO数据 - 明细集合
      List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(m -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(m, CommonDetailDto.class);
        detailDto.setMainId(m.getAllocateApplyId()); // 主表ID
        detailDto.setDetailId(m.getAllocateApplyDetailId()); // 明细ID
        detailDto.setInQuantity(m.getApplyQuantity());  // 入库数量
        detailDto.setOutQuantity(m.getApplyQuantity()); // 出库数量
        detailDto.setPositionNameIn(m.getPositionName()); // 入库货位
        detailDto.setPositionNameOut(m.getPositionName());  // 出库货位
        detailDto.setInStorageDate(m.getInStorageDate());  // 入库时间
        detailDto.setStorageId(commonMainDto.getStorageId());
        detailDto.setStorageName(commonMainDto.getStorageName());
        detailDto.setConsignorId(commonMainDto.getConsignorId());
        detailDto.setConsignorCode(commonMainDto.getConsignorCode());
        detailDto.setConsignorName(commonMainDto.getConsignorName());
        detailDto.setBatchNumber(null);
        return detailDto;
      }).toList();
      //查询枚举
      HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(storageAllocateApplyInfo.getOrderType());
      Assert.isFalse(holderSourceTypeEnum == null, "未找到调拨申请单的枚举");
      // 分拣库存

      var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, holderSourceTypeEnum);

      // 不是完全分拣成功的，清空占位
      if (!sortingResult.isResult()) {
        coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), id);
        //添加轨迹
        storageAllocateApplyStatusHistoryService.addHistoryInfo(storageAllocateApplyInfo, StorageAllocateApplyActionEnum.SUCCESS, StorageAllocateApplyStatusEnum.NEWED, StorageAllocateApplyStatusEnum.NEWED, "订单缺货");

        //修改调拨申请单状态
        LambdaUpdateWrapper<StorageAllocateApplyDetail> wrapperDetail = new LambdaUpdateWrapper<>();
        wrapperDetail.set(StorageAllocateApplyDetail::getSortingStatus, SortingStatusEnum.NONE.getId())
          .eq(StorageAllocateApplyDetail::getAllocateApplyId, id);
        storageAllocateApplyDetailService.update(wrapperDetail);

      }
      Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());
      //#endregion

      //修改调拨申请单状态
      LambdaUpdateWrapper<StorageAllocateApply> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(StorageAllocateApply::getAuditing, 2L)
        .set(StorageAllocateApply::getAuditDate, DateUtils.getNowDate())
        .set(StorageAllocateApply::getAuditor, LoginHelper.getNickname())
        .set(StorageAllocateApply::getApplyStatus, StorageAllocateApplyStatusEnum.SUCCESS.getName())
        .eq(StorageAllocateApply::getAllocateApplyId, id);
      this.update(wrapper);
      //添加轨迹
      storageAllocateApplyStatusHistoryService.addHistoryInfo(storageAllocateApplyInfo, StorageAllocateApplyActionEnum.SUCCESS, StorageAllocateApplyStatusEnum.NEWED, StorageAllocateApplyStatusEnum.SUCCESS);


      detailList = storageAllocateApplyDetailService.selectListByMainId(id);
      // 入库单状态处理
      if (detailList.stream().allMatch(f -> NumberUtil.equals(f.getSortingStatus(), Convert.toLong(2)))) {
        // 完全上架
        this.updateAllocateStatus(storageAllocateApplyInfo, SortingStatusEnum.ASSIGNED);

      } else if (
        detailList.stream().anyMatch(f -> B.isGreater(f.getLackStorage()) && B.isLess(f.getLackStorage(), f.getApplyQuantity()))) {
        // 部分上架
        this.updateAllocateStatus(storageAllocateApplyInfo, SortingStatusEnum.LACK);
      }

    }

    return R.ok("审核成功");
  }

  //#endregion

  //#region  保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<StorageAllocateApplyBo> saveEditorBo, EditorVo<StorageAllocateApplyVo> editor) {
    Boolean isAdd = saveEditorBo.isAdd();
    if (isAdd) {
      //添加轨迹信息
      storageAllocateApplyStatusHistoryService.addHistoryInfo(
        (BeanUtil.copyProperties(editor.getMaster(), StorageAllocateApply.class)), StorageAllocateApplyActionEnum.NEWED, StorageAllocateApplyStatusEnum.NEWED);
    }
  }
  //#endregion

  //#region 开启
  @Override
  public R<Void> open(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);

    for (Long id : ids) {
      StorageAllocateApply storageAllocateApplyInfo = this.getById(id);
      Assert.isTrue(StrUtil.equals(StorageAllocateApplyStatusEnum.STOP.getName(), storageAllocateApplyInfo.getApplyStatus()), "只有终止的单据才允许开启");

      //修改调拨申请单状态
      LambdaUpdateWrapper<StorageAllocateApply> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(StorageAllocateApply::getAuditing, null)
        .set(StorageAllocateApply::getAuditDate, null)
        .set(StorageAllocateApply::getAuditor, null)
        .set(StorageAllocateApply::getApplyStatus, StorageAllocateApplyStatusEnum.NEWED.getName())
        .eq(StorageAllocateApply::getAllocateApplyId, id);

      this.update(wrapper);
      //添加轨迹
      storageAllocateApplyStatusHistoryService.addHistoryInfo(storageAllocateApplyInfo, StorageAllocateApplyActionEnum.OPEN, StorageAllocateApplyStatusEnum.STOP, StorageAllocateApplyStatusEnum.NEWED);
    }
    return R.ok("开启成功");
  }
  //#endregion

  //#region 终止
  @Override
  public R<Void> stop(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);

    for (Long id : ids) {
      StorageAllocateApply storageAllocateApplyInfo = this.getById(id);
      Assert.isTrue(StrUtil.equals(StorageAllocateApplyStatusEnum.SUCCESS.getName(), storageAllocateApplyInfo.getApplyStatus()), "只有审核成功的单据才允许终止");
      Assert.isFalse(StrUtil.equals(StorageAllocateApplyStatusEnum.TO_OUT_ORDER.getName(), storageAllocateApplyInfo.getOrderType()), "已转出库的单据不允许在终止");

      //查询枚举
      HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(storageAllocateApplyInfo.getOrderType());
      Assert.isFalse(holderSourceTypeEnum == null, "未找到调拨申请单的枚举");

      //清除站位信息
      coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), id);
      //修改调拨申请单状态
      LambdaUpdateWrapper<StorageAllocateApply> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(StorageAllocateApply::getApplyStatus, StorageAllocateApplyStatusEnum.STOP.getName())
        .set(StorageAllocateApply::getSortingStatus, SortingStatusEnum.NONE.getId())
        .eq(StorageAllocateApply::getAllocateApplyId, id);
      this.update(wrapper);

      //修改调拨申请单状态
      LambdaUpdateWrapper<StorageAllocateApplyDetail> wrapperDetail = new LambdaUpdateWrapper<>();
      wrapperDetail.set(StorageAllocateApplyDetail::getSortingStatus, SortingStatusEnum.NONE.getId())
        .set(StorageAllocateApplyDetail::getLackStorage, BigDecimal.ZERO)
        .eq(StorageAllocateApplyDetail::getAllocateApplyId, id);
      storageAllocateApplyDetailService.update(wrapperDetail);
      //添加轨迹
      storageAllocateApplyStatusHistoryService.addHistoryInfo(storageAllocateApplyInfo, StorageAllocateApplyActionEnum.STOP, StorageAllocateApplyStatusEnum.SUCCESS, StorageAllocateApplyStatusEnum.STOP);
    }
    return R.ok("终止成功");
  }
  //#endregion

  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {
    StorageAllocateApply storageAllocateApplyInfo = this.getById(detailList.get(0).getMainId());
    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(storageAllocateApplyInfo.getOrderType());
    Assert.isFalse(holderSourceTypeEnum == null, "未找到调拨申请单的枚举");
    for (var detail : detailList) {

      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), holderSourceTypeEnum);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StorageAllocateApplyDetail> wrapper = new LambdaUpdateWrapper<>();
      wrapper
        .set(StorageAllocateApplyDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StorageAllocateApplyDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StorageAllocateApplyDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StorageAllocateApplyDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StorageAllocateApplyDetail::getAllocateApplyDetailId, detail.getDetailId());
      storageAllocateApplyDetailService.update(wrapper);
    }
  }
  //#endregion

  //#region 更新分拣状态
  @Override
  public boolean updateAllocateStatus(StorageAllocateApply storageAllocateApplyInfo, SortingStatusEnum sortingStatus) {
    LambdaUpdateWrapper<StorageAllocateApply> lambda = new UpdateWrapper<StorageAllocateApply>().lambda();
    lambda.set(StorageAllocateApply::getSortingStatus, sortingStatus.getId())
      .set(StorageAllocateApply::getAuditor, LoginHelper.getNickname())
      .set(StorageAllocateApply::getAuditDate, DateUtil.date())
      .set(StorageAllocateApply::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .eq(StorageAllocateApply::getAllocateApplyId, storageAllocateApplyInfo.getAllocateApplyId());

    return this.update(lambda);//更新状态
  }
  //#endregion

//#region 转到出库单


  //#region 设置分拣规则
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> setSortingRule(Map<String, Object> map) {

    LambdaQueryWrapper<ApplySortingRule> queryWrapper222 = new LambdaQueryWrapper<>();
    queryWrapper222.eq(ApplySortingRule::getProductCode, map.get("productCode"))
      .eq(ApplySortingRule::getAllocateApplyId, map.get("orderId"))
      .eq(ApplySortingRule::getAllocateApplyDetailId, map.get("orderDetailId"));
    List<ApplySortingRuleVo> sortingRule222 = applySortingRuleService.selectList(queryWrapper222);


    for (var item : sortingRule222) {
      if (!Objects.equals(map.get("batchNumber"), "") && !Objects.equals(item.getBatchNumber(), "") && ObjectUtil.isNotNull(item.getBatchNumber())) {
        throw new ServiceException("批次号已存在!");
      }
      if (!Objects.equals(map.get("produceDate"), "") && ObjectUtil.isNotNull(map.get("produceDate")) && !Objects.equals(item.getProduceDate(), "") && ObjectUtil.isNotNull(item.getProduceDate())) {
        throw new ServiceException("生产日期已存在!");
      }
      if (!Objects.equals(map.get("positionName"), "") && !Objects.equals(item.getPositionName(), "") && ObjectUtil.isNotNull(item.getPositionName())) {
        throw new ServiceException("拣货货位已存在!");
      }
      if (!Objects.equals(map.get("plateCode"), "") && !Objects.equals(item.getPlateCode(), "") && ObjectUtil.isNotNull(item.getPlateCode())) {
        throw new ServiceException("托盘号已存在!");
      }
      if (!Objects.equals(map.get("singleSignCode"), "") && !Objects.equals(item.getSingleSignCode(), "") && ObjectUtil.isNotNull(item.getSingleSignCode())) {
        throw new ServiceException("唯一码已存在!");
      }
      if (!Objects.equals(map.get("inventoryId"), "") && !Objects.equals(item.getInventoryId(), "") && ObjectUtil.isNotNull(item.getInventoryId())) {
        throw new ServiceException("库存ID已存在!");
      }
    }

    ApplySortingRule applySortingRule = new ApplySortingRule();
    applySortingRule.setBatchNumber(Convert.toStr(map.get("batchNumber")));
    applySortingRule.setConsignorId(Convert.toLong(map.get("consignorId")));
    applySortingRule.setConsignorCode(Convert.toStr(map.get("consignorCode")));
    applySortingRule.setConsignorName(Convert.toStr(map.get("consignorName")));
    applySortingRule.setCreateTime(new Date());
    applySortingRule.setAllocateApplyId(Convert.toLong(map.get("orderId")));
    applySortingRule.setAllocateApplyDetailId(Convert.toLong(map.get("orderDetailId")));
    applySortingRule.setAllocateApplyCode(Convert.toStr(map.get("orderCode")));
    applySortingRule.setPlateCode(Convert.toStr(map.get("plateCode")));
    applySortingRule.setPositionName(Convert.toStr(map.get("positionName")));
    applySortingRule.setProduceDate(Convert.toDate(map.get("produceDate")));
    applySortingRule.setProductId(Convert.toLong(map.get("productId")));
    applySortingRule.setProductCode(Convert.toStr(map.get("productCode")));
    applySortingRule.setSingleSignCode(Convert.toStr(map.get("singleSignCode")));
    applySortingRule.setInventoryId(Convert.toLong(map.get("inventoryId")));
    applySortingRule.setStorageId(Convert.toLong(map.get("storageId")));
    applySortingRule.setStorageName(Convert.toStr(map.get("storageName")));
    applySortingRuleService.save(applySortingRule);

    return R.ok(null);
  }
  //#endregion


  //#region 获取分拣列表
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<ApplySortingRule> getSortingRule(Map<String, Object> map) {
    // 获得分拣规则
    LambdaQueryWrapper<ApplySortingRule> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(ApplySortingRule::getAllocateApplyDetailId, Convert.toLong(map.get("orderDetailId")));


    return applySortingRuleService.list(orderLambdaQueryWrapper);
  }
  //#endregion


  //#region 关闭分拣规则
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> deleteSortingRule(Map<String, Object> map) {
    // 删除分拣规则
    LambdaQueryWrapper<ApplySortingRule> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(ApplySortingRule::getRuleId, Convert.toLong(map.get("ruleId")));

    applySortingRuleService.remove(orderLambdaQueryWrapper);
    return R.ok(null);
  }

  @Override
  public StorageAllocateApply calibrationApply(String sourceId, String sourceCode, String type) {
    LambdaQueryWrapper<StorageAllocateApply> storageAllocateApplyLqw = new LambdaQueryWrapper<>();
    storageAllocateApplyLqw.eq(StorageAllocateApply::getAllocateApplyId, sourceId)
      .eq(StorageAllocateApply::getAllocateApplyCode, sourceCode);

    StorageAllocateApply allocateApplyInfo = this.getOne(storageAllocateApplyLqw);

    Assert.isTrue(ObjectUtil.isNotEmpty(allocateApplyInfo), "未找到对应的调拨单！");
    if (StrUtil.equals(type, "in")) {
      if (StrUtil.equals(allocateApplyInfo.getApplyStatus(), StorageAllocateApplyStatusEnum.IN_FINISHED.getName())) {
        return null;
      }
    } else {
      if (StrUtil.equals(allocateApplyInfo.getApplyStatus(), StorageAllocateApplyStatusEnum.OUT_FINISHED.getName())) {
        return null;
      }
    }

    return allocateApplyInfo;
  }
  //endregion


}
