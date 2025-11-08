package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.BaseExpressCorp;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.basic.domain.tms.vo.TmsLineVo;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.base.IBaseExpressCorpService;
import com.yiruantong.basic.service.client.IBaseClientAddressService;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.basic.service.tms.ITmsLineService;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.out.*;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.excel.core.ExcelResult;
import com.yiruantong.common.excel.listener.ImportCommonListener;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderAndExpressVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPrintVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailSendBatchVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutOrderStatusHistory;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutOrderBo;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.domain.out.vo.*;
import com.yiruantong.outbound.liteflow.Context.OutOrderAuditingContext;
import com.yiruantong.outbound.liteflow.Context.QuickOutContext;
import com.yiruantong.outbound.mapper.out.OutOrderMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.domain.core.SysPrintTemplate;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.core.ISysPrintTemplateService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import com.yiruantong.system.service.monitor.ISysOperLogService;
import com.yiruantong.system.service.task.ITaskQueueService;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 出库订单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class OutOrderServiceImpl extends ServiceImplPlus<OutOrderMapper, OutOrder, OutOrderVo, OutOrderBo> implements IOutOrderService {
  private final IBaseClientAddressService baseClientAddressService;
  private final ITmsLineService tmsLineService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final ISysConfigService sysConfigService;
  private final IOutSortingRuleService outSortingRuleService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IBaseProductService baseProductService;
  private final DataSourceTransactionManager transactionManager;
  private final ISysImportService sysImportService;
  private final IDataAuthService dataAuthService;
  private final ITaskQueueService taskQueueService;
  private final IBaseExpressCorpService baseExpressCorpService;
  private final ISysPrintTemplateService sysPrintTemplateService;
  private final IBaseClientService baseClientService;
  private final ISysOperLogService sysOperLogService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseStorageService baseStorageService;
  @Resource
  private final FlowExecutor flowExecutor;
  private Date date = new Date();

  //#region 加载列表前事件
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    // 生成波次列表页面增加固定条件
    if (Objects.equals(pageQuery.getMenuId(), MenuEnum.MENU_2065.getId())) {
      // 只允许有快递单号进入波次生成
      boolean batch_onlyHasExpressCode = sysConfigService.getConfigBool("batch_onlyHasExpressCode");
      // 只允许CIQ状态（海关状态）为放行的进入波次
      boolean batch_onlyHasCiqStatus = sysConfigService.getConfigBool("batch_onlyHasCiqStatus");

      if (batch_onlyHasExpressCode) {
        QueryBo queryBo = new QueryBo();
        queryBo.setColumn("expressCode");
        queryBo.setQueryType(QueryTypeEnum.ISNOTNULL);
        queryBo.setDataType(DataTypeEnum.STRING);
        pageQuery.addQueryBo(queryBo);
      }

      if (batch_onlyHasCiqStatus) {
        QueryBo queryBo = new QueryBo();
        queryBo.setColumn("ciqStatus");
        queryBo.setQueryType(QueryTypeEnum.EQ);
        queryBo.setDataType(DataTypeEnum.STRING);
        queryBo.setValues(CiqStatusEnum.RELEASE.getName());
        pageQuery.addQueryBo(queryBo);
      }
    }


    //#region 列表之定义查询
    pageQuery.getQueryBoList().stream().filter(f -> f.getQueryType() == QueryTypeEnum.CUSTOM).forEach(f -> {
      if (f.getColumn().equals("itemDescr")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT order_id FROM out_order_detail d WHERE d.order_id=out_order.order_id AND JSON_EXTRACT(d.expand_fields, '$.itemDescr') like'%{}%'", f.getValues()));
      } else {
        String column = StringUtils.toUnderScoreCase(f.getColumn());
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT order_id FROM out_order_detail d WHERE d.order_id=out_order.order_id AND {} like'%{}%'", column, f.getValues()));
      }
    });
    super.beforePageQuery(pageQuery);
    //#endregion
  }
  //#endregion

  //#region 保存后事件
  @Override
  public void afterSaveEditor(SaveEditorBo<OutOrderBo> saveEditorBo, EditorVo<OutOrderVo> editor) {
    // 获取全局参数
    boolean sorting_autoAddRegular = sysConfigService.getConfigBool("sorting_autoAddRegular"); // 添加明细时增加分拣规则
    // 是否新建
    Boolean isAdd = saveEditorBo.isAdd();
    if (isAdd) {
      Assert.isFalse(ObjectUtil.isNull(editor.getMaster().getOrderId()), "出库单ID不能为空");
      OutOrder outOrder = this.baseMapper.selectById(editor.getMaster().getOrderId());
      outOrder.setOrderStatus(null);
      // 生成新建出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.NEWED, OutOrderStatusEnum.AUDIT_WAITING);
    }

    if (sorting_autoAddRegular) {
      for (var item : editor.getDetailList().get(0).getRows()) {
        OutOrderDetail outOrderDetail = Convert.convert(OutOrderDetail.class, item);
        LambdaQueryWrapper<OutSortingRule> ruleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ruleLambdaQueryWrapper
          .eq(OutSortingRule::getProductCode, outOrderDetail.getProductCode())
          .eq(OutSortingRule::getOrderId, outOrderDetail.getOrderId());
        if (ObjectUtil.isNotEmpty(outOrderDetail.getProduceDate())) {
          ruleLambdaQueryWrapper.eq(OutSortingRule::getProduceDate, outOrderDetail.getProduceDate());
        }
        if (ObjectUtil.isNotEmpty(outOrderDetail.getPlateCode())) {
          ruleLambdaQueryWrapper.eq(OutSortingRule::getPlateCode, outOrderDetail.getPlateCode());
        }
        if (ObjectUtil.isNotEmpty(outOrderDetail.getPositionName())) {
          ruleLambdaQueryWrapper.eq(OutSortingRule::getPositionName, outOrderDetail.getPositionName());
        }
        if (ObjectUtil.isNotEmpty(outOrderDetail.getBatchNumber())) {
          ruleLambdaQueryWrapper.eq(OutSortingRule::getBatchNumber, outOrderDetail.getBatchNumber());
        }
        if (ObjectUtil.isNotEmpty(outOrderDetail.getProductCode())) {
          ruleLambdaQueryWrapper.eq(OutSortingRule::getProductCode, outOrderDetail.getProductCode());
        }
        ruleLambdaQueryWrapper.eq(OutSortingRule::getOrderDetailId, outOrderDetail.getOrderDetailId());
        OutSortingRule ruleInfo = outSortingRuleService.getOnly(ruleLambdaQueryWrapper);

        if (ObjectUtil.isEmpty(ruleInfo)) {
          sortingRuleInfo(editor, outOrderDetail);
        }
      }
    }
  }

  private void sortingRuleInfo(EditorVo<OutOrderVo> editor, OutOrderDetail outOrderDetail) {
    OutSortingRule outSortingRule = new OutSortingRule();
    outSortingRule.setConsignorId(editor.getMaster().getConsignorId());
    outSortingRule.setConsignorCode(editor.getMaster().getConsignorCode());
    outSortingRule.setConsignorName(editor.getMaster().getConsignorName());
    outSortingRule.setCreateTime(new Date());
    outSortingRule.setOrderId(editor.getMaster().getOrderId());
    outSortingRule.setOrderDetailId(outOrderDetail.getOrderDetailId());
    outSortingRule.setOrderCode(editor.getMaster().getOrderCode());
    outSortingRule.setProductId(outOrderDetail.getProductId());
    outSortingRule.setProductCode(outOrderDetail.getProductCode());
    outSortingRule.setStorageId(editor.getMaster().getStorageId());
    outSortingRule.setStorageName(editor.getMaster().getStorageName());
    if (ObjectUtil.isNotEmpty(outOrderDetail.getPlateCode())) {
      outSortingRule.setPlateCode(outOrderDetail.getPlateCode());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getProduceDate())) {
      outSortingRule.setProduceDate(Convert.toDate(outOrderDetail.getProduceDate()));
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getPositionName())) {
      outSortingRule.setPositionName(outOrderDetail.getPositionName());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getBatchNumber())) {
      outSortingRule.setBatchNumber(outOrderDetail.getBatchNumber());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getProductCode())) {
      outSortingRule.setProductCode(outOrderDetail.getProductCode());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getProjectCode())) {
      outSortingRule.setProjectCode(outOrderDetail.getProjectCode());
    }
    outSortingRuleService.save(outSortingRule);
  }
  //#endregion

  //#region 根据单号获取出库单信息

  /**
   * 根据单号获取出库单信息
   *
   * @param orderCode 出库单号
   * @return OutOrder 对象结果
   */
  @Override
  public OutOrder getByCode(String orderCode) {
    LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper
      .eq(OutOrder::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
      .and(a -> a.eq(OutOrder::getOrderCode, orderCode)
        .or()
        .eq(OutOrder::getStoreOrderCode, orderCode)
      );

    return this.getOnly(orderLambdaQueryWrapper);
  }
  //#endregion

  //#region 批量审核

  /**
   * 批量审核
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    List<OutOrder> outOrders = this.listByIds(ids);
    LiteflowResponse response = flowExecutor.execute2Resp("outOrderAuditingChain", outOrders, OutOrderAuditingContext.class);
    if (!response.isSuccess()) {
      return R.fail(response.getMessage());
    }

    return R.ok("审核成功");
  }
  //#endregion

  //#region 开启

  /**
   * 开启
   *
   * @param map 前台传入
   * @return 无返回
   */
  @Override
  public R<Void> open(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    for (long orderId : _ids) {
      OutOrder outOrderInfo = this.baseMapper.selectById(orderId);
      Assert.isTrue(ObjectUtil.isNotEmpty(outOrderInfo), "单据不存在！");

      if (!outOrderInfo.getOrderStatus().equals(OutOrderStatusEnum.STOPED.getName())) {
        throw new ServiceException("只有终止的订单才允许开启");
      }
      // 更新出库单审核状态
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper.set(OutOrder::getOrderStatus, AuditEnum.AUDIT.getName())
        .set(OutOrder::getSortingStatus, SortingStatusEnum.NONE.getId())
        .set(OutOrder::getSortingDate, null)
        .set(OutOrder::getAuditing, BigDecimal.ZERO)
        .eq(OutOrder::getOrderId, orderId);
      this.update(orderLambdaUpdateWrapper);

      // 生成开启出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrderInfo, OutOperationTypeEnum.OPEN, OutOrderStatusEnum.AUDIT_WAITING);
    }
    return R.ok("开启成功，订单需要重新审核、分拣操作！");
  }
  //#endregion

  //#region 终止

  /**
   * 终止
   *
   * @param map 前台传入参数
   * @return 无返回
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> stop(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    for (long orderId : _ids) {
      OutOrder outOrderInfo = this.baseMapper.selectById(orderId);
      OutOrderVo outOrder = this.baseMapper.selectVoById(orderId);
      Assert.isFalse(ObjectUtil.isEmpty(outOrder), "单据不存在！");

      //10:部分打包；11;打包完成；12:发运完成；36:部分发运, 37=已关闭
      List<String> statusList = Arrays.asList(OutOrderStatusEnum.PACKAGE_PARTIAL.getName(), OutOrderStatusEnum.PACKAGE_FINISHED.getName(), OutOrderStatusEnum.SHIPMENT_FINISHED.getName(), OutOrderStatusEnum.SHIPMENT_PARTIAL.getName(), OutOrderStatusEnum.CLOSED.getName(), OutOrderStatusEnum.SIGNED.getName(), OutOrderStatusEnum.DELIVERED.getName(), OutOrderStatusEnum.STOPED.getName(), OutOrderStatusEnum.MERGED.getName());
      if (statusList.contains(outOrder.getOrderStatus())) {
        throw new ServiceException("订单状态不允许为：部分打包, 打包完成, 部分发运, 发运完成, 已妥投,已签收,终止");
      }
      //查询波次
      OutOrderWave outOrderWave = outOrderWaveService.getByCode(outOrder.getOrderWaveCode());
      //判断波次是否存在
      if (ObjectUtil.isNotEmpty(outOrderWave)) {
        List<OutOrderWaveDetail> outOrderWaveDetail = outOrderWaveDetailService.selectListById(outOrderWave.getOrderWaveId());
        if (ObjectUtil.isEmpty(outOrderWaveDetail)) {
          outOrderWaveService.deleteById(outOrderWave.getOrderWaveId());
        }
      }
      // 更新原缺货数量           注释原因：源代码更新的是扩展字段‘originLackQty’
      //      List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId());
      //      for (OutOrderDetail outOrderDetail :outOrderDetailList ){
      //        outOrderDetail.setOriginPlace("xxxx");
      //        outOrderDetailService.getBaseMapper().updateById(outOrderDetail);
      //      }
      //执行中止操作
      List<CoreInventoryHistory> coreInventoryHistories = coreInventoryHistoryService.selectListByMainId(orderId);
      Long[] historyIds = coreInventoryHistories.stream().map(CoreInventoryHistory::getHistoryId).toList().toArray(new Long[0]);

      if (!ObjectUtil.isEmpty(historyIds)) {
        coreInventoryHistoryService.deleteByIds(historyIds);
      }

      // 更新出库单明细
      LambdaUpdateWrapper<OutOrderDetail> orderDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderDetailLambdaUpdateWrapper.set(OutOrderDetail::getLackStorage, BigDecimal.ZERO)
        .set(OutOrderDetail::getBatchQuantity, BigDecimal.ZERO)
        .set(OutOrderDetail::getQuantityOuted, BigDecimal.ZERO)
        .set(OutOrderDetail::getSortingStatus, BigDecimal.ONE)
        .eq(OutOrderDetail::getOrderId, orderId);
      outOrderDetailService.update(orderDetailLambdaUpdateWrapper);
      // 更新出库单
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper.set(OutOrder::getOrderStatus, InventoryStatusEnum.TERMINATION.getName())
        .set(OutOrder::getSortingStatus, BigDecimal.ONE)
        .set(OutOrder::getSortingDate, null)
        .set(OutOrder::getAuditing, BigDecimal.ZERO)
        .eq(OutOrder::getOrderId, orderId);
      this.update(orderLambdaUpdateWrapper);

      //删除波次明细
//      List<OutOrderWaveDetail> outOrderWaveDetail = outOrderWaveDetailService.(orderId);

      LambdaQueryWrapper<OutOrderWaveDetail> outOrderWaveDetails = new LambdaQueryWrapper<>();
      outOrderWaveDetails.eq(OutOrderWaveDetail::getOrderId, orderId);
      List<OutOrderWaveDetailVo> outOrderWaveDetail = outOrderWaveDetailService.selectList(outOrderWaveDetails);

      Long[] orderWaveDetailIds = outOrderWaveDetail.stream().map(OutOrderWaveDetailVo::getOrderWaveDetailId).toList().toArray(new Long[0]);

      if (!ObjectUtil.isEmpty(orderWaveDetailIds)) {
        outOrderWaveDetailService.deleteByIds(orderWaveDetailIds);
        //处理波次
        List<OutOrderWaveDetail> outOrderWaveDetail1 = outOrderWaveDetailService.selectListById(outOrderWave.getOrderWaveId());

        BigDecimal TotalQuanity = outOrderWaveDetail1.stream().map(OutOrderWaveDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);

        QueryWrapper<OutOrderWaveDetail> inventoryWrapper = new QueryWrapper<>();
        inventoryWrapper.select("COUNT(DISTINCT Order_Id) as orderCount").lambda()
          .eq(OutOrderWaveDetail::getOrderWaveId, outOrderWave.getOrderWaveId());
        Map<String, Object> paiCountMap = outOrderWaveDetailService.getMap(inventoryWrapper);
        Long totalEnterQuantity = Convert.toLong(paiCountMap.get("orderCount"));

        LambdaUpdateWrapper<OutOrderWave> outOrderWaveWrapper = new LambdaUpdateWrapper<>();
        outOrderWaveWrapper.set(OutOrderWave::getOrderCount, totalEnterQuantity)
          .set(OutOrderWave::getUnFinishedCount, totalEnterQuantity)
          .set(OutOrderWave::getPickQuantity, TotalQuanity)
          .set(OutOrderWave::getTotalQuanityOrder, TotalQuanity)
          .eq(OutOrderWave::getOrderWaveId, outOrderWave.getOrderWaveId());
        outOrderWaveService.update(outOrderWaveWrapper);

      }


      orderLambdaUpdateWrapper.set(OutOrder::getOrderWaveId, null)
        .set(OutOrder::getOrderWaveCode, null)
        .eq(OutOrder::getOrderId, orderId);
      this.update(orderLambdaUpdateWrapper);

      // 清除占位
      coreInventoryHolderService.clearHolder(List.of(HolderSourceTypeEnum.OUT_ORDER_NORMAL, HolderSourceTypeEnum.PC_OUT_PLAN, HolderSourceTypeEnum.OUT_PICKING, HolderSourceTypeEnum.MATERIAL_SPARE_PARTS), orderId);
      // 生成终止出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrderInfo, OutOperationTypeEnum.STOP, OutOrderStatusEnum.STOPED);
      // 装载计划单删除逻辑  未实现
      //#region 装载计划单删除逻辑
      //      let planOrderInfo = await this.dbRead.findOne(TMSLoadPlanOrder, {
//        order_Id: dataInfo.order_Id
//        });
//      if (planOrderInfo) {
//        let loadPlanList_Id = planOrderInfo.loadPlanList_Id;
//
//        await this.dbWrite.delete(TMSLoadPlanOrder, {
//          order_Id: dataInfo.order_Id
//          });
//        let planOrderList = await this.dbRead.find(TMSLoadPlanOrder, {
//          loadPlanList_Id: loadPlanList_Id
//          });
//        let loadPlanInfo = await this.dbRead.findOne(TMSLoadPlanList, {
//          loadPlanList_Id: loadPlanList_Id
//          });
//        let loadPlan_Id = loadPlanInfo.loadPlan_Id;
//        if (planOrderList.length === 0) {
//          await this.dbWrite.delete(TMSLoadPlanList, {
//            loadPlanList_Id: loadPlanList_Id
//            });
//          let loadPlanList = await this.dbRead.find(TMSLoadPlanList, {
//            loadPlan_Id: loadPlan_Id
//            });
//          if (loadPlanList.length === 0) {
//            await this.dbWrite.delete(TMSLoadPlan, {
//              loadPlan_Id: loadPlan_Id
//              });
//          }
//        }
//
//        // 重新合计装载计划数值
//        var tolItemQuantity = 0;
//        var tolVehicleVolume = 0;
//        var tolLoadVehicleVolume = 0;
//        var tolSurplusVehicleVolume = 0;
//        var tolVehicleload = 0;
//        var tolLoadVehicleload = 0;
//        var tolSurplusVehicleload = 0;
//        let newplanOrderList = await this.dbRead.find(TMSLoadPlanOrder, {
//          loadPlanList_Id: loadPlanList_Id
//          });
//        let newLoadPlanList = await this.dbRead.find(TMSLoadPlanList, {
//          loadPlan_Id: loadPlan_Id
//          });
//        for (var newPlanOrderInfo of newplanOrderList) {
//          tolItemQuantity += newPlanOrderInfo.itemQuantity;
//          var sql2 =
//            " select top 1 (select isnull(weight,0) from Base_ProductInfo where Product_Id=Sale_OrderList.Product_Id ) as Product_Weight,(select ExpandFields from Base_ProductInfo where Product_Id=Sale_OrderList.Product_Id ) as Product_ExpandFields  from Sale_OrderList where OrderList_Id=" +
//              newPlanOrderInfo.orderList_Id;
//          let saleOrderDetailList = await this.dbRead.query(sql2);
//          var Product_ExpandFields = JSON.parse(saleOrderDetailList[0].Product_ExpandFields);
//          var Product_cube = 0;
//          var Product_Weight = 0;
//          var Product_coefficient = 1;
//          var itme = saleOrderDetailList[0];
//          if (itme.Product_ExpandFields) {
//            Product_ExpandFields = JSON.parse(itme.Product_ExpandFields);
//            if (Product_ExpandFields.cube) {
//              Product_cube = Product_ExpandFields.cube;
//            }
//            if (Product_ExpandFields.coefficient && parseInt(Product_ExpandFields.coefficient) > 1) {
//              Product_coefficient = Product_ExpandFields.coefficient;
//            }
//          }
//          if (itme.Product_Weight) {
//            Product_Weight = itme.Product_Weight;
//          }
//          var TolProduct_Weight = Product_Weight * newPlanOrderInfo.itemQuantity;
//          var TolProduct_cube = 0;
//          if (newPlanOrderInfo.itemQuantity > 1) {
//            TolProduct_cube = Product_cube * 1 + (newPlanOrderInfo.itemQuantity - 1) * Product_cube * Product_coefficient;
//          } else {
//            TolProduct_cube = Product_cube * 1;
//          }
//          await this.ctx.helper.setExpandFields(newPlanOrderInfo, "tolProduct_cube", TolProduct_cube);
//          await this.ctx.helper.setExpandFields(newPlanOrderInfo, "tolProduct_Weight", TolProduct_Weight);
//        }
//        for (var newLoadPlanInfo of newLoadPlanList) {
//          var filterList = newplanOrderList.filter(v => v.loadPlanList_Id === newLoadPlanInfo.loadPlanList_Id);
//          var cube = 0;
//          var Weight = 0;
//          for (var filterInfo of filterList) {
//            let tolcube = await this.ctx.helper.getExpandFields(filterInfo, "tolProduct_cube");
//            let tolWeight = await this.ctx.helper.getExpandFields(filterInfo, "tolProduct_Weight");
//            cube += this.ctx.helper.Round(tolcube, 4);
//            Weight += this.ctx.helper.Round(tolWeight, 4);
//          }
//          newLoadPlanInfo.surplusVehicleVolume = newLoadPlanInfo.vehicleVolume - cube;
//          newLoadPlanInfo.surplusVehicleload = newLoadPlanInfo.vehicleload - Weight;
//          newLoadPlanInfo.loadVehicleload = Weight;
//          newLoadPlanInfo.loadVehicleVolume = cube;
//
//          tolVehicleVolume += newLoadPlanInfo.vehicleVolume;
//          tolLoadVehicleVolume += newLoadPlanInfo.loadVehicleVolume;
//          tolSurplusVehicleVolume += newLoadPlanInfo.surplusVehicleVolume;
//          tolVehicleload += newLoadPlanInfo.vehicleload;
//          tolLoadVehicleload += newLoadPlanInfo.loadVehicleload;
//          tolSurplusVehicleload += newLoadPlanInfo.surplusVehicleload;
//          await this.dbWrite.save(newLoadPlanInfo);
//        }
//        let tMSLoadPlanInfo = await this.dbRead.findOne(TMSLoadPlan, {
//          loadPlan_Id: loadPlan_Id
//          });
//        tMSLoadPlanInfo.tolItemQuantity = tolItemQuantity;
//        tMSLoadPlanInfo.tolVehicleVolume = tolVehicleVolume;
//        tMSLoadPlanInfo.tolLoadVehicleVolume = tolLoadVehicleVolume;
//        tMSLoadPlanInfo.tolSurplusVehicleVolume = tolSurplusVehicleVolume;
//        tMSLoadPlanInfo.tolVehicleload = tolVehicleload;
//        tMSLoadPlanInfo.tolLoadVehicleload = tolLoadVehicleload;
//        tMSLoadPlanInfo.tolSurplusVehicleload = tolSurplusVehicleload;
//        await this.dbWrite.save(tMSLoadPlanInfo);
//      }
      //#endregion
    }
    return R.ok("终止成功");
  }
  //#endregion

  //#region 更新分拣状态、出库单状态、下架状态、配货状态、打包状态

  /**
   * 更新分拣状态
   *
   * @param orderId           出库单ID
   * @param sortingStatusEnum 分拣状态
   */
  @Override
  public void updateSortingStatus(Long orderId, SortingStatusEnum sortingStatusEnum) {
    LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(OutOrder::getSortingStatus, sortingStatusEnum.getId())
      .set(OutOrder::getSortingDate, DateUtil.date())
      .eq(OutOrder::getOrderId, orderId);
    this.update(orderLambdaUpdateWrapper);
  }

  @Override
  public void updateOrderStatus(Long orderId, OutOrderStatusEnum outOrderStatusEnum) {
    LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(OutOrder::getOrderStatus, outOrderStatusEnum.getName())
      .eq(OutOrder::getOrderId, orderId);
    this.update(orderLambdaUpdateWrapper);
  }

  @Override
  public void updatePickingStatus(Long orderId, OutPickingStatusEnum outPickingStatusEnum) {
    LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(OutOrder::getPickingStatus, outPickingStatusEnum.getName())
      .eq(OutOrder::getOrderId, orderId);
    this.update(orderLambdaUpdateWrapper);
  }

  @Override
  public void updateMatchStatus(Long orderId, OutMatchStatusEnum outMatchStatusEnum) {
    LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(OutOrder::getMatchStatus, outMatchStatusEnum.getName())
      .eq(OutOrder::getOrderId, orderId);
    this.update(orderLambdaUpdateWrapper);
  }

  @Override
  public void updatePackageStatus(Long orderId, OutPackageStatusEnum outPackageStatusEnum) {
    LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper.set(OutOrder::getPackageStatus, outPackageStatusEnum.getName())
      .eq(OutOrder::getOrderId, orderId);
    this.update(orderLambdaUpdateWrapper);
  }
  //#endregion

  //#region 分拣
  @Override
  public R<Void> sorting(Map<String, Object> map) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    String ids = Convert.toStr(map.get("ids"));
    Assert.isFalse(ObjectUtil.isNull(ids), "出库单ID不能为空");

    IOutOrderSortingService bean = SpringUtils.getBean(IOutOrderSortingService.class); // 需要动态获取bean，否则循环冲突
    List<Long> orderIdList = StringUtils.splitTo(ids, ",", Convert::toLong);
    for (Long orderId : orderIdList) {

      OutOrder outOrder = this.baseMapper.selectById(orderId);
      //调用RabbitMQ
      RabbitReceiverDto receiverDto = new RabbitReceiverDto();
      receiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_SORTING); // 审核完成后，自动分拣、自动生成波次
      receiverDto.setBillId(outOrder.getOrderId());
      receiverDto.setBillCode(outOrder.getOrderCode());
      receiverDto.setLoginUser(loginUser);
      taskQueueService.createTask(receiverDto);
      //#endregion
    }


    return R.ok("分拣完毕");
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
  public R<Map<String, Object>> copyEditor(SaveEditorBo<OutOrderBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    OutOrder outOrder = this.getById(saveEditorBo.getIdValue());
    List<OutOrderDetail> details = outOrderDetailService.selectListByMainId(saveEditorBo.getIdValue());

    String orderCode = DBUtils.getCodeRegular(Objects.requireNonNull(MenuEnum.getEnumById(saveEditorBo.getMenuId())));
    // 保存主表
    outOrder.setOrderId(null);
    outOrder.setOrderCode(orderCode);
    outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());
    outOrder.setSortingStatus(SortingStatusEnum.NONE.getId());
    outOrder.setSourceType(OutSourceTypeEnum.COPY_CREATE.getName());
    outOrder.setCiqStatus(null);
    outOrder.setFinStatus(null);
    outOrder.setMatchStatus(null);
    outOrder.setPackageStatus(null);
    outOrder.setPickingStatus(null);
    outOrder.setOrderWaveCode(null);
    outOrder.setOrderWaveId(null);
    outOrder.setAuditing(AuditEnum.AUDIT.getId());
    outOrder.setApplyDate(null);
    this.save(outOrder);

    // 保存明细
    details.forEach(item -> {
      item.setOrderId(outOrder.getOrderId());
      item.setOrderDetailId(null);
      item.setSortingStatus(null);
      item.setBatchQuantity(null);
      item.setSortingStatus(SortingStatusEnum.NONE.getId());
      item.setQuantityOuted(BigDecimal.ZERO);
      item.setQuantityShipped(BigDecimal.ZERO);
      item.setQuantityRefunded(BigDecimal.ZERO);
    });
    outOrderDetailService.saveBatch(details);


    // 生成复制出库单的轨迹
    outOrder.setOrderStatus(null);
    outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.COPY, OutOrderStatusEnum.AUDIT_WAITING);

    return R.ok("复制成功");
  }
  //#endregion

  //#region 获取分拣列表
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<OutSortingRule> getSortingRule(Map<String, Object> map) {
    // 获得分拣规则
    LambdaQueryWrapper<OutSortingRule> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(OutSortingRule::getOrderDetailId, Convert.toLong(map.get("orderDetailId")));


    return outSortingRuleService.list(orderLambdaQueryWrapper);
  }
  //#endregion

  //#region 获取拣配单
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<List<OutOrderDetailHolderComposeVO>> getOutPickingList(List<QueryBo> queryBoList) {
    // 获取拣配单
    String column = queryBoList.get(0).getColumn();
    String values = queryBoList.get(0).getValues();
    List<OutOrderDetailHolderComposeVO> list = new ArrayList<>();
    if (ObjectUtil.equals(column, "orderId")) {
      List<OutOrderDetail> orderDetailList = outOrderDetailService.selectListByMainId(Convert.toLong(values));

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
        list.add(outOrderDetailHolderComposeVO);
      }
    }
    return R.ok(list);
  }
  //#endregion

  //#region 设置分拣规则
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> setSortingRule(Map<String, Object> map) {

    LambdaQueryWrapper<OutSortingRule> queryWrapper222 = new LambdaQueryWrapper<>();
    queryWrapper222.eq(OutSortingRule::getProductCode, map.get("productCode"))
      .eq(OutSortingRule::getOrderId, map.get("orderId"))
      .eq(OutSortingRule::getOrderDetailId, map.get("orderDetailId"));
    List<OutSortingRuleVo> sortingRule222 = outSortingRuleService.selectList(queryWrapper222);


    for (var item : sortingRule222) {
      if (!Objects.equals(map.get("batchNumber"), "") && !Objects.equals(item.getBatchNumber(), "") && ObjectUtil.isNotNull(item.getBatchNumber())) {
        throw new ServiceException("批次号已存在!");
      }
      if (!Objects.equals(map.get("produceDate"), "") && ObjectUtil.isNotNull(map.get("produceDate")) && !Objects.equals(item.getProduceDate(), "") && ObjectUtil.isNotNull(item.getProduceDate())) {
        throw new ServiceException("生产日期已存在!");
      }
      if (!Objects.equals(map.get("positionName"), "") && !Objects.equals(item.getPositionName(), "") && ObjectUtil.isNotNull(item.getPositionName())) {
        throw new ServiceException("拣货货位已存在!");
      }
      if (!Objects.equals(map.get("plateCode"), "") && !Objects.equals(item.getPlateCode(), "") && ObjectUtil.isNotNull(item.getPlateCode())) {
        throw new ServiceException("托盘号已存在!");
      }
      if (!Objects.equals(map.get("singleSignCode"), "") && !Objects.equals(item.getSingleSignCode(), "") && ObjectUtil.isNotNull(item.getSingleSignCode())) {
        throw new ServiceException("唯一码已存在!");
      }
      if (!Objects.equals(map.get("inventoryId"), "") && !Objects.equals(item.getInventoryId(), "") && ObjectUtil.isNotNull(item.getInventoryId())) {
        throw new ServiceException("库存ID已存在!");
      }
      if (!Objects.equals(map.get("productSpec"), "") && !Objects.equals(item.getProductSpec(), "") && ObjectUtil.isNotNull(item.getProductSpec())) {
        throw new ServiceException("商品规格已存在!");
      }
    }

    OutSortingRule outSortingRule = new OutSortingRule();
    outSortingRule.setBatchNumber(Convert.toStr(map.get("batchNumber")));
    outSortingRule.setConsignorId(Convert.toLong(map.get("consignorId")));
    outSortingRule.setConsignorCode(Convert.toStr(map.get("consignorCode")));
    outSortingRule.setConsignorName(Convert.toStr(map.get("consignorName")));
    outSortingRule.setCreateTime(new Date());
    outSortingRule.setOrderId(Convert.toLong(map.get("orderId")));
    outSortingRule.setOrderDetailId(Convert.toLong(map.get("orderDetailId")));
    outSortingRule.setOrderCode(Convert.toStr(map.get("orderCode")));
    outSortingRule.setPlateCode(Convert.toStr(map.get("plateCode")));
    outSortingRule.setPositionName(Convert.toStr(map.get("positionName")));
    outSortingRule.setProduceDate(Convert.toDate(map.get("produceDate")));
    outSortingRule.setProductId(Convert.toLong(map.get("productId")));
    outSortingRule.setProductCode(Convert.toStr(map.get("productCode")));
    outSortingRule.setSingleSignCode(Convert.toStr(map.get("singleSignCode")));
    outSortingRule.setInventoryId(Convert.toLong(map.get("inventoryId")));
    outSortingRule.setStorageId(Convert.toLong(map.get("storageId")));
    outSortingRule.setStorageName(Convert.toStr(map.get("storageName")));
    outSortingRule.setProduceDateGt(Convert.toDate(map.get("produceDateGt")));
    outSortingRule.setCaseNumber(Convert.toStr(map.get("caseNumber")));
    outSortingRule.setProjectCode(Convert.toStr(map.get("projectCode")));
    outSortingRule.setProductSpec(Convert.toStr(map.get("productSpec")));
    outSortingRuleService.save(outSortingRule);

    return R.ok(null);
  }
  //#endregion

  //#region 关闭分拣规则
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> deleteSortingRule(Map<String, Object> map) {
    // 删除分拣规则
    LambdaQueryWrapper<OutSortingRule> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(OutSortingRule::getRuleId, Convert.toLong(map.get("ruleId")));

    outSortingRuleService.remove(orderLambdaQueryWrapper);
    return R.ok(null);
  }

  /**
   * 自定义 缺货转预到货 查询 页面
   *
   * @param pageQuery 前台传入
   * @return 返回内容
   */
  @Override
  public TableDataInfo<OrderDetailLackVo> orderDetailLackList(PageQuery pageQuery) {
    IPage<OrderDetailLackVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限

    MPJLambdaWrapper<OutOrderDetail> wrapper = new MPJLambdaWrapper<OutOrderDetail>()
      .selectAll(OutOrder.class)
      .selectAll(OutOrderDetail.class)
      .select(BaseProduct::getProviderId, BaseProduct::getProviderCode, BaseProduct::getProviderShortName, BaseProduct::getTypeId, BaseProduct::getTypeName)
      .eq(OutOrder::getOrderStatus, OutOrderStatusEnum.AUDIT_SUCCESS.getName())
      .in(OutOrder::getSortingStatus, 3, 5) //缺货或者
      .gt(OutOrderDetail::getLackStorage, 0)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderDetail::getProductId);

    //#region 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, OutOrderDetail.class, OutOrder.class, BaseProduct.class);
    //#endregion

    pageQuery.setFieldClassList(List.of(OutOrder.class, OutOrderDetail.class, BaseProduct.class)); // 设置别名

    IPage<OrderDetailLackVo> page = outOrderDetailService.selectJoinListPage(ipage, OrderDetailLackVo.class, wrapper);

    // 推荐货位
    for (var item : page.getRecords()) {
      item.setPurchaseStorage(item.getLackStorage());
    }
    TableDataInfo<OrderDetailLackVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  @Override
  public List<OutOrder> selectByOrderWaveId(Long orderWaveId) {
    LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper.eq(OutOrder::getOrderWaveId, orderWaveId);

    return this.getBaseMapper().selectList(orderLambdaQueryWrapper);
  }
  //#endregion

  //#region 出库单导入数据
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
//      Long storageId = Convert.toLong(request.getParameter("storageId"));
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
      //#region 数据校验
      for (var row : dataList) {
        i++;
        BigDecimal quantityOrder = Convert.toBigDecimal(row.get("quantityOrder"));
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
      //#endregion


      // dataList：excel数据
      // 处理客户订单号
      dataList = dataList.stream().map(item -> {
        String sourceDetailId = Convert.toStr(item.get("sourceDetailId"));
        String[] splitStr = StringUtils.split(sourceDetailId, "-");
        item.put("sourceDetailId1", sourceDetailId); // 带"-"的单号
        if (splitStr.length > 1) {
          item.put("sourceDetailId", "^" + splitStr[0]); // 截取后的单号和不带"-"的单号分组在一起
        }
        return item;
      }).toList();

      // 对单据进行分组
      var groupList = sysImportService.getGroupList(dataList, importId);
      // 循环处理分组数据，groupList对应的是主表数据
      i = 0;
      for (Map<String, Object> item : groupList) {
        i++;
        sysImportService.writeMsg("正在导入第{}行", i);
        final String trackingNumber = Convert.toStr(item.get("trackingNumber"));
        final String productCode = Convert.toStr(item.get("productCode"));
        final String productModel = Convert.toStr(item.get("productModel"));
        final String consignorName = Convert.toStr(item.get("consignorName"));
        final String expressCorpName = Convert.toStr(item.get("expressCorpName"));


        // 获得明细数据
        var detailList = sysImportService.getGroupDetails(dataList, item, importId);

        for (var detailItem : detailList) {
          String sourceDetailId1 = Convert.toStr(detailItem.get("sourceDetailId1")); // 原始值

          LambdaQueryWrapper<OutOrderDetail> detailWrapper = new LambdaQueryWrapper<>();
          detailWrapper.eq(OutOrderDetail::getSourceDetailId, sourceDetailId1); // 客户单号
          var list = outOrderDetailService.list(detailWrapper);
          String repeatMsg = "客户单号已存在，请检查：" + list.stream().map(f -> Convert.toStr(f.getSourceDetailId())).collect(Collectors.joining(","));
          if (!list.isEmpty()) {
            sysImportService.writeEnd(repeatMsg);
            return;
          }
        }
        // 判断是否存在多个集装箱号
        var containerGroupList = detailList.stream().filter(StreamUtils.distinctByKey(b -> b.get("containerNo"))).toList();
        sysImportService.isAssert(containerGroupList.size() > 1, "{}、一条单据只能有一个集装箱号，目前存在{}个集装箱号", i, containerGroupList.size());

        // 验证出库单不重复
        if (StringUtils.isNotEmpty(trackingNumber)) {
          LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
          orderLambdaQueryWrapper.eq(OutOrder::getStoreOrderCode, trackingNumber);
          if (this.exists(orderLambdaQueryWrapper)) {
            sysImportService.writeMsgRed("{}、店铺订单号{}已存在，不允许重复导入，已跳过", i, trackingNumber);
            continue;
          }
        }
        //判断快递公司是否存在
        if (StringUtils.isNotEmpty(expressCorpName)) {
          LambdaQueryWrapper<BaseExpressCorp> expressCorpLambdaQueryWrapper = new LambdaQueryWrapper<>();
          expressCorpLambdaQueryWrapper.eq(BaseExpressCorp::getExpressCorpName, expressCorpName);
          if (ObjectUtil.isEmpty(expressCorpLambdaQueryWrapper)) {
            sysImportService.writeMsgRed("{}、快递公司{}不存在，已跳过", i, expressCorpName);
            return;
          }
        }

        String auditRemark = detailList.stream()
          .map(f -> Convert.toStr(f.get("auditRemark"))) // 获取备注
          .filter(Objects::nonNull) // 过滤空值
          .collect(Collectors.joining(","));

        var orderInfo = new OutOrder();
        BeanUtil.copyProperties(item, orderInfo);
        String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1671, loginUser.getTenantId());
        orderInfo.setOrderCode(orderCode);
        orderInfo.setOrderType(OutOrderTypeEnum.NORMAL.getName());
        orderInfo.setSourceType(OutSourceTypeEnum.IMPORT_CREATE.getName());
        orderInfo.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());
        orderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
        orderInfo.setAuditing(AuditEnum.AUDIT.getId());
        // 供应商为空，默认第一个供应商
        orderInfo.setContainerNo(Convert.toStr(item.get("containerNo")));
        orderInfo.setCreateBy(loginUser.getUserId());
        orderInfo.setCreateByName(loginUser.getNickname());
        orderInfo.setUserId(loginUser.getUserId());
        orderInfo.setNickName(loginUser.getNickname());
        orderInfo.setDeptId(loginUser.getDeptId());
        orderInfo.setDeptName(loginUser.getDeptName());
