package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseCarrierArea;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierAreaBo;
import com.yiruantong.basic.domain.tms.vo.BaseCarrierAreaVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 承运商管辖区域Service接口
 *
 * @author YRT
 * @date 2025-02-11
 */
public interface IBaseCarrierAreaService extends IServicePlus<BaseCarrierArea, BaseCarrierAreaVo, BaseCarrierAreaBo> {
  List<BaseCarrierArea> selectListByMainId(Long carrierId);

  List<BaseCarrierArea> selectBySite(Long carrierId, String unloadSite);

  BaseCarrierArea getProvinceName(String provinceName, String thermocLine);

  BaseCarrierArea getCityName(String cityName, String thermocLine);

  BaseCarrierArea getRegionName(String regionName, String thermocLine);

  List<BaseCarrierArea> selectByRegion(Long carrierId, String regionName);

  List<BaseCarrierArea> selectByCity(Long carrierId, String cityName);

  List<BaseCarrierArea> selectByProvince(Long carrierId, String provinceName);
}
