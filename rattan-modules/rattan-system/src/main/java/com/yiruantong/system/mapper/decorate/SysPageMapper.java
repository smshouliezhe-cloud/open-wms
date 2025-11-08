package com.yiruantong.system.mapper.decorate;

import com.yiruantong.system.domain.decorate.SysPage;
import com.yiruantong.system.domain.decorate.vo.SysPageVo;
import org.apache.ibatis.annotations.Param;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 页面装修Mapper接口
 *
 * @author YRT
 * @date 2024-01-23
 */
public interface SysPageMapper extends BaseMapperPlus<SysPage, SysPageVo> {

  /**
   * 根据ID获取所有子孙ID，包含自己
   *
   * @param pageId 父级ID
   * @return 所有子孙ID，包含自己
   */
  String getChildrenId(@Param("pageId") Long pageId);
}
