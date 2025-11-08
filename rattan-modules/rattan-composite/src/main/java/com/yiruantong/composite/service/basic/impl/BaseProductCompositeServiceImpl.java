package com.yiruantong.composite.service.basic.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.composite.service.basic.IBaseProductCompositeService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品信息操作
 */
@RequiredArgsConstructor
@Service
public class BaseProductCompositeServiceImpl implements IBaseProductCompositeService {
  private final IBaseProductService baseProductService;
  private final ICoreInventoryService coreInventoryService;
  private final IBaseConsignorService baseConsignorService;

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
      // 查询当前商品信息
      BaseProduct baseProduct = baseProductService.getById(id);

      // 查询当前商品下的库存数据
      LambdaQueryWrapper<CoreInventory> coreInventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
      coreInventoryLambdaQueryWrapper.eq(CoreInventory::getProductCode, baseProduct.getProductCode()).gt(CoreInventory::getProductStorage, BigDecimal.ZERO);
      var coreList = coreInventoryService.selectList(coreInventoryLambdaQueryWrapper);
      if (!coreList.isEmpty()) {
        throw new ServiceException("当前商品下【" + baseProduct.getProductCode() + "】存在库存，不允许删除！");
      }

    }
    // 执行删除
    return baseProductService.deleteByIds(Ids);
  }

  @Override
  public TableDataInfo<BaseProductVo> getProductList(PageQuery pageQuery) {

    TableDataInfo<BaseProductVo> tableDataInfo = baseProductService.pageList(pageQuery);
    for (BaseProductVo info : tableDataInfo.getRows()) {

      //查询库存数量， 库存数量统计货主类型为 总公司的数据
      LambdaQueryWrapper<BaseConsignor> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(BaseConsignor::getConsignorId, info.getConsignorId()).eq(BaseConsignor::getConsignorType, "总公司");
      BaseConsignor consignor = baseConsignorService.getOne(queryWrapper);
      if (ObjectUtil.isNotNull(consignor)) {
        //查询总库存
        LambdaQueryWrapper<CoreInventory> coreInventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        coreInventoryLambdaQueryWrapper.select(CoreInventory::getValidStorage).eq(CoreInventory::getConsignorId, consignor.getConsignorId()).eq(CoreInventory::getProductId, info.getProductId());
        List<CoreInventory> list = coreInventoryService.getBaseMapper().selectList(coreInventoryLambdaQueryWrapper);
        if (ObjectUtil.isNotNull(list) && B.isGreater(list.size())) {
          Map<String, Object> expandFields = info.getExpandFields();
          if (ObjectUtil.isNull(expandFields)) {
            expandFields = new HashMap<>();

          }
          BigDecimal reduce = list.stream().filter(i -> B.isGreater(i.getValidStorage())).map(CoreInventory::getValidStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
          expandFields.put("validStorage", reduce);
        }
      }


    }
    return tableDataInfo;
  }
}
