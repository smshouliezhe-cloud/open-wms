package com.yiruantong.basic.service.tms.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.basic.domain.tms.BaseCarrierArea;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierAreaBo;
import com.yiruantong.basic.domain.tms.vo.BaseCarrierAreaVo;
import com.yiruantong.basic.mapper.tms.BaseCarrierAreaMapper;
import com.yiruantong.basic.service.tms.IBaseCarrierAreaService;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 承运商管辖区域Service业务层处理
 *
 * @author YRT
 * @date 2025-02-11
 */
@RequiredArgsConstructor
@Service
public class BaseCarrierAreaServiceImpl extends ServiceImplPlus<BaseCarrierAreaMapper, BaseCarrierArea, BaseCarrierAreaVo, BaseCarrierAreaBo> implements IBaseCarrierAreaService {
  @Override
  public List<BaseCarrierArea> selectListByMainId(Long carrierId) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getCarrierId, carrierId);
    return baseMapper.selectList(queryWrapper);
  }

  @Override
  public List<BaseCarrierArea> selectBySite(Long carrierId, String unloadSite) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getCarrierId, carrierId)
      .eq(BaseCarrierArea::getUnloadSite, unloadSite);
    return baseMapper.selectList(queryWrapper);
  }

  @Override
  public BaseCarrierArea getProvinceName(String provinceName, String thermocLine) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getProvinceName, provinceName)
      .eq(StringUtils.isNotEmpty(thermocLine), BaseCarrierArea::getThermocLine, thermocLine);

    return this.getOnly(queryWrapper);
  }

  @Override
  public BaseCarrierArea getCityName(String cityName, String thermocLine) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getCityName, cityName)
      .eq(StringUtils.isNotEmpty(thermocLine), BaseCarrierArea::getThermocLine, thermocLine);
    return this.getOnly(queryWrapper);
  }

  @Override
  public BaseCarrierArea getRegionName(String regionName, String thermocLine) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getRegionName, regionName)
      .eq(StringUtils.isNotEmpty(thermocLine), BaseCarrierArea::getThermocLine, thermocLine);
    return this.getOnly(queryWrapper);

  }

  @Override
  public List<BaseCarrierArea> selectByRegion(Long carrierId, String regionName) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getCarrierId, carrierId)
      .eq(BaseCarrierArea::getRegionName, regionName);
    return baseMapper.selectList(queryWrapper);
  }

  @Override
  public List<BaseCarrierArea> selectByCity(Long carrierId, String cityName) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getCarrierId, carrierId)
      .eq(BaseCarrierArea::getCityName, cityName);
    return baseMapper.selectList(queryWrapper);
  }

  @Override
  public List<BaseCarrierArea> selectByProvince(Long carrierId, String provinceName) {
    LambdaQueryWrapper<BaseCarrierArea> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseCarrierArea::getCarrierId, carrierId)
      .eq(BaseCarrierArea::getProvinceName, provinceName);
    return baseMapper.selectList(queryWrapper);
  }
}
