package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.bo.BasePlateOutBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailMergeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutVo;

import java.util.List;
import java.util.Map;

/**
 * 容器借出主Service接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface IBasePlateOutService extends IServicePlus<BasePlateOut, BasePlateOutVo, BasePlateOutBo> {
  /**
   * 容器归还根据客户名称获取源借出单号
   *
   * @param map
   * @return
   */
  List<Map<String, Object>> getSourceInfo(Map<String, Object> map);
  /**
   * 容器归还根据源借出单号查询对应的借出单信息
   * @param map
   * @return
   */
  R<BasePlateOutDetailMergeVo> getOrderInfo(Map<String, Object> map);
}
