package com.yiruantong.system.service.core.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.core.SysOss;
import com.yiruantong.system.domain.core.bo.SysOssBo;
import com.yiruantong.system.domain.core.vo.SysOssVo;
import com.yiruantong.system.mapper.core.SysOssMapper;
import com.yiruantong.system.service.core.ISysOssService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.CacheNames;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.service.OssService;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.file.FileUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.oss.core.OssClient;
import com.yiruantong.common.oss.entity.UploadResult;
import com.yiruantong.common.oss.enumd.AccessPolicyType;
import com.yiruantong.common.oss.factory.OssFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 文件上传 服务层实现
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysOssServiceImpl extends ServiceImplPlus<SysOssMapper, SysOss, SysOssVo, SysOssBo> implements ISysOssService, OssService {

  private final SysOssMapper baseMapper;

  @Override
  public TableDataInfo<SysOssVo> queryPageList(SysOssBo bo, PageQuery pageQuery) {
    LambdaQueryWrapper<SysOss> lqw = buildQueryWrapper(bo);
    Page<SysOssVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
    List<SysOssVo> filterResult = StreamUtils.toList(result.getRecords(), this::matchingUrl);
    result.setRecords(filterResult);
    return TableDataInfo.build(result);
  }

  @Override
  public String selectUrlByIds(String ossIds) {
    List<String> list = new ArrayList<>();
    for (Long id : StringUtils.splitTo(ossIds, Convert::toLong)) {
      SysOssVo vo = SpringUtils.getAopProxy(this).getById(id);
      if (ObjectUtil.isNotNull(vo)) {
        list.add(this.matchingUrl(vo).getUrl());
      }
    }
    return String.join(StringUtils.SEPARATOR_COMMA, list);
  }

  private LambdaQueryWrapper<SysOss> buildQueryWrapper(SysOssBo bo) {
    Map<String, Object> params = bo.getParams();
    LambdaQueryWrapper<SysOss> lqw = Wrappers.lambdaQuery();
    lqw.like(StringUtils.isNotBlank(bo.getFileName()), SysOss::getFileName, bo.getFileName());
    lqw.like(StringUtils.isNotBlank(bo.getOriginalName()), SysOss::getOriginalName, bo.getOriginalName());
    lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), SysOss::getFileSuffix, bo.getFileSuffix());
    lqw.eq(StringUtils.isNotBlank(bo.getUrl()), SysOss::getUrl, bo.getUrl());
    lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
      SysOss::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
    lqw.eq(ObjectUtil.isNotNull(bo.getCreateBy()), SysOss::getCreateBy, bo.getCreateBy());
    lqw.eq(StringUtils.isNotBlank(bo.getService()), SysOss::getService, bo.getService());
    lqw.orderByAsc(SysOss::getOssId);
    return lqw;
  }

  @Cacheable(cacheNames = CacheNames.SYS_OSS, key = "#ossId")
  @Override
  public SysOssVo getById(Long ossId) {
    return baseMapper.selectVoById(ossId);
  }

  @Override
  public void download(Long ossId, HttpServletResponse response) throws IOException {
    SysOssVo sysOss = SpringUtils.getAopProxy(this).getById(ossId);
    if (ObjectUtil.isNull(sysOss)) {
      throw new ServiceException("文件数据不存在!");
    }
    FileUtils.setAttachmentResponseHeader(response, sysOss.getOriginalName());
    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
    OssClient storage = OssFactory.instance(sysOss.getService());
    try (InputStream inputStream = storage.getObjectContent(sysOss.getUrl())) {
      int available = inputStream.available();
      IoUtil.copy(inputStream, response.getOutputStream(), available);
      response.setContentLength(available);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

  @Override
  public SysOssVo upload(MultipartFile file) {
    String originalFileName = file.getOriginalFilename();
    String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
    OssClient storage = OssFactory.instance();
    String md5Key;
    try {
      md5Key = storage.getMd5Key(file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    SysOss sysOss = getExistOss(md5Key);

    UploadResult uploadResult;
    if (ObjectUtil.isNotNull(sysOss)) {
      // 文件已存在，不在上传，直接使用
      uploadResult = UploadResult.builder().build();
      uploadResult.setFilename(sysOss.getFileName());
      uploadResult.setUrl(sysOss.getUrl());
      uploadResult.setMd5Key(md5Key);
    } else {
      try {
        uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
      } catch (IOException e) {
        throw new ServiceException(e.getMessage());
      }
    }

    // 保存文件信息
    return buildResultEntity(sysOss, originalFileName, suffix, storage.getConfigKey(), uploadResult);
  }

  @Override
  public SysOssVo upload(File file) {
    String originalFileName = file.getName();
    String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
    OssClient storage = OssFactory.instance();
    String md5Key = storage.getMd5Key(file);
    SysOss sysOss = getExistOss(md5Key);

    UploadResult uploadResult;
    if (ObjectUtil.isNotNull(sysOss)) {
      // 文件已存在，不在上传，直接使用
      uploadResult = UploadResult.builder().build();
      uploadResult.setFilename(sysOss.getFileName());
      uploadResult.setUrl(sysOss.getUrl());
      uploadResult.setMd5Key(md5Key);
      uploadResult.setFileSize(sysOss.getFileSize());
    } else {
      try {
        uploadResult = storage.uploadSuffix(md5Key, file, suffix);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    // 保存文件信息
    return buildResultEntity(sysOss, originalFileName, suffix, storage.getConfigKey(), uploadResult);
  }

  private SysOss getExistOss(String md5Key) {
    // 验证文件是否存在，避免重复上传
    LambdaQueryWrapper<SysOss> sysOssLambdaQueryWrapper = new LambdaQueryWrapper<>();
    sysOssLambdaQueryWrapper.eq(SysOss::getMd5key, md5Key);
    return this.getOne(sysOssLambdaQueryWrapper);
  }

  @NotNull
  private SysOssVo buildResultEntity(SysOss oss, String originalfileName, String suffix, String configKey, UploadResult uploadResult) {
    if (ObjectUtil.isNull(oss)) {
      oss = new SysOss();
      oss.setUrl(uploadResult.getUrl());
      oss.setFileSuffix(suffix);
      oss.setFileName(uploadResult.getFilename());
      oss.setOriginalName(originalfileName);
      oss.setService(configKey);
      oss.setMd5key(uploadResult.getMd5Key());
      oss.setFileSize(uploadResult.getFileSize());
      baseMapper.insert(oss);
    }

    SysOssVo sysOssVo = MapstructUtils.convert(oss, SysOssVo.class);
    return this.matchingUrl(sysOssVo);
  }

  @Override
  public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
    if (isValid) {
      // 做一些业务上的校验,判断是否需要校验
    }
    List<SysOss> list = baseMapper.selectBatchIds(ids);
    for (SysOss sysOss : list) {
      OssClient storage = OssFactory.instance(sysOss.getService());
      storage.delete(sysOss.getUrl());
    }
    return baseMapper.deleteBatchIds(ids) > 0;
  }

  /**
   * 匹配Url
   *
   * @param oss OSS对象
   * @return oss 匹配Url的OSS对象
   */
  private SysOssVo matchingUrl(SysOssVo oss) {
    OssClient storage = OssFactory.instance(oss.getService());
    // 仅修改桶类型为 private 的URL，临时URL时长为120s
    if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
      oss.setUrl(storage.getPrivateUrl(oss.getFileName(), 120));
    }
    return oss;
  }
}
