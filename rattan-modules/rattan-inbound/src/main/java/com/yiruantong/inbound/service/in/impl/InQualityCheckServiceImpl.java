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
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.api.ApiInQualityCheckBo;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.InQualityCheck;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;
import com.yiruantong.inbound.domain.in.bo.InQualityCheckBo;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckVo;
import com.yiruantong.inbound.domain.service.vo.InOrderAndDetailVo;
import com.yiruantong.inbound.mapper.in.InQualityCheckMapper;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInQualityCheckDetailService;
import com.yiruantong.inbound.service.in.IInQualityCheckService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 质检管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@RequiredArgsConstructor
@Service
public class InQualityCheckServiceImpl extends ServiceImplPlus<InQualityCheckMapper, InQualityCheck, InQualityCheckVo, InQualityCheckBo> implements IInQualityCheckService {
  private final IInQualityCheckDetailService iInQualityCheckDetailService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInOrderService inOrderService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseStorageService baseStorageService;

  @Override
  /**
   * 根据质检单号返回质检单信息
   * @param qualityCheckCode
   * @return 返回上架单信息
   */
  public InQualityCheck getByCode(String qualityCheckCode) {
    LambdaQueryWrapper<InQualityCheck> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(InQualityCheck::getQualityCheckCode, qualityCheckCode);

    return this.getOne(shelveLambdaQueryWrapper);
  }

