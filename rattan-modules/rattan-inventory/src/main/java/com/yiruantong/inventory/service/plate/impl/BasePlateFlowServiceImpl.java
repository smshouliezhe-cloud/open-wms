package com.yiruantong.inventory.service.plate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateFlow;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.bo.BasePlateFlowBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateFlowVo;
import com.yiruantong.inventory.mapper.plate.BasePlateFlowMapper;
import com.yiruantong.inventory.service.plate.IBasePlateFlowService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 容器流水记录Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateFlowServiceImpl extends ServiceImplPlus<BasePlateFlowMapper, BasePlateFlow, BasePlateFlowVo, BasePlateFlowBo> implements IBasePlateFlowService {

  /**
   * 生成容器流水记录
   *
   * @param mainInfo   主表信息
   * @param detailList 明细表信息
   */
  @Override
  public BasePlateFlow createPlateFlow(BasePlateOut mainInfo, List<BasePlateOutDetail> detailList) {

//    for(var item : detailList){
//      BasePlateFlow basePlateFlow = new BasePlateFlow();
//      String flowCode = DBUtils.getCodeRegular(MenuEnum.MENU_1810, LoginHelper.getLoginUser().getTenantId());
//
//      basePlateFlow.setFlowCode(flowCode);
//      BeanUtil.copyProperties(basePlateFlow, mainInfo);
//      basePlateFlow.setSourceCode(mainInfo.getOutCode());
//      basePlateFlow.setPlateSpec(item.getPlateSpec());
//      basePlateFlow.setPlateType(item.getPlateType());
//      basePlateFlow.setOuterQty(Convert.toLong(item.getNowOutQty())); // 借出数量
//      basePlateFlow.setReturnQty(0L); // 归还数量
//      this.save(basePlateFlow);
//    }

    // 保存占位

    return null;
  }
}
