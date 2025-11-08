package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseDriverContract;
import com.yiruantong.basic.domain.tms.bo.BaseDriverContractBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverContractVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Map;

/**
 * 司机合同管理Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface IBaseDriverContractService extends IServicePlus<BaseDriverContract, BaseDriverContractVo, BaseDriverContractBo> {
  /**
   * 批量审核
   *
   * @param ids 前端参数
   */
//  R<Void> multiAuditing(List<Long> ids);

  /**
   * 签署状态更新
   *
   * @param map 前端参数
   */
  R<Void> updateSign(Map<String, Object> map);
}
