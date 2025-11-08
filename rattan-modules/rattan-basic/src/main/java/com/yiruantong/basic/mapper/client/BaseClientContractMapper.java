package com.yiruantong.basic.mapper.client;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.client.BaseClientContract;
import com.yiruantong.basic.domain.client.vo.BaseClientContractVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 客户合同Mapper接口
 *
 * @author YRT
 * @date 2024-05-13
 */
public interface BaseClientContractMapper extends BaseMapperPlus<BaseClientContract, BaseClientContractVo> {

}
