package com.yiruantong.basic.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.product.BaseProductSecurity;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 防伪标签Mapper接口
 *
 * @author YRT
 * @date 2024-04-25
 */
public interface BaseProductSecurityMapper extends BaseMapperPlus<BaseProductSecurity, BaseProductSecurityVo> {

}
