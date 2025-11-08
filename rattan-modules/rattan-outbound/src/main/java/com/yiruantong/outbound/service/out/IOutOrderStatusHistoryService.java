package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderStatusHistoryVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderStatusHistoryBo;

/**
 * 订单状态历史记录Service接口
 *
 * @author YRT
 * @date 2023-11-10
 */
public interface IOutOrderStatusHistoryService extends IServicePlus<OutOrderStatusHistory, OutOrderStatusHistoryVo, OutOrderStatusHistoryBo> {
  /**
   * 添加出库单操作轨迹
   * @param outOrder 出库单信息
   * @param operationTypeEnum
   * @param toStatus
   * @param remark
   */
  void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus, String remark);

  /**
   * 添加出库单操作轨迹
   * @param outOrder 出库单信息
   * @param operationTypeEnum
   * @param toStatus
   */
  void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus);

  /**
   * 添加出库单操作轨迹
   * @param outOrder 出库单信息
   * @param operationTypeEnum
   * @param toStatus
   */
  void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus, LoginUser loginUser);

}
