package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysExportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysExportColumnBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportColumnVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 导出字段Service接口
 *
 * @author YRT
 * @date 2023-08-05
 */
public interface ISysExportColumnService
  extends IServicePlus<SysExportColumn, SysExportColumnVo, SysExportColumnBo> {
  /**
   * 根据主表ID获取明细集合
   * @param exportId
   * @return 返回明细集合
   */
  List<SysExportColumn> selectListByMainId(Long exportId);
}
