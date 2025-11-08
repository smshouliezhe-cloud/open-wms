package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.ErpOutOrder;
import com.yiruantong.inventory.domain.operation.vo.ErpOutOrderVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 其他出库单Mapper接口
 *
 * @author YRT
 * @date 2024-06-26
 */
public interface ErpOutOrderMapper extends BaseMapperPlus<ErpOutOrder, ErpOutOrderVo> {

}
