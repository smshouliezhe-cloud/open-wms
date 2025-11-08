package com.yiruantong.basic.mapper.client;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.client.vo.BaseClientVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 客户信息Mapper接口
 *
 * @author YRT
 * @date 2023-10-26
 */
public interface BaseClientMapper extends BaseMapperPlus<BaseClient, BaseClientVo> {

}
