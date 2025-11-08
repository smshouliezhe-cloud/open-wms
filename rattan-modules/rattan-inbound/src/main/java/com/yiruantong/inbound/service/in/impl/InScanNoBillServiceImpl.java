package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.enums.in.InSourceTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInScanNoBillService;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InScanNoBillServiceImpl implements IInScanNoBillService {
  private final IBasePositionService basePositionService;
  private final ICoreInventoryService coreInventoryService;
  private final IBaseProductService baseProductService;
  private final ISysConfigService sysConfigService;
  private final IInOrderService inOrderService;
  private final IBaseStorageService baseStorageService;
  private final IBaseProviderService baseProviderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IBaseConsignorService baseConsignorService;
  private final IInScanOrderService inScanOrderService;

  /**
   * 无单扫描入库
   *
   * @param inScanOrderBo 无单扫描入库参数
   * @return 返回R
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> noBillEnterSave(InScanOrderBo inScanOrderBo) {
    Long storageId = inScanOrderBo.getStorageId();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据

    Assert.isFalse(ObjectUtil.isEmpty(storageId), "仓库ID不能为空！");
    Assert.isFalse(dataList.isEmpty(), "扫描明细不能为空！");

    BaseStorage baseStorage = baseStorageService.getById(storageId);
    Assert.isFalse(ObjectUtil.isEmpty(baseStorage), "仓库不存在！");

    // 是否开启唯一码 - 自动生成唯一码
    var in_autoSingleSignCode = sysConfigService.getConfigBool("in_autoSingleSignCode");

    //#region 对扫描明细循环验证
    BaseProduct baseProduct = null;
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      Long productId = inScanOrderDetailBo.getProductId();
      String positionName = inScanOrderDetailBo.getPositionName();
      String plateCode = inScanOrderDetailBo.getPlateCode();

      // 优先采用主表供应商
      if (ObjectUtil.isNotEmpty(inScanOrderBo.getProviderId())) {
        inScanOrderDetailBo.setProviderId(inScanOrderBo.getProviderId());
        inScanOrderDetailBo.setProviderCode(inScanOrderBo.getProviderCode());
        inScanOrderDetailBo.setProviderShortName(inScanOrderBo.getProviderShortName());
      }

      String providerShortName = inScanOrderDetailBo.getProviderShortName();

      // 获取商品信息
      baseProduct = baseProductService.getById(productId);
      Assert.isFalse(ObjectUtil.isEmpty(baseProduct), "商品信息不存在！");

      // 验证货位是否存在
      LambdaQueryWrapper<BasePosition> positionQueryWrapper = new LambdaQueryWrapper<>();
      positionQueryWrapper.eq(BasePosition::getPositionName, positionName);
      if (!basePositionService.getBaseMapper().exists(positionQueryWrapper)) {
        throw new ServiceException("货位：" + positionName + "不存在！");
      }

      // 验证供应商是否存在
      LambdaQueryWrapper<BaseProvider> providerQueryWrapper = new LambdaQueryWrapper<>();
      providerQueryWrapper.eq(BaseProvider::getProviderShortName, providerShortName);
      if (!basePositionService.getBaseMapper().exists(positionQueryWrapper)) {
        throw new ServiceException("供应商：" + providerShortName + "不存在！");
      }

      // 验证拍号是否已使用
      LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
      inventoryLambdaQueryWrapper
        .eq(CoreInventory::getStorageId, storageId)
        .eq(CoreInventory::getPlateCode, plateCode);
      if (coreInventoryService.exists(inventoryLambdaQueryWrapper)) {
        throw new ServiceException("拍号：" + plateCode + "库存中已存在，不能重复使用！");
      }
    }
    //#endregion

    //#region 生成预到货主表
    // 获得供应商
    var firstItem = dataList.get(0);
    BaseProvider baseProvider = baseProviderService.getByShortName(firstItem.getProviderShortName());


    InOrder inOrder = new InOrder();
    // 对象数据的拷贝
    BeanUtil.copyProperties(dataList.get(0), inOrder);
    inOrder.setSourceType(InSourceTypeEnum.NO_BILL.getName());

    //前台页面是码盘扫描入库 ，遇到货单状态是 库内码盘
    if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PC_STOREHOUSE_STACKING) {
      inOrder.setOrderType(InOrderTypeEnum.STOREHOUSE_STACKING.getName()); //库内码盘
    } else if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PDA_NO_BILL_FLASH_IN) {
      inOrder.setOrderType(InOrderTypeEnum.NO_BILL_FLASH.getName()); //一键闪入
      // 记录来源单号，是从出库单来的
      inOrder.setSourceType(InSourceTypeEnum.NO_BILL_FLASH.getName());
      inOrder.setSourceId(Convert.toStr(inScanOrderBo.getOrderId()));
      inOrder.setSourceCode(inScanOrderBo.getOrderCode());
    } else if (inScanOrderBo.getScanInType() == InventorySourceTypeEnum.PDA_NO_BILL_SHELVE) {
      inOrder.setOrderType(InOrderTypeEnum.PDA_NO_BILL_SHELVE.getName()); //库内码盘
    } else {
      inOrder.setOrderType(InOrderTypeEnum.NO_BILL.getName());
    }

    inOrder.setOrderId(null);
    inOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001));
    inOrder.setStorageId(baseStorage.getStorageId());
    inOrder.setStorageName(baseStorage.getStorageName());

    // 优先采用前端传入的货主
    if (ObjectUtil.isNotEmpty(inScanOrderBo.getProviderId())) {
      inOrder.setProviderId(inScanOrderBo.getProviderId());
      inOrder.setProviderCode(inScanOrderBo.getProviderCode());
      inOrder.setProviderShortName(inScanOrderBo.getProviderShortName());
      inOrder.setProviderName(inScanOrderBo.getProviderShortName());
    }
    // 如果前端没有传入，采用商品信息默认供应商
    if (ObjectUtil.isNotEmpty(inScanOrderBo.getProviderId())) {
      inOrder.setProviderId(baseProvider.getProviderId());
      inOrder.setProviderCode(baseProvider.getProviderCode());
      inOrder.setProviderShortName(baseProvider.getProviderShortName());
      inOrder.setProviderName(baseProvider.getProviderName());
    }

    // 优先采用前端传入的货主
    if (ObjectUtil.isNotEmpty(inScanOrderBo.getProviderId())) {
      inOrder.setConsignorId(inScanOrderBo.getConsignorId());
      inOrder.setConsignorCode(inScanOrderBo.getConsignorCode());
      inOrder.setConsignorName(inScanOrderBo.getConsignorName());
    }
    // 如果前端没有传入，采用商品信息默认货主
    if (ObjectUtil.isEmpty(inOrder.getConsignorId())) {
      inOrder.setConsignorId(baseProduct.getConsignorId());
      inOrder.setConsignorCode(baseProduct.getConsignorCode());
      inOrder.setConsignorName(baseProduct.getConsignorName());
    }
    Assert.isFalse(ObjectUtil.isEmpty(inOrder.getConsignorId()), "没有货主信息，请选择货主或者绑定货主信息");

    inOrder.setApplyDate(new Date());
    inOrder.setNickName(LoginHelper.getNickname());
    inOrder.setUserId(LoginHelper.getUserId());
    inOrder.setDeptId(LoginHelper.getDeptId());
    inOrder.setDeptName(LoginHelper.getDeptName());
    inOrder.setShelveStatus(InShelveStatusEnum.WAITING.getName());

    inOrder.setOrderStatus(InOrderStatusEnum.IN_TRANSIT.getName());
    inOrder.setAuditing(AuditEnum.AUDITED_SUCCESS.getId()); // 审核成功
    inOrder.setAuditor(LoginHelper.getNickname());
    inOrder.setAuditDate(DateUtil.date());
    inOrderService.save(inOrder);
    inScanOrderBo.setOrderId(inOrder.getOrderId());
    inScanOrderBo.setOrderCode(inOrder.getOrderCode());
    //#endregion

    //#region 生成预到货明细
    List<InOrderDetail> inOrderDetailList = new ArrayList<>();
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      Long productId = inScanOrderDetailBo.getProductId();
      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量

      // 新增明细
      InOrderDetail inOrderDetail = new InOrderDetail();
      baseProduct = baseProductService.getById(productId);
      // 对象数据的拷贝
      BeanUtil.copyProperties(baseProduct, inOrderDetail);
      BeanUtil.copyProperties(inScanOrderDetailBo, inOrderDetail);

      inOrderDetail.setOrderId(inOrder.getOrderId());
      inOrderDetail.setQuantity(finishedQuantity);
      if (in_autoSingleSignCode) {
        // 开启唯一码
        String uuid = IdUtil.simpleUUID();
        inOrderDetail.setSingleSignCode(uuid);
      }
      inOrderDetail.setRate(baseProduct.getRate());
      inOrderDetail.setSourceType(InSourceTypeEnum.NO_BILL.getName());

      BigDecimal hundred = new BigDecimal(100);
      BigDecimal thousand = new BigDecimal(1000);


      BigDecimal ratePrice = B.mul(inOrderDetail.getPurchasePrice(), inOrderDetail.getRate());
      inOrderDetail.setRatePrice(B.add(ratePrice, baseProduct.getPurchasePrice())); // 税价（含税金额）
      inOrderDetail.setRateAmount(B.mul(inOrderDetail.getRatePrice(), finishedQuantity)); // 含税金额

      BigDecimal rowWeight = B.mul(inOrderDetail.getWeight(), finishedQuantity);
      inOrderDetail.setRowWeight(rowWeight); // 小计重量
      inOrderDetail.setRowWeightTon(B.div(rowWeight, thousand)); // 小计重量（吨）
      inOrderDetail.setRowCube(B.mul(inOrderDetail.getQuantity(), inOrderDetail.getUnitCube())); // 小计体积
      inOrderDetail.setRowNetWeight(rowWeight); // 小计重量
      inOrderDetail.setBigQty(B.ceiling(B.div(inOrderDetail.getQuantity(), baseProduct.getUnitConvert()))); // 大单位数量
      inOrderDetail.setPurchaseAmount(B.mul(finishedQuantity, inOrderDetail.getPurchasePrice()));

      inOrderDetailService.save(inOrderDetail);
      inOrderDetailList.add(inOrderDetail);

      // 复制扫描明细
      inScanOrderDetailBo.setOrderId(inOrderDetail.getOrderId());
      inScanOrderDetailBo.setOrderDetailId(inOrderDetail.getOrderDetailId());
    }
    //#endregion

    //#region  主表求和
    inOrder.setTotalQuantity(StreamUtils.sum(inOrderDetailList, InOrderDetail::getQuantity));
    inOrder.setTotalRateAmount(StreamUtils.sum(inOrderDetailList, InOrderDetail::getRateAmount));
    inOrder.setTotalWeight(StreamUtils.sum(inOrderDetailList, InOrderDetail::getRowWeight));
    inOrder.setTotalAmount(StreamUtils.sum(inOrderDetailList, InOrderDetail::getPurchaseAmount));
    inOrder.setTotalNetWeight(StreamUtils.sum(inOrderDetailList, InOrderDetail::getNetWeight));
    inOrder.setTotalPackage(StreamUtils.sum(inOrderDetailList, InOrderDetail::getRowPackage));
    inOrderService.getBaseMapper().updateById(inOrder);
    //#endregion

    // 调用通用入库操作
    return inScanOrderService.normalScanSave(inScanOrderBo);
  }
}
