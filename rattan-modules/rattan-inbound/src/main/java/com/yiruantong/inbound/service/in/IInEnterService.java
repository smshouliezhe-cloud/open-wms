package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InEnterStatusEnum;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.bo.InEnterBo;
import com.yiruantong.inbound.domain.in.vo.InCreateShelveVo;
import com.yiruantong.inbound.domain.in.vo.InEnterVo;

import java.util.Map;

/**
 * 入库管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
public interface IInEnterService extends IServicePlus<InEnter, InEnterVo, InEnterBo> {

  /**
   * 生成上架单列表查询
   *
   * @param pageQuery
   * @return 返回生成上架单列表查询
   */
  TableDataInfo<InCreateShelveVo> selectCreateShelveList(PageQuery pageQuery);

  /**
   * 撤销入库
   *
   * @param map
   * @return
   */
  R<Void> cancelEnter(Map<String, Object> map);


  /**
   * 入库单状态
   *
   * @param enterId
   * @return 返回上架单信息
   */
  boolean updateEnterStatus(Long enterId, InEnterStatusEnum status);


  /**
   * 生成上架单
   *
   * @return 返回上架单信息
   * @Map<String,Object> 前段传入产生
   */
  R<Void> createShelve(Map<String, Object> map);

  /**
   * 撤销入库取消
   *
   * @param map
   * @return
   */
  R<Void> quickOut(Map<String, Object> map);

  R<Void> repeatingSorting(Map<String, Object> map);

  /**
   * 校验LPN是否有效
   *
   * @param lpnCode LPN号
   */
  void checkLpnCodeValid(String lpnCode);

  /**
   * 生成一次性费用
   *
   * @param map 入参
   * @return R
   */
  R<Void> createBill(Map<String, Object> map);

  InEnter getByCode(String orderCode);
}
