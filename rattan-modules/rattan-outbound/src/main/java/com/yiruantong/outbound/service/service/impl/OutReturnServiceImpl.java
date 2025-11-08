package com.yiruantong.outbound.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutReturnStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.vo.OutOrderDataSetVo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailHolderComposeVO;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.domain.service.api.ApiOutReturnBo;
import com.yiruantong.outbound.domain.service.bo.OutReturnBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnVo;
import com.yiruantong.outbound.mapper.service.OutReturnMapper;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import com.yiruantong.outbound.service.service.IOutReturnService;
import com.yiruantong.system.service.core.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 出库退货单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class OutReturnServiceImpl extends ServiceImplPlus<OutReturnMapper, OutReturn, OutReturnVo, OutReturnBo> implements IOutReturnService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IBaseConsignorService baseConsignorService;
  private final IOutReturnDetailService outReturnDetailService;


  //#region 出库信息
  @Override
  public R<OutOrderDataSetVo> onBlurGetByCode(Map<String, Object> map) {
    try {

      LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(OutOrder::getOrderCode, map.get("orderCode"));
      var outOrder = outOrderService.getOne(orderLambdaQueryWrapper);
      if (ObjectUtil.isEmpty(outOrder)) {
        throw new ServiceException("单号不存在获取失败。");
      }
      //  打包完成 发运完成 已签收 已妥投
      if (!outOrder.getOrderStatus().equals(OutOrderStatusEnum.PACKAGE_FINISHED.getName()) && !outOrder.getOrderStatus().equals(OutOrderStatusEnum.SHIPMENT_FINISHED.getName()) && !outOrder.getOrderStatus().equals(OutOrderStatusEnum.SIGNED.getName()) && !outOrder.getOrderStatus().equals(OutOrderStatusEnum.DELIVERED.getName())) {
        throw new ServiceException("只有打包完成、发运完成、已签收、已妥投、才可以进行操作");
      }
      List<OutOrderDetail> orderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId());


      List<OutOrderDetailHolderComposeVO> outOrderDetails = new ArrayList<>();

      for (var item : orderDetailList) {
        OutOrderDetailHolderComposeVO outOrderDetailHolderComposeVO = new OutOrderDetailHolderComposeVO();
        BeanUtil.copyProperties(item, outOrderDetailHolderComposeVO);

        LambdaQueryWrapper<CoreInventoryHolder> holderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        holderLambdaQueryWrapper.eq(CoreInventoryHolder::getDetailId, item.getOrderDetailId());
        holderLambdaQueryWrapper.last("limit 1");
        CoreInventoryHolder coreInventoryHolder = coreInventoryHolderService.getOne(holderLambdaQueryWrapper);

        if (ObjectUtil.isNotNull(coreInventoryHolder)) {
          outOrderDetailHolderComposeVO.setProviderId(coreInventoryHolder.getProviderId());
          outOrderDetailHolderComposeVO.setProviderCode(coreInventoryHolder.getProviderCode());
          outOrderDetailHolderComposeVO.setProviderShortName(coreInventoryHolder.getProviderShortName());
          outOrderDetailHolderComposeVO.setPositionName(coreInventoryHolder.getPositionName());
          outOrderDetailHolderComposeVO.setBatchNumber(coreInventoryHolder.getBatchNumber());
          outOrderDetailHolderComposeVO.setProduceDate(coreInventoryHolder.getProduceDate());
        }

        BaseProduct productInfo = baseProductService.getById(item.getProductId());

        if (ObjectUtil.isNotEmpty(productInfo)) {
          outOrderDetailHolderComposeVO.setBrandName(productInfo.getBrandName());
          outOrderDetailHolderComposeVO.setBrandId(productInfo.getBrandId());
          outOrderDetailHolderComposeVO.setTypeName(productInfo.getTypeName());
          outOrderDetailHolderComposeVO.setProductBarCode(productInfo.getProductBarCode());
          outOrderDetailHolderComposeVO.setThermocline(productInfo.getThermocLine());
          outOrderDetailHolderComposeVO.setImages(productInfo.getImages());
        }

        var providerInfo = baseProviderService.getByShortName(outOrderDetailHolderComposeVO.getProviderShortName());

        if (ObjectUtil.isNotEmpty(providerInfo)) {
          outOrderDetailHolderComposeVO.setRate(providerInfo.getRate());
        }
        outOrderDetails.add(outOrderDetailHolderComposeVO);
      }


      OutOrderDataSetVo orderAndDetailVO = new OutOrderDataSetVo(); //重新构建Vo，传到前端
      orderAndDetailVO.setDetailsVo(outOrderDetails);
      orderAndDetailVO.setOrder(outOrder);
      return R.ok(orderAndDetailVO);
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  //#endregion

  //#region 出库退货单批量审核

  /**
   * 出库退货单批量审核
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    // 写具体审核逻辑
    try {
      for (long returnId : ids) {
        OutReturn outReturn = this.baseMapper.selectById(returnId);
        if (ObjectUtil.isEmpty(outReturn)) {
          throw new ServiceException("未获取到出库退货单");
        }
        // 修改数据
        LambdaUpdateWrapper<OutReturn> lambda = new UpdateWrapper<OutReturn>().lambda();
        lambda.set(OutReturn::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
          .set(OutReturn::getAuditor, LoginHelper.getNickname())
          .set(OutReturn::getReturnStatus, AuditEnum.AUDITED_SUCCESS.getName())
          .set(OutReturn::getAuditDate, DateUtils.getNowDate())
          .eq(OutReturn::getReturnId, returnId);
        this.update(lambda);//提交
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

  public OutReturn getSourceCode(String sourceCode) {
    LambdaQueryWrapper<OutReturn> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(OutReturn::getSourceCode, sourceCode);

    return this.getOnly(orderLambdaQueryWrapper);
  }

  //#region add
  @Override
  public R<Map<String, Object>> add(ApiOutReturnBo bo) {
    Assert.isFalse(ObjectUtil.isEmpty(bo.getDetailList()), "退货单明细不能为空！");

    // 验证单号是否已推送
    OutReturn storeOrderInfo = this.getSourceCode(bo.getSourceCode());
    if (ObjectUtil.isNotNull(storeOrderInfo)) {
      Map<String, Object> result = new HashMap<>();
      result.put("returnId", storeOrderInfo.getReturnId());
      result.put("returnCode", storeOrderInfo.getReturnCode());
      return R.ok(bo.getSourceCode() + "已存在，不允许重复推送", result);
    }

    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByCode(bo.getConsignorCode());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), "货主不存在！");

    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorName(consignorInfo.getConsignorName());

    // 验证仓库信息，如果推送仓库，走指定仓库，没有走系统某人仓库
    if (ObjectUtil.isNotNull(bo.getStorageName())) {
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在！");
      if (ObjectUtil.isNotNull(storageInfo)) {
        bo.setStorageId(storageInfo.getStorageId());
      }
    } else {
      // 接口推送时需要仓库
      String erp_outstorage_Id = sysConfigService.selectConfigByKey("erp_outstorage_Id");
      if (StringUtils.isNotEmpty(erp_outstorage_Id)) {
        // 验证仓库
        BaseStorage storageInfo = baseStorageService.getById(erp_outstorage_Id);
        Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在！");

        bo.setStorageId(storageInfo.getStorageId());
        bo.setStorageName(storageInfo.getStorageName());
      } else {
        return R.fail("请联系管理员设置默认仓库");
      }
    }
    bo.setApplyDate(DateUtil.date());

    for (var detailInfo : bo.getDetailList()) {
      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
      Assert.isFalse(ObjectUtil.isNull(prodInfo), "商品编号不存在！");

      detailInfo.setReturnQuantity(detailInfo.getReturnQuantity());
      detailInfo.setProductId(prodInfo.getProductId());
      detailInfo.setProductModel(prodInfo.getProductModel());
      detailInfo.setProductName(prodInfo.getProductName());
      detailInfo.setProductSpec(prodInfo.getProductSpec());
      detailInfo.setRowWeight(B.mul(detailInfo.getWeight(), detailInfo.getReturnQuantity()));

//      if (ObjectUtil.isNull(detailInfo.getReturnQuantity()) || B.isLessOrEqual(detailInfo.getReturnQuantity(), BigDecimal.ZERO)) {
//        return R.fail(detailInfo.getProductModel() + "数量必须大于0");
//      }
    }

    OutReturn dataInfo = new OutReturn();
    BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("returnId"));
    dataInfo.setReturnCode(DBUtils.getCodeRegular(MenuEnum.MENU_1733));
//    dataInfo.setReturnStatus(InOrderStatusEnum.NEWED.getName());

    if (ObjectUtil.isNotNull(bo.getOrderType())) {
      dataInfo.setOrderType(bo.getOrderType());
    } else {
      dataInfo.setOrderType("API预到货退货");
    }
    this.save(dataInfo);

    for (var detailInfo : bo.getDetailList()) {
      OutReturnDetail detail = new OutReturnDetail();
      BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("returnDetailId"));
      detail.setReturnId(dataInfo.getReturnId());
      outReturnDetailService.save(detail);
    }

    List<OutReturnDetail> returnDetails = outReturnDetailService.selectListByMainId(dataInfo.getReturnId());
    if (!returnDetails.isEmpty()) {

//      var totalQuantity = returnDetails.stream().map(OutReturnDetail::getReturnQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
//      var totalWeight = returnDetails.stream().map(OutReturnDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
//      var returnAmount = returnDetails.stream().map(OutReturnDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalQuantity = returnDetails.stream().map(m -> B.ifNullDefaultZero(m.getReturnQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalWeight = returnDetails.stream().map(m -> B.ifNullDefaultZero(m.getRowWeight())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal returnAmount = returnDetails.stream().map(m -> B.ifNullDefaultZero(m.getPurchaseAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);

      dataInfo.setTotalReturnQuantity(totalQuantity);
      dataInfo.setTotalAmount(returnAmount);
      dataInfo.setTotalWeight(totalWeight);
      this.saveOrUpdate(dataInfo);
    }

    Map<String, Object> result = new HashMap<>();
    result.put("returnId", dataInfo.getReturnId());
    result.put("returnCode", dataInfo.getReturnCode());
    return R.ok("订单保存成功", result);

  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> PdaAdd(ApiOutReturnBo info) {
    OutReturn dataInfo = new OutReturn();
    BeanUtil.copyProperties(info, dataInfo);
    dataInfo.setReturnCode(DBUtils.getCodeRegular(MenuEnum.MENU_1733));
    dataInfo.setReturnStatus(InOrderStatusEnum.NEWED.getName());
    dataInfo.setOrderType("PDA添加");
    dataInfo.setApplyDate(new Date());

    this.save(dataInfo);

    for (var detail : info.getDetailList()) {
      OutReturnDetail newDetail = new OutReturnDetail();
      BeanUtil.copyProperties(detail, newDetail);
      newDetail.setReturnId(dataInfo.getReturnId());
      newDetail.setRate(B.div(newDetail.getRate(), new BigDecimal(100L)));
      outReturnDetailService.save(newDetail);
    }
    return R.ok("添加成功，单号：" + dataInfo.getReturnCode());
  }

  //#endregion

  //#region 退货确认（仓管确认）
  @Override
  public R<Void> returnConfirm(List<Long> ids) {
    for (long returnId : ids) {
      OutReturn outReturn = this.baseMapper.selectById(returnId);
      if (ObjectUtil.isEmpty(outReturn)) {
        throw new ServiceException("未获取到出库退货单");
      }
      // 修改数据
      LambdaUpdateWrapper<OutReturn> lambda = new UpdateWrapper<OutReturn>().lambda();
      lambda.set(OutReturn::getWarehouseStatus, OutReturnStatusEnum.WAREHOUSE_CONFIRM.getName())
        .eq(OutReturn::getReturnId, returnId);
      this.update(lambda);//提交
    }
    return R.ok();
  }
  //#endregion

  //#region 退货驳回（仓管驳回）
  @Override
  public R<Void> returnReject(List<Long> ids) {
    for (long returnId : ids) {
      OutReturn outReturn = this.baseMapper.selectById(returnId);
      if (ObjectUtil.isEmpty(outReturn)) {
        throw new ServiceException("未获取到出库退货单");
      }
      // 修改数据
      LambdaUpdateWrapper<OutReturn> lambda = new UpdateWrapper<OutReturn>().lambda();
      lambda.set(OutReturn::getWarehouseStatus, OutReturnStatusEnum.WAREHOUSE_REJECT.getName())
        .eq(OutReturn::getReturnId, returnId);
      this.update(lambda);//提交
    }
    return R.ok();
  }


  //#endregion

  @Override
  public OutReturn getStoreOrderCode(String storeOrderCode) {
    LambdaQueryWrapper<OutReturn> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(OutReturn::getStoreOrderCode, storeOrderCode);
    return this.getOnly(orderLambdaQueryWrapper);
  }
}
