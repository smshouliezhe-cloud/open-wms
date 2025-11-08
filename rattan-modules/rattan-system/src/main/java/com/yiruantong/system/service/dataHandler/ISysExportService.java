package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysExport;
import com.yiruantong.system.domain.dataHandler.bo.SysExportBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportColumnVo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 导出设置Service接口
 *
 * @author YRT
 * @date 2023-08-05
 */
public interface ISysExportService extends IServicePlus<SysExport, SysExportVo, SysExportBo> {
  /**
   * 更新标准字段信息
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Void> saveFieldLeft(Map<String, Object> map);

  /**
   * 更新模块信息
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Void> saveModule(Map<String, Object> map);

  /**
   * 删除字段
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Void> deleteField(Map<String, Object> map);

  /**
   * 开启是否可用
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Void> changeEnable(Map<String, Object> map);

  /**
   * 导入模块
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Void> importModule(Map<String, Object> map);

  /**
   * 删除模块
   *
   * @param map 参数
   * @return 返回是否成功
   */
  R<Void> deleteModule(Map<String, Object> map);

  /**
   * 根据自定义ID或者主键ID获取导入信息
   *
   * @param exportId 自定义ID
   * @return SysImport实体
   */
  SysExport getInfoByCustomExportId(Long exportId);

  /**
   * 获取导出字段
   *
   * @param map 参数
   * @return List<SysExportColumnVo>
   */
  R<List<SysExportColumnVo>> selectExportColumnList(Map<String, Object> map);

  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);
}
