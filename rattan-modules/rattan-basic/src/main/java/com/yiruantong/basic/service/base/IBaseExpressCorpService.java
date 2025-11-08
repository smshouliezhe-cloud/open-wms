package com.yiruantong.basic.service.base;

import com.yiruantong.basic.domain.base.BaseExpressCorp;
import com.yiruantong.basic.domain.base.bo.BaseExpressCorpBo;
import com.yiruantong.basic.domain.base.vo.BaseExpressCorpVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 快递管理Service接口
 *
 * @author YRT
 * @date 2023-08-25
 */
public interface IBaseExpressCorpService extends IServicePlus<BaseExpressCorp, BaseExpressCorpVo, BaseExpressCorpBo> {
  /**
   * 查询快递
   *
   * @param map
   * @return
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 获取快递
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Map<String, Object>> getExpressCorp(Map<String, Object> map);

  /**
   * 根据名称获取快递
   *
   * @param expressCorpName 参数
   * @return 返回是否成功
   */
  BaseExpressCorp getByName(String expressCorpName);

  /**
   * 根据id获取快递
   *
   * @param expressCorpId 参数
   * @return 返回是否成功
   */
  BaseExpressCorp getById(Long expressCorpId);
}
