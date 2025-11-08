package com.yiruantong.inbound.service.in;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.domain.in.bo.InShelveDetailBo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailVo;

import java.util.List;

/**
 * 商品上架明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
public interface IInShelveDetailService extends IServicePlus<InShelveDetail, InShelveDetailVo, InShelveDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<InShelveDetail> selectListByMainId(Long mainId);

  /**
   * 上架记录明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<InShelveDetailComposeVo> selectInShelveDetailComposeList(PageQuery pageQuery);

  /**
   * 根据出库单主表和明细ID获取单条数据
   *
   * @param orderId
   * @param orderDetailId
   * @return 返回明细集合
   */
  InShelveDetail getById(Long orderId, Long orderDetailId);
}
