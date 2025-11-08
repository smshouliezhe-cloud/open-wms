package com.yiruantong.basic.service.storage;

import com.yiruantong.basic.domain.storage.BasePlatform;
import com.yiruantong.basic.domain.storage.bo.BasePlatformBo;
import com.yiruantong.basic.domain.storage.vo.BasePlatformVo;
import com.yiruantong.common.core.enums.base.BasePlatformStatusEnum;
import com.yiruantong.common.core.enums.base.BasePlatformTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 月台管理Service接口
 *
 * @author YRT
 * @date 2024-03-09
 */
public interface IBasePlatformService extends IServicePlus<BasePlatform, BasePlatformVo, BasePlatformBo> {

  /**
   * 通用 - 查询月台
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 更新月台状态
   *
   * @param basePlatformStatusEnum
   * @param startTime
   * @param endTime
   * @param basePlatformTypeEnum
   * @param truckNo
   * @return 返回查询结果
   */
  void updateStatus(Long platformId, BasePlatformStatusEnum basePlatformStatusEnum, Date startTime, Date endTime, BasePlatformTypeEnum basePlatformTypeEnum, String truckNo);
}
