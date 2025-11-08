package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.BaseCarrier;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierBo;
import com.yiruantong.basic.domain.tms.vo.BaseCarrierVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 承运商管理Service接口
 *
 * @author YRT
 * @date 2024-03-08
 */
public interface IBaseCarrierService extends IServicePlus<BaseCarrier, BaseCarrierVo, BaseCarrierBo> {
  /**
   * 通用 - 查询供应商
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 通用 - 查询供应商
   *
   * @param getListBo 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getListNew(GetListBo getListBo);

  /**
   * 查询承运商
   *
   * @param carrierName
   * @param enable
   * @return
   */
  BaseCarrier getByName(String carrierName, Long enable);

  /**
   * 查询承运商
   *
   * @param carrierName
   * @return
   */
  BaseCarrier getByName(String carrierName);

  /**
   * 接口新增
   *
   * @param bo
   * @return
   */
  R<Map<String, Object>> add(BaseCarrierBo bo);

  BaseCarrier getBySite(Long carrierId, String distributionSite);

  /**
   * 重置承运商密码
   *
   * @param carrierId 用户ID
   * @param password  密码
   * @return 结果
   */
  int resetUserPwd(Long carrierId, String password);
}
