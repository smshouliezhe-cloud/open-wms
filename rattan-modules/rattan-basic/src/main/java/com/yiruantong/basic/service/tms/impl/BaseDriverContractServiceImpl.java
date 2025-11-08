package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseDriverContract;
import com.yiruantong.basic.domain.tms.bo.BaseDriverContractBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverContractVo;
import com.yiruantong.basic.mapper.tms.BaseDriverContractMapper;
import com.yiruantong.basic.service.tms.IBaseDriverContractService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 司机合同管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class BaseDriverContractServiceImpl extends ServiceImplPlus<BaseDriverContractMapper, BaseDriverContract, BaseDriverContractVo, BaseDriverContractBo> implements IBaseDriverContractService {

  /**
   * 审核
   *
   * @param ids 审核参数
   * @return 返回查询列表数据
   */
//  @Override
//  public R<Void> multiAuditing(Map<String, Object> ids) {
//    // 写具体审核逻辑
//    try {
//      Long contractId = Convert.toLong(ids.get("contractId"));
//
//      // 获取主表数据
//      BaseDriverContract baseDriverContract = this.baseMapper.selectById(contractId);
//
//      if (ObjectUtil.isEmpty(baseDriverContract)) {
//        throw new ServiceException("数据不存在");
//      }
//
//
//      // 更新数据
//      LambdaUpdateWrapper<BaseDriverContract> lambda = new UpdateWrapper<BaseDriverContract>().lambda();
//      lambda.set(BaseDriverContract::getAuditing, ids.get("auditing"))
//        .eq(BaseDriverContract::getContractId, contractId);
//      this.update(lambda);
//      if (Convert.toLong(ids.get("auditing")) == 0) {
//        return R.ok("审核驳回");
//      } else if (Convert.toLong(ids.get("auditing")) == 2) {
//        return R.ok("通过审核");
//      } else {
//        return R.ok("审核失败");
//      }
//
//    } catch (NoSuchMessageException e) {
//      throw new ServiceException("错误" + e.getMessage());
//    }
//  }


  /**
   * 签署状态更新
   *
   * @param map 审核参数
   * @return 返回查询列表数据
   */
  @Override
  public R<Void> updateSign(Map<String, Object> map) {
    // 写具体审核逻辑
    try {
      Long contractId = Convert.toLong(map.get("contractId"));

      // 获取主表数据
      BaseDriverContract baseDriverContract = this.baseMapper.selectById(contractId);

      if (ObjectUtil.isEmpty(baseDriverContract)) {
        throw new ServiceException("数据不存在");
      }


      // 更新数据
      LambdaUpdateWrapper<BaseDriverContract> lambda = new UpdateWrapper<BaseDriverContract>().lambda();
      lambda.set(BaseDriverContract::getSigningStatus, map.get("signingStatus"))
        .eq(BaseDriverContract::getContractId, contractId);
      this.update(lambda);
      if (ObjectUtil.equals(Convert.toStr(map.get("signingStatus")), "已签署")) {
        return R.ok("签署成功");
      } else if (ObjectUtil.equals(Convert.toStr(map.get("signingStatus")), "未签署")) {
        return R.ok("签署驳回");
      } else {
        return R.ok("签署失败");
      }

    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
}
