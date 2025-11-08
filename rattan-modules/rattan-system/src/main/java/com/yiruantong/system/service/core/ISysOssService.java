package com.yiruantong.system.service.core;

import com.yiruantong.system.domain.core.SysOss;
import com.yiruantong.system.domain.core.bo.SysOssBo;
import com.yiruantong.system.domain.core.vo.SysOssVo;
import jakarta.servlet.http.HttpServletResponse;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * 文件上传 服务层
 *
 * @author YiRuanTong
 */
public interface ISysOssService extends IServicePlus<SysOss, SysOssVo, SysOssBo> {

  TableDataInfo<SysOssVo> queryPageList(SysOssBo sysOss, PageQuery pageQuery);

  SysOssVo getById(Long ossId);

  SysOssVo upload(MultipartFile file);

  SysOssVo upload(File file);

  void download(Long ossId, HttpServletResponse response) throws IOException;

  Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
