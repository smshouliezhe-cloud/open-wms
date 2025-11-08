package com.yiruantong.system.service.dataHandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.dataHandler.SysExportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysExportColumnBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportColumnVo;
import com.yiruantong.system.mapper.dataHandler.SysExportColumnMapper;
import com.yiruantong.system.service.dataHandler.ISysExportColumnService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导出字段Service业务层处理
 *
 * @author YRT
 * @date 2023-08-05
 */
@RequiredArgsConstructor
@Service
public class SysExportColumnServiceImpl extends ServiceImplPlus<SysExportColumnMapper, SysExportColumn, SysExportColumnVo, SysExportColumnBo> implements ISysExportColumnService {
  /**
   * 根据主表ID获取明细集合
   *
   * @param exportId
   * @return 返回明细集合
   */
  @Override
  public List<SysExportColumn> selectListByMainId(Long exportId) {
    LambdaQueryWrapper<SysExportColumn> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper
      .eq(SysExportColumn::getExportId, exportId)
      .eq(SysExportColumn::getEnable, EnableEnum.ENABLE.getId())
      .orderByDesc(SysExportColumn::getOrderNum)
      .orderByAsc(SysExportColumn::getColumnId);

    return this.list(detailLambdaQueryWrapper);

  }
}
