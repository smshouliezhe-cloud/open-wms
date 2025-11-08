package com.yiruantong.basic.service.product;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.product.BaseProductSet;
import com.yiruantong.basic.domain.product.vo.BaseProductSetVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSetBo;

/**
 * 商品套装主Service接口
 *
 * @author YRT
 * @date 2023-12-19
 */
public interface IBaseProductSetService extends IServicePlus<BaseProductSet, BaseProductSetVo, BaseProductSetBo> {
}
