package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;
import com.yiruantong.inbound.domain.in.bo.InQualityCheckDetailBo;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckDetailVo;

import java.util.List;

/**
 * 质检管理明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-25
 */
public interface IInQualityCheckDetailService extends IServicePlus<InQualityCheckDetail, InQualityCheckDetailVo, InQualityCheckDetailBo> {

  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<InQualityCheckDetail> selectListByMainId(Long mainId);
}
