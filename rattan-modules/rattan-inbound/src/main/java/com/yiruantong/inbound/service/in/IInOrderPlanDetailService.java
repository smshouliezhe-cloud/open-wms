package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanDetailVo;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanDetailBo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanDetailComposeVo;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.util.List;

/**
 * 收货计划单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInOrderPlanDetailService extends IServicePlus<InOrderPlanDetail, InOrderPlanDetailVo, InOrderPlanDetailBo> {
  /**
   * 入库计划明细查询对象
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<InOrderPlanDetailComposeVo> selectPlanDetailComposeList(PageQuery pageQuery);

  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<InOrderPlanDetail> selectListByMainId(Long mainId);
}
