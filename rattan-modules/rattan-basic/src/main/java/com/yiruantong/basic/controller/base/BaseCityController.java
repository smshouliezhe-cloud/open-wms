package com.yiruantong.basic.controller.base;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseCity;
import com.yiruantong.basic.domain.base.bo.BaseCityBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseCityVo;
import com.yiruantong.basic.mapper.base.BaseCityMapper;
import com.yiruantong.basic.service.base.IBaseCityService;
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
 * 省市区管理
 *
 * @author YRT
 * @date 2024-11-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@SaIgnore // 忽略接口鉴权
@RequestMapping("/basic/base/city")
public class BaseCityController extends AbstractController<BaseCityMapper, BaseCity, BaseCityVo, BaseCityBo> {
  private final IBaseCityService baseCityService;


  /**
   * 获取省
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getProvinceList")
  public List<Map<String, Object>> getProvinceList(@RequestBody Map<String, Object> map) {
    return baseCityService.getProvinceList(map);
  }

  /**
   * 获取市
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getCityList")
  public List<Map<String, Object>> getCityList(@RequestBody Map<String, Object> map) {
    return baseCityService.getCityList(map);
  }


  /**
   * 查询省市区
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseCityService.getList(getListBo);
    return R.ok(list);
  }
}
