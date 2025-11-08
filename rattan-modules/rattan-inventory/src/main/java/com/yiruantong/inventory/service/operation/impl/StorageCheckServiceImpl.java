package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.inventory.StorageCheckStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageCheckTypeEnum;
import com.yiruantong.common.core.enums.inventory.StorageProfitLossStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryCreateCheckVo;
import com.yiruantong.inventory.domain.operation.StorageCheck;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;
import com.yiruantong.inventory.domain.operation.StorageProfitLoss;
import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageCheckBo;
import com.yiruantong.inventory.domain.operation.vo.CreateStorageCheckVo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckVo;
import com.yiruantong.inventory.mapper.operation.StorageCheckMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageCheckDetailService;
import com.yiruantong.inventory.service.operation.IStorageCheckService;
import com.yiruantong.inventory.service.operation.IStorageProfitLossDetailService;
import com.yiruantong.inventory.service.operation.IStorageProfitLossService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 盘点单Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageCheckServiceImpl extends ServiceImplPlus<StorageCheckMapper, StorageCheck, StorageCheckVo, StorageCheckBo> implements IStorageCheckService {
  private final ICoreInventoryService coreInventoryService;
  private final IBaseStorageService baseStorageService;
  private final IBaseConsignorService baseConsignorService;
  private final IStorageCheckDetailService storageCheckDetailService;
  private final IStorageProfitLossService storageProfitLossService;
  private final IStorageProfitLossDetailService storageProfitLossDetailService;


  //#region 创建盘点单 createOrderCheck
  @Override
  public R<Void> createOrderCheck(CreateStorageCheckVo createStorageCheckVo) {

    //#region 构建查询数据
    List<QueryBo> queryBo = createStorageCheckVo.getAllWhere();
    if (!ObjectUtil.isEmpty(createStorageCheckVo.getInventoryIds())) {
      QueryBo query = new QueryBo();
      query.setColumn("inventoryId");
      query.setValues(createStorageCheckVo.getInventoryIds());
      query.setQueryType(QueryTypeEnum.IN);
      query.setDataType(DataTypeEnum.STRING);
      query.setOr(false);
      queryBo.add(query);
    }

    QueryBo query = new QueryBo();
    query.setColumn("productStorage");
    query.setValues("0");
    query.setQueryType(QueryTypeEnum.GT);
    query.setDataType(DataTypeEnum.BIGDECIMAL);
    query.setOr(false);
    queryBo.add(query);

    PageQuery pageQuery = new PageQuery();
    pageQuery.setMenuId(MenuEnum.MENU_1044.getId());
    pageQuery.setPrefixRouter("/inventory/core/inventory");
    pageQuery.setQueryBoList(queryBo);
    pageQuery.setPageIndex(1);
    pageQuery.setPageSize(1000);
    pageQuery.setOrderByColumn("t.positionName");
    pageQuery.setTableName("core_inventory");
    pageQuery.setIsAsc("asc");
    //#endregion
    //查询生成盘点单数据
    TableDataInfo<CoreInventoryCreateCheckVo> detailInfo = coreInventoryService.selectCoreInventoryCreateCheckList(pageQuery);

    BaseStorage storageInfo = baseStorageService.getById(createStorageCheckVo.getStorageId());
    BaseConsignor consignorInfo = baseConsignorService.getById(createStorageCheckVo.getConsignorId());

    //#region 生成盘点单
    StorageCheck checkInfo = new StorageCheck();
    checkInfo.setCheckCode(DBUtils.getCodeRegular(MenuEnum.MENU_1601));
    checkInfo.setCheckType(createStorageCheckVo.getCheckType());
    checkInfo.setStorageId(storageInfo.getStorageId());
    checkInfo.setStorageName(storageInfo.getStorageName());
    checkInfo.setConsignorId(consignorInfo.getConsignorId());
    checkInfo.setConsignorCode(consignorInfo.getConsignorCode());
    checkInfo.setConsignorName(consignorInfo.getConsignorName());
    checkInfo.setCheckStatus(StorageCheckStatusEnum.NEWED.getName());
    checkInfo.setUserId(LoginHelper.getUserId());
    checkInfo.setDeptId(LoginHelper.getUserId());
    checkInfo.setApplyDate(new Date());
    checkInfo.setNickName(LoginHelper.getNickname());
    if (ObjectUtil.equal(createStorageCheckVo.getCheckType(), "动态盘")) {
      checkInfo.setDiffDate(createStorageCheckVo.getDiffDate());
    }
    checkInfo.setIsBlind(createStorageCheckVo.getChecked());
    this.save(checkInfo);
    checkInfo.setTotalProductStorage(BigDecimal.ZERO);
    checkInfo.setTotalPurchaseAmount(BigDecimal.ZERO);
    checkInfo.setTotalWeight(BigDecimal.ZERO);
    for (CoreInventoryCreateCheckVo CoreInventoryCreateCheckVo : detailInfo.getRows()) {
      StorageCheckDetail detail = new StorageCheckDetail();
      BeanUtil.copyProperties(CoreInventoryCreateCheckVo, detail);
      detail.setCheckId(checkInfo.getCheckId());
      storageCheckDetailService.save(detail);
      checkInfo.setTotalProductStorage(B.add(checkInfo.getTotalProductStorage(), detail.getProductStorage()));
      checkInfo.setTotalPurchaseAmount(B.add(checkInfo.getTotalPurchaseAmount(), detail.getPurchaseAmount()));
      checkInfo.setTotalWeight(B.add(checkInfo.getTotalWeight(), detail.getRowWeight()));

    }
    //#endregion

    this.baseMapper.updateById(checkInfo);
    return R.ok("生成盘点单号成功");
  }
  //#endregion

  //#region 提交

  /**
   * 提交
   */
  @Override
  public R<Void> subimtCheckBill(Map<String, Object> map) {
    Long checkId = Convert.toLong(map.get("checkId"));
    StorageCheck storageCheck = this.getById(checkId);
    List<StorageCheckDetail> details = storageCheckDetailService.selectListByMainId(checkId);

    if ((!details.stream().anyMatch(f -> B.isGreaterOrEqual(f.getCheckQuantity()))) && (details.stream().anyMatch(f -> B.isGreater(f.getProfitQuantity())) || details.stream().anyMatch(f -> B.isGreater(f.getLossQuantity())))) {
      return R.warn("未做任何盘点操作不能提交！");
    }

    LambdaUpdateWrapper<StorageCheck> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(StorageCheck::getCheckStatus, StorageCheckStatusEnum.SUBIMT.getName())
      .set(StorageCheck::getApplyDate, new Date())
      .eq(StorageCheck::getCheckId, checkId);
    this.update(orderLambdaUpdateWrapper);

    //复盘提交后修改盈亏单中的复盘数量和复盘差异
    if (ObjectUtil.equal(storageCheck.getCheckType(), StorageCheckTypeEnum.REPLAY.getName())) {
      // 将复盘的盘亏盘盈数量更新盈亏单中
      for (StorageCheckDetail detail : details) {
        LambdaUpdateWrapper<StorageProfitLossDetail> orderLambdaUpdateWrapper1 = new LambdaUpdateWrapper<>();
        orderLambdaUpdateWrapper1.set(StorageProfitLossDetail::getReCheckQuantity, detail.getCheckQuantity())
          .set(StorageProfitLossDetail::getLossQuantity, detail.getLossQuantity())
          .set(StorageProfitLossDetail::getProfitQuantity, detail.getProfitQuantity())

          .set(StorageProfitLossDetail::getReCheckDiff, B.sub(detail.getCheckQuantity(), detail.getProductStorage()))
          .eq(StorageProfitLossDetail::getProfitLossDetailId, detail.getProfitLossDetailId());
        storageProfitLossDetailService.update(orderLambdaUpdateWrapper1);
      }

      LambdaUpdateWrapper<StorageProfitLoss> orderLambdaUpdateWrapper2 = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper2.set(StorageProfitLoss::getLossStatus, StorageProfitLossStatusEnum.REVIEWED.getName())
        .eq(StorageProfitLoss::getProfitLossId, storageCheck.getProfitLossId());
      storageProfitLossService.update(orderLambdaUpdateWrapper2);
    }

    return R.ok("提交成功");
  }
  //#endregion

  //#region 生成盈亏单

  /**
   * 生成盈亏单
   */
  @Override
  public R<Void> createCheck(Map<String, Object> map) {
    Long checkId = Convert.toLong(map.get("checkId"));
    StorageCheck checkInfo = this.getById(checkId);
    List<StorageCheckDetail> storageCheckDetail = storageCheckDetailService.selectListByMainId(checkInfo.getCheckId());

    if ((!ObjectUtil.equal(checkInfo.getCheckStatus(), "已提交")) || ObjectUtil.equal(checkInfo.getCheckType(), "复盘")) {
      throw new ServiceException("只有[已提交]状态并且类型不是[复盘]的盘点单才能生成盈亏单！");
    }

    //#region 生成盘点单
    StorageProfitLoss profitLosInfo = new StorageProfitLoss();
    BeanUtil.copyProperties(checkInfo, profitLosInfo);
    profitLosInfo.setProfitLossCode(DBUtils.getCodeRegular(MenuEnum.MENU_1565));
    profitLosInfo.setCheckId(checkInfo.getCheckId());
    profitLosInfo.setCheckCode(checkInfo.getCheckCode());
    profitLosInfo.setApplyDate(DateUtils.getNowDate());
    profitLosInfo.setLossStatus("新建");
    profitLosInfo.setSortingStatus(BigDecimal.ONE.longValue());
    storageProfitLossService.save(profitLosInfo);

    for (StorageCheckDetail storageCheckDetailVo : storageCheckDetail) {
      StorageProfitLossDetail detail = new StorageProfitLossDetail();
      BeanUtil.copyProperties(storageCheckDetailVo, detail);
      detail.setProfitLossId(profitLosInfo.getProfitLossId());
      detail.setCheckDiff(B.sub(storageCheckDetailVo.getCheckQuantity(), storageCheckDetailVo.getProductStorage()));
      storageProfitLossDetailService.save(detail);
    }
    //#endregion

    LambdaUpdateWrapper<StorageCheck> storageCheckLambdaUpdateWrapper = new UpdateWrapper<StorageCheck>().lambda();
    storageCheckLambdaUpdateWrapper.set(StorageCheck::getCheckStatus, "已生成盈亏单")
      .eq(StorageCheck::getCheckId, checkInfo.getCheckId());
    this.update(storageCheckLambdaUpdateWrapper);//提交

    return R.ok("已生成盈亏单");
  }
  //#endregion

  //#region 生成盈亏单

  /**
   * 生成盈亏单
   */
  @Override
  public R<Void> adjustInventory(Map<String, Object> map) {
    Long checkId = Convert.toLong(map.get("checkId"));
    StorageCheck checkInfo = this.getById(checkId);
    List<StorageCheckDetail> storageCheckDetail = storageCheckDetailService.selectListByMainId(checkInfo.getCheckId());

    if ((!ObjectUtil.equal(checkInfo.getCheckStatus(), "已提交")) || ObjectUtil.equal(checkInfo.getCheckType(), "复盘")) {
      throw new ServiceException("只有[已提交]状态并且类型不是[复盘]的盘点单才能生成盈亏单！");
    }

    //#region 生成盘点单
    StorageProfitLoss profitLosInfo = new StorageProfitLoss();
    BeanUtil.copyProperties(checkInfo, profitLosInfo);
    profitLosInfo.setProfitLossCode(DBUtils.getCodeRegular(MenuEnum.MENU_1565));
    profitLosInfo.setCheckId(checkInfo.getCheckId());
    profitLosInfo.setCheckCode(checkInfo.getCheckCode());
    profitLosInfo.setApplyDate(DateUtils.getNowDate());
    profitLosInfo.setLossStatus("新建");
    profitLosInfo.setSortingStatus(BigDecimal.ONE.longValue());
    storageProfitLossService.save(profitLosInfo);

    for (StorageCheckDetail storageCheckDetailVo : storageCheckDetail) {
      StorageProfitLossDetail detail = new StorageProfitLossDetail();
      BeanUtil.copyProperties(storageCheckDetailVo, detail);
      detail.setProfitLossId(profitLosInfo.getProfitLossId());
      detail.setCheckDiff(B.sub(storageCheckDetailVo.getCheckQuantity(), storageCheckDetailVo.getProductStorage()));
      storageProfitLossDetailService.save(detail);
    }
    //#endregion

    LambdaUpdateWrapper<StorageCheck> storageCheckLambdaUpdateWrapper = new UpdateWrapper<StorageCheck>().lambda();
    storageCheckLambdaUpdateWrapper.set(StorageCheck::getCheckStatus, "已生成盈亏单")
      .eq(StorageCheck::getCheckId, checkInfo.getCheckId());
    this.update(storageCheckLambdaUpdateWrapper);//提交

    return R.ok("已生成盈亏单");
  }
  //#endregion
}
