package com.yiruantong.inventory.controller.replenishment;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.domain.replenishment.bo.StorageReplenishmentDetailBo;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentDetailVo;
import com.yiruantong.inventory.mapper.replenishment.StorageReplenishmentDetailMapper;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 补货单明细
 *
 * @author YRT
 * @date 2024-08-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/replenishment/replenishmentDetail")
public class StorageReplenishmentDetailController extends AbstractController<StorageReplenishmentDetailMapper, StorageReplenishmentDetail, StorageReplenishmentDetailVo, StorageReplenishmentDetailBo> {
  private final IStorageReplenishmentDetailService storageReplenishmentDetailService;

  /**
   * 获取补货数据
   *
   * @param map
   * @return R 返回保存结果
   */
  @PostMapping("/getReplenishmentGroupData")
  public R<Map<String, Object>> getReplenishmentGroupData(@RequestBody Map<String, Object> map) {
    return storageReplenishmentDetailService.getReplenishmentGroupData(map);
  }
}
