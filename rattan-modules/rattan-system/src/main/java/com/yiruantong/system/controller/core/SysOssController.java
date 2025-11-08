package com.yiruantong.system.controller.core;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.validate.QueryGroup;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.system.domain.core.SysOss;
import com.yiruantong.system.domain.core.bo.SysOssBo;
import com.yiruantong.system.domain.core.vo.SysOssUploadVo;
import com.yiruantong.system.domain.core.vo.SysOssVo;
import com.yiruantong.system.mapper.core.SysOssMapper;
import com.yiruantong.system.service.core.ISysOssService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传 控制层
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/oss")
public class SysOssController extends AbstractController<SysOssMapper, SysOss, SysOssVo, SysOssBo> {

  private final ISysOssService ossService;

  /**
   * 查询OSS对象存储列表
   */
  @SaCheckPermission("system:oss:list")
  @GetMapping("/list")
  public TableDataInfo<SysOssVo> list(@Validated(QueryGroup.class) SysOssBo bo, PageQuery pageQuery) {
    return ossService.queryPageList(bo, pageQuery);
  }

  /**
   * 查询OSS对象基于id串
   *
   * @param ossIds OSS对象ID串
   */
  @SaCheckPermission("system:oss:list")
  @GetMapping("/listByIds/{ossIds}")
  public R<List<SysOssVo>> listByIds(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ossIds) {
    List<SysOssVo> list = MapstructUtils.convert(ossService.listByIds(Arrays.asList(ossIds)), SysOssVo.class);
    return R.ok(list);
  }

  /**
   * 上传OSS对象存储
   *
   * @param file 文件
   */
  @Log(title = "OSS对象存储", businessType = BusinessType.INSERT)
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public R<SysOssUploadVo> upload(@RequestPart("file") MultipartFile file) {
    if (ObjectUtil.isNull(file)) {
      return R.fail("上传文件不能为空");
    }
    SysOssVo oss;
    if (StpUtil.isLogin()) {
      oss = ossService.upload(file);
    } else {
      // 临时获取配置
      TenantHelper.setDynamic(TenantConstants.DEFAULT_TENANT_ID);
      oss = ossService.upload(file);
      TenantHelper.clearDynamic();
    }
    SysOssUploadVo uploadVo = new SysOssUploadVo();
    uploadVo.setUrl(oss.getUrl());
    uploadVo.setFileName(oss.getFileName());
    uploadVo.setOriginalName(oss.getOriginalName());
    uploadVo.setOriginalName(oss.getOriginalName());
    uploadVo.setFileSuffix(oss.getFileSuffix());
    uploadVo.setMd5key(oss.getMd5key());
    uploadVo.setFileSize(oss.getFileSize());
    uploadVo.setOssId(oss.getOssId().toString());
    return R.ok(uploadVo);
  }

  /**
   * 上传OSS对象存储
   *
   * @param file 文件
   */
  @Log(title = "OSS对象存储", businessType = BusinessType.INSERT)
  @SaIgnore
  @PostMapping(value = "/upload2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public R<SysOssUploadVo> upload2(@RequestPart("file") MultipartFile file) {
    if (ObjectUtil.isNull(file)) {
      return R.fail("上传文件不能为空");
    }
    SysOssVo oss;
    if (StpUtil.isLogin()) {
      oss = ossService.upload(file);
    } else {
      // 临时获取配置
      TenantHelper.setDynamic(TenantConstants.DEFAULT_TENANT_ID);
      oss = ossService.upload(file);
      TenantHelper.clearDynamic();
    }
    SysOssUploadVo uploadVo = new SysOssUploadVo();
    uploadVo.setUrl(oss.getUrl());
    uploadVo.setFileName(oss.getFileName());
    uploadVo.setOriginalName(oss.getOriginalName());
    uploadVo.setOriginalName(oss.getOriginalName());
    uploadVo.setFileSuffix(oss.getFileSuffix());
    uploadVo.setMd5key(oss.getMd5key());
    uploadVo.setFileSize(oss.getFileSize());
    uploadVo.setOssId(oss.getOssId().toString());
    return R.ok(uploadVo);
  }

  /**
   * 下载OSS对象
   *
   * @param ossId OSS对象ID
   */
  @SaCheckPermission("system:oss:download")
  @GetMapping("/download/{ossId}")
  public void download(@PathVariable Long ossId, HttpServletResponse response) throws IOException {
    ossService.download(ossId, response);
  }
}
