package com.yiruantong.inbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InRefuseDetail;
import com.yiruantong.inbound.domain.service.vo.InRefuseDetailVo;
import com.yiruantong.inbound.domain.service.bo.InRefuseDetailBo;

/**
 * 拒收单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInRefuseDetailService extends IServicePlus<InRefuseDetail, InRefuseDetailVo, InRefuseDetailBo> {
}
