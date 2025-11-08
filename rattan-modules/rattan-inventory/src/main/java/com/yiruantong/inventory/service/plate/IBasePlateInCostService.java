package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateInCost;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInCostBo;

/**
 * 容器归还费用明细Service接口
 *
 * @author YRT
 * @date 2024-03-14
 */
public interface IBasePlateInCostService extends IServicePlus<BasePlateInCost, BasePlateInCostVo, BasePlateInCostBo> {
  /**
   * 容易归还明细查询
   * @param pageQuery
   * @return
   */
  TableDataInfo<BasePlateInCostComposeVo> getBasePlateInDetailCompose(PageQuery pageQuery);
}
