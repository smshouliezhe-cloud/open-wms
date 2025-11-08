package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.inventory.StorageCheckStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageProfitLossStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.operation.StorageCheck;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;
import com.yiruantong.inventory.domain.operation.StorageProfitLoss;
import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageProfitLossBo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossVo;
import com.yiruantong.inventory.mapper.operation.StorageProfitLossMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageCheckDetailService;
import com.yiruantong.inventory.service.operation.IStorageCheckService;
import com.yiruantong.inventory.service.operation.IStorageProfitLossDetailService;
import com.yiruantong.inventory.service.operation.IStorageProfitLossService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 盈亏单Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageProfitLossServiceImpl extends ServiceImplPlus<StorageProfitLossMapper, StorageProfitLoss, StorageProfitLossVo, StorageProfitLossBo> implements IStorageProfitLossService, IInventoryBaseService {

  private final IInventoryCommonService inventoryCommonService;
  private final IStorageProfitLossDetailService storageProfitLossDetailService;

  private final ICoreInventoryHolderService coreInventoryHolderService;

  //#region multiAuditing
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    Assert.isFalse(ids.isEmpty(), "至少选择一条数据");

    for (Long id : ids) {
      StorageProfitLoss profitLoss = this.getById(id);
      Assert.isTrue((ObjectUtil.equal(profitLoss.getLossStatus(), StorageProfitLossStatusEnum.NEWED.getName()) ||
          ObjectUtil.equal(profitLoss.getLossStatus(), StorageProfitLossStatusEnum.REVIEWED.getName())),
        profitLoss.getProfitLossCode() + "只有盈亏单状态为“新建”和“已复盘”时，允许操作审核");

      //修改数据
      LambdaUpdateWrapper<StorageProfitLoss> lambda = new UpdateWrapper<StorageProfitLoss>().lambda();
      lambda.set(StorageProfitLoss::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
        .set(StorageProfitLoss::getAuditDate, DateUtils.getNowDate())
        .set(StorageProfitLoss::getAuditor, LoginHelper.getNickname())
        .eq(StorageProfitLoss::getProfitLossId, profitLoss.getProfitLossId());
      this.update(lambda);//提交

      //#endregion
    }
    return R.ok("审核成功");
  }

  //#region 生成盈亏单
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> createCheck(Map<String, Object> map) {
    Long profitLossId = Convert.toLong(map.get("profitLossId"));

    IStorageProfitLossDetailService storageProfitLossDetailService = SpringUtils.getBean(IStorageProfitLossDetailService.class);
    StorageProfitLoss storageProfitLoss = this.getById(profitLossId);

    List<StorageProfitLossDetail> storageProfitLossDetail = storageProfitLossDetailService.selectListByMainId(storageProfitLoss.getProfitLossId());
    IStorageCheckService storageCheckService = SpringUtils.getBean(IStorageCheckService.class);
    //#region 生成盘点单
    StorageCheck storageCheckInfo = new StorageCheck();
    BeanUtil.copyProperties(storageProfitLoss, storageCheckInfo);
    storageCheckInfo.setCheckCode(DBUtils.getCodeRegular(MenuEnum.MENU_1601));
    storageCheckInfo.setProfitLossId(storageProfitLoss.getProfitLossId());
    storageCheckInfo.setApplyDate(DateUtils.getNowDate());
    storageCheckInfo.setCheckStatus(StorageCheckStatusEnum.INREVIEW.getName());
    storageCheckInfo.setSortingStatus(BigDecimal.ONE.longValue());
    storageCheckInfo.setCheckId(null);
    storageCheckInfo.setCheckType("复盘");
    storageCheckInfo.setFromCheckCode(storageProfitLoss.getProfitLossCode());
    storageCheckInfo.setFromCheckId(storageProfitLoss.getProfitLossId());
    storageCheckService.save(storageCheckInfo);


    IStorageCheckDetailService storageCheckDetailService = SpringUtils.getBean(IStorageCheckDetailService.class);
    for (StorageProfitLossDetail Detail : storageProfitLossDetail) {
      StorageCheckDetail storageCheckDetail = new StorageCheckDetail();
      BeanUtil.copyProperties(Detail, storageCheckDetail);
      storageCheckDetail.setCheckId(storageCheckInfo.getCheckId());
      storageCheckDetail.setCheckDetailId(null);
      storageCheckDetailService.save(storageCheckDetail);
    }
    //#endregion

    //修改数据
    LambdaUpdateWrapper<StorageProfitLoss> lambda = new UpdateWrapper<StorageProfitLoss>().lambda();
    lambda.set(StorageProfitLoss::getLossStatus, StorageProfitLossStatusEnum.REVIEWED_CENTER.getName())
      .eq(StorageProfitLoss::getProfitLossId, profitLossId);
    this.update(lambda);//提交

    return R.ok("生成复盘单成功");
  }
  //#endregion

  //#region 调整库存
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> adjustInventory(Map<String, Object> map) {


    Long profitLossId = Convert.toLong(map.get("profitLossId"));

    IStorageProfitLossDetailService storageProfitLossDetailService = SpringUtils.getBean(IStorageProfitLossDetailService.class);
    StorageProfitLoss storageProfitLoss = this.getById(profitLossId);

    List<StorageProfitLossDetail> storageProfitLossDetail = storageProfitLossDetailService.selectListByMainId(storageProfitLoss.getProfitLossId());

    List<String> status = new ArrayList<>();
    status.add(StorageProfitLossStatusEnum.NEWED.getName());
    status.add(StorageProfitLossStatusEnum.REVIEWED.getName());
    Assert.isTrue(status.stream().anyMatch(i -> StrUtil.equals(storageProfitLoss.getLossStatus(), i)), storageProfitLoss.getLossStatus() + "出库完成的，不允许重复操作");

    //#region 通用模块调用
    inventoryCommonService.setBizService(this);

    // 构建DTO数据 - 主表
    CommonMainDto commonMainDto = BeanUtil.copyProperties(storageProfitLoss, CommonMainDto.class);
    commonMainDto.setMainId(storageProfitLoss.getProfitLossId());
    commonMainDto.setMainCode(storageProfitLoss.getProfitLossCode());

    // 构建DTO数据 - 明细集合
    List<CommonDetailDto> commonDetailDtoList = storageProfitLossDetail.stream().map(detail -> {
      CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
      detailDto.setMainId(detail.getProfitLossId()); // 主表ID
      detailDto.setDetailId(detail.getProfitLossDetailId()); // 明细ID
      detailDto.setBillCode(storageProfitLoss.getProfitLossCode());
      if (!B.isEqual(detail.getReCheckDiff()) || !B.isEqual(detail.getCheckDiff())) {
        BigDecimal number = detail.getReCheckDiff();
        if (B.isEqual(number)) {
          number = detail.getCheckDiff();
        }
        if (B.isGreater(number)) {
          // 盘盈
          detailDto.setInQuantity(number);  // 入库数量
          detailDto.setOutQuantity(BigDecimal.ZERO); // 出库数量
          detailDto.setPositionNameIn(detail.getPositionName()); // 入库货位
          detailDto.setPositionNameOut(null);  // 出库货位

        } else {
          // 盘亏
          detailDto.setInQuantity(null);  // 入库数量
          detailDto.setOutQuantity(Convert.toBigDecimal(Math.abs(Convert.toInt(number)))); // 出库数量
          detailDto.setPositionNameIn(null); // 入库货位
          detailDto.setPositionNameOut(detail.getPositionName());  // 出库货位
        }
//        detailDto.setInventoryId(detail.getInventoryId());

        //根据库存ID查询供应商
        if (B.isGreater(detailDto.getInventoryId())) {
          ICoreInventoryService bean = SpringUtils.getBean(ICoreInventoryService.class);
          CoreInventory inventoryInfo = bean.getById(detailDto.getInventoryId());
          detailDto.setProviderId(inventoryInfo.getProviderId());
          detailDto.setProviderCode(inventoryInfo.getProviderCode());
          detailDto.setProviderShortName(inventoryInfo.getProviderShortName());
          detailDto.setSourceCode(inventoryInfo.getSourceCode());

        }

        return detailDto;
      }
      return null;
    }).toList();
    commonDetailDtoList = commonDetailDtoList.stream().filter(ObjectUtil::isNotNull).toList();
    // 分拣库存
    var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, HolderSourceTypeEnum.ADJUST_INVENTORY);
    Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());

    // 出库
    inventoryCommonService.out(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_ADJUST_INVENTORY_OUT);

    // 入库
    inventoryCommonService.in(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_ADJUST_INVENTORY_IN);
    //#endregion

    //#region 更新状态
    storageProfitLoss.setLossStatus(StorageProfitLossStatusEnum.ADJUSTED.getName());

    this.updateById(storageProfitLoss);
    //#endregion


    return R.ok("调整成功");
    //endregion
  }


  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {
    for (var detail : detailList) {
      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), HolderSourceTypeEnum.ADJUST_INVENTORY);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StorageProfitLossDetail> wrapper = new LambdaUpdateWrapper<>();
      wrapper
        .set(StorageProfitLossDetail::getLackStorage, lackStorage)
        .eq(StorageProfitLossDetail::getProfitLossDetailId, detail.getDetailId());
      storageProfitLossDetailService.update(wrapper);
    }
  }
  //#endregion
}
