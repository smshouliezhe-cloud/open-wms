package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.bo.InEnterDetailBo;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InEnterDetailVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 入库管理明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
public interface IInEnterDetailService extends IServicePlus<InEnterDetail, InEnterDetailVo, InEnterDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<InEnterDetail> selectListByMainId(Long mainId);

  /**
   * 获取入库单明细合计数量，跨过个入库单
   *
   * @param orderId       预到货单ID
   * @param orderDetailId 预到货单明细ID
   * @return 入库单明细合计数量
   */
  BigDecimal getEnterQuantity(Long orderId, Long orderDetailId);

  /**
   * 入库记录明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<InEnterDetailComposeVo> selectInEnterDetailComposeList(PageQuery pageQuery);

  InEnterDetail getBySourceId(Long orderId, Long orderDetailId);

  List<InEnterDetail> getListBySourceId(Long orderId, Long orderDetailId);
}
