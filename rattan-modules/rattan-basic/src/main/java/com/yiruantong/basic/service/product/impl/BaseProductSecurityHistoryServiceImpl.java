package com.yiruantong.basic.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
import com.yiruantong.basic.service.product.IBaseProductSecurityDetailService;
import com.yiruantong.common.core.enums.base.BaseProductSecurityEnum;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.sensitive.core.SensitiveService;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityHistoryBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityHistoryVo;
import com.yiruantong.basic.domain.product.BaseProductSecurityHistory;
import com.yiruantong.basic.mapper.product.BaseProductSecurityHistoryMapper;
import com.yiruantong.basic.service.product.IBaseProductSecurityHistoryService;

/**
 * 防伪码轨迹Service业务层处理
 *
 * @author YRT
 * @date 2024-04-26
 */
@RequiredArgsConstructor
@Service
public class BaseProductSecurityHistoryServiceImpl extends ServiceImplPlus<BaseProductSecurityHistoryMapper, BaseProductSecurityHistory, BaseProductSecurityHistoryVo, BaseProductSecurityHistoryBo> implements IBaseProductSecurityHistoryService {
  @Override
  public void addHistory(String sn, Long orderId, String code) {
    IBaseProductSecurityDetailService baseProductSecurityDetailService = SpringUtils.getBean(IBaseProductSecurityDetailService.class);

    //查询防伪码信息
    LambdaQueryWrapper<BaseProductSecurityDetail> detail =new LambdaQueryWrapper<>();
    detail.eq(BaseProductSecurityDetail::getSecurityCode,sn);
    BaseProductSecurityDetail detailInfo = baseProductSecurityDetailService.getOne(detail);
    //修改防伪码状态
    LambdaUpdateWrapper<BaseProductSecurityDetail> updateWrapper =new LambdaUpdateWrapper<>();
    updateWrapper.set(BaseProductSecurityDetail::getSecurityStatus, BaseProductSecurityEnum.ASSIGNED.getName())
      .eq(BaseProductSecurityDetail::getSecurityDetailId,detailInfo.getSecurityDetailId());
    baseProductSecurityDetailService.update(updateWrapper);


    // 添加防伪码轨迹信息
    BaseProductSecurityHistory history =new BaseProductSecurityHistory();
    history.setSecurityCode(sn);
    history.setSecurityId(detailInfo.getSecurityId());
    history.setSecurityDetailId(detailInfo.getSecurityDetailId());
    history.setBillId(orderId);
    history.setBillCode(code);
    history.setOperationType("入库分配");
    history.setFromStatus(BaseProductSecurityEnum.NEWED.getName());
    history.setToStatus(BaseProductSecurityEnum.ASSIGNED.getName());
    history.setQuantity(1L);
    this.save(history);
  }
}
