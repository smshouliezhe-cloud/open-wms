package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.in.QualityCheckTypeEnum;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

import java.util.List;
import java.util.Map;

/**
 * 预到货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInScanOrderService {
  /**
   * 获取入库单扫描数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getInOrderData(Map<String, Object> map);

  /**
   * 入库前校验：是否存在符合禁收日期
   *
   * @param inScanOrderBo 前端参数
   */
  R<Void> saveCheck(InScanOrderBo inScanOrderBo);

  /**
   * 收货入库
   *
   * @param inScanOrderBo 入库主表bo
   * @return 是否入库成功
   */
  R<Void> normalScanSave(InScanOrderBo inScanOrderBo);

  /**
   * 收货入库
   *
   * @param inScanOrderBo 入库主表bo
   * @param callback      回调函数
   * @return 是否入库成功
   */
  R<Void> normalScanSave(InScanOrderBo inScanOrderBo, Runnable callback);

  /**
   * 收货质检
   *
   * @orderInfo 预到货单号
   * @inboundQualityPosition 质检位置
   */
  R<Void> qualityCheck(InOrder orderInfo, QualityCheckTypeEnum qualityCheckType);

  /**
   * 常规扫描入库 - 装箱收货入库  获取数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<List<Map<String, Object>>> getOrderByProductModel(Map<String, Object> map);

  /**
   * 根据预到货单ID，一键入库
   *
   * @param orderId                 预到货单ID
   * @param inventorySourceTypeEnum 库存来源类型枚举
   * @return
   */
  R<Void> oneKeyIn(Long orderId, InventorySourceTypeEnum inventorySourceTypeEnum);

  R<Void> normalScanSaveXG(InScanOrderBo inScanOrderBo);
  /**
   * 根据商品条码获取预到货单数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getPurchaseOrderData(Map<String, Object> map);
}
