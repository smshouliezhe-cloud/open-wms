package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.dataHandler.SysImport;
import com.yiruantong.system.domain.dataHandler.SysImportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysImportColumnBo;
import com.yiruantong.system.domain.dataHandler.vo.SysImportColumnVo;
import com.yiruantong.system.mapper.dataHandler.SysImportColumnMapper;
import com.yiruantong.system.service.dataHandler.ISysImportColumnService;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导入字段Service业务层处理
 *
 * @author YRT
 * @date 2023-08-05
 */
@RequiredArgsConstructor
@Service
public class SysImportColumnServiceImpl extends ServiceImplPlus<SysImportColumnMapper, SysImportColumn, SysImportColumnVo, SysImportColumnBo> implements ISysImportColumnService {
  /**
   * 根据主表ID获取明细集合
   *
   * @param importId
   * @return 返回明细集合
   */
  @Override
  public List<SysImportColumn> selectListByMainId(Long importId) {
    ISysImportService sysImportService = SpringUtils.getBean(ISysImportService.class);
    SysImport sysImport = sysImportService.getInfoByCustomImportId(importId);
    Assert.isFalse(ObjectUtil.isNull(sysImport), "未获取到导入模板信息");

    LambdaQueryWrapper<SysImportColumn> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper
      .eq(SysImportColumn::getImportId, sysImport.getImportId())
      .eq(SysImportColumn::getEnable, EnableEnum.ENABLE.getId())
      .orderByDesc(SysImportColumn::getOrderNum)
      .orderByAsc(SysImportColumn::getColumnId);

    return this.list(detailLambdaQueryWrapper);
  }

  @Override
  public List<SysImportColumn> selectGroupColumns(Long importId) {
    ISysImportService sysImportService = SpringUtils.getBean(ISysImportService.class);
    SysImport sysImport = sysImportService.getInfoByCustomImportId(importId);
    Assert.isFalse(ObjectUtil.isNull(sysImport), "未获取到导入模板信息");

    // 获取模板
    LambdaQueryWrapper<SysImportColumn> columnLambdaQueryWrapper = new LambdaQueryWrapper<>();
    columnLambdaQueryWrapper.eq(SysImportColumn::getImportId, sysImport.getImportId())
      .eq(SysImportColumn::getEnable, EnableEnum.ENABLE.getId())
      .eq(SysImportColumn::getIsGroup, EnableEnum.ENABLE.getId());
    return this.getBaseMapper().selectList(columnLambdaQueryWrapper);
  }
}
