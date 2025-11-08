package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.BaseAccessControl;
import com.yiruantong.basic.domain.tms.vo.BaseAccessControlVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 门禁信息Mapper接口
 *
 * @author YRT
 * @date 2024-12-26
 */
public interface BaseAccessControlMapper extends BaseMapperPlus<BaseAccessControl, BaseAccessControlVo> {

}
