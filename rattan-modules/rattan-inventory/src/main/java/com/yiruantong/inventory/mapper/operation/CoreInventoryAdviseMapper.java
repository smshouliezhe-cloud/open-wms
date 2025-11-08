package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import com.yiruantong.inventory.domain.operation.vo.CoreInventoryAdviseVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 建议采购转遇到货Mapper接口
 *
 * @author YRT
 * @date 2023-12-13
 */
public interface CoreInventoryAdviseMapper extends BaseMapperPlus<CoreInventoryAdvise, CoreInventoryAdviseVo> {

}
