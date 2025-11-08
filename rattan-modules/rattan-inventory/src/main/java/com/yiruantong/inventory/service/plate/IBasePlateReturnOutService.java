package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnOutBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnOutComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnOutVo;

/**
 * 返厂出库记录Service接口
 *
 * @author YRT
 * @date 2024-03-12
 */
public interface IBasePlateReturnOutService extends IServicePlus<BasePlateReturnOut, BasePlateReturnOutVo, BasePlateReturnOutBo> {
  /**
   * 容器返厂明细查询
   * @param pageQuery
   * @return
   */
  TableDataInfo<BasePlateReturnOutComposeVo> getBasePlateReturnOutCompose(PageQuery pageQuery);
}
