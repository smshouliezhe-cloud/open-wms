package com.yiruantong.system.controller.core;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yiruantong.system.domain.core.SysOssConfig;
import com.yiruantong.system.domain.core.bo.SysOssConfigBo;
import com.yiruantong.system.domain.core.vo.SysOssConfigVo;
import com.yiruantong.system.mapper.core.SysOssConfigMapper;
import com.yiruantong.system.service.core.ISysOssConfigService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.core.validate.QueryGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 对象存储配置
 *
 * @author YiRuanTong
 * @author 孤舟烟雨
 * @date 2021-08-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/ossConfig")
public class SysOssConfigController extends AbstractController<SysOssConfigMapper, SysOssConfig, SysOssConfigVo, SysOssConfigBo> {

  private final ISysOssConfigService ossConfigService;

  /**
   * 查询对象存储配置列表
   */
  @SaCheckPermission("system:oss:list")
  @GetMapping("/list")
  public TableDataInfo<SysOssConfigVo> list(@Validated(QueryGroup.class) SysOssConfigBo bo, PageQuery pageQuery) {
    return ossConfigService.queryPageList(bo, pageQuery);
  }

  /**
   * 获取对象存储配置详细信息
   *
   * @param ossConfigId OSS配置ID
   */
  @SaCheckPermission("system:oss:query")
  @GetMapping("/{ossConfigId}")
  public R<SysOssConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable Long ossConfigId) {
    return R.ok(ossConfigService.queryById(ossConfigId));
  }

  /**
   * 新增对象存储配置
   */
  @SaCheckPermission("system:oss:add")
  @Log(title = "对象存储配置", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping()
  public R<Void> add(@Validated(AddGroup.class) @RequestBody SysOssConfigBo bo) {
    return toAjax(ossConfigService.insertByBo(bo));
  }

  /**
   * 修改对象存储配置
   */
  @SaCheckPermission("system:oss:edit")
  @Log(title = "对象存储配置", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PutMapping()
  public R<Void> editData(@Validated(EditGroup.class) @RequestBody SysOssConfigBo bo) {
    return toAjax(ossConfigService.updateByBo(bo));
  }

  /**
   * 状态修改
   */
  @SaCheckPermission("system:oss:edit")
  @Log(title = "对象存储状态修改", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public R<Void> changeStatus(@RequestBody SysOssConfigBo bo) {
    return toAjax(ossConfigService.updateOssConfigStatus(bo));
  }
}
