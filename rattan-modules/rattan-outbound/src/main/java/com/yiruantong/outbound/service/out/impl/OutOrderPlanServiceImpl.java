package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProductTypeService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.other.XgOrderTypeEnum;
import com.yiruantong.common.core.enums.out.*;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.core.CoreSortingRule;
import com.yiruantong.inventory.service.core.ICoreSortingRuleService;
import com.yiruantong.outbound.domain.order.OutRetail;
import com.yiruantong.outbound.domain.order.OutRetailDetail;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanVo;
import com.yiruantong.outbound.mapper.out.OutOrderPlanMapper;
import com.yiruantong.outbound.service.order.IOutRetailDetailService;
import com.yiruantong.outbound.service.order.IOutRetailService;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * 出库计划单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class OutOrderPlanServiceImpl extends ServiceImplPlus<OutOrderPlanMapper, OutOrderPlan, OutOrderPlanVo, OutOrderPlanBo> implements IOutOrderPlanService {
  //SaleRetail
  public final IOutOrderPlanDetailService outOrderPlanDetailService;
  public final IBaseProductService baseProductService;
  private final IOutRetailService outRetailService;
  private final IOutRetailDetailService outRetailDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ISysConfigService sysConfigService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseStorageService baseStorageService;
  private final IOutOrderPlanStatusHistoryService outOrderPlanStatusHistoryService;
  private final IBaseConsignorService baseConsignorService;
  private final IOutSortingRuleService outSortingRuleService;
  private final IBaseProductTypeService baseProductTypeService;
  private final ICoreSortingRuleService coreSortingRuleService;

  /**
   * 批量审核
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    for (long orderPlanId : ids) {
      OutOrderPlan outOrderPlan = this.baseMapper.selectById(orderPlanId);
      if (ObjectUtil.isEmpty(outOrderPlan)) {
        throw new ServiceException("未获取到出库计划单");
      }
      String planStatus = outOrderPlan.getPlanStatus();
      if (ObjectUtil.isNotNull(planStatus) && !planStatus.equals(OutOrderStatusEnum.NEWED.getName())) {
        throw new ServiceException(outOrderPlan.getOrderPlanCode() + "只有新建的单子才允许审核");
      }
      //修改数据
      LambdaUpdateWrapper<OutOrderPlan> lambda = new UpdateWrapper<OutOrderPlan>().lambda();
      lambda.set(OutOrderPlan::getAuditDate, DateUtils.getNowDate())
        .set(OutOrderPlan::getAuditor, LoginHelper.getNickname())
        .set(OutOrderPlan::getAuditing, Convert.toLong(2))
        .set(OutOrderPlan::getPlanStatus, InOrderStatusEnum.SUCCESS.getName())
        .eq(OutOrderPlan::getOrderPlanId, orderPlanId);
      this.update(lambda);//提交

      //添加轨迹信息
      outOrderPlanStatusHistoryService.addHistoryInfo(outOrderPlan, OutOrderPlanActionEnum.AUDITING.getName(), outOrderPlan.getPlanStatus(), OutOrderPlanStatusEnum.SUCCESS.getName(), loginUser, null);
    }
    return R.ok("审核成功");
  }

  /**
   * 确认重量
   *
   * @param map 审核参数
   * @return 返回查询列表数据
   */
  @PostMapping
  public R<Void> confirmTheWeight(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    for (long orderPlanId : _ids) {
      List<OutOrderPlanDetail> outOrderPlanDetailVo = outOrderPlanDetailService.getBaseMapper().selectList(
        new LambdaQueryWrapper<OutOrderPlanDetail>()
          .eq(OutOrderPlanDetail::getOrderPlanId, orderPlanId));

      if (ObjectUtil.isEmpty(outOrderPlanDetailVo)) {
        throw new ServiceException("出库计划单明细不存在!");
      }
      OutOrderPlanVo outOrderPlanVo = this.baseMapper.selectVoById(orderPlanId);
      if (ObjectUtil.isEmpty(outOrderPlanVo)) {
        throw new ServiceException("出库计划单不存在!");
      }
      LambdaQueryWrapper<OutRetail> outRetailWrapper = new LambdaQueryWrapper<>();
      outRetailWrapper.eq(OutRetail::getRetailCode, outOrderPlanVo.getSourceCode());
      OutRetail enterDetail = outRetailService.getOne(outRetailWrapper);

      if (!ObjectUtil.isEmpty(enterDetail)) {
        throw new ServiceException("未获取到出库订单数据！!");
      }

      BigDecimal sumsalePrice = new BigDecimal(0);
      for (OutOrderPlanDetail item : outOrderPlanDetailVo) {
        //修改数据
        LambdaUpdateWrapper<OutRetailDetail> lambda = new UpdateWrapper<OutRetailDetail>().lambda();
        lambda.set(OutRetailDetail::getSalePrice, item.getSaleAmount())
          .eq(OutRetailDetail::getRetailId, item.getOrderPlanId());
        outRetailDetailService.update(lambda);
        sumsalePrice = sumsalePrice.add(item.getSaleAmount());
      }

      LambdaUpdateWrapper<OutRetail> outRetailWrapper2 = new UpdateWrapper<OutRetail>().lambda();
      outRetailWrapper2.set(OutRetail::getFinStatusText, OutOrderStatusEnum.PAYMENT.getName())
        .set(OutRetail::getTotalRateMoney, sumsalePrice)
        .eq(OutRetail::getRetailId, enterDetail.getRetailId());
      outRetailService.update(outRetailWrapper2);

      //修改数据
      LambdaUpdateWrapper<OutOrderPlan> outOrderPlanWrapper = new UpdateWrapper<OutOrderPlan>().lambda();
      outOrderPlanWrapper.set(OutOrderPlan::getAuditDate, DateUtils.getNowDate())
        .set(OutOrderPlan::getAuditor, LoginHelper.getNickname())
        .set(OutOrderPlan::getAuditing, OutOrderStatusEnum.AUDIT_SUCCESS.getName())
        .eq(OutOrderPlan::getOrderPlanId, orderPlanId);
      this.update(outOrderPlanWrapper);//提交
    }
    return R.ok("确认重量成功！");
  }


  /**
   * 转出库单
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
  @Override
  @PostMapping
  @Transactional(rollbackFor = Exception.class)
  public R<Void> toOutOrder(List<Long> ids) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    for (long orderPlanId : ids) {
      OutOrderPlanVo outOrderPlanVo = this.baseMapper.selectVoById(orderPlanId);
      OutOrderPlan outOrderPlan = this.baseMapper.selectById(orderPlanId);
      if (ObjectUtil.isEmpty(outOrderPlanVo)) {
        throw new ServiceException("出库计划单不存在!");
      }
      LambdaQueryWrapper<OutOrder> outOrderWrapper = new LambdaQueryWrapper<>();
      outOrderWrapper.eq(OutOrder::getSourceId, orderPlanId)
        .eq(OutOrder::getStoreOrderCode, outOrderPlanVo.getOrderPlanCode());
      //OutOrder enterDetail = outOrderService.getOne(outOrderWrapper);
      long count = outOrderService.count(outOrderWrapper);
      if (count > 0) {
        throw new ServiceException("已转到出库单，不允许重复操作！");
      }

      LambdaQueryWrapper<OutOrderPlanDetail> planDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      planDetailLambdaQueryWrapper
        .select(OutOrderPlanDetail::getStorageId, OutOrderPlanDetail::getConsignorId)
        .eq(OutOrderPlanDetail::getOrderPlanId, orderPlanId)
        .groupBy(OutOrderPlanDetail::getStorageId, OutOrderPlanDetail::getConsignorId);
      List<OutOrderPlanDetail> groupStorage = outOrderPlanDetailService.getBaseMapper().selectList(planDetailLambdaQueryWrapper);
      List<OutOrderPlanDetail> outOrderPlanDetail = outOrderPlanDetailService.getBaseMapper().selectList(
        new LambdaQueryWrapper<OutOrderPlanDetail>().eq(OutOrderPlanDetail::getOrderPlanId, orderPlanId)
      );

      groupStorage.forEach(item -> {
        BaseStorage baseStorage = baseStorageService.getById(item.getStorageId());
        Assert.isFalse(ObjectUtil.isEmpty(baseStorage), "仓库不存在！");
        BaseConsignor baseConsignor = baseConsignorService.getById(item.getConsignorId());
        Assert.isFalse(ObjectUtil.isEmpty(baseConsignor), "货主不存在！");
        var api_outInToBizMustFeeItem = sysConfigService.getConfigBool("api_outInToBizMustFeeItem");

        OutOrder outOrder = new OutOrder();
        // 对象数据的拷贝
        BeanUtil.copyProperties(item, outOrder);
        outOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));

        outOrder.setOrderType(OutOrderTypeEnum.OUT_PLAN_TO_ORDER.getName());
        outOrder.setStoreOrderCode(outOrderPlanVo.getOrderPlanCode());
        outOrder.setStorageName(item.getStorageName());
        outOrder.setSortingStatus(SortingStatusEnum.NONE.getId());

        outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());
        outOrder.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
        outOrder.setTelephone(outOrderPlanVo.getTel());
        outOrder.setPostCode(outOrderPlanVo.getZip());
        outOrder.setSourceId(Convert.toStr(outOrderPlanVo.getOrderPlanId()));
        outOrder.setSourceCode(outOrderPlanVo.getSourceCode());
        outOrder.setSourceType(InOrderTypeEnum.OUT_PLANORDER.getName());
        outOrder.setExpandFields(outOrderPlanVo.getExpandFields());

        outOrder.setConsignorId(outOrderPlanVo.getConsignorId());
        outOrder.setConsignorCode(outOrderPlanVo.getConsignorCode());
        outOrder.setConsignorName(outOrderPlanVo.getConsignorName());
        outOrder.setStorageName(outOrderPlanVo.getStorageName());
        outOrder.setClientId(outOrderPlanVo.getClientId());
        outOrder.setClientCode(outOrderPlanVo.getClientCode());
        outOrder.setClientShortName(outOrderPlanVo.getClientShortName());
        outOrder.setNickName(outOrderPlanVo.getNickName());
        outOrder.setShippingName(outOrderPlanVo.getShippingName());
        outOrder.setTelephone(outOrderPlanVo.getTel());
        outOrder.setMobile(outOrderPlanVo.getMobile());
        outOrder.setShippingAddress(outOrderPlanVo.getShippingAddress());
        outOrder.setPostCode(outOrderPlanVo.getZip());
        outOrder.setCountryName(outOrderPlanVo.getCountryName());
        outOrder.setProvinceName(outOrderPlanVo.getProvinceName());
        outOrder.setProvinceId(outOrderPlanVo.getProvinceId());
        outOrder.setCityId(outOrderPlanVo.getCityId());
        outOrder.setRegionId(outOrderPlanVo.getRegionId());
        outOrder.setCityName(outOrderPlanVo.getCityName());
        outOrder.setRegionName(outOrderPlanVo.getRegionName());
        outOrder.setTotalAmount(outOrderPlanVo.getTotalAmount());
        outOrder.setTotalWeight(outOrderPlanVo.getTotalWeight());
        outOrder.setTotalCube(outOrderPlanVo.getTotalCube());
        outOrder.setStorageId(baseStorage.getStorageId());
        outOrder.setStorageCode(baseStorage.getStorageCode());
        outOrder.setStorageName(baseStorage.getStorageName());
        outOrder.setDeptId(LoginHelper.getDeptId());
        outOrder.setDeptName(LoginHelper.getDeptName());
        outOrder.setDistributionType("城配");
        outOrder.setShippingAddress(outOrderPlanVo.getShippingAddress());

        //#region 主表扩展字段转物理字段
        Map<String, Object> expandFields = outOrder.getExpandFields();
        if (MapUtil.isNotEmpty(expandFields) && expandFields.containsKey("reqDate")) {
          Object reqDate = expandFields.get("reqDate");
          BeanUtil.setFieldValue(outOrder, "applyDate", reqDate);
        }
        //#endregion
        outOrderService.save(outOrder);

        AtomicReference<BigDecimal> totalQuantity = new AtomicReference<>(BigDecimal.ZERO);//合计数量
        AtomicReference<BigDecimal> bigQtyTotal = new AtomicReference<>(BigDecimal.ZERO);//大单位数量
        AtomicReference<BigDecimal> totalWeight = new AtomicReference<>(BigDecimal.ZERO);//合计重量
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);

        Stream<OutOrderPlanDetail> details = outOrderPlanDetail.stream().filter(m -> item.getStorageId().equals(m.getStorageId()));

        details.forEach(detail -> {
          OutOrderDetail detailInfo = new OutOrderDetail();
          BaseProduct baseProduct = baseProductService.getById(detail.getProductId());

          BeanUtil.copyProperties(baseProduct, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);

          if(ObjectUtil.isNotEmpty(baseProduct.getExpandFields())){
            Map<String, Object> mergedExpandFields = new HashMap<>(baseProduct.getExpandFields());
            Map<String, Object> qDetailInfoExpandFields = detail.getExpandFields();
            if (qDetailInfoExpandFields != null) {
              mergedExpandFields.putAll(qDetailInfoExpandFields);
            }
            detailInfo.setExpandFields(mergedExpandFields);
          }
          detailInfo.setOrderId(outOrder.getOrderId());
          detailInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
          detailInfo.setQuantityOrder(detail.getQuantity());
          detailInfo.setWeight(Convert.toBigDecimal(detail.getWeight()));
          detailInfo.setRowWeight(detail.getRowWeight());
          detailInfo.setOriginPlace(detail.getOriginPlace());
          if (outOrderPlanVo.getCreateByName().equals("erp-api")) {
            detailInfo.setSourceDetailId(detail.getSourceDetailId());
          } else {
            detailInfo.setSourceDetailId(Convert.toStr(detail.getOrderPlanDetailId()));
          }
          detailInfo.setPositionName(detail.getPositionName());
          detailInfo.setSingleSignCode(detail.getSingleSignCode());
          detailInfo.setProduceDate(detail.getProduceDate());
          detailInfo.setBatchNumber(detail.getBatchNumber());
          detailInfo.setProductId(detail.getProductId());
          detailInfo.setProductCode(detail.getProductCode());
          detailInfo.setProductName(detail.getProductName());
          detailInfo.setProductModel(detail.getProductModel());
          detailInfo.setSalePrice(detail.getSalePrice());
          detailInfo.setSaleAmount(detail.getSaleAmount());
          detailInfo.setSmallUnit(detail.getSmallUnit());
          detailInfo.setQuantityInvoiced(detail.getQuantity());
          BaseProductType baseProductType = baseProductTypeService.getByName(baseProduct.getTypeName());
          if (ObjectUtil.isNotNull(baseProductType)) {
            detailInfo.setTypeName(baseProductType.getTypeName());
            detailInfo.setTypeId(baseProductType.getTypeId());
          }
          //#region 明细扩展字段转物理字段
          Map<String, Object> detailExpandFields = detail.getExpandFields();
          if (MapUtil.isNotEmpty(detailExpandFields) && detailExpandFields.containsKey("planCw")) {
            Object planCwValue = detailExpandFields.get("planCw");
            BeanUtil.setFieldValue(detailInfo, "positionName", planCwValue);
          }
          if (MapUtil.isNotEmpty(detailExpandFields) && detailExpandFields.containsKey("batchNumber")) {
            Object batchNumberValue = detailExpandFields.get("batchNumber");
            BeanUtil.setFieldValue(detailInfo, "batchNumber", batchNumberValue);
          }
          if (MapUtil.isNotEmpty(detailExpandFields) && detailExpandFields.containsKey("caseNumber")) {
            Object caseNumberValue = detailExpandFields.get("caseNumber");
            BeanUtil.setFieldValue(detailInfo, "caseNumber", caseNumberValue);
          }
          //#endregion

          totalQuantity.set(B.add(totalQuantity.get(), detail.getQuantity()));
          bigQtyTotal.set(B.add(bigQtyTotal.get(), detail.getBigQty()));
          totalWeight.set(B.add(totalWeight.get(), detail.getRowWeight()));
          totalAmount.set(B.add(totalAmount.get(), detail.getSaleAmount()));


          outOrderDetailService.save(detailInfo);

          // 新增一条规则  废弃 用新的 匹配规则
//          OutSortingRule outSortingRule = new OutSortingRule();
//          outSortingRule.setOrderId(outOrder.getOrderId());
//          outSortingRule.setOrderCode(outOrder.getOrderCode());
//          outSortingRule.setOrderDetailId(detailInfo.getOrderDetailId());
//          outSortingRule.setProductId(detailInfo.getProductId());
//          outSortingRule.setProductCode(detailInfo.getProductCode());
//          if (ObjectUtil.isNotNull(detailInfo.getPositionName())) {
//            outSortingRule.setPositionName(detailInfo.getPositionName());
//          }
//          if (ObjectUtil.isNotNull(detailInfo.getBatchNumber())) {
//            outSortingRule.setBatchNumber(detailInfo.getBatchNumber());
//          }
//          if (ObjectUtil.isNotNull(detailInfo.getProduceDate())) {
//            outSortingRule.setProduceDate(detailInfo.getProduceDate());
//          }
//          outSortingRuleService.save(outSortingRule);

          //添加分配规则
          CoreSortingRule coreSortingRule = new CoreSortingRule();
          coreSortingRule.setBillCode(outOrder.getOrderCode());
          coreSortingRule.setBillId(outOrder.getOrderId());
          coreSortingRule.setBillDetailId(detailInfo.getOrderDetailId());
          coreSortingRule.setBillType("出库单");
          coreSortingRule.setProductCode(detailInfo.getProductCode());
          coreSortingRule.setProductId(detailInfo.getProductId());
          coreSortingRule.setProductName(detailInfo.getProductName());
          String str = "";
          if (ObjectUtil.isNotNull(detailInfo.getPositionName()) && B.isEqual(outOrder.getOrderType(), "材料/备件")) {
            str = StringUtils.format("""
                 {
                   "id": "{}",
                   "conjunction": "and",
                   "children": [
                     {
                       "id": "{}",
                       "op": "equal",
                       "left": {
                         "type": "field",
                         "field": "positionNameQty"
                       },
                       "right": [
                         {
                           "quantity": {},
                           "positionName": "{}"
                         }
                       ]
                     }
                   ]
                 }
              """, generateRandomString(), generateRandomString(), detailInfo.getQuantityOrder(), detailInfo.getPositionName());
          } else {
            str = StringUtils.format("""
                 {
                   "id": "{}",
                   "conjunction": "and",
                   "children": [
                     {
                       "id": "{}",
                       "op": "equal",
                       "left": {
                          "type": "field",
                          "field": "batchNumberQty"
                       },
                       "right": [
                         {
                           "quantity": {},
                           "batchNumber": ""
                         }
                       ]
                     }
                   ]
                 }
              """, generateRandomString(), generateRandomString(), detailInfo.getQuantityOrder());
          }

          coreSortingRule.setSortingRule(JsonUtils.parseObj(str));
          coreSortingRuleService.save(coreSortingRule);
        });


        //修改数据
        LambdaUpdateWrapper<OutOrder> outOrderWrapper1 = new UpdateWrapper<OutOrder>().lambda();
        outOrderWrapper1.set(OutOrder::getTotalQuantityOrder, totalQuantity.get())
          .set(OutOrder::getTotalWeight, totalWeight.get())
          .set(OutOrder::getBigQtyTotal, bigQtyTotal.get())
          .set(OutOrder::getTotalAmount, totalAmount.get())
          .eq(OutOrder::getOrderId, outOrder.getOrderId());
        outOrderService.update(outOrderWrapper1);//提交


//        outOrder.setOrderStatus(null);
        // 生成新建出库单的轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_OUT_PLAN, OutOrderStatusEnum.AUDIT_WAITING);

      });
      LambdaUpdateWrapper<OutOrderPlan> outOrderPlanWrapper = new UpdateWrapper<OutOrderPlan>().lambda();
      outOrderPlanWrapper.set(OutOrderPlan::getPlanStatus, OutOrderPlanStatusEnum.OVER_TO_OUTORDER.getName())
        .eq(OutOrderPlan::getOrderPlanId, orderPlanId);
      this.update(outOrderPlanWrapper);

      // 查询最近新生成的第一条入库单，就是计划单轨迹需要的备注单号
      LambdaQueryWrapper<OutOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.last("limit 1");
      lambdaQueryWrapper.orderByDesc(OutOrder::getOrderId);
      var order = outOrderService.getOne(lambdaQueryWrapper);

      //添加轨迹信息
      outOrderPlanStatusHistoryService.addHistoryInfo(outOrderPlan, OutOrderPlanActionEnum.TO_ORDER.getName(), outOrderPlan.getPlanStatus(), OutOrderPlanStatusEnum.OVER_TO_OUTORDER.getName(), loginUser, order.getOrderCode());
    }

    return R.ok("转入到出库单成功！");
  }

  //#region 出库计划导入数据
  @Async
  @Override
  public void importData(InputStream inputStream, Long importId, HttpServletRequest request, LoginUser loginUser) {
    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);

    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到
    LoginHelper.setLoginUser(loginUser);
    String key = request.getParameter("key");
    sysImportService.setKey(key);
    DateTime startDate = DateUtil.date(); // 导入开始时间

    try {
      Long storageId = Convert.toLong(request.getParameter("storageId"));
      Long consignorId = Convert.toLong(request.getParameter("consignorId"));
      // 是否开启唯一码
      sysImportService.isAssert(StringUtils.isEmpty(key), "上传key不存在");
      sysImportService.isAssert(ObjectUtil.isEmpty(importId), "未关联模板，不可导入");

      // 处理解析结果
      ExcelResult<Map<String, Object>> excelResult = null;
      excelResult = ExcelUtil.importExcel(inputStream, new ImportCommonListener());
      var dataList = excelResult.getList();

      // 通用验证
      sysImportService.setError(false);
      R<Void> result = sysImportService.commonCheck(dataList, importId, request, loginUser);
      sysImportService.isAssert(!result.isResult(), "导入数据有错误，请处理好重新导入");

      sysImportService.writeMsg("开始导入...");
      int i = 0;
      int successCount = 0;
      for (var row : dataList) {
        i++;
        BigDecimal quantity = Convert.toBigDecimal(row.get("quantity"));
        BaseProduct prodInfo;
        String productCode = Convert.toStr(row.get("productCode"));
        String productModel = Convert.toStr(row.get("productModel"));

        prodInfo = baseProductService.getByCode(productCode);
        sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "${}商品条码[{}]不存在，核对商品后在导入", i, productModel);

        sysImportService.isAssert(!NumberUtils.isPositiveInteger(quantity), "{}、产品编号[{}]数量请输入正整数！", i, productCode);
      }
      sysImportService.isAssert(sysImportService.isError(), "导入数据有错误，请处理好重新导入");

      // 对单据进行分组
      var groupList = sysImportService.getGroupList(dataList, importId);
      // 循环处理分组数据，groupList对应的是主表数据
      i = 0;
      for (var item : groupList) {
        i++;
        sysImportService.writeMsg("正在导入第{}行", i);
        String productCode = Convert.toStr(item.get("productCode"));
        String consignorName = Convert.toStr(item.get("consignorName"));

        // 获得明细数据
        var detailList = sysImportService.getGroupDetails(dataList, item, importId);


        var orderInfo = new OutOrderPlan();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1669, loginUser.getTenantId());
        orderInfo.setOrderPlanCode(orderCode);
//        orderInfo.setPlanType(InOrderPlanTypeEnum.ORDINARY_ORDER.getName());
        orderInfo.setPlanStatus(OutOrderPlanStatusEnum.NEWED.getName());
        orderInfo.setAuditing(Convert.toLong(AuditEnum.AUDIT.getId()));
        orderInfo.setCreateBy(loginUser.getUserId());
        orderInfo.setCreateByName(loginUser.getNickname());
        orderInfo.setUserId(loginUser.getUserId());
        orderInfo.setNickName(loginUser.getNickname());
        // 保存主表
        this.getBaseMapper().insert(orderInfo);

        //添加轨迹信息
        outOrderPlanStatusHistoryService.addHistoryInfo(orderInfo, OutOrderPlanActionEnum.IMPORT.getName(), null, OutOrderPlanStatusEnum.NEWED.getName(), loginUser, null);

        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<OutOrderPlanDetail> orderPlanDetailList = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new OutOrderPlanDetail();
          // 根据同货主同条码查找商品信息
          var baseProduct = this.baseProductService.getByCodeAndConsignor(Convert.toStr(detail.get("productCode")), Convert.toStr(detail.get("consignorName")));
          if (ObjectUtil.isEmpty(baseProduct)) {
            // 同货主没找到，在根据商品条码查询
            baseProduct = this.baseProductService.getByCode(Convert.toStr(detail.get("productCode")));
          }
          // 获得明细表扩展字段
          BeanUtil.copyProperties(baseProduct, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);


          // 单位毛重weight，小计毛重totalWeight，相互计算
          BigDecimal quantity = Convert.toBigDecimal(detail.get("quantity")); // 数量
          BigDecimal salePrice = Convert.toBigDecimal(detail.get("salePrice")); // 成本单价
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight")); // 单位重量
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight")); // 小计重量
          BigDecimal rowWeightTon = Convert.toBigDecimal(detail.get("rowWeightTon"));  // 小计重量吨
          BigDecimal bigQty = Convert.toBigDecimal(detail.get("bigQty")); // 大单位数量
          BigDecimal paiQty = Convert.toBigDecimal(detail.get("paiQty")); // 拍数
          BigDecimal unitPackage = Convert.toBigDecimal(detail.get("unitPackage")); // 单位包数
          BigDecimal unitCube = Convert.toBigDecimal(detail.get("unitCube")); // 单位体积
          BigDecimal rowCube = Convert.toBigDecimal(detail.get("rowCube")); // 小计体积


          if (ObjectUtil.isEmpty(salePrice)) {
            salePrice = baseProduct.getSalePrice();
          }
          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
//            detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
            detailInfo.setWeight(Convert.toBigDecimal(baseProduct.getWeight())); // 单位重量
            detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getQuantity())); // 合计重量
            // 如果没有商品重量并且有模板总重量吨
            if (ObjectUtil.isEmpty(baseProduct.getWeight()) && ObjectUtil.isNotEmpty(rowWeightTon)) {
              detailInfo.setRowWeight(B.div(detail.get("rowWeightTon"), 1000)); // 合计重量
              detailInfo.setWeight(B.div(detailInfo.getRowWeight(), quantity)); // 单位重量
            }
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getQuantity()));
            detailInfo.setRowWeight(rowWeight);
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getQuantity()));
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重和小计毛重都不为空时，直接赋值
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(rowWeight);
          }
          //#endregion

          //#region 体积计算
          if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积和小计体积都为空时，默认商品信息单位体积
            detailInfo.setUnitCube(baseProduct.getWeight()); // 单位体积
            detailInfo.setRowCube(B.mul(baseProduct.getWeight(), detailInfo.getQuantity())); // 合计体积
          } else if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积为空和小计体积不为空时，单位体积通过小计体积反算
            detailInfo.setUnitCube(B.div(rowCube, detailInfo.getQuantity()));
            detailInfo.setRowCube(rowCube);
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积不为空和小计体积为空时，小计体积通过单位体积计算
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(B.mul(unitCube, detailInfo.getQuantity()));
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积和小计体积都不为空时，直接赋值
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(rowCube);
          }
          //#endregion

          // 大单位数量 = 数量 / 大单位换算
          // 数量 = 大单位数 * 换算		// 数量必填
          if (ObjectUtil.isEmpty(bigQty)) {
            detailInfo.setBigQty(B.div(quantity, baseProduct.getUnitConvert()));
          }
          if (ObjectUtil.isEmpty(quantity)) {
            detailInfo.setQuantity(B.mul(baseProduct.getUnitConvert(), bigQty));
          }
          detailInfo.setSaleAmount(B.mul(salePrice, quantity));

          //建立关系，设置主表ID
          detailInfo.setOrderPlanId(orderInfo.getOrderPlanId());
          outOrderPlanDetailService.save(detailInfo);
          orderPlanDetailList.add(detailInfo);
        }
        // 主表求和字段计算
        var totalQuantityOrder = orderPlanDetailList.stream().map(OutOrderPlanDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalQuantity(totalQuantityOrder);
        var totalWeight = orderPlanDetailList.stream().map(OutOrderPlanDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalPurchaseAmount = orderPlanDetailList.stream().map(OutOrderPlanDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalAmount(totalPurchaseAmount);
        this.getBaseMapper().updateById(orderInfo);

        //添加轨迹信息
        outOrderPlanStatusHistoryService.addHistoryInfo(orderInfo, OutOrderPlanActionEnum.IMPORT.getName(), orderInfo.getPlanStatus(), OutOrderPlanStatusEnum.NEWED.getName(), loginUser, null);

        successCount++;
      }

      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);
      sysImportService.writeMsgBlue("导入完成，新增{}条", successCount);
      sysImportService.writeMsg("导入成功,共耗时{}秒", totalSeconds);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception exception) {
      sysImportService.writeMsgRed("导入错误：{}", exception);
      transactionManager.rollback(transaction); // 手动回滚事务
    }
    sysImportService.writeEnd(); // 标记结算
  }
  //#endregion

  //#region 复制编辑页面数据

  /**
   * 复制编辑页面数据
   *
   * @param saveEditorBo 复制信息bo
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> copyEditor(SaveEditorBo<OutOrderPlanBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }
    LoginUser loginUser = LoginHelper.getLoginUser();

    OutOrderPlan outOrderPlan = this.getById(saveEditorBo.getIdValue());
    List<OutOrderPlanDetail> details = outOrderPlanDetailService.selectListByMainId(saveEditorBo.getIdValue());

    String orderCode = DBUtils.getCodeRegular(Objects.requireNonNull(MenuEnum.getEnumById(saveEditorBo.getMenuId())));
    // 保存主表
    outOrderPlan.setOrderPlanId(null);
    outOrderPlan.setOrderPlanCode(orderCode);
    outOrderPlan.setPlanStatus(OutOrderStatusEnum.NEWED.getName());
    outOrderPlan.setPlanType(null);
    outOrderPlan.setTotalQuantity(BigDecimal.ZERO);
    outOrderPlan.setTotalAmount(BigDecimal.ZERO);
    outOrderPlan.setRemark(null);
    outOrderPlan.setAuditing(Convert.toLong(AuditEnum.AUDIT.getId()));
    this.save(outOrderPlan);

    // 保存明细
    details.forEach(item -> {
      item.setOrderPlanId(outOrderPlan.getOrderPlanId());
      item.setOrderPlanDetailId(null);
      item.setQuantity(BigDecimal.ZERO);
    });
    outOrderPlanDetailService.saveBatch(details);


//    // 生成复制出库单的轨迹
//    inOrderPlan.setPlanStatus(null);
//    outOrderStatusHistoryService.AddHistory(inOrderPlan, OutOperationTypeEnum.COPY, OutOrderStatusEnum.AUDIT_WAITING);

    //添加轨迹信息
    outOrderPlanStatusHistoryService.addHistoryInfo(outOrderPlan, OutOrderPlanActionEnum.COPY.getName(), null, OutOrderPlanStatusEnum.NEWED.getName(), loginUser, null);

    return R.ok("复制成功");
  }
  //#endregion

  /**
   * 终止
   *
   * @param map 前台传入参数
   * @return 无返回
   */
  @Override
  public R<Void> stop(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    LoginUser loginUser = LoginHelper.getLoginUser();
    for (long orderPlanId : _ids) {
      OutOrderPlan outOrderInfo = this.baseMapper.selectById(orderPlanId);
      com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(ObjectUtil.isEmpty(outOrderInfo), "单据不存在！");

      //新建；通过审核；
      List<String> statusList = Arrays.asList(OutOrderPlanStatusEnum.NEWED.getName(), OutOrderPlanStatusEnum.SUCCESS.getName());
      if (!statusList.contains(outOrderInfo.getPlanStatus())) {
        throw new ServiceException("只有状态为【新建, 审核成功】才允许终止操作！");
      }


      // 更新出库计划单
      LambdaUpdateWrapper<OutOrderPlan> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper.set(OutOrderPlan::getPlanStatus, OutOrderPlanStatusEnum.STOP.getName())
        .set(OutOrderPlan::getAuditing, BigDecimal.ZERO)
        .eq(OutOrderPlan::getOrderPlanId, orderPlanId);
      this.update(orderLambdaUpdateWrapper);


      //添加轨迹信息
      outOrderPlanStatusHistoryService.addHistoryInfo(outOrderInfo, OutOrderPlanActionEnum.STOP.getName(), outOrderInfo.getPlanStatus(), OutOrderPlanStatusEnum.STOP.getName(), loginUser, null);
    }
    return R.ok("终止成功");
  }
  //#endregion

  //#region 开启
  @Override
  public R<Void> open(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
    LoginUser loginUser = LoginHelper.getLoginUser();

    for (Long id : ids) {
      OutOrderPlan outOrderPlan = this.getById(id);
      Assert.isTrue(StrUtil.equals(InOrderStatusEnum.STOP.getName(), outOrderPlan.getPlanStatus()), "只有终止的单据才允许开启");

      //修改调拨申请单状态
      LambdaUpdateWrapper<OutOrderPlan> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(OutOrderPlan::getAuditing, null)
        .set(OutOrderPlan::getAuditDate, null)
        .set(OutOrderPlan::getAuditor, null)
        .set(OutOrderPlan::getPlanStatus, InOrderStatusEnum.NEWED.getName())
        .eq(OutOrderPlan::getOrderPlanId, id);

      this.update(wrapper);


      //添加轨迹信息
      outOrderPlanStatusHistoryService.addHistoryInfo(outOrderPlan, OutOrderPlanActionEnum.OPEN.getName(), outOrderPlan.getPlanStatus(), OutOrderPlanStatusEnum.NEWED.getName(), loginUser, null);
    }
    return R.ok("开启成功");
  }
  //#endregion


  //#region  保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<OutOrderPlanBo> saveEditorBo, EditorVo<OutOrderPlanVo> editor) {

    Boolean isAdd = saveEditorBo.isAdd();
    LoginUser loginUser = LoginHelper.getLoginUser();

    if (isAdd) {
      com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(ObjectUtil.isNull(editor.getMaster().getOrderPlanId()), "计划单ID不能为空");
      OutOrderPlan orderPlan = this.baseMapper.selectById(editor.getMaster().getOrderPlanId());

      //添加轨迹信息
      outOrderPlanStatusHistoryService.addHistoryInfo(orderPlan, OutOrderPlanActionEnum.NEWED.getName(), null, OutOrderPlanStatusEnum.NEWED.getName(), loginUser, null);
    }

  }
  //#endregion

  /**
   * 生成12 位随机数
   *
   * @return 随机数结果
   */
  private String generateRandomString() {
    // 定义包含所有可能字符的字符串
    String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
    // 创建一个Random对象
    Random random = new Random();
    // 创建一个StringBuilder来构建最终的字符串
    StringBuilder builder = new StringBuilder();

    // 循环12次，生成12位随机字符
    for (int i = 0; i < 12; i++) {
      // 从characters中随机选择一个字符
      int index = random.nextInt(characters.length());
      char randomChar = characters.charAt(index);
      // 将随机字符添加到StringBuilder中
      builder.append(randomChar);
    }

    // 返回生成的随机字符串
    return builder.toString();
  }
}
