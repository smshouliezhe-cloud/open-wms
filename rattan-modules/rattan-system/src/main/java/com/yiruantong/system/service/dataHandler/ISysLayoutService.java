package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysLayout;
import com.yiruantong.system.domain.dataHandler.bo.SysLayoutBo;
import com.yiruantong.system.domain.dataHandler.vo.SysLayoutVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ${author}
 * @date 2024-01-18
 */
public interface ISysLayoutService extends IServicePlus<SysLayout, SysLayoutVo, SysLayoutBo> {
  /**
   * 初始化首页布局页面
   */
  Map<String, Object> initLayout(Map<String, Object> map);

  /**
   * 保存
   */
  R<Void> saveLayout(Map<String, Object> map);
}
