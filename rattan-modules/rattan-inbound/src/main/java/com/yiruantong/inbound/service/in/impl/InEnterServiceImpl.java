package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.*;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.domain.in.bo.InEnterBo;
import com.yiruantong.inbound.domain.in.vo.InCreateShelveVo;
import com.yiruantong.inbound.domain.in.vo.InEnterVo;
import com.yiruantong.inbound.mapper.in.InEnterMapper;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inbound.tool.IShelveRecommendPositionService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.helper.RecommendPositionDto;
import com.yiruantong.inventory.service.core.ICoreInventoryHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.yiruantong.common.core.utils.StringUtils.splitTo;

/**
 * 入库管理Service业务层处理
 *
 * @author YRT
 */
@RequiredArgsConstructor
@Service
public class InEnterServiceImpl extends ServiceImplPlus<InEnterMapper, InEnter, InEnterVo, InEnterBo> implements IInEnterService {

  private final ICoreInventoryService coreInventoryService;
  private final ICoreInventoryHistoryService coreInventoryHistoryService;
  private final IInShelveService inShelveService;
  private final IInEnterDetailService inEnterDetailService;
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IShelveRecommendPositionService shelveRecommendPositionService;
  private final IRecommendPositionService recommendPositionService;
  private final IInShelveDetailService inShelveDetailService;
  private final IInEnterStatusHistoryService inEnterStatusHistoryService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final ITaskQueueService taskQueueService;

  //#region 列表加载前事件

