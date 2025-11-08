package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import com.yiruantong.system.domain.dataHandler.bo.SysMenuAppBo;
import com.yiruantong.system.domain.dataHandler.vo.SysMenuAppVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ${author}
 * @date 2024-01-18
 */
public interface ISysMenuAppService extends IServicePlus<SysMenuApp, SysMenuAppVo, SysMenuAppBo> {
  /**
   * 根据parentId获取子集合
   *
   * @param parentId 父级ID
   * @return app模块集合
   */
  List<SysMenuAppVo> getListByParentId(Long parentId);

  /**
   * 根据parentId获取子集合
   *
   * @param parentId 父级ID
   * @return app模块集合
   */
  List<Map<String, Object>> getMapListByParentId(Long parentId);

  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);
}
