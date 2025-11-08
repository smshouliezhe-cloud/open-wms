package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
import com.yiruantong.basic.domain.product.ProductSecurity;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityDetailBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityComposeVo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityDetailVo;
import com.yiruantong.basic.mapper.product.BaseProductSecurityDetailMapper;
import com.yiruantong.basic.service.product.IBaseProductSecurityDetailService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 防伪标签明细
 *
 * @author YRT
 * @date 2024-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/productSecurityDetail")
public class BaseProductSecurityDetailController extends AbstractController<BaseProductSecurityDetailMapper, BaseProductSecurityDetail, BaseProductSecurityDetailVo, BaseProductSecurityDetailBo> {


  private final IBaseProductSecurityDetailService baseProductSecurityDetailService;
  /**
   * 防伪码明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectBaseProductSecurityDetail")
  public TableDataInfo<BaseProductSecurityComposeVo> selectBaseProductSecurityDetail(@RequestBody PageQuery pageQuery) {
    return baseProductSecurityDetailService.selectBaseProductSecurityDetail(pageQuery);
  }

  /**
   * 根据批次号获取防伪码
   *
   * @param productSecurity 查询对象
   * @return 返回查询列表数据
   */
  @PostMapping("/getProductSecurity")
  public R<ProductSecurity> getProductSecurity(@RequestBody ProductSecurity productSecurity) {
    return baseProductSecurityDetailService.getProductSecurity(productSecurity);
  }
}
