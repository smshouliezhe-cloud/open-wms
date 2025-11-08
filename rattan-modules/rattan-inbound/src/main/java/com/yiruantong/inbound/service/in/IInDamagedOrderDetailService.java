package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InDamagedOrderDetail;
import com.yiruantong.inbound.domain.in.vo.InDamagedDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InDamagedOrderDetailVo;
import com.yiruantong.inbound.domain.in.bo.InDamagedOrderDetailBo;

/**
 * 残品入库单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
public interface IInDamagedOrderDetailService extends IServicePlus<InDamagedOrderDetail, InDamagedOrderDetailVo, InDamagedOrderDetailBo> {
  /**
   * 残品入库明细查询
   *
   * @param pageQuery
   * @return
   */
  TableDataInfo<InDamagedDetailComposeVo> selectDamagedDetailComposeList(PageQuery pageQuery);
}
