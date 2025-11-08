package com.yiruantong.system.service.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysCodeRegular;
import com.yiruantong.system.domain.dataHandler.bo.SysCodeRegularBo;
import com.yiruantong.system.domain.dataHandler.vo.SysCodeRegularVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 单据编码规则Service接口
 *
 * @author YRT
 * @date 2023-07-08
 */
public interface ISysCodeRegularService extends IServicePlus<SysCodeRegular, SysCodeRegularVo, SysCodeRegularBo> {
  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);
}
