package com.yiruantong.inventory.service.core.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySnBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventorySnVo;
import com.yiruantong.inventory.domain.core.vo.CoreSnComposeVo;
import com.yiruantong.inventory.mapper.core.CoreInventorySnMapper;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存明细SNService业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@RequiredArgsConstructor
@Service
public class CoreInventorySnServiceImpl extends ServiceImplPlus<CoreInventorySnMapper, CoreInventorySn, CoreInventorySnVo, CoreInventorySnBo> implements ICoreInventorySnService {
  //#region 根据SN号获取SN信息
  @Override
  public CoreInventorySn getByCode(Long storageId, String sn) {
    MPJLambdaWrapper<CoreInventorySn> snLambdaQueryWrapper = new MPJLambdaWrapper<>();
    snLambdaQueryWrapper
      .selectAll(CoreInventorySn.class)
      .eq(CoreInventory::getStorageId, storageId)
      .eq(CoreInventorySn::getEnable, EnableEnum.ENABLE.getId());
    return this.selectJoinOne(CoreInventorySn.class, snLambdaQueryWrapper);
  }
  //#endregion

  //#region SN查询页面自定义查询方法
  @Override
  public TableDataInfo<CoreSnComposeVo> selectSnComposeList(PageQuery pageQuery) {
    IPage<CoreSnComposeVo> ipage = pageQuery.build();

    MPJLambdaWrapper<CoreInventorySn> wrapper = new MPJLambdaWrapper<CoreInventorySn>()
      .selectAll(CoreInventorySn.class)
      .selectAll(CoreInventory.class)
      .select(BasePosition::getAreaCode, BasePosition::getPositionType)
      .innerJoin(CoreInventory.class, CoreInventory::getInventoryId, CoreInventorySn::getInventoryId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      });

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, CoreInventorySn.class, CoreInventory.class, BasePosition.class);

    IPage<CoreSnComposeVo> page = this.selectJoinListPage(ipage, CoreSnComposeVo.class, wrapper);

    TableDataInfo<CoreSnComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    return tableDataInfoV;
  }
  //#endregion

  //#region 获取有效SN信息
  @Override
  public List<CoreSnComposeVo> selectSnComposeList(Long storageId, Long consignorId, Long productid, List<String> querySnList, List<PositionTypeEnum> positionTypeEnumList) {
    MPJLambdaWrapper<CoreInventorySn> wrapper = new MPJLambdaWrapper<CoreInventorySn>()
      .selectAll(CoreInventorySn.class)
      .selectAll(CoreInventory.class)
      .select(BasePosition::getAreaCode, BasePosition::getPositionType)
      .innerJoin(CoreInventory.class, CoreInventory::getInventoryId, CoreInventorySn::getInventoryId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventorySn::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .eq(CoreInventorySn::getStorageId, storageId)
      .eq(CoreInventorySn::getConsignorId, consignorId)
      .eq(CoreInventorySn::getProductId, productid)
      .eq(CoreInventorySn::getEnable, EnableEnum.ENABLE.getId())
      .in(CoreInventorySn::getSnNo, querySnList)
      .in(BasePosition::getPositionType, positionTypeEnumList.stream().map(PositionTypeEnum::getId).toList());

    return this.selectJoinList(CoreSnComposeVo.class, wrapper);
  }
  //#endregion

  //#region 根据给定SN集合，返回无效的SN集合
  @Override
  public List<String> getInvalidSnList(Long storageId, String sns) {
    List<String> querySnList = StringUtils.splitList(sns); // 逗号分割
    // 返回SN在库存中不存在的SN
    return this.getInvalidSnList(storageId, querySnList);
  }

  @Override
  public List<String> getInvalidSnList(Long storageId, List<String> querySnList) {
    return this.getInvalidSnList(storageId, querySnList, null);
  }

  @Override
  public List<String> getInvalidSnList(Long storageId, List<String> querySnList, List<PositionTypeEnum> positionTypeEnumList) {
    MPJLambdaWrapper<CoreInventorySn> snLambdaQueryWrapper = new MPJLambdaWrapper<>();
    snLambdaQueryWrapper
      .selectAll(CoreInventorySn.class)
      .eq(CoreInventorySn::getStorageId, storageId)
      .eq(CoreInventorySn::getEnable, EnableEnum.ENABLE.getId())
      .in(CoreInventorySn::getSnNo, querySnList);

    if (CollUtil.isNotEmpty(positionTypeEnumList)) {
      snLambdaQueryWrapper
        .innerJoin(BasePosition.class, on -> {
          on.eq(BasePosition::getStorageId, CoreInventorySn::getStorageId)
            .eq(BasePosition::getPositionName, CoreInventorySn::getPositionName);
          return on;
        })
        .in(BasePosition::getPositionType, positionTypeEnumList.stream().map(PositionTypeEnum::getId).toList());
    }
    List<CoreInventorySn> snList = this.selectJoinList(CoreInventorySn.class, snLambdaQueryWrapper);

    // 返回SN在库存中不存在的SN
    return querySnList.stream().filter(sn -> snList.stream().noneMatch(a -> StringUtils.equals(a.getSnNo(), sn))).toList();
  }
  //#endregion

  //#region 根据给定SN集合，返回有效的SN集合
  @Override
  public List<CoreInventorySn> getValidSnList(Long storageId, String sns) {
    List<String> querySnList = StringUtils.splitList(sns); // 逗号分割
    // 返回SN在库存中不存在的SN
    return this.getValidSnList(storageId, querySnList);
  }

  @Override
  public List<CoreInventorySn> getValidSnList(Long storageId, List<String> querySnList) {
    LambdaQueryWrapper<CoreInventorySn> snLambdaQueryWrapper = new LambdaQueryWrapper<>();
    snLambdaQueryWrapper
      .eq(CoreInventorySn::getStorageId, storageId)
      .eq(CoreInventorySn::getEnable, EnableEnum.ENABLE.getId())
      .in(CoreInventorySn::getSnNo, querySnList);
    List<CoreInventorySn> snList = this.baseMapper.selectList(snLambdaQueryWrapper);

    // 返回SN在库存中不存在的SN
    return snList.stream().toList();
  }
  //#endregion

  //#region 根据库存ID返回SN集合
  @Override
  public R<List<CoreInventorySnVo>> selectListByInventoryId(long inventoryId) {
    LambdaQueryWrapper<CoreInventorySn> snLambdaQueryWrapper = new LambdaQueryWrapper<>();
    snLambdaQueryWrapper.eq(CoreInventorySn::getInventoryId, inventoryId);

    var dataList = this.getBaseMapper().selectVoList(snLambdaQueryWrapper);
    return R.ok(dataList);
  }
  //#endregion

  //#region 更新SN不可用
  @Override
  public boolean updateInvalid(Long storageId, List<String> querySnList, Long inventoryId) {
    LambdaUpdateWrapper<CoreInventorySn> snLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    snLambdaUpdateWrapper
      .set(CoreInventorySn::getEnable, EnableEnum.DISABLE.getId())
      .eq(CoreInventorySn::getStorageId, storageId)
      .eq(B.isGreater(inventoryId), CoreInventorySn::getInventoryId, inventoryId) // 库存ID条件
      .in(CoreInventorySn::getSnNo, querySnList);

    return this.update(snLambdaUpdateWrapper);
  }

  public boolean updateInvalid(Long storageId, List<String> querySnList) {
    return updateInvalid(storageId, querySnList, null);
  }

  @Override
  public boolean updateValid(Long storageId, List<String> querySnList, Long inventoryId) {
    LambdaUpdateWrapper<CoreInventorySn> snLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    snLambdaUpdateWrapper
      .set(CoreInventorySn::getEnable, EnableEnum.ENABLE.getId())
      .eq(CoreInventorySn::getStorageId, storageId)
      .eq(B.isGreater(inventoryId), CoreInventorySn::getInventoryId, inventoryId) // 库存ID条件
      .in(CoreInventorySn::getSnNo, querySnList);

    return this.update(snLambdaUpdateWrapper);
  }

  public boolean updateValid(Long storageId, List<String> querySnList) {
    return updateValid(storageId, querySnList, null);
  }
  //#endregion

}
