package com.yiruantong.outbound.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.out.*;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.OutOrderWaveSub;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveComposeVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPrintVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.mapper.operation.OutOrderWaveMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveSubService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.outbound.service.out.IOutOrderWaveStatusHistoryService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 出库单波次Service业务层处理
 *
 * @author YRT
 * @version 1.0
 * @date 2023-11-01
 */
@RequiredArgsConstructor
@Service
public class OutOrderWaveServiceImpl extends ServiceImplPlus<OutOrderWaveMapper, OutOrderWave, OutOrderWaveVo, OutOrderWaveBo> implements IOutOrderWaveService {

  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final ISysConfigService sysConfigService;
  private final IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService;
  private final IBasePositionService basePositionService;
  private final IOutOrderWaveSubService outOrderWaveSubService;
  private final IDataAuthService dataAuthService;

  //#region 加载列表前事件
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    //#region 列表之定义查询
    pageQuery.getQueryBoList().stream().filter(f -> f.getQueryType() == QueryTypeEnum.CUSTOM).forEach(f -> {
      if (f.getColumn().equals("productCode")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT order_wave_id FROM out_order_wave_detail d WHERE d.order_wave_id=out_order_wave.order_wave_id AND d.product_code='{}'", f.getValues()));
      } else if (f.getColumn().equals("orderCode")) {
        f.setQueryType(QueryTypeEnum.EXISTS);
        f.setValues(StringUtils.format("SELECT order_wave_id FROM out_order_wave_detail d WHERE d.order_wave_id=out_order_wave.order_wave_id AND d.order_code='{}'", f.getValues()));
      }
      //如果还有其他查询条件 则继续加else if

    });
    super.beforePageQuery(pageQuery);
    //#endregion
  }
  //#endregion

  //#region 根据波次单编号获取波次单信息

  /**
   * 根据波次单编号获取波次单信息
   *
   * @param orderWaveCode 波次单号
   * @return 返回波次单信息
   */
  @Override
  public OutOrderWave getByCode(String orderWaveCode) {
    LambdaQueryWrapper<OutOrderWave> shelveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    shelveLambdaQueryWrapper.eq(OutOrderWave::getOrderWaveCode, orderWaveCode);

    return this.getOne(shelveLambdaQueryWrapper);
  }
  //#endregion

  //#region cancelNickName

  /**
   * 取消主波次和对应的所有子波次的拣货人
   */
  @Override
  public R<Map<String, Object>> cancelNickName(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString());

    // 波次查询
    LambdaQueryWrapper<OutOrderWave> outOrderWaveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    outOrderWaveLambdaQueryWrapper.in(OutOrderWave::getOrderWaveId, (Object[]) ids)
      .orderByDesc(OutOrderWave::getOrderWaveId);
    List<OutOrderWave> outOrderWaves = this.list(outOrderWaveLambdaQueryWrapper);

    // 波次状态
    List<OutOrderStatusEnum> statusList = new ArrayList<>(Arrays.asList(OutOrderStatusEnum.WAVE_FINISHED, OutOrderStatusEnum.PICKING, OutOrderStatusEnum.PICKING_PARTIAL));

    for (var o : outOrderWaves) {
      boolean orderBatchType = statusList.stream().noneMatch(n -> Objects.deepEquals(n.getName(), o.getWaveStatus()));
      if (orderBatchType) {
        throw new ServiceException("只有是在波次完成、拣货中、部分拣货的波次才可以解绑！");
      }
    }

    // 修改
    Long[] _ids = Convert.toLongArray(ids);
    for (Long orderWaveId : _ids) {
      LambdaUpdateWrapper<OutOrderWave> lambda = new UpdateWrapper<OutOrderWave>().lambda();
      lambda.set(OutOrderWave::getNickName, null)
        .set(OutOrderWave::getPickNickName, null)
        .set(OutOrderWave::getPickUserId, null)
        .eq(OutOrderWave::getOrderWaveId, orderWaveId);
      this.update(lambda);
    }
    return R.ok();
  }
  //#endregion

  //#region 生成波次
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Void> createOrderWave(Map<String, Object> map) {
    // 不同快递公司拆分生成波次单
    boolean batch_differenceExpressSplitWave = sysConfigService.getConfigBool("batch_differenceExpressSplitWave");
    // 出库单id
    List<Long> idList = Convert.toList(Long.class, map.get("ids"));
    Long orderCount = Convert.toLong(map.get("orderCount"));
    Long priority = Convert.toLong(map.get("priority"));
    String nickName = Convert.toStr(map.get("nickName"));
    String remark = Convert.toStr(map.get("remark"));
    Long userId = Convert.toLong(map.get("userId"));
    System.out.println(orderCount);//波次订单数  暂时还没用
    IOutOrderService bean = SpringUtils.getBean(IOutOrderService.class);

    // 出库单分组
    LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    orderLambdaQueryWrapper
      .in(OutOrder::getOrderId, idList);

    List<OutOrder> outOrderList = bean.getBaseMapper().selectList(orderLambdaQueryWrapper);
    Assert.isFalse(outOrderList.stream().anyMatch(info -> StrUtil.isNotEmpty(info.getOrderWaveCode())), "已经生成过，波次不允许在生成");

    // 根据仓库ID和货主ID分组
    // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<OutOrder, List<Object>> compositeKey = key -> {
      // 根据货主ID、仓库ID
      List<Object> list = CollUtil.newArrayList(key.getConsignorId(), key.getStorageId());
      // 需要根据快递公司分组生成波次
      if (batch_differenceExpressSplitWave) {
        list.add(key.getExpressCorpId());
      }
      return list;
    };
    // 分组
    Map<List<Object>, List<OutOrder>> groupList = outOrderList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    for (var mapStorageItem : groupList.entrySet()) {
      List<Object> itemKey = mapStorageItem.getKey();// 分组关键词
      var groupDataList = mapStorageItem.getValue();  // 分子数据
      Long consignorId = Convert.toLong(itemKey.get(0));
      Long storageId = Convert.toLong(itemKey.get(1));

      //#region 分页生成波次单
      Long orderPages = Convert.toLong(Math.ceil((double) groupDataList.size() / orderCount));  // 分页数
      if (orderCount >= groupDataList.size()) {
        orderPages = 1L;
      }
      int groupDataIndex = 0; // 当前分组里面的数量索引
      for (int pageIndex = 0; pageIndex < orderPages; pageIndex++) {
        if (B.isEqual(groupDataIndex, groupDataList.size())) {
          break;
        }
        // 插入波次主表
        OutOrderWave outOrderWave = new OutOrderWave();
        outOrderWave.setConsignorId(consignorId);
        outOrderWave.setOrderNum(priority); // 优先级
        outOrderWave.setStorageId(storageId);
        outOrderWave.setSubBatch(EnableEnum.DISABLE.getId());
        // 设置快递公司
        var outOrderFirst = mapStorageItem.getValue().get(0);
        outOrderWave.setExpressCorpId(outOrderFirst.getExpressCorpId());
        outOrderWave.setExpressCorpName(outOrderFirst.getExpressCorpName());
        outOrderWave.setOrderWaveCode(DBUtils.getCodeRegular(MenuEnum.MENU_1681));

        this.save(outOrderWave);

        BigDecimal totalQuantityOrder = BigDecimal.ZERO; //订单数量合计
        BigDecimal totalWeight = BigDecimal.ZERO;//合计重量
        int allocIndex = 0; // 配货位
        int waveOrderNums = 0; // 波次单订单数量

        for (int k = 1; k <= orderCount; k++) {
          if (groupDataIndex >= groupDataList.size()) {
            break;
          }
          OutOrder outOrder = groupDataList.get(groupDataIndex);
          allocIndex++;
          // 出库单明细
          List<OutOrderDetail> outOrderDetail = outOrderDetailService.selectListByMainId(outOrder.getOrderId());

          // 出库单明细
          for (var detail : outOrderDetail) {
            BigDecimal batchQuantity = BigDecimal.ZERO; //波次数量合计
            // 获取出库单占位数据
            List<CoreInventoryHolder> holderList = coreInventoryHolderService.selectHolderList(outOrder.getOrderId(), detail.getOrderDetailId(), outOrder.getOrderCode());
            for (var holder : holderList) {
              // 波次查询明细
              OutOrderWaveDetail waveDetail = new OutOrderWaveDetail();
              BeanUtil.copyProperties(detail, waveDetail);
              waveDetail.setOrderWaveId(outOrderWave.getOrderWaveId());
              waveDetail.setHolderId(holder.getHolderId());
              waveDetail.setProduceDate(holder.getProduceDate());
              waveDetail.setPlateCode(holder.getPlateCode());
              waveDetail.setBatchNumber(holder.getBatchNumber());
              waveDetail.setPositionName(holder.getPositionName());

              // 获取货位类型
              BasePosition basePosition = basePositionService.getByName(holder.getStorageId(), holder.getPositionName());
              waveDetail.setPositionType(basePosition.getPositionType());

              waveDetail.setOrderCode(outOrder.getOrderCode());
              waveDetail.setExpressCorpId(outOrder.getExpressCorpId());
              waveDetail.setExpressCorpName(outOrder.getExpressCorpName());
              waveDetail.setExpressCode(outOrder.getExpressCode());
              waveDetail.setQuantityOrder(holder.getHolderStorage());
              waveDetail.setQuantityOrderOrigin(holder.getHolderStorage());
              waveDetail.setQuantityOuted(BigDecimal.ZERO);
              waveDetail.setPickQuantity(BigDecimal.ZERO);
              waveDetail.setMatchedQuantity(BigDecimal.ZERO);

              waveDetail.setSourceType(OutSourceTypeEnum.OUT_ORDER.getName());
              waveDetail.setSourceMainId(detail.getOrderId().toString());
              waveDetail.setSourceDetailId(detail.getOrderDetailId().toString());
              waveDetail.setLineName(outOrder.getLineName());
              waveDetail.setLineCode(outOrder.getLineCode());
              waveDetail.setLineId(outOrder.getLineId());
              waveDetail.setClientId(outOrder.getClientId());
              waveDetail.setClientCode(outOrder.getClientCode());
              waveDetail.setClientShortName(outOrder.getClientShortName());
              waveDetail.setSourceDetailId(detail.getOrderDetailId().toString());
              waveDetail.setAllotPositionName(String.format("%02d", allocIndex)); // 配货位

              outOrderWaveDetailService.save(waveDetail);

              // 求和
              batchQuantity = B.add(batchQuantity, holder.getHolderStorage());
            }
            detail.setBatchQuantity(batchQuantity);
            outOrderDetailService.getBaseMapper().updateById(detail);

            // 求和
            totalQuantityOrder = B.add(totalQuantityOrder, detail.getQuantityOrder());
            totalWeight = B.add(totalWeight, detail.getWeight());
          }

          // 更新出库单波次单号及状态
          outOrder.setOrderWaveId(outOrderWave.getOrderWaveId());
          outOrder.setOrderWaveCode(outOrderWave.getOrderWaveCode());
          // 出库单的轨迹
          outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.CREATE_WAVE, OutOrderStatusEnum.WAVE_FINISHED);

          outOrder.setOrderStatus(OutOrderStatusEnum.WAVE_FINISHED.getName());
          IOutOrderService outOrderService = SpringUtils.getBean(IOutOrderService.class);
          outOrderService.getBaseMapper().updateById(outOrder);
          BeanUtil.copyProperties(outOrder, outOrderWave);

          groupDataIndex = groupDataIndex + 1;
          waveOrderNums = waveOrderNums + 1;
        }

        LoginUser loginUser = LoginHelper.getLoginUser();
        outOrderWave.setWaveStatus(OutOrderStatusEnum.WAVE_FINISHED.getName());
        outOrderWave.setOrderCount(Convert.toLong(waveOrderNums));
        outOrderWave.setUnFinishedCount(outOrderWave.getOrderCount());
        outOrderWave.setCreateTime(new Date());
        outOrderWave.setUserId(loginUser.getUserId());
        outOrderWave.setCreateByName(loginUser.getNickname());

        outOrderWave.setTotalQuanityOrder(totalQuantityOrder);//订单数量合计
        outOrderWave.setTotalWeight(totalWeight);//订单重量合计

        outOrderWave.setPickUserId(userId); // 拣货人id
        outOrderWave.setPickNickName(nickName); // 拣货人
        outOrderWave.setRemark(remark); // 备注


        this.saveOrUpdate(outOrderWave);


//        outOrderWave.setWaveStatus(null);
        // 生成波次的轨迹
        outOrderWaveStatusHistoryService.AddHistory(outOrderWave, OutWaveOperationTypeEnum.CREATE_WARE, OutOrderStatusEnum.WAVE_FINISHED);

        //#region 子波次处理
        // 开启生成子波次
        boolean batch_onlySubBatch = sysConfigService.getConfigBool("batch_onlySubBatch");
        if (batch_onlySubBatch) {
          MPJLambdaWrapper<OutOrderWaveDetail> waveDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
          waveDetailMPJLambdaWrapper
            .select(BasePosition::getAreaCode)
            .select(OutOrderWave::getOrderWaveCode)
            .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
            .innerJoin(BasePosition.class, on -> {
              on.eq(BasePosition::getStorageId, OutOrderWave::getStorageId)
                .eq(BasePosition::getPositionName, OutOrderWaveDetail::getPositionName);
              return on;
            })
            .eq(OutOrderWave::getOrderWaveId, outOrderWave.getOrderWaveId())
            .groupBy(BasePosition::getAreaCode)
            .groupBy(OutOrderWave::getOrderWaveCode);

          List<Map<String, Object>> mapList = outOrderWaveDetailService.selectJoinMaps(waveDetailMPJLambdaWrapper);
          mapList = mapList.stream().filter(f -> StringUtils.isNotEmpty(Convert.toStr(f.get("areaCode")))).toList(); // 去除空库区

          // 存在两个以上库区才生成子波次
          if (mapList.size() >= 2) {
            long waveMapIndex = 1L;
            for (var waveMap : mapList) {
              String areaCode = Convert.toStr(waveMap.get("areaCode"));

              // 获取库区分组数据
              MPJLambdaWrapper<OutOrderWaveDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
              detailMPJLambdaWrapper
                .select(OutOrderWaveDetail::getOrderWaveDetailId)
                .select(BasePosition::getAreaCode)
                .select(OutOrderWave::getOrderWaveCode)
                .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
                .innerJoin(BasePosition.class, on -> {
                  on.eq(BasePosition::getStorageId, OutOrderWave::getStorageId)
                    .eq(BasePosition::getPositionName, OutOrderWaveDetail::getPositionName);
                  return on;
                })
                .eq(OutOrderWave::getOrderWaveId, outOrderWave.getOrderWaveId())
                .eq(BasePosition::getAreaCode, areaCode);

              // 子波次单号
              String subOrderWaveCode = outOrderWave.getOrderWaveCode() + StringUtils.SEPARATOR_MINUS + String.format("%02d", waveMapIndex);
              boolean isCreate = false;
              List<Map<String, Object>> groupMapList = outOrderWaveDetailService.selectJoinMaps(detailMPJLambdaWrapper);
              for (var groupMap : groupMapList) {
                Long orderWaveDetailId = Convert.toLong(groupMap.get("orderWaveDetailId"));

                if (!isCreate) {
                  OutOrderWaveSub sub = new OutOrderWaveSub();
                  sub.setOrderWaveId(outOrderWave.getOrderWaveId());
                  sub.setSubOrderWaveCode(subOrderWaveCode);
                  sub.setAreaCode(areaCode);
                  sub.setSubWaveStatus(OutOrderWaveStatusEnum.WAVE_FINISHED.getName());
                  sub.setOrderNum(0L);
                  outOrderWaveSubService.save(sub);
                  isCreate = true; // 只生成一次
                }

                // 更新明细表子波次单号
                LambdaUpdateWrapper<OutOrderWaveDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                detailLambdaUpdateWrapper.set(OutOrderWaveDetail::getSubOrderWaveCode, subOrderWaveCode)
                  .eq(OutOrderWaveDetail::getOrderWaveDetailId, orderWaveDetailId);
                outOrderWaveDetailService.update(detailLambdaUpdateWrapper);
              }

              waveMapIndex++;
            }

            // 更新波次单主表为已生成子波次
            LambdaUpdateWrapper<OutOrderWave> waveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            waveLambdaUpdateWrapper.set(OutOrderWave::getSubBatch, EnableEnum.ENABLE.getId())
              .eq(OutOrderWave::getOrderWaveId, outOrderWave.getOrderWaveId());
            this.update(waveLambdaUpdateWrapper);
          }
        }
        //#endregion
      }

      //#endregion
    }
    return R.ok();
  }
  //#endregion

  //#region 生成波次页面 统计商品规格
  @Override
  public List<OutOrderDetail> getOrderProductSpec(Map<String, Object> map) {
    String[] ids = StringUtils.split(map.get("ids").toString(), ",");
    Object[] _ids = Convert.toLongArray(ids);
    List<OutOrderDetail> list = null;
    if (ids.length != 0) {
      // 出库单分组
      LambdaQueryWrapper<OutOrderDetail> orderLambdaQueryWrapper = new QueryWrapper<OutOrderDetail>()
        .select(" sum(big_qty) as bigQty,SUM(quantity_order) AS quantityOrder,product_spec")
        .lambda()
        .in(OutOrderDetail::getOrderId, _ids)
        .isNotNull(OutOrderDetail::getProductSpec)
        .groupBy(OutOrderDetail::getProductSpec);

      list = outOrderDetailService.list(orderLambdaQueryWrapper);
    }

    return list;
  }
  //#endregion

  //#region 更新波次单状态
  @Override
  public void updateWaveStatus(Long orderWaveId, OutOrderWaveStatusEnum outOrderStatusEnum) {
    this.updateWaveStatus(orderWaveId, outOrderStatusEnum, null);
  }

  @Override
  public void updateWaveStatus(Long orderWaveId, OutOrderWaveStatusEnum outOrderStatusEnum, String subOrderWaveCode) {
    LambdaUpdateWrapper<OutOrderWave> outOrderWaveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    outOrderWaveLambdaUpdateWrapper
      .set(OutOrderWave::getWaveStatus, outOrderStatusEnum.getName())
      .eq(OutOrderWave::getOrderWaveId, orderWaveId);
    this.update(outOrderWaveLambdaUpdateWrapper);

    // 更新子波次状态
    if (StringUtils.contains(subOrderWaveCode, StringUtils.SEPARATOR_MINUS)) {
      List<OutOrderWaveDetail> waveDetailList = outOrderWaveDetailService.selectListById(orderWaveId);
      waveDetailList = waveDetailList.stream().filter(x -> StringUtils.equals(x.getSubOrderWaveCode(), subOrderWaveCode)).toList();
      // 更新波次单状态，获取波次数量
      BigDecimal pickQuantity = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getPickQuantity); // 获取下架数量
      BigDecimal quantityOrder = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getQuantityOrderOrigin); // 获取出库单数量

      LambdaUpdateWrapper<OutOrderWaveSub> subLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      if (B.isGreaterOrEqual(pickQuantity, quantityOrder)) {
        // 拣货完成
        subLambdaUpdateWrapper
          .set(OutOrderWaveSub::getSubWaveStatus, OutOrderWaveStatusEnum.PICKED.getName())
          .eq(OutOrderWaveSub::getSubOrderWaveCode, subOrderWaveCode);
      } else {
        subLambdaUpdateWrapper
          .set(OutOrderWaveSub::getSubWaveStatus, OutOrderWaveStatusEnum.PICKING.getName())
          .eq(OutOrderWaveSub::getSubOrderWaveCode, subOrderWaveCode);
      }
      outOrderWaveSubService.update(subLambdaUpdateWrapper);
    }
  }
  //#endregion

  //#region onFrozenOrder
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> onFrozenOrder(Map<String, Object> map) {
    Long orderWaveId = Convert.toLong(map.get("orderWaveId"));
    String orderCode = Convert.toStr(map.get("orderCode"));

    //判断波次单状态
    OutOrderWave outOrderWave = this.getById(orderWaveId);
    Assert.isFalse(ObjectUtil.isEmpty(outOrderWave), "未找到对应的波次单");
    Assert.isTrue(
      StrUtil.equals(outOrderWave.getWaveStatus(), OutOrderWaveStatusEnum.WAVE_FINISHED.getName()) ||
        StrUtil.equals(outOrderWave.getWaveStatus(), OutOrderWaveStatusEnum.PICKING.getName())
      , "只有【波次完成】、【拣货中】才允许操作");

    LambdaQueryWrapper<OutOrderWaveDetail> waveDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    waveDetailLambdaQueryWrapper.eq(OutOrderWaveDetail::getOrderWaveId, orderWaveId)
      .eq(OutOrderWaveDetail::getOrderCode, orderCode);
    List<OutOrderWaveDetail> detailRemoveds = outOrderWaveDetailService.list(waveDetailLambdaQueryWrapper);
    Assert.isFalse(B.isEqual(detailRemoveds.size()), "订单号不存在当前波次单中！");

    Assert.isTrue(B.isEqual(detailRemoveds.stream().filter(item -> B.isGreater(item.getQuantityOuted())).toList().size()), "订单号已经存在出库明细，不允许剔除！");

    //删除对应的 波次明细
    outOrderWaveDetailService.remove(waveDetailLambdaQueryWrapper);

    //从新获取所有的明细信息
    List<OutOrderWaveDetail> details = outOrderWaveDetailService.selectListByMainId(orderWaveId);
    //获取合计信息
    BigDecimal totalQuantityOrder = details.stream().map(OutOrderWaveDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal freezeQuantity = details.stream().filter(i -> B.isGreater(i.getFreezeQuantity())).map(OutOrderWaveDetail::getFreezeQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    int count = details.stream().collect(Collectors.groupingBy(OutOrderWaveDetail::getOrderCode)).size();
    int unFinishedCount = details.stream()
      .filter(f -> B.isGreater(f.getQuantityOrder(), f.getFreezeQuantity()))
      .collect(Collectors.groupingBy(OutOrderWaveDetail::getOrderCode)).size();

    //修改统计数据
    LambdaUpdateWrapper<OutOrderWave> waveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    waveLambdaUpdateWrapper.set(OutOrderWave::getOrderCount, count)
      .set(OutOrderWave::getUnFinishedCount, unFinishedCount)
      .set(OutOrderWave::getTotalQuanityOrder, totalQuantityOrder)
      .set(OutOrderWave::getFreezeQuantity, freezeQuantity)
      .eq(OutOrderWave::getOrderWaveId, orderWaveId);
    this.update(waveLambdaUpdateWrapper);


    for (var info : detailRemoveds) {
      //修改出库单明细 波次数量
      LambdaUpdateWrapper<OutOrderDetail> outOrderDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      outOrderDetailLambdaUpdateWrapper.set(OutOrderDetail::getBatchQuantity, BigDecimal.ZERO)
        .eq(OutOrderDetail::getOrderDetailId, info.getOrderDetailId());
      outOrderDetailService.update(outOrderDetailLambdaUpdateWrapper);
    }


    IOutOrderService bean = SpringUtils.getBean(IOutOrderService.class);
    OutOrder outOrder = bean.getByCode(orderCode);
    outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.WAVE_REMOVED, OutOrderStatusEnum.AUDIT_SUCCESS);
    //修改出库单信息
    LambdaUpdateWrapper<OutOrder> outOrderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    outOrderLambdaUpdateWrapper.set(OutOrder::getOrderStatus, OutOrderStatusEnum.AUDIT_SUCCESS.getName())
      .set(OutOrder::getOrderWaveId, null)
      .set(OutOrder::getOrderWaveCode, null)
      .eq(OutOrder::getOrderCode, orderCode);
    bean.update(outOrderLambdaUpdateWrapper);

    return R.ok("剔除订单成功！");
  }
  //#endregion

  /**
   * 入库计划批量审核
   *
   * @param map 审核参数
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> deleteData(Map<String, Object> map) {
    // 写具体审核逻辑
    try {
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);

      for (long orderWaveId : _ids) {
        OutOrderWaveVo outOrderWaveVo = this.baseMapper.selectVoById(orderWaveId);
        if (ObjectUtil.isEmpty(outOrderWaveVo)) {
          throw new ServiceException("未获取到波次单");
        }
        if (!B.isEqual(outOrderWaveVo.getOrderCount())) {
          throw new ServiceException("订单数必须为0才可以删除");
        }

        //删除对应的 波次明细
        this.deleteById(orderWaveId);
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }


  @Override
  public R<List<OutOrderWaveDetailPrintVo>> selectOrderWave(List<QueryBo> queryBoList) {

    List<OutOrderWaveDetailPrintVo> list = new ArrayList<>();
    queryBoList.forEach(
      item -> {

        String column = item.getColumn();
        String values = item.getValues();
        if (ObjectUtil.equals(column, "orderWaveId")) {
          // 构建联表查询
          MPJLambdaWrapper<OutOrderWaveDetail> wrapper = new MPJLambdaWrapper<>();
          wrapper
            .select(OutOrder::getStoreOrderCode)
            .selectAll(OutOrderWave.class)
            .selectAll(OutOrderWaveDetail.class)
            .selectAll(BaseProduct.class)
            .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
            .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderWaveDetail::getProductId)
            .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderWaveDetail::getOrderId)
            .eq(OutOrderWave::getOrderWaveId, values);


          List<OutOrderWaveDetailPrintVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailPrintVo.class, wrapper);
          for (OutOrderWaveDetailPrintVo sendBatchVo : inventoryComposeVoList) {
            if (ObjectUtil.isNotEmpty(sendBatchVo.getPositionName())) {
              // 获取通道和库区
              LambdaQueryWrapper<BasePosition> positionQueryWrapper = new LambdaQueryWrapper<>();
              positionQueryWrapper.eq(BasePosition::getPositionName, sendBatchVo.getPositionName())
                .eq(BasePosition::getStorageName, sendBatchVo.getStorageName())
                .last("limit 1");
              BasePosition basePosition = basePositionService.getOne(positionQueryWrapper);

              if (basePositionService.getBaseMapper().exists(positionQueryWrapper)) {
                sendBatchVo.setAreaCode(basePosition.getAreaCode());
                sendBatchVo.setChannelCode(basePosition.getChannelCode());
              }
            }
            list.add(sendBatchVo);
          }
        }

      });
    return R.ok(list);
  }

  // 修改优先级
  @Override
  public R<Void> updatePriority(Map<String, Object> map) {
    Long orderNum = Convert.toLong(map.get("orderNum"));

    String[] ids = StringUtils.split(map.get("idList").toString(), ",");
    Long[] _ids = Convert.toLongArray(ids);


    for (long orderId : _ids) {
      LambdaUpdateWrapper<OutOrderWave> outOrderWaveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      outOrderWaveLambdaUpdateWrapper
        .set(OutOrderWave::getOrderNum, orderNum);
      if (ObjectUtil.isNotEmpty(orderId)) {
        outOrderWaveLambdaUpdateWrapper.eq(OutOrderWave::getOrderWaveId, orderId);
        this.update(outOrderWaveLambdaUpdateWrapper);
      }
    }


    return R.ok("修改成功");
  }

  @Override
  public R<List<Map<String, Object>>> getSubWave(Long orderWaveId) {
    MPJLambdaWrapper<OutOrderWaveDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    detailMPJLambdaWrapper.select(OutOrderWaveDetail::getAllotPositionName, OutOrderWaveDetail::getPositionName, OutOrderWaveDetail::getOrderWaveId, OutOrderWaveDetail::getProductId, OutOrderWaveDetail::getProductCode, OutOrderWaveDetail::getProductName, OutOrderWaveDetail::getProductSpec, OutOrderWaveDetail::getProductModel, OutOrderWaveDetail::getProduceDate, OutOrderWaveDetail::getQuantityOrder, OutOrderWaveDetail::getStoreOrderCode, OutOrderWaveDetail::getOrderId, OutOrderWaveDetail::getOrderCode)
      .select(OutOrderWaveSub::getSubId, OutOrderWaveSub::getSubOrderWaveCode, OutOrderWaveSub::getAreaCode, OutOrderWaveSub::getPickNickName, OutOrderWaveSub::getCreateByName, OutOrderWaveSub::getCreateTime, OutOrderWaveSub::getSubWaveStatus)
      .innerJoin(OutOrderWaveSub.class, OutOrderWaveSub::getSubOrderWaveCode, OutOrderWaveDetail::getSubOrderWaveCode)
      .eq(OutOrderWaveDetail::getOrderWaveId, orderWaveId);

    List<Map<String, Object>> mapList = outOrderWaveDetailService.selectJoinMaps(detailMPJLambdaWrapper);
    return R.ok(mapList);
  }

  @Override
  public boolean updatePickUserInfo(String orderWaveCode, Long userId, String nickName) {
    LambdaUpdateWrapper<OutOrderWave> subLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    subLambdaUpdateWrapper
      .set(OutOrderWave::getPickUserId, userId)
      .set(OutOrderWave::getPickNickName, nickName)
      .eq(OutOrderWave::getOrderWaveCode, orderWaveCode);

    return this.update(subLambdaUpdateWrapper);
  }

  @Override
  public R<Void> cancelSubNickName(Long subId) {
    Assert.isFalse(ObjectUtil.isNull(subId), "参数不全，操作执行失败！");
    //子波次
    var printSubInfo = outOrderWaveSubService.getById(subId);
    Assert.isFalse(ObjectUtil.isNull(printSubInfo), "没有找到此子波次信息！");
    List<String> statusList = List.of(OutOrderWaveStatusEnum.PICKING.getName(), OutOrderWaveStatusEnum.WAVE_FINISHED.getName(), OutOrderStatusEnum.PICKING_PARTIAL.getName());
    Assert.isFalse(!statusList.contains(printSubInfo.getSubWaveStatus()), "只有【" + StringUtils.join(statusList, StrUtil.COMMA) + "】没有找到此子波次信息！");

    try {
      //扩展字段中拣货人信息清空
      printSubInfo.setPickUserId(null);
      printSubInfo.setPickNickName(null);

      LambdaUpdateWrapper<OutOrderWaveSub> subLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      subLambdaUpdateWrapper
        .set(OutOrderWaveSub::getPickUserId, null)
        .set(OutOrderWaveSub::getPickNickName, null)
        .eq(OutOrderWaveSub::getSubOrderWaveCode, printSubInfo.getSubOrderWaveCode());

      //更新所有子波次中的拣货人
      outOrderWaveSubService.update(subLambdaUpdateWrapper);

      return R.ok("子波次中拣货人信息已解绑！");
    } catch (Exception error) {
      return R.fail("错误信息：" + error.getMessage());
    }
  }

  @Override
  public R<Map<String, Object>> customPickBillDataList(List<QueryBo> queryBoList) {
    Long orderWaveId = queryBoList.stream().filter(f -> StringUtils.equals(f.getColumn(), "orderWaveId")).map(m -> Convert.toLong(m.getValues())).findFirst().orElse(null);
    Assert.isFalse(orderWaveId == null, "波次单ID不能为空！");

    String subOrderWaveCode = queryBoList.stream().filter(f -> StringUtils.equals(f.getColumn(), "subOrderWaveCode")).map(QueryBo::getValues).findFirst().orElse(null);

    var orderWave = this.getById(orderWaveId);
    // 分组获得是否存在子波次
    LambdaQueryWrapper<OutOrderWaveSub> subLambdaQueryWrapper = new LambdaQueryWrapper<>();
    subLambdaQueryWrapper.select(OutOrderWaveSub::getSubOrderWaveCode)
      .eq(OutOrderWaveSub::getOrderWaveId, orderWaveId)
      .groupBy(OutOrderWaveSub::getSubOrderWaveCode);

    // 子波次列表
    if (B.isEqual(orderWave.getSubBatch(), EnableEnum.ENABLE.getId()) && StringUtils.isNotEmpty(subOrderWaveCode)) {
      subLambdaQueryWrapper.eq(OutOrderWaveSub::getSubOrderWaveCode, subOrderWaveCode);
    }
    List<Map<String, Object>> waveSubMapList = outOrderWaveSubService.listMaps(subLambdaQueryWrapper);

    Map<String, Object> resultMapList = new HashMap<>(); // 返回结果
    List<Map<String, Object>> dataMapList = new ArrayList<>(); // 数据集合

    // 波次主表数据
    LambdaQueryWrapper<OutOrderWave> waveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    waveLambdaQueryWrapper.eq(OutOrderWave::getOrderWaveId, orderWaveId);
    Map<String, Object> mainMap = this.getMap(waveLambdaQueryWrapper);
    if (!waveSubMapList.isEmpty()) {
      // 拆分为多个子波次
      for (var waveSubMap : waveSubMapList) {
        Map<String, Object> _mainMap = new HashMap<>(mainMap);

        String _subOrderWaveCode = Convert.toStr(waveSubMap.get("subOrderWaveCode"));
        _mainMap.put("orderWaveCode", _subOrderWaveCode);

        // 波次明细数据
        LambdaQueryWrapper<OutOrderWaveDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper
          .eq(OutOrderWaveDetail::getOrderWaveId, orderWaveId)
          .eq(OutOrderWaveDetail::getSubOrderWaveCode, _subOrderWaveCode)
          .gt(OutOrderWaveDetail::getQuantityOrder, 0);
        var outOrderWaveDetails = outOrderWaveDetailService.listMaps(detailLambdaQueryWrapper);

        AtomicInteger index = new AtomicInteger(0);
        outOrderWaveDetails.forEach(item -> {
          item.put("index", index.getAndIncrement());
        });
        _mainMap.put("out_order_wave_detail", outOrderWaveDetails);

        dataMapList.add(_mainMap);
      }
    } else {
      // 常规波次单处理
      LambdaQueryWrapper<OutOrderWaveDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      detailLambdaQueryWrapper
        .eq(OutOrderWaveDetail::getOrderWaveId, orderWaveId);
      var outOrderWaveDetails = outOrderWaveDetailService.listMaps(detailLambdaQueryWrapper); // 波次明细数据

      AtomicInteger index = new AtomicInteger(0);
      outOrderWaveDetails.forEach(item -> {
        item.put("index", index.getAndIncrement());
      });
      mainMap.put("out_order_wave_detail", outOrderWaveDetails);

      dataMapList.add(mainMap);
    }
    resultMapList.put("isCustomData", true); // 自定义数据，必须标记上
    resultMapList.put("dataList", dataMapList);

    return R.ok(resultMapList);
  }

  /**
   * PDA 拣货任务列表自定义查询
   *
   * @param pageQuery
   * @return
   */

  @Override
  public TableDataInfo<OutOrderWaveComposeVo> pdaPageList(PageQuery pageQuery) {
    IPage<OutOrderWaveComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限

    MPJLambdaWrapper<OutOrderWave> wrapper = new MPJLambdaWrapper<OutOrderWave>()
      .selectAs(OutOrderWave::getPickUserId, OutOrderWaveComposeVo::getMainPickUserId)
      .selectAs(OutOrderWave::getPickNickName, OutOrderWaveComposeVo::getMainPickNickName)
      .select(OutOrderWaveSub::getSubOrderWaveCode, OutOrderWaveSub::getAreaCode, OutOrderWaveSub::getSubWaveStatus, OutOrderWaveSub::getPickUserId, OutOrderWaveSub::getPickNickName)
      .select(OutOrderWave::getOrderWaveCode, OutOrderWave::getWaveStatus, OutOrderWave::getStorageId, OutOrderWave::getStorageName, OutOrderWave::getConsignorId, OutOrderWave::getConsignorCode, OutOrderWave::getConsignorName, OutOrderWave::getOrderType, OutOrderWave::getOrderCount)
      .selectSum(OutOrderWaveDetail::getPickQuantity, OutOrderWaveComposeVo::getPickQuantity)
      .selectSum(OutOrderWaveDetail::getQuantityOrder, OutOrderWaveComposeVo::getTotalQuanityOrder)
      .innerJoin(OutOrderWaveDetail.class, OutOrderWaveDetail::getOrderWaveId, OutOrderWave::getOrderWaveId)
      .leftJoin(OutOrderWaveSub.class, OutOrderWaveSub::getSubOrderWaveCode, OutOrderWaveDetail::getSubOrderWaveCode)
      .gt(OutOrderWaveDetail::getQuantityOrder, BigDecimal.ZERO) // 订单数量>0
      .in(OutOrderWaveDetail::getPositionType, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.STORAGE.getId(), PositionTypeEnum.ELEVATED.getId())) // 货位类型
      .groupBy(OutOrderWave::getOrderWaveCode, OutOrderWave::getWaveStatus, OutOrderWave::getStorageId, OutOrderWave::getStorageName, OutOrderWave::getConsignorId, OutOrderWave::getConsignorCode, OutOrderWave::getConsignorName, OutOrderWave::getOrderType, OutOrderWave::getPickUserId, OutOrderWave::getPickNickName, OutOrderWave::getCreateTime)
      .groupBy(OutOrderWaveSub::getSubOrderWaveCode, OutOrderWaveSub::getAreaCode, OutOrderWaveSub::getSubWaveStatus, OutOrderWaveSub::getPickUserId, OutOrderWaveSub::getPickNickName)
      .orderByDesc(OutOrderWave::getCreateTime);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, OutOrderWave.class, OutOrderWaveSub.class);
    // 自定义拣货人查询条件
    if (!LoginHelper.isSuperAdmin()) {
      wrapper.and(a ->
        a.eq(OutOrderWave::getSubBatch, EnableEnum.DISABLE.getId()).and(c -> c.eq(OutOrderWave::getPickUserId, LoginHelper.getUserId()).or().isNull(OutOrderWave::getPickUserId))
          .or()
          .eq(OutOrderWave::getSubBatch, EnableEnum.ENABLE.getId()).and(c -> c.eq(OutOrderWaveSub::getPickUserId, LoginHelper.getUserId()).or().isNull(OutOrderWaveSub::getPickUserId))
      );
    }

    IPage<OutOrderWaveComposeVo> page = this.selectJoinListPage(ipage, OutOrderWaveComposeVo.class, wrapper);

    // 二次处理数据，将子波次相同地进行合并
    for (var item : page.getRecords()) {
      // 波次单号和拣货人处理
      if (StringUtils.isNotEmpty(item.getSubOrderWaveCode())) {
        item.setOrderWaveCode(item.getSubOrderWaveCode());
        item.setWaveStatus(item.getSubWaveStatus());
      } else {
        item.setPickUserId(item.getMainPickUserId());
        item.setPickNickName(item.getMainPickNickName());
      }
    }

    TableDataInfo<OutOrderWaveComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());

    return tableDataInfoV;
  }

}
