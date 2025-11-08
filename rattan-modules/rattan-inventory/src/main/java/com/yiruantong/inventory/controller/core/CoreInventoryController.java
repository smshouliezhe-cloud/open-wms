package com.yiruantong.inventory.controller.core;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.bo.ApiCoreInventoryBo;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryBo;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySearchBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryCreateCheckVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryReplenishmentVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryVo;
import com.yiruantong.inventory.mapper.core.CoreInventoryMapper;
import com.yiruantong.inventory.service.core.IApiCoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 库存调整选择器
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/core/inventory")
public class CoreInventoryController extends AbstractController<CoreInventoryMapper, CoreInventory, CoreInventoryVo, CoreInventoryBo> {
  private final ICoreInventoryService coreInventoryService;
  private final IApiCoreInventoryService apiCoreInventoryService;

  /**
   * 查询自定义库存数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryComposeList")
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryComposeList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectInventoryComposeList(pageQuery);
  }

  /**
   * 商品库存查询
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryComposeGroupList")
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryComposeGroupList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectInventoryComposeGroupList(pageQuery);
  }

  /**
   * 根据仓库ID、货主ID、商品ID、货位名称，查询有效库存数据
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectValidInventoryList")
  public R<List<CoreInventoryComposeVo>> selectValidInventoryList(@RequestBody Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));
    Long productId = Convert.toLong(map.get("productId"));
    String productModel = Convert.toStr(map.get("productModel"));
    String positionName = Convert.toStr(map.get("positionName"));
    boolean isBatchNumber = Convert.toBool(map.get("isBatchNumber")); // 只筛选出包含批次号的库存
    List<CoreInventoryComposeVo> dataList;
    if (StringUtils.isNotEmpty(productModel)) {
      dataList = coreInventoryService.selectValidInventoryList(storageId, productModel, positionName);
    } else {
      dataList = coreInventoryService.selectValidInventoryList(storageId, consignorId, productId, positionName);
    }
    if (isBatchNumber) {
      dataList = dataList.stream().filter(x -> StringUtils.isNotEmpty(x.getBatchNumber())).toList();
    }
    return R.ok(dataList);
  }

  /**
   * 生成盘点单查询列表
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectCoreInventoryCreateCheckList")
  public TableDataInfo<CoreInventoryCreateCheckVo> selectCoreInventoryCreateCheckList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectCoreInventoryCreateCheckList(pageQuery);
  }

  /**
   * 查询保质期预警数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryEarlyComposeList")
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryEarlyComposeList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectInventoryEarlyComposeList(pageQuery);
  }

  /**
   * 查询库龄预警数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryWarningComposeList")
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryWarningComposeList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectInventoryWarningComposeList(pageQuery);
  }

  /**
   * 货位最低库存预警
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryLowerComposeList")
  public TableDataInfo<CoreInventoryComposeVo> selectInventoryLowerComposeList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectInventoryLowerComposeList(pageQuery);
  }

  /**
   * 锁定
   *
   * @param map
   * @return
   */
  @RequestMapping("/lock")
  public R<Void> lock(@RequestBody Map<String, Object> map) {
    return coreInventoryService.lock(map);
  }

  /**
   * 解锁
   *
   * @param map
   * @return
   */
  @RequestMapping("/unlock")
  public R<Void> unlock(@RequestBody Map<String, Object> map) {
    return coreInventoryService.unlock(map);
  }

  /**
   * 库存下限转补货单
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryReplenishmentList")
  public TableDataInfo<CoreInventoryReplenishmentVo> selectInventoryReplenishmentList(@RequestBody PageQuery pageQuery) {
    return coreInventoryService.selectInventoryReplenishmentList(pageQuery);
  }

  /**
   * API商品库存查询
   *
   * @param bo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryList")
  public R<List<Map<String, Object>>> selectInventoryList(@RequestBody ApiCoreInventoryBo bo) {
    return apiCoreInventoryService.selectInventoryList(bo);
  }

  /**
   * API商品库存查询
   *
   * @param bo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<CoreInventory>> getList(@RequestBody CoreInventorySearchBo bo) {
    return coreInventoryService.getList(bo);
  }
}
