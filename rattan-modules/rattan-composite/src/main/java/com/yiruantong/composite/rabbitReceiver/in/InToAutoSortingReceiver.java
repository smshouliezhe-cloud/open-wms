package com.yiruantong.composite.rabbitReceiver.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import com.yiruantong.inbound.service.in.IInShelveService;
import com.yiruantong.outbound.service.operation.IOutOrderSortPoolService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入库上架后，出库单后自动分拣
 */
@RequiredArgsConstructor
@Service
public class InToAutoSortingReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final IOutOrderSortPoolService outOrderSortPoolService;
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.SHELVE_FINISHED_TO_AUTO_SORTING); // 上架完成后，执行出库单自动分拣
  }

  /**
   * 上架完成后，自动分拣
   *
   * @param rabbitReceiverDto 传输数据字典
   * @return
   */
  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    Long shelveId = rabbitReceiverDto.getBillId();
    InShelve inShelve = inShelveService.getById(shelveId);
    List<InShelveDetail> inShelveDetailList = inShelveDetailService.selectListByMainId(shelveId);

    outOrderSortPoolService.activeSortingByProductId(inShelve.getStorageId(), inShelveDetailList.stream().map(InShelveDetail::getProductId).toList(), LoginHelper.getLoginUser());
    // 更新任务状态为完成
    taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);

    return R.ok();
  }
}
