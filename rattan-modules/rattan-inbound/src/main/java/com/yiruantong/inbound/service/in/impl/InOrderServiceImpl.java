package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductSecurityService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProductTypeService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.*;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.RoleAuthDataEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.mybatis.core.domain.bo.EditorBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveDataDetailBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.DoStepDto;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.InQualityCheck;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;
import com.yiruantong.inbound.domain.in.bo.InOrderBo;
import com.yiruantong.inbound.domain.in.vo.InOrderVo;
import com.yiruantong.inbound.domain.in.vo.InQuantityCheckComposeVo;
import com.yiruantong.inbound.mapper.in.InOrderMapper;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import com.yiruantong.inbound.service.in.IInQualityCheckService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import com.yiruantong.system.service.task.ITaskQueueService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
 * 预到货单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
@RequiredArgsConstructor
@Service
public class InOrderServiceImpl extends ServiceImplPlus<InOrderMapper, InOrder, InOrderVo, InOrderBo> implements IInOrderService {
  private final DataSourceTransactionManager transactionManager;
  private final ISysConfigService sysConfigService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IInOrderDetailService inOrderDetailService;
  private final ISysImportService sysImportService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final ITaskQueueService taskQueueService;
  private final IDataAuthService dataAuthService;
  private final IBaseProductTypeService baseProductTypeService;
  private final IBaseProductSecurityService baseProductSecurityService;


