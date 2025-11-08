package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseAccessControl;
import com.yiruantong.basic.domain.tms.bo.BaseAccessControlBo;
import com.yiruantong.basic.domain.tms.vo.BaseAccessControlVo;
import com.yiruantong.basic.mapper.tms.BaseAccessControlMapper;
import com.yiruantong.basic.service.tms.IBaseAccessControlService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 门禁信息Service业务层处理
 *
 * @author YRT
 * @date 2024-12-26
 */
@RequiredArgsConstructor
@Service
public class BaseAccessControlServiceImpl extends ServiceImplPlus<BaseAccessControlMapper, BaseAccessControl, BaseAccessControlVo, BaseAccessControlBo> implements IBaseAccessControlService {


  //#region add
  @Override
  public R<Map<String, Object>> add(BaseAccessControlBo bo) {
    try {
      LambdaQueryWrapper<BaseAccessControl> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(BaseAccessControl::getRecordNo, bo.getRecordNo());

      BaseAccessControl baseAccessControl = this.getOnly(lambdaQueryWrapper);


      if (ObjectUtil.isNotNull(baseAccessControl)) {
        BeanUtil.copyProperties(bo, baseAccessControl, new CopyOptions().setIgnoreProperties("accessControlId"));
        this.saveOrUpdate(baseAccessControl);

        Map<String, Object> result = new HashMap<>();
        result.put("accessControlId", baseAccessControl.getAccessControlId());
        result.put("recordNo", baseAccessControl.getRecordNo());

        return R.ok("车辆入场信息更新成功", result);
      }

      baseAccessControl = new BaseAccessControl();
      BeanUtil.copyProperties(bo, baseAccessControl, new CopyOptions().setIgnoreProperties("accessControlId"));
      this.save(baseAccessControl);

      Map<String, Object> result = new HashMap<>();
      result.put("accessControlId", baseAccessControl.getAccessControlId());
      result.put("recordNo", baseAccessControl.getRecordNo());
      return R.ok("车辆入场信息保存成功", result);
    } catch (Exception error) {
      return R.fail("车辆入场信息保存失败，" + error.getMessage());
    }
  }

  //#endregion
}
