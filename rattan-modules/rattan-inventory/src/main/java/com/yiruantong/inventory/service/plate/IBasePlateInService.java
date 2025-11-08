package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateIn;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInVo;

import java.util.Map;

/**
 * 容器归还主Service接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface IBasePlateInService extends IServicePlus<BasePlateIn, BasePlateInVo, BasePlateInBo> {
  /**
   * 明细清除
   *
   * @param map 前端传递参数
   * @return
   */
  R<Void> deleteData(Map<String, Object> map);

  /**
   * 已核对
   *
   * @param map 前端传递参数
   * @return
   */
  R<Void> isCheck(Map<String, Object> map);

  /**
   * 垫板入库审核
   *
   * @param map 前端传递参数
   * @return
   */
  R<Void> backPlateAudit(Map<String, Object> map);
}
