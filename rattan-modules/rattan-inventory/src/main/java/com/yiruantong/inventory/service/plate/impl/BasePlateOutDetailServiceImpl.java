package com.yiruantong.inventory.service.plate.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.bo.BasePlateOutDetailBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailVo;
import com.yiruantong.inventory.mapper.plate.BasePlateOutDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateOutDetailService;
import org.springframework.stereotype.Service;

/**
 * 容器借出明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateOutDetailServiceImpl extends ServiceImplPlus<BasePlateOutDetailMapper, BasePlateOutDetail, BasePlateOutDetailVo, BasePlateOutDetailBo> implements IBasePlateOutDetailService {
  private final IDataAuthService dataAuthService;

  /**
   * 获取容器借出明细
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @Override
  public TableDataInfo<BasePlateOutDetailComposeVo> getBasePlateOutDetailCompose(PageQuery pageQuery) {
    IPage<BasePlateOutDetailComposeVo> ipage = pageQuery.build();
    dataAuthService.getDataAuth(pageQuery); // 数据权限
    MPJLambdaWrapper<BasePlateOutDetail> wrapper = new MPJLambdaWrapper<BasePlateOutDetail>()
      .select(BasePlateOutDetail::getPlateType)
      .selectAll(BasePlateOut.class)
      .selectAll(BasePlateOutDetail.class)
      .innerJoin(BasePlateOut.class, BasePlateOut::getOutId, BasePlateOutDetail::getOutId);

    //# 查询条件的拼接
    BuildWrapperHelper.mpjWrapperQuery(pageQuery.getQueryBoList(), wrapper, BasePlateOutDetail.class, BasePlateOut.class);

    IPage<BasePlateOutDetailComposeVo> page = this.selectJoinListPage(ipage, BasePlateOutDetailComposeVo.class, wrapper);
    TableDataInfo<BasePlateOutDetailComposeVo> tableDataInfoV = TableDataInfo.build(page);
    tableDataInfoV.setTableName(pageQuery.getTableName());
    return tableDataInfoV;
  }



}
