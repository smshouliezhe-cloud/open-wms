package com.yiruantong.inbound.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.outbound.ISortingRuleExtendService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.domain.service.api.ApiInReturnBo;
import com.yiruantong.inbound.domain.service.bo.InReturnBo;
import com.yiruantong.inbound.domain.service.vo.InReturnVo;
import com.yiruantong.inbound.liteflow.Context.InReturnAuditingContext;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.mapper.service.InReturnMapper;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import com.yiruantong.inbound.service.service.IInReturnService;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退货单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InReturnServiceImpl extends ServiceImplPlus<InReturnMapper, InReturn, InReturnVo, InReturnBo> implements IInReturnService {
  private final IInventoryCommonService inventoryCommonService;
  private final IInReturnStatusHistoryService inReturnStatusHistoryService;
  private final IInReturnDetailService inReturnDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IBaseStorageService baseStorageService;
  private final IBaseProviderService baseProviderService;
  private final IBaseProductService baseProductService;
  private final IBaseConsignorService baseConsignorService;
  private final ISysConfigService sysConfigService;
  private final IOutSortingRuleService outSortingRuleService;
  private final ISortingRuleExtendService sortingRuleExtendService;
  private final IBasePositionService basePositionService;
  private final IInEnterService inEnterService;
  private final ICoreInventoryService coreInventoryService;
  @Resource
  private final FlowExecutor flowExecutor;


  //#region  批量审核
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      InReturn returnInfo = this.getById(id);
      Assert.isFalse(StrUtil.equals(InReturnEnum.SUCCESS.getName(), returnInfo.getReturnStatus()), "单据已审核不允许在审核");

      Assert.isTrue(StrUtil.equals(InReturnEnum.NEWED.getName(), returnInfo.getReturnStatus()), "只有新建的单据才允许审核");
      //查询明细信息
      List<InReturnDetail> detailList = inReturnDetailService.selectListByMainId(id);
      InReturnAuditingContext ctx = new InReturnAuditingContext();
      ctx.setInReturn(returnInfo);
      ctx.setDetailList(detailList);
      LiteflowResponse response = flowExecutor.execute2Resp("inReturnAuditing", ctx, InScanContext.class);
      Assert.isFalse(!response.isSuccess(), response.getMessage());


    }
    return R.ok("审核成功");
  }

  //#endregion

  //#region  保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<InReturnBo> saveEditorBo, EditorVo<InReturnVo> editor) {
    Boolean isAdd = saveEditorBo.isAdd();
    if (isAdd) {
      //添加轨迹信息
      inReturnStatusHistoryService.addHistoryInfo(
        (BeanUtil.copyProperties(editor.getMaster(), InReturn.class)), InReturnActionEnum.NEWED, InReturnEnum.NEWED, "");
    }
  }
  //#endregion

  //#region 开启
  @Override
  public R<Void> open(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);

    for (Long id : ids) {
      InReturn inReturn = this.getById(id);
      Assert.isTrue(StrUtil.equals(InReturnEnum.STOP.getName(), inReturn.getReturnStatus()), "只有终止的单据才允许开启");

      //修改退货单申请单状态
      LambdaUpdateWrapper<InReturn> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(InReturn::getAuditing, null)
        .set(InReturn::getAuditDate, null)
        .set(InReturn::getAuditor, null)
        .set(InReturn::getReturnStatus, InReturnEnum.NEWED.getName())
        .eq(InReturn::getReturnId, id);

      this.update(wrapper);
      //添加轨迹
      inReturnStatusHistoryService.addHistoryInfo(inReturn, InReturnActionEnum.OPEN, InReturnEnum.STOP, InReturnEnum.NEWED, "");
    }
    return R.ok("开启成功");
  }
  //#endregion

  //#region 终止
  @Override
  public R<Void> stop(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);

    for (Long id : ids) {
      InReturn inReturn = this.getById(id);
      Assert.isTrue(StrUtil.equals(InReturnActionEnum.SUCCESS.getName(), inReturn.getReturnStatus()), "只有审核成功的单据才允许终止");
      Assert.isFalse(StrUtil.equals(InReturnActionEnum.TO_OUT_ORDER.getName(), inReturn.getReturnStatus()), "已转出库的单据不允许在终止");

      //清除站位信息
      coreInventoryHolderService.clearHolder(List.of(HolderSourceTypeEnum.RETURN_ORDER), id);
      //修改退货单申请单状态
      LambdaUpdateWrapper<InReturn> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(InReturn::getReturnStatus, InReturnEnum.STOP.getName())
        .set(InReturn::getSortingStatus, SortingStatusEnum.NONE.getId())
        .eq(InReturn::getReturnId, id);
      this.update(wrapper);

      //修改退货单申请单状态
      LambdaUpdateWrapper<InReturnDetail> wrapperDetail = new LambdaUpdateWrapper<>();
      wrapperDetail.set(InReturnDetail::getSortingStatus, SortingStatusEnum.NONE.getId())
        .set(InReturnDetail::getLackStorage, BigDecimal.ZERO)
        .eq(InReturnDetail::getReturnId, id);
      inReturnDetailService.update(wrapperDetail);
      //添加轨迹
      inReturnStatusHistoryService.addHistoryInfo(inReturn, InReturnActionEnum.STOP, InReturnEnum.SUCCESS, InReturnEnum.STOP, "");
    }
    return R.ok("终止成功");
  }
  //#endregion


  //#region 更新分拣状态
  @Override
  public boolean updateAllocateStatus(InReturn inReturn, SortingStatusEnum sortingStatus) {
    LambdaUpdateWrapper<InReturn> lambda = new UpdateWrapper<InReturn>().lambda();
    lambda.set(InReturn::getSortingStatus, sortingStatus.getId())
      .set(InReturn::getAuditor, LoginHelper.getNickname())
      .set(InReturn::getAuditDate, DateUtil.date())
      .set(InReturn::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .eq(InReturn::getReturnId, inReturn.getReturnId());

    return this.update(lambda);//更新状态
  }
  //#endregion

  @Override
  public InReturn getSourceCode(String sourceCode) {
    LambdaQueryWrapper<InReturn> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(InReturn::getSourceCode, sourceCode);

    return this.getOne(orderLambdaQueryWrapper);
  }


  //#region 新增
  @Override
  public R<Map<String, Object>> add(ApiInReturnBo bo) {
    LoginUser loginUser = LoginHelper.getLoginUser();

    Assert.isFalse(bo.getDetailList().isEmpty(), "退货单明细不能为空");


    // 验证单号是否已推送
    InReturn storeOrderInfo = this.getSourceCode(bo.getSourceCode());
    if (ObjectUtil.isNotNull(storeOrderInfo)) {
      Map<String, Object> result = new HashMap<>();
      result.put("returnId", storeOrderInfo.getReturnId());
      result.put("returnCode", storeOrderInfo.getReturnCode());
      return R.ok(bo.getSourceCode() + "已存在，不允许重复推送", result);
    }

    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), bo.getConsignorName() + "货主不存在");
    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorCode(consignorInfo.getConsignorCode());


    // 验证供应商
    BaseProvider baseProviderInfo = baseProviderService.getByShortName(bo.getProviderShortName());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), bo.getProviderShortName() + "供应商不存在");
    bo.setProviderCode(baseProviderInfo.getProviderCode());
    bo.setProviderId(baseProviderInfo.getProviderId());


    // 验证仓库信息，如果推送仓库，走指定仓库，没有走系统某人仓库
    if (ObjectUtil.isNotNull(bo.getStorageName())) {
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());

      if (ObjectUtil.isNotNull(storageInfo)) {
        bo.setStorageId(storageInfo.getStorageId());
      } else {
        Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在");
      }
    } else {
      // 接口推送时需要仓库
      String erp_outstorage_Id = sysConfigService.selectConfigByKey("erp_outstorage_Id");
      if (StringUtils.isNotEmpty(erp_outstorage_Id)) {
        // 验证仓库
        BaseStorage storageInfo = baseStorageService.getById(erp_outstorage_Id);

        if (ObjectUtil.isNull(storageInfo)) {
          Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在");
        }
        bo.setStorageId(storageInfo.getStorageId());
        bo.setStorageName(storageInfo.getStorageName());
      } else {
        return R.fail("请联系管理员设置默认仓库");
      }
    }
    bo.setApplyDate(DateUtil.date());

    for (var detailInfo : bo.getDetailList()) {
      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
      Assert.isFalse(ObjectUtil.isNull(prodInfo), detailInfo.getProductCode() + "商品编号不存在");

      detailInfo.setQuantity(detailInfo.getQuantity());
      detailInfo.setProductId(prodInfo.getProductId());
      detailInfo.setProductModel(prodInfo.getProductModel());
      detailInfo.setProductName(prodInfo.getProductName());
      detailInfo.setProductSpec(prodInfo.getProductSpec());
      detailInfo.setRowWeight(B.mul(detailInfo.getWeight(), detailInfo.getQuantity()));

//        Assert.isFalse(ObjectUtil.isNull(detailInfo.getQuantity()), detailInfo.getProductModel() + "数量必须大于0");
    }


    InReturn dataInfo = new InReturn();
    BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("returnId"));
    dataInfo.setReturnCode(DBUtils.getCodeRegular(MenuEnum.MENU_1661));

    dataInfo.setReturnStatus(InOrderStatusEnum.NEWED.getName());

    if (ObjectUtil.isNotNull(bo.getOrderType())) {
      dataInfo.setOrderType(bo.getOrderType());
    } else {
      dataInfo.setOrderType("API预到货退货");
    }
    dataInfo.setUserId(loginUser.getUserId());
    dataInfo.setNickName(loginUser.getNickname());
    this.save(dataInfo);
    InEnter inEnter = inEnterService.getByCode(dataInfo.getOrderCode());


    for (var detailInfo : bo.getDetailList()) {
      InReturnDetail detail = new InReturnDetail();
      BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("returnDetailId"));

      detail.setReturnId(dataInfo.getReturnId());
      inReturnDetailService.save(detail);


      // 新增一条规则
      OutSortingRule outSortingRule = new OutSortingRule();
      outSortingRule.setOrderId(dataInfo.getReturnId());
      outSortingRule.setOrderCode(dataInfo.getReturnCode());
      outSortingRule.setOrderDetailId(detail.getReturnDetailId());
      outSortingRule.setRuleType(InReturnEnum.QUALITY_INSPECTION_RETURN.getName());
      outSortingRule.setProductId(detail.getProductId());
      outSortingRule.setProductCode(detail.getProductCode());
      if (ObjectUtil.isNotNull(detail.getPositionName())) {
        outSortingRule.setPositionName(detail.getPositionName());
      }
      // 根据入库单查找有效库存，并赋值到规则
      if (ObjectUtil.isNotNull(inEnter)) {
        LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        inventoryLambdaQueryWrapper.eq(CoreInventory::getProductId, detail.getProductId())
          .eq(CoreInventory::getProductCode, detail.getProductCode())
          .eq(CoreInventory::getBillCode, inEnter.getEnterCode());
        CoreInventory coreInventory = coreInventoryService.getOnly(inventoryLambdaQueryWrapper);
        if (ObjectUtil.isNotNull(coreInventory.getBatchNumber())) {
          outSortingRule.setBatchNumber(coreInventory.getBatchNumber());
        }
        if (ObjectUtil.isNotNull(coreInventory)) {
          outSortingRule.setInventoryId(coreInventory.getInventoryId());
        }
      } else {
        if (ObjectUtil.isNotNull(detail.getBatchNumber())) {
          outSortingRule.setBatchNumber(detail.getBatchNumber());
        }
      }

      if (ObjectUtil.isNotNull(detail.getProduceDate())) {
        outSortingRule.setProduceDate(detail.getProduceDate());
      }
      outSortingRuleService.save(outSortingRule);
    }
    List<InReturnDetail> returnDetails = inReturnDetailService.selectListByMainId(dataInfo.getReturnId());
    if (!returnDetails.isEmpty()) {

      var totalQuantity = returnDetails.stream().map(InReturnDetail::getReturnQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
//        var totalWeight = returnDetails.stream().map(InReturnDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
//        var returnAmount = returnDetails.stream().map(InReturnDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

      dataInfo.setTotalReturnQuantity(totalQuantity);
//        dataInfo.setTotalAmount(returnAmount);
//        dataInfo.setTotalWeight(totalWeight);
      this.saveOrUpdate(dataInfo);
    }


    Map<String, Object> result = new HashMap<>();
    result.put("returnId", dataInfo.getReturnId());
    result.put("returnCode", dataInfo.getReturnCode());
    return R.ok("订单保存成功", result);

  }
  //#endregion

}
