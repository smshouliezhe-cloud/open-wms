package com.yiruantong.inventory.service.core;

import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySortTypeEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryHolderBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHolderComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHolderVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存占位查询(异常)Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
public interface ICoreInventoryHolderService extends IServicePlus<CoreInventoryHolder, CoreInventoryHolderVo, CoreInventoryHolderBo> {
  /**
   * 清除占位
   *
   * @param sourceTypeEnumList 占位类型
   * @param mainId             主表ID
   */
  void clearHolder(List<HolderSourceTypeEnum> sourceTypeEnumList, Long mainId);

  /**
   * 获取占位数据
   *
   * @param mainId               主表ID
   * @param detailId             明细表ID
   * @param holderSourceTypeEnum 占位类型
   * @return 返回占位数据
   */
  BigDecimal getPlaceholderStorage(Long mainId, Long detailId, HolderSourceTypeEnum... holderSourceTypeEnum);

  /**
   * 根据库存ID获取占位数据
   *
   * @param inventoryId 库存ID
   * @return 返回占位数据
   */
  BigDecimal getPlaceholderStorage(Long inventoryId);

  /**
   * 获取占位数据
   *
   * @param mainId               主表ID
   * @param detailId             明细表ID
   * @param holderSourceTypeEnum 占位类型
   * @return 返回占位数据
   */
  BigDecimal getPlaceholderStorage(Long mainId, Long detailId, String batchNumber, HolderSourceTypeEnum... holderSourceTypeEnum);

  /**
   * 获取占位数据
   *
   * @param mainId                主表ID
   * @param detailId              明细表ID
   * @param holderSourceTypeEnums 占位类型
   * @return 返回占位数据
   */
  List<CoreInventoryHolder> selectHolderList(Long mainId, Long detailId, HolderSourceTypeEnum... holderSourceTypeEnums);

  /**
   * 获取占位数据
   *
   * @param mainId                主表ID
   * @param holderSourceTypeEnums 占位类型
   * @return 返回占位数据
   */
  List<CoreInventoryHolder> selectHolderList(Long mainId, HolderSourceTypeEnum... holderSourceTypeEnums);

  /**
   * 获取占位数据
   *
   * @param mainId   主表ID
   * @param detailId 明细表ID
   * @param billCode 来源单号
   * @return 返回占位数据
   */
  List<CoreInventoryHolder> selectHolderList(Long mainId, Long detailId, String billCode);

  /**
   * 生成占位
   *
   * @param mainInfo              主表信息
   * @param detail                明细表信息
   * @param inventoryItem         库存数据
   * @param holderQty             占位数量
   * @param inventorySortTypeEnum 分拣类型
   */
  CoreInventoryHolder createHolder(CommonMainDto mainInfo, CommonDetailDto detail, CoreInventoryComposeVo inventoryItem, BigDecimal holderQty, InventorySortTypeEnum inventorySortTypeEnum);

  /**
   * 生成占位
   *
   * @param mainInfo              主表信息
   * @param detail                明细表信息
   * @param holderQty             占位数量
   * @param inventorySortTypeEnum 分拣类型
   */
  void createHolder(CommonMainDto mainInfo, CommonDetailDto detail, BigDecimal holderQty, InventorySortTypeEnum inventorySortTypeEnum);

  /**
   * 生成占位
   *
   * @param mainInfo 主表信息
   * @param detail   明细表信息
   */
  CoreInventoryHolder createHolder(CommonMainDto mainInfo, CommonDetailDto detail);

  /**
   * 查询自定义库存占位数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<CoreInventoryHolderComposeVo> selectInventoryComposeHolderList(PageQuery pageQuery);
}
