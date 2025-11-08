package com.yiruantong.system.service.tenant.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.permission.dto.SaveTenantResultDto;
import com.yiruantong.system.domain.tenant.SysTenant;
import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.bo.SysTenantBo;
import com.yiruantong.system.domain.tenant.bo.SysTenantPackageBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantPackageVo;
import com.yiruantong.system.mapper.tenant.SysTenantMapper;
import com.yiruantong.system.mapper.tenant.SysTenantPackageMapper;
import com.yiruantong.system.service.permission.ISysUserService;
import com.yiruantong.system.service.tenant.ISysTenantPackageService;
import com.yiruantong.system.service.tenant.ISysTenantService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.tenant.TenantAppCommandEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.tenant.ISysTenantProfileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 租户套餐Service业务层处理
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysTenantPackageServiceImpl extends ServiceImplPlus<SysTenantPackageMapper, SysTenantPackage, SysTenantPackageVo, SysTenantPackageBo> implements ISysTenantPackageService {

  private final SysTenantPackageMapper baseMapper;
  private final SysTenantMapper tenantMapper;
  private final ISysTenantProfileService sysTenantProfileService;
  private final ISysTenantService sysTenantService;
  private final ISysUserService sysUserService;

  @Autowired
  private List<ITenantInitService> tenantInitServiceList; // 所有初始化接口

  /**
   * 查询租户套餐
   */
  @Override
  public SysTenantPackageVo queryById(Long packageId) {
    return baseMapper.selectVoById(packageId);
  }

  /**
   * 查询租户套餐列表
   */
  @Override
  public TableDataInfo<SysTenantPackageVo> queryPageList(SysTenantPackageBo bo, PageQuery pageQuery) {
    LambdaQueryWrapper<SysTenantPackage> lqw = buildQueryWrapper(bo);
    Page<SysTenantPackageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
    return TableDataInfo.build(result);
  }

  @Override
  public List<SysTenantPackageVo> selectList() {
    return baseMapper.selectVoList(new LambdaQueryWrapper<SysTenantPackage>()
      .eq(SysTenantPackage::getStatus, TenantConstants.NORMAL));
  }

  /**
   * 查询租户套餐列表
   */
  @Override
  public List<SysTenantPackageVo> queryList(SysTenantPackageBo bo) {
    LambdaQueryWrapper<SysTenantPackage> lqw = buildQueryWrapper(bo);
    return baseMapper.selectVoList(lqw);
  }

  private LambdaQueryWrapper<SysTenantPackage> buildQueryWrapper(SysTenantPackageBo bo) {
    LambdaQueryWrapper<SysTenantPackage> lqw = Wrappers.lambdaQuery();
    lqw.like(StringUtils.isNotBlank(bo.getPackageName()), SysTenantPackage::getPackageName, bo.getPackageName());
    lqw.eq(Objects.equals(bo.getStatus(), EnableEnum.ENABLE.getId()), SysTenantPackage::getStatus, bo.getStatus());
    lqw.orderByAsc(SysTenantPackage::getPackageId);
    return lqw;
  }

  /**
   * 新增租户套餐
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean insertByBo(SysTenantPackageBo bo) {
    SysTenantPackage add = MapstructUtils.convert(bo, SysTenantPackage.class);
    // 保存菜单id
    List<Long> menuIds = Arrays.asList(Convert.toLong(bo.getMenuIds()));
    if (CollUtil.isNotEmpty(menuIds)) {
      add.setMenuIds(StringUtils.join(menuIds, ", "));
    } else {
      add.setMenuIds("");
    }
    boolean flag = baseMapper.insert(add) > 0;
    if (flag) {
      bo.setPackageId(add.getPackageId());
    }
    return flag;
  }

  /**
   * 修改租户套餐
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateByBo(SysTenantPackageBo bo) {
    SysTenantPackage update = MapstructUtils.convert(bo, SysTenantPackage.class);
    // 保存菜单id
    List<Long> menuIds = Collections.singletonList(Convert.toLong(bo.getMenuIds()));
    if (CollUtil.isNotEmpty(menuIds)) {
      update.setMenuIds(StringUtils.join(menuIds, ", "));
    } else {
      if (update != null) {
        update.setMenuIds("");
      }
    }
    return baseMapper.updateById(update) > 0;
  }

  /**
   * 修改套餐状态
   *
   * @param bo 套餐信息
   * @return 结果
   */
  @Override
  public int updatePackageStatus(SysTenantPackageBo bo) {
    SysTenantPackage tenantPackage = MapstructUtils.convert(bo, SysTenantPackage.class);
    return baseMapper.updateById(tenantPackage);
  }

  /**
   * 批量删除租户套餐
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
    if (isValid) {
      boolean exists = tenantMapper.exists(new LambdaQueryWrapper<SysTenant>().in(SysTenant::getPackageId, ids));
      if (exists) {
        throw new ServiceException("租户套餐已被使用");
      }
    }
    return baseMapper.deleteBatchIds(ids) > 0;
  }

  /**
   * 用户申请APP
   *
   * @param fromPackageId 从这个套餐ID复制数据
   * @return 申请是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Void> useApp(Long fromPackageId, TenantAppCommandEnum commandEnum) {
    TenantHelper.enableIgnore();
    // 创建账套
    SaveTenantResultDto saveTenantResultDto = saveSysTenant(fromPackageId);

    // 拷贝数据
    if (EnumUtil.equals(commandEnum, TenantAppCommandEnum.ONLY_APP.getName())) {
      for (var tenantInitService : tenantInitServiceList) {
        tenantInitService.tenantCopy(fromPackageId, saveTenantResultDto.getSysTenant().getTenantId(), saveTenantResultDto.getSysUser().getUserId());
      }
    }
    TenantHelper.disableIgnore();

    return R.ok();
  }

  @NotNull
  private SaveTenantResultDto saveSysTenant(Long packageId) {
    SysTenantPackage sysTenantPackage = this.getById(packageId);
    Assert.isFalse(ObjectUtil.isNull(sysTenantPackage), "申请的套餐模板不存在！");
    SysTenantProfile profile = sysTenantProfileService.getProfile(LoginHelper.getPhoneNumber());
    SysTenantBo sysTenant = new SysTenantBo();
    sysTenant.setPackageId(sysTenantPackage.getPackageId());
    sysTenant.setPackageName(sysTenantPackage.getPackageName());
    sysTenant.setContactUserName(LoginHelper.getUsername());
    sysTenant.setContactPhone(profile.getPhoneNumber());

    sysTenant.setCompanyName(profile.getCompanyName());
    sysTenant.setAddress(profile.getAddress());
    sysTenant.setIntro(profile.getIntro());
    sysTenant.setLicenseNumber(profile.getLicenseNumber());
    sysTenant.setLogoLong(sysTenantPackage.getLogoLong());
    sysTenant.setLogoShort(sysTenantPackage.getLogoShort());
    sysTenant.setLoginTemplate(sysTenantPackage.getLoginTemplate());
    sysTenant.setMainTemplate(sysTenantPackage.getMainTemplate());
    sysTenant.setSysFullName(sysTenantPackage.getSysFullName());
    sysTenant.setSysShortName(sysTenantPackage.getSysShortName());

    sysTenant.setStatus(EnableEnum.ENABLE.getId());
    sysTenant.setExpireTime(DateUtil.offsetMonth(DateUtil.date(), 12)); // 效期1年
    SaveTenantResultDto saveTenantResultDto = new SaveTenantResultDto();
    sysTenantService.insertByBo(sysTenant, saveTenantResultDto); // 新增租户
    return saveTenantResultDto;
  }
}
