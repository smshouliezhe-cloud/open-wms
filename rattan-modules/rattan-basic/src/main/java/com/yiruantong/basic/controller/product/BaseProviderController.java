package com.yiruantong.basic.controller.product;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.product.bo.BaseProviderBo;
import com.yiruantong.basic.domain.product.vo.BaseProviderVo;
import com.yiruantong.basic.mapper.product.BaseProviderMapper;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 供应商管理
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/provider")
public class BaseProviderController extends AbstractController<BaseProviderMapper, BaseProvider, BaseProviderVo, BaseProviderBo> {
  private final IBaseProviderService baseProviderService;

  /**
   * 查询供应商
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseProviderService.getList(map);
    return R.ok(list);
  }

  /**
   * 查询供应商
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getByShortName")
  public R<BaseProvider> getByShortName(@RequestBody Map<String, Object> map) {
    String providerShortName = Convert.toStr(map.get("providerShortName"));
    BaseProvider baseProvider = baseProviderService.getByShortName(providerShortName);
    return R.ok(baseProvider);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseProviderBo bo) {
    return baseProviderService.add(bo);
  }


  /**
   * 获取默认供应商
   *
   * @param map 参数
   */
  @PostMapping("/getProviderOne")
  public R<Map<String, Object>> getProviderOne(@RequestBody Map<String, Object> map) {
    return baseProviderService.getProviderOne(map);
  }
}
