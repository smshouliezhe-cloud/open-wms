package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.api.ApiInQualityCheckBo;
import com.yiruantong.inbound.domain.in.InQualityCheck;
import com.yiruantong.inbound.domain.in.bo.InQualityCheckBo;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckVo;
import com.yiruantong.inbound.domain.service.vo.InOrderAndDetailVo;

import java.util.Map;

/**
 * 质检管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
public interface IInQualityCheckService extends IServicePlus<InQualityCheck, InQualityCheckVo, InQualityCheckBo> {
  /**
   * 根据质检单号返回质检单信息
   *
   * @param qualityCheckCode
   * @return 返回上架单信息
   */
  InQualityCheck getByCode(String qualityCheckCode);

  /**
   * 质检单转预到货单
   *
   * @param map
   * @return
   */
  R<Void> toInOrder(Map<String, Object> map);

  /**
   * 输入遇到货单号失去焦点加载主表信息
   *
   * @param map
   * @return
   */
  R<InOrderAndDetailVo> onBlurGetByCode(Map<String, Object> map);

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiInQualityCheckBo bo);
}
