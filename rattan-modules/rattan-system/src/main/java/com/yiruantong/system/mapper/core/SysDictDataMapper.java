package com.yiruantong.system.mapper.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.core.SysDictData;
import com.yiruantong.system.domain.core.vo.SysDictDataVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author YiRuanTong
 */
public interface SysDictDataMapper extends BaseMapperPlus<SysDictData, SysDictDataVo> {

  default List<SysDictDataVo> selectDictDataByType(String dictType) {
    return selectVoList(
      new LambdaQueryWrapper<SysDictData>()
        .eq(SysDictData::getDictType, dictType)
        .orderByAsc(SysDictData::getDictSort));
  }
}
