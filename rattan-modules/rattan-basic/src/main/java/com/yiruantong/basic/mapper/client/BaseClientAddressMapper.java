package com.yiruantong.basic.mapper.client;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.domain.client.vo.BaseClientAddressVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 客户地址管理Mapper接口
 *
 * @author YRT
 * @date 2023-10-26
 */
public interface BaseClientAddressMapper extends BaseMapperPlus<BaseClientAddress, BaseClientAddressVo> {

}
