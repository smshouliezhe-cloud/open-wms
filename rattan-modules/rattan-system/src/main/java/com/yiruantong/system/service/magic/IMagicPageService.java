package com.yiruantong.system.service.magic;

import com.yiruantong.system.domain.magic.MagicPage;
import com.yiruantong.system.domain.magic.bo.MagicPageBo;
import com.yiruantong.system.domain.magic.vo.MagicPageUpdateVo;
import com.yiruantong.system.domain.magic.vo.MagicPageVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.BaseBo;
import com.yiruantong.common.core.domain.model.BaseVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Map;

/**
 * 页面开发Service接口
 *
 * @author YRT
 * @date 2024-11-10
 */
public interface IMagicPageService extends IServicePlus<MagicPage, MagicPageVo, MagicPageBo> {
  /**
   * 更新界面JSON架构
   *
   * @param baseBo 更新对象
   * @return Vo对象
   */
  MagicPageUpdateVo updatePageschema(BaseBo baseBo);

  /**
   * 更新界面JSON架构
   *
   * @param pageId 页面ID
   * @param code   模板编号
   * @return Vo对象
   */
  BaseVo generatorCode(Long pageId, String code);

  /**
   * 生成JSON架构
   *
   * @param tableId 表ID
   * @param menuId  菜单ID
   * @param code    膜拜编号
   * @return BaseVo
   */
  BaseVo generatorCodeByTableId(Long tableId, Long menuId, String code);

  /**
   * 更新页面JSON配置
   *
   * @param pageId 页面ID
   * @param code   模板编号
   * @return Vo对象
   */
  R<Void> updatePageJson(Long pageId, String code);

  /**
   * 根据模板编号查询页面信息
   *
   * @param code 模板编号
   * @return R
   */
  R<MagicPageVo> selectByCode(String code);

  /**
   * 获取动态加载页面结构
   *
   * @param code 页面编号
   * @return Map
   */
  R<Map<String, Object>> getSchema(String code);
}
