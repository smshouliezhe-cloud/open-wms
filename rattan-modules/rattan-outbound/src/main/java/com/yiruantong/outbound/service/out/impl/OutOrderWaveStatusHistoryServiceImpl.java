package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.service.common.ICommonOperationLogService;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.out.OutOrderWaveStatusHistory;
import com.yiruantong.outbound.domain.out.bo.OutOrderWaveStatusHistoryBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderWaveStatusHistoryVo;
import com.yiruantong.outbound.mapper.out.OutOrderWaveStatusHistoryMapper;
import com.yiruantong.outbound.service.out.IOutOrderWaveStatusHistoryService;
import org.springframework.stereotype.Service;

/**
 * 订单状态历史记录Service业务层处理
 *
 * @author YRT
 * @date 2023-11-24
 */
@RequiredArgsConstructor
@Service
public class OutOrderWaveStatusHistoryServiceImpl extends ServiceImplPlus<OutOrderWaveStatusHistoryMapper, OutOrderWaveStatusHistory, OutOrderWaveStatusHistoryVo, OutOrderWaveStatusHistoryBo> implements IOutOrderWaveStatusHistoryService {
  private final ICommonOperationLogService commonOperationLogService;

  //#region 添加波次查询操作轨迹
  @Override
  public void AddHistory(OutOrderWave outOrderWave, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum toStatus) {
    this.AddHistory(outOrderWave, outWaveOperationTypeEnum, toStatus, outOrderWave.getRemark());
  }

  @Override
  public void AddHistory(OutOrderWave outOrderWave, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum toStatus, String remark) {
    OutOrderStatusEnum fromStatus = OutOrderStatusEnum.matchingEnum(outOrderWave.getWaveStatus());
    Assert.isFalse(ObjectUtil.isNull(fromStatus), "波次单状态枚举不存在！");

    OutOrderWaveStatusHistory history = new OutOrderWaveStatusHistory();
    history.setOperationType(outWaveOperationTypeEnum.getName());
    history.setBillId(outOrderWave.getOrderWaveId());
    history.setBillCode(outOrderWave.getOrderWaveCode());
    history.setFromStatus(outOrderWave.getWaveStatus());
    history.setToStatus(toStatus.getName());
    history.setRemark(remark);
    this.save(history);
    this.commonOperationLogService.addOutOrderWaveLog(outOrderWave.getOrderWaveId(), outOrderWave.getOrderWaveCode(), outWaveOperationTypeEnum, fromStatus, toStatus, remark);
  }
  //#endregion
}