  //#region 列表加载前事件
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    pageQuery.getQueryBoList().stream().filter(f -> f.getQueryType() == QueryTypeEnum.CUSTOM).forEach(f -> {

      if (f.getColumn().equals("itemDescr")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT order_id FROM in_order_detail d WHERE d.order_id=in_order.order_id AND JSON_EXTRACT(d.expand_fields, '$.itemDescr') like'%{}%'", f.getValues()));
      } else {
        String column = StringUtils.toUnderScoreCase(f.getColumn());
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT order_id FROM in_order_detail d WHERE d.order_id=in_order.order_id AND {} like'%{}%'", column, f.getValues()));
      }
    });

    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!dataAuthService.isAllAuth(RoleAuthDataEnum.TYPE) && !loginUser.isAdministrator()) {
      List<Long> idList = dataAuthService.getTypeId(); // 获取类别 权限
      //如果不为空且 还有0则不走类别权限，因为 0是根目录
      if (!idList.isEmpty() && B.isGreater(idList.stream().filter(B::isEqual).toList().size())) return;

      if (idList.isEmpty()) idList.add(0L);
      idList = baseProductTypeService.findLeafNodesByParentIds(idList);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.EXISTS);
      queryBo.setColumn("team_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      String format = StringUtils.format("SELECT * FROM in_order_detail d WHERE d.order_id=in_order.order_id and type_id in ({})", CollUtil.join(idList, ","));
      queryBo.setValues(format);
      pageQuery.addQueryBo(queryBo);


      queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.NOTEXISTS);
      queryBo.setColumn("aaa");
      queryBo.setDataType(DataTypeEnum.LONG);
      format = StringUtils.format("SELECT * FROM in_order_detail d WHERE d.order_id=in_order.order_id and type_id not in ({})", CollUtil.join(idList, ","));

      queryBo.setValues(format);
      pageQuery.addQueryBo(queryBo);
    }

    super.beforePageQuery(pageQuery);
  }
  //#endregion

  //#region 根据单号获取预到货单
  @Override
  public InOrder getByCode(String orderCode) {
    LambdaQueryWrapper<InOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(InOrder::getOrderCode, orderCode);

    return this.getOne(orderLambdaQueryWrapper);
  }
  //#endregion

  //#region 批量审核

  /**
   * 批量审核
   *
   * @param ids 审核参数
   */
  @Override
  public R<Void> multiAuditing(List<Long> ids) {
    try {
      for (Long orderId : ids) {
        InOrder inOrder = this.getById(orderId);
        if (ObjectUtil.isEmpty(inOrder)) {
          throw new ServiceException("未获取到预到货单");
        }

        if (!inOrder.getOrderStatus().equals(InOrderStatusEnum.PENDING.getName()) && !inOrder.getOrderStatus().equals(InOrderStatusEnum.NEWED.getName())) {
          throw new ServiceException("只有新建或者待审核的单据才可以进行审核");
        }

        if (Objects.equals(inOrder.getAuditing(), AuditEnum.AUDITED_SUCCESS.getId())) {
          throw new ServiceException("只有新建或者待审核的单据才可以进行审核");
        }
        if (ObjectUtil.isEmpty(inOrder.getProviderId())) {
          throw new ServiceException(inOrder.getOrderCode() + "供应商不能为空");
        }

        boolean api_outInMustFeeItem = sysConfigService.getConfigBool("api_outInMustFeeItem"); // 出入库计划转移必须设置一次性费用项
        if (api_outInMustFeeItem && ObjectUtil.isEmpty(inOrder.getFeeItemIds())) {
          throw new ServiceException(inOrder.getOrderCode() + "必须设置一次性费用项");
        }

        //修改数据
        LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
        lambda.set(InOrder::getAuditing, 2L)
          .set(InOrder::getAuditDate, DateUtils.getNowDate())
          .set(InOrder::getAuditor, LoginHelper.getNickname())
          .set(InOrder::getOrderStatus, InOrderStatusEnum.SUCCESS.getName())
          .eq(InOrder::getOrderId, orderId);
        this.update(lambda);//提交

        //添加轨迹信息
        inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.AUDITING, InOrderStatusEnum.NEWED, InOrderStatusEnum.SUCCESS);

      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

  //#region 确认为在途中

  /**
   * 确认为在途中
   *
   * @param ids 参数
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> onInTransit(List<Long> ids) {
    try {
      List<InOrder> orders = this.listByIds(ids);
      var data = orders.stream().filter(item -> !B.isEqual(item.getOrderStatus(), InOrderStatusEnum.SUCCESS.getName())).toList();
      if (B.isGreater(data.size())) {
        throw new ServiceException("只有审核成功才允许操作");
      }

      for (InOrder orderInfo : data) {
        //修改数据
        this.updateStatus(orderInfo.getOrderId(), InOrderStatusEnum.IN_TRANSIT);
        //添加轨迹信息
        inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.OPERATION, InOrderStatusEnum.SUCCESS, InOrderStatusEnum.IN_TRANSIT);
      }
      return R.ok("在途成功");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

  //#region 强制完成

  /**
   * 强制完成
   *
   * @param ids 审核参数
   */
  public R<Void> forceFinish(List<Long> ids) {
    try {

      List<InOrder> orders = this.listByIds(ids);
      var data = orders.stream().filter(item -> !B.isEqual(item.getOrderStatus(), InOrderStatusEnum.PARTIAL_FINISHED.getName())).toList();
      if (B.isGreater(data.size())) {
        throw new ServiceException("只有部分交货的单据才可以强制完成");
      }
      for (InOrder orderInfo : orders) {

        //如果订单是调拨单传入 并且来源类型是【常规调拨入库	】 则需要在执行其他业务逻辑信息
        if (StrUtil.equals(orderInfo.getSourceType(), StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_IN.getName())) {
          //调用RabbitMQ
          RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
          rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_ALLOCATE_APPLY); //类别
          rabbitReceiverDto.setBillId(orderInfo.getOrderId());
          rabbitReceiverDto.setBillCode(orderInfo.getOrderCode());
          rabbitReceiverDto.setSourceCode(orderInfo.getSourceCode());
          rabbitReceiverDto.setSourceId(orderInfo.getSourceId());
          taskQueueService.createTask(rabbitReceiverDto);
          //#endregion
        }
        //修改数据
        this.updateStatus(orderInfo.getOrderId(), InOrderStatusEnum.FORCE_FINISHED);


        //添加轨迹信息
        inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.OPERATION, InOrderStatusEnum.PARTIAL_FINISHED, InOrderStatusEnum.FORCE_FINISHED);
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

  //#region 更新预到货状态

  /**
   * 更新预到货状态
   *
   * @param orderId 主表Id
   */
  @Override
  public void updateStatus(Long orderId, InOrderStatusEnum status) {
    LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
    lambda.set(InOrder::getOrderStatus, status.getName())
      .eq(InOrder::getOrderId, orderId);
    this.update(lambda);
  }

  /**
   * 更新审核流状态
   *
   * @param rabbitReceiverDto 消息队列数据
   * @param toStatus          目标状态
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String updateWorkflowStatus(RabbitReceiverDto rabbitReceiverDto, String toStatus) {
    Long billId = rabbitReceiverDto.getBillId();
    String action = rabbitReceiverDto.getAction().getName();
    String remark = rabbitReceiverDto.getRemark();

    InOrder inOrder = this.getById(billId);

    // 增加轨迹
    inOrderStatusHistoryService.addHistoryInfo(inOrder, action, inOrder.getOrderStatus(), toStatus, remark);
    inOrder.setOrderStatus(toStatus); // 记录更新后的状态

    // 如果最后一个节点自动转为审核成功，同时自动增加一个规矩数据
    var otherField = rabbitReceiverDto.getOtherField();
    if (ObjectUtil.isNull(otherField)) otherField = new HashMap<>();
    Long orderNum = 10L; // 排序号，用于置顶

    // 将下一节点审批人、下一节点任务名，记录下来
    Map<String, Object> expandFields = inOrder.getExpandFields();
    if (ObjectUtil.isNull(expandFields)) {
      expandFields = new HashMap<>();
    }
    expandFields.putAll(otherField);

    // 更新单据状态
    LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
    lambda.set(InOrder::getOrderStatus, toStatus)
      .set(InOrder::getOrderNum, orderNum)
      // 更新扩展字段
      .set(InOrder::getExpandFields, expandFields, "typeHandler=com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler")
      .eq(InOrder::getOrderId, billId);
    this.update(lambda);

    return toStatus;
  }
  //#endregion

  //#region 更新上架状态

  /**
   * 更新上架状态
   *
   * @param orderId 主表Id
   */
  @Override
  public void updateShelverStatus(Long orderId, InShelveStatusEnum status) {
    LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
    lambda.set(InOrder::getShelveStatus, status.getName())
      .eq(InOrder::getOrderId, orderId);
    this.update(lambda);
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
  public R<Map<String, Object>> copyEditor(SaveEditorBo<InOrderBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    InOrder inOrder = this.getById(saveEditorBo.getIdValue());
    List<InOrderDetail> details = inOrderDetailService.selectListByMainId(saveEditorBo.getIdValue());

    String orderCode = DBUtils.getCodeRegular(Objects.requireNonNull(MenuEnum.getEnumById(saveEditorBo.getMenuId())));
    // 保存主表
    inOrder.setOrderId(null);
    inOrder.setOrderCode(orderCode);
    inOrder.setOrderStatus(InOrderStatusEnum.NEWED.getName());
    inOrder.setShelveStatus(InShelveStatusEnum.WAITING.getName());
    inOrder.setAuditor(null);
    inOrder.setAuditing(AuditEnum.AUDIT.getId());
    inOrder.setApplyDate(null);
    inOrder.setTotalShelvedQuantity(BigDecimal.ZERO);
    inOrder.setTotalEnterQuantity(BigDecimal.ZERO);
    inOrder.setSourceType("复制生成");
    this.save(inOrder);

    // 保存明细
    details.forEach(item -> {
      item.setOrderId(inOrder.getOrderId());
      item.setOrderDetailId(null);
      item.setEnterQuantity(BigDecimal.ZERO);
      item.setShelvedQuantity(BigDecimal.ZERO);
    });
    inOrderDetailService.saveBatch(details);
//添加轨迹信息
    inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.COPY, InOrderStatusEnum.NEWED);


    return R.ok("复制成功");
  }
  //#endregion

  //#region 预到货导入数据
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
      boolean in_autoSingleSignCode = sysConfigService.getConfigBool("in_autoSingleSignCode");
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
        BigDecimal quantityOrder = Convert.toBigDecimal(row.get("quantity"));
        BaseProduct prodInfo;
        String productCode = Convert.toStr(row.get("productCode"));
        String productModel = Convert.toStr(row.get("productModel"));

        prodInfo = baseProductService.getByCode(productCode);
        sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "${}商品条码[{}]不存在，核对商品后在导入", i, productModel);

        // 预到货导入验证商品绑定货主
        var in_purchaseImportConsignorCheck = sysConfigService.getConfigBool("in_purchaseImportConsignorCheck");
        if (in_purchaseImportConsignorCheck) {
          if (ObjectUtil.isNotEmpty(prodInfo)) {
            LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
            productLambdaQueryWrapper.eq(BaseProduct::getProductCode, productCode)
              .eq(BaseProduct::getConsignorId, consignorId);
            prodInfo = baseProductService.getOne(productLambdaQueryWrapper);
          }
          sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "{}商品[{}]与所选货主不一致，核对商品和货主一致后在导入", i, productCode);
          // 小单位校验
          sysImportService.isAssert(!B.isEqual(row.get("smallUnit"), prodInfo.getSmallUnit()),
            "{}、商品编号[{}]导入小单位[{}]和商品信息中的小单位[{}]不一致", i, productCode, row.get("smallUnit"), prodInfo.getSmallUnit());

          // 大单位校验
          sysImportService.isAssert(!B.isEqual(row.get("bigUnit"), prodInfo.getBigUnit()),
            "{}、商品编号[${row.productCode}}]导入大单位[${row.bigUnit}]和商品信息中的大单位[${prodInfo.bigUnit}]不一致", i, productCode, row.get("bigUnit"), prodInfo.getBigUnit());
        }
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
        String trackingNumber = Convert.toStr(item.get("trackingNumber"));
        String productCode = Convert.toStr(item.get("productCode"));
        String productModel = Convert.toStr(item.get("productModel"));
        String consignorName = Convert.toStr(item.get("consignorName"));

        // 获得明细数据
        var detailList = sysImportService.getGroupDetails(dataList, item, importId);
        // 判断是否存在多个集装箱号
        var containerGroupList = detailList.stream().filter(StreamUtils.distinctByKey(b -> b.get("containerNo"))).toList();
        sysImportService.isAssert(containerGroupList.size() > 1, "{}、一条单据只能有一个集装箱号，目前存在{}个集装箱号", i, containerGroupList.size());

        // 验证预到货单不重复
        if (StringUtils.isNotEmpty(trackingNumber)) {
          LambdaQueryWrapper<InOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
          orderLambdaQueryWrapper.eq(InOrder::getTrackingNumber, trackingNumber);
          if (this.exists(orderLambdaQueryWrapper)) {
            sysImportService.writeMsgRed("{}、来源单号{}已存在，不允许重复导入，已跳过", i, trackingNumber);
            continue;
          }
        }

        var orderInfo = new InOrder();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1001, loginUser.getTenantId());
        orderInfo.setOrderCode(orderCode);
        orderInfo.setOrderType(InOrderTypeEnum.NORMAL.getName());
        orderInfo.setReturnStatus(InOrderReturnStatusEnum.NONE.getName());
        orderInfo.setOrderStatus(InOrderStatusEnum.NEWED.getName());
        orderInfo.setShelveStatus(InShelveStatusEnum.WAITING.getName());
        orderInfo.setAuditing(AuditEnum.AUDIT.getId());
        // 供应商为空，默认第一个供应商
        if (StringUtils.isEmpty(orderInfo.getProviderShortName())) {
          var providerInfo = baseProviderService.getDefaultOne();
          if (ObjectUtil.isNotNull(providerInfo)) {
            orderInfo.setProviderId(providerInfo.getProviderId());
            orderInfo.setProviderCode(providerInfo.getProviderCode());
            orderInfo.setProviderShortName(providerInfo.getProviderShortName());
          }
        }
        orderInfo.setContainerNos(Convert.toStr(item.get("containerNo")));

        // 预到货导入验证商品绑定货主
        var in_purchaseImportConsignorCheck = sysConfigService.getConfigBool("in_purchaseImportConsignorCheck");
        if (in_purchaseImportConsignorCheck && ObjectUtil.isNotEmpty(productModel)) {
          // 验证商品条码是否存在
          var prodList = baseProductService.selectByModel(productModel);
          BaseProduct prodInfo = null;
          if (prodList.size() > 1) {
            prodInfo = baseProductService.getByCodeAndConsignor(productCode, consignorName);
          } else if (prodList.size() == 1) {
            prodInfo = prodList.get(0);
          }

          sysImportService.isAssert(ObjectUtil.isNull(prodInfo), "商品条码[{}]与所选货主不一致，不允许导入", productModel);

          // 更新Excel数据集合中的数据
          item.put("productId", prodInfo.getProductId());
          item.put("productModel", prodInfo.getProductModel());
          item.put("productName", prodInfo.getProductName());
          item.put("productSpec", prodInfo.getProductSpec());

          /*
           将商品扩展字段处理
           if (prodInfo.expandFields) {
             var expandFields = JSON.parse(prodInfo.expandFields);
             item = Object.assign(item, expandFields);
           }
          */
        } else {
          // 验证商品编号是否存在
          var prodInfo = baseProductService.getByCode(Convert.toStr(item.get("productCode")));
          sysImportService.isAssert(ObjectUtil.isEmpty(prodInfo), "商品编号[{}]与所选货主不一致，不允许导入", productCode);
        }
        orderInfo.setUserId(loginUser.getUserId());
        orderInfo.setNickName(loginUser.getNickname());
        orderInfo.setDeptId(loginUser.getDeptId());
        orderInfo.setDeptName(loginUser.getDeptName());
        orderInfo.setApplyDate(DateUtil.date());
        orderInfo.setCreateBy(loginUser.getUserId());
        orderInfo.setCreateByName(loginUser.getNickname());
        orderInfo.setCreateTime(DateUtils.getNowDate());
        orderInfo.setSourceType("导入生成");

        // 保存主表
        this.getBaseMapper().insert(orderInfo);
        // 添加一条状态轨迹
        inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.IMPORT, InOrderStatusEnum.NEWED, loginUser);
        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<InOrderDetail> orderDetailList = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new InOrderDetail();
          // 根据同货主同条码查找商品信息
          var baseProduct = this.baseProductService.getByCodeAndConsignor(Convert.toStr(detail.get("productCode")), Convert.toStr(detail.get("consignorName")));
          if (ObjectUtil.isEmpty(baseProduct)) {
            // 同货主没找到，在根据商品条码查询
            baseProduct = this.baseProductService.getByCode(Convert.toStr(detail.get("productCode")));
          }
          // 获得明细表扩展字段
          BeanUtil.copyProperties(baseProduct, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);
          detailInfo.setExpandFields(sysImportService.getExpandFields(detail));

          if (in_autoSingleSignCode) {
            // 开启唯一码
            detailInfo.setSingleSignCode(IdUtil.simpleUUID());
          } else {
            detailInfo.setSingleSignCode(Convert.toStr(detail.get("singleSignCode")));
          }

          // 单位毛重weight，小计毛重totalWeight，相互计算
          BigDecimal quantity = Convert.toBigDecimal(detail.get("quantity"));
          BigDecimal purchasePrice = Convert.toBigDecimal(detail.get("purchasePrice"));
          BigDecimal ratePrice = Convert.toBigDecimal(detail.get("ratePrice"));
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight"));
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight"));
          BigDecimal rowWeightTon = Convert.toBigDecimal(detail.get("rowWeightTon"));
          BigDecimal bigQty = Convert.toBigDecimal(detail.get("bigQty")); // 大单位数量
          BigDecimal paiQty = Convert.toBigDecimal(detail.get("paiQty")); // 拍数
          BigDecimal unitPackage = Convert.toBigDecimal(detail.get("unitPackage")); // 单位包数
          BigDecimal unitCube = Convert.toBigDecimal(detail.get("unitCube"));
          BigDecimal rowCube = Convert.toBigDecimal(detail.get("rowCube"));
          BigDecimal netWeight = Convert.toBigDecimal(detail.get("netWeight"));
          BigDecimal thousand = new BigDecimal(1000);

          // 采购价为空时，使用商品采购价
          if (ObjectUtil.isEmpty(purchasePrice)) {
            purchasePrice = baseProduct.getPurchasePrice();
            detailInfo.setPurchasePrice(baseProduct.getPurchasePrice());
          }

          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
            detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
            detailInfo.setRowWeight(B.mul(baseProduct.getWeight(), detailInfo.getQuantity())); // 合计重量
            // 如果没有商品重量并且有模板总重量吨
            if (ObjectUtil.isEmpty(baseProduct.getWeight()) && ObjectUtil.isNotEmpty(rowWeightTon)) {
              detailInfo.setRowWeight(B.mul(detail.get("rowWeightTon"), 1000)); // 合计重量
              detailInfo.setWeight(B.div(detailInfo.getRowWeight(), detailInfo.getQuantity())); // 单位重量
            } else {
              detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand));
            }
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getQuantity()));
            detailInfo.setRowWeight(rowWeight);
            detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计体积吨
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getQuantity()));
            detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计体积吨
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重和小计毛重都不为空时，直接赋值
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(rowWeight);
            detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计体积吨
          }
          detailInfo.setRowNetWeight(B.mul(detailInfo.getNetWeight(), detailInfo.getQuantity())); // 合计净重
          //#endregion

          //#region 体积计算
          if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积和小计体积都为空时，默认商品信息单位体积
            detailInfo.setUnitCube(baseProduct.getUnitCube()); // 单位体积
            detailInfo.setRowCube(B.mul(baseProduct.getUnitCube(), detailInfo.getQuantity())); // 合计体积
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

          if (ObjectUtil.isEmpty(paiQty)) {
            // 计算拍数
            if (ObjectUtil.isNotEmpty(unitPackage)) {
              detailInfo.setUnitPackage(unitPackage);
              detailInfo.setPaiQty(B.div(quantity, unitPackage));
            } else {
              detailInfo.setUnitPackage(BigDecimal.ZERO);
              detailInfo.setPaiQty(BigDecimal.ZERO);
            }
          }

          // 生产日期
          // 判断生产日期是否存在
          if (ObjectUtil.isNotEmpty(detail.get("produceDate"))) {
            Integer days = Convert.toInt(baseProduct.getShelfLifeDay());
            var produceDate = DateUtil.parseDate(Convert.toStr(detail.get("produceDate")));
            var _limitDate = DateUtil.offsetDay(produceDate, NumberUtils.ifNullDefault(days));
            detailInfo.setLimitDate(_limitDate); // 有效日期
          } else {
            detailInfo.setLimitDate(Convert.toDate(detail.get("limitDate"))); // 有效日期
          }
          BigDecimal hundred = new BigDecimal(100);
          BigDecimal rate = BigDecimal.ZERO;

          // 默认使用供应商税率，如果没有，则使用商品信息税率
          var providerInfo = baseProviderService.getByShortName(orderInfo.getProviderShortName());
          if (ObjectUtil.isNotNull(providerInfo.getRate())) {
            rate = B.div(providerInfo.getRate(), hundred); // 税率小数点除100
          } else {
            rate = B.div(baseProduct.getRate(), hundred); // 税率小数点除100
          }
          ratePrice = B.mul(purchasePrice, rate);
          detailInfo.setRatePrice(B.add(ratePrice, purchasePrice)); // 含税单价
          detailInfo.setRateAmount(B.muls(quantity, detailInfo.getRatePrice())); // 含税金额
          detailInfo.setRowPackage(B.div(quantity, unitPackage));
          detailInfo.setPurchaseAmount(B.mul(quantity, purchasePrice)); // 成本金额
          //建立关系，设置主表ID
          detailInfo.setOrderId(orderInfo.getOrderId());
          inOrderDetailService.save(detailInfo);
          orderDetailList.add(detailInfo);
        }
        // 主表求和字段计算
        var totalQuantity = orderDetailList.stream().map(InOrderDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalQuantity(totalQuantity);
        var totalWeight = orderDetailList.stream().map(InOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalWeight(totalWeight);
        var totalAmount = orderDetailList.stream().map(InOrderDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalAmount(totalAmount);
        var totalRateAmount = orderDetailList.stream().map(InOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalRateAmount(totalRateAmount);
        var totalCube = orderDetailList.stream().map(InOrderDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalCube(totalCube);
        this.getBaseMapper().updateById(orderInfo);

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

  //#region open
  @Override
  public R<Void> open(Map<String, Object> map) {
    try {
      InOrder inOrder = this.getById(Convert.toLong(map.get("ids")));

      Assert.isTrue(inOrder.getOrderStatus().equals(InOrderStatusEnum.STOP.getName()), "只有终止的单据才允许开启");

      InOrderStatusEnum inOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus());
      Assert.isFalse(ObjectUtil.isNull(inOrderStatusEnum), "预到货单状态不存在！");
      //添加轨迹信息
      inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.OPEN, inOrderStatusEnum, InOrderStatusEnum.NEWED);
      //修改数据
      //修改数据
      LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
      lambda.set(InOrder::getAuditing, 0L)
        .set(InOrder::getAuditDate, null)
        .set(InOrder::getAuditor, null)
        .set(InOrder::getOrderStatus, InOrderStatusEnum.NEWED.getName())
        .eq(InOrder::getOrderId, inOrder.getOrderId());
      this.update(lambda);//提交
      return R.ok("终止成功！");
    } catch (Exception exception) {

      return R.fail(exception.getMessage());
    }
  }
  //#endregion

  //#region stop
  @Override
  public R<Void> stop(Map<String, Object> map) {
    try {
      InOrder inOrder = this.getById(Convert.toLong(map.get("ids")));

      InOrderStatusEnum inOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus());
      Assert.isFalse(ObjectUtil.isNull(inOrderStatusEnum), "预到货单状态不存在！");
      //添加轨迹信息
      inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.STOP, inOrderStatusEnum, InOrderStatusEnum.STOP);
      //修改数据
      this.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.STOP);

      return R.ok("终止成功！");
    } catch (Exception exception) {

      return R.fail(exception.getMessage());
    }
  }
