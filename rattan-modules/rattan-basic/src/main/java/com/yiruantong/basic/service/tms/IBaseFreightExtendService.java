package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseFreightDetailExtendVo;
import com.yiruantong.basic.domain.tms.BaseFreightExtendVo;

import java.util.List;

/**
 * 运价模板查询扩展服务
 */
public interface IBaseFreightExtendService {
  /**
   * 查询运价模板
   *
   * @param carrierName
   * @return
   */
  List<BaseFreightExtendVo> selectFreightList(String carrierName);

  /**
   * 查询运价模板
   *
   * @param templateId       主表ID
   * @param placeDestination 目的地
   * @return
   */
  List<BaseFreightDetailExtendVo> freightDetailList(Long templateId, String placeDestination);
}
