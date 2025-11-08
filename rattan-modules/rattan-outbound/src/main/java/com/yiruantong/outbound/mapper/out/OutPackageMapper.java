package com.yiruantong.outbound.mapper.out;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.vo.OutPackageVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 打包单Mapper接口
 *
 * @author YRT
 * @date 2023-11-07
 */
public interface OutPackageMapper extends BaseMapperPlus<OutPackage, OutPackageVo> {

}
