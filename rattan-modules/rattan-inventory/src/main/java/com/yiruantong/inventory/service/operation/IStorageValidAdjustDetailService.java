package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageValidAdjustDetailBo;

import java.util.List;

/**
 * 效期信息调整明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageValidAdjustDetailService extends IServicePlus<StorageValidAdjustDetail, StorageValidAdjustDetailVo, StorageValidAdjustDetailBo> {
  List<StorageValidAdjustDetail> selectListByMainId(Long id);

  /**
   * 效期信息调整明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageValidAdjustDetailComposeVo> selectValidAdjustDetailComposeList(PageQuery pageQuery);
}
