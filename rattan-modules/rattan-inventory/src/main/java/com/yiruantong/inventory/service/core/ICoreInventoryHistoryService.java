package com.yiruantong.inventory.service.core;

import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryHistoryBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHistoryVo;
import com.yiruantong.inventory.enums.HistoryTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * WMS库存变化轨迹Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
public interface ICoreInventoryHistoryService extends IServicePlus<CoreInventoryHistory, CoreInventoryHistoryVo, CoreInventoryHistoryBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId 主表ID
   * @return 返回明细集合
   */
  List<CoreInventoryHistory> selectListByMainId(Long mainId);

  /**
   * 新增库存轨迹
   *
   * @param historyTypeEnum         轨迹类型：IN=入库轨迹、OUT=出库轨迹
   * @param productInfo             商品信息
   * @param commonDetailDto         扫描行信息
   * @param inventorySourceTypeEnum 来源类型
   * @param beforeQuantity          入库前数量
   * @param beforeWeight            入库前重量
   * @param afterQuantity           入库后数量
   * @param afterWeight             入库后重量
   */
  void addHistory(HistoryTypeEnum historyTypeEnum, BaseProduct productInfo, CommonDetailDto commonDetailDto, InventorySourceTypeEnum inventorySourceTypeEnum, BigDecimal beforeQuantity, BigDecimal beforeWeight, BigDecimal afterQuantity, BigDecimal afterWeight);
}
