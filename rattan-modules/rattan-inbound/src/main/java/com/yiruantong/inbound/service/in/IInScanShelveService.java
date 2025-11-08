package com.yiruantong.inbound.service.in;

import com.yiruantong.basic.domain.storage.vo.BasePositionVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * 上架扫描Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInScanShelveService {
  /**
   * 获取上架单扫描数据
   *
   * @param map 前端参数
   */
  R<List<Map<String, Object>>> getShelveData(Map<String, Object> map);

  /**
   * 获取上架单货位
   *
   * @param map 前端参数
   */
  R<List<Map<String, Object>>> getShelvePositionList(Map<String, Object> map);

  /**
   * 上架扫描保存
   *
   * @param inScanOrderBo 前端参数
   */
  R<Void> shelveSave(@NotNull InScanOrderBo inScanOrderBo);

  /**
   * 获取单号筛选上架货位
   *
   * @param map 单号
   */
  List<BasePositionVo> searchShelvePositionList(Map<String, Object> map);

  /**
   * 无单扫描入库 - 获取数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<List<Map<String, Object>>> getShelveNoBillData(Map<String, Object> map);
}