  /**
   * 列表加载前事件
   *
   * @param pageQuery 加载参数
   */
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    pageQuery.getQueryBoList().stream().filter(f -> f.getQueryType() == QueryTypeEnum.CUSTOM).forEach(f -> {
      if (f.getColumn().equals("productCode")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT enter_id FROM in_enter_detail d WHERE d.enter_id=in_enter.enter_id AND d.product_code='{}'", f.getValues()));
      }
      //如果还有其他查询条件 则继续加else if

    });
    super.beforePageQuery(pageQuery);
  }
  //#endregion

  //#region 生成上架单列表查询

  /**
   * 生成上架单列表查询
   *
   * @return 返回生成上架单列表查询
   */
  @Override
  public TableDataInfo<InCreateShelveVo> selectCreateShelveList(PageQuery pageQuery) {
    IPage ipage = pageQuery.build();

    MPJLambdaWrapper<InEnterDetail> wrapper = new MPJLambdaWrapper<InEnterDetail>()
      .selectAll(InEnterDetail.class)
      .selectAll(InEnter.class)
      .innerJoin(InEnter.class, InEnter::getEnterId, InEnterDetail::getEnterId)
//      .gt(InEnterDetail::getEnterQuantity, e->"IFNULL(t.shelved_quantity ,0)")
      .apply("t.enter_quantity > IFNULL(t.shelved_quantity ,0) and t1.enter_status <> '" + InEnterStatusEnum.CANCEL.getName() + "'")
      .notExists("SELECT enter_detail_id FROM in_shelve_detail AS SL WHERE enter_detail_id = t.enter_detail_id");

    IPage<InCreateShelveVo> page = inEnterDetailService.selectJoinListPage(ipage, InCreateShelveVo.class, wrapper);

    // 推荐货位
    for (var item : page.getRecords()) {
      RecommendPositionDto recommendPsoitionDto = BeanUtil.copyProperties(item, RecommendPositionDto.class);// 复制数据
      recommendPsoitionDto.setInQty(item.getEnterQuantity());
      String positionName = recommendPositionService.getRecommendPosition(recommendPsoitionDto);
      item.setShelvePositionName(positionName);
    }

    TableDataInfo<InCreateShelveVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    return tableDataInfoV;
  }
  //#endregion

  //#region 撤销入库

  /**
   * 撤销入库
   *
   * @param map 入参
   * @return R
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> cancelEnter(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);

    for (long enterId : _ids) {
      // 入库单信息
      InEnter inEnterInfo = this.getById(enterId);
      Assert.isFalse(StringUtils.equals(inEnterInfo.getOrderType(), InOrderTypeEnum.NO_BILL_FLASH.getName()), "一键闪入不允许撤销操作");

      if (ObjectUtil.isEmpty(inEnterInfo)) {
        throw new ServiceException("未获取到入库单");
      }
      LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
      // 查询
      inventoryLambdaQueryWrapper.eq(CoreInventory::getBillCode, inEnterInfo.getEnterCode());
      List<CoreInventory> inventoryList = coreInventoryService.list(inventoryLambdaQueryWrapper);

      for (CoreInventory inventoryInfo : inventoryList) {
        // 当所操作的单据对应的库存没有占位，但是库存量已经小于原始量时，需要增加提示:当前库存已部分出库，是否继续操作取消入库
        if (B.isLess(inventoryInfo.getProductStorage(), inventoryInfo.getOriginStorage())) {
          throw new ServiceException("商品编号为" + inventoryInfo.getProductCode() + "原始量与库存量不相等，不允许执行取消操作！");
        }

        // 盘点是否存在占位
        MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
          .select(CoreInventory::getInventoryId)
          .innerJoin(CoreInventoryHolder.class, CoreInventoryHolder::getInventoryId, CoreInventory::getInventoryId)
          .eq(CoreInventory::getBillCode, inEnterInfo.getEnterCode())
          .gt(CoreInventoryHolder::getHolderStorage, 0)
          .ne(CoreInventoryHolder::getSourceType, "渠道分货");

        var inventoryInfo4 = coreInventoryService.list(wrapper);
        if (CollUtil.isNotEmpty(inventoryInfo4)) {
          String productCodes = inventoryInfo4.stream().map(CoreInventory::getProductCode).distinct().collect(Collectors.joining(","));
          throw new ServiceException("商品编号为" + productCodes + "库存有占位，不允许撤销操作！");
        }
      }

      // 删除库存数据
      MPJLambdaWrapper<CoreInventory> deleteCoreInventory = new MPJLambdaWrapper<>();
      deleteCoreInventory.eq(CoreInventory::getBillCode, inEnterInfo.getEnterCode());
      coreInventoryService.deleteJoin(deleteCoreInventory);

      // 删除库存监测记录轨迹
      MPJLambdaWrapper<CoreInventoryHistory> deleteHistory = new MPJLambdaWrapper<>();
      deleteHistory.eq(CoreInventoryHistory::getBillCode, inEnterInfo.getOrderCode());
      coreInventoryHistoryService.deleteJoin(deleteHistory);

      // 删除上架单
      MPJLambdaWrapper<InShelve> deleteShelve = new MPJLambdaWrapper<>();
      deleteShelve.eq(InShelve::getEnterCode, inEnterInfo.getEnterCode());
      inShelveService.deleteJoin(deleteShelve);

      // 修改入库单主表
      LambdaUpdateWrapper<InEnter> updateInEnter = new UpdateWrapper<InEnter>().lambda();
      updateInEnter.set(InEnter::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
        .set(InEnter::getAuditor, LoginHelper.getNickname())
        .set(InEnter::getAuditDate, DateUtils.getNowDate())
        .set(InEnter::getEnterStatus, InEnterStatusEnum.CANCEL.getName()) // 取消入库
        .eq(InEnter::getEnterId, inEnterInfo.getEnterId());
      this.update(updateInEnter);//提交

      //#region 重新计算数量
//      // 修改入库单明细数据
//      LambdaUpdateWrapper<InEnterDetail> updateInEnterDetail = new UpdateWrapper<InEnterDetail>().lambda();
//      updateInEnterDetail.set(InEnterDetail::getShelvedQuantity, 0)
//        .set(InEnterDetail::getDetailStatus, InEnterStatusEnum.CANCEL.getName()) // 取消入库
//        .eq(InEnterDetail::getEnterId, inEnterInfo.getEnterId());
//      inEnterDetailService.update(updateInEnterDetail);//提交

      InEnterStatusEnum StatusEnumInfo = InEnterStatusEnum.matchingEnum(inEnterInfo.getEnterStatus());
      Assert.isFalse(ObjectUtil.isNull(StatusEnumInfo), "入库单状态不存在！");
      //生成入库记录轨迹
      inEnterStatusHistoryService.addHistoryInfo(inEnterInfo, InEnterActionEnum.PC_CANCEL_IN, StatusEnumInfo, InEnterStatusEnum.CANCEL);

      // 查询预到货主表
      InOrder inOrder = inOrderService.getByCode(inEnterInfo.getOrderCode());
      // 预到货明细
      List<InOrderDetail> inOrderDetailList = inOrderDetailService.selectListByMainId(inOrder.getOrderId());
      // 更新预到货入库数量和出库数量
      for (InOrderDetail detail : inOrderDetailList) {

        LambdaQueryWrapper<InEnterDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper
          .eq(InEnterDetail::getEnterId, inEnterInfo.getEnterId())
          .eq(InEnterDetail::getOrderId, inOrder.getOrderId())
          .eq(InEnterDetail::getOrderDetailId, detail.getOrderDetailId());
        // 入库明细
        List<InEnterDetail> inEnterDetail = inEnterDetailService.list(detailLambdaQueryWrapper);
        // 求和入库数量
        BigDecimal totalQuantity = inEnterDetail.stream().map(InEnterDetail::getEnterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 已入库数量 -   要 撤销入库的数量
        detail.setEnterQuantity(B.sub(detail.getEnterQuantity(), totalQuantity));
        detail.setShelvedQuantity(BigDecimal.ZERO);
        inOrderDetailService.getBaseMapper().updateById(detail);
      }
      //#endregion

      // 更新预到货主表合计入库数量和上架数量
      BigDecimal enterQuantity = inOrderDetailList.stream().map(InOrderDetail::getEnterQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      inOrder.setTotalEnterQuantity(enterQuantity);
      inOrder.setTotalShelvedQuantity(BigDecimal.ZERO);
      inOrderService.getBaseMapper().updateById(inOrder);

      //#region 预到货单状态处理
      BigDecimal totalShelvedQuantity = BigDecimal.ZERO;
      BigDecimal totalQuantity = Optional.ofNullable(inOrder.getTotalQuantity()).orElse(BigDecimal.ZERO);

      if (inOrderDetailList.stream().allMatch(f -> B.isGreaterOrEqual(f.getEnterQuantity(), f.getQuantity()))) {
        // 所有明细入库数量>=0明细预到货数量时，预到货单状态更新为：入库完成
        inOrderService.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.FINISHED);
        inOrderService.updateShelverStatus(inOrder.getOrderId(), InShelveStatusEnum.WAITING);

        //根据预到货单的当前状态获取对应的枚举
        InOrderStatusEnum fromOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus());
        cn.hutool.core.lang.Assert.isFalse(ObjectUtil.isNull(inOrder.getOrderStatus()), "预到货单状态不存在！");
        //添加轨迹
        inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.PC_CANCEL_IN, fromOrderStatusEnum, InOrderStatusEnum.FINISHED);
      } else if (inOrderDetailList.stream().anyMatch(f -> B.isGreater(f.getEnterQuantity()))
        && inOrderDetailList.stream().anyMatch(f -> B.isLess(f.getEnterQuantity(), f.getQuantity()))) {
        // 预到货单状态更新为：部分入库
        inOrderService.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.PARTIAL_FINISHED);
        inOrderService.updateShelverStatus(inOrder.getOrderId(), InShelveStatusEnum.WAITING);

        //添加轨迹
        InOrderStatusEnum fromInOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus()); // 开始状态
        inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.PC_CANCEL_IN, fromInOrderStatusEnum, InOrderStatusEnum.PARTIAL_FINISHED);
      } else {
        // 预到货单状态更新为：部分入库
        inOrderService.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.SUCCESS);
        inOrderService.updateShelverStatus(inOrder.getOrderId(), InShelveStatusEnum.WAITING);

        //添加轨迹
        InOrderStatusEnum fromInOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus()); // 开始状态
        inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.PC_CANCEL_IN, fromInOrderStatusEnum, InOrderStatusEnum.SUCCESS);
      }
      //#endregion

      //取消入库时 把入库单明细删除掉
      LambdaQueryWrapper<InEnterDetail> enterDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      enterDetailLambdaQueryWrapper.eq(InEnterDetail::getEnterId, inEnterInfo.getEnterId());
      List<InEnterDetail> inEnterDetails = inEnterDetailService.getBaseMapper().selectList(enterDetailLambdaQueryWrapper);
      if (ObjectUtil.isNotEmpty(inEnterDetails)) {
        Long[] detailIds = Convert.toLongArray(inEnterDetails.stream().map(item -> item.getEnterDetailId()).toArray());
        inEnterDetailService.deleteByIds(detailIds);
      }
    }

    return R.ok("入库撤销成功！");
  }
  //#endregion

  //#region 入库单状态

  /**
   * 入库单状态
   *
   * @param enterId 入库单ID
   * @return 返回上架单信息
   */
  @Override
  public boolean updateEnterStatus(Long enterId, InEnterStatusEnum status) {
    LambdaUpdateWrapper<InEnter> lambda = new UpdateWrapper<InEnter>().lambda();
    lambda.set(InEnter::getEnterStatus, status.getName())
      .set(InEnter::getAuditor, LoginHelper.getUserId())
      .set(InEnter::getAuditDate, DateUtil.date())
      .set(InEnter::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .eq(InEnter::getEnterId, enterId);

    return this.update(lambda);//更新状态
  }
  //#endregion

  //#region 生成上架单
  @Override
  public R<Void> createShelve(Map<String, Object> map) {
    try {
      String msg = "";
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);
      LambdaQueryWrapper<InEnterDetail> inEnterLambdaQueryWrapper = new LambdaQueryWrapper<>();
      inEnterLambdaQueryWrapper.in(InEnterDetail::getEnterDetailId, _ids);
      List<InEnterDetail> inEnterDetails = inEnterDetailService.list(inEnterLambdaQueryWrapper);
      Map<Long, List<InEnterDetail>> shelveGroup = inEnterDetails.stream().collect(Collectors.groupingBy(InEnterDetail::getEnterId));
      for (Long item : shelveGroup.keySet()) {
        // 入库单号
        InEnter inEnter = this.getById(item);
        if (ObjectUtil.isNotEmpty(inEnter.getOrderCode())) {
          //标记为已上架
          LambdaUpdateWrapper<InEnter> inEnterLambdaUpdateWrapper = new UpdateWrapper<InEnter>().lambda();
          inEnterLambdaUpdateWrapper.set(InEnter::getShelveStatus, "已上架")
            .eq(InEnter::getEnterId, item);
          this.update(inEnterLambdaUpdateWrapper);
        }

        var enterLists = shelveGroup.get(item);
        int num = Convert.toInt(map.get("num"));
        // num>0进行分页操作，num=0是不分页，所有数据放到一个单子明细里面
        long shelveOrderCount = num > 0 ? Convert.toLong(Math.ceil((1.0 * enterLists.size()) / num)) : 1;
        for (int i = 0; i < shelveOrderCount; i++) {
          //分单

          //#region  上架主表信息
          String shelveCode = DBUtils.getCodeRegular(MenuEnum.MENU_1660);
          if (ObjectUtil.isNotEmpty(msg)) msg += ", ";
          msg += shelveCode;
          InShelve inShelve = new InShelve();
          BeanUtil.copyProperties(inEnter, inShelve);
          inShelve.setShelveCode(shelveCode);
          inShelve.setShelveStatus(InShelveStatusEnum.WAITING.getName());
          inShelve.setTotalQuantity(Convert.toBigDecimal(0));
          inShelve.setAuditing((byte) 0);
          inShelve.setCreateBy(LoginHelper.getUserId());
          inShelve.setCreateByName(LoginHelper.getNickname());
          inShelve.setCreateTime(DateUtil.date());
          inShelve.setUserId(LoginHelper.getUserId());
          inShelve.setTotalShelvedQuantity((new BigDecimal(0)));
          inShelveService.save(inShelve);
          //#endregion

          //#region  上架明细信息

          int maxIndex = (i + 1) * num;

          if (maxIndex > enterLists.size() || ObjectUtil.isNotEmpty(num)) maxIndex = enterLists.size();

          ArrayList<InEnterDetail> list = new ArrayList<>();
          int index = 0;
          for (var info : enterLists) {

            if (ObjectUtil.isNotEmpty(info) && index >= i * num && index < maxIndex) {
              list.add(info);
            }
            index += 1;
          }
          BigDecimal quantity = new BigDecimal(0);
          BigDecimal weight = new BigDecimal(0);
          for (var enterDetailInfo : list) {
            InShelveDetail inShelveDetail = new InShelveDetail();
            BeanUtil.copyProperties(enterDetailInfo, inShelveDetail);
            inShelveDetail.setShelveId(inShelve.getShelveId());
            inShelveDetail.setOnShelveQuantity(enterDetailInfo.getEnterQuantity());

            RecommendPositionDto recommendPsoitionDto = BeanUtil.copyProperties(item, RecommendPositionDto.class);// 复制数据
            recommendPsoitionDto.setInQty(enterDetailInfo.getEnterQuantity());
            String positionName = recommendPositionService.getRecommendPosition(recommendPsoitionDto);

            inShelveDetail.setPositionName(positionName);
            inShelveDetail.setPositionNo(enterDetailInfo.getPositionName());
            inShelveDetail.setCreateBy(LoginHelper.getUserId());
            inShelveDetail.setCreateByName(LoginHelper.getNickname());
            inShelveDetail.setCreateTime(DateUtil.date());
            inShelveDetail.setShelveStatus(InShelveStatusEnum.WAITING.getName());
            inShelveDetail.setQuantity(enterDetailInfo.getEnterQuantity());
            inShelveDetailService.save(inShelveDetail);
            quantity = B.add(inShelveDetail.getQuantity(), quantity);
            weight = B.add(inShelveDetail.getRowWeight(), weight);
          }
          //#endregion

          //#region 更新合计数量
          // 主表求和字段计算

          LambdaUpdateWrapper<InShelve> inShelveLambdaUpdateWrapper = new UpdateWrapper<InShelve>().lambda();
          inShelveLambdaUpdateWrapper.set(InShelve::getTotalQuantity, quantity)
            .set(InShelve::getTotalOnshelveQuantity, quantity)
            .set(InShelve::getTotalWeight, weight)
            .eq(InShelve::getShelveId, inShelve.getShelveId());
          inShelveService.update(inShelveLambdaUpdateWrapper);//提交

          //#endregion
        }
      }
      return R.ok(msg + "上架单生成成功！");
    } catch (Exception e) {
      return R.fail(e.getMessage());
    }
  }
  //#endregion

  //#region 撤销入库校验

  /**
   * 撤销入库校验
   *
   * @param map
   * @return
   */
  @Override
  public R<Void> quickOut(Map<String, Object> map) {
    try {
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);

      for (long enterId : _ids) {
        // 入库单信息
        InEnter inEnterInfo = this.getById(enterId);

        if (ObjectUtil.isEmpty(inEnterInfo)) {
          throw new ServiceException("未获取到入库单");
        }

        LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询
        inventoryLambdaQueryWrapper.eq(CoreInventory::getBillCode, inEnterInfo.getEnterCode());
        List<CoreInventory> inventors = coreInventoryService.list(inventoryLambdaQueryWrapper);

        for (CoreInventory inventoryInfo : inventors) {
          // 当所操作的单据对应的库存没有占位，但是库存量已经小于原始量时，需要增加提示:当前库存已部分出库，是否继续操作取消入库
          if (B.isLessOrEqual(inventoryInfo.getHolderStorage()) && B.isLess(inventoryInfo.getProductStorage(), inventoryInfo.getOriginStorage())) {
            return R.warn("当前库存已部分出库，是否继续操作取消入库");
          }
        }
      }
      return R.ok("校验成功！");
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> repeatingSorting(Map<String, Object> map) {
    List<Long> ids = splitTo(Convert.toStr(map.get("ids")), ",", Convert::toLong);

    LambdaQueryWrapper<InEnterDetail> enterDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    enterDetailLambdaQueryWrapper.in(InEnterDetail::getEnterDetailId, ids);
    List<InEnterDetail> enterDetails = inEnterDetailService.list(enterDetailLambdaQueryWrapper);
    for (InEnterDetail enterDetail : enterDetails) {

      enterDetail.setSortingCount(Convert.toLong(B.add(enterDetail.getSortingCount(), 1)));

      LambdaUpdateWrapper<InEnterDetail> enterDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      enterDetailLambdaUpdateWrapper.set(InEnterDetail::getSortingCount, enterDetail.getSortingCount())
        .eq(InEnterDetail::getEnterDetailId, enterDetail.getEnterDetailId());
      inEnterDetailService.update(enterDetailLambdaUpdateWrapper);
    }


    return R.ok("操作成功");
  }
  //#endregion

  //#region 校验LPN是否有效

  /**
   * 校验LPN是否有效
   *
   * @param lpnCode LPN号
   */
  @Override
  public void checkLpnCodeValid(String lpnCode) {
    MPJLambdaWrapper<InEnterDetail> orderDetailLambdaUpdateWrapper = new MPJLambdaWrapper<>();
    orderDetailLambdaUpdateWrapper
      .select(InOrder::getOrderId)
      .innerJoin(InEnter.class, InEnter::getEnterId, InEnterDetail::getEnterId)
      .eq(InEnterDetail::getCaseNumber, lpnCode)
      .notIn(InEnter::getEnterStatus, List.of(InEnterStatusEnum.FINISHED.getName()));
    Assert.isFalse(inEnterDetailService.selectJoinCount(orderDetailLambdaUpdateWrapper) > 0, "LPN正在使用中，不可重复使用");
  }
  //#endregion

  //#region 生成一次性费用

  /**
   * 生成一次性费用
   *
   * @param map 入参
   * @return R
   */
  @Override
  public R<Void> createBill(Map<String, Object> map) {
    Long[] ids = Convert.toLongArray(map.get("ids"));

    boolean isCreated = false;
    for (long enterId : ids) {
      // 入库单信息
      InEnter inEnter = this.getById(enterId);
      Assert.isFalse(StringUtils.equals(inEnter.getOrderType(), InOrderTypeEnum.NO_BILL_FLASH.getName()), "一键闪入不允许撤销操作");
      Assert.isFalse(ObjectUtil.isNull(inEnter), "未获取到入库单");

      if (StrUtil.isNotEmpty(inEnter.getFeeItemIds())) {
        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_GENERATING_BILL); //类别
        rabbitReceiverDto.setBillId(inEnter.getEnterId());
        rabbitReceiverDto.setBillCode(inEnter.getEnterCode());
        rabbitReceiverDto.setSourceCode(inEnter.getOrderCode());
        rabbitReceiverDto.setSourceId("" + inEnter.getOrderId());
        taskQueueService.createTask(rabbitReceiverDto);
        isCreated = true;
      }
    }

    return isCreated ? R.ok("一次性费用项生成成功！") : R.fail("当前入库单没有设置费用项，不需要生成");
  }

  //#endregion

  /**
   * 根据预到货单号获取入库单信息
   *
   * @param orderCode
   * @return
   */
  @Override
  public InEnter getByCode(String orderCode) {
    LambdaQueryWrapper<InEnter> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(InEnter::getOrderCode, orderCode);
    return this.getOnly(lambdaQueryWrapper);
  }


}
