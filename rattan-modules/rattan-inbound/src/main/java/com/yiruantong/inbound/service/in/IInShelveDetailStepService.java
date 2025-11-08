package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.domain.in.InShelveDetailStep;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailStepVo;
import com.yiruantong.inbound.domain.in.bo.InShelveDetailStepBo;

import java.util.List;

/**
 * 商品上架明细的明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IInShelveDetailStepService extends IServicePlus<InShelveDetailStep, InShelveDetailStepVo, InShelveDetailStepBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<InShelveDetailStep> selectListByMainId(Long mainId);
}
