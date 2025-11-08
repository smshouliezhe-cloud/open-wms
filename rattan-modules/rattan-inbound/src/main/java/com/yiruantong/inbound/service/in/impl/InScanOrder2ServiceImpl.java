package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.translation.annotation.Translation;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.system.service.task.ITaskQueueService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InScanOrder2ServiceImpl implements IInScanOrder2Service{
  //#region service实例化
  private final ITaskQueueService taskQueueService;
  private final IInOrderService inOrderService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInScanOrderService inScanOrderService;
  private final IBasePositionService basePositionService;
  //#endregion

  //#region 保存货代扫描入库
  /**
   * 保存货代扫描入库
   *
   * @param inScanOrderBo 前端参数
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Void> saveOrderFreight(@NotNull InScanOrderBo inScanOrderBo) {
    Long orderId = inScanOrderBo.getOrderId();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList();

    // 预到货单信息
    var orderInfo = inOrderService.getById(orderId);
    inScanOrderBo.setOrderCode(orderInfo.getOrderCode());

    if (ObjectUtil.isEmpty(orderInfo)) {
      throw new ServiceException("采购单号不存在！");
    }
    //获取货位
    LambdaQueryWrapper<BasePosition> positionLqw = new LambdaQueryWrapper<>();
    positionLqw.eq(BasePosition::getStorageId, orderInfo.getStorageId())
      .eq(BasePosition::getPositionType, PositionTypeEnum.NORMAL.getId())
      .orderByDesc(BasePosition::getPositionId);
    BasePosition basePositionInfo = basePositionService.getOnly(positionLqw);

    //删除原明细数据 后续按填入的重新添加
    inOrderDetailService.batchRemove(orderId);

    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      BaseProduct productInfo = baseProductService.getByCodeAndName(inScanOrderDetailBo.getProductCode(),inScanOrderDetailBo.getProductName());
        //商品不存在的情况新增
        if (!ObjectUtil.isNotNull(productInfo)){
          BaseProvider providerInfo = baseProviderService.getByShortName("默认供应商");
          productInfo = new BaseProduct();
          String codeRegular = DBUtils.getCodeRegular(MenuEnum.MENU_1758);
          productInfo.setProductCode(codeRegular);
          productInfo.setProductName(inScanOrderDetailBo.getProductName());
          productInfo.setProductModel(codeRegular);
          productInfo.setProviderId(providerInfo.getProviderId());
          productInfo.setProviderCode(providerInfo.getProviderCode());
          productInfo.setProviderShortName(providerInfo.getProviderName());
          productInfo.setConsignorId(orderInfo.getConsignorId());
          productInfo.setConsignorCode(orderInfo.getConsignorCode());
          productInfo.setConsignorName(orderInfo.getConsignorName());
          baseProductService.save(productInfo);
        }
        //预到货单明细添加
        InOrderDetail orderDetail = new InOrderDetail();
        BigDecimal enterQuantity = orderDetail.getEnterQuantity();
        BeanUtil.copyProperties(inScanOrderDetailBo, orderDetail);
        orderDetail.setOrderDetailId(null);
        orderDetail.setOrderId(orderId);
        orderDetail.setEnterQuantity(enterQuantity);
        orderDetail.setProductModel(productInfo.getProductModel());
        orderDetail.setProductCode(productInfo.getProductCode());
        orderDetail.setProductId(productInfo.getProductId());
        orderDetail.setProviderId(productInfo.getProviderId());
        orderDetail.setProviderCode(productInfo.getProviderCode());
        orderDetail.setProviderShortName(productInfo.getProviderShortName());
        orderDetail.setContainerNo(inScanOrderDetailBo.getCaseNumber());

        Map<String, Object> expandFields = orderDetail.getExpandFields();
        if(ObjectUtil.isNull(expandFields)) {
          expandFields = new HashMap<>();
        }
        expandFields.put("marking", inScanOrderDetailBo.getMarking());
        expandFields.put("containerType", inScanOrderDetailBo.getContainerType());
        expandFields.put("packagingType", inScanOrderDetailBo.getPackagingType());
        orderDetail.setExpandFields(expandFields);

        inOrderDetailService.saveOrUpdate(orderDetail);

        inScanOrderDetailBo.setProductId(productInfo.getProductId());
        inScanOrderDetailBo.setProviderId(productInfo.getProviderId());
        inScanOrderDetailBo.setProviderCode(productInfo.getProviderCode());
        inScanOrderDetailBo.setProviderShortName(productInfo.getProviderShortName());
        inScanOrderDetailBo.setProductId(productInfo.getProductId());
        inScanOrderDetailBo.setProductCode(productInfo.getProductCode());
        inScanOrderDetailBo.setProductName(productInfo.getProductName());
        inScanOrderDetailBo.setOrderDetailId(orderDetail.getOrderDetailId());
        inScanOrderDetailBo.setPositionName(basePositionInfo.getPositionName());

    }
    inScanOrderService.normalScanSave(inScanOrderBo);

    //调用RabbitMQ
    RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
    rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.FREIGHT_ORDER_BOOKCABIN_PRODUCT); //类别
    rabbitReceiverDto.setBillId(orderId);
    rabbitReceiverDto.setBillCode(orderInfo.getOrderCode());
    rabbitReceiverDto.setLoginUser(LoginHelper.getLoginUser());
    taskQueueService.createTask(rabbitReceiverDto);

    return R.ok();
  }
  //#endregion

}
