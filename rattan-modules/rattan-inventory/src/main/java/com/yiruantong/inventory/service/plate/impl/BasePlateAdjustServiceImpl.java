package com.yiruantong.inventory.service.plate.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateAdjust;
import com.yiruantong.inventory.domain.plate.BasePlateClient;
import com.yiruantong.inventory.domain.plate.bo.BasePlateAdjustBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateAdjustVo;
import com.yiruantong.inventory.mapper.plate.BasePlateAdjustMapper;
import com.yiruantong.inventory.service.plate.IBasePlateAdjustService;
import com.yiruantong.inventory.service.plate.IBasePlateClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 容器调整主Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateAdjustServiceImpl extends ServiceImplPlus<BasePlateAdjustMapper, BasePlateAdjust, BasePlateAdjustVo, BasePlateAdjustBo> implements IBasePlateAdjustService {

    private  final IBasePlateClientService basePlateClientService;
  //#region 获取现借出数量
  @Override
  @Transactional(rollbackFor = Exception.class)
  public BigDecimal getNowOutQty(Map<String, Object> map) {


    LambdaQueryWrapper<BasePlateClient> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(BasePlateClient::getPlateType,map.get("plateType"))
      .eq(BasePlateClient::getPlateSpec,map.get("plateSpec"))
      .eq(BasePlateClient::getStorageName,map.get("storageName"))
      .eq(BasePlateClient::getClientShortName,map.get("clientShortName"));
    BasePlateClient basePlateClient = basePlateClientService.getOne(lambdaQueryWrapper);

    BigDecimal zero = BigDecimal.ZERO;
    if(ObjectUtil.isNotEmpty(basePlateClient))
    {
      zero = zero.add(basePlateClient.getOuterOty());
    }
    return zero;
  }
  //endregion


}
