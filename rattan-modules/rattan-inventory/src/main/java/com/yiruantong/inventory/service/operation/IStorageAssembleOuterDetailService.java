package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageAssembleOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleOuterDetailBo;

/**
 * 商品拆装单出库明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageAssembleOuterDetailService extends IServicePlus<StorageAssembleOuterDetail, StorageAssembleOuterDetailVo, StorageAssembleOuterDetailBo> {
  /**
   * 商品拆装单出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageAssembleOuterDetailComposeVo> selectAssembleOuterDetailComposeList(PageQuery pageQuery);
}
