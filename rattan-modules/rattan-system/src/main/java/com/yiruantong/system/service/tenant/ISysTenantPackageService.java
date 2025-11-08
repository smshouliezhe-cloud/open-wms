package com.yiruantong.system.service.tenant;

import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.system.domain.tenant.bo.SysTenantPackageBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantPackageVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.tenant.TenantAppCommandEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Collection;
import java.util.List;

/**
 * 租户套餐Service接口
 *
 * @author YiRuanTong
 */
public interface ISysTenantPackageService extends IServicePlus<SysTenantPackage, SysTenantPackageVo, SysTenantPackageBo> {

  /**
   * 查询租户套餐
   */
  SysTenantPackageVo queryById(Long packageId);

  /**
   * 查询租户套餐列表
   */
  TableDataInfo<SysTenantPackageVo> queryPageList(SysTenantPackageBo bo, PageQuery pageQuery);

  /**
   * 查询租户套餐已启用列表
   */
  List<SysTenantPackageVo> selectList();

  /**
   * 查询租户套餐列表
   */
  List<SysTenantPackageVo> queryList(SysTenantPackageBo bo);

  /**
   * 新增租户套餐
   */
  Boolean insertByBo(SysTenantPackageBo bo);

  /**
   * 修改租户套餐
   */
  Boolean updateByBo(SysTenantPackageBo bo);

  /**
   * 修改套餐状态
   */
  int updatePackageStatus(SysTenantPackageBo bo);

  /**
   * 校验并批量删除租户套餐信息
   */
  Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

  /**
   * 创建账套应用
   *
   * @param fromPackageId 来源套餐ID
   * @param commandEnum   创建命令
   * @return R
   */
  R<Void> useApp(Long fromPackageId, TenantAppCommandEnum commandEnum);
}
