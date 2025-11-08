package com.yiruantong.system.controller.goview;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.system.domain.core.vo.SysOssVo;
import com.yiruantong.system.domain.goview.GoViewProject;
import com.yiruantong.system.domain.goview.bo.GoViewProjectBo;
import com.yiruantong.system.domain.goview.vo.GoViewProjectVo;
import com.yiruantong.system.mapper.goview.GoViewProjectMapper;
import com.yiruantong.system.service.core.ISysOssService;
import com.yiruantong.system.service.goview.IGoViewProjectService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 大屏项目
 *
 * @author YRT
 * @date 2024-11-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/goview/viewProject")
public class GoViewProjectController extends AbstractController<GoViewProjectMapper, GoViewProject, GoViewProjectVo, GoViewProjectBo> {
  private final IGoViewProjectService goViewProjectService;
  private final ISysOssService sysOssService;

  /**
   * 查询大屏项目列表
   */
  @GetMapping("/list")
  public TableDataInfo<GoViewProjectVo> list(GoViewProjectBo bo, PageQuery pageQuery) {
    return goViewProjectService.pageList(pageQuery);
  }

  /**
   * 新增大屏项目
   */
  @Log(title = "大屏项目", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/create")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody GoViewProjectBo bo) {
    if (ObjectUtil.isEmpty(bo.getContent())) {
      bo.setContent("{\"componentList\":[]}");
    }

    goViewProjectService.insertByBo(bo);
    return R.ok(MapUtil.of("id", bo.getId()));
  }

  /**
   * 获取大屏项目详细信息
   *
   * @param id 主键
   */
  @GetMapping("/getData")
  public R<GoViewProjectVo> getData(@RequestParam(value = "projectId") Long id) {
    return R.ok(goViewProjectService.selectById(id));
  }

  /**
   * 修改大屏项目
   */
  @SaIgnore
  @Log(title = "大屏项目", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping(value = {"/save/data", "/edit"})
  public R<Void> editData(@Validated(EditGroup.class) @RequestBody GoViewProjectBo bo) {
    if (ObjectUtil.isNull(bo.getId())) {
      bo.setId(bo.getProjectId());
    }
    return toAjax(goViewProjectService.updateByBo(bo));
  }

  /**
   * 发布大屏项目
   */
  @Log(title = "大屏项目", businessType = BusinessType.UPDATE)
  @PutMapping("/publish")
  public R<Void> publish(@Validated(EditGroup.class) @RequestBody GoViewProjectBo bo) {
    return toAjax(goViewProjectService.updateByBo(bo));
  }

  /**
   * 删除大屏项目
   *
   * @param ids 主键串
   */
  @Log(title = "大屏项目", businessType = BusinessType.DELETE)
  @DeleteMapping("/delete")
  public R<Void> remove(@RequestParam(value = "ids") Long[] ids) {
    return toAjax(goViewProjectService.deleteByIds(ids));
  }

  @SaIgnore
  @PostMapping(value = "/upload")
  public R<Map<String, String>> upload(@RequestBody MultipartFile object) {
    if (ObjectUtil.isNull(object)) {
      return R.fail("上传文件不能为空");
    }
    SysOssVo oss = sysOssService.upload(object);
    Map<String, String> map = new HashMap<>();
    map.put("fileurl", oss.getUrl());
    map.put("fileName", oss.getOriginalName());
    return R.ok(map);
  }

  /**
   * 获取OSS信息
   */
  @SaIgnore
  @GetMapping("/getOssInfo")
  public R<Void> getOssInfo() {
    return R.ok();
  }
}
