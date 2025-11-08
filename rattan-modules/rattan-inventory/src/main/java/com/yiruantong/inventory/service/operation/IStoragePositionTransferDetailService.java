package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransferDetail;
import com.yiruantong.inventory.domain.operation.StoragePositionTransferDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StoragePositionTransferDetailBo;

import java.util.List;

/**
 * 货位转移明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStoragePositionTransferDetailService extends IServicePlus<StoragePositionTransferDetail, StoragePositionTransferDetailVo, StoragePositionTransferDetailBo> {
  /**
   * 根据主表ID获取明细集合
   *
   * @param id 主表ID
   * @return 返回明细集合
   */
  List<StoragePositionTransferDetail> selectListByMainId(Long id);

  /**
   * 货位转移明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StoragePositionTransferDetailComposeVo> selectPositionTransferDetailComposeList(PageQuery pageQuery);
}
