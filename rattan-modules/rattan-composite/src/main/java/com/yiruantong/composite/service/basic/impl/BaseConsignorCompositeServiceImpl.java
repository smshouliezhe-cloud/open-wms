package com.yiruantong.composite.service.basic.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.BaseConsignorSalesLevel;
import com.yiruantong.basic.domain.base.bo.LevelSettingBo;
import com.yiruantong.basic.service.base.IBaseConsignorSalesLevelService;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.composite.service.basic.IBaseConsignorCompositeService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.out.IOutOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货主信息操作
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorCompositeServiceImpl implements IBaseConsignorCompositeService {
  private final IBaseConsignorService baseConsignorService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutOrderService outOrderService;
  private final IBaseConsignorSalesLevelService baseConsignorSalesLevelService;

  /**
   * 删除前事件
   *
   * @param Ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public int deleteByIds(Long[] Ids) {
    for (Long id : Ids) {
      // 查询当前货主信息
      BaseConsignor baseConsignor = baseConsignorService.getById(id);

      // 查询当前货主下的库存数据
      LambdaQueryWrapper<CoreInventory> coreInventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
      coreInventoryLambdaQueryWrapper.eq(CoreInventory::getConsignorName, baseConsignor.getConsignorName())
        .gt(CoreInventory::getProductStorage, BigDecimal.ZERO);
      var coreList = coreInventoryService.selectList(coreInventoryLambdaQueryWrapper);
      if (!coreList.isEmpty()) {
        throw new ServiceException("当前货主【" + baseConsignor.getConsignorName() + "】存在库存，不允许删除！");
      }

    }
    // 执行删除
    return baseConsignorService.deleteByIds(Ids);
  }


  //#region 销售等级设置
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Map<String, Object>> saveGrade(LevelSettingBo bo) {

    LambdaQueryWrapper<BaseConsignorSalesLevel> consignorSalesLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
    List<BaseConsignorSalesLevel> baseConsignorSalesLevels = baseConsignorSalesLevelService.list(consignorSalesLevelLambdaQueryWrapper);

    // 如果设置销售等级有值的话就清空
    if (ObjectUtils.isNotEmpty(baseConsignorSalesLevels)) {
      for (var item : baseConsignorSalesLevels) {
        baseConsignorSalesLevelService.deleteById(item.getSalesLevelId());
      }
    }
    BaseConsignorSalesLevel baseConsignorSalesLevel = new BaseConsignorSalesLevel();
    baseConsignorSalesLevel.setSalesLevelA(bo.getGradeA());
    baseConsignorSalesLevel.setSalesLevelB(bo.getGradeB());
    baseConsignorSalesLevel.setSalesLevelC(bo.getGradeC());
    baseConsignorSalesLevel.setSalesLevelS(bo.getGradeS());
    baseConsignorSalesLevelService.save(baseConsignorSalesLevel);


    return R.ok("等级设置成功");
  }
  //#endregion

  //#region 获取销售等级
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Map<String, Object>> getGrade() {
    LambdaQueryWrapper<BaseConsignorSalesLevel> queryWrapper = new LambdaQueryWrapper<>();
    var dataInfo = baseConsignorSalesLevelService.getOne(queryWrapper);

    Map<String, Object> result = new HashMap<>();
    result.put("dataList", dataInfo);

    return R.ok(result);
  }
  //#endregion

  //#region matchGrade
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void matchGrade(LoginUser loginUser) {

    // 所有门店信息(货主)
    LambdaQueryWrapper<BaseConsignor> consignorLambdaQueryWrapper = new LambdaQueryWrapper<>();
    var consignorList = baseConsignorService.list(consignorLambdaQueryWrapper);

    LambdaQueryWrapper<BaseConsignorSalesLevel> consignorSalesLevelLambdaQueryWrapper = new LambdaQueryWrapper<>();
    consignorSalesLevelLambdaQueryWrapper.last("limit 1");
    BaseConsignorSalesLevel baseConsignorSalesLevel = baseConsignorSalesLevelService.getOne(consignorSalesLevelLambdaQueryWrapper);
    // 销售等级没有设置的话，就返回
    if (ObjectUtils.isEmpty(baseConsignorSalesLevel)) {
      return;
    }
    if (ObjectUtils.isEmpty(consignorList)) {
      return;
    }

    for (var item : consignorList) {
      // 获取三个月前的日期
      DateTime prevMonth = DateUtil.offsetMonth(DateUtil.date(), -3); // 上
      // 近3个月的天数
      long daysBetween = DateUtil.between(prevMonth, DateUtil.date(), DateUnit.DAY);

      LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper
        .eq(OutOrder::getConsignorId, item.getConsignorId())
        .between(OutOrder::getAuditDate, prevMonth, DateUtil.date()); // 近三个月
      var orderList = outOrderService.list(orderLambdaQueryWrapper);


      BigDecimal totalQuantity = BigDecimal.ZERO;
      if (ObjectUtils.isNotEmpty(orderList)) {
        totalQuantity = StreamUtils.sum(orderList, OutOrder::getTotalQuantityOrder);
      }
      // 销售等级根据三个月总销量计算每日销量
      BigDecimal dailySalesVolume = B.div(totalQuantity, daysBetween);

      LambdaUpdateWrapper<BaseConsignor> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      // 小于C的时候就是C级
      if (B.isLessOrEqual(dailySalesVolume, Convert.toBigDecimal(baseConsignorSalesLevel.getSalesLevelC()))) {
        lambdaUpdateWrapper.set(BaseConsignor::getSalesGrade, "C");
      }
      if (B.isGreater(dailySalesVolume, Convert.toBigDecimal(baseConsignorSalesLevel.getSalesLevelB()))) {
        lambdaUpdateWrapper.set(BaseConsignor::getSalesGrade, "B");
      }
      if (B.isGreater(dailySalesVolume, Convert.toBigDecimal(baseConsignorSalesLevel.getSalesLevelA()))) {
        lambdaUpdateWrapper.set(BaseConsignor::getSalesGrade, "A");
      }
      if (B.isGreater(dailySalesVolume, Convert.toBigDecimal(baseConsignorSalesLevel.getSalesLevelS()))) {
        lambdaUpdateWrapper.set(BaseConsignor::getSalesGrade, "S");
      }
      lambdaUpdateWrapper.eq(BaseConsignor::getConsignorId, item.getConsignorId());
      baseConsignorService.update(lambdaUpdateWrapper);
    }
  }
  //#endregion
}
