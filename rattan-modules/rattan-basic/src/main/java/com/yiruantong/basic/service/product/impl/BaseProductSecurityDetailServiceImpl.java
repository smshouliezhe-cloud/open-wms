package com.yiruantong.basic.service.product.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductSecurity;
import com.yiruantong.basic.domain.product.BaseProductSecurityDetail;
import com.yiruantong.basic.domain.product.ProductSecurity;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityDetailBo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityComposeVo;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityDetailVo;
import com.yiruantong.basic.mapper.product.BaseProductSecurityDetailMapper;
import com.yiruantong.basic.service.product.IBaseProductSecurityDetailService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 防伪标签明细Service业务层处理
 *
 * @author YRT
 * @date 2024-04-25
 */
@RequiredArgsConstructor
@Service
public class BaseProductSecurityDetailServiceImpl extends ServiceImplPlus<BaseProductSecurityDetailMapper, BaseProductSecurityDetail, BaseProductSecurityDetailVo, BaseProductSecurityDetailBo> implements IBaseProductSecurityDetailService {
  private final IDataAuthService dataAuthService;


  /**
   * 防伪码明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<BaseProductSecurityComposeVo> selectBaseProductSecurityDetail(PageQuery pageQuery) {
    IPage<BaseProductSecurityComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<BaseProductSecurityDetail> wrapper = new MPJLambdaWrapper<BaseProductSecurityDetail>()
      .selectAll(BaseProductSecurityDetail.class)
      .selectAll(BaseProductSecurity.class)
      .innerJoin(BaseProductSecurity.class, BaseProductSecurity::getSecurityId, BaseProductSecurityDetail::getSecurityId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, BaseProductSecurityDetail.class, BaseProductSecurity.class);

    IPage<BaseProductSecurityComposeVo> page = this.selectJoinListPage(ipage, BaseProductSecurityComposeVo.class, wrapper);
    TableDataInfo<BaseProductSecurityComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }

  @Override
  public R<ProductSecurity> getProductSecurity(ProductSecurity productSecurity) {

    MPJLambdaWrapper<BaseProductSecurityDetail> wrapper = new MPJLambdaWrapper<BaseProductSecurityDetail>()
      .selectAll(BaseProductSecurityDetail.class)
      .innerJoin(BaseProductSecurity.class, BaseProductSecurity::getSecurityId, BaseProductSecurityDetail::getSecurityId)
      .eq(BaseProductSecurity::getProductId, productSecurity.getProductId())
      .eq(BaseProductSecurity::getBatchCode, productSecurity.getBatchNumber())
      .eq(BaseProductSecurityDetail::getSecurityStatus, AuditEnum.NEWED.getName());
    List<BaseProductSecurityDetail> list = this.list(wrapper);
    if (ObjectUtil.isNull(list) || B.isEqual(list.size())) {
      throw new ServiceException("当前批次号没有防伪码");
    }
    if (B.isLess(list.size(), productSecurity.getQuantity())) {
      throw new ServiceException("可用防伪码数量不足!");
    }
    //获取预入库数量的防伪码
    BigDecimal total = productSecurity.getQuantity();
    List<String> codes = new ArrayList<>();
    for (var detail : list) {
      if (B.isEqual(total)) {
        break;
      }
      codes.add(detail.getSecurityCode());
      total = B.sub(total, BigDecimal.ONE);
    }
    productSecurity.setSecurityCode(CollUtil.join(codes, ","));
    return R.ok(productSecurity);
  }


}
