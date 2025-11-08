package com.yiruantong.basic.mapper.product;

import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.domain.product.vo.BaseProductTypeVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品类目管理Mapper接口
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
public interface BaseProductTypeMapper extends BaseMapperPlus<BaseProductType, BaseProductTypeVo> {

    /**
     * 获取子节点ID列表
     * @param typeId 父节点ID
     * @return 子节点ID列表
     */
    List<Long> getChildrenId(@Param("typeId") Long typeId);

}
