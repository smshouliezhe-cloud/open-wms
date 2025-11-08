package com.yiruantong.outbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.domain.service.bo.OutReturnDetailBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnDetailVo;

import java.util.List;

/**
 * 出库退货单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IOutReturnDetailService extends IServicePlus<OutReturnDetail, OutReturnDetailVo, OutReturnDetailBo> {

  List<OutReturnDetail> selectListByMainId(Long mainId);

}
