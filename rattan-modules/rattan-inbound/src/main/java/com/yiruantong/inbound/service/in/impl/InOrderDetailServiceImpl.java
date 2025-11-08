package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InOrderDetailBo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailVo;
import com.yiruantong.inbound.mapper.in.InOrderDetailMapper;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.domain.helper.RecommendPositionDto;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 预到货单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
@RequiredArgsConstructor
@Service
public class InOrderDetailServiceImpl extends ServiceImplPlus<InOrderDetailMapper, InOrderDetail, InOrderDetailVo, InOrderDetailBo> implements IInOrderDetailService {
  private final IBasePositionService basePositionService;
  private final IDataAuthService dataAuthService;
  private final ISysConfigService sysConfigService;
  private final IRecommendPositionService recommendPositionService;

  /**
   * 根据主表ID获取明细集合
   *
   * @param orderId
   * @return 返回明细集合
   */
  public List<InOrderDetail> selectListByMainId(Long orderId) {
    LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(InOrderDetail::getOrderId, orderId);

    return this.list(detailLambdaQueryWrapper);
  }

  /**
   * 判断预到货明细是否已上架
   *
   * @param storageId
   * @param orderId
   * @return 返回明细集合
   */
  @Override
  public boolean isAllShelvePosition(Long storageId, Long orderId) {
    var details = this.selectListByMainId(orderId);

    boolean allShelvePosition = true; // 全部为上架货位
    for (var detail : details) {
      BasePosition basePosition = basePositionService.getByName(storageId, detail.getPositionName());
      if (Optional.ofNullable(basePosition).map(BasePosition::getPositionType)
        .orElse(PositionTypeEnum.RECEIVING.getId())
        .equals(PositionTypeEnum.RECEIVING.getId())) {
        allShelvePosition = false;
        break;
      }
    }

    return allShelvePosition;
  }

