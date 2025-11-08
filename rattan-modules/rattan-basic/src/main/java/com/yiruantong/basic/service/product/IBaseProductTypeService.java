package com.yiruantong.basic.service.product;

import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.domain.product.bo.BaseProductTypeBo;
import com.yiruantong.basic.domain.product.vo.BaseProductTypeVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 商品类目管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
public interface IBaseProductTypeService extends IServicePlus<BaseProductType, BaseProductTypeVo, BaseProductTypeBo> {
  BaseProductType getByName(String name);

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(BaseProductTypeBo bo);

  /**
   * 新增数据
   */
  List<Long> findLeafNodesByParentIds(List<Long> ids);


  /**
   * 新增数据
   */
  Map<String, Object> findAncestorsToRoot(List<Long> ids);

  BaseProductType getByCode(String code);
}
