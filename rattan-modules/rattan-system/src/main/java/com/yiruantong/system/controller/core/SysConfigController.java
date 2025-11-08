package com.yiruantong.system.controller.core;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yiruantong.system.domain.core.SysConfig;
import com.yiruantong.system.domain.core.bo.SysConfigBo;
import com.yiruantong.system.domain.core.vo.SysConfigVo;
import com.yiruantong.system.mapper.core.SysConfigMapper;
import com.yiruantong.system.service.core.ISysConfigService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 参数配置 信息操作处理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/config")
public class SysConfigController extends AbstractController<SysConfigMapper, SysConfig, SysConfigVo, SysConfigBo> {

  private final ISysConfigService configService;

  /**
   * 获取参数配置列表
   */
  @SaCheckPermission("system:config:list")
  @GetMapping("/list")
  public TableDataInfo<SysConfigVo> list(SysConfigBo config, PageQuery pageQuery) {
    return configService.selectPageConfigList(config, pageQuery);
  }

  /**
   * 导出参数配置列表
   */
  @Log(title = "参数管理", businessType = BusinessType.EXPORT)
  @SaCheckPermission("system:config:export")
  @PostMapping("/export")
  public void export(SysConfigBo config, HttpServletResponse response) {
    List<SysConfigVo> list = configService.selectConfigList(config);
    ExcelUtil.exportExcel(list, "参数数据", SysConfigVo.class, response);
  }

  /**
   * 根据参数编号获取详细信息
   *
   * @param configId 参数ID
   */
  @SaCheckPermission("system:config:query")
  @GetMapping(value = "/{configId}")
  public R<SysConfigVo> getInfo(@PathVariable Long configId) {
    return R.ok(configService.selectConfigById(configId));
  }

  /**
   * 根据参数键名查询参数值
   *
   * @param configKey 参数Key
   */
  @GetMapping(value = "/configKey/{configKey}")
  public R<Void> getConfigKey(@PathVariable String configKey) {
    return R.ok(configService.selectConfigByKey(configKey));
  }

  /**
   * 新增参数配置
   */
  @SaCheckPermission("system:config:add")
  @Log(title = "参数管理", businessType = BusinessType.INSERT)
  @PostMapping
  public R<Void> add(@Validated @RequestBody SysConfigBo config) {
    if (!configService.checkConfigKeyUnique(config)) {
      return R.fail("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    configService.insertConfig(config);
    return R.ok();
  }

  /**
   * 修改参数配置
   */
  @SaCheckPermission("system:config:edit")
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editData(@Validated @RequestBody SysConfigBo config) {
    if (!configService.checkConfigKeyUnique(config)) {
      return R.fail("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    configService.updateConfig(config);
    return R.ok();
  }

  /**
   * 根据参数键名修改参数配置
   */
  @SaCheckPermission("system:config:edit")
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @PutMapping("/updateByKey")
  public R<Void> updateByKey(@RequestBody SysConfigBo config) {
    configService.updateConfig(config);
    return R.ok();
  }

  /**
   * 删除参数配置
   *
   * @param configIds 参数ID串
   */
  @SaCheckPermission("system:config:remove")
  @Log(title = "参数管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{configIds}")
  public R<Void> remove(@PathVariable Long[] configIds) {
    configService.deleteConfigByIds(configIds);
    return R.ok();
  }

  /**
   * 刷新参数缓存
   */
  @SaCheckPermission("system:config:remove")
  @Log(title = "参数管理", businessType = BusinessType.CLEAN)
  @DeleteMapping("/refreshCache")
  public R<Void> refreshCache() {
    configService.resetConfigCache();
    return R.ok();
  }

  /**
   * 据参数 keys 获取参数值，多个值采用都好分隔
   */
  @PostMapping("/getConfigValues")
  public R<List<Map<String, Object>>> getConfigValues(@RequestBody Map<String, Object> config) {
    String keys = config.get("keys").toString();
    List<Map<String, Object>> configValues = configService.getConfigValues(keys);
    return R.ok(configValues);
  }

  /**
   * 批量保存参数集合
   */
  @PostMapping("/saveParams")
  public R<List<SysConfigVo>> saveParams(@RequestBody Map<String, Object> map) {
    return configService.saveParams(map);
  }
}
