package com.yiruantong.inbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InRefuse;
import com.yiruantong.inbound.domain.service.vo.InRefuseVo;
import com.yiruantong.inbound.domain.service.bo.InRefuseBo;

/**
 * 拒收单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInRefuseService extends IServicePlus<InRefuse, InRefuseVo, InRefuseBo> {
}
