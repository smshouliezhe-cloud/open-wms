package com.yiruantong.basic.service.product;

import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.product.bo.BaseProviderBo;
import com.yiruantong.basic.domain.product.vo.BaseProviderVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 供应商管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
public interface IBaseProviderService extends IServicePlus<BaseProvider, BaseProviderVo, BaseProviderBo> {

  /**
   * 通用 - 查询供应商
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 获取默认供应商
   *
   * @return 返回查询结果
   */
  BaseProvider getDefaultOne();

  /**
   * 获取默认供应商
   *
   * @return 返回查询结果
   */
  R<Map<String, Object>> getProviderOne(Map<String, Object> map);

  /**
   * 根据名称获取供应商
   *
   * @param providerShortName
   * @return
   */
  BaseProvider getByShortName(String providerShortName);

  /**
   * 根据编号获取供应商
   *
   * @param providerCode
   * @return
   */
  BaseProvider getByCode(String providerCode);

  /**
   * 新增数据
   *
   * @param bo
   * @return
   */
  R<Map<String, Object>> add(BaseProviderBo bo);
}
