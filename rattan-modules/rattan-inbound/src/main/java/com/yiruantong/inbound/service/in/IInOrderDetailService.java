package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InOrderDetailBo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailComposeVo;
import com.yiruantong.inbound.domain.in.vo.InOrderDetailVo;

import java.util.List;
import java.util.Map;

/**
 * 预到货单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInOrderDetailService extends IServicePlus<InOrderDetail, InOrderDetailVo, InOrderDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  List<InOrderDetail> selectListByMainId(Long mainId);

  /**
   * 判断明细货位全部可分拣货位
   *
   * @param storageId
   * @param orderId
   * @return 返回明细集合
   */
  boolean isAllShelvePosition(Long storageId, Long orderId);

  /**
   * 预到货单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<InOrderDetailComposeVo> selectInOrderDetailComposeList(PageQuery pageQuery);

  R<List<Map<String, Object>>> getQuickEnterList(List<Long> ids);

  R<List<Map<String, Object>>> getQuickEnterLists(List<Long> ids);

  InOrderDetail selectByMainId(Long orderId);

  void upCheckingStatus(InOrderStatusEnum inOrderStatusEnum, Long orderId);

  boolean batchRemove( Long orderId);
}
