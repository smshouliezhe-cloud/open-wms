package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.TmsSite;
import com.yiruantong.basic.domain.tms.bo.TmsSiteBo;
import com.yiruantong.basic.domain.tms.vo.TmsSiteVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 网点管理Service接口
 *
 * @author YRT
 * @date 2024-03-08
 */
public interface ITmsSiteService extends IServicePlus<TmsSite, TmsSiteVo, TmsSiteBo> {
  /**
   * 通用查询
   *
   * @param getListBo@return 返回查询结果
   */
  List<Map<String, Object>> getList(GetListBo getListBo);

  TmsSite getByName(String siteName);
}
