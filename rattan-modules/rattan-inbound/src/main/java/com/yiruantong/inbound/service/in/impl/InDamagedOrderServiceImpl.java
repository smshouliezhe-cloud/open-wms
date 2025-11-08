package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InDamagedOrder;
import com.yiruantong.inbound.domain.in.InDamagedOrderDetail;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InDamagedOrderBo;
import com.yiruantong.inbound.domain.in.vo.InDamagedOrderVo;
import com.yiruantong.inbound.mapper.in.InDamagedOrderMapper;
import com.yiruantong.inbound.service.in.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 残品入库单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
@RequiredArgsConstructor
@Service
public class InDamagedOrderServiceImpl extends ServiceImplPlus<InDamagedOrderMapper, InDamagedOrder, InDamagedOrderVo, InDamagedOrderBo> implements IInDamagedOrderService {
  public final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInDamagedOrderDetailService inDamagedOrderDetailService;

  @Override

  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      InDamagedOrder inDamagedOrder = this.getBaseMapper().selectById(id);
      if (ObjectUtil.isEmpty(inDamagedOrder)) {
        throw new ServiceException("未获取到残品计划单");
      }
      if (!InOrderPlanStatusEnum.NEWED.getName().equals(inDamagedOrder.getDamagedStatus()) && !InOrderPlanStatusEnum.PENDING.getName().equals(inDamagedOrder.getDamagedStatus())) {
        throw new ServiceException("只有新建或者待审核的单据才可以进行审核");
      }
    }

    // 修改数据
    LambdaUpdateWrapper<InDamagedOrder> lambda = new UpdateWrapper<InDamagedOrder>().lambda();
    lambda.set(InDamagedOrder::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .set(InDamagedOrder::getAuditor, LoginHelper.getNickname())
      .set(InDamagedOrder::getAuditDate, DateUtils.getNowDate())
      .set(InDamagedOrder::getDamagedStatus, "审核成功")
      .in(InDamagedOrder::getDamagedOrderId, ids);
    this.update(lambda);//提交

    return R.ok("审核成功");
  }

  //#region 残品入库：转到预到货单
  public R<Void> toInOrder(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);

    for (Long damageOdorderId : _ids) {
      saveInOrder(damageOdorderId);
    }
    return R.ok();
  }
  //#endregion

  //#region 循环处理选中多条的残品入库单
  private R<Void> saveInOrder(Long damageOdorderId) {
    try {

      InDamagedOrderVo damagedOrderVo = this.baseMapper.selectVoById(damageOdorderId);

      Assert.isTrue(StrUtil.equals(damagedOrderVo.getDamagedStatus(), AuditEnum.AUDITED_SUCCESS.getName()), "单据未审核不允许转预到货单");

      InOrder orderInfo = new InOrder();
      BeanUtil.copyProperties(damagedOrderVo, orderInfo);
      orderInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001)); //得到预到货单自动编号
      orderInfo.setAuditor(damagedOrderVo.getAuditor());
      orderInfo.setStorageId(damagedOrderVo.getStorageId());
      orderInfo.setStorageName(damagedOrderVo.getStorageName());
      orderInfo.setShelveStatus(InShelveStatusEnum.WAITING.getName());
      orderInfo.setProviderId(damagedOrderVo.getProviderId());
      orderInfo.setProviderCode(damagedOrderVo.getProviderCode());
      orderInfo.setProviderShortName(damagedOrderVo.getProviderShortName());
      orderInfo.setTotalQuantity(damagedOrderVo.getTotalQuantity());
      orderInfo.setTotalAmount(damagedOrderVo.getTotalAmount());
      orderInfo.setNickName(damagedOrderVo.getNickName());
      orderInfo.setDeptId(damagedOrderVo.getDeptId());
      orderInfo.setDeptName(damagedOrderVo.getDeptName());
      orderInfo.setOrderStatus(damagedOrderVo.getDamagedStatus());
      orderInfo.setCreateTime(new Date());
      orderInfo.setRemark(damagedOrderVo.getRemark());
      orderInfo.setConsignorId(damagedOrderVo.getConsignorId());
      orderInfo.setConsignorCode(damagedOrderVo.getConsignorCode());
      orderInfo.setConsignorName(damagedOrderVo.getConsignorName());
      orderInfo.setTrackingNumber(damagedOrderVo.getDamagedOrderCode());
      orderInfo.setOrderType("残品预到货");
      orderInfo.setTrackingNumber(damagedOrderVo.getDamagedOrderCode());

      inOrderService.save(orderInfo);

      // 残品入库单明细
      LambdaQueryWrapper<InDamagedOrderDetail> detailDamaged = new LambdaQueryWrapper<>();
      detailDamaged.eq(InDamagedOrderDetail::getDamagedOrderId, damageOdorderId);
      List<InDamagedOrderDetail> detailDamagedList = inDamagedOrderDetailService.list(detailDamaged);

      for (InDamagedOrderDetail item : detailDamagedList) {
        InOrderDetail detail = BeanUtil.copyProperties(item, InOrderDetail.class);
        detail.setOrderId(orderInfo.getOrderId());
        detail.setProductId(item.getProductId());
        detail.setProductCode(item.getProductCode());
        detail.setProductName(item.getProductName());
        detail.setProductModel(item.getProductModel());
        detail.setProductSpec(item.getProductSpec());
        detail.setRelationCode(item.getRelationCode());
        detail.setQuantity(item.getQuantity());
        detail.setPurchasePrice(item.getPurchasePrice());
        detail.setPurchasePrice(item.getPurchasePrice());
        detail.setPurchasePrice(item.getPurchasePrice());
        detail.setPurchaseAmount(item.getRowAmount());
        detail.setBatchNumber(item.getBatchOrder());
        detail.setProduceDate(item.getProductDate());
        detail.setSmallUnit(item.getSmallUnit());
        detail.setRemark(item.getRemark());
        detail.setProviderShortName(orderInfo.getProviderShortName());
        detail.setProviderCode(orderInfo.getProviderCode());
        detail.setProviderId(orderInfo.getProviderId());
        inOrderDetailService.save(detail);
      }
      // 修改数据
      if (ObjectUtil.isNotEmpty(orderInfo.getOrderId())) {

        // 修改数据
        LambdaUpdateWrapper<InDamagedOrder> lambda = new UpdateWrapper<InDamagedOrder>().lambda();
        lambda.set(InDamagedOrder::getDamagedStatus, "已转预到货单")
          .eq(InDamagedOrder::getDamagedOrderId, damageOdorderId);
        this.update(lambda);
      }
      inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.DAMAGED_TO_ORDER, InOrderStatusEnum.NEWED);
      return R.ok("转入到预到货单成功！");
    } catch (Exception e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion
}
