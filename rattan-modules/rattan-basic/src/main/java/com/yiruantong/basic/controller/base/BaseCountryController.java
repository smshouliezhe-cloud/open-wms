package com.yiruantong.basic.controller.base;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseCountry;
import com.yiruantong.basic.domain.base.bo.BaseCountryBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseCountryVo;
import com.yiruantong.basic.mapper.base.BaseCountryMapper;
import com.yiruantong.basic.service.base.IBaseCountryService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 国家信息
 *
 * @author YRT
 * @date 2024-06-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/country")
public class BaseCountryController extends AbstractController<BaseCountryMapper, BaseCountry, BaseCountryVo, BaseCountryBo> {
  private final IBaseCountryService baseCountryService;

  /**
   * 下拉框查询
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseCountryService.getList(getListBo);
    return R.ok(list);
  }
}
