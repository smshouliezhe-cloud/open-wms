package com.yiruantong.composite.rabbitReceiver.plate;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.basic.service.storage.IBasePlateProductService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PlateSourceTypeEnum;
import com.yiruantong.common.core.enums.in.InSourceTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.plate.BasePlateClient;
import com.yiruantong.inventory.domain.plate.BasePlateFlow;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.service.plate.IBasePlateClientService;
import com.yiruantong.inventory.service.plate.IBasePlateFlowService;
import com.yiruantong.inventory.service.plate.IBasePlateOutDetailService;
import com.yiruantong.inventory.service.plate.IBasePlateOutService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.redisson.api.RLock;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 出库单出库后自动生成容器借出单
 */
@RequiredArgsConstructor
@Service
public class OutFinishedToBasePlateOutReceiver implements IRabbitReceiver {
  private final DataSourceTransactionManager transactionManager;
  private final ITaskQueueService taskQueueService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IBasePlateOutService basePlateOutService;
  private final IBasePlateProductService basePlateProductService;
  private final IBasePlateOutDetailService basePlateOutDetailService;
  private final IBasePlateFlowService basePlateFlowService;
  private final IBasePlateClientService basePlateClientService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_FINISHED_TO_BASE_PLATE_OUT); // 接收哪几种类型的数据
  }


  //#region 实现MQ 方法

  /**
   * 出库单出库后自动生成容器借出单
   */
  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }
    RLock lock = RedisUtils.getClient().getLock("rabbitReceiver-plateOut");
    try {
      lock.lock(20, TimeUnit.SECONDS);

      // 手动开启事务  start
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
      definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus transaction = transactionManager.getTransaction(definition);
      try {
        // 出库单信息
        OutOrder outOrder = outOrderService.getById(rabbitReceiverDto.getBillId());
        if (ObjectUtil.isNull(outOrder)) {
          // 更新任务状态为失败
          transactionManager.rollback(transaction); // 手动回滚事务
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到出库单！");
          R.fail("");
        }
        // 出库单明细
        LambdaQueryWrapper<OutOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OutOrderDetail::getOrderId, rabbitReceiverDto.getBillId());
        var outOrderDetails = outOrderDetailService.selectList(queryWrapper);
        if (outOrderDetails.isEmpty()) {
          transactionManager.commit(transaction); // 手动提交事务
          // 更新任务状态为失败
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到出库单明细！");
          R.fail("");
        }


        //#region 验证容器是否有出库单数据
        List<BasePlateProduct> plateProductList = new ArrayList<>();
        // 循环出库单明细
        for (var detailInfo : outOrderDetails) {
          // 查询商品容器数据
          LambdaQueryWrapper<BasePlateProduct> plateProductLambdaQueryWrapper = new LambdaQueryWrapper<>();
          plateProductLambdaQueryWrapper
            .eq(BasePlateProduct::getProductId, detailInfo.getProductId())
            .eq(BasePlateProduct::getProductCode, detailInfo.getProductCode())
            .eq(BasePlateProduct::getProductName, detailInfo.getProductName());
          var basePlateProduct = basePlateProductService.getOne(plateProductLambdaQueryWrapper);

          if (ObjectUtil.isNotNull(basePlateProduct)) {
            plateProductList.add(basePlateProduct);
          }
        }
        if (plateProductList.isEmpty()) {
          transactionManager.commit(transaction); // 手动提交事务
          // 更新任务状态为失败
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到商品容器！");
          R.fail("");
        } else {

          //#region 生成容器借出单
          BasePlateOut basePlateOut = new BasePlateOut();
          BeanUtil.copyProperties(outOrder, basePlateOut);
          String outCode = DBUtils.getCodeRegular(MenuEnum.MENU_1811, LoginHelper.getLoginUser().getTenantId());
          basePlateOut.setOutCode(outCode);
          basePlateOut.setSourceId(outOrder.getOrderId());
          basePlateOut.setSourceCode(outOrder.getOrderCode());
          basePlateOut.setSourceType(InSourceTypeEnum.OUT_ORDER.getName());
          basePlateOut.setOutDate(new Date());
          basePlateOut.setNickName(LoginHelper.getNickname());
          basePlateOut.setStatusText(AuditEnum.AUDITED_SUCCESS.getName());
          basePlateOut.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
          basePlateOut.setAuditor(LoginHelper.getLoginUser().getNickname());
          basePlateOut.setAuditDate(new Date());
          basePlateOut.setCustomerOrderCode(outOrder.getStoreOrderCode()); // 店铺订单号
          basePlateOutService.save(basePlateOut);

          // 循环出库单明细
          for (var item : outOrderDetails) {
            // 查询商品容器数据
            LambdaQueryWrapper<BasePlateProduct> plateProductLambdaQueryWrapper = new LambdaQueryWrapper<>();
            plateProductLambdaQueryWrapper
              .eq(BasePlateProduct::getProductId, item.getProductId())
              .eq(BasePlateProduct::getProductCode, item.getProductCode())
              .eq(BasePlateProduct::getProductName, item.getProductName());
            BasePlateProduct basePlateProduct = basePlateProductService.getOne(plateProductLambdaQueryWrapper);

            if (ObjectUtil.isNotNull(basePlateProduct)) {
              // 容器借出单明细
              var basePlateOutDetail = new BasePlateOutDetail();
              basePlateOutDetail.setOutId(basePlateOut.getOutId());
              basePlateOutDetail.setPlateSpec(basePlateProduct.getPlateSpec());
              basePlateOutDetail.setPlateType(basePlateProduct.getPlateType());
              basePlateOutDetail.setPlateName(basePlateProduct.getPlateName());
              basePlateOutDetail.setWeight(basePlateProduct.getWeight());
              basePlateOutDetail.setUnitCube(basePlateProduct.getUnitCube());
              if (ObjectUtil.isNotNull(basePlateProduct.getWeight())) {
                basePlateOutDetail.setRowWeight(B.mul(basePlateProduct.getWeight(), item.getQuantityOrder()));
              }
              if (ObjectUtil.isNotNull(basePlateProduct.getUnitCube())) {
                basePlateOutDetail.setRowCube(B.mul(basePlateProduct.getUnitCube(), item.getQuantityOrder()));
              }
              basePlateOutDetail.setNowOutQty(item.getQuantityOrder()); // 现借出数量
              basePlateOutDetail.setReturnedQty(BigDecimal.ZERO); // 已归还数量
              basePlateOutDetail.setUnreturnedQty(item.getQuantityOrder()); // 未归还数量
              basePlateOutDetailService.save(basePlateOutDetail);
            }
          }

          //#endregion

          LambdaQueryWrapper<BasePlateOutDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(BasePlateOutDetail::getOutId, basePlateOut.getOutId());
          var basePlateOutDetails = basePlateOutDetailService.list(lambdaQueryWrapper);

          if (!basePlateOutDetails.isEmpty()) {

            var totalOutQty = basePlateOutDetails.stream().map(BasePlateOutDetail::getNowOutQty).reduce(BigDecimal.ZERO, BigDecimal::add);
            var totalReturnedQty = basePlateOutDetails.stream().map(BasePlateOutDetail::getReturnedQty).reduce(BigDecimal.ZERO, BigDecimal::add);
            var totalUnreturnedQty = basePlateOutDetails.stream().map(BasePlateOutDetail::getUnreturnedQty).reduce(BigDecimal.ZERO, BigDecimal::add);
            var totalWeight = basePlateOutDetails.stream().map(BasePlateOutDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
            var totalCube = basePlateOutDetails.stream().map(BasePlateOutDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);

            // 更新主表数据
            LambdaUpdateWrapper<BasePlateOut> lambda = new UpdateWrapper<BasePlateOut>().lambda();
            lambda.set(BasePlateOut::getTotalOutQty, totalOutQty)
              .set(BasePlateOut::getTotalReturnedQty, totalReturnedQty)
              .set(BasePlateOut::getTotalUnreturnedQty, totalUnreturnedQty)
              .set(BasePlateOut::getTotalWeight, totalWeight)
              .set(BasePlateOut::getTotalCube, totalCube)
              .eq(BasePlateOut::getOutId, basePlateOut.getOutId());
            basePlateOutService.update(lambda);//提交
          }

          // 容器借出单
          LambdaQueryWrapper<BasePlateOut> outLambdaQueryWrapper = new LambdaQueryWrapper<>();
          outLambdaQueryWrapper.eq(BasePlateOut::getOutId, basePlateOut.getOutId());
          var mainInfo = basePlateOutService.getOne(outLambdaQueryWrapper);

          // 容器借出单明细
          LambdaQueryWrapper<BasePlateOutDetail> outDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
          outDetailLambdaQueryWrapper.eq(BasePlateOutDetail::getOutId, mainInfo.getOutId());
          var detailList = basePlateOutDetailService.list(outDetailLambdaQueryWrapper);

          if (detailList.isEmpty()) {
            transactionManager.commit(transaction); // 手动提交事务
            // 更新任务状态为失败
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到借出单明细！");
            R.fail("");
          }

          //#region 生成流水
          for (var item : detailList) {
            BasePlateFlow basePlateFlow = new BasePlateFlow();
            String flowCode = DBUtils.getCodeRegular(MenuEnum.MENU_1810, LoginHelper.getLoginUser().getTenantId());

            basePlateFlow.setFlowCode(flowCode);
            BeanUtil.copyProperties(mainInfo, basePlateFlow);
            basePlateFlow.setSourceCode(mainInfo.getOutCode());
            basePlateFlow.setPlateSpec(item.getPlateSpec());
            basePlateFlow.setPlateType(item.getPlateType());
            basePlateFlow.setPlateName(item.getPlateName());
            basePlateFlow.setSourceType(PlateSourceTypeEnum.PLATE_OUT.getName()); // 容器借出
            basePlateFlow.setOuterQty(item.getNowOutQty()); // 借出数量
            basePlateFlow.setReturnQty(BigDecimal.ZERO); // 归还数量
            basePlateFlow.setNickName(LoginHelper.getNickname());
            basePlateFlow.setDeptId(LoginHelper.getDeptId());
            basePlateFlow.setDeptName(LoginHelper.getDeptName());
            // 操作前数量：
            // 查询客户容器管理，条件为：客户名称+仓库名称+容器类别+容器规格；容器借出数量，作为流水中的：
            // 借出：操作前数量
            // 归还：操作后数量
            // 客户容器管理
            LambdaQueryWrapper<BasePlateClient> clientLambdaQueryWrapper = new LambdaQueryWrapper<>();
            clientLambdaQueryWrapper.eq(BasePlateClient::getClientCode, mainInfo.getClientCode())
              .eq(BasePlateClient::getClientShortName, mainInfo.getClientShortName())
              .eq(BasePlateClient::getStorageId, mainInfo.getStorageId())
              .eq(BasePlateClient::getPlateType, item.getPlateType())
              .eq(BasePlateClient::getPlateSpec, item.getPlateSpec());
            var plateClientInfo = basePlateClientService.getOne(clientLambdaQueryWrapper);

            // 查询符合条件为：客户名称+仓库名称+容器类别+容器规格的历史流水数据
            LambdaQueryWrapper<BasePlateFlow> flowLambdaQueryWrapper = new LambdaQueryWrapper<>();
            flowLambdaQueryWrapper.eq(BasePlateFlow::getClientCode, mainInfo.getClientCode())
              .eq(BasePlateFlow::getClientShortName, mainInfo.getClientShortName())
              .eq(BasePlateFlow::getStorageId, mainInfo.getStorageId())
              .eq(BasePlateFlow::getPlateType, item.getPlateType())
              .eq(BasePlateFlow::getPlateSpec, item.getPlateSpec());
            var flowList = basePlateFlowService.list(flowLambdaQueryWrapper);

            BigDecimal beforeBalanceQty = BigDecimal.ZERO;
            BigDecimal balanceQty = BigDecimal.ZERO;
            if (!flowList.isEmpty()) {
              beforeBalanceQty = flowList.stream().map(BasePlateFlow::getBeforeBalanceQty).reduce(BigDecimal.ZERO, BigDecimal::add); // 操作前数量合计
              balanceQty = flowList.stream().map(BasePlateFlow::getBalanceQty).reduce(BigDecimal.ZERO, BigDecimal::add); // 操作后数量合计
            }
            if (ObjectUtil.isNull(plateClientInfo)) {
              // 新增客户容器借出数
              plateClientInfo = new BasePlateClient();
              BeanUtil.copyProperties(mainInfo, plateClientInfo);
              plateClientInfo.setPlateType(item.getPlateType());
              plateClientInfo.setPlateSpec(item.getPlateSpec());
              plateClientInfo.setPlateName(item.getPlateName());
              plateClientInfo.setOuterOty(B.adds(item.getNowOutQty()));
              basePlateClientService.save(plateClientInfo);

              basePlateFlow.setBeforeBalanceQty(BigDecimal.ZERO); // 操作前数量
              basePlateFlow.setBalanceQty(B.add(item.getNowOutQty(), balanceQty)); // 操作后数量
            } else {
              basePlateFlow.setBeforeBalanceQty(plateClientInfo.getOuterOty()); // 操作前数量
              basePlateFlow.setBalanceQty(B.add(plateClientInfo.getOuterOty(), item.getNowOutQty())); // 操作后数量

              // 更新客户容器借出数
              LambdaUpdateWrapper<BasePlateClient> lambda2 = new UpdateWrapper<BasePlateClient>().lambda();
              lambda2.set(BasePlateClient::getOuterOty, B.add(plateClientInfo.getOuterOty(), item.getNowOutQty())) // 容器借出数量增加
                .eq(BasePlateClient::getClientShortName, mainInfo.getClientShortName())
                .eq(BasePlateClient::getStorageId, mainInfo.getStorageId())
                .eq(BasePlateClient::getPlateType, item.getPlateType())
                .eq(BasePlateClient::getPlateSpec, item.getPlateSpec());
              basePlateClientService.update(lambda2);//提交
            }
            basePlateFlowService.save(basePlateFlow);
          }
        }
        //#endregion

        // 更新任务状态为完成
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, "success");
        transactionManager.commit(transaction); // 手动提交事务
      } catch (Exception e) {
        // 更新任务状态为失败
        transactionManager.rollback(transaction); // 手动回滚事务
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
      }
    } finally {
      lock.unlock();
    }
    return R.ok();
  }
  //#endregion
}
