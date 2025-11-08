package com.yiruantong.outbound.service.out;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanDetailVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutPlanDetailComposeVo;

import java.util.List;

/**
 * 出库计划单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
public interface IOutOrderPlanDetailService extends IServicePlus<OutOrderPlanDetail, OutOrderPlanDetailVo, OutOrderPlanDetailBo> {
  /**
   * 出库计划明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<OutPlanDetailComposeVo> selectOutPlanDetailComposeList(PageQuery pageQuery);

  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<OutOrderPlanDetail> selectListByMainId(Long mainId);
}
