package com.yiruantong.inbound.controller.in;

import com.yiruantong.basic.service.storage.IBasePlateService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InArrivalProcess;
import com.yiruantong.inbound.domain.in.bo.InArrivalProcessBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.vo.InArrivalProcessVo;
import com.yiruantong.inbound.mapper.in.InArrivalProcessMapper;
import com.yiruantong.inbound.service.in.IInScanLpnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * LPN 扫描
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/inScanLpn")
public class InScanLpnController extends AbstractController<InArrivalProcessMapper, InArrivalProcess, InArrivalProcessVo, InArrivalProcessBo> {

  private final IBasePlateService basePlateService;
  private final IInScanLpnService inScanLpnService;

  /**
   * - 获取容器信息
   *
   * @param map 前段传入数据
   * @return 返回查询数据
   */
  @PostMapping("/getList")
  public R<List<BasePlate>> getList(@RequestBody Map<String, Object> map) {
    return R.ok(basePlateService.getList(map));
  }

  /**
   * 常规扫描入库 - LPN扫描
   *
   * @param inScanOrderBo 扫描入库数据
   * @return 返回查询数据
   */
  @PostMapping("/scanPlateInSave")
  public R<Void> scanPlateInSave(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanLpnService.scanPlateInSave(inScanOrderBo);
  }

  /**
   * - LPN号扫描上架 -- 获取数据
   *
   * @param map 前段传入数据
   * @return 返回查询数据
   */
  @PostMapping("/getShelveInfoByLpnCode")
  public List<Map<String, Object>> getShelveInfoByLpnCode(@RequestBody Map<String, Object> map) {
    return inScanLpnService.getShelveInfoByLpnCode(map);
  }


}