//        orderInfo.setApplyDate(DateUtil.date());
        orderInfo.setRemark(Convert.toStr(item.get("mainRemark"))); // 主表备注
        orderInfo.setAuditRemark(auditRemark); // 审核备注


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

        //#region 匹配仓库
        String storageName = Convert.toStr(item.get("storageName")); // 北京-天津DC
        BaseStorage storageInfo = null;
        if (ObjectUtil.isNotNull(storageName)) {
          // 查询仓库
          LambdaQueryWrapper<BaseStorage> storageWrapper = new LambdaQueryWrapper<>();
          storageWrapper.eq(BaseStorage::getStorageName, storageName) // 优先按仓库名称匹配
            .eq(BaseStorage::getEnable, EnableEnum.ENABLE.getId());
          storageInfo = baseStorageService.getOne(storageWrapper);
          if (ObjectUtil.isNotNull(storageInfo)) {
            orderInfo.setStorageId(storageInfo.getStorageId());
            orderInfo.setStorageName(storageInfo.getStorageName());
          } else {
            // 查询仓库
            LambdaQueryWrapper<BaseStorage> storageWrapper2 = new LambdaQueryWrapper<>();
            storageWrapper2.like(BaseStorage::getAliasName, storageName) // 按仓库别名匹配
              .eq(BaseStorage::getEnable, EnableEnum.ENABLE.getId());
            storageInfo = baseStorageService.getOne(storageWrapper2);
            if (ObjectUtil.isNotNull(storageInfo)) {
              orderInfo.setStorageId(storageInfo.getStorageId());
              orderInfo.setStorageName(storageInfo.getStorageName());
            }
          }
        }
        //#endregion

        //#region 时间处理
        Date date1 = calculateDate(orderInfo.getApplyDate(), orderInfo, storageInfo, true);
        orderInfo.setDeliveryDate(date1);
        orderInfo.setArriveDate(date1);
        //#endregion

        //#region 线路处理
        LambdaQueryWrapper<BaseClientAddress> clientAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientAddressLambdaQueryWrapper.eq(BaseClientAddress::getClientId, orderInfo.getClientId())
          .eq(BaseClientAddress::getAddress, orderInfo.getShippingAddress())
          .last("limit 1");
        BaseClientAddress clientAddress = baseClientAddressService.getOne(clientAddressLambdaQueryWrapper);
        sysImportService.isAssert(ObjectUtil.isNull(clientAddress), "没有查询到客户地址，请检查客户：【" + orderInfo.getClientShortName() + "】的地址：【" + orderInfo.getShippingAddress() + "】是否已存在！");

        //查询线路信息
        LambdaQueryWrapper<TmsLine> tmsLineLambdaQueryWrapper = new LambdaQueryWrapper<TmsLine>();
        tmsLineLambdaQueryWrapper.eq(TmsLine::getDistributionSiteId, storageInfo.getSiteId())
          .eq(TmsLine::getUnloadSiteId, clientAddress.getSiteId())
          .last("limit 1");
        TmsLineVo lineInfo = tmsLineService.selectOne(tmsLineLambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(lineInfo)) {
          orderInfo.setExpressCorpLine(lineInfo.getExpressCorpLine());
          orderInfo.setExpressCorpTel(lineInfo.getExpressCorpTel());
          orderInfo.setLineId(lineInfo.getLineId());
          orderInfo.setLineCode(lineInfo.getLineCode());
          orderInfo.setLineName(lineInfo.getLineName());

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date1);
          BigDecimal prescription = StrUtil.isNotEmpty(lineInfo.getPrescription()) ? new BigDecimal(lineInfo.getPrescription()) : BigDecimal.ZERO;
          if (B.isGreaterOrEqual(prescription, new BigDecimal(10L))) {
            calendar.add(Calendar.DAY_OF_MONTH, (int) Math.ceil(Convert.toDouble(B.div(prescription, new BigDecimal(24)))));
          }

          orderInfo.setArriveDate(calendar.getTime());
        }
        //#endregion


        // 保存主表
        this.getBaseMapper().insert(orderInfo);

        orderInfo.setOrderStatus(null); // 主表保存后再置为空，为了导入后状态轨迹使用
        // 出库单的轨迹
        outOrderStatusHistoryService.AddHistory(orderInfo, OutOperationTypeEnum.IMPORT, OutOrderStatusEnum.AUDIT_WAITING, loginUser);

        /*——————————————————————————————————————————————————
         * 处理明细，将明细挂载到主表下
         *——————————————————————————————————————————————————*/
        List<OutOrderDetail> orderDetailList = new ArrayList<>();
        for (var detail : detailList) {
          var detailInfo = new OutOrderDetail();
          // 根据同货主同条码查找商品信息
          var baseProduct = this.baseProductService.getByCodeAndConsignor(Convert.toStr(detail.get("productCode")), Convert.toStr(detail.get("consignorName")));
          if (ObjectUtil.isEmpty(baseProduct)) {
            // 同货主没找到，在根据商品条码查询
            baseProduct = this.baseProductService.getByCode(Convert.toStr(detail.get("productCode")));
          }
          // 获得明细表扩展字段
          BeanUtil.copyProperties(baseProduct, detailInfo);
          BeanUtil.copyProperties(detail, detailInfo);
          detailInfo.setSourceDetailId(Convert.toStr(detail.get("sourceDetailId1"))); // 原始值

          //#region 匹配销售区域
          String sourceDetailId = Convert.toStr(detail.get("sourceDetailId")); // CD24031008
          if (ObjectUtil.isNotNull(sourceDetailId)) {
            Pattern pattern = Pattern.compile("^[^\\d]+"); // 匹配开头的非数字字符
            Matcher matcher = pattern.matcher(sourceDetailId);

            if (matcher.find()) {
              // 匹配出来的前缀
              String prefix = matcher.group();

              // 查询货主
              LambdaQueryWrapper<BaseConsignor> consignorWrapper = new LambdaQueryWrapper<>();
              consignorWrapper.apply("find_in_set('" + prefix + "',alias_name)")
                .eq(BaseConsignor::getEnable, EnableEnum.ENABLE.getId());
              BaseConsignor consignorInfo = baseConsignorService.getOne(consignorWrapper);
              if (ObjectUtil.isNotNull(consignorInfo)) {
                detailInfo.setConsignorIdSale(consignorInfo.getConsignorId());
                detailInfo.setConsignorNameSale(consignorInfo.getConsignorName());
              }
            }
          }
          //#endregion

          if (in_autoSingleSignCode) {
            // 开启唯一码
            detailInfo.setSingleSignCode(IdUtil.simpleUUID());
          } else {
            detailInfo.setSingleSignCode(Convert.toStr(detail.get("singleSignCode")));
          }

          //#region 公式计算
          // 单位毛重weight，小计毛重totalWeight，相互计算
          BigDecimal quantityOrder = Convert.toBigDecimal(detail.get("quantityOrder"));
          BigDecimal salePrice = Convert.toBigDecimal(detail.get("salePrice"));
          BigDecimal ratePrice = Convert.toBigDecimal(detail.get("ratePrice"));
          BigDecimal weight = Convert.toBigDecimal(detail.get("weight"));
          BigDecimal rowWeight = Convert.toBigDecimal(detail.get("rowWeight"));
          BigDecimal logisticsWeightTon = Convert.toBigDecimal(detail.get("logisticsWeightTon"));
          BigDecimal rowLogisticsWeight = Convert.toBigDecimal(detail.get("rowLogisticsWeight"));
          BigDecimal rowWeightTon = Convert.toBigDecimal(detail.get("rowWeightTon"));
          BigDecimal bigQty = Convert.toBigDecimal(detail.get("bigQty")); // 大单位数量
          BigDecimal paiQty = Convert.toBigDecimal(detail.get("paiQty")); // 拍数
          BigDecimal unitPackage = Convert.toBigDecimal(detail.get("unitPackage")); // 单位包数
          BigDecimal unitCube = Convert.toBigDecimal(detail.get("unitCube"));
          BigDecimal rowCube = Convert.toBigDecimal(detail.get("rowCube"));
          String smallUnit = Convert.toStr(detail.get("smallUnit"));
          String bigUnit = Convert.toStr(detail.get("bigUnit"));

          BigDecimal thousand = new BigDecimal(1000);

          if (ObjectUtil.isEmpty(salePrice)) {
            salePrice = baseProduct.getSalePrice();
            detailInfo.setSalePrice(baseProduct.getSalePrice());
          }
          if (ObjectUtil.isEmpty(smallUnit)) {
            smallUnit = baseProduct.getSmallUnit();
          }
          if (ObjectUtil.isEmpty(bigUnit)) {
            bigUnit = baseProduct.getBigUnit();
          }
          detailInfo.setSmallUnit(smallUnit);
          detailInfo.setBigUnit(bigUnit);

          detailInfo.setQuantityOrder(quantityOrder);
          detailInfo.setSortingStatus(SortingStatusEnum.NONE.getId());

          BigDecimal RowWeight = BigDecimal.ZERO;
          //#region 重量计算
          if (ObjectUtil.isEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重和小计毛重都为空时，默认商品信息单位重量
            detailInfo.setWeight(baseProduct.getWeight()); // 单位重量
            RowWeight = B.mul(baseProduct.getWeight(), detailInfo.getQuantityOrder());
            if (ObjectUtil.isNotNull(RowWeight)) {
              RowWeight = RowWeight.setScale(2, RoundingMode.HALF_UP); // 保留2位小数并进行四舍五入
              detailInfo.setRowWeight(RowWeight); // 小计重量
            }
            // 如果没有商品重量并且有模板总重量吨
            if (ObjectUtil.isEmpty(baseProduct.getWeight()) && ObjectUtil.isNotEmpty(rowWeightTon)) {
              detailInfo.setRowWeight(B.mul(detail.get("rowWeightTon"), 1000)); // 合计重量
              detailInfo.setWeight(B.div(detailInfo.getRowWeight(), quantityOrder)); // 单位重量
            } else {
              detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计重量吨
            }
          } else if (ObjectUtil.isEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重为空和小计毛重不为空时，单位毛重通过小计毛重反算
            detailInfo.setWeight(B.div(rowWeight, detailInfo.getQuantityOrder()));
            detailInfo.setRowWeight(rowWeight);
            detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计重量吨
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isEmpty(rowWeight)) {
            // 单位毛重不为空和小计毛重为空时，小计毛重通过单位毛重计算
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(B.mul(weight, detailInfo.getQuantityOrder()));
            detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计重量吨
          } else if (ObjectUtil.isNotEmpty(weight) && ObjectUtil.isNotEmpty(rowWeight)) {
            // 单位毛重和小计毛重都不为空时，直接赋值
            detailInfo.setWeight(weight);
            detailInfo.setRowWeight(rowWeight);
            detailInfo.setRowWeightTon(B.div(detailInfo.getRowWeight(), thousand)); // 合计重量吨
          }
          //#endregion

          //#region 物流重量计算
          if (ObjectUtil.isEmpty(logisticsWeightTon) && ObjectUtil.isEmpty(rowLogisticsWeight)) {
            // 物流重量和小计物流重量都为空时，默认商品信息物流重量
            detailInfo.setLogisticsWeightTon(baseProduct.getLogisticsWeightTon()); // 物流重量
            BigDecimal RowLogisticsWeight = BigDecimal.ZERO;
            RowLogisticsWeight = B.mul(baseProduct.getLogisticsWeightTon(), detailInfo.getQuantityOrder());
            if (ObjectUtil.isNotNull(RowLogisticsWeight)) {
              RowLogisticsWeight = RowLogisticsWeight.setScale(2, RoundingMode.HALF_UP); // 保留2位小数并进行四舍五入
              detailInfo.setRowLogisticsWeight(RowLogisticsWeight); // 合计物流重量
            }
          } else if (ObjectUtil.isEmpty(logisticsWeightTon) && ObjectUtil.isNotEmpty(rowLogisticsWeight)) {
            // 物流重量毛重为空和小计物流毛重不为空时，物流毛重通过小计物流毛重反算
            detailInfo.setLogisticsWeightTon(B.div(rowLogisticsWeight, detailInfo.getQuantityOrder()));
            detailInfo.setRowLogisticsWeight(rowLogisticsWeight);
          } else if (ObjectUtil.isNotEmpty(logisticsWeightTon) && ObjectUtil.isEmpty(rowLogisticsWeight)) {
            // 物流毛重不为空和小计物流毛重为空时，小计物流毛重通过物流毛重计算
            detailInfo.setLogisticsWeightTon(logisticsWeightTon);
            detailInfo.setRowLogisticsWeight(B.mul(logisticsWeightTon, detailInfo.getQuantityOrder()));
          } else if (ObjectUtil.isNotEmpty(logisticsWeightTon) && ObjectUtil.isNotEmpty(rowLogisticsWeight)) {
            // 物流毛重和小计物流毛重都不为空时，直接赋值
            detailInfo.setLogisticsWeightTon(logisticsWeightTon);
            detailInfo.setRowLogisticsWeight(rowLogisticsWeight);
          }
          //#endregion

          //#region 体积计算
          if (ObjectUtil.isEmpty(unitCube) && ObjectUtil.isEmpty(rowCube)) {
            // 单位体积和小计体积都为空时，默认商品信息单位体积
            detailInfo.setUnitCube(baseProduct.getUnitCube()); // 单位体积
            detailInfo.setRowCube(B.mul(baseProduct.getUnitCube(), detailInfo.getQuantityOrder())); // 合计体积
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
              detailInfo.setPaiQty(B.div(quantityOrder, unitPackage));
            } else {
              detailInfo.setUnitPackage(Convert.toStr(BigDecimal.ZERO));
              detailInfo.setPaiQty(BigDecimal.ZERO);
            }
          }
          // 计算金额
          // 客户信息
          var clientInfo = baseClientService.getById(orderInfo.getClientId());
          if (ObjectUtil.isNotNull(clientInfo)) {
//          rate = B.div(clientInfo.getRate(), hundred); // 税率小数点除100
            ratePrice = B.mul(salePrice, clientInfo.getRate());
            detailInfo.setRate(clientInfo.getRate());
            detailInfo.setRatePrice(B.add(ratePrice, salePrice)); // 含税单价
            detailInfo.setRateAmount(B.mul(quantityOrder, detailInfo.getRatePrice())); // 含税金额
            detailInfo.setSaleAmount(B.mul(quantityOrder, salePrice)); // 成本金额
            BigDecimal RowNetWeight = BigDecimal.ZERO;
            RowNetWeight = B.mul(baseProduct.getNetWeight(), quantityOrder);
            if (ObjectUtil.isNotNull(RowNetWeight)) {
              RowNetWeight = RowNetWeight.setScale(2, RoundingMode.HALF_UP); // 保留2位小数并进行四舍五入
              detailInfo.setRowNetWeight(RowNetWeight); // 小计净重
            }
          } else {
            detailInfo.setRate(BigDecimal.ZERO);
            detailInfo.setRatePrice(BigDecimal.ZERO); // 含税单价
            detailInfo.setRateAmount(BigDecimal.ZERO); // 含税金额
            detailInfo.setSaleAmount(BigDecimal.ZERO); // 成本金额
            detailInfo.setRowNetWeight(BigDecimal.ZERO); // 小计净重
          }
          //#endregion

          //建立关系，设置主表ID
          detailInfo.setOrderId(orderInfo.getOrderId());
          if (B.isGreater(new BigDecimal("0.01"), detailInfo.getRowWeight())) {
            detailInfo.setRowWeight(new BigDecimal("0.01"));
          }
          if (B.isGreater(new BigDecimal("0.01"), detailInfo.getRowNetWeight())) {
            detailInfo.setRowNetWeight(new BigDecimal("0.01"));
          }
          outOrderDetailService.save(detailInfo);

          OutSortingRule outSortingRule = new OutSortingRule();
          outSortingRule.setOrderId(orderInfo.getOrderId());
          outSortingRule.setOrderCode(orderInfo.getOrderCode());
          outSortingRule.setOrderDetailId(detailInfo.getOrderDetailId());
          outSortingRule.setProductId(detailInfo.getProductId());
          outSortingRule.setProductCode(detailInfo.getProductCode());
          outSortingRule.setProduceDateGt(Convert.toDate(detail.get("produceDate")));
          outSortingRule.setProjectCode(Convert.toStr(detail.get("projectCode")));
          outSortingRule.setCaseNumber(Convert.toStr(detail.get("caseNumber")));
          outSortingRuleService.save(outSortingRule);
          orderDetailList.add(detailInfo);
        }
        // 主表求和字段计算

        var totalNetWeight = orderDetailList.stream().map(OutOrderDetail::getRowNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (ObjectUtil.isNotNull(totalNetWeight)) {
          orderInfo.setTotalNetWeight(totalNetWeight.setScale(2, RoundingMode.HALF_UP));
        }

        var totalQuantity = orderDetailList.stream().map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalQuantityOrder(totalQuantity);

        var totalWeight = orderDetailList.stream().map(OutOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (ObjectUtil.isNotNull(totalWeight)) {
          orderInfo.setTotalWeight(totalWeight.setScale(2, RoundingMode.HALF_UP));
        }

        var totalLogisticsWeight = orderDetailList.stream().map(OutOrderDetail::getRowLogisticsWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (ObjectUtil.isNotNull(totalLogisticsWeight)) {
          orderInfo.setTotalLogisticsWeight(totalLogisticsWeight.setScale(2, RoundingMode.HALF_UP));
        }

        var totalAmount = orderDetailList.stream().map(OutOrderDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalAmount(totalAmount);
        var totalRateAmount = orderDetailList.stream().map(OutOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalRateAmount(totalRateAmount);
        var totalCube = orderDetailList.stream().map(OutOrderDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTotalCube(totalCube);
        var bigQtyTotal = orderDetailList.stream().map(OutOrderDetail::getBigQty).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setBigQtyTotal(bigQtyTotal);
        var taxAmount = orderDetailList.stream().map(OutOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderInfo.setTaxAmount(taxAmount);

        // 去重
        Set<String> uniqueSourceDetailIds = new HashSet<>();
        for (var orderDetail : orderDetailList) {
          uniqueSourceDetailIds.add(orderDetail.getSourceDetailId());
        }
        String sourceDetailIdCode = String.join(",", uniqueSourceDetailIds);
        orderInfo.setStoreOrderCode(sourceDetailIdCode); // 店铺订单号


        // 更新主表字段
        this.getBaseMapper().updateById(orderInfo);
        successCount++;
      }

      var endDate = DateUtil.date();
      var totalSeconds = DateUtil.between(startDate, endDate, DateUnit.SECOND);

      // 更新日志
      String remark = StringUtils.format("导入完成，新增{}条，导入成功,共耗时{}秒", successCount, totalSeconds);
      sysOperLogService.updateRemark(key, remark);
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

  //#region incorprationOrder
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> incorprationOrder(Map<String, Object> map) {
    String selectOrderCode = Convert.toStr(map.get("selectOrderCode"));
    Long[] ids = Convert.toLongArray(map.get("ids"));

    OutOrder outOrder = this.getByCode(selectOrderCode);
    Assert.isTrue(ObjectUtil.isNotEmpty(outOrder), "未找到对应的出库单");
    String oldOrderCode = ""; // 旧单的编号

    for (Long id : ids) {
      //把明细信息转到选中的单据中
      LambdaUpdateWrapper<OutOrderDetail> outOrderDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      outOrderDetailLambdaUpdateWrapper.set(OutOrderDetail::getOrderId, outOrder.getOrderId())
        .eq(OutOrderDetail::getOrderId, id);
      outOrderDetailService.update(outOrderDetailLambdaUpdateWrapper);
      OutOrder oldOutOrder = this.getById(id);
      if (!B.isEqual(oldOutOrder.getOrderCode(), selectOrderCode)) {
        // 旧单轨迹
        outOrderStatusHistoryService.AddHistory(oldOutOrder, OutOperationTypeEnum.INCORPORATION_ORDER, OutOrderStatusEnum.FULFILLMENT_INCORPORATION, selectOrderCode);
        // 旧单的编号
        oldOrderCode = oldOutOrder.getOrderCode();
        this.updateOrderStatus(id, OutOrderStatusEnum.FULFILLMENT_INCORPORATION); // 已合并
      }

    }

    StringBuilder remark = new StringBuilder();
    for (Long id : ids) {
      if (remark.length() > 0) {
        remark.append(",");
      }
      OutOrder oldOutOrder = this.getById(id);
      if (!B.isEqual(oldOutOrder.getOrderCode(), selectOrderCode)) {
        remark.append(oldOutOrder.getOrderCode());
      }

    }
    // 生成新单的轨迹
    outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.INCORPORATION_ORDER, OutOrderStatusEnum.AUDIT_WAITING, oldOrderCode);

    //从新计算主表信息
    List<OutOrderDetail> outOrderDetails = outOrderDetailService.selectListByMainId(outOrder.getOrderId());
    BigDecimal totalQuantityOrder = outOrderDetails.stream().map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalAmount = outOrderDetails.stream().map(OutOrderDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal discountAmount = outOrderDetails.stream().filter(f -> B.isGreater(f.getDiscountAmount())).map(OutOrderDetail::getDiscountAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal taxAmount = outOrderDetails.stream().filter(f -> B.isGreater(f.getRateAmount())).map(OutOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalCanceled = outOrderDetails.stream().filter(f -> B.isGreater(f.getRateAmount())).map(OutOrderDetail::getRateAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalWeight = outOrderDetails.stream().filter(f -> B.isGreater(f.getRowWeight())).map(OutOrderDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalCube = outOrderDetails.stream().filter(f -> B.isGreater(f.getRowCube())).map(OutOrderDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal bigQty = outOrderDetails.stream().filter(f -> B.isGreater(f.getBigQty())).map(OutOrderDetail::getBigQty).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalNetWeight = outOrderDetails.stream().filter(f -> B.isGreater(f.getRowNetWeight())).map(OutOrderDetail::getRowNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalLogisticsWeight = outOrderDetails.stream().filter(f -> B.isGreater(f.getRowLogisticsWeight())).map(OutOrderDetail::getRowLogisticsWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

    outOrder.setTotalNetWeight(totalNetWeight);
    outOrder.setTotalLogisticsWeight(totalLogisticsWeight);
    outOrder.setTotalQuantityOrder(totalQuantityOrder);
    outOrder.setTotalAmount(totalAmount);
    outOrder.setDiscountAmount(discountAmount);
    outOrder.setTaxAmount(taxAmount);
    outOrder.setTotalCanceled(totalCanceled);
    outOrder.setTotalWeight(totalWeight);
    outOrder.setTotalCube(totalCube);
    outOrder.setBigQtyTotal(bigQty);
    outOrder.setTotalUnpaid(B.sub(outOrder.getTaxAmount(), outOrder.getDiscountAmount()));

    this.updateById(outOrder);

    return R.ok("合并成功");
  }
  //#endregion

  //#region 一键出库获取数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<List<OutOrderMainAndDetailVo>> getOrderOuterDetails(Map<String, Object> map) {

    // 查询字段
    String selectFields = "orderDetailId,unitConvert,productName,productModel,quantityOrder,smallUnit,bigUnit," +
      "storageName,storageId";

    // 求和字段
    String sumFields = "quantityOrder";

    // 分组字段
    String groupFields = "orderDetailId";
    // 构建联表查询
    MPJLambdaWrapper<OutOrderDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .selectAll(OutOrderDetail.class)
      .select(OutOrder::getOrderCode)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId);
    if (ObjectUtil.isNotNull(map.get("ids"))) {

      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
//      List<Long> ids = new ArrayList<>(Arrays.asList(Convert.toLong(map.get("ids"))));
      wrapper.in(OutOrderDetail::getOrderId, ids);
    } else {
      wrapper.eq(OutOrderDetail::getOrderId, Convert.toLong(map.get("orderId")));
    }

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, OutOrderDetail.class, OutOrder.class);
    List<OutOrderMainAndDetailVo> inventoryComposeVoList = outOrderDetailService.selectJoinList(OutOrderMainAndDetailVo.class, wrapper);


    return R.ok(inventoryComposeVoList);
  }
  //#endregion

  //#region 一键出库
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> quickOut(OutScanMainBo outScanMainBo) {
    LiteflowResponse response = flowExecutor.execute2Resp("quickOutChain", outScanMainBo, QuickOutContext.class);
    Assert.isFalse(!response.isSuccess(), response.getMessage());

    return R.ok("出库成功");
  }
  //#endregion

  //#region 批量出库
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> batchOut(Map<String, Object> map) {
    IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);

    InventorySourceTypeEnum scanInType = InventorySourceTypeEnum.valueOf(Convert.toStr(map.get("scanInType")));
    String[] ids = StringUtils.split(map.get("idList").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    Date deliveryDate = Convert.toDate(map.get("deliveryDate"));

    for (long orderId : _ids) {
      // 出库单
      OutOrder orderOrder = this.baseMapper.selectById(orderId);

      //#region 日期处理
      if (ObjectUtil.isNotNull(deliveryDate) && ObjectUtil.isNotNull(orderOrder.getDeliveryDate()) && !DateUtil.isSameDay(orderOrder.getDeliveryDate(), deliveryDate)) {
        BaseStorage storageInfo = baseStorageService.getById(orderOrder.getStorageId());
        Date date1 = calculateDate(deliveryDate, orderOrder, storageInfo, false);
        orderOrder.setDeliveryDate(date1);
        orderOrder.setArriveDate(date1);
        LambdaQueryWrapper<BaseClientAddress> clientAddressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clientAddressLambdaQueryWrapper.eq(BaseClientAddress::getClientId, orderOrder.getClientId())
          .eq(BaseClientAddress::getAddress, orderOrder.getShippingAddress())
          .last("limit 1");
        BaseClientAddress clientAddress = baseClientAddressService.getOne(clientAddressLambdaQueryWrapper);
        sysImportService.isAssert(ObjectUtil.isNull(clientAddress), "没有查询到客户地址，请检查客户：【" + orderOrder.getClientShortName() + "】的地址：【" + orderOrder.getShippingAddress() + "】是否已存在！");
        //查询线路信息
        LambdaQueryWrapper<TmsLine> tmsLineLambdaQueryWrapper = new LambdaQueryWrapper<TmsLine>();
        tmsLineLambdaQueryWrapper.eq(TmsLine::getDistributionSiteId, storageInfo.getSiteId())
          .eq(TmsLine::getUnloadSiteId, clientAddress.getSiteId())
          .last("limit 1");
        TmsLineVo lineInfo = tmsLineService.selectOne(tmsLineLambdaQueryWrapper);
        if (ObjectUtil.isNotEmpty(lineInfo)) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date1);
          BigDecimal prescription = StrUtil.isNotEmpty(lineInfo.getPrescription()) ? new BigDecimal(lineInfo.getPrescription()) : BigDecimal.ZERO;
          if (B.isGreaterOrEqual(prescription, new BigDecimal(10L))) {
            calendar.add(Calendar.DAY_OF_MONTH, (int) Math.ceil(Convert.toDouble(B.div(prescription, new BigDecimal(24)))));
          }
          orderOrder.setArriveDate(calendar.getTime());
        }
        this.getBaseMapper().updateById(orderOrder);
      }
      //#endregion

      OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(orderOrder, OutScanMainBo.class);
      newOutScanOrderBo.setScanInType(scanInType);
      newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.OUT_ORDER_NORMAL);
      // 出库单明细
      List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(orderOrder.getOrderId()); // 订单明细集合
      newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
      for (var outOrderDetail : outOrderDetailList) {
        OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
        outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
        newOutScanOrderBo.getDataList().add(outScanDetailBo);
      }
      newOutScanOrderBo.setDeliveryDate(deliveryDate);
      outScanOrderService.normalOutSave(newOutScanOrderBo);
    }
    return R.ok("出库成功");
  }
  //#endregion

  //#region 强制完成
  @Override
  public R<Void> forceFinish(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);
    for (Long orderId : _ids) {
      OutOrder orderInfo = this.getById(orderId);
      Assert.isTrue(ObjectUtil.isNotEmpty(orderInfo), "未找到出库单，请刷新数据信息");
      Assert.isTrue(StrUtil.equals(orderInfo.getPackageStatus(), OutOrderStatusEnum.PACKAGE_PARTIAL.getName()), "单据状态不是部分打包不允许操作");

      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_ALLOCATE); //类别
      rabbitReceiverDto.setBillId(orderId);
      rabbitReceiverDto.setBillCode(orderInfo.getOrderCode());
      rabbitReceiverDto.setSourceCode(orderInfo.getSourceCode());
      rabbitReceiverDto.setSourceId(orderInfo.getSourceId());
      taskQueueService.createTask(rabbitReceiverDto);
      //#endregion
      //修改状态
      this.updateOrderStatus(orderId, OutOrderStatusEnum.FORCE_FINISH);
      //出库单添加轨迹
      outOrderStatusHistoryService.AddHistory(orderInfo, OutOperationTypeEnum.FORCE_FINISH, OutOrderStatusEnum.FORCE_FINISH);

    }
    return R.ok("执行成功，请去消息队列查看具体信息");
  }

  @Override
  public R<Map<String, Object>> getOutIds(Map<String, Object> map) {

    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
    // 构建联表查询
    MPJLambdaWrapper<OutOrderWaveDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .select(OutOrderWaveDetail::getOrderId)
      .select(OutOrderWave::getExpressCorpName)
      .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
      .groupBy("order_id,express_corp_name");
    if (ObjectUtil.isNotEmpty(ids)) {
      wrapper.in(OutOrderWaveDetail::getOrderWaveId, ids);
    } else if (ObjectUtil.isNotEmpty(map.get("orderWaveCode"))) {
      wrapper.in(OutOrderWave::getOrderWaveCode, map.get("orderWaveCode"));
    }

    List<OutOrderWaveDetailSendBatchVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailSendBatchVo.class, wrapper);


    //      for (OutOrderWaveDetailSendBatchVo item : inventoryComposeVoList) {
    //        if(ObjectUtil.isEmpty(item.getExpressCorpName())){
    //          // 查询快递公司
    //                LambdaQueryWrapper<BaseExpressCorp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    //          lambdaQueryWrapper
    //                .eq(BaseExpressCorp::getExpressCorpName,item.getExpressCorpName());
    //
    //          BaseExpressCorp baseExpressCorp = baseExpressCorpService.getOne(lambdaQueryWrapper);
    //        }
    //      }

    Map<String, Object> result = new HashMap<>();

    result.put("dataList", inventoryComposeVoList);
    return R.ok(result);

  }
  //#endregion

  //#region getOutAndExpress
  @Override
  public R<Map<String, Object>> getOutAndExpress(Map<String, Object> map) {
    List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong); // 波次ID集合

    // 构建联表查询
    MPJLambdaWrapper<OutOrderWaveDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .select(OutOrderWaveDetail::getOrderId)
      .select(OutOrderWave::getExpressCorpName)
      .select(OutOrderWave::getOrderWaveId)
      .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
      .in(OutOrderWaveDetail::getOrderWaveId, ids)
      .groupBy("order_id,express_corp_name,order_wave_id");

    List<OutOrderWaveDetailSendBatchVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailSendBatchVo.class, wrapper);

    List<OutOrderAndExpressVo> orderAndExpressList = new ArrayList<>();
    for (OutOrderWaveDetailSendBatchVo item : inventoryComposeVoList) {
      if (ObjectUtil.isNotEmpty(item.getExpressCorpName())) {
        OutOrderAndExpressVo detailDto = new OutOrderAndExpressVo();
        // 查询快递公司
        LambdaQueryWrapper<BaseExpressCorp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
          .eq(BaseExpressCorp::getExpressCorpName, item.getExpressCorpName());
        BaseExpressCorp baseExpressCorp = baseExpressCorpService.getOne(lambdaQueryWrapper);

        // 查询打印模版信息
        LambdaQueryWrapper<SysPrintTemplate> templateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        templateLambdaQueryWrapper
          .eq(SysPrintTemplate::getTemplateName, baseExpressCorp.getFaceBillTemplate())
          .last("limit 1");

        SysPrintTemplate sysPrintTemplate = sysPrintTemplateService.getOne(templateLambdaQueryWrapper);

        if (ObjectUtil.isEmpty(sysPrintTemplate)) {
          throw new ServiceException("未找到对应的模版");
        }
        detailDto.setMenuId(sysPrintTemplate.getMenuId());
        detailDto.setOrderId(item.getOrderId());
        detailDto.setExpressCorpName(item.getExpressCorpName());
        detailDto.setPrintTemplateId(sysPrintTemplate.getPrintTemplateId());
        orderAndExpressList.add(detailDto);

      }
    }

    Map<String, Object> result = new HashMap<>();

    result.put("dataList", orderAndExpressList);
    return R.ok(result);

  }
  //#endregion

  @Override
  public R<OutOrderPrintVo> selectOutPrint(List<QueryBo> queryBoList) {
    List<OutOrderWaveDetailPrintVo> list = new ArrayList<>();
    OutOrderPrintVo outOrderPrintVo = new OutOrderPrintVo();
    queryBoList.forEach(
      item -> {
        String column = item.getColumn();
        String values = item.getValues();

        if (ObjectUtil.equals(column, "order_Id")) {
          OutOrder outOrder = this.getById(values);

          BeanUtil.copyProperties(outOrder, outOrderPrintVo);
          outOrderPrintVo.setTwoDimensionCode(outOrder.getOrderCode());

        }


      });

    return R.ok(outOrderPrintVo);
  }

  //#region 更新物流信息
  @Override
  public R<Void> updateLogistics(Map<String, Object> map) {

    if (ObjectUtil.isNotEmpty(map.get("orderId"))) {
      // 更新物流信息
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper
        .set(OutOrder::getShippingName, map.get("shippingName"))
        .set(OutOrder::getShippingAddress, map.get("shippingAddress"))
        .set(OutOrder::getProvinceName, map.get("provinceName"))
        .set(OutOrder::getProvinceId, map.get("provinceId"))
        .set(OutOrder::getExpressCorpType, map.get("expressCorpType"))
        .set(OutOrder::getTelephone, map.get("telephone"))
        .set(OutOrder::getPostCode, map.get("postCode"))
        .set(OutOrder::getExpressCorpName, map.get("expressCorpName"))
        .set(OutOrder::getLineName, map.get("lineName"))
        .set(OutOrder::getLineId, map.get("lineId"))
        .set(OutOrder::getMobile, map.get("mobile"))
        .set(OutOrder::getCountryName, map.get("countryName"))
        .set(OutOrder::getCountryId, map.get("countryId"))
        .set(OutOrder::getExpressCode, map.get("expressCode"))
        .set(OutOrder::getCityId, map.get("cityId"))
        .set(OutOrder::getCityName, map.get("cityName"))
        .set(OutOrder::getRegionId, map.get("regionId"))
        .set(OutOrder::getRegionName, map.get("regionName"))
        .set(OutOrder::getDistributionType, map.get("distributionType"))
        .eq(OutOrder::getOrderId, map.get("orderId"));
      this.update(orderLambdaUpdateWrapper);

      OutOrder outOrder = this.baseMapper.selectById(Convert.toLong(map.get("orderId")));

      // 找到最新一条的状态轨迹
      LambdaQueryWrapper<OutOrderStatusHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(OutOrderStatusHistory::getBillId, map.get("orderId"))
        .orderByDesc(OutOrderStatusHistory::getHistoryId)
        .last("limit 1");
      var orderStatusHistory = outOrderStatusHistoryService.getOne(lambdaQueryWrapper);


      OutOrderStatusEnum toStatus = OutOrderStatusEnum.matchingEnum(orderStatusHistory.getToStatus());
      outOrder.setOrderStatus(orderStatusHistory.getFromStatus());
      // 生成开启出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_UPDATE_LOGISTICS, toStatus);
    }
    return R.ok("处理完成");
  }

  @Override
  public Date calculateDate(Date dateTime, OutOrder orderInfo, BaseStorage storageInfo, Boolean flag) {

    //#region 判断日期是否为节假日 如果是需要跳过
    SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
    if (ObjectUtil.isNull(dateTime)) {
      dateTime = new Date();
    }
    if (flag) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dateTime);
      calendar.add(Calendar.DAY_OF_YEAR, 1);
      dateTime = calendar.getTime();
    }
    //配送日期如果当天或者第二天是节假日，需要把配送日期往后延迟节假日放假天数
    String results = HttpUtil.get("http://timor.tech/api/holiday/year/" + formatter.format(dateTime));
    if (ObjectUtil.isNotNull(results)) {
      HashMap jsonData = JsonUtils.parseObject(results, HashMap.class);
      if (!ObjectUtil.isNull(jsonData)) {
        //如果 出现限流提示则把上一次保存后日期进行计算
        if (B.isEqual(Convert.toLong(jsonData.get("code")), 249)) {
          dateTime = date;
        } else if (B.isEqual(Convert.toLong(jsonData.get("code")), 0) && !StrUtil.equals(Convert.toStr(jsonData.get("holiday")), "{}")) {

          //取值转成map类型
          Map<String, Object> holiday = BeanUtil.copyProperties(jsonData.get("holiday"), Map.class);
          if (B.isGreater(holiday.size())) {
            //如果 对象中target有内容 说明当前日期是调休状态
            holiday = holiday.entrySet().stream().filter(k -> StrUtil.isEmpty(Convert.toStr(BeanUtil.copyProperties(k.getValue(), Map.class).get("target")))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            //假期
            //当前日期
            formatter = new SimpleDateFormat("MM-dd");
            for (int k = 0; k <= holiday.size(); k++) {
              //后一天
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(dateTime);
              calendar.add(Calendar.DATE, 1); // 在当前日期上加一天
              //如果当前日期  或者 后一天存在  则循环继续 直到找不到为止
              if (holiday.containsKey(formatter.format(dateTime)) || holiday.containsKey(formatter.format(calendar.getTime()))) {
                dateTime = calendar.getTime();//找到了则把加一天后的时间赋值给dateTime;
                continue;
              }
              break;
            }
          }

        }
      }

    }
    //把计算后的时间保存一下 防止接口限流 导致查不到日期信息
    date = dateTime;
    // 添加天数后的日期
    //#endregion

    return dateTime;
  }


  public R<List<Map<String, Object>>> getStatisticData(Map<String, Object> maps) {
    String tenantId = Convert.toStr(maps.get("tenantId"));
    Long userId = Convert.toLong(maps.get("userId"));

    if (StrUtil.isEmpty(tenantId)) {
      tenantId = TenantConstants.DEFAULT_TENANT_ID;
    }

    List<Map<String, Object>> data = new ArrayList<>();
    Map<String, Object> mapData = new HashMap<>();

    //#region 拣货统计
    //统计 今日拣货数量
    String sql = StringUtils.format("""
      SELECT IFNULL(sum(total_quanity_order),0) as today_total FROM out_order_picking where  DATE(create_time) = DATE(NOW()) and  tenant_id ='{}' ;
       """, tenantId);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long pickingTotal = Convert.toLong(dataInfo.get("todayTotal")); //今日进货数量
    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "今日拣货");
    mapData.put("value", pickingTotal);
    mapData.put("unit", "件");

    //统计 昨日拣货数量
    sql = StringUtils.format("""
      SELECT IFNULL(sum(total_quanity_order),0) as yesterday_total FROM out_order_picking where TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);

    Long yesterdayPickingTotal = Convert.toLong(dataInfo.get("yesterdayTotal")); //今日进货数量
    mapData.put("title", "昨日拣货量");
    mapData.put("number", yesterdayPickingTotal);
    data.add(mapData); //添加统计数据


    //统计 今日拣货率
    sql = StringUtils.format("""
      SELECT IFNULL(sum(quantity_order),0) as out_total,IFNULL(sum(quantity_outed),0) as outed_total FROM out_order_detail where DATE(create_time) = DATE(NOW())  and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long outTotal = Convert.toLong(dataInfo.get("outTotal")); //今日出库数量
    Long outedTotal = Convert.toLong(dataInfo.get("outedTotal")); //今日 出库完成数量
    //计算占比
    BigDecimal percentage = BigDecimal.ZERO;
    if (B.isGreater(pickingTotal) && B.isGreater(outTotal)) {
      percentage = B.div(pickingTotal, outTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "拣货完成率");
    mapData.put("value", percentage);
    mapData.put("unit", "%");

    //昨日拣货率
    sql = StringUtils.format("""
      SELECT IFNULL(sum(quantity_order),0) as out_total,IFNULL(sum(quantity_outed),0) as outed_total FROM out_order_detail where TO_DAYS(NOW()) - TO_DAYS(create_time) = 1  and  tenant_id ='{}'  ;
       """, tenantId);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long yesterdayOutTotal = Convert.toLong(dataInfo.get("outTotal")); //昨日出库数量
    Long yesterdayOutedTotal = Convert.toLong(dataInfo.get("outedTotal")); //昨日 出库完成数量
    //计算占比
    percentage = BigDecimal.ZERO;
    if (B.isGreater(yesterdayPickingTotal) && B.isGreater(yesterdayOutTotal)) {
      percentage = B.div(yesterdayPickingTotal, yesterdayOutTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData.put("title", "昨日拣货率");
    mapData.put("number", percentage);
    data.add(mapData); //添加统计数据

    //#endregion

    //#region 出库统计
    //  出库统计 上面已经取值过 则不用再取值
    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "今日出库");
    mapData.put("value", outTotal);
    mapData.put("unit", "件");
    mapData.put("title", "昨日出库量");
    mapData.put("number", yesterdayOutTotal);
    data.add(mapData); //添加统计数据

    mapData = new HashMap<>(); //重新定义Map
    mapData.put("label", "出库完成率");
    percentage = BigDecimal.ZERO;
    if (B.isGreater(outedTotal) && B.isGreater(outTotal)) {
      percentage = B.div(outedTotal, outTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData.put("value", percentage);
    mapData.put("unit", "件");
    mapData.put("title", "昨日出库量");

    percentage = BigDecimal.ZERO;
    if (B.isGreater(yesterdayOutTotal) && B.isGreater(yesterdayOutedTotal)) {
      percentage = B.div(yesterdayOutTotal, yesterdayOutedTotal);
      percentage = B.mul(percentage, 100);
    }
    mapData.put("number", percentage);
    data.add(mapData); //添加统计数据

    //出库完成率
    //#endregion

    return R.ok(data);
  }

  //#region getByStoreOrder 根据店铺订单号获取
  @Override
  public OutOrder getByStoreOrder(String storeOrderCode) {
    LambdaQueryWrapper<OutOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper
      .eq(OutOrder::getStoreOrderCode, storeOrderCode);
    return this.getOnly(lambdaQueryWrapper);
  }

  //#endregion

  //#region 根据来源单号获取
  @Override
  public OutOrder getBySourceCode(String sourceCode) {
    LambdaQueryWrapper<OutOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper
      .eq(OutOrder::getSourceCode, sourceCode);
    return this.getOnly(lambdaQueryWrapper);
  }

  @Override
  public R<Void> compulsoryAccomplish(List<Long> ids) {
    List<OutOrder> orders = this.listByIds(ids);
    List<String> status = new ArrayList<>();
    status.add(OutOrderStatusEnum.AUDIT_SUCCESS.getName());

    var finList = orders.stream().filter(item -> B.isGreater(status.stream().filter(i -> B.isEqual(i, item.getOrderStatus())).toList().size())).toList();
    if (ObjectUtil.isNull(finList) || !B.isEqual(finList.size(), orders.size())) {
      throw new ServiceException("只有【审核成功】的的单据才允许 强制完成");
    }
    for (OutOrder orderInfo : orders) {
      this.updateOrderStatus(orderInfo.getOrderId(), OutOrderStatusEnum.PACKAGE_FINISHED);

      outOrderStatusHistoryService.AddHistory(orderInfo, OutOperationTypeEnum.COMPULSORY_CCOMPLISH, OutOrderStatusEnum.PACKAGE_FINISHED);
    }
    //强制完成把数量 合计为0
    LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(OutOrder::getTotalQuantityOrder, BigDecimal.ZERO)
      .in(OutOrder::getOrderId, ids);
    this.update(updateWrapper);

    LambdaUpdateWrapper<OutOrderDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    detailLambdaUpdateWrapper.set(OutOrderDetail::getQuantityOrder, BigDecimal.ZERO)
      .in(OutOrderDetail::getOrderId, ids);
    outOrderDetailService.update(detailLambdaUpdateWrapper);

    return R.ok("强制完成执行成功");
  }

  @Override
  public R<Map<String, Object>> mergePrintList(List<QueryBo> queryBos) {
    Assert.isFalse(CollUtil.isEmpty(queryBos), "打印单据ID至少选择一条");

    Map<String, Object> printMap = new HashMap<>();
    String[] mainIdList = StringUtils.split(queryBos.get(0).getValues(), ",");

    OutOrderVo outOrderVo = this.selectById(Convert.toLong(mainIdList[0]));
    // 获取主表的数据
    Map<String, Object> mainInfo = BeanUtil.beanToMap(outOrderVo);

    LambdaQueryWrapper<OutOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(OutOrderDetail::getOrderId, mainIdList);

    var detailList = outOrderDetailService.list(queryWrapper);

    Map<Long, List<OutOrderDetail>> groupList = detailList.stream().collect(Collectors.groupingBy(OutOrderDetail::getProductId));
    List<Map<String, Object>> data = new ArrayList<>();
    //分组 并且合并数量
    for (var item : groupList.entrySet()) {
      var quantityOrder = item.getValue().stream().map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
      var bigQty = item.getValue().stream().map(OutOrderDetail::getBigQty).reduce(BigDecimal.ZERO, BigDecimal::add);
      Map<String, Object> detailInfo = BeanUtil.beanToMap(item.getValue().get(0));
      detailInfo.put("quantityOrder", quantityOrder);
      detailInfo.put("bigQty", bigQty);
      data.add(detailInfo);
    }

    IntStream.range(0, data.size()).forEach(i -> data.get(i).put("index", i));

    // 明细数据
    mainInfo.put("out_order_detail", data);
    printMap.put("dataInfo", mainInfo);
    printMap.put("isCustomData", true);

    return R.ok(printMap);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> quickOutList(OutScanMainBo outScanMainBo) {

    //因为多单一起出库 则需要根据 出库单ID分组 一单一单一键出库
    Map<Long, List<OutScanDetailBo>> groupDetails = outScanMainBo.getDataList().stream().collect(Collectors.groupingBy(OutScanDetailBo::getOrderId));

    for (var group : groupDetails.entrySet()) {
      OutScanDetailBo outScanDetailBo = group.getValue().get(0);
      OutScanMainBo info = new OutScanMainBo();
      info.setOrderId(group.getKey());
      info.setOrderCode(outScanDetailBo.getOrderCode());
      info.setScanInType(InventorySourceTypeEnum.PC_QUICK_OUT);
      info.setDataList(group.getValue());
      info.setSpillover(outScanMainBo.getSpillover());

      this.quickOut(info);

    }

    return R.ok("出库成功");
  }
  //#endregion
}



