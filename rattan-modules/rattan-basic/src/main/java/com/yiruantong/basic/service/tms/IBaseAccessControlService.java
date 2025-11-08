package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseAccessControl;
import com.yiruantong.basic.domain.tms.bo.BaseAccessControlBo;
import com.yiruantong.basic.domain.tms.vo.BaseAccessControlVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Map;

/**
 * 门禁信息Service接口
 *
 * @author YRT
 * @date 2024-12-26
 */
public interface IBaseAccessControlService extends IServicePlus<BaseAccessControl, BaseAccessControlVo, BaseAccessControlBo> {

  R<Map<String, Object>> add(BaseAccessControlBo bo);

}
