package com.yiruantong.basic.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 防伪标签明细Mapper接口
 *
 * @author YRT
 * @date 2024-04-25
 */
public interface BaseProductSecurityDetailMapper extends BaseMapperPlus<BaseProductSecurityDetail, BaseProductSecurityDetailVo> {

}
