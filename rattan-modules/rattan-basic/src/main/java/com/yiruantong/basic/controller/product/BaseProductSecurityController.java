package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.product.BaseProductSecurity;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityBo;
import com.yiruantong.basic.mapper.product.BaseProductSecurityMapper;
import com.yiruantong.basic.service.product.IBaseProductSecurityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 防伪标签
 *
 * @author YRT
 * @date 2024-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/productSecurity")
public class BaseProductSecurityController extends AbstractController<BaseProductSecurityMapper, BaseProductSecurity, BaseProductSecurityVo, BaseProductSecurityBo> {

  private final IBaseProductSecurityService baseProductSecurityService;
  /**
   * 生成防伪码
   * @param map
   * @return
   */
  @RequestMapping("/createSecurity")
  public R<Void> createSecurity(@RequestBody Map<String, Object> map) {
    return baseProductSecurityService.createSecurity(map);
  }

  /**
   * 作废
   * @param map
   * @return
   */
  @RequestMapping("/cancel")
  public R<Void> cancel(@RequestBody Map<String, Object> map) {
    return baseProductSecurityService.cancel(map);
  }
  /**
   * 明细作废
   * @param map
   * @return
   */
  @RequestMapping("/detailCancel")
  public R<Void> detailCancel(@RequestBody Map<String, Object> map) {
    return baseProductSecurityService.detailCancel(map);
  }

}
