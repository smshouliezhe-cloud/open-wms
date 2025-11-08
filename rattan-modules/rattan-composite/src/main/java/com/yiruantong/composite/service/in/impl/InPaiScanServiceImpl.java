package com.yiruantong.composite.service.in.impl;


import cn.hutool.core.convert.Convert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.composite.service.in.IInPaiScanService;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InPaiScanServiceImpl implements IInPaiScanService {
  private final ICoreInventoryService coreInventoryService;
  private final IInScanOrderService inScanOrderService;

  //#region  按单码盘扫描保存前校验拍号是否存在

  /**
   * 按单码盘扫描保存前校验拍号是否存在
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @Override
  public R<Void> saveCheckPlateCode(Map<String, Object> map) {
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
    wrapper.in(CoreInventory::getPlateCode, Convert.toList(map.get("plateCodes")))
      .gt(CoreInventory::getValidStorage, 0);

    List<CoreInventory> list = coreInventoryService.getBaseMapper().selectList(wrapper);
    if (B.isGreater(Convert.toBigDecimal(list.size()))) {
      List<String> plateCodes = list.stream().map(CoreInventory::getPlateCode).toList();

      plateCodes = plateCodes.stream().distinct().collect(Collectors.toList());
      return R.fail("拍号与库存中重复:" + String.join(",", plateCodes));
    }

    return R.ok("成功");
  }

  /**
   * 常规扫描入库 - 按单码盘扫描
   *
   * @param inScanOrderBo 常规扫描入库数据
   * @return 返回查询数据
   */
  @Override
  public R<Void> scanPlateInSave(InScanOrderBo inScanOrderBo) {
    return inScanOrderService.normalScanSave(inScanOrderBo);
  }
  //#endregion
}
