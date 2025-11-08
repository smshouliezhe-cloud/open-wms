package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import com.yiruantong.inventory.domain.operation.bo.CoreInventoryAdviseBo;
import com.yiruantong.inventory.domain.operation.vo.CoreInventoryAdviseVo;
import com.yiruantong.inventory.mapper.operation.CoreInventoryAdviseMapper;
import com.yiruantong.inventory.service.operation.ICoreInventoryAdviseService;
import com.yiruantong.system.service.core.ICommonService;
import org.springframework.stereotype.Service;

/**
 * 建议采购转遇到货Service业务层处理
 *
 * @author YRT
 * @date 2023-12-13
 */
@RequiredArgsConstructor
@Service
public class CoreInventoryAdviseServiceImpl extends ServiceImplPlus<CoreInventoryAdviseMapper, CoreInventoryAdvise, CoreInventoryAdviseVo, CoreInventoryAdviseBo> implements ICoreInventoryAdviseService {
  private final ICommonService commonService;

  /**
   * 刷新数据
   *
   */
  @Override
  public R<Void> refreshData() {
    String sql = """
      TRUNCATE TABLE core_inventory_advise;
      INSERT INTO core_inventory_advise(product_id, product_code, product_name, storage_id, storage_name,provider_id,provider_code,provider_short_name,
      		consignor_id,consignor_code,consignor_name,product_model,storage_lower,thirty_day_sale,cycle, product_storage,thirty_day_average_sale,reserve_qty,purchase_price,advise_qty)

      SELECT product_id, product_code, product_name, storage_id, storage_name,provider_id,provider_code,provider_short_name,
      		consignor_id,consignor_code,consignor_name,product_model,storage_lower,ThirtyDaySale,Cycle, product_storage,thirty_day_average_sale,reserve_qty	,purchase_price,
      	(reserve_qty + storage_lower + thirty_day_average_sale - product_storage) AS advise_qty -- 建议采购量
      FROM (
       SELECT *,(ThirtyDaySale/30*Cycle) AS thirty_day_average_sale  -- 最近30天销量均值
      	FROM (
      		SELECT  I.product_id, I.product_code, I.product_name, pp.storage_id, pp.storage_name,I.provider_id,I.provider_code,I.provider_short_name,
      		I.consignor_id,I.consignor_code,I.consignor_name,I.product_model,pp.purchase_price,
      		I.storage_lower, -- 最低库存
        IFNULL((
      			SELECT SUM(L.quantity_order) FROM out_order M INNER JOIN out_order_detail L ON L.order_Id = M.order_Id
      			WHERE  M.storage_id=pp.storage_id AND L.product_id=I.product_id AND M.order_status IN('审核成功')
      				AND L.sorting_status=3 AND M.create_time>=DATE_SUB(NOW(), INTERVAL 1 MONTH)
      		), 0) AS reserve_qty, -- 预定数量
      		IFNULL((
      				SELECT SUM(L.quantity_order) FROM out_order M INNER JOIN out_order_detail L ON L.order_Id = M.order_Id
      			WHERE  M.storage_id=pp.storage_id AND L.product_id=I.product_id AND M.order_status IN('审核成功')
      				AND L.sorting_status=3 AND M.create_time>=DATE_SUB(NOW(), INTERVAL 1 MONTH)
      		), 0) AS ThirtyDaySale, -- 天销量30

      		IFNULL((SELECT cycle FROM base_brand B WHERE B.brand_id=I.brand_id), 0) AS Cycle,
      		SUM(pp.product_storage) AS product_storage -- 在库量
      		FROM   base_product I
          INNER JOIN core_inventory pp  ON  pp.product_id=I.product_id
      		GROUP BY I.product_id, I.product_code, I.product_name, pp.storage_id, pp.storage_name, I.provider_id,I.provider_code,I.provider_short_name,
      		I.consignor_id,I.consignor_code,I.consignor_name,I.product_model,I.storage_lower,pp.purchase_price
      	) t1
      ) t2
      where (reserve_qty + storage_lower + thirty_day_average_sale - product_storage)>0
      """;
    Assert.isTrue(commonService.executeSql(sql),"刷洗数据失败");
    return  R.ok("刷新数据成功！");

  }

}
