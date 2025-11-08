package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterDetailBo;

import java.util.List;

/**
 * 盘点单明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageOuterDetailService extends IServicePlus<StorageOuterDetail, StorageOuterDetailVo, StorageOuterDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param id 主表ID
   * @return 返回明细集合
   */
  List<StorageOuterDetail> selectListByMainId(Long id);

  /**
   * 其他出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StorageOuterDetailComposeVo> selectStorageOuterDetailComposeList(PageQuery pageQuery);
}
