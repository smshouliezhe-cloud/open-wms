package com.yiruantong.basic.service.storage.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.storage.bo.BasePlateProductBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateProductVo;
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.basic.mapper.storage.BasePlateProductMapper;
import com.yiruantong.basic.service.storage.IBasePlateProductService;

/**
 * 商品容器管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-05
 */
@RequiredArgsConstructor
@Service
public class BasePlateProductServiceImpl extends ServiceImplPlus<BasePlateProductMapper, BasePlateProduct, BasePlateProductVo, BasePlateProductBo> implements IBasePlateProductService {
}
