package com.yiruantong.generator.service;

import com.yiruantong.generator.domain.vo.GenTableTenantVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.generator.domain.GenTableTenant;
import com.yiruantong.generator.domain.bo.GenTableTenantBo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 生成表租户数据Service接口
 *
 * @author 谢天保
 * @date 2023-06-19
 */
public interface IGenTableTenantService
  extends IServicePlus<GenTableTenant, GenTableTenantVo, GenTableTenantBo> {

  /**
   * 查询生成表租户数据
   */
  GenTableTenantVo queryById(Long tableTenantId);

  /**
   * 查询生成表租户数据列表
   */
  TableDataInfo<GenTableTenantVo> queryPageList(GenTableTenantBo bo, PageQuery pageQuery);

  /**
   * 查询生成表租户数据列表
   */
  List<GenTableTenantVo> queryList(GenTableTenantBo bo);

  /**
   * 新增生成表租户数据
   */
  Boolean insertByBo(GenTableTenantBo bo);

  /**
   * 修改生成表租户数据
   */
  Boolean updateByBo(GenTableTenantBo bo);

  /**
   * 校验并批量删除生成表租户数据信息
   */
  Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

  /**
   * 保存UI设计数据
   */
  R<Map<String, Object>> saveui(GenTableTenantBo genTableTenantBo);
}
