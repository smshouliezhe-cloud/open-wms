package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.BaseVehicleHistory;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 车辆轨迹Mapper接口
 *
 * @author YRT
 * @date 2024-05-31
 */
public interface BaseVehicleHistoryMapper extends BaseMapperPlus<BaseVehicleHistory, BaseVehicleHistoryVo> {

}
