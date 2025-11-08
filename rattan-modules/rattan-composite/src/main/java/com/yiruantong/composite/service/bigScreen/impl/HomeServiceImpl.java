package com.yiruantong.composite.service.bigScreen.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.composite.service.bigScreen.HomeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {

  //#region 查询库存预警
  @Override
  public R<Map<String, Object>> inventoryAlert(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));

    // 第一列数据
    final Map<String, Object> column1Map = inventoryAlertColumn1(storageId, consignorId);
    final Map<String, Object> column2Map = inventoryAlertColumn2(storageId, consignorId);
    final Map<String, Object> column3Map = inventoryAlertColumn3(storageId, consignorId);
    final Map<String, Object> column4Map = inventoryAlertColumn4(storageId, consignorId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();

    List<Map<String, Object>> columns = new ArrayList<>();
    // 4列
    for (int i = 0; i < 4; i++) {
      Map<String, Object> column = new HashMap<>();
      if (i == 0) {
        column.put("num1", column1Map.get("positionCount")); // 高于上限
        column.put("num2", column1Map.get("ratio")); //
      } else if (i == 1) {
        column.put("num1", column2Map.get("positionCount")); // 低于上限
        column.put("num2", column2Map.get("ratio")); //
      } else if (i == 2) {
        column.put("num1", column3Map.get("adventCount")); // 临期商品数量
        column.put("num2", column3Map.get("ratio")); //
      } else if (i == 3) {
        column.put("num1", column4Map.get("expiredCount")); // 到期商品数量
        column.put("num2", column4Map.get("ratio")); //
      }

      columns.add(column);
    }
    resultData.put("columns", columns);

    return R.ok(resultData);
  }

  /**
   * 第一列数据（库存高于上限）
   */
  private Map<String, Object> inventoryAlertColumn1(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and a.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and a.consignor_id='{}'
        """, consignorId);
    }
    // 查询库存高于上限
    String sql = StringUtils.format("""
      SELECT count(*) as positionCount
      FROM (
      	select a.storage_id, a.position_name
      	FROM core_inventory a INNER JOIN base_position p on a.storage_id=p.storage_id and a.position_name=p.position_name
      	WHERE a.tenant_id='{}' {}
      	GROUP BY a.storage_id, a.position_name
      	HAVING sum(product_storage)>max(p.max_capacity)
      	ORDER BY a.position_name
      ) t
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long positionCount =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("positionCount")):0; // 获取的库存高于上限值

    String where2 = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where2 = StringUtils.format("""
        and storage_id='{}'
        """, storageId);
    }
    // 查询库存总数
    sql = StringUtils.format("""
      SELECT count(*) as allPositionCount FROM base_position WHERE tenant_id='{}' {}
      """, LoginHelper.getTenantId(), where2);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long allPositionCount =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("allPositionCount")):0; // 获取的库存总数

    Map<String, Object> result = new HashMap<>();
    result.put("positionCount", positionCount);
    result.put("allPositionCount", allPositionCount);
    if (!B.isEqual(positionCount)) {
      BigDecimal ratio = B.div(allPositionCount, positionCount); // 库存总数/库存上限量 / 100
      ratio = B.div(Optional.ofNullable(ratio), 100);
      DecimalFormat df = new DecimalFormat("#.####");
      result.put("ratio", df.format(ratio) + "%");
    } else {
      result.put("ratio", 0); // 库存总数/库存上限量
    }

    return result;
  }

  /**
   * 第二列数据（库存低于上限）
   */
  private Map<String, Object> inventoryAlertColumn2(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and a.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and a.consignor_id='{}'
        """, consignorId);
    }
    String sql = StringUtils.format("""
      SELECT count(*) as positionCount
      FROM (
      	select a.storage_id, a.position_name
      	FROM core_inventory a INNER JOIN base_position p on a.storage_id=p.storage_id and a.position_name=p.position_name
      	WHERE a.tenant_id='{}' {}
      	GROUP BY a.storage_id, a.position_name
      	HAVING sum(product_storage)>max(p.min_capacity)
      	ORDER BY a.position_name
      ) t
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long positionCount =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("positionCount")):0; // 获取的库存高于下限值

    String where2 = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where2 = StringUtils.format("""
        and storage_id='{}'
        """, storageId);
    }

    sql = StringUtils.format("""
      SELECT count(*) as allPositionCount FROM base_position WHERE tenant_id='{}' {}
      """, LoginHelper.getTenantId(), where2);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long allPositionCount = ObjectUtil.isNotNull(dataInfo)?Convert.toLong(dataInfo.get("allPositionCount")):0; // 获取的库存总数

    Map<String, Object> result = new HashMap<>();
    result.put("positionCount", positionCount);
    result.put("allPositionCount", allPositionCount);
    if (!B.isEqual(positionCount)) {
      BigDecimal ratio = B.div(allPositionCount, positionCount); // 库存总数/库存下限量 / 100
      ratio = B.div(Optional.ofNullable(ratio), 100);
      DecimalFormat df = new DecimalFormat("#.####");
      result.put("ratio", df.format(ratio) + "%");
    } else {
      result.put("ratio", 0); // 库存总数/库存下限量
    }

    return result;
  }

  /**
   * 第三列数据（临期商品）
   */
  private Map<String, Object> inventoryAlertColumn3(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    String sql = StringUtils.format("""
      SELECT count(*) as adventCount
         FROM core_inventory t INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
         WHERE t.tenant_id='{}' {}
         AND (t.product_storage > 0 AND (IFNULL(t.shelf_life_day, 0) - DATEDIFF(NOW(), produce_date)) > 0 AND (IFNULL(t.shelf_life_day, 0) - DATEDIFF(NOW(), produce_date)) < t.shelf_life_day / 3)
      """, LoginHelper.getTenantId(), where);


    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long adventCount = ObjectUtil.isNotNull(dataInfo)?Convert.toLong(dataInfo.get("adventCount")):0; // 获取的临期商品数量（库存有效期天数>0且小于1/3）

    String where2 = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where2 = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where2 += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    sql = StringUtils.format("""
      SELECT count(*) as allAdventCount
      FROM core_inventory t
      INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
      INNER JOIN base_product t2 ON (t.product_id = t2.product_id) AND t2.tenant_id = '{}' WHERE (t.product_storage > 0) {}
      """, LoginHelper.getTenantId(), where2);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long allAdventCount =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("allAdventCount")):0; // 获取的保质期预警总数

    Map<String, Object> result = new HashMap<>();
    result.put("adventCount", adventCount);
    result.put("allAdventCount", allAdventCount);
    if (!B.isEqual(adventCount)) {
      BigDecimal ratio = B.div(allAdventCount, adventCount); // 保质期预警总数/临期数量 / 100
      ratio = B.div(Optional.ofNullable(ratio), 100);
      DecimalFormat df = new DecimalFormat("#.####");
      result.put("ratio", df.format(ratio) + "%");
    } else {
      result.put("ratio", 0); // 库存总数/库存上限量
    }

    return result;
  }

  /**
   * 第四列数据（到期商品）
   */
  private Map<String, Object> inventoryAlertColumn4(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    String sql = StringUtils.format("""
       SELECT count(*) as expiredCount
         FROM core_inventory t INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
         INNER JOIN base_product t2 ON (t.product_id = t2.product_id)
       WHERE t.tenant_id='{}' {} AND (t.product_storage > 0 AND (IFNULL(t.shelf_life_day, 0) - datediff(NOW(), produce_date)) < 0)
      """, LoginHelper.getTenantId(), where);

    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long expiredCount =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("expiredCount")):0; // 获取的到期商品数量

    String where2 = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where2 = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where2 += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    sql = StringUtils.format("""
      SELECT count(*) as allAdventCount
      FROM core_inventory t
      INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
      INNER JOIN base_product t2 ON (t.product_id = t2.product_id) AND t2.tenant_id = '{}' WHERE (t.product_storage > 0) {}
      """, LoginHelper.getTenantId(), where2);
    dataInfo = SqlRunner.db().selectOne(sql);
    Long allAdventCount =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("allAdventCount")):0; // 获取的保质期预警总数

    Map<String, Object> result = new HashMap<>();
    result.put("expiredCount", expiredCount);
    result.put("allAdventCount", allAdventCount);
    if (!B.isEqual(expiredCount)) {
      BigDecimal ratio = B.div(allAdventCount, expiredCount); // 保质期预警总数/到期数量 / 100
      ratio = B.div(Optional.ofNullable(ratio), 100);
      DecimalFormat df = new DecimalFormat("#.####");
      result.put("ratio", df.format(ratio) + "%");
    } else {
      result.put("ratio", 0); // 库存总数/库存上限量
    }

    return result;
  }

  //#endregion

  //#region 查询库存状况
  @Override
  public R<Map<String, Object>> inventoryStatus(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));

    // 第一列数据
    final Map<String, Object> column1Map = inventoryStatusColumn1(storageId, consignorId);
    final Map<String, Object> column2Map = inventoryStatusColumn2(storageId, consignorId);
    final Map<String, Object> column3Map = inventoryStatusColumn3(storageId, consignorId);
    final Map<String, Object> column4Map = inventoryStatusColumn4(storageId, consignorId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();

    List<Map<String, Object>> columns = new ArrayList<>();
    // 4列
    for (int i = 0; i < 4; i++) {
      Map<String, Object> column = new HashMap<>();
      if (i == 0) {
        column.put("name", "库存概况"); // 标题
        column.put("name2", column1Map.get("allProductStorage")); //
      } else if (i == 1) {
        column.put("name", "待处理单据"); // 标题
        column.put("name2", column2Map.get("allOrderCount")); //
      } else if (i == 2) {
        column.put("name", "入库概况"); // 标题
        column.put("name2", column3Map.get("allInOrderCount")); //
      } else if (i == 3) {
        column.put("name", "出库概况"); // 标题
        column.put("name2", column4Map.get("allOutOrderCount")); //
      }


      // 每列中的明细数据
      List<Map<String, Object>> rows = new ArrayList<>();
      Map<String, Object> row = new HashMap<>();

      //#region
      if (i == 0) {
        row.put("title", "库存重量");
        row.put("value", column1Map.get("allRowWeight"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "库存金额");
        row.put("value", column1Map.get("allPurchaseAmount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "缺货数量");
        row.put("value", column1Map.get("totalQuantityOrder"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "滞销数量");
        row.put("value", column1Map.get("totalEnterQuantity"));
        rows.add(row);
        column.put("rows", rows);
      } else if (i == 1) {
        row = new HashMap<>();
        row.put("title", "待入库单据");
        row.put("value", column2Map.get("inOrderCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "待上架单据");
        row.put("value", column2Map.get("inShelveCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "待拣货单据");
        row.put("value", column2Map.get("pickingCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "待出库单据");
        row.put("value", column2Map.get("outOrderCount"));
        rows.add(row);
        column.put("rows", rows);
      } else if (i == 2) {
        row = new HashMap<>();
        row.put("title", "今日入库单据");
        row.put("value", column3Map.get("todayCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "昨日入库单据");
        row.put("value", column3Map.get("yesterDayCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "入库重量");
        row.put("value", column3Map.get("totalWeight"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "入库金额");
        row.put("value", column3Map.get("totalAmount"));
        rows.add(row);
        column.put("rows", rows);
      } else if (i == 3) {
        row = new HashMap<>();
        row.put("title", "今日出库单据");
        row.put("value", column4Map.get("todayCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "昨日出库单据");
        row.put("value", column4Map.get("yesterDayCount"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "出库重量");
        row.put("value", column4Map.get("totalWeight"));
        rows.add(row);

        row = new HashMap<>();
        row.put("title", "出库金额");
        row.put("value", column4Map.get("totalAmount"));
        rows.add(row);

        column.put("rows", rows);
      }
      columns.add(column);
      //#endregion
    }
    resultData.put("columns", columns);
    return R.ok(resultData);
  }

  /**
   * 第一列数据（库存概况）
   */
  private Map<String, Object> inventoryStatusColumn1(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }
    // 查询库存总量
    String sql = StringUtils.format("""
      SELECT SUM(product_storage) AS allProductStorage
      FROM core_inventory t INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
      INNER JOIN base_product t2 ON (t.product_id = t2.product_id)  WHERE (t.product_storage > 0) AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long allProductStorage =ObjectUtil.isNotNull(dataInfo)? Convert.toLong(dataInfo.get("allProductStorage")):0; // 获取的库存总量


    // 查询库存总重量
    sql = StringUtils.format("""
      SELECT SUM(row_weight) AS allRowWeight
      FROM core_inventory t INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
      INNER JOIN base_product t2 ON (t.product_id = t2.product_id)  WHERE (t.product_storage > 0) AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal allRowWeight =ObjectUtil.isNotNull(dataInfo)? Convert.toBigDecimal(dataInfo.get("allRowWeight")):BigDecimal.ZERO; // 查询库存总重量(kg)

    // 查询库存总金额
    sql = StringUtils.format("""
      SELECT SUM(purchase_amount) AS allPurchaseAmount
      FROM core_inventory t INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
      INNER JOIN base_product t2 ON (t.product_id = t2.product_id)  WHERE (t.product_storage > 0) AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal allPurchaseAmount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("allPurchaseAmount")):BigDecimal.ZERO; // 查询库存总金额

    // 查询出库单缺货总数量
    sql = StringUtils.format("""
      SELECT ROUND(SUM(total_quantity_order)) AS totalQuantityOrder
      FROM out_order t WHERE (t.sorting_status = '3' AND t.source_type <> '调拨在途虚拟出库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal totalQuantityOrder =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("totalQuantityOrder")):BigDecimal.ZERO; // 查询出库单缺货总数量


    // 滞销数量：仓库内入库三个月以上未出库商品数量
    sql = StringUtils.format("""
      SELECT ROUND(SUM(total_enter_quantity)) AS totalEnterQuantity
      FROM in_enter t
      WHERE (t.enter_status = '确认入库' AND t.apply_date <= CURDATE() - INTERVAL 3 MONTH) AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal totalEnterQuantity = ObjectUtil.isNotNull(dataInfo)? Convert.toBigDecimal(dataInfo.get("totalEnterQuantity")):BigDecimal.ZERO; // 滞销数量：仓库内入库三个月以上未出库商品数量

    Map<String, Object> result = new HashMap<>();
    result.put("allProductStorage", "库存量：" + allProductStorage); // 库存总量

    allRowWeight = B.div(Optional.ofNullable(allRowWeight), 2000);
    DecimalFormat df = new DecimalFormat("#.####");
    result.put("allRowWeight", df.format(allRowWeight) + "吨"); // 库存总重量

    DecimalFormat df2 = new DecimalFormat("#");
    result.put("allPurchaseAmount", df2.format(allPurchaseAmount) + "元"); // 库存总金额
    result.put("totalQuantityOrder", totalQuantityOrder); // 出库单缺货总数量
    result.put("totalEnterQuantity", totalEnterQuantity); // 出库单缺货总数量


    return result;
  }

  /**
   * 第二列数据（待处理单据）
   */
  private Map<String, Object> inventoryStatusColumn2(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    // 待入库单据数
    String sql = StringUtils.format("""
      SELECT count(*) as inOrderCount
      FROM in_order t WHERE ((t.order_status = '部分交货' OR t.order_status = '审核成功') AND t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal inOrderCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("inOrderCount")):BigDecimal.ZERO; // 待入库单据数

    // 待上架单据数
    sql = StringUtils.format("""
      SELECT count(*) as inShelveCount
      FROM in_order t
      WHERE (t.order_status = '完全交货' AND (t.shelve_status = '待上架' OR t.shelve_status = '部分上架') AND t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal inShelveCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("inShelveCount")):BigDecimal.ZERO; // 待上架单据数

    // 待拣货单据
    sql = StringUtils.format("""
      SELECT count(*) as pickingCount FROM out_order t
      INNER JOIN out_order_wave o on o.order_wave_code=t.order_wave_code
      WHERE (t.order_status = '审核成功' AND t.sorting_status = '2' AND t.source_type <> '调拨在途虚拟出库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal pickingCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("pickingCount")):BigDecimal.ZERO; // 待拣货单据数


    // 待出库单据
    sql = StringUtils.format("""
      SELECT count(*) as outOrderCount
      FROM out_order t
      WHERE (t.picking_status = '拣货完成' AND t.match_status IN ('部分配货', '配货完成', '部分打包') AND t.source_type <> '调拨在途虚拟出库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal outOrderCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("outOrderCount")):BigDecimal.ZERO; // 待出库单据数

    Map<String, Object> result = new HashMap<>();
    result.put("inOrderCount", inOrderCount); // 待入库单据数
    result.put("inShelveCount", inShelveCount); // 待上架单据数
    result.put("pickingCount", pickingCount); // 待拣货单据数
    result.put("outOrderCount", outOrderCount); // 待出库单据数
    result.put("allOrderCount", "单据单数：" + B.adds(inOrderCount, inShelveCount, pickingCount, outOrderCount)); // 单据单数

    return result;
  }

  /**
   * 第三列数据（入库概况）
   */
  private Map<String, Object> inventoryStatusColumn3(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    // 今日入库单据
    String sql = StringUtils.format("""
      SELECT count(*) as todayCount
      FROM in_order t
      WHERE DATE(t.create_time) = CURDATE() AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal todayCount = ObjectUtil.isNotNull(dataInfo)? Convert.toBigDecimal(dataInfo.get("todayCount")):BigDecimal.ZERO; // 今日入库单据

    // 昨日入库单据
    sql = StringUtils.format("""
      SELECT count(*) as yesterDayCount
      FROM in_order t
      WHERE (t.source_type <> '调拨在途虚拟入库') AND (t.create_time = CURDATE() - INTERVAL 1 DAY) AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal yesterDayCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("yesterDayCount")):BigDecimal.ZERO; // 昨日入库单据

    // 合计入库重量
    sql = StringUtils.format("""
      SELECT SUM(total_weight) AS totalWeight
      FROM in_order t WHERE (t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal totalWeight =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("totalWeight")):BigDecimal.ZERO; // 合计入库重量


    // 入库金额
    sql = StringUtils.format("""
      SELECT SUM(total_amount) AS totalAmount
      FROM in_order t WHERE (t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal totalAmount = ObjectUtil.isNotNull(dataInfo)? Convert.toBigDecimal(dataInfo.get("totalAmount")):BigDecimal.ZERO; // 入库金额

    // 入库单数量
    sql = StringUtils.format("""
      SELECT count(*) as allInOrderCount
      FROM in_order t WHERE (t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal allInOrderCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("allInOrderCount")):BigDecimal.ZERO; // 入库单数量

    Map<String, Object> result = new HashMap<>();
    result.put("todayCount", todayCount); // 今日入库单数
    result.put("yesterDayCount", yesterDayCount); // 昨日入库单据

    DecimalFormat df = new DecimalFormat("#.####");
    result.put("totalWeight", df.format(B.div(Optional.ofNullable(totalWeight), 2000)) + "吨"); // 入库重量

    result.put("totalAmount", totalAmount); // 入库金额
    result.put("allInOrderCount", "入库单数量：" + allInOrderCount); // 入库单数量

    return result;
  }

  /**
   * 第四列数据（出库概况）
   */
  private Map<String, Object> inventoryStatusColumn4(Long storageId, Long consignorId) {
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    if (ObjectUtil.isNotNull(consignorId)) {
      where += StringUtils.format("""
        and t.consignor_id='{}'
        """, consignorId);
    }

    // 今日出库单据
    String sql = StringUtils.format("""
      SELECT count(*) as todayCount
      FROM out_order t
      WHERE (t.source_type <> '调拨在途虚拟出库') AND DATE(t.create_time) = CURDATE() AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal todayCount = ObjectUtil.isNotNull(dataInfo)? Convert.toBigDecimal(dataInfo.get("todayCount")):BigDecimal.ZERO; // 今日出库单据

    // 昨日出库单据
    sql = StringUtils.format("""
      SELECT count(*) as yesterDayCount
      FROM out_order t
      WHERE (t.source_type <> '调拨在途虚拟出库') AND (t.create_time = CURDATE() - INTERVAL 1 DAY) AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal yesterDayCount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("yesterDayCount")):BigDecimal.ZERO; // 昨日出库单据

    // 合计出库重量
    sql = StringUtils.format("""
      SELECT SUM(total_weight) AS totalWeight
      FROM out_order t WHERE (t.source_type <> '调拨在途虚拟出库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal totalWeight =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("totalWeight")):BigDecimal.ZERO; // 合计出库重量


    // 出库金额
    sql = StringUtils.format("""
      SELECT SUM(total_amount) AS totalAmount
      FROM out_order t WHERE (t.source_type <> '调拨在途虚拟出库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal totalAmount =ObjectUtil.isNotNull(dataInfo)?  Convert.toBigDecimal(dataInfo.get("totalAmount")):BigDecimal.ZERO; // 出库金额

    // 出库单数量
    sql = StringUtils.format("""
      SELECT count(*) as allOutOrderCount
      FROM out_order t WHERE (t.source_type <> '调拨在途虚拟出库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    dataInfo = SqlRunner.db().selectOne(sql);
    BigDecimal allOutOrderCount = ObjectUtil.isNotNull(dataInfo)? Convert.toBigDecimal(dataInfo.get("allOutOrderCount")):BigDecimal.ZERO; // 出库单数量

    Map<String, Object> result = new HashMap<>();
    result.put("todayCount", todayCount); // 今日出库单数
    result.put("yesterDayCount", yesterDayCount); // 昨日出库单据

    DecimalFormat df = new DecimalFormat("#.####");
    result.put("totalWeight", df.format(B.div(Optional.ofNullable(totalWeight), 2000)) + "吨"); // 出库重量

    result.put("totalAmount", totalAmount); // 出库金额
    result.put("allOutOrderCount", "出库单数量：" + allOutOrderCount); // 出库单数量

    return result;
  }

  //#endregion

  //#region 查询库存金额（饼图）

  @Override
  public R<Map<String, Object>> inventoryAmount(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));

//    pcPieChart
    final List<Map<String, Object>> columnMap = inventoryAmountColumn(storageId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();
    List<String> storageName= new ArrayList<>();
    List<String> ratio= new ArrayList<>();
    for (var item : columnMap) {
      storageName.add(Convert.toStr(item.get("storageName")));
      ratio.add(Convert.toStr(item.get("ratio")));
    }
    // 以逗号形式隔开
    resultData.put("storageName", storageName.stream().collect(Collectors.joining(",")));
    resultData.put("ratio", ratio.stream().collect(Collectors.joining(",")));
    return R.ok(resultData);
  }

  /**
   * 查询库存金额（饼图）
   */
  private List<Map<String, Object>> inventoryAmountColumn(Long storageId) {

    // 库存金额统计
    //不同仓库所有产品金额相加占比（库存明细查询可以找到）
    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t3.storage_id='{}'
        """, storageId);
    }

    String sql = StringUtils.format("""
      WITH TotalStockValue AS (
        SELECT t3.storage_id as storageId,t3.storage_name as storageName,t.tenant_id, SUM(purchase_amount) as purchaseAmount
        FROM core_inventory t
        INNER JOIN base_position t1 ON t.storage_id = t1.storage_id AND t.position_name = t1.position_name
        INNER JOIN base_product t2 ON t.product_id = t2.product_id
        INNER JOIN base_storage t3 ON t.storage_id = t3.storage_id
        WHERE t.product_storage > 0 and t.tenant_id='{}' {}
        GROUP BY t3.storage_id,t3.storage_name
     ),

         OverallTotal AS (
             SELECT SUM(purchase_amount) as allPurchaseAmount
             FROM core_inventory t
            INNER JOIN base_position t1 ON t.storage_id = t1.storage_id AND t.position_name = t1.position_name
            INNER JOIN base_product t2 ON t.product_id = t2.product_id
            INNER JOIN base_storage t3 ON t.storage_id = t3.storage_id
             WHERE (t.product_storage > 0)
         )

         SELECT i.storageId, i.storageName, i.purchaseAmount, ROUND(100.0 * i.purchaseAmount / o.allPurchaseAmount,4) AS ratio
         FROM TotalStockValue i
         CROSS JOIN
             OverallTotal o
       ORDER BY i.storageId,i.storageName
      """,LoginHelper.getTenantId(), where);
    List<Map<String, Object>> dataInfo = SqlRunner.db().selectList(sql);

    return dataInfo;
  }


  //#endregion

  //#region 查询仓储使用情况
  @Override
  public R<Map<String, Object>> storageStatus(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));

    final List<Map<String, Object>> tableData = storageStatusData(storageId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();
    resultData.put("tableData", tableData);

    return R.ok(resultData);
  }


  /**
   * 仓库使用情况，各仓库库存数量重量金额（库存明细查询可以找到）
   */
  private List<Map<String, Object>> storageStatusData(Long storageId) {

    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    // 仓库使用情况，各仓库库存数量重量金额（库存明细查询可以找到）
    String sql = StringUtils.format("""
      SELECT t3.storage_id,t3.storage_name,FLOOR(SUM(product_storage)) as productStorage,
      ROUND(SUM(row_weight / 2000),4) as rowWeight,SUM(purchase_amount) as purchaseAmount
       FROM core_inventory t
       INNER JOIN base_position t1 ON (t.storage_id = t1.storage_id AND t.position_name = t1.position_name)
       INNER JOIN base_product t2 ON (t.product_id = t2.product_id)
       INNER JOIN base_storage t3 ON (t.storage_id = t3.storage_id)
       WHERE (t.product_storage > 0 AND t.tenant_id = '{}' {})
      GROUP BY
        t3.storage_id,t3.storage_name
      """, LoginHelper.getTenantId(), where);
    List<Map<String, Object>> dataInfo = SqlRunner.db().selectList(sql);

    return dataInfo;
  }
  //#endregion

  //#region 货位库存统计
  @Override
  public R<Map<String, Object>> inventoryStatistics(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));

    final List<Map<String, Object>> tableData = inventoryStatisticsData(storageId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();
    resultData.put("tableData", tableData);

    return R.ok(resultData);
  }


  /**
   * 货位库存统计
   * 各仓库不同货位类型，所有货位的库存数量重量金额统计（库存明细查询可以找到）
   *
   * @param storageId
   * @return
   */
  private List<Map<String, Object>> inventoryStatisticsData(Long storageId) {

    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    // 仓库使用情况，各仓库库存数量重量金额（库存明细查询可以找到）
    String sql = StringUtils.format("""
      SELECT
          t.storage_id, t.storage_name,
          t1.position_type,
          SUM(product_storage) AS productStorage,
          SUM(row_weight) AS rowWeight,
          SUM(purchase_amount) AS purchaseAmount
      FROM
          core_inventory t
      INNER JOIN base_position t1 ON t.storage_id = t1.storage_id AND t.position_name = t1.position_name
      INNER JOIN base_product t2 ON t.product_id = t2.product_id
      INNER JOIN base_storage t3 ON t.storage_id = t3.storage_id
      WHERE t.product_storage > 0 and t.tenant_id='{}' {}
      GROUP BY
          t.storage_id,  t.storage_name,
          t1.position_type;
       """, LoginHelper.getTenantId(), where);
    List<Map<String, Object>> dataInfo = SqlRunner.db().selectList(sql);

    return dataInfo;
  }
  //#endregion

  //#region 库存商品类别
  @Override
  public R<Map<String, Object>> inventoryProductType(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));

    final List<Map<String, Object>> columnMap = inventoryProductTypeColumn(storageId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();
    List<String> typeName= new ArrayList<>();
    List<String> ratio= new ArrayList<>();
    for (var item : columnMap) {
      typeName.add(Convert.toStr(item.get("typeName")));
      ratio.add(Convert.toStr(item.get("ratio")));
    }
    // 以逗号形式隔开
    resultData.put("typeName", typeName.stream().collect(Collectors.joining(",")));
    resultData.put("ratio", ratio.stream().collect(Collectors.joining(",")));
    return R.ok(resultData);
  }

  /**
   * 库存商品类别
   * 按类别统计，一级类别下所有的产品数量相加，只显示前五的
   *
   * @param storageId
   * @return
   */
  private List<Map<String, Object>> inventoryProductTypeColumn(Long storageId) {

    String where = "";
    if (ObjectUtil.isNotNull(storageId)) {
      where = StringUtils.format("""
        and t.storage_id='{}'
        """, storageId);
    }
    // 仓库使用情况，各仓库库存数量重量金额（库存明细查询可以找到）
    String sql = StringUtils.format("""
      WITH CategorizedInventory AS (
         SELECT t2.type_name as typeName, FLOOR(SUM(t.product_storage)) AS productStorage,t.tenant_id
          FROM core_inventory t
          INNER JOIN base_position t1 ON t.storage_id = t1.storage_id AND t.position_name = t1.position_name
          INNER JOIN base_product t2 ON t.product_id = t2.product_id
          WHERE t.tenant_id = '{}' {} AND t.product_storage > 0
          GROUP BY t2.type_name
          ORDER BY productStorage DESC
          LIMIT 5
      ),
      RankedInventory AS (
          SELECT *,ROW_NUMBER() OVER (ORDER BY productStorage DESC) AS ranks,
              SUM(productStorage) OVER () AS total_top_five
          FROM CategorizedInventory
      )
      SELECT typeName,
          productStorage,
          ROUND(productStorage * 100.0 / total_top_five,4) AS ratio
      FROM RankedInventory
      ORDER BY ranks
       """, LoginHelper.getTenantId(), where);
    List<Map<String, Object>> dataInfo = SqlRunner.db().selectList(sql);

    return dataInfo;
  }
  //#endregion
}
