package com.yiruantong.outbound.service.out;

import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutMatchStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutPackageStatusEnum;
import com.yiruantong.common.core.enums.out.OutPickingStatusEnum;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutOrderBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.domain.out.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库订单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
public interface IOutOrderService extends IServicePlus<OutOrder, OutOrderVo, OutOrderBo> {
  /**
   * 根据单号获取出库单信息
   *
   * @param orderCode 预到货单号
   */
  OutOrder getByCode(String orderCode);

  /**
   * 更新分拣状态
   *
   * @param orderId           出库单ID
   * @param sortingStatusEnum 分拣状态
   */
  void updateSortingStatus(Long orderId, SortingStatusEnum sortingStatusEnum);

  /**
   * 更新出库单状态
   *
   * @param orderId            出库单ID
   * @param outOrderStatusEnum 出库单状态
   */
  void updateOrderStatus(Long orderId, OutOrderStatusEnum outOrderStatusEnum);

  /**
   * 更新打包状态
   *
   * @param orderId              出库单ID
   * @param outPickingStatusEnum 打包状态
   */
  void updatePickingStatus(Long orderId, OutPickingStatusEnum outPickingStatusEnum);

  /**
   * 更新配货状态
   *
   * @param orderId            出库单ID
   * @param outMatchStatusEnum 配货状态
   */
  void updateMatchStatus(Long orderId, OutMatchStatusEnum outMatchStatusEnum);

  /**
   * 更新打包状态
   *
   * @param orderId              出库单ID
   * @param outPackageStatusEnum 打包状态
   */
  void updatePackageStatus(Long orderId, OutPackageStatusEnum outPackageStatusEnum);

  /**
   * 获取分拣列表
   *
   * @param map
   * @return
   */
  List<OutSortingRule> getSortingRule(Map<String, Object> map);

  /**
   * 获取拣配单
   *
   * @param queryBoList
   * @return
   */
  R<List<OutOrderDetailHolderComposeVO>> getOutPickingList(List<QueryBo> queryBoList);

  /**
   * 提交分拣规则
   *
   * @param map
   */
  R<Void> setSortingRule(Map<String, Object> map);

  /**
   * 关闭分拣规则
   *
   * @param map
   */
  R<Void> deleteSortingRule(Map<String, Object> map);

  /**
   * 自定义 缺货转预到货 查询 页面
   *
   * @param pageQuery 前台传入
   * @return 返回内容
   */
  TableDataInfo<OrderDetailLackVo> orderDetailLackList(PageQuery pageQuery);

  /**
   * 根据波次单ID获取出库单集合
   *
   * @param orderWaveId 波次单ID
   * @return 出库单集合
   */
  List<OutOrder> selectByOrderWaveId(Long orderWaveId);


  R<Void> incorprationOrder(Map<String, Object> map);

  /**
   * 一键出库获取数据
   *
   * @param map
   * @return 出库单集合
   */
  R<List<OutOrderMainAndDetailVo>> getOrderOuterDetails(Map<String, Object> map);

  /**
   * 一键出库
   *
   * @param outScanMainBo
   * @return 出库单集合
   */
  R<Void> quickOut(OutScanMainBo outScanMainBo);

  /**
   * 一键出库
   *
   * @param map
   * @return 出库单集合
   */
  R<Void> batchOut(Map<String, Object> map);

  R<Void> forceFinish(Map<String, Object> map);


  /**
   * 获取出库单信息
   *
   * @param map
   */
  R<Map<String, Object>> getOutIds(Map<String, Object> map);

  /**
   * 获取出库单和快递信息
   *
   * @param map
   */
  R<Map<String, Object>> getOutAndExpress(Map<String, Object> map);


  /**
   * 查询拣配单数据
   *
   * @param queryBoList 前端参数
   */
  R<OutOrderPrintVo> selectOutPrint(List<QueryBo> queryBoList);


  /**
   * 更新物流信息
   *
   * @param map
   */
  R<Void> updateLogistics(Map<String, Object> map);

  Date calculateDate(Date dateTime, OutOrder orderInfo, BaseStorage storageInfo, Boolean flag);

  R<List<Map<String, Object>>> getStatisticData(Map<String, Object> maps);


  /**
   * 根据店铺订单号获取
   *
   * @param storeOrderCode
   * @return
   */
  OutOrder getByStoreOrder(String storeOrderCode);

  OutOrder getBySourceCode(String sourceCode);

  R<Void> compulsoryAccomplish(List<Long> ids);

  /**
   * 多个出库单合并打印
   *
   * @param queryBos 查询条件
   * @return 返回合并后的一张单子带明细
   */
  R<Map<String, Object>> mergePrintList(List<QueryBo> queryBos);

  R<Void> quickOutList(OutScanMainBo outScanMainBo);
}
