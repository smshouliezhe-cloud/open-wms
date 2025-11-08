package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsClientLineDetail;
import com.yiruantong.basic.domain.tms.vo.TmsClientLineDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 客户线路关系明细Mapper接口
 *
 * @author YRT
 * @date 2024-03-08
 */
public interface TmsClientLineDetailMapper extends BaseMapperPlus<TmsClientLineDetail, TmsClientLineDetailVo> {

}
