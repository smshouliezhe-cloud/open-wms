package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateIn;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInVo;
import com.yiruantong.inventory.mapper.plate.BasePlateInMapper;
import com.yiruantong.inventory.service.plate.IBasePlateInService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 容器归还主
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateIn")
public class BasePlateInController extends AbstractController<BasePlateInMapper, BasePlateIn, BasePlateInVo, BasePlateInBo> {
  private final IBasePlateInService basePlateInService;

  /**
   * 审核
   *
   * @param ids 前端传递参数
   * @return
   */
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return basePlateInService.multiAuditing(ids);
  }

  /**
   * 明细清除
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/deleteData")
  public R<Void> deleteData(@RequestBody Map<String, Object> map) {
    return basePlateInService.deleteData(map);
  }

  /**
   * 已核对
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/isCheck")
  public R<Void> isCheck(@RequestBody Map<String, Object> map) {
    return basePlateInService.isCheck(map);
  }

  /**
   * 垫板入库审核
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/backPlateAudit")
  public R<Void> backPlateAudit(@RequestBody Map<String, Object> map) {
    return basePlateInService.backPlateAudit(map);
  }

}
