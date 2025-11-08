package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.IInScanPaiService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 商品上架明细
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/InScanPai")
public class InScanPaiController extends BaseController {
  private final IInScanPaiService inScanPaiService;

  /**
   * 安排扫描商入库  获取数据
   *
   * @param maps 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getPlateCodeData")
  public R<List<Map<String, Object>>> getPlateCodeData(@RequestBody Map<String, Object> maps) {
    return R.ok(inScanPaiService.getPlateCodeData(maps));
  }

  /**
   * 安排扫描商入库  获取数据
   *
   * @param inScanOrderBo 返回的数据
   * @return 返回查询列表数据
   */
  @PostMapping("/enterPaiSave")
  public R<Void> enterPaiSave(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanPaiService.enterPaiSave(inScanOrderBo);
  }

  /**
   * 按拍扫描上架  获取数据
   *
   * @param maps 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getShelvePlateData")
  public R<List<Map<String, Object>>> getShelvePlateData(@RequestBody Map<String, Object> maps) {
    return R.ok(inScanPaiService.getShelvePlateData(maps));
  }
}
