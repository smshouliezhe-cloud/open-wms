package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InArrivalProcess;
import com.yiruantong.inbound.domain.in.vo.InArrivalProcessVo;
import com.yiruantong.inbound.domain.in.bo.InArrivalProcessBo;

/**
 * 到货加工Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
public interface IInArrivalProcessService extends IServicePlus<InArrivalProcess, InArrivalProcessVo, InArrivalProcessBo> {
}
