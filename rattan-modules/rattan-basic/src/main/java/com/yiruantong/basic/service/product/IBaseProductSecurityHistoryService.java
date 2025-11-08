package com.yiruantong.basic.service.product;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.product.BaseProductSecurityHistory;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityHistoryVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityHistoryBo;

/**
 * 防伪码轨迹Service接口
 *
 * @author YRT
 * @date 2024-04-26
 */
public interface IBaseProductSecurityHistoryService extends IServicePlus<BaseProductSecurityHistory, BaseProductSecurityHistoryVo, BaseProductSecurityHistoryBo> {

  void addHistory(String sn ,Long orderId,String code);
}
