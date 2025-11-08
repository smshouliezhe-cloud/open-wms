package com.yiruantong.composite.liteflow.checkingCmp;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.liteflow.Context.CheckingContext;
import com.yiruantong.composite.service.in.IReturnService;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import com.yiruantong.inbound.domain.api.ApiInOrderDetailBo;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import com.yiruantong.inbound.service.service.IInReturnService;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;

import java.util.*;

@LiteflowComponent(id = "isAddReturnCmp", name = "2.如果入库，生成一个入库退货单，自动出库")
@RequiredArgsConstructor
public class IsAddReturnCmp extends NodeComponent {

  private final IInReturnService inReturnService;
  private final IInReturnDetailService inReturnDetailService;
  private final IInReturnStatusHistoryService inReturnStatusHistoryService;
  private final IReturnService returnService;
  private final IInOrderDetailService inOrderDetailService;

  @Override
  public void process() {
    CheckingContext ctx = this.getContextBean(CheckingContext.class);
    InOrder orderInfo = ctx.getInOrder();
    ApiInOrderBo apiInOrderBo = ctx.getApiInOrderBo();

    List<InOrderStatusEnum> outOrderStatusEnumList = Arrays.asList(InOrderStatusEnum.PENDING, InOrderStatusEnum.NEWED,
      InOrderStatusEnum.SUCCESS);
    //如果只是审核或者 新建的单据 不往下执行 生成退货单
    if (!outOrderStatusEnumList.stream().noneMatch(a -> B.isEqual(a.getName(), orderInfo.getOrderStatus()))) {

      for (ApiInOrderDetailBo detail : apiInOrderBo.getDetailList()) {
        if (ObjectUtils.isNotNull(detail.getOrderDetailId())) {
          LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
          queryWrapper.eq(InOrderDetail::getOrderDetailId, detail.getOrderDetailId());
          InOrderDetail inOrderDetailInfo = inOrderDetailService.getOne(queryWrapper);

          LambdaUpdateWrapper<InOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
          updateWrapper.set(InOrderDetail::getQuantity, B.sub(inOrderDetailInfo.getQuantity(), detail.getRowDeductWeight())) // 预到货数量 - 扣重
            .eq(InOrderDetail::getOrderDetailId, detail.getOrderDetailId());
          inOrderDetailService.update(updateWrapper);
        }
      }

      return;
    }
    // 生成退货单
    InReturn returnInfo = new InReturn();
    BeanUtil.copyProperties(orderInfo, returnInfo);
    returnInfo.setReturnCode(DBUtils.getCodeRegular(MenuEnum.MENU_1661));
    returnInfo.setTotalReturnQuantity(orderInfo.getTotalQuantity());
    returnInfo.setReturnAmount(orderInfo.getTotalAmount());
    returnInfo.setReturnId(null);
    returnInfo.setReturnStatus(InReturnEnum.NEWED.getName());
    returnInfo.setAuditDate(null);
    returnInfo.setAuditing(null);
    returnInfo.setAuditor(null);
    returnInfo.setExpandFields(orderInfo.getExpandFields());
    returnInfo.setApplyDate(new Date());
    inReturnService.save(returnInfo);
    inReturnStatusHistoryService.addHistoryInfo(
      returnInfo, InReturnActionEnum.IN_CHECKING, InReturnEnum.NEWED, "");

    //生成退货单明细信息
    for (InOrderDetail detail : ctx.getInOrderDetails()) {
      InReturnDetail returnDetail = new InReturnDetail();
      BeanUtil.copyProperties(detail, returnDetail);
      returnDetail.setReturnId(returnInfo.getReturnId());
      returnDetail.setReturnQuantity(detail.getRowDeductWeight()); // 扣重
      returnDetail.setSortingStatus(SortingStatusEnum.NONE.getId());
      returnDetail.setAmountRefunded(detail.getPurchaseAmount());
      returnDetail.setReturnDetailId(null);
      inReturnDetailService.save(returnDetail);
    }
    //调用 退货单的审核功能
    Map<String, Object> maps = new HashMap<>();
    maps.put("ids", returnInfo.getReturnId());
    maps.put("ids", returnInfo.getReturnId());
    R<Void> result = inReturnService.multiAuditing(List.of(returnInfo.getReturnId()));
    returnInfo = inReturnService.getBaseMapper().selectById(returnInfo.getReturnId());

    // 如果分拣成功，并且 分配状态是已分配 走转到出库单逻辑
    if (result.isResult() && B.isEqual(returnInfo.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId())) {
      //如果
      maps.put("returnType", "validateOut");
      returnService.toOutOrder(maps);


    }
  }

}
