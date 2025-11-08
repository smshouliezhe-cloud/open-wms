package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAdjustDetailBo;

import java.util.List;

/**
 * 库存调整单明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageAdjustDetailService extends IServicePlus<StorageAdjustDetail, StorageAdjustDetailVo, StorageAdjustDetailBo> {
  /**
   * 根据主表 ID查询调整明细集合
   *
   * @param id
   * @return
   */
  List<StorageAdjustDetail> selectListByMainId(Long id);

  /**
   * 库存调整单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageAdjustDetailComposeVo> selectAdjustDetailComposeList(PageQuery pageQuery);
}
