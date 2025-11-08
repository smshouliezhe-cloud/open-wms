package com.yiruantong.inventory.mapper.stat;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.stat.StatStorageDay;
import com.yiruantong.inventory.domain.stat.vo.StatStorageDayVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 每日库存快照Mapper接口
 *
 * @author YRT
 * @date 2024-03-18
 */
public interface StatStorageDayMapper extends BaseMapperPlus<StatStorageDay, StatStorageDayVo> {

}
