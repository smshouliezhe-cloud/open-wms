package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateFlow;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.bo.BasePlateFlowBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateFlowVo;

import java.util.List;

/**
 * 容器流水记录Service接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface IBasePlateFlowService extends IServicePlus<BasePlateFlow, BasePlateFlowVo, BasePlateFlowBo> {

  /**
   * 生成容器流水记录
   *
   * @param mainInfo   主表信息
   * @param detailList 明细表信息
   */
  BasePlateFlow createPlateFlow(BasePlateOut mainInfo, List<BasePlateOutDetail> detailList);
}
