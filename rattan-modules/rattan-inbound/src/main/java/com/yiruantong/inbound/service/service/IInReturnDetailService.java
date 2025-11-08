package com.yiruantong.inbound.service.service;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.domain.service.vo.InReturnDetailComposeVo;
import com.yiruantong.inbound.domain.service.vo.InReturnDetailVo;
import com.yiruantong.inbound.domain.service.bo.InReturnDetailBo;

import java.util.List;

/**
 * 退货管理明细单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInReturnDetailService extends IServicePlus<InReturnDetail, InReturnDetailVo, InReturnDetailBo> {
  /**
   * 到货退货明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<InReturnDetailComposeVo> selectInReturnDetailComposeList(PageQuery pageQuery);

  /**
   * 根据主表ID查询对应明细信息
   *
   * @param mainId 查询条件
   * @return 返回查询列表数据
   */
  List<InReturnDetail> selectListByMainId(Long mainId);
}
