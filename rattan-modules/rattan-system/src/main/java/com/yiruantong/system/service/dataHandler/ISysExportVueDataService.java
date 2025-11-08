package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysExportVueData;
import com.yiruantong.system.domain.dataHandler.bo.SysExportVueDataBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportVueDataVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 导出视图设置Service接口
 *
 * @author YRT
 * @date 2023-08-05
 */
public interface ISysExportVueDataService extends IServicePlus<SysExportVueData, SysExportVueDataVo, SysExportVueDataBo> {
  /**
   * 根据exportId获取VueData集合
   * @param map 请求参数
   * @return 返回VueData集合
   */
	R<List<SysExportVueDataVo>> selectByExportId(Map<String, Object> map);
  R<Void> updateTitle(Map<String, Object> map);

  R<Map<String, Object>> saveData(Map<String, Object> map);
}
