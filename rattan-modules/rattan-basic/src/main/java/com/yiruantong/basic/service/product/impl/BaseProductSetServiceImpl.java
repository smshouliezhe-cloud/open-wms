package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.product.bo.BaseProductSetBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSetVo;
import com.yiruantong.basic.domain.product.BaseProductSet;
import com.yiruantong.basic.mapper.product.BaseProductSetMapper;
import com.yiruantong.basic.service.product.IBaseProductSetService;

/**
 * 商品套装主Service业务层处理
 *
 * @author YRT
 * @date 2023-12-19
 */
@RequiredArgsConstructor
@Service
public class BaseProductSetServiceImpl extends ServiceImplPlus<BaseProductSetMapper, BaseProductSet, BaseProductSetVo, BaseProductSetBo> implements IBaseProductSetService {
  //#region 套装商品保存前事件
  @Override
  public void beforeSaveEditor(SaveEditorBo<BaseProductSetBo> saveEditorBo) {
    LambdaQueryWrapper<BaseProductSet> productSetLambdaQueryWrapper = new LambdaQueryWrapper<>();
    productSetLambdaQueryWrapper.eq(BaseProductSet::getProductCode, saveEditorBo.getData().getMaster().getProductCode());
    // 如果商品信息Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getProductSetId())) {
      productSetLambdaQueryWrapper.eq(BaseProductSet::getProductCode, saveEditorBo.getData().getMaster().getProductCode())
        .ne(BaseProductSet::getProductSetId, saveEditorBo.getData().getMaster().getProductSetId());
    }
    long count = this.count(productSetLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("商品编码【" + saveEditorBo.getData().getMaster().getProductCode() + "】已存在不允许重复添加，无法添加！");
    }
    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion
}