  //#region 质检单-生成预到货单
  public R<Void> toInOrder(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString());
    Long[] _ids = Convert.toLongArray(ids);
    for (Long qualityCheckId : _ids) {
      //       LambdaQueryWrapper<InQualityCheck> queryWrapper = new LambdaQueryWrapper<>();
      //       queryWrapper.eq(InQualityCheck::getQualityCheckId, qualityCheckId);
      //       InQualityCheck inQualityCheck = this.baseMapper.selectOne(queryWrapper);
      InQualityCheckVo inQualityCheck = this.baseMapper.selectVoById(qualityCheckId);

      if (inQualityCheck.getAuditing() != 2) {
        throw new ServiceException("单据未审核，不允许操作");
      }

      InOrder inOrder = new InOrder();
      BeanUtil.copyProperties(inQualityCheck, inOrder);
      inOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001));
      inOrder.setOrderStatus(InOrderStatusEnum.NEWED.getName());
      inOrder.setShelveStatus(InShelveStatusEnum.WAITING.getName());
      inOrder.setAuditor("系统");
      inOrder.setAuditing((byte) 2);
      inOrder.setAuditDate(new Date());
      inOrder.setSourceId(qualityCheckId.toString());
      inOrder.setStorageId(inQualityCheck.getStorageId());
      inOrder.setStorageName(inQualityCheck.getStorageName());
      inOrder.setSourceCode(inQualityCheck.getQualityCheckCode());
      inOrder.setTrackingNumber(inQualityCheck.getQualityCheckCode());
      inOrder.setOrderType("质检退回单");
      inOrderService.save(inOrder);

      LambdaQueryWrapper<InQualityCheckDetail> detailWrapper = new LambdaQueryWrapper<>();
      detailWrapper.eq(InQualityCheckDetail::getQualityCheckId, qualityCheckId);

      List<InQualityCheckDetail> detailQualityCheck = iInQualityCheckDetailService.list(detailWrapper);

      for (InQualityCheckDetail item : detailQualityCheck) {
        InOrderDetail inOrderDetail = new InOrderDetail();
        BeanUtil.copyProperties(inOrderDetail, item);
        inOrderDetail.setOrderId(inOrder.getOrderId());
        inOrderDetail.setSourceMainId(qualityCheckId.toString());
        inOrderDetail.setSourceDetailId(item.getQualityCheckDetailId().toString());
        // 次品数量
        inOrderDetail.setDefectiveQty(item.getDefectiveQuantity());
        inOrderDetail.setPurchaseAmount(B.mul(inOrderDetail.getQuantity(), inOrderDetail.getPurchasePrice()));
        // 计划数量
        inOrderDetail.setPlanQty(item.getQuantity());
        inOrderDetailService.save(inOrderDetail);
      }
    }
    return R.ok("Ok");
  }
  //#endregion


  /**
   * 入库计划批量审核
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    // 写具体审核逻辑
    try {
      for (long qualityCheckId : ids) {
        InQualityCheckVo inQualityCheck = this.baseMapper.selectVoById(qualityCheckId);
        if (ObjectUtil.isEmpty(inQualityCheck)) {
          throw new ServiceException("未获取到质检管理");
        }
        // 修改数据
        LambdaUpdateWrapper<InQualityCheck> lambda = new UpdateWrapper<InQualityCheck>().lambda();
        lambda.set(InQualityCheck::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
          .set(InQualityCheck::getAuditor, LoginHelper.getNickname())
          .set(InQualityCheck::getAuditDate, DateUtils.getNowDate())
          .eq(InQualityCheck::getQualityCheckId, qualityCheckId);
        this.update(lambda);//提交


        if (ObjectUtil.isNotEmpty(inQualityCheck.getOrderCode())) {
          // 质检单的明细数据
          LambdaQueryWrapper<InQualityCheckDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
          detailLambdaQueryWrapper.eq(InQualityCheckDetail::getQualityCheckId, qualityCheckId);
          List<InQualityCheckDetail> qualityCheckDetails = iInQualityCheckDetailService.list(detailLambdaQueryWrapper);


          for (InQualityCheckDetail item : qualityCheckDetails) {
            // 修改预到货单的次品数量
            LambdaUpdateWrapper<InOrderDetail> updateWrapper = new UpdateWrapper<InOrderDetail>().lambda();
            updateWrapper.set(InOrderDetail::getDefectiveQty, item.getDefectiveQuantity());
            updateWrapper.eq(InOrderDetail::getOrderDetailId, item.getOrderDetailId());
            inOrderDetailService.update(updateWrapper);
          }
        }
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  @Override
  public R<InOrderAndDetailVo> onBlurGetByCode(Map<String, Object> map) {
    try {
      String type = Convert.toStr(map.get("type"));
      LambdaQueryWrapper<InOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(InOrder::getOrderCode, map.get("orderCode"));
      if (StrUtil.equals(type, "退货单")) {
        List<String> statusEnums = new ArrayList<>();
        statusEnums.add(InOrderStatusEnum.PARTIAL_FINISHED.getName());
        statusEnums.add(InOrderStatusEnum.FINISHED.getName());
        orderLambdaQueryWrapper.in(InOrder::getOrderStatus, statusEnums);
      }
      var inOrder = inOrderService.getOne(orderLambdaQueryWrapper);
      if (ObjectUtil.isEmpty(inOrder)) {
        throw new ServiceException("获取失败,未找到相应的单据信息。");
      }
      List<InOrderDetail> orderDetailList = inOrderDetailService.selectListByMainId(inOrder.getOrderId());

      InOrderAndDetailVo orderAndDetailVO = new InOrderAndDetailVo(); //重新构建Vo，传到前端
      orderAndDetailVO.setDetails(orderDetailList);
      orderAndDetailVO.setOrder(inOrder);
      return R.ok(orderAndDetailVO);
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#region 质检单add

  /**
   * 质检单add
   *
   * @param bo
   * @return
   */
  @Override
  public R<Map<String, Object>> add(ApiInQualityCheckBo bo) {

//    InOrder inOrder = inOrderService.getBySourceCode(bo.getSourceCode());
    LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrderDetail::getCaseNumber, bo.getSourceCode())
      .last("limit 1");
    InOrderDetail detail = inOrderDetailService.getOnly(queryWrapper);
    Assert.isFalse(ObjectUtil.isNull(detail), bo.getSourceCode() + "送货单不存在");

    InOrder inOrder = inOrderService.getById(detail.getOrderId());

    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByCode(inOrder.getConsignorCode());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), inOrder.getConsignorCode() + "货主不存在");
    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorName(consignorInfo.getConsignorName());

    // 验证仓库
    BaseStorage storageInfo = baseStorageService.getByName(inOrder.getStorageName());
    Assert.isFalse(ObjectUtil.isNull(storageInfo), inOrder.getStorageName() + "仓库不存在");
    bo.setStorageName(storageInfo.getStorageName());
    bo.setStorageId(storageInfo.getStorageId());


    InQualityCheck inQualityCheck = new InQualityCheck();
    BeanUtil.copyProperties(bo, inQualityCheck);

    String code = DBUtils.getCodeRegular(MenuEnum.MENU_1641);
    inQualityCheck.setQualityCheckCode(code);

    this.save(inQualityCheck);

    if (!bo.getDetailList().isEmpty()) {
      for (var item : bo.getDetailList()) {
        InQualityCheckDetail inQualityCheckDetail = new InQualityCheckDetail();
        BeanUtil.copyProperties(item, inQualityCheckDetail);
        inQualityCheckDetail.setOrderId(inOrder.getOrderId());
        inQualityCheckDetail.setQualityCheckId(inQualityCheck.getQualityCheckId());
        iInQualityCheckDetailService.save(inQualityCheckDetail);
      }
    }


    Map<String, Object> result = new HashMap<>();
    result.put("qualityCheckId", inQualityCheck.getQualityCheckId());
    result.put("qualityCheckCode", inQualityCheck.getQualityCheckCode());
    return R.ok("订单保存成功", result);
  }
  //#endregion
}
