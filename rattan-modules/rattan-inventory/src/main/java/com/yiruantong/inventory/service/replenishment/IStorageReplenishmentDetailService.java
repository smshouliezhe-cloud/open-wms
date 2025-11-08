package com.yiruantong.inventory.service.replenishment;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.domain.replenishment.bo.StorageReplenishmentDetailBo;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentDetailVo;

import java.util.List;
import java.util.Map;

/**
 * 补货单明细Service接口
 *
 * @author YRT
 * @date 2024-08-23
 */
public interface IStorageReplenishmentDetailService extends IServicePlus<StorageReplenishmentDetail, StorageReplenishmentDetailVo, StorageReplenishmentDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId 主表ID
   * @return 返回明细集合
   */
  List<StorageReplenishmentDetail> selectListByMainId(Long mainId);

  /**
   * 获取补货数据
   *
   * @param map
   * @return R 返回保存结果
   */
  R<Map<String, Object>> getReplenishmentGroupData(Map<String, Object> map);
}
