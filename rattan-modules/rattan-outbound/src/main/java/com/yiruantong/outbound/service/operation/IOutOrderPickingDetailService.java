package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
import com.yiruantong.outbound.domain.operation.bo.OutOrderPickingDetailBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailComposeVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailVo;

import java.util.List;

/**
 * 订单拣货查询明细Service接口
 *
 * @author YRT
 * @date 2023-12-01
 */
public interface IOutOrderPickingDetailService extends IServicePlus<OutOrderPickingDetail, OutOrderPickingDetailVo, OutOrderPickingDetailBo> {

  /**
   * 拣货下架明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<OutOrderPickingDetailComposeVo> selectPickingDetailComposeList(PageQuery pageQuery);

  /**
   * 根据主表ID获取明细集合
   *
   * @param orderPickingId 拣货单号
   * @return 返回明细集合
   */
  List<OutOrderPickingDetail> selectListByMainId(Long orderPickingId);
}
