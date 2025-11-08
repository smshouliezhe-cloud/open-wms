package com.yiruantong.basic.service.consignor;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.consignor.BaseConsignorContract;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorContractVo;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorContractBo;

import java.util.Map;

/**
 * 货主合同Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-13
 */
public interface IBaseConsignorContractService extends IServicePlus<BaseConsignorContract, BaseConsignorContractVo, BaseConsignorContractBo> {
  /**
   * 货主信息修改状态
   *
   * @param map
   * @return
   */
  R<Void> contractAlter(Map<String, Object> map);
}
