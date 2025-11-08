package com.yiruantong.basic.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.product.BaseProductSet;
import com.yiruantong.basic.domain.product.vo.BaseProductSetVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 商品套装主Mapper接口
 *
 * @author YRT
 * @date 2023-12-19
 */
public interface BaseProductSetMapper extends BaseMapperPlus<BaseProductSet, BaseProductSetVo> {

}
