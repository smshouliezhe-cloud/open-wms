package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysImportColumn;
import com.yiruantong.system.domain.dataHandler.bo.SysImportColumnBo;
import com.yiruantong.system.domain.dataHandler.vo.SysImportColumnVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 导入字段Service接口
 *
 * @author YRT
 * @date 2023-08-05
 */
public interface ISysImportColumnService extends IServicePlus<SysImportColumn, SysImportColumnVo, SysImportColumnBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param importId
   * @return 返回明细集合
   */
  List<SysImportColumn> selectListByMainId(Long importId);
  /**
   * 根据主表ID获取明细集合
   * @param importId
   * @return 返回明细集合
   */
  List<SysImportColumn> selectGroupColumns(Long importId);
}
