package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProductTypeService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.*;
import com.yiruantong.common.core.enums.out.OutOrderPlanActionEnum;
import com.yiruantong.common.core.enums.out.OutOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanBo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanVo;
import com.yiruantong.inbound.mapper.in.InOrderPlanMapper;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 收货计划单Service业务层处理
 *
 * @author YiRuanTong
 * &#064;date  2023-10-14
 */
@RequiredArgsConstructor
@Service
public class InOrderPlanServiceImpl extends ServiceImplPlus<InOrderPlanMapper, InOrderPlan, InOrderPlanVo, InOrderPlanBo> implements IInOrderPlanService {
  public final IInOrderPlanDetailService inOrderPlanDetailService;
  public final ISysConfigService sysConfigService;
  public final IBaseProductService baseProductService;
  public final IInOrderService inOrderService;
  public final IInOrderDetailService inOrderDetailService;
  public final IBaseProviderService baseProviderService;
  public final IInOrderStatusHistoryService inOrderStatusHistoryService;
  public final IInOrderPlanStatusHistoryService inOrderPlanStatusHistoryService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IBaseProductTypeService baseProductTypeService;


  //#region 入库计划批量审核

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
      LoginUser loginUser = LoginHelper.getLoginUser();
      for (long orderId : ids) {
        InOrderPlan inOrderPlan = this.baseMapper.selectById(orderId);
        if (ObjectUtil.isEmpty(inOrderPlan)) {
          throw new ServiceException("未获取到入库计划单");
        }
        if (!InOrderPlanStatusEnum.NEWED.getName().equals(inOrderPlan.getPlanStatus()) && !InOrderPlanStatusEnum.PENDING.getName().equals(inOrderPlan.getPlanStatus())) {
          throw new ServiceException("只有新建或者待审核的单据才可以进行审核");
        }
        // 修改数据
        LambdaUpdateWrapper<InOrderPlan> lambda = new UpdateWrapper<InOrderPlan>().lambda();
        lambda.set(InOrderPlan::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
          .set(InOrderPlan::getAuditor, LoginHelper.getNickname())
          .set(InOrderPlan::getAuditDate, DateUtils.getNowDate())
          .set(InOrderPlan::getPlanStatus, InOrderPlanStatusEnum.SUCCESS.getName())
          .eq(InOrderPlan::getPlanId, orderId);
        this.update(lambda);//提交


        //添加轨迹信息
        inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.AUDITING.getName(), inOrderPlan.getPlanStatus(), InOrderPlanStatusEnum.SUCCESS.getName(), loginUser, null);
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

  //#region 入库计划转到预到货单

  /**
   * 入库计划转到预到货单
   *
   * @param ids@return
   */
  public R<Void> toInOrder(List<Long> ids) {
    // 是否开启唯一码
    var in_autoSingleSignCode = sysConfigService.getConfigBool("in_autoSingleSignCode");
    LoginUser loginUser = LoginHelper.getLoginUser();

    for (Long planId : ids) {
      InOrderPlanVo inOrderPlanVo = this.baseMapper.selectVoById(planId);
      InOrderPlan inOrderPlan = this.baseMapper.selectById(planId);
      if (ObjectUtil.isEmpty(inOrderPlanVo)) {
        throw new ServiceException("未获取到入库计划单");
      }
      if (inOrderPlanVo.getPlanStatus().equals(InOrderPlanStatusEnum.OVER_TO_INORDER.getName())) {
        throw new ServiceException("单据已转到预到货单，不允许重复操作！");
      }
      Assert.isTrue(StrUtil.equals(inOrderPlanVo.getPlanStatus(), InOrderPlanStatusEnum.SUCCESS.getName()), "单据未审核不允许转出库单！");
      // 明细数据查询
      LambdaQueryWrapper<InOrderPlanDetail> planLma = new LambdaQueryWrapper<>();
      planLma.eq(InOrderPlanDetail::getPlanId, planId);
      List<InOrderPlanDetail> inOrderPlanDetails = inOrderPlanDetailService.list(planLma);

      // 仓库+货主分组
//        Map<Long, Map<Long, List<InOrderPlanDetail>>> groupList = inOrderPlanDetails.stream()
//          .collect(Collectors.groupingBy(InOrderPlanDetail::getStorageId, Collectors.groupingBy(InOrderPlanDetail::getProviderId)));
      Map<Long, Map<Long, List<InOrderPlanDetail>>> groupList = inOrderPlanDetails.stream()
        .collect(Collectors.groupingBy(InOrderPlanDetail::getStorageId, Collectors.groupingBy(InOrderPlanDetail::getConsignorId)));

      for (var mapStorageItem : groupList.entrySet()) {
        Long storageId = mapStorageItem.getKey(); // 分组仓库ID
        for (var mapProviderItem : mapStorageItem.getValue().entrySet()) {
//            Long providerId = mapProviderItem.getKey(); // 分组供应商id
          Long consignorId = mapProviderItem.getKey(); // 分组货主id
          List<InOrderPlanDetail> orderPlanDetails = mapProviderItem.getValue(); // 分组后的明细数据集合

          // 插入主表
          InOrder inOrder = new InOrder();
          inOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001));
          if (ObjectUtil.isNotEmpty(inOrderPlanVo.getPlanType())) {
            inOrder.setOrderType(inOrderPlanVo.getPlanType());
          } else {
            inOrder.setOrderType(InOrderTypeEnum.ORDER_PLAN.getName());
          }


          // 对象数据的拷贝
          BeanUtil.copyProperties(inOrderPlanVo, inOrder);
          inOrder.setApplyDate(new Date());
          inOrder.setTotalQuantity(inOrderPlanVo.getTotalQuantityOrder());
          inOrder.setContainerNos(inOrderPlanVo.getContainerNo());
          inOrder.setNickName(LoginHelper.getNickname());
          inOrder.setUserId(LoginHelper.getUserId());
          inOrder.setDeptId(LoginHelper.getDeptId());
          inOrder.setDeptName(LoginHelper.getDeptName());
          inOrder.setTotalWeight(inOrderPlanVo.getTotalWeight());
          inOrder.setTrackingNumber(inOrderPlanVo.getPlanCode());
          inOrder.setRemark(inOrderPlanVo.getRemark());
          inOrder.setShelveStatus(InShelveStatusEnum.WAITING.getName());
          inOrder.setTotalQuantity(inOrderPlanVo.getTotalQuantityOrder()); // 合计数量
          inOrder.setTotalAmount(inOrderPlanVo.getTotalPurchaseAmount()); // 不含税金额


          var baseProvider = baseProviderService.getById(inOrderPlanVo.getProviderId());
          if (ObjectUtil.isNotEmpty(baseProvider)) {
            inOrder.setProviderName(baseProvider.getProviderName());
          }

          inOrder.setOrderStatus(InOrderStatusEnum.SUCCESS.getName());
          inOrder.setAuditing(AuditEnum.AUDITED_SUCCESS.getId()); // 待审核

          inOrder.setSourceId(inOrderPlanVo.getPlanId().toString());
          inOrder.setSourceCode(inOrderPlanVo.getSourceCode());
          inOrder.setSourceType(InOrderActionEnum.PLAN_TO_ORDER.getName());
          inOrder.setExpandFields(inOrderPlanVo.getExpandFields());
          inOrder.setExpressCorpId(inOrderPlanVo.getExpressCorpId());
          inOrder.setExpressCode(inOrderPlanVo.getExpressCode());
          inOrder.setExpressCorpName(inOrderPlanVo.getConsignorName());
          inOrder.setCheckingStatus(InOrderStatusEnum.UN_ALREADY_CHECKING.getName()); // 质检状态-未质检

          Map<String, Object> expandFields = inOrderPlanVo.getExpandFields();

          //#region 扩展字段转实体字段
          if (MapUtil.isNotEmpty(expandFields) && expandFields.containsKey("crossDocking")) {
            Object crossDockingValue = expandFields.get("crossDocking");
            BeanUtil.setFieldValue(inOrder, "crossDocking", crossDockingValue);
          }
          if (MapUtil.isNotEmpty(expandFields) && expandFields.containsKey("deliveryManTel")) {
            Object deliveryManTel = expandFields.get("deliveryManTel");
            BeanUtil.setFieldValue(inOrder, "mobile", deliveryManTel);
          }
          //#endregion
          inOrder.setExpressFee(Convert.toBigDecimal(orderPlanDetails.size())); // 车数
          inOrder.setIsChecking(EnableEnum.ENABLE.getId());
          inOrderService.save(inOrder);

          BigDecimal totalRateAmount = BigDecimal.ZERO;
          // 明细循环
          var inOrderDetails = orderPlanDetails.stream().map(qDetailInfo -> {
            InOrderDetail inOrderDetail = new InOrderDetail();

            // findOne用法:查询商品信息
            LambdaQueryWrapper<BaseProduct> arrivalWrapper = new LambdaQueryWrapper<>();
            arrivalWrapper.eq(BaseProduct::getProductId, qDetailInfo.getProductId());
            BaseProduct baseProduct = baseProductService.getOne(arrivalWrapper);

            BeanUtil.copyProperties(baseProduct, inOrderDetail);
            BeanUtil.copyProperties(qDetailInfo, inOrderDetail);
            Map<String, Object> mergedExpandFields = new HashMap<>();
            Map<String, Object> qDetailInfoExpandFields = qDetailInfo.getExpandFields();
            if (baseProduct.getExpandFields() != null) {
              mergedExpandFields.putAll(baseProduct.getExpandFields());
            }
            if (qDetailInfoExpandFields != null) {
              mergedExpandFields.putAll(qDetailInfoExpandFields);
            }
            Object tareWeight = mergedExpandFields.get("bigTareWeight");
            mergedExpandFields.put("tareWeight", tareWeight);// 每包扣重 默认大包装皮重
            inOrderDetail.setExpandFields(mergedExpandFields);
            inOrderDetail.setSmallUnit(baseProduct.getSmallUnit());
            inOrderDetail.setOrderId(inOrder.getOrderId());
            inOrderDetail.setQuantity(qDetailInfo.getQuantityOrder());
            inOrder.setStorageId(qDetailInfo.getStorageId());
            inOrder.setStorageCode(qDetailInfo.getStorageCode());
            inOrder.setStorageName(qDetailInfo.getStorageName());
            inOrder.setConsignorId(qDetailInfo.getConsignorId());
            inOrder.setConsignorCode(qDetailInfo.getConsignorCode());
            inOrder.setConsignorName(qDetailInfo.getConsignorName());

            inOrderService.saveOrUpdate(inOrder);


            BigDecimal purchaseAmount = BigDecimal.ZERO;
            purchaseAmount = B.mul(qDetailInfo.getPurchasePrice(), qDetailInfo.getQuantityOrder()); // 小计金额
            inOrderDetail.setPurchaseAmount(purchaseAmount);
            if (ObjectUtil.isNotEmpty(inOrderPlanVo.getPlanType()) && inOrderPlanVo.getPlanType().equals(InSourceTypeEnum.IN_ZCC.getName())) {
              inOrderDetail.setPurchaseAmount(qDetailInfo.getRowSaleAmount());
            }
            if (in_autoSingleSignCode) {
              // 开启唯一码
              String uuid = IdUtil.simpleUUID();
              inOrderDetail.setSingleSignCode(uuid);
            }
            inOrderDetail.setRate(baseProduct.getRate());
            if (inOrderPlanVo.getCreateByName().equals("erp-api")) {
              inOrderDetail.setSourceDetailId(qDetailInfo.getSourceDetailId());
            } else {
              inOrderDetail.setSourceDetailId(Convert.toStr(qDetailInfo.getPlanDetailId()));
            }
            inOrderDetail.setSourceMainId(inOrderPlanVo.getPlanId().toString()); // 来源主表id
            inOrderDetail.setCaseNumber(qDetailInfo.getCaseNumber());
            BigDecimal thousand = new BigDecimal(1000);

            BigDecimal rate = BigDecimal.ZERO;


            BigDecimal ratePrice = B.mul(qDetailInfo.getPurchasePrice(), inOrderDetail.getRate());
            inOrderDetail.setRatePrice(B.add(ratePrice, qDetailInfo.getPurchasePrice())); // 税价（含税金额）
            inOrderDetail.setRateAmount(B.mul(inOrderDetail.getRatePrice(), qDetailInfo.getQuantityOrder())); // 含税金额

            BigDecimal rowWeight = B.mul(inOrderDetail.getWeight(), inOrderDetail.getQuantity());
            inOrderDetail.setRowWeight(rowWeight); // 小计重量
            inOrderDetail.setRowWeightTon(B.div(rowWeight, thousand)); // 小计重量（吨）
            inOrderDetail.setRowCube(B.mul(inOrderDetail.getQuantity(), inOrderDetail.getUnitCube())); // 小计体积
            inOrderDetail.setRowNetWeight(rowWeight); // 小计重量

            inOrderDetail.setShelfLifeDay(baseProduct.getShelfLifeDay());
            inOrderDetail.setBigUnit(baseProduct.getBigUnit()); // 大单位
            inOrderDetail.setBigQty(B.ceiling(B.div(inOrderDetail.getQuantity(), baseProduct.getUnitConvert()))); // 大单位数量
            inOrderDetail.setProviderId(inOrder.getProviderId());
            inOrderDetail.setProviderCode(inOrder.getProviderCode());
            inOrderDetail.setProviderShortName(inOrder.getProviderShortName());
            inOrderDetail.setOvercharges(qDetailInfo.getUnitConvert()); // 允差
            inOrderDetail.setRowWeight(qDetailInfo.getRowWeight());
            inOrderDetail.setRowNetWeight(qDetailInfo.getRowNetWeight());
            inOrderDetail.setPurchaseAmount(qDetailInfo.getRowPurchaseAmount());

            BaseProductType baseProductType = baseProductTypeService.getByName(baseProduct.getTypeName());
            if (ObjectUtil.isNotNull(baseProductType)) {
              inOrderDetail.setTypeName(baseProductType.getTypeName());
              inOrderDetail.setTypeId(baseProductType.getTypeId());
            }

            Map<String, Object> detailExpandFields = qDetailInfo.getExpandFields();

            // 是否质检
            if (MapUtil.isNotEmpty(detailExpandFields) && detailExpandFields.containsKey("isChecking")) {
              Object isChecking = detailExpandFields.get("isChecking");
              BeanUtil.setFieldValue(inOrderDetail, "isChecking", isChecking);
            }
            // 皮重
            if (MapUtil.isNotEmpty(detailExpandFields) && detailExpandFields.containsKey("tareWgt")) {
              Object tareWgt = detailExpandFields.get("tareWgt");
              BeanUtil.setFieldValue(inOrderDetail, "rowTareWeight", tareWgt);
            }
            // 货位
            if (MapUtil.isNotEmpty(detailExpandFields) && detailExpandFields.containsKey("positionName")) {
              Object positionName = detailExpandFields.get("positionName");
              BeanUtil.setFieldValue(inOrderDetail, "positionName", positionName);
            }
            inOrderDetail.setIsChecking(EnableEnum.ENABLE.getId());
            return inOrderDetail;
          }).toList();
          // 批量保存明细
          inOrderDetailService.saveBatch(inOrderDetails);
          // 重新查询预到货明细
          List<InOrderDetail> orderDetailList = inOrderDetailService.selectListByMainId(inOrder.getOrderId());


          totalRateAmount = orderDetailList.stream().map(InOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
          BigDecimal totalQuantity = orderDetailList.stream().map(InOrderDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
          BigDecimal totalNetWeight = orderDetailList.stream().filter(item -> ObjectUtil.isNotNull(item.getRowNetWeight())).map(InOrderDetail::getRowNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

          // 更新预到货单：合计价税
          LambdaUpdateWrapper<InOrder> lambda2 = new UpdateWrapper<InOrder>().lambda();
          lambda2.set(InOrder::getTotalRateAmount, totalRateAmount)
            .set(InOrder::getTotalQuantity, totalQuantity)
            .set(InOrder::getTotalNetWeight, totalNetWeight)
            .eq(InOrder::getOrderId, inOrder.getOrderId());
          inOrderService.update(lambda2);

          //状态不同 添加的轨迹也不同
          inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.PLAN_TO_ORDER, InOrderStatusEnum.NEWED);
        }
      }
      // 修改入库计划单状态
      LambdaUpdateWrapper<InOrderPlan> lambda3 = new UpdateWrapper<InOrderPlan>().lambda();
      lambda3.set(InOrderPlan::getPlanStatus, InOrderPlanStatusEnum.OVER_TO_INORDER.getName()) // 已转预到货单
        .eq(InOrderPlan::getPlanId, planId);
      this.update(lambda3);

      // 修改入库计划单状态
//      LambdaQueryWrapper<InOrder> inOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
//      inOrderLambdaQueryWrapper.eq(InOrder::getSourceId, Convert.toStr(planId));
//      var inOrder = inOrderService.getOne(inOrderLambdaQueryWrapper);
//      if (ObjectUtil.isNotNull(inOrder)) {
//
//        //添加轨迹信息
//        inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.TO_ORDER.getName(), inOrderPlan.getPlanStatus(), InOrderPlanStatusEnum.OVER_TO_INORDER.getName(), loginUser, inOrder.getOrderCode());
//      }
    }
    return R.ok();
  }


  //#endregion

  //#region 入库计划导入数据
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
        BigDecimal quantityOrder = Convert.toBigDecimal(row.get("quantityOrder"));
        BaseProduct prodInfo;
        String productCode = Convert.toStr(row.get("productCode"));
        String productModel = Convert.toStr(row.get("productModel"));

        prodInfo = baseProductService.getByCode(productCode);
        sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "${}商品条码[{}]不存在，核对商品后在导入", i, productModel);

