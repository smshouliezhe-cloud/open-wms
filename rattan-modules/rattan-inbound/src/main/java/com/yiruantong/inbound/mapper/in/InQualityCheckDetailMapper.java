package com.yiruantong.inbound.mapper.in;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 质检管理明细Mapper接口
 *
 * @author YiRuanTong
 * @date 2023-10-25
 */
public interface InQualityCheckDetailMapper extends BaseMapperPlus<InQualityCheckDetail, InQualityCheckDetailVo> {

}
