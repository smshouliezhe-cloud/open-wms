package com.yiruantong.inventory.service.plate.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactory;
import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnOutBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnOutComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnOutVo;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnOutMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnOutService;
import org.springframework.stereotype.Service;

/**
 * 返厂出库记录Service业务层处理
 *
 * @author YRT
 * @date 2024-03-12
 */
@RequiredArgsConstructor
@Service
public class BasePlateReturnOutServiceImpl extends ServiceImplPlus<BasePlateReturnOutMapper, BasePlateReturnOut, BasePlateReturnOutVo, BasePlateReturnOutBo> implements IBasePlateReturnOutService {

  private final IDataAuthService dataAuthService;

  /**
   * 容器返厂明细查询
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<BasePlateReturnOutComposeVo> getBasePlateReturnOutCompose(PageQuery pageQuery) {
    IPage<BasePlateReturnOutComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<BasePlateReturnOut> wrapper = new MPJLambdaWrapper<BasePlateReturnOut>()
      .selectAll(BasePlateReturnFactory.class)
      .selectAll(BasePlateReturnOut.class)
      .innerJoin(BasePlateReturnFactory.class, BasePlateReturnFactory::getReturnFactoryId, BasePlateReturnOut::getReturnFactoryId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, BasePlateReturnOut.class, BasePlateReturnFactory.class);

    IPage<BasePlateReturnOutComposeVo> page = this.selectJoinListPage(ipage, BasePlateReturnOutComposeVo.class, wrapper);
    TableDataInfo<BasePlateReturnOutComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }
}