//#endregion

  //#region 拆分
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> splitOrder(Map<String, Object> map) {
    LambdaQueryWrapper<InOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(InOrder::getOrderId, map.get("order_Id"));
    InOrder inOrder = this.baseMapper.selectOne(orderLambdaQueryWrapper);

    if (ObjectUtil.isNotNull(inOrder.getOrderStatus()) && !inOrder.getOrderStatus().equals(InOrderStatusEnum.NEWED.getName())) {
      throw new ServiceException("只有新建的预到货才允许拆分");
    }
    // 新建预到货单
    var orderInfo = new InOrder();
    BeanUtil.copyProperties(inOrder, orderInfo);
    orderInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001));
    orderInfo.setCreateTime(new Date());
    orderInfo.setOrderId(null);
    // 保存主表
    this.getBaseMapper().insert(orderInfo);


    List<InOrderDetail> valueList = Convert.toList(InOrderDetail.class, map.get("productModelList"));
    for (var detail : valueList) {

      // 查到当前明细数据
      LambdaQueryWrapper<InOrderDetail> inOrderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      inOrderDetailLambdaQueryWrapper.eq(InOrderDetail::getOrderDetailId, detail.getOrderDetailId());
      InOrderDetail inOrderDetail = inOrderDetailService.getOne(inOrderDetailLambdaQueryWrapper);


      if (detail.getQuantity().compareTo(inOrderDetail.getQuantity()) >= 0) {
        // 如果数量大于源单数量，就直接把当条明细数据转到新单下
        LambdaUpdateWrapper<InOrderDetail> updateWrapper = new UpdateWrapper<InOrderDetail>().lambda();
        updateWrapper.set(InOrderDetail::getOrderId, orderInfo.getOrderId())
          .eq(InOrderDetail::getOrderDetailId, detail.getOrderDetailId());
        inOrderDetailService.update(updateWrapper);
      } else {

        // 根据同货主同条码查找商品信息
        var baseProduct = this.baseProductService.getByCode(Convert.toStr(inOrderDetail.getProductCode()));
        // 新增一条明细
        var detailInfo = new InOrderDetail();
        BeanUtil.copyProperties(inOrderDetail, detailInfo);
        detailInfo.setOrderDetailId(null);
        //建立关系，设置主表ID
        detailInfo.setOrderId(orderInfo.getOrderId());
        detailInfo.setQuantity(detail.getQuantity());
        detailInfo.setPurchaseAmount(B.mul(detail.getQuantity(), inOrderDetail.getPurchasePrice()));
        detailInfo.setRatePrice(B.mul(inOrderDetail.getPurchasePrice(), B.add(BigDecimal.ONE, detailInfo.getRate())));
        detailInfo.setRateAmount(B.mul(detail.getQuantity(), detailInfo.getRatePrice()));
        detailInfo.setRowWeight(B.mul(detail.getQuantity(), inOrderDetail.getWeight()));
        detailInfo.setRowWeightTon(B.div(Optional.ofNullable(detailInfo.getRowWeight()), 1000));
        detailInfo.setRowNetWeight(B.mul(detail.getQuantity(), inOrderDetail.getNetWeight()));
        detailInfo.setBigQty(B.div(detail.getQuantity(), baseProduct.getUnitConvert()));

        detailInfo.setRowCube(B.mul(detail.getQuantity(), detailInfo.getUnitCube()));


        inOrderDetailService.save(detailInfo);


        BigDecimal newQuantity = B.sub(inOrderDetail.getQuantity(), detail.getQuantity());
        BigDecimal newQuantityTwo = B.mul(B.sub(inOrderDetail.getQuantity(), detail.getQuantity()), inOrderDetail.getPurchasePrice());
        BigDecimal newQuantityThree = B.mul(newQuantityTwo, inOrderDetail.getRate());
        // 修改源单数据
        LambdaUpdateWrapper<InOrderDetail> updateWrapper = new UpdateWrapper<InOrderDetail>().lambda();
        updateWrapper.set(InOrderDetail::getQuantity, B.sub(inOrderDetail.getQuantity(), detail.getQuantity()))
          .set(InOrderDetail::getPurchaseAmount, B.mul(B.sub(inOrderDetail.getQuantity(), detail.getQuantity()), inOrderDetail.getPurchasePrice()))
          .set(InOrderDetail::getRateAmount, B.mul(newQuantity, detailInfo.getRatePrice()))
          .set(InOrderDetail::getRowWeight, B.mul(B.sub(inOrderDetail.getQuantity(), detail.getQuantity()), inOrderDetail.getWeight()))
          .set(InOrderDetail::getRowWeightTon, B.div(Optional.ofNullable(B.mul(B.sub(inOrderDetail.getQuantity(), detail.getQuantity()), inOrderDetail.getWeight())), 1000))
          .set(InOrderDetail::getRowNetWeight, B.mul(newQuantity, detailInfo.getNetWeight()))
          .set(InOrderDetail::getBigQty, B.div(newQuantity, baseProduct.getUnitConvert()))
          .set(InOrderDetail::getRowCube, B.mul(newQuantity, detailInfo.getUnitCube()))
          .eq(InOrderDetail::getOrderDetailId, detail.getOrderDetailId());

        inOrderDetailService.update(updateWrapper);
      }

      LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper.eq(InOrderDetail::getOrderId, map.get("order_Id"));
      List<InOrderDetail> inOrderDetails = inOrderDetailService.list(detailLambdaQueryWrapper);
      if ((long) inOrderDetails.size() >= 1) {
        //合计
        BigDecimal totalQuantity = inOrderDetails.stream().map(InOrderDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalWeight = inOrderDetails.stream().map(InOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAmount = inOrderDetails.stream().map(InOrderDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal rateAmount = inOrderDetails.stream().map(InOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        //修改数据
        LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
        lambda.set(InOrder::getTotalQuantity, totalQuantity)
          .set(InOrder::getTotalWeight, totalWeight)
          .set(InOrder::getTotalAmount, totalAmount)
          .set(InOrder::getTotalRateAmount, rateAmount)
          .eq(InOrder::getOrderId, map.get("order_Id"));
        this.update(lambda);//修改
      }


      LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper2.eq(InOrderDetail::getOrderId, orderInfo.getOrderId());
      List<InOrderDetail> orderDetailList = inOrderDetailService.list(detailLambdaQueryWrapper2);
      if ((long) orderDetailList.size() >= 1) {
        //合计
        BigDecimal totalQuantity = orderDetailList.stream().map(InOrderDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalWeight = orderDetailList.stream().map(InOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAmount = orderDetailList.stream().map(InOrderDetail::getPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal rateAmount = orderDetailList.stream().map(InOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        //修改数据
        LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
        lambda.set(InOrder::getTotalQuantity, totalQuantity)
          .set(InOrder::getTotalWeight, totalWeight)
          .set(InOrder::getTotalAmount, totalAmount)
          .set(InOrder::getTotalRateAmount, rateAmount)
          .eq(InOrder::getOrderId, orderInfo.getOrderId());
        this.update(lambda);//修改
      }


      //      var printInfo = this.getById((Serializable) map.get("order_Id"));
      //      //      if (ObjectUtil.isNotNull(printInfo)) {
      //      printInfo.setTotalQuantity(BigDecimal.valueOf(1));
      //      this.updateById(printInfo);
      //      //      }


    }


    //添加轨迹信息
    orderInfo.setOrderStatus(null);
    orderInfo.setRemark(inOrder.getOrderCode());
    inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.SPLIT, InOrderStatusEnum.NEWED);

    return null;
  }
  //#endregion

  //#region PDA页面筛选初始化数据

  /**
   * PDA页面筛选初始化数据
   */
  @Override
  public R<Map<String, Object>> pdaWhereInit() {
    Map<String, Object> maps = new HashMap<>();
    // 查询预到货单已经存在的单据类型
    String sql = StringUtils.format("""
      SELECT GROUP_CONCAT( DISTINCT order_type) as orderType FROM in_order where order_type is not null;
       """);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    maps.put("orderType", dataInfo.get("orderType").toString().split(","));

    // 查询预到货单已经存在的经手人
    sql = StringUtils.format("""
      SELECT GROUP_CONCAT( DISTINCT nick_name) as nickName FROM in_order where nick_name is not null;
       """);
    dataInfo = SqlRunner.db().selectOne(sql);
    maps.put("nickName", dataInfo.get("nickName").toString().split(","));

    return R.ok(maps);
  }
  //#endregion

  //#region 获取统计数据
  @Override
  public R<List<Map<String, Object>>> getStatisticData(Map<String, Object> maps) {
    String tenantId = Convert.toStr(maps.get("tenantId"));
    Long userId = Convert.toLong(maps.get("userId"));

    if (StrUtil.isEmpty(tenantId)) {
      tenantId = TenantConstants.DEFAULT_TENANT_ID;
    }

    List<Map<String, Object>> data = new ArrayList<>();
    Map<String, Object> mapData = new HashMap<>();

    //#region 入库统计
    //统计 今日入库数量
    String sql = StringUtils.format("""
      SELECT IFNULL(sum(total_enter_quantity),0) as today_total FROM in_enter where  DATE(create_time) = DATE(NOW()) and  tenant_id ='{}' ;
       """, tenantId);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);

    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "今日入库");
    mapData.put("value", Convert.toLong(dataInfo.get("todayTotal")));
    mapData.put("unit", "件");

    //统计 昨日入库数量
    sql = StringUtils.format("""
      SELECT IFNULL(sum(total_enter_quantity),0) as yesterday_total FROM in_enter where TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);
    mapData.put("title", "昨日入库量");
    mapData.put("number", Convert.toLong(dataInfo.get("yesterdayTotal")));

    data.add(mapData); //添加统计数据

    //统计 今日入库率
    sql = StringUtils.format("""
      SELECT IFNULL(sum(quantity),0) as quantity_total,IFNULL(sum(enter_quantity),0) as enter_total FROM in_order_detail where DATE(create_time) = DATE(NOW())  and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long inOrderTotal = Convert.toLong(dataInfo.get("quantityTotal"));
    Long inEnterTotal = Convert.toLong(dataInfo.get("enterTotal"));

    //计算占比
    BigDecimal percentage = BigDecimal.ZERO;
    if (B.isGreater(inEnterTotal) && B.isGreater(inOrderTotal)) {
      percentage = B.div(inEnterTotal, inOrderTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "入库完成率");
    mapData.put("value", percentage);
    mapData.put("unit", "%");

    //昨日入库率
    sql = StringUtils.format("""
      SELECT IFNULL(sum(quantity),0) as quantity_total,IFNULL(sum(enter_quantity),0) as enter_total FROM in_order_detail where TO_DAYS(NOW()) - TO_DAYS(create_time) = 1  and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);
    inOrderTotal = Convert.toLong(dataInfo.get("quantityTotal"));
    inEnterTotal = Convert.toLong(dataInfo.get("enterTotal"));
    //计算占比
    percentage = BigDecimal.ZERO;
    if (B.isGreater(inEnterTotal) && B.isGreater(inOrderTotal)) {
      percentage = B.div(inEnterTotal, inOrderTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData.put("title", "昨日入库率");
    mapData.put("number", percentage);
    data.add(mapData); //添加统计数据
    //#endregion

    //#region 上架统计
    //统计 今日上架数量
    sql = StringUtils.format("""
      SELECT IFNULL(sum(quantity),0) as today_total,IFNULL(sum(shelved_quantity),0) as today_shelve_total FROM in_shelve_detail where  DATE(create_time) = DATE(NOW()) and  tenant_id ='{}' ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long todayTotal = Convert.toLong(dataInfo.get("todayTotal"));
    Long todayShelveTotal = Convert.toLong(dataInfo.get("todayShelveTotal"));
    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "今日上架");
    mapData.put("value", todayShelveTotal);
    mapData.put("unit", "件");

    //统计 昨日上架数量
    sql = StringUtils.format("""
      SELECT IFNULL(sum(quantity),0) as yesterday_total,IFNULL(sum(shelved_quantity),0) as yesterday_shelve_total FROM in_shelve_detail where TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);

    Long yesterdayTotal = Convert.toLong(dataInfo.get("yesterdayTotal"));
    Long yesterdayShelveTotal = Convert.toLong(dataInfo.get("yesterdayShelveTotal"));
    mapData.put("title", "昨日上架量");
    mapData.put("number", yesterdayShelveTotal);
    data.add(mapData); //添加统计数据

    //统计 今日上架率
    percentage = BigDecimal.ZERO;
    if (B.isGreater(todayShelveTotal) && B.isGreater(todayTotal)) {
      percentage = B.div(todayShelveTotal, todayTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "上架完成率");
    mapData.put("value", percentage);
    mapData.put("unit", "%");

    //昨日上架率
    percentage = BigDecimal.ZERO;
    if (B.isGreater(yesterdayShelveTotal) && B.isGreater(yesterdayTotal)) {
      percentage = B.div(yesterdayShelveTotal, yesterdayTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData.put("title", "昨日上架率");
    mapData.put("number", percentage);
    data.add(mapData); //添加统计数据
    //#endregion

    return R.ok(data);
  }
  //#endregion

  //#region 零库存出库
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> zeroOut(List ids, BigDecimal unqualifiedQty) {
    List<InOrder> orders = this.listByIds(ids);

    for (InOrder inOrder : orders) {
      // 执行后续步骤链
      doRabbitMq(inOrder, unqualifiedQty);
    }

    return R.ok("零库存出库提交成功");
  }


  //#endregion

  //#region 执行后续步骤链 doRabbitMq

  /**
   * 执行后续步骤链
   *
   * @param inOrder 预到货单
   */
  private void doRabbitMq(@NotNull InOrder inOrder, BigDecimal unqualifiedQty) {
    //第一步：预到货单生成出库单
    RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
    rabbitReceiverDto.setMenuId(MenuEnum.MENU_1001.getId().longValue());
//    rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_ORDER_CROSS_DOCKING_IN); // 类别
    rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_CROSS); // 预到货单生成出库单
    rabbitReceiverDto.setBillId(inOrder.getOrderId());
    rabbitReceiverDto.setBillCode(inOrder.getOrderCode());
    rabbitReceiverDto.setRootId(inOrder.getOrderId());
    rabbitReceiverDto.setRootCode(inOrder.getOrderCode());
    Map<String, Object> map = new HashMap<>();
    map.put("unqualifiedQty", unqualifiedQty);
    rabbitReceiverDto.setOtherField(map);

    // 下一步执行步骤
    List<DoStepDto> doStepDtoList = new ArrayList<>();
    // 预到货单生成出库单
//    DoStepDto doStepDto = new DoStepDto();
//    doStepDto.setRabbitmqType(RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_CROSS);
//    doStepDto.setFinished(false);
//    doStepDtoList.add(doStepDto);
    // 出库单自动审核
    DoStepDto doStepDto = new DoStepDto();
    doStepDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_AUTO_AUDITING);
    doStepDto.setFinished(false);
    doStepDtoList.add(doStepDto);
    // 出库单分拣
    doStepDto = new DoStepDto();
    doStepDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_SORTING);
    doStepDto.setFinished(false);
    doStepDtoList.add(doStepDto);
//    // 出库单自动确认出库
//    doStepDto = new DoStepDto();
//    doStepDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_AUTO_OUT);
//    doStepDto.setFinished(false);
//    doStepDtoList.add(doStepDto);
//    // 入库转出库单自动出库完成
//    doStepDto = new DoStepDto();
//    doStepDto.setRabbitmqType(RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_FINISHED);
//    doStepDto.setFinished(false);
//    doStepDtoList.add(doStepDto);

    // 出完库自动生成TMS运单
//    doStepDto = new DoStepDto();
//    doStepDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_TMS_WAY_BILL);
//    doStepDto.setFinished(false);
//    HashMap<String, Object> maps = new HashMap<>();
//    maps.put("notTransport", true);
//    doStepDto.setOtherField(maps);
//    doStepDtoList.add(doStepDto);

    rabbitReceiverDto.setDoStepList(doStepDtoList);

    // 创建消息队列
    taskQueueService.createTask(rabbitReceiverDto);
  }
  //#endregion

  //#region beforeSaveEditor2
  @Override
  public void beforeSaveEditor2(SaveEditorBo<InOrderBo> saveEditorBo, InOrder inOrder) {
    InOrderBo master = saveEditorBo.getData().getMaster();
    List<SaveDataDetailBo> detailList = saveEditorBo.getData().getDetailList();
    if (!detailList.isEmpty()) {
      var detail = detailList.get(0);
//    获取到所有的sn
      List<String> singleSignCode = detail.getRows().stream().map(item -> Convert.toStr(item.get("singleSignCode"))).toList();

      List<String> snList = new ArrayList<>();
      for (String sn : singleSignCode) {
        if (StrUtil.isNotEmpty(sn)) {
          snList.addAll(List.of(StringUtils.split(sn, ",")));
        }
      }
      if (B.isGreater(snList.size())) {
        baseProductSecurityService.addSecurity(master.getOrderId(), master.getOrderCode(), snList);
      }
    }

  }
  //#endregion

  @Override
  public void afterEditor(EditorVo<InOrderVo> editor) {
    if (B.isEqual(editor.getMaster().getOrderType(), "合金/辅料")) {
      //合金辅料页面 编辑页面查询 质检单信息
      MPJLambdaWrapper<InQualityCheck> wrapper = new MPJLambdaWrapper<InQualityCheck>()
        .selectAll(InQualityCheckDetail.class)
        .selectAll(InQualityCheck.class)
        .innerJoin(InQualityCheckDetail.class, InQualityCheckDetail::getQualityCheckId, InQualityCheck::getQualityCheckId)
        .eq(InQualityCheck::getOrderId, editor.getMaster().getOrderId())
        .eq(InQualityCheck::getOrderCode, editor.getMaster().getOrderCode());


      IInQualityCheckService inQualityCheckService = SpringUtils.getBean(IInQualityCheckService.class);

      IPage<InQuantityCheckComposeVo> ipage = new Page<>();
      IPage<InQuantityCheckComposeVo> page = inQualityCheckService.selectJoinListPage(ipage, InQuantityCheckComposeVo.class, wrapper);

      TableDataInfo<InQuantityCheckComposeVo> tableDataInfoV = TableDataInfo.build(page);

      editor.getDetailList().add(tableDataInfoV);
    }

    super.afterEditor(editor);

  }

  //#region  保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<InOrderBo> saveEditorBo, EditorVo<InOrderVo> editor) {

    List<InOrderDetail> inOrderDetails = inOrderDetailService.selectListByMainId(editor.getMaster().getOrderId());
    if (!inOrderDetails.isEmpty()) {
      //合计
      BigDecimal totalQuantity = inOrderDetails.stream().map(InOrderDetail::getQuantity).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalWeight = inOrderDetails.stream().map(InOrderDetail::getRowWeight).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalAmount = inOrderDetails.stream().map(InOrderDetail::getPurchaseAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal rateAmount = inOrderDetails.stream().map(InOrderDetail::getRateAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

      //修改数据
      LambdaUpdateWrapper<InOrder> lambda = new LambdaUpdateWrapper<>();
      lambda.set(InOrder::getTotalQuantity, totalQuantity)
        .set(InOrder::getTotalWeight, totalWeight)
        .set(InOrder::getTotalAmount, totalAmount)
        .set(InOrder::getTotalRateAmount, rateAmount)
        .eq(InOrder::getOrderId, editor.getMaster().getOrderId());

      //返回前段数据设置
      editor.getMaster().setTotalWeight(totalWeight);
      editor.getMaster().setTotalAmount(totalAmount);
      editor.getMaster().setTotalQuantity(totalQuantity);
      editor.getMaster().setTotalRateAmount(rateAmount);
      this.update(lambda);//修改
    }
    boolean isAdd = saveEditorBo.isAdd();
    if (isAdd) {
      //添加轨迹信息
      inOrderStatusHistoryService.addHistoryInfo(BeanUtil.copyProperties(editor.getMaster(), InOrder.class), InOrderActionEnum.NEWED, InOrderStatusEnum.NEWED);
    }

    //#region 拆分托盘号
    int maxIndex = 0;
    // 获取历史最大序号
    for (var row : inOrderDetails) {
      String plateCode = Convert.toStr(row.getPlateCode());
      if (StringUtils.isNotEmpty(plateCode)) {
        String[] plateCodes = StringUtils.split(plateCode, "-");
        if (plateCodes.length == 2) {
          Integer lastIndex = Convert.toInt(plateCodes[1]);
          if (lastIndex > maxIndex) {
            maxIndex = lastIndex;
          }
        }
      }
    }

    // 自动拆分托盘码
    List<InOrderDetail> newRowList = new ArrayList<>();
    for (var row : inOrderDetails) {
      if (StringUtils.isNotEmpty(row.getPlateCode())) continue;

      Long productId = Convert.toLong(row.getProductId());
      BigDecimal quantity = Convert.toBigDecimal(row.getQuantity());

      BaseProduct baseProduct = baseProductService.getById(productId);

      // 新建明细和数量大于商品设置的托盘数量，且商品信息中的托盘数量必须大于0
      if (ObjectUtil.isNotNull(baseProduct) && B.isGreater(baseProduct.getPlateQty())) {
        maxIndex++;
        if (B.isGreater(quantity, baseProduct.getPlateQty())) {
          // 处理自己本身行，改为每托盘数量
          row.setQuantity(baseProduct.getPlateQty());
          row.setPlateCode(editor.getMaster().getOrderCode() + "-" + maxIndex);
          // 需要新增行的可用数量
          quantity = B.sub(quantity, baseProduct.getPlateQty());
          // 托盘数
          int plateCount = B.ceilDiv(quantity, baseProduct.getPlateQty());
          for (int i = 0; i < plateCount; i++) {
            maxIndex++;
            if (B.isLessOrEqual(quantity)) {
              inOrderDetailService.updateById(row);
              break;
            }
            InOrderDetail newRow = BeanUtil.copyProperties(row, InOrderDetail.class);
            newRow.setOrderDetailId(0L);
            if (B.isGreater(quantity, baseProduct.getPlateQty())) {
              newRow.setQuantity(baseProduct.getPlateQty());
            } else {
              newRow.setQuantity(quantity);
            }
            quantity = B.sub(quantity, baseProduct.getPlateQty());
            newRow.setPlateCode(editor.getMaster().getOrderCode() + "-" + maxIndex);
            newRowList.add(newRow);
          }
        } else {
          // 每托盘数量大于当前行数量时
          row.setQuantity(quantity);
          row.setPlateCode(editor.getMaster().getOrderCode() + "-" + maxIndex);
        }
        inOrderDetailService.updateById(row);
      }
    }
    inOrderDetailService.saveBatch(newRowList);
    //#endregion

    // 获取明细数据
    List<TableDataInfo> detailDataList = new ArrayList<>();
    EditorBo editorBo = new EditorBo();
    BeanUtil.copyProperties(saveEditorBo, editorBo);
    this.getDetails(editorBo, detailDataList); // 获取明细
    editor.setDetailList(detailDataList); // 明细集合数据
    editor.setDetailList(detailDataList); // 明细集合数据
  }
  //#endregion

  @Override
  public InOrder getBySourceCode(String sourceCode) {
    LambdaQueryWrapper<InOrder> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrder::getSourceCode, sourceCode);
    return this.getOnly(queryWrapper);
  }

  /**
   * 更新质检状态
   *
   * @param name
   * @param orderId
   */
  @Override
  public void upCheckingStatus(String name, Long orderId) {
    LambdaUpdateWrapper<InOrder> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(InOrder::getCheckingStatus, name)
      .eq(InOrder::getOrderId, orderId);
    this.update(updateWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> compulsoryReceipt(List<Long> ids) {
    List<InOrder> orders = this.listByIds(ids);
    List<String> status = new ArrayList<>();
    status.add(InOrderStatusEnum.SUCCESS.getName());
    status.add(InOrderStatusEnum.PARTIAL_FINISHED.getName());
    var finList = orders.stream().filter(item -> B.isGreater(status.stream().filter(i -> B.isEqual(i, item.getOrderStatus())).toList().size())).toList();
    if (ObjectUtil.isNull(finList) || !B.isEqual(finList.size(), orders.size())) {
      throw new ServiceException("只有【部分交货，审核成功】的的单据才允许 强制入库");
    }
    for (InOrder orderInfo : orders) {
      this.updateStatus(orderInfo.getOrderId(), InOrderStatusEnum.FINISHED);
      InOrderStatusEnum fromStatus = InOrderStatusEnum.matchingEnum(orderInfo.getOrderStatus());
      inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.COMPULSORY_RECEIPT, fromStatus, InOrderStatusEnum.FINISHED);
    }

    return R.ok("强制入库完成");
  }

  //#region 湘钢ERP整单删除数据

  /**
   * 湘钢ERP整单删除数据
   *
   * @param sourceCode
   */
  @Override
  public void apiAllDelete(String sourceCode) {
    LambdaQueryWrapper<InOrder> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrder::getSourceCode, sourceCode);
    List<InOrder> inOrderList = this.list(queryWrapper);
    for (InOrder inOrder : inOrderList) {
      if (ObjectUtil.isNull(inOrder)) {
        throw new ServiceException("未找到对应的数据");
      }
      if (B.isEqual(inOrder.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
        throw new ServiceException("该单据已入库完成，不允许删除");
      }
      if (B.isEqual(inOrder.getOrderStatus(), InOrderStatusEnum.PARTIAL_FINISHED.getName())) {
        throw new ServiceException("该单据已部分入库完成，不允许删除");
      }
      this.deleteById(inOrder.getOrderId());
    }
  }
  //#endregion

  //#region 湘钢ERP部分删除数据

  /**
   * 湘钢ERP部分删除数据
   *
   * @param sourceCode
   * @param caseNumber
   */
  @Override
  public void apiPartDelete(String sourceCode, String caseNumber) {
    LambdaQueryWrapper<InOrder> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrder::getSourceCode, sourceCode);
    List<InOrder> inOrderList = this.list(queryWrapper);
    for (InOrder inOrder : inOrderList) {
      if (ObjectUtil.isNull(inOrder)) {
        throw new ServiceException("未找到对应的数据");
      }
      if (B.isEqual(inOrder.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
        throw new ServiceException("该单据已入库完成，不允许删除");
      }
      if (B.isEqual(inOrder.getOrderStatus(), InOrderStatusEnum.PARTIAL_FINISHED.getName())) {
        throw new ServiceException("该单据已部分入库完成，不允许删除");
      }
      LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper.eq(InOrderDetail::getOrderId, inOrder.getOrderId())
        .eq(InOrderDetail::getCaseNumber, caseNumber);
      InOrderDetail orderDetail = inOrderDetailService.getOnly(detailLambdaQueryWrapper);
      if (ObjectUtil.isNotNull(orderDetail)) {
        inOrderDetailService.deleteById(orderDetail.getOrderDetailId());
      }
      List<InOrderDetail> detailList = inOrderDetailService.selectListByMainId(inOrder.getOrderId());
      // 如果明细都删完了，则把主表也删掉
      if (detailList.isEmpty()) {
        this.deleteById(inOrder.getOrderId());
      }
    }

  }

  @Override
  public R<Map<String, Object>> printPlateCodeList(List<QueryBo> queryBos) {
    Assert.isFalse(CollUtil.isEmpty(queryBos), "打印单据ID至少选择一条");

    Map<String, Object> printMap = new HashMap<>();
    List<Map<String, Object>> dataList = new ArrayList<>();
    String[] mainIdList = StringUtils.split(queryBos.get(0).getValues(), ",");

    // 获取所有明细
    LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(InOrderDetail::getOrderId, mainIdList);
    var detailList = inOrderDetailService.list(queryWrapper);

    int index = 0;
    for (var id : mainIdList) {
      InOrder inOrder = this.getById(id);
      // 获取主表的数据
      Map<String, Object> mainInfo = BeanUtil.beanToMap(inOrder);

      List<InOrderDetail> inOrderDetails = detailList.stream().filter(x -> B.isEqual(x.getOrderId(), id)).toList();
      List<Map<String, Object>> data = new ArrayList<>();

      for (var item : inOrderDetails) {
        Map<String, Object> rowMap = new HashMap<>(mainInfo);
        rowMap.putAll(BeanUtil.beanToMap(item));
        rowMap.put("index", index);
        dataList.add(rowMap);
        index++;
      }
    }

    // 明细数据
    printMap.put("dataList", dataList);
    printMap.put("isCustomData", true);

    return R.ok(printMap);
  }


  //#endregion

  //#region 送货完成归档

  /**
   * 送货完成归档
   *
   * @param ids 审核参数
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> deliveryComplet(List<Long> ids) {
    List<InOrder> inOrderList = this.listByIds(ids);
    var data = inOrderList.stream().filter(item -> !B.isEqual(item.getOrderStatus(), InOrderStatusEnum.SUCCESS.getName())).toList();
    if (B.isGreater(data.size())) {
      throw new ServiceException("只有审核成功的才允许操作");
    }

    for (InOrder orderInfo : inOrderList) {
      List<InOrderDetail> inOrderDetails = inOrderDetailService.selectListByMainId(orderInfo.getOrderId());
      for (var item : inOrderDetails) {
        if (B.isGreater(item.getQuantity())) {
          Map<String, Object> expandFields = item.getExpandFields();
          if (ObjectUtil.isNull(expandFields)) {
            expandFields = new HashMap<>();
          }
          BigDecimal surplusQuantity = B.subs(item.getQuantity(), item.getPlanQty(), item.getEnterQuantity());  // 剩余未送货量 = 送货数量 - 已收回数量
          BigDecimal enterQuantity = B.add(item.getEnterQuantity(), item.getPlanQty()); // 已收货数量 = 累加送货数量
          expandFields.put("surplusQuantity", surplusQuantity); // 剩余未送货量
          item.setEnterQuantity(enterQuantity);
          item.setExpandFields(expandFields);
          inOrderDetailService.updateById(item);
        }
      }
      inOrderDetails = inOrderDetailService.selectListByMainId(orderInfo.getOrderId());
      var totalEnterQuantity = StreamUtils.sum(inOrderDetails, InOrderDetail::getEnterQuantity);
      orderInfo.setTotalEnterQuantity(totalEnterQuantity);
      this.updateById(orderInfo);

      //修改数据
      this.updateStatus(orderInfo.getOrderId(), InOrderStatusEnum.DELIVERY_COMPLETED);
      //添加轨迹信息
      inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.OPERATION, InOrderStatusEnum.SUCCESS, InOrderStatusEnum.DELIVERY_COMPLETED);

      // 如果已收货数量 < 合计总数量，复制一条单据
      if (B.isLess(totalEnterQuantity, orderInfo.getTotalQuantity())) {
        this.deliveryCopy(orderInfo);
      }
    }
    return R.ok("送货完成");
  }
  //#endregion

  //#region 送货完成复制
  public void deliveryCopy(InOrder inOrder) {

    LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrderDetail::getOrderId, inOrder.getOrderId())
      .apply("CAST(JSON_EXTRACT(expand_fields, '$.surplusQuantity') AS SIGNED) > 0");
    List<InOrderDetail> details = inOrderDetailService.list(queryWrapper);

    String orderCode = DBUtils.getCodeRegular(Objects.requireNonNull(MenuEnum.MENU_1001));
    // 保存主表
    inOrder.setOrderId(null);
    inOrder.setOrderCode(orderCode);
    inOrder.setOrderStatus(InOrderStatusEnum.NEWED.getName());
    inOrder.setShelveStatus(InShelveStatusEnum.WAITING.getName());
    inOrder.setAuditor(null);
    inOrder.setAuditing(AuditEnum.AUDIT.getId());
    inOrder.setArrivedDate(null);
    inOrder.setTotalShelvedQuantity(BigDecimal.ZERO);
    inOrder.setTotalEnterQuantity(BigDecimal.ZERO);
    inOrder.setSourceType("复制生成");
    inOrder.setCreateByName(inOrder.getCreateByName());

    // 重新给扩展字段值赋空
    Map<String, Object> expandFields = inOrder.getExpandFields();
    expandFields.put("truckNo", "");
    expandFields.put("driversName", "");
    expandFields.put("driverMobile", "");
    inOrder.setExpandFields(expandFields);

    this.save(inOrder);

    // 保存明细
    details.forEach(item -> {
      Object surplusQuantity = item.getExpandFields().get("surplusQuantity");
      item.setOrderId(inOrder.getOrderId());
      item.setOrderDetailId(null);
//      item.setEnterQuantity(BigDecimal.ZERO);
      item.setShelvedQuantity(BigDecimal.ZERO);
      item.setOriginPlace(null);
      item.setBatchNumber(null);
      item.setProduceDate(null);
      item.setPlanQty(Convert.toBigDecimal(surplusQuantity)); // 复制后的送货数量 = 上一单的剩余未送货数量
    });
    inOrderDetailService.saveBatch(details);
    //添加轨迹信息
    inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.TAKE_DELIVERY_REMAINING, InOrderStatusEnum.NEWED);


  }
  //#endregion
}
