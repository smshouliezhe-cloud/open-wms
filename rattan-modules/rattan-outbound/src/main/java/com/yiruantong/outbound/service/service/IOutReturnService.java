package com.yiruantong.outbound.service.service;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.vo.OutOrderDataSetVo;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.api.ApiOutReturnBo;
import com.yiruantong.outbound.domain.service.bo.OutReturnBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnVo;

import java.util.List;
import java.util.Map;

/**
 * 出库退货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IOutReturnService extends IServicePlus<OutReturn, OutReturnVo, OutReturnBo> {


  /**
   * 输入遇到货单号失去焦点加载主表信息
   *
   * @param map
   * @return
   */
  R<OutOrderDataSetVo> onBlurGetByCode(Map<String, Object> map);

  OutReturn getSourceCode(String sourceCode);

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiOutReturnBo bo);

  R<Void> PdaAdd(ApiOutReturnBo bo);

  R<Void> returnConfirm(List<Long> ids);

  R<Void> returnReject(List<Long> ids);

  OutReturn getStoreOrderCode(String storeOrderCode);
}
