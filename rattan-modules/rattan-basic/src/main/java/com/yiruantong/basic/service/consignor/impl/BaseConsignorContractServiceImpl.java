package com.yiruantong.basic.service.consignor.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.consignor.BaseConsignorContract;
import com.yiruantong.basic.domain.consignor.bo.BaseConsignorContractBo;
import com.yiruantong.basic.domain.consignor.vo.BaseConsignorContractVo;
import com.yiruantong.basic.mapper.consignor.BaseConsignorContractMapper;
import com.yiruantong.basic.service.consignor.IBaseConsignorContractService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 货主合同Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-13
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorContractServiceImpl extends ServiceImplPlus<BaseConsignorContractMapper, BaseConsignorContract, BaseConsignorContractVo, BaseConsignorContractBo> implements IBaseConsignorContractService {
  /**
   * 货主信息修改状态
   *
   * @param map
   * @return
   */
  @Override
  public R<Void> contractAlter(Map<String, Object> map) {
    try {
      List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
      String contractSatus = Convert.toStr(map.get("contractSatus"));

      for (long contractId : ids) {
        BaseConsignorContract baseConsignorContract = this.baseMapper.selectById(contractId);
        if (ObjectUtil.isEmpty(baseConsignorContract)) {
          throw new ServiceException("未获取到单据");
        }
        // 修改数据
        LambdaUpdateWrapper<BaseConsignorContract> lambda = new UpdateWrapper<BaseConsignorContract>().lambda();
        lambda.set(BaseConsignorContract::getContractSatus, contractSatus)
          .eq(BaseConsignorContract::getContractId, contractId);
        this.update(lambda);//提交
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
}
