package com.yiruantong.system.controller.magic;

import com.yiruantong.system.domain.magic.MagicPage;
import com.yiruantong.system.domain.magic.bo.MagicPageBo;
import com.yiruantong.system.domain.magic.vo.MagicPageUpdateVo;
import com.yiruantong.system.domain.magic.vo.MagicPageVo;
import com.yiruantong.system.mapper.magic.MagicPageMapper;
import com.yiruantong.system.service.magic.IMagicPageService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.BaseBo;
import com.yiruantong.common.core.domain.model.BaseVo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 页面开发
 *
 * @author YRT
 * @date 2024-11-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/magic/page")
public class MagicPageController extends AbstractController<MagicPageMapper, MagicPage, MagicPageVo, MagicPageBo> {
  private final IMagicPageService magicPageService;

  /**
   * 更新设计器JSON
   *
   * @param baseBo 保存数据
   */
  @PostMapping(value = "/updatePageschema")
  public R<MagicPageUpdateVo> getConfigKey(@RequestBody BaseBo baseBo) {
    return R.ok(magicPageService.updatePageschema(baseBo));
  }

  /**
   * 生成代码
   *
   * @param pageId 页面ID
   * @return R
   */
  @PostMapping(value = "/generatorCode/{pageId}/{code}")
  public R<BaseVo> generatorCode(@PathVariable Long pageId, @PathVariable String code) {
    return R.ok(magicPageService.generatorCode(pageId, code));
  }

  /**
   * 生成代码
   *
   * @param tableId 表ID
   * @param menuId  菜单ID
   * @param code    膜拜编号
   * @return R
   */
  @PostMapping(value = "/generatorCodeByTableId/{tableId}/{menuId}/{code}")
  public R<BaseVo> generatorCodeByTableId(@PathVariable Long tableId, @PathVariable Long menuId, @PathVariable String code) {
    return R.ok(magicPageService.generatorCodeByTableId(tableId, menuId, code));
  }

  /**
   * 生成代码
   *
   * @param pageId 页面ID
   * @return R
   */
  @PostMapping(value = "/updatePageJson/{pageId}/{code}")
  public R<Void> updatePageJson(@PathVariable Long pageId, @PathVariable String code) {
    return magicPageService.updatePageJson(pageId, code);
  }

  /**
   * 生成代码
   *
   * @param code 页面编号
   * @return R
   */
  @PostMapping(value = "/selectByCode/{code}")
  public R<MagicPageVo> selectByCode(@PathVariable String code) {
    return magicPageService.selectByCode(code);
  }

  /**
   * 生成代码
   *
   * @param code 页面编号
   * @return R
   */
  @PostMapping(value = "/getSchema/{code}")
  public R<Map<String, Object>> getSchema(@PathVariable String code) {
    return magicPageService.getSchema(code);
  }
}
