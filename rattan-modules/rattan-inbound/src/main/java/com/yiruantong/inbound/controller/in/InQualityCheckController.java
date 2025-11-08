package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.api.ApiInQualityCheckBo;
import com.yiruantong.inbound.domain.in.InQualityCheck;
import com.yiruantong.inbound.domain.in.bo.InQualityCheckBo;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckVo;
import com.yiruantong.inbound.domain.service.vo.InOrderAndDetailVo;
import com.yiruantong.inbound.mapper.in.InQualityCheckMapper;
import com.yiruantong.inbound.service.in.IInQualityCheckService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 质检管理
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/qualityCheck")
public class InQualityCheckController extends AbstractController<InQualityCheckMapper, InQualityCheck, InQualityCheckVo, InQualityCheckBo> {
  private final IInQualityCheckService inQualityCheckService;

  /*
   * 转到预到货单
   * */
  @RequestMapping("/toInOrder")
  public R<Void> toInOrder(@RequestBody Map<String, Object> map) {
    return inQualityCheckService.toInOrder(map);
  }

  /*
   * 转到预到货单
   * */
  @RequestMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return inQualityCheckService.multiAuditing(ids);
  }

  /**
   * 输入遇到货单号失去焦点加载主表信息
   *
   * @param map
   * @return
   */
  @RequestMapping("/onBlurGetByCode")
  public R<InOrderAndDetailVo> onBlurGetByCode(@RequestBody Map<String, Object> map) {
    return inQualityCheckService.onBlurGetByCode(map);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiInQualityCheckBo bo) {
    return inQualityCheckService.add(bo);
  }
}
