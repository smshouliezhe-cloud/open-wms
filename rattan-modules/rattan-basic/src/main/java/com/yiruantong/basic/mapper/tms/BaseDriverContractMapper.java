package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.BaseDriverContract;
import com.yiruantong.basic.domain.tms.vo.BaseDriverContractVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 司机合同管理Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface BaseDriverContractMapper extends BaseMapperPlus<BaseDriverContract, BaseDriverContractVo> {

}
