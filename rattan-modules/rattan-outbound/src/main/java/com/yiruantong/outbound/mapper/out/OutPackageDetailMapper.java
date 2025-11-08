package com.yiruantong.outbound.mapper.out;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 打包单明细Mapper接口
 *
 * @author YRT
 * @date 2023-11-07
 */
public interface OutPackageDetailMapper extends BaseMapperPlus<OutPackageDetail, OutPackageDetailVo> {

}
