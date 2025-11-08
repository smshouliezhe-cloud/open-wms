package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;

import java.util.Map;

/**
 * 出库配货
 * @author xtb
 * Created 2023-12-09
 */
public interface IOutScanMatchService {
  /**
   * 获取出库配货数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getMatchData(Map<String, Object> map);

  /**
   * 保存扫描出库配货
   * @param outScanMainBo 扫描参数
   * @return 扫描下架结果
   */
  R<Void> saveMatchScan(OutScanMainBo outScanMainBo);

  /**
   * 保存扫描出库批量配货
   * @param outScanMainBo 扫描参数
   * @return 扫描下架结果
   */
  R<Void> saveMatchBatchScan(OutScanMainBo outScanMainBo);
}
