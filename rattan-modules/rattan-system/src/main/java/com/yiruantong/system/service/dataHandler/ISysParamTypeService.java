package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysParamType;
import com.yiruantong.system.domain.dataHandler.bo.SysParamTypeBo;
import com.yiruantong.system.domain.dataHandler.vo.SysParamTypeVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 下拉框设置Service接口
 *
 * @author YRT
 * @date 2023-07-28
 */
public interface ISysParamTypeService extends IServicePlus<SysParamType, SysParamTypeVo, SysParamTypeBo> {
  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);
}
