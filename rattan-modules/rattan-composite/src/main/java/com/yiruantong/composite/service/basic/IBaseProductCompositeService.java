package com.yiruantong.composite.service.basic;

import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;

public interface IBaseProductCompositeService {
  /**
   * 删除前事件
   *
   * @param Ids
   * @return
   */
  int deleteByIds(Long[] Ids);

  TableDataInfo<BaseProductVo> getProductList(PageQuery pageQuery);
}
