package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateOutDetailBo;

/**
 * 容器借出明细Service接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface IBasePlateOutDetailService extends IServicePlus<BasePlateOutDetail, BasePlateOutDetailVo, BasePlateOutDetailBo> {
  /**
   * 获取容器借出明细
   * @param pageQuery
   * @return
   */
  TableDataInfo<BasePlateOutDetailComposeVo> getBasePlateOutDetailCompose(PageQuery pageQuery);
}
