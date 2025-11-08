package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutOrderDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailComposeVo;
import com.yiruantong.outbound.domain.out.vo.OutOrderDetailVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 销售订单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
public interface IOutOrderDetailService extends IServicePlus<OutOrderDetail, OutOrderDetailVo, OutOrderDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId 主表ID
   * @return 返回明细集合
   */
  List<OutOrderDetail> selectListByMainId(Long mainId);

  /**
   * 更新缺货数量
   *
   * @param detailId           明细ID
   * @param placeholderStorage 占位数量
   * @return 返回明细集合
   */
  boolean updateLackStorage(Long detailId, BigDecimal placeholderStorage);

  /**
   * 明细订单拆分（查询）
   *
   * @param map
   * @return
   */
  List<OutOrderDetailComposeVo> selectDetailSplitList(Map<String, Object> map);

  /**
   * 拆分单据（确认）
   *
   * @param map
   * @return
   */
  Void splitOrder(Map<String, Object> map, LoginUser loginUser);

  /**
   * 出库订单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<OutOrderDetailComposeVo> selectOutOrderDetailComposeList(PageQuery pageQuery);

  R<Void> saveDetailTask(List<OutScanDetailBo> dataList);
}