  /**
   * 预到货单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<InOrderDetailComposeVo> selectInOrderDetailComposeList(PageQuery pageQuery) {
    IPage<InOrderDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<InOrderDetail> wrapper = new MPJLambdaWrapper<InOrderDetail>()
      .select(InOrder::getOrderCode,
        InOrder::getConsignorName, InOrder::getProviderShortName, InOrder::getStorageName, InOrder::getSourceCode,
        InOrder::getTrackingNumber, InOrder::getShelveStatus, InOrder::getNickName, InOrder::getOrderStatus,
        InOrder::getOrderType, InOrder::getArrivedDate, InOrder::getIsChecking, InOrder::getRemark,
        InOrder::getContainerNos, InOrder::getSealNos, InOrder::getDeptName)
      .selectAll(InOrderDetail.class)
      .innerJoin(InOrder.class, InOrder::getOrderId, InOrderDetail::getOrderId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, InOrderDetail.class, InOrder.class);

    IPage<InOrderDetailComposeVo> page = this.selectJoinListPage(ipage, InOrderDetailComposeVo.class, wrapper);
    TableDataInfo<InOrderDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  @Override
  public R<List<Map<String, Object>>> getQuickEnterList(List<Long> ids) {

    // 审核成功允许扫描入库
    var in_isAuditAfter = sysConfigService.getConfigBool("in_isAuditAfter");
    List<InOrderStatusEnum> statusList = new ArrayList<>(Arrays.asList(InOrderStatusEnum.IN_TRANSIT, InOrderStatusEnum.PARTIAL_FINISHED, InOrderStatusEnum.SUCCESS, InOrderStatusEnum.NEWED));
    if (in_isAuditAfter) {
      statusList = statusList.stream().filter(item -> !ObjectUtil.equals(item, InOrderStatusEnum.NEWED)).toList();
    }
    boolean in_isQuickEnterPositionName = sysConfigService.getConfigBool("in_isQuickEnterPositionName"); // 默认收货位
    MPJLambdaWrapper<InOrderDetail> wrapper = new MPJLambdaWrapper<InOrderDetail>()
      .select(" JSON_MERGE_PATCH(t1.expand_fields,t2.expand_fields, t.expand_fields) AS expand_fields") // 把主表跟明细的扩展字段组合起来
      .selectAll(InOrder.class, InOrder::getExpandFields)
      .selectAll(InOrderDetail.class, InOrderDetail::getExpandFields)
      .innerJoin(InOrder.class, InOrder::getOrderId, InOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, InOrderDetail::getProductId)
      .in(InOrder::getOrderId, ids)
      .in(InOrder::getOrderStatus, statusList.stream().map(InOrderStatusEnum::getName).toList())

      .apply("t.quantity > IFNULL(t.enter_quantity, 0)")
      .ge(InOrderDetail::getQuantity, 0)
      .orderByAsc(InOrderDetail::getOrderDetailId);

    List<Map<String, Object>> orderList = this.selectJoinMaps(wrapper);
    if (ArrayUtil.isEmpty(orderList) || orderList.isEmpty()) {
      throw new ServiceException("未找到对应的订单明细数据");
    }
//    Assert.isFalse(B.isEqual(orderList.get(0).get("crossDocking"), NumberUtils.ONE), "越库预到货单不允许操作！");

    // 开启SN管理状态处理
    orderList.forEach(item -> {
      //开支之后匹配规则
      if (in_isAuditAfter) {
        RecommendPositionDto recommendPsoitionDto = new RecommendPositionDto();// 复制数据
        recommendPsoitionDto.setInQty(Convert.toBigDecimal(item.get("quantity")));
        recommendPsoitionDto.setPlateCode(Convert.toStr(item.get("plateCode")));
        recommendPsoitionDto.setBatchNumber(Convert.toStr(item.get("batchNumber")));
        recommendPsoitionDto.setProductId(Convert.toLong(item.get("productId")));
        recommendPsoitionDto.setProductSpec(Convert.toStr(item.get("productSpec")));
        recommendPsoitionDto.setConsignorId(Convert.toLong(item.get("consignorId")));
        recommendPsoitionDto.setStorageId(Convert.toLong(item.get("storageId")));
        recommendPsoitionDto.setProviderId(Convert.toLong(item.get("providerId")));
        recommendPsoitionDto.setWeight(Convert.toBigDecimal(item.get("weight")));
        recommendPsoitionDto.setUnitCube(Convert.toBigDecimal(item.get("unitCube")));
        String positionName = recommendPositionService.getRecommendPosition(recommendPsoitionDto);
        item.put("positionName", positionName);
      }

      // 获取货位类型
      Long storageId = Convert.toLong(item.get("storageId"));
      String positionName = Convert.toStr(item.get("positionName"));
      BasePosition positionInfo = basePositionService.getByName(storageId, positionName);
      if (ObjectUtil.isNotNull(positionInfo)) {
        item.put("positionType", positionInfo.getPositionType());
      }

      if (B.isEqual(Convert.toByte(item.get("crossDocking")), EnableEnum.ENABLE.getId())) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("positionName", "NO-STOCK");
        maps.put("finishedQuantity", B.sub(Convert.toBigDecimal(item.get("quantity")), Convert.toBigDecimal(item.get("enterQuantity"))));

        List<Map<String, Object>> listMap = new ArrayList<>();
        listMap.add(maps);
        item.put("positionNames", listMap);
      }

      // 将json字符串转为对象
      item.put("expandFields", JSONUtil.parse(item.get("expandFields")));
    });
    return R.ok(orderList);
  }

  @Override
  public R<List<Map<String, Object>>> getQuickEnterLists(List<Long> ids) {
    R<List<Map<String, Object>>> quickEnterList = this.getQuickEnterList(ids);
    List<Map<String, Object>> data = quickEnterList.getData();

    // 根据 "category" 键进行分组
    Map<String, List<Map<String, Object>>> grouped = data.stream()
      .collect(Collectors.groupingBy(map -> (String) map.get("sourceCode")));
    if (B.isGreater(grouped.size(), 1)) {
      return R.fail("只能选择同一个送货行号！");
    }
    return quickEnterList;
  }

  @Override
  public InOrderDetail selectByMainId(Long orderId) {
    LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(InOrderDetail::getOrderId, orderId)
      .last("limit 1");
    return this.getOnly(queryWrapper);
  }


  @Override
  public InOrderDetailVo selectOne(List<QueryBo> queryBoList) {
    QueryWrapper<InOrderDetail> wrapper = BuildWrapperHelper.createWrapper(queryBoList, InOrderDetail.class); // 构建查询条件
    wrapper.last("limit 1");
    InOrderDetailVo inOrderDetailVo = this.baseMapper.selectVoOne(wrapper);
    //查询主表的 到货日期
    IInOrderService bean = SpringUtils.getBean(IInOrderService.class);
    InOrder orderInfo = bean.getById(inOrderDetailVo.getOrderId());
    inOrderDetailVo.getExpandFields().put("arrivedDate", orderInfo.getArrivedDate());
    inOrderDetailVo.getExpandFields().put("storageCode", orderInfo.getStorageCode());
    return inOrderDetailVo;
  }

  @Override
  public void upCheckingStatus(InOrderStatusEnum inOrderStatusEnum, Long orderId) {
    LambdaUpdateWrapper<InOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(InOrderDetail::getIsChecking, inOrderStatusEnum.getId())
      .eq(InOrderDetail::getOrderId, orderId);
  }

  @Override
  public boolean batchRemove(Long orderId) {
    LambdaUpdateWrapper<InOrderDetail> removeWrapper = new LambdaUpdateWrapper<>();
    removeWrapper.eq(InOrderDetail::getOrderId, orderId);
    return this.remove(removeWrapper);
  }
}
