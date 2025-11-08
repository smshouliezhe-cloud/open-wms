package com.yiruantong.system.service.tenant.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.system.domain.tenant.bo.SysClientBo;
import com.yiruantong.system.domain.tenant.vo.SysClientVo;
import com.yiruantong.system.mapper.tenant.SysClientMapper;
import com.yiruantong.system.service.tenant.ISysClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 客户端管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-06-18
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysClientServiceImpl extends ServiceImplPlus<SysClientMapper, SysClient, SysClientVo, SysClientBo> implements ISysClientService {

  private final SysClientMapper baseMapper;

  /**
   * 查询客户端管理
   */
  @Override
  public SysClientVo queryById(Long id) {
    SysClientVo vo = baseMapper.selectVoById(id);
//    vo.setGrantTypeList(List.of(vo.getGrantType().split(",")));
    return vo;
  }


  /**
   * 查询客户端管理
   */
  @Override
  public SysClient queryByClientId(String clientId) {
    return baseMapper.selectOne(new LambdaQueryWrapper<SysClient>().eq(SysClient::getClientId, clientId));
  }

  /**
   * 查询客户端管理列表
   */
  @Override
  public TableDataInfo<SysClientVo> queryPageList(SysClientBo bo, PageQuery pageQuery) {
    LambdaQueryWrapper<SysClient> lqw = buildQueryWrapper(bo);
    Page<SysClientVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
//    result.getRecords().forEach(r -> r.setGrantTypeList(List.of(r.getGrantType().split(","))));
    return TableDataInfo.build(result);
  }

  /**
   * 查询客户端管理列表
   */
  @Override
  public List<SysClientVo> queryList(SysClientBo bo) {
    LambdaQueryWrapper<SysClient> lqw = buildQueryWrapper(bo);
    return baseMapper.selectVoList(lqw);
  }

  private LambdaQueryWrapper<SysClient> buildQueryWrapper(SysClientBo bo) {
    LambdaQueryWrapper<SysClient> lqw = Wrappers.lambdaQuery();
    lqw.eq(StringUtils.isNotBlank(bo.getClientId()), SysClient::getClientId, bo.getClientId());
    lqw.eq(StringUtils.isNotBlank(bo.getClientKey()), SysClient::getClientKey, bo.getClientKey());
    lqw.eq(StringUtils.isNotBlank(bo.getClientSecret()), SysClient::getClientSecret, bo.getClientSecret());
    lqw.eq(NumberUtils.isValidNumber(bo.getStatus()), SysClient::getStatus, bo.getStatus());
    lqw.orderByAsc(SysClient::getId);
    return lqw;
  }

  /**
   * 新增客户端管理
   */
  @Override
  public Boolean insertByBo(SysClientBo bo) {
    SysClient add = MapstructUtils.convert(bo, SysClient.class);
    validEntityBeforeSave(add);
//    add.setGrantType(String.join(",", bo.getGrantTypeList()));
    // 生成clientid
    String clientKey = bo.getClientKey();
    String clientSecret = bo.getClientSecret();
    add.setClientId(SecureUtil.md5(clientKey + clientSecret));
    boolean flag = baseMapper.insert(add) > 0;
    if (flag) {
      bo.setId(add.getId());
    }
    return flag;
  }

  /**
   * 修改客户端管理
   */
  @Override
  public Boolean updateByBo(SysClientBo bo) {
    SysClient update = MapstructUtils.convert(bo, SysClient.class);
    validEntityBeforeSave(update);
//    update.setGrantType(String.join(",", bo.getGrantTypeList()));
    return baseMapper.updateById(update) > 0;
  }

  /**
   * 修改状态
   */
  @Override
  public int updateUserStatus(Long id, Byte status) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<SysClient>()
        .set(SysClient::getStatus, status)
        .eq(SysClient::getId, id));
  }

  /**
   * 批量删除客户端管理
   */
  @Override
  public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
    if (isValid) {
      //TODO 做一些业务上的校验,判断是否需要校验
    }
    return baseMapper.deleteBatchIds(ids) > 0;
  }

  @Override
  public void beforeSaveEditor(SaveEditorBo<SysClientBo> saveEditorBo) {
    var master = saveEditorBo.getData().getMaster();
    if (StringUtils.isEmpty(master.getClientId())) {
      // 生成clientid
      String clientKey = master.getClientKey();
      String clientSecret = master.getClientSecret();
      String clientId = SecureUtil.md5(clientKey + clientSecret);
      master.setClientId(clientId);
    }
  }
}
