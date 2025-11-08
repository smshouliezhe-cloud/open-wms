package com.yiruantong.inbound.service.service;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.api.ApiInReturnBo;
import com.yiruantong.inbound.domain.service.bo.InReturnBo;
import com.yiruantong.inbound.domain.service.vo.InReturnVo;

import java.util.Map;

/**
 * 退货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInReturnService extends IServicePlus<InReturn, InReturnVo, InReturnBo> {
  boolean updateAllocateStatus(InReturn inReturn, SortingStatusEnum sortingStatus);


  InReturn getSourceCode(String sourceCode);

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiInReturnBo bo);
}
