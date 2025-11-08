package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.basic.domain.tms.bo.TmsLineBo;
import com.yiruantong.basic.domain.tms.vo.TmsLineVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 线路管理Service接口
 *
 * @author YRT
 * @date 2023-12-28
 */
public interface ITmsLineService extends IServicePlus<TmsLine, TmsLineVo, TmsLineBo> {

  TmsLine getByName(String name);

  /**
   * 下拉框查询
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  List<Map<String, Object>> getList(GetListBo getListBo);

}
