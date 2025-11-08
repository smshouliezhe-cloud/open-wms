package com.yiruantong.inventory.mapper.stat;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 每日库存快照明细Mapper接口
 *
 * @author YRT
 * @date 2024-03-18
 */
public interface StatStorageDayDetailMapper extends BaseMapperPlus<StatStorageDayDetail, StatStorageDayDetailVo> {

}
