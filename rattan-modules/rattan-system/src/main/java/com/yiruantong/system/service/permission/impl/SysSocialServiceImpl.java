package com.yiruantong.system.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.permission.SysSocial;
import com.yiruantong.system.domain.permission.bo.SysSocialBo;
import com.yiruantong.system.domain.permission.vo.SysSocialVo;
import com.yiruantong.system.mapper.permission.SysSocialMapper;
import com.yiruantong.system.service.permission.ISysSocialService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.utils.MapstructUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社会化关系Service业务层处理
 *
 * @author thiszhc
 * @date 2023-06-12
 */
@RequiredArgsConstructor
@Service
public class SysSocialServiceImpl implements ISysSocialService {

  private final SysSocialMapper baseMapper;


  /**
   * 查询社会化关系
   */
  @Override
  public SysSocialVo queryById(String id) {
    return baseMapper.selectVoById(id);
  }

  /**
   * 授权列表
   */
  @Override
  public List<SysSocialVo> queryList() {
    return baseMapper.selectVoList();
  }

  @Override
  public List<SysSocialVo> queryListByUserId(Long userId) {
    return baseMapper.selectVoList(new LambdaQueryWrapper<SysSocial>().eq(SysSocial::getUserId, userId));
  }


  /**
   * 新增社会化关系
   */
  @Override
  public Boolean insertByBo(SysSocialBo bo) {
    SysSocial add = MapstructUtils.convert(bo, SysSocial.class);
    validEntityBeforeSave(add);
    boolean flag = baseMapper.insert(add) > 0;
    if (flag) {
      if (add != null) {
        bo.setId(add.getId());
      } else {
        return false;
      }
    }
    return flag;
  }

  /**
   * 更新社会化关系
   */
  @Override
  public Boolean updateByBo(SysSocialBo bo) {
    SysSocial update = MapstructUtils.convert(bo, SysSocial.class);
    validEntityBeforeSave(update);
    return baseMapper.updateById(update) > 0;
  }

  /**
   * 保存前的数据校验
   */
  private void validEntityBeforeSave(SysSocial entity) {
    //TODO 做一些数据校验,如唯一约束
  }


  /**
   * 删除社会化关系
   */
  @Override
  public Boolean deleteWithValidById(Long id) {
    return baseMapper.deleteById(id) > 0;
  }


  /**
   * 根据 authId 查询用户信息
   *
   * @param authId 认证id
   * @return 授权信息
   */
  @Override
  public SysSocialVo selectByAuthId(String authId) {
    return baseMapper.selectVoOne(new LambdaQueryWrapper<SysSocial>().eq(SysSocial::getAuthId, authId));
  }

}
