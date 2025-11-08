package com.yiruantong.inventory.service.core;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryBo;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySearchBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryCreateCheckVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryReplenishmentVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 库存调整选择器Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
public interface ICoreInventoryService extends IServicePlus<CoreInventory, CoreInventoryVo, CoreInventoryBo> {
  /**
   * 库存分拣状态
   *
   * @param inventoryStatusEnumList 库存状态
   */
  void setInventoryStatusEnumList(List<InventoryStatusEnum> inventoryStatusEnumList);

  /**
   * 设置可分拣的库存属性
   *
   * @param productAttributeEnumList 库存属性
   * @return 返回查询列表数据
   */
  void setProductAttributeEnumList(List<InventoryStatusEnum> productAttributeEnumList);

  /**
   * 库存分拣货位类型
   *
   * @param positionTypeEnumList 货位类型
   */
  void setPositionTypeEnumList(List<PositionTypeEnum> positionTypeEnumList);

  /**
   * 获取仓库货位库存量
   *
   * @param storageId    仓库ID
   * @param positionName 货位名称
   * @return 返回查询结果
   */
  BigDecimal getPositionStorage(Long storageId, String positionName);

  /**
   * 获取仓库货位拍数
   *
   * @param storageId    仓库ID
   * @param positionName 货位名称
   * @return 返回查询结果
   */
  Long getPlateCount(Long storageId, String positionName);

  /**
   * 判断库存存在混物料，同货位只能存放同一种商品
   *
   * @param storageId    仓库ID
   * @param productId    商品ID
   * @param positionName 货位名称
   * @return 返回查询结果
   */
  boolean isMixProduct(Long storageId, Long productId, String positionName);

  /**
   * 获取库存求和
   *
   * @param map 参数
   * @return 返回库存求和结果
   */
  Map<String, Object> getSumData(Map<String, Object> map);

  /**
   * 获取库存组合信息，查询列表页面
   */
  TableDataInfo<CoreInventoryComposeVo> selectInventoryComposeList(PageQuery pageQuery);

  /**
   * 获取库存信息
   *
   * @param inventoryId 库存ID
   * @return 返回有效库存列表
   */
  CoreInventoryComposeVo getInventoryComposeVo(Long inventoryId);

  /**
   * 获取有效库存列表
   *
   * @param storageId   仓库ID
   * @param consignorId 货主ID
   * @param productId   商品ID
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long consignorId, Long productId, String positionName);

  /**
   * 获取有效库存列表
   *
   * @param storageId 仓库ID
   * @param productId 商品ID
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long productId, String positionName);

  /**
   * 获取有效库存列表
   *
   * @param storageId   仓库ID
   * @param consignorId 货主ID
   * @param productId   商品ID
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long consignorId, Long productId);

  /**
   * 获取有效库存列表
   *
   * @param storageId 仓库ID
   * @param productId 商品ID
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long productId);

  /**
   * 获取有效库存列表
   *
   * @param storageId 仓库ID
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId);

  /**
   * 获取有效库存列表
   *
   * @param storageId    仓库ID
   * @param productModel 商品条码
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, String productModel, String positionName);

  /**
   * 获取有效库存列表
   *
   * @param storageId 仓库ID
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, String positionName);

  /**
   * 获取有效库存列表
   *
   * @param storageId     仓库ID
   * @param consignorId   货主ID
   * @param productId     商品ID
   * @param positionTypes 货位类型
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(Long storageId, Long consignorId, Long productId, String positionName, List<Byte> positionTypes);

  /**
   * MPJLambdaWrapper 查询库存
   *
   * @param wrapper 查询条件
   * @return 返回有效库存列表
   * @inventoryStatusEnums 多参数库存状态
   */
  List<CoreInventoryComposeVo> selectValidInventoryList(MPJLambdaWrapper<CoreInventory> wrapper);

  /**
   * 增加库存
   *
   * @param productInfo             商品信息
   * @param basePosition            货位信息
   * @param commonDetailDto         扫描明细行
   * @param inventorySourceTypeEnum 入库类型
   * @return 返回有效库存列表
   */
  CoreInventory addInventory(BaseProduct productInfo, BasePosition basePosition, CommonDetailDto commonDetailDto, InventorySourceTypeEnum inventorySourceTypeEnum);

  /**
   * 减少库存
   *
   * @param inventoryId 库存ID
   * @param quantity    减少库存数量
   */
  void subInventory(Long inventoryId, BigDecimal quantity);

  /**
   * 商品库存查询
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<CoreInventoryComposeVo> selectInventoryComposeGroupList(PageQuery pageQuery);

  /**
   * 根据货位获取获取有效库存列表
   *
   * @param map 查询参数
   * @return 返回有效库存列表
   */
  List<CoreInventoryComposeVo> selectValidInventoryListByPosition(Map<String, Object> map);

  /**
   * 自定义 生成盘点单 查询 页面
   *
   * @param pageQuery 前台传入
   * @return 返回内容
   */
  TableDataInfo<CoreInventoryCreateCheckVo> selectCoreInventoryCreateCheckList(PageQuery pageQuery);

  /**
   * 更新占位量和有效库存数据
   *
   * @param inventoryId 库存ID
   */
  void updateHolderStorage(Long inventoryId);

  /**
   * 判断拍号是否已使用
   *
   * @param storageId 仓库ID
   * @param plateCode 托盘号
   */
  boolean isExistPlate(Long storageId, String plateCode);

  /**
   * 查询保质期预警数据
   *
   * @param pageQuery
   * @return
   */
  TableDataInfo<CoreInventoryComposeVo> selectInventoryEarlyComposeList(PageQuery pageQuery);

  /**
   * 查询库龄预警数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<CoreInventoryComposeVo> selectInventoryWarningComposeList(PageQuery pageQuery);

  /**
   * 货位最低库存预警
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<CoreInventoryComposeVo> selectInventoryLowerComposeList(PageQuery pageQuery);

  /**
   * 锁定
   *
   * @param map
   * @return
   */
  R<Void> lock(Map<String, Object> map);

  /**
   * 解锁
   *
   * @param map
   * @return
   */
  R<Void> unlock(Map<String, Object> map);

  /**
   * 库存下限转补货单
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<CoreInventoryReplenishmentVo> selectInventoryReplenishmentList(PageQuery pageQuery);

  R<List<CoreInventory>> getList(CoreInventorySearchBo bo);
}
