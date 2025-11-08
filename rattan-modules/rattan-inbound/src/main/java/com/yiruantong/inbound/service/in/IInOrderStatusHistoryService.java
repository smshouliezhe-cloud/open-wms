package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderStatusHistory;
import com.yiruantong.inbound.domain.in.bo.InOrderStatusHistoryBo;
import com.yiruantong.inbound.domain.in.vo.InOrderStatusHistoryVo;

/**
 * 采购订单流水记录Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
public interface IInOrderStatusHistoryService extends IServicePlus<InOrderStatusHistory, InOrderStatusHistoryVo, InOrderStatusHistoryBo> {
  //#region 枚举方式添加 轨迹信息
  void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum fromStatus, InOrderStatusEnum toStatus);

  void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum toStatus);

  void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum toStatus, String Remark);

  void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum toStatus, LoginUser loginUser);

  void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum fromStatus, InOrderStatusEnum toStatus, LoginUser loginUser, String Remark);

  void addHistoryInfo(InOrder inOrderInfo, String action, String fromStatus, String toStatus);

  void addHistoryInfo(InOrder inOrderInfo, String action, String fromStatus, String toStatus, String remark);

  void addHistoryInfo(InOrder inOrderInfo, String action, String fromStatus, String toStatus, LoginUser loginUser, String remark);
}
