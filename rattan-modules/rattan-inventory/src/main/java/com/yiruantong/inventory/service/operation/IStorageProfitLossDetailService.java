package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageProfitLossDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailVo;

import java.util.List;

/**
 * 盈亏单明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageProfitLossDetailService extends IServicePlus<StorageProfitLossDetail, StorageProfitLossDetailVo, StorageProfitLossDetailBo> {
  /**
   * 盈亏单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageProfitLossDetailComposeVo> selectProfitLossDetailComposeList(PageQuery pageQuery);

  /**
   * 根据主表ID获取明细集合
   *
   * @param id 主表ID
   * @return 返回明细集合
   */
  List<StorageProfitLossDetail> selectListByMainId(Long id);
}
