package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InOrderBo;
import com.yiruantong.inbound.domain.in.vo.InOrderVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 预到货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInOrderService extends IServicePlus<InOrder, InOrderVo, InOrderBo> {
  /**
   * 根据预到货单号获取预到货信息
   *
   * @param orderCode 预到货单号
   */
  InOrder getByCode(String orderCode);

  /**
   * 确认为在途中
   *
   * @param ids 前端参数
   */
  R<Void> onInTransit(List<Long> ids);

  /**
   * 强制完成
   *
   * @param ids 前端参数
   */
  R<Void> forceFinish(List<Long> ids);

  /**
   * 更新预到货状态
   *
   * @param orderId 预到货单ID
   */
  void updateStatus(Long orderId, InOrderStatusEnum status);

  /**
   * 更新审核流状态
   *
   * @param rabbitReceiverDto 消息队列数据
   * @param toStatus          目标状态
   * @return
   */
  String updateWorkflowStatus(RabbitReceiverDto rabbitReceiverDto, String toStatus);

  /**
   * 更新上架状态
   *
   * @param orderId 预到货单ID
   */
  void updateShelverStatus(Long orderId, InShelveStatusEnum status);

  /**
   * 拆分
   *
   * @param map 拆分
   */
  R<Void> splitOrder(Map<String, Object> map);

  /**
   * PDA页面筛选初始化数据
   */
  R<Map<String, Object>> pdaWhereInit();

  R<List<Map<String, Object>>> getStatisticData(Map<String, Object> maps);

  /**
   * 零库存出库
   *
   * @param ids
   * @return
   */
  R<Void> zeroOut(List ids, BigDecimal unqualifiedQty);

  InOrder getBySourceCode(String sourceCode);

  void upCheckingStatus(String name, Long orderId);

  R<Void> compulsoryReceipt(List<Long> ids);

  void apiAllDelete(String sourceCode);

  void apiPartDelete(String sourceCode, String caseNumber);

  /**
   * 自定义打印明细托盘码
   *
   * @param queryBos 参数
   */
  R<Map<String, Object>> printPlateCodeList(List<QueryBo> queryBos);

  /**
   * 送货完成归档
   *
   * @param ids 审核参数
   */
  R<Void> deliveryComplet(List<Long> ids);
}
