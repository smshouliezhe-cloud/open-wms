package com.yiruantong.basic.service.product;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.product.BaseProductSecurity;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityBo;

import java.util.List;
import java.util.Map;

/**
 * 防伪标签Service接口
 *
 * @author YRT
 * @date 2024-04-25
 */
public interface IBaseProductSecurityService extends IServicePlus<BaseProductSecurity, BaseProductSecurityVo, BaseProductSecurityBo> {
  /**
   * 生成防伪码
   *
   * @param map
   * @return
   */
  R<Void> createSecurity(Map<String, Object> map);
  /**
   * 作废
   *
   * @param map
   * @return
   */
  R<Void> cancel(Map<String, Object> map);
  /**
   * 明细作废
   *
   * @param map
   * @return
   */
  R<Void> detailCancel(Map<String, Object> map);

    boolean addSecurity(Long orderId, String orderCode, List<String> snList);
}
