package com.yiruantong.basic.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.client.BaseClientContract;
import com.yiruantong.basic.domain.client.bo.BaseClientContractBo;
import com.yiruantong.basic.domain.client.bo.ContractStatusBo;
import com.yiruantong.basic.domain.client.vo.BaseClientContractVo;
import com.yiruantong.basic.mapper.client.BaseClientContractMapper;
import com.yiruantong.basic.service.client.IBaseClientContractService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户合同Service业务层处理
 *
 * @author YRT
 * @date 2024-05-13
 */
@RequiredArgsConstructor
@Service
public class BaseClientContractServiceImpl extends ServiceImplPlus<BaseClientContractMapper, BaseClientContract, BaseClientContractVo, BaseClientContractBo> implements IBaseClientContractService {
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> contractStatus(ContractStatusBo statusBo) {
    String status = statusBo.getContractStatus();
    // 运单主表
    LambdaQueryWrapper<BaseClientContract> tmsWayBillLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tmsWayBillLambdaQueryWrapper.in(BaseClientContract::getContractId, statusBo.getContractIds()); // id
    var clientContractList = this.list(tmsWayBillLambdaQueryWrapper);
    for (var contractInfo : clientContractList) {
      LambdaUpdateWrapper<BaseClientContract> wrapper = new LambdaUpdateWrapper<>();
      wrapper.set(BaseClientContract::getContractSatus, status)
        .eq(BaseClientContract::getContractId, contractInfo.getContractId());
      this.update(wrapper);
    }

    return R.ok("修改完成");
  }
}
