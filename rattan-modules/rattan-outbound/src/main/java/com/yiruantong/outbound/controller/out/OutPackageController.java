package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.bo.OutPackageBo;
import com.yiruantong.outbound.domain.out.vo.OutPackageVo;
import com.yiruantong.outbound.mapper.out.OutPackageMapper;
import com.yiruantong.outbound.service.out.IOutPackageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 打包单
 *
 * @author YRT
 * @date 2023-11-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/package")
public class OutPackageController extends AbstractController<OutPackageMapper, OutPackage, OutPackageVo, OutPackageBo> {
  private final IOutPackageService outPackageService;

  /**
   * 生成一次性费用
   *
   * @param map 前端传递参数
   * @return
   */
  @PostMapping("/createBill")
  public R<Void> createBill(@RequestBody Map<String, Object> map) {
    return outPackageService.createBill(map);
  }
}
