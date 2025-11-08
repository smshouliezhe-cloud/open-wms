package com.yiruantong.basic.mapper.product;

import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 商品基础信息Mapper接口
 *
 * @author YRT
 * @date 2023-10-15
 */
public interface BaseProductMapper extends BaseMapperPlus<BaseProduct, BaseProductVo> {
}
