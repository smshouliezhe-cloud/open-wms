package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysDropdown;
import com.yiruantong.system.domain.dataHandler.bo.SysDropdownBo;
import com.yiruantong.system.domain.dataHandler.vo.SysDropdownVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 下拉框设置Service接口
 *
 * @author YRT
 * @date 2023-07-27
 */
public interface ISysDropdownService extends IServicePlus<SysDropdown, SysDropdownVo, SysDropdownBo> {
  /**
   * 下拉框值查询
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Map<String, Object>> loadDropDown(Map<String, Object> map);

  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);

  /**
   * 根据下列ID获取
   *
   * @param id 下拉框ID
   * @return R<Map < String, Object>>
   */
  R<Map<String, Object>> loadDropDownById(Long id);
}
