package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.TmsVehicleAccess;
import com.yiruantong.basic.domain.tms.bo.TmsVehicleAccessBo;
import com.yiruantong.basic.domain.tms.vo.TmsVehicleAccessVo;
import com.yiruantong.basic.mapper.tms.TmsVehicleAccessMapper;
import com.yiruantong.basic.service.tms.ITmsVehicleAccessService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车辆出入信息
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/vehicleAccess")
public class TmsVehicleAccessController extends AbstractController<TmsVehicleAccessMapper, TmsVehicleAccess, TmsVehicleAccessVo, TmsVehicleAccessBo> {
  private final ITmsVehicleAccessService tmsVehicleAccessService;

  /**
   * 批量审核
   *
   * @param ids 审核参数
   * @return 返回审核结果
   */
  @Log(title = "审核数据", businessType = BusinessType.AUDIT)
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return tmsVehicleAccessService.multiAuditing(ids);
  }
}
