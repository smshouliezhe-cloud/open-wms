package com.yiruantong.system.service.goview.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yiruantong.system.domain.goview.GoViewProject;
import com.yiruantong.system.domain.goview.bo.GoViewProjectBo;
import com.yiruantong.system.domain.goview.vo.GoViewProjectVo;
import com.yiruantong.system.mapper.goview.GoViewProjectMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.system.service.goview.IGoViewProjectService;
import org.springframework.stereotype.Service;

/**
 * 大屏项目Service业务层处理
 *
 * @author YRT
 * @date 2024-11-01
 */
@RequiredArgsConstructor
@Service
public class GoViewProjectServiceImpl extends ServiceImplPlus<GoViewProjectMapper, GoViewProject, GoViewProjectVo, GoViewProjectBo> implements IGoViewProjectService {
  /**
   * 新增大屏项目
   */
  @Override
  public Boolean insertByBo(GoViewProjectBo bo) {
    GoViewProject add = BeanUtil.toBean(bo, GoViewProject.class);
    validEntityBeforeSave(add);
    boolean flag = baseMapper.insert(add) > 0;
    if (flag) {
      bo.setId(add.getId());
    }
    return flag;
  }
}
