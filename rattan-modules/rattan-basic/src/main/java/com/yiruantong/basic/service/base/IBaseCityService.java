package com.yiruantong.basic.service.base;

import com.yiruantong.basic.domain.base.BaseCity;
import com.yiruantong.basic.domain.base.bo.BaseCityBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseCityVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 省市区管理Service接口
 *
 * @author YRT
 * @date 2024-11-05
 */
public interface IBaseCityService extends IServicePlus<BaseCity, BaseCityVo, BaseCityBo> {
  /**
   * 获取省
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  List<Map<String, Object>> getProvinceList(Map<String, Object> map);

  /**
   * 获取市
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  List<Map<String, Object>> getCityList(Map<String, Object> map);

  BaseCity getById(Long id);

  List<Map<String, Object>> getList(GetListBo getListBo);
}
