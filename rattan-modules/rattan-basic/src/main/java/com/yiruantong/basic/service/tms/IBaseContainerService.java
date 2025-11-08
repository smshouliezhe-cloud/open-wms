package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.tms.BaseContainer;
import com.yiruantong.basic.domain.tms.bo.BaseContainerBo;
import com.yiruantong.basic.domain.tms.vo.BaseContainerVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 集装箱信息Service接口
 *
 * @author YRT
 * @date 2025-01-19
 */
public interface IBaseContainerService extends IServicePlus<BaseContainer, BaseContainerVo, BaseContainerBo> {
  /**
   * 通用 - 查询集装箱列表
   *
   * @param getListBo 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(GetListBo getListBo);
}
