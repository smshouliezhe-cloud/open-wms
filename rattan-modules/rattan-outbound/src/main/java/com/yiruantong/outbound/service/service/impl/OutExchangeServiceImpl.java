package com.yiruantong.outbound.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.outbound.domain.service.*;
import com.yiruantong.outbound.domain.service.vo.OutExchangeComposeVo;
import com.yiruantong.outbound.service.service.IOutExchangeEnterDetailService;
import com.yiruantong.outbound.service.service.IOutExchangeOuterDetailService;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.service.bo.OutExchangeBo;
import com.yiruantong.outbound.domain.service.vo.OutExchangeVo;
import com.yiruantong.outbound.mapper.service.OutExchangeMapper;
import com.yiruantong.outbound.service.service.IOutExchangeService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 换货管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-23
 */
@RequiredArgsConstructor
@Service
public class OutExchangeServiceImpl extends ServiceImplPlus<OutExchangeMapper, OutExchange, OutExchangeVo, OutExchangeBo> implements IOutExchangeService {
  private final IOutExchangeOuterDetailService outExchangeOuterDetailService;
  private final IOutExchangeEnterDetailService outExchangeEnterDetailService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> PdaAdd(OutExchangeComposeVo compose) {
    OutExchange dataInfo = new OutExchange();
    BeanUtil.copyProperties(compose, dataInfo);
    dataInfo.setExchangeCode(DBUtils.getCodeRegular(MenuEnum.MENU_1734));
    dataInfo.setExchangeStatus(InOrderStatusEnum.NEWED.getName());
    dataInfo.setStorageIdIn(dataInfo.getStorageId());
    dataInfo.setStorageNameIn(dataInfo.getStorageName());
    dataInfo.setApplyDate(new Date());

    this.save(dataInfo);

    for (var detail : compose.getOuterDetails()) {
      OutExchangeOuterDetail newDetail = new OutExchangeOuterDetail();
      BeanUtil.copyProperties(detail, newDetail);
      newDetail.setExchangeId(dataInfo.getExchangeId());
      newDetail.setRate(B.div(newDetail.getRate(), new BigDecimal(100L)));
      outExchangeOuterDetailService.save(newDetail);
    }

    for (var detail : compose.getEnterDetails()) {
      OutExchangeEnterDetail newDetail = new OutExchangeEnterDetail();
      BeanUtil.copyProperties(detail, newDetail);
      newDetail.setExchangeId(dataInfo.getExchangeId());
      newDetail.setRate(B.div(newDetail.getRate(), new BigDecimal(100L)));
      outExchangeEnterDetailService.save(newDetail);
    }
    return R.ok("添加成功，单号：" + dataInfo.getExchangeCode());
  }
}
