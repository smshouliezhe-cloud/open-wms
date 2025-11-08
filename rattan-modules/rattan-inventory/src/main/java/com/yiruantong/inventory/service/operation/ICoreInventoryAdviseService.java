package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import com.yiruantong.inventory.domain.operation.vo.CoreInventoryAdviseVo;
import com.yiruantong.inventory.domain.operation.bo.CoreInventoryAdviseBo;

import java.util.Map;

/**
 * 建议采购转遇到货Service接口
 *
 * @author YRT
 * @date 2023-12-13
 */
public interface ICoreInventoryAdviseService extends IServicePlus<CoreInventoryAdvise, CoreInventoryAdviseVo, CoreInventoryAdviseBo> {
  /**
   * 刷新数据
   *
   */
  R<Void> refreshData();
}
