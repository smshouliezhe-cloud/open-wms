package com.yiruantong.basic.service.base;

import com.yiruantong.basic.domain.base.BaseCountry;
import com.yiruantong.basic.domain.base.bo.BaseCountryBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseCountryVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 国家信息Service接口
 *
 * @author YRT
 * @date 2024-06-06
 */
public interface IBaseCountryService extends IServicePlus<BaseCountry, BaseCountryVo, BaseCountryBo> {
  /**
   * 通用查询
   *
   * @param getListBo@return 返回查询结果
   */
  List<Map<String, Object>> getList(GetListBo getListBo);
}
