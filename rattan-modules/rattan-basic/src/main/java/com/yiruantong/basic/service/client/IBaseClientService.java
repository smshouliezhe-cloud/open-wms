package com.yiruantong.basic.service.client;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.client.bo.BaseClientBo;
import com.yiruantong.basic.domain.client.vo.BaseClientVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 客户信息Service接口
 *
 * @author YRT
 * @date 2023-10-26
 */
public interface IBaseClientService extends IServicePlus<BaseClient, BaseClientVo, BaseClientBo> {

  /**
   * 通用 - 查询客户
   *
   * @param getListBo@return 返回查询结果
   */
  List<Map<String, Object>> getList(GetListBo getListBo);

  /**
   * 波次单编号
   *
   * @param clientId
   * @return 返回波次单信息
   */
  BaseClient getClientInfo(Long clientId);

  /**
   * 根据编号查询客户信息
   *
   * @param clientCode
   * @return 返回波次单信息
   */
  BaseClient getByCode(String clientCode);

  /**
   * 根据名称查询客户信息
   *
   * @param clientShortName
   * @return 返回波次单信息
   */
  BaseClient getByName(String clientShortName);

  R<Map<String, Object>> add(BaseClientBo bo);

  BaseClient getConsignorInfo(String consignorName);
}