        sysImportService.isAssert(!NumberUtils.isPositiveInteger(quantityOrder), "{}、产品编号[{}]数量请输入正整数！", i, productCode);
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


        var orderInfo = new InOrderPlan();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1003, loginUser.getTenantId());
        orderInfo.setPlanCode(orderCode);
//        orderInfo.setPlanType(InOrderPlanTypeEnum.ORDINARY_ORDER.getName());
        orderInfo.setPlanStatus(InOrderPlanStatusEnum.NEWED.getName());
        orderInfo.setAuditing(Convert.toLong(AuditEnum.AUDIT.getId()));
        // 供应商为空，默认第一个供应商
        if (StringUtils.isEmpty(orderInfo.getProviderShortName())) {
          var providerInfo = baseProviderService.getDefaultOne();
          if (ObjectUtil.isNotNull(providerInfo)) {
            orderInfo.setProviderId(providerInfo.getProviderId());
            orderInfo.setProviderCode(providerInfo.getProviderCode());
            orderInfo.setProviderShortName(providerInfo.getProviderShortName());
          }
        }

        // 保存主表
        this.getBaseMapper().insert(orderInfo);

        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<InOrderPlanDetail> orderPlanDetailList = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new InOrderPlanDetail();
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
          BigDecimal quantityOrder = Convert.toBigDecimal(detail.get("quantityOrder")); // 数量
          BigDecimal purchasePrice = Convert.toBigDecimal(detail.get("purchasePrice")); // 成本单价
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight")); // 单位重量
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight")); // 小计重量
          BigDecimal rowWeightTon = Convert.toBigDecimal(detail.get("rowWeightTon"));  // 小计重量吨
          BigDecimal bigQty = Convert.toBigDecimal(detail.get("bigQty")); // 大单位数量
          BigDecimal paiQty = Convert.toBigDecimal(detail.get("paiQty")); // 拍数
          BigDecimal unitPackage = Convert.toBigDecimal(detail.get("unitPackage")); // 单位包数
          BigDecimal unitCube = Convert.toBigDecimal(detail.get("unitCube")); // 单位体积
          BigDecimal rowCube = Convert.toBigDecimal(detail.get("rowCube")); // 小计体积


          if (ObjectUtil.isEmpty(purchasePrice)) {
            purchasePrice = baseProduct.getPurchasePrice();
          }
          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
            detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
            detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getQuantityOrder())); // 合计重量
            // 如果没有商品重量并且有模板总重量吨
            if (ObjectUtil.isEmpty(baseProduct.getWeight()) && ObjectUtil.isNotEmpty(rowWeightTon)) {
              detailInfo.setRowWeight(B.div(detail.get("rowWeightTon"), 1000)); // 合计重量
              detailInfo.setWeight(B.div(detailInfo.getRowWeight(), quantityOrder)); // 单位重量
            }
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getQuantityOrder()));
            detailInfo.setRowWeight(rowWeight);
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getQuantityOrder()));
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
            detailInfo.setRowCube(B.mul(baseProduct.getWeight(), detailInfo.getQuantityOrder())); // 合计体积
          } else if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积为空和小计体积不为空时，单位体积通过小计体积反算
            detailInfo.setUnitCube(B.div(rowCube, detailInfo.getQuantityOrder()));
            detailInfo.setRowCube(rowCube);
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积不为空和小计体积为空时，小计体积通过单位体积计算
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(B.mul(unitCube, detailInfo.getQuantityOrder()));
          } else if (ObjectUtil.isNotEmpty(unitCube) && ObjectUtil.isNotEmpty(rowCube)) {
            // 单位体积和小计体积都不为空时，直接赋值
            detailInfo.setUnitCube(unitCube);
            detailInfo.setRowCube(rowCube);
          }
          //#endregion

          // 大单位数量 = 数量 / 大单位换算
          // 数量 = 大单位数 * 换算		// 数量必填
          if (ObjectUtil.isEmpty(bigQty)) {
            detailInfo.setBigQty(B.div(quantityOrder, baseProduct.getUnitConvert()));
          }
          if (ObjectUtil.isEmpty(quantityOrder)) {
            detailInfo.setQuantityOrder(B.mul(baseProduct.getUnitConvert(), bigQty));
          }

          if (ObjectUtil.isEmpty(paiQty)) {
            // 计算拍数
            if (ObjectUtil.isNotEmpty(unitPackage)) {
              detailInfo.setUnitPackage(Convert.toStr(unitPackage));
              detailInfo.setPlateCount(Convert.toStr(B.div(quantityOrder, unitPackage)));
            } else {
              detailInfo.setUnitPackage(Convert.toStr(BigDecimal.ZERO));
              detailInfo.setPlateCount(Convert.toStr(BigDecimal.ZERO));
            }
          }
          detailInfo.setRowPurchaseAmount(B.mul(purchasePrice, quantityOrder));

          //建立关系，设置主表ID
          detailInfo.setPlanId(orderInfo.getPlanId());
          inOrderPlanDetailService.save(detailInfo);
          orderPlanDetailList.add(detailInfo);
        }
        // 主表求和字段计算
        var totalQuantityOrder = orderPlanDetailList.stream().map(InOrderPlanDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalQuantityOrder(totalQuantityOrder);
        var totalWeight = orderPlanDetailList.stream().map(InOrderPlanDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalPurchaseAmount = orderPlanDetailList.stream().map(InOrderPlanDetail::getRowPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalPurchaseAmount(totalPurchaseAmount);
        this.getBaseMapper().updateById(orderInfo);


        //添加轨迹信息
        inOrderPlanStatusHistoryService.addHistoryInfo(orderInfo, InOrderPlanActionEnum.IMPORT.getName(), null, InOrderPlanStatusEnum.NEWED.getName(), loginUser, null);

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
  public R<Map<String, Object>> copyEditor(SaveEditorBo<InOrderPlanBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }
    LoginUser loginUser = LoginHelper.getLoginUser();

    InOrderPlan inOrderPlan = this.getById(saveEditorBo.getIdValue());
    List<InOrderPlanDetail> details = inOrderPlanDetailService.selectListByMainId(saveEditorBo.getIdValue());

    String orderCode = DBUtils.getCodeRegular(Objects.requireNonNull(MenuEnum.getEnumById(saveEditorBo.getMenuId())));
    // 保存主表
    inOrderPlan.setPlanId(null);
    inOrderPlan.setPlanCode(orderCode);
    inOrderPlan.setPlanStatus(OutOrderStatusEnum.NEWED.getName());
    inOrderPlan.setAuditing(null);
    inOrderPlan.setAuditDate(null);
    inOrderPlan.setAuditing(0L);
    this.save(inOrderPlan);
    // 保存明细
    details.forEach(item -> {
      item.setPlanId(inOrderPlan.getPlanId());
      item.setPlanDetailId(null);
    });
    inOrderPlanDetailService.saveBatch(details);

    //添加轨迹信息
    inOrderPlan.setPlanStatus(null);
    inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.COPY.getName(), null, InOrderPlanStatusEnum.NEWED.getName(), loginUser, null);

    return R.ok("复制成功");
  }
  //#endregion


  //#region 开启
  @Override
  public R<Void> open(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
    LoginUser loginUser = LoginHelper.getLoginUser();

    for (Long id : ids) {
      InOrderPlan inOrderPlan = this.getById(id);
      Assert.isTrue(StrUtil.equals(InOrderStatusEnum.STOP.getName(), inOrderPlan.getPlanStatus()), "只有终止的单据才允许开启");

      //修改调拨申请单状态
      LambdaUpdateWrapper<InOrderPlan> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(InOrderPlan::getAuditing, null)
        .set(InOrderPlan::getAuditDate, null)
        .set(InOrderPlan::getAuditor, null)
        .set(InOrderPlan::getPlanStatus, InOrderStatusEnum.NEWED.getName())
        .eq(InOrderPlan::getPlanId, id);

      this.update(wrapper);
      //添加轨迹信息
      inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.OPEN.getName(), inOrderPlan.getPlanStatus(), InOrderPlanStatusEnum.NEWED.getName(), loginUser, null);
    }
    return R.ok("开启成功");
  }
  //#endregion

  //#region 终止
  @Override
  public R<Void> stop(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
    LoginUser loginUser = LoginHelper.getLoginUser();

    for (Long id : ids) {
      InOrderPlan inOrderPlan = this.getById(id);
      Assert.isTrue(StrUtil.equals(InOrderStatusEnum.SUCCESS.getName(), inOrderPlan.getPlanStatus()), "只有终止的单据才允许开启");
      //修改调拨申请单状态
      LambdaUpdateWrapper<InOrderPlan> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(InOrderPlan::getPlanStatus, InOrderStatusEnum.STOP.getName())
        .eq(InOrderPlan::getPlanId, id);
      this.update(wrapper);
      //添加轨迹信息
      inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.STOP.getName(), inOrderPlan.getPlanStatus(), InOrderPlanStatusEnum.STOP.getName(), loginUser, null);
    }
    return R.ok("终止成功");
  }
  //#endregion

  //#region  保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<InOrderPlanBo> saveEditorBo, EditorVo<InOrderPlanVo> editor) {

    Boolean isAdd = saveEditorBo.isAdd();
    LoginUser loginUser = LoginHelper.getLoginUser();

    if (isAdd) {
      com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(ObjectUtil.isNull(editor.getMaster().getPlanId()), "计划单ID不能为空");
      InOrderPlan orderPlan = this.baseMapper.selectById(editor.getMaster().getPlanId());

      //添加轨迹信息
      inOrderPlanStatusHistoryService.addHistoryInfo(orderPlan, OutOrderPlanActionEnum.NEWED.getName(), null, OutOrderPlanStatusEnum.NEWED.getName(), loginUser, null);
    }

  }
  //#endregion

  @Override
  public InOrderPlan getBySourceCode(String sourceCode) {
    LambdaQueryWrapper<InOrderPlan> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrderPlan::getSourceCode, sourceCode);
    return this.getOnly(queryWrapper);
  }
}
