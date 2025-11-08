package com.yiruantong.composite.service.bigScreen.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.composite.service.bigScreen.InService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
@Service
public class InServiceImpl implements InService {
  //#region 查询入库统计
  @Override
  public R<Map<String, Object>> inOrderStat(Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));

    // 第一列数据
    final Map<String, Object> column1Map = inOrderDataColumn1(storageId, consignorId);
    final Map<String, Object> column2Map = inOrderDataColumn2(storageId, consignorId);
    final Map<String, Object> column3Map = inOrderDataColumn3(storageId, consignorId);
    final Map<String, Object> column4Map = inOrderDataColumn4(storageId, consignorId);

    // 创建静态数据列表
    Map<String, Object> resultData = new HashMap<>();

    List<Map<String, Object>> columns = new ArrayList<>();
    // 4列
    for (int i = 0; i < 4; i++) {
      Map<String, Object> column = new HashMap<>();
      if (i == 0) {
        column.put("num1", column1Map.get("inOrderCount")); // 已入库单据
      } else if (i == 1) {
        column.put("num1", column2Map.get("notInOrderCount")); // 未入库单据
      } else if (i == 2) {
        column.put("num1", column3Map.get("countEnterQuantity")); // 已入库数量
      } else {
        column.put("num1", column4Map.get("unCountEnterQuantity")); // 未入库数量
      }

      columns.add(column);
    }
    resultData.put("columns", columns);

    return R.ok(resultData);
  }

  /**
   * 第一列数据（已入库单据数）
   */
  private Map<String, Object> inOrderDataColumn1(Long storageId, Long consignorId) {
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
    // 已入库单据
    String sql = StringUtils.format("""
      SELECT count(*) as inOrderCount FROM in_order t WHERE (t.order_status = '完全交货' AND t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long inOrderCount = Convert.toLong(dataInfo.get("inOrderCount")); // 已入库单据


    Map<String, Object> result = new HashMap<>();
    result.put("inOrderCount", inOrderCount + "单");

    return result;
  }

  /**
   * 第二列数据（未入库单据）
   */
  private Map<String, Object> inOrderDataColumn2(Long storageId, Long consignorId) {
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
    // 未入库单据
    String sql = StringUtils.format("""
      SELECT count(*) as notInOrderCount FROM in_order t WHERE ((t.order_status = '审核成功' OR t.order_status = '部分交货') AND t.source_type <> '调拨在途虚拟入库') AND t.tenant_id = '{}' {}
      """, LoginHelper.getTenantId(), where);
    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long notInOrderCount = Convert.toLong(dataInfo.get("notInOrderCount")); // 未入库单据


    Map<String, Object> result = new HashMap<>();
    result.put("notInOrderCount", notInOrderCount + "单");

    return result;
  }

  /**
   * 第三列数据（已入库数量）
   */
  private Map<String, Object> inOrderDataColumn3(Long storageId, Long consignorId) {
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
    // 已入库数量
    String sql = StringUtils.format("""
      		select FLOOR(SUM(enter_quantity)) as countEnterQuantity
      		FROM in_order t
      		INNER JOIN in_order_detail t2
      		ON t.order_Id=t2.order_id
      		WHERE	t.tenant_id='{}' {} AND t2.enter_quantity>0
      """, LoginHelper.getTenantId(), where);

    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    Long countEnterQuantity = Convert.toLong(Optional.ofNullable(dataInfo).orElse(new HashMap<>()).get("countEnterQuantity")); // 已入库数量

    Map<String, Object> result = new HashMap<>();
    result.put("countEnterQuantity", ObjectUtil.defaultIfNull(countEnterQuantity, 0) + "件");

    return result;
  }

  /**
   * 第四列数据（未入库数量）
   */
  private Map<String, Object> inOrderDataColumn4(Long storageId, Long consignorId) {
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
    // 未入库数量
    String sql = StringUtils.format("""
       select FLOOR(SUM(enter_quantity)) as countEnterQuantity ,FLOOR(SUM(quantity)) as countQuantity
        FROM in_order t
        INNER JOIN in_order_detail t2
        ON t.order_Id=t2.order_id
        WHERE	t.tenant_id='000000' AND t2.enter_quantity>0
      """, LoginHelper.getTenantId(), where);

    Map<String, Object> dataInfo = SqlRunner.db().selectOne(sql);
    dataInfo = Optional.ofNullable(dataInfo).orElse(new HashMap<>());
    Long countQuantity = Convert.toLong(dataInfo.get("countQuantity")); // 总数
    Long countEnterQuantity = Convert.toLong(dataInfo.get("countEnterQuantity")); // 已入库数量


    Map<String, Object> result = new HashMap<>();
    BigDecimal unCountEnterQuantity = B.sub(countQuantity, countEnterQuantity);
    result.put("unCountEnterQuantity", ObjectUtil.defaultIfNull(unCountEnterQuantity, 0) + "件");

    return result;
  }

  //#endregion
}
