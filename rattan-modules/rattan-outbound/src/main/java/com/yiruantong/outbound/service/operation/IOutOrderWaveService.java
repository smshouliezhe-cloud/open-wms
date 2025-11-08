package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.out.OutOrderWaveStatusEnum;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveComposeVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPrintVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveVo;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.util.List;
import java.util.Map;

/**
 * 出库单波次Service接口
 *
 * @author YRT
 * @date 2023-11-01
 */
public interface IOutOrderWaveService extends IServicePlus<OutOrderWave, OutOrderWaveVo, OutOrderWaveBo> {
  /**
   * 波次单编号
   *
   * @param orderWaveCode
   * @return 返回波次单信息
   */
  OutOrderWave getByCode(String orderWaveCode);

  /**
   * 取消主波次和对应的所有子波次的拣货人
   *
   * @param map
   * @return
   */
  R<Map<String, Object>> cancelNickName(Map<String, Object> map);

  /**
   * 生成波次
   *
   * @param map
   * @return
   */

  R<Void> createOrderWave(Map<String, Object> map);

  /**
   * 生成波次页面 统计商品规格
   *
   * @param map
   * @return
   */
  List<OutOrderDetail> getOrderProductSpec(Map<String, Object> map);

  /**
   * 更新波次状态
   *
   * @param orderWaveId            波次单ID
   * @param outOrderWaveStatusEnum 波次单状态
   */
  void updateWaveStatus(Long orderWaveId, OutOrderWaveStatusEnum outOrderWaveStatusEnum);

  /**
   * 更新波次状态
   *
   * @param orderWaveId            波次单ID
   * @param outOrderWaveStatusEnum 波次单状态
   * @param subOrderWaveCode       子波次单号
   */
  void updateWaveStatus(Long orderWaveId, OutOrderWaveStatusEnum outOrderWaveStatusEnum, String subOrderWaveCode);

  R<Void> onFrozenOrder(Map<String, Object> map);

  /**
   * 删除
   *
   * @param map 前端参数
   */
  R<Void> deleteData(Map<String, Object> map);

  /**
   * 查询拣配单数据
   *
   * @param queryBoList 前端参数
   */
  R<List<OutOrderWaveDetailPrintVo>> selectOrderWave(List<QueryBo> queryBoList);


  /**
   * 更新优先级
   *
   * @param map
   */
  R<Void> updatePriority(Map<String, Object> map);

  /**
   * 获取子波次集合
   *
   * @param orderWaveId 波次单ID
   * @return 返回子波次单集合
   */
  R<List<Map<String, Object>>> getSubWave(Long orderWaveId);

  /**
   * 更新波次所熟人
   *
   * @param orderWaveCode 波次号
   * @param userId        所属人ID
   * @param nickName      所属人
   * @return 返回子波次单信息
   */
  boolean updatePickUserInfo(String orderWaveCode, Long userId, String nickName);

  /**
   * 取消子波次拣货人
   *
   * @param subId 子波次ID
   * @return R
   */
  R<Void> cancelSubNickName(Long subId);

  /**
   * 自定义拣配单打印
   *
   * @param queryBoList@return R
   */
  R<Map<String, Object>> customPickBillDataList(List<QueryBo> queryBoList);

  TableDataInfo<OutOrderWaveComposeVo> pdaPageList(PageQuery pageQuery);
}
