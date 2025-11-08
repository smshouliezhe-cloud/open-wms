package com.yiruantong.basic.service.client;

import com.yiruantong.basic.domain.client.BaseClientContract;
import com.yiruantong.basic.domain.client.bo.BaseClientContractBo;
import com.yiruantong.basic.domain.client.bo.ContractStatusBo;
import com.yiruantong.basic.domain.client.vo.BaseClientContractVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 客户合同Service接口
 *
 * @author YRT
 * @date 2024-05-13
 */
public interface IBaseClientContractService extends IServicePlus<BaseClientContract, BaseClientContractVo, BaseClientContractBo> {
  R<Void> contractStatus(ContractStatusBo statusBo);
}
